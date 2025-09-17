package com.wanmi.sbc.empower.pay.service.lakala;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.account.api.provider.ledgerfunds.LedgerFundsProvider;
import com.wanmi.sbc.account.api.request.ledgerfunds.LedgerFundsAmountRequest;
import com.wanmi.sbc.account.bean.enums.LakalaLedgerStatus;
import com.wanmi.sbc.account.bean.enums.PayOrderStatus;
import com.wanmi.sbc.account.bean.vo.LakalaSettleGoodVO;
import com.wanmi.sbc.account.bean.vo.SettleGoodVO;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.constant.RedisKeyConstant;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.*;
import com.wanmi.sbc.customer.api.provider.ledgeraccount.LedgerAccountQueryProvider;
import com.wanmi.sbc.customer.api.provider.ledgerreceiverrel.LedgerReceiverRelQueryProvider;
import com.wanmi.sbc.customer.api.request.ledgeraccount.QueryByBusinessIdsRequest;
import com.wanmi.sbc.customer.api.request.ledgerreceiverrel.LedgerReceiverRelListRequest;
import com.wanmi.sbc.customer.api.response.ledgerreceiverrel.LedgerReceiverRelListResponse;
import com.wanmi.sbc.customer.bean.vo.LedgerAccountVO;
import com.wanmi.sbc.empower.api.request.pay.lakala.LakalaShareProfitBaseRequest;
import com.wanmi.sbc.empower.api.request.pay.lakala.LakalaShareProfitQueryRequest;
import com.wanmi.sbc.empower.api.request.pay.lakala.LakalaShareProfitRequest;
import com.wanmi.sbc.empower.api.request.settlement.SettlementRequest;
import com.wanmi.sbc.empower.api.response.pay.lakala.LakalaShareProfitQueryResponse;
import com.wanmi.sbc.empower.api.response.pay.lakala.LakalaShareProfitResponse;
import com.wanmi.sbc.empower.bean.enums.EmpowerErrorCodeEnum;
import com.wanmi.sbc.empower.bean.vo.PayGatewayVO;
import com.wanmi.sbc.empower.legder.lakala.LakalaUtils;
import com.wanmi.sbc.empower.pay.model.root.PayGateway;
import com.wanmi.sbc.order.api.provider.distribution.DistributionTaskTempProvider;
import com.wanmi.sbc.order.api.provider.payorder.PayOrderQueryProvider;
import com.wanmi.sbc.order.api.provider.paytraderecord.PayTradeRecordQueryProvider;
import com.wanmi.sbc.order.api.provider.settlement.SettlementDetailProvider;
import com.wanmi.sbc.order.api.provider.settlement.SettlementDetailQueryProvider;
import com.wanmi.sbc.order.api.provider.trade.TradeQueryProvider;
import com.wanmi.sbc.order.api.request.distribution.DistributionLedgerRequest;
import com.wanmi.sbc.order.api.request.distribution.DistributionTaskTempLedgerRequest;
import com.wanmi.sbc.order.api.request.payorder.FindByOrderNosRequest;
import com.wanmi.sbc.order.api.request.paytraderecord.TradeNoByBusinessIdRequest;
import com.wanmi.sbc.order.api.request.settlement.BatchUpdateSettlementDetailStatus;
import com.wanmi.sbc.order.api.request.settlement.LakalaSettlementDetailByIdReq;
import com.wanmi.sbc.order.api.request.settlement.SettlementDetailListBySettleUuidsRequest;
import com.wanmi.sbc.order.bean.dto.TradeDistributeItemCommissionDTO;
import com.wanmi.sbc.order.bean.vo.LakalaSettleTradeVO;
import com.wanmi.sbc.order.bean.vo.LakalaSettlementDetailVO;
import com.wanmi.sbc.order.bean.vo.PayOrderVO;
import com.wanmi.sbc.order.bean.vo.TradeCommissionVO;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author edz
 * @className LakalaSeporcancelService
 * @description 拉卡拉结算业务
 * @date 2022/6/29 19:35
 */
@Service
@Slf4j
public class LakalaSeporcancelService {

    @Value("${lakala.pay.seporcancel}")
    private String seporcancel;

    @Value("${lakala.pay.seporcancelQuery}")
    private String seporcancelQuery;

//    @Value("${lakala.openApi.feeRate}")
//    private String feeRate;

    @Autowired private PayOrderQueryProvider payOrderQueryProvider;

    @Autowired private LedgerAccountQueryProvider ledgerAccountQueryProvider;

