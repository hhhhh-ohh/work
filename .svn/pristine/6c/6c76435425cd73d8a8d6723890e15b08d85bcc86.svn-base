package com.wanmi.sbc.order.trade.service;

import com.wanmi.sbc.common.constant.RedisKeyConstant;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.goods.api.provider.buycyclegoods.BuyCycleGoodsQueryProvider;
import com.wanmi.sbc.goods.api.request.buycyclegoods.BuyCycleGoodsBySkuIdRequest;
import com.wanmi.sbc.goods.api.request.info.TradeConfirmGoodsRequest;
import com.wanmi.sbc.goods.api.response.info.GoodsInfoResponse;
import com.wanmi.sbc.goods.bean.enums.DeliveryCycleType;
import com.wanmi.sbc.goods.bean.vo.BuyCycleVO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.goods.bean.vo.GoodsVO;
import com.wanmi.sbc.goods.bean.vo.MarketingPluginLabelDetailVO;
import com.wanmi.sbc.marketing.bean.constant.MarketingPluginConstant;
import com.wanmi.sbc.order.api.request.trade.BuyCyclePlanRequest;
import com.wanmi.sbc.order.api.response.trade.BuyCycleInfoResponse;
import com.wanmi.sbc.order.bean.dto.CycleDeliveryPlanDTO;
import com.wanmi.sbc.order.bean.dto.TradeItemDTO;
import com.wanmi.sbc.order.bean.enums.CycleDeliveryState;
import com.wanmi.sbc.order.bean.enums.OrderErrorCodeEnum;
import com.wanmi.sbc.order.bean.vo.CycleDeliveryPlanVO;
import com.wanmi.sbc.order.bean.vo.TradeItemVO;
import com.wanmi.sbc.order.bean.vo.TradeSimpleBuyCycleVO;
import com.wanmi.sbc.order.optimization.trade1.confirm.service.query.TradeConfirmQueryService;
import com.wanmi.sbc.order.optimization.trade1.snapshot.base.service.QueryDataInterface;
import com.wanmi.sbc.order.optimization.trade1.snapshot.base.service.TradeBuyAssembleInterface;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author xuyunpeng
 * @className TradeBuyCycleService
 * @description
 * @date 2022/10/19 1:58 PM
 **/
@Service
public class TradeBuyCycleService {


    @Autowired
    private BuyCycleGoodsQueryProvider buyCycleGoodsQueryProvider;

    @Autowired
    @Qualifier("queryDataService")
    private QueryDataInterface queryDataInterface;

    @Autowired
    @Qualifier("tradeBuyAssembleService")
    private TradeBuyAssembleInterface tradeBuyAssembleInterface;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private BuyCycleDeliveryPlanService buyCycleDeliveryPlanService;

    @Autowired
    private TradeConfirmQueryService tradeConfirmQueryService;

    /**
     * 查询周期计划
     * @param skuId
     * @return
     */
    public BuyCycleInfoResponse buyCycleInfo(String skuId, String customerId, String terminalToken){
        BuyCycleInfoResponse buyCycleInfoResponse = new BuyCycleInfoResponse();

        //周期购信息
        BuyCycleVO buyCycleVO = buyCycleGoodsQueryProvider.getBySkuId(
                BuyCycleGoodsBySkuIdRequest.builder().skuId(skuId).build()).getContext().getBuyCycleVO();

        if (buyCycleVO == null) {
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050136);
        }

        //商品信息
        TradeConfirmGoodsRequest goodsRequest =
                TradeConfirmGoodsRequest.builder()
                        .skuIds(Collections.singletonList(skuId))
                        .isHavSpecText(Boolean.TRUE)
                        .isHavIntervalPrice(Boolean.FALSE)
                        .showLabelFlag(Boolean.FALSE)
                        .showSiteLabelFlag(Boolean.FALSE)
                        .isHavRedisStock(Boolean.FALSE)
                        .build();
        GoodsInfoResponse goodsInfoResponse = queryDataInterface.getGoodsInfo(goodsRequest);
        // <sku, goodsInfo> goodsInfo是数据库查询出来的
        Map<String, GoodsInfoVO> skuGoodsInfoMap =
                goodsInfoResponse.getGoodsInfos().stream()
                        .collect(
                                Collectors.toMap(GoodsInfoVO::getGoodsInfoId, Function.identity()));

