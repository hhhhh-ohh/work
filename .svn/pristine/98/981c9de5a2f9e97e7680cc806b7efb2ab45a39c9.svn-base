package com.wanmi.sbc.order.provider.impl.paytraderecord;


import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.order.api.provider.paytraderecord.PayTradeRecordQueryProvider;
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
import com.wanmi.sbc.order.bean.vo.PayTradeRecordVO;
import com.wanmi.sbc.order.paytraderecord.model.root.PayTradeRecord;
import com.wanmi.sbc.order.paytraderecord.service.PayTradeRecordService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.Collections;
import java.util.List;

@Validated
@RestController
public class PayTradeRecordQueryController implements PayTradeRecordQueryProvider {

    @Autowired
    private PayTradeRecordService payTradeRecordService;

    @Override
    public BaseResponse<PayTradeRecordResponse> getTradeRecordById(@RequestBody @Valid TradeRecordByIdRequest
                                                                           tradeRecordByIdRequest) {
        PayTradeRecord payTradeRecord = payTradeRecordService.queryTradeRecord(tradeRecordByIdRequest.getRecodId());
        return BaseResponse.success(wrapperResponseForRecord(payTradeRecord));
    }

    @Override
    public BaseResponse<PayTradeRecordResponse> getTradeRecordByChargeId(@RequestBody @Valid TradeRecordByChargeRequest
                                                                                 recordByChangeRequest) {
        PayTradeRecord payTradeRecord = payTradeRecordService.queryByChargeId(recordByChangeRequest.getChargeId());
        return BaseResponse.success(wrapperResponseForRecord(payTradeRecord));
    }

    @Override
    public BaseResponse<PayTradeRecordResponse> getTradeRecordByOrderCode(@RequestBody @Valid
                                                                                  TradeRecordByOrderCodeRequest
                                                                                  recordByOrderCodeRequest) {
        PayTradeRecord payTradeRecord = payTradeRecordService.queryByBusinessId(recordByOrderCodeRequest.getOrderId());
        return BaseResponse.success(wrapperResponseForRecord(payTradeRecord));
    }

    /***
     * 根据业务ID（订单号或退单号）
     * 返回<businessId:tradeNo> 格式Map
     * 如果参数为空或返回值为空，返回空Map，不会抛出异常
     * @param request           业务ID集合
     * @return                  业务ID:业务流水号 Map
     */
    @Override
    public BaseResponse<PayTradeNoMapResponse> queryTradeNoMapByBusinessIds(TradeNoByBusinessIdRequest request) {
        return BaseResponse.success(PayTradeNoMapResponse.builder().tradeNoMap(payTradeRecordService
                .queryTradeNoMapByBusinessIds(request.getBusinessIdList())).build());
    }

    @Override
    public BaseResponse<PayTradeRecordResponse> getTradeRecordByOrderOrParentCode(
            @RequestBody @Valid TradeRecordByOrderOrParentCodeRequest tradeRecordByOrderOrParentCodeRequest) {
        PayTradeRecord payTradeRecord = null;
        if (StringUtils.isNotBlank(tradeRecordByOrderOrParentCodeRequest.getOrderId())) {
            payTradeRecord = payTradeRecordService.queryByBusinessId(tradeRecordByOrderOrParentCodeRequest.getOrderId());
        }
        if (payTradeRecord == null && StringUtils.isNotBlank(tradeRecordByOrderOrParentCodeRequest.getParentId())) {
            payTradeRecord = payTradeRecordService.queryByBusinessId(tradeRecordByOrderOrParentCodeRequest.getParentId());
        }
        return BaseResponse.success(wrapperResponseForRecord(payTradeRecord));
    }

    @Override
    public BaseResponse<PayTradeRecordCountResponse> getTradeRecordCountByOrderOrParentCode(
            @RequestBody @Valid TradeRecordCountByOrderOrParentCodeRequest tradeRecordCountByOrderOrParentCodeRequest) {
        long count = 0L;
        if (StringUtils.isNotBlank(tradeRecordCountByOrderOrParentCodeRequest.getOrderId())) {
            count = payTradeRecordService.countByBusinessId(tradeRecordCountByOrderOrParentCodeRequest.getOrderId());
        }
        if (count == 0L && StringUtils.isNotBlank(tradeRecordCountByOrderOrParentCodeRequest.getParentId())) {
            count = payTradeRecordService.countByBusinessId(tradeRecordCountByOrderOrParentCodeRequest.getParentId());
        }
        return BaseResponse.success(new PayTradeRecordCountResponse(count));
    }

    @Override
    public BaseResponse<PayTradeRecordCountResponse> getTradeRecordCountByOrderCode
            (@RequestBody @Valid TradeRecordCountByOrderCodeRequest recordCountByOrderCodeRequest) {
        long count = payTradeRecordService.countByBusinessId(recordCountByOrderCodeRequest.getOrderId());
        return BaseResponse.success(new PayTradeRecordCountResponse(count));
    }

    @Override
    public BaseResponse<PayResultResponse> getPayResponseByOrdercode(@RequestBody @Valid PayResultByOrdercodeRequest payResultByOrdercodeRequest) {
        return BaseResponse.success(new PayResultResponse(payTradeRecordService.queryPayResult(payResultByOrdercodeRequest
                .getOrderCode())));
    }

    @Override
    public BaseResponse<RefundResultResponse> getRefundResponseByOrdercode(@RequestBody @Valid RefundResultByOrdercodeRequest refundResultByOrdercodeRequest) {

        return BaseResponse.success(new RefundResultResponse(payTradeRecordService.queryRefundResult
                (refundResultByOrdercodeRequest.getReturnOrderCode(), refundResultByOrdercodeRequest.getOrderCode())));
    }

    @Override
    public BaseResponse<PayTradeRecordCreditStatisticsResponse> getPayTradeRecordCreditStatistics() {
        return BaseResponse.success(payTradeRecordService.getPayTradeRecordCreditStatistics());
    }

    @Override
    public BaseResponse<FindPayTradeRefundResponse> findPayTradeRecords(@RequestBody @Valid FundPayTradeRecordsRefundRequest request) {
        List<PayTradeRecordVO> payTradeRecordList = payTradeRecordService.queryByBusinessIds(request.getBusinessIds());
        if (CollectionUtils.isEmpty(payTradeRecordList)) {
            return BaseResponse.success(FindPayTradeRefundResponse.builder().payTradeRecordList(Collections.emptyList()).build());
        }
        return BaseResponse.success(FindPayTradeRefundResponse.builder().payTradeRecordList(payTradeRecordList).build());
    }

    /**
     * @description 封装交易记录Response
     * @author  songhanlin
     * @date: 2021/6/3 上午11:34
     * @param record 交易记录
     * @return 交易记录Response
     **/
    private PayTradeRecordResponse wrapperResponseForRecord(PayTradeRecord record) {
        if (record != null) {
            PayTradeRecordResponse response = new PayTradeRecordResponse();
            KsBeanUtil.copyPropertiesThird(record, response);
            return response;
        }
        return null;
    }

    @Override
    public BaseResponse<PayTradeRecordResponse> findTopByBusinessIdAndStatus(@RequestBody PayTradeRecordByParamsRequest request) {
        PayTradeRecord payTradeRecord = payTradeRecordService.findTopByBusinessIdAndStatus(request.getBusinessId(),
                request.getTradeStatus());
        return BaseResponse.success(wrapperResponseForRecord(payTradeRecord));
    }

}
