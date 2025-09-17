package com.wanmi.sbc.marketing.api.request.giftcard;


import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @description 注销用户名下所有礼品卡
 * @author malianfeng
 * @date 2022/12/26 14:05
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GiftCardCustomerCancelRequest implements Serializable {
    private static final long serialVersionUID = 5036585577605234040L;

    /**
     * 用户id
     */
    @Schema(description = "用户id")
    @NotBlank
    private String customerId;
}
