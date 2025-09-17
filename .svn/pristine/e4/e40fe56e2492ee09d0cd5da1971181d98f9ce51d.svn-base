package com.wanmi.sbc.goods.providergoodsedit.service;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.EditStatus;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.elastic.api.provider.goods.EsGoodsInfoElasticProvider;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsInfoRequest;
import com.wanmi.sbc.goods.api.request.wechatvideo.wechatsku.WechatSkuQueryRequest;
import com.wanmi.sbc.goods.bean.dto.GoodsInfoSaveDTO;
import com.wanmi.sbc.goods.bean.dto.GoodsSaveDTO;
import com.wanmi.sbc.goods.bean.enums.AddedFlag;
import com.wanmi.sbc.goods.bean.enums.CommissionSynPriceType;
import com.wanmi.sbc.goods.bean.enums.GoodsEditType;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.goods.brand.entity.ContractBrandBase;
import com.wanmi.sbc.goods.brand.service.ContractBrandService;
import com.wanmi.sbc.goods.goodscommissionconfig.model.root.GoodsCommissionConfig;
import com.wanmi.sbc.goods.goodscommissionconfig.service.GoodsCommissionConfigService;
import com.wanmi.sbc.goods.goodscommissionpriceconfig.model.root.GoodsCommissionPriceConfig;
import com.wanmi.sbc.goods.goodscommissionpriceconfig.service.GoodsCommissionPriceService;
import com.wanmi.sbc.goods.info.model.root.GoodsInfo;
import com.wanmi.sbc.goods.info.repository.GoodsInfoRepository;
import com.wanmi.sbc.goods.info.repository.GoodsRepository;
import com.wanmi.sbc.goods.info.service.GoodsInfoService;
import com.wanmi.sbc.goods.message.StoreMessageBizService;
import com.wanmi.sbc.goods.wechatvideosku.model.root.WechatSku;
import com.wanmi.sbc.goods.wechatvideosku.repository.WechatSkuRepository;
import com.wanmi.sbc.goods.wechatvideosku.service.WechatSkuService;
import com.wanmi.sbc.mq.api.provider.MqSendProvider;
import com.wanmi.sbc.vas.api.provider.sellplatform.SellPlatformCateProvider;
import com.wanmi.sbc.vas.api.request.sellplatform.cate.SellPlatformUploadImgRequest;
import com.wanmi.sbc.vas.api.request.sellplatform.goods.SellPlatformUpdateGoodsRequest;
import com.wanmi.sbc.vas.bean.vo.sellplatform.SellPlatformGoodsInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;


/**
*
 * @description 商品代销设置服务
 * @author  wur
 * @date: 2021/9/9 14:41
 **/
@Slf4j
@Service
public class ProviderGoodsEditSynService {

    @Autowired private GoodsRepository goodsRepository;

    @Autowired private GoodsInfoRepository goodsInfoRepository;

    @Autowired private EsGoodsInfoElasticProvider esGoodsInfoElasticProvider;

    @Autowired private GoodsCommissionConfigService goodsCommissionConfigService;

    @Autowired private GoodsInfoService goodsInfoService;

    @Autowired private GoodsCommissionPriceService goodsCommissionPriceService;

    @Autowired private ProviderGoodsSynService providerGoodsSynService;

    @Autowired private WechatSkuService wechatSkuService;

    @Autowired private SellPlatformCateProvider sellPlatformCateProvider;

    @Autowired private WechatSkuRepository wechatSkuRepository;

    @Autowired private MqSendProvider mqSendProvider;

    @Autowired private StoreMessageBizService storeMessageBizService;

    @Autowired private ContractBrandService contractBrandService;

