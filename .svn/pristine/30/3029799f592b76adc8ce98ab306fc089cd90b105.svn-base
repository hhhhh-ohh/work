package com.wanmi.sbc.marketing.provider.impl.coupon;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.plugin.annotation.RoutingResource;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.store.ListStoreByNameRequest;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.marketing.api.provider.coupon.CouponCodeQueryProvider;
import com.wanmi.sbc.marketing.api.request.coupon.*;
import com.wanmi.sbc.marketing.api.response.coupon.*;
import com.wanmi.sbc.marketing.bean.dto.CouponCodeAutoSelectDTO;
import com.wanmi.sbc.marketing.bean.dto.CouponCodeDTO;
import com.wanmi.sbc.marketing.bean.enums.CouponMarketingType;
import com.wanmi.sbc.marketing.bean.enums.CouponType;
import com.wanmi.sbc.marketing.bean.enums.QueryCouponType;
import com.wanmi.sbc.marketing.bean.vo.CouponCodeVO;
import com.wanmi.sbc.marketing.common.mapper.TradeItemInfoMapper;
import com.wanmi.sbc.marketing.coupon.model.entity.TradeCouponSnapshot;
import com.wanmi.sbc.marketing.coupon.model.root.CouponCode;
import com.wanmi.sbc.marketing.coupon.request.CouponCodeListForUseRequest;
import com.wanmi.sbc.marketing.coupon.response.CouponCodeQueryResponse;
import com.wanmi.sbc.marketing.coupon.service.CouponCodeService;
import com.wanmi.sbc.marketing.coupon.service.CouponCodeServiceInterface;
import com.wanmi.sbc.marketing.coupon.service.TradeCouponSnapshotService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import jakarta.validation.Valid;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>对优惠券码查询接口</p>
 * Created by daiyitian on 2018-11-23-下午6:23.
 */
@Validated
@RestController
public class CouponCodeQueryController implements CouponCodeQueryProvider {

    @Autowired
    private CouponCodeService couponCodeService;
    @Autowired
    private TradeItemInfoMapper tradeItemInfoMapper;
    @Autowired
    private TradeCouponSnapshotService tradeCouponSnapshotService;
    @Autowired
    private CouponCodeServiceInterface couponCodeServiceInterface;
    @Autowired
    private StoreQueryProvider storeQueryProvider;

    /**
     * 根据客户id查询使用优惠券列表
     *
     * @param request 包含客户id查询请求结构 {@link CouponCodeListForUseByCustomerIdRequest}
     * @return 优惠券列表 {@link CouponCodeListForUseByCustomerIdResponse}
     */
    @Override
    public BaseResponse<CouponCodeListForUseByCustomerIdResponse> listForUseByCustomerId(@RequestBody @Valid
                                                                                                 CouponCodeListForUseByCustomerIdRequest
                                                                                                 request) {
        com.wanmi.sbc.marketing.coupon.request.CouponCodePageRequest couponCodePageRequest = new com.wanmi.sbc.marketing.coupon.request.CouponCodePageRequest();
        buildPageRequest(request.getQueryCouponType(), couponCodePageRequest);
        CouponCodeListForUseRequest useRequest = new CouponCodeListForUseRequest();
        useRequest.setCustomerId(request.getCustomerId());
        useRequest.setTerminalToken(request.getTerminalToken());
        useRequest.setTradeItems(tradeItemInfoMapper.tradeItemInfoDTOsToTradeItemInfos(request.getTradeItems()));
        useRequest.setStoreId(request.getStoreId());
        useRequest.setPrice(request.getPrice());
        useRequest.setCouponMarketingTypes(request.getCouponMarketingTypes());
        useRequest.setCouponType(couponCodePageRequest.getCouponType());
        useRequest.setCouponMarketingType(couponCodePageRequest.getCouponMarketingType());
        useRequest.setSelectedCouponCodeIds(request.getSelectedCouponCodeIds());
        useRequest.setStoreFreights(request.getStoreFreights());
        couponCodeService.queryStoreTypeForAspect(useRequest);
        List<CouponCodeVO> voList = couponCodeServiceInterface.listCouponCodeForUse(useRequest);
        return BaseResponse.success(CouponCodeListForUseByCustomerIdResponse.builder().couponCodeList(voList).build());
    }

