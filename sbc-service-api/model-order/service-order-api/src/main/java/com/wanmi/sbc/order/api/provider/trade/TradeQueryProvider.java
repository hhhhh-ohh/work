package com.wanmi.sbc.order.api.provider.trade;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.order.api.request.trade.*;
import com.wanmi.sbc.order.api.response.trade.*;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @Author: ZhangLingKe
 * @Description:
 * @Date: 2018-12-03 17:24
 */
@FeignClient(value = "${application.order.name}", contextId = "TradeQueryProvider")
public interface TradeQueryProvider {

//    /**
//     * 分页
//     *
//     * @param tradePageRequest 分页参数 {@link TradePageRequest}
//     * @return
//     */
//    @PostMapping("/order/${application.order.version}/trade/page")
//    BaseResponse<TradePageResponse> page(@RequestBody @Valid TradePageRequest tradePageRequest);

    /**
     * 条件分页
     *
     * @param tradePageCriteriaRequest 带参分页参数 {@link TradePageCriteriaRequest}
     * @return
     */
    @PostMapping("/order/${application.order.version}/trade/page-criteria")
    BaseResponse<TradePageCriteriaResponse> pageCriteria(@RequestBody @Valid TradePageCriteriaRequest tradePageCriteriaRequest);

    /**
     * 条件分页
     *
     * @param tradePageCriteriaRequest 带参分页参数 {@link TradePageCriteriaRequest}
     * @return
     */
    @PostMapping("/order/${application.order.version}/trade/supplier-page-criteria")
    BaseResponse<TradePageCriteriaResponse> supplierPageCriteria(@RequestBody @Valid TradePageCriteriaRequest tradePageCriteriaRequest);

    /**
     * boss 订单条件分页
     * @param tradePageCriteriaRequest
     * @return
     */
    @PostMapping("/order/${application.order.version}/trade/page-boss-criteria")
    BaseResponse<TradePageCriteriaResponse> pageBossCriteria(@RequestBody @Valid TradePageCriteriaRequest tradePageCriteriaRequest);
    /**
     * 条件分页
     *
     * @param tradeCountCriteriaRequest 带参分页参数 {@link TradeCountCriteriaRequest}
     * @return
     */
    @PostMapping("/order/${application.order.version}/trade/count-criteria")
    BaseResponse<TradeCountCriteriaResponse> countCriteria(@RequestBody @Valid TradeCountCriteriaRequest tradeCountCriteriaRequest);

    /**
     * 调用校验与封装单个订单信息
     *
     * @param tradeWrapperBackendCommitRequest 包装信息参数 {@link TradeWrapperBackendCommitRequest}
     * @return 订单信息 {@link TradeWrapperBackendCommitResponse}
     */
    @PostMapping("/order/${application.order.version}/trade/wrapper-backend-commit")
    BaseResponse<TradeWrapperBackendCommitResponse> wrapperBackendCommit(@RequestBody @Valid TradeWrapperBackendCommitRequest tradeWrapperBackendCommitRequest);

    /**
     * 查询店铺订单应付的运费
     *
     * @param tradeParamsRequest 包装信息参数 {@link TradeParamsRequest}
     * @return 店铺运费信息 {@link TradeGetFreightResponse}
     */
    @PostMapping("/order/${application.order.version}/trade/get-freight")
    BaseResponse<TradeGetFreightResponse> getFreight(@RequestBody @Valid TradeParamsRequest tradeParamsRequest);

    /**
     * 获取订单商品详情,不包含区间价，会员级别价信息
     *
     * @param tradeGetGoodsRequest 商品skuid列表 {@link TradeGetGoodsRequest}
     * @return 商品信息列表 {@link TradeGetGoodsResponse}
     */
    @PostMapping("/order/${application.order.version}/trade/get-goods")
    BaseResponse<TradeGetGoodsResponse> getGoods(@RequestBody @Valid TradeGetGoodsRequest tradeGetGoodsRequest);


    /**
     * 发货校验,检查请求发货商品数量是否符合应发货数量
     *
     * @param tradeDeliveryCheckRequest 订单号 物流信息 {@link TradeDeliveryCheckRequest}
     * @return 处理结果 {@link BaseResponse}
     */
    @PostMapping("/order/${application.order.version}/trade/delivery-check")
    BaseResponse deliveryCheck(@RequestBody @Valid TradeDeliveryCheckRequest tradeDeliveryCheckRequest);

