package com.wanmi.sbc.marketing.newplugin.impl;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.google.common.collect.Lists;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoViewByIdsRequest;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.goods.bean.vo.MarketingPluginLabelDetailVO;
import com.wanmi.sbc.goods.bean.vo.MarketingPluginLabelVO;
import com.wanmi.sbc.goods.bean.vo.MarketingPluginSimpleLabelVO;
import com.wanmi.sbc.marketing.api.request.newplugin.GoodsInfoPluginRequest;
import com.wanmi.sbc.marketing.api.request.newplugin.GoodsListPluginRequest;
import com.wanmi.sbc.marketing.api.response.newplugin.GoodsInfoDetailPluginResponse;
import com.wanmi.sbc.marketing.api.response.newplugin.GoodsListPluginResponse;
import com.wanmi.sbc.marketing.bean.dto.CountPriceMarketingDTO;
import com.wanmi.sbc.marketing.bean.enums.MarketingPluginType;
import com.wanmi.sbc.marketing.bean.enums.MarketingSubType;
import com.wanmi.sbc.marketing.bean.enums.MarketingType;
import com.wanmi.sbc.marketing.bean.vo.*;
import com.wanmi.sbc.marketing.common.response.MarketingResponse;
import com.wanmi.sbc.marketing.countPrice.CountMarketingPriceService;
import com.wanmi.sbc.marketing.newplugin.MarketingPluginInterface;
import com.wanmi.sbc.marketing.newplugin.common.MarketingContext;
import com.wanmi.sbc.marketing.newplugin.common.MarketingPluginLabelBuild;
import com.wanmi.sbc.marketing.newplugin.config.MarketingPluginService;
import com.wanmi.sbc.order.bean.enums.OrderErrorCodeEnum;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author edz
 * @className MarketingPreferentialPlugin
 * @description 加价购
 * @date 2022/11/22 17:54
 **/
@Slf4j
@MarketingPluginService(type = MarketingPluginType.PREFERENTIAL)
public class MarketingPreferentialPlugin implements MarketingPluginInterface, CountMarketingPriceService {

    @Autowired
    private GoodsInfoQueryProvider goodsInfoQueryProvider;

