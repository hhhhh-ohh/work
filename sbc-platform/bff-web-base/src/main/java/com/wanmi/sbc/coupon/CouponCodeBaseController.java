package com.wanmi.sbc.coupon;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.common.constant.RedisKeyConstant;
import com.wanmi.sbc.common.enums.ChannelType;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.PluginType;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.util.UUIDUtil;
import com.wanmi.sbc.common.util.WmCollectionUtils;
import com.wanmi.sbc.coupon.dto.CouponCheckoutTradeParams;
import com.wanmi.sbc.coupon.dto.CouponSelectGoodsInfoDTO;
import com.wanmi.sbc.coupon.request.CouponAutoSelectForCartRequest;
import com.wanmi.sbc.coupon.request.CouponAutoSelectForConfirmRequest;
import com.wanmi.sbc.coupon.request.CouponCheckoutBaseRequest;
import com.wanmi.sbc.coupon.request.CouponFetchBaseRequest;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.store.ListNoDeleteStoreByIdsRequest;
import com.wanmi.sbc.customer.bean.vo.CustomerSimplifyOrderCommitVO;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.distribute.DistributionCacheService;
import com.wanmi.sbc.distribute.DistributionService;
import com.wanmi.sbc.elastic.api.provider.coupon.EsCouponScopeQueryProvider;
import com.wanmi.sbc.elastic.api.provider.goods.EsGoodsInfoElasticQueryProvider;
import com.wanmi.sbc.elastic.api.request.coupon.EsCouponScopePageRequest;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsInfoQueryRequest;
import com.wanmi.sbc.elastic.api.response.goods.EsGoodsInfoSimpleResponse;
import com.wanmi.sbc.elastic.bean.vo.coupon.EsCouponScopeVO;
import com.wanmi.sbc.elastic.bean.vo.goods.EsGoodsInfoVO;
import com.wanmi.sbc.elastic.bean.vo.goods.GoodsInfoNestVO;
import com.wanmi.sbc.goods.api.provider.buycyclegoodsinfo.BuyCycleGoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.request.buycyclegoodsinfo.BuyCycleGoodsInfoByGoodsIdRequest;
import com.wanmi.sbc.goods.api.response.info.GoodsInfoResponse;
import com.wanmi.sbc.goods.bean.enums.DistributionGoodsAudit;
import com.wanmi.sbc.goods.bean.enums.EnterpriseAuditState;
import com.wanmi.sbc.goods.bean.enums.GoodsPriceType;
import com.wanmi.sbc.goods.bean.vo.BuyCycleGoodsInfoVO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoSimpleVO;
import com.wanmi.sbc.goods.bean.vo.MarketingPluginLabelDetailVO;
import com.wanmi.sbc.goods.bean.vo.MarketingPluginSimpleLabelVO;
import com.wanmi.sbc.marketing.api.provider.coupon.CouponCodeProvider;
import com.wanmi.sbc.marketing.api.provider.coupon.CouponCodeQueryProvider;
import com.wanmi.sbc.marketing.api.provider.newplugin.NewMarketingPluginProvider;
import com.wanmi.sbc.marketing.api.request.coupon.*;
import com.wanmi.sbc.marketing.api.request.newplugin.GoodsListPluginRequest;
import com.wanmi.sbc.marketing.api.response.coupon.*;
import com.wanmi.sbc.marketing.bean.dto.TradeItemInfoDTO;
import com.wanmi.sbc.marketing.bean.enums.BookingType;
import com.wanmi.sbc.marketing.bean.enums.MarketingPluginType;
import com.wanmi.sbc.marketing.bean.enums.QueryCouponType;
import com.wanmi.sbc.marketing.bean.enums.RangeDayType;
import com.wanmi.sbc.marketing.bean.vo.CheckGoodsInfoVO;
import com.wanmi.sbc.marketing.bean.vo.CouponCodeVO;
import com.wanmi.sbc.order.api.provider.trade.TradeItemQueryProvider;
import com.wanmi.sbc.order.api.provider.trade.VerifyQueryProvider;
import com.wanmi.sbc.order.api.request.trade.TradeCommitRequest;
import com.wanmi.sbc.order.api.request.trade.TradeItemSnapshotByCustomerIdRequest;
import com.wanmi.sbc.order.api.request.trade.VerifyGoodsRequest;
import com.wanmi.sbc.order.api.response.trade.TradeConfirmResponse;
import com.wanmi.sbc.order.bean.dto.TradeGoodsInfoPageDTO;
import com.wanmi.sbc.order.bean.dto.TradeItemDTO;
import com.wanmi.sbc.order.bean.vo.TradeItemGroupVO;
import com.wanmi.sbc.order.bean.vo.TradeItemSnapshotVO;
import com.wanmi.sbc.order.bean.vo.TradeItemVO;
import com.wanmi.sbc.setting.api.provider.systemconfig.SystemConfigQueryProvider;
import com.wanmi.sbc.setting.api.request.ConfigQueryRequest;
import com.wanmi.sbc.setting.api.response.systemconfig.SystemConfigTypeResponse;
import com.wanmi.sbc.setting.bean.enums.ConfigType;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.util.GoodsInfoConvertMapper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 *
 * Created by CHENLI on 2018/9/21.
 */
