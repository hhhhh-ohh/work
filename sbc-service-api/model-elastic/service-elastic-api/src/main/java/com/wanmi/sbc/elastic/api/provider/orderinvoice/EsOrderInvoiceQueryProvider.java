package com.wanmi.sbc.elastic.api.provider.orderinvoice;

import com.wanmi.sbc.common.base.BaseQueryResponse;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.elastic.api.request.orderinvoice.EsOrderInvoiceFindAllRequest;
import com.wanmi.sbc.elastic.api.response.orderinvoice.EsOrderInvoiceResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * @author houshuai
 * @date 2020/12/29 18:15
 * @description <p> 订单开票分页查询 </p>
 */
@FeignClient(value = "${application.elastic.name}", contextId = "EsOrderInvoiceQueryProvider")
public interface EsOrderInvoiceQueryProvider {

    /**
     * 查询所有
     * @param request  {@link EsOrderInvoiceFindAllRequest}
     * @return  开票信息 {@link EsOrderInvoiceResponse}
     */
    @PostMapping("/elastic/${application.elastic.version}/invoice/find-page")
    BaseResponse<BaseQueryResponse<EsOrderInvoiceResponse>> findOrderInvoicePage(@RequestBody @Valid EsOrderInvoiceFindAllRequest request);

}
