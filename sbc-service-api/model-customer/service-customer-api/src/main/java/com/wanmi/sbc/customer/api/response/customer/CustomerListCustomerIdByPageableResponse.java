package com.wanmi.sbc.customer.api.response.customer;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerListCustomerIdByPageableResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 会员ID集合
     */
    @Schema(description = "会员ID集合")
    private List<String> customerIds;
}
