package com.wanmi.sbc.marketing;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.MarketingGoodsStatus;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.store.ListStoreByIdsRequest;
import com.wanmi.sbc.customer.api.request.store.NoDeleteStoreByIdRequest;
import com.wanmi.sbc.customer.api.response.store.ListStoreByIdsResponse;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.goods.api.response.info.GoodsInfoResponse;
import com.wanmi.sbc.goods.api.response.price.GoodsIntervalPriceResponse;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoResponseVO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.goods.service.GoodsBaseService;
import com.wanmi.sbc.intervalprice.GoodsIntervalPriceService;
import com.wanmi.sbc.marketing.api.provider.market.MarketingProvider;
import com.wanmi.sbc.marketing.api.provider.market.MarketingQueryProvider;
import com.wanmi.sbc.marketing.api.provider.marketingsuits.MarketingSuitsProvider;
import com.wanmi.sbc.marketing.api.provider.marketingsuits.MarketingSuitsQueryProvider;
import com.wanmi.sbc.marketing.api.request.market.ExistsSkuByMarketingTypeRequest;
import com.wanmi.sbc.marketing.api.request.market.MarketingGetByIdRequest;
import com.wanmi.sbc.marketing.api.request.market.MarketingQueryByIdsRequest;
import com.wanmi.sbc.marketing.api.request.marketingsuits.MarketingSuitsByMarketingIdRequest;
import com.wanmi.sbc.marketing.api.request.marketingsuits.MarketingSuitsSaveRequest;
import com.wanmi.sbc.marketing.api.response.marketingsuits.MarketingSuitsByMarketingIdResponse;
import com.wanmi.sbc.marketing.bean.dto.SkuExistsDTO;
import com.wanmi.sbc.marketing.bean.enums.MarketingErrorCodeEnum;
import com.wanmi.sbc.marketing.bean.enums.MarketingType;
import com.wanmi.sbc.marketing.bean.vo.MarketingForEndVO;
import com.wanmi.sbc.marketing.bean.vo.MarketingFullReturnStoreVO;
import com.wanmi.sbc.marketing.bean.vo.MarketingSuitsSkuVO;
import com.wanmi.sbc.store.StoreBaseService;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.util.OperateLogMQUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@Tag(name = "MarketingController", description = "营销服务API")
@RestController
@Validated
    @RequestMapping("/marketing")
public class MarketingController {

    @Autowired
    private MarketingQueryProvider marketingQueryProvider;

    @Autowired
    private MarketingProvider marketingProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private GoodsIntervalPriceService goodsIntervalPriceService;

    @Autowired
    private OperateLogMQUtil operateLogMQUtil;

    @Autowired
    private MarketingSuitsProvider marketingSuitsSaveProvider;
    @Autowired
    private MarketingSuitsQueryProvider marketingSuitsQueryProvider;

    @Autowired
    private StoreQueryProvider storeQueryProvider;

    @Autowired
    private StoreBaseService storeBaseService;

    @Autowired
    private GoodsBaseService goodsBaseService;


    /**
     * 查询在相同类型的营销活动中，skuList是否存在重复
     * @param skuExistsDTO {@link SkuExistsDTO}
     * @return
     */
    @Operation(summary = "查询在相同类型的营销活动中，skuList是否存在重复")
    @RequestMapping(value = "/sku/exists", method = RequestMethod.POST)
    public BaseResponse<List<String>> ifSkuExists(@RequestBody @Valid SkuExistsDTO skuExistsDTO) {
        return BaseResponse.success(marketingQueryProvider.queryExistsSkuByMarketingType(ExistsSkuByMarketingTypeRequest.builder()
                .storeId(commonUtil.getStoreId())
                .skuExistsDTO(skuExistsDTO)
                .build()).getContext());
    }

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
        MarketingForEndVO marketingResponse = marketingQueryProvider.getByIdForSupplier(marketingGetByIdRequest)
                .getContext().getMarketingForEndVO();

        List<GoodsInfoVO> goodsInfoVOS = Optional.ofNullable(marketingResponse.getGoodsList())
                .map(GoodsInfoResponseVO::getGoodsInfoPage).map(MicroServicePage::getContent)
                .orElse(Collections.emptyList());
        //填充供应商名称
        storeBaseService.populateProviderName(goodsInfoVOS);

        //填充marketingGoodsStatus属性
        goodsBaseService.populateMarketingGoodsStatus(goodsInfoVOS);

