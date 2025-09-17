package com.wanmi.sbc.customer.api.request.invoice;


import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;


import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhangjin on 2017/5/4.
 */
@Schema
@Data
public class CustomerInvoiceDeleteOneRequest extends CustomerBaseRequest implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 专票ids
     */
    @Schema(description = "发票id")
    private Long customerInvoiceId;

    private String customerId;
}