    @Autowired private SettlementDetailProvider settlementDetailProvider;

    @Autowired private PayTradeRecordQueryProvider tradeRecordQueryProvider;

    @Autowired private LedgerFundsProvider ledgerFundsProvider;

    @Autowired private DistributionTaskTempProvider distributionTaskTempProvider;

    @Autowired private LedgerReceiverRelQueryProvider ledgerReceiverRelQueryProvider;

    @Autowired private SettlementDetailQueryProvider settlementDetailQueryProvider;

    @Autowired private TradeQueryProvider tradeQueryProvider;

    /**
     * @description 拉卡拉分账接口
     * @author edz
     * @date: 2022/7/30 16:52
     * @param lakalaShareProfitRequest
     * @return
     *     com.wanmi.sbc.common.base.BaseResponse<com.wanmi.sbc.empower.api.response.pay.lakala.LakalaShareProfitResponse>
     */
    private BaseResponse<LakalaShareProfitResponse> seporcancel(
            LakalaShareProfitRequest lakalaShareProfitRequest) {
        log.info(
                "拉卡拉分账接口入参lakalaShareProfitRequest：{}",
                JSON.toJSONString(lakalaShareProfitRequest));
        LakalaShareProfitBaseRequest lakalaShareProfitBaseRequest =
                new LakalaShareProfitBaseRequest();
        lakalaShareProfitBaseRequest.setReqTime(
                DateUtil.format(LocalDateTime.now(), DateUtil.FMT_TIME_3));
        lakalaShareProfitBaseRequest.setReqData(lakalaShareProfitRequest);
        String body = JSON.toJSONString(lakalaShareProfitBaseRequest);
        LakalaUtils.getPayGatewayIgnoreSwitch();
        String authorization = LakalaUtils.getAuthorization(body);
        return LakalaUtils.post(seporcancel, body, authorization, LakalaShareProfitResponse.class);
    }

    /**
     * @description 拉卡拉分账查询接口
     * @author edz
     * @date: 2022/7/30 16:52
     * @param queryRequest
     * @return
     *     com.wanmi.sbc.common.base.BaseResponse<com.wanmi.sbc.empower.api.response.pay.lakala.LakalaShareProfitQueryResponse>
     */
    public BaseResponse<LakalaShareProfitQueryResponse> seporcancelQuery(
            LakalaShareProfitQueryRequest queryRequest) {
        log.info("拉卡拉分账查询接口入参LakalaShareProfitQueryRequest：{}", JSON.toJSONString(queryRequest));
        PayGateway payGateway = LakalaUtils.getPayGatewayIgnoreSwitch();
        queryRequest.setPayInstId(payGateway.getConfig().getAccount());
        LakalaShareProfitBaseRequest lakalaShareProfitBaseRequest =
                new LakalaShareProfitBaseRequest();
        lakalaShareProfitBaseRequest.setReqTime(
                DateUtil.format(LocalDateTime.now(), DateUtil.FMT_TIME_3));
        lakalaShareProfitBaseRequest.setReqData(queryRequest);
        String body = JSON.toJSONString(lakalaShareProfitBaseRequest);
        String authorization = LakalaUtils.getAuthorization(body);
        return LakalaUtils.post(
                seporcancelQuery, body, authorization, LakalaShareProfitQueryResponse.class);
    }

    /**
     * @description 拉卡拉异步分账
     * @author edz
     * @date: 2022/7/30 16:52
     * @param settlementRequest
     * @return void
     */
    @Async
    public void asyncMode(SettlementRequest settlementRequest) {
        LakalaSeporcancelService lakalaSeporcancelService = SpringContextHolder.getBean(LakalaSeporcancelService.class);
        lakalaSeporcancelService.syncMode(settlementRequest);
    }

    /**
     * @description 拉卡拉同步分账
     * @author edz
     * @date: 2022/7/30 16:53
     * @param settlementRequest
     * @return void
     */
    @Transactional
    @GlobalTransactional
    public void syncMode(SettlementRequest settlementRequest) {
        int pageNum = 0;
        while (true) {
            SettlementDetailListBySettleUuidsRequest request =
                    new SettlementDetailListBySettleUuidsRequest();
            request.setSettleUuids(settlementRequest.getUuids());
            request.setIds(settlementRequest.getIds());
            request.setLakalaLedgerStatus(Arrays.asList(LakalaLedgerStatus.FAIL, LakalaLedgerStatus.INSUFFICIENT_AMOUNT));
            request.setPageNum(pageNum);
            request.setPageSize(1000);
            MicroServicePage<LakalaSettlementDetailVO> page =
                    settlementDetailQueryProvider
                            .getLakalaSettlementDetailsPageByUuid(request)
                            .getContext();
            this.seporcancel(page.getContent(), settlementRequest.getType());
            if (page.isLast()) break;
            pageNum = pageNum + 1;
        }
    }