        // <spu, goods> goods是数据库查询出来的
        Map<String, GoodsVO> spuGoodsMap =
                goodsInfoResponse.getGoodses().stream()
                        .collect(Collectors.toMap(GoodsVO::getGoodsId, Function.identity()));

        // 构建TradeItemDTO
        GoodsInfoVO goodsInfoVO = skuGoodsInfoMap.get(skuId);
        TradeItemDTO tradeItemDTO = new TradeItemDTO();
        tradeItemDTO.setNum(Constants.NUM_1L);
        // 构建tradeItem基础数据
        tradeBuyAssembleInterface.tradeItemBaseBuilder(
                tradeItemDTO,
                goodsInfoVO,
                spuGoodsMap.get(goodsInfoVO.getGoodsId()),
                goodsInfoResponse.getGoodsInfos().get(0).getStoreId());
        if (StringUtils.isBlank(tradeItemDTO.getPic())) {
            tradeItemDTO.setPic(spuGoodsMap.get(goodsInfoVO.getGoodsId()).getGoodsImg());
        }
        // 构建tradeItem价格信息
        tradeItemDTO.setLevelPrice(buyCycleVO.getCyclePrice());
        tradeItemDTO.setOriginalPrice(buyCycleVO.getCyclePrice());
        tradeItemDTO.setPrice(buyCycleVO.getCyclePrice());
        tradeItemDTO.setSplitPrice(buyCycleVO.getCyclePrice());
        // 查询店铺
        StoreVO storeVO =
                queryDataInterface.getStoreInfo(
                        goodsInfoResponse.getGoodsInfos().get(0).getStoreId(),
                        customerId);

        //营销价格 粉丝价/付费会员价
        Map<String, List<MarketingPluginLabelDetailVO>> marketingMap = tradeConfirmQueryService.getMarketing(goodsInfoResponse.getGoodsInfos(), customerId, false, false);
        if (MapUtils.isNotEmpty(marketingMap)) {
            List<MarketingPluginLabelDetailVO> marketings = marketingMap.get(skuId);
            if (CollectionUtils.isNotEmpty(marketings)) {
                for (MarketingPluginLabelDetailVO marketingPlugin : marketings) {
                    if (MarketingPluginConstant.PRICE_EXIST.contains(marketingPlugin.getMarketingType())) {
                        tradeItemDTO.setLevelPrice(marketingPlugin.getPluginPrice());
                        tradeItemDTO.setPrice(marketingPlugin.getPluginPrice());
                        tradeItemDTO.setSplitPrice(marketingPlugin.getPluginPrice());
                    }
                }
            }
        }
        //配送计划
        List<CycleDeliveryPlanVO> plans = new ArrayList<>();
        LocalDate startDate = buyCycleDeliveryPlanService.getChoseStartDay(buyCycleVO);
        LocalDate endDate = buyCycleDeliveryPlanService.getChoseEndDate(startDate);
        LocalDateTime deliveryDate = LocalDateTime.now();
        for (int i = 1; i <= buyCycleVO.getMinCycleNum(); i++) {
            deliveryDate = buyCycleDeliveryPlanService.getNextDeliveryDate(i, deliveryDate, buyCycleVO).atStartOfDay();
            CycleDeliveryPlanVO plan = new CycleDeliveryPlanVO();
            plan.setDeliveryNum(i);
            plan.setCycleDeliveryState(CycleDeliveryState.NOT_SHIP);
            plan.setDeliveryDate(deliveryDate.toLocalDate());
            plans.add(plan);
        }