    /**
     * @description    供应商商品编辑商品信息同步，价格同步和商品基础信息同步
     * @author  wur
     * @date: 2021/9/23 16:19
     * @param providerGoods           供应商商品
     * @param provideGoodsInfoList    供应商GoodsInfo
     * @param updatePriceGoodsInfoId  改供货价的供应商GoodsInfo
     * @return
     */
    @Async
    public void synGoodsUpdate(GoodsSaveDTO providerGoods, List<GoodsInfoSaveDTO> provideGoodsInfoList,
                               List<String> updatePriceGoodsInfoId, Map<String, GoodsInfoSaveDTO> newInfoNoMap,
                               Map<String, GoodsInfoSaveDTO> delInfoNoMap, Map<GoodsEditType, ArrayList<String>> editMap) {
        log.info("======商品信息同步开始，providerGoods={}", JSONObject.toJSONString(providerGoods));

        this.updateProvideGoodsInfoId(newInfoNoMap, delInfoNoMap, provideGoodsInfoList);

        // 查询商家商品
        List<GoodsSaveDTO> goodsList = KsBeanUtil.convertList(goodsRepository.findAllByProviderGoodsId(providerGoods.getGoodsId()), GoodsSaveDTO.class);
        if (CollectionUtils.isEmpty(goodsList)) {
            return;
        }

        //查询供应商商品信息
        List<String> goodsIdList = goodsList.stream().map(GoodsSaveDTO :: getGoodsId).collect(Collectors.toList());
        List<GoodsInfoSaveDTO> goodsInfoList = KsBeanUtil.convertList(goodsInfoRepository.findByGoodsIdIn(goodsIdList), GoodsInfoSaveDTO.class);
        if (CollectionUtils.isEmpty(goodsInfoList)) {
            return;
        }
        List<Long> storeIds = goodsList.stream().map(GoodsSaveDTO :: getStoreId).collect(Collectors.toList());
        List<Long> storeIdList = new ArrayList<>();
        if (Objects.nonNull(providerGoods.getBrandId())) {
            List<ContractBrandBase> contractBrandList = contractBrandService.findStoreIdAndBrandId(storeIds, providerGoods.getBrandId());
            if (CollectionUtils.isNotEmpty(contractBrandList)) {
                storeIdList = contractBrandList.stream().map(ContractBrandBase :: getStoreId).collect(Collectors.toList());
            }
        }


        Map<String, List<GoodsInfoSaveDTO>> goodsInfoMap = goodsInfoList.stream().collect(Collectors.groupingBy(GoodsInfoSaveDTO::getGoodsId));
        Map<String, GoodsInfoSaveDTO> providerGoodsInfoMap = provideGoodsInfoList.stream().collect(Collectors.toMap(GoodsInfoSaveDTO::getGoodsInfoId, g->g));
        List<String> updateFreightTemp = new ArrayList<>();
        //根据商家查询商家代销设置
        for (GoodsSaveDTO goods : goodsList) {
            if (!goodsInfoMap.containsKey(goods.getGoodsId())) {
                continue;
            }
            //获取商家代销设置
            GoodsCommissionConfig goodsCommissionConfig = goodsCommissionConfigService.queryBytStoreId(goods.getStoreId());
            log.info("======商品信息同步 商家代销配置，CommissionConfig={}", JSONObject.toJSONString(goodsCommissionConfig));
            //更新微信视频号商品
            List<WechatSku> wechatSkus = wechatSkuService.list(WechatSkuQueryRequest.builder()
                    .delFlag(DeleteFlag.NO)
                    .notEditStatus(EditStatus.failure)
                    .goodsId(goods.getGoodsId()).build());
            //待更新的微信视频号商品
            GoodsInfoVO willUpdate = null;//可能更新的微信商品
            Boolean auditUpdate = false;//微信商品，更新的字段是否要审核
            String wechatImg = null;//微信端图片链接
            EditStatus editStatus = null;
            //验证是否自动同步基础信息
            if (DefaultFlag.YES.toValue() == goodsCommissionConfig.getInfoSynFlag().toValue()) {
                List<GoodsInfoSaveDTO> goodsInfoSaveDTOS = goodsInfoMap.get(goods.getGoodsId());
                if (CollectionUtils.isNotEmpty(wechatSkus)) {
                    WechatSku wechatSku = wechatSkus.get(0);
                    editStatus = wechatSku.getEditStatus();
                    for (GoodsInfoSaveDTO goodsInfoSaveDTO : goodsInfoSaveDTOS) {
                        if (wechatSku.getGoodsInfoId().equals(goodsInfoSaveDTO.getGoodsInfoId())) {
                            GoodsInfoSaveDTO provideGoodsInfo =
                                    providerGoodsInfoMap.get(
                                            goodsInfoSaveDTO.getProviderGoodsInfoId());
                            if (provideGoodsInfo != null) {
                                if (!goodsInfoSaveDTO
                                        .getGoodsInfoName()
                                        .equals(providerGoods.getGoodsName())) {
                                    willUpdate =
                                            JSON.parseObject(
                                                    JSON.toJSONString(goodsInfoSaveDTO),
                                                    GoodsInfoVO.class);
                                    auditUpdate = true;
                                }
                                String img =
                                        StringUtils.isNotBlank(provideGoodsInfo.getGoodsInfoImg())
                                                ? provideGoodsInfo.getGoodsInfoImg()
                                                : providerGoods.getGoodsImg();
                                if (StringUtils.isNotBlank(img)
                                        && !img.equals(goodsInfoSaveDTO.getGoodsInfoImg())) {
                                    if (willUpdate == null) {
                                        willUpdate =
                                                JSON.parseObject(
                                                        JSON.toJSONString(goodsInfoSaveDTO),
                                                        GoodsInfoVO.class);
                                    }
                                    wechatImg =
                                            sellPlatformCateProvider
                                                    .uploadImg(
                                                            new SellPlatformUploadImgRequest(img))
                                                    .getContext()
                                                    .getTemp_img_url();
                                    providerGoodsSynService.updateWechatImg(
                                            willUpdate.getGoodsInfoId(), wechatImg);
                                    auditUpdate = true;
                                } else {
                                    wechatImg = wechatSku.getImg();
                                }
                            }
                        }
                    }
                }
                boolean storeHasBrand = false;
                if (Objects.isNull(providerGoods.getBrandId()) || storeIdList.contains(goods.getStoreId())) {
                    storeHasBrand = true;
                }
                providerGoodsSynService.synGoodsInfo(
                        providerGoods, goods, provideGoodsInfoList, goodsInfoSaveDTOS, storeHasBrand);
            } else {
                if (!Objects.equals(goods.getFreightTempId(), providerGoods.getFreightTempId())) {
                    updateFreightTemp.add(goods.getGoodsId());
                }
            }

            // 验证是否智能设价
            if (CommissionSynPriceType.AI_SYN.toValue()
                            == goodsCommissionConfig.getSynPriceType().toValue()
                    && CollectionUtils.isNotEmpty(updatePriceGoodsInfoId)) {
                List<GoodsInfoSaveDTO> updateGoodsInfo = new ArrayList<>();
                for (GoodsInfoSaveDTO goodsInfo : goodsInfoMap.get(goods.getGoodsId())) {
                    if (!updatePriceGoodsInfoId.contains(goodsInfo.getProviderGoodsInfoId())) {
                        continue;
                    }
                    //根据商品查询加价比例
                    BigDecimal addRate = Objects.isNull(goodsCommissionConfig.getAddRate()) ? BigDecimal.ZERO : goodsCommissionConfig.getAddRate();
                    //根据商品查询加价比例
                    GoodsCommissionPriceConfig priceConfig = goodsCommissionPriceService.queryByGoodsInfo(goodsCommissionConfig.getStoreId(), goodsInfo);
                    if (Objects.nonNull(priceConfig)) {
                        addRate = priceConfig.getAddRate();
                    }
                    GoodsInfoSaveDTO providerGoodsInf = providerGoodsInfoMap.get(goodsInfo.getProviderGoodsInfoId());
                    //重新计算商品市场价= supplyPrice + addRate% * supplyPrice
                    BigDecimal marketPrice = providerGoodsInf.getSupplyPrice().add(goodsCommissionPriceService.getAddPrice(addRate, providerGoodsInf.getSupplyPrice()));
                    if (CollectionUtils.isNotEmpty(wechatSkus)) {
                        WechatSku wechatSku = wechatSkus.get(0);
                        if (wechatSku.getGoodsInfoId().equals(goodsInfo.getGoodsInfoId())) {
                                if (goodsInfo.getMarketPrice().compareTo(marketPrice)!=0) {
                                    if (willUpdate == null) {
                                        willUpdate = JSON.parseObject(JSON.toJSONString(goodsInfo),GoodsInfoVO.class);
                                    }
                                }
                        }
                    }
                    goodsInfo.setMarketPrice(marketPrice);
                    goodsInfo.setSupplyPrice(providerGoodsInf.getSupplyPrice());
                    goodsInfo.setCostPrice(providerGoodsInf.getSupplyPrice());
                    updateGoodsInfo.add(goodsInfo);
                }
                if (CollectionUtils.isNotEmpty(updateGoodsInfo)) {
                    goodsInfoRepository.saveAll(KsBeanUtil.convertList(updateGoodsInfo, GoodsInfo.class));
                }
            }
            //手动设价将售价小于供货价的代销商品下架
            if (CommissionSynPriceType.HAND_SYN.toValue() == goodsCommissionConfig.getSynPriceType().toValue()
                    && DefaultFlag.YES.toValue() == goodsCommissionConfig.getUnderFlag().toValue()
                    && CollectionUtils.isNotEmpty(updatePriceGoodsInfoId)){
                List<String> updateGoodsInfoId = new ArrayList<>();
                for (GoodsInfoSaveDTO goodsInfo : goodsInfoMap.get(goods.getGoodsId())) {
                    if (!updatePriceGoodsInfoId.contains(goodsInfo.getProviderGoodsInfoId())) {
                        continue;
                    }
                    GoodsInfoSaveDTO providerGoodsInf = providerGoodsInfoMap.get(goodsInfo.getProviderGoodsInfoId());
                    //代销价 < 小于供货价   商品下架操作
                    if(goodsInfo.getMarketPrice().compareTo(providerGoodsInf.getSupplyPrice()) < 0) {
                        updateGoodsInfoId.add(goodsInfo.getGoodsInfoId());
                    }
                }
                if (CollectionUtils.isNotEmpty(updateGoodsInfoId)) {
                    goodsInfoService.updateAddedStatusPlus(AddedFlag.NO.toValue(), updateGoodsInfoId);
                }
            }
            if (willUpdate != null) {
                SellPlatformUpdateGoodsRequest sellPlatformUpdateGoodsRequest;
                if (auditUpdate) {
                    willUpdate.setGoodsInfoImg(wechatImg);
                    sellPlatformUpdateGoodsRequest = JSON.parseObject(JSON.toJSONString(wechatSkuService.preAddOrUpdate(Collections.singletonList(willUpdate)).get(0)), SellPlatformUpdateGoodsRequest.class);
                    sellPlatformUpdateGoodsRequest.setTitle(goods.getGoodsName());
                    sellPlatformUpdateGoodsRequest.setHead_img(Collections.singletonList(wechatImg));
                    sellPlatformUpdateGoodsRequest.getSkus().get(0).setThumb_img(wechatImg);
                } else {
                    sellPlatformUpdateGoodsRequest = new SellPlatformUpdateGoodsRequest();
                    sellPlatformUpdateGoodsRequest.setOut_product_id(willUpdate.getGoodsId());
                    SellPlatformGoodsInfoVO sellPlatformGoodsInfoVO = new SellPlatformGoodsInfoVO();
                    sellPlatformGoodsInfoVO.setOut_sku_id(willUpdate.getGoodsInfoId());
                    sellPlatformGoodsInfoVO.setStock_num(willUpdate.getStock().intValue());
                    sellPlatformGoodsInfoVO.setSale_price(willUpdate.getMarketPrice().multiply(new BigDecimal("100")).intValue());
                    sellPlatformGoodsInfoVO.setMarket_price(willUpdate.getMarketPrice().multiply(new BigDecimal("100")).intValue());
                    sellPlatformUpdateGoodsRequest.setSkus(Collections.singletonList(sellPlatformGoodsInfoVO));
                }
                sellPlatformUpdateGoodsRequest.setAuditUpdate(auditUpdate);
                sellPlatformUpdateGoodsRequest.setEditStatus(editStatus);
                wechatSkuService.update(sellPlatformUpdateGoodsRequest);
            }

            // ============= 处理商家的消息发送：代销商品变更提醒 START =============
            storeMessageBizService.handleForCommissionGoodsChange(goods, editMap);
            // ============= 处理商家的消息发送：代销商品变更提醒 END =============

        }
        // 跟新代销商品运费模板
        if (CollectionUtils.isNotEmpty(updateFreightTemp)) {
            providerGoodsSynService.updateFreightTemp(providerGoods.getFreightTempId(), updateFreightTemp);
        }
        //更新商品ES数据
        List<String> infoIdList = goodsInfoList.stream().map(GoodsInfoSaveDTO :: getGoodsInfoId).collect(Collectors.toList());
        esGoodsInfoElasticProvider.initEsGoodsInfo(EsGoodsInfoRequest.builder().skuIds(infoIdList).build());
        log.info("======商品信息同步结束，providerGoods={}", JSONObject.toJSONString(providerGoods));
    }

