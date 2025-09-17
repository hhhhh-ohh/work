package com.wanmi.sbc.marketing;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.MarketingGoodsStatus;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.customer.CustomerQueryProvider;
import com.wanmi.sbc.customer.api.provider.level.CustomerLevelQueryProvider;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.customer.CustomerGetByIdRequest;
import com.wanmi.sbc.customer.api.request.level.CustomerLevelListByCustomerLevelNameRequest;
import com.wanmi.sbc.customer.api.request.store.NoDeleteStoreByIdRequest;
import com.wanmi.sbc.customer.api.response.customer.CustomerGetByIdResponse;
import com.wanmi.sbc.customer.bean.dto.CustomerDTO;
import com.wanmi.sbc.customer.bean.dto.MarketingCustomerLevelDTO;
import com.wanmi.sbc.customer.bean.vo.MarketingCustomerLevelVO;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.goods.bean.vo.FlashSaleGoodsVO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.goods.service.GoodsBaseService;
import com.wanmi.sbc.marketing.api.provider.gift.FullGiftQueryProvider;
import com.wanmi.sbc.marketing.api.provider.market.MarketingQueryProvider;
import com.wanmi.sbc.marketing.api.provider.marketingsuits.MarketingSuitsQueryProvider;
import com.wanmi.sbc.marketing.api.request.gift.FullGiftLevelListByMarketingIdAndCustomerRequest;
import com.wanmi.sbc.marketing.api.request.market.MarketingGetByIdRequest;
import com.wanmi.sbc.marketing.api.request.market.MarketingPageRequest;
import com.wanmi.sbc.marketing.api.request.marketingsuits.MarketingSuitsByMarketingIdRequest;
import com.wanmi.sbc.marketing.api.response.gift.FullGiftLevelListByMarketingIdAndCustomerResponse;
import com.wanmi.sbc.marketing.api.response.market.MarketingPageResponse;
import com.wanmi.sbc.marketing.api.response.marketingsuits.MarketingSuitsByMarketingIdResponse;
import com.wanmi.sbc.marketing.bean.dto.MarketingPageDTO;
import com.wanmi.sbc.marketing.bean.vo.MarketingForEndVO;
import com.wanmi.sbc.marketing.bean.vo.MarketingPageVO;
import com.wanmi.sbc.marketing.bean.vo.MarketingSuitsSkuVO;
import com.wanmi.sbc.marketing.request.MarketingPageListRequest;
import com.wanmi.sbc.marketing.service.MarketingBaseService;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author 黄昭
 * @className MarketingController
 * @description TODO
 * @date 2022/2/10 11:23
 **/
@Tag(name = "MarketingController", description = "营销基础服务API")
@RestController
@Validated
@RequestMapping("/marketing")
public class MarketingController {

    @Autowired
    private StoreQueryProvider storeQueryProvider;

    @Autowired
    private MarketingQueryProvider marketingQueryProvider;

    @Autowired
    private CustomerLevelQueryProvider customerLevelQueryProvider;

    @Autowired
    private CustomerQueryProvider customerQueryProvider;

    @Autowired
    private FullGiftQueryProvider fullGiftQueryProvider;

    @Autowired
    private MarketingSuitsQueryProvider marketingSuitsQueryProvider;

    @Autowired
    private MarketingBaseService baseService;

