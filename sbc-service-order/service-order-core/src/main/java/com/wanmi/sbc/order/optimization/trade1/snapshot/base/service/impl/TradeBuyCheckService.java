package com.wanmi.sbc.order.optimization.trade1.snapshot.base.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.base.PlatformAddress;
import com.wanmi.sbc.common.constant.VASStatus;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.VASConstants;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.IteratorUtils;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.bean.vo.CustomerVO;
import com.wanmi.sbc.customer.bean.vo.StoreCustomerRelaVO;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.empower.api.request.channel.base.ChannelSkuOffAddedSyncRequest;
import com.wanmi.sbc.goods.api.constant.GoodsRestrictedErrorTips;
import com.wanmi.sbc.goods.api.provider.goodsrestrictedsale.GoodsRestrictedSaleQueryProvider;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.request.goodsrestrictedsale.GoodsRestrictedBatchValidateRequest;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoListByIdsRequest;
import com.wanmi.sbc.goods.bean.enums.GoodsStatus;
import com.wanmi.sbc.goods.bean.vo.BookingSaleVO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoBaseVO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.marketing.api.provider.distribution.DistributionSettingQueryProvider;
import com.wanmi.sbc.marketing.bean.vo.MarketingVO;
import com.wanmi.sbc.mq.api.provider.MqSendProvider;
import com.wanmi.sbc.mq.bean.constants.ProducerTopic;
import com.wanmi.sbc.mq.bean.dto.MqSendDTO;
import com.wanmi.sbc.order.api.optimization.trade1.request.TradeItemRequest;
import com.wanmi.sbc.order.api.request.appointmentrecord.AppointmentRecordQueryRequest;
import com.wanmi.sbc.order.appointmentrecord.model.root.AppointmentRecord;
import com.wanmi.sbc.order.appointmentrecord.service.AppointmentRecordService;
import com.wanmi.sbc.order.bean.constant.TradeBuyErrorCode;
import com.wanmi.sbc.order.bean.enums.OrderErrorCodeEnum;

import com.wanmi.sbc.order.optimization.trade1.snapshot.ParamsDataVO;
import com.wanmi.sbc.order.optimization.trade1.snapshot.base.service.TradeBuyCheckInterface;
import com.wanmi.sbc.order.thirdplatformtrade.service.ThirdPlatformTradeService;
import com.wanmi.sbc.setting.bean.enums.ConfigKey;
import com.wanmi.sbc.vas.api.provider.channel.goods.ChannelGoodsProvider;
import com.wanmi.sbc.vas.api.request.channel.ChannelGoodsVerifyRequest;
import com.wanmi.sbc.vas.api.response.channel.ChannelGoodsVerifyResponse;
import com.wanmi.sbc.vas.bean.dto.channel.ChannelGoodsInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author 下单校验
 * @className TradeBuyCheckService
 * @description TODO
 * @date 2022/2/24 10:36
 */
@Service
@Slf4j
public class TradeBuyCheckService implements TradeBuyCheckInterface {

    @Autowired private GoodsRestrictedSaleQueryProvider goodsRestrictedSaleQueryProvider;

    @Autowired private RedisUtil redisUtil;

    @Autowired private ChannelGoodsProvider channelGoodsProvider;

    @Autowired private MqSendProvider mqSendProvider;

    @Autowired private AppointmentRecordService appointmentRecordService;

    @Autowired private GoodsInfoQueryProvider goodsInfoQueryProvider;

    @Autowired ThirdPlatformTradeService thirdPlatformTradeService;

    @Autowired DistributionSettingQueryProvider distributionSettingQueryProvider;

    @Override
    public void validateOrderRestricted(GoodsRestrictedBatchValidateRequest request) {
        goodsRestrictedSaleQueryProvider.validateOrderRestricted(request);
    }

