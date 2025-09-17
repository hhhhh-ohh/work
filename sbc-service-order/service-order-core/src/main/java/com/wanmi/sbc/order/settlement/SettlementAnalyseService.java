package com.wanmi.sbc.order.settlement;

import com.alibaba.fastjson2.JSON;
import com.wanmi.sbc.account.api.provider.finance.record.SettlementProvider;
import com.wanmi.sbc.account.api.provider.finance.record.SettlementQueryProvider;
import com.wanmi.sbc.account.api.request.finance.record.SettlementLastByStoreIdRequest;
import com.wanmi.sbc.account.api.response.finance.record.LakalaSettlementGetByIdResponse;
import com.wanmi.sbc.account.api.response.finance.record.SettlementLastResponse;
import com.wanmi.sbc.account.api.response.finance.record.SettlementResponse;
import com.wanmi.sbc.account.bean.enums.*;
import com.wanmi.sbc.account.bean.vo.LakalaSettlementVO;
import com.wanmi.sbc.account.bean.vo.SettlementVO;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.OrderType;
import com.wanmi.sbc.common.enums.StoreType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.DateUtil;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.util.UUIDUtil;
import com.wanmi.sbc.customer.api.provider.ledgeraccount.LedgerAccountQueryProvider;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.ledgeraccount.QueryByBusinessIdsRequest;
import com.wanmi.sbc.customer.api.request.store.ListNoDeleteStoreByIdsRequest;
import com.wanmi.sbc.customer.api.request.store.ListStoreRequest;
import com.wanmi.sbc.customer.api.request.store.StoreByIdRequest;
import com.wanmi.sbc.customer.api.response.store.StoreByIdResponse;
import com.wanmi.sbc.customer.bean.enums.LedgerAccountState;
import com.wanmi.sbc.customer.bean.enums.LedgerState;
import com.wanmi.sbc.customer.bean.vo.LedgerAccountVO;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.elastic.api.provider.settlement.EsSettlementProvider;
import com.wanmi.sbc.empower.api.provider.pay.Lakala.LakalaShareProfitProvider;
import com.wanmi.sbc.goods.api.provider.cate.GoodsCateQueryProvider;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.request.cate.GoodsCateByIdRequest;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoViewByIdRequest;
import com.wanmi.sbc.goods.api.response.info.GoodsInfoViewByIdResponse;
import com.wanmi.sbc.goods.bean.enums.DistributionGoodsAudit;
import com.wanmi.sbc.marketing.bean.enums.BookingType;
import com.wanmi.sbc.marketing.bean.enums.GiftCardType;
import com.wanmi.sbc.marketing.bean.vo.TradeCouponVO;
import com.wanmi.sbc.order.api.request.settlement.SettlementAnalyseRequest;
import com.wanmi.sbc.order.api.response.settlement.SettlementBatchAddResponse;
import com.wanmi.sbc.order.bean.enums.FlowState;
import com.wanmi.sbc.order.bean.enums.PayState;
import com.wanmi.sbc.order.bean.enums.ReturnFlowState;
import com.wanmi.sbc.order.bean.vo.LklOrderTradeInfoVO;
import com.wanmi.sbc.order.message.StoreMessageBizService;
import com.wanmi.sbc.order.returnorder.model.entity.ReturnItem;
import com.wanmi.sbc.order.returnorder.model.root.ReturnOrder;
import com.wanmi.sbc.order.returnorder.model.value.ReturnPrice;
import com.wanmi.sbc.order.returnorder.service.ReturnOrderService;
import com.wanmi.sbc.order.settlement.model.root.LakalaSettlementDetail;
import com.wanmi.sbc.order.settlement.model.root.SettlementDetail;
import com.wanmi.sbc.order.settlement.model.value.LakalaSettleGood;
import com.wanmi.sbc.order.settlement.model.value.LakalaSettleTrade;
import com.wanmi.sbc.order.settlement.model.value.SettleGood;
import com.wanmi.sbc.order.settlement.model.value.SettleTrade;
import com.wanmi.sbc.order.settlement.service.SettlementDetailService;
import com.wanmi.sbc.order.trade.model.entity.TradeItem;
import com.wanmi.sbc.order.trade.model.root.CommunityTradeCommission;
import com.wanmi.sbc.order.trade.model.root.ProviderTrade;
import com.wanmi.sbc.order.trade.model.root.Trade;
import com.wanmi.sbc.order.trade.model.root.TradeDistributeItem;
import com.wanmi.sbc.order.trade.service.ProviderTradeService;
import com.wanmi.sbc.order.trade.service.TradeService;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/** Created by hht on 2017/12/7. */
@Service
@Slf4j
public class SettlementAnalyseService {

    private static Logger logger = LoggerFactory.getLogger(SettlementAnalyseService.class);

    /** 后台步长 */
    private static final int STEP = 1000;

    @Autowired private SettlementDetailService settlementDetailService;

    @Autowired private SettlementProvider settlementProvider;

    @Autowired private SettlementQueryProvider settlementQueryProvider;

    @Autowired private StoreQueryProvider storeQueryProvider;

    @Autowired private TradeService tradeService;

    @Autowired private ReturnOrderService returnOrderService;

    @Autowired private ProviderTradeService providerTradeService;

    @Autowired private GoodsInfoQueryProvider goodsInfoQueryProvider;

    @Autowired private GoodsCateQueryProvider goodsCateQueryProvider;

    @Autowired private LakalaShareProfitProvider lakalaShareProfitProvider;

    @Autowired private LedgerAccountQueryProvider ledgerAccountQueryProvider;

    @Autowired private EsSettlementProvider esSettlementProvider;

    @Autowired
    StoreMessageBizService storeMessageBizService;

    @Value("${lakala.openApi.feeRate300}")
    private String feeRate300;

    @Value("${lakala.openApi.feeRate301}")
    private String feeRate301;

    @Value("${lakala.openApi.max300}")
    private String max300;

    @Value("${lakala.openApi.feeRate302}")
    private String feeRate302;

    @Value("${lakala.openApi.feeRate303}")
    private String feeRate303;

    @Value("${lakala.openApi.feeRate314}")
    private String feeRate314;

    @Value("${lakala.openApi.feeRate315}")
    private String feeRate315;

    @Value("${lakala.openApi.feeRate322}")
    private String feeRate322;

    @Value("${lakala.openApi.max322}")
    private String max322;

    @Value("${lakala.openApi.B2B}")
    private String B2B;

    @Value("${lakala.openApi.feeRate323}")
    private String feeRate323;

    @Value("${lakala.openApi.feeRate324}")
    private String feeRate324;

    @Value("${lakala.openApi.feeRate325}")
    private String feeRate325;

    @Value("${lakala.openApi.feeRate326}")
    private String feeRate326;

    @Data
    private static class SettlementHelper {
        private BigDecimal salePrice = BigDecimal.ZERO;
        private BigDecimal returnPrice = BigDecimal.ZERO;
        private long saleNum = 0L;
        private long returnNum = 0L;
        private BigDecimal platformPrice = BigDecimal.ZERO;
        private BigDecimal deliveryPrice = BigDecimal.ZERO;
        private BigDecimal commonCouponPrice = BigDecimal.ZERO;
        // 积分抵扣总额
        private BigDecimal pointPrice = BigDecimal.ZERO;
        // 积分数量
        private Long points = 0L;
        // 分销佣金总额
        private BigDecimal commissionPrice = BigDecimal.ZERO;
        // 商品实付总额
        private BigDecimal splitPayPrice = BigDecimal.ZERO;
        // 店铺应收总额
        private BigDecimal storePrice = BigDecimal.ZERO;
        // 供货总额
        private BigDecimal providerPrice = BigDecimal.ZERO;
        // 供货运费总额
        private BigDecimal totalThirdPlatFormFreight = BigDecimal.ZERO;
        // 礼品卡-现金卡抵扣总金额
        private BigDecimal giftCardPrice = BigDecimal.ZERO;
        // 礼品卡-提货卡
        private BigDecimal pickupGiftCardPrice = BigDecimal.ZERO;
        // 社区团购佣金
        private BigDecimal communityCommissionPrice = BigDecimal.ZERO;
    }

    @Data
    private static class LakalaSettlementHelper {
        private boolean flag = false;
        private BigDecimal salePrice = BigDecimal.ZERO;
        private BigDecimal platformPrice = BigDecimal.ZERO;
        private BigDecimal deliveryPrice = BigDecimal.ZERO;
        private BigDecimal commonCouponPrice = BigDecimal.ZERO;
        // 积分抵扣总额
        private BigDecimal pointPrice = BigDecimal.ZERO;
        // 积分数量
        private Long points = 0L;
        // 分销佣金总额
        private BigDecimal commissionPrice = BigDecimal.ZERO;
        // 商品实付总额
        private BigDecimal splitPayPrice = BigDecimal.ZERO;
        // 店铺应收总额
        private BigDecimal storePrice = BigDecimal.ZERO;
        // 店铺运费
        private BigDecimal storeFreight = BigDecimal.ZERO;
        // 供货实际总额 = 供货总额-退单总额
        private BigDecimal providerPrice = BigDecimal.ZERO;
        // 供货运费总额
        private BigDecimal providerFreight = BigDecimal.ZERO;
        // 礼品卡抵扣总金额
        private BigDecimal giftCardPrice = BigDecimal.ZERO;
    }

    public SettlementResponse analyseSettlement(Date targetDate, SettlementAnalyseRequest request) {
//        SettlementResponse settlementResponse = new SettlementResponse();
//        String param = request.getParam();
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(targetDate);
//        calendar.set(Calendar.HOUR_OF_DAY, 0);
//        calendar.set(Calendar.MINUTE, 0);
//        calendar.set(Calendar.SECOND, 0);
//        calendar.set(Calendar.MILLISECOND, 0);
//        if (StringUtils.isNotEmpty(param)) {
//            calendar.add(Calendar.DATE, 1);
//            logger.info("包含今日订单的结算");
//        }
//
//        // 获取当天的日期，几号
//        int targetDay = calendar.get(Calendar.DAY_OF_MONTH);
//        List<StoreVO> storeList =
//                storeQueryProvider
//                        .listForSettle(
//                                ListStoreForSettleRequest.builder()
//                                        .targetDay(targetDay)
//                                        .storeType(request.getStoreType())
//                                        .build())
//                        .getContext()
//                        .getStoreVOList();
//        log.info("商家列表>>>{}", JSON.toJSONString(storeList));
//        if (CollectionUtils.isEmpty(storeList)) {
//            return settlementResponse;
//        }
//
//        List<String> companyInfoIds =
//                storeList.stream()
//                        .map(StoreVO::getCompanyInfoId)
//                        .map(String::valueOf)
//                        .collect(Collectors.toList());
//        Map<String, LedgerAccountVO> businessIdToLedgerAccountVOMap =
//                ledgerAccountQueryProvider
//                        .findByBusinessIds(
//                                QueryByBusinessIdsRequest.builder()
//                                        .businessIds(companyInfoIds)
//                                        .build())
//                        .getContext()
//                        .getBusinessIdToLedgerAccountVOMap();
//        List<SettlementAddResponse> responses = new ArrayList<>();
//        for (StoreVO store : storeList) {
//            this.analyseSettlementForStore(
//                    businessIdToLedgerAccountVOMap,
//                    store,
//                    request.getStoreType(),
//                    calendar,
//                    responses);
//        }
//
//        settlementResponse.setSettlementAddResponses(responses);
//        return settlementResponse;
        return null;
    }

    public List<SettlementBatchAddResponse> analyseSettlementForStore(
            LedgerAccountVO ledgerAccountVO,
            StoreVO store,
            StoreType storeType,
            String endTime
            ) {
        List<String> lakalaIds = new ArrayList<>();
        Date endDate = null;
        try {
            endDate = DateUtils.parseDate(endTime, DateUtil.FMT_TIME_1);
        } catch (ParseException e) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
        }
        List<SettlementBatchAddResponse> responses = new ArrayList<>();
        try {
            // 根据结束时间获取账期的开始时间
            Date startDate = this.getStartDate(store);
            if (startDate != null) {
                if (storeType.equals(StoreType.SUPPLIER)) {
                    analyseSettlementByStore(
                                    store,
                                    startDate,
                                    endDate,
                                    StoreType.SUPPLIER,
                                    lakalaIds,
                                    ledgerAccountVO)
                            .forEach(
                                    val -> {
                                        responses.add(val);
                                    });
                } else if (storeType.equals(StoreType.PROVIDER)) {
                    SettlementBatchAddResponse response =
                            analyseSettlementByProvider(
                                    store, startDate, endDate, StoreType.PROVIDER);
                    if (Objects.nonNull(response)) {
                        response.setStoreName(store.getStoreName());
                    }
                    responses.add(response);

                } else if (storeType.equals(StoreType.O2O)) {
                    analyseSettlementByStore(
                                    store,
                                    startDate,
                                    endDate,
                                    StoreType.O2O,
                                    lakalaIds,
                                    ledgerAccountVO)
                            .forEach(
                                    val -> {
                                        responses.add(val);
                                    });
                }
//                List<String> settlementIds =
//                        lakalaSettlementAddResponses.stream().map(LakalaSettlementAddResponse::getSettleId).map(String::valueOf
//                ).collect(Collectors.toList());
//                if (CollectionUtils.isNotEmpty(settlementIds)){
//                    EsSettlementInitRequest esSettlementInitRequest = new EsSettlementInitRequest();
//                    esSettlementInitRequest.setIdList(settlementIds);
//                    esSettlementProvider.initLakalaSettlement(esSettlementInitRequest);
//
//                    // ============= 处理消息发送：平台、商家、供应商结算单生成提醒 START =============
//                    //平台
//                    storeMessageBizService.handleForLakalaBossSettlementProduce(
//                            BossMessageNode.LAKALA_SETTLEMENT_SETTLE.getCode(), lakalaSettlementAddResponses);
//                    //商家、供应商
//                    storeMessageBizService.handleForLakalaSupplierSettlementProduce(lakalaSettlementAddResponses);
//                    // ============= 处理消息发送：平台、商家、供应商结算单生成提醒 END =============
//                }
            }
        } catch (Exception e) {
            logger.error("结算数据异常", e);
        }
//        if (CollectionUtils.isNotEmpty(lakalaIds)){
//            // 拉卡拉自动分账
//            lakalaShareProfitProvider.seporcancel(
//                    SettlementRequest.builder().type(NumberUtils.INTEGER_ONE).uuids(lakalaIds).build());
//        }
        return responses;
    }

    /**
     * 供应商解析入口
     *
     * @param store
     * @param startTime
     * @param endTime
     */
