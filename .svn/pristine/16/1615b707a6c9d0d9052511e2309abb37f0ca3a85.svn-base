package com.wanmi.sbc.open;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSONObject;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.constant.RedisKeyConstant;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.OperateType;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.growthvalue.CustomerGrowthValueProvider;
import com.wanmi.sbc.customer.api.provider.loginregister.CustomerSiteProvider;
import com.wanmi.sbc.customer.api.provider.loginregister.CustomerSiteQueryProvider;
import com.wanmi.sbc.customer.api.provider.payingmembercustomerrel.PayingMemberCustomerRelProvider;
import com.wanmi.sbc.customer.api.provider.points.CustomerPointsDetailSaveProvider;
import com.wanmi.sbc.customer.api.request.growthvalue.CustomerGrowthValueAddRequest;
import com.wanmi.sbc.customer.api.request.loginregister.CustomerByAccountRequest;
import com.wanmi.sbc.customer.api.request.loginregister.CustomerRegisterRequest;
import com.wanmi.sbc.customer.api.request.payingmembercustomerrel.PayingMemberAssignRequest;
import com.wanmi.sbc.customer.api.request.points.CustomerPointsDetailAddRequest;
import com.wanmi.sbc.customer.api.response.loginregister.CustomerByAccountResponse;
import com.wanmi.sbc.customer.api.response.loginregister.CustomerRegisterResponse;
import com.wanmi.sbc.customer.bean.dto.CustomerDTO;
import com.wanmi.sbc.customer.bean.enums.GrowthValueServiceType;
import com.wanmi.sbc.customer.bean.enums.PointsServiceType;
import com.wanmi.sbc.customer.bean.vo.CustomerDetailVO;
import com.wanmi.sbc.customer.bean.vo.CustomerVO;
import com.wanmi.sbc.elastic.api.provider.coupon.EsCouponActivityProvider;
import com.wanmi.sbc.elastic.api.request.coupon.EsCouponActivityAddRequest;
import com.wanmi.sbc.elastic.bean.dto.coupon.EsCouponActivityDTO;
import com.wanmi.sbc.elastic.bean.dto.customer.EsCustomerDetailDTO;
import com.wanmi.sbc.elastic.bean.dto.customer.EsEnterpriseInfoDTO;
import com.wanmi.sbc.marketing.api.provider.coupon.CouponActivityProvider;
import com.wanmi.sbc.marketing.api.provider.coupon.CouponCodeProvider;
import com.wanmi.sbc.marketing.api.provider.coupon.CouponCodeQueryProvider;
import com.wanmi.sbc.marketing.api.provider.coupon.CouponInfoQueryProvider;
import com.wanmi.sbc.marketing.api.provider.giftcard.GiftCardBatchProvider;
import com.wanmi.sbc.marketing.api.provider.giftcard.GiftCardDetailProvider;
import com.wanmi.sbc.marketing.api.provider.giftcard.UserGiftCardProvider;
import com.wanmi.sbc.marketing.api.request.coupon.*;
import com.wanmi.sbc.marketing.api.request.giftcard.OldSendNewAutoActivateGiftCardRequest;
import com.wanmi.sbc.marketing.api.request.giftcard.OldSendNewGiftCardCancelRequest;
import com.wanmi.sbc.marketing.api.request.giftcard.OldSendNewSchoolUniformRequest;
import com.wanmi.sbc.marketing.api.response.coupon.CouponActivityDetailResponse;
import com.wanmi.sbc.marketing.api.response.coupon.CouponCodeListNotUseResponse;
import com.wanmi.sbc.marketing.api.response.coupon.CouponInfosQueryResponse;
import com.wanmi.sbc.marketing.api.response.giftcard.GiftCardCancelResultResponse;
import com.wanmi.sbc.marketing.bean.constant.Constant;
import com.wanmi.sbc.marketing.bean.enums.CouponActivityType;
import com.wanmi.sbc.marketing.bean.vo.CouponActivityVO;
import com.wanmi.sbc.marketing.bean.vo.CouponCodeVO;
import com.wanmi.sbc.marketing.bean.vo.CouponInfoVO;
import com.wanmi.sbc.mq.api.provider.MqSendProvider;
import com.wanmi.sbc.mq.bean.constants.ProducerTopic;
import com.wanmi.sbc.mq.bean.dto.MqSendDTO;
import com.wanmi.sbc.open.enums.JuneActivityRewardLevelEnum;
import com.wanmi.sbc.open.request.CouponsAndPointsRequest;
import com.wanmi.sbc.open.request.OrderGrowthRequest;
import com.wanmi.sbc.util.OperateLogMQUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@Tag(name =  "校服小助手通过白名单对接接口", description =  "SwdSchoolUniformAssistantController")
@RestController
@Validated
@RequestMapping(value = "/swdSchoolUniformAssistant")
@Slf4j
public class SwdSchoolUniformAssistantController {


