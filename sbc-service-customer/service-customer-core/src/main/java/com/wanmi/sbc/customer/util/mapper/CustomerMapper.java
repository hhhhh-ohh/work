package com.wanmi.sbc.customer.util.mapper;

import com.wanmi.sbc.customer.api.response.customer.CustomerGetByIdResponse;
import com.wanmi.sbc.customer.bean.vo.*;
import com.wanmi.sbc.customer.detail.model.root.CustomerDetail;
import com.wanmi.sbc.customer.detail.model.root.CustomerDetailInitEs;
import com.wanmi.sbc.customer.enterpriseinfo.model.root.EnterpriseInfo;
import com.wanmi.sbc.customer.model.root.Customer;
import com.wanmi.sbc.customer.storecustomer.root.StoreCustomerRela;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring",builder = @Builder(disableBuilder = true))
public interface CustomerMapper {

    @Mappings({})
    CustomerVO customerToCustomerVO(Customer bean);

    @Mappings({})
    CustomerGetByIdResponse customerToCustomerGetByIdResponse(Customer bean);
}