    /**
     * 通过id获取交易单信息,支付使用，不需要加密buyer.account加密
     *
     * @param tradeGetByIdRequest 交易单id {@link TradeGetByIdRequest}
     * @return 交易单信息 {@link TradeGetByIdResponse}
     */
    @PostMapping("/order/${application.order.version}/trade/get-by-id-for-balance-pay")
    BaseResponse<TradeGetByIdResponse> getByIdForBalancePay(@RequestBody @Valid TradeGetByIdRequest tradeGetByIdRequest);

    /**
     * 通过id获取交易单信息并将buyer.account加密
     *
     * @param tradeGetByIdRequest 交易单id {@link TradeGetByIdRequest}
     * @return 交易单信息 {@link TradeGetByIdResponse}
     */
    @PostMapping("/order/${application.order.version}/trade/get-by-id")
    BaseResponse<TradeGetByIdResponse> getById(@RequestBody @Valid TradeGetByIdRequest tradeGetByIdRequest);

    /**
     * 通过id批量获取交易单信息并将buyer.account加密
     *
     * @param tradeGetByIdsRequest 交易单id {@link TradeGetByIdsRequest}
     * @return 交易单信息 {@link TradeGetByIdsResponse}
     */
    @PostMapping("/order/${application.order.version}/trade/get-by-ids")
    BaseResponse<TradeGetByIdsResponse> getByIds(@RequestBody @Valid TradeGetByIdsRequest tradeGetByIdsRequest);

    /**
     * 通过id批量获取交易单信息
     *
     * @param tradeGetByIdListRequest 交易单id {@link TradeGetByIdsRequest}
     * @return 交易单信息 {@link TradeGetByIdsResponse}
     */
    @PostMapping("/order/${application.order.version}/trade/get-trade-by-ids")
    BaseResponse<TradeListAllResponse> getTradeListByIds(@RequestBody @Valid TradeGetByIdListRequest tradeGetByIdListRequest);

    /**
     * 通过id获取交易单信息
     *
     * @param tradeGetByIdRequest 交易单id {@link TradeGetByIdRequest}
     * @return 交易单信息 {@link TradeGetByIdResponse}
     */
    @PostMapping("/order/${application.order.version}/trade/get-order-by-id")
    BaseResponse<TradeGetByIdResponse> getOrderById(@RequestBody @Valid TradeGetByIdRequest tradeGetByIdRequest);

    /**
     * 通过父订单号获取交易单集合并将buyer.account加密
     *
     * @param tradeListByParentIdRequest 父交易单id {@link TradeListByParentIdRequest}
     * @return 交易单信息 {@link TradeListByParentIdResponse}
     */
    @PostMapping("/order/${application.order.version}/trade/get-list-by-parent-id")
    BaseResponse<TradeListByParentIdResponse> getListByParentId(@RequestBody @Valid TradeListByParentIdRequest tradeListByParentIdRequest);

    /**
     * 通过父订单号获取交易单集合
     *
     * @param tradeListByParentIdRequest 父交易单id {@link TradeListByParentIdRequest}
     * @return 交易单信息 {@link TradeListByParentIdResponse}
     */
    @PostMapping("/order/${application.order.version}/trade/get-order-list-by-parent-id")
    BaseResponse<TradeListByParentIdResponse> getOrderListByParentId(@RequestBody @Valid TradeListByParentIdRequest tradeListByParentIdRequest);

    /**
     * 验证订单是否全部售后申请
     *
     * @param tradeVerifyAfterProcessingRequest 交易单id {@link TradeVerifyAfterProcessingRequest}
     * @return 验证结果 {@link TradeVerifyAfterProcessingResponse}
     */
    @PostMapping("/order/${application.order.version}/trade/verify-after-processing")
    BaseResponse<TradeVerifyAfterProcessingResponse> verifyAfterProcessing(@RequestBody @Valid TradeVerifyAfterProcessingRequest tradeVerifyAfterProcessingRequest);

    /**
     * 验证订单是否存在售后申请
     *
     * @param tradeVerifyAfterProcessingRequest 交易单id {@link TradeVerifyAfterProcessingRequest}
     * @return 验证结果 {@link TradeVerifyAfterProcessingResponse}
     */
    @PostMapping("/order/${application.order.version}/trade/verify-processing")
    BaseResponse<TradeVerifyAfterProcessingResponse> verifyProcessing(@RequestBody @Valid TradeVerifyAfterProcessingRequest tradeVerifyAfterProcessingRequest);


