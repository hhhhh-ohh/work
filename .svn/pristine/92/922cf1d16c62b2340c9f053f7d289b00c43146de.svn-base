package com.wanmi.sbc.elastic.provider.impl.orderinvoice;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.elastic.api.provider.orderinvoice.EsOrderInvoiceProvider;
import com.wanmi.sbc.elastic.api.request.orderinvoice.*;
import com.wanmi.sbc.elastic.orderinvoice.service.EsOrderInvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * @author houshuai
 * @date 2020/12/30 13:58
 * @description <p> 订单开票 </p>
 */
@RestController
public class EsOrderInvoiceController implements EsOrderInvoiceProvider {

    @Autowired
    private EsOrderInvoiceService esOrderInvoiceService;

    /**
     * 新增开票信息
     * @param request {@link EsOrderInvoiceGenerateRequest}
     * @return BaseResponse
     */
    @Override
    public BaseResponse addEsOrderInvoice(@RequestBody @Valid EsOrderInvoiceGenerateRequest request) {
        esOrderInvoiceService.addEsOrderInvoice(request);
        return BaseResponse.SUCCESSFUL();
    }

    /**
     *修改订单开票信息
     * @param stateRequest {@link EsOrderInvoiceModifyStateRequest}
     * @return
     */
    @Override
    public BaseResponse modifyState(@RequestBody @Valid EsOrderInvoiceModifyStateRequest stateRequest) {
        esOrderInvoiceService.modifyState(stateRequest);
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 初始化订单开票信息
     * @param invoiceRequest
     * @return
     */
    @Override
    public BaseResponse init(@RequestBody @Valid EsOrderInvoiceInitRequest initRequest) {
        esOrderInvoiceService.init(initRequest);
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse delete(@Valid EsOrderInvoiceDeleteRequest request) {
        esOrderInvoiceService.delete(request);
        return BaseResponse.SUCCESSFUL();
    }
}
