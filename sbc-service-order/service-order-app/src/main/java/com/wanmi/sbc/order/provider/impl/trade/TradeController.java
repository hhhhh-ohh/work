package com.wanmi.sbc.order.provider.impl.trade;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.Platform;
import com.wanmi.sbc.common.enums.SellPlatformType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.GeneratorService;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.response.store.StoreInfoResponse;
import com.wanmi.sbc.order.api.provider.trade.TradeProvider;
import com.wanmi.sbc.order.api.request.trade.*;
import com.wanmi.sbc.order.api.response.trade.*;
import com.wanmi.sbc.order.bean.enums.OrderErrorCodeEnum;
import com.wanmi.sbc.order.bean.enums.PayCallBackType;
import com.wanmi.sbc.order.bean.enums.PayState;
import com.wanmi.sbc.order.bean.vo.PointsTradeCommitResultVO;
import com.wanmi.sbc.order.bean.vo.TradeCommitResultVO;
import com.wanmi.sbc.order.payingmemberrecordtemp.service.PayingMemberRecordTempService;
import com.wanmi.sbc.order.payorder.model.root.PayOrder;
import com.wanmi.sbc.order.receivables.request.ReceivableAddRequest;
import com.wanmi.sbc.order.returnorder.service.ReturnOrderService;
import com.wanmi.sbc.order.sellplatform.SellPlatformTradeService;
import com.wanmi.sbc.order.trade.model.entity.*;
import com.wanmi.sbc.order.trade.model.entity.value.Invoice;
import com.wanmi.sbc.order.trade.model.root.Trade;
import com.wanmi.sbc.order.trade.model.root.TradeGroup;
import com.wanmi.sbc.order.trade.request.TradePriceChangeRequest;
import com.wanmi.sbc.order.trade.request.TradeQueryRequest;
import com.wanmi.sbc.order.trade.request.TradeRemedyRequest;
import com.wanmi.sbc.order.trade.service.PayAndRefundCallBackService;
import com.wanmi.sbc.order.trade.service.TradeOptimizeService;
import com.wanmi.sbc.order.trade.service.TradeService;
import com.wanmi.sbc.setting.api.provider.AuditQueryProvider;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Author: ZhangLingKe
 * @Description:
 * @Date: 2018-12-04 10:04
 */
@Slf4j
@Validated
@RestController
public class TradeController implements TradeProvider {

    @Autowired
    private TradeService tradeService;

    @Autowired
    private GeneratorService generatorService;

    @Autowired
    private TradeOptimizeService tradeOptimizeService;

    @Autowired
    private PayAndRefundCallBackService payAndRefundCallBackService;

    @Autowired
    private AuditQueryProvider auditQueryProvider;

    @Autowired
    private SellPlatformTradeService sellPlatformTradeService;

    @Autowired
    private PayingMemberRecordTempService payingMemberRecordTempService;

    @Autowired
    private ReturnOrderService returnOrderService;


    /**
     * 批量新增交易单
     *
     * @param tradeAddBatchRequest 交易单集合 操作信息 {@link TradeAddBatchRequest}
     * @return 交易单提交结果集合 {@link TradeAddBatchResponse}
     */
    @Override
    public BaseResponse<TradeAddBatchResponse> addBatch(@RequestBody @Valid TradeAddBatchRequest tradeAddBatchRequest) {
        List<TradeCommitResult> trades =
                tradeService.createBatch(KsBeanUtil.convert(tradeAddBatchRequest.getTradeDTOList(), Trade.class),null,
                        tradeAddBatchRequest.getOperator());
        return BaseResponse.success(TradeAddBatchResponse.builder().tradeCommitResultVOS(KsBeanUtil.convert(trades,
                TradeCommitResultVO.class)).build());
    }

