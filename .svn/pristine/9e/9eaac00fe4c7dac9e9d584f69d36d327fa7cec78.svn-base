package com.wanmi.sbc.customer.api.request.invoice;


import com.wanmi.sbc.customer.bean.dto.CustomerInvoiceDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

/**
 * 会员增专票
 * Created by CHENLI on 2017/4/21.
 */
@Schema
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CustomerInvoiceModifyRequest extends CustomerInvoiceDTO implements Serializable {


    private static final long serialVersionUID = 1L;

    @Schema(description = "负责业务员")
    private String employeeId;
}
