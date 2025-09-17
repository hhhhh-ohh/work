package com.wanmi.sbc.order.api.provider.trade;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.order.api.request.trade.*;
import com.wanmi.sbc.order.api.response.trade.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * @Author: ZhangLingKe
 * @Description:
 * @Date: 2018-12-03 17:24
 */
@FeignClient(value = "${application.order.name}", contextId = "TradeProvider")
public interface TradeProvider {
    /**
     * 批量新增交易单
     *
     * @param tradeAddBatchRequest 交易单集合 操作信息 {@link TradeAddBatchRequest}
     * @return 交易单提交结果集合 {@link TradeAddBatchResponse}
     */
    @PostMapping("/order/${application.order.version}/trade/add-batch")
    BaseResponse<TradeAddBatchResponse> addBatch(@RequestBody @Valid TradeAddBatchRequest tradeAddBatchRequest);

    /**
     * 代客下单批量新增交易单
     *
     * @param tradeAddBatchRequest 交易单集合 操作信息 {@link TradeAddBatchRequest}
     * @return 交易单提交结果集合 {@link TradeAddBatchResponse}
     */
    @PostMapping("/order/${application.order.version}/trade/supplier-add-batch")
    BaseResponse<TradeAddBatchResponse> supplierAddBatch(@RequestBody @Valid TradeAddBatchRequest tradeAddBatchRequest);


    /**
     * 批量分组新增交易单
     *
     * @param tradeAddBatchWithGroupRequest 交易单集合 分组信息 操作信息 {@link TradeAddBatchWithGroupRequest}
     * @return 交易单提交结果集合 {@link TradeAddBatchWithGroupResponse}
     */
    @PostMapping("/order/${application.order.version}/trade/add-batch-with-group")
    BaseResponse<TradeAddBatchWithGroupResponse> addBatchWithGroup(@RequestBody @Valid TradeAddBatchWithGroupRequest tradeAddBatchWithGroupRequest);

    /**
     * 订单改价
     *
     * @param tradeModifyPriceRequest 价格信息 交易单号 操作信息 {@link TradeModifyPriceRequest}
     * @return 操作结果 {@link BaseResponse}
     */
    @PostMapping("/order/${application.order.version}/trade/modify-price")
    BaseResponse modifyPrice(@RequestBody @Valid TradeModifyPriceRequest tradeModifyPriceRequest);


    /**
     * C端提交订单
     *
     * @param tradeCommitRequest 提交订单请求对象  {@link TradeCommitRequest}
     * @return
     */
    @PostMapping("/order/${application.order.version}/trade/commitFlashSale")
    BaseResponse<TradeCommitResponse> commitFlashSale(@RequestBody @Valid TradeCommitRequest tradeCommitRequest);

    /**
     * C端提交尾款订单
     *
     * @param tradeCommitRequest 提交订单请求对象  {@link TradeCommitRequest}
     * @return
     */
    @PostMapping("/order/${application.order.version}/trade/commit-tail")
    BaseResponse<TradeCommitResponse> commitTail(@RequestBody @Valid TradeCommitRequest tradeCommitRequest);

    /**
     * 移动端提交积分商品订单
     *
     * @param pointsTradeCommitRequest 提交订单请求对象  {@link PointsTradeCommitRequest}
     * @return
     */
    @PostMapping("/order/${application.order.version}/trade/points-commit")
    BaseResponse<PointsTradeCommitResponse> pointsCommit(@RequestBody @Valid PointsTradeCommitRequest pointsTradeCommitRequest);

    /**
     * 移动端提交积分优惠券订单
     *
     * @param pointsTradeCommitRequest 提交订单请求对象  {@link PointsTradeCommitRequest}
     * @return
     */
    @PostMapping("/order/${application.order.version}/trade/points-coupon-commit")
    BaseResponse<PointsTradeCommitResponse> pointsCouponCommit(@RequestBody @Valid PointsCouponTradeCommitRequest pointsTradeCommitRequest);

