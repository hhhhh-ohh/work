package com.wanmi.sbc.customer.api.request.detail;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;
import java.util.List;

/**
 * 根据会员ID集合查询会员详情集合
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDetailListByCustomerIdsRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;
    /**
     * 批量多个会员ID
     */
    @Schema(description = "批量多个会员ID")
    @NotEmpty
    private List<String> customerIds;


}
