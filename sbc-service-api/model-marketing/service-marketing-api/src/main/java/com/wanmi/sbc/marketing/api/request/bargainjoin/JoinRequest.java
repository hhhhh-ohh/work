package com.wanmi.sbc.marketing.api.request.bargainjoin;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class JoinRequest implements Serializable {

    @Schema(description = "用户id")
    private String customerId;

    @Schema(description = "砍价id", required = true)
    @NotNull
    private Long bargainId;

}
