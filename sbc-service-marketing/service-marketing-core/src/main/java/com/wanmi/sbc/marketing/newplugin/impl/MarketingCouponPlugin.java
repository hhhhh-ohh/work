package com.wanmi.sbc.marketing.newplugin.impl;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.ares.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.PluginType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.plugin.annotation.RoutingResource;
import com.wanmi.sbc.goods.api.provider.storecate.StoreCateQueryProvider;
import com.wanmi.sbc.goods.api.request.storecate.StoreCateListByGoodsRequest;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoSimpleVO;
import com.wanmi.sbc.goods.bean.vo.MarketingPluginLabelDetailVO;
import com.wanmi.sbc.goods.bean.vo.MarketingPluginLabelVO;
import com.wanmi.sbc.goods.bean.vo.MarketingPluginSimpleLabelVO;
import com.wanmi.sbc.goods.bean.vo.StoreCateGoodsRelaVO;
import com.wanmi.sbc.marketing.api.request.countprice.TradeCountCouponPriceRequest;
import com.wanmi.sbc.marketing.api.request.newplugin.GoodsInfoPluginRequest;
import com.wanmi.sbc.marketing.api.request.newplugin.GoodsListPluginRequest;
import com.wanmi.sbc.marketing.api.response.countprice.TradeCountCouponPriceResponse;
import com.wanmi.sbc.marketing.api.response.newplugin.GoodsInfoDetailPluginResponse;
import com.wanmi.sbc.marketing.api.response.newplugin.GoodsListPluginResponse;
import com.wanmi.sbc.marketing.bean.enums.MarketingErrorCodeEnum;
import com.wanmi.sbc.marketing.bean.enums.MarketingPluginType;
import com.wanmi.sbc.marketing.countPrice.CountCouponPriceService;
import com.wanmi.sbc.marketing.coupon.model.entity.cache.CouponCache;
import com.wanmi.sbc.marketing.coupon.model.root.CouponCode;
import com.wanmi.sbc.marketing.coupon.model.root.CouponInfo;
import com.wanmi.sbc.marketing.coupon.repository.CouponCodeRepository;
import com.wanmi.sbc.marketing.coupon.repository.CouponInfoRepository;
import com.wanmi.sbc.marketing.coupon.service.CouponCacheServiceInterface;
import com.wanmi.sbc.marketing.newplugin.MarketingPluginInterface;
import com.wanmi.sbc.marketing.newplugin.common.MarketingContext;
import com.wanmi.sbc.marketing.newplugin.config.MarketingPluginService;
import com.wanmi.sbc.marketing.newplugin.impl.coupon.CouponPluginContext;
import com.wanmi.sbc.marketing.newplugin.impl.coupon.MarketingCouponPluginInterface;
import com.wanmi.sbc.marketing.newplugin.service.MarketingCouponServiceInterface;
import com.wanmi.sbc.marketing.util.mapper.MarketingGoodsInfoMapper;
import jakarta.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 优惠券插件
 *
 * @author zhanggaolei
 * @className MarketingCouponPlugin
 * @description TODO
 * @date 2021/5/19 14:08
 */
@Slf4j
@MarketingPluginService(type = MarketingPluginType.COUPON)
public class MarketingCouponPlugin implements MarketingPluginInterface, CountCouponPriceService {

    @Autowired private CouponCacheServiceInterface couponCacheServiceInterface;

    @Resource
    private StoreCateQueryProvider storeCateQueryProvider;

    @Resource private MarketingGoodsInfoMapper marketingGoodsInfoMapper;

    @Autowired private CouponCodeRepository couponCodeRepository;

    @Autowired private CouponInfoRepository couponInfoRepository;

    @Autowired private CouponPluginContext couponPluginContext;

    @Autowired
    private MarketingCouponServiceInterface marketingCouponServiceInterface;

