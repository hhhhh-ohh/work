package com.wanmi.sbc.marketing;

import com.alibaba.fastjson2.JSON;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoListByIdsRequest;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoViewByIdsRequest;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.marketing.api.provider.gift.FullGiftQueryProvider;
import com.wanmi.sbc.marketing.api.provider.market.MarketingQueryProvider;
import com.wanmi.sbc.marketing.api.provider.market.MarketingScopeQueryProvider;
import com.wanmi.sbc.marketing.api.request.gift.FullGiftLevelListByMarketingIdAndCustomerRequest;
import com.wanmi.sbc.marketing.api.request.gift.FullGiftLevelListByMarketingIdRequest;
import com.wanmi.sbc.marketing.api.request.market.MarketingGetByIdRequest;
import com.wanmi.sbc.marketing.api.request.market.MarketingScopeByMarketingIdRequest;
import com.wanmi.sbc.marketing.api.response.gift.FullGiftLevelListByMarketingIdAndCustomerResponse;
import com.wanmi.sbc.marketing.bean.enums.MarketingScopeType;
import com.wanmi.sbc.marketing.bean.enums.MarketingSubType;
import com.wanmi.sbc.marketing.bean.vo.MarketingFullGiftDetailVO;
import com.wanmi.sbc.marketing.bean.vo.MarketingFullGiftLevelVO;
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

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;


@Tag(name = "MarketingFullGiftController", description = "满赠营销服务API")
@RestController
@RequestMapping("/gift")
@Validated
@Slf4j
public class MarketingFullGiftController {

    @Autowired
    private FullGiftQueryProvider fullGiftQueryProvider;

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

    /**
     * 根据营销Id获取赠品信息
     *
     * @param marketingId 活动ID
     * @return
     */
    @Operation(summary = "根据营销Id获取赠品信息")
    @Parameter(name = "marketingId", description = "营销Id", required = true)
    @RequestMapping(value = "/{marketingId}", method = RequestMethod.GET)
    public BaseResponse<FullGiftLevelListByMarketingIdAndCustomerResponse> getGiftByMarketingId(@PathVariable("marketingId") Long marketingId) {
        FullGiftLevelListByMarketingIdAndCustomerRequest fullgiftRequest = FullGiftLevelListByMarketingIdAndCustomerRequest
                .builder().customerId(commonUtil.getOperatorId()).build();
        fullgiftRequest.setMarketingId(marketingId);
        return fullGiftQueryProvider.listGiftByMarketingIdAndCustomer(fullgiftRequest);
    }

    /**
     * 未登录时根据营销Id获取赠品信息
     * @param marketingId 活动ID
     * @return
     */
    @Operation(summary = "未登录时根据营销Id获取赠品信息")
    @Parameter( name = "marketingId", description = "营销Id", required = true)
    @RequestMapping(value = "/unLogin/{marketingId}", method = RequestMethod.GET)
    public BaseResponse<FullGiftLevelListByMarketingIdAndCustomerResponse> getGiftByMarketingIdWithOutLogin(@PathVariable("marketingId")Long marketingId) {
        FullGiftLevelListByMarketingIdAndCustomerRequest fullgiftRequest = FullGiftLevelListByMarketingIdAndCustomerRequest.builder().build();
        fullgiftRequest.setMarketingId(marketingId);
        return fullGiftQueryProvider.listGiftByMarketingIdAndCustomer(fullgiftRequest);
    }