    /**
     * @description 分账参数组装
     * @author edz
     * @date: 2022/7/30 16:53
     * @param list
     * @return void
     */
    private void seporcancel(List<LakalaSettlementDetailVO> list, int type) {
        List<String> tids =
                list.stream()
                        .map(item -> item.getSettleTrade().getTradeCode())
                        .collect(Collectors.toList());
        List<PayOrderVO> orders =
                payOrderQueryProvider
                        .findByOrderNos(
                                FindByOrderNosRequest.builder()
                                        .orderNos(tids)
                                        .payOrderStatus(PayOrderStatus.PAYED)
                                        .queryReceivable(DefaultFlag.YES)
                                        .build())
                        .getContext()
                        .getOrders();
        Map<String, PayOrderVO> tidToPayOrder =
                orders.stream()
                        .collect(Collectors.toMap(PayOrderVO::getOrderCode, Function.identity()));

        List<String> parentIds =
                list.stream()
                        .map(item -> item.getSettleTrade().getParentId())
                        .collect(Collectors.toList());
        Map<String, String> tidToTradeNo = new HashMap<>();
        if (CollectionUtils.isNotEmpty(parentIds)) {
            Map<String, String> parentIdToTradeNoMap =
                    tradeRecordQueryProvider
                            .queryTradeNoMapByBusinessIds(
                                    TradeNoByBusinessIdRequest.builder()
                                            .businessIdList(parentIds)
                                            .build())
                            .getContext()
                            .getTradeNoMap();
            tidToTradeNo.putAll(parentIdToTradeNoMap);
        }

        Map<String, String> tradeCodeToTradeNoMap =
                tradeRecordQueryProvider
                        .queryTradeNoMapByBusinessIds(
                                TradeNoByBusinessIdRequest.builder()
                                        .businessIdList(tids)
                                        .build())
                        .getContext()
                        .getTradeNoMap();

        tidToTradeNo.putAll(tradeCodeToTradeNoMap);

        // 店铺
        List<String> businessIds =
                list.stream()
                        .map(item -> item.getSettleTrade().getSupplierId().toString())
                        .collect(Collectors.toList());

        // 供应商
        Set<String> providerCompanyInfoIds =
                list.stream()
                        .flatMap(
                                lakalaSettlementDetailVO ->
                                        lakalaSettlementDetailVO.getSettleGoodList().stream())
                        .map(LakalaSettleGoodVO::getProviderCompanyInfoId)
                        .collect(Collectors.toSet());
        businessIds.addAll(providerCompanyInfoIds);

        // 平台
        businessIds.add("-1");

        // 分销员
        Set<String> distributorCustomerIds =
                list.stream()
                        .flatMap(
                                lakalaSettlementDetailVO ->
                                        lakalaSettlementDetailVO.getSettleGoodList().stream())
                        .map(LakalaSettleGoodVO::getDistributorCustomerId)
                        .collect(Collectors.toSet());
        businessIds.addAll(distributorCustomerIds);

        // 佣金提成人
        Set<String> commissionCustomerIds =
                list.stream()
                        .flatMap(
                                lakalaSettlementDetailVO ->
                                        lakalaSettlementDetailVO
                                                .getSettleTrade()
                                                .getCommissions()
                                                .stream())
                        .map(TradeCommissionVO::getCustomerId)
                        .collect(Collectors.toSet());
        businessIds.addAll(commissionCustomerIds);

        // 分账配置信息
        Map<String, LedgerAccountVO> businessIdToLedgerAccountVOMap =
                ledgerAccountQueryProvider
                        .findByBusinessIds(
                                QueryByBusinessIdsRequest.builder()
                                        .businessIds(businessIds)
                                        .build())
                        .getContext()
                        .getBusinessIdToLedgerAccountVOMap();

        PayGatewayVO payGateway = LakalaUtils.getPayGatewayVO();

        List<String> insufficientAmount = new ArrayList<>();
        List<String> processing = new ArrayList<>();
        List<String> success = new ArrayList<>();
        List<String> fail = new ArrayList<>();
        Map<String, String> lakalaSettlementDetailIdTosepTranSidMap = new HashMap<>();
        //
        for (LakalaSettlementDetailVO lakalaSettlementDetailVO : list) {
            LakalaSettleTradeVO tradeVO = lakalaSettlementDetailVO.getSettleTrade();
            LakalaShareProfitRequest request = new LakalaShareProfitRequest();
            request.setSepTranSid(UUIDUtil.getUUID());
            log.info("拉卡拉分账流水号：{}, 订单ID：{}", request.getSepTranSid(), tradeVO.getTradeCode());
            PayOrderVO payOrderVO = tidToPayOrder.get(tradeVO.getTradeCode());
            String tradeNo = "";
            if (tidToTradeNo.containsKey(tradeVO.getParentId())) {
                tradeNo = tidToTradeNo.get(tradeVO.getParentId());
            } else {
                tradeNo = tidToTradeNo.get(tradeVO.getTradeCode());
            }
            log.info("分账-拉卡拉支付流水号tradeNo:{}", tradeNo);
            request.setPaySerial(tradeNo);
            request.setOrderNo(tradeVO.getTradeCode());
            request.setLogdat(DateUtil.format(payOrderVO.getCreateTime(), DateUtil.FMT_TIME_5));
            if(Objects.nonNull(payOrderVO.getReceivable())){
                request.setLogdat(DateUtil.format(payOrderVO.getReceivable().getCreateTime(), DateUtil.FMT_TIME_5));
            }
            request.setLogNo(payOrderVO.getPayOrderNo());

            if (payGateway != null) {
                request.setPayInstId(payGateway.getConfig().getAccount());
            } else {
                log.error("分账-拉卡拉机构号获取失败");
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
            }

            if (businessIdToLedgerAccountVOMap.get(tradeVO.getSupplierId().toString()) != null) {
                LedgerAccountVO ledgerAccountVO =
                        businessIdToLedgerAccountVOMap.get(tradeVO.getSupplierId().toString());
                request.setPayMerNo(ledgerAccountVO.getThirdMemNo());
                request.setUnionpayMerNo(ledgerAccountVO.getMerCupNo());
                request.setTermId(ledgerAccountVO.getTermNo());
                if(StringUtils.isNotBlank(tradeVO.getTermNo())){
                    request.setTermId(tradeVO.getTermNo());
                }
                if(StringUtils.isNotBlank(tradeVO.getMerchantNo())){
                    request.setUnionpayMerNo(tradeVO.getMerchantNo());
                }
            } else {
                log.error("分账商家-拉卡拉LedgerAccountVO：{}配置为空", tradeVO.getSupplierId());
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
            }
            // 支付时间
            request.setPayTime(DateUtil.format(tradeVO.getTradePayTime(), DateUtil.FMT_TIME_3));
            BigDecimal splitPayPrice =
                    lakalaSettlementDetailVO.getSettleGoodList().stream()
                            .map(SettleGoodVO::getSplitPayPrice)
                            .reduce(BigDecimal::add)
                            .orElse(BigDecimal.ZERO)
                            .add(tradeVO.getDeliveryPrice() != null ? tradeVO.getDeliveryPrice() : BigDecimal.ZERO)
                            .add(tradeVO.getThirdPlatFormFreight() != null ? tradeVO.getThirdPlatFormFreight() :
                    BigDecimal.ZERO);
            // 总支付金额 是下单时的支付金额，不是退货后的金额
            String payAmt = payOrderVO.getPayOrderPrice().multiply(new BigDecimal("100")).toString();
            if(payAmt.indexOf(".") > -1){
                payAmt = payAmt.substring(0, payAmt.indexOf("."));
            }
            request.setPayAmt(payAmt);

            BigDecimal totalProviderPrice =
                    lakalaSettlementDetailVO.getSettleTrade().getTotalProviderPrice();
            BigDecimal totalPlatformPrice =
                    lakalaSettlementDetailVO.getSettleTrade().getTotalPlatformPrice();
            BigDecimal totalCommissionPrice =
                    lakalaSettlementDetailVO.getSettleTrade().getTotalCommissionPrice();
            BigDecimal storePrice = lakalaSettlementDetailVO.getSettleTrade().getStorePrice();

            BigDecimal total =
                    totalProviderPrice
                            .add(totalPlatformPrice)
                            .add(totalCommissionPrice)
                            .add(storePrice);
            if (BigDecimal.ZERO.compareTo(total) == 0){
                success.add(lakalaSettlementDetailVO.getId());
                continue;
            } else if (total.compareTo(splitPayPrice) > 0) { // 分账金额比实付金额大。直接分账失败
                log.info("分账金额比实付金额大,直接分账失败.分账金额：{},支付金额：{}", total, splitPayPrice);
                insufficientAmount.add(lakalaSettlementDetailVO.getId());
                continue;
            } else if (storePrice.compareTo(new BigDecimal("0")) <= 0 || total.multiply(new BigDecimal("0.1")).compareTo(storePrice) > 0){
                log.info("店铺分账金额不符合要求。店铺分账金额:{}, 商家最低分账金额:{}", storePrice, total.multiply(new BigDecimal("0.1")));
                insufficientAmount.add(lakalaSettlementDetailVO.getId());
                continue;
            } else if(LakalaLedgerStatus.INSUFFICIENT_AMOUNT.equals(lakalaSettlementDetailVO.getLakalaLedgerStatus())){
                log.info("预售订单：{}", lakalaSettlementDetailVO);
                insufficientAmount.add(lakalaSettlementDetailVO.getId());
                continue;
            }

            // 分账总金额 拉卡拉计算方式：四舍五入
            String sepAmt = total.multiply(new BigDecimal("100")).toString();
            if(sepAmt.indexOf(".") > -1){
                sepAmt = sepAmt.substring(0, sepAmt.indexOf("."));
            }
            request.setSepAmt(sepAmt);
            List<LakalaShareProfitRequest.RecvData> recvDataList = new ArrayList<>();

            // 供应商金额
            if (totalProviderPrice.compareTo(BigDecimal.ZERO) > 0) {
                String providerCompanyInfoId =
                        lakalaSettlementDetailVO
                                .getSettleGoodList()
                                .get(0)
                                .getProviderCompanyInfoId();
                if (businessIdToLedgerAccountVOMap.get(providerCompanyInfoId) != null) {
                    LedgerAccountVO ledgerAccountVO =
                            businessIdToLedgerAccountVOMap.get(providerCompanyInfoId);
                    LakalaShareProfitRequest.RecvData recvData =
                            new LakalaShareProfitRequest.RecvData();
                    recvData.setRecvMerId(ledgerAccountVO.getThirdMemNo());
                    String sepValue = totalProviderPrice
                            .multiply(new BigDecimal("100"))
                            .setScale(0, RoundingMode.HALF_UP)
                            .toString();
                    if(sepValue.indexOf(".") > -1){
                        sepValue = sepValue.substring(0, sepValue.indexOf("."));
                    }
                    recvData.setSepValue(sepValue);
                    recvDataList.add(recvData);
                } else {
                    log.error("分账供应商-拉卡拉LedgerAccountVO：{}配置为空", providerCompanyInfoId);
                    throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
                }
            }

            // 平台金额
            if (totalPlatformPrice.compareTo(BigDecimal.ZERO) > 0) {
                LakalaShareProfitRequest.RecvData recvData =
                        new LakalaShareProfitRequest.RecvData();
                if (businessIdToLedgerAccountVOMap.get("-1") != null) {
                    recvData.setRecvMerId(businessIdToLedgerAccountVOMap.get("-1").getThirdMemNo());
                    String sepValue = totalPlatformPrice
                            .multiply(new BigDecimal("100"))
                            .setScale(0, RoundingMode.HALF_UP)
                            .toString();
                    if(sepValue.indexOf(".") > -1){
                        sepValue = sepValue.substring(0, sepValue.indexOf("."));
                    }
                    recvData.setSepValue(sepValue);
                    recvDataList.add(recvData);
                } else {
                    log.error("分账boss-拉卡拉LedgerAccountVO配置为空");
                    throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
                }
            }

            // 佣金返利
            if (totalCommissionPrice.compareTo(BigDecimal.ZERO) > 0) {
                String customerId =
                        lakalaSettlementDetailVO
                                .getSettleGoodList()
                                .get(0)
                                .getDistributorCustomerId();
                if (businessIdToLedgerAccountVOMap.get(customerId) != null) {
                    LedgerAccountVO ledgerAccountVO =
                            businessIdToLedgerAccountVOMap.get(customerId);
                    LedgerReceiverRelListResponse ledgerReceiverRelListResponse =
                            ledgerReceiverRelQueryProvider
                                    .list(
                                            LedgerReceiverRelListRequest.builder()
                                                    .receiverId(ledgerAccountVO.getBusinessId())
                                                    .supplierId(tradeVO.getSupplierId())
                                                    .build())
                                    .getContext();
                    if (CollectionUtils.isNotEmpty(
                                    ledgerReceiverRelListResponse.getLedgerReceiverRelVOList())
                            && ledgerReceiverRelListResponse
                                            .getLedgerReceiverRelVOList()
                                            .get(0)
                                            .getBindState()
                                    == 2) {
                        LakalaShareProfitRequest.RecvData recvData =
                                new LakalaShareProfitRequest.RecvData();
                        recvData.setRecvMerId(ledgerAccountVO.getThirdMemNo());
                        String sepValue = tradeVO.getCommission()
                                .multiply(new BigDecimal("100"))
                                .setScale(0, RoundingMode.HALF_UP)
                                .toString();
                        if(sepValue.indexOf(".") > -1){
                            sepValue = sepValue.substring(0, sepValue.indexOf("."));
                        }
                        recvData.setSepValue(sepValue);
                        recvDataList.add(recvData);
                    } else {
                        log.warn("分账-分销未绑定。分销员会员ID:{}", customerId);
                        this.updateCommission(lakalaSettlementDetailVO);
                    }
                } else {
                    log.warn("分账-分销未进件。分销员会员ID:{}", customerId);
                    this.updateCommission(lakalaSettlementDetailVO);
                }
            }

            // 佣金提成
            if (CollectionUtils.isNotEmpty(tradeVO.getCommissions())) {
                tradeVO.getCommissions()
                        .forEach(
                                tradeCommissionVO -> {
                                    String customerId = tradeCommissionVO.getCustomerId();
                                    if (businessIdToLedgerAccountVOMap.get(customerId) != null) {
                                        LedgerAccountVO ledgerAccountVO =
                                                businessIdToLedgerAccountVOMap.get(customerId);
                                        // 分销员分账判断分销员是否进件成功
                                        if (Objects.nonNull(ledgerAccountVO)) {
                                            LedgerReceiverRelListResponse
                                                    ledgerReceiverRelListResponse =
                                                            ledgerReceiverRelQueryProvider
                                                                    .list(
                                                                            LedgerReceiverRelListRequest
                                                                                    .builder()
                                                                                    .receiverId(
                                                                                            ledgerAccountVO
                                                                                                    .getBusinessId())
                                                                                    .supplierId(
                                                                                            tradeVO
                                                                                                    .getSupplierId())
                                                                                    .build())
                                                                    .getContext();
                                            // 分销员是否绑定分账关系
                                            if (CollectionUtils.isNotEmpty(
                                                            ledgerReceiverRelListResponse
                                                                    .getLedgerReceiverRelVOList())
                                                    && ledgerReceiverRelListResponse
                                                                    .getLedgerReceiverRelVOList()
                                                                    .get(0)
                                                                    .getBindState()
                                                            == 2) {
                                                LakalaShareProfitRequest.RecvData recvData =
                                                        new LakalaShareProfitRequest.RecvData();
                                                recvData.setRecvMerId(
                                                        ledgerAccountVO.getThirdMemNo());
                                                String sepValue = tradeCommissionVO.getCommission()
                                                        .multiply(new BigDecimal("100"))
                                                        .setScale(0, RoundingMode.HALF_UP)
                                                        .toString();
                                                if(sepValue.indexOf(".") > -1){
                                                    sepValue = sepValue.substring(0, sepValue.indexOf("."));
                                                }
                                                recvData.setSepValue(sepValue);
                                                recvDataList.add(recvData);
                                            } else {
                                                // 分销员未绑定分账关系。重置分销金额为0
                                                this.updateCommission(lakalaSettlementDetailVO);
                                                log.warn("分账-分销未绑定。分销员会员ID:{}", customerId);
                                            }
                                        } else {
                                            // 分销云为进件。重置分销金额为0
                                            this.updateCommission(lakalaSettlementDetailVO);
                                            log.warn("分账-分销未进件。分销员会员ID:{}", customerId);
                                        }
                                    }
                                });
            }

            // 店铺金额
            storePrice =
                    storePrice.multiply(new BigDecimal("100"));
            // 拉卡拉的手续费由商家承担
            if (storePrice.compareTo(BigDecimal.ZERO) > 0) {
                String id =
                        lakalaSettlementDetailVO.getSettleTrade().getSupplierId().toString();
                LedgerAccountVO ledgerAccountVO = businessIdToLedgerAccountVOMap.get(id);
                LakalaShareProfitRequest.RecvData recvData =
                        new LakalaShareProfitRequest.RecvData();
                recvData.setRecvMerId(ledgerAccountVO.getMerCupNo());
                recvData.setRevcTermId(ledgerAccountVO.getTermNo());
                recvData.setSepValue(storePrice.toString().substring(0, storePrice.toString().indexOf(".")));
                recvDataList.add(recvData);
            } else {
                insufficientAmount.add(lakalaSettlementDetailVO.getId());
                log.error(
                        "分账-失败，商家不足支付手续费。结算单uuid:{}", lakalaSettlementDetailVO.getSettleUuid());
            }
            request.setRecvDatas(recvDataList);
            request.setSepTime(DateUtil.format(LocalDateTime.now(), DateUtil.FMT_TIME_3));
            request.setRetUrl(
                    payGateway.getConfig().getBossBackUrl().concat("/tradeCallback/seporcancel"));
            log.info("拉卡拉分账接口入参：{}", request);

            BaseResponse<LakalaShareProfitResponse> lakalaShareProfitResponseBaseResponse = this.seporcancel(request);

            if (CommonErrorCodeEnum.K000000.getCode().equals(
                    lakalaShareProfitResponseBaseResponse.getCode())) {
                LakalaShareProfitResponse response =
                        lakalaShareProfitResponseBaseResponse.getContext();
                if ("0000".equals(response.getResCode())) {
                    lakalaSettlementDetailIdTosepTranSidMap.put(
                            lakalaSettlementDetailVO.getId(), request.getSepTranSid());
                    switch (response.getStatus()) {
                        case "1":
                            success.add(lakalaSettlementDetailVO.getId());
                            this.updateWithdrawnAmount(lakalaSettlementDetailVO);
                            if (tradeVO.getCommission() != null) {
                                DistributionLedgerRequest distributionLedgerRequest =
                                        DistributionLedgerRequest.builder()
                                                .commission(tradeVO.getCommission())
                                                .commissions(
                                                        KsBeanUtil.convertList(
                                                                tradeVO.getCommissions(),
                                                                TradeDistributeItemCommissionDTO
                                                                        .class))
                                                .orderId(tradeVO.getTradeCode())
                                                .build();
                                distributionTaskTempProvider.updateForLedger(
                                        DistributionTaskTempLedgerRequest.builder()
                                                .orderId(tradeVO.getTradeCode())
                                                .ledgerTime(LocalDateTime.now().plusDays(Constants.ONE))
                                                .params(
                                                        JSON.toJSONString(
                                                                distributionLedgerRequest))
                                                .build());
                            }
                            break;
                        case "2":
                            processing.add(lakalaSettlementDetailVO.getId());
                            break;
                        case "3":
                            fail.add(lakalaSettlementDetailVO.getId());
                            break;
                        default:
                            break;
                    }
                } else if("LFPS2004".equals(response.getResCode())){ // 分账交易记录已经存在，不允许再次分账
                    processing.add(lakalaSettlementDetailVO.getId());
                }  else {
                    log.error("拉卡拉分账接口失败:{}", response.getResDesc());
                    // 0：手动分账。分账失败抛出异常 1：自动批量分账，只记录错误日志，不抛异常
                    if (type == Constants.ZERO) {
                        throw new SbcRuntimeException(EmpowerErrorCodeEnum.K060030);
                    }
                }
            } else {
                fail.add(lakalaSettlementDetailVO.getId());
                log.error("拉卡拉分账接口失败:{}", lakalaShareProfitResponseBaseResponse.getMessage());
                // 0：手动分账。分账失败抛出异常 1：自动批量分账，只记录错误日志，不抛异常
                if (type == Constants.ZERO) {
                    throw new SbcRuntimeException(EmpowerErrorCodeEnum.K060030);
                }
            }
        }
        settlementDetailProvider.batchUpdateLakalaSettlementDetailStatus(
                BatchUpdateSettlementDetailStatus.builder()
                        .success(success)
                        .fail(fail)
                        .processing(processing)
                        .insufficientAmount(insufficientAmount)
                        .lakalaSettlementDetailIdTosepTranSidMap(
                                lakalaSettlementDetailIdTosepTranSidMap)
                        .build());
    }

