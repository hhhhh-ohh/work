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
import com.wanmi.sbc.marketing.bean.enums.GiftType;
import com.wanmi.sbc.marketing.bean.enums.MarketingPluginType;
import com.wanmi.sbc.marketing.bean.enums.MarketingSubType;
import com.wanmi.sbc.marketing.bean.enums.MarketingType;
import com.wanmi.sbc.marketing.bean.vo.*;
import com.wanmi.sbc.marketing.common.response.MarketingResponse;
import com.wanmi.sbc.marketing.countPrice.CountMarketingPriceService;
import com.wanmi.sbc.marketing.gift.repository.MarketingFullGiftLevelRepository;
import com.wanmi.sbc.marketing.newplugin.MarketingPluginInterface;
import com.wanmi.sbc.marketing.newplugin.common.MarketingContext;
import com.wanmi.sbc.marketing.newplugin.common.MarketingPluginLabelBuild;
import com.wanmi.sbc.marketing.newplugin.config.MarketingPluginService;
import com.wanmi.sbc.marketing.util.mapper.MarketingMapper;
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
 * 满赠插件
 *
 * @author zhanggaolei
 * @className MarketingGiftPlugin
 * @description TODO
 * @date 2021/5/19 14:08
 */
@Slf4j
@MarketingPluginService(type = MarketingPluginType.GIFT)
public class MarketingGiftPlugin implements MarketingPluginInterface, CountMarketingPriceService {

    @Autowired MarketingFullGiftLevelRepository marketingFullGiftLevelRepository;

    @Autowired private MarketingMapper marketingMapper;

    @Autowired private GoodsInfoQueryProvider goodsInfoQueryProvider;

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