        if(marketingResponse.getStoreId() != null && commonUtil.getStoreId() != null && commonUtil.getStoreId().longValue() != marketingResponse.getStoreId()){
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080016);
        }else{
            //获取满返活动店铺信息
            if(Objects.equals(MarketingType.RETURN,marketingResponse.getMarketingType())
                    && CollectionUtils.isNotEmpty(marketingResponse.getMarketingFullReturnStoreList())){
                List<Long> storeIds = marketingResponse.getMarketingFullReturnStoreList().stream()
                        .map(MarketingFullReturnStoreVO::getStoreId)
                        .collect(Collectors.toList());
                ListStoreByIdsResponse response = storeQueryProvider.listByIds(ListStoreByIdsRequest.builder().storeIds(storeIds).build()).getContext();
                marketingResponse.setReturnStoreNum((long) response.getStoreVOList().size());
                marketingResponse.setReturnStoreInfoList(response.getStoreVOList());
            }
            return BaseResponse.success(marketingResponse);
        }
    }

    /**
     *  商家-营销中心-促销活动-组合购活动详情
     *  @param marketingId
     *  @return
     */
    @Operation(summary = "组合购活动详情")
    @Parameter(name = "marketingId", description = "营销Id", required = true)
    @RequestMapping(value = "/getGoodsSuitsDetail/{marketingId}", method = RequestMethod.GET)
    public BaseResponse<MarketingSuitsByMarketingIdResponse> getGoodsSuitsDetail(@PathVariable("marketingId")Long marketingId){
        MarketingSuitsByMarketingIdResponse response = marketingSuitsQueryProvider.getByMarketingIdForSupplier(
                MarketingSuitsByMarketingIdRequest.builder().marketingId(marketingId).build()).getContext();

        StoreVO storeVO = storeQueryProvider
                .getNoDeleteStoreById(NoDeleteStoreByIdRequest.builder().storeId(response.getMarketingVO().getStoreId()).build())
                .getContext()
                .getStoreVO();
        response.getMarketingVO().setStoreName(storeVO.getStoreName());

        //填充marketingGoodsStatus属性
        this.populateMarketingGoodsStatus(response.getMarketingSuitsSkuVOList());

        return BaseResponse.success(response);
    }

    /**
     * @description 填充marketingGoodsStatus属性
     * @Author qiyuanzhao
     * @Date 2022/10/10 14:15
     * @param
     * @return
     **/
    private void populateMarketingGoodsStatus(List<MarketingSuitsSkuVO> marketingSuitsSkuVOS) {
        if (org.apache.commons.collections.CollectionUtils.isEmpty(marketingSuitsSkuVOS)){
            return;
        }
        //获取goodsInfoVO的集合
        List<String> goodsInfoIds = marketingSuitsSkuVOS.stream().map(MarketingSuitsSkuVO::getSkuId).collect(Collectors.toList());
        Map<String, MarketingGoodsStatus> statusMap = goodsBaseService.getMarketingGoodsStatusListByGoodsInfoIds(goodsInfoIds);

        for (MarketingSuitsSkuVO marketingSuitsSkuVO : marketingSuitsSkuVOS){
            String goodsInfoId = marketingSuitsSkuVO.getSkuId();

            MarketingGoodsStatus marketingGoodsStatus = statusMap.get(goodsInfoId);
            marketingSuitsSkuVO.setMarketingGoodsStatus(marketingGoodsStatus);
        }
    }


    /**
     * 根据营销Id获取关联商品信息
     * @param marketingId
     * @return
     */
    @Operation(summary = "根据营销Id获取关联商品信息")
    @Parameter(name = "marketingId", description = "营销Id", required = true)
    @RequestMapping(value = "/goods/{marketingId}", method = RequestMethod.GET)
    public BaseResponse<GoodsInfoResponse> getGoodsByMarketingId(@PathVariable("marketingId")Long marketingId){
        MarketingGetByIdRequest marketingGetByIdRequest = new MarketingGetByIdRequest();
        marketingGetByIdRequest.setMarketingId(marketingId);
        GoodsInfoResponseVO response = marketingQueryProvider.getGoodsById(marketingGetByIdRequest)
                .getContext().getGoodsInfoResponseVO();
        if(Objects.nonNull(response.getGoodsInfoPage()) && CollectionUtils.isNotEmpty(response.getGoodsInfoPage().getContent())) {
            GoodsIntervalPriceResponse priceResponse =
                    goodsIntervalPriceService.getGoodsIntervalPriceVOList(response.getGoodsInfoPage().getContent());
            response.setGoodsIntervalPrices(priceResponse.getGoodsIntervalPriceVOList());
            response.setGoodsInfoPage(new MicroServicePage<>(priceResponse.getGoodsInfoVOList()));
        }
        return BaseResponse.success(KsBeanUtil.convert(response, GoodsInfoResponse.class));
    }

    /**
     * 根据营销Id获取正进行中的营销Id
     * @param request 参数
     * @return
     */
    @Operation(summary = "根据营销Id获取正进行中的营销Id")
    @RequestMapping(value = "/isStart", method = RequestMethod.POST)
    public BaseResponse<List<String>> getGoodsByMarketingId(@RequestBody @Valid MarketingQueryByIdsRequest request){
        return BaseResponse.success(marketingQueryProvider.queryStartingByIds(request).getContext().getMarketingIdList());
    }

    /**
     *  创建组合购活动
     * @Param MarketingSuitsSaveRequest
     * @Return
     */
    @Operation(summary = "创建组合活动")
    @RequestMapping(value = "/saveSuits", method = RequestMethod.POST)
    @Transactional
    public BaseResponse addSuits(@RequestBody @Valid MarketingSuitsSaveRequest marketingSuitsSaveRequest){
        marketingSuitsSaveRequest.setBaseStoreId(commonUtil.getStoreIdWithDefault());
        try {
            marketingSuitsSaveProvider.add(marketingSuitsSaveRequest);
        } catch (SbcRuntimeException ex) {
            return BaseResponse.info(ex.getErrorCode(),ex.getResult());
        }
        return BaseResponse.SUCCESSFUL();
    }

    /**
     *  修改组合购活动
     * @Param MarketingSuitsSaveRequest
     * @Return
     */
    @Operation(summary = "修改组合活动")
    @RequestMapping(value = "/modifySuits", method = RequestMethod.POST)
    @Transactional
    public BaseResponse modifySuits(@RequestBody @Valid MarketingSuitsSaveRequest marketingSuitsSaveRequest){

        marketingSuitsSaveRequest.setBaseStoreId(commonUtil.getStoreIdWithDefault());
        marketingSuitsSaveProvider.modify(marketingSuitsSaveRequest);
        return BaseResponse.SUCCESSFUL();
    }

}
