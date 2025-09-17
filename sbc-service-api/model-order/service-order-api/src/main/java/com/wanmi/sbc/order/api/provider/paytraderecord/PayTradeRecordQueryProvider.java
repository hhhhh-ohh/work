package com.wanmi.sbc.order.api.provider.paytraderecord;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.order.api.request.paytraderecord.FundPayTradeRecordsRefundRequest;
import com.wanmi.sbc.order.api.request.paytraderecord.PayResultByOrdercodeRequest;
import com.wanmi.sbc.order.api.request.paytraderecord.PayTradeRecordByParamsRequest;
import com.wanmi.sbc.order.api.request.paytraderecord.RefundResultByOrdercodeRequest;
import com.wanmi.sbc.order.api.request.paytraderecord.TradeNoByBusinessIdRequest;
import com.wanmi.sbc.order.api.request.paytraderecord.TradeRecordByChargeRequest;
import com.wanmi.sbc.order.api.request.paytraderecord.TradeRecordByIdRequest;
import com.wanmi.sbc.order.api.request.paytraderecord.TradeRecordByOrderCodeRequest;
import com.wanmi.sbc.order.api.request.paytraderecord.TradeRecordByOrderOrParentCodeRequest;
import com.wanmi.sbc.order.api.request.paytraderecord.TradeRecordCountByOrderCodeRequest;
import com.wanmi.sbc.order.api.request.paytraderecord.TradeRecordCountByOrderOrParentCodeRequest;
import com.wanmi.sbc.order.api.response.paytraderecord.FindPayTradeRefundResponse;
import com.wanmi.sbc.order.api.response.paytraderecord.PayResultResponse;
import com.wanmi.sbc.order.api.response.paytraderecord.PayTradeNoMapResponse;
import com.wanmi.sbc.order.api.response.paytraderecord.PayTradeRecordCountResponse;
import com.wanmi.sbc.order.api.response.paytraderecord.PayTradeRecordCreditStatisticsResponse;
import com.wanmi.sbc.order.api.response.paytraderecord.PayTradeRecordResponse;
import com.wanmi.sbc.order.api.response.paytraderecord.RefundResultResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

@FeignClient(value = "${application.order.name}", contextId = "PayTradeRecordQueryProvider")
public interface PayTradeRecordQueryProvider {

    /**
     * 根据id查询交易记录
     *
     * @param tradeRecordByIdRequest 包含记录id的查询参数 {@link TradeRecordByIdRequest}
     * @return 交易记录 {@link PayTradeRecordResponse}
     */
    @PostMapping("/order/${application.order.version}/get-trade-record-by-id")
    BaseResponse<PayTradeRecordResponse> getTradeRecordById(@RequestBody @Valid TradeRecordByIdRequest
                                                                    tradeRecordByIdRequest);

    /**
     * 根据支付/退款对象id查询交易记录
     *
     * @param recordByChangeRequest 包含支付/退款对象id的查询参数 {@link TradeRecordByChargeRequest}
     * @return 交易记录 {@link PayTradeRecordResponse}
     */
    @PostMapping("/order/${application.order.version}/get-trade-record-by-charge-id")
    BaseResponse<PayTradeRecordResponse> getTradeRecordByChargeId(@RequestBody @Valid TradeRecordByChargeRequest
                                                                          recordByChangeRequest);

    /**
     * 根据业务订单/退单号查询交易记录
     *
     * @param recordByOrderCodeRequest 包含业务订单/退单号的查询参数 {@link TradeRecordByOrderCodeRequest}
     * @return 交易记录 {@link PayTradeRecordResponse}
     */
    @PostMapping("/order/${application.order.version}/get-trade-record-by-order-code")
    BaseResponse<PayTradeRecordResponse> getTradeRecordByOrderCode(@RequestBody @Valid TradeRecordByOrderCodeRequest
                                                                           recordByOrderCodeRequest);

    /***
     * 根据业务ID（订单号或退单号）
     * 返回<businessId:tradeNo> 格式Map
     * 如果参数为空或返回值为空，返回空Map，不会抛出异常
     * @param request           业务ID集合
     * @return                  业务ID:业务流水号 Map
     */
    @PostMapping("/order/${application.order.version}/query-trade-no-map-by-business-ids")
    BaseResponse<PayTradeNoMapResponse> queryTradeNoMapByBusinessIds(@RequestBody @Valid TradeNoByBusinessIdRequest request);