    /**
     * 获取营销描述<营销编号,描述>
     *
     * @param marketingList 营销列表
     * @return
     */
    private Map<Long, String> getLabelMap(List<MarketingResponse> marketingList) {
        Map<Long, String> labelMap = new HashMap<>();
        DecimalFormat fmt = new DecimalFormat("#.##");
        marketingList.stream()
                .forEach(
                        marketing -> {
                            if (Objects.nonNull(marketing)
                                    && CollectionUtils.isNotEmpty(
                                            marketing.getFullGiftLevelList())) {
                                if (MarketingSubType.GIFT_FULL_COUNT == marketing.getSubType()) {
                                    List<String> count =
                                            marketing.getFullGiftLevelList().stream()
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
                                                    "满%s获赠品，赠完为止", StringUtils.join(count, "，")));
                                } else {
                                    List<String> amount =
                                            marketing.getFullGiftLevelList().stream()
                                                    .filter(
                                                            level ->
                                                                    Objects.nonNull(
                                                                            level.getFullAmount()))
                                                    .map(level -> fmt.format(level.getFullAmount()).concat("元"))
                                                    .collect(Collectors.toList());
                                    labelMap.put(
                                            marketing.getMarketingId(),
                                            String.format(
                                                    "满%s获赠品，赠完为止", StringUtils.join(amount, "，")));
                                }
                            }
                        });

        return labelMap;
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
                                            MarketingType.GIFT == marketing.getMarketingType())
                            .collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(marketingList)) {
                MarketingResponse marketingObj = marketingList.get(0);
                // 填充营销描述<营销编号,描述>
                MarketingPluginLabelDetailVO label = new MarketingPluginLabelDetailVO();
                label.setMarketingType(MarketingPluginType.GIFT.getId());
                label.setMarketingDesc("满赠");
                label.setMarketingId(marketingObj.getMarketingId());
                label.setLinkId(request.getGoodsInfoPluginRequest().getGoodsInfoId());
                if (Objects.nonNull(marketingObj.getSubType())) {
                    label.setSubType(MarketingSubType.GIFT_FULL_AMOUNT.equals(marketingObj.getSubType()) ? 0 : 1);
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
                        List<MarketingFullGiftLevelVO> fullGiftLevelVOList = marketingObj.getFullGiftLevelList();
                        label.setDetail(wrapperGiftGoodsInfo(fullGiftLevelVOList));
                        return (T) label;
                    }
                }
            }
        }
        return null;
    }

    /**
     * 封装赠品信息
     * @param fullGiftLevelVOList
     */
    private List<MarketingFullGiftLevelVO> wrapperGiftGoodsInfo(List<MarketingFullGiftLevelVO> fullGiftLevelVOList){
        //获取赠品Id
        List<String> goodsInfoIdList = new ArrayList<>();
        fullGiftLevelVOList.forEach(marketingFullGiftLevel -> {
            goodsInfoIdList.addAll(marketingFullGiftLevel.getFullGiftDetailList().stream().map(MarketingFullGiftDetailVO:: getProductId).collect(Collectors.toList()));
        });
        //查询赠品信息
        if (CollectionUtils.isEmpty(goodsInfoIdList)) {
            return fullGiftLevelVOList;
        }
        List<GoodsInfoVO> giftList = goodsInfoQueryProvider.listViewByIds(
                GoodsInfoViewByIdsRequest.builder().goodsInfoIds(goodsInfoIdList).isHavSpecText(Constants.yes).isMarketing(true).build()
        ).getContext().getGoodsInfos();
        if (CollectionUtils.isEmpty(giftList)) {
            return fullGiftLevelVOList;
        }
//        List<String> providerGoodsInfoIdList = giftList.stream().filter(g -> io.seata.common.util.StringUtils.isNotBlank(g.getProviderGoodsInfoId())).map(GoodsInfoVO::getProviderGoodsInfoId).collect(Collectors.toList());
//        //查询供应商商品 查询供应商库存
//        if (CollectionUtils.isNotEmpty(providerGoodsInfoIdList)) {
//            List<GoodsInfoVO> goodsInfos = goodsInfoQueryProvider.getGoodsInfoByIds(GoodsInfoListByIdsRequest.builder().goodsInfoIds(providerGoodsInfoIdList).build()).getContext().getGoodsInfos();
//            if (CollectionUtils.isNotEmpty(goodsInfos)) {
//                for (GoodsInfoVO goodsInfoVo : giftList) {
//                    for (GoodsInfoVO pGoodsInfo : goodsInfos) {
//                        if (pGoodsInfo.getGoodsInfoId().equals(goodsInfoVo.getProviderGoodsInfoId())) {
//                            goodsInfoVo.setStock(pGoodsInfo.getStock());
//                        }
//                    }
//                    //处理库存小于等于零的商品状态
//                    if(goodsInfoVo.getStock() <= 1) {
//                        goodsInfoVo.setGoodsStatus(GoodsStatus.OUT_STOCK);
//                    }
//                }
//            }
//        }

        //赠品匹配赋值
        Map<String, GoodsInfoVO> goodsInfoVOMap = giftList.stream().collect(Collectors.toMap(GoodsInfoVO::getGoodsInfoId, GoodsInfoVO -> GoodsInfoVO));
        fullGiftLevelVOList.forEach(fullGiftLevel -> {
            fullGiftLevel.getFullGiftDetailList().forEach(giftDetail -> {
                if (goodsInfoVOMap.containsKey(giftDetail.getProductId())) {
                    giftDetail.setGoodsInfo(goodsInfoVOMap.get(giftDetail.getProductId()));
                }
            });
        });

        return fullGiftLevelVOList;
    }

    @Override
    public CountPriceItemVO countMarketingPrice(CountPriceItemVO countPriceItemVO, CountPriceMarketingDTO countPriceMarketingDTO) {
        log.info("满赠-算价服务 Begin； goods：{}， marketing:{}", JSONObject.toJSONString(countPriceItemVO), JSONObject.toJSONString(countPriceMarketingDTO));
        //选择目标商品
        List<CountPriceItemGoodsInfoVO> itemVOList = countPriceItemVO.getGoodsInfoList().stream()
                .filter(itemGoodsInfoVO -> countPriceMarketingDTO.getSkuIds().contains(itemGoodsInfoVO.getGoodsInfoId())).collect(Collectors.toList());
        if (org.springframework.util.CollectionUtils.isEmpty(itemVOList)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        List<MarketingFullGiftLevelVO> levelList = JSONArray.parseArray(JSONObject.toJSONString(Optional.ofNullable(countPriceMarketingDTO.getDetail())), MarketingFullGiftLevelVO.class);

        //获取满赠等级   如果没有指定根据目标商品获取最优等级
        MarketingFullGiftLevelVO level = null;
        //BigDecimal price = itemVOList.stream().map(CountPriceItemGoodsInfoVO::getSplitPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal price = itemVOList.stream().map(CountPriceItemGoodsInfoVO::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        Long count = itemVOList.stream().map(CountPriceItemGoodsInfoVO::getNum).reduce(0L, Long::sum);
        if (Objects.nonNull(countPriceMarketingDTO.getMarketingLevelId())) {
            Optional<MarketingFullGiftLevelVO> inviter = levelList.stream().filter(levelVO -> levelVO.getGiftLevelId().compareTo(countPriceMarketingDTO.getMarketingLevelId()) >=0).findFirst();
            level = inviter.orElse(null);
        } else {
            //满金额
            if (Objects.nonNull(levelList.get(0).getFullAmount())) {
                levelList.sort(Comparator.comparing(MarketingFullGiftLevelVO::getFullAmount).reversed());
                Optional<MarketingFullGiftLevelVO> inviter = levelList.stream().filter(levelVo -> price.compareTo(levelVo.getFullAmount()) >= 0).findFirst();
                level = inviter.orElse(null);
            } else { //满数量
                levelList.sort(Comparator.comparing(MarketingFullGiftLevelVO::getFullCount).reversed());
                Optional<MarketingFullGiftLevelVO> inviter = levelList.stream().filter(levelVo -> count.compareTo(levelVo.getFullCount()) >= 0).findFirst();
                level = inviter.orElse(null);
            }
        }

        if(Objects.isNull(level)){
            log.warn("满赠-算价服务，用户选中的活动规则有误：{}", countPriceMarketingDTO.getMarketingLevelId());
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050084);
        }

        //验证
        MarketingSubType subType = MarketingSubType.GIFT_FULL_AMOUNT;
        if (Objects.nonNull(level.getFullAmount())) {
            //满金额赠
            if (price.compareTo(level.getFullAmount()) < 0) {
                log.warn("满赠-算价服务，订单商品不满足活动门槛：金额{}", price);
                throw new SbcRuntimeException(OrderErrorCodeEnum.K050084);
            }
        } else {
            subType = MarketingSubType.GIFT_FULL_COUNT;
            //满数量赠
            if (count < level.getFullCount()) {
                log.warn("满赠-算价服务，订单商品不满足活动门槛：数量{}", count);
                throw new SbcRuntimeException(OrderErrorCodeEnum.K050084);
            }
        }
        // 查询赠品信息   如果没有指定则赠送该等级的所有赠品  如果未选中并且赠送方式是一个则取第一个赠品
        List<MarketingFullGiftDetailVO> fullGiftDetailList = new ArrayList<>();
        if (CollectionUtils.isEmpty(countPriceMarketingDTO.getGiftSkuIds())) {
            if (GiftType.ALL.toValue() == level.getGiftType().toValue()) {
                fullGiftDetailList = level.getFullGiftDetailList();
            } else {
                fullGiftDetailList.add(level.getFullGiftDetailList().get(0));
            }
        } else {
            fullGiftDetailList =  level.getFullGiftDetailList().stream().filter(
                    marketingFullGiftDetailVO -> countPriceMarketingDTO.getGiftSkuIds().contains(marketingFullGiftDetailVO.getProductId()))
                    .collect(Collectors.toList());
        }

        List<CountPriceItemGiftVO> giftVOList = CollectionUtils.isEmpty(fullGiftDetailList) ? new ArrayList<>() : KsBeanUtil.convertList(fullGiftDetailList, CountPriceItemGiftVO.class);
        countPriceItemVO.getGiftList().addAll(giftVOList);
        //封装商品活动数据
        wrapperItem(itemVOList, giftVOList, level);
        //封装活动数据
        countPriceItemVO.getTradeMarketings().add(wrapperTradeMarketingVO(itemVOList, level, giftVOList, subType));
        log.info("满赠-算价服务，End：{}", JSONObject.toJSONString(countPriceItemVO));
        return countPriceItemVO;
    }

    /**
     *
     * @description   封装营销算价的商品信息
     * @author  wur
     * @date: 2022/2/28 18:31
     * @param itemGoodsInfoVOList
     * @param giftVOList
     * @return
     **/
    private List<CountPriceItemGoodsInfoVO> wrapperItem(List<CountPriceItemGoodsInfoVO> itemGoodsInfoVOList, List<CountPriceItemGiftVO> giftVOList, MarketingFullGiftLevelVO level) {
        itemGoodsInfoVOList.forEach(
                itemGoodsInfoVO -> {
                    CountPriceItemMarketingVO itemMarketingVO =
                            CountPriceItemMarketingVO.builder()
                                    .marketingId(level.getMarketingId())
                                    .marketingType(MarketingType.GIFT)
                                    .splitPrice(itemGoodsInfoVO.getSplitPrice())
                                    .giftList(giftVOList)
                                    .marketingLevelId(level.getGiftLevelId())
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
     *
     * @description 封装营销算价的 营销活动信息
     * @author  wur
     * @date: 2022/2/28 18:31
     * @param itemGoodsInfoVOList
     * @param level
     * @param giftVOList
     * @return
     **/
    private TradeMarketingVO wrapperTradeMarketingVO(List<CountPriceItemGoodsInfoVO> itemGoodsInfoVOList,
                                                     MarketingFullGiftLevelVO level,
                                                     List<CountPriceItemGiftVO> giftVOList, MarketingSubType subType) {
        List<String> goodsInfoIdList = itemGoodsInfoVOList.stream().map(CountPriceItemGoodsInfoVO::getGoodsInfoId).collect(Collectors.toList());
        BigDecimal amount = itemGoodsInfoVOList.stream().map(CountPriceItemGoodsInfoVO::getSplitPrice).reduce(BigDecimal.ZERO, BigDecimal::add).setScale(2, RoundingMode.HALF_UP);
        return TradeMarketingVO.builder()
                .marketingId(level.getMarketingId())
                .marketingType(MarketingType.GIFT)
                .marketingLevelId(level.getGiftLevelId())
                .subType(subType)
                .skuIds(goodsInfoIdList)
                .discountsAmount(BigDecimal.ZERO)
                .realPayAmount(amount)
                .giftLevel(level)
                .giftIds(giftVOList.stream().map(CountPriceItemGiftVO :: getProductId).collect(Collectors.toList()))
                .build();
    }
}