    /**
     * 订单确认页-自动选券
     * @param request
     * @return
     */
    @Override
    public BaseResponse<CouponCodeAutoSelectResponse> autoSelectForConfirm(@RequestBody @Valid CouponCodeAutoSelectForConfirmRequest request) {
        TradeCouponSnapshot checkInfo = tradeCouponSnapshotService.getByTerminalToken(request.getTerminalToken());
        List<CouponCodeAutoSelectDTO> allSelectCoupons;

        // 根据用户自选券 couponCodeIds 是否有值，决定走完整的自动选券逻辑，还是部分自动选券逻辑
        if (Objects.isNull(request.getCustomCouponCodeIds())) {
            allSelectCoupons = couponCodeService.autoSelectCoupons(checkInfo, request.getStoreFreights());
        } else {
            allSelectCoupons = couponCodeService.autoSelectCouponsWithCustom(checkInfo, request.getCustomCouponCodeIds(), request.getStoreFreights());
        }

        // 区分满减/折券列表
        // 组装 满折/满减券 抵扣金额
        List<CouponCodeAutoSelectDTO> selectCoupons = allSelectCoupons.stream().filter(CouponCodeVO::isFullAmount).collect(Collectors.toList());
        BigDecimal sumActualDiscount = selectCoupons.stream().map(CouponCodeAutoSelectDTO::getActualDiscount).reduce(BigDecimal.ZERO, BigDecimal::add);

        // 区分运费券列表
        // 组装 运费券 抵扣金额
        List<CouponCodeAutoSelectDTO> selectFreightCoupons = allSelectCoupons.stream().filter(CouponCodeVO::isFreight).collect(Collectors.toList());
        BigDecimal sumFreightActualDiscount = selectFreightCoupons.stream().map(CouponCodeAutoSelectDTO::getActualDiscount).reduce(BigDecimal.ZERO, BigDecimal::add);

        // 总实际抵扣
        BigDecimal totalActualDiscount = BigDecimal.ZERO.add(sumActualDiscount).add(sumFreightActualDiscount);

        return BaseResponse.success(CouponCodeAutoSelectResponse.builder()
                .selectCoupons(selectCoupons)
                .selectFreightCoupons(selectFreightCoupons)
                .sumActualDiscount(sumActualDiscount)
                .sumFreightActualDiscount(sumFreightActualDiscount)
                .totalActualDiscount(totalActualDiscount).build());
    }

    /**
     * 购物车页-自动选券-优惠明细
     * @param selectRequest
     * @return
     */
    @Override
    public BaseResponse<CouponDiscountDetailForCartResponse> autoSelectForCart(@RequestBody @Valid CouponCodeAutoSelectForCartRequest selectRequest) {
        CouponDiscountDetailForCartResponse detailForCartResponse = new CouponDiscountDetailForCartResponse();
        CouponCodeListForUseRequest useRequest = new CouponCodeListForUseRequest();
        useRequest.setCustomerId(selectRequest.getCustomerId());
        useRequest.setTradeItems(tradeItemInfoMapper.tradeItemInfoDTOsToTradeItemInfos(selectRequest.getTradeItems()));
        useRequest.setStoreId(selectRequest.getStoreId());
        useRequest.setPrice(selectRequest.getPrice());
        couponCodeService.queryStoreTypeForAspect(useRequest);

        // 收集SKU维度优惠券ID集合，key为[activityId_couponId]唯一确定一张券
        Set<String> skuCouponIds = selectRequest.getSkuCouponCodeVos().stream()
                .map(item -> String.format("%s_%s", item.getActivityId(), item.getCouponId())).collect(Collectors.toSet());

        // 购物车选券
        List<CouponCodeAutoSelectDTO> allSelectCoupons = couponCodeService.autoSelectForCart(useRequest, selectRequest);

        // 筛选出已领券key的集合
        Set<String> hasFetchedCouponIds = selectRequest.getHasFetchedCouponIds();

        // 区分 满减/折券列表 和 已领/待领抵扣
        BigDecimal fetchedDiscount = BigDecimal.ZERO;
        BigDecimal waitFetchedDiscount = BigDecimal.ZERO;
        List<CouponCodeAutoSelectDTO> selectCoupons = allSelectCoupons.stream()
                .filter(item -> CouponMarketingType.REDUCTION_COUPON == item.getCouponMarketingType()
                        || CouponMarketingType.DISCOUNT_COUPON == item.getCouponMarketingType()).collect(Collectors.toList());
        for (CouponCodeAutoSelectDTO item : selectCoupons) {
            String couponKey = String.format("%s_%s", item.getActivityId(), item.getCouponId());
            BigDecimal actualDiscount = item.getActualDiscount();
            if (hasFetchedCouponIds.contains(couponKey)) {
                // 满减/折券已领
                fetchedDiscount = fetchedDiscount.add(actualDiscount);
            } else {
                // 满减/折券待领
                waitFetchedDiscount = waitFetchedDiscount.add(actualDiscount);
            }
        }

        // 区分 运费券列表 和 已领/待领抵扣
        BigDecimal fetchedFreightDiscount = BigDecimal.ZERO;
        BigDecimal waitFetchedFreightDiscount = BigDecimal.ZERO;
        List<CouponCodeAutoSelectDTO> selectFreightCoupons = allSelectCoupons.stream()
                .filter(item -> CouponMarketingType.FREIGHT_COUPON == item.getCouponMarketingType()).collect(Collectors.toList());
        for (CouponCodeAutoSelectDTO item : selectFreightCoupons) {
            String couponKey = String.format("%s_%s", item.getActivityId(), item.getCouponId());
            BigDecimal actualDiscount = item.getActualDiscount();
            if (hasFetchedCouponIds.contains(couponKey)) {
                // 运费券已领
                fetchedFreightDiscount = fetchedFreightDiscount.add(actualDiscount);
            } else {
                // 运费券待领
                waitFetchedFreightDiscount = waitFetchedFreightDiscount.add(actualDiscount);
            }
        }

        // 组装券列表
        detailForCartResponse.setSelectCoupons(selectCoupons);
        detailForCartResponse.setSelectFreightCoupons(selectFreightCoupons);
        // 组装 满折/满减券 抵扣金额
        detailForCartResponse.setFetchedDiscount(fetchedDiscount);
        detailForCartResponse.setWaitFetchedDiscount(waitFetchedDiscount);
        detailForCartResponse.setSumActualDiscount(fetchedDiscount.add(waitFetchedDiscount));
        // 组装 运费费券 抵扣金额
        detailForCartResponse.setFetchedFreightDiscount(fetchedFreightDiscount);
        detailForCartResponse.setWaitFetchedFreightDiscount(waitFetchedFreightDiscount);
        detailForCartResponse.setSumFreightActualDiscount(fetchedFreightDiscount.add(waitFetchedFreightDiscount));
        // 总实际优惠金额
        detailForCartResponse.setTotalActualDiscount(
                detailForCartResponse.getSumActualDiscount().add(detailForCartResponse.getSumFreightActualDiscount()));
        // 判断是否存在未领优惠券（用于标识C端是否展示"领券结算"），若SKU维度优惠券和已领优惠券的差集非空，即存在未领优惠券
        Boolean hasWaitFetchedFlag = !CollectionUtils.subtract(skuCouponIds, hasFetchedCouponIds).isEmpty();
        detailForCartResponse.setHasWaitFetchedFlag(hasWaitFetchedFlag);
        return BaseResponse.success(detailForCartResponse);
    }