    /**
     * 修改订单
     *
     * @param tradeRemedyRequest 店铺信息 交易单信息 操作信息 {@link TradeModifyRemedyRequest}
     * @return 操作结果 {@link BaseResponse}
     */
    @PostMapping("/order/${application.order.version}/trade/remedy")
    BaseResponse remedy(@RequestBody @Valid TradeModifyRemedyRequest tradeRemedyRequest);

    /**
     * 修改订单-部分修改
     *
     * @param tradeRemedyPartRequest 店铺信息 交易单信息 操作信息 {@link TradeRemedyPartRequest}
     * @return 操作结果 {@link BaseResponse}
     */
    @PostMapping("/order/${application.order.version}/trade/remedy-part")
    BaseResponse remedyPart(@RequestBody @Valid TradeRemedyPartRequest tradeRemedyPartRequest);

    /**
     * 取消订单
     *
     * @param tradeCancelRequest 店铺信息 交易单信息 操作信息 {@link TradeCancelRequest}
     * @return 操作结果 {@link BaseResponse}
     */
    @PostMapping("/order/${application.order.version}/trade/cancel")
    BaseResponse cancel(@RequestBody @Valid TradeCancelRequest tradeCancelRequest);

    /**
     * 订单审核
     *
     * @param tradeAuditRequest 订单审核相关必要信息 {@link TradeAuditRequest}
     * @return 操作结果 {@link BaseResponse}
     */
    @PostMapping("/order/${application.order.version}/trade/audit")
    BaseResponse audit(@RequestBody @Valid TradeAuditRequest tradeAuditRequest);

    /**
     * 订单回审
     *
     * @param tradeRetrialRequest 订单审核相关必要信息 {@link TradeRetrialRequest}
     * @return 操作结果 {@link BaseResponse}
     */
    @PostMapping("/order/${application.order.version}/trade/retrial")
    BaseResponse retrial(@RequestBody @Valid TradeRetrialRequest tradeRetrialRequest);

    /**
     * 订单批量审核
     *
     * @param tradeAuditBatchRequest 订单审核相关必要信息 {@link TradeAuditBatchRequest}
     * @return 操作结果 {@link BaseResponse}
     */
    @PostMapping("/order/${application.order.version}/trade/audit-batch")
    BaseResponse auditBatch(@RequestBody @Valid TradeAuditBatchRequest tradeAuditBatchRequest);

    /**
     * 修改卖家备注
     *
     * @param tradeRemedySellerRemarkRequest 订单修改相关必要信息 {@link TradeRemedySellerRemarkRequest}
     * @return 操作结果 {@link BaseResponse}
     */
    @PostMapping("/order/${application.order.version}/trade/remedy-seller-remark")
    BaseResponse remedySellerRemark(@RequestBody @Valid TradeRemedySellerRemarkRequest tradeRemedySellerRemarkRequest);

    /**
     * 发货
     *
     * @param tradeDeliverRequest 物流信息 操作信息 {@link TradeDeliverRequest}
     * @return 物流编号 {@link TradeDeliverResponse}
     */
    @PostMapping("/order/${application.order.version}/trade/deliver")
    BaseResponse<TradeDeliverResponse> deliver(@RequestBody @Valid TradeDeliverRequest tradeDeliverRequest);

    /**
     * 批量发货
     */
    @PostMapping("/order/${application.order.version}/trade/batch-deliver")
    BaseResponse batchDeliver(@RequestBody @Valid TradeBatchDeliverRequest tradeBatchDeliverRequest);

    /**
     * 确认收货
     *
     * @param tradeConfirmReceiveRequest 订单编号 操作信息 {@link TradeConfirmReceiveRequest}
     * @return 操作结果 {@link BaseResponse}
     */
    @PostMapping("/order/${application.order.version}/trade/confirm-receive")
    BaseResponse confirmReceive(@RequestBody @Valid TradeConfirmReceiveRequest tradeConfirmReceiveRequest);

    /**
     * 退货 | 退款
     *
     * @param tradeReturnOrderRequest 订单编号 操作信息 {@link TradeReturnOrderRequest}
     * @return 操作结果 {@link BaseResponse}
     */
    @PostMapping("/order/${application.order.version}/trade/return-order")
    BaseResponse returnOrder(@RequestBody @Valid TradeReturnOrderRequest tradeReturnOrderRequest);

