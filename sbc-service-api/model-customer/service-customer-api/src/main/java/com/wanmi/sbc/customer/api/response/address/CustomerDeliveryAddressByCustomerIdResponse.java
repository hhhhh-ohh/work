package com.wanmi.sbc.customer.api.response.address;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author: wanggang
 * @createDate: 2018/12/5 11:30
 * @version: 1.0
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDeliveryAddressByCustomerIdResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    @Schema(description = "总数")
    private Integer result;
}
