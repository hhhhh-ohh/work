package com.wanmi.sbc.customer.api.request.customer;

import com.wanmi.sbc.common.base.BaseQueryRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerListCustomerIdByPageableRequest extends BaseQueryRequest {
    private static final long serialVersionUID = 1L;

    /**
     * 会员等级ID集合
     */
    @Schema(description = "会员等级ID集合")
    private List<Long> customerLevelIds;
}
