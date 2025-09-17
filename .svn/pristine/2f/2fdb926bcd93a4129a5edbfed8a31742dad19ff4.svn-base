package com.wanmi.sbc.goods;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.AppointmentStatus;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.RequestSource;
import com.wanmi.sbc.common.enums.SortType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.customer.CustomerQueryProvider;
import com.wanmi.sbc.customer.api.request.customer.CustomerGetByIdRequest;
import com.wanmi.sbc.customer.api.response.customer.CustomerGetByIdResponse;
import com.wanmi.sbc.customer.bean.enums.CustomerErrorCodeEnum;
import com.wanmi.sbc.elastic.api.provider.sku.EsSkuQueryProvider;
import com.wanmi.sbc.elastic.api.request.sku.EsSkuPageRequest;
import com.wanmi.sbc.elastic.api.response.sku.EsSkuPageResponse;
import com.wanmi.sbc.goods.api.response.price.GoodsIntervalPriceByCustomerIdResponse;
import com.wanmi.sbc.goods.api.response.price.GoodsIntervalPriceResponse;
import com.wanmi.sbc.goods.bean.dto.GoodsInfoDTO;
import com.wanmi.sbc.goods.bean.enums.CheckStatus;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.goods.service.GoodsBaseService;
import com.wanmi.sbc.intervalprice.GoodsIntervalPriceService;
import com.wanmi.sbc.marketing.api.provider.appointmentsalegoods.AppointmentSaleGoodsQueryProvider;
import com.wanmi.sbc.marketing.api.provider.bookingsalegoods.BookingSaleGoodsQueryProvider;
import com.wanmi.sbc.marketing.api.provider.newcomerpurchasecoupon.NewcomerPurchaseCouponQueryProvider;
import com.wanmi.sbc.marketing.api.provider.plugin.MarketingLevelPluginProvider;
import com.wanmi.sbc.marketing.api.request.appointmentsale.AppointmentGoodsInfoSimplePageRequest;
import com.wanmi.sbc.marketing.api.request.bookingsale.BookingGoodsInfoSimplePageRequest;
import com.wanmi.sbc.marketing.api.request.newcomerpurchasecoupon.NewcomerPurchaseCouponGetFetchRequest;
import com.wanmi.sbc.marketing.api.request.plugin.MarketingLevelGoodsListFilterRequest;
import com.wanmi.sbc.marketing.api.response.appointmentsalegoods.AppointmentResponse;
import com.wanmi.sbc.marketing.api.response.bookingsalegoods.BookingResponse;
import com.wanmi.sbc.marketing.bean.enums.ScopeType;
import com.wanmi.sbc.marketing.bean.vo.CouponInfoVO;
import com.wanmi.sbc.system.service.SystemPointsConfigService;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Author: gaomuwei
 * @Date: Created In 下午4:08 2019/2/26
 * @Description: 平台端单品服务
 */
@RestController
@Validated
@Tag(name =  "平台端单品服务", description =  "BossGoodsInfoController")
public class BossGoodsInfoController {

    @Autowired
    private CustomerQueryProvider customerQueryProvider;

    @Autowired
    private GoodsIntervalPriceService goodsIntervalPriceService;


    @Autowired
    private MarketingLevelPluginProvider marketingLevelPluginProvider;

    @Autowired
    private EsSkuQueryProvider esSkuQueryProvider;

    @Autowired
    private SystemPointsConfigService systemPointsConfigService;

    @Autowired
    private NewcomerPurchaseCouponQueryProvider newcomerPurchaseCouponQueryProvider;

    @Autowired
    private GoodsBaseService goodsBaseService;

    @Autowired
    private AppointmentSaleGoodsQueryProvider appointmentSaleGoodsQueryProvider;

    @Autowired
    private BookingSaleGoodsQueryProvider bookingSaleGoodsQueryProvider;