    @Override
    public void verifyChannelGoods(ParamsDataVO paramsDataVO) {
        List<GoodsInfoVO> skuList = paramsDataVO.getGoodsInfoResponse().getGoodsInfos();
        List<TradeItemRequest> tradeItemRequests = paramsDataVO.getRequest().getTradeItemRequests();
        if (CollectionUtils.isNotEmpty(skuList)
                && skuList.stream().anyMatch(sku -> Objects.nonNull(sku.getThirdPlatformType()))) {
            // 是否开启LM
            boolean vasFlag =
                    !VASStatus.ENABLE
                            .toValue()
                            .equals(
                                    redisUtil.hget(
                                            ConfigKey.VALUE_ADDED_SERVICES.toString(),
                                            VASConstants.THIRD_PLATFORM_LINKED_MALL.toValue()));

            // 是否开启VOP
            if (VASStatus.ENABLE
                    .toValue()
                    .equals(
                            redisUtil.hget(
                                    ConfigKey.VALUE_ADDED_SERVICES.toString(),
                                    VASConstants.THIRD_PLATFORM_VOP.toValue()))) {
                vasFlag = false;
            }
            if (vasFlag) {
                log.error("渠道商商品校验失败");
                throw new SbcRuntimeException(OrderErrorCodeEnum.K050119);
            }
            List<ChannelGoodsInfoDTO> goodsInfoList =
                    KsBeanUtil.convert(skuList, ChannelGoodsInfoDTO.class);
            goodsInfoList.forEach(s -> s.setBuyCount(1L));
            if (CollectionUtils.isNotEmpty(tradeItemRequests)) {
                goodsInfoList =
                        IteratorUtils.zip(
                                goodsInfoList,
                                tradeItemRequests,
                                (a, b) -> a.getGoodsInfoId().equals(b.getSkuId()),
                                (c, d) -> {
                                    c.setBuyCount(d.getNum());
                                });
            }
            ChannelGoodsVerifyRequest verifyRequest = new ChannelGoodsVerifyRequest();
            verifyRequest.setGoodsInfoList(goodsInfoList);
            ChannelGoodsVerifyResponse verifyResponse =
                    channelGoodsProvider.verifyGoods(verifyRequest).getContext();
            if (verifyResponse.getInvalidGoods()) {
                // 下架商品
                if (CollectionUtils.isNotEmpty(verifyResponse.getOffAddedSkuId())) {
                    List<String> providerSkuId = verifyResponse.getOffAddedSkuId();
                    try {
                        ChannelSkuOffAddedSyncRequest request = new ChannelSkuOffAddedSyncRequest();
                        request.setProviderSkuId(providerSkuId);
                        MqSendDTO mqSendDTO = new MqSendDTO();
                        mqSendDTO.setTopic(ProducerTopic.THIRD_PLATFORM_SKU_OFF_ADDED_FLAG);
                        mqSendDTO.setData(JSONObject.toJSONString(request));
                        mqSendProvider.send(mqSendDTO);
                    } catch (Exception e) {
                        log.error("第三方订单验证调用-下架SKU商品MQ异常", e);
                    }
                }
                log.error("渠道商商品失效");
                throw new SbcRuntimeException(OrderErrorCodeEnum.K050119);
            }
        }
    }

    @Override
    public void validateAppointmentQualification(ParamsDataVO paramsDataVO) {
        String customerId = paramsDataVO.getCustomerVO().getCustomerId();
        if (CollectionUtils.isEmpty(paramsDataVO.getAppointmentSaleVOS())) return;
        paramsDataVO
                .getAppointmentSaleVOS()
                .forEach(
                        a -> {
                            if (!(a.getSnapUpStartTime().isBefore(LocalDateTime.now())
                                    && a.getSnapUpEndTime().isAfter(LocalDateTime.now()))) {
                                throw new SbcRuntimeException(
                                        OrderErrorCodeEnum.K050125);
                            }
                            if (a.getAppointmentType().equals(NumberUtils.INTEGER_ONE)) {
                                // 判断活动是否是全平台客户
                                if (!a.getJoinLevel().equals("-1")) {
                                    // 第三方商家
                                    StoreVO storeVO = paramsDataVO.getStoreVO();
                                    CustomerVO customerVO = paramsDataVO.getCustomerVO();
                                    if (BoolFlag.YES.equals(storeVO.getCompanyType())) {
                                        List<StoreCustomerRelaVO> relaVOList =
                                                paramsDataVO.getStoreCustomerRelaVOS();
                                        if (Objects.nonNull(relaVOList) && relaVOList.size() > 0) {
                                            if (!a.getJoinLevel().equals("0")
                                                    && !Arrays.asList(a.getJoinLevel().split(","))
                                                            .contains(
                                                                    relaVOList
                                                                            .get(0)
                                                                            .getStoreLevelId()
                                                                            .toString())) {
                                                log.error(
                                                        "会员ID：{}，订单异常：此商品预约活动范围只限于店铺内会员",
                                                        customerId);
                                                throw new SbcRuntimeException(
                                                        OrderErrorCodeEnum.K050130);
                                            }
                                        } else {
                                            log.error(
                                                    "会员ID：{}，订单异常：此商品预约活动范围只限于店铺内会员, 店铺等级为空",
                                                    customerId);
                                            throw new SbcRuntimeException(
                                                    OrderErrorCodeEnum.K050130);
                                        }
                                    } else {
                                        if (!a.getJoinLevel().equals("0")
                                                && !Arrays.asList(a.getJoinLevel().split(","))
                                                        .contains(
                                                                Objects.toString(
                                                                        customerVO
                                                                                .getCustomerLevelId()))) {
                                            log.error(
                                                    "会员ID：{}，订单异常：此商品预约活动范围只限于指定会员等级", customerId);
                                            throw new SbcRuntimeException(
                                                    OrderErrorCodeEnum.K050130);
                                        }
                                    }
                                }
                            } else {
                                AppointmentRecord appointmentRecord =
                                        appointmentRecordService.getAppointmentInfo(
                                                AppointmentRecordQueryRequest.builder()
                                                        .buyerId(customerId)
                                                        .goodsInfoId(
                                                                a.getAppointmentSaleGood()
                                                                        .getGoodsInfoId())
                                                        .appointmentSaleId(a.getId())
                                                        .build());
                                if (Objects.isNull(appointmentRecord)) {
                                    throw new SbcRuntimeException(
                                            OrderErrorCodeEnum.K050127);
                                }
                            }
                        });
    }