@RestController
@Validated
@RequestMapping("/coupon-code")
@Tag(name = "CouponCodeBaseController", description = "S2B web公用-我的优惠券API")
public class CouponCodeBaseController {


    @Autowired
    private CouponCodeQueryProvider couponCodeQueryProvider;

    @Autowired
    private CouponCodeProvider couponCodeProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private TradeItemQueryProvider tradeItemQueryProvider;

    @Autowired
    private DistributionService distributionService;

    @Resource
    private DistributionCacheService distributionCacheService;

    @Resource
    private VerifyQueryProvider verifyQueryProvider;

    @Resource
    private StoreQueryProvider storeQueryProvider;

    @Autowired
    private EsGoodsInfoElasticQueryProvider esGoodsInfoElasticQueryProvider;

    @Autowired
    EsCouponScopeQueryProvider esCouponScopeQueryProvider;

    @Autowired
    private SystemConfigQueryProvider systemConfigQueryProvider;

    @Autowired
    private GoodsInfoConvertMapper goodsInfoConvertMapper;

    @Autowired
    private NewMarketingPluginProvider newMarketingPluginProvider;

    @Autowired
    private BuyCycleGoodsInfoQueryProvider buyCycleGoodsInfoQueryProvider;

    @Autowired
    private RedissonClient redissonClient;

    /**
     * APP / H5 查询我的优惠券
     * @param request
     * @return
     */
    @Operation(summary = "APP/H5查询我的优惠券")
    @RequestMapping(value = "/my-coupon", method = RequestMethod.POST)
    public BaseResponse<CouponCodePageResponse> listMyCouponList(@RequestBody CouponCodePageRequest request){
        request.setCustomerId(commonUtil.getOperatorId());

        return couponCodeQueryProvider.page(request);
    }

    /**
     * 根据活动和优惠券领券
     * @param baseRequest
     * @return
     */
    @Operation(summary = "根据活动和优惠券领券")
    @RequestMapping(value = "/fetch-coupon", method = RequestMethod.POST)
    public BaseResponse customerFetchCoupon(@Valid @RequestBody CouponFetchBaseRequest baseRequest){
        CouponFetchRequest request = new CouponFetchRequest();
        KsBeanUtil.copyProperties(baseRequest, request);
        request.setCustomerId(commonUtil.getOperatorId());
        request.setFetchCouponFlag(Boolean.TRUE);
        RLock rLock = redissonClient.getFairLock(RedisKeyConstant.FETCH_COUPON.concat(commonUtil.getOperatorId()));
        rLock.lock();
        try {
            return couponCodeProvider.fetch(request);
        } catch (Exception e) {
            throw e;
        } finally {
            rLock.unlock();
        }
    }


    /**
     * APP/H5 统计用户可领取但未领取的优惠券数量
     */
    @Operation(summary = "APP/H5 统计用户可领取但未领取的优惠券数量")
    @RequestMapping(value = "/not/fetch-coupon/count", method = RequestMethod.POST)
    public BaseResponse customerNotFetchCouponCount(){
        return couponCodeProvider
                .notFetchCount(
                        CouponCacheCenterPageRequest
                                .builder()
                                .customerId(commonUtil.getOperatorId()).build());
    }