    /**
     * 代客下单批量新增交易单
     *
     * @param tradeAddBatchRequest 交易单集合 操作信息 {@link TradeAddBatchRequest}
     * @return 交易单提交结果集合 {@link TradeAddBatchResponse}
     */
    @Override
    public BaseResponse<TradeAddBatchResponse> supplierAddBatch(@RequestBody @Valid TradeAddBatchRequest tradeAddBatchRequest) {
        List<TradeCommitResult> trades =
                tradeService.supplierAddBatch(KsBeanUtil.convert(tradeAddBatchRequest.getTradeDTOList(), Trade.class),null,
                        tradeAddBatchRequest.getOperator(), tradeAddBatchRequest.getCustomerVO(),
                        tradeAddBatchRequest.getGoodsInfoIds());
        return BaseResponse.success(TradeAddBatchResponse.builder().tradeCommitResultVOS(KsBeanUtil.convert(trades,
                TradeCommitResultVO.class)).build());
    }

    /**
     * 批量分组新增交易单
     *
     * @param tradeAddBatchWithGroupRequest 交易单集合 分组信息 操作信息 {@link TradeAddBatchWithGroupRequest}
     * @return 交易单提交结果集合 {@link TradeAddBatchWithGroupResponse}
     */
    @Override
    public BaseResponse<TradeAddBatchWithGroupResponse> addBatchWithGroup(@RequestBody @Valid TradeAddBatchWithGroupRequest tradeAddBatchWithGroupRequest) {
        List<TradeCommitResult> trades = tradeService.createBatchWithGroup(
                KsBeanUtil.convert(tradeAddBatchWithGroupRequest.getTradeDTOList(), Trade.class),
                KsBeanUtil.convert(tradeAddBatchWithGroupRequest.getTradeGroup(), TradeGroup.class),
                tradeAddBatchWithGroupRequest.getOperator());
        return BaseResponse.success(TradeAddBatchWithGroupResponse.builder().tradeCommitResultVOS(KsBeanUtil.convert(trades,
                TradeCommitResultVO.class)).build());
    }

    /**
     * 订单改价
     *
     * @param tradeModifyPriceRequest 价格信息 交易单号 操作信息 {@link TradeModifyPriceRequest}
     * @return 操作结果 {@link BaseResponse}
     */
    @Override
    public BaseResponse modifyPrice(@RequestBody @Valid TradeModifyPriceRequest tradeModifyPriceRequest) {
        tradeService.changePrice(KsBeanUtil.convert(tradeModifyPriceRequest.getTradePriceChangeDTO(),
                TradePriceChangeRequest.class),
                tradeModifyPriceRequest.getTid(),
                tradeModifyPriceRequest.getOperator());
        return BaseResponse.SUCCESSFUL();
    }


    /**
     * C端提交订单
     *
     * @param tradeCommitRequest 提交订单请求对象
     * @return 提交订单结果
     */
    @Override
    public BaseResponse<TradeCommitResponse> commitFlashSale(@RequestBody @Valid TradeCommitRequest tradeCommitRequest) {
        TradeCommitResponse response = new TradeCommitResponse();
        try {
            List<TradeCommitResult> results = tradeOptimizeService.commitFlashSale(tradeCommitRequest);
            tradeService.commitSuccessDelayProcess(tradeCommitRequest);
            response.setTradeCommitResults(KsBeanUtil.convert(results, TradeCommitResultVO.class));
        } catch (Exception e) {
            tradeService.commitErrorDelayProcess(tradeCommitRequest);
            throw e;
        }
        return BaseResponse.success(response);
    }

    /**
     * C端提交尾款订单
     *
     * @param tradeCommitRequest 提交订单请求对象
     * @return 提交订单结果
     */
    @Override
    public BaseResponse<TradeCommitResponse> commitTail(@RequestBody @Valid TradeCommitRequest tradeCommitRequest) {
        TradeCommitResponse response = new TradeCommitResponse();
        try {
            List<TradeCommitResult> results = tradeService.commitTail(tradeCommitRequest);
            tradeService.commitSuccessDelayProcess(tradeCommitRequest);
            response.setTradeCommitResults(KsBeanUtil.convert(results, TradeCommitResultVO.class));
        } catch (Exception e) {
            tradeService.commitErrorDelayProcess(tradeCommitRequest);
            throw e;
        }
        return BaseResponse.success(response);

    }