    /**
     * 验证订单是否存在售后申请
     *
     * @param providerTradeVerifyAfterProcessingRequest 交易单id {@link TradeVerifyAfterProcessingRequest}
     * @return 验证结果 {@link TradeVerifyAfterProcessingResponse}
     */
    @PostMapping("/order/${application.order.version}/provider/trade/verify-after-processing")
    BaseResponse<TradeVerifyAfterProcessingResponse> providerVerifyAfterProcessing(@RequestBody @Valid ProviderTradeVerifyAfterProcessingRequest providerTradeVerifyAfterProcessingRequest);

    /**
     * 条件查询所有订单
     *
     * @param tradeListAllRequest 查询条件 {@link TradeListAllRequest}
     * @return 验证结果 {@link TradeListAllResponse}
     */
    @PostMapping("/order/${application.order.version}/trade/list-all")
    BaseResponse<TradeListAllResponse> listAll(@RequestBody @Valid TradeListAllRequest tradeListAllRequest);


    /**
     * 条件查询所有订单(分页)
     *
     * @param tradeListAllRequest 查询条件 {@link TradeListAllRequest}
     * @return 验证结果 {@link TradeListAllResponse}
     */
    @PostMapping("/order/${application.order.version}/trade/page-all")
    BaseResponse<TradeListAllResponse> pageAll(@RequestBody @Valid TradeListAllRequest tradeListAllRequest);

    /**
     * 获取支付单
     *
     * @param tradeGetPayOrderByIdRequest 支付单号 {@link TradeGetPayOrderByIdRequest}
     * @return 支付单 {@link TradeGetPayOrderByIdResponse}
     */
    @PostMapping("/order/${application.order.version}/trade/get-payOrder-by-id")
    BaseResponse<TradeGetPayOrderByIdResponse> getPayOrderById(@RequestBody @Valid TradeGetPayOrderByIdRequest tradeGetPayOrderByIdRequest);


    /**
     * 查询订单信息作为结算原始数据
     *
     * @param tradePageForSettlementRequest 查询分页参数 {@link TradePageForSettlementRequest}
     * @return 支付单集合 {@link TradePageForSettlementResponse}
     */
    @PostMapping("/order/${application.order.version}/trade/page-for-settlement")
    BaseResponse<TradePageForSettlementResponse> pageForSettlement(@RequestBody @Valid TradePageForSettlementRequest tradePageForSettlementRequest);

    /**
     * 根据快照封装订单确认页信息
     *
     * @param tradeQueryPurchaseInfoRequest 交易单快照信息 {@link TradeQueryPurchaseInfoRequest}
     * @return 交易单确认项 {@link TradeQueryPurchaseInfoResponse}
     */
    @PostMapping("/order/${application.order.version}/trade/query-purchaseInfo")
    BaseResponse<TradeQueryPurchaseInfoResponse> queryPurchaseInfo(@RequestBody @Valid TradeQueryPurchaseInfoRequest tradeQueryPurchaseInfoRequest);


    /**
     * 用于编辑订单前的展示信息，包含了原订单信息和最新关联的订单商品价格（计算了会员价和级别价后的商品单价）
     *
     * @param tradeGetRemedyByTidRequest 交易单id {@link TradeGetRemedyByTidRequest}
     * @return 废弃单 {@link TradeGetRemedyByTidResponse}
     */
    @PostMapping("/order/${application.order.version}/trade/get-remedy-by-tid")
    BaseResponse<TradeGetRemedyByTidResponse> getRemedyByTid(@RequestBody @Valid TradeGetRemedyByTidRequest tradeGetRemedyByTidRequest);

    /**
     * 查询客户首笔完成的交易号
     *
     * @param request
     * @return
     */
    @PostMapping("/order/${application.order.version}/trade/query-first-complete-trade")
    BaseResponse<TradeQueryFirstCompleteResponse> queryFirstCompleteTrade(@RequestBody @Valid TradeQueryFirstCompleteRequest request);


