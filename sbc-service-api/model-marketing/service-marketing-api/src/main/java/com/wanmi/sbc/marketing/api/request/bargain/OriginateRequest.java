package com.wanmi.sbc.marketing.api.request.bargain;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OriginateRequest implements Serializable {


    @Schema(description = "用户id")
    private String customerId;

    @Schema(description = "用户账号")
    private String customerAccount;

    /**
     * 砍价活动id
     */
    @Schema(description = "砍价活动id", required = true)
    @NotNull
    private Long bargainGoodsId;

}