    @Autowired
    private CustomerSiteQueryProvider customerSiteQueryProvider;

    @Autowired
    private CustomerSiteProvider customerSiteProvider;

    @Autowired
    private CustomerPointsDetailSaveProvider customerPointsDetailSaveProvider;

    @Autowired
    private CouponInfoQueryProvider couponInfoQueryProvider;

    @Autowired
    private CouponCodeQueryProvider couponCodeQueryProvider;

    @Autowired
    private CouponCodeProvider couponCodeProvider;

    @Autowired
    private CouponActivityProvider couponActivityProvider;

    @Autowired
    private OperateLogMQUtil operateLogMQUtil;

    @Autowired
    private EsCouponActivityProvider esCouponActivityProvider;

    @Autowired
    private MqSendProvider mqSendProvider;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private GiftCardBatchProvider giftCardBatchProvider;

    @Autowired
    private GiftCardDetailProvider giftCardDetailProvider;

    @Autowired
    private UserGiftCardProvider userGiftCardProvider;

    @Autowired
    private PayingMemberCustomerRelProvider payingMemberCustomerRelProvider;

    @Autowired
    private CustomerGrowthValueProvider customerGrowthValueProvider;







    private String juneActivityCouponsNamePrefix = "6月送-";


    @Operation(summary = "校服小助手下单，发放优惠券和积分")
    @PostMapping("/placeOrderAndIssueCouponsAndPoints")
    public BaseResponse placeOrderAndIssueCouponsAndPoints(@RequestBody @Valid CouponsAndPointsRequest request) {
        log.info("【小助手下单发放奖励】:{}", request);

        String cache =redisUtil.getString(RedisKeyConstant.XFXZSD_ORDER_REWORD_CACHE + request.getOrderSn());

        if(StrUtil.isNotEmpty(cache)){
            log.info("【下单奖励已经发放】:{}", cache);
            return BaseResponse.SUCCESSFUL();
        }
        String customerId = getCustomerIdByPhone(request.getPhone());

        JuneActivityRewardLevelEnum byRewardPriceEnum =
                JuneActivityRewardLevelEnum.getByRewardPrice(request.getRewardPrice());

        //赠送积分
        Long points = request.getRewardPrice().subtract(byRewardPriceEnum.getDiscountPrice())
                .multiply(new BigDecimal(byRewardPriceEnum.getRewardPointRate())).longValue();

        customerPointsDetailSaveProvider.add(CustomerPointsDetailAddRequest.builder()
                .customerId(customerId)
                .type(OperateType.GROWTH)
                .serviceType(PointsServiceType.OLD_SYSTEM_PLACE_ORDER)
                .points(points)
                .content(JSONObject.toJSONString(Collections.singletonMap("orderSn", request.getOrderSn())))
                .build());
        //根据奖励等级判断送券
        CouponInfoQueryRequest couponInfoQueryRequest = CouponInfoQueryRequest.builder()
                .likeCouponName(juneActivityCouponsNamePrefix)
                .delFlag(DeleteFlag.NO)
                .build();
        CouponInfosQueryResponse couponInfosQueryResponse =
                couponInfoQueryProvider.queryCouponInfos(couponInfoQueryRequest).getContext();
        if(couponInfosQueryResponse != null && CollUtil.isNotEmpty(couponInfosQueryResponse.getCouponCodeList())){

            CouponInfoVO couponInfoVO = couponInfosQueryResponse.getCouponCodeList().stream().filter(item
                            -> item.getDenomination().compareTo(byRewardPriceEnum.getCouponAmount()) == 0)
                    .findFirst().orElse(null);
            if(couponInfoVO != null){
                //创建精准赠券活动，赠送优惠券
                //couponActivityController.add(buildCouponActivityAddRequest(customerId,couponInfoVO.getCouponId()));
                CouponActivityAddRequest couponActivityAddRequest = buildCouponActivityAddRequest(customerId,couponInfoVO.getCouponId());
                BaseResponse<CouponActivityDetailResponse> response = couponActivityProvider.add(couponActivityAddRequest);
                //记录操作日志
                if (CommonErrorCodeEnum.K000000.getCode().equals(response.getCode())) {
                    CouponActivityVO couponActivity = response.getContext().getCouponActivity();
                    EsCouponActivityDTO esCouponActivityDTO = KsBeanUtil.convert(couponActivity, EsCouponActivityDTO.class);
                    List<String> joinLevels = Splitter.on(",").trimResults().splitToList(couponActivity.getJoinLevel());
                    esCouponActivityDTO.setJoinLevels(joinLevels);
                    operateLogMQUtil.convertAndSend("营销", "创建优惠券活动", "优惠券活动：" + couponActivityAddRequest.getActivityName());
                    esCouponActivityProvider.add(new EsCouponActivityAddRequest(esCouponActivityDTO));
                }
            }
        }

        //设置订单号缓存
        redisUtil.setString(RedisKeyConstant.XFXZSD_ORDER_REWORD_CACHE + request.getOrderSn(),request.getOrderSn(),3600);
        return BaseResponse.SUCCESSFUL();
    }

