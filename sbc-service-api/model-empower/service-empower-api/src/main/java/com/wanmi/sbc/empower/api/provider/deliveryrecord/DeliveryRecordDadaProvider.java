package com.wanmi.sbc.empower.api.provider.deliveryrecord;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.empower.api.request.deliveryrecord.DadaMessageRiderCancelRequest;
import com.wanmi.sbc.empower.api.request.deliveryrecord.DeliveryRecordDadaAddRequest;
import com.wanmi.sbc.empower.api.request.deliveryrecord.DeliveryRecordDadaCallBackRequest;
import com.wanmi.sbc.empower.api.request.deliveryrecord.DeliveryRecordDadaCancelByIdRequest;
import com.wanmi.sbc.empower.api.request.deliveryrecord.DeliveryRecordDadaFaultConfirmRequest;
import com.wanmi.sbc.empower.api.response.deliveryrecord.DeliveryRecordDadaAddResponse;
import com.wanmi.sbc.empower.api.response.deliveryrecord.DeliveryRecordDadaModifyResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>达达配送记录保存服务Provider</p>
 *
 * @author zhangwenchang
 */
@FeignClient(value = "${application.empower.name}", contextId = "DeliveryRecordDadaProvider")
public interface DeliveryRecordDadaProvider {

    /**
     * 订单下单
     *
     * @param request 达达配送记录下单参数结构 {@link DeliveryRecordDadaAddRequest}
     * @return 新增的达达配送记录信息 {@link DeliveryRecordDadaAddResponse}
     * @author dyt
     */
    @PostMapping("/empower/${application.empower.version}/delivery-record-dada/add")
    BaseResponse<DeliveryRecordDadaAddResponse> add(@RequestBody @Valid DeliveryRecordDadaAddRequest request);

    /**
     * 回调更新配送记录
     *
     * @param request 达达配送记录回调修改参数结构 {@link DeliveryRecordDadaCallBackRequest}
     * @return 修改的达达配送记录信息 {@link DeliveryRecordDadaModifyResponse}
     * @author dyt
     */
    @PostMapping("/empower/${application.empower.version}/delivery-record-dada/call-back")
    BaseResponse callBack(@RequestBody @Valid DeliveryRecordDadaCallBackRequest request);

    /**
     * 单个取消达达配送记录API
     *
     * @param request 单个取消参数结构 {@link DeliveryRecordDadaCancelByIdRequest}
     * @return 取消结果 {@link BaseResponse}
     * @author dyt
     */
    @PostMapping("/empower/${application.empower.version}/delivery-record-dada/cancel-by-id")
    BaseResponse cancelById(@RequestBody @Valid DeliveryRecordDadaCancelByIdRequest request);

    /**
     * 单个确认妥投达达配送记录API
     *
     * @param request 单个确认妥投参数结构 {@link DeliveryRecordDadaFaultConfirmRequest}
     * @return 取消结果 {@link BaseResponse}
     * @author dyt
     */
    @PostMapping("/empower/${application.empower.version}/delivery-record-dada/fault-confirm")
    BaseResponse faultConfirm(@RequestBody @Valid DeliveryRecordDadaFaultConfirmRequest request);

    /**
     * 骑手取消消息通知
     *
     * @param request 骑手取消消息通知参数结构 {@link DadaMessageRiderCancelRequest}
     * @return 取消结果 {@link BaseResponse}
     * @author dyt
     */
    @PostMapping("/empower/${application.empower.version}/delivery-record-dada/riderCancel")
    BaseResponse riderCancel(@RequestBody DadaMessageRiderCancelRequest request);
}