    /**
     * 移动端提交积分商品订单
     *
     * @param pointsTradeCommitRequest 提交订单请求对象  {@link PointsTradeCommitRequest}
     * @return
     */
    @Override
    public BaseResponse<PointsTradeCommitResponse> pointsCommit(@RequestBody @Valid PointsTradeCommitRequest pointsTradeCommitRequest) {
        PointsTradeCommitResult result = tradeService.pointsCommit(pointsTradeCommitRequest);
        return BaseResponse.success(new PointsTradeCommitResponse(KsBeanUtil.convert(result,
                PointsTradeCommitResultVO.class)));
    }

    /**
     * 移动端提交积分优惠券订单
     *
     * @param pointsTradeCommitRequest 提交订单请求对象  {@link PointsTradeCommitRequest}
     * @return
     */
    @Override
    public BaseResponse<PointsTradeCommitResponse> pointsCouponCommit(@RequestBody @Valid PointsCouponTradeCommitRequest pointsTradeCommitRequest) {
        PointsTradeCommitResult result = tradeService.pointsCouponCommit(pointsTradeCommitRequest);
        return BaseResponse.success(new PointsTradeCommitResponse(KsBeanUtil.convert(result,
                PointsTradeCommitResultVO.class)));
    }

    /**
     * 修改订单
     *
     * @param tradeModifyRemedyRequest 店铺信息 交易单信息 操作信息 {@link TradeModifyRemedyRequest}
     * @return 操作结果 {@link BaseResponse}
     */
    @Override
    public BaseResponse remedy(@RequestBody @Valid TradeModifyRemedyRequest tradeModifyRemedyRequest) {
        tradeService.remedy(KsBeanUtil.convert(tradeModifyRemedyRequest.getTradeRemedyDTO(), TradeRemedyRequest.class),
                tradeModifyRemedyRequest.getOperator(),
                KsBeanUtil.convert(tradeModifyRemedyRequest.getStoreInfoDTO(), StoreInfoResponse.class),tradeModifyRemedyRequest.getIsOpen());
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 修改订单-部分修改
     *
     * @param tradeRemedyPartRequest 店铺信息 交易单信息 操作信息 {@link TradeRemedyPartRequest}
     * @return 操作结果 {@link BaseResponse}
     */
    @Override
    public BaseResponse remedyPart(@RequestBody @Valid TradeRemedyPartRequest tradeRemedyPartRequest) {
        tradeService.remedyPart(KsBeanUtil.convert(tradeRemedyPartRequest.getTradeRemedyDTO(),
                TradeRemedyRequest.class),
                tradeRemedyPartRequest.getOperator(),
                KsBeanUtil.convert(tradeRemedyPartRequest.getStoreInfoDTO(), StoreInfoResponse.class));
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 取消订单
     *
     * @param tradeCancelRequest 交易编号 操作信息 {@link TradeCancelRequest}
     * @return 操作结果 {@link BaseResponse}
     */
    @Override
    public BaseResponse cancel(@RequestBody @Valid TradeCancelRequest tradeCancelRequest) {
        tradeService.cancel(tradeCancelRequest.getTid(), tradeCancelRequest.getOperator());
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 订单审核
     *
     * @param tradeAuditRequest 订单审核相关必要信息 {@link TradeAuditRequest}
     * @return 操作结果 {@link BaseResponse}
     */
    @Override
    public BaseResponse audit(@RequestBody @Valid TradeAuditRequest tradeAuditRequest) {
        tradeService.audit(tradeAuditRequest.getTid(), tradeAuditRequest.getAuditState(), tradeAuditRequest.getReason(),
                tradeAuditRequest.getOperator());
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 订单回审
     *
     * @param tradeRetrialRequest 订单审核相关必要信息 {@link TradeRetrialRequest}
     * @return 操作结果 {@link BaseResponse}
     */
    @Override
    public BaseResponse retrial(@RequestBody @Valid TradeRetrialRequest tradeRetrialRequest) {
        tradeService.retrial(tradeRetrialRequest.getTid(), tradeRetrialRequest.getOperator());
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 订单批量审核
     *
     * @param tradeAuditBatchRequest 订单审核相关必要信息 {@link TradeAuditBatchRequest}
     * @return 操作结果 {@link BaseResponse}
     */
    @Override
    public BaseResponse auditBatch(@RequestBody @Valid TradeAuditBatchRequest tradeAuditBatchRequest) {
        tradeService.batchAudit(tradeAuditBatchRequest.getIds(), tradeAuditBatchRequest.getAuditState(),
                tradeAuditBatchRequest.getReason(),
                tradeAuditBatchRequest.getOperator());
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 修改卖家备注
     *
     * @param tradeRemedySellerRemarkRequest 订单修改相关必要信息 {@link TradeRemedySellerRemarkRequest}
     * @return 操作结果 {@link BaseResponse}
     */
    @Override
    public BaseResponse remedySellerRemark(@RequestBody @Valid TradeRemedySellerRemarkRequest tradeRemedySellerRemarkRequest) {
        tradeService.remedySellerRemark(tradeRemedySellerRemarkRequest.getTid(),
                tradeRemedySellerRemarkRequest.getSellerRemark(),
                tradeRemedySellerRemarkRequest.getOperator());
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 发货
     *
     * @param tradeDeliverRequest 物流信息 操作信息 {@link TradeDeliverRequest}
     * @return 物流编号 {@link TradeDeliverResponse}
     */
    @Override
    public BaseResponse<TradeDeliverResponse> deliver(@RequestBody @Valid TradeDeliverRequest tradeDeliverRequest) {
        String deliverId = tradeService.deliver(tradeDeliverRequest.getTid(),
                KsBeanUtil.convert(tradeDeliverRequest.getTradeDeliver(), TradeDeliver.class),
                tradeDeliverRequest.getOperator(), BoolFlag.NO, tradeDeliverRequest.getRemindShipping());
        return BaseResponse.success(TradeDeliverResponse.builder().deliverId(deliverId).build());
    }

    @Override
    public BaseResponse batchDeliver(@RequestBody @Valid TradeBatchDeliverRequest tradeBatchDeliverRequest) {
        if (CollectionUtils.isEmpty(tradeBatchDeliverRequest.getBatchDeliverDTOList())){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000011);
        }
        tradeOptimizeService.batchDeliver(tradeBatchDeliverRequest);
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 确认收货
     *
     * @param tradeConfirmReceiveRequest 订单编号 操作信息 {@link TradeConfirmReceiveRequest}
     * @return 操作结果 {@link BaseResponse}
     */
    @Override
    public BaseResponse confirmReceive(@RequestBody @Valid TradeConfirmReceiveRequest tradeConfirmReceiveRequest) {
        tradeService.confirmReceive(tradeConfirmReceiveRequest.getTid(), tradeConfirmReceiveRequest.getOperator());
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 退货 | 退款
     *
     * @param tradeReturnOrderRequest 订单编号 操作信息 {@link TradeReturnOrderRequest}
     * @return 操作结果 {@link BaseResponse}
     */
    @Override
    public BaseResponse returnOrder(@RequestBody @Valid TradeReturnOrderRequest tradeReturnOrderRequest) {
        tradeService.returnOrder(tradeReturnOrderRequest.getTid(), tradeReturnOrderRequest.getOperator());
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 作废订单
     *
     * @param tradeVoidedRequest 订单编号 操作信息 {@link TradeVoidedRequest}
     * @return 操作结果 {@link BaseResponse}
     */
    @Override
    public BaseResponse voided(@RequestBody @Valid TradeVoidedRequest tradeVoidedRequest) {
        tradeService.voidTrade(tradeVoidedRequest.getTid(), tradeVoidedRequest.getOperator());
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 退单作废后的订单状态扭转
     *
     * @param tradeReverseRequest 订单编号 操作信息 {@link TradeReverseRequest}
     * @return 操作结果 {@link BaseResponse}
     */
    @Override
    public BaseResponse reverse(@RequestBody @Valid TradeReverseRequest tradeReverseRequest) {
        tradeService.reverse(tradeReverseRequest.getTid(), tradeReverseRequest.getOperator(),
                tradeReverseRequest.getReturnType());
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 发货记录作废
     *
     * @param tradeDeliverRecordObsoleteRequest 订单编号 物流单号 操作信息 {@link TradeDeliverRecordObsoleteRequest}
     * @return 操作结果 {@link BaseResponse}
     */
    @Override
    public BaseResponse deliverRecordObsolete(@RequestBody @Valid TradeDeliverRecordObsoleteRequest tradeDeliverRecordObsoleteRequest) {
        tradeService.deliverRecordObsolete(tradeDeliverRecordObsoleteRequest.getTid(),
                tradeDeliverRecordObsoleteRequest.getDeliverId(),
                tradeDeliverRecordObsoleteRequest.getOperator());
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 保存发票信息
     *
     * @param tradeAddInvoiceRequest 订单编号 发票信息 {@link TradeAddInvoiceRequest}
     * @return 操作结果 {@link BaseResponse}
     */
    @Override
    public BaseResponse saveInvoice(@RequestBody @Valid TradeAddInvoiceRequest tradeAddInvoiceRequest) {
        tradeService.saveInvoice(tradeAddInvoiceRequest.getTid(),
                KsBeanUtil.convert(tradeAddInvoiceRequest.getInvoiceDTO(), Invoice.class));
        return BaseResponse.SUCCESSFUL();


    }

    /**
     * 支付作废
     *
     * @param tradePayRecordObsoleteRequest 订单编号 操作信息 {@link TradePayRecordObsoleteRequest}
     * @return 操作结果 {@link BaseResponse}
     */
    @Override
    public BaseResponse payRecordObsolete(@RequestBody @Valid TradePayRecordObsoleteRequest tradePayRecordObsoleteRequest) {
        tradeService.payRecordObsolete(tradePayRecordObsoleteRequest.getTid(),
                tradePayRecordObsoleteRequest.getOperator());
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 线上订单支付回调
     *
     * @param tradePayCallBackOnlineRequest 订单 支付单 操作信息 {@link TradePayCallBackOnlineRequest}
     * @return 操作结果 {@link BaseResponse}
     */
    @Override
    public BaseResponse payCallBackOnline(@RequestBody @Valid TradePayCallBackOnlineRequest tradePayCallBackOnlineRequest) {
        tradeService.payCallBackOnline(
                KsBeanUtil.convert(tradePayCallBackOnlineRequest.getTrade(), Trade.class),
                KsBeanUtil.convert(tradePayCallBackOnlineRequest.getPayOrderOld(), PayOrder.class),
                tradePayCallBackOnlineRequest.getOperator());
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse payCallBackOnlineBatch(@RequestBody @Valid TradePayCallBackOnlineBatchRequest tradePayCallBackOnlineBatchRequest) {
        if (StringUtils.isEmpty(tradePayCallBackOnlineBatchRequest.getRecordId()) && CollectionUtils.isNotEmpty(tradePayCallBackOnlineBatchRequest.getRequestList())) {
            List<PayCallBackOnlineBatch> request = tradePayCallBackOnlineBatchRequest.getRequestList().stream().map(i -> {
                PayCallBackOnlineBatch data = new PayCallBackOnlineBatch();
                data.setPayOrderOld(KsBeanUtil.convert(i.getPayOrderOld(), PayOrder.class));
                data.setTrade(KsBeanUtil.convert(i.getTrade(), Trade.class));
                return data;
            }).collect(Collectors.toList());
            tradeService.payCallBackOnlineBatch(request, tradePayCallBackOnlineBatchRequest.getOperator());
        } else {
            payingMemberRecordTempService.addForCallBack(tradePayCallBackOnlineBatchRequest.getRecordId());
        }
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 订单支付回调
     *
     * @param tradePayCallBackRequest 订单号 支付金额 操作信息 支付方式{@link TradePayCallBackRequest}
     * @return 操作结果 {@link BaseResponse}
     */
    @Override
    public BaseResponse payCallBack(@RequestBody @Valid TradePayCallBackRequest tradePayCallBackRequest) {
        tradeService.payCallBack(null,
                tradePayCallBackRequest.getTid(),
                tradePayCallBackRequest.getPayOrderPrice(),
                tradePayCallBackRequest.getOperator(),
                tradePayCallBackRequest.getPayWay());
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 0 元订单默认支付
     *
     * @param tradeDefaultPayRequest 订单号{@link TradeDefaultPayRequest}
     * @return 支付结果 {@link TradeDefaultPayResponse}
     */
    @Override
    public BaseResponse<TradeDefaultPayResponse> defaultPay(@RequestBody @Valid TradeDefaultPayRequest tradeDefaultPayRequest) {
        Trade trade = tradeService.detail(tradeDefaultPayRequest.getTid());
        if (Objects.isNull(trade)) {
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050010, new Object[]{tradeDefaultPayRequest.getTid()});
        }
        return BaseResponse.success(TradeDefaultPayResponse.builder()
                .payResult(tradeService.tradeDefaultPay(trade, tradeDefaultPayRequest.getPayWay())).build());
    }

    @Override
    public BaseResponse defaultPayBatch(@RequestBody @Valid TradeDefaultPayBatchRequest tradeDefaultPayBatchRequest) {
        List<Trade> trades = tradeService.details(tradeDefaultPayBatchRequest.getTradeIds());
        //请求包含不存在的订单
        if (trades.size() != tradeDefaultPayBatchRequest.getTradeIds().size()) {
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050010);
        }
        tradeService.tradeDefaultPayBatch(trades, tradeDefaultPayBatchRequest.getPayWay());
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 新增线下收款单(包含线上线下的收款单)
     *
     * @param tradeAddReceivableRequest 收款单平台信息{@link TradeAddReceivableRequest}
     * @return 支付结果 {@link TradeDefaultPayResponse}
     */
    @Override
    public BaseResponse addReceivable(@RequestBody @Valid TradeAddReceivableRequest tradeAddReceivableRequest) {
        tradeService.addReceivable(KsBeanUtil.convert(tradeAddReceivableRequest.getReceivableAddDTO(),
                ReceivableAddRequest.class), tradeAddReceivableRequest.getPlatform(),
                tradeAddReceivableRequest.getOperator());
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 确认支付单
     *
     * @param tradeConfirmPayOrderRequest 支付单id集合{@link TradeConfirmPayOrderRequest}
     * @return 支付单集合 {@link TradeConfirmPayOrderResponse}
     */
    @Override
    public BaseResponse confirmPayOrder(@RequestBody @Valid TradeConfirmPayOrderRequest tradeConfirmPayOrderRequest) {
        tradeService.confirmPayOrder(tradeConfirmPayOrderRequest.getPayOrderIds(),
                tradeConfirmPayOrderRequest.getOperator());
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 更新订单的结算状态
     *
     * @param tradeUpdateSettlementStatusRequest 店铺id 起始时间 {@link TradeUpdateSettlementStatusRequest}
     * @return 支付单集合 {@link TradeCountByPayStateResponse}
     */
    @Override
    public BaseResponse updateSettlementStatus(@RequestBody @Valid TradeUpdateSettlementStatusRequest tradeUpdateSettlementStatusRequest) {
        tradeService.updateSettlementStatus(tradeUpdateSettlementStatusRequest.getStoreId(),
                tradeUpdateSettlementStatusRequest.getStartTime()
                , tradeUpdateSettlementStatusRequest.getEndTime());
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 添加订单
     *
     * @param tradeAddRequest 订单信息 {@link TradeAddRequest}
     * @return 操作结果 {@link BaseResponse}
     */
    @Override
    public BaseResponse add(@RequestBody @Valid TradeAddRequest tradeAddRequest) {
        tradeService.addTrade(KsBeanUtil.convert(tradeAddRequest.getTrade(), Trade.class));
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 更新订单的业务员
     *
     * @param tradeUpdateEmployeeIdRequest 业务员信息 {@link TradeUpdateEmployeeIdRequest}
     * @return 操作结果 {@link BaseResponse}
     */
    @Override
    public BaseResponse updateEmployeeId(@RequestBody @Valid TradeUpdateEmployeeIdRequest tradeUpdateEmployeeIdRequest) {
        tradeService.updateEmployeeId(tradeUpdateEmployeeIdRequest.getEmployeeId(),
                tradeUpdateEmployeeIdRequest.getCustomerId());
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 更新返利标志
     *
     * @param request
     * @return
     */
    @Override
    public BaseResponse updateCommissionFlag(@RequestBody @Valid TradeUpdateCommissionFlagRequest request) {
        tradeService.updateCommissionFlag(request.getTradeId(), request.getCommissionFlag());
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 更新最终时间
     *
     * @param request
     * @return
     */
    @Override
    public BaseResponse updateFinalTime(@RequestBody @Valid TradeFinalTimeUpdateRequest request) {
        tradeService.updateFinalTime(request.getTid(), request.getOrderReturnTime());
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 更新正在进行的退单数量
     *
     * @param request
     * @return
     */
    @Override
    public BaseResponse updateReturnOrderNum(@RequestBody @Valid TradeReturnOrderNumUpdateRequest request) {
        tradeService.updateReturnOrderNum(request.getTid(), request.isAddFlag());
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 完善没有业务员的订单
     *
     * @return 操作结果 {@link BaseResponse}
     */
    @Override
    public BaseResponse fillEmployeeId() {
        tradeService.fillEmployeeId();
        return BaseResponse.SUCCESSFUL();
    }


    /**
     * 更新订单
     *
     * @param tradeUpdateRequest 订单信息 {@link TradeUpdateRequest}
     * @return 操作结果 {@link BaseResponse}
     */
    @Override
    public BaseResponse update(@RequestBody @Valid TradeUpdateRequest tradeUpdateRequest) {
        tradeService.updateTradeInfo(tradeUpdateRequest);
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse payOnlineCallBack(@RequestBody @Valid TradePayOnlineCallBackRequest tradePayOnlineCallBackRequest) {
        try {
            if(tradePayOnlineCallBackRequest.getPayCallBackType()== PayCallBackType.WECAHT){
                payAndRefundCallBackService.wxPayOnlineCallBack(tradePayOnlineCallBackRequest);
            } else if(tradePayOnlineCallBackRequest.getPayCallBackType()== PayCallBackType.ALI){
                payAndRefundCallBackService.aliPayOnlineCallBack(tradePayOnlineCallBackRequest);
            } else if(tradePayOnlineCallBackRequest.getPayCallBackType()== PayCallBackType.UNIONPAY){
                payAndRefundCallBackService.unionPayOnlineCallBack(tradePayOnlineCallBackRequest);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse<TradeTimeOutCancelResponse> timeOutCancel(@RequestBody TradeTimeOutCancelRequest tradeTimeOutCancelRequest) {
        Operator operator = new Operator();
        operator.setPlatform(Platform.PLATFORM);
        operator.setName("system");
        operator.setAccount("system");
        operator.setIp("127.0.0.1");
        //订单取消方法
        List<String> tidList = tradeTimeOutCancelRequest.getTidList();
        List<String> sucTidList = new ArrayList<>();
        List<String> failTidList = new ArrayList<>();
        for(String tid:tidList){
            try{
                tradeService.autoCancelOrder(tid, operator);
                sucTidList.add(tid);
            }catch (Exception e){
                failTidList.add(tid);
                log.error("手动补偿", e);
            }
        }
        return BaseResponse.success(TradeTimeOutCancelResponse.builder()
                .sucTidList(sucTidList)
                .failTidList(failTidList)
                .build());
    }

    @Override
    public BaseResponse updateCreditHasRepaid(@RequestBody @Valid TradeCreditHasRepaidRequest request) {
        tradeService.updateCreditHasRepaid(request.getTid(), request.getHasRepaid());
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse updateTradeState(@RequestBody @Valid TradeUpdateStateRequest request) {
        tradeService.updateTradeState(request);
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse writeOffOrder(@RequestBody @Valid WriteOffCodeRequest request) {

        tradeService.writeOffOrder(request);
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse sendElectronicAgain(@RequestBody @Valid ElectronicCardSendAgainRequest request) {
        tradeService.sendElectronicAgain(request.getRecordIds());
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse saveAsSellPlatformOrder(@RequestBody @Valid SellPlatformOrderSaveRequest request) {
        // 查询订单详情
        Trade trade = tradeService.detail(request.getTid());
        if (Objects.isNull(trade)) {
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050153);
        }
        if (SellPlatformType.WECHAT_VIDEO == request.getSellPlatformType()) {
            TradeState tradeState = trade.getTradeState();
            if (tradeState.getPayState() != PayState.NOT_PAID) {
                // 校验是否待支付
                throw new SbcRuntimeException(CommonErrorCodeEnum.K999999, "请填写待支付的订单编号");
            }
            // 微信视频号订单标识
            trade.setSellPlatformType(SellPlatformType.WECHAT_VIDEO);
            // 场景值置为特殊值
            trade.setSceneGroup(-1);
            sellPlatformTradeService.addOrder(trade);
            return BaseResponse.SUCCESSFUL();
        } else {
            // 目前仅存在微信视频号一种代销类型，后续若有其他类型，在此进行实现
            return BaseResponse.error(CommonErrorCodeEnum.K000019.getMsg());
        }
    }

    @Override
    public BaseResponse modifySendFlagById(@RequestBody @Valid OrderModifySendFlagRequest request){
        // 查询订单详情
        Trade trade = tradeService.detail(request.getTid());
        if (Boolean.TRUE.equals(request.getBookingStartSendFlag())){
            trade.setBookingStartSendFlag(Boolean.TRUE);
        }else {
            trade.setBookingEndSendFlag(Boolean.TRUE);
        }
        tradeService.updateTrade(trade);
        return BaseResponse.SUCCESSFUL();
    }

    public BaseResponse updateVersion(
            @RequestBody @Valid TradeListByIdOrPidRequest tradeListByIdOrPidRequest) {

        tradeService.updateVersion(
                TradeQueryRequest.builder()
                        .id(tradeListByIdOrPidRequest.getTid())
                        .parentId(tradeListByIdOrPidRequest.getParentTid())
                        .tradeState(TradeState.builder().payState(PayState.NOT_PAID).build())
                        .isAddFlag(tradeListByIdOrPidRequest.getIsAddFlag())
                        .build());
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse buyCycleDeferOrCancel(@RequestBody @Valid TradeBuyCycleModifyRequest request) {
        tradeService.buyCycleDeferOrCancel(request);
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse modifyConsigneeByBuyer(@RequestBody @Valid TradeConsigneeModifyByBuyerRequest request) {
        tradeService.modifyConsigneeByBuyer(request);
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse updateHasReturn (@RequestBody @Valid TradeUpdateHasReturnRequest tradeUpdateRequest){

        tradeService.updateHasReturn(tradeUpdateRequest.getTids());
        tradeService.providerUpdateHasReturn(tradeUpdateRequest.getPtids());
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * @return com.wanmi.sbc.common.base.BaseResponse
     * @description 社区团购佣金处理
     * @author edz
     * @date: 2023/7/28 17:47
     */
    @Override
    public BaseResponse communityCommission() {
        tradeService.communityTrade();
        return BaseResponse.SUCCESSFUL();
    }
}