    /**
     * 使用优惠券选择时的后台处理
     * @param baseRequest
     * @return
     */
    @Operation(summary = "使用优惠券选择时的后台处理")
    @RequestMapping(value = "/checkout-coupons", method = RequestMethod.POST)
    public BaseResponse<CouponCheckoutResponse> checkoutCoupons(@Valid @RequestBody CouponCheckoutBaseRequest baseRequest) {
        // 券ID去重
        baseRequest.setCouponCodeIds(baseRequest.getCouponCodeIds().stream().distinct().collect(Collectors.toList()));
        CouponCheckoutRequest request = new CouponCheckoutRequest();
        KsBeanUtil.copyProperties(baseRequest, request);
        request.setCustomerId(commonUtil.getOperatorId());
        request.setTerminalToken(commonUtil.getTerminalToken());
        request.setUnreachedTypes(Arrays.asList(QueryCouponType.GENERAL_REDUCTION, QueryCouponType.STORE_FREIGHT));
        CouponCheckoutResponse couponCheckoutResponse = couponCodeQueryProvider.checkout(request).getContext();
        TradeItemSnapshotVO tradeItemSnapshotVO = tradeItemQueryProvider.listByTerminalTokenWithout(TradeItemSnapshotByCustomerIdRequest
                .builder().terminalToken(commonUtil.getTerminalToken()).build()).getContext().getTradeItemSnapshotVO();
        //特殊处理尾款场景
        if (Objects.isNull(tradeItemSnapshotVO)){
            return BaseResponse.success(couponCheckoutResponse);
        }
        List<TradeItemGroupVO> tradeItemGroups = tradeItemSnapshotVO.getItemGroups();

        List<String> skuIds = tradeItemGroups.stream().flatMap(i -> i.getTradeItems().stream())
                .map(TradeItemVO::getSkuId).collect(Collectors.toList());
        //获取订单商品详情,包含区间价，会员级别价salePrice
        GoodsInfoResponse skuResp = distributionService.getGoodsResponse(skuIds, commonUtil.getOperatorId());
        Map<Long, StoreVO> storeMap = storeQueryProvider.listNoDeleteStoreByIds(new ListNoDeleteStoreByIdsRequest
                (tradeItemGroups.stream().map(g -> g
                        .getSupplier().getStoreId())
                        .collect(Collectors.toList()))).getContext().getStoreVOList().stream().collect(Collectors
                .toMap(StoreVO::getStoreId, Function.identity()));

        // 组合购标记
        boolean suitMarketingFlag = tradeItemGroups.stream().anyMatch(s -> Objects.equals(Boolean.TRUE,
                s.getSuitMarketingFlag()));
        //拼团标记
        boolean grouponFlag = tradeItemGroups.stream().anyMatch(s -> s.getGrouponForm() != null && s.getGrouponForm().getOpenGroupon() != null);

        boolean buyCycleFlag = tradeItemGroups.stream().anyMatch(s -> s.getOrderTag() != null && Boolean.TRUE.equals(s.getOrderTag().getBuyCycleFlag()));

        // 如果为PC商城下单or组合购商品，将分销商品变为普通商品
        if (ChannelType.PC_MALL.equals(commonUtil.getDistributeChannel().getChannelType()) || suitMarketingFlag || grouponFlag || buyCycleFlag) {
            tradeItemGroups.forEach(tradeItemGroup ->
                    tradeItemGroup.getTradeItems().forEach(tradeItem -> {
                        tradeItem.setDistributionGoodsAudit(DistributionGoodsAudit.COMMON_GOODS);
                        if (suitMarketingFlag) {
                            tradeItem.setBuyPoint(NumberUtils.LONG_ZERO);
                        }
                    })
            );
            skuResp.getGoodsInfos().forEach(item -> {
                item.setDistributionGoodsAudit(DistributionGoodsAudit.COMMON_GOODS);
                if (suitMarketingFlag || buyCycleFlag) {
                    item.setBuyPoint(NumberUtils.LONG_ZERO);
                }
            });
        }


        List<CheckGoodsInfoVO> checkGoodsInfos = couponCheckoutResponse.getCheckGoodsInfos();
        Map<String, CheckGoodsInfoVO> checkGoodsInfoVOMap = checkGoodsInfos.stream().collect(Collectors.toMap(CheckGoodsInfoVO::getGoodsInfoId,Function.identity()));
        tradeItemGroups.forEach(
                g -> {
                    g.getSupplier().setFreightTemplateType(storeMap.get(g.getSupplier().getStoreId())
                            .getFreightTemplateType());
                    List<TradeItemVO> tradeItems = g.getTradeItems();
                    List<TradeItemDTO> tradeItemDTOList = KsBeanUtil.convert(tradeItems, TradeItemDTO.class);
                    //商品验证并填充商品价格
                    List<TradeItemVO> tradeItemVOList =
                            verifyQueryProvider.verifyGoods(new VerifyGoodsRequest(tradeItemDTOList, Collections
                                    .emptyList(),
                                    KsBeanUtil.convert(skuResp, TradeGoodsInfoPageDTO.class),
                                    g.getSupplier().getStoreId(), Boolean.TRUE, null)).getContext().getTradeItems();

                    g.setTradeItems(tradeItemVOList);
                    // 分销商品、开店礼包商品，重新设回市场价
                    if (DefaultFlag.YES.equals(distributionCacheService.queryOpenFlag())
                            && !ChannelType.PC_MALL.equals(commonUtil.getDistributeChannel().getChannelType())) {
                        g.getTradeItems().stream().filter(tradeItemVO -> DefaultFlag.YES.equals(g.getStoreBagsFlag())
                                || (Objects.isNull(tradeItemVO.getBuyPoint()) || tradeItemVO.getBuyPoint() == 0)).forEach(item -> {
                            DefaultFlag storeOpenFlag = distributionCacheService.queryStoreOpenFlag(item.getStoreId()
                                    .toString());
                            if ((Objects.isNull(item.getIsFlashSaleGoods()) ||
                                    (Objects.nonNull(item.getIsFlashSaleGoods()) && !item.getIsFlashSaleGoods())) &&
                                    (Objects.isNull(item.getIsAppointmentSaleGoods()) || !item.getIsAppointmentSaleGoods()) &&
                                    !(Objects.nonNull(item.getIsBookingSaleGoods()) && item.getIsBookingSaleGoods() && item.getBookingType() == BookingType.FULL_MONEY) &&
                                    DefaultFlag.YES.equals(storeOpenFlag) && (
                                    DistributionGoodsAudit.CHECKED.equals(item.getDistributionGoodsAudit())
                                            || DefaultFlag.YES.equals(g.getStoreBagsFlag()))) {
                                if(null == item.getOriginalPrice()){
                                    item.setOriginalPrice(BigDecimal.ZERO);
                                }
                                item.setSplitPrice(checkGoodsInfoVOMap.get(item.getSkuId()).getSplitPrice());
                                if (DefaultFlag.YES.equals(g.getStoreBagsFlag())) {
                                    item.setBuyPoint(NumberUtils.LONG_ZERO);
                                }
                            } else {
                                item.setDistributionGoodsAudit(DistributionGoodsAudit.COMMON_GOODS);
                            }
                        });
                    }

                }
        );
        TradeCommitRequest tradeCommitRequest = new TradeCommitRequest();
        CustomerSimplifyOrderCommitVO simplifyOrderCommitVO = new CustomerSimplifyOrderCommitVO();
        simplifyOrderCommitVO.setCustomerId(commonUtil.getOperatorId());
        tradeCommitRequest.setCustomer(simplifyOrderCommitVO);
        tradeCommitRequest.setPoints(baseRequest.getPoints());

        distributionService.dealPoints(tradeItemGroups,tradeCommitRequest);

        BigDecimal commission = distributionService.dealDistribution(tradeItemGroups,new TradeConfirmResponse());
        couponCheckoutResponse.setCommission(commission);
        // 处理加价购商品拆分
        if (CollectionUtils.isNotEmpty(baseRequest.getTradeParams())) {
            this.couponCheckoutResponse(baseRequest.getTradeParams(), couponCheckoutResponse);
        }
        return BaseResponse.success(couponCheckoutResponse);
    }

