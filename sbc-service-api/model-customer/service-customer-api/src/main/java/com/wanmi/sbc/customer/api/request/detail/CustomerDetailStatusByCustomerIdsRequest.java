package com.wanmi.sbc.customer.api.request.detail;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;
import java.util.List;

/**
 * 根据customerid查询会员是否被禁用以及禁用原因
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDetailStatusByCustomerIdsRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;
    /**
     * 批量多个会员ID
     */
    @Schema(description = "批量多个会员ID")
    @NotEmpty
    private List<String> customerIds;


}
