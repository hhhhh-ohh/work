package com.wanmi.sbc.marketing;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.bean.dto.CustomerDTO;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoListByIdsRequest;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.marketing.api.provider.coupon.CouponInfoQueryProvider;
import com.wanmi.sbc.marketing.api.provider.fullreturn.FullReturnQueryProvider;
import com.wanmi.sbc.marketing.api.provider.market.MarketingQueryProvider;
import com.wanmi.sbc.marketing.api.provider.market.MarketingScopeQueryProvider;
import com.wanmi.sbc.marketing.api.request.coupon.CouponInfoQueryRequest;
import com.wanmi.sbc.marketing.api.request.fullreturn.FullReturnLevelListByMarketingIdAndCustomerRequest;
import com.wanmi.sbc.marketing.api.request.fullreturn.FullReturnLevelListByMarketingIdRequest;
import com.wanmi.sbc.marketing.api.request.market.MarketingGetByIdRequest;
import com.wanmi.sbc.marketing.api.request.market.MarketingScopeByMarketingIdRequest;
import com.wanmi.sbc.marketing.api.response.fullreturn.FullReturnLevelListByMarketingIdAndCustomerResponse;
import com.wanmi.sbc.marketing.bean.enums.MarketingScopeType;
import com.wanmi.sbc.marketing.bean.enums.MarketingSubType;
import com.wanmi.sbc.marketing.bean.vo.MarketingFullReturnDetailVO;
import com.wanmi.sbc.marketing.bean.vo.MarketingFullReturnLevelVO;
import com.wanmi.sbc.marketing.bean.vo.MarketingScopeVO;
import com.wanmi.sbc.marketing.bean.vo.MarketingVO;
import com.wanmi.sbc.order.api.provider.trade.TradeItemQueryProvider;
import com.wanmi.sbc.order.api.request.trade.TradeItemSnapshotByCustomerIdRequest;
import com.wanmi.sbc.order.bean.vo.TradeItemGroupVO;
import com.wanmi.sbc.order.bean.vo.TradeItemSnapshotVO;
import com.wanmi.sbc.order.bean.vo.TradeItemVO;
import com.wanmi.sbc.util.CommonUtil;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;


@Tag(name = "MarketingFullReturnController", description = "满返营销服务API")
@RestController
@Validated
@RequestMapping("/fullReturn")
public class MarketingFullReturnController {

    @Autowired
    private FullReturnQueryProvider fullReturnQueryProvider;

    @Autowired
    private GoodsInfoQueryProvider goodsInfoQueryProvider;

    @Autowired
    private MarketingQueryProvider marketingQueryProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private TradeItemQueryProvider tradeItemQueryProvider;

    @Autowired
    private MarketingScopeQueryProvider marketingScopeQueryProvider;

    @Autowired
    protected CouponInfoQueryProvider couponInfoQueryProvider;

    /**
     * 根据营销Id获取赠券信息
     *
     * @param marketingId 活动ID
     * @return
     */
    @Operation(summary = "根据营销Id获取赠券信息")
    @Parameter(name = "marketingId", description = "营销Id", required = true)
    @RequestMapping(value = "/{marketingId}", method = RequestMethod.GET)
    public BaseResponse<FullReturnLevelListByMarketingIdAndCustomerResponse> getReturnByMarketingId(@PathVariable("marketingId") Long marketingId) {
        FullReturnLevelListByMarketingIdAndCustomerRequest fullreturnRequest = FullReturnLevelListByMarketingIdAndCustomerRequest
                .builder().build();
        fullreturnRequest.setMarketingId(marketingId);
        return fullReturnQueryProvider.listReturnByMarketingIdAndCustomer(fullreturnRequest);
    }