    private CouponActivityAddRequest buildCouponActivityAddRequest(String customerId,String couponId){
        List<String> customerIds = new ArrayList<>();
        customerIds.add(customerId);

        List<CouponActivityConfigSaveRequest> couponActivityConfigs = new ArrayList<>();

        CouponActivityConfigSaveRequest couponActivityConfig = CouponActivityConfigSaveRequest.builder()
                .couponId(couponId)
                .totalCount(1l)
                .build();
        couponActivityConfigs.add(couponActivityConfig);
        CouponActivityAddRequest couponActivityAddRequest = CouponActivityAddRequest.builder()
                .activityName("校服小助手下单送券")
                .startTime(DateUtil.toLocalDateTime( DateUtil.offset(new Date(), DateField.SECOND, 5)))
                .endTime(DateUtil.toLocalDateTime( DateUtil.offset(new Date(), DateField.SECOND, 30)))
                .couponActivityType(CouponActivityType.SPECIFY_COUPON)
                .receiveType(DefaultFlag.YES)
                .receiveCount(1)
                .platformFlag(DefaultFlag.YES)
                .joinLevel("-2")
                .customerScopeIds(customerIds)
                .couponActivityConfigs(couponActivityConfigs)
                .build();
        couponActivityAddRequest.setPlatformFlag(DefaultFlag.YES);
        couponActivityAddRequest.setCreatePerson("system");
        couponActivityAddRequest.setStoreId(Constant.BOSS_DEFAULT_STORE_ID);
        //设置是否平台等级
        couponActivityAddRequest.setJoinLevelType(DefaultFlag.YES);
        couponActivityAddRequest.setWithinTime(30);

        return couponActivityAddRequest;
    }

    @Operation(summary = "查询用户相关优惠券数量")
    @PostMapping("/getCustomerCouponsCount")
    public BaseResponse getCustomerCouponsCount(@RequestBody @Valid CouponsAndPointsRequest request) {
        log.info("【查询用户相关优惠券数量】:{}", request);
        //查询用户
        CustomerByAccountResponse customerByCustomerAccount = getCustomer(request.getPhone());
        if(customerByCustomerAccount == null){
            return BaseResponse.success(0);
        }

        //查询用户活动未使用的优惠券
        List<CouponCodeVO> couponCodeVOList = getCustomerActivityCoupons(customerByCustomerAccount.getCustomerId());
        log.info("【查询用户相关优惠券码】:{}", couponCodeVOList);
        if(CollUtil.isNotEmpty(couponCodeVOList)){
            return BaseResponse.success(couponCodeVOList.size());
        }
        return BaseResponse.success(0);
    }


