package com.wanmi.sbc.payingmember;


import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.annotation.MultiSubmitWithToken;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.enums.RightsCouponSendType;
import com.wanmi.sbc.customer.api.provider.customer.CustomerQueryProvider;
import com.wanmi.sbc.customer.api.provider.payingmembercustomerrel.PayingMemberCustomerRelQueryProvider;
import com.wanmi.sbc.customer.api.provider.payingmemberlevel.PayingMemberLevelQueryProvider;
import com.wanmi.sbc.customer.api.provider.payingmemberprice.PayingMemberPriceQueryProvider;
import com.wanmi.sbc.customer.api.request.customer.CustomerBaseByCustomerIdAndDeleteFlagRequest;
import com.wanmi.sbc.customer.api.request.payingmembercustomerrel.PayingMemberCheckRequest;
import com.wanmi.sbc.customer.api.request.payingmembercustomerrel.PayingMemberCustomerByCustomerIdRequest;
import com.wanmi.sbc.customer.api.request.payingmembercustomerrel.PayingMemberCustomerRelQueryRequest;
import com.wanmi.sbc.customer.api.request.payingmemberlevel.PayingMemberLevelByIdRequest;
import com.wanmi.sbc.customer.api.request.payingmemberlevel.PayingMemberLevelListRequest;
import com.wanmi.sbc.customer.api.request.payingmemberprice.PayingMemberPriceByIdRequest;
import com.wanmi.sbc.customer.api.response.payingmembercustomerrel.PayingMemberCustomerRelByIdResponse;
import com.wanmi.sbc.customer.api.response.payingmembercustomerrel.PayingMemberCustomerResponse;
import com.wanmi.sbc.customer.api.response.payingmemberlevel.PayingMemberLevelByIdResponse;
import com.wanmi.sbc.customer.api.response.payingmemberlevel.PayingMemberLevelListResponse;
import com.wanmi.sbc.customer.bean.enums.CustomerErrorCodeEnum;
import com.wanmi.sbc.customer.bean.enums.LevelRightsType;
import com.wanmi.sbc.customer.bean.vo.*;
import com.wanmi.sbc.elastic.api.provider.sku.EsSkuQueryProvider;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsInfoQueryRequest;
import com.wanmi.sbc.elastic.api.response.goods.EsGoodsInfoSimpleResponse;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoByIdRequest;
import com.wanmi.sbc.goods.api.response.info.GoodsInfoByIdResponse;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.goods.response.GoodsInfoListVO;
import com.wanmi.sbc.goods.service.GoodsBaseService;
import com.wanmi.sbc.goods.service.list.GoodsListInterface;
import com.wanmi.sbc.marketing.api.provider.coupon.CouponActivityQueryProvider;
import com.wanmi.sbc.marketing.api.provider.payingmember.PayingMemberDiscountProvider;
import com.wanmi.sbc.marketing.api.request.coupon.CouponActivityGetDetailByIdAndStoreIdRequest;
import com.wanmi.sbc.marketing.api.request.payingmember.PayingMemberSkuRequest;
import com.wanmi.sbc.marketing.api.response.coupon.CouponActivityDetailResponse;
import com.wanmi.sbc.marketing.api.response.payingmember.PayingMemberSkuResponse;
import com.wanmi.sbc.marketing.bean.vo.CouponInfoVO;
import com.wanmi.sbc.order.api.provider.payingmemberrecord.PayingMemberRecordQueryProvider;
import com.wanmi.sbc.order.api.provider.payingmemberrecordtemp.PayingMemberRecordTempProvider;
import com.wanmi.sbc.order.api.provider.payingmemberrecordtemp.PayingMemberRecordTempQueryProvider;
import com.wanmi.sbc.order.api.provider.trade.TradeQueryProvider;
import com.wanmi.sbc.order.api.request.payingmemberrecord.PayingMemberRecordPageRequest;
import com.wanmi.sbc.order.api.request.payingmemberrecordtemp.PayingMemberRecordTempAddRequest;
import com.wanmi.sbc.order.api.request.payingmemberrecordtemp.PayingMemberRecordTempByIdRequest;
import com.wanmi.sbc.order.api.request.trade.CustomerTradeListRequest;
import com.wanmi.sbc.order.api.response.payingmemberrecord.PayingMemberRecordPageResponse;
import com.wanmi.sbc.order.api.response.payingmemberrecordtemp.PayingMemberRecordTempAddResponse;
import com.wanmi.sbc.order.api.response.payingmemberrecordtemp.PayingMemberRecordTempByIdResponse;
import com.wanmi.sbc.order.api.response.trade.TradeListAllResponse;
import com.wanmi.sbc.order.bean.vo.PayingMemberRecordTempVO;
import com.wanmi.sbc.setting.api.provider.baseconfig.BaseConfigQueryProvider;
import com.wanmi.sbc.setting.api.provider.systemconfig.SystemConfigQueryProvider;
import com.wanmi.sbc.setting.api.request.ConfigQueryRequest;
import com.wanmi.sbc.setting.api.response.baseconfig.PayingMemberAgreementResponse;
import com.wanmi.sbc.setting.api.response.systemconfig.PayingMemberSettingResponse;
import com.wanmi.sbc.setting.bean.enums.ConfigType;
import com.wanmi.sbc.setting.bean.vo.ConfigVO;
import com.wanmi.sbc.util.CommonUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>订单公共Controller</p>
 *
 */