//    @GlobalTransactional
//    @Transactional
    public SettlementBatchAddResponse analyseSettlementByProvider(
            StoreVO store, Date startTime, Date endTime, StoreType storeType) {
        log.info(
                "开始时间>>>{}，结束时间>>>{}",
                formatDate(startTime),
                formatDate(getSettleEndDateForView(endTime)));

        // 生成结算单uuid，用作插入结算单明细
        String uuid = UUIDUtil.getUUID();

        int pageNum = 0;
        // 解析订单信息
        SettlementHelper settlementHelper = new SettlementHelper();
        while (true) {
            if (!analyseForProviderTrade(
                    pageNum, store, startTime, endTime, uuid, settlementHelper, storeType)) {
                break;
            }
            pageNum++;
        }

        // 插入settlement结算表
        SettlementVO settlement = new SettlementVO();
        settlement.setSettleUuid(uuid);
        settlement.setCreateTime(LocalDateTime.now());
        settlement.setStartTime(formatDate(startTime));
        settlement.setEndTime(formatDate(getSettleEndDateForView(endTime)));
        settlement.setStoreId(store.getStoreId());
        settlement.setStoreType(store.getStoreType());
        settlement.setSalePrice(settlementHelper.getSalePrice());
        settlement.setSaleNum(settlementHelper.getSaleNum());
        settlement.setReturnPrice(settlementHelper.getReturnPrice());
        settlement.setReturnNum(settlementHelper.getReturnNum());
        settlement.setPlatformPrice(settlementHelper.getPlatformPrice());
        settlement.setDeliveryPrice(settlementHelper.getDeliveryPrice());
        settlement.setSplitPayPrice(settlementHelper.getSplitPayPrice());
        settlement.setCommonCouponPrice(settlementHelper.getCommonCouponPrice());
        settlement.setCommissionPrice(settlementHelper.getCommissionPrice());
        settlement.setPointPrice(settlementHelper.getPointPrice());
        settlement.setSettleStatus(SettleStatus.NOT_SETTLED);
        settlement.setStorePrice(settlementHelper.getStorePrice());
        settlement.setProviderPrice(settlementHelper.getProviderPrice());
        settlement.setThirdPlatFormFreight(settlementHelper.getTotalThirdPlatFormFreight());
        SettlementBatchAddResponse response = new SettlementBatchAddResponse();
        response.setSettlementVO(settlement);
        response.setSettlementType(SettlementBatchAddResponse.SettlementType.NORMAL);
        response.setStoreName(store.getStoreName());
        return response;
    }

    /**
     * 处理订单信息，插入结算明细表
     *
     * @param pageNum 步长
     * @param startTime
     * @param endTime
     * @param uuid 结算uuid
     * @return
     */
    private boolean analyseForProviderTrade(
            int pageNum,
            StoreVO store,
            Date startTime,
            Date endTime,
            String uuid,
            SettlementHelper settlement,
            StoreType storeType) {
        PageRequest pageRequest = PageRequest.of(pageNum, STEP);
        Long storeId = store.getStoreId();
        store.setStoreType(StoreType.PROVIDER);
        // 查询订单
        List<ProviderTrade> providerTradeList =
                providerTradeService.findTradeListForSettlement(
                        storeId, startTime, endTime, pageRequest);
        log.info("需结算的订单集合>>>{}", JSON.toJSONString(providerTradeList));
        if (CollectionUtils.isEmpty(providerTradeList)) {
            return false;
        }
        // 1.从结算单中获取金额信息
        // 店铺应收总额
        BigDecimal totalStorePrice = settlement.getStorePrice();
        // 供货总额
        BigDecimal totalProviderPrice = settlement.getProviderPrice();
        // 供货运费总额
        BigDecimal totalThirdPlatFormFreightPrice = settlement.getTotalThirdPlatFormFreight();

        // 2.处理每笔订单，生成结算单明细；并将相应金额累加至结算单
        List<SettlementDetail> settlementDetailList = new ArrayList<>(2);
        for (ProviderTrade trade : providerTradeList) {

            ProviderTrade tradeTmp = providerTradeService.providerDetail(trade.getId());

            // 过滤订单营销信息
            // trimTradeMarketing(trade);

            // 2.1.查询订单关联的退单，并按sku分组退货信息
            List<ReturnOrder> returnOrders = returnOrderService.findByPtid(tradeTmp.getId());

            returnOrders =
                    returnOrders.stream()
                            .filter(item -> ReturnFlowState.COMPLETED == item.getReturnFlowState())
                            .collect(Collectors.toList());

            //普通商品
            Map<String, List<ReturnItem>> returnItemsMap =
                    returnOrders.stream()
                            .flatMap(item -> item.getReturnItems().stream())
                            .collect(Collectors.groupingBy(ReturnItem::getSkuId));

            // 赠品
            List<String> returnGiftSkuIds = returnOrders.stream()
                    .flatMap(item -> item.getReturnGifts().stream())
                    .map(ReturnItem::getSkuId)
                    .collect(Collectors.toList());

            //换购商品
            Map<Long, Map<String,List<ReturnItem>>> returnPreferItemsMap =
                     returnOrders.stream()
                            .map(ReturnOrder::getReturnPreferential)
                            .flatMap(Collection::stream)
                            .collect(Collectors.groupingBy(ReturnItem::getMarketingId, Collectors.groupingBy(ReturnItem::getSkuId)));


            // 2.2.构建结算单明细中的商品列表，并将相应金额累加至结算单
            List<SettleGood> settleGoods =
                    transTradeItems(
                            KsBeanUtil.convert(trade, Trade.class),
                            returnItemsMap,
                            returnGiftSkuIds,
                            returnPreferItemsMap,
                            trade.getOrderType(),
                            storeType);
            // 2.3.计算退单改价差额，退单改价差额=退运费金额
            BigDecimal returnSpecialPrice = BigDecimal.ZERO;
            for (ReturnOrder returnOrder : returnOrders) {
                ReturnPrice returnPrice = returnOrder.getReturnPrice();
                returnSpecialPrice =
                        returnSpecialPrice.add(
                                Objects.nonNull(returnPrice.getFee())
                                        ? returnPrice.getFee()
                                        : BigDecimal.ZERO);
            }

            // 供货价
            BigDecimal providerPrice = BigDecimal.ZERO;
            for (SettleGood goodsInfo : settleGoods) {
                // 供货金额
                providerPrice =
                        providerPrice.add(
                                goodsInfo.getProviderPrice() != null
                                        ? goodsInfo.getProviderPrice()
                                        : BigDecimal.ZERO);
            }

            if (CollectionUtils.isEmpty(settleGoods)) {
                continue;
            }

            BigDecimal thirdPlatFormFreight =
                    trade.getThirdPlatFormFreight() != null
                            ? trade.getThirdPlatFormFreight()
                            : BigDecimal.ZERO;
            // 供应商应收金额=供货单价*数量（上面计算出来的供货总额）+供货运费
            providerPrice = providerPrice.add(thirdPlatFormFreight);

            // 店铺营收金额=每个商品供货价×数量之和+供货运费-退单改价差额；
            BigDecimal storePrice = providerPrice.subtract(returnSpecialPrice);

            // 入账时间：finalTime不为空直接取finalTime；
            // 为了入账时间不为空，默认取个当前时间
            LocalDateTime finalTime = trade.getTradeState().getFinalTime();
            if (Objects.isNull(finalTime)) {
                finalTime = LocalDateTime.now();
            }
            // 2.6.新增结算明细
            settlementDetailList.add(
                    // 结算明细
                    new SettlementDetail(
                            null,
                            uuid,
                            formatDate(startTime),
                            formatDate(getSettleEndDateForView(endTime)),
                            storeId,
                            trade.getTradePrice().isSpecial(),
                            // 组装结算明细 - 订单明细
                            new SettleTrade(
                                    trade.getTradeState().getPayTime(),
                                    trade.getTradeState().getCreateTime(),
                                    trade.getTradeState().getEndTime(),
                                    finalTime,
                                    trade.getId(),
                                    TradeType.NORMAL_TRADE,
                                    trade.getOrderType(),
                                    GatherType.PLATFORM,
                                    trade.getTradePrice().getDeliveryPrice(),
                                    BigDecimal.ZERO,
                                    BigDecimal.ZERO,
                                    returnSpecialPrice,
                                    storePrice,
                                    providerPrice,
                                    thirdPlatFormFreight,
                                    null,
                                    null,
                                    null,
                                    BigDecimal.ZERO,
                                    BigDecimal.ZERO,
                                    null
                            ),
                            // 组装结算明细 - 商品明细
                            settleGoods,
                            true));
            // 店铺应收总额
            totalStorePrice = totalStorePrice.add(storePrice);
            // 商品供货总额
            totalProviderPrice = totalProviderPrice.add(providerPrice);

            // 商品供货运费总额
            totalThirdPlatFormFreightPrice =
                    totalThirdPlatFormFreightPrice.add(thirdPlatFormFreight);
        }

//        List<SettlementDetail> settlementDetailDTOList =
//                KsBeanUtil.convert(settlementDetailList, SettlementDetail.class);

        settlementDetailService.save(settlementDetailList);

        settlement.setStorePrice(totalStorePrice);
        settlement.setProviderPrice(totalStorePrice);
        settlement.setTotalThirdPlatFormFreight(totalThirdPlatFormFreightPrice);
        return true;
    }

    private SettlementBatchAddResponse analyseLakalaSettlementByProvider(
            StoreVO store, Date startTime, Date endTime, Long storeId) {
        log.info(
                "开始时间>>>{}，结束时间>>>{}",
                formatDate(startTime),
                formatDate(getSettleEndDateForView(endTime)));
        // 生成之前先删除，防止重复
//        LakalaSettlementGetByIdResponse lakalaSettlementGetByIdResponse = settlementProvider.lakalaDelete(
//                SettlementDeleteRequest.builder()
//                        .storeId(store.getStoreId())
//                        .startTime(formatDate(startTime))
//                        .endTime(formatDate(getSettleEndDateForView(endTime)))
//                        .build()).getContext();
        Map<String, String> tidToSepTranSidMap = settlementDetailService.getTidToSepTranSidMap(store.getStoreId(),
                formatDate(startTime), formatDate(getSettleEndDateForView(endTime)));

        settlementDetailService.deleteProviderLakalaSettlement(
                store.getStoreId(), formatDate(startTime), formatDate(getSettleEndDateForView(endTime)),storeId);

//        if (Objects.nonNull(lakalaSettlementGetByIdResponse)){
//            esSettlementProvider.delLakalaSettlement(
//                    EsLakalaSettlementDelRequest.builder()
//                            .settleId(lakalaSettlementGetByIdResponse.getSettleId())
//                            .build());
//        }

        // 生成结算单uuid，用作插入结算单明细
        String uuid = UUIDUtil.getUUID();

        int pageNum = 0;
        // 解析订单信息
        LakalaSettlementHelper lakalaSettlementHelper = new LakalaSettlementHelper();
        while (analyseForProviderLakalaTrade(
                pageNum, store, storeId, startTime, endTime, uuid, lakalaSettlementHelper, tidToSepTranSidMap)) {
            pageNum++;
        }

        // 插入settlement结算表
        LakalaSettlementVO settlementVO = new LakalaSettlementVO();
        settlementVO.setSettleUuid(uuid);
        settlementVO.setCreateTime(LocalDateTime.now());
        settlementVO.setStartTime(formatDate(startTime));
        settlementVO.setEndTime(formatDate(getSettleEndDateForView(endTime)));
        settlementVO.setStoreId(store.getStoreId());
        settlementVO.setStoreType(StoreType.PROVIDER);
        settlementVO.setTotalProviderPrice(
                lakalaSettlementHelper
                        .getProviderPrice()
                        .add(lakalaSettlementHelper.getProviderFreight()));
        settlementVO.setTotalCommissionPrice(
                lakalaSettlementHelper.getCommissionPrice());
        settlementVO.setTotalPlatformPrice(lakalaSettlementHelper.getPlatformPrice());
        settlementVO.setTotalStorePrice(lakalaSettlementHelper.getStorePrice());
        settlementVO.setTotalAmount(
                settlementVO
                        .getTotalProviderPrice()
                        .add(
                                settlementVO
                                        .getTotalStorePrice()
                                        .add(
                                                settlementVO
                                                        .getTotalCommissionPrice()
                                                        .add(
                                                                settlementVO
                                                                        .getTotalPlatformPrice()))));
        settlementVO.setTotalSplitPayPrice(lakalaSettlementHelper.getSplitPayPrice());
        settlementVO.setTotalCommonCouponPrice(
                lakalaSettlementHelper.getCommonCouponPrice());
        settlementVO.setPointPrice(lakalaSettlementHelper.getPointPrice());
        settlementVO.setTotalPoints(lakalaSettlementHelper.getPoints());
        settlementVO.setProviderGoodsTotalPrice(
                lakalaSettlementHelper.getProviderPrice());
        settlementVO.setProviderDeliveryTotalPrice(
                lakalaSettlementHelper.getProviderFreight());
        settlementVO.setDeliveryPrice(lakalaSettlementHelper.getDeliveryPrice());
        settlementVO.setSupplierStoreId(storeId);
        if (settlementVO.getTotalProviderPrice() == null
                || settlementVO.getTotalProviderPrice().compareTo(BigDecimal.ZERO)
                        == 0) {
            settlementVO.setLakalaLedgerStatus(LakalaLedgerStatus.SUCCESS);
        } else {
            settlementVO.setLakalaLedgerStatus(LakalaLedgerStatus.FAIL);
        }
        //        LakalaSettlementAddResponse lakalaSettlementAddResponse =
//                        settlementProvider.lakalaAdd(lakalaSettlementAddRequest).getContext();
        SettlementBatchAddResponse response =
                new SettlementBatchAddResponse();
        response.setSettlementType(SettlementBatchAddResponse.SettlementType.LAKALA);
        response.setStoreName(store.getStoreName());
        return response;
    }

    /**
     * 处理订单信息，插入结算明细表
     *
     * @param pageNum 步长
     * @param endTime
     * @param uuid 结算uuid
     * @return
     */
    private boolean analyseForProviderLakalaTrade(
            int pageNum,
            StoreVO providerStore,
            Long storeId,
            Date startTime,
            Date endTime,
            String uuid,
            LakalaSettlementHelper settlement,
            Map<String, String> tidToSepTranSidMap) {
        PageRequest pageRequest = PageRequest.of(pageNum, STEP);
        Long providerStoreId = providerStore.getStoreId();
        providerStore.setStoreType(StoreType.PROVIDER);
        // 查询订单
        List<ProviderTrade> providerTradeList =
                providerTradeService.findLakalaTradeListForSettlement(
                        providerStoreId, startTime, endTime, pageRequest, storeId);
        log.info("需结算的订单集合>>>{}", JSON.toJSONString(providerTradeList));
        if (CollectionUtils.isEmpty(providerTradeList)) {
            return false;
        }
        List<Trade> trades = KsBeanUtil.convert(providerTradeList, Trade.class);
        analyseForLakalaTrade(
                trades, providerStore, startTime, endTime, uuid, settlement, StoreType.PROVIDER, tidToSepTranSidMap);
        return true;
    }

    /**
     * 解析入口
     *
     * @param store
     * @param startTime
     * @param endTime
     */
