package com.wanmi.sbc.order.provider.impl.paytraderecord;


import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.order.api.provider.paytraderecord.PayTradeRecordProvider;
import com.wanmi.sbc.order.api.request.paytraderecord.PayTradeRecordDeleteAndSaveRequest;
import com.wanmi.sbc.order.api.request.paytraderecord.PayTradeRecordRequest;
import com.wanmi.sbc.order.api.response.paytraderecord.PayTradeRecordResponse;
import com.wanmi.sbc.order.paytraderecord.model.root.PayTradeRecord;
import com.wanmi.sbc.order.paytraderecord.service.PayTradeRecordService;
import com.wanmi.sbc.order.util.GeneratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;

@Validated
@RestController
public class PayTradeRecordController implements PayTradeRecordProvider {

    @Autowired
    private PayTradeRecordService payTradeRecordService;

    /**
     * 微信支付 同步回调添加交易数据
     *
     * @param request
     * @return
     */
    @Override
    public BaseResponse addPayRecordForCallBack(@RequestBody PayTradeRecordRequest request) {
        PayTradeRecord payTradeRecord = payTradeRecordService.queryByBusinessId(request.getBusinessId());
        if (payTradeRecord == null) {
            payTradeRecord = new PayTradeRecord();
            payTradeRecord.setId(GeneratorUtils.generatePT());
        }
        if (request.getChannelItemId() != null) {
            //更新支付记录支付项字段
            payTradeRecord.setChannelItemId(request.getChannelItemId());
        }
        payTradeRecordService.addPayRecordForCallBack(request, payTradeRecord);
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse batchSavePayTradeRecord(@RequestBody List<PayTradeRecordRequest> payTradeRecordRequestList) {
        payTradeRecordRequestList.forEach(
                recordRequest -> payTradeRecordService.addPayTradeRecord(recordRequest));
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 添加交易记录
     *
     * @param payTradeRecordRequest
     * @return
     */
    @Override
    public BaseResponse queryAndSave(@Valid PayTradeRecordRequest payTradeRecordRequest) {
        payTradeRecordService.queryAndSave(payTradeRecordRequest);
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 添加交易记录
     *
     * @param request
     * @return
     */
    @Override
    public BaseResponse<PayTradeRecordResponse> deleteAndSave(@Valid PayTradeRecordDeleteAndSaveRequest request) {
        return BaseResponse.success(payTradeRecordService.deleteAndSave(request));
    }

    /**
     * 添加交易记录
     *
     * @param request
     * @return
     */
    @Override
    public BaseResponse<PayTradeRecordResponse> saveAndFlush(@Valid PayTradeRecordRequest request) {
        payTradeRecordService.saveAndFlush(request);
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse deleteByPayNo( @Valid PayTradeRecordRequest request){
        payTradeRecordService.deleteByPayNo(request.getPayNo());
        return BaseResponse.SUCCESSFUL();
    }


    /**
     * 添加交易记录
     *
     * @param request
     * @return
     */
//    @Override
//    public BaseResponse save(@RequestBody @Valid PayTradeRecordRequest request) {
//        payTradeRecordService.save(request);
//        return BaseResponse.SUCCESSFUL();
//    }
}
