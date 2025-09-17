package com.wanmi.sbc.order.api.provider.paytraderecord;


import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.order.api.request.paytraderecord.PayTradeRecordDeleteAndSaveRequest;
import com.wanmi.sbc.order.api.request.paytraderecord.PayTradeRecordRequest;
import com.wanmi.sbc.order.api.response.paytraderecord.PayTradeRecordResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;
import java.util.List;

@FeignClient(value = "${application.order.name}", contextId = "PayTradeRecordProvider")
public interface PayTradeRecordProvider {

    /**
     * 同步回调添加交易数据
     *
     * @param request
     * @return
     */
    @PostMapping("/order/${application.order.version}/add-pay-record-for-call-back")
    BaseResponse addPayRecordForCallBack(@RequestBody PayTradeRecordRequest request);

    /**
     * 批量添加交易记录
     *
     * @param payTradeRecordRequestList
     * @return
     */
    @PostMapping("/order/${application.order.version}/batch-save-pay-trade-record")
    BaseResponse batchSavePayTradeRecord(@RequestBody List<PayTradeRecordRequest> payTradeRecordRequestList);

    /**
     * 添加交易记录
     *
     * @param payTradeRecordRequest
     * @return
     */
    @PostMapping("/order/${application.order.version}/query-and-save-pay-trade-record")
    BaseResponse queryAndSave(@RequestBody @Valid PayTradeRecordRequest payTradeRecordRequest);

    /**
     * 添加交易记录
     *
     * @param request
     * @return
     */
    @PostMapping("/order/${application.order.version}/delete-and-save-pay-trade-record")
    BaseResponse<PayTradeRecordResponse> deleteAndSave(@RequestBody @Valid PayTradeRecordDeleteAndSaveRequest request);

    /**
     * 添加交易记录
     *
     * @param request
     * @return
     */
    @PostMapping("/order/${application.order.version}/save-and-flush-pay-trade-record")
    BaseResponse<PayTradeRecordResponse> saveAndFlush(@RequestBody @Valid PayTradeRecordRequest request);

    /**
     * 删除交易记录
     *
     * @param request
     * @return
     */
    @PostMapping("/order/${application.order.version}/delete-by-pay-no")
    BaseResponse deleteByPayNo(@RequestBody @Valid PayTradeRecordRequest request);



//    /**
//     * 添加交易记录
//     *
//     * @param request
//     * @return
//     */
//    @PostMapping("/order/${application.order.version}/save-pay-trade-record")
//    BaseResponse save(@RequestBody @Valid PayTradeRecordRequest request);
}