    /**
     * 根据业务订单/退单号统计交易记录数
     *
     * @param recordCountByOrderCodeRequest 包含业务订单/退单号的查询参数 {@link TradeRecordCountByOrderCodeRequest}
     * @return 交易记录 {@link PayTradeRecordCountResponse}
     */
    @PostMapping("/order/${application.order.version}/get-trade-record-count-by-order-code")
    BaseResponse<PayTradeRecordCountResponse> getTradeRecordCountByOrderCode(
            @RequestBody @Valid TradeRecordCountByOrderCodeRequest recordCountByOrderCodeRequest);


    /**
     * 根据业务父子订单号查询交易记录，若子订单查不出，则匹配父订单号
     *
     * @param tradeRecordByOrderOrParentCodeRequest 包含业务订单/退单号的查询参数
     *                                              {@link TradeRecordByOrderOrParentCodeRequest}
     * @return 交易记录 {@link PayTradeRecordResponse}
     */
    @PostMapping("/order/${application.order.version}/get-trade-record-by-order-or-parent-code")
    BaseResponse<PayTradeRecordResponse> getTradeRecordByOrderOrParentCode(@RequestBody @Valid TradeRecordByOrderOrParentCodeRequest
                                                                                   tradeRecordByOrderOrParentCodeRequest);

    /**
     * 根据业务父子订单号统计交易记录数，若子订单查不出，则匹配父订单号
     *
     * @param tradeRecordCountByOrderOrParentCodeRequest 包含业务订单/退单号的查询参数
     *                                                   {@link TradeRecordCountByOrderOrParentCodeRequest}
     * @return 交易记录 {@link PayTradeRecordCountResponse}
     */
    @PostMapping("/order/${application.order.version}/get-trade-record-count-by-order-or-parent-code")
    BaseResponse<PayTradeRecordCountResponse> getTradeRecordCountByOrderOrParentCode(
            @RequestBody @Valid TradeRecordCountByOrderOrParentCodeRequest tradeRecordCountByOrderOrParentCodeRequest);


    /**
     * 根据订单号查询支付结果
     *
     * @param payResultByOrdercodeRequest 包含订单号的请求参数 {@link PayResultByOrdercodeRequest}
     * @return 返回信息包含支付状态 {@link PayResultResponse}
     */
    @PostMapping("/order/${application.order.version}/get-pay-response-by-ordercode")
    BaseResponse<PayResultResponse> getPayResponseByOrdercode(@RequestBody @Valid PayResultByOrdercodeRequest
                                                                      payResultByOrdercodeRequest);

    /**
     * 根据订单号和退单号查询退款结果
     *
     * @param refundResultByOrdercodeRequest 包含订单号和退单号的请求参数 {@link RefundResultByOrdercodeRequest}
     * @return 返回信息包含退款状态 {@link RefundResultResponse}
     */
    @PostMapping("/order/${application.order.version}/get-refund-response-by-ordercode")
    BaseResponse<RefundResultResponse> getRefundResponseByOrdercode(@RequestBody @Valid RefundResultByOrdercodeRequest
                                                                            refundResultByOrdercodeRequest);


    /**
     * @description 从交易记录中统计授信支付和还款的总数
     * @author  chenli
     * @date 2021/4/22 15:22
     * @return
     **/
    @PostMapping("/order/${application.order.version}/get-pay-trade-record-credit-statistics")
    BaseResponse<PayTradeRecordCreditStatisticsResponse> getPayTradeRecordCreditStatistics();


    /**
     * 批量查询支付流水信息
     * @param request  订单号
     * @return   订单支付流水明细
     */
    @PostMapping("/order/${application.order.version}/find-pay-trade-list")
    BaseResponse<FindPayTradeRefundResponse> findPayTradeRecords(@RequestBody @Valid FundPayTradeRecordsRefundRequest request);

    /**
     * 未退款或退款失败的退单
     *
     * @param request
     * @return
     */
    @PostMapping("/order/${application.order.version}/find-pay-trade-record-by-businessId-status")
    BaseResponse<PayTradeRecordResponse> findTopByBusinessIdAndStatus(@RequestBody PayTradeRecordByParamsRequest request);
}