@Tag(name = "PayMemberController", description = "付费会员服务API")
@RestController
@RequestMapping("/paymember")
@Slf4j
@Validated
public class PayMemberController {


    @Autowired
    private PayingMemberLevelQueryProvider payingMemberLevelQueryProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private PayingMemberRecordTempProvider payingMemberRecordTempProvider;

    @Autowired
    private PayingMemberRecordTempQueryProvider payingMemberRecordTempQueryProvider;

    @Autowired
    private PayingMemberDiscountProvider payingMemberDiscountProvider;

    @Autowired
    private GoodsInfoQueryProvider goodsInfoQueryProvider;

    @Autowired
    private SystemConfigQueryProvider systemConfigQueryProvider;

    @Autowired
    private PayingMemberCustomerRelQueryProvider payingMemberCustomerRelQueryProvider;

    @Autowired
    private EsSkuQueryProvider esSkuQueryProvider;

    @Autowired
    private PayingMemberRecordQueryProvider payingMemberRecordQueryProvider;

    @Autowired
    private PayingMemberPriceQueryProvider payingMemberPriceQueryProvider;

    @Autowired
    private CouponActivityQueryProvider couponActivityQueryProvider;

    @Autowired
    private BaseConfigQueryProvider baseConfigQueryProvider;


    @Autowired
    private CustomerQueryProvider customerQueryProvider;

    @Resource(name = "goodsInfoListService")
    private GoodsListInterface<EsGoodsInfoQueryRequest, EsGoodsInfoSimpleResponse> goodsListInterface;

    @Autowired
    private GoodsBaseService goodsBaseService;

    @Autowired
    private TradeQueryProvider tradeQueryProvider;