    @Override
    public GoodsInfoDetailPluginResponse goodsDetail(GoodsInfoPluginRequest request) {
        if (CollectionUtils.isEmpty(request.getGoodsInfoPluginRequest().getStoreCateIds())) {

            String goodsId = request.getGoodsInfoPluginRequest().getGoodsId();
            StoreCateListByGoodsRequest storeCateRequest = new StoreCateListByGoodsRequest();
            storeCateRequest.setGoodsIds(Collections.singletonList(goodsId));
            Map<String, List<StoreCateGoodsRelaVO>> storeCateMap =
                    storeCateQueryProvider
                            .listByGoods(storeCateRequest)
                            .getContext()
                            .getStoreCateGoodsRelaVOList()
                            .stream()
                            .collect(Collectors.groupingBy(StoreCateGoodsRelaVO::getGoodsId));
            request.getGoodsInfoPluginRequest()
                    .setStoreCateIds(
                            storeCateMap.get(goodsId).stream()
                                    .map(StoreCateGoodsRelaVO::getStoreCateId)
                                    .collect(Collectors.toList()));
        }
        // 门店ID
        Long storeId = request.getStoreId();
        PluginType pluginType = Objects.isNull(storeId) ? PluginType.NORMAL : PluginType.O2O;
        List<CouponCache> couponCacheList =
                couponCacheServiceInterface.listCouponForGoodsInfo(
                        marketingGoodsInfoMapper.goodsInfoPluginRequestToMarketingGoods(
                                request.getGoodsInfoPluginRequest()),
                        MarketingContext.getBaseRequest().getLevelMap(),
                        storeId,
                        pluginType);
        if (CollectionUtils.isNotEmpty(couponCacheList)) {
            List<MarketingPluginLabelVO> labelList =
                    couponCacheList.stream()
                            .limit(3)
                            .map(
                                    cache -> {
                                        MarketingPluginLabelVO labelVO =
                                                new MarketingPluginLabelVO();
                                        labelVO.setMarketingId(cache.getCouponActivityId());
                                        labelVO.setLinkId(cache.getCouponInfoId());
                                        labelVO.setMarketingDesc(getLabelMap(cache));
                                        labelVO.setMarketingType(
                                                MarketingPluginType.COUPON.getId());
                                        return labelVO;
                                    })
                            .collect(Collectors.toList());

            GoodsInfoDetailPluginResponse detailResponse = new GoodsInfoDetailPluginResponse();
            detailResponse.setMarketingLabels(labelList);
            //            MarketingResponseProcess.setGoodsDetailResponseByLabel(detailResponse);

            log.debug(" couponCacheService goodsDetail process");
            return detailResponse;
        }
        return null;
    }

    @Override
    public GoodsListPluginResponse goodsList(GoodsListPluginRequest request) {
        return null;
    }

    @Override
    public MarketingPluginSimpleLabelVO check(GoodsInfoPluginRequest request) {
        return marketingCouponServiceInterface.getCouponMarketingVo(request);
    }

    @Override
    public MarketingPluginLabelDetailVO cartMarketing(GoodsInfoPluginRequest request) {

        return (MarketingPluginLabelDetailVO) this.check(request);
    }

    @Override
    public MarketingPluginLabelDetailVO tradeMarketing(GoodsInfoPluginRequest request) {

        return (MarketingPluginLabelDetailVO) this.check(request);
    }