    @Operation(summary = "用户退单(取消退单)，失效（重新发放）优惠券和积分")
    @PostMapping("/reSetRewards")
    public BaseResponse reSetRewards(@RequestBody @Valid CouponsAndPointsRequest request) {
        log.info("【小助手退货重置奖励】:{}", request);
        //查询用户
        CustomerByAccountResponse customerByCustomerAccount = getCustomer(request.getPhone());

        JuneActivityRewardLevelEnum oldLevel =
                JuneActivityRewardLevelEnum.getByRewardPrice(request.getLastRewardPrice());

        JuneActivityRewardLevelEnum newLevel =
                JuneActivityRewardLevelEnum.getByRewardPrice(request.getRewardPrice());
        //查询用户当前未使用的优惠券
        CouponCodeQueryNotUseRequest couponCodeQueryNotUseRequest = CouponCodeQueryNotUseRequest.builder()
                .customerId(customerByCustomerAccount.getCustomerId())
                .notExpire(true)
                .useStatus(DefaultFlag.NO)
                .delFlag(DeleteFlag.NO)
                .build();
        CouponCodeListNotUseResponse couponCodeListNotUseResponse =
                couponCodeQueryProvider.listNotUseStatus(couponCodeQueryNotUseRequest).getContext();
        //查询用户还有优惠券
        List<CouponCodeVO> couponCodeVOList =  couponCodeListNotUseResponse.getCouponCodeList();

        //查询活动的所有优惠券
        CouponInfoQueryRequest couponInfoQueryRequest = CouponInfoQueryRequest.builder()
                .likeCouponName(juneActivityCouponsNamePrefix)
                .delFlag(DeleteFlag.NO)
                .build();
        List<CouponInfoVO> couponInfoVOList =
                couponInfoQueryProvider.queryCouponInfos(couponInfoQueryRequest)
                        .getContext().getCouponCodeList();
        couponCodeVOList = couponCodeVOList.stream().filter(item -> {
            CouponInfoVO couponInfoVO = couponInfoVOList.stream()
                    .filter(that -> that.getCouponId().equals(item.getCouponId()))
                    .findFirst().orElse(null);
            if(couponInfoVO != null){
                item.setDenomination(couponInfoVO.getDenomination());
                return true;
            }
            return false;
        }).collect(Collectors.toList());

        //处理优惠券
        BigDecimal diffPrice = handleCoupon(couponCodeVOList,
                oldLevel, newLevel,couponInfoVOList,customerByCustomerAccount.getCustomerId());

        //扣除积分
        if(newLevel.getLevel() - oldLevel.getLevel() < 0){



            BigDecimal oldPoints = request.getLastRewardPrice().subtract(oldLevel.getDiscountPrice())
                    .multiply(new BigDecimal(oldLevel.getRewardPointRate()));
            BigDecimal points = request.getRewardPrice().subtract(newLevel.getDiscountPrice())
                    .multiply(new BigDecimal(newLevel.getRewardPointRate()));
            Long diffPoints = oldPoints.subtract(points).longValue();
            Long addPoints = customerByCustomerAccount.getPointsAvailable() > diffPoints ? diffPoints : customerByCustomerAccount.getPointsAvailable();
            if(addPoints > 0){
                customerPointsDetailSaveProvider.add(CustomerPointsDetailAddRequest.builder()
                        .customerId(customerByCustomerAccount.getCustomerId())
                        .type(OperateType.DEDUCT)
                        .serviceType(PointsServiceType.OLD_SYSTEM_REFLOUND_ORDER)
                        .points( addPoints)
                        .content(JSONObject.toJSONString(Collections.singletonMap("orderSn", request.getOrderSn())))
                        .build());
            }
        }
        //返回已使用的优惠券
        log.info("【小助手退货重置奖励返回】：{}：{}", request.getPhone(), diffPrice);
        return BaseResponse.success(diffPrice);
    }