    /**
     * 作废订单
     *
     * @param tradeVoidedRequest 订单编号 操作信息 {@link TradeVoidedRequest}
     * @return 操作结果 {@link BaseResponse}
     */
    @PostMapping("/order/${application.order.version}/trade/voided")
    BaseResponse voided(@RequestBody @Valid TradeVoidedRequest tradeVoidedRequest);

    /**
     * 退单作废后的订单状态扭转
     *
     * @param tradeReverseRequest 订单编号 操作信息 {@link TradeReverseRequest}
     * @return 操作结果 {@link BaseResponse}
     */
    @PostMapping("/order/${application.order.version}/trade/reverse")
    BaseResponse reverse(@RequestBody @Valid TradeReverseRequest tradeReverseRequest);

    /**
     * 发货记录作废
     *
     * @param tradeDeliverRecordObsoleteRequest 订单编号 物流单号 操作信息 {@link TradeDeliverRecordObsoleteRequest}
     * @return 操作结果 {@link BaseResponse}
     */
    @PostMapping("/order/${application.order.version}/trade/deliver-record-obsolete")
    BaseResponse deliverRecordObsolete(@RequestBody @Valid TradeDeliverRecordObsoleteRequest tradeDeliverRecordObsoleteRequest);

    /**
     * 保存发票信息
     *
     * @param tradeAddInvoiceRequest 订单编号 发票信息 {@link TradeAddInvoiceRequest}
     * @return 操作结果 {@link BaseResponse}
     */
    @PostMapping("/order/${application.order.version}/trade/save-invoice")
    BaseResponse saveInvoice(@RequestBody @Valid TradeAddInvoiceRequest tradeAddInvoiceRequest);

    /**
     * 支付作废
     *
     * @param tradePayRecordObsoleteRequest 订单编号 操作信息 {@link TradePayRecordObsoleteRequest}
     * @return 操作结果 {@link BaseResponse}
     */
    @PostMapping("/order/${application.order.version}/trade/pay-pecord-obsolete")
    BaseResponse payRecordObsolete(@RequestBody @Valid TradePayRecordObsoleteRequest tradePayRecordObsoleteRequest);

    /**
     * 线上订单支付回调
     *
     * @param tradePayCallBackOnlineRequest 订单 支付单 操作信息 {@link TradePayCallBackOnlineRequest}
     * @return 操作结果 {@link BaseResponse}
     */
    @PostMapping("/order/${application.order.version}/trade/pay-callBack-online")
    BaseResponse payCallBackOnline(@RequestBody @Valid TradePayCallBackOnlineRequest tradePayCallBackOnlineRequest);

    /**
     * 线上订单支付批量回调
     *
     * @param tradePayCallBackOnlineBatchRequest 订单 支付单 操作批量信息 {@link TradePayCallBackOnlineBatchRequest}
     * @return 操作结果 {@link BaseResponse}
     */
    @PostMapping("/order/${application.order.version}/trade/pay-callBack-online-batch")
    BaseResponse payCallBackOnlineBatch(@RequestBody @Valid TradePayCallBackOnlineBatchRequest tradePayCallBackOnlineBatchRequest);

    /**
     * 订单支付回调
     *
     * @param tradePayCallBackRequest 订单号 支付金额 操作信息 支付方式{@link TradePayCallBackRequest}
     * @return 操作结果 {@link BaseResponse}
     */
    @PostMapping("/order/${application.order.version}/trade/pay-callBack")
    BaseResponse payCallBack(@RequestBody @Valid TradePayCallBackRequest tradePayCallBackRequest);

    /**
     * 0 元订单默认支付
     *
     * @param tradeDefaultPayRequest 订单号{@link TradeDefaultPayRequest}
     * @return 支付结果 {@link TradeDefaultPayResponse}
     */
    @PostMapping("/order/${application.order.version}/trade/default-pay")
    BaseResponse<TradeDefaultPayResponse> defaultPay(@RequestBody @Valid TradeDefaultPayRequest tradeDefaultPayRequest);