    public void validateBookingSale(ParamsDataVO paramsDataVO) {
        if (CollectionUtils.isEmpty(paramsDataVO.getBookingSaleVOS())) return;
        List<TradeItemRequest> tradeItemRequests = paramsDataVO.getRequest().getTradeItemRequests();
        Map<Long, BookingSaleVO> bookingSaleVOMap =
                paramsDataVO.getBookingSaleVOS().stream()
                        .collect(Collectors.toMap(BookingSaleVO::getId, Function.identity()));

        tradeItemRequests.forEach(
                item -> {
                    if (item.getIsBookingSaleGoods()) {
                        BookingSaleVO bookingSaleVO = bookingSaleVOMap.get(item.getBookingSaleId());
                        if (bookingSaleVO.getPauseFlag() == 1) {
                            throw new SbcRuntimeException(OrderErrorCodeEnum.K050128);
                        }
                        if (bookingSaleVO.getBookingType().equals(NumberUtils.INTEGER_ONE)) {
                            if (bookingSaleVO.getHandSelEndTime().isBefore(LocalDateTime.now())
                                    || bookingSaleVO
                                            .getHandSelStartTime()
                                            .isAfter(LocalDateTime.now())) {
                                throw new SbcRuntimeException(
                                        OrderErrorCodeEnum.K050128);
                            }
                        }
                        if (bookingSaleVO.getBookingType().equals(NumberUtils.INTEGER_ZERO)) {
                            if (bookingSaleVO.getBookingEndTime().isBefore(LocalDateTime.now())
                                    || bookingSaleVO
                                            .getBookingStartTime()
                                            .isAfter(LocalDateTime.now())) {
                                throw new SbcRuntimeException(
                                        OrderErrorCodeEnum.K050128);
                            }
                        }
                        // 判断活动是否是全平台客户还是店铺内客户
                        if (!bookingSaleVO.getJoinLevel().equals("-1")) {
                            // 第三方商家
                            StoreVO storeVO = paramsDataVO.getStoreVO();
                            CustomerVO customerVO = paramsDataVO.getCustomerVO();
                            if (BoolFlag.YES.equals(storeVO.getCompanyType())) {
                                List<StoreCustomerRelaVO> relaVOList =
                                        paramsDataVO.getStoreCustomerRelaVOS();
                                if (Objects.nonNull(relaVOList) && relaVOList.size() > 0) {
                                    if (!bookingSaleVO.getJoinLevel().equals("0")
                                            && !Arrays.asList(
                                                            bookingSaleVO.getJoinLevel().split(","))
                                                    .contains(
                                                            relaVOList
                                                                    .get(0)
                                                                    .getStoreLevelId()
                                                                    .toString())) {
                                        log.error(
                                                "会员ID：{}，订单异常：此商品预约活动范围只限于店铺内会员",
                                                paramsDataVO.getRequest().getCustomerId());
                                        throw new SbcRuntimeException(
                                                OrderErrorCodeEnum.K050130);
                                    }
                                } else {
                                    log.error(
                                            "会员ID：{}，订单异常：此商品预约活动范围只限于店铺内会员, 店铺等级为空",
                                            paramsDataVO.getRequest().getCustomerId());
                                    throw new SbcRuntimeException(
                                            OrderErrorCodeEnum.K050130);
                                }
                            } else {
                                if (!bookingSaleVO.getJoinLevel().equals("0")
                                        && !Arrays.asList(bookingSaleVO.getJoinLevel().split(","))
                                                .contains(
                                                        Objects.toString(
                                                                customerVO.getCustomerLevelId()))) {
                                    log.error(
                                            "会员ID：{}，订单异常：此商品预约活动范围只限于指定会员等级",
                                            paramsDataVO.getRequest().getCustomerId());
                                    throw new SbcRuntimeException(
                                            OrderErrorCodeEnum.K050130);
                                }
                            }
                        }

                        // 预售商品库存校验
                        if (Objects.nonNull(bookingSaleVO.getBookingSaleGoods().getBookingCount())
                                && Objects.nonNull(
                                        bookingSaleVO.getBookingSaleGoods().getCanBookingCount())) {
                            if (item.getNum()
                                    > (bookingSaleVO.getBookingSaleGoods().getCanBookingCount())) {
                                throw new SbcRuntimeException(
                                        OrderErrorCodeEnum.K050164,
                                        new Object[] {String.format(
                                                GoodsRestrictedErrorTips.GOODS_PURCHASE_MOST_NUMBER,
                                                bookingSaleVO
                                                        .getBookingSaleGoods()
                                                        .getCanBookingCount())});
                            }
                        }
                    }
                });
    }