    /**
     * @description   处理加价商品均摊
     * @author  wur
     * @date: 2023/1/5 15:44
     * @param tradeParams    请求参数
     * @param couponCheckoutResponse   返回处理
     * @return
     **/
    private void couponCheckoutResponse(List<CouponCheckoutTradeParams> tradeParams, CouponCheckoutResponse couponCheckoutResponse) {
        List<CheckGoodsInfoVO> checkPreferentialSku = new ArrayList<>();
        List<CheckGoodsInfoVO> checkGoodsInfos =  new ArrayList<>();
        for (CouponCheckoutTradeParams tradeParam : tradeParams) {
            // 参数错误直接return
            if (CollectionUtils.isEmpty(tradeParam.getOldTradeItems())) {
                return;
            }
            Long storeId = tradeParam.getOldTradeItems().get(0).getStoreId();
            List<CheckGoodsInfoVO> storeCheckGoodsInfos = couponCheckoutResponse.getCheckGoodsInfos().stream()
                    .filter(g -> g.getStoreId().compareTo(storeId) == 0)
                    .collect(Collectors.toList());
            if (CollectionUtils.isEmpty(storeCheckGoodsInfos)) {
                continue;
            }
            // 验证是否有加价购商品
            if(CollectionUtils.isEmpty(tradeParam.getOldPreferential())) {
                checkGoodsInfos.addAll(storeCheckGoodsInfos);
                continue;
            }
            // 计算加价购商品的总额
            Map<String, BigDecimal> skuIdToPriceMap =
                    tradeParam.getOldPreferential().stream().collect(Collectors.toMap(TradeItemDTO::getSkuId,
                            TradeItemDTO::getSplitPrice, BigDecimal::add));
            // 叠加普通商品金额
            for (TradeItemDTO itemDTO : tradeParam.getOldTradeItems()) {
                if (skuIdToPriceMap.containsKey(itemDTO.getSkuId())) {
                    BigDecimal totalPrice = skuIdToPriceMap.getOrDefault(itemDTO.getSkuId(),BigDecimal.ZERO).add(itemDTO.getSplitPrice());
                    skuIdToPriceMap.put(itemDTO.getSkuId(), totalPrice);
                }
            }
            //普通商品信息
            List<String> skuIdList = tradeParam.getOldTradeItems().stream().map(TradeItemDTO::getSkuId).collect(Collectors.toList());
            // 处理每个商品的均摊
            for (CheckGoodsInfoVO skuVO : storeCheckGoodsInfos) {
                if (skuIdToPriceMap.containsKey(skuVO.getGoodsInfoId())) {
                    BigDecimal alrPrice = BigDecimal.ZERO;
                    BigDecimal splitPrice = skuVO.getSplitPrice();
                    BigDecimal oldPrice = skuIdToPriceMap.get(skuVO.getGoodsInfoId());
                    List<TradeItemDTO> oldPreferential = tradeParam.getOldPreferential().stream().filter(item-> item.getSkuId().equals(skuVO.getGoodsInfoId())).collect(Collectors.toList());
                    int size = oldPreferential.size();
                    // 循环处理每个加价购商品
                    for (int index = 0; index < size; index++) {
                        TradeItemDTO itemDTO = oldPreferential.get(index);
                        if (itemDTO.getSplitPrice().compareTo(BigDecimal.ZERO) <= 0){
                            continue;
                        }
                        // 参数错误直接return
                        if (CollectionUtils.isEmpty(itemDTO.getMarketingIds())) {
                            return;
                        }

                        CheckGoodsInfoVO preferentialSku = new CheckGoodsInfoVO();
                        preferentialSku.setGoodsInfoId(itemDTO.getSkuId());
                        preferentialSku.setPreferentialMarketingId(itemDTO.getMarketingIds().get(0));
                        preferentialSku.setStoreId(itemDTO.getStoreId());
                        // 处理只有加价购商品的最后一个
                        if (!skuIdList.contains(itemDTO.getSkuId()) && index == size-1) {
                            preferentialSku.setSplitPrice(splitPrice.subtract(alrPrice));
                        } else {
                            BigDecimal ratio = itemDTO.getSplitPrice().divide(oldPrice, 4, RoundingMode.DOWN);
                            BigDecimal newSplitPrice = splitPrice.multiply(ratio).setScale(2, RoundingMode.HALF_UP);
                            preferentialSku.setSplitPrice(newSplitPrice.compareTo(itemDTO.getSplitPrice()) <=0 ? newSplitPrice : itemDTO.getSplitPrice());
                        }
                        checkPreferentialSku.add(preferentialSku);
                        alrPrice = alrPrice.add(preferentialSku.getSplitPrice());
                    }
                    if (skuIdList.contains(skuVO.getGoodsInfoId())) {
                        skuVO.setSplitPrice(splitPrice.subtract(alrPrice));
                        checkGoodsInfos.add(skuVO);
                    }
                } else {
                    checkGoodsInfos.add(skuVO);
                }
            }
        }
        couponCheckoutResponse.setCheckPreferentialSku(checkPreferentialSku);
        couponCheckoutResponse.setCheckGoodsInfos(checkGoodsInfos);
    }