    /**
     * 根据营销Id和规则id获取赠品信息（提供给立即购买->确认订单->赠品选择查询）
     *
     * @param marketingId 活动ID
     * @param levelId 最高规则ID
     * @return
     */
    @Operation(summary = "根据营销Id获取赠品信息")
    @Parameters({
        @Parameter( name = "marketingId", description = "营销Id", required = true),
        @Parameter(name = "levelId", description = "最高规则Id", required = true)
    })
    @RequestMapping(value = "/{marketingId}/{levelId}", method = RequestMethod.GET)
    public BaseResponse<FullGiftLevelListByMarketingIdAndCustomerResponse> getGiftByMarketingId(
            @PathVariable("marketingId") Long marketingId, @PathVariable("levelId") Long levelId) {
        FullGiftLevelListByMarketingIdAndCustomerResponse response = new FullGiftLevelListByMarketingIdAndCustomerResponse();
        response.setLevelList(Collections.emptyList());
        response.setGiftList(Collections.emptyList());
        MarketingGetByIdRequest idRequest = new MarketingGetByIdRequest();
        idRequest.setMarketingId(marketingId);
        MarketingVO marketingVO = marketingQueryProvider.getById(idRequest).getContext().getMarketingVO();
        if(Objects.isNull(marketingVO)){
            return BaseResponse.success(response);
        }
        response.setMarketingSubType(marketingVO.getSubType());

        //满赠系列验证
        BigDecimal fullAmount = BigDecimal.ZERO;
        Long fullCount = 0L;
        if(marketingVO.getSubType() == MarketingSubType.GIFT_FULL_AMOUNT
                || marketingVO.getSubType() == MarketingSubType.GIFT_FULL_COUNT) {
            //获取用户订单快照
            TradeItemSnapshotVO tradeItemSnapshotVO =
                    tradeItemQueryProvider.listByTerminalToken(TradeItemSnapshotByCustomerIdRequest
                            .builder().terminalToken(commonUtil.getTerminalToken()).build()).getContext().getTradeItemSnapshotVO();
            log.info("用户订单快照---------------："+ JSON.toJSONString(tradeItemSnapshotVO));
            //满系活动只能商家自己创建
            TradeItemGroupVO tradeItemGroupVO =
                    tradeItemSnapshotVO.getItemGroups().stream()
                            .filter(t -> t.getSupplier().getStoreId().equals(marketingVO.getStoreId()))
                            .findFirst()
                            .orElseThrow(() -> new SbcRuntimeException(CommonErrorCodeEnum.K000009));
            List<TradeItemVO> scopeTradeItems = new ArrayList<>();
            //获取活动商品限制
            List<TradeItemVO> tradeItemVOS =
                    tradeItemGroupVO.getTradeItems().stream().filter(g -> g.getMarketingIds().contains(marketingVO.getMarketingId())).collect(Collectors.toList());
            List<String> skuIdList = tradeItemVOS.stream().map(TradeItemVO :: getSkuId).collect(Collectors.toList());
            List<GoodsInfoVO> goodsInfoVOList = goodsInfoQueryProvider.listByIds(GoodsInfoListByIdsRequest.builder().goodsInfoIds(skuIdList).build()).getContext().getGoodsInfos();

            if (marketingVO.getScopeType().toValue() == MarketingScopeType.SCOPE_TYPE_ALL.toValue()) {
                scopeTradeItems.addAll(tradeItemVOS);
            } else {
                MarketingScopeByMarketingIdRequest request = new MarketingScopeByMarketingIdRequest();
                request.setMarketingId(marketingId);
                List<MarketingScopeVO> marketingScopeVOList = marketingScopeQueryProvider.listByMarketingId(request).getContext().getMarketingScopeVOList();
                if (CollectionUtils.isNotEmpty(marketingScopeVOList)) {
                    List<String> scopeIdList = marketingScopeVOList.stream().map(MarketingScopeVO :: getScopeId).collect(Collectors.toList());
                    Map<String, TradeItemVO> tradeItemVOMap = tradeItemVOS.stream().collect(Collectors.toMap(TradeItemVO::getSkuId,t -> t));
                    for(GoodsInfoVO goodsInfoVO : goodsInfoVOList) {
                        if (marketingVO.getScopeType().toValue() == MarketingScopeType.SCOPE_TYPE_CUSTOM.toValue() && scopeIdList.contains(goodsInfoVO.getGoodsInfoId())
                                && tradeItemVOMap.containsKey(goodsInfoVO.getGoodsInfoId())) {
                            scopeTradeItems.add(tradeItemVOMap.get(goodsInfoVO.getGoodsInfoId()));
                        }
                        if (marketingVO.getScopeType().toValue() == MarketingScopeType.SCOPE_TYPE_BRAND.toValue()
                                && scopeIdList.contains(goodsInfoVO.getBrandId().toString())
                                && tradeItemVOMap.containsKey(goodsInfoVO.getGoodsInfoId())) {
                            scopeTradeItems.add(tradeItemVOMap.get(goodsInfoVO.getGoodsInfoId()));
                        }
                        if (marketingVO.getScopeType().toValue() == MarketingScopeType.SCOPE_TYPE_STORE_CATE.toValue()
                                && goodsInfoVO.getStoreCateIds().stream().map(Object::toString).anyMatch(scopeIdList::contains)
                                && tradeItemVOMap.containsKey(goodsInfoVO.getGoodsInfoId())) {
                            scopeTradeItems.add(tradeItemVOMap.get(goodsInfoVO.getGoodsInfoId()));
                        }
                    }
                }
            }
            if (CollectionUtils.isNotEmpty(scopeTradeItems)) {
                if(marketingVO.getSubType() == MarketingSubType.GIFT_FULL_AMOUNT) {
                    for (TradeItemVO tradeItemVO : scopeTradeItems) {
                        fullAmount = fullAmount.add(ObjectUtils.defaultIfNull(tradeItemVO.getSplitPrice(),BigDecimal.ZERO));
//                        for (GoodsInfoVO goodsInfoVO : goodsInfoVOList) {
//                            if (tradeItemVO.getSkuId().equals(goodsInfoVO.getGoodsInfoId())) {
//                                fullAmount = fullAmount.add(goodsInfoVO.getMarketPrice().multiply(BigDecimal.valueOf(tradeItemVO.getNum())));
//                            }
//                        }
                    }
                }
                if (marketingVO.getSubType() == MarketingSubType.GIFT_FULL_COUNT) {
                    fullCount = scopeTradeItems.stream().mapToLong(TradeItemVO :: getNum).sum();
                }
            }
        }

        FullGiftLevelListByMarketingIdRequest request = new FullGiftLevelListByMarketingIdRequest();
        request.setMarketingId(marketingId);
        //所有赠品
        List<MarketingFullGiftLevelVO> levels = fullGiftQueryProvider.listLevelByMarketingId(request).getContext().getFullGiftLevelVOList();
        if (CollectionUtils.isNotEmpty(levels)) {
            // 按子类型 由升序排序
            if (marketingVO.getSubType() == MarketingSubType.GIFT_FULL_AMOUNT) {
                levels.sort(Comparator.comparing(MarketingFullGiftLevelVO::getFullAmount));
            }else if (marketingVO.getSubType() == MarketingSubType.GIFT_FULL_COUNT) {
                levels.sort(Comparator.comparing(MarketingFullGiftLevelVO::getFullCount));
            }

            //只返回当前等级以及低级活动赠品规则
            List<MarketingFullGiftLevelVO> levelVos = new ArrayList<>();
            for (MarketingFullGiftLevelVO level : levels) {
                if (level.getGiftLevelId().equals(levelId)) {
                    levelVos.add(level);
                    break;
                }
                if(marketingVO.getSubType() == MarketingSubType.GIFT_FULL_AMOUNT
                        && fullAmount.compareTo(level.getFullAmount()) < 0) {
                    continue;
                } else if (marketingVO.getSubType() == MarketingSubType.GIFT_FULL_COUNT
                        && fullCount.compareTo(level.getFullCount()) < 0) {
                    continue;
                }
                levelVos.add(level);
            }
            response.setLevelList(levelVos);
            //赠品详情信息
            if (CollectionUtils.isNotEmpty(response.getLevelList())) {
                List<String> skuIds = response.getLevelList().stream().filter(s -> CollectionUtils.isNotEmpty(s.getFullGiftDetailList()))
                        .flatMap(s -> s.getFullGiftDetailList().stream())
                        .map(MarketingFullGiftDetailVO::getProductId).collect(Collectors.toList());
                response.setGiftList(goodsInfoQueryProvider.listViewByIds(GoodsInfoViewByIdsRequest.builder()
                        .goodsInfoIds(skuIds).isHavSpecText(Constants.yes).isMarketing(Boolean.TRUE).build()).getContext().getGoodsInfos());
            }
        }
        return BaseResponse.success(response);
    }
}