    /**
     * 分页显示商品
     *
     * @param queryRequest 商品
     * @return 商品详情
     */
    @Operation(summary = "分页显示商品")
    @RequestMapping(value = "/goods/skus", method = RequestMethod.POST)
    public BaseResponse<EsSkuPageResponse> skuList(@RequestBody EsSkuPageRequest queryRequest) {
        //获取会员
        CustomerGetByIdResponse customer = null;
        if (StringUtils.isNotBlank(queryRequest.getCustomerId())) {
            customer = customerQueryProvider.getCustomerById(new CustomerGetByIdRequest(queryRequest.getCustomerId())
            ).getContext();
            if (Objects.isNull(customer)) {
                throw new SbcRuntimeException(CustomerErrorCodeEnum.K010001);
            }
        }

        // 代客下单时，积分开关开启 并且 积分使用方式是订单抵扣，此时不需要过滤积分价商品
        if (Boolean.TRUE.equals(queryRequest.getIntegralPriceFlag()) && !systemPointsConfigService.isGoodsPoint()){
            queryRequest.setIntegralPriceFlag(Boolean.FALSE);
        }

        // 按创建时间倒序、ID升序
        if (Objects.nonNull(queryRequest.getSortColumn())) {
            queryRequest.putSort(queryRequest.getSortColumn(),queryRequest.getSortRole());
        } else if (MapUtils.isEmpty(queryRequest.getSortMap())) {
            queryRequest.putSort("addedTime", SortType.DESC.toValue());
        }
        queryRequest.setDelFlag(DeleteFlag.NO.toValue());//可用
        queryRequest.setAuditStatus(CheckStatus.CHECKED);//已审核
        queryRequest.setVendibility(Constants.yes);
        queryRequest.setShowPointFlag(Boolean.TRUE);
        queryRequest.setShowProviderInfoFlag(Boolean.TRUE);
        queryRequest.setFillLmInfoFlag(Boolean.TRUE);
        queryRequest.setShowPointFlag(Boolean.TRUE);
        // 提货卡商品只能是实物商品
        if (Objects.nonNull(queryRequest.getRequestSource())
                && RequestSource.PICKUP_CARD.equals(queryRequest.getRequestSource())) {
            queryRequest.setGoodsType(0);
        }
        EsSkuPageResponse response = esSkuQueryProvider.page(queryRequest).getContext();

        List<GoodsInfoVO> goodsInfoVOList = response.getGoodsInfoPage().getContent();

        if (customer != null && StringUtils.isNotBlank(customer.getCustomerId())) {
            GoodsIntervalPriceByCustomerIdResponse priceResponse =
                    goodsIntervalPriceService.getGoodsIntervalPriceVOList(goodsInfoVOList, customer.getCustomerId());
            response.setGoodsIntervalPrices(priceResponse.getGoodsIntervalPriceVOList());
            goodsInfoVOList = priceResponse.getGoodsInfoVOList();
        } else {
            GoodsIntervalPriceResponse priceResponse =
                    goodsIntervalPriceService.getGoodsIntervalPriceVOList(goodsInfoVOList);
            response.setGoodsIntervalPrices(priceResponse.getGoodsIntervalPriceVOList());
            goodsInfoVOList = priceResponse.getGoodsInfoVOList();
        }

        //计算会员价
        if (customer != null && StringUtils.isNotBlank(customer.getCustomerId())) {
            goodsInfoVOList =
                    marketingLevelPluginProvider
                            .goodsListFilter(
                                    MarketingLevelGoodsListFilterRequest.builder()
                                            .customerId(customer.getCustomerId())
                                            .goodsInfos(
                                                    KsBeanUtil.convert(
                                                            goodsInfoVOList, GoodsInfoDTO.class))
                                            .build())
                            .getContext()
                            .getGoodsInfoVOList();
        }
        Boolean isNewCustomerGoods = queryRequest.getIsNewCustomerGoods();
        //如果是新人购的请求，需要判定优惠券的商品范围
        if (Boolean.TRUE.equals(isNewCustomerGoods)) {
            List<CouponInfoVO> coupons = newcomerPurchaseCouponQueryProvider
                    .getFetchCoupons(NewcomerPurchaseCouponGetFetchRequest.builder().build())
                    .getContext().getCoupons();
            if (CollectionUtils.isEmpty(coupons)) {
                goodsInfoVOList.forEach(goodsInfoVO -> goodsInfoVO.setDisabled(Boolean.TRUE));
            } else {
                Optional<CouponInfoVO> optional = coupons.parallelStream().filter(couponInfoVO -> ScopeType.ALL.equals(couponInfoVO.getScopeType()))
                        .findFirst();
                //如果不存在全部商品的优惠券，则按照其他范围处理
                if (!optional.isPresent()) {
                    goodsInfoVOList.forEach(goodsInfoVO -> coupons.forEach(couponInfoVO -> {
                        ScopeType scopeType = couponInfoVO.getScopeType();
                        List<String> scopeIds = couponInfoVO.getScopeIds();
                        switch (scopeType) {
                            case BOSS_CATE:
                                Long cateId = goodsInfoVO.getCateId();
                                if (scopeIds.contains(String.valueOf(cateId))) {
                                    //设置为可以选择
                                    goodsInfoVO.setDisabled(Boolean.FALSE);
                                }
                                break;
                            case BRAND:
                                Long brandId = goodsInfoVO.getBrandId();
                                if (scopeIds.contains(String.valueOf(brandId))) {
                                    //设置为可以选择
                                    goodsInfoVO.setDisabled(Boolean.FALSE);
                                }
                                break;
                            case SKU:
                                String goodsInfoId = goodsInfoVO.getGoodsInfoId();
                                if (scopeIds.contains(goodsInfoId)) {
                                    //设置为可以选择
                                    goodsInfoVO.setDisabled(Boolean.FALSE);
                                }
                                break;
                            default:
                                break;
                        }
                    }));
                    //余下没有设置的只能是不能选择，不再优惠券的范围内
                    goodsInfoVOList.forEach(goodsInfoVO -> {
                        if (Objects.isNull(goodsInfoVO.getDisabled())) {
                            goodsInfoVO.setDisabled(Boolean.TRUE);
                        }
                    });
                } else {
                    //全部设置为可以选择
                    goodsInfoVOList.forEach(goodsInfoVO -> goodsInfoVO.setDisabled(Boolean.FALSE));
                }
            }
        }

        //填充marketingGoodsStatus属性
        goodsBaseService.populateMarketingGoodsStatus(goodsInfoVOList);

        // 创建提货卡的请求商品标记预约、预售标志
        if (Objects.nonNull(queryRequest.getRequestSource())
                && RequestSource.PICKUP_CARD.equals(queryRequest.getRequestSource())){
            Map<String, GoodsInfoVO> skuIdToInfoMap =
                    goodsInfoVOList.stream().collect(Collectors.toMap(GoodsInfoVO::getGoodsInfoId,
                            Function.identity()));

            List<String> goodsInfoIds =
                    goodsInfoVOList.stream().map(GoodsInfoVO::getGoodsInfoId).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(goodsInfoIds)){
                // 预售
                BookingResponse bookingResponse =
                        bookingSaleGoodsQueryProvider.pageBoss(BookingGoodsInfoSimplePageRequest.builder()
                                        .goodsInfoIds(goodsInfoIds).queryTab(AppointmentStatus.RUNNING_SUSPENDED).build())
                                .getContext();
                if (Objects.nonNull(bookingResponse.getBookingVOMicroServicePage())){
                    bookingResponse.getBookingVOMicroServicePage().getContent().forEach(g -> {
                        if (Objects.nonNull(g)) {
                            skuIdToInfoMap.get(g.getBookingSaleGoods().getGoodsInfoId()).setBookingSaleFlag(Boolean.TRUE);
                        }

                    });
                }

                // 预约
                AppointmentResponse appointmentResponse =
                        appointmentSaleGoodsQueryProvider.pageBoss(AppointmentGoodsInfoSimplePageRequest.builder()
                                        .goodsInfoIds(goodsInfoIds).queryTab(AppointmentStatus.RUNNING_SUSPENDED).build())
                                .getContext();
                if (Objects.nonNull(appointmentResponse.getAppointmentVOPage())){
                    appointmentResponse.getAppointmentVOPage().getContent().forEach(g -> {
                        if (Objects.nonNull(g)) {
                            skuIdToInfoMap.get(g.getAppointmentSaleGoods().getGoodsInfoId()).setAppointmentSaleFlag(Boolean.TRUE);
                        }
                    });
                }
            }
        }
        systemPointsConfigService.clearBuyPointsForGoodsInfoVO(goodsInfoVOList);
        response.setGoodsInfoPage(new MicroServicePage<>(goodsInfoVOList, queryRequest.getPageRequest(),
                response.getGoodsInfoPage().getTotalElements()));
        return BaseResponse.success(response);
    }


}