    /**
     * 查询订单详情
     */
    @Operation(summary = "查询订单详情")
    @Parameter( name = "recordId", description = "付费记录ID", required = true)
    @RequestMapping(value = "/{recordId}", method = RequestMethod.GET)
    public BaseResponse<PayingMemberRecordTempByIdResponse> details(@PathVariable String recordId) {
        PayingMemberRecordTempVO payingMemberRecordTempVO = payingMemberRecordTempQueryProvider.getById(PayingMemberRecordTempByIdRequest.builder()
                .recordId(recordId)
                .build()).getContext().getPayingMemberRecordTempVO();
        String customerId = payingMemberRecordTempVO.getCustomerId();
        String operatorId = commonUtil.getOperatorId();
        if (!StringUtils.equals(customerId,operatorId)){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000014);
        }
        return BaseResponse.success(PayingMemberRecordTempByIdResponse.builder()
                .payingMemberRecordTempVO(payingMemberRecordTempVO)
                .build());
    }

    /**
     * 提交订单，用于生成付费会员临时记录
     */
    @Operation(summary = "提交订单，用于生成付费会员临时记录")
    @RequestMapping(value = "/commit", method = RequestMethod.POST)
    @MultiSubmitWithToken
    public BaseResponse<PayingMemberRecordTempAddResponse> commit(@RequestBody @Valid PayingMemberRecordTempAddRequest payingMemberRecordTempAddRequest) {
        //获取下单会员数据
        String customerId = commonUtil.getOperatorId();
        String customerName = customerQueryProvider.getBaseCustomer(new CustomerBaseByCustomerIdAndDeleteFlagRequest(customerId, DeleteFlag.NO))
                .getContext().getCustomerBaseVO().getCustomerName();
        payingMemberRecordTempAddRequest.setCustomerId(customerId);
        payingMemberRecordTempAddRequest.setCustomerName(customerName);
        Integer levelId = payingMemberRecordTempAddRequest.getLevelId();
        //根据levelId查询付费会员等级数据
        PayingMemberLevelVO payingMemberLevelVO = payingMemberLevelQueryProvider.getById(PayingMemberLevelByIdRequest.builder()
                .levelId(levelId)
                .isCustomer(true)
                .build()).getContext().getPayingMemberLevelVO();
        Integer levelState = payingMemberLevelVO.getLevelState();
        //如果该等级暂停，则提示前端付费会员等级状态已暂停，请联系客服
        if (levelState.equals(NumberUtils.INTEGER_ONE)) {
            throw new SbcRuntimeException(CustomerErrorCodeEnum.K010131);
        }
        payingMemberRecordTempAddRequest.setLevelName(payingMemberLevelVO.getLevelName());
        payingMemberRecordTempAddRequest.setLevelNickName(payingMemberLevelVO.getLevelNickName());
        List<PayingMemberPriceVO> payingMemberPriceVOS = payingMemberLevelVO.getPayingMemberPriceVOS();
        //根据priceId找出付费设置id
        PayingMemberPriceVO priceVO = payingMemberPriceVOS.parallelStream()
                .filter(payingMemberPriceVO -> payingMemberRecordTempAddRequest.getPriceId().equals(payingMemberPriceVO.getPriceId()))
                .findFirst().orElseThrow(() -> new SbcRuntimeException(CustomerErrorCodeEnum.K010132));
        payingMemberRecordTempAddRequest.setPayNum(priceVO.getPriceNum());
        payingMemberRecordTempAddRequest.setPayAmount(priceVO.getPriceTotal());
        //找到改付费设置下权益
        List<PayingMemberRightsRelVO> payingMemberRightsRelVOS = priceVO.getPayingMemberRightsRelVOS();
        List<Integer> rightsIdList = payingMemberRightsRelVOS.parallelStream().map(PayingMemberRightsRelVO::getRightsId).collect(Collectors.toList());
        payingMemberRecordTempAddRequest.setRightsIds(StringUtils.join(rightsIdList,','));
        payingMemberRecordTempAddRequest.setDelFlag(DeleteFlag.NO);
        String userId = commonUtil.getOperator().getUserId();
        payingMemberRecordTempAddRequest.setCreatePerson(userId);
        RLock rLock = redissonClient.getFairLock(userId);
        rLock.lock();
        PayingMemberRecordTempAddResponse response;
        try {
            response = payingMemberRecordTempProvider.add(payingMemberRecordTempAddRequest).getContext();
        } catch (Exception e) {
            throw e;
        } finally {
            rLock.unlock();
        }
        return BaseResponse.success(response);
    }

    @Operation(summary = "商品详情获取付费会员优惠信息")
    @GetMapping("/goods/{skuId}")
    public BaseResponse<PayingMemberSkuResponse> skuPayingMember(@PathVariable String skuId) {
        PayingMemberSkuResponse skuResponse = null;
        //查询配置
        ConfigQueryRequest request = new ConfigQueryRequest();
        request.setConfigType(ConfigType.PAYING_MEMBER.toValue());
        request.setDelFlag(DeleteFlag.NO.toValue());
        ConfigVO config = systemConfigQueryProvider.findByConfigTypeAndDelFlag(request).getContext().getConfig();
        PayingMemberSettingResponse res = JSONObject.parseObject(config.getContext(), PayingMemberSettingResponse.class);
        if (!res.getOpenFlag()) {
           return BaseResponse.success(skuResponse);
        }
        //查询商品信息
        GoodsInfoByIdResponse response = goodsInfoQueryProvider.getById(GoodsInfoByIdRequest.builder().goodsInfoId(skuId).build()).getContext();
        if (Objects.isNull(response)) {
            return BaseResponse.success(skuResponse);
        }
        //获取最大优惠
        PayingMemberSkuRequest skuRequest = PayingMemberSkuRequest.builder()
                .goodsInfoId(skuId)
                .customerId(commonUtil.getOperatorId())
                .companyType(response.getCompanyType())
                .goodsPrice(response.getMarketPrice())
                .storeId(response.getStoreId()).build();
        skuResponse = payingMemberDiscountProvider.discountForSku(skuRequest).getContext();
        return BaseResponse.success(skuResponse);
    }

    @Operation(summary = "订单详情获取付费会员优惠信息")
    @GetMapping("/goods/unlogin/{skuId}")
    public BaseResponse<PayingMemberSkuResponse> skuPayingMemberUnlogin(@PathVariable String skuId) {
        PayingMemberSkuResponse skuResponse = null;
        //查询配置
        ConfigQueryRequest request = new ConfigQueryRequest();
        request.setConfigType(ConfigType.PAYING_MEMBER.toValue());
        request.setDelFlag(DeleteFlag.NO.toValue());
        ConfigVO config = systemConfigQueryProvider.findByConfigTypeAndDelFlag(request).getContext().getConfig();
        PayingMemberSettingResponse res = JSONObject.parseObject(config.getContext(), PayingMemberSettingResponse.class);
        if (!res.getOpenFlag()) {
            return BaseResponse.success(skuResponse);
        }
        //查询商品信息
        GoodsInfoByIdResponse response = goodsInfoQueryProvider.getById(GoodsInfoByIdRequest.builder().goodsInfoId(skuId).build()).getContext();
        if (Objects.isNull(response)) {
            return BaseResponse.success(skuResponse);
        }
        //获取最大优惠
        PayingMemberSkuRequest skuRequest = PayingMemberSkuRequest.builder()
                .goodsInfoId(skuId)
                .companyType(response.getCompanyType())
                .goodsPrice(response.getMarketPrice())
                .storeId(response.getStoreId()).build();
        skuResponse = payingMemberDiscountProvider.discountForSku(skuRequest).getContext();
        return BaseResponse.success(skuResponse);
    }

    /**
     * 个人中心-付费会员入口-付费会员设置和付费会员等级等信息查询
     * @param request
     * @return
     */
    @Operation(summary = "个人中心-付费会员入口-付费会员设置和付费会员等级等信息查询")
    @RequestMapping(value = "/setting", method = RequestMethod.GET)
    public BaseResponse<PayingMemberCustomerResponse> getPayingMemberCustomerSetting(PayingMemberCustomerByCustomerIdRequest request) {
        String customerId = commonUtil.getOperatorId();
        request.setCustomerId(customerId);

        return payingMemberCustomerRelQueryProvider.getPayingMemberCustomerSetting(request);
    }



    @Operation(summary = "查询付费会员信息")
    @GetMapping("/detail")
    public BaseResponse<PayingMemberCustomerRelByIdResponse> findByCustomerId(){
        String customerId = commonUtil.getOperatorId();
        BaseResponse<PayingMemberCustomerRelByIdResponse> byCustomerId = payingMemberCustomerRelQueryProvider.findByCustomerId(PayingMemberCustomerRelQueryRequest.builder()
                .customerId(customerId)
                .build());
        Integer levelId = byCustomerId.getContext().getPayingMemberCustomerRelVO().getLevelId();
        PayingMemberLevelByIdRequest request = PayingMemberLevelByIdRequest.builder()
                .levelId(levelId)
                .isCustomer(true)
                .build();
        BaseResponse<PayingMemberLevelByIdResponse> byId = payingMemberLevelQueryProvider.getById(request);
        String levelName = byId.getContext().getPayingMemberLevelVO().getLevelName();
        byCustomerId.getContext().getPayingMemberCustomerRelVO().setLevelName(levelName);

        //查询用户升级还差金额
        CustomerTradeListRequest tradeListAllRequest = CustomerTradeListRequest.builder()
                .customerId(customerId)
                .build();
        BaseResponse<TradeListAllResponse> tradeListAllResponse =
                tradeQueryProvider.getListByCustomerId(tradeListAllRequest);
        BigDecimal totalPay = BigDecimal.ZERO;
        tradeListAllResponse.getContext().getTradeVOList().stream()
                .forEach(tradeVO -> {
                    BigDecimal payCash = tradeVO.getTradePrice().getTotalPayCash();
                    totalPay.add(payCash);
                });
        BigDecimal upgradePay = BigDecimal.ZERO;
        if(levelName.equals("V1")){
            upgradePay = BigDecimal.valueOf(1000);
        } else if(levelName.equals("V2")){
            upgradePay = BigDecimal.valueOf(2000);
        }
        byCustomerId.getContext().getPayingMemberCustomerRelVO().setRemainingAmount(upgradePay.subtract(totalPay));

        return byCustomerId;
    }

    @Operation(summary = "查询所有的付费会员等级")
    @GetMapping("/levels")
    public BaseResponse<PayingMemberLevelListResponse> findLevels(){
        List<Integer> LevelIds = payingMemberLevelQueryProvider.list(PayingMemberLevelListRequest.builder()
                .delFlag(DeleteFlag.NO)
                .build()).getContext().getPayingMemberLevelVOList().parallelStream().map(PayingMemberLevelVO::getLevelId)
                .collect(Collectors.toList());
        List<PayingMemberLevelVO> payingMemberLevelVOList = LevelIds.stream().map(levelId -> {
            PayingMemberLevelVO payingMemberLevelVO = payingMemberLevelQueryProvider.getById(PayingMemberLevelByIdRequest.builder()
                    .levelId(levelId)
                    .isCustomer(true)
                    .build())
                    .getContext().getPayingMemberLevelVO();
            // 如果存在自定义折扣商品，则实时查询商品数据（暂时去掉）
//            if (CollectionUtils.isNotEmpty(payingMemberLevelVO.getPayingMemberDiscountRelVOS())) {
//                List<PayingMemberDiscountRelVO> payingMemberDiscountRelVOS = payingMemberLevelVO.getPayingMemberDiscountRelVOS();
//                List<String> discountSkuIds = payingMemberDiscountRelVOS.parallelStream().map(PayingMemberDiscountRelVO::getGoodsInfoId)
//                        .collect(Collectors.toList());
//                List<GoodsInfoVO> goodsInfos = getGoodsInfoList(discountSkuIds);
//                payingMemberDiscountRelVOS = goodsInfos.parallelStream().map(goodsInfoVO -> {
//                    Optional<PayingMemberDiscountRelVO> optional = payingMemberLevelVO.getPayingMemberDiscountRelVOS().parallelStream()
//                            .filter(payingMemberDiscountRelVO -> payingMemberDiscountRelVO.getGoodsInfoId().equals(goodsInfoVO.getGoodsInfoId()))
//                            .findFirst();
//                    PayingMemberDiscountRelVO payingMemberDiscountRelVO = null;
//                    if (optional.isPresent()) {
//                        payingMemberDiscountRelVO = optional.get();
//                        payingMemberDiscountRelVO.setGoodsInfoVO(goodsInfoVO);
//                    }
//                    return payingMemberDiscountRelVO;
//                }).filter(Objects::nonNull).collect(Collectors.toList());
//                payingMemberLevelVO.setPayingMemberDiscountRelVOS(payingMemberDiscountRelVOS);
//            }
            //实时查询推荐商品信息
            List<PayingMemberRecommendRelVO> payingMemberRecommendRelVOS = payingMemberLevelVO.getPayingMemberRecommendRelVOS();
            List<String> recommendSkuIds = payingMemberRecommendRelVOS.parallelStream().map(PayingMemberRecommendRelVO::getGoodsInfoId).collect(Collectors.toList());
            List<GoodsInfoVO> recommendSkuList = getGoodsInfoList(recommendSkuIds);
            payingMemberRecommendRelVOS = recommendSkuList.parallelStream().map(goodsInfoVO -> {
                Optional<PayingMemberRecommendRelVO> optional = payingMemberLevelVO.getPayingMemberRecommendRelVOS().parallelStream()
                        .filter(payingMemberRecommendRelVO -> payingMemberRecommendRelVO.getGoodsInfoId().equals(goodsInfoVO.getGoodsInfoId()))
                        .findFirst();
                PayingMemberRecommendRelVO payingMemberRecommendRelVO = null;
                if (optional.isPresent()) {
                    payingMemberRecommendRelVO = optional.get();
                    Integer levelDiscountType = payingMemberLevelVO.getLevelDiscountType();
                    BigDecimal marketPrice = goodsInfoVO.getMarketPrice();
                    if (Objects.isNull(marketPrice)) {
                        marketPrice = BigDecimal.ZERO;
                    }
                    if (NumberUtils.INTEGER_ZERO.equals(levelDiscountType)) {
                        BigDecimal levelAllDiscount = payingMemberLevelVO.getLevelAllDiscount();
                        BigDecimal salePrice = (marketPrice
                                .multiply(levelAllDiscount.divide(new BigDecimal("10"))))
                                .setScale(2, RoundingMode.HALF_UP);
                        goodsInfoVO.setSalePrice(salePrice);
                    } else {
                        PayingMemberDiscountRelVO payingMemberDiscount = payingMemberLevelVO.getPayingMemberDiscountRelVOS().parallelStream().filter(payingMemberDiscountRelVO -> {
                            String goodsInfoId = payingMemberDiscountRelVO.getGoodsInfoId();
                            String skuId = goodsInfoVO.getGoodsInfoId();
                            return StringUtils.equals(goodsInfoId, skuId);
                        }).findFirst().orElseThrow(() -> new SbcRuntimeException(CommonErrorCodeEnum.K000001));

                        BigDecimal discount = payingMemberDiscount.getDiscount();
                        BigDecimal salePrice = (marketPrice
                                .multiply(discount.divide(new BigDecimal("10"))))
                                .setScale(2, RoundingMode.HALF_UP);
                        goodsInfoVO.setSalePrice(salePrice);
                    }
                    payingMemberRecommendRelVO.setGoodsInfoVO(goodsInfoVO);
                }
                return payingMemberRecommendRelVO;
            }).filter(Objects::nonNull).collect(Collectors.toList());
            payingMemberLevelVO.setPayingMemberRecommendRelVOS(payingMemberRecommendRelVOS);
            payingMemberLevelVO.setPayingMemberDiscountRelVOS(null);
            return payingMemberLevelVO;
        }).collect(Collectors.toList());
        return BaseResponse.success(PayingMemberLevelListResponse.builder()
                .payingMemberLevelVOList(payingMemberLevelVOList)
                .build());
    }

    @Operation(summary = "根据levelId查询付费会员等级")
    @GetMapping("/level/{levelId}")
    public BaseResponse<PayingMemberLevelByIdResponse> findLevels(@PathVariable Integer levelId){
        return payingMemberLevelQueryProvider.getById(PayingMemberLevelByIdRequest.builder()
                .isCustomer(true)
                .levelId(levelId)
                .build());
    }

    @Operation(summary = "根据付费设置id查询付费会员权益")
    @GetMapping("/level/price/{priceId}")
    public BaseResponse<PayingMemberPriceVO> getById(@PathVariable Integer priceId) {
        if (priceId == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        PayingMemberPriceVO payingMemberPriceVO= payingMemberPriceQueryProvider.getById(PayingMemberPriceByIdRequest.builder()
                .priceId(priceId)
                .build()).getContext().getPayingMemberPriceVO();
        List<CustomerLevelRightsVO> customerLevelRightsVOS = payingMemberPriceVO.getCustomerLevelRightsVOS();
        customerLevelRightsVOS.parallelStream().map(rights -> {
            if (rights.getRightsType() == LevelRightsType.COUPON_GIFT
                    && rights.getActivityId() != null) {
                String type = JSON.parseObject(rights.getRightsRule()).get("type").toString();
                if (RightsCouponSendType.ISSUE_ONCE.getStateId().equals(type) || RightsCouponSendType.REPEAT.getStateId().equals(type)) {
                    String activityId = rights.getActivityId();
                    CouponActivityDetailResponse response =
                            couponActivityQueryProvider
                                    .getDetailByIdAndStoreId(
                                            CouponActivityGetDetailByIdAndStoreIdRequest.builder()
                                                    .id(activityId)
                                                    .storeId(null)
                                                    .build())
                                    .getContext();
                    List<CouponInfoVO> couponInfoList = response.getCouponInfoList();
                    rights.setCouponInfoList(couponInfoList);
                }
            }
            return rights;
        }).collect(Collectors.toList());

        return BaseResponse.success(payingMemberPriceVO);
    }

    @Operation(summary = "分页查询付费记录")
    @PostMapping("/record")
    public BaseResponse<PayingMemberRecordPageResponse> findPayingMemberRecordByCustomerId(@RequestBody @Valid PayingMemberRecordPageRequest pageReq) {
        pageReq.setDelFlag(DeleteFlag.NO);
        pageReq.setCustomerId(commonUtil.getOperatorId());
        pageReq.putSort("payTime", "desc");
        return payingMemberRecordQueryProvider.page(pageReq);
    }


    /**
     * 查询商品信息
     * @param goodsInfoIds
     * @return
     */
    private  List<GoodsInfoVO> getGoodsInfoList(List<String> goodsInfoIds) {
        EsGoodsInfoQueryRequest esSkuPageRequest = new EsGoodsInfoQueryRequest();
        esSkuPageRequest.setPageSize(Constants.NUM_1000);
        esSkuPageRequest.setGoodsInfoIds(goodsInfoIds);
        esSkuPageRequest.setCustomerId(commonUtil.getOperatorId());

        List<GoodsInfoListVO> content = goodsBaseService.skuListConvert(
                goodsListInterface.getList(esSkuPageRequest), null).getGoodsInfoPage().getContent();
        return KsBeanUtil.convert(content, GoodsInfoVO.class);
    }

    /**
     * 付费会员协议
     * @return
     */
    @Operation(summary = "付费会员协议")
    @GetMapping("/agreement")
    public BaseResponse<PayingMemberAgreementResponse> payingMemberAgreement(){
        return baseConfigQueryProvider.getPayingMemberAgreement();
    }

    @Operation(summary = "检查当前用户是否为付费会员")
    @GetMapping("/checkUser")
    public BaseResponse checkPayMember() {
        return payingMemberCustomerRelQueryProvider.checkPayMember(PayingMemberCheckRequest.builder()
                .customerId(commonUtil.getOperatorId()).build());
    }

}