    private <T extends MarketingPluginSimpleLabelVO> T setLabel(
            GoodsInfoPluginRequest request, Class<T> c) {
        List<CouponCache> couponCacheList = MarketingContext.getBaseRequest().getCouponCaches();
        GoodsInfoSimpleVO goodsInfo = request.getGoodsInfoPluginRequest();
        MarketingPluginLabelDetailVO label = new MarketingPluginLabelDetailVO();
        label.setMarketingType(MarketingPluginType.COUPON.getId());
        label.setMarketingDesc("券");
        List<String> couponIds = new ArrayList<>();
        for (CouponCache couponCache : couponCacheList) {

            switch (couponCache.getCouponInfo().getScopeType()) {
                case ALL:
                    if (couponCache.getCouponInfo().getStoreId().equals(goodsInfo.getStoreId())
                            || couponCache.getCouponInfo().getStoreId() == -1) {
                        couponIds.add(couponCache.getCouponInfoId());
                        continue;
                    }
                    break;
                case BRAND:
                    if (CollectionUtils.isNotEmpty(
                            couponCache.getScopes().stream()
                                    .filter(s -> s.getScopeId().equals(goodsInfo.getBrandId() + ""))
                                    .collect(Collectors.toList()))) {
                        couponIds.add(couponCache.getCouponInfoId());
                        continue;
                    }
                    break;
                case STORE_CATE:
                    if (CollectionUtils.isNotEmpty(
                            couponCache.getScopes().stream()
                                    .filter(
                                            s ->
                                                    goodsInfo
                                                            .getStoreCateIds()
                                                            .contains(
                                                                    Long.parseLong(s.getScopeId())))
                                    .collect(Collectors.toList()))) {
                        couponIds.add(couponCache.getCouponInfoId());
                        continue;
                    }
                    break;
                case O2O_CATE:
                    if (CollectionUtils.isNotEmpty(
                            couponCache.getScopes().stream()
                                    .filter(
                                            s ->
                                                    goodsInfo
                                                            .getStoreCateIds()
                                                            .contains(
                                                                    Long.parseLong(s.getScopeId())))
                                    .collect(Collectors.toList()))) {
                        couponIds.add(couponCache.getCouponInfoId());
                        continue;
                    }
                    break;
                case SKU:
                    if (CollectionUtils.isNotEmpty(
                            couponCache.getScopes().stream()
                                    .filter(
                                            s ->
                                                    s.getScopeId()
                                                            .equals(
                                                                    goodsInfo.getGoodsInfoId()
                                                                            + ""))
                                    .collect(Collectors.toList()))) {
                        couponIds.add(couponCache.getCouponInfoId());
                        continue;
                    }
                    break;
                case BOSS_CATE:
                    if (CollectionUtils.isNotEmpty(
                            couponCache.getScopes().stream()
                                    .filter(s -> s.getScopeId().equals(goodsInfo.getCateId() + ""))
                                    .collect(Collectors.toList()))) {
                        couponIds.add(couponCache.getCouponInfoId());
                        continue;
                    }
                    break;
                case STORE:
                    if (CollectionUtils.isNotEmpty(
                            couponCache.getScopes().stream()
                                    .filter(
                                            s ->
                                                    s.getScopeId()
                                                            .equals(
                                                                    goodsInfo.getStoreId()
                                                                            + ""))
                                    .collect(Collectors.toList()))) {
                        couponIds.add(couponCache.getCouponInfoId());
                        continue;
                    }
                    break;
                default:
                    break;
            }
        }
        if (CollectionUtils.isNotEmpty(couponIds)) {
            label.setMarketingId(StringUtils.join(couponIds, ","));

            return (T) label;
        }

        return null;
    }
    /**
     * 获取优惠券描述
     *
     * @return
     */
    private String getLabelMap(CouponCache coupon) {
        MarketingCouponPluginInterface couponPluginService = couponPluginContext.getCouponService(coupon.getCouponInfo().getCouponMarketingType());
        if (Objects.isNull(couponPluginService)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000021);
        }
        return couponPluginService.getLabelMap(coupon);
    }

    /**
     * 使用优惠券算价
     * @param couponPriceRequest 订单数据商品信息
     * @return
     */
    @Override
    public TradeCountCouponPriceResponse countCouponPrice(TradeCountCouponPriceRequest couponPriceRequest) {
        log.info("优惠券-算价服务 Begin，请求:{}", JSONObject.toJSONString(couponPriceRequest));
        CouponCode couponCode = couponCodeRepository.findByCouponCodeId(couponPriceRequest.getCouponCodeId(), couponPriceRequest.getCustomerId());
        // 验证优惠券码信息
        if (Objects.isNull(couponCode) || DeleteFlag.YES.equals(couponCode.getDelFlag()) || DefaultFlag.YES.equals(couponCode.getUseStatus())) {
            log.warn("优惠券-算价服务 优惠券信息有误:{}", JSONObject.toJSONString(couponPriceRequest));
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080090);
        }

        //查询券信息
        CouponInfo couponInfo = couponInfoRepository.findById(couponCode.getCouponId()).orElse(null);
        if (Objects.isNull(couponInfo)) {
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080090);
        }
        //根据券类型查询券的处理类
        MarketingCouponPluginInterface couponPluginService = couponPluginContext.getCouponService(couponInfo.getCouponMarketingType());
        if (Objects.isNull(couponPluginService)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000021);
        }
        return couponPluginService.countCouponPrice(couponCode, couponInfo, couponPriceRequest);
    }
}