    public void validateGoodsStock(ParamsDataVO paramsDataVO) {
        List<GoodsInfoVO> goodsInfos = paramsDataVO.getGoodsInfoResponse().getGoodsInfos();
        Map<String, GoodsInfoVO> skuIdGoodsInfoMap =
                goodsInfos.stream()
                        .collect(
                                Collectors.toMap(GoodsInfoVO::getGoodsInfoId, Function.identity()));
        List<String> goodsInfoIds = new ArrayList<>();
        List<GoodsInfoVO> channelList = new ArrayList<>();
        paramsDataVO
                .getGoodsInfoResponse()
                .getGoodsInfos()
                .forEach(
                        g -> {
                            if (g.getGoodsStatus().equals(GoodsStatus.OK)) {
                                if (StringUtils.isNotBlank(g.getProviderGoodsInfoId())
                                        && Objects.nonNull(g.getThirdPlatformType())) {
                                    channelList.add(g);
                                } else {
                                    if (StringUtils.isNotEmpty(g.getProviderGoodsInfoId())) {
                                        goodsInfoIds.add(g.getProviderGoodsInfoId());
                                    } else {
                                        goodsInfoIds.add(g.getGoodsInfoId());
                                    }
                                }
                            }
                        });
        // 正常商品库存/ 供应商商品库存
        if (CollectionUtils.isNotEmpty(goodsInfoIds)) {
            Map<String, Long> skuStockMap =
                    goodsInfoQueryProvider
                            .getStockByGoodsInfoIds(
                                    GoodsInfoListByIdsRequest.builder()
                                            .goodsInfoIds(goodsInfoIds)
                                            .build())
                            .getContext();
            if (MapUtils.isNotEmpty(skuStockMap)) {
                paramsDataVO.getGoodsInfoResponse().getGoodsInfos().stream()
                        .filter(g -> GoodsStatus.OK.equals(g.getGoodsStatus()))
                        .forEach(
                                g -> {
                                    Long stock = 0L;
                                    if (StringUtils.isNotEmpty(g.getProviderGoodsInfoId())) {
                                        stock = skuStockMap.get(g.getProviderGoodsInfoId());
                                    } else {
                                        stock = skuStockMap.get(g.getGoodsInfoId());
                                    }
                                    g.setStock(stock);
                                    if (stock == null || stock < 1) {
                                        g.setGoodsStatus(GoodsStatus.OUT_STOCK);
                                    }
                                });
            }
        }

        // 渠道商品库存
        if (CollectionUtils.isNotEmpty(channelList)) {
            String[] addr = paramsDataVO.getRequest().getAddressId().split("\\|");
            String provinceId = addr[0];
            String cityId = addr[1];
            String aresId = addr[2];
            String streetId = addr[3];
            PlatformAddress platformAddress =
                    PlatformAddress.builder()
                            .provinceId(provinceId)
                            .cityId(cityId)
                            .areaId(aresId)
                            .streetId(streetId)
                            .build();
            List<GoodsInfoBaseVO> goodsInfoBaseVOS =
                    KsBeanUtil.convert(
                            paramsDataVO.getGoodsInfoResponse().getGoodsInfos(),
                            GoodsInfoBaseVO.class);
            thirdPlatformTradeService.cartStatus(goodsInfoBaseVOS, platformAddress);
            goodsInfoBaseVOS.forEach(goodsInfoBaseVO -> {
                GoodsInfoVO goodsInfoVO = skuIdGoodsInfoMap.get(goodsInfoBaseVO.getGoodsInfoId());
                goodsInfoVO.setStock(goodsInfoBaseVO.getStock() == null ? 0 : goodsInfoBaseVO.getStock());
            });
        }

        paramsDataVO
                .getRequest()
                .getTradeItemRequests()
                .forEach(
                        tradeItemRequest -> {
                            GoodsInfoVO goodsInfoVO =
                                    skuIdGoodsInfoMap.get(tradeItemRequest.getSkuId());
                            long num = tradeItemRequest.getNum();
                            if (paramsDataVO.getBuyCycleVO() != null) {
                                num = paramsDataVO.getRequest().getDeliveryCycleNum() * tradeItemRequest.getNum();
                            }
                            if (num > goodsInfoVO.getStock()) {
                                throw new SbcRuntimeException(
                                        OrderErrorCodeEnum.K050122);
                            }
                        });
    }

