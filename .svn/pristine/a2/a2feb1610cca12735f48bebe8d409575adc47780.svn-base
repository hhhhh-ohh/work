package com.wanmi.sbc.marketing.newplugin.impl;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.goods.bean.vo.MarketingPluginLabelDetailVO;
import com.wanmi.sbc.goods.bean.vo.MarketingPluginLabelVO;
import com.wanmi.sbc.goods.bean.vo.MarketingPluginSimpleLabelVO;
import com.wanmi.sbc.marketing.api.request.coupon.CouponInfoQueryRequest;
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
import com.wanmi.sbc.marketing.coupon.model.root.CouponInfo;
import com.wanmi.sbc.marketing.coupon.service.CouponInfoService;
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
 * 满返插件
 *
 * @author xufeng
 * @className MarketingFullReturnPlugin
 * @description TODO
 * @date 2022/4/11 14:08
 */
@Slf4j
@MarketingPluginService(type = MarketingPluginType.RETURN)
public class MarketingFullReturnPlugin implements MarketingPluginInterface, CountMarketingPriceService {

    @Autowired
    private MarketingMapper marketingMapper;

    @Autowired
    private CouponInfoService couponInfoService;

    @Override
    public GoodsInfoDetailPluginResponse goodsDetail(GoodsInfoPluginRequest request) {
        return MarketingPluginLabelBuild.getDetailResponse(
                this.setLabel(request, MarketingPluginLabelDetailVO.class));
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
        marketingList.forEach(
                        marketing -> {
                            if (Objects.nonNull(marketing)
                                    && CollectionUtils.isNotEmpty(
                                            marketing.getFullReturnLevelList())) {
                                List<String> amount =
                                        marketing.getFullReturnLevelList().stream()
                                                .filter(
                                                        level ->
                                                                Objects.nonNull(
                                                                        level.getFullAmount()))
                                                .map(level -> fmt.format(level.getFullAmount()))
                                                .collect(Collectors.toList());
                                labelMap.put(
                                        marketing.getMarketingId(),
                                        String.format(
                                                "满%s元获优惠券，赠完为止", StringUtils.join(amount, "，")));
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
                                            MarketingType.RETURN == marketing.getMarketingType())
                            .collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(marketingList)) {
                MarketingResponse marketingObj;
                if (Constants.BOSS_DEFAULT_STORE_ID.equals(request.getStoreId())){
                    marketingObj =
                            marketingList.stream().filter(marketingResponse -> marketingResponse.getIsBoss()== BoolFlag.YES).findFirst().orElse(null);
                }else {
                    marketingObj =
                            marketingList.stream().filter(marketingResponse -> marketingResponse.getIsBoss()== BoolFlag.NO).findFirst().orElse(null);
                }
                if (Objects.isNull(marketingObj)){
                    return null;
                }
                // 填充营销描述<营销编号,描述>
                MarketingPluginLabelDetailVO label = new MarketingPluginLabelDetailVO();
                label.setMarketingType(MarketingPluginType.RETURN.getId());
                label.setMarketingDesc("满返");
                label.setMarketingId(marketingObj.getMarketingId());
                label.setLinkId(request.getGoodsInfoPluginRequest().getGoodsInfoId());
                if (Objects.nonNull(marketingObj.getSubType())) {
                    label.setSubType(marketingObj.getSubType().toValue());
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
                        //封装优惠券信息
                        List<MarketingFullReturnLevelVO> fullReturnLevelVOList = marketingMapper.returnLevelsToReturnLevelVOs(
                                marketingObj.getFullReturnLevelList());
                        label.setDetail(wrapperReturnCouponInfo(fullReturnLevelVOList));
                        return (T) label;
                    }
                }
            }
        }
        return null;
    }

    /**
     * 封装赠券信息
     * @param fullReturnLevelVOList
     */
    private List<MarketingFullReturnLevelVO> wrapperReturnCouponInfo(List<MarketingFullReturnLevelVO> fullReturnLevelVOList){
        //获取赠券Id
        List<String> couponInfoIdList = new ArrayList<>();
        fullReturnLevelVOList.forEach(marketingFullReturnLevel -> {
            couponInfoIdList.addAll(marketingFullReturnLevel.getFullReturnDetailList().stream().map(MarketingFullReturnDetailVO:: getCouponId).collect(Collectors.toList()));
        });
        //查询赠券信息
        if (CollectionUtils.isEmpty(couponInfoIdList)) {
            return fullReturnLevelVOList;
        }
        //获取优惠券信息
        List<CouponInfo> returnList = couponInfoService.queryCouponInfos(CouponInfoQueryRequest.builder().couponIds(couponInfoIdList).build());
        if (CollectionUtils.isEmpty(returnList)) {
            return fullReturnLevelVOList;
        }
        List<MagicCouponInfoVO> magicCouponInfoVOS = couponInfoService.getMagicCouponInfoVOS(returnList);

        //赠券匹配赋值
        Map<String, MagicCouponInfoVO> couponInfoVOMap =
                magicCouponInfoVOS.stream().collect(Collectors.toMap(MagicCouponInfoVO::getCouponId, MagicCouponInfoVO -> MagicCouponInfoVO));
        fullReturnLevelVOList.forEach(fullReturnLevel -> {
            fullReturnLevel.getFullReturnDetailList().forEach(returnDetail -> {
                if (couponInfoVOMap.containsKey(returnDetail.getCouponId())) {
                    returnDetail.setMagicCouponInfoVO(couponInfoVOMap.get(returnDetail.getCouponId()));
                }
            });
        });

        return fullReturnLevelVOList;
    }

    @Override
    public CountPriceItemVO countMarketingPrice(CountPriceItemVO countPriceItemVO, CountPriceMarketingDTO countPriceMarketingDTO) {
        log.info("满返-算价服务 Begin； goods：{}， marketing:{}", JSONObject.toJSONString(countPriceItemVO), JSONObject.toJSONString(countPriceMarketingDTO));
        //选择目标商品
        List<CountPriceItemGoodsInfoVO> itemVOList = countPriceItemVO.getGoodsInfoList().stream()
                .filter(itemGoodsInfoVO -> countPriceMarketingDTO.getSkuIds().contains(itemGoodsInfoVO.getGoodsInfoId())).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(itemVOList)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        List<MarketingFullReturnLevelVO> levelList = JSONArray.parseArray(JSONObject.toJSONString(Optional.ofNullable(countPriceMarketingDTO.getDetail())), MarketingFullReturnLevelVO.class);

        //获取满返等级   如果没有指定根据目标商品获取最优等级
        MarketingFullReturnLevelVO level = null;
        BigDecimal price = itemVOList.stream().map(CountPriceItemGoodsInfoVO::getSplitPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        if (Objects.nonNull(countPriceMarketingDTO.getMarketingLevelId())) {
            Optional<MarketingFullReturnLevelVO> inviter = levelList.stream().filter(levelVO -> levelVO.getReturnLevelId().compareTo(countPriceMarketingDTO.getMarketingLevelId()) >=0).findFirst();
            level = inviter.orElse(null);
        } else {
            //满金额
            levelList.sort(Comparator.comparing(MarketingFullReturnLevelVO::getFullAmount).reversed());
            Optional<MarketingFullReturnLevelVO> inviter = levelList.stream().filter(levelVo -> price.compareTo(levelVo.getFullAmount()) >= 0).findFirst();
            level = inviter.orElse(null);
        }

        if(Objects.isNull(level)){
            log.warn("满返-算价服务，用户选中的活动规则有误：{}", countPriceMarketingDTO.getMarketingLevelId());
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050084);
        }

        //验证
        MarketingSubType subType = MarketingSubType.RETURN;
        if (Objects.nonNull(level.getFullAmount())) {
            //满金额折
            if (price.compareTo(level.getFullAmount()) < 0) {
                log.warn("满返-算价服务，订单商品不满足活动门槛：金额{}", price);
                throw new SbcRuntimeException(OrderErrorCodeEnum.K050084);
            }
        }
        // 查询赠券信息
        List<MarketingFullReturnDetailVO> fullReturnDetailList = new ArrayList<>();
        if (CollectionUtils.isEmpty(countPriceMarketingDTO.getCouponIds())) {
            fullReturnDetailList = level.getFullReturnDetailList();
        } else {
            fullReturnDetailList =  level.getFullReturnDetailList().stream().filter(
                    marketingFullReturnDetailVO -> countPriceMarketingDTO.getCouponIds().contains(marketingFullReturnDetailVO.getCouponId()))
                    .collect(Collectors.toList());
        }

        List<CountPriceItemReturnVO> returnVOList = CollectionUtils.isEmpty(fullReturnDetailList) ? new ArrayList<>() :
                KsBeanUtil.convertList(fullReturnDetailList, CountPriceItemReturnVO.class);
        countPriceItemVO.getReturnList().addAll(returnVOList);
        //封装商品活动数据
        wrapperItem(itemVOList, returnVOList, level);
        //封装活动数据
        countPriceItemVO.getTradeMarketings().add(wrapperTradeMarketingVO(itemVOList, level, returnVOList, subType));
        log.info("满返-算价服务，End：{}", JSONObject.toJSONString(countPriceItemVO));
        return countPriceItemVO;
    }

    /**
     *
     * @description   封装营销算价的商品信息
     * @author  xufeng
     * @date: 2022/04/12 11:31
     * @param itemGoodsInfoVOList
     * @param returnVOList
     * @return
     **/
    private List<CountPriceItemGoodsInfoVO> wrapperItem(List<CountPriceItemGoodsInfoVO> itemGoodsInfoVOList,
                                                        List<CountPriceItemReturnVO> returnVOList,
                                                        MarketingFullReturnLevelVO level) {
        itemGoodsInfoVOList.forEach(
                itemGoodsInfoVO -> {
                    CountPriceItemMarketingVO itemMarketingVO =
                            CountPriceItemMarketingVO.builder()
                                    .marketingId(level.getMarketingId())
                                    .marketingType(MarketingType.RETURN)
                                    .splitPrice(itemGoodsInfoVO.getSplitPrice())
                                    .returnList(returnVOList)
                                    .marketingLevelId(level.getReturnLevelId())
                                    .build();
                    if (Objects.isNull(itemGoodsInfoVO.getMarketingList())) {
                        itemGoodsInfoVO.setMarketingList(Collections.singletonList(itemMarketingVO));
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
     * @param returnVOList
     * @return
     **/
    private TradeMarketingVO wrapperTradeMarketingVO(List<CountPriceItemGoodsInfoVO> itemGoodsInfoVOList,
                                                     MarketingFullReturnLevelVO level,
                                                     List<CountPriceItemReturnVO> returnVOList,
                                                     MarketingSubType subType) {
        List<String> goodsInfoIdList = itemGoodsInfoVOList.stream().map(CountPriceItemGoodsInfoVO::getGoodsInfoId).collect(Collectors.toList());
        BigDecimal amount = itemGoodsInfoVOList.stream().map(CountPriceItemGoodsInfoVO::getSplitPrice).reduce(BigDecimal.ZERO, BigDecimal::add).setScale(2, RoundingMode.HALF_UP);
        return TradeMarketingVO.builder()
                .marketingId(level.getMarketingId())
                .marketingType(MarketingType.RETURN)
                .marketingLevelId(level.getReturnLevelId())
                .subType(subType)
                .skuIds(goodsInfoIdList)
                .discountsAmount(BigDecimal.ZERO)
                .realPayAmount(amount)
                .returnLevel(level)
                .returnIds(returnVOList.stream().map(CountPriceItemReturnVO :: getCouponId).collect(Collectors.toList()))
                .build();
    }
}
