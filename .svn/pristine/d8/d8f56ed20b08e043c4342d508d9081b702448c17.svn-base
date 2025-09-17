package com.wanmi.sbc.marketing;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.Platform;
import com.wanmi.sbc.common.enums.PluginType;
import com.wanmi.sbc.common.enums.SortType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.level.CustomerLevelQueryProvider;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.provider.storelevel.StoreLevelQueryProvider;
import com.wanmi.sbc.customer.api.request.store.ListStoreByIdsRequest;
import com.wanmi.sbc.customer.api.request.store.StoreByIdRequest;
import com.wanmi.sbc.customer.api.request.storelevel.StoreLevelListRequest;
import com.wanmi.sbc.customer.api.response.store.ListStoreByIdsResponse;
import com.wanmi.sbc.customer.api.response.store.StoreByIdResponse;
import com.wanmi.sbc.customer.bean.vo.CustomerLevelVO;
import com.wanmi.sbc.customer.bean.vo.StoreLevelVO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoResponseVO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.goods.service.GoodsBaseService;
import com.wanmi.sbc.marketing.api.provider.buyoutprice.MarketingBuyoutPriceQueryProvider;
import com.wanmi.sbc.marketing.api.provider.market.MarketingQueryProvider;
import com.wanmi.sbc.marketing.api.request.buyoutprice.MarketingBuyoutPriceSearchRequest;
import com.wanmi.sbc.marketing.api.request.market.MarketingGetByIdRequest;
import com.wanmi.sbc.marketing.api.request.market.MarketingPageRequest;
import com.wanmi.sbc.marketing.api.response.market.MarketingPageResponse;
import com.wanmi.sbc.marketing.bean.constant.MarketingErrorCode;
import com.wanmi.sbc.marketing.bean.dto.MarketingPageDTO;
import com.wanmi.sbc.marketing.bean.enums.MarketingErrorCodeEnum;
import com.wanmi.sbc.marketing.bean.enums.MarketingType;
import com.wanmi.sbc.marketing.bean.vo.MarketingForEndVO;
import com.wanmi.sbc.marketing.bean.vo.MarketingFullReturnStoreVO;
import com.wanmi.sbc.marketing.bean.vo.MarketingPageVO;
import com.wanmi.sbc.marketing.request.MarketingPageListRequest;
import com.wanmi.sbc.store.StoreBaseService;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.util.OperateLogMQUtil;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Tag(name = "MarketingBuyoutPriceController", description = "一口价营销服务API")
@RestController
@Validated
@RequestMapping("/marketing/buyout_price")
public class MarketingBuyoutPriceController {

    @Autowired
    private MarketingQueryProvider marketingQueryProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private MarketingBuyoutPriceQueryProvider marketingBuyoutPriceQueryProvider;

    @Autowired
    private StoreQueryProvider storeQueryProvider;

    @Autowired
    private CustomerLevelQueryProvider customerLevelQueryProvider;

    @Autowired
    private StoreLevelQueryProvider storeLevelQueryProvider;

    @Autowired
    private GoodsBaseService goodsBaseService;

    @Autowired
    private StoreBaseService storeBaseService;

    /**
     * 获取营销活动列表
     *
     * @param marketingPageListRequest {@link MarketingPageRequest}
     * @return
     */
    @Operation(summary = "获取营销活动列表")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public BaseResponse<MicroServicePage<MarketingPageVO>> getMarketingList(@RequestBody MarketingPageListRequest marketingPageListRequest) {

        MarketingPageRequest marketingPageRequest = new MarketingPageRequest();
        marketingPageRequest.setStoreId(commonUtil.getStoreId());
        marketingPageRequest.setMarketingPageDTO(KsBeanUtil.convert(marketingPageListRequest, MarketingPageDTO.class));
        //判断是否需要展示营销信息和店铺名称
        if (marketingPageListRequest.getRules() != null) {
            marketingPageRequest.setRules(marketingPageListRequest.getRules());
            marketingPageRequest.getMarketingPageDTO().setShowStoreNameFlag(Boolean.TRUE);

        }
        BaseResponse<MarketingPageResponse> pageResponse = marketingQueryProvider.page(marketingPageRequest);
        return BaseResponse.success(pageResponse.getContext().getMarketingVOS());
    }

