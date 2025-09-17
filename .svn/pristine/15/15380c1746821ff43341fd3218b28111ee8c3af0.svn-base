package com.wanmi.sbc.customer.api.request.points;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerPointsShareRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 分享人id
     */
    @NotBlank
    @Schema(description = "分享人id")
    public String customerId;

    /**
     * token
     */
    @Schema(description = "token")
    public String token;

    /**
     * 分享id
     */
    @Schema(description = "分享id")
    public String shareId;
}