    @Operation(summary = "查询用户退款的差额")
    @PostMapping("/searchRewardsDiff")
    public BaseResponse searchRewardsDiff(@RequestBody @Valid CouponsAndPointsRequest request) {
        log.info("【查询用户退款的差额】:{}", request);
        //查询用户
        CustomerByAccountResponse customerByCustomerAccount = getCustomer(request.getPhone());

        JuneActivityRewardLevelEnum oldLevel =
                JuneActivityRewardLevelEnum.getByRewardPrice(request.getLastRewardPrice());

        JuneActivityRewardLevelEnum newLevel =
                JuneActivityRewardLevelEnum.getByRewardPrice(request.getRewardPrice());

        //查询用户活动未使用的优惠券
        List<CouponCodeVO> couponCodeVOList = getCustomerActivityCoupons(customerByCustomerAccount.getCustomerId());

        //处理优惠券
        BigDecimal diffPrice = BigDecimal.ZERO;

        //先获取到原等级的优惠券
        CouponCodeVO couponCodeVO = couponCodeVOList.stream().filter(item -> item.getDenomination().compareTo(oldLevel.getCouponAmount()) == 0)
                .findFirst().orElse(null);
        if(couponCodeVO == null){
            //计算差价，
            int diff = Math.abs(newLevel.getCouponAmount().intValue() - oldLevel.getCouponAmount().intValue());
            diffPrice = new BigDecimal(diff);
        } else {

        }
        log.info("【查询用户退款的差额返回】：{}：{}", request.getPhone(), diffPrice);
        return BaseResponse.success(diffPrice);
    }


    @Operation(summary = "查询用户是否还有没用的优惠券")
    @PostMapping("/searchRewards")
    public BaseResponse searchRewards(@RequestBody @Valid CouponsAndPointsRequest request) {
        log.info("【查询用户是否还有没用的优惠券】:{}", request);
        CustomerByAccountResponse customerByCustomerAccount = getCustomer(request.getPhone());
        if(customerByCustomerAccount == null){
            return BaseResponse.success(false);
        }
        List<CouponCodeVO> couponCodeVOList = getCustomerActivityCoupons(customerByCustomerAccount.getCustomerId());

        JuneActivityRewardLevelEnum level =
                JuneActivityRewardLevelEnum.getByRewardPrice(request.getRewardPrice());
        couponCodeVOList = couponCodeVOList.stream().filter(item -> item.getDenomination().compareTo(level.getCouponAmount()) == 0)
                .collect(Collectors.toList());
        return BaseResponse.success(couponCodeVOList.size() > 0);
    }

    @Operation(summary = "旧校服订单赠送新校服提货款")
    @PostMapping("/oldSendNewSchoolUniform")
    public BaseResponse OldSendNewSchoolUniform(@RequestBody @Valid OldSendNewSchoolUniformRequest request) {
        log.info("【旧校服订单赠送新校服提货款】:{}", request);
        //验证用户是否存在,不存在则注册
        String customerId = getCustomerIdByPhone(request.getCustomerAccount());
        request.setCustomerId(customerId);
        request.setAppointmentShipmentFlag(1);
        request.setCreatePerson("2c8080815cd3a74a015cd3ae86850001");
        BaseResponse<Boolean> booleanBaseResponse = giftCardBatchProvider.OldSendNewSchoolUniformToAccount(request);
        log.info("【{},旧校服订单赠送新校服提货款返回】：{}", request.getCustomerAccount(), booleanBaseResponse);
        //送银卡会员
        try{
            PayingMemberAssignRequest payingMemberAssignRequest = PayingMemberAssignRequest.builder()
                    .customerId(customerId)
                    .levenName("V1")
                    .build();
            log.info("【送银卡会员】请求参数:{}", payingMemberAssignRequest);
            BaseResponse assignResponse = payingMemberCustomerRelProvider.assign(payingMemberAssignRequest);
            log.info("【送银卡会员响应数据】:{}", assignResponse);
        }catch (Exception e){
            log.error("【送银卡会员失败】:{}", e);
        }
        return booleanBaseResponse;
    }

