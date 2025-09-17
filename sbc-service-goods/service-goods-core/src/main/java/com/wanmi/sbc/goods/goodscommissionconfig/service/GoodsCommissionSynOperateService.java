package com.wanmi.sbc.goods.goodscommissionconfig.service;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.EnableStatus;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.elastic.api.provider.goods.EsGoodsInfoElasticProvider;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsInfoRequest;
import com.wanmi.sbc.goods.bean.dto.GoodsInfoSaveDTO;
import com.wanmi.sbc.goods.bean.enums.CommissionPriceTargetType;
import com.wanmi.sbc.goods.goodsaudit.model.root.GoodsAudit;
import com.wanmi.sbc.goods.goodsaudit.repository.GoodsAuditRepository;
import com.wanmi.sbc.goods.goodscommissionconfig.model.root.GoodsCommissionConfig;
import com.wanmi.sbc.goods.goodscommissionconfig.respository.GoodsCommissionConfigRepository;
import com.wanmi.sbc.goods.goodscommissionpriceconfig.model.root.GoodsCommissionPriceConfig;
import com.wanmi.sbc.goods.goodscommissionpriceconfig.service.GoodsCommissionPriceService;
import com.wanmi.sbc.goods.info.model.root.Goods;
import com.wanmi.sbc.goods.info.model.root.GoodsInfo;
import com.wanmi.sbc.goods.info.repository.GoodsInfoRepository;
import com.wanmi.sbc.goods.info.repository.GoodsRepository;
import com.wanmi.sbc.goods.standard.model.root.StandardSku;
import com.wanmi.sbc.goods.standard.repository.StandardSkuRepository;
import com.wanmi.sbc.goods.suppliercommissiongoods.model.root.SupplierCommissionGood;
import com.wanmi.sbc.goods.suppliercommissiongoods.repository.SupplierCommissionGoodRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;


/**
*
 * @description 商品代销设置服务
 * @author  wur
 * @date: 2021/9/9 14:41
 **/
@Service
public class GoodsCommissionSynOperateService {

    @Autowired private GoodsRepository goodsRepository;

    @Autowired private GoodsInfoRepository goodsInfoRepository;

    @Autowired private StandardSkuRepository standardSkuRepository;

    @Autowired private SupplierCommissionGoodRepository supplierCommissionGoodRepository;

    @Autowired private GoodsCommissionPriceService goodsCommissionPriceService;

    @Autowired private EsGoodsInfoElasticProvider esGoodsInfoElasticProvider;

    @Autowired private GoodsCommissionConfigRepository goodsCommissionConfigRepository;

    @Autowired private GoodsAuditRepository goodsAuditRepository;