    @Autowired
    private GoodsBaseService goodsBaseService;
    /**
     * 获取营销活动列表
     * @param marketingPageListRequest {@link MarketingPageRequest}
     * @return
     */
    @Operation(summary = "获取营销活动列表")
    @RequestMapping(value = "/supplier/list", method = RequestMethod.POST)
    public BaseResponse<MicroServicePage<MarketingPageVO>> getMarketingList(@RequestBody MarketingPageListRequest marketingPageListRequest) {
        MarketingPageRequest marketingPageRequest = new MarketingPageRequest();
        //模糊查询店铺名称
        BaseResponse<MicroServicePage<MarketingPageVO>> microServicePageBaseResponse = baseService
                .getMicroServicePageBaseResponse(marketingPageListRequest);
        if (Objects.nonNull(microServicePageBaseResponse)){
            return microServicePageBaseResponse;
        }

        marketingPageRequest.setMarketingPageDTO(KsBeanUtil.convert(marketingPageListRequest, MarketingPageDTO.class));
        marketingPageRequest.setRules(marketingPageListRequest.getRules());
        marketingPageRequest.setIsRule(marketingPageListRequest.getIsRule());
        BaseResponse<MarketingPageResponse> pageResponse = marketingQueryProvider.page(marketingPageRequest);

        List<MarketingPageVO> content = pageResponse.getContext().getMarketingVOS().getContent();

        //查询客户等级+店铺名称
        List<MarketingCustomerLevelDTO> customerLevelDTOList = content.stream().map(m -> {
            MarketingCustomerLevelDTO dto = new MarketingCustomerLevelDTO();
            dto.setMarketingId(m.getMarketingId());
            dto.setStoreId(m.getStoreId());
            dto.setJoinLevel(m.getJoinLevel());
            return dto;
        }).collect(Collectors.toList());
        List<MarketingCustomerLevelVO> marketingCustomerLevelVOList = customerLevelQueryProvider.
                listByCustomerLevelName(new CustomerLevelListByCustomerLevelNameRequest(customerLevelDTOList)).
                getContext().getCustomerLevelVOList();
        Map<Long, MarketingCustomerLevelVO> levelVOMap = marketingCustomerLevelVOList.stream().
                collect(Collectors.toMap(MarketingCustomerLevelVO::getMarketingId, Function.identity()));
        content.forEach(vo -> {
            MarketingCustomerLevelVO levelVO = levelVOMap.get(vo.getMarketingId());
            vo.setLevelName(levelVO.getLevelName());
            vo.setStoreName(levelVO.getStoreName());
        });
        return BaseResponse.success(pageResponse.getContext().getMarketingVOS());
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

        StoreVO storeVO = storeQueryProvider.getNoDeleteStoreById(NoDeleteStoreByIdRequest.builder()
                .storeId(marketingResponse.getStoreId())
                .build())
                .getContext()
                .getStoreVO();
        marketingResponse.setStoreName(storeVO.getStoreName());

        return BaseResponse.success(marketingResponse);
    }

    /**
     * 根据营销Id获取赠品信息
     * @param request 参数
     * @return
     */
    @Operation(summary = "根据营销Id获取赠品信息")
    @RequestMapping(value = "/fullGift/giftList", method = RequestMethod.POST)
    public BaseResponse<FullGiftLevelListByMarketingIdAndCustomerResponse> getGiftByMarketingId(@Valid @RequestBody FullGiftLevelListByMarketingIdAndCustomerRequest request) {
        CustomerGetByIdResponse customer = null;
        if(StringUtils.isNotBlank(request.getCustomerId())){
            customer = customerQueryProvider.getCustomerById(new CustomerGetByIdRequest(request.getCustomerId())).getContext();
        }
        request.setCustomerId(customer==null?null:customer.getCustomerId());
        request.setIsMarketing(Boolean.TRUE);

        BaseResponse<FullGiftLevelListByMarketingIdAndCustomerResponse> response = fullGiftQueryProvider.listGiftByMarketingIdAndCustomer(request);
        List<GoodsInfoVO> goodsInfoVOS = response.getContext().getGiftList();
        if (CollectionUtils.isNotEmpty(goodsInfoVOS)){
            goodsBaseService.populateMarketingGoodsStatus(goodsInfoVOS);
        }

        return response;
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

        StoreVO storeVO = storeQueryProvider.getNoDeleteStoreById(NoDeleteStoreByIdRequest.builder()
                        .storeId(response.getMarketingVO().getStoreId())
                        .build())
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
        if (CollectionUtils.isEmpty(marketingSuitsSkuVOS)){
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

}