    @Override
    public CountPriceItemVO countMarketingPrice(CountPriceItemVO countPriceItemVO, CountPriceMarketingDTO countPriceMarketingDTO) {
        log.info("加价购-算价服务 Begin； goods：{}， marketing:{}", JSONObject.toJSONString(countPriceItemVO),
                JSONObject.toJSONString(countPriceMarketingDTO));
        //选择目标商品
        List<CountPriceItemGoodsInfoVO> itemVOList = countPriceItemVO.getGoodsInfoList().stream()
                .filter(itemGoodsInfoVO -> countPriceMarketingDTO.getSkuIds().contains(itemGoodsInfoVO.getGoodsInfoId())).collect(Collectors.toList());
        if (org.springframework.util.CollectionUtils.isEmpty(itemVOList)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        List<MarketingPreferentialLevelVO> levelList =
                JSONArray.parseArray(JSONObject.toJSONString(Optional.ofNullable(countPriceMarketingDTO.getDetail())),
                        MarketingPreferentialLevelVO.class);

        //获取等级   如果没有指定根据目标商品获取最优等级
        MarketingPreferentialLevelVO level;
        List<MarketingPreferentialLevelVO> inviter;
        // 商品总价
        BigDecimal price = itemVOList.stream().map(CountPriceItemGoodsInfoVO::getSplitPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        // 商品总数
        Long count = itemVOList.stream().map(CountPriceItemGoodsInfoVO::getNum).reduce(0L, Long::sum);
        if (Objects.nonNull(countPriceMarketingDTO.getMarketingLevelId())) {
            inviter =
                    levelList.stream().filter(levelVO -> levelVO.getPreferentialLevelId().compareTo(countPriceMarketingDTO.getMarketingLevelId()) <= 0)
                            .sorted(Comparator.comparing(MarketingPreferentialLevelVO::getPreferentialLevelId).reversed()).collect(Collectors.toList());
            level = inviter.get(0);
        } else {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        if(Objects.isNull(level)){
            log.warn("加价购-算价服务，用户选中的活动规则有误：{}", countPriceMarketingDTO.getMarketingLevelId());
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050084);
        }

        //验证
        MarketingSubType subType = MarketingSubType.PREFERENTIAL_FULL_AMOUNT;
        if (Objects.nonNull(level.getFullAmount())) {
            //满金额
            if (price.compareTo(level.getFullAmount()) < 0) {
                log.warn("加价购-算价服务，订单商品不满足活动门槛：金额{}", price);
                throw new SbcRuntimeException(OrderErrorCodeEnum.K050084);
            }
        } else {
            subType = MarketingSubType.PREFERENTIAL_FULL_COUNT;
            //满数量
            if (count < level.getFullCount()) {
                log.warn("加价购-算价服务，订单商品不满足活动门槛：数量{}", count);
                throw new SbcRuntimeException(OrderErrorCodeEnum.K050084);
            }
        }
        // 查询选中商品信息
        List<MarketingPreferentialGoodsDetailVO> preferentialGoodsDetailList;
        if (CollectionUtils.isNotEmpty(countPriceMarketingDTO.getPreferentialSkuIds())) {
            preferentialGoodsDetailList = inviter.stream().flatMap(g -> g.getPreferentialDetailList().stream()).filter(
                    preferentialGoodsDetail -> countPriceMarketingDTO.getPreferentialSkuIds().contains(preferentialGoodsDetail.getGoodsInfoId()))
                    .collect(Collectors.toList());
        } else {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        List<CountPriceItemForPreferentialVO> countPriceItemForPreferentialVOList = KsBeanUtil.convertList(preferentialGoodsDetailList, CountPriceItemForPreferentialVO.class);
        countPriceItemVO.getPreferentialGoodsList().addAll(countPriceItemForPreferentialVOList);
        //封装商品活动数据
        wrapperItem(itemVOList, countPriceItemForPreferentialVOList, level);
        //封装活动数据
        countPriceItemVO.getTradeMarketings().add(wrapperTradeMarketingVO(itemVOList, inviter, countPriceItemForPreferentialVOList, subType));
        log.info("加价购-算价服务，End：{}", JSONObject.toJSONString(countPriceItemVO));
        return countPriceItemVO;
    }

    /**
     * @description 填充活动信息
     * @author  edz
     * @date: 2022/12/9 15:08
     * @param itemGoodsInfoVOList
     * @param countPriceItemForPreferentialVOList
     * @param level
     * @return java.util.List<com.wanmi.sbc.marketing.bean.vo.CountPriceItemGoodsInfoVO>
     */
    private List<CountPriceItemGoodsInfoVO> wrapperItem(List<CountPriceItemGoodsInfoVO> itemGoodsInfoVOList,
                                                        List<CountPriceItemForPreferentialVO> countPriceItemForPreferentialVOList,
                                                        MarketingPreferentialLevelVO level) {
        itemGoodsInfoVOList.forEach(
                itemGoodsInfoVO -> {
                    CountPriceItemMarketingVO itemMarketingVO =
                            CountPriceItemMarketingVO.builder()
                                    .marketingId(level.getMarketingId())
                                    .marketingType(MarketingType.PREFERENTIAL)
                                    .splitPrice(itemGoodsInfoVO.getSplitPrice())
                                    .countPriceItemForPreferentialVOList(countPriceItemForPreferentialVOList)
                                    .marketingLevelId(level.getPreferentialLevelId())
                                    .build();
                    if (Objects.isNull(itemGoodsInfoVO.getMarketingList())) {
                        itemGoodsInfoVO.setMarketingList(Lists.newArrayList(itemMarketingVO));
                    } else {
                        itemGoodsInfoVO.getMarketingList().add(itemMarketingVO);
                    }
                });
        return itemGoodsInfoVOList;
    }

    /**
     * @description 包装TradeMarketingVO对象
     * @author  edz
     * @date: 2022/12/9 15:07
     * @param itemGoodsInfoVOList
     * @param inviter
     * @param countPriceItemForPreferentialVOList
     * @param subType
     * @return com.wanmi.sbc.marketing.bean.vo.TradeMarketingVO
     */
    private TradeMarketingVO wrapperTradeMarketingVO(List<CountPriceItemGoodsInfoVO> itemGoodsInfoVOList,
                                                     List<MarketingPreferentialLevelVO> inviter,
                                                     List<CountPriceItemForPreferentialVO> countPriceItemForPreferentialVOList,
                                                     MarketingSubType subType) {
        List<String> goodsInfoIdList = itemGoodsInfoVOList.stream().map(CountPriceItemGoodsInfoVO::getGoodsInfoId).collect(Collectors.toList());
        BigDecimal amount = itemGoodsInfoVOList.stream().map(CountPriceItemGoodsInfoVO::getSplitPrice).reduce(BigDecimal.ZERO, BigDecimal::add).setScale(2, RoundingMode.HALF_UP);
        BigDecimal price =
                countPriceItemForPreferentialVOList.stream().map(CountPriceItemForPreferentialVO::getPreferentialAmount).reduce(BigDecimal.ZERO, BigDecimal::add).setScale(2, RoundingMode.HALF_UP);
        BigDecimal discountsAmount = BigDecimal.ZERO;
        for (CountPriceItemForPreferentialVO goodsDetail : countPriceItemForPreferentialVOList){
            discountsAmount =
                    discountsAmount.add(goodsDetail.getGoodsInfoVO().getMarketPrice().subtract(goodsDetail.getPreferentialAmount()));
        }
        List<String> selectSkuIdList =
                countPriceItemForPreferentialVOList.stream().map(CountPriceItemForPreferentialVO ::getGoodsInfoId).collect(Collectors.toList());
        inviter.forEach(level -> {
            List<MarketingPreferentialGoodsDetailVO> goodsDetailVOS =
                    level.getPreferentialDetailList().stream().filter(g -> selectSkuIdList.contains(g.getGoodsInfoId())).collect(Collectors.toList());
            level.setPreferentialDetailList(goodsDetailVOS);
        });
        return TradeMarketingVO.builder()
                .marketingId(inviter.get(0).getMarketingId())
                .marketingType(MarketingType.PREFERENTIAL)
                .marketingLevelId(inviter.get(0).getPreferentialLevelId())
                .subType(subType)
                .skuIds(goodsInfoIdList)
                .discountsAmount(discountsAmount)
                .realPayAmount(amount.add(price))
                .preferentialLevelVOS(inviter)
                .preferentialIds(countPriceItemForPreferentialVOList.stream().map(CountPriceItemForPreferentialVO ::getGoodsInfoId).collect(Collectors.toList()))
                .build();
    }

    @Override
    public GoodsInfoDetailPluginResponse goodsDetail(GoodsInfoPluginRequest request) {
        return MarketingPluginLabelBuild.getDetailResponse(
                this.setLabel(request, MarketingPluginLabelVO.class));
    }

    @Override
    public GoodsListPluginResponse goodsList(GoodsListPluginRequest request) {
        return null;
    }

    @Override
    public MarketingPluginSimpleLabelVO check(GoodsInfoPluginRequest request) {
        return this.setLabel(request,MarketingPluginSimpleLabelVO.class);
    }

    @Override
    public MarketingPluginLabelDetailVO cartMarketing(GoodsInfoPluginRequest request) {
        return setLabel(request,MarketingPluginLabelDetailVO.class);
    }

    @Override
    public MarketingPluginLabelDetailVO tradeMarketing(GoodsInfoPluginRequest request) {
        return setLabel(request,MarketingPluginLabelDetailVO.class);
    }

    private <T extends MarketingPluginSimpleLabelVO> T setLabel(
            GoodsInfoPluginRequest request, Class<T> c) {
        if (MarketingContext.isNotNullMultiMarketingMap(
                request.getGoodsInfoPluginRequest().getGoodsInfoId())) {
            List<MarketingResponse> marketingList =
                    MarketingContext.getBaseRequest()
                            .getMultiTypeMarketingMap()
                            .get(request.getGoodsInfoPluginRequest().getGoodsInfoId())
                            .stream()
                            .filter(
                                    marketing ->
                                            MarketingType.PREFERENTIAL == marketing.getMarketingType())
                            .collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(marketingList)) {
                MarketingResponse marketingObj = marketingList.get(0);
                // 填充营销描述<营销编号,描述>
                MarketingPluginLabelDetailVO label = new MarketingPluginLabelDetailVO();
                label.setMarketingType(MarketingPluginType.PREFERENTIAL.getId());
                label.setMarketingDesc("加价购");
                label.setMarketingId(marketingObj.getMarketingId());
                label.setLinkId(request.getGoodsInfoPluginRequest().getGoodsInfoId());
                if (Objects.nonNull(marketingObj.getSubType())) {
                    label.setSubType(MarketingSubType.PREFERENTIAL_FULL_AMOUNT.equals(marketingObj.getSubType()) ? 0 : 1);
                }
                if (c.equals(MarketingPluginSimpleLabelVO.class)) {
                    return (T) label;
                } else {
                    String desc =
                            this.getLabelMap(marketingList).get(marketingObj.getMarketingId());
                    label.setMarketingDesc(desc);
                    if (c.equals(MarketingPluginLabelVO.class)) {
                        return (T) label;
                    }
                    if (c.equals(MarketingPluginLabelDetailVO.class)) {
                        //封装赠品关联的商品信息
                        List<MarketingPreferentialLevelVO> preferentialLevelList = marketingObj.getPreferentialLevelList();
                        label.setDetail(wrapperGoodsInfo(preferentialLevelList));
                        return (T) label;
                    }
                }
            }
        }
        return null;
    }

    private List<MarketingPreferentialLevelVO> wrapperGoodsInfo(List<MarketingPreferentialLevelVO> preferentialLevelList){
        //获取赠品Id
        List<String> goodsInfoIdList = new ArrayList<>();
        preferentialLevelList.forEach(levelVO -> {
            goodsInfoIdList.addAll(levelVO.getPreferentialDetailList().stream()
                    .map(MarketingPreferentialGoodsDetailVO:: getGoodsInfoId).collect(Collectors.toList()));
        });
        //查询赠品信息
        if (CollectionUtils.isEmpty(goodsInfoIdList)) {
            return preferentialLevelList;
        }
        List<GoodsInfoVO> goodsInfoVOList = goodsInfoQueryProvider.listViewByIds(
                GoodsInfoViewByIdsRequest.builder().goodsInfoIds(goodsInfoIdList).isHavSpecText(Constants.yes).isMarketing(true).build()
        ).getContext().getGoodsInfos();
        if (CollectionUtils.isEmpty(goodsInfoVOList)) {
            return preferentialLevelList;
        }

        //赠品匹配赋值
        Map<String, GoodsInfoVO> goodsInfoVOMap = goodsInfoVOList.stream().
                collect(Collectors.toMap(GoodsInfoVO::getGoodsInfoId, GoodsInfoVO -> GoodsInfoVO));
        preferentialLevelList.forEach(levelVO -> {
            levelVO.getPreferentialDetailList().forEach(detailVO -> {
                if (goodsInfoVOMap.containsKey(detailVO.getGoodsInfoId())) {
                    detailVO.setGoodsInfoVO(goodsInfoVOMap.get(detailVO.getGoodsInfoId()));
                }
            });
        });
        return preferentialLevelList;
    }

    private Map<Long, String> getLabelMap(List<MarketingResponse> marketingList) {
        Map<Long, String> labelMap = new HashMap<>();
        DecimalFormat fmt = new DecimalFormat("#.##");
        marketingList.forEach(
                        marketing -> {
                            if (Objects.nonNull(marketing)
                                    && CollectionUtils.isNotEmpty(
                                    marketing.getPreferentialLevelList())) {
                                if (MarketingSubType.PREFERENTIAL_FULL_COUNT == marketing.getSubType()) {
                                    List<String> count =
                                            marketing.getPreferentialLevelList().stream()
                                                    .filter(
                                                            level ->
                                                                    Objects.nonNull(
                                                                            level.getFullCount()))
                                                    .map(
                                                            level ->
                                                                    ObjectUtils.toString(
                                                                                    level
                                                                                            .getFullCount())
                                                                            .concat("件"))
                                                    .collect(Collectors.toList());
                                    labelMap.put(
                                            marketing.getMarketingId(),
                                            String.format(
                                                    "满%s享超值换购", StringUtils.join(count, "，")));
                                } else {
                                    List<String> amount =
                                            marketing.getPreferentialLevelList().stream()
                                                    .filter(
                                                            level ->
                                                                    Objects.nonNull(
                                                                            level.getFullAmount()))
                                                    .map(level -> fmt.format(level.getFullAmount()).concat("元"))
                                                    .collect(Collectors.toList());
                                    labelMap.put(
                                            marketing.getMarketingId(),
                                            String.format(
                                                    "满%s享超值换购", StringUtils.join(amount, "，")));
                                }
                            }
                        });

        return labelMap;
    }
}
