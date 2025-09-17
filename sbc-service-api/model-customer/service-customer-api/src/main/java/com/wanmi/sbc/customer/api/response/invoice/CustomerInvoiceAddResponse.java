package com.wanmi.sbc.customer.api.response.invoice;

import com.wanmi.sbc.customer.bean.vo.CustomerInvoiceVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CustomerInvoiceAddResponse extends CustomerInvoiceVO {

    private static final long serialVersionUID = 1L;
}
