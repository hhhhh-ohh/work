package com.wanmi.sbc.order;

import com.alibaba.fastjson2.JSONObject;
import com.google.common.collect.Lists;
import com.wanmi.sbc.account.api.provider.offline.OfflineQueryProvider;
import com.wanmi.sbc.account.api.request.offline.OfflineAccountGetByIdRequest;
import com.wanmi.sbc.account.api.response.offline.OfflineAccountGetByIdResponse;
import com.wanmi.sbc.account.bean.enums.AccountErrorCodeEnum;
import com.wanmi.sbc.account.bean.enums.AccountStatus;
import com.wanmi.sbc.account.bean.enums.PayType;
import com.wanmi.sbc.account.bean.enums.PayWay;
import com.wanmi.sbc.account.request.PaymentRecordRequest;
import com.wanmi.sbc.common.annotation.MultiSubmit;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.DistributeChannel;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.common.enums.*;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.redis.CacheKeyConstant;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.*;
import com.wanmi.sbc.common.validation.Assert;
import com.wanmi.sbc.customer.api.provider.address.CustomerDeliveryAddressQueryProvider;
import com.wanmi.sbc.customer.api.provider.customer.CustomerQueryProvider;
import com.wanmi.sbc.customer.api.provider.distribution.DistributionCustomerQueryProvider;
import com.wanmi.sbc.customer.api.provider.level.CustomerLevelQueryProvider;
import com.wanmi.sbc.customer.api.provider.payingmembercustomerrel.PayingMemberCustomerRelQueryProvider;
import com.wanmi.sbc.customer.api.provider.store.StoreCustomerQueryProvider;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.customer.CustomerGetByIdRequest;
import com.wanmi.sbc.customer.api.request.distribution.DistributionCustomerByCustomerIdRequest;
import com.wanmi.sbc.customer.api.request.level.CustomerLevelByCustomerIdAndStoreIdRequest;
import com.wanmi.sbc.customer.api.request.level.CustomerLevelMapByCustomerIdAndStoreIdsRequest;
import com.wanmi.sbc.customer.api.request.payingmembercustomerrel.PayingMemberQueryDiscountRequest;
import com.wanmi.sbc.customer.api.request.store.ListNoDeleteStoreByIdsRequest;
import com.wanmi.sbc.customer.api.request.store.StoreByIdRequest;
import com.wanmi.sbc.customer.api.response.customer.CustomerGetByIdResponse;
import com.wanmi.sbc.customer.api.response.level.CustomerLevelByCustomerIdAndStoreIdResponse;
import com.wanmi.sbc.customer.api.response.store.StoreByIdResponse;
import com.wanmi.sbc.customer.bean.enums.EnterpriseCheckState;
import com.wanmi.sbc.customer.bean.vo.*;
import com.wanmi.sbc.distribute.DistributionCacheService;
import com.wanmi.sbc.distribute.DistributionService;
import com.wanmi.sbc.elastic.api.provider.goods.EsGoodsInfoElasticQueryProvider;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsInfoQueryRequest;
import com.wanmi.sbc.elastic.bean.vo.goods.EsGoodsInfoVO;
import com.wanmi.sbc.elastic.bean.vo.goods.GoodsInfoNestVO;
import com.wanmi.sbc.empower.api.provider.logisticssetting.ExpressQueryProvider;
import com.wanmi.sbc.empower.api.request.logisticssetting.DeliveryQueryRequest;
import com.wanmi.sbc.goods.api.provider.distributor.goods.DistributorGoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.provider.flashsalegoods.FlashSaleGoodsQueryProvider;
import com.wanmi.sbc.goods.api.provider.goodsrestrictedsale.GoodsRestrictedSaleQueryProvider;
import com.wanmi.sbc.goods.api.provider.goodstobeevaluate.GoodsTobeEvaluateQueryProvider;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.request.distributor.goods.DistributorGoodsInfoVerifyRequest;
import com.wanmi.sbc.goods.api.request.goodsrestrictedsale.GoodsRestrictedBatchValidateRequest;
import com.wanmi.sbc.goods.api.request.goodstobeevaluate.GoodsTobeEvaluateQueryRequest;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoListByIdsRequest;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoViewByIdsRequest;
import com.wanmi.sbc.goods.api.response.info.GoodsInfoResponse;
import com.wanmi.sbc.goods.api.response.info.GoodsInfoViewByIdsResponse;
import com.wanmi.sbc.goods.api.response.price.GoodsIntervalPriceByCustomerIdResponse;
import com.wanmi.sbc.goods.bean.dto.GoodsInfoDTO;
import com.wanmi.sbc.goods.bean.enums.*;
import com.wanmi.sbc.goods.bean.enums.GoodsStatus;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.goods.bean.vo.GoodsRestrictedValidateVO;
import com.wanmi.sbc.goods.bean.vo.GrouponGoodsInfoVO;
import com.wanmi.sbc.intervalprice.GoodsIntervalPriceService;
import com.wanmi.sbc.marketing.api.provider.appointmentsale.AppointmentSaleQueryProvider;
import com.wanmi.sbc.marketing.api.provider.bookingsale.BookingSaleQueryProvider;
import com.wanmi.sbc.marketing.api.provider.coupon.CouponCodeQueryProvider;
import com.wanmi.sbc.marketing.api.provider.distribution.DistributionCacheQueryProvider;
import com.wanmi.sbc.marketing.api.provider.distributionrecord.DistributionRecordQueryProvider;
import com.wanmi.sbc.marketing.api.provider.giftcard.UserGiftCardProvider;
import com.wanmi.sbc.marketing.api.provider.grouponactivity.GrouponActivityQueryProvider;
import com.wanmi.sbc.marketing.api.provider.market.MarketingQueryProvider;
import com.wanmi.sbc.marketing.api.provider.marketingsuits.MarketingSuitsQueryProvider;
import com.wanmi.sbc.marketing.api.provider.newplugin.NewMarketingPluginProvider;
import com.wanmi.sbc.marketing.api.provider.plugin.MarketingLevelPluginProvider;
import com.wanmi.sbc.marketing.api.provider.preferential.PreferentialQueryProvider;
import com.wanmi.sbc.marketing.api.request.appointmentsale.AppointmentSaleInProgressRequest;
import com.wanmi.sbc.marketing.api.request.coupon.CouponCodeListForUseByCustomerIdRequest;
import com.wanmi.sbc.marketing.api.request.distributionrecord.DistributionRecordListRequest;
import com.wanmi.sbc.marketing.api.request.giftcard.UserGiftCardTradeRequest;
import com.wanmi.sbc.marketing.api.request.grouponactivity.GrouponActivityFreeDeliveryByIdRequest;
import com.wanmi.sbc.marketing.api.request.plugin.MarketingLevelGoodsListFilterRequest;
import com.wanmi.sbc.marketing.api.request.preferential.DetailByMIdsAndLIdsRequest;
import com.wanmi.sbc.marketing.api.request.preferential.LeveByMIdsRequest;
import com.wanmi.sbc.marketing.api.response.appointmentsale.AppointmentSaleInProcessResponse;
import com.wanmi.sbc.marketing.api.response.distributionrecord.DistributionRecordListResponse;
import com.wanmi.sbc.marketing.api.response.giftcard.UserGiftCardTradeResponse;
import com.wanmi.sbc.marketing.bean.dto.TradeItemInfoDTO;
import com.wanmi.sbc.marketing.bean.dto.TradeMarketingDTO;
import com.wanmi.sbc.marketing.bean.enums.*;
import com.wanmi.sbc.marketing.bean.vo.CouponCodeVO;
import com.wanmi.sbc.marketing.bean.vo.DistributionRecordVO;
import com.wanmi.sbc.marketing.bean.vo.MarketingPreferentialGoodsDetailVO;
import com.wanmi.sbc.marketing.bean.vo.MarketingPreferentialLevelVO;
import com.wanmi.sbc.order.api.provider.appointmentrecord.AppointmentRecordQueryProvider;
import com.wanmi.sbc.order.api.provider.groupon.GrouponProvider;
import com.wanmi.sbc.order.api.provider.payorder.PayOrderQueryProvider;
import com.wanmi.sbc.order.api.provider.paytimeseries.PayTimeSeriesQueryProvider;
import com.wanmi.sbc.order.api.provider.returnorder.ReturnOrderQueryProvider;
import com.wanmi.sbc.order.api.provider.trade.*;
import com.wanmi.sbc.order.api.provider.wxpayuploadshippinginfo.WxPayUploadShippingInfoQueryProvider;
import com.wanmi.sbc.order.api.request.appointmentrecord.AppointmentRecordQueryRequest;
import com.wanmi.sbc.order.api.request.groupon.GrouponOrderValidRequest;
import com.wanmi.sbc.order.api.request.payorder.FindPayOrderListRequest;
import com.wanmi.sbc.order.api.request.payorder.FindPayOrderRequest;
import com.wanmi.sbc.order.api.request.paytimeseries.PayTimeSeriesByBusinessIdRequest;
import com.wanmi.sbc.order.api.request.returnorder.ReturnCountByConditionRequest;
import com.wanmi.sbc.order.api.request.trade.*;
import com.wanmi.sbc.order.api.request.wxpayuploadshippinginfo.WxPayShippingOrderStatusRequest;
import com.wanmi.sbc.order.api.response.appointmentrecord.AppointmentRecordResponse;
import com.wanmi.sbc.order.api.response.payorder.FindPayOrderListResponse;
import com.wanmi.sbc.order.api.response.payorder.FindPayOrderResponse;
import com.wanmi.sbc.order.api.response.payorder.PayAdvertisementResponse;
import com.wanmi.sbc.order.api.response.trade.*;
import com.wanmi.sbc.order.api.response.wxpayuploadshippinginfo.WxPayShippingOrderStatusResponse;
import com.wanmi.sbc.order.bean.dto.*;
import com.wanmi.sbc.order.bean.enums.*;
import com.wanmi.sbc.order.bean.enums.DeliverStatus;
import com.wanmi.sbc.order.bean.enums.FlowState;
import com.wanmi.sbc.order.bean.enums.PayState;
import com.wanmi.sbc.order.bean.vo.*;
import com.wanmi.sbc.order.mapper.CustmerMapper;
import com.wanmi.sbc.order.mapper.GoodsInfoMapper;
import com.wanmi.sbc.order.mapper.TradeGoodsInfoPageMapper;
import com.wanmi.sbc.order.mapper.TradeItemMapper;
import com.wanmi.sbc.order.request.*;
import com.wanmi.sbc.order.response.OrderCommissionResponse;
import com.wanmi.sbc.order.response.OrderTodoResp;
import com.wanmi.sbc.order.service.GoodsInfoExtraService;
import com.wanmi.sbc.setting.api.provider.AuditQueryProvider;
import com.wanmi.sbc.setting.api.provider.payadvertisement.PayAdvertisementQueryProvider;
import com.wanmi.sbc.setting.api.provider.platformaddress.PlatformAddressQueryProvider;
import com.wanmi.sbc.setting.api.provider.systemconfig.SystemConfigQueryProvider;
import com.wanmi.sbc.setting.api.request.ConfigQueryRequest;
import com.wanmi.sbc.setting.api.request.TradeConfigGetByTypeRequest;
import com.wanmi.sbc.setting.api.request.payadvertisement.PayAdvertisementPageRequest;
import com.wanmi.sbc.setting.api.request.platformaddress.PlatformAddressListRequest;
import com.wanmi.sbc.setting.api.response.TradeConfigGetByTypeResponse;
import com.wanmi.sbc.setting.api.response.payadvertisement.PayAdvertisementPageResponse;
import com.wanmi.sbc.setting.api.response.systemconfig.SystemConfigTypeResponse;
import com.wanmi.sbc.setting.bean.enums.ConfigType;
import com.wanmi.sbc.setting.bean.enums.SettingErrorCodeEnum;
import com.wanmi.sbc.setting.bean.enums.WriteOffStatus;
import com.wanmi.sbc.setting.bean.vo.PayAdvertisementStoreVO;
import com.wanmi.sbc.setting.bean.vo.PayAdvertisementVO;
import com.wanmi.sbc.setting.bean.vo.PlatformAddressVO;
import com.wanmi.sbc.system.service.SystemConfigService;
import com.wanmi.sbc.system.service.SystemPointsConfigService;
import com.wanmi.sbc.third.goods.ThirdPlatformGoodsService;
import com.wanmi.sbc.util.CommonUtil;

import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/** 订单公共Controller Created by of628-wenzhi on 2017-07-10-下午4:12. */
@Tag(name = "TradeBaseController", description = "订单公共服务API")
@RestController
@Validated
@RequestMapping("/trade")
@Slf4j
public class TradeBaseController {

    @Autowired private TradeQueryProvider tradeQueryProvider;

    @Autowired private TradeProvider tradeProvider;

    @Autowired private PayOrderQueryProvider payOrderQueryProvider;

    @Autowired private PreferentialQueryProvider preferentialQueryProvider;

    @Autowired private TradeItemProvider tradeItemProvider;

    @Autowired private TradeItemQueryProvider tradeItemQueryProvider;

    @Resource private VerifyQueryProvider verifyQueryProvider;

    @Autowired private CustomerQueryProvider customerQueryProvider;

    @Resource private CommonUtil commonUtil;

    @Resource private GoodsIntervalPriceService goodsIntervalPriceService;

    @Resource private AuditQueryProvider auditQueryProvider;

    @Resource private MarketingLevelPluginProvider marketingLevelPluginProvider;

    @Resource private StoreQueryProvider storeQueryProvider;

    @Resource private CouponCodeQueryProvider couponCodeQueryProvider;

    @Resource private DistributorGoodsInfoQueryProvider distributorGoodsInfoQueryProvider;

    @Resource private DistributionCacheService distributionCacheService;

    @Autowired private DistributionService distributionService;

    @Autowired private DistributionRecordQueryProvider distributionRecordQueryProvider;

    @Autowired private DistributionCustomerQueryProvider distributionCustomerQueryProvider;

    @Autowired private GrouponProvider grouponProvider;

    @Autowired private GrouponActivityQueryProvider grouponActivityQueryProvider;

    @Autowired private GrouponInstanceQueryProvider grouponInstanceQueryProvider;

    @Autowired private RedissonClient redissonClient;

    @Autowired private GoodsRestrictedSaleQueryProvider goodsRestrictedSaleQueryProvider;

    @Autowired private ProviderTradeProvider providerTradeProvider;

    @Autowired private MarketingSuitsQueryProvider marketingSuitsQueryProvider;

    @Autowired private AppointmentRecordQueryProvider appointmentRecordQueryProvider;

    @Autowired private AppointmentSaleQueryProvider appointmentSaleQueryProvider;

    @Autowired private BookingSaleQueryProvider bookingSaleQueryProvider;

    @Autowired private ReturnOrderQueryProvider returnOrderQueryProvider;

    @Autowired private GoodsTobeEvaluateQueryProvider goodsTobeEvaluateQueryProvider;

    @Autowired private CustomerLevelQueryProvider customerLevelQueryProvider;

    @Autowired private StoreCustomerQueryProvider storeCustomerQueryProvider;

    @Autowired private MarketingQueryProvider marketingQueryProvider;

    @Autowired private RedisUtil redisService;

    @Autowired private SystemPointsConfigService systemPointsConfigService;

    @Autowired private FlashSaleGoodsQueryProvider flashSaleGoodsQueryProvider;

    @Autowired private GoodsInfoQueryProvider goodsInfoQueryProvider;

    @Autowired private TradeItemMapper tradeItemMapper;

    @Autowired private TradeGoodsInfoPageMapper tradeGoodsInfoPageMapper;

    @Autowired private GoodsInfoMapper goodsInfoMapper;

    @Autowired private CustmerMapper custmerMapper;

    @Autowired private CustomerDeliveryAddressQueryProvider customerDeliveryAddressQueryProvider;

    @Autowired private PlatformAddressQueryProvider platformAddressQueryProvider;

    @Autowired private SystemConfigService systemConfigService;

    @Autowired private DistributionCacheQueryProvider distributionCacheQueryProvider;

    @Autowired private ExpressQueryProvider expressQueryProvider;

    @Autowired private ThirdPlatformGoodsService thirdPlatformGoodsService;

    @Autowired private VerifyService verifyService;

    @Autowired private GoodsInfoExtraService goodsInfoExtraService;

    @Autowired private NewMarketingPluginProvider newMarketingPluginProvider;

    @Autowired private PayAdvertisementQueryProvider payAdvertisementQueryProvider;

    @Autowired private PayingMemberCustomerRelQueryProvider payingMemberCustomerRelQueryProvider;

    @Autowired private EsGoodsInfoElasticQueryProvider esGoodsInfoElasticQueryProvider;

    @Autowired private SystemConfigQueryProvider systemConfigQueryProvider;

    @Autowired
    private OfflineQueryProvider offlineQueryProvider;

    @Autowired private UserGiftCardProvider userGiftCardProvider;

    @Autowired
    private PayTimeSeriesQueryProvider payTimeSeriesQueryProvider;

    @Autowired private WxPayUploadShippingInfoQueryProvider wxPayUploadShippingInfoQueryProvider;

    /** 查询订单详情 */
    @Operation(summary = "查询订单详情")
    @Parameter(
            name = "tid",
            description = "订单ID",
            required = true)
    @RequestMapping(value = "/{tid}", method = RequestMethod.GET)
    public BaseResponse<TradeVO> details(@PathVariable String tid) {
        Operator operator = commonUtil.getOperator();
        TradeGetByIdResponse tradeGetByIdResponse =
                tradeQueryProvider
                        .getById(
                                TradeGetByIdRequest.builder()
                                        .tid(tid)
                                        .needLmOrder(Boolean.TRUE)
                                        .operator(operator)
                                        .build())
                        .getContext();
        if (tradeGetByIdResponse == null || tradeGetByIdResponse.getTradeVO() == null) {
            return BaseResponse.success(null);
        }
        return BaseResponse.success(getTradeVO(tradeGetByIdResponse,tid,null));
    }

    /** 未登录查询订单详情 */
    @Operation(summary = "查询订单详情")
    @Parameter(
            name = "tid",
            description = "订单ID",
            required = true)
    @RequestMapping(value = "/unlogin/{tid}/{customerId}", method = RequestMethod.GET)
    public BaseResponse<TradeVO> getUnloginDetails(@PathVariable String tid,@PathVariable String customerId) {
        TradeGetByIdResponse tradeGetByIdResponse =
                tradeQueryProvider
                        .getById(
                                TradeGetByIdRequest.builder()
                                        .tid(tid)
                                        .needLmOrder(Boolean.TRUE)
                                        .build())
                        .getContext();
        if (tradeGetByIdResponse == null || tradeGetByIdResponse.getTradeVO() == null) {
            return BaseResponse.success(null);
        } else {
            BuyerVO buyer = tradeGetByIdResponse.getTradeVO().getBuyer();
            if(!buyer.getId().equals(customerId)){
                return BaseResponse.success(null);
            }
        }
        return BaseResponse.success(getTradeVO(tradeGetByIdResponse,tid,customerId));
    }

    private TradeVO getTradeVO(TradeGetByIdResponse tradeGetByIdResponse,String tid,String customerId){
        TradeVO detail = tradeGetByIdResponse.getTradeVO();
        if(StringUtils.isBlank(customerId)){
            checkUnauthorized(tid, tradeGetByIdResponse.getTradeVO(),true);
        }
        TradeConfigGetByTypeRequest request = new TradeConfigGetByTypeRequest();
        request.setConfigType(ConfigType.ORDER_SETTING_APPLY_REFUND);
        TradeConfigGetByTypeResponse config =
                auditQueryProvider.getTradeConfigByType(request).getContext();
        boolean flag = config.getStatus() == 1;
        int days = JSONObject.parseObject(config.getContext()).getInteger("day");
        TradeStateVO tradeState = detail.getTradeState();
        // 申请退单状态数据库状态优先
        if (Objects.nonNull(tradeState.getRefundStatus())) {
            flag = tradeState.getRefundStatus() == 1;
        }

        boolean canReturnFlag =
                tradeState.getFlowState() == FlowState.COMPLETED
                        || (tradeState.getPayState() == PayState.PAID
                        && tradeState.getDeliverStatus() == DeliverStatus.NOT_YET_SHIPPED
                        && tradeState.getFlowState() != FlowState.VOID);
        OrderTagVO orderTag = detail.getOrderTag();
        // 是否是虚拟订单或者卡券订单
        boolean isVirtual =
                Objects.nonNull(orderTag)
                        && (orderTag.getVirtualFlag() || orderTag.getElectronicCouponFlag());
        if (isVirtual) {
            canReturnFlag = true;
        } else {
            canReturnFlag = isCanReturnTime(flag, days, tradeState, canReturnFlag);

            // 在途订单是否允许退货
            if (tradeState.getFlowState() == FlowState.DELIVERED
                    || tradeState.getFlowState() == FlowState.DELIVERED_PART) {
                canReturnFlag = Boolean.TRUE.equals(detail.getTransitReturn());
            }
        }

        // 开店礼包及提货卡订单不支持退货退款
        boolean isPickupCard = Objects.nonNull(orderTag) && orderTag.getPickupCardFlag();
        canReturnFlag =
                canReturnFlag && DefaultFlag.NO == detail.getStoreBagsFlag() && !isPickupCard;
        //提货卡订单不支持售后
        if(Objects.nonNull(detail.getTradePrice().getGiftCardType()) && detail.getTradePrice().getGiftCardType() == GiftCardType.PICKUP_CARD){
            canReturnFlag = false;
            detail.getTradePrice().setBuyPoints(0L);
            detail.getTradePrice().setPoints(0L);
            detail.getTradePrice().setPointsPrice(BigDecimal.ZERO);
        }
        detail.setCanReturnFlag(canReturnFlag);
        // 处理预售支付尾款
        if (Objects.nonNull(detail.getIsBookingSaleGoods())
                && detail.getBookingType() == BookingType.EARNEST_MONEY) {
            BigDecimal price =
                    detail.getTradePrice()
                            .getTailPrice()
                            .subtract(detail.getTradePrice().getDeliveryPrice());
            //预售订单 调用优惠券的金额需要调整为
            List<TradeItemInfoDTO> tradeItems = tradeItemMapper.tradeItemVOsToTradeItemInfoDTOs(detail.getTradeItems());
            tradeItems.get(0).setPrice(price);
            CouponCodeListForUseByCustomerIdRequest requ =
                    CouponCodeListForUseByCustomerIdRequest.builder()
                            .customerId(commonUtil.getOperatorId())
                            .terminalToken(commonUtil.getTerminalToken())
                            .tradeItems(tradeItems)
                            .price(price)
                            .build();
            detail.setCouponCodes(
                    couponCodeQueryProvider
                            .listForUseByCustomerId(requ)
                            .getContext()
                            .getCouponCodeList());
            detail.setCouponAvailableCount(CollectionUtils.isNotEmpty(detail.getCouponCodes()) ? detail.getCouponCodes().size() : 0);
            // 查询可用礼品卡数量
            List<GoodsInfoVO> goodsInfoVOList = new ArrayList<>();
            detail.getTradeItems().forEach(tradeItemVO -> {
                GoodsInfoVO goodsInfoVO = new GoodsInfoVO();
                goodsInfoVO.setGoodsInfoId(tradeItemVO.getSkuId());
                goodsInfoVO.setStoreId(tradeItemVO.getStoreId());
                goodsInfoVO.setCateId(tradeItemVO.getCateId());
                goodsInfoVO.setBrandId(tradeItemVO.getBrand());
                goodsInfoVOList.add(goodsInfoVO);
            });

            UserGiftCardTradeRequest giftCardTradeRequest = new UserGiftCardTradeRequest();
            giftCardTradeRequest.setCustomerId(commonUtil.getOperatorId());
            giftCardTradeRequest.setGoodsInfoVOList(goodsInfoVOList);
            UserGiftCardTradeResponse response = userGiftCardProvider.tradeUserGiftCard(giftCardTradeRequest).getContext();
            detail.setGiftCardNum(response.getValidNum());

        }

        getAddressInfo(detail.getPickSettingInfo());

        // 未完全支付的定金预售订单改变会作废
        this.fillTradeBookingTimeOut(detail);

        // 处理VOP订单的可退款状态
        this.fillVopOrderReturnFlag(detail);
        //用户手机号脱敏
        if(Objects.nonNull(detail.getConsignee())){
            detail.getConsignee().setPhone(SensitiveUtils.handlerMobilePhone(detail.getConsignee().getPhone()));
        }

        return detail;
    }