    /**
     * @description SKU 编辑信息同步，主要处理价格和条形码
     * @author wur
     * @date: 2021/9/24 11:42
     * @param providerGoodsInfo
     * @return
     */
    @Async
    public void synSkuUpdate(GoodsInfo providerGoodsInfo) {

        // 根据供应商商品信息ID 查询关联的代销商品信息
        List<GoodsInfoSaveDTO> goodsInfoList =
                KsBeanUtil.convertList(
                        goodsInfoRepository.findByProviderGoodsInfoId(
                                providerGoodsInfo.getGoodsInfoId()),
                        GoodsInfoSaveDTO.class);
        if (CollectionUtils.isEmpty(goodsInfoList)) {
            return;
        }

        for (GoodsInfoSaveDTO goodsInfo : goodsInfoList) {
            // 获取商家代销设置
            GoodsCommissionConfig goodsCommissionConfig =
                    goodsCommissionConfigService.queryBytStoreId(goodsInfo.getStoreId());
            // 验证是否智能设价
            if (CommissionSynPriceType.AI_SYN.toValue()
                    == goodsCommissionConfig.getSynPriceType().toValue()) {
                // 根据商品查询加价比例
                BigDecimal addRate =
                        Objects.isNull(goodsCommissionConfig.getAddRate())
                                ? BigDecimal.ZERO
                                : goodsCommissionConfig.getAddRate();
                // 根据商品查询加价比例
                GoodsCommissionPriceConfig priceConfig =
                        goodsCommissionPriceService.queryByGoodsInfo(
                                goodsCommissionConfig.getStoreId(), goodsInfo);
                if (Objects.nonNull(priceConfig)) {
                    addRate = priceConfig.getAddRate();
                }
                // 重新计算商品市场价= supplyPrice + addRate% * supplyPrice
                BigDecimal marketPrice =
                        providerGoodsInfo
                                .getSupplyPrice()
                                .add(
                                        goodsCommissionPriceService.getAddPrice(
                                                addRate, providerGoodsInfo.getSupplyPrice()));
                goodsInfo.setMarketPrice(marketPrice);
            }
            goodsInfo.setGoodsInfoImg(providerGoodsInfo.getGoodsInfoImg());
            goodsInfo.setGoodsInfoBarcode(providerGoodsInfo.getGoodsInfoBarcode());
            goodsInfo.setStock(providerGoodsInfo.getStock());
        }
        goodsInfoRepository.saveAll(KsBeanUtil.convertList(goodsInfoList, GoodsInfo.class));
        // 更新商品ES数据
        List<String> infoIdList =
                goodsInfoList.stream()
                        .map(GoodsInfoSaveDTO::getGoodsInfoId)
                        .collect(Collectors.toList());
        esGoodsInfoElasticProvider.initEsGoodsInfo(
                EsGoodsInfoRequest.builder().skuIds(infoIdList).build());
    }