    /**
     * 查询客户首笔完成的交易号
     *
     * @param request
     * @return
     */
    @PostMapping("/order/${application.order.version}/trade/query-first-pay-trade")
    BaseResponse<TradeQueryFirstCompleteResponse> queryFirstPayTrade(@RequestBody @Valid TradeQueryFirstCompleteRequest request);

    /**
     * 订单选择银联企业支付通知财务
     *
     * @param tradeSendEmailToFinanceRequest 邮箱信息 {@link TradeSendEmailToFinanceRequest}
     * @return 发送结果 {@link BaseResponse}
     */
    @PostMapping("/order/${application.order.version}/trade/send-email-to-finance")
    BaseResponse sendEmailToFinance(@RequestBody @Valid TradeSendEmailToFinanceRequest tradeSendEmailToFinanceRequest);

    /**
     * 查询导出数据
     *
     * @param tradeListExportRequest 查询条件 {@link TradeListExportRequest}
     * @return 验证结果 {@link TradeListExportResponse}
     */
    @PostMapping("/order/${application.order.version}/trade/list-export")
    BaseResponse<TradeListExportResponse> listTradeExport(@RequestBody @Valid TradeListExportRequest tradeListExportRequest);

    /**
     * 查询导出数据量
     *
     * @param tradeListExportRequest 查询条件 {@link TradeListExportRequest}
     * @return 验证结果 {@link TradeListExportResponse}
     */
    @PostMapping("/order/${application.order.version}/trade/count-export")
    BaseResponse<Long> countTradeExport(@RequestBody @Valid TradeListExportRequest tradeListExportRequest);

    /**
     * 通过支付单获取交易单
     *
     * @param tradeByPayOrderIdRequest 父交易单id {@link TradeByPayOrderIdRequest}
     * @return 交易单信息 {@link TradeByPayOrderIdResponse}
     */
    @PostMapping("/order/${application.order.version}/trade/get-order-list-by-pay-order-id")
    BaseResponse<TradeByPayOrderIdResponse> getOrderByPayOrderId(@RequestBody @Valid TradeByPayOrderIdRequest tradeByPayOrderIdRequest);

    /**
     * 条件分页（优化版）
     *
     * @param tradePageCriteriaRequest 带参分页参数 {@link TradePageCriteriaRequest}
     * @return
     */
    @PostMapping("/order/${application.order.version}/trade/page-criteria-optimize")
    BaseResponse<TradePageCriteriaResponse> pageCriteriaOptimize(@RequestBody @Valid TradePageCriteriaRequest tradePageCriteriaRequest);

    /**
     * 批量查询物流单号是否重复
     */
    @PostMapping("/order/${application.order.version}/trade/logisticNo-repeat")
    BaseResponse<LogisticNoRepeatResponse> getRepeatLogisticNo(@RequestBody @Valid LogisticsRepeatRequest logisticsRepeatRequest);

    /**
     * 分页查询授信订单使用记录
     *
     * @param request
     * @return
     */
    @PostMapping("/order/${application.order.version}/trade/find-credit-used-page")
    BaseResponse<CreditTradeVOPageResponse> findCreditUsedPage(@RequestBody @Valid CreditTradePageRequest request);

    /**
     * 分页查询授信订单使用记录
     *
     * @param request
     * @return
     */
    @PostMapping("/order/${application.order.version}/trade/find-credit-repay-page")
    BaseResponse<CreditTradeVOPageResponse> findCreditRepayPage(@RequestBody @Valid CreditTradePageRequest request);

    /**
     * 根据id或者pid查询订单
     * @param tradeListByIdOrPidRequest
     * @return
     */
    @PostMapping("/order/${application.order.version}/trade/select-list-byid-or-pid")
    BaseResponse<TradeListByParentIdResponse> selectListByidOrPid(@RequestBody @Valid TradeListByIdOrPidRequest tradeListByIdOrPidRequest);

    /**
     * 未核销订单数量
     * @param request
     * @return
     */
    @PostMapping("/order/${application.order.version}/trade/count-by-not-written-off-order")
    BaseResponse<CountByNotWrittenOffOrderResponse> countByNotWrittenOffOrder(@RequestBody @Valid CountByNotWrittenOffOrderRequest request);

    /**
     * 核销订单详情
     * @param request
     * @return
     */
    @PostMapping("/order/${application.order.version}/trade/order-details-by-written-off-code")
    BaseResponse<OrderDetailsByWriteOffCodeResponse> orderDetailsByWriteOffCode(@RequestBody @Valid WriteOffCodeRequest request);