    private void getAddressInfo(PickSettingInfoVO pickSettingInfoVo) {
        if (Objects.nonNull(pickSettingInfoVo)) {
            List<String> addrIds = new ArrayList<>();
            addrIds.add(Objects.toString(pickSettingInfoVo.getProvinceId()));
            addrIds.add(Objects.toString(pickSettingInfoVo.getCityId()));
            addrIds.add(Objects.toString(pickSettingInfoVo.getAreaId()));
            addrIds.add(Objects.toString(pickSettingInfoVo.getStreetId()));
            // 根据Id获取地址信息
            List<PlatformAddressVO> voList =
                    platformAddressQueryProvider
                            .list(PlatformAddressListRequest.builder().addrIdList(addrIds).build())
                            .getContext()
                            .getPlatformAddressVOList();
            Map<String, String> addrMap = new HashMap<>();
            if (org.apache.commons.collections4.CollectionUtils.isNotEmpty(voList)) {
                addrMap =
                        voList.stream()
                                .collect(
                                        Collectors.toMap(
                                                PlatformAddressVO::getAddrId,
                                                PlatformAddressVO::getAddrName));
            }
            pickSettingInfoVo.setProvinceName(
                    addrMap.get(Objects.toString(pickSettingInfoVo.getProvinceId())));
            pickSettingInfoVo.setCityName(
                    addrMap.get(Objects.toString(pickSettingInfoVo.getCityId())));
            pickSettingInfoVo.setAreaName(
                    addrMap.get(Objects.toString(pickSettingInfoVo.getAreaId())));
            pickSettingInfoVo.setStreetName(
                    addrMap.get(Objects.toString(pickSettingInfoVo.getStreetId())));
            try {
                StoreByIdResponse context =
                        storeQueryProvider
                                .getById(new StoreByIdRequest(pickSettingInfoVo.getStoreId()))
                                .getContext();
                pickSettingInfoVo.setStoreName(
                        context.getStoreVO() == null ? null : context.getStoreVO().getStoreName());
            } catch (Exception e) {
                pickSettingInfoVo.setStoreName(null);
            }
        }
    }