//    @GlobalTransactional
//    @Transactional
    public List<SettlementBatchAddResponse> analyseSettlementByStore(
            StoreVO store,
            Date startTime,
            Date endTime,
            StoreType storeType,
            List<String> uuids,
            LedgerAccountVO storeledgerAccount) {
        List<SettlementBatchAddResponse> resultList = new ArrayList<>();
        settlementDetailService.deleteSettlement(
                store.getStoreId(), formatDate(startTime), formatDate(endTime));

        // 判断商家是否进件
        boolean ledgerAccountStateFlag =
                Objects.nonNull(storeledgerAccount)
                        && LedgerAccountState.PASS.toValue() == storeledgerAccount.getAccountState()
                        && LedgerState.PASS.toValue() == storeledgerAccount.getLedgerState();

        Map<String, String> tidToSepTranSidMap = new HashMap<>();
        if (ledgerAccountStateFlag){
            tidToSepTranSidMap = settlementDetailService.getTidToSepTranSidMap(store.getStoreId(),
                    formatDate(startTime), formatDate(getSettleEndDateForView(endTime)));

            settlementDetailService.deleteLakalaSettlement(
                    store.getStoreId(), formatDate(startTime), formatDate(getSettleEndDateForView(endTime)));
        }

        // 生成结算单uuid，用作插入结算单明细
        String uuid = UUIDUtil.getUUID();
        String lakalaUuid = UUIDUtil.getUUID();



        int pageNum = 0;
        // 解析订单信息
        SettlementHelper settlementHelper = new SettlementHelper();
        LakalaSettlementHelper lakalaSettlementHelper = new LakalaSettlementHelper();
        Set<Long> privoderIds = new HashSet<>();
        while (true) {
            PageRequest pageRequest = PageRequest.of(pageNum, STEP);
            Long storeId = store.getStoreId();
            // 查询订单
            List<Trade> tradeList =
                    tradeService.findTradeListForSettlement(
                            storeId, startTime, endTime, pageRequest);
            if (CollectionUtils.isEmpty(tradeList)) {
                break;
            }
            // 商品
            privoderIds.addAll(
                    tradeList.stream()
                            .filter(trade -> PayWay.LAKALA.equals(trade.getPayWay()) || PayWay.LAKALACASHIER.equals(trade.getPayWay()))
                            .flatMap(trade -> trade.getTradeItems().stream())
                            .map(TradeItem::getProviderId)
                            .collect(Collectors.toSet()));
            // 赠品
            privoderIds.addAll(
                    tradeList.stream()
                            .filter(trade -> PayWay.LAKALA.equals(trade.getPayWay()) || PayWay.LAKALACASHIER.equals(trade.getPayWay()))
                            .flatMap(trade -> trade.getGifts().stream())
                            .map(TradeItem::getProviderId)
                            .collect(Collectors.toSet()));

            // 加价购品
            privoderIds.addAll(
                    tradeList.stream()
                            .filter(trade -> PayWay.LAKALA.equals(trade.getPayWay()))
                            .flatMap(trade -> trade.getPreferential().stream())
                            .map(TradeItem::getProviderId)
                            .collect(Collectors.toSet()));

            analyseForTrade(
                    tradeList, store, startTime, endTime, uuid, settlementHelper, storeType);

            if (ledgerAccountStateFlag) {
                analyseForLakalaTrade(
                        tradeList,
                        store,
                        startTime,
                        endTime,
                        lakalaUuid,
                        lakalaSettlementHelper,
                        storeType,
                        tidToSepTranSidMap);
            }

            pageNum++;
        }

        // 插入settlement结算表
        SettlementVO settlement = new SettlementVO();
        settlement.setSettleUuid(uuid);
        settlement.setCreateTime(LocalDateTime.now());
        settlement.setStartTime(formatDate(startTime));
        settlement.setEndTime(formatDate(getSettleEndDateForView(endTime)));
        settlement.setStoreId(store.getStoreId());
        settlement.setStoreType(storeType);
        settlement.setSalePrice(settlementHelper.getSalePrice());
        settlement.setSaleNum(settlementHelper.getSaleNum());
        settlement.setReturnPrice(settlementHelper.getReturnPrice());
        settlement.setReturnNum(settlementHelper.getReturnNum());
        settlement.setPlatformPrice(settlementHelper.getPlatformPrice());
        settlement.setStorePrice(settlementHelper.getStorePrice());
        settlement.setDeliveryPrice(settlementHelper.getDeliveryPrice());
        settlement.setSplitPayPrice(settlementHelper.getSplitPayPrice());
        settlement.setCommonCouponPrice(settlementHelper.getCommonCouponPrice());
        settlement.setCommissionPrice(settlementHelper.getCommissionPrice());
        settlement.setPointPrice(settlementHelper.getPointPrice());
        settlement.setPoints(settlementHelper.getPoints());
        settlement.setGiftCardPrice(settlementHelper.getGiftCardPrice());
        settlement.setPickupGiftCardPrice(settlementHelper.getPickupGiftCardPrice());
        settlement.setSettleStatus(SettleStatus.NOT_SETTLED);
        settlement.setProviderPrice(settlementHelper.getProviderPrice());
        settlement.setThirdPlatFormFreight(settlementHelper.totalThirdPlatFormFreight);
        settlement.setCommunityCommissionPrice(settlementHelper.getCommunityCommissionPrice());
//        SettlementAddResponse response = settlementProvider.add(settlement).getContext();
        SettlementBatchAddResponse response = new SettlementBatchAddResponse();
        response.setSettlementVO(settlement);
        response.setSettlementType(SettlementBatchAddResponse.SettlementType.NORMAL);
        response.setStoreName(store.getStoreName());
        resultList.add(response);

        if (ledgerAccountStateFlag) {
            // 店铺拉卡拉结算
            LakalaSettlementVO lakalaSettlementVO =
                    new LakalaSettlementVO();
            lakalaSettlementVO.setSettleUuid(lakalaUuid);
            lakalaSettlementVO.setCreateTime(LocalDateTime.now());
            lakalaSettlementVO.setStartTime(formatDate(startTime));
            lakalaSettlementVO.setEndTime(formatDate(getSettleEndDateForView(endTime)));
            lakalaSettlementVO.setStoreId(store.getStoreId());
            lakalaSettlementVO.setStoreType(storeType);
            lakalaSettlementVO.setTotalProviderPrice(
                    lakalaSettlementHelper
                            .getProviderPrice()
                            .add(lakalaSettlementHelper.getProviderFreight()));
            lakalaSettlementVO.setTotalCommissionPrice(
                    lakalaSettlementHelper.getCommissionPrice());
            lakalaSettlementVO.setTotalPlatformPrice(
                    lakalaSettlementHelper.getPlatformPrice());
            lakalaSettlementVO.setTotalStorePrice(lakalaSettlementHelper.getStorePrice());
            lakalaSettlementVO.setTotalAmount(
                    lakalaSettlementVO
                            .getTotalProviderPrice()
                            .add(
                                    lakalaSettlementVO
                                            .getTotalStorePrice()
                                            .add(
                                                    lakalaSettlementVO
                                                            .getTotalCommissionPrice()
                                                            .add(
                                                                    lakalaSettlementVO
                                                                            .getTotalPlatformPrice()))));
            lakalaSettlementVO.setTotalSplitPayPrice(
                    lakalaSettlementHelper.getSplitPayPrice());
            lakalaSettlementVO.setTotalCommonCouponPrice(
                    lakalaSettlementHelper.getCommonCouponPrice());
            lakalaSettlementVO.setPointPrice(lakalaSettlementHelper.getPointPrice());
            lakalaSettlementVO.setTotalPoints(lakalaSettlementHelper.getPoints());
            lakalaSettlementVO.setGiftCardPrice(lakalaSettlementHelper.getGiftCardPrice());
            lakalaSettlementVO.setProviderGoodsTotalPrice(
                    lakalaSettlementHelper.getProviderPrice());
            lakalaSettlementVO.setProviderDeliveryTotalPrice(
                    lakalaSettlementHelper.getProviderFreight());
            lakalaSettlementVO.setDeliveryPrice(lakalaSettlementHelper.getDeliveryPrice());
            // 分账金额为零默认分账成功。否则默认填充失败状态。分账后进行状态更新
            if (lakalaSettlementVO.getTotalAmount() == null
                    || lakalaSettlementVO.getTotalAmount().compareTo(BigDecimal.ZERO)
                            == 0) {
                lakalaSettlementVO.setLakalaLedgerStatus(LakalaLedgerStatus.SUCCESS);
            } else {
                uuids.add(lakalaUuid);
                lakalaSettlementVO.setLakalaLedgerStatus(LakalaLedgerStatus.FAIL);
            }
            SettlementBatchAddResponse lakalaResponse = new SettlementBatchAddResponse();
            lakalaResponse.setLakalaSettlementVO(lakalaSettlementVO);
            lakalaResponse.setSettlementType(SettlementBatchAddResponse.SettlementType.LAKALA);
//            LakalaSettlementAddResponse lakalaSettlementAddResponse =
//                    settlementProvider.lakalaAdd(lakalaSettlementVO).getContext();
            lakalaResponse.setStoreName(store.getStoreName());
            resultList.add(lakalaResponse);
        }

        // 供应商拉卡拉结算
        if (CollectionUtils.isNotEmpty(privoderIds)) {
            List<StoreVO> storeList =
                    storeQueryProvider
                            .listStore(
                                    ListStoreRequest.builder()
                                            .storeIds(new ArrayList<>(privoderIds))
                                            .build())
                            .getContext()
                            .getStoreVOList();
            log.info("供应商列表>>>{}", JSON.toJSONString(storeList));
            if (CollectionUtils.isNotEmpty(storeList)) {
                List<String> companyInfoIds =
                        storeList.stream()
                                .map(StoreVO::getCompanyInfoId)
                                .map(String::valueOf)
                                .collect(Collectors.toList());
                Map<String, LedgerAccountVO> businessIdToLedgerAccountVOMap =
                        ledgerAccountQueryProvider
                                .findByBusinessIds(
                                        QueryByBusinessIdsRequest.builder()
                                                .businessIds(companyInfoIds)
                                                .build())
                                .getContext()
                                .getBusinessIdToLedgerAccountVOMap();
                storeList.forEach(
                        storeVO -> {
                            LedgerAccountVO ledgerAccountVO =
                                    businessIdToLedgerAccountVOMap.get(
                                            storeVO.getCompanyInfoId().toString());
                            if (Objects.nonNull(ledgerAccountVO)
                                    && LedgerAccountState.PASS.toValue()
                                            == ledgerAccountVO.getAccountState()) {
                                SettlementBatchAddResponse lakalaSettlementAddResponse1 =
                                        analyseLakalaSettlementByProvider(
                                                storeVO, startTime, endTime, store.getStoreId());
                                resultList.add(lakalaSettlementAddResponse1);
                            }
                        });
            }
        }
        return resultList;
    }

    /**
     * 处理订单信息，插入结算明细表
     *
     * @param startTime
     * @param endTime
     * @param uuid 结算uuid
     * @return
     */
    private void analyseForTrade(
            List<Trade> tradeList,
            StoreVO store,
            Date startTime,
            Date endTime,
            String uuid,
            SettlementHelper settlement,
            StoreType _storeType) {

        List<Trade> directTradeList =
                tradeList.stream()
                        .filter(trade -> !PayWay.LAKALA.equals(trade.getPayWay()) && !PayWay.LAKALACASHIER.equals(trade.getPayWay()))
                        .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(directTradeList)) {
            return;
        }

        // 1.从结算单中获取金额信息
        // 运费总额
        BigDecimal totalDeliveryPrice = settlement.getDeliveryPrice();
        // 平台佣金总额
        BigDecimal totalPlatformPrice = settlement.getPlatformPrice();
        // 通用券优惠总额
        BigDecimal totalCommonCouponPrice = settlement.getCommonCouponPrice();
        // 分销佣金总额
        BigDecimal totalCommissionPrice = settlement.getCommissionPrice();
        // 积分抵扣总额
        BigDecimal totalPointPrice = settlement.getPointPrice();
        // 积分数量
        Long totalPoints = settlement.getPoints();
        // 商品实付总额
        BigDecimal totalSplitPayPrice = settlement.getSplitPayPrice();
        // 店铺应收总额
        BigDecimal totalStorePrice = settlement.getStorePrice();
        // 供货总额
        BigDecimal totalProviderPrice = settlement.getProviderPrice();

        // 礼品卡-现金卡
        BigDecimal totalGiftCardPrice = settlement.getGiftCardPrice();
        // 礼品卡-提货卡
        BigDecimal totalPickupGiftCardPrice = settlement.getPickupGiftCardPrice();

        BigDecimal totalThirdPlatFormFreightPrice = settlement.getTotalThirdPlatFormFreight();
        // 社区团购佣金
        BigDecimal totalCommunityCommissionPrice = settlement.getCommunityCommissionPrice();

        // 2.处理每笔订单，生成结算单明细；并将相应金额累加至结算单
        List<SettlementDetail> settlementDetailList = new ArrayList<>(2);
        for (Trade trade : directTradeList) {

            BigDecimal deliveryPrice;
            if (Objects.nonNull(trade.getBookingType())
                    && BookingType.EARNEST_MONEY == trade.getBookingType()
                    && trade.getTradeState().getPayState() == PayState.PAID_EARNEST) {
                deliveryPrice = BigDecimal.ZERO;
            } else {
                deliveryPrice =
                        trade.getTradePrice().getDeliveryPrice() == null
                                ? BigDecimal.ZERO
                                : trade.getTradePrice().getDeliveryPrice();
            }

            // 订单入账时间：普通订单则为已完成或已作废时间
            LocalDateTime finalTime = trade.getTradeState().getFinalTime();
            if (Boolean.TRUE.equals(trade.getIsBookingSaleGoods())
                    && BookingType.EARNEST_MONEY.equals(trade.getBookingType())
                    && Objects.nonNull(trade.getTradeState())
                    && (!PayState.PAID.equals(trade.getTradeState().getPayState()))) {
                // 尾款时间 < 今天 已作废的定金预售订单
                if (Objects.nonNull(trade.getTradeState().getTailEndTime())
                        && trade.getTradeState().getTailEndTime().isBefore(LocalDateTime.now())) {
                    // 预约预售仅支付了定金但已作废的订单则为尾款失效时间
                    finalTime = trade.getTradeState().getTailEndTime();
                }
            }

            // 过滤订单营销信息
            // trimTradeMarketing(trade);

            // 2.1.查询订单关联的退单，并按sku分组退货信息
            List<ReturnOrder> returnOrders = returnOrderService.findReturnByTid(trade.getId());
            returnOrders =
                    returnOrders.stream()
                            .filter(item -> ReturnFlowState.COMPLETED == item.getReturnFlowState())
                            .collect(Collectors.toList());
            Map<String, List<ReturnItem>> returnItemsMap =
                    returnOrders.stream()
                            .flatMap(item -> item.getReturnItems().stream())
                            .collect(Collectors.groupingBy(ReturnItem::getSkuId));

            List<String> returnGiftSkuIds =
                    returnOrders.stream()
                            .flatMap(item -> item.getReturnGifts().stream())
                            .map(ReturnItem::getSkuId)
                            .collect(Collectors.toList());

            //换购商品
            Map<Long, Map<String,List<ReturnItem>>> returnPreferItemsMap =
                    returnOrders.stream()
                            .map(ReturnOrder::getReturnPreferential)
                            .flatMap(Collection::stream)
                            .collect(Collectors.groupingBy(ReturnItem::getMarketingId, Collectors.groupingBy(ReturnItem::getSkuId)));

            // 2.2.构建结算单明细中的商品列表，并将相应金额累加至结算单
            List<SettleGood> settleGoods =
                    transTradeItems(trade, returnItemsMap,returnGiftSkuIds, returnPreferItemsMap, trade.getOrderType()
                            , _storeType);

            // 2.3.计算退单改价差额
            BigDecimal returnSpecialPrice = BigDecimal.ZERO;
            // 计算供应商退单改价
            BigDecimal providerReturnSpecialPrice = BigDecimal.ZERO;
            for (ReturnOrder returnOrder : returnOrders) {
                ReturnPrice returnPrice = returnOrder.getReturnPrice();
                returnSpecialPrice =
                        returnSpecialPrice.add(
                                returnPrice
                                        .getTotalPrice()
                                        .subtract(returnPrice.getActualReturnPrice()));

                if (StringUtils.isNotBlank(returnOrder.getProviderId())) {
                    providerReturnSpecialPrice =
                            providerReturnSpecialPrice.add(
                                    Objects.nonNull(returnPrice.getFee())
                                            ? returnPrice.getFee()
                                            : BigDecimal.ZERO);
                }
            }

            // 2.4.计算订单的通用券优惠金额、积分抵现金额、平台佣金、分销佣金、供货价
            BigDecimal commonCouponPrice = BigDecimal.ZERO;
            BigDecimal pointPrice = BigDecimal.ZERO;
            Long points = 0L;
            BigDecimal platformPrice = BigDecimal.ZERO;
            BigDecimal commissionPrice = BigDecimal.ZERO;
            BigDecimal splitPayPrice = BigDecimal.ZERO;
            // 供货总金额
            BigDecimal providerPrice = BigDecimal.ZERO;
            BigDecimal giftCardPrice = BigDecimal.ZERO;

            for (SettleGood goodsInfo : settleGoods) {
                // 通用券累加
                if (CollectionUtils.isNotEmpty(goodsInfo.getCouponSettlements())) {
                    Optional<SettleGood.CouponSettlement> couponSettle =
                            goodsInfo.getCouponSettlements().stream()
                                    .filter(
                                            item ->
                                                    item.getCouponType()
                                                            == SettleCouponType.GENERAL_VOUCHERS)
                                    .findFirst();
                    if (couponSettle.isPresent()) {
                        commonCouponPrice =
                                commonCouponPrice.add(couponSettle.get().getReducePrice());
                    }
                }

                // 积分抵扣累加
                if (Objects.nonNull(goodsInfo.getPointPrice())) {
                    pointPrice = pointPrice.add(goodsInfo.getPointPrice());
                }

                // 积分数量累加
                if (Objects.nonNull(goodsInfo.getPoints())) {
                    points = Long.sum(points, goodsInfo.getPoints());
                }

                // 平台佣金累加
                platformPrice =
                        goodsInfo.getSplitPayPrice() != null
                                        && Objects.nonNull(goodsInfo.getCateRate())
                                ? platformPrice.add(
                                        goodsInfo
                                                .getSplitPayPrice()
                                                .multiply(goodsInfo.getCateRate())
                                                .divide(new BigDecimal(100))
                                                .setScale(2, RoundingMode.DOWN))
                                : BigDecimal.ZERO;

                // 分销佣金累加
                if (Objects.nonNull(goodsInfo.getCommission())) {
                    commissionPrice = commissionPrice.add(goodsInfo.getCommission());
                }

                // 实付金额累加
                splitPayPrice =
                        splitPayPrice.add(
                                goodsInfo.getSplitPayPrice() != null
                                        ? goodsInfo.getSplitPayPrice()
                                        : BigDecimal.ZERO);

                //如果是商家或者平台商品则不统计供货价   transTradeItems 中已对ProviderPrice非代销商品的处理
//                if (goodsInfo.getGoodsSource() == GoodsSource.SELLER.toValue() ||
//                    goodsInfo.getGoodsSource() == GoodsSource.PLATFORM.toValue()){
//                    goodsInfo.setProviderPrice(BigDecimal.ZERO);
//                }

                // 供货金额
                providerPrice =
                        providerPrice.add(
                                goodsInfo.getProviderPrice() != null
                                        ? goodsInfo.getProviderPrice()
                                        : BigDecimal.ZERO);
                // 处理总的礼品卡金额
                giftCardPrice =
                        giftCardPrice.add(
                                Objects.nonNull(goodsInfo.getGiftCardPrice())
                                        ? goodsInfo.getGiftCardPrice()
                                        : BigDecimal.ZERO);
            }

            BigDecimal thirdPlatFormFreight =
                    trade.getThirdPlatFormFreight() != null
                            ? trade.getThirdPlatFormFreight()
                            : BigDecimal.ZERO;
            // 商家结算的供货总额要再额外加上供货运费
            providerPrice = providerPrice.add(thirdPlatFormFreight);

            // 店铺运费券优惠金额
            BigDecimal freightCouponPrice =
                    Objects.isNull(trade.getFreightCoupon())
                            ? BigDecimal.ZERO
                            : trade.getFreightCoupon().getDiscountsAmount();

            // 2.5.计算店铺应收金额 = 商品实付金额+退单改价差额+运费+通用券优惠+现金卡抵扣+（提货卡抵扣-运费）+积分抵扣-供货金额-供货运费-平台佣金-分销佣金-社区团购佣金
            BigDecimal storePrice = BigDecimal.ZERO;
            BigDecimal communityTotalCommission = BigDecimal.ZERO;
            StoreType storeType = store.getStoreType();
            if (StoreType.SUPPLIER.equals(storeType) || StoreType.O2O.equals(storeType)) {
                if (Objects.nonNull(trade.getOrderTag()) && trade.getOrderTag().getCommunityFlag()){
                    communityTotalCommission = trade.getCommunityTradeCommission().getTotalCommission();
                }
                // 提货卡订单
                if (Objects.nonNull(trade.getOrderTag()) && Objects.nonNull(trade.getOrderTag().getPickupCardFlag()) && trade.getOrderTag().getPickupCardFlag()){
                    giftCardPrice = giftCardPrice.add(deliveryPrice);
                    storePrice =
                            storePrice
                                    .add(giftCardPrice)
                                    .subtract(platformPrice)
                                    .subtract(commissionPrice)
                                    .subtract(providerPrice);

                } else {
                    storePrice =
                            storePrice
                                    .add(splitPayPrice)
                                    .add(deliveryPrice)
                                    .add(commonCouponPrice)
                                    .add(pointPrice)
                                    .subtract(platformPrice)
                                    .subtract(commissionPrice)
                                    .add(returnSpecialPrice)
                                    .subtract(providerPrice)
                                    .add(giftCardPrice)
                                    .subtract(communityTotalCommission);
                }
//4
            } else {
                // 供货总额-平台佣金；
                storePrice = providerPrice;
            }
            // 2.6.新增结算明细
            settlementDetailList.add(
                    // 结算明细
                    new SettlementDetail(
                            null,
                            uuid,
                            formatDate(startTime),
                            formatDate(getSettleEndDateForView(endTime)),
                            store.getStoreId(),
                            trade.getTradePrice().isSpecial(),
                            // 组装结算明细 - 订单明细
                            new SettleTrade(
                                    trade.getTradeState().getPayTime(),
                                    trade.getTradeState().getCreateTime(),
                                    trade.getTradeState().getEndTime(),
                                    finalTime,
                                    trade.getId(),
                                    TradeType.NORMAL_TRADE,
                                    trade.getOrderType(),
                                    GatherType.PLATFORM,
                                    deliveryPrice,
                                    BigDecimal.ZERO,
                                    BigDecimal.ZERO,
                                    returnSpecialPrice,
                                    storePrice,
                                    providerPrice,
                                    thirdPlatFormFreight,
                                    null,
                                    null,
                                    providerReturnSpecialPrice,
                                    freightCouponPrice,
                                    giftCardPrice,
                                    trade.getTradePrice().getGiftCardType()
                            ),
                            // 组装结算明细 - 商品明细
                            settleGoods,
                            true));

            // 2.7.累加结算单中的金额信息
            totalDeliveryPrice = totalDeliveryPrice.add(deliveryPrice);
            // 商品实付总额
            if (trade.getOrderTag() != null && trade.getOrderTag().getPickupCardFlag()) {
                splitPayPrice = BigDecimal.ZERO;
            }
            totalSplitPayPrice = totalSplitPayPrice.add(splitPayPrice);
            // 平台佣金总额
            totalPlatformPrice = totalPlatformPrice.add(platformPrice);
            // 通用券优惠总额
            totalCommonCouponPrice = totalCommonCouponPrice.add(commonCouponPrice);
            // 分销佣金总额
            totalCommissionPrice = totalCommissionPrice.add(commissionPrice);
            // 积分抵扣总额
            totalPointPrice = totalPointPrice.add(pointPrice);
            // 积分数量
            totalPoints = Long.sum(totalPoints, points);
            // 店铺应收总额
            totalStorePrice = totalStorePrice.add(storePrice);
            // 商品供货总额
            totalProviderPrice = totalProviderPrice.add(providerPrice);
            totalCommunityCommissionPrice = totalCommunityCommissionPrice.add(communityTotalCommission);
            if (GiftCardType.PICKUP_CARD.equals(trade.getTradePrice().getGiftCardType())){
                totalPickupGiftCardPrice = totalPickupGiftCardPrice.add(giftCardPrice);
            } else {
                // 礼品卡抵扣总额
                totalGiftCardPrice = totalGiftCardPrice.add(giftCardPrice);
            }

            // 商品供货运费总额
            totalThirdPlatFormFreightPrice =
                    totalThirdPlatFormFreightPrice.add(thirdPlatFormFreight);
        }
        /*List<SettlementDetail> settlementDetailDTOList = KsBeanUtil.convert(settlementDetailList,
                SettlementDetailDTO.class);*/
        log.info("--------------财务结算 settleDetail:{}", JSON.toJSONString(settlementDetailList));
        settlementDetailService.save(settlementDetailList);

        // 3.重新设回累加后的金额
        settlement.setDeliveryPrice(totalDeliveryPrice);
        settlement.setSplitPayPrice(totalSplitPayPrice);
        settlement.setPlatformPrice(totalPlatformPrice);
        settlement.setCommonCouponPrice(totalCommonCouponPrice);
        settlement.setCommissionPrice(totalCommissionPrice);
        settlement.setPointPrice(totalPointPrice);
        settlement.setPoints(totalPoints);
        settlement.setStorePrice(totalStorePrice);
        settlement.setProviderPrice(totalProviderPrice);
        settlement.setGiftCardPrice(totalGiftCardPrice);
        settlement.setPickupGiftCardPrice(totalPickupGiftCardPrice);
        settlement.setTotalThirdPlatFormFreight(totalThirdPlatFormFreightPrice);
        settlement.setCommunityCommissionPrice(totalCommunityCommissionPrice);
    }

    private void analyseForLakalaTrade(
            List<Trade> tradeList,
            StoreVO store,
            Date startTime,
            Date endTime,
            String uuid,
            LakalaSettlementHelper lakalasettlement,
            StoreType _storeType,
            Map<String, String> tidToSepTranSidMap) {
        List<Trade> lakalaTradeList =
                tradeList.stream()
                        .filter(trade -> PayWay.LAKALA.equals(trade.getPayWay()) || PayWay.LAKALACASHIER.equals(trade.getPayWay()))
                        .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(lakalaTradeList)) {
            return;
        }

        List<Trade> lakalaBookingSaleTradeList =
                lakalaTradeList.stream()
                        .filter(trade -> Boolean.TRUE.equals(trade.getIsBookingSaleGoods())
                                && BookingType.EARNEST_MONEY.equals(trade.getBookingType())
                                && PayState.PAID.equals(trade.getTradeState().getPayState()))
                        .collect(Collectors.toList());
        List<Trade> earnestTrades = new ArrayList<>(lakalaBookingSaleTradeList.size());
        Map<String, String> tailOrderNoToTidMap = new HashMap<>();
        // 将已完成的定金预售订单拆分为两条订单。对应两次支付流水
        lakalaBookingSaleTradeList.forEach(lakalaBookingSaleTrade -> {
            // 定金
            Trade earnestTrade = KsBeanUtil.convert(lakalaBookingSaleTrade, Trade.class);
            TradeItem tradeItem = earnestTrade.getTradeItems().get(0);
            tradeItem.setSplitPrice(earnestTrade.getTradePrice().getEarnestPrice());
            earnestTrade.getTradePrice().setDeliveryPrice(BigDecimal.ZERO);
            earnestTrade.getTradeState().setPayState(PayState.PAID_EARNEST);
            earnestTrade.setFreightCoupon(new TradeCouponVO());
            tradeItem.setCouponSettlements(new ArrayList<>());
            tradeItem.setPointsPrice(new BigDecimal("0"));
            tradeItem.setPoints(0L);
            earnestTrades.add(earnestTrade);

            // 尾款实际支付金额
            tailOrderNoToTidMap.put(lakalaBookingSaleTrade.getTailOrderNo(), lakalaBookingSaleTrade.getId());
            lakalaBookingSaleTrade.getTradeItems().get(0).setSplitPrice(lakalaBookingSaleTrade.getTradeItems()
                    .get(0).getSplitPrice().subtract(lakalaBookingSaleTrade.getTradePrice().getEarnestPrice()));
            lakalaBookingSaleTrade.setId(lakalaBookingSaleTrade.getTailOrderNo());

        });
        lakalaTradeList.addAll(earnestTrades);


        //拉卡拉普通商品
        List<Long> providerIds =
                lakalaTradeList.stream()
                        .flatMap(trade -> trade.getTradeItems().stream())
                        .map(TradeItem::getProviderId)
                        .collect(Collectors.toList());

        //拉卡拉赠品
        providerIds.addAll(lakalaTradeList.stream()
                .flatMap(trade -> trade.getGifts().stream())
                .map(TradeItem::getProviderId)
                .collect(Collectors.toList()));

        //拉卡拉换购商品
        providerIds.addAll(lakalaTradeList.stream()
                .flatMap(trade -> trade.getPreferential().stream())
                .map(TradeItem::getProviderId)
                .collect(Collectors.toList()));

        Map<Long, StoreVO> storeIdToStore =
                storeQueryProvider
                        .listNoDeleteStoreByIds(new ListNoDeleteStoreByIdsRequest(providerIds))
                        .getContext()
                        .getStoreVOList()
                        .stream()
                        .collect(Collectors.toMap(StoreVO::getStoreId, Function.identity()));

        // 1.从结算单中获取金额信息
        // 运费总额
        BigDecimal totalDeliveryPrice = lakalasettlement.getDeliveryPrice();
        // 平台佣金总额
        BigDecimal totalPlatformPrice = lakalasettlement.getPlatformPrice();
        // 通用券优惠总额
        BigDecimal totalCommonCouponPrice = lakalasettlement.getCommonCouponPrice();
        // 分销佣金总额
        BigDecimal totalCommissionPrice = lakalasettlement.getCommissionPrice();
        // 积分抵扣总额
        BigDecimal totalPointPrice = lakalasettlement.getPointPrice();
        // 积分数量
        Long totalPoints = lakalasettlement.getPoints();
        // 商品实付总额
        BigDecimal totalSplitPayPrice = lakalasettlement.getSplitPayPrice();
        // 店铺应收总额
        BigDecimal totalStorePrice = lakalasettlement.getStorePrice();
        // 供货总额
        BigDecimal totalProviderPrice = lakalasettlement.getProviderPrice();
        BigDecimal totalProviderFreight = lakalasettlement.getProviderFreight();
        BigDecimal totalGiftCardPrice = lakalasettlement.getGiftCardPrice();
        // 2.处理每笔订单，生成结算单明细；并将相应金额累加至结算单
        List<LakalaSettlementDetail> settlementDetailList = new ArrayList<>();
        for (Trade trade : lakalaTradeList) {
            LakalaLedgerStatus status = LakalaLedgerStatus.FAIL;
            BigDecimal deliveryPrice = BigDecimal.ZERO;
            String lakalaLedgerFailReason = null;
            if (Objects.nonNull(trade.getBookingType())
                    && BookingType.EARNEST_MONEY == trade.getBookingType()
                    && !FlowState.VOID.equals(trade.getTradeState().getFlowState())) {
                if (trade.getTradeState().getPayState() == PayState.PAID_EARNEST){
                    deliveryPrice = BigDecimal.ZERO;
                }
                status = LakalaLedgerStatus.INSUFFICIENT_AMOUNT;
                lakalaLedgerFailReason = "预售订单仅支持线下分账";
            } else if (trade.getThirdPlatFormFreight() == null){
                deliveryPrice =
                        trade.getTradePrice().getDeliveryPrice() == null
                                ? BigDecimal.ZERO
                                : trade.getTradePrice().getDeliveryPrice();
            }

            // 订单入账时间：普通订单则为已完成或已作废时间
            LocalDateTime finalTime = trade.getTradeState().getFinalTime();
            if (Boolean.TRUE.equals(trade.getIsBookingSaleGoods())
                    && BookingType.EARNEST_MONEY.equals(trade.getBookingType())
                    && Objects.nonNull(trade.getTradeState())
                    && (!PayState.PAID.equals(trade.getTradeState().getPayState()))) {
                // 尾款时间 < 今天 已作废的定金预售订单
                if (Objects.nonNull(trade.getTradeState().getTailEndTime())
                        && trade.getTradeState().getTailEndTime().isBefore(LocalDateTime.now())) {
                    // 预约预售仅支付了定金但已作废的订单则为尾款失效时间
                    finalTime = trade.getTradeState().getTailEndTime();
                }
            }

            // 2.1.查询订单关联的退单，并按sku分组退货信息
            List<ReturnOrder> returnOrders;
            // 尾款订单ID是尾款订单号。这里需要找到定金的订单ID
            if (tailOrderNoToTidMap.containsKey(trade.getId())){
                returnOrders = returnOrderService.findReturnByTid(tailOrderNoToTidMap.get(trade.getId()));
            } else {
                returnOrders = returnOrderService.findReturnByTid(trade.getId());
            }
            returnOrders =
                    returnOrders.stream()
                            .filter(item -> ReturnFlowState.COMPLETED == item.getReturnFlowState())
                            .collect(Collectors.toList());
            Map<String, List<ReturnItem>> returnItemsMap =
                    returnOrders.stream()
                            .flatMap(item -> item.getReturnItems().stream())
                            .collect(Collectors.groupingBy(ReturnItem::getSkuId));

            //换购商品
            Map<Long, Map<String,List<ReturnItem>>> returnPreferItemsMap =
                    returnOrders.stream()
                            .map(ReturnOrder::getReturnPreferential)
                            .flatMap(Collection::stream)
                            .collect(Collectors.groupingBy(ReturnItem::getMarketingId, Collectors.groupingBy(ReturnItem::getSkuId)));


            // 2.2.构建结算单明细中的商品列表，并将相应金额累加至结算单
            List<LakalaSettleGood> settleGoods =
                    lakalaTransTradeItems(
                            trade,
                            returnItemsMap,
                            returnPreferItemsMap,
                            trade.getOrderType(),
                            _storeType,
                            storeIdToStore);

            // 订单交易金额
            BigDecimal tradeTotalPrice = BigDecimal.ZERO;
            // 全额手续费
            BigDecimal tradeTotalHandlingFee = BigDecimal.ZERO;
            tradeTotalPrice = tradeTotalPrice.add(Objects.isNull(trade.getTradePrice().getTotalPrice())
                    ? BigDecimal.ZERO
                    : trade.getTradePrice().getTotalPrice());
            //获取订单拉卡拉扣率
            String feeRate = getFeeRate(trade);
            tradeTotalHandlingFee = tradeTotalHandlingFee.add(
                    tradeTotalPrice.multiply(new BigDecimal(feeRate).divide(new BigDecimal("100"))))
                    .setScale(2, RoundingMode.HALF_UP);

            tradeTotalHandlingFee = getLowUpPrice(trade, tradeTotalHandlingFee);

            // 2.3.计算退单改价差额
            BigDecimal returnSpecialPrice = BigDecimal.ZERO;
            // 计算供应商退单改价
            BigDecimal providerReturnSpecialPrice = BigDecimal.ZERO;
            // 拉卡拉退货手续费=退货金额/原交易金额*全额手续费
            BigDecimal returnHandlingFee = BigDecimal.ZERO;
            for (ReturnOrder returnOrder : returnOrders) {
                ReturnPrice returnPrice = returnOrder.getReturnPrice();
                // 退单的实退金额
                BigDecimal actualReturnPrice = Objects.nonNull(returnPrice.getActualReturnPrice())
                        ? returnPrice.getActualReturnPrice()
                        : BigDecimal.ZERO;
                returnSpecialPrice =
                        returnSpecialPrice.add(
                                returnPrice
                                        .getTotalPrice()
                                        .subtract(returnPrice.getActualReturnPrice()));

                if (StringUtils.isNotBlank(returnOrder.getProviderId())) {
                    providerReturnSpecialPrice =
                            providerReturnSpecialPrice.add(
                                    Objects.nonNull(returnPrice.getFee())
                                            ? returnPrice.getFee()
                                            : BigDecimal.ZERO);
                }
                returnHandlingFee = returnHandlingFee.add(
                        (actualReturnPrice.divide(tradeTotalPrice).multiply(tradeTotalHandlingFee))
                                .setScale(2, RoundingMode.HALF_UP));
            }

            // 2.4.计算订单的通用券优惠金额、积分抵现金额、平台佣金、分销佣金、供货价
            BigDecimal commonCouponPrice = BigDecimal.ZERO;
            BigDecimal pointPrice = BigDecimal.ZERO;
            long points = 0L;
            BigDecimal platformPrice = BigDecimal.ZERO;
            BigDecimal commissionPrice = BigDecimal.ZERO;
            BigDecimal splitPayPrice = BigDecimal.ZERO;
            // 供货总金额
            BigDecimal providerPrice = BigDecimal.ZERO;
            BigDecimal providerFreight;
            BigDecimal giftCardPrice = BigDecimal.ZERO;

            for (SettleGood goodsInfo : settleGoods) {
                // 通用券累加
                if (CollectionUtils.isNotEmpty(goodsInfo.getCouponSettlements())) {
                    Optional<SettleGood.CouponSettlement> couponSettle =
                            goodsInfo.getCouponSettlements().stream()
                                    .filter(
                                            item ->
                                                    item.getCouponType()
                                                            == SettleCouponType.GENERAL_VOUCHERS)
                                    .findFirst();
                    if (couponSettle.isPresent()) {
                        commonCouponPrice =
                                commonCouponPrice.add(couponSettle.get().getReducePrice());
                    }
                }

                // 积分抵扣累加
                if (Objects.nonNull(goodsInfo.getPointPrice())) {
                    pointPrice = pointPrice.add(goodsInfo.getPointPrice());
                }

                // 积分数量累加
                if (Objects.nonNull(goodsInfo.getPoints())) {
                    points = Long.sum(points, goodsInfo.getPoints());
                }

                // 平台佣金累加
                platformPrice =
                        goodsInfo.getSplitPayPrice() != null
                                        && Objects.nonNull(goodsInfo.getCateRate())
                                ? platformPrice.add(
                                        goodsInfo
                                                .getSplitPayPrice()
                                                .multiply(goodsInfo.getCateRate())
                                                .divide(new BigDecimal(100), 2, RoundingMode.DOWN))
                                : BigDecimal.ZERO;

                // 分销佣金累加
                if (Objects.nonNull(goodsInfo.getCommission())) {
                    commissionPrice = commissionPrice.add(goodsInfo.getCommission());
                }

                // 实付金额累加
                splitPayPrice =
                        splitPayPrice.add(
                                goodsInfo.getSplitPayPrice() != null
                                        ? goodsInfo.getSplitPayPrice()
                                        : BigDecimal.ZERO);

                // 供货金额
                providerPrice =
                        providerPrice.add(
                                goodsInfo.getProviderPrice() != null
                                        ? goodsInfo.getProviderPrice()
                                        : BigDecimal.ZERO);

                // 处理总的礼品卡金额
                giftCardPrice =
                        giftCardPrice.add(
                                Objects.nonNull(goodsInfo.getGiftCardPrice())
                                        ? goodsInfo.getGiftCardPrice()
                                        : BigDecimal.ZERO);
            }

            providerFreight =
                    trade.getThirdPlatFormFreight() != null
                            ? trade.getThirdPlatFormFreight()
                            : BigDecimal.ZERO;
            // 店铺运费券优惠金额
            BigDecimal freightCouponPrice =
                    Objects.isNull(trade.getFreightCoupon())
                            ? BigDecimal.ZERO
                            : trade.getFreightCoupon().getDiscountsAmount();

            // 拉卡拉交易手续费
            BigDecimal handlingFee = BigDecimal.ZERO;

            // 2.5.计算店铺应收金额 = 实付金额 + 实付运费 + 通用券金额 + 积分抵现金额 - 平台佣金 - 分销佣金 + 退单改价差额 - 供货金额(含供货运费) +
            // 店铺应收
            BigDecimal storePrice = BigDecimal.ZERO;
            StoreType storeType = store.getStoreType();
            if (StoreType.SUPPLIER.equals(storeType) || StoreType.O2O.equals(storeType)) {
                // 交易金额小于1元不收取手续费
                if (splitPayPrice.add(deliveryPrice).add(providerFreight).compareTo(BigDecimal.ONE) >= 0) {
                    // 拉卡拉手续费=全额手续费 - 退货金额/交易金额*全额手续费
                    handlingFee = tradeTotalHandlingFee.subtract(returnHandlingFee);
                }
                // 如果有退货，店铺应收金额（分账金额）计算就是：
//                待分账余额=原始订单交易金额-全额手续费-退货金额+退货金额/交易金额*全额手续费
//                其中，全额手续费(31800*0.28%)需要四舍五入，为89分
//                退货金额/交易金额*全额手续费(15900/31800)*89，需要四舍五入，为45分
//                交易金额31800分
//                全额手续费89分
//                退货金额15900分
//                待分账余额=31800-89-15900+45=15856分
                storePrice =
                        storePrice
                                .add(splitPayPrice)
                                .add(deliveryPrice)
                                .add(commonCouponPrice)
                                .add(pointPrice)
                                .subtract(platformPrice)
                                .subtract(commissionPrice)
                                .add(returnSpecialPrice)
                                .subtract(providerPrice)
//                                .subtract(providerFreight)
                                .add(providerReturnSpecialPrice)
                                .subtract(handlingFee)
                                .add(giftCardPrice);

            } else {
                // 供货总额-平台佣金；
                storePrice = providerPrice;
            }

            // 2.6.新增结算明细
            settlementDetailList.add(
                    // 结算明细
                    new LakalaSettlementDetail(
                            null,
                            uuid,
                            _storeType,
                            formatDate(startTime),
                            formatDate(getSettleEndDateForView(endTime)),
                            store.getStoreId(),
                            trade.getTradeItems().get(0).getProviderId(),
                            trade.getTradePrice().isSpecial(),
                            // 组装结算明细 - 订单明细
                            new LakalaSettleTrade(
                                    trade.getTradeState().getPayTime(),
                                    trade.getTradeState().getCreateTime(),
                                    trade.getTradeState().getEndTime(),
                                    finalTime,
                                    trade.getId(),
                                    TradeType.NORMAL_TRADE,
                                    trade.getOrderType(),
                                    GatherType.PLATFORM,
                                    deliveryPrice,
                                    BigDecimal.ZERO,
                                    BigDecimal.ZERO,
                                    returnSpecialPrice,
                                    storePrice,
                                    providerPrice,
                                    providerFreight,
                                    points,
                                    PayWay.LAKALA,
                                    providerReturnSpecialPrice,
                                    trade.getSupplier().getSupplierCode(),
                                    trade.getSupplier().getSupplierId(),
                                    trade.getSupplier().getStoreId(),
                                    trade.getParentId(),
                                    providerPrice.add(providerFreight),
                                    commissionPrice,
                                    platformPrice,
                                    trade.getCommission(),
                                    trade.getCommissions(),
                                    freightCouponPrice,
                                    giftCardPrice,
                                    Objects.nonNull(trade.getLklOrderTradeInfo()) ? trade.getLklOrderTradeInfo().getMerchantNo() : null,
                                    Objects.nonNull(trade.getLklOrderTradeInfo()) ? trade.getLklOrderTradeInfo().getTermNo(): null),
                            // 组装结算明细 - 商品明细
                            settleGoods,
                            true,
                            status,
                            tidToSepTranSidMap.get(trade.getId()),
                            feeRate,
                            handlingFee.toString(),
                            store.getStoreId(),
                            lakalaLedgerFailReason)
                    );

            // 2.7.累加结算单中的金额信息
            totalDeliveryPrice = totalDeliveryPrice.add(deliveryPrice);
            // 商品实付总额
            if (trade.getOrderTag() != null && trade.getOrderTag().getPickupCardFlag()) {
                splitPayPrice = BigDecimal.ZERO;
            }
            totalSplitPayPrice = totalSplitPayPrice.add(splitPayPrice);
            // 平台佣金总额
            totalPlatformPrice = totalPlatformPrice.add(platformPrice);
            // 通用券优惠总额
            totalCommonCouponPrice = totalCommonCouponPrice.add(commonCouponPrice);
            // 分销佣金总额
            totalCommissionPrice = totalCommissionPrice.add(commissionPrice);
            // 积分抵扣总额
            totalPointPrice = totalPointPrice.add(pointPrice);
            // 积分数量
            totalPoints = Long.sum(totalPoints, points);
            // 店铺应收总额
            totalStorePrice = totalStorePrice.add(storePrice);
            // 商品供货总额
            totalProviderPrice = totalProviderPrice.add(providerPrice);

            // 商品供货运费总额
            totalProviderFreight = totalProviderFreight.add(providerFreight);

            // 礼品卡抵扣总额
            totalGiftCardPrice = totalGiftCardPrice.add(giftCardPrice);
        }
        settlementDetailService.lakalaSave(settlementDetailList);

        // 3.重新设回累加后的金额
        lakalasettlement.setFlag(true);
        lakalasettlement.setDeliveryPrice(totalDeliveryPrice);
        lakalasettlement.setSplitPayPrice(totalSplitPayPrice);
        lakalasettlement.setPlatformPrice(totalPlatformPrice);
        lakalasettlement.setCommonCouponPrice(totalCommonCouponPrice);
        lakalasettlement.setCommissionPrice(totalCommissionPrice);
        lakalasettlement.setPointPrice(totalPointPrice);
        lakalasettlement.setPoints(totalPoints);
        lakalasettlement.setStorePrice(totalStorePrice);
        lakalasettlement.setProviderPrice(totalProviderPrice);
        lakalasettlement.setProviderFreight(totalProviderFreight);
        lakalasettlement.setGiftCardPrice(totalGiftCardPrice);
    }

    /**
     * 构建结算单明细中的商品列表，并将相应金额累加至结算单
     *
     * @param trade 订单
     * @param returnItemsMap 退单商品退货信息map(key:skuId, value:退货信息列表)
     * @return
     */
    private List<SettleGood> transTradeItems(
            Trade trade,
            Map<String, List<ReturnItem>> returnItemsMap,
            List<String> returnGiftSkuIds,
            Map<Long, Map<String,List<ReturnItem>>> returnPreferItemsMap,
            OrderType orderType,
            StoreType storeType) {

        List<TradeItem> tradeItemList = trade.getTradeItems();

        log.info("------------------ 结算 trade: {}", JSON.toJSONString(trade));

        //订单中包含赠品时，将赠品纳入计算范围
        List<TradeItem> giftItemList = trade.getGifts();
        if (CollectionUtils.isNotEmpty(giftItemList)) {
            if (CollectionUtils.isNotEmpty(returnGiftSkuIds)){
                giftItemList =
                        giftItemList.stream().filter(g -> !returnGiftSkuIds.contains(g.getSkuId())).collect(Collectors.toList());
            }
            tradeItemList =
                    Stream.of(tradeItemList, giftItemList)
                            .flatMap(Collection::stream)
                            .collect(Collectors.toList());
        }

        //订单中包含换购商品时，将换购商品纳入计算范围
        List<TradeItem> preferItemList = trade.getPreferential();

        // 将订单商品的skuno转换为供应商商品的skno
        try {
            tradeItemList.forEach(
                    tradeItem -> {
                        if (StoreType.PROVIDER == storeType) {
                            GoodsInfoViewByIdResponse goodsInfoViewByIdResponse =
                                    goodsInfoQueryProvider
                                            .getViewById(
                                                    GoodsInfoViewByIdRequest.builder()
                                                            .goodsInfoId(tradeItem.getSkuId())
                                                            .build())
                                            .getContext();
                            if (Objects.nonNull(goodsInfoViewByIdResponse)
                                    && Objects.nonNull(goodsInfoViewByIdResponse.getGoodsInfo())
                                    && StringUtils.isNotBlank(
                                            goodsInfoViewByIdResponse
                                                    .getGoodsInfo()
                                                    .getProviderGoodsInfoId())) {
                                goodsInfoViewByIdResponse =
                                        goodsInfoQueryProvider
                                                .getViewById(
                                                        GoodsInfoViewByIdRequest.builder()
                                                                .goodsInfoId(
                                                                        goodsInfoViewByIdResponse
                                                                                .getGoodsInfo()
                                                                                .getProviderGoodsInfoId())
                                                                .build())
                                                .getContext();
                                if (Objects.nonNull(goodsInfoViewByIdResponse)
                                        && Objects.nonNull(
                                                goodsInfoViewByIdResponse.getGoodsInfo())) {
                                    tradeItem.setSkuNo(
                                            goodsInfoViewByIdResponse
                                                    .getGoodsInfo()
                                                    .getGoodsInfoNo());
                                    tradeItem.setSkuName(
                                            goodsInfoViewByIdResponse
                                                    .getGoodsInfo()
                                                    .getGoodsInfoName());
                                }
                            }
                        }
                        if (StringUtils.isBlank(tradeItem.getCateName())) {
                            GoodsCateByIdRequest request = new GoodsCateByIdRequest();
                            request.setCateId(tradeItem.getCateId());
                            tradeItem.setCateName(
                                    goodsCateQueryProvider
                                            .getById(request)
                                            .getContext()
                                            .getCateName());
                        }
                    });
        } catch (Exception e) {
            log.error("order tradeitem parse exception", e);
        }

        PayState payState = trade.getTradeState().getPayState();
        BookingType bookingType = trade.getBookingType();
        List<CommunityTradeCommission.GoodsInfoItem> goodsInfoItems = new ArrayList<>();
        if (Objects.nonNull(trade.getOrderTag()) && trade.getOrderTag().getCommunityFlag()){
            goodsInfoItems = trade.getCommunityTradeCommission().getGoodsInfoItem();
        }
        Map<String, CommunityTradeCommission.GoodsInfoItem> skuIdToIdentityMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(goodsInfoItems)){
            skuIdToIdentityMap = goodsInfoItems.stream()
                    .collect(Collectors.toMap(CommunityTradeCommission.GoodsInfoItem::getGoodsInfoId, Function.identity()));
        }
        // 1.构建结算单明细商品列表
        List<SettleGood> newSettleGoodList = new ArrayList<>(2);
        for (TradeItem item : tradeItemList) {
            CommunityTradeCommission.GoodsInfoItem goodsInfoItem = skuIdToIdentityMap.getOrDefault(item.getSkuId(),
                    new CommunityTradeCommission.GoodsInfoItem());
            // 1.1.从订单商品中获取相应字段
            // 购买数量
            long buyCount = item.getNum();
            //如果存在周期购的购买期数，则购买数量 = 购买数量 * 期数
            Integer buyCycleNum = item.getBuyCycleNum();
            if (Objects.nonNull(buyCycleNum) && buyCycleNum> Constants.ZERO) {
                buyCount = BigDecimal.valueOf(buyCycleNum).longValue() * buyCount;
                item.setNum(buyCount);
            }
            // 营销信息(满减、满折)
            List<SettleGood.MarketingSettlement> marketings =
                    convertMarketingType(item.getMarketingSettlements());
            // 优惠券信息(店铺券、通用券)
            List<SettleGood.CouponSettlement> coupons =
                    convertCouponType(item.getCouponSettlements());

            // 订单为积分订单，积分抵现金额为结算价，普通订单则累加积分抵扣
            BigDecimal pointPrice;
            if (orderType == OrderType.POINTS_ORDER) {
                pointPrice = item.getSettlementPrice().multiply(new BigDecimal(buyCount));
            } else {
                pointPrice = item.getPointsPrice();
            }

            Long points = item.getPoints();

            // 实付金额
            BigDecimal splitPayPrice = item.getSplitPrice();
            if (Objects.nonNull(bookingType)
                    && bookingType == BookingType.EARNEST_MONEY
                    && payState == PayState.PAID_EARNEST) {
                splitPayPrice = item.getEarnestPrice();
                pointPrice=null;
                points=null;
                coupons=null;
            }

            // 供货价
            BigDecimal supplyPrice = item.getProviderId() != null
                    ? item.getSupplyPrice() : BigDecimal.ZERO;

            // 供货金额
            BigDecimal providerPrice = BigDecimal.ZERO;

            if (supplyPrice != null) {
                providerPrice = supplyPrice.multiply(new BigDecimal(buyCount));
            }

            //礼品卡金额
            BigDecimal giftCardPrice = BigDecimal.ZERO;
            if (CollectionUtils.isNotEmpty(item.getGiftCardItemList())) {
                giftCardPrice = item.getGiftCardItemList().stream().map(i->Objects.isNull(i.getPrice())?BigDecimal.ZERO:i.getPrice()).reduce(BigDecimal.ZERO, BigDecimal::add);
            }

            // 分销佣金
            BigDecimal commission = BigDecimal.ZERO;
            BigDecimal commissionRate = BigDecimal.ZERO;
            if (DistributionGoodsAudit.CHECKED.equals(item.getDistributionGoodsAudit())) {
                Optional<TradeDistributeItem> distributeItem =
                        trade.getDistributeItems().stream()
                                .filter(i -> i.getGoodsInfoId().equals(item.getSkuId()))
                                .findFirst();
                if (distributeItem.isPresent()) {
                    commission = distributeItem.get().getTotalCommission();
                    commissionRate = distributeItem.get().getCommissionRate();
                }
            }
            // 商品退货信息
            List<ReturnItem> returnItems = returnItemsMap.get(item.getSkuId());

            // 1.2.如果商品有退货，计算排除退货后的价格
            if (CollectionUtils.isNotEmpty(returnItems)) {

                // 获取退货数量
                Integer returnNum =
                        returnItems.stream().map(g -> {
                            Integer buyCycleNumItem = g.getCycleReturnNum();
                            if (Objects.nonNull(buyCycleNumItem) && buyCycleNumItem> Constants.ZERO){
                                return buyCycleNumItem;
                            } else {
                                return g.getNum();
                            }
                        }).reduce(0, Integer::sum);
                // 重算购买数量
                buyCount = buyCount - returnNum;
                // 重算营销信息(满减、满折)
                if (CollectionUtils.isNotEmpty(marketings)) {
                    marketings.forEach(
                            marketing ->
                                    marketing.setSplitPrice(
                                            calcSubReturnPrice(
                                                    marketing.getSplitPrice(),
                                                    item.getNum(),
                                                    returnNum)));
                }
                // 重算优惠券信息(店铺券、通用券)
                if (CollectionUtils.isNotEmpty(coupons)) {
                    coupons.forEach(
                            coupon -> {
                                coupon.setSplitPrice(
                                        calcSubReturnPrice(
                                                coupon.getSplitPrice(), item.getNum(), returnNum));
                                coupon.setReducePrice(
                                        calcSubReturnPrice(
                                                coupon.getReducePrice(), item.getNum(), returnNum));
                            });
                }
                // 重算积分抵扣、实付金额
                if (Objects.nonNull(pointPrice)) {
                    pointPrice = calcSubReturnPrice(pointPrice, item.getNum(), returnNum);
                }
                // 重算积分金额
                if (Objects.nonNull(points)) {
                    points = calcSubReturnPoint(points, item.getNum(), returnNum);
                }

                // 重算实付金额
                splitPayPrice = calcSubReturnPrice(item.getSplitPrice(), item.getNum(), returnNum);
                // 重算分销佣金
                if (Objects.nonNull(commission)) {
                    commission = calcSubReturnPrice(commission, item.getNum(), returnNum);
                }
                // 重新计算供货价
                if (Objects.nonNull(providerPrice)) {
                    providerPrice = calcSubReturnPrice(providerPrice, item.getNum(), returnNum);
                }

                // 从退单中获取 已退礼品卡金额
                List<ReturnItem> hasGiftCardReturnItems = returnItems.stream().filter(i -> CollectionUtils.isNotEmpty(i.getGiftCardItemList())).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(hasGiftCardReturnItems)) {
                    for (ReturnItem returnItem : hasGiftCardReturnItems) {
                        giftCardPrice = giftCardPrice.subtract(returnItem.getGiftCardItemList().stream().map(i->Objects.isNull(i.getReturnPrice())? BigDecimal.ZERO:i.getReturnPrice()).reduce(BigDecimal.ZERO, BigDecimal::add));
                    }
                }
            }

            GiftCardType giftCardType = null;
            if (CollectionUtils.isNotEmpty(item.getGiftCardItemList())){
                giftCardType = item.getGiftCardItemList().get(0).getGiftCardType();
            }
            // 1.3.添加结算单明细商品
            newSettleGoodList.add(
                    new SettleGood(
                            item.getSkuName(),
                            item.getSkuNo(),
                            item.getSpecDetails(),
                            item.getPrice(),
                            item.getCateName(),
                            item.getCateRate(),
                            buyCount,
                            splitPayPrice,
                            0L,
                            0L,
                            BigDecimal.ZERO,
                            item.getSkuId(),
                            null,
                            marketings,
                            coupons,
                            pointPrice,
                            points,
                            commissionRate,
                            commission,
                            providerPrice,
                            supplyPrice,
                            item.getGoodsSource(),
                            giftCardPrice,
                            giftCardType,
                            goodsInfoItem.getCommission(),
                            goodsInfoItem.getCommissionRate()));
        }


        // 1.构建结算单明细换购商品列表
        for (TradeItem item : preferItemList) {

            // 1.1.从订单商品中获取相应字段
            // 购买数量
            long buyCount = item.getNum();
            // 营销信息(满减、满折)
            List<SettleGood.MarketingSettlement> marketings =
                    convertMarketingType(item.getMarketingSettlements());
            // 优惠券信息(店铺券、通用券)
            List<SettleGood.CouponSettlement> coupons =
                    convertCouponType(item.getCouponSettlements());

            // 订单为积分订单，积分抵现金额为结算价，普通订单则累加积分抵扣
            BigDecimal pointPrice;
            if (orderType == OrderType.POINTS_ORDER) {
                pointPrice = item.getSettlementPrice().multiply(new BigDecimal(buyCount));
            } else {
                pointPrice = item.getPointsPrice();
            }

            Long points = item.getPoints();

            // 实付金额
            BigDecimal splitPayPrice = item.getSplitPrice();
            if (Objects.nonNull(bookingType)
                    && bookingType == BookingType.EARNEST_MONEY
                    && payState == PayState.PAID_EARNEST) {
                splitPayPrice = item.getEarnestPrice();
            }

            // 供货价
            BigDecimal supplyPrice = item.getProviderId() != null
                    ? item.getSupplyPrice() : BigDecimal.ZERO;

            // 供货金额
            BigDecimal providerPrice = BigDecimal.ZERO;

            if (supplyPrice != null) {
                providerPrice = supplyPrice.multiply(new BigDecimal(buyCount));
            }

            // 分销佣金
            BigDecimal commission = BigDecimal.ZERO;
            BigDecimal commissionRate = BigDecimal.ZERO;
            if (DistributionGoodsAudit.CHECKED.equals(item.getDistributionGoodsAudit())) {
                Optional<TradeDistributeItem> distributeItem =
                        trade.getDistributeItems().stream()
                                .filter(i -> i.getGoodsInfoId().equals(item.getSkuId()))
                                .findFirst();
                if (distributeItem.isPresent()) {
                    commission = distributeItem.get().getTotalCommission();
                    commissionRate = distributeItem.get().getCommissionRate();
                }
            }
            // 商品退货信息
            Map<String,List<ReturnItem>> returnItemsMaps
                    = returnPreferItemsMap.getOrDefault(item.getMarketingIds().get(0), new HashMap<>());

            List<ReturnItem> returnItems = returnItemsMaps.get(item.getSkuId());

            // 1.2.如果商品有退货，计算排除退货后的价格
            if (CollectionUtils.isNotEmpty(returnItems)) {

                // 获取退货数量
                Integer returnNum =
                        returnItems.stream().map(ReturnItem::getNum).reduce(0, Integer::sum);
                // 重算购买数量
                buyCount = item.getNum() - returnNum;
                // 重算营销信息(满减、满折)
                if (Objects.nonNull(marketings) && CollectionUtils.isNotEmpty(marketings)) {
                    marketings.forEach(
                            marketing ->
                                    marketing.setSplitPrice(
                                            calcSubReturnPrice(
                                                    marketing.getSplitPrice(),
                                                    item.getNum(),
                                                    returnNum)));
                }
                // 重算优惠券信息(店铺券、通用券)
                if (Objects.nonNull(coupons) && CollectionUtils.isNotEmpty(coupons)) {
                    coupons.forEach(
                            coupon -> {
                                coupon.setSplitPrice(
                                        calcSubReturnPrice(
                                                coupon.getSplitPrice(), item.getNum(), returnNum));
                                coupon.setReducePrice(
                                        calcSubReturnPrice(
                                                coupon.getReducePrice(), item.getNum(), returnNum));
                            });
                }
                // 重算积分抵扣、实付金额
                if (Objects.nonNull(pointPrice)) {
                    pointPrice = calcSubReturnPrice(pointPrice, item.getNum(), returnNum);
                }
                // 重算实付金额
                splitPayPrice = calcSubReturnPrice(item.getSplitPrice(), item.getNum(), returnNum);
                // 重算分销佣金
                if (Objects.nonNull(commission)) {
                    commission = calcSubReturnPrice(commission, item.getNum(), returnNum);
                }
                // 重新计算供货价
                if (Objects.nonNull(providerPrice)) {
                    providerPrice = calcSubReturnPrice(providerPrice, item.getNum(), returnNum);
                }
            }

            // 1.3.添加结算单明细商品
            newSettleGoodList.add(
                    new SettleGood(
                            item.getSkuName(),
                            item.getSkuNo(),
                            item.getSpecDetails(),
                            item.getPrice(),
                            item.getCateName(),
                            item.getCateRate(),
                            buyCount,
                            splitPayPrice,
                            0L,
                            0L,
                            BigDecimal.ZERO,
                            item.getSkuId(),
                            null,
                            marketings,
                            coupons,
                            pointPrice,
                            points,
                            commissionRate,
                            commission,
                            providerPrice,
                            supplyPrice,
                            item.getGoodsSource(),
                            BigDecimal.ZERO,
                            null,
                            null,
                            null));
        }

        return newSettleGoodList;
    }

    private List<LakalaSettleGood> lakalaTransTradeItems(
            Trade trade,
            Map<String, List<ReturnItem>> returnItemsMap,
            Map<Long, Map<String,List<ReturnItem>>> returnPreferItemsMap,
            OrderType orderType,
            StoreType storeType,
            Map<Long, StoreVO> storeIdToStore) {
        List<TradeItem> tradeItemList = trade.getTradeItems();
        // 订单中包含赠品时，将赠品纳入计算范围
        List<TradeItem> giftItemList = trade.getGifts();
        if (CollectionUtils.isNotEmpty(giftItemList)) {
            tradeItemList =
                    Stream.of(tradeItemList, giftItemList)
                            .flatMap(Collection::stream)
                            .collect(Collectors.toList());
        }

        //订单中包含换购商品时，将换购商品纳入计算范围
        List<TradeItem> preferItemList = trade.getPreferential();

        // 将订单商品的skuno转换为供应商商品的skno
        try {
            tradeItemList.forEach(
                    tradeItem -> {
                        if (StoreType.PROVIDER == storeType) {
                            GoodsInfoViewByIdResponse goodsInfoViewByIdResponse =
                                    goodsInfoQueryProvider
                                            .getViewById(
                                                    GoodsInfoViewByIdRequest.builder()
                                                            .goodsInfoId(tradeItem.getSkuId())
                                                            .build())
                                            .getContext();
                            if (Objects.nonNull(goodsInfoViewByIdResponse)
                                    && Objects.nonNull(goodsInfoViewByIdResponse.getGoodsInfo())
                                    && StringUtils.isNotBlank(
                                            goodsInfoViewByIdResponse
                                                    .getGoodsInfo()
                                                    .getProviderGoodsInfoId())) {
                                goodsInfoViewByIdResponse =
                                        goodsInfoQueryProvider
                                                .getViewById(
                                                        GoodsInfoViewByIdRequest.builder()
                                                                .goodsInfoId(
                                                                        goodsInfoViewByIdResponse
                                                                                .getGoodsInfo()
                                                                                .getProviderGoodsInfoId())
                                                                .build())
                                                .getContext();
                                if (Objects.nonNull(goodsInfoViewByIdResponse)
                                        && Objects.nonNull(
                                                goodsInfoViewByIdResponse.getGoodsInfo())) {
                                    tradeItem.setSkuNo(
                                            goodsInfoViewByIdResponse
                                                    .getGoodsInfo()
                                                    .getGoodsInfoNo());
                                    tradeItem.setSkuName(
                                            goodsInfoViewByIdResponse
                                                    .getGoodsInfo()
                                                    .getGoodsInfoName());
                                }
                            }
                        }
                        if (StringUtils.isBlank(tradeItem.getCateName())) {
                            GoodsCateByIdRequest request = new GoodsCateByIdRequest();
                            request.setCateId(tradeItem.getCateId());
                            tradeItem.setCateName(
                                    goodsCateQueryProvider
                                            .getById(request)
                                            .getContext()
                                            .getCateName());
                        }
                    });
        } catch (Exception e) {
            log.error("order tradeitem parse exception", e);
        }

        PayState payState = trade.getTradeState().getPayState();
        BookingType bookingType = trade.getBookingType();
        List<CommunityTradeCommission.GoodsInfoItem> goodsInfoItems = new ArrayList<>();
        if (Objects.nonNull(trade.getOrderTag()) && trade.getOrderTag().getCommunityFlag()){
            goodsInfoItems = trade.getCommunityTradeCommission().getGoodsInfoItem();
        }
        Map<String, CommunityTradeCommission.GoodsInfoItem> skuIdToIdentityMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(goodsInfoItems)){
            skuIdToIdentityMap = goodsInfoItems.stream()
                    .collect(Collectors.toMap(CommunityTradeCommission.GoodsInfoItem::getGoodsInfoId, Function.identity()));
        }
        // 1.构建结算单明细商品列表
        List<LakalaSettleGood> newSettleGoodList = new ArrayList<>(2);
        for (TradeItem item : tradeItemList) {
            CommunityTradeCommission.GoodsInfoItem goodsInfoItem = skuIdToIdentityMap.getOrDefault(item.getSkuId(),
                    new CommunityTradeCommission.GoodsInfoItem());
            // 1.1.从订单商品中获取相应字段
            // 购买数量
            long buyCount = item.getNum();
            // 营销信息(满减、满折)
            List<SettleGood.MarketingSettlement> marketings =
                    convertMarketingType(item.getMarketingSettlements());
            // 优惠券信息(店铺券、通用券)
            List<SettleGood.CouponSettlement> coupons =
                    convertCouponType(item.getCouponSettlements());

            // 订单为积分订单，积分抵现金额为结算价，普通订单则累加积分抵扣
            BigDecimal pointPrice;
            if (orderType == OrderType.POINTS_ORDER) {
                pointPrice = item.getSettlementPrice().multiply(new BigDecimal(buyCount));
            } else {
                pointPrice = item.getPointsPrice();
            }

            Long points = item.getPoints();

            // 实付金额
            BigDecimal splitPayPrice = item.getSplitPrice();
            if (Objects.nonNull(bookingType)
                    && bookingType == BookingType.EARNEST_MONEY
                    && payState == PayState.PAID_EARNEST) {
                splitPayPrice = item.getEarnestPrice();
            }

            // 供货价
            BigDecimal supplyPrice = item.getProviderId() != null
                    ? item.getSupplyPrice() : BigDecimal.ZERO;

            // 供货金额
            BigDecimal providerPrice = BigDecimal.ZERO;

            if (supplyPrice != null) {
                providerPrice = supplyPrice.multiply(new BigDecimal(buyCount));
            }

            //礼品卡金额
            BigDecimal giftCardPrice = BigDecimal.ZERO;
            if (CollectionUtils.isNotEmpty(item.getGiftCardItemList())) {
                giftCardPrice = item.getGiftCardItemList().stream().map(i->Objects.isNull(i.getPrice())?BigDecimal.ZERO:i.getPrice()).reduce(BigDecimal.ZERO, BigDecimal::add);
            }

            // 分销佣金
            BigDecimal commission = BigDecimal.ZERO;
            BigDecimal commissionRate = BigDecimal.ZERO;
            if (DistributionGoodsAudit.CHECKED.equals(item.getDistributionGoodsAudit())) {
                Optional<TradeDistributeItem> distributeItem =
                        trade.getDistributeItems().stream()
                                .filter(i -> i.getGoodsInfoId().equals(item.getSkuId()))
                                .findFirst();
                if (distributeItem.isPresent()) {
                    commission = distributeItem.get().getTotalCommission();
                    commissionRate = distributeItem.get().getCommissionRate();
                }
            }
            // 商品退货信息
            List<ReturnItem> returnItems = returnItemsMap.get(item.getSkuId());

            // 1.2.如果商品有退货，计算排除退货后的价格
            if (CollectionUtils.isNotEmpty(returnItems)) {

                // 获取退货数量
                Integer returnNum =
                        returnItems.stream().map(ReturnItem::getNum).reduce(0, Integer::sum);
                // 重算购买数量
                buyCount = item.getNum() - returnNum;
                // 重算营销信息(满减、满折)
                if (CollectionUtils.isNotEmpty(marketings)) {
                    marketings.forEach(
                            marketing ->
                                    marketing.setSplitPrice(
                                            calcSubReturnPrice(
                                                    marketing.getSplitPrice(),
                                                    item.getNum(),
                                                    returnNum)));
                }
                // 重算优惠券信息(店铺券、通用券)
                if (CollectionUtils.isNotEmpty(coupons)) {
                    coupons.forEach(
                            coupon -> {
                                coupon.setSplitPrice(
                                        calcSubReturnPrice(
                                                coupon.getSplitPrice(), item.getNum(), returnNum));
                                coupon.setReducePrice(
                                        calcSubReturnPrice(
                                                coupon.getReducePrice(), item.getNum(), returnNum));
                            });
                }
                // 重算积分抵扣、实付金额
                if (Objects.nonNull(pointPrice)) {
                    pointPrice = calcSubReturnPrice(pointPrice, item.getNum(), returnNum);
                }
                // 重算实付金额
                splitPayPrice = calcSubReturnPrice(item.getSplitPrice(), item.getNum(), returnNum);
                // 重算分销佣金
                if (Objects.nonNull(commission)) {
                    commission = calcSubReturnPrice(commission, item.getNum(), returnNum);
                }
                // 重新计算供货价
                if (Objects.nonNull(providerPrice)) {
                    providerPrice = calcSubReturnPrice(providerPrice, item.getNum(), returnNum);
                }
                // 从退单中获取 已退礼品卡金额
                List<ReturnItem> hasGiftCardReturnItems = returnItems.stream().filter(i -> CollectionUtils.isNotEmpty(i.getGiftCardItemList())).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(hasGiftCardReturnItems)) {
                    for (ReturnItem returnItem : hasGiftCardReturnItems) {
                        giftCardPrice = giftCardPrice.subtract(returnItem.getGiftCardItemList().stream().map(i->Objects.isNull(i.getReturnPrice())?BigDecimal.ZERO:i.getReturnPrice()).reduce(BigDecimal.ZERO, BigDecimal::add));
                    }
                }
            }

            String providerName = "";
            String providerCompanyInfoId = "";
            if (Objects.nonNull(storeIdToStore) && Objects.nonNull(item.getProviderId())) {
                providerName = storeIdToStore.get(item.getProviderId()).getStoreName();
                providerCompanyInfoId =
                        storeIdToStore.get(item.getProviderId()).getCompanyInfoId().toString();
            }

            // 1.3.添加结算单明细商品
            newSettleGoodList.add(
                    new LakalaSettleGood(
                            item.getSkuName(),
                            item.getSkuNo(),
                            item.getSpecDetails(),
                            item.getPrice(),
                            item.getCateName(),
                            item.getCateRate(),
                            buyCount,
                            splitPayPrice,
                            0L,
                            0L,
                            BigDecimal.ZERO,
                            item.getSkuId(),
                            null,
                            marketings,
                            coupons,
                            pointPrice,
                            points,
                            commissionRate,
                            commission,
                            providerPrice,
                            supplyPrice,
                            trade.getInviteeId(),
                            trade.getDistributorName(),
                            providerCompanyInfoId,
                            providerName,
                            item.getGoodsSource(),
                            giftCardPrice,
                            goodsInfoItem.getCommission(),
                            goodsInfoItem.getCommissionRate()));
        }

        for (TradeItem item : preferItemList) {

            // 1.1.从订单商品中获取相应字段
            // 购买数量
            long buyCount = item.getNum();
            // 营销信息(满减、满折)
            List<SettleGood.MarketingSettlement> marketings =
                    convertMarketingType(item.getMarketingSettlements());
            // 优惠券信息(店铺券、通用券)
            List<SettleGood.CouponSettlement> coupons =
                    convertCouponType(item.getCouponSettlements());

            // 订单为积分订单，积分抵现金额为结算价，普通订单则累加积分抵扣
            BigDecimal pointPrice;
            if (orderType == OrderType.POINTS_ORDER) {
                pointPrice = item.getSettlementPrice().multiply(new BigDecimal(buyCount));
            } else {
                pointPrice = item.getPointsPrice();
            }

            Long points = item.getPoints();

            // 实付金额
            BigDecimal splitPayPrice = item.getSplitPrice();
            if (Objects.nonNull(bookingType)
                    && bookingType == BookingType.EARNEST_MONEY
                    && payState == PayState.PAID_EARNEST) {
                splitPayPrice = item.getEarnestPrice();
            }

            // 供货价
            BigDecimal supplyPrice = item.getProviderId() != null
                    ? item.getSupplyPrice() : BigDecimal.ZERO;

            // 供货金额
            BigDecimal providerPrice = BigDecimal.ZERO;

            if (supplyPrice != null) {
                providerPrice = supplyPrice.multiply(new BigDecimal(buyCount));
            }

            // 分销佣金
            BigDecimal commission = BigDecimal.ZERO;
            BigDecimal commissionRate = BigDecimal.ZERO;
            if (DistributionGoodsAudit.CHECKED.equals(item.getDistributionGoodsAudit())) {
                Optional<TradeDistributeItem> distributeItem =
                        trade.getDistributeItems().stream()
                                .filter(i -> i.getGoodsInfoId().equals(item.getSkuId()))
                                .findFirst();
                if (distributeItem.isPresent()) {
                    commission = distributeItem.get().getTotalCommission();
                    commissionRate = distributeItem.get().getCommissionRate();
                }
            }
            // 商品退货信息
            Map<String,List<ReturnItem>> returnItemsMaps
                    = returnPreferItemsMap.getOrDefault(item.getMarketingIds().get(0), new HashMap<>());

            List<ReturnItem> returnItems = returnItemsMaps.get(item.getSkuId());

            // 1.2.如果商品有退货，计算排除退货后的价格
            if (CollectionUtils.isNotEmpty(returnItems)) {

                // 获取退货数量
                Integer returnNum =
                        returnItems.stream().map(ReturnItem::getNum).reduce(0, Integer::sum);
                // 重算购买数量
                buyCount = item.getNum() - returnNum;
                // 重算营销信息(满减、满折)
                if (Objects.nonNull(marketings) && CollectionUtils.isNotEmpty(marketings)) {
                    marketings.forEach(
                            marketing ->
                                    marketing.setSplitPrice(
                                            calcSubReturnPrice(
                                                    marketing.getSplitPrice(),
                                                    item.getNum(),
                                                    returnNum)));
                }
                // 重算优惠券信息(店铺券、通用券)
                if (Objects.nonNull(coupons) && CollectionUtils.isNotEmpty(coupons)) {
                    coupons.forEach(
                            coupon -> {
                                coupon.setSplitPrice(
                                        calcSubReturnPrice(
                                                coupon.getSplitPrice(), item.getNum(), returnNum));
                                coupon.setReducePrice(
                                        calcSubReturnPrice(
                                                coupon.getReducePrice(), item.getNum(), returnNum));
                            });
                }
                // 重算积分抵扣、实付金额
                if (Objects.nonNull(pointPrice)) {
                    pointPrice = calcSubReturnPrice(pointPrice, item.getNum(), returnNum);
                }
                // 重算实付金额
                splitPayPrice = calcSubReturnPrice(item.getSplitPrice(), item.getNum(), returnNum);
                // 重算分销佣金
                if (Objects.nonNull(commission)) {
                    commission = calcSubReturnPrice(commission, item.getNum(), returnNum);
                }
                // 重新计算供货价
                if (Objects.nonNull(providerPrice)) {
                    providerPrice = calcSubReturnPrice(providerPrice, item.getNum(), returnNum);
                }
            }

            String providerName = "";
            String providerCompanyInfoId = "";
            if (Objects.nonNull(storeIdToStore) && Objects.nonNull(item.getProviderId())) {
                providerName = storeIdToStore.get(item.getProviderId()).getStoreName();
                providerCompanyInfoId =
                        storeIdToStore.get(item.getProviderId()).getCompanyInfoId().toString();
            }

            // 1.3.添加结算单明细商品
            newSettleGoodList.add(
                    new LakalaSettleGood(
                            item.getSkuName(),
                            item.getSkuNo(),
                            item.getSpecDetails(),
                            item.getPrice(),
                            item.getCateName(),
                            item.getCateRate(),
                            buyCount,
                            splitPayPrice,
                            0L,
                            0L,
                            BigDecimal.ZERO,
                            item.getSkuId(),
                            null,
                            marketings,
                            coupons,
                            pointPrice,
                            points,
                            commissionRate,
                            commission,
                            providerPrice,
                            supplyPrice,
                            trade.getInviteeId(),
                            trade.getDistributorName(),
                            providerCompanyInfoId,
                            providerName,
                            item.getGoodsSource(),
                            BigDecimal.ZERO,
                            null,
                            null));
        }


        log.info("--------------财务结算 settleGoods:{}", JSON.toJSONString(newSettleGoodList));
        return newSettleGoodList;
    }

    /**
     * 计算排除退货后的价格
     *
     * @param originPrice 原价
     * @param buyNum 购买数量
     * @param returnNum 退货数量
     * @return
     */
    private static BigDecimal calcSubReturnPrice(
            BigDecimal originPrice, Long buyNum, Integer returnNum) {
        if (buyNum - returnNum == 0) {
            return BigDecimal.ZERO;
        }
        // 金额 = 原价 - (原价 / 购买数量) * 退货数量
        return originPrice.subtract(
                originPrice
                        .divide(new BigDecimal(buyNum), 2, RoundingMode.DOWN)
                        .multiply(new BigDecimal(returnNum)));
    }

    /**
     * 计算排除退货后的积分
     *
     * @param point 原价
     * @param buyNum 购买数量
     * @param returnNum 退货数量
     * @return
     */
    private static Long calcSubReturnPoint(
            Long point, Long buyNum, Integer returnNum) {
        if (buyNum - returnNum == 0) {
            return 0L;
        }
        // 金额 = 原价 - (原价 / 购买数量) * 退货数量
        return point-point/buyNum*returnNum;
    }

    /**
     * 获取结算单最后一天日期
     *
     * <p>目前设定新的业务逻辑: 准备生成结算单: 获取开始时间的逻辑 1. 查询上一次结算单的终止时间 2. 如果没有, 则认为该店铺没有结算过, 则设为该店铺的签约起始日期
     *
     * @param store
     * @return
     */
    private Date getStartDate(StoreVO store) {
        ZoneId zoneId = ZoneId.systemDefault();
        SettlementLastResponse response =
                settlementQueryProvider
                        .getLastSettlementByStoreId(
                                SettlementLastByStoreIdRequest.builder()
                                        .storeId(store.getStoreId())
                                        .build())
                        .getContext();

        LakalaSettlementGetByIdResponse lakalaSettlementGetByIdResponse =
                settlementQueryProvider
                        .getLastLakalaSettlementByStoreId(
                                SettlementLastByStoreIdRequest.builder()
                                        .storeId(store.getStoreId())
                                        .build())
                        .getContext();
        Date date = null;
        if (Objects.nonNull(response) || Objects.nonNull(lakalaSettlementGetByIdResponse)) {
            LocalDate lakalaSettlement = null, settlement = null;
            if (Objects.nonNull(lakalaSettlementGetByIdResponse)) {
                String lakalaSettlementStr = lakalaSettlementGetByIdResponse.getEndTime();
                lakalaSettlement =
                        LocalDate.parse(
                                lakalaSettlementStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            }
            if (Objects.nonNull(response)) {
                String str = response.getEndTime();
                settlement = LocalDate.parse(str, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            }
            if (Objects.nonNull(lakalaSettlement) && Objects.nonNull(settlement)) {
                if (lakalaSettlement.isAfter(settlement)) {
                    LocalDateTime beginDateTime =
                            lakalaSettlement.atTime(LocalTime.MIN).plusDays(1);
                    date = Date.from(beginDateTime.atZone(zoneId).toInstant());
                } else {
                    LocalDateTime beginDateTime = settlement.atTime(LocalTime.MIN).plusDays(1);
                    date = Date.from(beginDateTime.atZone(zoneId).toInstant());
                }
            } else if (Objects.nonNull(lakalaSettlement)) {
                LocalDateTime beginDateTime = lakalaSettlement.atTime(LocalTime.MIN).plusDays(1);
                date = Date.from(beginDateTime.atZone(zoneId).toInstant());
            } else if (Objects.nonNull(settlement)) {
                LocalDateTime beginDateTime = settlement.atTime(LocalTime.MIN).plusDays(1);
                date = Date.from(beginDateTime.atZone(zoneId).toInstant());
            }
        } else {
            if (store.getContractStartDate() == null) {
                StoreByIdResponse storeByIdResponse =
                        storeQueryProvider
                                .getById(
                                        StoreByIdRequest.builder()
                                                .storeId(store.getStoreId())
                                                .build())
                                .getContext();
                if (store.getContractStartDate() == null) {
                    if (storeByIdResponse != null
                            && storeByIdResponse.getStoreVO().getContractStartDate() != null) {
                        store.setContractStartDate(
                                storeByIdResponse.getStoreVO().getContractStartDate());
                    }
                }
            }
            LocalDateTime localDateTime = store.getContractStartDate();
            ZonedDateTime zdt = localDateTime.atZone(zoneId);
            date = Date.from(zdt.toInstant());
        }
        return date;
    }

    /**
     * 因为实际账期如果是10，前台和明细表中记录的结束时间是9
     *
     * @param targetDate
     * @return
     */
    private static Date getSettleEndDateForView(Date targetDate) {
        // 账期结束时间，显示的其实是前一天，比如10号截至的账期，显示的是 xxx～9号的账期
        Calendar settleEndDateForView = Calendar.getInstance();
        settleEndDateForView.setTime(targetDate);
        settleEndDateForView.add(Calendar.DAY_OF_MONTH, -1);
        return settleEndDateForView.getTime();
    }

    /**
     * 格式化时间
     *
     * @param date
     * @return
     */
    private static String formatDate(Date date) {
        return DateUtil.format(date, DateUtil.FMT_DATE_1);
    }

    /**
     * 两个模块的marketing不能同步，将值转换成财务模块的marketingType
     *
     * @param list
     * @return
     */
    private static List<SettleGood.MarketingSettlement> convertMarketingType(
            List<TradeItem.MarketingSettlement> list) {
        if (list != null && !list.isEmpty()) {
            return list.stream()
                    .map(
                            item ->
                                    SettleGood.MarketingSettlement.builder()
                                            .marketingType(
                                                    SettleMarketingType.fromValue(
                                                            item.getMarketingType().toValue()))
                                            .splitPrice(item.getSplitPrice())
                                            .build())
                    .collect(Collectors.toList());
        } else {
            return null;
        }
    }

    /**
     * 两个模块的coupon不能同步，将值转换成财务模块的couponType
     *
     * @param list
     * @return
     */
    private static List<SettleGood.CouponSettlement> convertCouponType(
            List<TradeItem.CouponSettlement> list) {
        if (list != null && !list.isEmpty()) {
            return list.stream()
                    .map(
                            item ->
                                    SettleGood.CouponSettlement.builder()
                                            .couponCodeId(item.getCouponCodeId())
                                            .couponCode(item.getCouponCode())
                                            .couponType(
                                                    SettleCouponType.fromValue(
                                                            item.getCouponType().toValue()))
                                            .splitPrice(item.getSplitPrice())
                                            .reducePrice(item.getReducePrice())
                                            .build())
                    .collect(Collectors.toList());
        } else {
            return null;
        }
    }

    /**
     * 拉卡拉费率取值
     * @param trade
     * @return
     */
    private String getFeeRate(Trade trade){
        String feeRate = "0.21";
        //拉卡拉支付信息
        LklOrderTradeInfoVO lklOrderTradeInfoVO =  trade.getLklOrderTradeInfo();
        //支付业务类型：UPCARD-银行卡 SCPAY-扫码支付（支付宝微信） DCPAY-数币支付 ONLINE-线上支付(银联)
        String busiType = lklOrderTradeInfoVO.getBusiType();

        //busi_type为SCPAY时返回：UQRCODEPAY-银联、WECHAT-微信、ALIPAY-支付宝
        String payMode = lklOrderTradeInfoVO.getPayMode();

        //  busi_type为UPCARD时返回：00-借记卡,01-贷记卡,02-准贷记卡,03-预付卡
        //  busi_type为SCPAY时返回：02-微信零钱,03-支付宝花呗,04-支付宝钱包,99-未知
        String accType = lklOrderTradeInfoVO.getAccType();
        if("ONLINE_B2C".equals(busiType)){
            //网银b2c
            if("00".equals(accType)){
                //借记卡
                feeRate = feeRate325;
            } else if("01".equals(accType)){
                //贷记卡
                feeRate = feeRate326;
            }
        } else if("ONLINE_B2B".equals(busiType)){
            //网银b2b
            if("00".equals(accType)){
                //借记卡
                feeRate = feeRate323;
            } else if("01".equals(accType)){
                //贷记卡
                feeRate = feeRate324;
            }
        } else if("SCPAY".equals(busiType)){
            //扫码
            if("WECHAT".equals(payMode)){
                //微信
                feeRate = feeRate302;
            } else if("ALIPAY".equals(payMode)){
                //支付宝
                feeRate = feeRate302;
            } else if("UQRCODEPAY".equals(payMode)){
                //银联
                feeRate = feeRate302;
            }
        } else if ("UPCARD".equals(busiType) || "ONLINE_LKLAT".equals(busiType)) {
            //大额转账
            if("00".equals(accType)){
                //借记卡
                feeRate = feeRate322;
            }
        }
        return feeRate;
    }


    /**
     * 拉卡拉服务费封顶保底取值
     * @param trade
     * @return
     */
    private BigDecimal getLowUpPrice(Trade trade, BigDecimal amount){
        //拉卡拉支付信息
        LklOrderTradeInfoVO lklOrderTradeInfoVO =  trade.getLklOrderTradeInfo();
        //支付业务类型：UPCARD-银行卡 SCPAY-扫码支付（支付宝微信） DCPAY-数币支付 ONLINE-线上支付(银联)
        String busiType = lklOrderTradeInfoVO.getBusiType();

        //busi_type为SCPAY时返回：UQRCODEPAY-银联、WECHAT-微信、ALIPAY-支付宝
        String payMode = lklOrderTradeInfoVO.getPayMode();

        //  busi_type为UPCARD时返回：00-借记卡,01-贷记卡,02-准贷记卡,03-预付卡
        //  busi_type为SCPAY时返回：02-微信零钱,03-支付宝花呗,04-支付宝钱包,99-未知
        String accType = lklOrderTradeInfoVO.getAccType();
        if("ONLINE_B2C".equals(busiType)){
            //网银b2c
            if(amount.compareTo(new BigDecimal(999999)) > 0){
                amount = BigDecimal.valueOf(999999);
            }
        } else if("ONLINE_B2B".equals(busiType)){
            //网银b2b
            if(amount.compareTo(new BigDecimal(10)) != 0 ){
                amount = BigDecimal.valueOf(10);
            }
        } else if("SCPAY".equals(busiType)){
            //扫码
//            if("WECHAT".equals(payMode)){
//                //微信
//            } else if("ALIPAY".equals(payMode)){
//                //支付宝
//            } else if("UQRCODEPAY".equals(payMode)){
//                //银联
//            }
        } else if ("UPCARD".equals(busiType)) {
            //大额转账
            if(amount.compareTo(new BigDecimal(18)) > 0 ){
                amount = new BigDecimal(18);
            }
//            if("00".equals(accType)){
//                //借记卡
//                if(amount.compareTo(new BigDecimal(20)) > 0 ){
//                    amount = BigDecimal.valueOf(20);
//                }
//            }
//            else if("01".equals(accType)){
//                //贷记卡
//
//            }
        }
        return amount;
    }

}