    /**
     * APP / H5 查询我的优惠券
     * @param request
     * @return
     */
    @Operation(summary = "查询我的优惠券")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public BaseResponse<CouponCodeSimplePageResponse> list(@RequestBody CouponCodeSimplePageRequest request){
        Operator operator = commonUtil.getOperator();
        request.setCustomerId(operator.getUserId());
        //是否第一次登陆
        if(Boolean.TRUE.equals(request.getFirstLoginShowFlag())
                && Boolean.FALSE.equals(operator.getFirstLogin())){
            return BaseResponse.success(CouponCodeSimplePageResponse.builder().build());
        }

        return couponCodeQueryProvider.simplePage(request);
    }

    /**
     * 根据商品自动领券
     * @param request
     * @return
     */
    @Operation(summary = "根据商品自动领券")
    @RequestMapping(value = "/auto-fetch-coupons", method = RequestMethod.POST)
    public BaseResponse autoFetchCoupons(@RequestBody @Valid CouponAutoFetchRequest request) {
        request.setCustomerId(commonUtil.getOperatorId());
        return couponCodeProvider.autoFetchCoupons(request);
    }

    /**
     * 订单确认页，自动选券
     * @param request
     * @return
     */
    @Operation(summary = "订单确认页，自动选券")
    @RequestMapping(value = "/auto-select-coupons-for-confirm", method = RequestMethod.POST)
    public BaseResponse<CouponCodeAutoSelectResponse> autoSelectCouponsForConfirm(@RequestBody @Valid CouponAutoSelectForConfirmRequest request) {
        String customerId = commonUtil.getOperatorId();
            TradeItemSnapshotVO tradeItemSnapshotVO =
                tradeItemQueryProvider.listByTerminalToken(TradeItemSnapshotByCustomerIdRequest
                        .builder().terminalToken(commonUtil.getTerminalToken()).build()).getContext().getTradeItemSnapshotVO();
        List<TradeItemGroupVO> items = tradeItemSnapshotVO.getItemGroups();
        TradeItemGroupVO tradeItemGroupVO = items.get(0);
        Long storeId = tradeItemGroupVO.getSupplier().getStoreId();

        // 砍价订单验证是否可以叠加优惠券使用
        if(Objects.equals(Boolean.TRUE, tradeItemSnapshotVO.getBargain())) {
            ConfigQueryRequest configQueryRequest = new ConfigQueryRequest();
            configQueryRequest.setConfigType(ConfigType.BARGIN_USE_COUPON.toValue());
            configQueryRequest.setDelFlag(DeleteFlag.NO.toValue());
            SystemConfigTypeResponse response =
                    systemConfigQueryProvider
                            .findByConfigTypeAndDelFlag(configQueryRequest)
                            .getContext();
            if (Objects.isNull(response.getConfig())
                    || Objects.equals(DefaultFlag.NO.toValue(), response.getConfig().getStatus())) {
                return BaseResponse.success(new CouponCodeAutoSelectResponse());
            }
        }

        // 定金预售，支付定金时无法使用优惠券，直接返回
        if (tradeItemGroupVO.getTradeItems().get(0).getIsBookingSaleGoods() && BookingType.EARNEST_MONEY == tradeItemGroupVO.getTradeItems().get(0).getBookingType()) {
            return BaseResponse.success(new CouponCodeAutoSelectResponse());
        }

        // 开店礼包商品无法使用优惠券，直接返回
        if (DefaultFlag.YES == tradeItemGroupVO.getStoreBagsFlag()) {
            return BaseResponse.success(new CouponCodeAutoSelectResponse());
        }

        CouponCodeAutoSelectForConfirmRequest autoSelectRequest = new CouponCodeAutoSelectForConfirmRequest();
        autoSelectRequest.setCustomCouponCodeIds(request.getCustomCouponCodeIds());
        autoSelectRequest.setStoreFreights(request.getStoreFreights());
        autoSelectRequest.setCustomerId(customerId);
        autoSelectRequest.setTerminalToken(commonUtil.getTerminalToken());
        return couponCodeQueryProvider.autoSelectForConfirm(autoSelectRequest);
    }