    @Override
    public void validateMarketingSuits(ParamsDataVO paramsDataVO) {
        String customerId = paramsDataVO.getCustomerVO().getCustomerId();
        MarketingVO marketingVO = paramsDataVO.getMarketingResponse().getMarketingVO();
        if (marketingVO == null) return;
        if (marketingVO.getIsPause() == BoolFlag.YES
                || marketingVO.getDelFlag() == DeleteFlag.YES
                || marketingVO.getBeginTime().isAfter(LocalDateTime.now())
                || marketingVO.getEndTime().isBefore(LocalDateTime.now())) {
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050161);
        }

        // 判断活动是否是全平台客户
        if (!marketingVO.getJoinLevel().equals("-1")) {
            // 第三方商家
            StoreVO storeVO = paramsDataVO.getStoreVO();
            CustomerVO customerVO = paramsDataVO.getCustomerVO();
            if (BoolFlag.YES.equals(storeVO.getCompanyType())) {
                List<StoreCustomerRelaVO> relaVOList = paramsDataVO.getStoreCustomerRelaVOS();
                if (Objects.nonNull(relaVOList) && relaVOList.size() > 0) {
                    if (!marketingVO.getJoinLevel().equals("0")
                            && !Arrays.asList(marketingVO.getJoinLevel().split(","))
                                    .contains(relaVOList.get(0).getStoreLevelId().toString())) {
                        log.error("会员ID：{}，订单异常：此商品组合购活动范围只限于店铺内会员", customerId);
                        throw new SbcRuntimeException(OrderErrorCodeEnum.K050130);
                    }
                } else {
                    log.error("会员ID：{}，订单异常：此商品组合购活动范围只限于店铺内会员, 店铺等级为空", customerId);
                    throw new SbcRuntimeException(OrderErrorCodeEnum.K050130);
                }
            } else {
                if (!marketingVO.getJoinLevel().equals("0")
                        && !Arrays.asList(marketingVO.getJoinLevel().split(","))
                                .contains(Objects.toString(customerVO.getCustomerLevelId()))) {
                    log.error("会员ID：{}，订单异常：此商品组合购活动范围只限于指定会员等级", customerId);
                    throw new SbcRuntimeException(OrderErrorCodeEnum.K050130);
                }
            }
        }
    }

    @Override
    public void validateStoreBag(ParamsDataVO paramsDataVO) {
        Boolean isStoreBag =
                distributionSettingQueryProvider
                        .isStoreBag(paramsDataVO.getRequest().getTradeItemRequests().get(0).getSkuId())
                        .getContext();
        if (!isStoreBag) {
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050027);
        }
    }
}