    /**
     * 分页查询优惠券列表
     *
     * @param request 分页查询优惠券列表请求结构 {@link CouponCodePageRequest}
     * @return 优惠券分页列表 {@link CouponCodePageResponse}
     */
    @Override
    public BaseResponse<CouponCodePageResponse> page(@RequestBody @Valid CouponCodePageRequest request) {
        com.wanmi.sbc.marketing.coupon.request.CouponCodePageRequest pageRequest = KsBeanUtil.convert(request,
                com.wanmi.sbc.marketing.coupon.request.CouponCodePageRequest.class);

        this.buildPageRequest(request.getQueryCouponType(), pageRequest);
        if (StringUtils.isNotBlank(request.getStoreName())) {
            List<StoreVO> storeVOList = storeQueryProvider.listByName(ListStoreByNameRequest.builder().storeName(request.getStoreName()).build()).getContext().getStoreVOList();
            List<Long> storeIds = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(storeVOList)) {
                storeIds = storeVOList.stream().map(StoreVO::getStoreId).collect(Collectors.toList());
            } else {
                storeIds.add(-99L);
            }
            pageRequest.setStoreIds(storeIds);
        }


        CouponCodeQueryResponse queryResponse = couponCodeService.listMyCouponList(pageRequest);
        return BaseResponse.success(CouponCodePageResponse.builder()
                .couponCodeVos(KsBeanUtil.convertPage(queryResponse.getCouponCodeVos(), CouponCodeVO.class))
                .overDueCount(queryResponse.getOverDueCount())
                .unUseCount(queryResponse.getUnUseCount())
                .usedCount(queryResponse.getUsedCount())
                .build());
    }

    private void buildPageRequest(QueryCouponType queryCouponType, com.wanmi.sbc.marketing.coupon.request.CouponCodePageRequest pageRequest) {
        // 处理券平台类型和营销类型
        if (Objects.nonNull(queryCouponType)) {
            switch (queryCouponType) {
                case GENERAL_REDUCTION: {
                    // 通用+满减券
                    pageRequest.setCouponType(CouponType.GENERAL_VOUCHERS);
                    pageRequest.setCouponMarketingType(CouponMarketingType.REDUCTION_COUPON);
                    break;
                }
                case STORE_REDUCTION: {
                    // 店铺+满减券
                    pageRequest.setCouponType(CouponType.STORE_VOUCHERS);
                    pageRequest.setCouponMarketingType(CouponMarketingType.REDUCTION_COUPON);
                    break;
                }
                case STORE_DISCOUNT: {
                    // 店铺+满折券
                    pageRequest.setCouponType(CouponType.STORE_VOUCHERS);
                    pageRequest.setCouponMarketingType(CouponMarketingType.DISCOUNT_COUPON);
                    break;
                }
                case STORE_FREIGHT: {
                    // 店铺+运费券
                    pageRequest.setCouponType(CouponType.STORE_VOUCHERS);
                    pageRequest.setCouponMarketingType(CouponMarketingType.FREIGHT_COUPON);
                    break;
                }
                default: break;
            }
        }
    }

    /**
     * 分页查询优惠券列表
     *
     * @param request 分页查询优惠券列表请求结构 {@link CouponCodePageRequest}
     * @return 优惠券分页列表 {@link CouponCodePageResponse}
     */
    @Override
    public BaseResponse<CouponCodeSimplePageResponse> simplePage(@RequestBody @Valid CouponCodeSimplePageRequest request) {
        Page<CouponCodeVO> queryResponse = couponCodeService.pageMyCouponList(
                KsBeanUtil.convert(request, com.wanmi.sbc.marketing.coupon.request.CouponCodePageRequest.class));
        return BaseResponse.success(CouponCodeSimplePageResponse.builder()
                .couponCodeVos(KsBeanUtil.convertPage(queryResponse, CouponCodeVO.class))
                .build());
    }

    /**
     * 根据客户和券码id查询不可用的平台券以及优惠券实际优惠总额的请求结构
     *
     * @param request 包含客户和券码id的查询请求结构 {@link CouponCheckoutRequest}
     * @return 操作结果 {@link CouponCheckoutResponse}
     */
    @Override
    public BaseResponse<CouponCheckoutResponse> checkout(@RequestBody @Valid CouponCheckoutRequest request) {
        return BaseResponse.success(couponCodeServiceInterface.checkoutCoupons(request));
    }

    /**
     * 根据条件查询优惠券码列表
     *
     * @param request
     * @return
     */
    @Override
    public BaseResponse<CouponCodeListByConditionResponse> listCouponCodeByCondition(@RequestBody @Valid CouponCodeQueryRequest request) {
        List<CouponCode> couponCodeList = couponCodeService.listCouponCodeByCondition(request);
        CouponCodeListByConditionResponse response = new CouponCodeListByConditionResponse();
        response.setCouponCodeList(KsBeanUtil.copyListProperties(couponCodeList, CouponCodeDTO.class));
        return BaseResponse.success(response);
    }

    /**
     * 根据条件分頁查询优惠券码列表
     *
     * @param request
     * @return
     */
    @Override
    public BaseResponse<CouponCodePageByConditionResponse> pageCouponCodeByCondition(@RequestBody @Valid CouponCodeQueryRequest request) {
        Page<CouponCode> couponCodePage = couponCodeService.pageCouponCodeByCondition(request);
        Page<CouponCodeDTO> newPage = couponCodePage.map(couponCode -> KsBeanUtil.convert(couponCode, CouponCodeDTO.class));
        return BaseResponse.success(CouponCodePageByConditionResponse.builder().couponCodeDTOPage(newPage.getContent()).build());
    }


    /**
     * 根据条件分頁查询优惠券码列表
     *
     * @param request
     * @return
     */
    @Override
    public BaseResponse<CouponCodePageByConditionResponse> pageCouponCodeByCondition_test(@RequestBody @Valid CouponCodeQueryRequest request) {

        Page<CouponCode> couponCodePage = couponCodeService.pageCouponCodeByCondition_test(request);

        Page<CouponCodeDTO> newPage = couponCodePage.map(couponCode -> KsBeanUtil.convert(couponCode, CouponCodeDTO.class));
        return BaseResponse.success(CouponCodePageByConditionResponse.builder().couponCodeDTOPage(newPage.getContent()).build());
    }


    /**
     * 根据条件查询优惠券码列表
     *
     * @param request
     * @return
     */
    @Override
    public BaseResponse<CouponCodeListNotUseResponse> listNotUseStatus(@RequestBody @Valid CouponCodeQueryNotUseRequest request) {
        CouponCodeQueryRequest codeQueryRequest = KsBeanUtil.convert(request, CouponCodeQueryRequest.class);
        List<CouponCode> couponCodeList = couponCodeService.findNotUseStatusCouponCode(codeQueryRequest);
        CouponCodeListNotUseResponse response = new CouponCodeListNotUseResponse();
        response.setCouponCodeList(KsBeanUtil.copyListProperties(couponCodeList, CouponCodeVO.class));
        return BaseResponse.success(response);
    }

    @Override
    public BaseResponse<CouponCodeValidOrderCommitResponse> validOrderCommit(@Valid CouponCodeValidOrderCommitRequest request) {
        CouponCodeValidOrderCommitResponse response = couponCodeService.validOrderCommit(request);
        return BaseResponse.success(response);
    }
}