    @Operation(summary = "旧校服订单退单销卡")
    @PostMapping("/oldSendNewCancelCard")
    public BaseResponse oldSendNewCancelCard(@RequestBody OldSendNewGiftCardCancelRequest request) {
        log.info("【旧校服订单退单销卡】:{}", request);
        request.setCancelDesc("旧订单取消自动销卡");
        request.setCancelPerson("2c8080815cd3a74a015cd3ae86850001");
        request.setTradePersonType(DefaultFlag.YES);
        BaseResponse<GiftCardCancelResultResponse> cancelResultResponse = giftCardDetailProvider.oldSendNewCancelCard(request);
        log.info("【{},旧校服订单赠送新校服提货款返回】：{}", request.getOrderSn(), cancelResultResponse);
        return cancelResultResponse;
    }

    @Operation(summary = "旧校服订单过售后自动激活提货卡")
    @PostMapping("/oldSendNewAutoActivateGiftCard")
    public BaseResponse oldSendNewAutoActivateGiftCard(@RequestBody OldSendNewAutoActivateGiftCardRequest request ){
        List<String> orderSnList = request.getOrderSnList();
        if(CollUtil.isEmpty(orderSnList)){
            return BaseResponse.error("参数异常");
        }
        userGiftCardProvider.oldSendNewAutoActivateGiftCard(request);
        return BaseResponse.SUCCESSFUL();
    }

    @Operation(summary = "旧校服订单过售后增加会员成长值")
    @PostMapping("/afterSaleGrowValue")
    public BaseResponse afterSaleGrowValue(@RequestBody List<OrderGrowthRequest>  afterSaleGrowValueList){
        log.info("【旧校服订单过售后增加会员成长值】:{}", afterSaleGrowValueList);
        for (OrderGrowthRequest orderGrowthRequest : afterSaleGrowValueList) {
            try {
                //验证用户是否存在,不存在则注册
                String customerId = getCustomerIdByPhone(orderGrowthRequest.getMobile());
                customerGrowthValueProvider.increaseGrowthValue(CustomerGrowthValueAddRequest.builder()
                        .customerId(customerId)
                        .type(OperateType.GROWTH)
                        .serviceType(GrowthValueServiceType.SWDH5ORDERCOMPLETION)
                        .growthValue(orderGrowthRequest.getGrowthValue())
                        .tradeNo(orderGrowthRequest.getOrderSn())
                        .opTime(LocalDateTime.now())
                        .build());
            } catch (Exception e) {
                log.error("【旧校服订单:{}过售后增加会员成长值报错】:{}",orderGrowthRequest.getOrderSn(), e);
            }

        }

        return BaseResponse.SUCCESSFUL();
    }


    private List<CouponCodeVO> getCustomerActivityCoupons(String customerId){
        //查询用户当前未使用的优惠券
        CouponCodeQueryNotUseRequest couponCodeQueryNotUseRequest = CouponCodeQueryNotUseRequest.builder()
                .customerId(customerId)
                .notExpire(true)
                .useStatus(DefaultFlag.NO)
                .delFlag(DeleteFlag.NO)
                .build();
        CouponCodeListNotUseResponse couponCodeListNotUseResponse =
                couponCodeQueryProvider.listNotUseStatus(couponCodeQueryNotUseRequest).getContext();
        //查询用户还有优惠券
        List<CouponCodeVO> couponCodeVOList =  couponCodeListNotUseResponse.getCouponCodeList();

        //查询活动的所有优惠券
        CouponInfoQueryRequest couponInfoQueryRequest = CouponInfoQueryRequest.builder()
                .likeCouponName(juneActivityCouponsNamePrefix)
                .delFlag(DeleteFlag.NO)
                .build();
        List<CouponInfoVO> couponInfoVOList =
                couponInfoQueryProvider.queryCouponInfos(couponInfoQueryRequest)
                        .getContext().getCouponCodeList();
        couponCodeVOList = couponCodeVOList.stream().filter(item -> {
            CouponInfoVO couponInfoVO = couponInfoVOList.stream()
                    .filter(that -> that.getCouponId().equals(item.getCouponId()))
                    .findFirst().orElse(null);
            if(couponInfoVO != null){
                item.setDenomination(couponInfoVO.getDenomination());
                return true;
            }
            return false;
        }).collect(Collectors.toList());
        return couponCodeVOList;
    }

