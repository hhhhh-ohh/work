package com.wanmi.sbc.marketing;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.AuditStatus;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.store.StoreBaseInfoByIdRequest;
import com.wanmi.sbc.customer.api.response.store.StoreBaseResponse;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoByIdRequest;
import com.wanmi.sbc.goods.api.response.info.GoodsInfoByIdResponse;
import com.wanmi.sbc.marketing.api.provider.market.MarketingQueryProvider;
import com.wanmi.sbc.marketing.api.provider.marketingsuits.MarketingSuitsQueryProvider;
import com.wanmi.sbc.marketing.api.provider.preferential.PreferentialQueryProvider;
import com.wanmi.sbc.marketing.api.request.market.MarketingGetByIdRequest;
import com.wanmi.sbc.marketing.api.request.market.MarketingIdRequest;
import com.wanmi.sbc.marketing.api.request.market.MarketingSuitsBySkuIdRequest;
import com.wanmi.sbc.marketing.api.response.market.MarketingCheckResponse;
import com.wanmi.sbc.marketing.api.response.market.MarketingGetByIdResponse;
import com.wanmi.sbc.marketing.api.response.market.MarketingMoreSuitsGoodInfoIdResponse;
import com.wanmi.sbc.marketing.api.response.preferential.PreferentialDetailResponse;
import com.wanmi.sbc.marketing.bean.vo.MarketingForEndVO;
import com.wanmi.sbc.marketing.bean.vo.MarketingVO;
import com.wanmi.sbc.util.CommonUtil;


import io.swagger.v3.oas.annotations.Parameter;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.Objects;

@Tag(name = "MarketingController", description = "营销服务 API")
@RestController
@RequestMapping("/marketing")
@Validated
public class MarketingController {

    @Autowired
    private MarketingQueryProvider marketingQueryProvider;

    @Autowired
    private StoreQueryProvider storeQueryProvider;

    @Autowired
    private MarketingSuitsQueryProvider marketingSuitsQueryProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private GoodsInfoQueryProvider goodsInfoQueryProvider;

    @Autowired
    private PreferentialQueryProvider preferentialQueryProvider;


    /**
     * 根据营销Id获取营销详细信息
     * @param marketingId
     * @return
     */
    @Operation(summary = "根据营销Id获取营销详细信息")
    @Parameter(name = "marketingId", description = "营销Id", required = true)
    @RequestMapping(value = "/{marketingId}", method = RequestMethod.GET)
    public BaseResponse<MarketingForEndVO> getMarketingById(@PathVariable("marketingId")Long marketingId){
        MarketingGetByIdRequest marketingGetByIdRequest = new MarketingGetByIdRequest();
        marketingGetByIdRequest.setMarketingId(marketingId);
        MarketingForEndVO response = marketingQueryProvider.getByIdForCustomer(marketingGetByIdRequest).getContext().getMarketingForEndVO();
        // 校验店铺是否过期
        if (!Constants.BOSS_DEFAULT_STORE_ID.equals(response.getStoreId())){
            StoreBaseResponse storeBaseResponse =
                    storeQueryProvider.getStoreBaseInfoById(new StoreBaseInfoByIdRequest(response.getStoreId())).getContext();
            response.setStoreName(storeBaseResponse.getStoreName());
        }
        return BaseResponse.success(response);
    }


    /**
     *  移动端-商品详情-组合商品-活动列表
     */
    @Operation(summary = "查看活动列表")
    @RequestMapping(value = "/getMoreSuitsInfo", method = RequestMethod.POST)
    public BaseResponse<MarketingMoreSuitsGoodInfoIdResponse> getMoreSuitsInfo(@RequestBody @Valid MarketingSuitsBySkuIdRequest request){
//        request.setBaseStoreId(commonUtil.getStoreIdWithDefault());
        if(Objects.isNull(request.getStoreId())){
            GoodsInfoByIdResponse infoByIdResponse = goodsInfoQueryProvider.getById(GoodsInfoByIdRequest.builder().goodsInfoId(request.getGoodsInfoId()).build()).getContext();
            request.setStoreId(infoByIdResponse.getStoreId());
        }
        MarketingMoreSuitsGoodInfoIdResponse response = marketingSuitsQueryProvider.getMarketingBySuitsSkuId(request).getContext();
        return BaseResponse.success(response);
    }