    /**
     * 购物车页，自动选券优惠明细
     * @param request
     * @return
     */
    @Operation(summary = "购物车页，自动选券优惠明细")
    @RequestMapping(value = "/auto-select-coupons-discount-detail", method = RequestMethod.POST)
    public BaseResponse<CouponDiscountDetailForCartResponse> autoSelectCouponsDiscountDetail(@RequestBody @Valid CouponAutoSelectForCartRequest request) {
        CouponDiscountDetailForCartResponse detailForCartResponse = new CouponDiscountDetailForCartResponse();
        // 加价购商品信息列表
        List<CouponSelectGoodsInfoDTO> preferentialGoodsInfos = Optional.ofNullable(request.getPreferentialGoodsInfos()).orElse(new ArrayList<>());
        // 查询当前用户已拥有且针对入参商品，能够使用的券列表
        List<String> skuIds = request.getGoodsInfos().stream().map(CouponSelectGoodsInfoDTO::getSkuId).collect(Collectors.toList());
        List<String> preferentialSkuIds = preferentialGoodsInfos.stream().map(CouponSelectGoodsInfoDTO::getSkuId).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(preferentialSkuIds)){
            skuIds.addAll(preferentialSkuIds);
            skuIds = skuIds.stream().distinct().collect(Collectors.toList());
            EsGoodsInfoQueryRequest esGoodsInfoQueryRequest = new EsGoodsInfoQueryRequest();
            esGoodsInfoQueryRequest.setGoodsInfoIds(preferentialSkuIds);
            EsGoodsInfoSimpleResponse response = esGoodsInfoElasticQueryProvider.skuPage(esGoodsInfoQueryRequest).getContext();
            GoodsListPluginRequest pluginRequest = new GoodsListPluginRequest();
            List<GoodsInfoSimpleVO> goodsInfoSimpleVos = WmCollectionUtils
                    .convert(response.getEsGoodsInfoPage().getContent().stream()
                                    .filter(goodsInfo -> DeleteFlag.NO == goodsInfo.getGoodsInfo().getDelFlag()).collect(Collectors.toList()),
                            this::convertBean);
            pluginRequest.setGoodsInfoPluginRequests(goodsInfoSimpleVos);
            String customerId = commonUtil.getOperatorId();
            if (StringUtils.isNotBlank(customerId)) {
                pluginRequest.setCustomerId(customerId);
            }
            pluginRequest.setPluginType(PluginType.NORMAL);
            pluginRequest.setTerminalSource(commonUtil.getTerminal().name());
            pluginRequest.setMarketingPluginType(MarketingPluginType.COUPON);
            pluginRequest.setHandlePosit(Boolean.FALSE);
            Map<String,List<MarketingPluginLabelDetailVO>> skuMarketingLabelMap =
                    newMarketingPluginProvider.commitPlugin(pluginRequest).getContext().getSkuMarketingLabelMap();
            List<String> couponIds =
                    skuMarketingLabelMap.values().stream().flatMap(Collection::stream)
                            .filter(g -> Objects.equals(g.getMarketingType(), MarketingPluginType.COUPON.getId()))
                            .map(MarketingPluginSimpleLabelVO::getMarketingId).map(Objects::toString).flatMap(t -> Arrays.stream(t.split(
                                    ","))).collect(Collectors.toList());
            request.getCouponIds().addAll(couponIds);
            request.setCouponIds(request.getCouponIds().stream().distinct().collect(Collectors.toList()));
        }
        // 组装ESGoodsInfo查询条件
        EsGoodsInfoQueryRequest goodsInfoQueryRequest = new EsGoodsInfoQueryRequest();
        goodsInfoQueryRequest.setGoodsInfoIds(skuIds);
        goodsInfoQueryRequest.setPageSize(skuIds.size());
        List<EsGoodsInfoVO> goodsInfos = esGoodsInfoElasticQueryProvider.skuPage(goodsInfoQueryRequest).getContext().getEsGoodsInfoPage().getContent();
        if (CollectionUtils.isEmpty(goodsInfos)) {
            // 商品不存在直接返回
            return BaseResponse.success(detailForCartResponse);
        }
        // 构造查询客户和商品的优惠券列表请求
        List<TradeItemInfoDTO> tradeItems = new ArrayList<>();