    @Data
    class SettlementUuidItem {
        private int total;
        private int successCount;
        private int processingCount;
        private int failCount;
    }

    /**
     * @description 修改待提现金额
     * @author edz
     * @date: 2022/8/1 15:57
     * @param lakalaSettlementDetailVO
     * @return void
     */
    private void updateWithdrawnAmount(LakalaSettlementDetailVO lakalaSettlementDetailVO) {
        List<LedgerFundsAmountRequest> ledgerFundsAmountRequests = new ArrayList<>();
        List<TradeCommissionVO> tradeCommissionVOS =
                lakalaSettlementDetailVO.getSettleTrade().getCommissions();
        if (CollectionUtils.isNotEmpty(tradeCommissionVOS)) {
            tradeCommissionVOS.forEach(
                    tradeCommissionVO -> {
                        ledgerFundsAmountRequests.add(
                                LedgerFundsAmountRequest.builder()
                                        .amount(tradeCommissionVO.getCommission())
                                        .customerId(tradeCommissionVO.getCustomerId())
                                        .build());
                    });
        }

        lakalaSettlementDetailVO
                .getSettleGoodList()
                .forEach(
                        lakalaSettleGoodVO -> {
                            if (StringUtils.isNotBlank(
                                    lakalaSettleGoodVO.getDistributorCustomerId())) {
                                ledgerFundsAmountRequests.add(
                                        LedgerFundsAmountRequest.builder()
                                                .amount(lakalaSettleGoodVO.getCommission())
                                                .customerId(
                                                        lakalaSettleGoodVO
                                                                .getDistributorCustomerId())
                                                .build());
                            }
                        });
        if (CollectionUtils.isNotEmpty(ledgerFundsAmountRequests)) {
            ledgerFundsAmountRequests.forEach(g -> ledgerFundsProvider.updateWithdrawnAmount(g));
        }
    }