    private void updateProvideGoodsInfoId(
            Map<String, GoodsInfoSaveDTO> newInfoNoMap,
            Map<String, GoodsInfoSaveDTO> delInfoNoMap,
            List<GoodsInfoSaveDTO> oldGoodsInfo) {
        List<GoodsInfo> updateGoodsInfo = new ArrayList<>();
        newInfoNoMap.forEach(
                (k, v) -> {
                    if (delInfoNoMap.containsKey(k)) {
                        GoodsInfoSaveDTO oldInfo = delInfoNoMap.get(k);
                        oldGoodsInfo.add(v);
                        // 获取已经关联GoodsInfo
                        List<GoodsInfo> oldSku =
                                goodsInfoRepository.findByProviderGoodsInfoId(
                                        oldInfo.getGoodsInfoId());
                        if (CollectionUtils.isNotEmpty(oldSku)) {
                            oldSku.forEach(
                                    old -> {
                                        old.setProviderGoodsInfoId(v.getGoodsInfoId());
                                        old.setStock(v.getStock());
                                        updateGoodsInfo.add(old);
                                    });
                        }
                    }
                });
        if (CollectionUtils.isNotEmpty(updateGoodsInfo)) {
            goodsInfoRepository.saveAll(updateGoodsInfo);
        }
    }
}