        Map<String, BigDecimal> skuIdToPriceMap =
                preferentialGoodsInfos.stream().collect(Collectors.toMap(CouponSelectGoodsInfoDTO::getSkuId,
                CouponSelectGoodsInfoDTO::getPrice, BigDecimal::add));
        Map<String, EsGoodsInfoVO> goodsInfoMap = goodsInfos.stream().collect(Collectors.toMap(EsGoodsInfoVO::getId, Function.identity()));
        request.getGoodsInfos().stream().filter(item -> goodsInfoMap.containsKey(item.getSkuId()))
                .forEach(item -> {
                    EsGoodsInfoVO esGoodsInfoVO = goodsInfoMap.get(item.getSkuId());
                    GoodsInfoNestVO goodsInfo = esGoodsInfoVO.getGoodsInfo();
                    TradeItemInfoDTO tradeItemInfoDTO = TradeItemInfoDTO.builder()
                            .skuId(item.getSkuId())
                            .price(item.getPrice())
                            // 填充其他商品信息
                            .spuId(goodsInfo.getGoodsId())
                            .storeId(goodsInfo.getStoreId())
                            .brandId(goodsInfo.getBrandId())
                            .cateId(goodsInfo.getCateId())
                            .storeCateIds(esGoodsInfoVO.getStoreCateIds()).build();
                    if (Objects.nonNull(skuIdToPriceMap.get(item.getSkuId()))){
                        tradeItemInfoDTO.setPrice(tradeItemInfoDTO.getPrice().add(skuIdToPriceMap.getOrDefault(item.getSkuId()
                                , new BigDecimal("0"))));
                        skuIdToPriceMap.remove(item.getSkuId());
                    }
                    tradeItems.add(tradeItemInfoDTO);
                });
        skuIdToPriceMap.forEach((k, v) -> {
            EsGoodsInfoVO esGoodsInfoVO = goodsInfoMap.get(k);
            GoodsInfoNestVO goodsInfo = esGoodsInfoVO.getGoodsInfo();
            TradeItemInfoDTO tradeItemInfoDTO = TradeItemInfoDTO.builder()
                    .skuId(k)
                    .price(v)
                    // 填充其他商品信息
                    .spuId(goodsInfo.getGoodsId())
                    .storeId(goodsInfo.getStoreId())
                    .brandId(goodsInfo.getBrandId())
                    .cateId(goodsInfo.getCateId())
                    .storeCateIds(esGoodsInfoVO.getStoreCateIds()).build();
            tradeItems.add(tradeItemInfoDTO);
        });
        // 构造券码列表
        List<CouponCodeVO> skuCouponCodeVos = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(request.getCouponIds())) {
            // 优惠券去重
            List<String> couponIds = request.getCouponIds().stream().distinct().collect(Collectors.toList());
            // 组装ESCouponInfo查询条件
            int maxResultWindow = 20000;
            EsCouponScopePageRequest couponInfoQueryRequest = new EsCouponScopePageRequest();
            couponInfoQueryRequest.setCouponIdList(couponIds);
            couponInfoQueryRequest.setPageSize(maxResultWindow);
            List<EsCouponScopeVO> couponInfos = esCouponScopeQueryProvider.page(couponInfoQueryRequest).getContext().getCouponScopes().getContent();
            // 筛选出进行中且有库存的优惠券活动
            LocalDateTime now = LocalDateTime.now();
            couponInfos = couponInfos.stream().filter(item -> DefaultFlag.YES == item.getHasLeft() && DefaultFlag.NO == item.getPauseFlag()
                    && item.getActivityStartTime().isBefore(now) && item.getActivityEndTime().isAfter(now)).collect(Collectors.toList());
            for (EsCouponScopeVO item : couponInfos) {
                // couponInfo信息拷贝到couponCode
                CouponCodeVO couponCodeVO = KsBeanUtil.convert(item, CouponCodeVO.class);
                // 生成券码ID（仅填充字段作为key，不会实际发券）
                couponCodeVO.setCouponCodeId(UUIDUtil.getUUID());
                // 创建和领取时间默认为当前
                couponCodeVO.setCreateTime(now);
                couponCodeVO.setAcquireTime(now);
                // 特殊处理起止时间类型为N天有效的券
                if (RangeDayType.DAYS == item.getRangeDayType()) {
                    // 若起止时间类型为N天有效，手动填充起止时间，逻辑和领券保持一致
                    // 开始时间未当前，结束时间为有效天日期23:59:59
                    couponCodeVO.setStartTime(now);
                    couponCodeVO.setEndTime(now.with(LocalTime.MIN).plusDays(item.getEffectiveDays()).minusSeconds(1));
                }
                skuCouponCodeVos.add(couponCodeVO);
            }
        }
        // 构造选券请求
        CouponCodeAutoSelectForCartRequest autoSelectRequest = new CouponCodeAutoSelectForCartRequest();
        autoSelectRequest.setStoreFreights(request.getStoreFreights());
        autoSelectRequest.setCustomCouponCodeIds(request.getCustomCouponCodeIds());
        autoSelectRequest.setSkuCouponCodeVos(skuCouponCodeVos);
        autoSelectRequest.setCustomerId(commonUtil.getOperatorId());
        autoSelectRequest.setTerminalToken(commonUtil.getTerminalToken());
        autoSelectRequest.setTradeItems(tradeItems);
        return couponCodeQueryProvider.autoSelectForCart(autoSelectRequest);
    }

    protected GoodsInfoSimpleVO convertBean(EsGoodsInfoVO bean) {
        GoodsInfoSimpleVO simpleVO =
                goodsInfoConvertMapper.goodsInfoNestVOToGoodsInfoSimpleVO(bean.getGoodsInfo());
        Integer isBuyCycle = bean.getGoodsInfo().getIsBuyCycle();
        simpleVO.setStoreCateIds(bean.getStoreCateIds());
        simpleVO.setIsBuyCycle(isBuyCycle);
        simpleVO.setEnterPriseAuditState(
                EnterpriseAuditState.values()[bean.getGoodsInfo().getEnterPriseAuditStatus()]);
        if (simpleVO.getPriceType() == GoodsPriceType.STOCK.toValue()) {
            simpleVO.setSalePrice(simpleVO.getIntervalMinPrice());
            simpleVO.setMarketPrice(simpleVO.getIntervalMinPrice());
        }
        if (Constants.yes.equals(isBuyCycle)) {
            BuyCycleGoodsInfoVO buyCycleGoodsInfoVO = buyCycleGoodsInfoQueryProvider.getById(BuyCycleGoodsInfoByGoodsIdRequest.builder()
                    .goodsInfoId(simpleVO.getGoodsInfoId())
                    .build()).getContext().getBuyCycleGoodsInfoVO();
            if (buyCycleGoodsInfoVO != null) {
                simpleVO.setSalePrice(simpleVO.getIntervalMinPrice());
                simpleVO.setMarketPrice(buyCycleGoodsInfoVO.getCyclePrice());
                simpleVO.setBuyPoint(BigDecimal.ZERO.longValue());
            }
        }
        return simpleVO;
    }
}