    /**
     * @description 更新分销佣金为0
     * @author edz
     * @date: 2022/8/1 16:07
     * @param lakalaSettlementDetailVO
     * @return void
     */
    private void updateCommission(LakalaSettlementDetailVO lakalaSettlementDetailVO) {
        List<TradeCommissionVO> tradeCommissionVOS =
                lakalaSettlementDetailVO.getSettleTrade().getCommissions();
        if (CollectionUtils.isNotEmpty(tradeCommissionVOS)) {
            tradeCommissionVOS.forEach(
                    commissionVO -> {
                        commissionVO.setCommission(BigDecimal.ZERO);
                    });
        }

        lakalaSettlementDetailVO
                .getSettleGoodList()
                .forEach(
                        lakalaSettleGoodVO -> {
                            if (StringUtils.isNotBlank(
                                    lakalaSettleGoodVO.getDistributorCustomerId())) {
                                lakalaSettleGoodVO.setCommission(BigDecimal.ZERO);
                            }
                        });
    }

    /**
     * @description 全部分给商家
     * @author  edz
     * @date: 2022/10/15 16:59
     * @param LakalaSettlementDetailId
     * @return void
     */
    public void seporcancelForSupplier(String LakalaSettlementDetailId){
        LakalaSettlementDetailVO lakalaSettlementDetailVO =
                settlementDetailQueryProvider.LakalaSettlementDetailById(LakalaSettlementDetailByIdReq.builder()
                        .id(LakalaSettlementDetailId).build()).getContext().getSettlementDetailVO();
        if (Objects.nonNull(lakalaSettlementDetailVO)){
            LakalaSettleTradeVO tradeVO = lakalaSettlementDetailVO.getSettleTrade();
            tradeVO.setTotalProviderPrice(BigDecimal.ZERO);
            tradeVO.setTotalPlatformPrice(BigDecimal.ZERO);
            tradeVO.setTotalCommissionPrice(BigDecimal.ZERO);
            tradeVO.setCommissions(new ArrayList<>());
            BigDecimal splitPayPrice =
                    lakalaSettlementDetailVO.getSettleGoodList().stream()
                            .map(SettleGoodVO::getSplitPayPrice)
                            .reduce(BigDecimal::add)
                            .orElse(BigDecimal.ZERO)
                            .add(tradeVO.getDeliveryPrice() != null ? tradeVO.getDeliveryPrice() : BigDecimal.ZERO)
                            .add(tradeVO.getThirdPlatFormFreight() != null ? tradeVO.getThirdPlatFormFreight() :
                                    BigDecimal.ZERO);
            tradeVO.setStorePrice(splitPayPrice);

           // 分账
           this.seporcancel(new ArrayList<>(Collections.singletonList(lakalaSettlementDetailVO)),
                   Constants.ONE);
        }
    }
}