    /**
     * 搜索营销活动
     *
     * @param marketingPageListRequest {@link MarketingPageRequest}
     * @return
     */
    @Operation(summary = "搜索营销活动")
    @RequestMapping(value = "/searchMarketing", method = RequestMethod.POST)
    public BaseResponse<MicroServicePage<MarketingPageVO>> searchMarketingList(@RequestBody MarketingBuyoutPriceSearchRequest marketingPageListRequest) {
        marketingPageListRequest.setStoreId(commonUtil.getStoreId());
        marketingPageListRequest.setPlatform(Platform.BOSS);
        marketingPageListRequest.setPluginType(PluginType.NORMAL);
        marketingPageListRequest.putSort("marketingId", SortType.DESC.toValue());
        BaseResponse<MarketingPageResponse> pageResponse =
                marketingBuyoutPriceQueryProvider.search(marketingPageListRequest);
        return BaseResponse.success(pageResponse.getContext().getMarketingVOS());
    }

    /**
     * 根据营销Id获取营销详细信息
     *
     * @param marketingId
     * @return
     */
    @Operation(summary = "根据营销Id获取营销详细信息")
    @Parameter(name = "marketingId", description = "营销Id", required = true)
    @RequestMapping(value = "/{marketingId}", method = RequestMethod.GET)
    public BaseResponse<MarketingForEndVO> getMarketingById(@PathVariable("marketingId") Long marketingId) {
        MarketingGetByIdRequest marketingGetByIdRequest = new MarketingGetByIdRequest();
        marketingGetByIdRequest.setMarketingId(marketingId);
        MarketingForEndVO marketingResponse = marketingQueryProvider.getByIdForSupplier(marketingGetByIdRequest)
                .getContext().getMarketingForEndVO();

        if (marketingResponse.getStoreId() != null && commonUtil.getStoreId() != null && commonUtil.getStoreId().longValue() != marketingResponse.getStoreId()) {
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080016);
        }

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

        // 根据营销活动的店铺id查询店铺信息
        StoreByIdResponse storeByIdResponse =
                storeQueryProvider.getById(StoreByIdRequest.builder().storeId(marketingResponse.getStoreId()).build()).getContext();
        if (Objects.nonNull(storeByIdResponse) && Objects.nonNull(storeByIdResponse.getStoreVO())) {
            marketingResponse.setStoreName(storeByIdResponse.getStoreVO().getStoreName());
            BoolFlag companyType = storeByIdResponse.getStoreVO().getCompanyType();
            List<Long> levels = marketingResponse.getJoinLevelList();
            String levelName = "";
            if (BoolFlag.NO.equals(companyType) || Objects.equals(Constants.NUM_MINUS_1L,marketingResponse.getStoreId())) {
                //平台客户等级
                List<CustomerLevelVO> customerLevelVOList =
                        customerLevelQueryProvider.listAllCustomerLevel().getContext().getCustomerLevelVOList();
                //平台
                if (CollectionUtils.isNotEmpty(customerLevelVOList) && CollectionUtils.isNotEmpty(levels)) {
                    levelName = levels.stream().flatMap(level -> customerLevelVOList.stream()
                            .filter(customerLevelVO -> level.equals(customerLevelVO.getCustomerLevelId()))
                            .map(CustomerLevelVO::getCustomerLevelName)).collect(Collectors.joining(","));
                }
            } else {
                //商家
                StoreLevelListRequest storeLevelListRequest =
                        StoreLevelListRequest.builder().storeId(marketingResponse.getStoreId()).build();
                List<StoreLevelVO> storeLevelVOList = storeLevelQueryProvider
                        .listAllStoreLevelByStoreId(storeLevelListRequest)
                        .getContext().getStoreLevelVOList();
                if (CollectionUtils.isNotEmpty(storeLevelVOList) && CollectionUtils.isNotEmpty(levels)) {
                    levelName = levels.stream().flatMap(level -> storeLevelVOList.stream()
                            .filter(storeLevelVO -> level.equals(storeLevelVO.getStoreLevelId()))
                            .map(StoreLevelVO::getLevelName)).collect(Collectors.joining(","));
                }
            }
            marketingResponse.setJoinLevelName(levelName);
        }

        //填充营销商品状态
        GoodsInfoResponseVO goodsList = marketingResponse.getGoodsList();
        if (Objects.nonNull(goodsList)){
            MicroServicePage<GoodsInfoVO> page = goodsList.getGoodsInfoPage();
            if (Objects.nonNull(page)){
                List<GoodsInfoVO> goodsInfoVOS = page.getContent();
                if (CollectionUtils.isNotEmpty(goodsInfoVOS)){
                    goodsBaseService.populateMarketingGoodsStatus(goodsInfoVOS);
                    //填充供应商名称
                    storeBaseService.populateProviderName(goodsInfoVOS);
                }
            }
        }

        return BaseResponse.success(marketingResponse);
    }
}