    /**
     * 查询订单关联的售后商品数量
     * @param request
     * @return
     */
    @PostMapping("/order/${application.order.version}/trade/find-return-item-num")
    BaseResponse<OrderReturnItemNumResponse> findReturnItemNum(@RequestBody @Valid OrderReturnItemNumRequest request);

    /**
     * 注销校验
     *
     * @param validateTradeAndAccountRequest 参数 {@link ValidateTradeAndAccountRequest}
     * @return
     */
    @PostMapping("/order/${application.order.version}/trade/validateTradeAndAccount")
    BaseResponse<ValidateTradeAndAccountResponse> validateTradeAndAccount(@RequestBody @Valid ValidateTradeAndAccountRequest validateTradeAndAccountRequest);

    /**
     * 省钱列表
     *
     * @param tradeEconomicalPageRequest 带参分页参数 {@link TradeEconomicalPageRequest}
     * @return
     */
    @PostMapping("/order/${application.order.version}/trade/economical-page")
    BaseResponse<TradeEconomicalPageResponse> economicalPage(@RequestBody @Valid TradeEconomicalPageRequest tradeEconomicalPageRequest);

    /**
     * 判断是否查询已审核且未支付订单的条件
     **/
    @PostMapping("/order/${application.order.version}/trade/countdown")
    BaseResponse<TradeListAllResponse> countDown(@RequestBody ValidateTradeAndAccountRequest request);

    /**
     * 通过父订单号获取交易单集合
     *
     * @param tradeListByGrouponNoRequest
     * @return
     */
    @PostMapping("/order/${application.order.version}/trade/get-trade-by-grouponno")
    BaseResponse<TradeListByGrouponNoResponse> getTradeByGrouponNo(@RequestBody @Valid TradeListByGrouponNoRequest tradeListByGrouponNoRequest);


    /**
     * @description 尾款ID批量查询
     * @author  edz
     * @date: 2022/10/14 22:14
     * @param findByTailOrderNoInRequest
     * @return com.wanmi.sbc.common.base.BaseResponse<com.wanmi.sbc.order.api.response.trade.FindByTailOrderNoInResponse>
     */
    @PostMapping("/order/${application.order.version}/trade/find-tailOrderNo")
    BaseResponse<FindByTailOrderNoInResponse> findByTailOrderNoIn(@RequestBody FindByTailOrderNoInRequest findByTailOrderNoInRequest);


    /**
     * @description 查询所有未完成的定金预售且未作废的
     * @author  bob
     * @date: 2022/10/17 15:33
     * @return com.wanmi.sbc.common.base.BaseResponse<java.lang.Long>
     */
    @PostMapping("/order/${application.order.version}/trade/find-efficient-tailOrder")
    BaseResponse<Long> findForEfficientTailOrder();

    /**
     * 列表查询
     *
     * @param tradeListCriteriaRequest 带参分页参数 {@link TradeListCriteriaRequest}
     * @return
     */
    @PostMapping("/order/${application.order.version}/trade/list-criteria")
    BaseResponse<TradeListCriteriaResponse> listCriteria(@RequestBody @Valid TradeListCriteriaRequest tradeListCriteriaRequest);


    @PostMapping("/order/${application.order.version}/trade/get-list-by-customer-id")
    BaseResponse<TradeListAllResponse> getListByCustomerId(@RequestBody CustomerTradeListRequest tradeListAllRequest);
    /**
     * 获取订单详情
     *
     * @param tradeGetByIdRequest 订单id {@link TradeGetByIdRequest}
     * @return
     */
    @PostMapping("/order/${application.order.version}/trade/get-by-id-for-deliver")
    BaseResponse<TradeGetByIdResponse> getByIdForDeliver(@RequestBody @Valid TradeGetByIdRequest tradeGetByIdRequest);

    /**
     * 获取当天预约发货的订单列表
     *
     * @param appointmentShipmentQueryRequest 开始时间,结束时间 {@link TradeGetByIdRequest}
     * @return
     */
    @PostMapping("/order/${application.order.version}/trade/find-expired-appointment-trade-ids")
    BaseResponse<List<String>> findExpiredAppointmentTradeIds(@RequestBody AppointmentShipmentQueryRequest appointmentShipmentQueryRequest);
}
