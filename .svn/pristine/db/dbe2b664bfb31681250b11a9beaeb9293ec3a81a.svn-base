package com.wanmi.sbc.marketing.newplugin.impl;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
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
import com.wanmi.sbc.marketing.bean.vo.CountPriceItemGoodsInfoVO;
import com.wanmi.sbc.marketing.bean.vo.CountPriceItemVO;
import com.wanmi.sbc.marketing.bean.vo.MarketingHalfPriceSecondPieceLevelVO;
import com.wanmi.sbc.marketing.bean.vo.TradeMarketingVO;
import com.wanmi.sbc.marketing.common.response.MarketingResponse;
import com.wanmi.sbc.marketing.countPrice.CountMarketingPriceService;
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
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 第二件半价插件
 *
 * @author zhanggaolei
 * @className MarketingHalfPriceSecondPiecePlugin
 * @description
 * @date 2021/5/19 14:08
 */
@Slf4j
@MarketingPluginService(type = MarketingPluginType.HALF_PRICE_SECOND_PIECE)
public class MarketingHalfPriceSecondPiecePlugin implements MarketingPluginInterface, CountMarketingPriceService {

    @Autowired private MarketingMapper marketingMapper;

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
                            List<String> descs = new ArrayList<>();
                            if (null != marketing
                                    && CollectionUtils.isNotEmpty(
                                            marketing.getHalfPriceSecondPieceLevel())) {
                                for (MarketingHalfPriceSecondPieceLevelVO halfPriceSecondPieceLevel :
                                        marketing.getHalfPriceSecondPieceLevel()) {
                                    if (halfPriceSecondPieceLevel
                                                    .getDiscount()
                                                    .compareTo(BigDecimal.ZERO)
                                            == 0) {
                                        descs.add(
                                                String.format(
                                                        "买%s送1",
                                                        halfPriceSecondPieceLevel.getNumber() - 1));
                                    } else {
                                        descs.add(
                                                String.format(
                                                        "第%s件%s折",
                                                        halfPriceSecondPieceLevel.getNumber(),
                                                        fmt.format(
                                                                halfPriceSecondPieceLevel
                                                                        .getDiscount())));
                                    }
                                }
                            }
                            labelMap.put(marketing.getMarketingId(), StringUtils.join(descs, "，"));
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
                                            MarketingType.HALF_PRICE_SECOND_PIECE
                                                    == marketing.getMarketingType())
                            .collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(marketingList)) {
                MarketingResponse marketingObj = marketingList.get(0);
                // 填充营销描述<营销编号,描述>
                String desc = this.getLabelMap(marketingList).get(marketingObj.getMarketingId());
                MarketingPluginLabelDetailVO label = new MarketingPluginLabelDetailVO();
                label.setMarketingType(MarketingPluginType.HALF_PRICE_SECOND_PIECE.getId());

                label.setMarketingId(marketingObj.getMarketingId());
                label.setLinkId(request.getGoodsInfoPluginRequest().getGoodsInfoId());
                if (StringUtils.isBlank(desc)) {
                    label.setMarketingDesc("N件N折");
                } else {
                    label.setMarketingDesc(desc);
                }

                if (c.equals(MarketingPluginLabelDetailVO.class)) {

                    label.setDetail(marketingObj.getHalfPriceSecondPieceLevel());
                    return (T) label;
                } else {
                    return (T) label;
                }
            }
        }
        return null;
    }

    @Override
    public CountPriceItemVO countMarketingPrice(CountPriceItemVO countPriceItemVO, CountPriceMarketingDTO countPriceMarketingDTO) {
        log.info("第二件半价-算价服务 Begin； goodes：{}， marketing:{}", JSONObject.toJSONString(countPriceItemVO), JSONObject.toJSONString(countPriceMarketingDTO));
        //选择目标商品
        List<CountPriceItemGoodsInfoVO> itemVOList = countPriceItemVO.getGoodsInfoList().stream()
                .filter(itemGoodsInfoVO -> countPriceMarketingDTO.getSkuIds().contains(itemGoodsInfoVO.getGoodsInfoId())).collect(Collectors.toList());
        if (org.springframework.util.CollectionUtils.isEmpty(itemVOList)) {
            log.warn("第二件半价-算价服务, 订单商品信息有误：{}", JSONObject.toJSONString(countPriceMarketingDTO.getSkuIds()));
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        List<MarketingHalfPriceSecondPieceLevelVO> levelList = JSONArray.parseArray(JSONObject.toJSONString(Optional.ofNullable(countPriceMarketingDTO.getDetail())), MarketingHalfPriceSecondPieceLevelVO.class);
        //合计数量
        Long count = itemVOList.stream().map(CountPriceItemGoodsInfoVO::getNum).reduce(0L, Long::sum);
        //按照条件倒序
        levelList.sort(Comparator.comparing(MarketingHalfPriceSecondPieceLevelVO :: getNumber).reversed());
        Optional<MarketingHalfPriceSecondPieceLevelVO> inviter = levelList.stream().filter(level -> count.compareTo(level.getNumber()) >=0 ).findFirst();
        MarketingHalfPriceSecondPieceLevelVO level = inviter.orElse(null);
        if (Objects.isNull(level)) {
            log.warn("第二件半价-算价服务，用户选中的活动规则有误：{}", countPriceMarketingDTO.getMarketingLevelId());
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050084);
        }

        // 计算折扣次数
        long grade = count / level.getNumber();
        // 按价格升序,以便优先以价格低的商品凑单计算优惠金额
        List<CountPriceItemGoodsInfoVO> itemInfoList =
                itemVOList.stream().sorted(Comparator.comparing(CountPriceItemGoodsInfoVO::getPrice)).collect(Collectors.toList());
        Map<String, BigDecimal> beforePriceMap = itemVOList.stream().collect(Collectors.toMap(CountPriceItemGoodsInfoVO::getGoodsInfoId, CountPriceItemGoodsInfoVO::getSplitPrice));
        // 一笔订单满足第二件半价活动时，优惠金额用最低价的几件商品参与折扣计算
        // 商品实付金额=该商品应付金额-该商品分摊的优惠金额
        // 商品分摊的优惠金额=该商品应付金额/活动商品应付总额×优惠总额（精确到小数点后两位）
        // 优惠总额=打折商品原价总额-打折商品折后价格（精确到小数点后两位）
        // 最后一款商品的实付金额=活动商品实付总额-活动中其他商品实付金额
        // 将商品信息封装成新对象便计算优惠
        List<CountPriceItemGoodsInfoVO> discountInfoList = new ArrayList<>();
        exitFlag: for (CountPriceItemGoodsInfoVO item : itemInfoList) {
            for (int i = 0; i < item.getNum(); i++) {
                discountInfoList.add(item);
                if(discountInfoList.size() >= grade) {
                    break exitFlag;
                }
            }
        }
        //优惠金额
        BigDecimal discountsAmount = BigDecimal.ZERO;
        // 有折扣
        if (grade > 0) {
            for (CountPriceItemGoodsInfoVO tradeItemInfo : discountInfoList) {
                BigDecimal price = tradeItemInfo.getSplitPrice().divide(BigDecimal.valueOf(tradeItemInfo.getNum()), 8, RoundingMode.HALF_UP);
                if (level.getDiscount().compareTo(BigDecimal.ZERO) == 0) {
                    // 第N件0折，第N件是买N件送一件，以价格最低的送
                    discountsAmount = discountsAmount.add(price);
                } else {
                    // 第N件以最小的商品金额计算折扣
                    discountsAmount =
                            discountsAmount.add(
                                    price.multiply(
                                            (BigDecimal.valueOf(10)
                                                    .subtract(level.getDiscount())
                                                    .multiply(BigDecimal.valueOf(0.1))))
                                            .setScale(2, RoundingMode.HALF_UP));
                }
            }
        }

        //计算优惠均摊
        discountsAmount = divideAmount(itemVOList, discountsAmount);
        //封装返回数据
        wrapperItem(itemVOList, countPriceMarketingDTO.getMarketingId(),
                countPriceMarketingDTO.getMarketingLevelId(), MarketingType.HALF_PRICE_SECOND_PIECE,
                beforePriceMap);
        countPriceItemVO.getTradeMarketings().add(wrapperTradeMarketingVO(itemVOList, discountsAmount, level));

        //根据活动ID查询规格
        log.info("第二件半价-算价服务 End：" + JSONObject.toJSONString(countPriceItemVO));
        return countPriceItemVO;
    }

    private TradeMarketingVO wrapperTradeMarketingVO(List<CountPriceItemGoodsInfoVO> itemGoodsInfoVOList,
                                                     BigDecimal discountsAmount, MarketingHalfPriceSecondPieceLevelVO level) {
        List<String> goodsInfoIdList = itemGoodsInfoVOList.stream().map(CountPriceItemGoodsInfoVO::getGoodsInfoId).collect(Collectors.toList());
        BigDecimal amount = itemGoodsInfoVOList.stream().map(CountPriceItemGoodsInfoVO::getSplitPrice).reduce(BigDecimal.ZERO, BigDecimal::add).setScale(2, RoundingMode.HALF_UP);
        return TradeMarketingVO.builder()
                .marketingId(level.getMarketingId())
                .marketingLevelId(level.getId())
                .marketingType(MarketingType.HALF_PRICE_SECOND_PIECE)
                .subType(MarketingSubType.HALF_PRICE_SECOND_PIECE)
                .skuIds(goodsInfoIdList)
                .discountsAmount(discountsAmount)
                .realPayAmount(amount)
                .halfPriceSecondPieceLevel(level)
                .build();
    }
}
