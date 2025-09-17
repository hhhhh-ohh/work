package com.wanmi.sbc.elastic.provider.impl.customerInvoice;


import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.customer.bean.vo.CustomerInvoiceVO;
import com.wanmi.sbc.elastic.api.provider.customerInvoice.EsCustomerInvoiceQueryProvider;
import com.wanmi.sbc.elastic.api.request.customerInvoice.EsCustomerInvoicePageRequest;
import com.wanmi.sbc.elastic.api.response.customerInvoice.EsCustomerInvoicePageResponse;
import com.wanmi.sbc.elastic.customerInvoice.mapper.EsCustomerInvoiceMapper;
import com.wanmi.sbc.elastic.customerInvoice.model.root.EsCustomerInvoice;
import com.wanmi.sbc.elastic.customerInvoice.service.EsCustomerInvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@Validated
public class EsCustomerInvoiceQueryController implements EsCustomerInvoiceQueryProvider {

    @Autowired
    EsCustomerInvoiceService esCustomerInvoiceService;

    @Autowired
    EsCustomerInvoiceMapper esCustomerInvoiceMapper;

    @Override
    public BaseResponse<EsCustomerInvoicePageResponse> page(@RequestBody @Valid EsCustomerInvoicePageRequest customerInvoicePageRequest) {
        Page<EsCustomerInvoice> page = esCustomerInvoiceService.page(customerInvoicePageRequest);
        Page<CustomerInvoiceVO> pageVo = page.map(esCustomerInvoice -> esCustomerInvoiceMapper.esCustomerInvoiceToCustomerInvoiceVO(esCustomerInvoice));
        MicroServicePage<CustomerInvoiceVO> customerInvoiceVOPage = new MicroServicePage(pageVo,pageVo.getPageable());
        EsCustomerInvoicePageResponse esCustomerInvoicePageResponse = EsCustomerInvoicePageResponse.builder()
                .customerInvoiceVOPage(customerInvoiceVOPage)
                .build();
        return BaseResponse.success(esCustomerInvoicePageResponse);
    }
}