        TradeSimpleBuyCycleVO tradeBuyCycleVO = KsBeanUtil.convert(buyCycleVO, TradeSimpleBuyCycleVO.class);
        tradeBuyCycleVO.setDeliveryStartDate(startDate);
        tradeBuyCycleVO.setDeliveryEndDate(endDate);
        tradeBuyCycleVO.setPlans(plans);

        //缓存周期购信息
        redisUtil.setObj(RedisKeyConstant.BUY_CYCLE_TRADE_INFO + terminalToken, buyCycleVO, 24*60*60L);

        buyCycleInfoResponse.setTradeBuyCycleVO(tradeBuyCycleVO);
        buyCycleInfoResponse.setTradeItemVO(KsBeanUtil.convert(tradeItemDTO, TradeItemVO.class));
        buyCycleInfoResponse.setStoreVO(storeVO);
        return buyCycleInfoResponse;
    }


    /**
     * 根据期数和开始时间生成配送计划
     * @param request
     */
    public List<CycleDeliveryPlanVO> deliveryPlans(BuyCyclePlanRequest request){
        // 周期购信息
        BuyCycleVO buyCycleVO = redisUtil.getObj(
                RedisKeyConstant.BUY_CYCLE_TRADE_INFO + request.getTerminalToken(),
                BuyCycleVO.class);
        if (buyCycleVO == null) {
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050136);
        }
        check(request.getCycleNum(), request.getDeliveryDate(), buyCycleVO, null);
        List<CycleDeliveryPlanDTO> plans;
        if (DeliveryCycleType.WEEK_OPT_MANY.toValue() == buyCycleVO.getDeliveryCycle()
                || DeliveryCycleType.MONTH_OPT_MANY.toValue() == buyCycleVO.getDeliveryCycle()) {
            plans = this.createOptPlans(request.getDeliveryDate(), request.getCycleNum(), buyCycleVO);
        } else {
            plans = this.createPlans(request.getDeliveryDate(), request.getCycleNum(), buyCycleVO, null);
        }
        return KsBeanUtil.convertList(plans, CycleDeliveryPlanVO.class);
    }

    /**
     * 获取自选周期发货日期
     * @param deliveryDate  开始时间
     * @param cycleNum      购买期数
     * @param buyCycleVO    周期购配置
     * @return
     */
    private List<CycleDeliveryPlanDTO> createOptPlans(LocalDate deliveryDate, Integer cycleNum, BuyCycleVO buyCycleVO) {
        int dayOf = deliveryDate.getDayOfMonth();
        if (DeliveryCycleType.WEEK_OPT_MANY.toValue() == buyCycleVO.getDeliveryCycle()) {
            dayOf = deliveryDate.getDayOfWeek().getValue();
        }

        String[] ruleArrays = buyCycleVO.getDeliveryDate().split(",");
        List<Integer> rules = Arrays.stream(ruleArrays).map(Integer::parseInt).sorted().collect(Collectors.toList());
        rules = rules.subList(0, buyCycleVO.getOptionalNum()-1);
        if (!rules.contains(dayOf)) {
            if (DeliveryCycleType.WEEK_OPT_MANY.toValue() == buyCycleVO.getDeliveryCycle()) {
                deliveryDate = deliveryDate.plusWeeks(1).with(ChronoField.DAY_OF_WEEK, rules.get(0));
            } else {
                deliveryDate = deliveryDate.withDayOfMonth(rules.get(0));
            }
        }

        List<CycleDeliveryPlanDTO> plans = new ArrayList<>();
        CycleDeliveryPlanDTO firstDTO = new CycleDeliveryPlanDTO();
        firstDTO.setDeliveryDate(deliveryDate);
        firstDTO.setCycleDeliveryState(CycleDeliveryState.NOT_SHIP);
        firstDTO.setDeliveryNum(Constants.ONE);
        plans.add(firstDTO);
        for (int i = 2; i <= cycleNum; i++) {
            LocalDate date = plans.get(i - 2).getDeliveryDate();
            LocalDate nextDeliveryDate = buyCycleDeliveryPlanService.getNextDeliveryDate(i, date.atStartOfDay(), buyCycleVO);
            CycleDeliveryPlanDTO dto = new CycleDeliveryPlanDTO();
            dto.setDeliveryNum(i);
            dto.setDeliveryDate(nextDeliveryDate);
            dto.setCycleDeliveryState(CycleDeliveryState.NOT_SHIP);
            plans.add(dto);
        }
        return plans;
    }

    /**
     * 期数、首期送达时间校验
     * @param cycleNum
     * @param deliveryDate
     * @param buyCycleVO
     */
    public void check(Integer cycleNum, LocalDate deliveryDate, BuyCycleVO buyCycleVO, List<LocalDate> deliveryDateList) {
        //最低期数
        if (cycleNum < buyCycleVO.getMinCycleNum()) {
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050134, new Object[]{buyCycleVO.getMinCycleNum()});
        }
        //最多购买期数
        if (cycleNum > Constants.NUM_365) {
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050138, new Object[]{Constants.NUM_365});
        }
        //首期日期是否在可选范围内
        LocalDateTime now = LocalDateTime.now();
        if(Objects.nonNull(deliveryDate)) {
            LocalDate startDate = buyCycleDeliveryPlanService.getChoseStartDay(buyCycleVO);
            LocalDate endDate = buyCycleDeliveryPlanService.getChoseEndDate(startDate);
            if (deliveryDate.isBefore(startDate) || deliveryDate.isAfter(endDate)) {
                throw new SbcRuntimeException(OrderErrorCodeEnum.K050135);
            }
        }
        //首期日期是否在用户可选日期规则内
        if (DeliveryCycleType.WEEK_ONE.toValue() == buyCycleVO.getDeliveryCycle()
                || DeliveryCycleType.WEEK_MANY.toValue() == buyCycleVO.getDeliveryCycle()) {
            int dayOfWeek = deliveryDate.getDayOfWeek().getValue();
            String[] rules = buyCycleVO.getDeliveryDate().split(",");
            if (!Arrays.asList(rules).contains(String.valueOf(dayOfWeek))) {
                throw new SbcRuntimeException(OrderErrorCodeEnum.K050135);
            }

        } else if (DeliveryCycleType.MONTH_ONE.toValue() == buyCycleVO.getDeliveryCycle()
                || DeliveryCycleType.MONTH_MANY.toValue() == buyCycleVO.getDeliveryCycle()) {
            int dayOfMonth = deliveryDate.getDayOfMonth();
            String[] rules = buyCycleVO.getDeliveryDate().split(",");
            if (!Arrays.asList(rules).contains(String.valueOf(dayOfMonth))) {
                throw new SbcRuntimeException(OrderErrorCodeEnum.K050135);
            }
        } else if (DeliveryCycleType.WEEK_OPT_MANY.toValue() == buyCycleVO.getDeliveryCycle()
                || DeliveryCycleType.MONTH_OPT_MANY.toValue() == buyCycleVO.getDeliveryCycle()) { // 每周自选多期
            if (CollectionUtils.isNotEmpty(deliveryDateList)) {
                List<String> rules = Arrays.asList(buyCycleVO.getDeliveryDate().split(","));
                long count = deliveryDateList.stream().filter(localDate -> {
                    int dayOf = localDate.getDayOfMonth();
                    if (DeliveryCycleType.WEEK_OPT_MANY.toValue() == buyCycleVO.getDeliveryCycle()) {
                        dayOf = localDate.getDayOfWeek().getValue();
                    }
                    return !rules.contains(String.valueOf(dayOf));
                }).count();
                if (count > 0 ) {
                    throw new SbcRuntimeException(OrderErrorCodeEnum.K050135);
                }

                if (DeliveryCycleType.WEEK_OPT_MANY.toValue() == buyCycleVO.getDeliveryCycle()) {
                    Map<Integer, List<LocalDate>> groupedByWeek =
                            deliveryDateList.stream()
                                    .collect(Collectors.groupingBy(
                                            date -> date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
                                                    .get(ChronoField.ALIGNED_WEEK_OF_YEAR)
                                    ));
                    groupedByWeek.forEach((week, datesInWeek) -> {
                        if (datesInWeek.size() > buyCycleVO.getOptionalNum()) {
                            throw new SbcRuntimeException(OrderErrorCodeEnum.K050135);
                        }
                    });
                }

                if (DeliveryCycleType.WEEK_OPT_MANY.toValue() == buyCycleVO.getDeliveryCycle()) {
                    Map<String, List<LocalDate>> groupedByMonth = deliveryDateList.stream()
                            .collect(Collectors.groupingBy(
                                    date -> DateTimeFormatter.ofPattern("yyyy-MM").format(date)
                            ));
                    groupedByMonth.forEach((month, datesInMonth) -> {
                        if (datesInMonth.size() > buyCycleVO.getOptionalNum()) {
                            throw new SbcRuntimeException(OrderErrorCodeEnum.K050135);
                        }
                    });
                }
            }
        }
    }

    /**
     * 根据指定的首期日期生成送达计划
     * @param deliveryDate
     * @param cycleNum
     * @param buyCycleVO
     */
    public List<CycleDeliveryPlanDTO> createPlans(LocalDate deliveryDate, Integer cycleNum, BuyCycleVO buyCycleVO, List<LocalDate> deliveryDateList){
        List<CycleDeliveryPlanDTO> plans = new ArrayList<>();
        // 处理用户自选
        if (DeliveryCycleType.WEEK_OPT_MANY.toValue() == buyCycleVO.getDeliveryCycle()
                || DeliveryCycleType.MONTH_OPT_MANY.toValue() == buyCycleVO.getDeliveryCycle()) {
            if (CollectionUtils.isEmpty(deliveryDateList)) {
                return plans;
            }
            // 对deliveryDateList进行升序排序
            for (int i = 0; i < deliveryDateList.size(); i++) {
                CycleDeliveryPlanDTO dto = new CycleDeliveryPlanDTO();
                dto.setDeliveryDate(deliveryDateList.get(i));
                dto.setCycleDeliveryState(CycleDeliveryState.NOT_SHIP);
                dto.setDeliveryNum(i+1);
                plans.add(dto);
            }
            plans.sort(Comparator.comparing(CycleDeliveryPlanDTO::getDeliveryDate));
            return plans;
        }
        CycleDeliveryPlanDTO firstDTO = new CycleDeliveryPlanDTO();
        firstDTO.setDeliveryDate(deliveryDate);
        firstDTO.setCycleDeliveryState(CycleDeliveryState.NOT_SHIP);
        firstDTO.setDeliveryNum(Constants.ONE);
        plans.add(firstDTO);
        for (int i = 2; i <= cycleNum; i++) {
            LocalDate date = plans.get(i - 2).getDeliveryDate();
            LocalDate nextDeliveryDate = buyCycleDeliveryPlanService.getNextDeliveryDate(i, date.atStartOfDay(), buyCycleVO);
            CycleDeliveryPlanDTO dto = new CycleDeliveryPlanDTO();
            dto.setDeliveryNum(i);
            dto.setDeliveryDate(nextDeliveryDate);
            dto.setCycleDeliveryState(CycleDeliveryState.NOT_SHIP);
            plans.add(dto);
        }
        return plans;
    }

    /**
     * 删除周期购信息
     * @param terminalToken
     */
    public void removeSnapshot(String terminalToken){
        redisUtil.delete(RedisKeyConstant.BUY_CYCLE_TRADE_INFO + terminalToken);
    }
}