    @Async
    public void updatePrice(GoodsCommissionConfig commissionConfig) {
        //查询商家代销商品
        List<SupplierCommissionGood> commissionGoodsList = supplierCommissionGoodRepository.findByStoreIdAndDelFlag(commissionConfig.getStoreId(), DeleteFlag.NO);
        if (CollectionUtils.isEmpty(commissionGoodsList)) {
            return;
        }
        List<String> goodsIdList = commissionGoodsList.stream().map(SupplierCommissionGood:: getGoodsId).collect(Collectors.toList());
        List<Goods> goodsList = goodsRepository.findAllByGoodsIdIn(goodsIdList);
        List<GoodsAudit> goodsAuditList = goodsAuditRepository.findByOldGoodsIds(goodsIdList);

        if (CollectionUtils.isEmpty(goodsList)) {
            return;
        }

        List<String> goodsIds = goodsList.stream()
                .filter(g -> g.getDelFlag().toValue() == DeleteFlag.NO.toValue())
                .map(Goods::getGoodsId)
                .collect(Collectors.toList());

        if (CollectionUtils.isNotEmpty(goodsAuditList)){
            goodsIds.addAll(goodsAuditList.stream()
                    .filter(g -> g.getDelFlag().toValue() == DeleteFlag.NO.toValue())
                    .map(GoodsAudit::getGoodsId)
                    .collect(Collectors.toList()));
        }


        List<GoodsInfoSaveDTO> goodsInfoList = KsBeanUtil.convertList(goodsInfoRepository.findByGoodsIdIn(goodsIds), GoodsInfoSaveDTO.class);
        List<String> providerGoodsInfoIds = goodsInfoList.stream()
                .filter(goodsInfo -> goodsInfo.getDelFlag().toValue() == DeleteFlag.NO.toValue())
                .map(GoodsInfoSaveDTO :: getProviderGoodsInfoId)
                .collect(Collectors.toList());
        List<StandardSku> standardSkuList = standardSkuRepository.findByProviderGoodsInfoIdIn(providerGoodsInfoIds);
        Map<String, StandardSku> standardSkuMap = standardSkuList.stream().collect(Collectors.toMap(StandardSku :: getProviderGoodsInfoId, standardSku -> standardSku));
        //循环处理商品信息
        List<GoodsInfoSaveDTO> updateGoodsInfo = new ArrayList<>();
        for (GoodsInfoSaveDTO goodsInfo : goodsInfoList) {
            if (!standardSkuMap.containsKey(goodsInfo.getProviderGoodsInfoId())) {
                continue;
            }
            //根据商品查询加价比例 如果有商品或类目的不使用默认的加价比例
            GoodsCommissionPriceConfig priceConfig = goodsCommissionPriceService.queryByGoodsInfo(commissionConfig.getStoreId(), goodsInfo);
            BigDecimal addRate = commissionConfig.getAddRate();
            if (Objects.nonNull(priceConfig)) {
                addRate = priceConfig.getAddRate();
            }
            StandardSku standardSku = standardSkuMap.get(goodsInfo.getProviderGoodsInfoId());
            //重新计算商品市场价= supplyPrice + addRate% * supplyPrice
            BigDecimal marketPrice = standardSku.getSupplyPrice().add(goodsCommissionPriceService.getAddPrice(addRate, standardSku.getSupplyPrice()));
            goodsInfo.setMarketPrice(marketPrice);
            goodsInfo.setBuyPoint(0L);
            //重新计算预计返佣
            if(Objects.nonNull(goodsInfo.getCommissionRate())){
                goodsInfo.setDistributionCommission(marketPrice.multiply(goodsInfo.getCommissionRate()));
            }
            updateGoodsInfo.add(goodsInfo);
        }

        if (CollectionUtils.isNotEmpty(updateGoodsInfo)) {
            //批量更新商品价格
            goodsInfoRepository.saveAll(KsBeanUtil.convertList(updateGoodsInfo, GoodsInfo.class));
            //更新商品ES数据
            List<String> goodsInfoIdList = updateGoodsInfo.stream().map(GoodsInfoSaveDTO :: getGoodsInfoId).collect(Collectors.toList());
            esGoodsInfoElasticProvider.initEsGoodsInfo(EsGoodsInfoRequest.builder().skuIds(goodsInfoIdList).build());
        }
    }