    /**
     * 0 元订单默认批量支付
     *
     * @param tradeDefaultPayBatchRequest 订单号{@link TradeDefaultPayBatchRequest}
     * @return {@link BaseResponse}
     */
    @PostMapping("/order/${application.order.version}/trade/default-pay-batch")
    BaseResponse defaultPayBatch(@RequestBody @Valid TradeDefaultPayBatchRequest tradeDefaultPayBatchRequest);

    /**
     * 新增线下收款单(包含线上线下的收款单)
     *
     * @param tradeAddReceivableRequest 收款单平台信息{@link TradeAddReceivableRequest}
     * @return 支付单 {@link TradeDefaultPayResponse}
     */
    @PostMapping("/order/${application.order.version}/trade/add-receivable")
    BaseResponse addReceivable(@RequestBody @Valid TradeAddReceivableRequest tradeAddReceivableRequest);

    /**
     * 确认支付单
     *
     * @param tradeConfirmPayOrderRequest 支付单id集合{@link TradeConfirmPayOrderRequest}
     * @return 支付单集合 {@link TradeConfirmPayOrderResponse}
     */
    @PostMapping("/order/${application.order.version}/trade/confirm-payOrder")
    BaseResponse confirmPayOrder(@RequestBody @Valid TradeConfirmPayOrderRequest tradeConfirmPayOrderRequest);

    /**
     * 根据支付状态统计订单
     *
     * @param tradeUpdateSettlementStatusRequest 查询参数 {@link TradeUpdateSettlementStatusRequest}
     * @return 支付单集合 {@link TradeCountByPayStateResponse}
     */
    @PostMapping("/order/${application.order.version}/trade/update-settlement-status")
    BaseResponse updateSettlementStatus(@RequestBody @Valid TradeUpdateSettlementStatusRequest tradeUpdateSettlementStatusRequest);


    /**
     * 添加订单
     *
     * @param tradeAddRequest 订单信息 {@link TradeAddRequest}
     * @return 操作结果 {@link BaseResponse}
     */
    @PostMapping("/order/${application.order.version}/trade/add")
    BaseResponse add(@RequestBody @Valid TradeAddRequest tradeAddRequest);

    /**
     * 更新订单的业务员
     *
     * @param tradeUpdateEmployeeIdRequest 业务员信息 {@link TradeUpdateEmployeeIdRequest}
     * @return 操作结果 {@link BaseResponse}
     */
    @PostMapping("/order/${application.order.version}/trade/update-employeeId")
    BaseResponse updateEmployeeId(@RequestBody @Valid TradeUpdateEmployeeIdRequest tradeUpdateEmployeeIdRequest);

    /**
     * 更新返利标志
     *
     * @param request
     * @return
     */
    @PostMapping("/order/${application.order.version}/trade/update-commission-flag")
    BaseResponse updateCommissionFlag(@RequestBody @Valid TradeUpdateCommissionFlagRequest request);

    /**
     * 更新最终时间
     *
     * @param request
     * @return
     */
    @PostMapping("/order/${application.order.version}/trade/update-final-time")
    BaseResponse updateFinalTime(@RequestBody @Valid TradeFinalTimeUpdateRequest request);

    /**
     * 更新正在进行的退单数量
     *
     * @param request
     * @return
     */
    @PostMapping("/order/${application.order.version}/trade/update-return-order-num")
    BaseResponse updateReturnOrderNum(@RequestBody @Valid TradeReturnOrderNumUpdateRequest request);

    /**
     * 完善没有业务员的订单
     *
     * @return 操作结果 {@link BaseResponse}
     */
    @PostMapping("/order/${application.order.version}/trade/fill-employeeId")
    BaseResponse fillEmployeeId();

    /**
     * 更新订单
     *
     * @param tradeUpdateRequest 订单信息 {@link TradeUpdateRequest}
     * @return 操作结果 {@link BaseResponse}
     */
    @PostMapping("/order/${application.order.version}/trade/update")
    BaseResponse update(@RequestBody @Valid TradeUpdateRequest tradeUpdateRequest);