    /**
     * 未登录时根据营销Id获取赠券信息
     * @param marketingId 活动ID
     * @return
     */
    @Operation(summary = "未登录时根据营销Id获取赠券信息")
    @Parameter(name = "marketingId", description = "营销Id", required = true)
    @RequestMapping(value = "/unLogin/{marketingId}", method = RequestMethod.GET)
    public BaseResponse<FullReturnLevelListByMarketingIdAndCustomerResponse> getReturnByMarketingIdWithOutLogin(@PathVariable("marketingId")Long marketingId) {
        FullReturnLevelListByMarketingIdAndCustomerRequest fullreturnRequest = FullReturnLevelListByMarketingIdAndCustomerRequest.builder().build();
        fullreturnRequest.setMarketingId(marketingId);
        return fullReturnQueryProvider.listReturnByMarketingIdAndCustomer(fullreturnRequest);
    }

    /**
     * 根据营销Id和规则id获取赠券信息（提供给立即购买->确认订单->赠券选择查询）
     *
     * @param marketingId 活动ID
     * @param levelId 最高规则ID
     * @return
     */
    @Operation(summary = "根据营销Id获取赠券信息")
    @Parameters({
        @Parameter( name = "marketingId", description = "营销Id", required = true),
        @Parameter( name = "levelId", description = "最高规则Id", required = true)
    })
    @RequestMapping(value = "/{marketingId}/{levelId}", method = RequestMethod.GET)
    public BaseResponse<FullReturnLevelListByMarketingIdAndCustomerResponse> getReturnByMarketingId(
            @PathVariable("marketingId") Long marketingId, @PathVariable("levelId") Long levelId) {
        FullReturnLevelListByMarketingIdAndCustomerResponse response = new FullReturnLevelListByMarketingIdAndCustomerResponse();
        response.setLevelList(Collections.emptyList());
        response.setReturnList(Collections.emptyList());
        MarketingGetByIdRequest idRequest = new MarketingGetByIdRequest();
        idRequest.setMarketingId(marketingId);
        MarketingVO marketingVO = marketingQueryProvider.getById(idRequest).getContext().getMarketingVO();
        if(Objects.isNull(marketingVO)){
            return BaseResponse.success(response);
        }
        response.setMarketingSubType(marketingVO.getSubType());

        //满返系列验证
        BigDecimal fullAmount = BigDecimal.ZERO;
        Long fullCount = 0L;
        if(marketingVO.getSubType() == MarketingSubType.RETURN) {
            //获取用户订单快照
            TradeItemSnapshotVO tradeItemSnapshotVO =
                    tradeItemQueryProvider.listByTerminalToken(TradeItemSnapshotByCustomerIdRequest
                            .builder().terminalToken(commonUtil.getTerminalToken()).build()).getContext().getTradeItemSnapshotVO();
            //满系活动只能商家自己创建
            TradeItemGroupVO tradeItemGroupVO = tradeItemSnapshotVO.getItemGroups().stream().filter(t -> t.getSupplier().getStoreId().equals(marketingVO.getStoreId())).findFirst().orElse(null);
            List<TradeItemVO> scopeTradeItems = new ArrayList<>();
            //获取活动商品限制
            List<String> skuIdList = tradeItemGroupVO.getTradeItems().stream().map(TradeItemVO :: getSkuId).collect(Collectors.toList());
            List<GoodsInfoVO> goodsInfoVOList = goodsInfoQueryProvider.listByIds(GoodsInfoListByIdsRequest.builder().goodsInfoIds(skuIdList).build()).getContext().getGoodsInfos();
            if (marketingVO.getScopeType().toValue() == MarketingScopeType.SCOPE_TYPE_ALL.toValue()) {
                scopeTradeItems.addAll(tradeItemGroupVO.getTradeItems());
            } else {
                MarketingScopeByMarketingIdRequest request = new MarketingScopeByMarketingIdRequest();
                request.setMarketingId(marketingId);
                List<MarketingScopeVO> marketingScopeVOList = marketingScopeQueryProvider.listByMarketingId(request).getContext().getMarketingScopeVOList();
                if (CollectionUtils.isNotEmpty(marketingScopeVOList)) {
                    List<String> scopeIdList = marketingScopeVOList.stream().map(MarketingScopeVO :: getScopeId).collect(Collectors.toList());
                    Map<String, TradeItemVO> tradeItemVOMap = tradeItemGroupVO.getTradeItems().stream().collect(Collectors.toMap(TradeItemVO::getSkuId,t -> t));
                    for(GoodsInfoVO goodsInfoVO : goodsInfoVOList) {
                        if (marketingVO.getScopeType().toValue() == MarketingScopeType.SCOPE_TYPE_CUSTOM.toValue() && scopeIdList.contains(goodsInfoVO.getGoodsInfoId())
                                && tradeItemVOMap.containsKey(goodsInfoVO.getGoodsInfoId())) {
                            scopeTradeItems.add(tradeItemVOMap.get(goodsInfoVO.getGoodsInfoId()));
                        }
                        if (marketingVO.getScopeType().toValue() == MarketingScopeType.SCOPE_TYPE_BRAND.toValue() && scopeIdList.contains(goodsInfoVO.getBrandId().toString()) && tradeItemVOMap.containsKey(goodsInfoVO.getGoodsInfoId())) {
                            scopeTradeItems.add(tradeItemVOMap.get(goodsInfoVO.getGoodsInfoId()));
                        }
                        if (marketingVO.getScopeType().toValue() == MarketingScopeType.SCOPE_TYPE_STORE_CATE.toValue() && goodsInfoVO.getStoreCateIds().stream().anyMatch(brandId -> scopeIdList.contains(brandId)) && tradeItemVOMap.containsKey(goodsInfoVO.getGoodsInfoId())) {
                            scopeTradeItems.add(tradeItemVOMap.get(goodsInfoVO.getGoodsInfoId()));
                        }
                    }
                }
            }
            if (CollectionUtils.isNotEmpty(scopeTradeItems)) {
                if(marketingVO.getSubType() == MarketingSubType.RETURN) {
                    for (TradeItemVO tradeItemVO : scopeTradeItems) {
                        for (GoodsInfoVO goodsInfoVO : goodsInfoVOList) {
                            if (tradeItemVO.getSkuId().equals(goodsInfoVO.getGoodsInfoId())) {
                                fullAmount = fullAmount.add(goodsInfoVO.getMarketPrice().multiply(BigDecimal.valueOf(tradeItemVO.getNum())));
                            }
                        }
                    }
                }
                if (marketingVO.getSubType() == MarketingSubType.RETURN) {
                    fullCount = scopeTradeItems.stream().mapToLong(TradeItemVO :: getNum).sum();
                }
            }
        }

        FullReturnLevelListByMarketingIdRequest request = new FullReturnLevelListByMarketingIdRequest();
        request.setMarketingId(marketingId);
        //所有赠券
        List<MarketingFullReturnLevelVO> levels = fullReturnQueryProvider.listLevelByMarketingId(request).getContext().getFullReturnLevelVOList();
        if (CollectionUtils.isNotEmpty(levels)) {
            // 按子类型 由升序排序
            if (marketingVO.getSubType() == MarketingSubType.RETURN) {
                levels.sort(Comparator.comparing(MarketingFullReturnLevelVO::getFullAmount));
            }

            //只返回当前等级以及低级活动赠券规则
            List<MarketingFullReturnLevelVO> levelVos = new ArrayList<>();
            for (MarketingFullReturnLevelVO level : levels) {
                if (level.getReturnLevelId().equals(levelId)) {
                    levelVos.add(level);
                    break;
                }
                if(marketingVO.getSubType() == MarketingSubType.RETURN
                        && fullAmount.compareTo(level.getFullAmount()) < 0) {
                    continue;
                }
                levelVos.add(level);
            }
            response.setLevelList(levelVos);
            //赠券详情信息
            if (CollectionUtils.isNotEmpty(response.getLevelList())) {
                List<String> couponIds = response.getLevelList().stream().filter(s -> CollectionUtils.isNotEmpty(s.getFullReturnDetailList()))
                        .flatMap(s -> s.getFullReturnDetailList().stream())
                        .map(MarketingFullReturnDetailVO::getCouponId).collect(Collectors.toList());
                response.setReturnList(couponInfoQueryProvider.queryCouponInfos(CouponInfoQueryRequest.builder().couponIds(couponIds).build())
                        .getContext().getCouponCodeList());
            }
        }
        return BaseResponse.success(response);
    }
}
