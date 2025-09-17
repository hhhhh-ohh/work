package com.wanmi.sbc.customer.api.request.ledgerreceiverrel;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import lombok.*;

import java.util.List;

/**
 * @author xuyunpeng
 * @className DistributionBindStateRequest
 * @description
 * @date 2022/7/19 1:51 PM
 **/
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DistributionBindStateRequest extends BaseRequest {
    private static final long serialVersionUID = -7076401445526494531L;

    /**
     * 会员id
     */
    @NotBlank
    @Schema(description = "会员id")
    private String customerId;

    @Schema(description = "店铺ids")
    @NotEmpty
    private List<Long> storeIds;
}
