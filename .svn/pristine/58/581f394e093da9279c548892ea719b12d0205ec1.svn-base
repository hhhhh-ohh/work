package com.wanmi.sbc.elastic.api.provider.orderinvoice;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.elastic.api.request.orderinvoice.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * @author houshuai
 * @date 2020/12/29 18:15
 * @description <p> 订单开票 </p>
 */
@FeignClient(value = "${application.elastic.name}", contextId = "EsOrderInvoiceProvider")
public interface EsOrderInvoiceProvider {

    /**
     * 新增开票信息
     *
     * @param request {@link EsOrderInvoiceGenerateRequest}
     */
    @PostMapping("/elastic/${application.elastic.version}/invoice/add-order-invoice")
    BaseResponse addEsOrderInvoice(@RequestBody @Valid EsOrderInvoiceGenerateRequest request);


    /**
     * 批量开票/单个开票
     *
     * @param stateRequest {@link EsOrderInvoiceModifyStateRequest}
     */
    @PostMapping("/elastic/${application.elastic.version}/invoice/update-order-invoice")
    BaseResponse modifyState(@RequestBody @Valid EsOrderInvoiceModifyStateRequest stateRequest);


    /**
     * 批量开票/单个开票
     *
     * @param request {@link EsOrderInvoiceInitRequest}
     */
    @PostMapping("/elastic/${application.elastic.version}/invoice/init")
    BaseResponse init(@RequestBody @Valid EsOrderInvoiceInitRequest request);

    /**
     * 删除订单开票信息
     *
     * @param request {@link EsOrderInvoiceDeleteRequest}
     */
    @PostMapping("/elastic/${application.elastic.version}/invoice/delete-order-invoice")
    BaseResponse delete(@RequestBody @Valid EsOrderInvoiceDeleteRequest request);

}
