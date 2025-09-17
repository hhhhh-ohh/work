package com.wanmi.sbc.newcomer;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.constant.RedisKeyConstant;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsInfoQueryRequest;
import com.wanmi.sbc.elastic.api.response.goods.EsGoodsInfoSimpleResponse;
import com.wanmi.sbc.goods.api.provider.newcomerpurchasegoods.NewcomerPurchaseGoodsQueryProvider;
import com.wanmi.sbc.goods.api.request.newcomerpurchasegoods.NewcomerPurchaseGoodsListRequest;
import com.wanmi.sbc.goods.bean.vo.NewcomerPurchaseGoodsVO;
import com.wanmi.sbc.goods.response.GoodsInfoListResponse;
import com.wanmi.sbc.goods.service.GoodsBaseService;
import com.wanmi.sbc.goods.service.list.GoodsListInterface;
import com.wanmi.sbc.marketing.api.provider.newcomerpurchaseconfig.NewcomerPurchaseConfigQueryProvider;
import com.wanmi.sbc.marketing.api.provider.newcomerpurchasecoupon.NewcomerPurchaseCouponProvider;
import com.wanmi.sbc.marketing.api.provider.newcomerpurchasecoupon.NewcomerPurchaseCouponQueryProvider;
import com.wanmi.sbc.marketing.api.request.newcomerpurchaseconfig.NewcomerPurchaseDetailRequest;
import com.wanmi.sbc.marketing.api.request.newcomerpurchasecoupon.NewcomerPurchaseCouponFetchRequest;
import com.wanmi.sbc.marketing.api.request.newcomerpurchasecoupon.NewcomerPurchaseCouponGetFetchRequest;
import com.wanmi.sbc.marketing.api.request.newcomerpurchasecoupon.NewcomerPurchaseXsiteRequest;
import com.wanmi.sbc.marketing.api.response.newcomerpurchaseconfig.NewcomerPurchaseDetailResponse;
import com.wanmi.sbc.marketing.bean.vo.CouponInfoVO;
import com.wanmi.sbc.newcomer.response.NewcomerPurchaseDetailMobileResponse;
import com.wanmi.sbc.newcomer.response.NewcomerPurchaseXsiteResponse;
import com.wanmi.sbc.util.CommonUtil;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xuyunpeng
 * @className NewComerPurchaseController
 * @description 新人专享
 * @date 2022/8/22 10:15 AM
 **/
@Tag(name =  "新人专享API", description =  "NewComerMobileController")
@Slf4j
@RestController
@Validated
@RequestMapping(value = "/newcomer/purchase")
public class NewComerMobileController {

    @Autowired
    private NewcomerPurchaseConfigQueryProvider newcomerPurchaseConfigQueryProvider;

    @Autowired
    private GoodsBaseService goodsBaseService;

    @Resource(name = "goodsInfoListService")
    private GoodsListInterface<EsGoodsInfoQueryRequest, EsGoodsInfoSimpleResponse> goodsListInterface;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private NewcomerPurchaseCouponProvider newcomerPurchaseCouponProvider;

    @Autowired
    private NewcomerPurchaseCouponQueryProvider newcomerPurchaseCouponQueryProvider;

    @Autowired
    private NewcomerPurchaseGoodsQueryProvider newcomerPurchaseGoodsQueryProvider;

    @Autowired
    private RedissonClient redissonClient;

    @Operation(summary = "新人专享页")
    @GetMapping(value = "/detail")
    public BaseResponse<NewcomerPurchaseDetailMobileResponse> detail(){
        String customerId = commonUtil.getOperatorId();
        NewcomerPurchaseDetailResponse response = newcomerPurchaseConfigQueryProvider
                .detailForMobile(NewcomerPurchaseDetailRequest.builder().customerId(customerId).build()).getContext();
        NewcomerPurchaseDetailMobileResponse mobileResponse = KsBeanUtil.convert(response, NewcomerPurchaseDetailMobileResponse.class);

        if (response != null && CollectionUtils.isNotEmpty(response.getSkuIds())) {
            EsGoodsInfoQueryRequest queryRequest = new EsGoodsInfoQueryRequest();
            queryRequest.setCustomerId(commonUtil.getOperatorId());
            queryRequest.setGoodsInfoIds(response.getSkuIds());
            queryRequest.setPageSize(response.getSkuIds().size());
            queryRequest.setSortFlag(NumberUtils.INTEGER_ZERO);
            GoodsInfoListResponse goodsInfoListResponse = goodsBaseService.skuListConvert(goodsListInterface.getList(queryRequest), queryRequest.getSortFlag());
            mobileResponse.setGoods(goodsInfoListResponse.getGoodsInfoPage().getContent());
        }
        return BaseResponse.success(mobileResponse);
    }