    private  CustomerByAccountResponse getCustomer(String phone){
        CustomerByAccountRequest customerByAccountRequest = new CustomerByAccountRequest();
        customerByAccountRequest.setCustomerAccount(phone);
        CustomerByAccountResponse customerByCustomerAccount =
                customerSiteQueryProvider.getCustomerByCustomerAccount(customerByAccountRequest).getContext();
        return customerByCustomerAccount;
    }





    private BigDecimal handleCoupon(List<CouponCodeVO> couponCodeVOList,
                                    JuneActivityRewardLevelEnum oldLevel,
                                    JuneActivityRewardLevelEnum newLevel,
                                    List<CouponInfoVO> couponInfoVOList,String customerId) {

        //新的优惠券等级是否比老的等级高
        boolean isNewLevelHigher = newLevel.getLevel() - oldLevel.getLevel() > 0;

        //先获取到原等级的优惠券
        CouponCodeVO couponCodeVO = couponCodeVOList.stream().filter(item -> item.getDenomination().compareTo(oldLevel.getCouponAmount()) == 0)
                .findFirst().orElse(null);



        CouponActivityAddRequest couponActivityAddRequest = null;

        if(couponCodeVO == null){
            //计算差价，
            int diff = Math.abs(newLevel.getCouponAmount().intValue() - oldLevel.getCouponAmount().intValue());
            if(isNewLevelHigher){
                //发放差价优惠券
                CouponInfoVO couponInfoVO = couponInfoVOList.stream()
                        .filter(item -> item.getDenomination().compareTo(new BigDecimal(diff)) == 0)
                        .findFirst().orElse(null);
                if(couponInfoVO != null){
                    couponActivityAddRequest = buildCouponActivityAddRequest(customerId, couponInfoVO.getCouponId());
                }


            } else {
                //返回给老系统
                return new BigDecimal(diff);
            }
        } else {
            //失效掉发放新的优惠券,重新发放新的优惠券
            CouponCodeRecycleByIdRequest couponCodeRecycleByIdRequest = CouponCodeRecycleByIdRequest.builder()
                    .customerId(customerId)
                    .couponCodeId(couponCodeVO.getCouponCodeId())
                    .build();
            couponCodeProvider.recycleCoupon(couponCodeRecycleByIdRequest);

            if(CollUtil.isNotEmpty(couponInfoVOList)){
                CouponInfoVO couponInfoVO = couponInfoVOList.stream()
                        .filter(item -> item.getDenomination().compareTo(newLevel.getCouponAmount()) == 0)
                        .findFirst().orElse(null);
                if(couponInfoVO != null){
                    couponActivityAddRequest = buildCouponActivityAddRequest(customerId, couponInfoVO.getCouponId());
                }

            }

        }

        if(couponActivityAddRequest != null){
            BaseResponse<CouponActivityDetailResponse> response = couponActivityProvider.add(couponActivityAddRequest);
            //记录操作日志
            if (CommonErrorCodeEnum.K000000.getCode().equals(response.getCode())) {
                CouponActivityVO couponActivity = response.getContext().getCouponActivity();
                EsCouponActivityDTO esCouponActivityDTO = KsBeanUtil.convert(couponActivity, EsCouponActivityDTO.class);
                List<String> joinLevels = Splitter.on(",").trimResults().splitToList(couponActivity.getJoinLevel());
                esCouponActivityDTO.setJoinLevels(joinLevels);
                operateLogMQUtil.convertAndSend("营销", "创建优惠券活动", "优惠券活动：" + couponActivityAddRequest.getActivityName());
                esCouponActivityProvider.add(new EsCouponActivityAddRequest(esCouponActivityDTO));
            }
        }
        return BigDecimal.ZERO;
    }




