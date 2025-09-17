package com.wanmi.sbc.crm.api.request.rfmstatistic;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * <p>根据customerId查询最近一天的rfm会员统计明细request</p>
 * @author dyt
 * @date 2019-11-12 14:49:08
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RfmCustomerDetailByCustomerIdRequest extends BaseRequest {

    /**
     * 会员id
     */
    @Schema(description = "会员id", required = true)
    @NotBlank
    private String customerId;
}
