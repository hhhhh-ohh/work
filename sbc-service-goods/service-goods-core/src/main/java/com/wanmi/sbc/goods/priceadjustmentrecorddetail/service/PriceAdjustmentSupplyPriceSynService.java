package com.wanmi.sbc.goods.priceadjustmentrecorddetail.service;


import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.elastic.api.provider.goods.EsGoodsInfoElasticProvider;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsInfoRequest;
import com.wanmi.sbc.goods.api.constant.GoodsEditMsg;
import com.wanmi.sbc.goods.bean.dto.GoodsInfoSaveDTO;
import com.wanmi.sbc.goods.bean.enums.AddedFlag;
import com.wanmi.sbc.goods.bean.enums.CommissionSynPriceType;
import com.wanmi.sbc.goods.bean.enums.GoodsEditType;
import com.wanmi.sbc.goods.goodscommissionconfig.model.root.GoodsCommissionConfig;
import com.wanmi.sbc.goods.goodscommissionconfig.service.GoodsCommissionConfigService;
import com.wanmi.sbc.goods.goodscommissionpriceconfig.model.root.GoodsCommissionPriceConfig;
import com.wanmi.sbc.goods.goodscommissionpriceconfig.service.GoodsCommissionPriceService;
import com.wanmi.sbc.goods.info.model.root.GoodsInfo;
import com.wanmi.sbc.goods.info.repository.GoodsInfoRepository;
import com.wanmi.sbc.goods.info.service.GoodsInfoService;
import com.wanmi.sbc.goods.providergoodsedit.service.ProviderGoodsEditDetailService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
*
 * @description 供货价同步
 * @author  wur
 * @date: 2021/10/27 18:31
 **/
@Service
public class PriceAdjustmentSupplyPriceSynService {

    @Autowired private GoodsInfoRepository goodsInfoRepository;

    @Autowired private EsGoodsInfoElasticProvider esGoodsInfoElasticProvider;

    @Autowired private GoodsCommissionConfigService goodsCommissionConfigService;

    @Lazy
    @Autowired private GoodsInfoService goodsInfoService;

    @Autowired private GoodsCommissionPriceService goodsCommissionPriceService;

    @Autowired private ProviderGoodsEditDetailService providerGoodsEditDetailService;

    @Async
    public void synSupplyPrice(String providerGoodsInfoId, BigDecimal supplyPrice) {
        //根据供应商商品Id验证
        GoodsInfo providerGoodsInfo = goodsInfoRepository.findByGoodsInfoIdAndDelFlag(providerGoodsInfoId, DeleteFlag.NO);
        if (Objects.isNull(providerGoodsInfo)) {
            return;
        }

        //获取供应商商品信息
        List<GoodsInfoSaveDTO> goodsInfoList = KsBeanUtil.convertList(goodsInfoRepository.findByProviderGoodsInfoId(providerGoodsInfoId), GoodsInfoSaveDTO.class);
        if (CollectionUtils.isEmpty(goodsInfoList)) {
            return;
        }

        //记录代销价格变更日志
        providerGoodsEditDetailService.addGoodsEdit(providerGoodsInfo.getGoodsId(),
                GoodsEditType.PRICE_EDIT,
                GoodsEditMsg.UPDATE_GOODS_PRICE);

        //循环处理代销商品
        List<GoodsInfoSaveDTO> updateGoodsInfo = new ArrayList<>();
        List<String> updateGoodsInfoId = new ArrayList<>();
        for(GoodsInfoSaveDTO goodsInfo : goodsInfoList) {
            //获取商家代销设置
            GoodsCommissionConfig goodsCommissionConfig = goodsCommissionConfigService.queryBytStoreId(goodsInfo.getStoreId());

            //验证是否智能设价
            if( CommissionSynPriceType.AI_SYN.toValue() == goodsCommissionConfig.getSynPriceType().toValue()) {
                //根据商品查询加价比例
                BigDecimal addRate = Objects.isNull(goodsCommissionConfig.getAddRate()) ? BigDecimal.ZERO : goodsCommissionConfig.getAddRate();
                //根据商品查询加价比例
                GoodsCommissionPriceConfig priceConfig = goodsCommissionPriceService.queryByGoodsInfo(goodsCommissionConfig.getStoreId(), goodsInfo);
                if (Objects.nonNull(priceConfig)) {
                    addRate = priceConfig.getAddRate();
                }
                //重新计算商品市场价= supplyPrice + addRate% * supplyPrice
                BigDecimal marketPrice = supplyPrice.add(goodsCommissionPriceService.getAddPrice(addRate, supplyPrice));
                goodsInfo.setMarketPrice(marketPrice);
                updateGoodsInfo.add(goodsInfo);

            }
            //手动设价将售价小于供货价的代销商品下架
            if (CommissionSynPriceType.HAND_SYN.toValue() == goodsCommissionConfig.getSynPriceType().toValue()
                    && DefaultFlag.YES.toValue() == goodsCommissionConfig.getUnderFlag().toValue()){
                //代销价 < 小于供货价   商品下架操作
                if(goodsInfo.getMarketPrice().compareTo(supplyPrice) < 0) {
                    updateGoodsInfoId.add(goodsInfo.getGoodsInfoId());
                }
            }
        }

        //跟新数据并同步ES
        if (CollectionUtils.isNotEmpty(updateGoodsInfo)) {
            goodsInfoRepository.saveAll(KsBeanUtil.convertList(updateGoodsInfo, GoodsInfo.class));
            List<String> infoIdList = updateGoodsInfo.stream().map(GoodsInfoSaveDTO :: getGoodsInfoId).collect(Collectors.toList());
            esGoodsInfoElasticProvider.initEsGoodsInfo(EsGoodsInfoRequest.builder().skuIds(infoIdList).build());
        }

        if (CollectionUtils.isNotEmpty(updateGoodsInfoId)) {
            goodsInfoService.updateAddedStatusPlus(AddedFlag.NO.toValue(), updateGoodsInfoId);
        }
    }

}