    /**
     * 包装同步ES会员对象
     * @param customerVO
     * @return
     */
    public EsCustomerDetailDTO wrapperEsCustomerDetailDTO(CustomerVO customerVO){
        CustomerDetailVO customerDetail = customerVO.getCustomerDetail();
        EsCustomerDetailDTO esCustomerDetail = new EsCustomerDetailDTO();
        esCustomerDetail.setCustomerId( customerDetail.getCustomerId() );
        esCustomerDetail.setCustomerName( customerDetail.getCustomerName() );
        esCustomerDetail.setCustomerAccount( customerVO.getCustomerAccount() );
        esCustomerDetail.setProvinceId( customerDetail.getProvinceId() );
        esCustomerDetail.setCityId( customerDetail.getCityId() );
        esCustomerDetail.setAreaId( customerDetail.getAreaId() );
        esCustomerDetail.setStreetId( customerDetail.getStreetId() );
        esCustomerDetail.setCustomerAddress( customerDetail.getCustomerAddress() );
        esCustomerDetail.setContactName( customerDetail.getContactName() );
        esCustomerDetail.setCustomerLevelId( customerVO.getCustomerLevelId() );
        esCustomerDetail.setContactPhone( customerDetail.getContactPhone() );
        esCustomerDetail.setCheckState( customerVO.getCheckState() );
        esCustomerDetail.setCustomerStatus( customerDetail.getCustomerStatus() );
        esCustomerDetail.setCustomerType(customerVO.getCustomerType());
        esCustomerDetail.setEmployeeId( customerDetail.getEmployeeId() );
        esCustomerDetail.setIsDistributor( customerDetail.getIsDistributor() );
        esCustomerDetail.setRejectReason( customerDetail.getRejectReason() );
        esCustomerDetail.setForbidReason( customerDetail.getForbidReason() );
        esCustomerDetail.setEsStoreCustomerRelaList(Lists.newArrayList() );
        esCustomerDetail.setPointsAvailable(customerVO.getPointsAvailable());
        esCustomerDetail.setEnterpriseInfo(Objects.isNull(customerVO.getEnterpriseInfoVO()) ? null : KsBeanUtil.convert(customerVO.getEnterpriseInfoVO(), EsEnterpriseInfoDTO.class));
        esCustomerDetail.setEnterpriseCheckState( customerVO.getEnterpriseCheckState() );
        esCustomerDetail.setEnterpriseCheckReason( customerVO.getEnterpriseCheckReason() );
        esCustomerDetail.setCreateTime( customerDetail.getCreateTime());
        esCustomerDetail.setLogOutStatus(Objects.isNull(customerVO.getLogOutStatus())? Constants.NUM_1L:(long)customerVO.getLogOutStatus().toValue());
        return esCustomerDetail;
    }
    private String getCustomerIdByPhone(String phone) {
        //查询用户
        CustomerByAccountResponse customerByCustomerAccount = getCustomer(phone);
        String customerId = "";
        if(customerByCustomerAccount == null){
            CustomerDTO customer = new CustomerDTO();
            customer.setCustomerAccount(phone);
            customer.setCustomerPassword(DigestUtils.md5Hex(phone));
            //注册用户
            CustomerRegisterRequest customerRegisterRequest = CustomerRegisterRequest.builder()
                    .customerDTO(customer)
                    .build();

            CustomerRegisterResponse customerRegisterResponse =
                    customerSiteProvider.register(customerRegisterRequest).getContext();

            //同步es
            MqSendDTO mqSendDTO = new MqSendDTO();
            mqSendDTO.setTopic(ProducerTopic.ES_SERVICE_CUSTOMER_REGISTER);
            mqSendDTO.setData(JSONObject.toJSONString(wrapperEsCustomerDetailDTO(customerRegisterResponse)));
            mqSendProvider.send(mqSendDTO);

            customerId = customerRegisterResponse.getCustomerId();
        } else{
            customerId = customerByCustomerAccount.getCustomerId();
        }
        return customerId;
    }

}
