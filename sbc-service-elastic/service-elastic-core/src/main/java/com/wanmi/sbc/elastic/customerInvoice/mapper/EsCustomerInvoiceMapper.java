package com.wanmi.sbc.elastic.customerInvoice.mapper;

import com.wanmi.sbc.customer.api.request.invoice.CustomerInvoicePageRequest;
import com.wanmi.sbc.customer.bean.vo.CustomerInvoiceVO;
import com.wanmi.sbc.elastic.api.request.customerInvoice.EsCustomerInvoiceAddRequest;
import com.wanmi.sbc.elastic.api.request.customerInvoice.EsCustomerInvoiceModifyRequest;
import com.wanmi.sbc.elastic.api.request.customerInvoice.EsCustomerInvoicePageRequest;
import com.wanmi.sbc.elastic.customerInvoice.model.root.EsCustomerInvoice;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring",builder = @Builder(disableBuilder = true))
public interface EsCustomerInvoiceMapper {

    @Mappings({})
    CustomerInvoiceVO esCustomerInvoiceToCustomerInvoiceVO(EsCustomerInvoice esCustomerInvoice);

    @Mappings({})
    EsCustomerInvoice customerInvoiceVOToEsCustomerInvoice(CustomerInvoiceVO customerInvoiceVO);

    List<EsCustomerInvoice> customerInvoiceVOListToEsCustomerInvoiceList(List<CustomerInvoiceVO> customerInvoiceVOs);

    @Mappings({})
    CustomerInvoicePageRequest esCustomerInvoicePageRequestToCustomerInvoicePageRequest(EsCustomerInvoicePageRequest esCustomerInvoicePageRequest);

    @Mappings({})
    EsCustomerInvoice esCustomerInvoiceAddRequestToEsCustomerInvoice(EsCustomerInvoiceAddRequest esCustomerInvoiceAddRequest);

    @Mappings({})
    EsCustomerInvoice esCustomerInvoiceModifyRequestToEsCustomerInvoice(EsCustomerInvoiceModifyRequest esCustomerInvoiceModifyRequest);
}