    @Operation(summary = "领取优惠券")
    @PostMapping(value = "/fetch")
    public BaseResponse fetchCoupon(){
        String customerId = commonUtil.getOperatorId();
        //redis锁
        RLock rLock = redissonClient.getFairLock(RedisKeyConstant.NEWCOMER_STOCK_FETCH.concat(customerId));
        rLock.lock();
        try {
            newcomerPurchaseCouponProvider.fetchCoupon(NewcomerPurchaseCouponFetchRequest.builder().customerId(customerId).build());
        } catch (Exception e) {
            throw e;
        } finally {
            rLock.unlock();
        }
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 魔方组件查询新人券和商品
     * @param request
     * @return
     */
    @Operation(summary = "魔方组件查询新人券和商品")
    @PostMapping(value = "/xsite")
    public BaseResponse<NewcomerPurchaseXsiteResponse> xsiteInfo(@RequestBody @Valid NewcomerPurchaseXsiteRequest request){
        NewcomerPurchaseXsiteResponse response = newcomerCouponAndGoods(request);
        return BaseResponse.success(response);
    }

    /**
     * 魔方组件查询新人券和商品
     * @param request
     * @return
     */
    @Operation(summary = "魔方组件查询新人券和商品")
    @PostMapping(value = "/unLogin/xsite")
    public BaseResponse<NewcomerPurchaseXsiteResponse> unLoginXsiteInfo(@RequestBody @Valid NewcomerPurchaseXsiteRequest request){
        NewcomerPurchaseXsiteResponse response = newcomerCouponAndGoods(request);
        return BaseResponse.success(response);
    }

    /**
     * 查询新人券和商品
     * @param request
     * @return
     */
    public NewcomerPurchaseXsiteResponse newcomerCouponAndGoods(NewcomerPurchaseXsiteRequest request) {
        NewcomerPurchaseXsiteResponse response = new NewcomerPurchaseXsiteResponse();
        String customerId = commonUtil.getOperatorId();

        //检查活动是否有效
        Boolean active = newcomerPurchaseConfigQueryProvider.checkActive().getContext();
        response.setActive(active);
        if (!active) {
            return response;
        }

        //已登陆-非新人 活动无效
        if (StringUtils.isNotBlank(customerId) && !commonUtil.isNewCustomer()) {
            response.setActive(Boolean.FALSE);
            return response;
        }

        //优惠券
        if (CollectionUtils.isNotEmpty(request.getCouponIds())) {
            List<CouponInfoVO> coupons = newcomerPurchaseCouponQueryProvider
                    .getFetchCoupons(NewcomerPurchaseCouponGetFetchRequest.builder().couponIds(request.getCouponIds()).build())
                    .getContext().getCoupons();
            response.setCoupons(coupons);
        }

        //商品
        if (CollectionUtils.isNotEmpty(request.getGoodsInfoIds())) {
            List<NewcomerPurchaseGoodsVO> newcomerPurchaseGoodsVOList = newcomerPurchaseGoodsQueryProvider
                    .list(NewcomerPurchaseGoodsListRequest.builder()
                            .goodsInfoIds(request.getGoodsInfoIds()).delFlag(DeleteFlag.NO).build())
                    .getContext().getNewcomerPurchaseGoodsVOList();
            List<String> skuIds = newcomerPurchaseGoodsVOList.stream().map(NewcomerPurchaseGoodsVO::getGoodsInfoId).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(skuIds)) {
                EsGoodsInfoQueryRequest queryRequest = new EsGoodsInfoQueryRequest();
                queryRequest.setCustomerId(customerId);
                queryRequest.setGoodsInfoIds(skuIds);
                queryRequest.setPageSize(skuIds.size());
                GoodsInfoListResponse goodsInfoListResponse = goodsBaseService.skuListConvert(goodsListInterface.getList(queryRequest), queryRequest.getSortFlag());
                response.setGoods(goodsInfoListResponse.getGoodsInfoPage().getContent());
            }
        }

        return response;
    }
}
