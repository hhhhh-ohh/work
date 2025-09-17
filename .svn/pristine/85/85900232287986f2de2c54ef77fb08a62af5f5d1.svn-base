package com.wanmi.sbc.elastic.provider.impl.orderinvoice;

import com.wanmi.sbc.common.base.BaseQueryResponse;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.elastic.api.provider.orderinvoice.EsOrderInvoiceQueryProvider;
import com.wanmi.sbc.elastic.api.request.orderinvoice.EsOrderInvoiceFindAllRequest;
import com.wanmi.sbc.elastic.api.response.orderinvoice.EsOrderInvoiceResponse;
import com.wanmi.sbc.elastic.orderinvoice.service.EsOrderInvoiceQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * @author houshuai
 * @date 2020/12/29 19:14
 * @description <p> 订单开票 </p>
 */
@RestController
public class EsOrderInvoiceQueryController implements EsOrderInvoiceQueryProvider {

    @Autowired
    private EsOrderInvoiceQueryService esOrderInvoiceQueryService;

    /**
     * 订单开票分页查询
     * @param request  {@link EsOrderInvoiceFindAllRequest}
     * @return
     */
    @Override
    public BaseResponse<BaseQueryResponse<EsOrderInvoiceResponse>> findOrderInvoicePage(@RequestBody @Valid EsOrderInvoiceFindAllRequest request) {
        BaseQueryResponse<EsOrderInvoiceResponse> orderInvoicePage = esOrderInvoiceQueryService.findOrderInvoicePage(request);

        return BaseResponse.success(orderInvoicePage);
    }
}