    /**
     *  移动端-商品详情-组合商品-活动列表(登录状态)
     */
    @Operation(summary = "查看活动列表")
    @RequestMapping(value = "/login/getMoreSuitsInfo", method = RequestMethod.POST)
    public BaseResponse<MarketingMoreSuitsGoodInfoIdResponse> getMoreSuitsInfoForLogin(@RequestBody @Valid MarketingSuitsBySkuIdRequest request){
        request.setUserId(commonUtil.getOperatorId());
        if(Objects.isNull(request.getStoreId())){
            GoodsInfoByIdResponse infoByIdResponse = goodsInfoQueryProvider.getById(GoodsInfoByIdRequest.builder().goodsInfoId(request.getGoodsInfoId()).build()).getContext();
            request.setStoreId(infoByIdResponse.getStoreId());
        }
        MarketingMoreSuitsGoodInfoIdResponse response = marketingSuitsQueryProvider.getMarketingBySuitsSkuId(request).getContext();
        return BaseResponse.success(response);
    }

    /**
     * 根据营销Id校验活动是否开始，暂停，审核通过
     * @param marketingId
     * @return
     */
    @Operation(summary = "根据营销Id校验活动是否开始，暂停，审核通过")
    @Parameter(name = "marketingId", description = "营销Id", required = true)
    @RequestMapping(value = "/check/{marketingId}", method = RequestMethod.GET)
    public BaseResponse<MarketingCheckResponse> check(@PathVariable("marketingId")Long marketingId){
        MarketingGetByIdRequest request = MarketingGetByIdRequest.buildRequest(marketingId);
        MarketingGetByIdResponse response = marketingQueryProvider.getById(request).getContext();
        MarketingVO marketingVO = response.getMarketingVO();
        LocalDateTime now = LocalDateTime.now();
        MarketingCheckResponse checkResponse = MarketingCheckResponse.builder().build();
        //返回空值，前端校验缺省页面活动找不到
        if(Objects.isNull(marketingVO)
                || Objects.equals(marketingVO.getDelFlag(), DeleteFlag.YES)
                || !Objects.equals(marketingVO.getAuditStatus(),AuditStatus.CHECKED)
                || marketingVO.getEndTime().isBefore(now)){
            checkResponse.setActivityValidity(2);
            return BaseResponse.success(checkResponse);
        }
        LocalDateTime beginTime = marketingVO.getBeginTime();
        LocalDateTime endTime = marketingVO.getEndTime();

        BoolFlag isPause = marketingVO.getIsPause();
        //是否未开始，进行中，审核通过，未暂停的营销活动,前端进入缺省页面活动未开始
        if((beginTime.isBefore(now) && endTime.isAfter(now))
                && Objects.equals(isPause,BoolFlag.NO)){
            checkResponse.setActivityValidity(NumberUtils.INTEGER_ZERO);
            return BaseResponse.success(checkResponse);
        }
        checkResponse.setActivityValidity(NumberUtils.INTEGER_ONE);
        checkResponse.setBeginTime(beginTime);
        checkResponse.setEndTime(endTime);
        return BaseResponse.success(checkResponse);
    }

    @Operation(summary = "加价购商品")
    @GetMapping("/preferential/{marketingId}")
    public BaseResponse<PreferentialDetailResponse> getGoodsInfo(@PathVariable Long marketingId){
        MarketingIdRequest marketingIdRequest = new MarketingIdRequest();
        marketingIdRequest.setMarketingId(marketingId);
        return preferentialQueryProvider.getPreferentialDetail(marketingIdRequest);
    }

}