    /**
     * 商品类目加价比例变更更新商品价格
     * 1. 如果类目关闭 获取商品对应的有效加价比例，如果不存在则使用系统默认 否则验证是否SKU如果是不更新
     * 2. 开启，根据商品获取加价比例是不是该加价比例如果是则更新
     * @author  wur
     * @date: 2021/10/21 20:49
     * @param priceConfig
     * @return
     **/
    @Async
    public void updatePriceByPriceConfig(GoodsCommissionPriceConfig priceConfig) {
        //查询商家代销商品
        List<SupplierCommissionGood> commissionGoodsList = supplierCommissionGoodRepository.findByStoreIdAndDelFlag(priceConfig.getStoreId(), DeleteFlag.NO);
        if (CollectionUtils.isEmpty(commissionGoodsList)) {
            return;
        }
        List<String> goodsIdList = commissionGoodsList.stream().map(SupplierCommissionGood:: getGoodsId).collect(Collectors.toList());
        List<Goods> goodsList = goodsRepository.findAllByGoodsIdIn(goodsIdList);
        if (CollectionUtils.isEmpty(goodsList)) {
            return;
        }
        List<GoodsInfoSaveDTO> goodsInfoList = KsBeanUtil.convertList(goodsInfoRepository.findByGoodsIdIn(goodsList.stream().filter(g-> g.getDelFlag().toValue() == DeleteFlag.NO.toValue())
                .map(Goods :: getGoodsId).collect(Collectors.toList())), GoodsInfoSaveDTO.class);
        List<String> providerGoodsInfoIds = goodsInfoList.stream()
                .filter(goodsInfo -> goodsInfo.getDelFlag().toValue() == DeleteFlag.NO.toValue())
                .map(GoodsInfoSaveDTO :: getProviderGoodsInfoId)
                .collect(Collectors.toList());
        List<StandardSku> standardSkuList = standardSkuRepository.findByProviderGoodsInfoIdIn(providerGoodsInfoIds);
        Map<String, StandardSku> standardSkuMap = standardSkuList.stream().collect(Collectors.toMap(StandardSku :: getProviderGoodsInfoId, standardSku -> standardSku));
        //循环处理商品信息
        List<GoodsInfoSaveDTO> updateGoodsInfo = new ArrayList<>();
        for (GoodsInfoSaveDTO goodsInfo : goodsInfoList) {
            BigDecimal addRate = BigDecimal.ZERO;
            if (!standardSkuMap.containsKey(goodsInfo.getProviderGoodsInfoId())) {
                continue;
            }
            //验证加价比例设置是否是同一个
            GoodsCommissionPriceConfig newPriceConfig = goodsCommissionPriceService.queryByGoodsInfo(priceConfig.getStoreId(), goodsInfo);
            if (Objects.isNull(newPriceConfig)) {
                GoodsCommissionConfig commissionConfig = goodsCommissionConfigRepository.findByStoreId(priceConfig.getStoreId());
                if (Objects.isNull(commissionConfig) || Objects.isNull(commissionConfig.getAddRate())) {
                    continue;
                }
                addRate = commissionConfig.getAddRate();
            } else  {
                if (EnableStatus.ENABLE.toValue() == priceConfig.getEnableStatus().toValue()) {
                    if (!newPriceConfig.getId().equals(priceConfig.getId())) {
                        continue;
                    }
                    addRate = priceConfig.getAddRate();
                } else {
                    if (newPriceConfig.getTargetType().toValue() == CommissionPriceTargetType.SKU.toValue()) {
                        continue;
                    }
                    addRate = newPriceConfig.getAddRate();
                }
            }
            StandardSku standardSku = standardSkuMap.get(goodsInfo.getProviderGoodsInfoId());
            //重新计算商品市场价= supplyPrice + addRate% * supplyPrice
            BigDecimal marketPrice = standardSku.getSupplyPrice().add(goodsCommissionPriceService.getAddPrice(addRate, standardSku.getSupplyPrice()));
            goodsInfo.setMarketPrice(marketPrice);
            goodsInfo.setBuyPoint(0L);
            //重新计算预计返佣
            if(Objects.nonNull(goodsInfo.getCommissionRate())){
                goodsInfo.setDistributionCommission(marketPrice.multiply(goodsInfo.getCommissionRate()));
            }
            updateGoodsInfo.add(goodsInfo);
        }

        if (CollectionUtils.isNotEmpty(updateGoodsInfo)) {
            //批量更新商品价格
            goodsInfoRepository.saveAll(KsBeanUtil.convertList(updateGoodsInfo, GoodsInfo.class));
            //更新商品ES数据
            List<String> goodsInfoIdList = updateGoodsInfo.stream().map(GoodsInfoSaveDTO :: getGoodsInfoId).collect(Collectors.toList());
            esGoodsInfoElasticProvider.initEsGoodsInfo(EsGoodsInfoRequest.builder().skuIds(goodsInfoIdList).build());
        }
    }
}