    /** B店主客户订单详情 */
    @Operation(summary = "B店主客户订单详情")
    @Parameter(
            name = "tid",
            description = "订单ID",
            required = true)
    @RequestMapping(value = "/distribute/{tid}", method = RequestMethod.GET)
    public BaseResponse<TradeVO> distributeDetails(@PathVariable String tid) {
        TradeGetByIdResponse tradeGetByIdResponse =
                tradeQueryProvider
                        .getById(TradeGetByIdRequest.builder().tid(tid).build())
                        .getContext();
        TradeVO detail = tradeGetByIdResponse.getTradeVO();
        String inviteeId = detail.getInviteeId();
        if (!inviteeId.equals(commonUtil.getOperatorId())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        TradeConfigGetByTypeRequest request = new TradeConfigGetByTypeRequest();
        request.setConfigType(ConfigType.ORDER_SETTING_APPLY_REFUND);
        TradeConfigGetByTypeResponse config =
                auditQueryProvider.getTradeConfigByType(request).getContext();
        boolean flag = config.getStatus() == 1;
        int days = JSONObject.parseObject(config.getContext()).getInteger("day");
        TradeStateVO tradeState = detail.getTradeState();
        // 申请退单状态数据库状态优先
        if (Objects.nonNull(tradeState.getRefundStatus())) {
            flag = tradeState.getRefundStatus() == 1;
        }
        boolean canReturnFlag =
                tradeState.getFlowState() == FlowState.COMPLETED
                        || (tradeState.getPayState() == PayState.PAID
                                && tradeState.getDeliverStatus() == DeliverStatus.NOT_YET_SHIPPED
                                && tradeState.getFlowState() != FlowState.VOID);
        OrderTagVO orderTag = detail.getOrderTag();
        // 是否是虚拟订单或者卡券订单
        boolean isVirtual =
                Objects.nonNull(orderTag)
                        && (orderTag.getVirtualFlag() || orderTag.getElectronicCouponFlag());
        if (isVirtual) {
            canReturnFlag = true;
        } else {
            canReturnFlag = isCanReturnTime(flag, days, tradeState, canReturnFlag);
        }
        // 开店礼包及提货卡订单不支持退货退款
        boolean isPickupCard = Objects.nonNull(orderTag) && orderTag.getPickupCardFlag();
        canReturnFlag = canReturnFlag && DefaultFlag.NO == detail.getStoreBagsFlag() && !isPickupCard;
        //提货卡订单不支持售后
        if(Objects.nonNull(detail.getTradePrice().getGiftCardType()) && detail.getTradePrice().getGiftCardType() == GiftCardType.PICKUP_CARD){
            canReturnFlag = false;
        }
        detail.setCanReturnFlag(canReturnFlag);
        if (!isVirtual) {
            detail.getConsignee()
                    .setPhone(
                            detail.getConsignee()
                                    .getPhone()
                                    .replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2"));
            detail.getConsignee()
                    .setDetailAddress(
                            detail.getConsignee()
                                    .getDetailAddress()
                                    .replace(detail.getConsignee().getAddress(), "********"));
        }

        // 查询商品的入账状态
        DistributionRecordListRequest distributionRecordListRequest =
                DistributionRecordListRequest.builder().tradeId(detail.getId()).build();
        BaseResponse<DistributionRecordListResponse> response =
                distributionRecordQueryProvider.list(distributionRecordListRequest);
        if (response != null
                && response.getContext() != null
                && CollectionUtils.isNotEmpty(
                        response.getContext().getDistributionRecordVOList())) {
            List<DistributionRecordVO> distributionRecordVOList =
                    response.getContext().getDistributionRecordVOList();
            detail.getTradeItems()
                    .forEach(
                            tradeItemVO ->
                                    distributionRecordVOList.stream()
                                            .filter(
                                                    distributionRecordVO ->
                                                            distributionRecordVO
                                                                    .getGoodsInfoId()
                                                                    .equals(tradeItemVO.getSkuId()))
                                            .forEach(
                                                    distributionRecordVO -> {
                                                        if (distributionRecordVO
                                                                .getDeleteFlag()
                                                                .equals(DeleteFlag.YES)) {
                                                            tradeItemVO.setIsAccountStatus(
                                                                    IsAccountStatus.FAIL);
                                                        } else if (distributionRecordVO
                                                                .getCommissionState()
                                                                .equals(
                                                                        CommissionReceived
                                                                                .RECEIVED)) {
                                                            tradeItemVO.setIsAccountStatus(
                                                                    IsAccountStatus.YES);
                                                        } else {
                                                            tradeItemVO.setIsAccountStatus(
                                                                    IsAccountStatus.NO);
                                                        }
                                                    }));
        }
        getAddressInfo(detail.getPickSettingInfo());
        return BaseResponse.success(detail);
    }

    /** 校验商品是否可以立即购买 */
    @Operation(summary = "校验商品是否可以立即购买")
    @RequestMapping(value = "/checkGoods", method = RequestMethod.PUT)
    public BaseResponse checkGoods(@RequestBody @Valid TradeItemConfirmRequest confirmRequest) {
        String customerId = commonUtil.getOperatorId();
        List<TradeItemDTO> tradeItems =
                confirmRequest.getTradeItems().stream()
                        .map(
                                o ->
                                        TradeItemDTO.builder()
                                                .skuId(o.getSkuId())
                                                .num(o.getNum())
                                                .isAppointmentSaleGoods(
                                                        o.getIsAppointmentSaleGoods())
                                                .appointmentSaleId(o.getAppointmentSaleId())
                                                .isBookingSaleGoods(o.getIsBookingSaleGoods())
                                                .bookingSaleId(o.getBookingSaleId())
                                                .build())
                        .collect(Collectors.toList());
        List<String> skuIds =
                confirmRequest.getTradeItems().stream().map(TradeItemRequest::getSkuId).collect(Collectors.toList());
        //验证用户
        CustomerGetByIdResponse customer = customerQueryProvider.getCustomerById(new CustomerGetByIdRequest
                (customerId)).getContext();
        GoodsInfoResponse response = getGoodsResponse(skuIds, customer.getCustomerId(), confirmRequest.getStoreId());
        //商品验证
        verifyQueryProvider.verifyGoods(new VerifyGoodsRequest(tradeItems, Lists.newArrayList(),
                tradeGoodsInfoPageMapper.goodsInfoResponseToTradeGoodsInfoPageDTO(response), confirmRequest.getStoreId(), Boolean.FALSE, null));
        verifyQueryProvider.verifyStore(new VerifyStoreRequest(response.getGoodsInfos().stream().map
                (GoodsInfoVO::getStoreId).collect(Collectors.toList())));

        // 渠道验证
        thirdPlatformGoodsService.verifyChannelGoods(response.getGoodsInfos(), tradeItems);

        Map<String, GoodsInfoVO> goodsInfoVOMap =
                response.getGoodsInfos().stream()
                        .collect(
                                Collectors.toMap(GoodsInfoVO::getGoodsInfoId, Function.identity()));
        tradeItems.forEach(
                tradeItemDTO -> {
                    tradeItemDTO.setBuyPoint(
                            goodsInfoVOMap.get(tradeItemDTO.getSkuId()).getBuyPoint());
                });
        // 校验商品限售信息
        TradeItemGroupVO tradeItemGroupVOS = new TradeItemGroupVO();
        tradeItemGroupVOS.setTradeItems(tradeItemMapper.tradeItemDTOsToTradeItemVos(tradeItems));
        if (Objects.nonNull(confirmRequest.getStoreId())) {
            SupplierVO supplierVO = new SupplierVO();
            supplierVO.setStoreType(StoreType.O2O);
            supplierVO.setStoreId(confirmRequest.getStoreId());
            tradeItemGroupVOS.setSupplier(supplierVO);
        }
        this.validateRestrictedGoods(tradeItemGroupVOS, customer, confirmRequest.getAddressId());
        IteratorUtils.zip(
                response.getGoodsInfos(),
                tradeItemGroupVOS.getTradeItems(),
                (a, b) -> a.getGoodsInfoId().equals(b.getSkuId()),
                (c, d) -> {
                    d.setBuyPoint(c.getBuyPoint());
                });
        // 预约活动校验是否有资格
        this.validateAppointmentQualification(Collections.singletonList(tradeItemGroupVOS));
        return BaseResponse.SUCCESSFUL();
    }



// --Commented out by Inspection START (2022/8/18, 11:29):
//    /**
//     * 校验活动初始化价格
//     *
//     * @param tradeItems
//     * @return
//     */
//    private List<TradeItemDTO> fillActivityPrice(
//            List<TradeItemDTO> tradeItems,
//            List<GoodsInfoVO> goodsInfoVOList,
//            CustomerVO customer,
//            StoreVO storeVO) {
//        Map<String, BigDecimal> skuMap =
//                goodsInfoVOList.stream()
//                        .collect(
//                                Collectors.toMap(
//                                        GoodsInfoVO::getGoodsInfoId, GoodsInfoVO::getMarketPrice));
//        return tradeItems.stream()
//                .map(
//                        item -> {
//                            if (item.getIsAppointmentSaleGoods()) {
//                                AppointmentSaleVO appointmentSaleVO =
//                                        appointmentSaleQueryProvider
//                                                .getAppointmentSaleRelaInfo(
//                                                        RushToAppointmentSaleGoodsRequest.builder()
//                                                                .appointmentSaleId(
//                                                                        item.getAppointmentSaleId())
//                                                                .skuId(item.getSkuId())
//                                                                .build())
//                                                .getContext()
//                                                .getAppointmentSaleVO();
//                                if (Objects.isNull(appointmentSaleVO)) {
//                                    throw new SbcRuntimeException(MarketingErrorCodeEnum.K080127);
//                                }
//                                if (appointmentSaleVO
//                                                .getSnapUpEndTime()
//                                                .isAfter(LocalDateTime.now())
//                                        && appointmentSaleVO
//                                                .getSnapUpStartTime()
//                                                .isBefore(LocalDateTime.now())) {
//                                    item.setPrice(
//                                            Objects.isNull(
//                                                            appointmentSaleVO
//                                                                    .getAppointmentSaleGood()
//                                                                    .getPrice())
//                                                    ? appointmentSaleVO
//                                                            .getAppointmentSaleGood()
//                                                            .getGoodsInfoVO()
//                                                            .getMarketPrice()
//                                                    : appointmentSaleVO
//                                                            .getAppointmentSaleGood()
//                                                            .getPrice());
//                                }
//                                return item;
//                            }
//                            if (item.getIsBookingSaleGoods()) {
//                                BookingSaleIsInProgressResponse bookingResponse =
//                                        bookingSaleQueryProvider
//                                                .isInProgress(
//                                                        BookingSaleIsInProgressRequest.builder()
//                                                                .goodsInfoId(item.getSkuId())
//                                                                .build())
//                                                .getContext();
//                                if (Objects.isNull(bookingResponse)
//                                        || Objects.isNull(bookingResponse.getBookingSaleVO())) {
//                                    throw new SbcRuntimeException(OrderErrorCodeEnum.K050151);
//                                }
//                                BookingSaleVO bookingSaleVO = bookingResponse.getBookingSaleVO();
//                                if (!bookingSaleVO.getId().equals(item.getBookingSaleId())) {
//                                    throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
//                                }
//                                if (bookingSaleVO.getPauseFlag() == 1) {
//                                    throw new SbcRuntimeException(MarketingErrorCodeEnum.K080127);
//                                }
//                                if (bookingSaleVO
//                                        .getBookingType()
//                                        .equals(NumberUtils.INTEGER_ONE)) {
//                                    if (bookingSaleVO
//                                                    .getHandSelEndTime()
//                                                    .isBefore(LocalDateTime.now())
//                                            || bookingSaleVO
//                                                    .getHandSelStartTime()
//                                                    .isAfter(LocalDateTime.now())) {
//                                        throw new SbcRuntimeException(MarketingErrorCodeEnum.K080127);
//                                    }
//                                    item.setPrice(
//                                            skuMap.get(
//                                                    bookingSaleVO
//                                                            .getBookingSaleGoods()
//                                                            .getGoodsInfoId()));
//                                    item.setBookingType(BookingType.EARNEST_MONEY);
//                                    BigDecimal handSelPrice =
//                                            bookingSaleVO.getBookingSaleGoods().getHandSelPrice();
//                                    BigDecimal inflationPrice =
//                                            bookingSaleVO.getBookingSaleGoods().getInflationPrice();
//                                    item.setEarnestPrice(
//                                            handSelPrice.multiply(
//                                                    BigDecimal.valueOf(item.getNum())));
//                                    if (Objects.nonNull(inflationPrice)) {
//                                        item.setSwellPrice(
//                                                inflationPrice.multiply(
//                                                        BigDecimal.valueOf(item.getNum())));
//                                    } else {
//                                        item.setSwellPrice(item.getEarnestPrice());
//                                    }
//                                    item.setHandSelStartTime(bookingSaleVO.getHandSelStartTime());
//                                    item.setHandSelEndTime(bookingSaleVO.getHandSelEndTime());
//                                    item.setTailStartTime(bookingSaleVO.getTailStartTime());
//                                    item.setTailEndTime(bookingSaleVO.getTailEndTime());
//                                }
//                                if (bookingSaleVO
//                                        .getBookingType()
//                                        .equals(NumberUtils.INTEGER_ZERO)) {
//                                    if (bookingSaleVO
//                                                    .getBookingEndTime()
//                                                    .isBefore(LocalDateTime.now())
//                                            || bookingSaleVO
//                                                    .getBookingStartTime()
//                                                    .isAfter(LocalDateTime.now())) {
//                                        throw new SbcRuntimeException(MarketingErrorCodeEnum.K080127);
//                                    }
//                                    item.setBookingType(BookingType.FULL_MONEY);
//                                    item.setPrice(
//                                            Objects.isNull(
//                                                            bookingSaleVO
//                                                                    .getBookingSaleGoods()
//                                                                    .getBookingPrice())
//                                                    ? skuMap.get(
//                                                            bookingSaleVO
//                                                                    .getBookingSaleGoods()
//                                                                    .getGoodsInfoId())
//                                                    : bookingSaleVO
//                                                            .getBookingSaleGoods()
//                                                            .getBookingPrice());
//                                }
//                                // 判断活动是否是全平台客户还是店铺内客户
//                                if (!bookingSaleVO.getJoinLevel().equals(Constants.STR_MINUS_1)) {
//                                    // 第三方商家
//                                    if (BoolFlag.YES.equals(storeVO.getCompanyType())) {
//                                        StoreCustomerRelaListByConditionRequest
//                                                listByConditionRequest =
//                                                        new StoreCustomerRelaListByConditionRequest();
//                                        listByConditionRequest.setCustomerId(
//                                                commonUtil.getOperatorId());
//                                        listByConditionRequest.setStoreId(
//                                                bookingSaleVO.getStoreId());
//                                        List<StoreCustomerRelaVO> relaVOList =
//                                                storeCustomerQueryProvider
//                                                        .listByCondition(listByConditionRequest)
//                                                        .getContext()
//                                                        .getRelaVOList();
//                                        if (Objects.nonNull(relaVOList) && relaVOList.size() > 0) {
//                                            if (!bookingSaleVO
//                                                            .getJoinLevel()
//                                                            .equals(Constants.STR_0)
//                                                    && !Arrays.asList(
//                                                                    bookingSaleVO
//                                                                            .getJoinLevel()
//                                                                            .split(","))
//                                                            .contains(
//                                                                    relaVOList
//                                                                            .get(0)
//                                                                            .getStoreLevelId()
//                                                                            .toString())) {
//                                                throw new SbcRuntimeException(MarketingErrorCodeEnum.K080122);
//                                            }
//                                        } else {
//                                            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080123);
//                                        }
//                                    } else {
//                                        if (!bookingSaleVO.getJoinLevel().equals(Constants.STR_0)
//                                                && !Arrays.asList(
//                                                                bookingSaleVO
//                                                                        .getJoinLevel()
//                                                                        .split(","))
//                                                        .contains(
//                                                                Objects.toString(
//                                                                        customer
//                                                                                .getCustomerLevelId()))) {
//                                            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080122);
//                                        }
//                                    }
//                                }
//                                return item;
//                            }
//                            return item;
//                        })
//                .collect(Collectors.toList());
//    }
// --Commented out by Inspection STOP (2022/8/18, 11:29)

    /** 订单满赠订单快照刷新 */
    @Operation(summary = "订单满赠订单快照刷新")
    @RequestMapping(value = "/full-gift/confirm", method = RequestMethod.PUT)
    @MultiSubmit
    public BaseResponse fullGiftConfirm(@RequestBody @Valid TradeItemSnapshotGiftRequest request) {
        String customerId = commonUtil.getOperatorId();
        List<TradeItemDTO> tradeItems =
                request.getTradeItems().stream()
                        .map(
                                o ->
                                        TradeItemDTO.builder()
                                                .skuId(o.getSkuId())
                                                .num(o.getNum())
                                                .storeId(o.getStoreId())
                                                .build())
                        .collect(Collectors.toList());
        // 营销活动校验
        List<TradeMarketingDTO> tradeMarketingList =
                verifyQueryProvider
                        .verifyTradeMarketing(
                                new VerifyTradeMarketingRequest(
                                        Collections.singletonList(request.getTradeMarketingDTO()),
                                        Collections.emptyList(),
                                        tradeItems,
                                        customerId,
                                        Boolean.FALSE))
                        .getContext()
                        .getTradeMarketingList();
        return tradeItemProvider.snapshotGift(
                TradeItemSnapshotGiftRequest.builder()
                        .terminalToken(commonUtil.getTerminalToken())
                        .tradeItems(tradeItems)
                        .tradeMarketingDTO(tradeMarketingList.get(0))
                        .build());
    }

// --Commented out by Inspection START (2022/8/18, 11:29):
//    /**
//     * @param marketingId 指定的活动id
//     * @param tradeItem
//     * @param marketings
//     * @return
//     */
//    private TradeMarketingDTO chooseMarketing(
//            Long marketingId, TradeItemDTO tradeItem, List<MarketingViewVO> marketings) {
//
//        BigDecimal total = tradeItem.getPrice().multiply(new BigDecimal(tradeItem.getNum()));
//        Long num = tradeItem.getNum();
//
//        TradeMarketingDTO tradeMarketing = new TradeMarketingDTO();
//        tradeMarketing.setSkuIds(Collections.singletonList(tradeItem.getSkuId()));
//        tradeMarketing.setGiftSkuIds(new ArrayList<>());
//
//        if (marketingId != null) {
//            marketings =
//                    marketings.stream()
//                            .filter(
//                                    marketingViewVO ->
//                                            marketingId.equals(marketingViewVO.getMarketingId()))
//                            .collect(Collectors.toList());
//        }
//        if (CollectionUtils.isNotEmpty(marketings)) {
//            for (MarketingViewVO marketing : marketings) {
//                // 满金额减
//                if (marketing.getSubType() == MarketingSubType.REDUCTION_FULL_AMOUNT) {
//                    List<MarketingFullReductionLevelVO> levels =
//                            marketing.getFullReductionLevelList();
//                    if (CollectionUtils.isNotEmpty(levels)) {
//                        levels.sort(
//                                Comparator.comparing(MarketingFullReductionLevelVO::getFullAmount)
//                                        .reversed());
//                        for (MarketingFullReductionLevelVO level : levels) {
//                            if (level.getFullAmount().compareTo(total) <= 0) {
//                                tradeMarketing.setMarketingLevelId(level.getReductionLevelId());
//                                tradeMarketing.setMarketingId(level.getMarketingId());
//                                return tradeMarketing;
//                            }
//                        }
//                    }
//                }
//
//                // 满数量减
//                if (marketing.getSubType() == MarketingSubType.REDUCTION_FULL_COUNT) {
//                    List<MarketingFullReductionLevelVO> levels =
//                            marketing.getFullReductionLevelList();
//                    if (CollectionUtils.isNotEmpty(levels)) {
//                        levels.sort(
//                                Comparator.comparing(MarketingFullReductionLevelVO::getFullCount)
//                                        .reversed());
//                        for (MarketingFullReductionLevelVO level : levels) {
//                            if (level.getFullCount().compareTo(num) <= 0) {
//                                tradeMarketing.setMarketingLevelId(level.getReductionLevelId());
//                                tradeMarketing.setMarketingId(level.getMarketingId());
//                                return tradeMarketing;
//                            }
//                        }
//                    }
//                }
//
//                // 满金额折
//                if (marketing.getSubType() == MarketingSubType.DISCOUNT_FULL_AMOUNT) {
//                    List<MarketingFullDiscountLevelVO> levels =
//                            marketing.getFullDiscountLevelList();
//                    if (CollectionUtils.isNotEmpty(levels)) {
//                        levels.sort(
//                                Comparator.comparing(MarketingFullDiscountLevelVO::getFullAmount)
//                                        .reversed());
//                        for (MarketingFullDiscountLevelVO level : levels) {
//                            if (level.getFullAmount().compareTo(total) <= 0) {
//                                tradeMarketing.setMarketingLevelId(level.getDiscountLevelId());
//                                tradeMarketing.setMarketingId(level.getMarketingId());
//                                return tradeMarketing;
//                            }
//                        }
//                    }
//                }
//                // 满数量折
//                if (marketing.getSubType() == MarketingSubType.DISCOUNT_FULL_COUNT) {
//                    List<MarketingFullDiscountLevelVO> levels =
//                            marketing.getFullDiscountLevelList();
//                    if (CollectionUtils.isNotEmpty(levels)) {
//                        levels.sort(
//                                Comparator.comparing(MarketingFullDiscountLevelVO::getFullCount)
//                                        .reversed());
//                        for (MarketingFullDiscountLevelVO level : levels) {
//                            if (level.getFullCount().compareTo(num) <= 0) {
//                                tradeMarketing.setMarketingLevelId(level.getDiscountLevelId());
//                                tradeMarketing.setMarketingId(level.getMarketingId());
//                                return tradeMarketing;
//                            }
//                        }
//                    }
//                }
//
//                // 满金额赠
//                if (marketing.getSubType() == MarketingSubType.GIFT_FULL_AMOUNT) {
//                    List<MarketingFullGiftLevelVO> levels = marketing.getFullGiftLevelList();
//                    if (CollectionUtils.isNotEmpty(levels)) {
//                        levels.sort(
//                                Comparator.comparing(MarketingFullGiftLevelVO::getFullAmount)
//                                        .reversed());
//                        for (MarketingFullGiftLevelVO level : levels) {
//                            if (level.getFullAmount().compareTo(total) <= 0) {
//                                tradeMarketing.setMarketingLevelId(level.getGiftLevelId());
//                                tradeMarketing.setMarketingId(level.getMarketingId());
//                                List<String> giftIds =
//                                        level.getFullGiftDetailList().stream()
//                                                .map(MarketingFullGiftDetailVO::getProductId)
//                                                .collect(Collectors.toList());
//                                if (GiftType.ONE.equals(level.getGiftType())) {
//                                    giftIds = Collections.singletonList(giftIds.get(0));
//                                }
//                                tradeMarketing.setGiftSkuIds(giftIds);
//                                return tradeMarketing;
//                            }
//                        }
//                    }
//                }
//                // 满数量赠
//                if (marketing.getSubType() == MarketingSubType.GIFT_FULL_COUNT) {
//                    List<MarketingFullGiftLevelVO> levels = marketing.getFullGiftLevelList();
//                    if (CollectionUtils.isNotEmpty(levels)) {
//                        levels.sort(
//                                Comparator.comparing(MarketingFullGiftLevelVO::getFullCount)
//                                        .reversed());
//                        for (MarketingFullGiftLevelVO level : levels) {
//                            if (level.getFullCount().compareTo(num) <= 0) {
//                                tradeMarketing.setMarketingLevelId(level.getGiftLevelId());
//                                tradeMarketing.setMarketingId(level.getMarketingId());
//                                List<String> giftIds =
//                                        level.getFullGiftDetailList().stream()
//                                                .map(MarketingFullGiftDetailVO::getProductId)
//                                                .collect(Collectors.toList());
//                                if (GiftType.ONE.equals(level.getGiftType())) {
//                                    giftIds = Collections.singletonList(giftIds.get(0));
//                                }
//                                tradeMarketing.setGiftSkuIds(giftIds);
//                                return tradeMarketing;
//                            }
//                        }
//                    }
//                }
//
//                // 打包一口价
//                if (marketing.getSubType() == MarketingSubType.BUYOUT_PRICE) {
//                    List<MarketingBuyoutPriceLevelVO> levels = marketing.getBuyoutPriceLevelList();
//                    if (CollectionUtils.isNotEmpty(levels)) {
//                        levels.sort(
//                                Comparator.comparing(MarketingBuyoutPriceLevelVO::getChoiceCount)
//                                        .reversed());
//                        for (MarketingBuyoutPriceLevelVO level : levels) {
//                            if (level.getChoiceCount().compareTo(num) <= 0) {
//                                tradeMarketing.setMarketingLevelId(level.getReductionLevelId());
//                                tradeMarketing.setMarketingId(level.getMarketingId());
//                                return tradeMarketing;
//                            }
//                        }
//                    }
//                }
//
//                // 第二件半价
//                if (marketing.getSubType() == MarketingSubType.HALF_PRICE_SECOND_PIECE) {
//                    List<MarketingHalfPriceSecondPieceLevelVO> levels =
//                            marketing.getHalfPriceSecondPieceLevel();
//                    if (CollectionUtils.isNotEmpty(levels)) {
//                        levels.sort(
//                                Comparator.comparing(
//                                                MarketingHalfPriceSecondPieceLevelVO::getNumber)
//                                        .reversed());
//                        for (MarketingHalfPriceSecondPieceLevelVO level : levels) {
//                            if (level.getNumber().compareTo(num) <= 0) {
//                                tradeMarketing.setMarketingLevelId(level.getId());
//                                tradeMarketing.setMarketingId(level.getMarketingId());
//                                return tradeMarketing;
//                            }
//                        }
//                    }
//                }
//            }
//        }
//
//        return null;
//    }
// --Commented out by Inspection STOP (2022/8/18, 11:29)




    /** 提交订单，用于尾款支付操作 */
    @Operation(summary = "提交订单，用于尾款支付操作")
    @RequestMapping(value = "/commit/final", method = RequestMethod.POST)
    @MultiSubmit
    @GlobalTransactional
    public BaseResponse<List<TradeCommitResultVO>> commitTail(
            @RequestBody @Valid TradeCommitRequest tradeCommitRequest) {
        if (StringUtils.isEmpty(tradeCommitRequest.getTid())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        if (StringUtils.isEmpty(tradeCommitRequest.getTailNoticeMobile())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        RLock rLock = redissonClient.getFairLock(commonUtil.getOperatorId());
        rLock.lock();
        List<TradeCommitResultVO> successResults;
        try {
            TradeVO trade =
                    tradeQueryProvider
                            .getById(
                                    TradeGetByIdRequest.builder()
                                            .tid(tradeCommitRequest.getTid())
                                            .build())
                            .getContext()
                            .getTradeVO();
            if (Objects.isNull(trade)
                    || Objects.isNull(trade.getIsBookingSaleGoods())
                    || !trade.getIsBookingSaleGoods()
                    || trade.getBookingType() != BookingType.EARNEST_MONEY) {
                throw new SbcRuntimeException(OrderErrorCodeEnum.K050153);
            }
            if (LocalDateTime.now().isBefore(trade.getTradeState().getTailStartTime())) {
                throw new SbcRuntimeException(MarketingErrorCodeEnum.K080182);
            }
            if (trade.getTradeState().getFlowState() == FlowState.AUDIT
                    || trade.getTradeState().getPayState() != PayState.PAID_EARNEST) {
                return BaseResponse.SUCCESSFUL();
            }
            Operator operator = commonUtil.getOperator();
            tradeCommitRequest.setOperator(operator);
            tradeCommitRequest.setTerminalToken(commonUtil.getTerminalToken());
            // 生成尾预售款订单快照
            generateSnapshot(tradeCommitRequest, trade);
            List<TradeItemGroupVO> tradeItemGroups =
                    tradeItemQueryProvider
                            .listByTerminalToken(
                                    TradeItemByCustomerIdRequest.builder()
                                            .customerId(commonUtil.getOperatorId())
                                            .terminalToken(commonUtil.getTerminalToken())
                                            .build())
                            .getContext()
                            .getTradeItemGroupList();

            DefaultFlag storeBagsFlag = tradeItemGroups.get(0).getStoreBagsFlag();
            if (DefaultFlag.NO.equals(storeBagsFlag)
                    && !distributionService.checkInviteeIdEnable()) {
                // 非开店礼包情况下，判断小店状态不可用
                throw new SbcRuntimeException(MarketingErrorCodeEnum.K080181);
            }

            // 邀请人不是分销员时，清空inviteeId
            DistributeChannel distributeChannel = commonUtil.getDistributeChannel();
            if (StringUtils.isNotEmpty(distributeChannel.getInviteeId())
                    && !distributionService.isDistributor(distributeChannel.getInviteeId())) {
                distributeChannel.setInviteeId(null);
            }
            // 设置下单用户，是否分销员
            if (distributionService.isDistributor(operator.getUserId())) {
                tradeCommitRequest.setIsDistributor(DefaultFlag.YES);
            }
            tradeCommitRequest.setDistributeChannel(distributeChannel);
            tradeCommitRequest.setShopName(distributionCacheService.getShopName());

            // 设置分销设置开关
            tradeCommitRequest.setOpenFlag(distributionCacheService.queryOpenFlag());
            tradeCommitRequest
                    .getStoreCommitInfoList()
                    .forEach(
                            item ->
                                    item.setStoreOpenFlag(
                                            distributionCacheService.queryStoreOpenFlag(
                                                    item.getStoreId().toString())));
            // 开启第三方平台
            boolean isOpen = commonUtil.findVASBuyOrNot(VASConstants.THIRD_PLATFORM_LINKED_MALL);
            tradeCommitRequest.setIsOpen(isOpen);
            successResults =
                    tradeProvider
                            .commitTail(tradeCommitRequest)
                            .getContext()
                            .getTradeCommitResults();
        } catch (Exception e) {
            throw e;
        } finally {
            rLock.unlock();
        }
        return BaseResponse.success(successResults);
    }

    /**
     * @return com.wanmi.sbc.common.base.BaseResponse @Author zhangxiaodong @Description
     *     生成尾预售款订单快照 @Date 2020/06/08 @Param [request]
     */
    @GlobalTransactional
    public BaseResponse generateSnapshot(TradeCommitRequest request, TradeVO tradeVO) {

        if (StringUtils.isEmpty(request.getTailNoticeMobile())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        String customerId = request.getOperator().getUserId();
        // 设置尾款订单快照数据信息
        TradeItemRequest tradeItemRequest = new TradeItemRequest();
        TradeItemVO tradeItemVO = tradeVO.getTradeItems().get(0);
        tradeItemRequest.setNum(tradeItemVO.getNum());
        tradeItemRequest.setSkuId(tradeItemVO.getSkuId());
        tradeItemRequest.setIsFlashSaleGoods(Boolean.FALSE);
        tradeItemRequest.setBookingSaleId(tradeItemVO.getBookingSaleId());
        tradeItemRequest.setIsBookingSaleGoods(Boolean.TRUE);

        List<TradeItemRequest> tradeItemConfirmRequests = new ArrayList<>();
        tradeItemConfirmRequests.add(tradeItemRequest);

        List<TradeMarketingDTO> tradeMarketingList = new ArrayList<>();
        TradeItemConfirmRequest confirmRequest = new TradeItemConfirmRequest();
        confirmRequest.setTradeItems(tradeItemConfirmRequests);
        confirmRequest.setTradeMarketingList(tradeMarketingList);
        confirmRequest.setForceConfirm(false);
        List<TradeItemDTO> tradeItems =
                confirmRequest.getTradeItems().stream()
                        .map(
                                o ->
                                        TradeItemDTO.builder()
                                                .skuId(o.getSkuId())
                                                .num(o.getNum())
                                                .price(o.getPrice())
                                                .isFlashSaleGoods(o.getIsFlashSaleGoods())
                                                .flashSaleGoodsId(o.getFlashSaleGoodsId())
                                                .isBookingSaleGoods(o.getIsAppointmentSaleGoods())
                                                .bookingSaleId(o.getAppointmentSaleId())
                                                .build())
                        .collect(Collectors.toList());
        List<String> skuIds =
                confirmRequest.getTradeItems().stream().map(TradeItemRequest::getSkuId).collect(Collectors.toList());
        //验证用户
        CustomerGetByIdResponse customer = customerQueryProvider.getCustomerById(new CustomerGetByIdRequest
                (customerId)).getContext();
        GoodsInfoResponse response = getGoodsResponse(skuIds, customer.getCustomerId(), null);
        //商品验证
        verifyQueryProvider.verifyStore(new VerifyStoreRequest(response.getGoodsInfos().stream().map
                (GoodsInfoVO::getStoreId).collect(Collectors.toList())));
        //营销活动校验
        verifyQueryProvider.verifyTradeMarketing(new VerifyTradeMarketingRequest(confirmRequest.getTradeMarketingList
                (), Collections.emptyList(), tradeItems, customerId, confirmRequest.isForceConfirm()));
        return tradeItemProvider.snapshot(TradeItemSnapshotRequest.builder().customerId(customerId).tradeItems
                (tradeItems)
                .tradeMarketingList(confirmRequest.getTradeMarketingList())
                .skuList(KsBeanUtil.convertList(response.getGoodsInfos(), GoodsInfoDTO.class))
                .snapshotType(Constants.BOOKING_SALE_TYPE)
                .terminalToken(request.getTerminalToken()).build());
    }

    @Operation(summary = "展示订单基本信息")
    @Parameter(
            name = "tid",
            description = "订单ID",
            required = true)
    @RequestMapping(value = "/show/{tid}", method = RequestMethod.GET)
    public BaseResponse<TradeCommitResultVO> commitResp(@PathVariable String tid) {
        TradeVO trade =
                tradeQueryProvider
                        .getById(TradeGetByIdRequest.builder().tid(tid).build())
                        .getContext()
                        .getTradeVO();
        checkUnauthorized(tid, trade, true);
        BigDecimal payPrice = trade.getTradePrice().getTotalPrice();
        if (Objects.nonNull(trade.getIsBookingSaleGoods())
                && trade.getIsBookingSaleGoods()
                && trade.getBookingType() == BookingType.EARNEST_MONEY
                && trade.getTradeState().getPayState() == PayState.NOT_PAID) {
            payPrice = trade.getTradePrice().getEarnestPrice();
        }
        if (Objects.nonNull(trade.getIsBookingSaleGoods())
                && trade.getIsBookingSaleGoods()
                && trade.getBookingType() == BookingType.EARNEST_MONEY
                && trade.getTradeState().getPayState() == PayState.PAID_EARNEST) {
            payPrice = trade.getTradePrice().getTailPrice();
        }
        boolean crossFlag =
                PluginType.CROSS_BORDER.equals(
                        trade.getTradeItems().get(NumberUtils.INTEGER_ZERO).getPluginType());
        return BaseResponse.success(
                new TradeCommitResultVO(
                        tid,
                        trade.getParentId(),
                        trade.getTradeState(),
                        trade.getPaymentOrder(),
                        payPrice,
                        trade.getTradePrice().getTotalPrice(),
                        trade.getOrderTimeOut(),
                        trade.getIsBookingSaleGoods(),
                        trade.getBookingType(),
                        trade.getSupplier().getStoreName(),
                        trade.getSupplier().getIsSelf(),
                        crossFlag,
                        trade.getSupplier().getStoreType()));
    }



    /** 订单确认页中初始化时验证订单商品信息 切换收货地址也要验证 */
    @Operation(summary = "用于确认订单后，创建订单前的获取订单商品信息")
    @RequestMapping(value = "/verifyPurchase", method = RequestMethod.POST)
    public BaseResponse verifyPurchase(@RequestBody TradeVerifyPurchaseRequest request) {
        request.setCustomerId(commonUtil.getOperatorId());
        request.setTerminalToken(commonUtil.getTerminalToken());
        return verifyQueryProvider.verifyPurchase(request);
    }

    /** 用于确认订单后，创建订单前的获取订单商品信息 */
    @Operation(summary = "用于立即购买，确认订单中更新商品购买数量")
    @RequestMapping(value = "/modifyGoodsNumForConfirm", method = RequestMethod.PUT)
    @GlobalTransactional
    public BaseResponse<TradeConfirmResponse> modifyGoodsNumForConfirm(
            @RequestBody @Valid TradeItemConfirmModifyGoodsNumRequest request) {
        TradeConfirmResponse confirmResponse = new TradeConfirmResponse();
        String customerId = commonUtil.getOperatorId();
        //验证用户
        CustomerGetByIdResponse customer = customerQueryProvider.getCustomerById(
                new CustomerGetByIdRequest(customerId)).getContext();
        GoodsInfoResponse skuResp = getGoodsResponse(Collections.singletonList(request.getGoodsInfoId()), customer.getCustomerId(), request.getStoreId());
        if (CollectionUtils.isEmpty(skuResp.getGoodsInfos())) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030035);
        }

        // 商品验证
        List<TradeItemDTO> itemDTOS =
                Collections.singletonList(
                        TradeItemDTO.builder()
                                .skuId(request.getGoodsInfoId())
                                .num(request.getBuyCount())
                                .build());
        List<TradeItemVO> itemVOList =
                verifyQueryProvider
                        .verifyGoods(
                                new VerifyGoodsRequest(
                                        itemDTOS,
                                        Collections.emptyList(),
                                        KsBeanUtil.convert(skuResp, TradeGoodsInfoPageDTO.class),
                                        skuResp.getGoodsInfos().get(0).getStoreId(),
                                        Boolean.TRUE,
                                        null))
                        .getContext()
                        .getTradeItems();

        // 渠道验证
        thirdPlatformGoodsService.verifyChannelGoods(skuResp.getGoodsInfos(), itemDTOS);

        // 更新快照
        com.wanmi.sbc.order.api.request.trade.TradeItemModifyGoodsNumRequest numRequest =
                com.wanmi.sbc.order.api.request.trade.TradeItemModifyGoodsNumRequest.builder()
                        .customerId(customerId)
                        .terminalToken(commonUtil.getTerminalToken())
                        .num(request.getBuyCount())
                        .skuId(request.getGoodsInfoId())
                        .skuList(
                                KsBeanUtil.convertList(skuResp.getGoodsInfos(), GoodsInfoDTO.class))
                        .build();
        List<TradeItemGroupVO> tradeItemGroups =
                tradeItemProvider.modifyGoodsNum(numRequest).getContext().getTradeItemGroupList();

        List<TradeConfirmItemVO> items = new ArrayList<>();
        Map<Long, StoreVO> storeMap =
                storeQueryProvider
                        .listNoDeleteStoreByIds(
                                new ListNoDeleteStoreByIdsRequest(
                                        tradeItemGroups.stream()
                                                .map(g -> g.getSupplier().getStoreId())
                                                .collect(Collectors.toList())))
                        .getContext()
                        .getStoreVOList()
                        .stream()
                        .collect(Collectors.toMap(StoreVO::getStoreId, Function.identity()));
        // 如果为PC商城下单，将分销商品变为普通商品
        if (ChannelType.PC_MALL.equals(commonUtil.getDistributeChannel().getChannelType())) {
            tradeItemGroups.forEach(
                    tradeItemGroup ->
                            tradeItemGroup
                                    .getTradeItems()
                                    .forEach(
                                            tradeItem -> {
                                                tradeItem.setDistributionGoodsAudit(
                                                        DistributionGoodsAudit.COMMON_GOODS);
                                            }));
            skuResp.getGoodsInfos()
                    .forEach(
                            item ->
                                    item.setDistributionGoodsAudit(
                                            DistributionGoodsAudit.COMMON_GOODS));
        }

        //企业会员判断
        boolean isIepCustomerFlag = isIepCustomer(customer);
        tradeItemGroups.forEach(
                g -> {
                    g.getSupplier()
                            .setFreightTemplateType(
                                    storeMap.get(g.getSupplier().getStoreId())
                                            .getFreightTemplateType());
                    // 分销商品、开店礼包商品、拼团商品、企业购商品，不验证起限定量
                    skuResp.getGoodsInfos()
                            .forEach(
                                    item -> {
                                        // 企业购商品
                                        boolean isIep =
                                                isIepCustomerFlag
                                                        && isEnjoyIepGoodsInfo(
                                                                item.getEnterPriseAuditState());
                                        if (DistributionGoodsAudit.CHECKED.equals(
                                                        item.getDistributionGoodsAudit())
                                                || DefaultFlag.YES.equals(g.getStoreBagsFlag())
                                                || Objects.nonNull(g.getGrouponForm())
                                                || isIep) {
                                            item.setCount(null);
                                            item.setMaxCount(null);
                                        }
                                    });

                    // 企业购商品价格回设
                    if (isIepCustomerFlag) {
                        itemVOList.forEach(
                                tradeItemVO -> {
                                    if (isEnjoyIepGoodsInfo(
                                            tradeItemVO.getEnterPriseAuditState())) {
                                        tradeItemVO.setPrice(tradeItemVO.getEnterPrisePrice());
                                        tradeItemVO.setSplitPrice(
                                                tradeItemVO
                                                        .getEnterPrisePrice()
                                                        .multiply(
                                                                new BigDecimal(
                                                                        tradeItemVO.getNum())));
                                        tradeItemVO.setLevelPrice(tradeItemVO.getEnterPrisePrice());
                                    }
                                });
                    }

                    // 抢购商品价格回设
                    if (StringUtils.isNotBlank(g.getSnapshotType())
                            && g.getSnapshotType().equals(Constants.FLASH_SALE_GOODS_ORDER_TYPE)) {
                        itemVOList.forEach(
                                tradeItemVO -> {
                                    g.getTradeItems()
                                            .forEach(
                                                    tradeItem -> {
                                                        if (tradeItem
                                                                .getSkuId()
                                                                .equals(tradeItemVO.getSkuId())) {
                                                            tradeItemVO.setPrice(
                                                                    tradeItem.getPrice());
                                                        }
                                                    });
                                });
                    }
                    itemVOList.forEach(
                            tradeItemVO -> {
                                g.getTradeItems()
                                        .forEach(
                                                tradeItem -> {
                                                    if ((Objects.nonNull(
                                                                    tradeItem
                                                                            .getIsAppointmentSaleGoods())
                                                            && tradeItem
                                                                    .getIsAppointmentSaleGoods())) {
                                                        if (tradeItem
                                                                .getSkuId()
                                                                .equals(tradeItemVO.getSkuId())) {
                                                            tradeItemVO.setPrice(
                                                                    tradeItem.getPrice());
                                                            tradeItemVO.setIsAppointmentSaleGoods(
                                                                    tradeItem
                                                                            .getIsAppointmentSaleGoods());
                                                            tradeItemVO.setAppointmentSaleId(
                                                                    tradeItem
                                                                            .getAppointmentSaleId());
                                                        }
                                                    }
                                                    if (Objects.nonNull(
                                                                    tradeItem
                                                                            .getIsBookingSaleGoods())
                                                            && tradeItem.getIsBookingSaleGoods()
                                                            && tradeItem.getBookingType()
                                                                    == BookingType.FULL_MONEY) {
                                                        if (tradeItem
                                                                .getSkuId()
                                                                .equals(tradeItemVO.getSkuId())) {
                                                            tradeItemVO.setPrice(
                                                                    tradeItem.getOriginalPrice());
                                                            tradeItemVO.setIsBookingSaleGoods(
                                                                    tradeItem
                                                                            .getIsBookingSaleGoods());
                                                            tradeItemVO.setBookingSaleId(
                                                                    tradeItem.getBookingSaleId());
                                                            tradeItemVO.setBookingType(
                                                                    tradeItem.getBookingType());
                                                        }
                                                    }
                                                    if (Objects.nonNull(
                                                                    tradeItem
                                                                            .getIsBookingSaleGoods())
                                                            && tradeItem.getIsBookingSaleGoods()
                                                            && tradeItem.getBookingType()
                                                                    == BookingType.EARNEST_MONEY) {
                                                        if (tradeItem
                                                                .getSkuId()
                                                                .equals(tradeItemVO.getSkuId())) {
                                                            tradeItemVO.setPrice(
                                                                    tradeItem.getOriginalPrice());
                                                            tradeItemVO.setIsBookingSaleGoods(
                                                                    tradeItem
                                                                            .getIsBookingSaleGoods());
                                                            tradeItemVO.setBookingSaleId(
                                                                    tradeItem.getBookingSaleId());
                                                            tradeItemVO.setBookingType(
                                                                    tradeItem.getBookingType());
                                                            tradeItemVO.setHandSelStartTime(
                                                                    tradeItem
                                                                            .getHandSelStartTime());
                                                            tradeItemVO.setHandSelEndTime(
                                                                    tradeItem.getHandSelEndTime());
                                                            tradeItemVO.setTailStartTime(
                                                                    tradeItem.getTailStartTime());
                                                            tradeItemVO.setTailEndTime(
                                                                    tradeItem.getTailEndTime());
                                                            tradeItemVO.setEarnestPrice(
                                                                    tradeItem.getEarnestPrice());
                                                            tradeItemVO.setSwellPrice(
                                                                    tradeItem.getSwellPrice());
                                                        }
                                                    }
                                                });
                            });
                    g.setTradeItems(itemVOList);
                    // 分销商品、开店礼包商品，重新设回市场价
                    if (DefaultFlag.YES.equals(distributionCacheService.queryOpenFlag())
                            && !ChannelType.PC_MALL.equals(
                                    commonUtil.getDistributeChannel().getChannelType())) {
                        g.getTradeItems()
                                .forEach(
                                        item -> {
                                            DefaultFlag storeOpenFlag =
                                                    distributionCacheService.queryStoreOpenFlag(
                                                            item.getStoreId().toString());
                                            if ((Objects.isNull(item.getIsFlashSaleGoods())
                                                            || (Objects.nonNull(
                                                                            item
                                                                                    .getIsFlashSaleGoods())
                                                                    && !item.getIsFlashSaleGoods()))
                                                    && (Objects.isNull(
                                                                    item
                                                                            .getIsAppointmentSaleGoods())
                                                            || !item.getIsAppointmentSaleGoods())
                                                    && !(Objects.nonNull(
                                                                    item.getIsBookingSaleGoods())
                                                            && item.getIsBookingSaleGoods()
                                                            && item.getBookingType()
                                                                    == BookingType.FULL_MONEY)
                                                    && DefaultFlag.YES.equals(storeOpenFlag)
                                                    && (DistributionGoodsAudit.CHECKED.equals(
                                                                    item
                                                                            .getDistributionGoodsAudit())
                                                            || DefaultFlag.YES.equals(
                                                                    g.getStoreBagsFlag()))) {
                                                item.setSplitPrice(
                                                        item.getOriginalPrice()
                                                                .multiply(
                                                                        new BigDecimal(
                                                                                item.getNum())));
                                                item.setPrice(item.getOriginalPrice());
                                                item.setLevelPrice(item.getOriginalPrice());
                                            } else {
                                                item.setDistributionGoodsAudit(
                                                        DistributionGoodsAudit.COMMON_GOODS);
                                            }
                                        });
                    }

                    confirmResponse.setStoreBagsFlag(g.getStoreBagsFlag());
                    items.add(
                            tradeQueryProvider
                                    .queryPurchaseInfo(
                                            TradeQueryPurchaseInfoRequest.builder()
                                                    .tradeItemGroupDTO(
                                                            KsBeanUtil.convert(
                                                                    g, TradeItemGroupDTO.class))
                                                    .tradeItemList(Collections.emptyList())
                                                    .build())
                                    .getContext()
                                    .getTradeConfirmItemVO());
                });

        // 验证小店商品
        this.validShopGoods(tradeItemGroups, commonUtil.getDistributeChannel());
        // 设置购买总积分
        confirmResponse.setTotalBuyPoint(
                items.stream()
                        .flatMap(i -> i.getTradeItems().stream())
                        .mapToLong(
                                v ->
                                        Objects.isNull(v.getBuyPoint())
                                                ? 0
                                                : v.getBuyPoint() * v.getNum())
                        .sum());

        confirmResponse.setTradeConfirmItems(items);

        // 设置小店名称、返利总价
        confirmResponse.setShopName(distributionCacheService.getShopName());
        BigDecimal totalCommission =
                items.stream()
                        .flatMap(i -> i.getTradeItems().stream())
                        .filter(
                                i ->
                                        DistributionGoodsAudit.CHECKED.equals(
                                                i.getDistributionGoodsAudit()))
                        .filter(i -> i.getDistributionCommission() != null)
                        .map(
                                i ->
                                        i.getDistributionCommission()
                                                .multiply(new BigDecimal(i.getNum())))
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
        confirmResponse.setTotalCommission(totalCommission);

        // 设置邀请人名字
        if (StringUtils.isNotEmpty(commonUtil.getDistributeChannel().getInviteeId())) {
            DistributionCustomerByCustomerIdRequest idRequest =
                    new DistributionCustomerByCustomerIdRequest();
            idRequest.setCustomerId(commonUtil.getDistributeChannel().getInviteeId());
            DistributionCustomerVO distributionCustomer =
                    distributionCustomerQueryProvider
                            .getByCustomerId(idRequest)
                            .getContext()
                            .getDistributionCustomerVO();
            if (distributionCustomer != null) {
                confirmResponse.setInviteeName(distributionCustomer.getCustomerName());
            }
        }

        // 校验拼团信息
        validGrouponOrder(confirmResponse, tradeItemGroups, customerId);
        // 根据确认订单计算出的信息，查询出使用优惠券页需要的优惠券列表数据
        DefaultFlag openFlag = distributionCacheService.queryOpenFlag();
        List<TradeItemInfoDTO> tradeDtos =
                items.stream()
                        .flatMap(
                                confirmItem ->
                                        confirmItem.getTradeItems().stream()
                                                .map(
                                                        tradeItem -> {
                                                            TradeItemInfoDTO dto =
                                                                    new TradeItemInfoDTO();
                                                            dto.setBrandId(tradeItem.getBrand());
                                                            dto.setCateId(tradeItem.getCateId());
                                                            dto.setSpuId(tradeItem.getSpuId());
                                                            dto.setSkuId(tradeItem.getSkuId());
                                                            dto.setStoreId(
                                                                    confirmItem
                                                                            .getSupplier()
                                                                            .getStoreId());
                                                            dto.setPrice(tradeItem.getSplitPrice());
                                                            dto.setDistributionGoodsAudit(
                                                                    tradeItem
                                                                            .getDistributionGoodsAudit());
                                                            if (DefaultFlag.NO.equals(openFlag)
                                                                    || DefaultFlag.NO.equals(
                                                                            distributionCacheService
                                                                                    .queryStoreOpenFlag(
                                                                                            String
                                                                                                    .valueOf(
                                                                                                            tradeItem
                                                                                                                    .getStoreId())))) {
                                                                tradeItem.setDistributionGoodsAudit(
                                                                        DistributionGoodsAudit
                                                                                .COMMON_GOODS);
                                                            }
                                                            return dto;
                                                        }))
                        .collect(Collectors.toList());

        CouponCodeListForUseByCustomerIdRequest requ =
                CouponCodeListForUseByCustomerIdRequest.builder()
                        .customerId(customerId)
                        .terminalToken(commonUtil.getTerminalToken())
                        .tradeItems(tradeDtos)
                        .build();
        confirmResponse.setCouponCodes(
                couponCodeQueryProvider
                        .listForUseByCustomerId(requ)
                        .getContext()
                        .getCouponCodeList());

        return BaseResponse.success(confirmResponse);
    }



    private void validGrouponOrder(
            TradeConfirmResponse response,
            List<TradeItemGroupVO> tradeItemGroups,
            String customerId) {
        TradeItemGroupVO tradeItemGroup = tradeItemGroups.get(NumberUtils.INTEGER_ZERO);
        TradeItemVO item = tradeItemGroup.getTradeItems().get(NumberUtils.INTEGER_ZERO);
        TradeConfirmItemVO confirmItem =
                response.getTradeConfirmItems().get(NumberUtils.INTEGER_ZERO);
        TradeItemVO resItem = confirmItem.getTradeItems().get(NumberUtils.INTEGER_ZERO);
        if (Objects.nonNull(tradeItemGroup.getGrouponForm())) {

            TradeGrouponCommitFormVO grouponForm = tradeItemGroup.getGrouponForm();

            if (!DistributionGoodsAudit.COMMON_GOODS.equals(item.getDistributionGoodsAudit())) {
                log.error("拼团单，不能下分销商品");
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }

            // 1.校验拼团商品
            GrouponGoodsInfoVO grouponGoodsInfo =
                    grouponProvider
                            .validGrouponOrderBeforeCommit(
                                    GrouponOrderValidRequest.builder()
                                            .buyCount(item.getNum().intValue())
                                            .customerId(customerId)
                                            .goodsId(item.getSpuId())
                                            .goodsInfoId(item.getSkuId())
                                            .grouponNo(grouponForm.getGrouponNo())
                                            .openGroupon(grouponForm.getOpenGroupon())
                                            .build())
                            .getContext()
                            .getGrouponGoodsInfo();

            if (Objects.isNull(grouponGoodsInfo)) {
                log.error("拼团单下的不是拼团商品");
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }

            // 2.设置拼团活动信息
            boolean freeDelivery =
                    grouponActivityQueryProvider
                            .getFreeDeliveryById(
                                    new GrouponActivityFreeDeliveryByIdRequest(
                                            grouponGoodsInfo.getGrouponActivityId()))
                            .getContext()
                            .isFreeDelivery();
            response.setOpenGroupon(grouponForm.getOpenGroupon());
            response.setGrouponFreeDelivery(freeDelivery);

            // 3.设成拼团价
            BigDecimal grouponPrice = grouponGoodsInfo.getGrouponPrice();
            BigDecimal splitPrice = grouponPrice.multiply(new BigDecimal(item.getNum()));
            resItem.setSplitPrice(splitPrice);
            resItem.setPrice(grouponPrice);
            resItem.setLevelPrice(grouponPrice);
            resItem.setBuyPoint(NumberUtils.LONG_ZERO);
            confirmItem.getTradePrice().setGoodsPrice(splitPrice);
            confirmItem.getTradePrice().setTotalPrice(splitPrice);
            confirmItem.getTradePrice().setBuyPoints(NumberUtils.LONG_ZERO);
            response.setTotalBuyPoint(NumberUtils.LONG_ZERO);
        }
    }



    /**
     * 填充商品状态
     *
     * @param items
     */
    @Deprecated
    public void setGoodsStatus(List<TradeConfirmItemVO> items, Long storeId) {
        if (CollectionUtils.isNotEmpty(items)) {
            items.forEach(
                    item -> {
                        List<TradeItemVO> gifts = item.getGifts();
                        if (CollectionUtils.isNotEmpty(gifts)) {
                            Map<String, GoodsStatus> statusMap;
                            List<String> skuIds =
                                    gifts.stream()
                                            .map(TradeItemVO::getSkuId)
                                            .collect(Collectors.toList());
                            List<GoodsInfoVO> goodsInfos =
                                    goodsInfoQueryProvider
                                            .listByIds(
                                                    GoodsInfoListByIdsRequest.builder()
                                                            .goodsInfoIds(skuIds)
                                                            .build())
                                            .getContext()
                                            .getGoodsInfos();
                            if (CollectionUtils.isNotEmpty(goodsInfos)) {
                                goodsInfos =
                                        goodsInfos.parallelStream()
                                                .map(
                                                        goodsInfoVO -> {
                                                            if (goodsInfoVO.getPluginType()
                                                                            == PluginType.O2O
                                                                    && Objects.nonNull(storeId)) {
                                                                goodsInfoVO.setStoreId(storeId);
                                                                return goodsInfoExtraService
                                                                        .setGoodsInfo(goodsInfoVO);
                                                            }
                                                            return goodsInfoVO;
                                                        })
                                                .collect(Collectors.toList());
                                statusMap =
                                        goodsInfos.stream()
                                                .collect(
                                                        Collectors.toMap(
                                                                GoodsInfoVO::getGoodsInfoId,
                                                                GoodsInfoVO::getGoodsStatus,
                                                                (k1, k2) -> k1));
                                for (TradeItemVO gift : gifts) {
                                    gift.setGoodsStatus(statusMap.get(gift.getSkuId()));
                                }
                            }
                        }
                    });
        }
    }

    /**
     * 根据参数查询某订单的运费
     *
     * @param tradeParams
     * @return
     */
    @Operation(summary = "根据参数查询某订单的运费")
    @RequestMapping(value = "/getFreight", method = RequestMethod.POST)
    public BaseResponse<List<TradeGetFreightResponse>> getFreight(
            @RequestBody List<TradeParamsRequest> tradeParams) {
        List<TradeGetFreightResponse> freightResponses = new ArrayList<>();
        tradeParams.stream().forEach((params ->{
            freightResponses.add(tradeQueryProvider.getFreight(params).getContext());
        }));
        return BaseResponse.success(freightResponses);
    }

    @Operation(summary = "根据参数查询某订单的运费")
    @RequestMapping(value = "/getFreight/new", method = RequestMethod.POST)
    public BaseResponse<List<TradeGetFreightResponse>> getFreightNew(
            @RequestBody GetFreightRequest request) {
        //根据处理积分均摊
        request.checkParam();
        request.handlePointsPrice();
        List<TradeGetFreightResponse> freightResponses = new ArrayList<>();
        request.getTradeParams().stream()
                .forEach(params -> {
                    freightResponses.add(tradeQueryProvider.getFreight(params).getContext());
                });
        return BaseResponse.success(freightResponses);
    }


    @Operation(summary = "根据参数查询某订单的佣金")
    @RequestMapping(value = "/getCommission", method = RequestMethod.POST)
    public BaseResponse<OrderCommissionResponse> getCommission(@RequestBody GetCommissionRequest request) {
        //根据处理积分均摊
        request.checkParam();
        request.handlePointsPrice();
        OrderCommissionResponse response = new OrderCommissionResponse();
        List<GetCommissionTradeParams> tradeParams = request.getTradeParams();
        // 获取订单快照
        TradeItemSnapshotVO tradeItemSnapshotVO = tradeItemQueryProvider.listByTerminalTokenWithout(TradeItemSnapshotByCustomerIdRequest
                .builder().terminalToken(commonUtil.getTerminalToken()).build()).getContext().getTradeItemSnapshotVO();
        if (Objects.isNull(tradeItemSnapshotVO)) {
            return BaseResponse.success(OrderCommissionResponse.builder().commission(BigDecimal.ZERO).build());
        }
        for (GetCommissionTradeParams params : tradeParams) {
            for (TradeItemGroupVO itemGroupVO : tradeItemSnapshotVO.getItemGroups()) {
                if (Objects.nonNull(itemGroupVO.getSupplier())
                        && Objects.nonNull(params.getSupplier())
                        && Objects.equals(itemGroupVO.getSupplier().getSupplierId(), params.getSupplier().getSupplierId())) {
                    params.setStoreBagsFlag(itemGroupVO.getStoreBagsFlag());
                }
            }
        }
        // 匹配订单封装开店礼包标识
        BigDecimal commission = distributionService.dealDistribution(tradeParams);
        response.setCommission(commission);
        return BaseResponse.success(response);
    }


    /** 我的拼购分页查询订单 */
    @Operation(summary = "我的拼购分页查询订单")
    @RequestMapping(value = "/page/groupons", method = RequestMethod.POST)
    public BaseResponse<Page<GrouponTradeVO>> grouponPage(
            @RequestBody TradePageQueryRequest paramRequest) {
        TradeQueryDTO tradeQueryRequest =
                TradeQueryDTO.builder()
                        .buyerId(commonUtil.getOperatorId())
                        .grouponFlag(Boolean.TRUE)
                        .isBoss(Boolean.FALSE)
                        .build();
        tradeQueryRequest.setPageNum(paramRequest.getPageNum());
        tradeQueryRequest.setPageSize(paramRequest.getPageSize());
        Page<TradeVO> tradePage =
                tradeQueryProvider
                        .pageCriteria(
                                TradePageCriteriaRequest.builder()
                                        .tradePageDTO(tradeQueryRequest)
                                        .build())
                        .getContext()
                        .getTradePage();
        List<GrouponTradeVO> tradeReponses = new ArrayList<>();
        tradePage
                .getContent()
                .forEach(
                        info -> {
                            GrouponTradeVO tradeReponse =
                                    KsBeanUtil.convert(info, GrouponTradeVO.class);
                            // 待成团-获取团实例
                            if (GrouponOrderStatus.WAIT.equals(
                                            tradeReponse.getTradeGroupon().getGrouponOrderStatus())
                                    && PayState.PAID.equals(
                                            tradeReponse.getTradeState().getPayState())) {
                                GrouponInstanceByGrouponNoRequest request =
                                        GrouponInstanceByGrouponNoRequest.builder()
                                                .grouponNo(info.getTradeGroupon().getGrouponNo())
                                                .build();
                                tradeReponse.setGrouponInstance(
                                        grouponInstanceQueryProvider
                                                .detailByGrouponNo(request)
                                                .getContext()
                                                .getGrouponInstance());
                            }
                            tradeReponses.add(tradeReponse);
                        });

        return BaseResponse.success(
                new PageImpl<>(
                        tradeReponses,
                        tradeQueryRequest.getPageable(),
                        tradePage.getTotalElements()));
    }

    /** 分页查询订单 */
    @Operation(summary = "分页查询订单")
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public BaseResponse<Page<TradeVO>> page(@RequestBody TradePageQueryRequest paramRequest) {
        TradeQueryDTO tradeQueryRequest =
                TradeQueryDTO.builder()
                        .tradeState(
                                TradeStateDTO.builder()
                                        .flowState(paramRequest.getFlowState())
                                        .payState(paramRequest.getPayState())
                                        .deliverStatus(paramRequest.getDeliverStatus())
                                        .build())
                        .buyerId(commonUtil.getOperatorId())
                        .inviteeId(paramRequest.getInviteeId())
                        .channelType(paramRequest.getChannelType())
                        .beginTime(paramRequest.getCreatedFrom())
                        .endTime(paramRequest.getCreatedTo())
                        .keyworks(paramRequest.getKeywords())
                        .isBoss(Boolean.FALSE)
                        .goodsType(paramRequest.getGoodsType())
                        .filterCycleOrder(TerminalSource.PC.equals(commonUtil.getTerminal()) ? Boolean.TRUE: Boolean.FALSE)
                        .build();
        if (Objects.isNull(paramRequest.getTimeState())) {
            tradeQueryRequest.setEndTime(DateUtil.nowDate());
        } else if (paramRequest.getTimeState().equals(TimeState.SEVEN_DAY)) {
            tradeQueryRequest.setBeginTime(DateUtil.beforeSevenDate());
            tradeQueryRequest.setEndTime(DateUtil.nowDate());
        } else if (paramRequest.getTimeState().equals(TimeState.ONE_MONTH)) {
            tradeQueryRequest.setBeginTime(DateUtil.beforeOneMonthDate());
            tradeQueryRequest.setEndTime(DateUtil.nowDate());
        } else if (paramRequest.getTimeState().equals(TimeState.THREE_MONTH)) {
            tradeQueryRequest.setBeginTime(DateUtil.beforeThreeMonth());
            tradeQueryRequest.setEndTime(DateUtil.nowDate());
        }
        tradeQueryRequest.setPageNum(paramRequest.getPageNum());
        tradeQueryRequest.setPageSize(paramRequest.getPageSize());
        // 设定状态条件逻辑,已审核状态需筛选出已审核与部分发货
        tradeQueryRequest.makeAllAuditFlow();

        Page<TradeVO> tradePage =
                tradeQueryProvider
                        .pageCriteria(
                                TradePageCriteriaRequest.builder()
                                        .tradePageDTO(tradeQueryRequest)
                                        .build())
                        .getContext()
                        .getTradePage();
        TradeConfigGetByTypeRequest request = new TradeConfigGetByTypeRequest();
        request.setConfigType(ConfigType.ORDER_SETTING_APPLY_REFUND);
        TradeConfigGetByTypeResponse config =
                auditQueryProvider.getTradeConfigByType(request).getContext();
        AtomicBoolean flag = new AtomicBoolean(config.getStatus() == 1);
        int days = JSONObject.parseObject(config.getContext()).getInteger("day");
        List<TradeVO> tradeReponses = new ArrayList<>();
        tradePage
                .getContent()
                .forEach(
                        info -> {
                            TradeVO tradeReponse = new TradeVO();
                            BeanUtils.copyProperties(info, tradeReponse);
                            TradeStateVO tradeState = tradeReponse.getTradeState();
                            // 申请退单状态数据库状态优先
                            if (Objects.nonNull(tradeState.getRefundStatus())) {
                                flag.set(tradeState.getRefundStatus() == 1);
                            }
                            boolean canReturnFlag =
                                    tradeState.getFlowState() == FlowState.COMPLETED
                                            || (tradeState.getPayState() == PayState.PAID
                                                    && tradeState.getDeliverStatus()
                                                            == DeliverStatus.NOT_YET_SHIPPED
                                                    && tradeState.getFlowState() != FlowState.VOID);
                            OrderTagVO orderTag = tradeReponse.getOrderTag();
                            // 是否是虚拟订单或者卡券订单
                            boolean isVirtual =
                                    Objects.nonNull(orderTag)
                                            && (orderTag.getVirtualFlag()
                                                    || orderTag.getElectronicCouponFlag());
                            if (isVirtual) {
                                canReturnFlag = true;
                            } else {
                                canReturnFlag =
                                        isCanReturnTime(
                                                flag.get(), days, tradeState, canReturnFlag);
                                if (tradeState.getFlowState() == FlowState.DELIVERED_PART
                                        || tradeState.getFlowState() == FlowState.DELIVERED) {
                                    canReturnFlag = Boolean.TRUE.equals(info.getTransitReturn());
                                }
                            }

                            // 开店礼包及提货卡订单不支持退货退款
                            boolean isPickupCard = Objects.nonNull(orderTag) && orderTag.getPickupCardFlag();
                            canReturnFlag =
                                    canReturnFlag
                                            && DefaultFlag.NO == tradeReponse.getStoreBagsFlag() && !isPickupCard;
                            //提货卡订单不支持售后
                            if(Objects.nonNull(tradeReponse.getTradePrice().getGiftCardType()) && tradeReponse.getTradePrice().getGiftCardType() == GiftCardType.PICKUP_CARD){
                                canReturnFlag = false;
                                tradeReponse.getTradePrice().setBuyPoints(0L);
                                tradeReponse.getTradePrice().setPoints(0L);
                                tradeReponse.getTradePrice().setPointsPrice(BigDecimal.ZERO);
                            }
                            tradeReponse.setCanReturnFlag(canReturnFlag);

                            if (Objects.nonNull(tradeReponse.getIsBookingSaleGoods())
                                    && tradeReponse.getIsBookingSaleGoods()
                                    && tradeReponse.getBookingType() == BookingType.EARNEST_MONEY
                                    && tradeReponse.getTradeState().getPayState()
                                            == PayState.NOT_PAID) {
                                tradeReponse
                                        .getTradePrice()
                                        .setTotalPrice(
                                                tradeReponse.getTradePrice().getEarnestPrice());
                            }
                            if (Objects.nonNull(tradeReponse.getIsBookingSaleGoods())
                                    && tradeReponse.getIsBookingSaleGoods()
                                    && tradeReponse.getBookingType() == BookingType.EARNEST_MONEY
                                    && tradeReponse.getTradeState().getPayState()
                                            == PayState.PAID_EARNEST) {
                                tradeReponse
                                        .getTradePrice()
                                        .setTotalPrice(tradeReponse.getTradePrice().getTailPrice());
                            }

                            FindProviderTradeResponse findProviderTradeResponse =
                                    providerTradeProvider
                                            .findByParentIdList(
                                                    FindProviderTradeRequest.builder()
                                                            .parentId(
                                                                    Collections.singletonList(
                                                                            info.getId()))
                                                            .build())
                                            .getContext();
                            List<TradeVO> tradeVOList = findProviderTradeResponse.getTradeVOList();
                            tradeVOList.forEach(this::fillTradeBookingTimeOut);
                            tradeReponse.setTradeVOList(tradeVOList);
                            tradeReponses.add(tradeReponse);
                        });
        tradeReponses.forEach(this::fillTradeBookingTimeOut);
        tradeReponses.forEach(this::fillVopOrderReturnFlag);
        return BaseResponse.success(
                new PageImpl<>(
                        tradeReponses,
                        tradeQueryRequest.getPageable(),
                        tradePage.getTotalElements()));
    }

    /** 分页查询客户订单 */
    @Operation(summary = "分页查询客户订单")
    @RequestMapping(value = "/customer/page", method = RequestMethod.POST)
    public BaseResponse<Page<TradeVO>> customerOrderPage(
            @RequestBody TradePageQueryRequest paramRequest) {
        TradeQueryDTO tradeQueryRequest =
                TradeQueryDTO.builder()
                        .tradeState(
                                TradeStateDTO.builder()
                                        .flowState(paramRequest.getFlowState())
                                        .payState(paramRequest.getPayState())
                                        .deliverStatus(paramRequest.getDeliverStatus())
                                        .build())
                        .inviteeId(commonUtil.getOperatorId())
                        .channelType(paramRequest.getChannelType())
                        .beginTime(paramRequest.getCreatedFrom())
                        .endTime(paramRequest.getCreatedTo())
                        .keyworks(paramRequest.getKeywords())
                        .customerOrderListAllType(paramRequest.isCustomerOrderListAllType())
                        .isBoss(Boolean.FALSE)
                        .build();
        tradeQueryRequest.setPageSize(paramRequest.getPageSize());
        tradeQueryRequest.setPageNum(paramRequest.getPageNum());

        // 设定状态条件逻辑,需筛选出已支付下已审核与部分发货订单
        tradeQueryRequest.setPayedAndAudit();

        Page<TradeVO> tradePage =
                tradeQueryProvider
                        .pageCriteria(
                                TradePageCriteriaRequest.builder()
                                        .tradePageDTO(tradeQueryRequest)
                                        .isCustomerPage(true)
                                        .build())
                        .getContext()
                        .getTradePage();
        TradeConfigGetByTypeRequest request = new TradeConfigGetByTypeRequest();
        request.setConfigType(ConfigType.ORDER_SETTING_APPLY_REFUND);
        TradeConfigGetByTypeResponse config =
                auditQueryProvider.getTradeConfigByType(request).getContext();
        AtomicBoolean flag = new AtomicBoolean(config.getStatus() == 1);
        int days = JSONObject.parseObject(config.getContext()).getInteger("day");
        List<TradeVO> tradeReponses = new ArrayList<>();
        tradePage
                .getContent()
                .forEach(
                        info -> {
                            TradeVO tradeReponse = new TradeVO();
                            BeanUtils.copyProperties(info, tradeReponse);
                            TradeStateVO tradeState = tradeReponse.getTradeState();
                            // 申请退单状态数据库状态优先
                            if (Objects.nonNull(tradeState.getRefundStatus())) {
                                flag.set(tradeState.getRefundStatus() == 1);
                            }
                            boolean canReturnFlag =
                                    (tradeState.getPayState() == PayState.PAID
                                            || tradeState.getFlowState() == FlowState.COMPLETED
                                                    && tradeState.getDeliverStatus()
                                                            == DeliverStatus.NOT_YET_SHIPPED
                                                    && tradeState.getFlowState() != FlowState.VOID);
                            OrderTagVO orderTag = tradeReponse.getOrderTag();
                            // 是否是虚拟订单或者卡券订单
                            boolean isVirtual =
                                    Objects.nonNull(orderTag)
                                            && (orderTag.getVirtualFlag()
                                                    || orderTag.getElectronicCouponFlag());
                            if (isVirtual) {
                                canReturnFlag = true;
                            } else {
                                canReturnFlag =
                                        isCanReturnTime(
                                                flag.get(), days, tradeState, canReturnFlag);
                            }
                            // 开店礼包及提货卡订单不支持退货退款
                            boolean isPickupCard = Objects.nonNull(orderTag) && orderTag.getPickupCardFlag();
                            canReturnFlag =
                                    canReturnFlag
                                            && DefaultFlag.NO == tradeReponse.getStoreBagsFlag() && !isPickupCard;
                            //提货卡订单不支持售后
                            if(Objects.nonNull(tradeReponse.getTradePrice().getGiftCardType()) && tradeReponse.getTradePrice().getGiftCardType() == GiftCardType.PICKUP_CARD){
                                canReturnFlag = false;
                            }
                            tradeReponse.setCanReturnFlag(canReturnFlag);
                            tradeReponses.add(tradeReponse);
                        });
        return BaseResponse.success(
                new PageImpl<>(
                        tradeReponses,
                        tradeQueryRequest.getPageable(),
                        tradePage.getTotalElements()));
    }

    /** 查询订单商品清单 */
    @Operation(summary = "查询订单商品清单")
    @Parameter(
            name = "tid",
            description = "订单ID",
            required = true)
    @RequestMapping(value = "/goods/{tid}", method = RequestMethod.GET)
    public BaseResponse<List<TradeItemVO>> tradeItems(@PathVariable String tid) {
        TradeVO trade =
                tradeQueryProvider
                        .getById(TradeGetByIdRequest.builder().tid(tid).build())
                        .getContext()
                        .getTradeVO();
        checkUnauthorized(tid, trade,true);
        return BaseResponse.success(trade.getTradeItems());
    }

    /** 查询订单发货清单 */
    @Operation(summary = "查询订单发货清单")
    @Parameter(
            name = "tid",
            description = "订单ID",
            required = true)
    @RequestMapping(value = "/deliverRecord/{tid}", method = RequestMethod.GET)
    public BaseResponse<TradeDeliverRecordResponse> tradeDeliverRecord(@PathVariable String tid) {
        TradeVO trade =
                tradeQueryProvider
                        .getById(
                                TradeGetByIdRequest.builder()
                                        .tid(tid)
                                        .needLmOrder(Boolean.TRUE)
                                        .build())
                        .getContext()
                        .getTradeVO();
        // 订单列表做验证,客户订单列表无需验证
        checkUnauthorized(tid, trade,false);

        TradeDeliverRecordResponse tradeDeliverRecord =
                TradeDeliverRecordResponse.builder()
                        .status(trade.getTradeState().getFlowState().getStateId())
                        .tradeDeliver(trade.getTradeDelivers())
                        .pickupFlag(trade.getPickupFlag())
                        .build();

        List<TradeVO> providerTradeList =
                trade.getTradeVOList().stream()
                        .filter(providerTrade -> providerTrade.getId().startsWith("P"))
                        .collect(Collectors.toList());

        if (Boolean.TRUE.equals(trade.getPickupFlag())
                && CollectionUtils.isNotEmpty(providerTradeList)
                && !Objects.equals(FlowState.VOID, trade.getTradeState().getFlowState())) {
            tradeDeliverRecord.setOrdersFlag(Boolean.TRUE);

            // 供应商子单全部发货且没有完成
            if (CollectionUtils.isEmpty(
                            providerTradeList.stream()
                                    .filter(
                                            providerTrade ->
                                                    !Objects.equals(
                                                            DeliverStatus.SHIPPED,
                                                            providerTrade
                                                                    .getTradeState()
                                                                    .getDeliverStatus()))
                                    .collect(Collectors.toList()))
                    && !Objects.equals(
                            FlowState.COMPLETED,
                            providerTradeList.get(0).getTradeState().getFlowState())
                    && !Objects.equals(
                            FlowState.CONFIRMED,
                            providerTradeList.get(0).getTradeState().getFlowState())) {
                tradeDeliverRecord.setDeliverStatus(DeliverStatus.SHIPPED);
            }
        }
        return BaseResponse.success(tradeDeliverRecord);
    }

    /** 获取订单发票信息 */
    @Operation(summary = "获取订单发票信息")
    @Parameters({
        @Parameter(
                name = "tid",
                description = "订单ID",
                required = true),
        @Parameter(
                name = "type",
                description = "主客订单TYPE",
                required = true)
    })
    @RequestMapping(value = "/invoice/{tid}/{type}", method = RequestMethod.GET)
    public BaseResponse<InvoiceVO> invoice(@PathVariable String tid, @PathVariable String type) {
        TradeVO trade =
                tradeQueryProvider
                        .getById(TradeGetByIdRequest.builder().tid(tid).build())
                        .getContext()
                        .getTradeVO();
        checkUnauthorized(tid, trade,false);
        InvoiceVO invoice = trade.getInvoice();
        // 若无发票收货地址，则默认为订单收货地址
        if (Objects.nonNull(invoice) && invoice.getAddress() == null) {
            InvoiceVO.builder()
                    .address(trade.getConsignee().getDetailAddress())
                    .contacts(trade.getConsignee().getName())
                    .phone(trade.getConsignee().getPhone())
                    .build();
        }
        return BaseResponse.success(invoice);
    }

    /** 查询订单附件信息，只做展示使用 */
    @Operation(summary = "查询订单附件信息，只做展示使用")
    @Parameter(
            name = "tid",
            description = "订单ID",
            required = true)
    @RequestMapping(value = "/encloses/{tid}", method = RequestMethod.GET)
    public BaseResponse<String> encloses(@PathVariable String tid) {
        TradeVO trade =
                tradeQueryProvider
                        .getById(TradeGetByIdRequest.builder().tid(tid).build())
                        .getContext()
                        .getTradeVO();
        checkUnauthorized(tid, trade,false);
        return BaseResponse.success(trade.getEncloses());
    }

    /** 查询订单付款记录 */
    @Operation(summary = "查询订单付款记录")
    @Parameter(
            name = "tid",
            description = "订单ID",
            required = true)
    @RequestMapping(value = "/payOrder/{tid}", method = RequestMethod.GET)
    public BaseResponse<FindPayOrderResponse> payOrder(@PathVariable String tid) {
        TradeVO trade =
                tradeQueryProvider
                        .getById(TradeGetByIdRequest.builder().tid(tid).build())
                        .getContext()
                        .getTradeVO();
        checkUnauthorized(tid, trade,false);
        List<String> goodsIdList = new ArrayList<>();
        trade.getTradeItems()
                .forEach(
                        tradeItemVO -> {
                            if (Objects.nonNull(tradeItemVO.getSpuId())) {
                                goodsIdList.add(tradeItemVO.getSpuId());
                            }
                        });

        FindPayOrderResponse payOrderResponse = null;
        try {

            BaseResponse<FindPayOrderResponse> response =
                    payOrderQueryProvider.findPayOrder(
                            FindPayOrderRequest.builder().value(trade.getId()).build());

            payOrderResponse = response.getContext();

        } catch (SbcRuntimeException e) {
            if (AccountErrorCodeEnum.K020032.getCode().equals(e.getErrorCode())) {
                payOrderResponse = new FindPayOrderResponse();
                payOrderResponse.setPayType(
                        PayType.fromValue(Integer.parseInt(trade.getPayInfo().getPayTypeId())));
                payOrderResponse.setTotalPrice(trade.getTradePrice().getTotalPrice());
            } else {
                throw e;
            }
        }
        if (Objects.nonNull(payOrderResponse)) {
            if (Objects.nonNull(trade.getTradeGroupon())) {
                payOrderResponse.setGrouponNo(trade.getTradeGroupon().getGrouponNo());
            }
            if (StringUtils.isBlank(payOrderResponse.getOrderCode())) {
                payOrderResponse.setOrderCode(trade.getId());
            }
            payOrderResponse.setPayOrderPrice(trade.getTradePrice().getTotalPayCash());
            if (StringUtils.isNoneBlank(
                    trade.getShopName(), trade.getDistributorId(), trade.getDistributorName())) {
                payOrderResponse.setStoreName(
                        trade.getDistributorName().concat("的").concat(trade.getShopName()));
            } else {
                payOrderResponse.setStoreName(trade.getSupplier().getStoreName());
            }
            payOrderResponse.setIsSelf(trade.getSupplier().getIsSelf());
            if (Objects.nonNull(trade.getTradePrice().getBuyPoints())
                    && trade.getTradePrice().getBuyPoints().compareTo(Long.valueOf(0L)) > 0) {
                payOrderResponse.setBuyPoints(trade.getTradePrice().getBuyPoints());
            } else {
                payOrderResponse.setBuyPoints(trade.getTradePrice().getPoints());
            }

            if (Objects.isNull(payOrderResponse.getPayOrderPoints())) {
                payOrderResponse.setPayOrderPoints(payOrderResponse.getBuyPoints());
            }
            payOrderResponse.setGiftCardPrice(trade.getTradePrice().getGiftCardPrice());
            // 订单流程状态
            payOrderResponse.setFlowState(trade.getTradeState().getFlowState());
            payOrderResponse.setGoodsIdList(goodsIdList);
            payOrderResponse.setStoreType(trade.getSupplier().getStoreType());
            if (Objects.isNull(payOrderResponse.getPayOrderPrice())) {
                payOrderResponse.setPayOrderPrice(trade.getTradePrice().getTotalPrice());
            }
            payOrderResponse.setSendCouponFlag(Boolean.FALSE);
            // 第一次进来
            String redisKey = CacheKeyConstant.PAY_ORDER_STATUS_KEY.concat(tid);
            if (Objects.nonNull(trade.getSendCouponFlag())
                    && trade.getSendCouponFlag()
                    && trade.getTradeState().getPayState() == PayState.PAID
                    && !redisService.hasKey(redisKey)) {
                redisService.setString(redisKey, Constants.STR_1);
                payOrderResponse.setSendCouponFlag(Boolean.TRUE);
            }
            // 线下订单不展示支付广告
            if (!PayType.OFFLINE.name().equals(trade.getPayInfo().getPayTypeName())) {
                // 已支付的展示广告
                PayAdvertisementPageRequest pageReq = new PayAdvertisementPageRequest();
                pageReq.setQueryTab(Constants.ONE);
                pageReq.setDelFlag(DeleteFlag.NO);
                pageReq.putSort("createTime", "asc");
                BaseResponse<PayAdvertisementPageResponse> response =
                        payAdvertisementQueryProvider.page(pageReq);
                // 获取进行中的广告数据
                List<PayAdvertisementVO> page =
                        response.getContext().getPayAdvertisementVOPage().getContent();
                if (CollectionUtils.isNotEmpty(page)) {
                    for (PayAdvertisementVO payAdvertisementVO : page) {
                        if (validateShowAd(trade, payAdvertisementVO)) {
                            payOrderResponse.setPayAdvertisementVO(payAdvertisementVO);
                            break;
                        }
                    }
                }
            }

            if (Objects.nonNull(trade.getOrderTag()) && Objects.nonNull(trade.getOrderTag().getPickupCardFlag()) && trade.getOrderTag().getPickupCardFlag()){
                payOrderResponse.setPayOrderPoints(0L);
                payOrderResponse.setBuyPoints(0L);
            }
        }
        return BaseResponse.success(payOrderResponse);
    }
    // 校验是否满足展示支付广告
    private boolean validateShowAd(TradeVO trade, PayAdvertisementVO payAdvertisementVO) {
        // 店铺判断
        if (Objects.nonNull(payAdvertisementVO.getStoreType())) {
            // 部分店铺
            if (Constants.TWO == payAdvertisementVO.getStoreType()) {
                // 获取配置店铺
                List<PayAdvertisementStoreVO> payAdvertisementStore =
                        payAdvertisementVO.getPayAdvertisementStore();
                if(payAdvertisementStore == null) {
                    return false;
                }
                List<Long> storeIds =
                        payAdvertisementStore.stream()
                                .map(PayAdvertisementStoreVO::getStoreId)
                                .filter(Objects::nonNull)
                                .distinct()
                                .collect(Collectors.toList());
                if (!storeIds.contains(trade.getSupplier().getStoreId())) {
                    return false;
                }
            }
            // 订单实付金额判断
            BigDecimal total =
                    Objects.isNull(trade.getTradePrice().getTotalPrice())
                            ? BigDecimal.ZERO
                            : trade.getTradePrice().getTotalPrice();
            // 定金预售按付款价格计算
            if (trade.getIsBookingSaleGoods() != null
                    && trade.getIsBookingSaleGoods()
                    && trade.getBookingType() != null
                    && trade.getBookingType().equals(BookingType.EARNEST_MONEY)) {
                // 付定金不展示广告
                if (trade.getTradeState().getPayState() == PayState.PAID_EARNEST
                        && trade.getTradeState().getFlowState() == FlowState.WAIT_PAY_TAIL) {
                    return false;
                }
            }
            if (total.compareTo(payAdvertisementVO.getOrderPrice()) >= 0) {
                return validateCustomerLevel(trade, payAdvertisementVO);
            }
        }
        return false;
    }

    // 校验目标客户
    private boolean validateCustomerLevel(TradeVO trade, PayAdvertisementVO payAdvertisementVO) {
        // 不限等级
        if (Constants.STR_0.equals(payAdvertisementVO.getJoinLevel())) {
            return true;
        }
        Long storeId = trade.getSupplier().getStoreId();
        // 目标客户判断
        CustomerLevelMapByCustomerIdAndStoreIdsRequest customerLevelMapRequest =
                new CustomerLevelMapByCustomerIdAndStoreIdsRequest();
        customerLevelMapRequest.setCustomerId(trade.getBuyer().getId());
        customerLevelMapRequest.setStoreIds(Collections.singletonList(storeId));
        // 根据会员ID查询平台会员等级的Map结果
        Map<Long, CommonLevelVO> storeLevelMap =
                customerLevelQueryProvider
                        .listCustomerLevelMapByCustomerId(customerLevelMapRequest)
                        .getContext()
                        .getCommonLevelVOMap();

        CommonLevelVO level = storeLevelMap.get(Constants.BOSS_DEFAULT_STORE_ID);
        if (Objects.nonNull(level)) {
            return Arrays.asList(payAdvertisementVO.getJoinLevel().split(","))
                    .contains(String.valueOf(level.getLevelId()));
        }
        return false;
    }

    /** 根据父订单查询子订单付款记录 */
    @Operation(summary = "查询订单付款记录")
    @Parameter(
            name = "parentTid",
            required = true)
    @RequestMapping(value = "/payOrders/{parentTid}", method = RequestMethod.GET)
    public BaseResponse<FindPayOrderListResponse> payOrders(@PathVariable String parentTid) {
        return payOrderQueryProvider.findPayOrderList(
                new FindPayOrderListRequest(parentTid, commonUtil.getOperator().getUserId()));
    }

    /** 查询尾款订单付款记录 */
    @Operation(summary = "查询尾款订单付款记录")
    @Parameter(
            name = "tailOrderNo",
            description = "父订单ID",
            required = true)
    @RequestMapping(value = "/payOrderByTailOrderNo/{parentTid}", method = RequestMethod.GET)
    public BaseResponse<FindPayOrderResponse> payOrderByTailOrderNo(
            @PathVariable String parentTid) {

        List<TradeVO> tradeVOList =
                tradeQueryProvider
                        .getListByParentId(
                                TradeListByParentIdRequest.builder().parentTid(parentTid).build())
                        .getContext()
                        .getTradeVOList();
        TradeVO trade = null;
        FindPayOrderResponse payOrderResponse = null;
        if (CollectionUtils.isNotEmpty(tradeVOList)) {
            trade = tradeVOList.get(0);
            try {
                BaseResponse<FindPayOrderResponse> response =
                        payOrderQueryProvider.findPayOrder(
                                FindPayOrderRequest.builder()
                                        .value(trade.getTailOrderNo())
                                        .build());
                payOrderResponse = response.getContext();
            } catch (SbcRuntimeException e) {
                if (AccountErrorCodeEnum.K020032.getCode().equals(e.getErrorCode())) {
                    payOrderResponse = new FindPayOrderResponse();
                    payOrderResponse.setPayType(
                            PayType.fromValue(Integer.parseInt(trade.getPayInfo().getPayTypeId())));
                    payOrderResponse.setTotalPrice(trade.getTradePrice().getTotalPrice());
                } else {
                    throw e;
                }
            }
            if (Objects.nonNull(payOrderResponse)) {
                payOrderResponse.setStoreName(trade.getSupplier().getStoreName());
                payOrderResponse.setIsSelf(trade.getSupplier().getIsSelf());
                // 订单流程状态
                payOrderResponse.setFlowState(trade.getTradeState().getFlowState());
            }
        }
        return BaseResponse.success(payOrderResponse);
    }

    /** 根据订单号与物流单号查询发货信息 */
    @Operation(summary = "根据订单号与物流单号查询发货信息")
    @Parameters({
        @Parameter(
                name = "tid",
                description = "订单ID",
                required = true),
        @Parameter(
                name = "deliverId",
                description = "发货单号",
                required = true)
    })
    @RequestMapping(value = "/shipments/{tid}/{deliverId}/{type}", method = RequestMethod.GET)
    public BaseResponse<TradeDeliverVO> shippItemsByLogisticsNo(
            @PathVariable String tid, @PathVariable String deliverId, @PathVariable String type) {
        TradeVO trade =
                tradeQueryProvider
                        .getById(TradeGetByIdRequest.builder().tid(tid).build())
                        .getContext()
                        .getTradeVO();
        checkUnauthorized(tid, trade,false);
        TradeDeliverVO deliver =
                trade.getTradeDelivers().stream()
                        .filter(d -> deliverId.equals(d.getDeliverId()))
                        .findFirst()
                        .orElse(null);

        if (deliver != null) {
            List<ShippingItemVO> vos = new ArrayList<>();
            trade.getTradeItems()
                    .forEach(
                            tradeItemVO -> {
                                Optional<ShippingItemVO> vo =
                                        deliver.getShippingItems().stream()
                                                .filter(
                                                        d ->
                                                                d.getSkuId()
                                                                        .equals(
                                                                                tradeItemVO
                                                                                        .getSkuId()))
                                                .peek(
                                                        x -> {
                                                            if (OrderType.NORMAL_ORDER.equals(trade.getOrderType()) ||
                                                                    Objects.isNull(tradeItemVO.getPoints())) {
                                                                x.setPrice(tradeItemVO.getPrice());
                                                            }
                                                            x.setBuyPoint(
                                                                    tradeItemVO.getBuyPoint());
                                                            x.setPoints(tradeItemVO.getPoints());
                                                            x.setPluginType(
                                                                    tradeItemVO.getPluginType());
                                                        })
                                                .findFirst();
                                vo.ifPresent(vos::add);
                            });

            List<ShippingItemVO> giftItemList = new ArrayList<>();
            trade.getGifts()
                    .forEach(
                            tradeItemVO -> {
                                Optional<ShippingItemVO> shippingItemVO =
                                        deliver.getGiftItemList().stream()
                                                .filter(
                                                        d ->
                                                                d.getSkuId()
                                                                        .equals(
                                                                                tradeItemVO
                                                                                        .getSkuId()))
                                                .peek(
                                                        x ->
                                                                x.setPluginType(
                                                                        tradeItemVO
                                                                                .getPluginType()))
                                                .findFirst();
                                shippingItemVO.ifPresent(giftItemList::add);
                            });
            List<ShippingItemVO> preferentialItemList = new ArrayList<>();
            trade.getPreferential()
                    .forEach(
                            tradeItemVO -> {
                                Optional<ShippingItemVO> shippingItemVO =
                                        deliver.getPreferentialItemList().stream()
                                                .filter(
                                                        d ->
                                                                d.getSkuId()
                                                                        .equals(
                                                                                tradeItemVO
                                                                                        .getSkuId()) && Objects.nonNull(d.getMarketingId())
                                                                        && d.getMarketingId().equals(tradeItemVO.getMarketingIds().get(0)))
                                                .peek(
                                                        x -> {
                                                            if (OrderType.NORMAL_ORDER.equals(trade.getOrderType())) {
                                                                x.setPrice(tradeItemVO.getPrice());
                                                            }
                                                            x.setBuyPoint(
                                                                    tradeItemVO.getBuyPoint());
                                                            x.setPoints(tradeItemVO.getPoints());
                                                            x.setPluginType(
                                                                    tradeItemVO.getPluginType());
                                                        })
                                                .findFirst();
                                shippingItemVO.ifPresent(preferentialItemList::add);
                            });

            deliver.setShippingItems(vos);
            deliver.setGiftItemList(giftItemList);
            deliver.setPreferentialItemList(preferentialItemList);
        }
        return BaseResponse.success(deliver);
    }

    /** 根据快递公司及快递单号查询物流详情 */
    @Operation(summary = "根据快递公司及快递单号查询物流详情")
    @RequestMapping(value = "/deliveryInfos", method = RequestMethod.POST)
    public ResponseEntity<List<Map<Object, Object>>> logistics(
            @RequestBody DeliveryQueryRequest queryRequest) {
        List<Map<Object, Object>> result =
                expressQueryProvider.expressInfoQuery(queryRequest).getContext().getOrderList();
        return ResponseEntity.ok(result);
    }

    /** 新增线下付款单 */
    @Operation(summary = "新增线下付款单")
    @RequestMapping(value = "/pay/offline", method = RequestMethod.POST)
    @GlobalTransactional
    public BaseResponse createPayOrder(
            @RequestBody @Valid PaymentRecordRequest paymentRecordRequest) {
        Operator operator = commonUtil.getOperator();
        TradeVO trade =
                tradeQueryProvider
                        .getById(
                                TradeGetByIdRequest.builder()
                                        .tid(paymentRecordRequest.getTid())
                                        .build())
                        .getContext()
                        .getTradeVO();
        // 若商家后台修改了支付方式，则通知前端
        PayInfoVO payInfo = trade.getPayInfo();
        if (!payInfo.getPayTypeId().equals(String.valueOf(Constants.ONE))) {
            throw new SbcRuntimeException(SettingErrorCodeEnum.K070085);
        }
        if (!trade.getBuyer().getId().equals(commonUtil.getOperatorId())) {
            return BaseResponse.error("非法越权操作");
        }
        // 校验该商家是否存在该 accountId
        OfflineAccountGetByIdResponse response = offlineQueryProvider.getById(
                new OfflineAccountGetByIdRequest(paymentRecordRequest.getAccountId())).getContext();
        if(Objects.isNull(response) || (!Objects.isNull(response) && !Objects.isNull(response.getDeleteFlag()) && 1 == response.getDeleteFlag())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000014);
        }
        if(AccountStatus.DISABLE.toValue() == ObjectUtils.defaultIfNull(response.getBankStatus(), AccountStatus.DISABLE.toValue()) ) {
            throw new SbcRuntimeException(AccountErrorCodeEnum.K020035);
        }

        if (trade.getTradeState().getFlowState() == FlowState.INIT
                || trade.getTradeState().getFlowState() == FlowState.VOID) {
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050068);
        }
        ReceivableAddDTO receivableAddDTO =
                ReceivableAddDTO.builder()
                        .accountId(paymentRecordRequest.getAccountId())
                        .payOrderId(trade.getPayOrderId())
                        .createTime(paymentRecordRequest.getCreateTime())
                        .comment(paymentRecordRequest.getRemark())
                        .encloses(paymentRecordRequest.getEncloses())
                        .build();

        TradeAddReceivableRequest tradeAddReceivableRequest =
                TradeAddReceivableRequest.builder()
                        .receivableAddDTO(receivableAddDTO)
                        .platform(operator.getPlatform())
                        .operator(operator)
                        .build();
        return tradeProvider.addReceivable(tradeAddReceivableRequest);
    }

    /** 取消订单 */
    @Operation(summary = "取消订单")
    @Parameter(
            name = "tid",
            description = "订单ID",
            required = true)
    @RequestMapping(value = "/cancel/{tid}", method = RequestMethod.GET)
    @GlobalTransactional
    public BaseResponse cancel(@PathVariable(value = "tid") String tid) {
        Operator operator = commonUtil.getOperator();
        TradeCancelRequest tradeCancelRequest =
                TradeCancelRequest.builder().tid(tid).operator(operator).build();
        RLock rLock = redissonClient.getFairLock(tid);
        rLock.lock();
        try {
            tradeProvider.cancel(tradeCancelRequest);
        } catch (Exception e) {
            log.error("取消订单发生异常", e);
            throw e;
        } finally {
            rLock.unlock();
        }
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 确认收货
     *
     * @param tid 订单号
     */
    @Operation(summary = "确认收货")
    @Parameter(
            name = "tid",
            description = "订单ID",
            required = true)
    @RequestMapping(value = "/receive/{tid}", method = RequestMethod.GET)
    public BaseResponse confirm(@PathVariable String tid) {
        Operator operator = commonUtil.getOperator();

        TradeVO trade =
                tradeQueryProvider
                        .getById(TradeGetByIdRequest.builder().tid(tid).build())
                        .getContext()
                        .getTradeVO();
        // 为规避达达取货后骑手取消状态无法控制，同城配送单不允许提前确认收货
        // 判断如果是同城配送订单，不能提前确认收货
        // 收货状态由达达回调控制
        if (trade.getDeliverWay() == DeliverWay.SAME_CITY) {
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050162);
        }
        checkUnauthorized(tid, trade,false);

        // 自提订单未核销状态下只确认供应商商品
        if (Boolean.TRUE.equals(trade.getPickupFlag())
                && WriteOffStatus.NOT_WRITTEN_OFF.equals(
                        trade.getWriteOffInfo().getWriteOffStatus())) {
            providerTradeProvider.confirmProviderOrder(
                    ConfirmProviderOrderRequest.builder().id(tid).operator(operator).build());
        } else {
            TradeConfirmReceiveRequest tradeConfirmReceiveRequest =
                    TradeConfirmReceiveRequest.builder().operator(operator).tid(tid).build();

            tradeProvider.confirmReceive(tradeConfirmReceiveRequest);
        }

        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 0元订单默认支付
     *
     * @param tid
     * @return
     */
    @Operation(summary = "0元订单默认支付")
    @Parameter(
            name = "tid",
            description = "订单ID",
            required = true)
    @RequestMapping(value = "/default/pay/{tid}", method = RequestMethod.GET)
    @GlobalTransactional
    public BaseResponse<Boolean> defaultPay(@PathVariable String tid) {
        TradeVO trade =
                tradeQueryProvider.getById(TradeGetByIdRequest.builder().tid(tid).build()).getContext().getTradeVO();
        checkUnauthorized(tid, trade,false);
        // 如果是拉卡拉支付。优惠券支付尾款不覆盖支付方式
        PayWay payWay = PayWay.UNIONPAY;
        if (BookingType.EARNEST_MONEY.equals(trade.getBookingType()) && PayWay.LAKALA.equals(trade.getPayWay())){
            payWay = PayWay.LAKALA;
        }
        TradeDefaultPayRequest tradeDefaultPayRequest = TradeDefaultPayRequest
                .builder()
                .payWay(payWay)
                .tid(tid)
                .build();

        return BaseResponse.success(
                tradeProvider.defaultPay(tradeDefaultPayRequest).getContext().getPayResult());
    }

    /**
     * 获取订单商品详情,包含区间价，会员级别价
     */
    private GoodsInfoResponse getGoodsResponse(List<String> skuIds, String customerId, Long storeId) {
        //性能优化，原来从order服务绕道，现在直接从goods服务直行
        GoodsInfoViewByIdsRequest goodsInfoRequest = GoodsInfoViewByIdsRequest.builder()
                .goodsInfoIds(skuIds)
                .isHavSpecText(Constants.yes)
                .build();
        GoodsInfoViewByIdsResponse response = goodsInfoQueryProvider.listViewByIds(goodsInfoRequest).getContext();
        List<GoodsInfoVO> goodsInfos = response.getGoodsInfos();
        //如果是o2o商品，重新设置商品
        goodsInfos = goodsInfos.stream().map(goodsInfoVO -> {
            if (goodsInfoVO.getPluginType() == PluginType.O2O && Objects.nonNull(storeId)) {
                goodsInfoVO.setStoreId(storeId);
                return goodsInfoExtraService.setGoodsInfo(goodsInfoVO);
            }
            return goodsInfoVO;
        }).collect(Collectors.toList());
        //计算区间价
        GoodsIntervalPriceByCustomerIdResponse priceResponse = goodsIntervalPriceService.getGoodsIntervalPriceVOList
                (goodsInfos, customerId);
        response.setGoodsInfos(priceResponse.getGoodsInfoVOList());
        //获取客户的等级
        if (StringUtils.isNotBlank(customerId)) {
            //计算会员价
            response.setGoodsInfos(
                    marketingLevelPluginProvider.goodsListFilter(MarketingLevelGoodsListFilterRequest.builder()
                            .goodsInfos(goodsInfoMapper.goodsInfoVOsToGoodsInfoDTOs(response.getGoodsInfos()))
                            .customerId(customerId).build())
                            .getContext().getGoodsInfoVOList());
        }
        return GoodsInfoResponse.builder()
                .goodsInfos(response.getGoodsInfos())
                .goodses(response.getGoodses())
                .goodsIntervalPrices(priceResponse.getGoodsIntervalPriceVOList())
                .build();
    }

    private void checkUnauthorized(@PathVariable String tid, TradeVO detail,Boolean checkLeader) {
        // ！（是购买人 && 是分享者 && 团长）
        if (StringUtils.isBlank(detail.getId()) ||
                !(
                        detail.getBuyer().getId().equals(commonUtil.getOperatorId())
                                ||
                        (checkLeader && Objects.nonNull(detail.getCommunityTradeCommission()) &&
                                detail.getCommunityTradeCommission().getCustomerId().equals(commonUtil.getOperatorId()))
                                ||
                        (StringUtils.isNotBlank(detail.getInviteeId()) && detail.getInviteeId().equals(commonUtil.getOperatorId()))
                )

        ) {
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050010, new Object[] {tid});
        }
    }

    /**
     * 1.订单未完成 （订单已支付扒拉了巴拉 显示退货退款按钮-与后台开关设置无关） 2.订单已完成，在截止时间内，且退货开关开启时，前台显示
     * 申请入口（完成时记录订单可退申请的截止时间，如果完成时开关关闭 时间记录完成当时的时间）
     *
     * @param flag
     * @param days
     * @param tradeState
     * @param canReturnFlag
     * @return
     */
    private boolean isCanReturnTime(
            boolean flag, int days, TradeStateVO tradeState, boolean canReturnFlag) {
        if (canReturnFlag && tradeState.getFlowState() == FlowState.COMPLETED) {
            if (flag) {
                if (Objects.nonNull(tradeState.getFinalTime())) {
                    // 是否可退根据订单完成时配置为准
                    flag = tradeState.getFinalTime().isAfter(LocalDateTime.now());
                } else if (Objects.nonNull(tradeState.getEndTime())) {
                    // 容错-历史数据
                    // 判断是否在可退时间范围内
                    LocalDateTime endTime = tradeState.getEndTime();
                    return endTime.plusDays(days).isAfter(LocalDateTime.now());
                }
            } else {
                return false;
            }
            return flag;
        }
        return canReturnFlag;
    }

    /** 验证小店商品，开店礼包 */
    private void validShopGoods(List<TradeItemGroupVO> tradeItemGroups, DistributeChannel channel) {

        DefaultFlag storeBagsFlag = tradeItemGroups.get(0).getStoreBagsFlag();
        if (DefaultFlag.NO.equals(storeBagsFlag)) {
            if (channel.getChannelType() == ChannelType.SHOP) {
                // 1.验证商品是否是小店商品
                List<String> skuIds =
                        tradeItemGroups.stream()
                                .flatMap(i -> i.getTradeItems().stream())
                                .map(TradeItemVO::getSkuId)
                                .collect(Collectors.toList());
                DistributorGoodsInfoVerifyRequest verifyRequest =
                        new DistributorGoodsInfoVerifyRequest();
                verifyRequest.setDistributorId(channel.getInviteeId());
                verifyRequest.setGoodsInfoIds(skuIds);
                List<String> invalidIds =
                        distributorGoodsInfoQueryProvider
                                .verifyDistributorGoodsInfo(verifyRequest)
                                .getContext()
                                .getInvalidIds();
                if (CollectionUtils.isNotEmpty(invalidIds)) {
                    throw new SbcRuntimeException(GoodsErrorCodeEnum.K030151);
                }

                // 2.验证商品对应商家的分销开关有没有关闭
                tradeItemGroups.stream()
                        .flatMap(
                                i ->
                                        i.getTradeItems().stream()
                                                .map(
                                                        item -> {
                                                            item.setStoreId(
                                                                    i.getSupplier().getStoreId());
                                                            return item;
                                                        }))
                        .forEach(
                                item -> {
                                    if (DefaultFlag.NO.equals(
                                            distributionCacheService.queryStoreOpenFlag(
                                                    String.valueOf(item.getStoreId())))) {
                                        throw new SbcRuntimeException(GoodsErrorCodeEnum.K030151);
                                    }
                                });
            }
        } else {
            // 开店礼包商品校验
            RecruitApplyType applyType =
                    distributionCacheService.queryDistributionSetting().getApplyType();
            if (RecruitApplyType.REGISTER.equals(applyType)) {
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030151);
            }
            TradeItemVO tradeItem = tradeItemGroups.get(0).getTradeItems().get(0);
            List<String> goodsInfoIds =
                    distributionCacheService.queryStoreBags().stream()
                            .map(GoodsInfoVO::getGoodsInfoId)
                            .collect(Collectors.toList());
            if (!goodsInfoIds.contains(tradeItem.getSkuId())) {
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030151);
            }
        }
    }

    /**
     * 预约活动校验是否有资格
     *
     * @param tradeItemGroups
     */
    private void validateAppointmentQualification(List<TradeItemGroupVO> tradeItemGroups) {
        Boolean suitMarketingFlag =
                tradeItemGroups.stream()
                        .anyMatch(
                                tradeItemGroupVO ->
                                        Objects.nonNull(tradeItemGroupVO.getSuitMarketingFlag())
                                                && tradeItemGroupVO
                                                        .getSuitMarketingFlag()
                                                        .equals(Boolean.TRUE));
        Boolean isGrouponOrder =
                tradeItemGroups.stream()
                        .anyMatch(
                                tradeItemGroupVO ->
                                        Objects.nonNull(tradeItemGroupVO.getGrouponForm())
                                                && Objects.nonNull(
                                                        tradeItemGroupVO
                                                                .getGrouponForm()
                                                                .getOpenGroupon()));
        if (suitMarketingFlag || isGrouponOrder) {
            return;
        }
        List<String> appointmentSaleSkuIds = new ArrayList<>();
        List<String> allSkuIds = new ArrayList<>();

        tradeItemGroups.forEach(
                tradeItemGroup -> {
                    appointmentSaleSkuIds.addAll(
                            tradeItemGroup.getTradeItems().stream()
                                    .filter(
                                            i ->
                                                    Objects.nonNull(i.getIsAppointmentSaleGoods())
                                                            && i.getIsAppointmentSaleGoods())
                                    .map(TradeItemVO::getSkuId)
                                    .collect(Collectors.toList()));
                    allSkuIds.addAll(
                            tradeItemGroup.getTradeItems().stream()
                                    .filter(
                                            i ->
                                                    Objects.isNull(i.getBuyPoint())
                                                            || i.getBuyPoint() == 0)
                                    .map(TradeItemVO::getSkuId)
                                    .collect(Collectors.toList()));
                });
        if (CollectionUtils.isEmpty(allSkuIds)) {
            return;
        }
        AppointmentSaleInProcessResponse response =
                appointmentSaleQueryProvider
                        .inProgressAppointmentSaleInfoByGoodsInfoIdList(
                                AppointmentSaleInProgressRequest.builder()
                                        .goodsInfoIdList(allSkuIds)
                                        .build())
                        .getContext();
        int purchaseNum = appointmentSaleSkuIds.size();
        int actualNum = 0;
        // 修复sonar检测出的bug
        if (Objects.nonNull(response)
                && CollectionUtils.isNotEmpty(response.getAppointmentSaleVOList())) {
            actualNum = response.getAppointmentSaleVOList().size();
        }

        // 包含预约中商品, 校验不通过
        if (actualNum > purchaseNum) {
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080170);
        }

        // 预约活动失效
        if (purchaseNum > actualNum) {
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050150);
        }

        if (Objects.nonNull(response) && CollectionUtils.isNotEmpty(response.getAppointmentSaleVOList())) {
            response.getAppointmentSaleVOList().forEach(a -> {
                if (!(a.getSnapUpStartTime().isBefore(LocalDateTime.now()) && a.getSnapUpEndTime().isAfter(LocalDateTime.now()))) {
                    throw new SbcRuntimeException(OrderErrorCodeEnum.K050150);
                }
                if (a.getAppointmentType().equals(NumberUtils.INTEGER_ONE)) {
                    // 判断活动是否是全平台客户
                    if (!a.getJoinLevel().equals(Constants.STR_MINUS_1)) {
                        //获取会员在该店铺的等级，自营店铺取平台等级；第三方店铺取店铺等级
                        CustomerLevelByCustomerIdAndStoreIdResponse levelResponse = customerLevelQueryProvider
                                .getCustomerLevelByCustomerIdAndStoreId(CustomerLevelByCustomerIdAndStoreIdRequest.builder().customerId(commonUtil.getOperatorId()).storeId(a.getStoreId()).build())
                                .getContext();
                        if (Objects.nonNull(levelResponse) && Objects.nonNull(levelResponse.getLevelId())) {
                            if (!a.getJoinLevel().equals(Constants.STR_0) && !Arrays.asList(a.getJoinLevel().split(",")).contains(levelResponse.getLevelId().toString())) {
                                throw new SbcRuntimeException(MarketingErrorCodeEnum.K080122);
                            }
                        } else {
                            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080123);
                        }
                    }
                } else {
                    AppointmentRecordResponse recordResponse =
                            appointmentRecordQueryProvider.getAppointmentInfo(AppointmentRecordQueryRequest.builder().
                                    buyerId(commonUtil.getOperatorId())
                                    .goodsInfoId(a.getAppointmentSaleGood().getGoodsInfoId()).appointmentSaleId(a.getId()).build()).getContext();
                    if (Objects.isNull(recordResponse) || Objects.isNull(recordResponse.getAppointmentRecordVO())) {
                        throw new SbcRuntimeException(MarketingErrorCodeEnum.K080134);
                    }
                }
            });
        }
    }

    /**
     * 校验商品限售信息
     *
     * @param tradeItemGroupVO
     * @param customerVO
     */
    private void validateRestrictedGoods(
            TradeItemGroupVO tradeItemGroupVO, CustomerVO customerVO, String addressId) {
        // 组装请求的数据
        List<TradeItemVO> tradeItemVOS = tradeItemGroupVO.getTradeItems();
        List<GoodsRestrictedValidateVO> list =
                tradeItemMapper.tradeItemVOsToGoodsRestrictedValidateVOs(tradeItemVOS);
        // 组合商品不考虑限售限制
        if (Objects.nonNull(tradeItemGroupVO.getSuitMarketingFlag())
                && tradeItemGroupVO.getSuitMarketingFlag()) {
            return;
        }
        Boolean openGroup = Boolean.FALSE;
        if (Objects.nonNull(tradeItemGroupVO.getGrouponForm())
                && Objects.nonNull(tradeItemGroupVO.getGrouponForm().getOpenGroupon())) {
            openGroup = tradeItemGroupVO.getGrouponForm().getOpenGroupon();
        }
        Boolean storeBagsFlag = Boolean.FALSE;
        if (DefaultFlag.YES.equals(tradeItemGroupVO.getStoreBagsFlag())) {
            storeBagsFlag = Boolean.TRUE;
        }
        SupplierVO supplier = tradeItemGroupVO.getSupplier();
        Long storeId = null;
        if (Objects.nonNull(supplier) && StoreType.O2O == supplier.getStoreType()) {
            storeId = supplier.getStoreId();
        }
        goodsRestrictedSaleQueryProvider.validateOrderRestricted(
                GoodsRestrictedBatchValidateRequest.builder()
                        .goodsRestrictedValidateVOS(list)
                        .snapshotType(tradeItemGroupVO.getSnapshotType())
                        .customerVO(customerVO)
                        .openGroupon(openGroup)
                        .storeId(storeId)
                        .storeBagsFlag(storeBagsFlag)
                        .addressId(addressId)
                        .build());
    }



    private boolean isIepCustomer(CustomerVO customerVO) {
        EnterpriseCheckState customerState = customerVO.getEnterpriseCheckState();
        return Objects.nonNull(customerState) && customerState == EnterpriseCheckState.CHECKED
                && commonUtil.findVASBuyOrNot(VASConstants.VAS_IEP_SETTING);
    }

    /**
     * 判断商品是否企业购商品
     *
     * @param enterpriseAuditState
     * @return
     */
    private boolean isEnjoyIepGoodsInfo(EnterpriseAuditState enterpriseAuditState) {
        return !Objects.isNull(enterpriseAuditState)
                && enterpriseAuditState == EnterpriseAuditState.CHECKED;
    }

    @Operation(summary = "订单todo")
    @Parameter(
            name = "inviteeId",
            description = "inviteeId",
            required = true)
    @PostMapping(value = "/todoBySellType/{inviteeId}")
    public BaseResponse<OrderTodoResp> tradeTodoBySellType(
            @PathVariable String inviteeId,
            @RequestBody @Valid QueryTradeTodoRequest paymentRecordRequest) {
        return BaseResponse.success(getTodo(inviteeId, paymentRecordRequest.getSellPlatformType()));
    }

    /** @Description: 订单各状态（待支付、待发货...）下的统计 @Date: 2020/7/16 11:23 */
    @Operation(summary = "订单todo")
    @Parameter(
            name = "inviteeId",
            description = "inviteeId",
            required = true)
    @GetMapping(value = "/todo/{inviteeId}")
    public BaseResponse<OrderTodoResp> TardeTodo(@PathVariable String inviteeId) {
        return BaseResponse.success(getTodo(inviteeId, null));
    }

    private OrderTodoResp getTodo(String inviteeId, SellPlatformType sellPlatformType) {
        OrderTodoResp resp = new OrderTodoResp();
        TradeQueryDTO queryRequest = new TradeQueryDTO();
        queryRequest.setIsBoss(false);
        queryRequest.setBuyerId(commonUtil.getOperatorId());
        queryRequest.setSellPlatformType(sellPlatformType);
        if (StringUtils.isNotBlank(inviteeId) && !"null".equals(inviteeId)) {
            queryRequest.setInviteeId(inviteeId);
        }
        TradeStateDTO tradeState = new TradeStateDTO();

        // 都未发货
        tradeState.setFlowState(FlowState.AUDIT);
        TradeConfigGetByTypeRequest configGetByTypeRequest = new TradeConfigGetByTypeRequest();
        configGetByTypeRequest.setConfigType(ConfigType.ORDER_SETTING_PAYMENT_ORDER);
        TradeConfigGetByTypeResponse context =
                auditQueryProvider.getTradeConfigByType(configGetByTypeRequest).getContext();
        // 不限付款顺序时，未付款同时展示在待付款、待发货下
        // 限制先款后货时，未付款只展示在待付款下
        // 0 不限； 1先款后货
        if (NumberUtils.INTEGER_ONE.equals(context.getStatus())) {
            tradeState.setPayState(PayState.PAID);
        }
        queryRequest.setTradeState(tradeState);
        queryRequest.makeCustomerAllAuditFlow();
        Long noDeliveredCount =
                tradeQueryProvider
                        .countCriteria(
                                TradeCountCriteriaRequest.builder()
                                        .tradePageDTO(queryRequest)
                                        .build())
                        .getContext()
                        .getCount();
        // 部分发货
//        tradeState.setFlowState(FlowState.DELIVERED_PART);
//        queryRequest.setTradeState(tradeState);
//        Long deliveredPartCount =
//                tradeQueryProvider
//                        .countCriteria(
//                                TradeCountCriteriaRequest.builder()
//                                        .tradePageDTO(queryRequest)
//                                        .build())
//                        .getContext()
//                        .getCount();
        resp.setWaitDeliver(noDeliveredCount);
        // 设置待付款订单
        tradeState.setFlowState(null);
        tradeState.setPayState(PayState.NOT_PAID);
        queryRequest.setTradeState(tradeState);
        resp.setWaitPay(
                tradeQueryProvider
                        .countCriteria(
                                TradeCountCriteriaRequest.builder()
                                        .tradePageDTO(queryRequest)
                                        .build())
                        .getContext()
                        .getCount());

        // 设置待收货订单
        tradeState.setPayState(null);
        tradeState.setFlowState(FlowState.DELIVERED);
        queryRequest.setTradeState(tradeState);
        queryRequest.setFlowStates(new ArrayList<>());
        resp.setWaitReceiving(
                tradeQueryProvider
                        .countCriteria(
                                TradeCountCriteriaRequest.builder()
                                        .tradePageDTO(queryRequest)
                                        .build())
                        .getContext()
                        .getCount());

        ReturnCountByConditionRequest returnQueryRequest = new ReturnCountByConditionRequest();
        returnQueryRequest.setBuyerId(commonUtil.getOperatorId());
        Platform platform = commonUtil.getOperator().getPlatform();
        if (Objects.nonNull(platform) && platform == Platform.CUSTOMER) {
            returnQueryRequest.setPlatform(platform);
        }
        if (StringUtils.isNotBlank(inviteeId) && !"null".equals(inviteeId)) {
            returnQueryRequest.setInviteeId(inviteeId);
        }
        // 待审核
        returnQueryRequest.setReturnFlowState(ReturnFlowState.INIT);
        Long waitAudit =
                returnOrderQueryProvider
                        .countByCondition(returnQueryRequest)
                        .getContext()
                        .getCount();
        // 待收货
        returnQueryRequest.setReturnFlowState(ReturnFlowState.DELIVERED);
        Long waitReceiving =
                returnOrderQueryProvider
                        .countByCondition(returnQueryRequest)
                        .getContext()
                        .getCount();
        // 待退款
        returnQueryRequest.setReturnFlowState(ReturnFlowState.RECEIVED);
        Long waitRefund =
                returnOrderQueryProvider
                        .countByCondition(returnQueryRequest)
                        .getContext()
                        .getCount();
        // 待填写物流
        returnQueryRequest.setReturnFlowState(ReturnFlowState.AUDIT);
        Long waitLogistics =
                returnOrderQueryProvider
                        .countByCondition(returnQueryRequest)
                        .getContext()
                        .getCount();

        resp.setRefund(waitAudit + waitReceiving + waitRefund + waitLogistics);

        // 商品待评价
        Long goodsEvaluate =
                goodsTobeEvaluateQueryProvider
                        .getGoodsTobeEvaluateNum(
                                GoodsTobeEvaluateQueryRequest.builder()
                                        .customerId(commonUtil.getOperatorId())
                                        .build())
                        .getContext();
        /*        // 店铺服务待评价
        Long storeEvaluate =
                storeTobeEvaluateQueryProvider.getStoreTobeEvaluateNum(StoreTobeEvaluateQueryRequest.builder()
                .customerId(commonUtil.getOperatorId()).build()).getContext();*/

        //        resp.setWaitEvaluate(goodsEvaluate + storeEvaluate);
        resp.setWaitEvaluate(goodsEvaluate);
        return resp;
    }

    /** 分页查询订单(优化版) */
    @Operation(summary = "分页查询订单")
    @RequestMapping(value = "/pageOptimize", method = RequestMethod.POST)
    public BaseResponse<Page<TradeVO>> pageOptimize(
            @RequestBody TradePageQueryRequest paramRequest) {
        TradeQueryDTO tradeQueryRequest =
                TradeQueryDTO.builder()
                        .tradeState(
                                TradeStateDTO.builder()
                                        .flowState(paramRequest.getFlowState())
                                        .payState(paramRequest.getPayState())
                                        .deliverStatus(paramRequest.getDeliverStatus())
                                        .build())
                        .buyerId(commonUtil.getOperatorId())
                        .inviteeId(paramRequest.getInviteeId())
                        .channelType(paramRequest.getChannelType())
                        .beginTime(paramRequest.getCreatedFrom())
                        .endTime(paramRequest.getCreatedTo())
                        .keyworks(paramRequest.getKeywords())
                        .isBoss(Boolean.FALSE)
                        .goodsType(paramRequest.getGoodsType())
                        .sellPlatformType(paramRequest.getSellPlatformType())
                        .build();
        tradeQueryRequest.setPageNum(paramRequest.getPageNum());
        tradeQueryRequest.setPageSize(paramRequest.getPageSize());
        // 设定状态条件逻辑,已审核状态需筛选出已审核与部分发货
        tradeQueryRequest.makeCustomerAllAuditFlow();

        // 不限付款顺序时，未付款同时展示在待付款、待发货下
        // 限制先款后货时，未付款只展示在待付款下
        // 0 不限； 1先款后货
        if (Objects.isNull(paramRequest.getPayState())
                && paramRequest.getFlowState() == FlowState.AUDIT) {
            TradeConfigGetByTypeRequest configGetByTypeRequest = new TradeConfigGetByTypeRequest();
            configGetByTypeRequest.setConfigType(ConfigType.ORDER_SETTING_PAYMENT_ORDER);
            TradeConfigGetByTypeResponse context =
                    auditQueryProvider.getTradeConfigByType(configGetByTypeRequest).getContext();
            if (NumberUtils.INTEGER_ONE.equals(context.getStatus())) {
                tradeQueryRequest.getTradeState().setPayState(PayState.PAID);
            }
        }
        MicroServicePage<TradeVO> tradePage =
                tradeQueryProvider
                        .pageCriteriaOptimize(
                                TradePageCriteriaRequest.builder()
                                        .tradePageDTO(tradeQueryRequest)
                                        .build())
                        .getContext()
                        .getTradePage();
        List<TradeVO> tradeVOList = tradePage.getContent();

        // 筛选待发货、如果所以商品发起全部退款过滤
        if (Objects.isNull(paramRequest.getPayState())
                && paramRequest.getFlowState() == FlowState.AUDIT) {
            tradeVOList = tradeVOList.stream()
                    .filter(tradeVO -> Boolean.FALSE.equals(tradeVO.getIsAllReturn()))
                    .collect(Collectors.toList());
        }

        // 扭转预售商品支付尾款状态为已作废
        tradeVOList.forEach(this::fillTradeBookingTimeOut);
        tradeVOList.forEach(this::fillVopOrderReturnFlag);
        tradeVOList.forEach(tradeVO -> {
            if(Objects.nonNull(tradeVO.getTradePrice().getGiftCardType()) &&
                    tradeVO.getTradePrice().getGiftCardType() == GiftCardType.PICKUP_CARD){
                tradeVO.getTradePrice().setBuyPoints(0L);
            }
        });
        tradePage.setContent(tradeVOList);
        return BaseResponse.success(tradePage);
    }

    /**
     * 未完全支付的定金预售订单状态填充为已作废状态
     *
     * <p>（主要订单真实作废比较延迟，计时过后仍然处于待支付尾款情况，前端由订单状态判断来控制支付尾款按钮的展示）
     *
     * @param detail 订单
     */
    private void fillTradeBookingTimeOut(TradeVO detail) {
        // 未完全支付的定金预售订单
        if (Boolean.TRUE.equals(detail.getIsBookingSaleGoods())
                && BookingType.EARNEST_MONEY.equals(detail.getBookingType())
                && Objects.nonNull(detail.getTradeState())
                && (!PayState.PAID.equals(detail.getTradeState().getPayState()))) {
            // 尾款时间 < 今天
            if (Objects.nonNull(detail.getTradeState().getTailEndTime())
                    && detail.getTradeState().getTailEndTime().isBefore(LocalDateTime.now())) {
                detail.getTradeState().setFlowState(FlowState.VOID); // 作废
            }
        }
    }

    /**
     * @description 处理京东VOP订单可退货退款状态
     * @author wur
     * @date: 2022/6/15 16:35
     * @param detail
     */
    private void fillVopOrderReturnFlag(TradeVO detail) {
        if (Objects.nonNull(detail.getCanReturnFlag())
                && detail.getCanReturnFlag()
                && CollectionUtils.isNotEmpty(detail.getThirdPlatformTypes())
                && detail.getThirdPlatformTypes().contains(ThirdPlatformType.VOP)) {
            detail.setCanReturnFlag(Boolean.FALSE);
        }
    }

    /** 通过tid获取支付成功广告 */
    @Operation(summary = "通过tid获取支付成功广告")
    @Parameter(
            name = "tid",
            description = "订单ID",
            required = true)
    @RequestMapping(value = "/payadvertisement/{tid}", method = RequestMethod.GET)
    public BaseResponse<PayAdvertisementResponse> getPayAdvertisementByTid(
            @PathVariable String tid) {
        PayAdvertisementResponse response = new PayAdvertisementResponse();
        TradeVO trade =
                tradeQueryProvider
                        .getById(TradeGetByIdRequest.builder().tid(tid).build())
                        .getContext()
                        .getTradeVO();
        // 线下订单不展示支付广告
        if (!PayType.OFFLINE.name().equals(trade.getPayInfo().getPayTypeName())) {
            // 已支付的展示广告
            PayAdvertisementPageRequest pageReq = new PayAdvertisementPageRequest();
            pageReq.setQueryTab(Constants.ONE);
            pageReq.setDelFlag(DeleteFlag.NO);
            pageReq.putSort("createTime", "asc");
            BaseResponse<PayAdvertisementPageResponse> pageResponseBaseResponse =
                    payAdvertisementQueryProvider.page(pageReq);
            // 获取进行中的广告数据
            List<PayAdvertisementVO> page =
                    pageResponseBaseResponse.getContext().getPayAdvertisementVOPage().getContent();
            if (CollectionUtils.isNotEmpty(page)) {
                for (PayAdvertisementVO payAdvertisementVO : page) {
                    if (validateShowAd(trade, payAdvertisementVO)) {
                        response.setPayAdvertisementVO(payAdvertisementVO);
                        break;
                    }
                }
            }
        }
        return BaseResponse.success(response);
    }

    /** 通过pid获取支付成功广告 */
    @Operation(summary = "通过pid获取支付成功广告")
    @Parameter(
            name = "pid",
            description = "订单ID",
            required = true)
    @RequestMapping(value = "/payadvertisements/{pid}", method = RequestMethod.GET)
    public BaseResponse<PayAdvertisementResponse> getPayAdvertisementByPid(
            @PathVariable String pid) {
        PayAdvertisementResponse response = new PayAdvertisementResponse();
        List<TradeVO> trades =
                tradeQueryProvider
                        .getOrderListByParentId(
                                TradeListByParentIdRequest.builder().parentTid(pid).build())
                        .getContext()
                        .getTradeVOList();
        // 线下订单不展示支付广告
        if (!PayType.OFFLINE.name().equals(trades.get(0).getPayInfo().getPayTypeName())) {
            // 已支付的展示广告
            PayAdvertisementPageRequest pageReq = new PayAdvertisementPageRequest();
            pageReq.setQueryTab(Constants.ONE);
            pageReq.setDelFlag(DeleteFlag.NO);
            pageReq.putSort("createTime", "desc");
            BaseResponse<PayAdvertisementPageResponse> pageResponseBaseResponse =
                    payAdvertisementQueryProvider.page(pageReq);
            // 获取进行中的广告数据
            List<PayAdvertisementVO> page =
                    pageResponseBaseResponse.getContext().getPayAdvertisementVOPage().getContent();
            if (CollectionUtils.isNotEmpty(page)) {
                List<PayAdvertisementVO> payAdvertisementVOS = Lists.newArrayList();
                for (PayAdvertisementVO payAdvertisementVO : page) {
                    if (validateShowAd(trades, payAdvertisementVO)) {
                        payAdvertisementVOS.add(payAdvertisementVO);
                    }
                }
                response.setPayAdvertisementVOS(payAdvertisementVOS);
            }
        }
        return BaseResponse.success(response);
    }
    /** 用于确认订单后，获取订单中可用优惠券 */
    @Operation(summary = "用于确认订单后，创建订单前的获取订单商品信息")
    @RequestMapping(value = "/purchase/coupons/list", method = RequestMethod.POST)
    public BaseResponse<PurchaseCouponCodeListResponse> queryPageCouponCodesForOrder(
            @RequestBody PurchaseCouponCodeListRequest request) {
        Long storeId;
        String operatorId = commonUtil.getOperatorId();
        List<TradeItemInfoDTO> tradeDtos;
        // 券排序map，用于列表券顺序和快照中的店铺顺序，保持一致
        Map<Long, Long> storeIdSortMap = new HashMap<>();
        // 平台券，默认排到最后展示
        storeIdSortMap.put(Constants.BOSS_DEFAULT_STORE_ID, Constants.NUM_9999L);
        if (request.getBookingSaleFlag() && StringUtils.isNotEmpty(request.getTid())) {
            // 定金预售，待支付尾款阶段，订单快照不存在，根据入参查询订单详情
            TradeVO tradeVO = tradeQueryProvider.getById(TradeGetByIdRequest.builder().tid(request.getTid()).build()).getContext().getTradeVO();
            if (Objects.isNull(tradeVO.getId()) || !tradeVO.getIsBookingSaleGoods()) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
            TradeItemVO tradeItem = tradeVO.getTradeItems().get(0);
            String goodsInfoId = tradeItem.getSkuId();
            EsGoodsInfoQueryRequest goodsInfoQueryRequest = new EsGoodsInfoQueryRequest();
            goodsInfoQueryRequest.setGoodsInfoIds(Collections.singletonList(goodsInfoId));
            List<EsGoodsInfoVO> goodsInfos = esGoodsInfoElasticQueryProvider.skuPage(goodsInfoQueryRequest).getContext().getEsGoodsInfoPage().getContent();
            if (CollectionUtils.isEmpty(goodsInfos)) {
                // 商品不存在直接返回
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030035);
            }
            EsGoodsInfoVO esGoodsInfoVO = goodsInfos.get(0);
            GoodsInfoNestVO goodsInfo = esGoodsInfoVO.getGoodsInfo();
            List<TradeItemInfoDTO> tradeItems = new ArrayList<>();
            // 尾款 - 物流费用
            BigDecimal deliveryPrice = Objects.nonNull(tradeVO.getTradePrice().getDeliveryPrice()) ? tradeVO.getTradePrice().getDeliveryPrice() : BigDecimal.ZERO;
            BigDecimal price = tradeVO.getTradePrice().getTailPrice().subtract(deliveryPrice);

            tradeItems.add(TradeItemInfoDTO.builder()
                    .skuId(tradeItem.getSkuId())
                    .price(price)
                    // 填充其他商品信息
                    .spuId(goodsInfo.getGoodsId())
                    .storeId(goodsInfo.getStoreId())
                    .brandId(goodsInfo.getBrandId())
                    .cateId(goodsInfo.getCateId())
                    .storeCateIds(esGoodsInfoVO.getStoreCateIds()).build());
            storeId = goodsInfo.getStoreId();
            tradeDtos = tradeItems;
            // 定金预售采购单价格直接赋值尾款金额
            request.setPurchasePrice(tradeVO.getTradePrice().getTailPrice());
        } else {
            // 普通订单确认页，直接从快照中取
            TradeItemSnapshotVO tradeItemSnapshotVO =
                    tradeItemQueryProvider
                            .listByTerminalToken(
                                    TradeItemSnapshotByCustomerIdRequest.builder()
                                            .terminalToken(commonUtil.getTerminalToken())
                                            .build())
                            .getContext()
                            .getTradeItemSnapshotVO();

            // 构造券排序map，[storeId] => [sortNo]，值越小，越靠前
            if (CollectionUtils.isNotEmpty(tradeItemSnapshotVO.getItemGroups())) {
                List<TradeItemGroupVO> itemGroups = tradeItemSnapshotVO.getItemGroups();
                for (int i = 0; i < itemGroups.size(); i++) {
                    TradeItemGroupVO tradeItemGroupVO = itemGroups.get(i);
                    if (Objects.nonNull(tradeItemGroupVO.getSupplier())) {
                        // sortNo为快照中店铺的下标
                        storeIdSortMap.put(tradeItemGroupVO.getSupplier().getStoreId(), (long) i);
                    }
                }
            }

            //如果是砍价订单验证是否开通优惠券叠加
            if(Objects.equals(Boolean.TRUE, tradeItemSnapshotVO.getBargain())) {
                // 查询优惠券叠加开关是否打开
                ConfigQueryRequest configQueryRequest = new ConfigQueryRequest();
                configQueryRequest.setConfigType(ConfigType.BARGIN_USE_COUPON.toValue());
                configQueryRequest.setDelFlag(DeleteFlag.NO.toValue());
                SystemConfigTypeResponse response =
                        systemConfigQueryProvider
                                .findByConfigTypeAndDelFlag(configQueryRequest)
                                .getContext();
                if (Objects.nonNull(response.getConfig())
                        && Objects.equals(DefaultFlag.NO.toValue(), response.getConfig().getStatus())) {
                    return BaseResponse.SUCCESSFUL();
                }
            }
            List<TradeItemGroupVO> items = tradeItemSnapshotVO.getItemGroups();
            storeId = items.get(0).getSupplier().getStoreId();
            // 根据确认订单计算出的信息，查询出使用优惠券页需要的优惠券列表数据
            DefaultFlag openFlag = distributionCacheService.queryOpenFlag();

            Map<String, TradeItemInfoDTO> skuToTradeItemInfoDTOMap =
                    items.stream()
                            .flatMap(
                                    confirmItem ->
                                            confirmItem.getTradeItems().stream()
                                                    .map(
                                                            tradeItem -> {
                                                                TradeItemInfoDTO dto =
                                                                        new TradeItemInfoDTO();
                                                                dto.setBrandId(tradeItem.getBrand());
                                                                dto.setCateId(tradeItem.getCateId());
                                                                dto.setSpuId(tradeItem.getSpuId());
                                                                dto.setSkuId(tradeItem.getSkuId());
                                                                dto.setStoreId(
                                                                        confirmItem
                                                                                .getSupplier()
                                                                                .getStoreId());
                                                                dto.setPrice(tradeItem.getSplitPrice());
                                                                dto.setDistributionGoodsAudit(
                                                                        tradeItem
                                                                                .getDistributionGoodsAudit());
                                                                if (DefaultFlag.NO.equals(openFlag)
                                                                        || DefaultFlag.NO.equals(
                                                                        distributionCacheService
                                                                                .queryStoreOpenFlag(
                                                                                        String
                                                                                                .valueOf(
                                                                                                        tradeItem
                                                                                                                .getStoreId())))) {
                                                                    tradeItem.setDistributionGoodsAudit(
                                                                            DistributionGoodsAudit
                                                                                    .COMMON_GOODS);
                                                                }
                                                                return dto;
                                                            }))
                            .collect(Collectors.toMap(TradeItemInfoDTO::getSkuId, Function.identity()));

//            List<TradeItemMarketingVO> preferentialMarketingList =
//                    items.stream().map(TradeItemGroupVO::getTradeMarketingList).flatMap(Collection::stream).
//                    filter(g -> CollectionUtils.isNotEmpty(g.getPreferentialSkuIds())).collect(Collectors.toList());

            List<TradeItemMarketingVO> preferentialMarketingList = new ArrayList<>();
            items.forEach(item -> {
                if(CollectionUtils.isNotEmpty(item.getTradeMarketingList())){
                    item.getTradeMarketingList().forEach(tradeItemMarketingVO -> {
                        if(Objects.nonNull(tradeItemMarketingVO)){
                            if(CollectionUtils.isNotEmpty(tradeItemMarketingVO.getPreferentialSkuIds())){
                                preferentialMarketingList.add(tradeItemMarketingVO);
                            }
                        }
                    });
                }
            });
            List<TradeItemInfoDTO> tradeItemInfoDTOS = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(preferentialMarketingList)) {
                List<Long> marketingIds =
                        preferentialMarketingList.stream()
                                .map(TradeItemMarketingVO::getMarketingId)
                                .collect(Collectors.toList());
                List<Long> marketingLevelIds =
                        preferentialMarketingList.stream()
                                .map(TradeItemMarketingVO::getMarketingLevelId)
                                .collect(Collectors.toList());
                Map<String, BigDecimal> skuIdToPreferentialPriceMap = new HashMap<>();
                if (CollectionUtils.isNotEmpty(marketingIds)
                        && CollectionUtils.isNotEmpty(marketingLevelIds)) {

                    //根据营销id查询所有加价购活动等级（多个活动的多个等级）
                    List<MarketingPreferentialGoodsDetailVO> voList =
                            preferentialQueryProvider
                                    .listDetailByMarketingIds(
                                            DetailByMIdsAndLIdsRequest.builder()
                                                    .marketingIds(marketingIds)
                                                    .build())
                                    .getContext()
                                    .getPreferentialGoodsDetailVOS();
                    List<MarketingPreferentialLevelVO> leveList =
                            preferentialQueryProvider
                                    .listLeveByMarketingIds(
                                            LeveByMIdsRequest.builder()
                                                    .marketingIds(marketingIds)
                                                    .build())
                                    .getContext()
                                    .getLevelVOList();

                    //按照活动id分组
                    Map<Long,List<MarketingPreferentialGoodsDetailVO>> mvoList =
                            voList.stream().collect(Collectors.groupingBy(MarketingPreferentialGoodsDetailVO::getMarketingId));

                    Map<Long,List<MarketingPreferentialLevelVO>> mLeveList =
                            leveList.stream().collect(Collectors.groupingBy(MarketingPreferentialLevelVO::getMarketingId));


                    List<Long> allMarketingLevelIds = new ArrayList<>();
                    mvoList.forEach((key,values) -> {
                        if(CollectionUtils.isNotEmpty(values)){

                            //取出当前满足的活动等级信息
                            List<MarketingPreferentialGoodsDetailVO> detailVOs
                                    = values.stream().filter(v -> marketingLevelIds.contains(v.getPreferentialLevelId()))
                                    .collect(Collectors.toList());
                            //获取活动的等级
                            List<MarketingPreferentialLevelVO> levelVOList = mLeveList.get(key);
                            if (CollectionUtils.isNotEmpty(levelVOList)) {
                                // 获取快照中满足的等级-最大等级
                                Optional<MarketingPreferentialLevelVO> optional = levelVOList.stream().filter(le-> le.getPreferentialLevelId().compareTo(detailVOs.get(0).getPreferentialLevelId()) == 0).findFirst();
                                if (optional.isPresent()) {
                                    MarketingPreferentialLevelVO levelVO = optional.get();
                                    List<MarketingPreferentialLevelVO> findLevelVOList = new ArrayList<>();
                                    if (Objects.nonNull(levelVO.getFullCount())) {
                                        findLevelVOList = levelVOList.stream().filter(le-> le.getFullCount().compareTo(levelVO.getFullCount()) <= 0).collect(Collectors.toList());
                                    } else if (Objects.nonNull(levelVO.getFullAmount())) {
                                        findLevelVOList = levelVOList.stream().filter(le-> le.getFullAmount().compareTo(levelVO.getFullAmount()) <= 0).collect(Collectors.toList());
                                    }
                                    if (CollectionUtils.isNotEmpty(findLevelVOList)) {
                                        allMarketingLevelIds.addAll(findLevelVOList.stream().map(MarketingPreferentialLevelVO::getPreferentialLevelId).collect(Collectors.toList()));
                                    }
                                }
                            }
                        }
                    });

                    //先过滤满足条件的活动等级 然后按照商品分组 活动价格累加（未考虑到数量 换购商品单个商品数量为1）
                    skuIdToPreferentialPriceMap =
                            voList.stream()
                                    .filter(vo -> allMarketingLevelIds.contains(vo.getPreferentialLevelId()))
                                    .collect(Collectors.toMap(MarketingPreferentialGoodsDetailVO::getGoodsInfoId,
                                    MarketingPreferentialGoodsDetailVO::getPreferentialAmount,
                                    BigDecimal::add));
                }


                List<String> goodsInfoIds =
                        preferentialMarketingList.stream().map(TradeItemMarketingVO::getPreferentialSkuIds)
                                .flatMap(Collection::stream).distinct().collect(Collectors.toList());
                EsGoodsInfoQueryRequest goodsInfoQueryRequest = new EsGoodsInfoQueryRequest();
                goodsInfoQueryRequest.setGoodsInfoIds(goodsInfoIds);
                goodsInfoQueryRequest.setPageSize(goodsInfoIds.size());
                List<EsGoodsInfoVO> goodsInfos = esGoodsInfoElasticQueryProvider.skuPage(goodsInfoQueryRequest)
                        .getContext().getEsGoodsInfoPage().getContent();
                if (!Objects.equals(goodsInfoIds.size(), goodsInfos.size())){
                    throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
                }

                Map<String, BigDecimal> finalSkuIdToPreferentialPriceMap = skuIdToPreferentialPriceMap;
                goodsInfos.forEach((esGoodsInfoVO -> {
                    GoodsInfoNestVO goodsInfoNestVO = esGoodsInfoVO.getGoodsInfo();
                    TradeItemInfoDTO dto =
                            new TradeItemInfoDTO();
                    dto.setBrandId(goodsInfoNestVO.getBrandId());
                    dto.setCateId(goodsInfoNestVO.getCateId());
                    dto.setSpuId(goodsInfoNestVO.getGoodsId());
                    dto.setSkuId(goodsInfoNestVO.getGoodsInfoId());
                    dto.setStoreId(goodsInfoNestVO.getStoreId());
                    dto.setPrice(finalSkuIdToPreferentialPriceMap.get(goodsInfoNestVO.getGoodsInfoId()));
                    dto.setDistributionGoodsAudit(DistributionGoodsAudit.COMMON_GOODS);
                    tradeItemInfoDTOS.add(dto);
                }));
            }

            tradeItemInfoDTOS.forEach(dto -> {
                TradeItemInfoDTO tradeItemInfoDTO = skuToTradeItemInfoDTOMap.get(dto.getSkuId());
                if (Objects.isNull(tradeItemInfoDTO)){
                    skuToTradeItemInfoDTOMap.put(dto.getSkuId(), dto);
                } else {
                    tradeItemInfoDTO.setPrice(tradeItemInfoDTO.getPrice().add(dto.getPrice()));
                }
            });
            tradeDtos = new ArrayList<>(skuToTradeItemInfoDTOMap.values());
        }

        CouponCodeListForUseByCustomerIdRequest requ =
                CouponCodeListForUseByCustomerIdRequest.builder()
                        .customerId(operatorId)
                        .terminalToken(commonUtil.getTerminalToken())
                        .tradeItems(tradeDtos)
                        .price(request.getPurchasePrice())
                        .storeId(storeId)
                        .selectedCouponCodeIds(request.getSelectedCouponCodeIds())
                        .storeFreights(request.getStoreFreights())
                        .build();

        List<CouponCodeVO> couponCodeList =
                couponCodeQueryProvider
                        .listForUseByCustomerId(requ)
                        .getContext()
                        .getCouponCodeList();

        // 根据C端优惠券列表类型，筛选优惠券
        Stream<CouponCodeVO> couponFilterStream = null;
        // 移动端使用 couponListType 筛选，优惠券列表类型，0满系券（满减、满折） 1运费券
        if (Objects.nonNull(request.getCouponListType())) {
            couponFilterStream = couponCodeList.stream().filter(item -> item.isCouponListType(request.getCouponListType()));
        }
        // PC端使用 queryCouponType 筛选，优惠券筛选类型 0：通用满减券 1：店铺满减券 2：店铺满折券 3：店铺运费券
        if (Objects.nonNull(request.getQueryCouponType())) {
            couponFilterStream = couponCodeList.stream().filter(item -> item.isQueryCouponType(request.getQueryCouponType()));
        }
        // filterStream 非空，执行真正的 toList
        if (Objects.nonNull(couponFilterStream)) {
            couponCodeList = couponFilterStream.collect(Collectors.toList());
        }

        PurchaseCouponCodeListResponse response = new PurchaseCouponCodeListResponse();
        if (CollectionUtils.isNotEmpty(couponCodeList)) {
            List<CouponCodeVO> canUse =
                    couponCodeList.stream()
                            .filter(
                                    couponCodeVO ->
                                            CouponCodeStatus.AVAILABLE.equals(
                                                    couponCodeVO.getStatus()))
                            .collect(Collectors.toList());
            List<CouponCodeVO> notCanUse =
                    couponCodeList.stream()
                            .filter(
                                    couponCodeVO ->
                                            !CouponCodeStatus.AVAILABLE.equals(
                                                    couponCodeVO.getStatus()))
                            .collect(Collectors.toList());
            response.setAvailableCount(canUse.size());
            response.setNoAvailableCount(notCanUse.size());

            // 券排序，按照快照中的店铺顺序
            if (MapUtils.isNotEmpty(storeIdSortMap)) {
                // 根据storeId对券的排序方法
                Comparator<CouponCodeVO> couponStoreIdComparator = Comparator.comparing(CouponCodeVO::getStoreId, (s1, s2) -> {
                    Long s1SortNo = storeIdSortMap.getOrDefault(s1, 0L);
                    Long s2SortNo = storeIdSortMap.getOrDefault(s2, 0L);
                    return s1SortNo.compareTo(s2SortNo);
                });
                // 对可用券进行排序
                canUse = canUse.stream().sorted(couponStoreIdComparator).collect(Collectors.toList());
                // 对不可用券进行排序
                notCanUse = notCanUse.stream().sorted(couponStoreIdComparator).collect(Collectors.toList());
            }

            if (DefaultFlag.YES.equals(request.getUseStatus())) {
                response.setCouponCodeList(canUse);

            } else {
                response.setCouponCodeList(notCanUse);
            }
        }
        return BaseResponse.success(response);
    }

    private boolean validateShowAd(List<TradeVO> trades, PayAdvertisementVO payAdvertisementVO) {
        // 店铺判断
        if (Objects.nonNull(payAdvertisementVO.getStoreType())) {
            // 全部店铺
            if (Constants.ONE == payAdvertisementVO.getStoreType()) {
                return dealShowAd(trades, payAdvertisementVO);
            } else {
                // 获取配置店铺
                List<PayAdvertisementStoreVO> payAdvertisementStore =
                        payAdvertisementVO.getPayAdvertisementStore();
                List<Long> storeIds =
                        payAdvertisementStore.stream()
                                .map(PayAdvertisementStoreVO::getStoreId)
                                .filter(Objects::nonNull)
                                .distinct()
                                .collect(Collectors.toList());
                // 过滤不满足配置店铺的订单
                List<TradeVO> filterTrade =
                        trades.stream()
                                .filter(
                                        trade ->
                                                storeIds.contains(trade.getSupplier().getStoreId()))
                                .collect(Collectors.toList());
                if (CollectionUtils.isEmpty(filterTrade)) {
                    return false;
                }
                return dealShowAd(filterTrade, payAdvertisementVO);
            }
        }
        return false;
    }

    private boolean dealShowAd(List<TradeVO> trades, PayAdvertisementVO payAdvertisementVO) {
        BigDecimal totalPrice = BigDecimal.ZERO;
        // 全部店铺金额相加
        for (TradeVO trade : trades) {
            BigDecimal total =
                    Objects.isNull(trade.getTradePrice().getTotalPrice())
                            ? BigDecimal.ZERO
                            : trade.getTradePrice().getTotalPrice();
            // 定金预售按付款价格计算
            if (trade.getIsBookingSaleGoods() != null
                    && trade.getIsBookingSaleGoods()
                    && trade.getBookingType() != null
                    && trade.getBookingType().equals(BookingType.EARNEST_MONEY)) {
                if (trade.getTradeState().getPayState() == PayState.PAID_EARNEST
                        && trade.getTradeState().getFlowState() == FlowState.WAIT_PAY_TAIL) {
                    total = BigDecimal.ZERO;
                }
            }
            totalPrice = totalPrice.add(total);
        }
        // 订单实付金额判断
        if (totalPrice.compareTo(payAdvertisementVO.getOrderPrice()) >= 0) {
            // 不限等级
            if (Constants.STR_0.equals(payAdvertisementVO.getJoinLevel())) {
                return true;
            }
            // 店铺列表
            List<Long> storeIds = Lists.newArrayList();
            for (TradeVO trade : trades) {
                storeIds.add(trade.getSupplier().getStoreId());
            }
            // 目标客户判断
            CustomerLevelMapByCustomerIdAndStoreIdsRequest customerLevelMapRequest =
                    new CustomerLevelMapByCustomerIdAndStoreIdsRequest();
            customerLevelMapRequest.setCustomerId(trades.get(0).getBuyer().getId());
            customerLevelMapRequest.setStoreIds(storeIds);
            // 根据会员ID查询平台会员等级的Map结果
            Map<Long, CommonLevelVO> storeLevelMap =
                    customerLevelQueryProvider
                            .listCustomerLevelMapByCustomerId(customerLevelMapRequest)
                            .getContext()
                            .getCommonLevelVOMap();
            CommonLevelVO level = storeLevelMap.get(Constants.BOSS_DEFAULT_STORE_ID);
            if (Objects.nonNull(level)) {
                if (Arrays.asList(payAdvertisementVO.getJoinLevel().split(","))
                        .contains(String.valueOf(level.getLevelId()))) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    @Operation(summary = "分页查询省钱订单")
    @RequestMapping(value = "/economicalPage", method = RequestMethod.POST)
    public BaseResponse<TradeEconomicalPageResponse> economicalPage(
            @RequestBody @Valid TradeEconomicalPageRequest paramRequest) {
        // 等级优惠金额
        BigDecimal totalDiscount =
                payingMemberCustomerRelQueryProvider
                        .findDiscountByLevelId(
                                PayingMemberQueryDiscountRequest.builder()
                                        .customerId(commonUtil.getOperatorId())
                                        .levelId(paramRequest.getLevelId())
                                        .build())
                        .getContext()
                        .getTotalDiscount();
        // 优惠订单分页数据
        TradeQueryDTO tradeQueryDTO =
                TradeQueryDTO.builder()
                        .tradeState(TradeStateDTO.builder().payState(PayState.PAID).build())
                        .notFlowStates(Lists.newArrayList(FlowState.VOID))
                        .buyerId(commonUtil.getOperatorId())
                        .payMemberLevelId(paramRequest.getLevelId())
                        .isBoss(Boolean.FALSE)
                        .build();
        tradeQueryDTO.setPageSize(paramRequest.getPageSize());
        tradeQueryDTO.setPageNum(paramRequest.getPageNum());
        paramRequest.setTradePageDTO(tradeQueryDTO);
        MicroServicePage<TradeVO> tradePage =
                tradeQueryProvider.economicalPage(paramRequest).getContext().getTradePage();

        return BaseResponse.success(
                TradeEconomicalPageResponse.builder()
                        .tradePage(tradePage)
                        .totalDiscount(totalDiscount)
                        .build());
    }

    /** 用户关闭订单倒计时 */
    @Operation(summary = "用户关闭订单倒计时")
    @RequestMapping(value = "/countdown/close", method = RequestMethod.POST)
    public BaseResponse countDownClose() {
        // 用户id
        String operatorId = commonUtil.getOperatorId();
        Assert.assertNotNull(operatorId, CommonErrorCodeEnum.K000001);
        // 保存关闭订单倒计时标识
        redisService.setString(
                CacheKeyConstant.CUSTOMER_COUNTDOWN_KEY.concat(operatorId),
                Boolean.FALSE.toString(),
                60 * 60 * 24);
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * @description 用户订单倒计时查询 @Author qiyuanzhao @Date 2022/6/8 13:48
     * @param isFrontPage 0 是首页; 1是个人中心
     * @return
     */
    @Operation(summary = "用户订单倒计时查询")
    @RequestMapping(value = "/countdown/{isFrontPage}", method = RequestMethod.GET)
    public BaseResponse<TradeListAllResponse> countDown(
            @PathVariable("isFrontPage") Integer isFrontPage) {

        String operatorId = commonUtil.getOperatorId();
        Assert.assertNotNull(operatorId, CommonErrorCodeEnum.K000001);

        if (isFrontPage != Constants.ZERO && isFrontPage != Constants.ONE) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        // 如果是首页
        if (isFrontPage == Constants.ZERO) {
            // 判断用户是否关闭首页倒计时
            String string =
                    redisService.getString(
                            CacheKeyConstant.CUSTOMER_COUNTDOWN_KEY.concat(operatorId));
            if (StringUtils.isNotBlank(string)) {
                return BaseResponse.success(
                        TradeListAllResponse.builder().tradeVOList(Lists.newArrayList()).build());
            }
        }

        ValidateTradeAndAccountRequest request =
                ValidateTradeAndAccountRequest.builder().buyerId(operatorId).build();
        // 判断是否查询已审核且未支付订单的条件
        return tradeQueryProvider.countDown(request);
    }

    /** 查询周期购计划详情 */
    @Operation(summary = "查询周期购计划详情")
    @Parameter(
            name = "tid",
            description = "订单ID",
            required = true)
    @RequestMapping(value = "/buyCyclePlan/{tid}", method = RequestMethod.GET)
    public BaseResponse<List<CycleDeliveryPlanVO>> buyCyclePlan(@PathVariable String tid) {
        TradeGetByIdResponse tradeGetByIdResponse =
                tradeQueryProvider
                        .getById(
                                TradeGetByIdRequest.builder()
                                        .tid(tid)
                                        .needLmOrder(Boolean.TRUE)
                                        .build())
                        .getContext();
        if (tradeGetByIdResponse == null || tradeGetByIdResponse.getTradeVO() == null) {
            return BaseResponse.success(null);
        }
        TradeVO detail = tradeGetByIdResponse.getTradeVO();
        TradeBuyCycleVO tradeBuyCycle = detail.getTradeBuyCycle();
        if (Objects.isNull(tradeBuyCycle)) {
            return BaseResponse.success(null);
        }
        List<CycleDeliveryPlanVO> deliveryPlanS = tradeBuyCycle.getDeliveryPlanS();
        return BaseResponse.success(deliveryPlanS);
    }

    @Operation(summary = "查询重复支付的记录")
    @Parameter(
            name = "tid",
            description = "订单ID",
            required = true)
    @RequestMapping(value = "/duplicate/pay/{tid}", method = RequestMethod.GET)
    public BaseResponse<List<PayTimeSeriesVO>> duplicatePay(@PathVariable String tid) {
        TradeGetByIdResponse response = tradeQueryProvider.getById(TradeGetByIdRequest.builder().tid(tid).build()).getContext();
        if(response==null){
            //订单不存在
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050153);
        }
        TradeVO  tradeVO = response.getTradeVO();
        if(!commonUtil.getOperatorId().equals(tradeVO.getBuyer().getId())){
            //非法越权操作
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030130);
        }
        List<PayTimeSeriesVO> resultList = new ArrayList<>();
        // 先查询订单id
        List<PayTimeSeriesVO> tList =
                payTimeSeriesQueryProvider
                        .getDuplicatePay(
                                PayTimeSeriesByBusinessIdRequest.builder().businessId(tid).build())
                        .getContext()
                        .getPayTimeSeriesVOList();
        if(CollectionUtils.isNotEmpty(tList)){
            resultList.addAll(tList);
        }
        //再查父单
        List<PayTimeSeriesVO> pList =
                payTimeSeriesQueryProvider
                        .getDuplicatePay(
                                PayTimeSeriesByBusinessIdRequest.builder().businessId(tradeVO.getParentId()).build())
                        .getContext()
                        .getPayTimeSeriesVOList();
        if(CollectionUtils.isNotEmpty(pList)){
            resultList.addAll(pList);
        }
        //是否是尾款
        if(tradeVO.getTailOrderNo()!=null){
            List<PayTimeSeriesVO> tailList =
                    payTimeSeriesQueryProvider
                            .getDuplicatePay(
                                    PayTimeSeriesByBusinessIdRequest.builder().businessId(tradeVO.getTradeNo()).build())
                            .getContext()
                            .getPayTimeSeriesVOList();
            if(CollectionUtils.isNotEmpty(tailList)){
                resultList.addAll(tailList);
            }
        }
        return BaseResponse.success(resultList);
    }

    @Operation(summary = "买家修改收货地址")
    @RequestMapping(value = "/modifyConsigneeByBuyer", method = RequestMethod.PUT)
    public BaseResponse modifyConsigneeByBuyer(@RequestBody @Valid ModifyConsigneeByBuyerRequest request) {
        // 构造买家修改收货地址请求
        TradeConsigneeModifyByBuyerRequest modifyByBuyerRequest = new TradeConsigneeModifyByBuyerRequest();
        modifyByBuyerRequest.setTid(request.getTid());
        modifyByBuyerRequest.setDeliveryAddressId(request.getDeliveryAddressId());
        modifyByBuyerRequest.setCustomerId(commonUtil.getOperatorId());
        modifyByBuyerRequest.setOperator(commonUtil.getOperator());
        tradeProvider.modifyConsigneeByBuyer(modifyByBuyerRequest);
        return BaseResponse.SUCCESSFUL();
    }

    @Operation(summary = "分页查询物流单号")
    @PostMapping(value = "/wxOrderShippingStatus")
    public BaseResponse<WxPayShippingOrderStatusResponse> getWxOrderShipping(@RequestBody WxPayShippingOrderStatusRequest request) {
        return wxPayUploadShippingInfoQueryProvider.getOrderStatus(request);
    }
}
