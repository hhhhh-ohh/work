package com.wanmi.sbc.elastic.api.provider.customerInvoice;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.elastic.api.request.customerInvoice.EsCustomerInvoicePageRequest;
import com.wanmi.sbc.elastic.api.response.customerInvoice.EsCustomerInvoicePageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

@FeignClient(value = "${application.elastic.name}", contextId = "EsCustomerInvoiceQueryProvider")
public interface EsCustomerInvoiceQueryProvider {

    /**
     * 分页查询会员增票资质
     * @param customerInvoicePageRequest
     * @return
     */
    @PostMapping("/elastic/${application.elastic.version}/esCustomerInvoice/page")
    BaseResponse<EsCustomerInvoicePageResponse> page(@RequestBody @Valid EsCustomerInvoicePageRequest
                                                           customerInvoicePageRequest);
}