    /**
     * 订单支付回调处理
     *
     * @param tradePayOnlineCallBackRequest 订单信息 {@link TradePayOnlineCallBackRequest}
     * @return 操作结果 {@link BaseResponse}
     */
    @Deprecated
    @PostMapping("/order/${application.order.version}/trade/pay-online-call-back")
    BaseResponse payOnlineCallBack(@RequestBody @Valid TradePayOnlineCallBackRequest tradePayOnlineCallBackRequest);

    /**
     * 超时未支付，补偿取消
     *
     * @return
     */
    @PostMapping("/order/${application.order.version}/trade/time-out-cancel")
    BaseResponse<TradeTimeOutCancelResponse> timeOutCancel(@RequestBody TradeTimeOutCancelRequest tradeTimeOutCancelRequest);

    /**
     * 更新授信订单的还款状态
     *
     * @param request
     * @return
     */
    @PostMapping("/order/${application.order.version}/trade/updateCreditHasRepaid")
    BaseResponse updateCreditHasRepaid(@RequestBody @Valid TradeCreditHasRepaidRequest request);



    /**
     * o2o更新订单状态
     *
     * @param request
     * @return
     */
    @PostMapping("/order/${application.order.version}/trade/updateTradeState")
    BaseResponse updateTradeState(@RequestBody @Valid TradeUpdateStateRequest request);

    /**
     * 自提订单核销
     * @param request
     */
    @PostMapping("/order/${application.order.version}/trade/write/off")
    BaseResponse writeOffOrder(@RequestBody @Valid WriteOffCodeRequest request);

    /**
     * 卡密批量重发
     * @param request
     */
    @PostMapping("/order/${application.order.version}/trade/send-electronic-again")
    BaseResponse sendElectronicAgain(@RequestBody @Valid ElectronicCardSendAgainRequest request);

    /**
     * 保存为代销订单
     * @param request
     * @return
     */
    @PostMapping("/order/${application.order.version}/trade/save-as-sell-platform-order")
    BaseResponse saveAsSellPlatformOrder(@RequestBody @Valid SellPlatformOrderSaveRequest request);

    /**
     * 条件
     *
     * @param request 带参参数 {@link GrouponInstanceByGrouponNoRequest}
     * @return
     */
    @PostMapping("/order/${application.order.version}/trade/modify-send-flag-by-id")
    BaseResponse modifySendFlagById(@RequestBody @Valid OrderModifySendFlagRequest request);

    @PostMapping("/order/${application.order.version}/trade/update-version")
    BaseResponse updateVersion(@RequestBody @Valid TradeListByIdOrPidRequest tradeListByIdOrPidRequest);

    /**
     * 周期购顺延配送，取消顺延
     *
     * @param request 带参参数 {@link TradeBuyCycleModifyRequest}
     * @return
     */
    @PostMapping("/order/${application.order.version}/trade/buy-cycle-defer-or-cancel")
    BaseResponse buyCycleDeferOrCancel(@RequestBody @Valid TradeBuyCycleModifyRequest request);

    /**
     * 买家修改订单收货地址
     * @param request
     * @return
     */
    @PostMapping("/order/${application.order.version}/trade/modify-consignee-by-buyer")
    BaseResponse modifyConsigneeByBuyer(@RequestBody @Valid TradeConsigneeModifyByBuyerRequest request);

    /**
     * 批量修改订单表中是否有售后状态
     */
    @PostMapping("/order/${application.order.version}/trade/updata-has-return")
    BaseResponse updateHasReturn (@RequestBody @Valid TradeUpdateHasReturnRequest tradeUpdateHasReturnRequest);

    /**
     * @description 社区团购佣金处理
     * @author  edz
     * @date: 2023/7/28 17:47
     * @return com.wanmi.sbc.common.base.BaseResponse
     */
    @PostMapping("/order/${application.order.version}/trade/community-commission")
    BaseResponse communityCommission();
}
