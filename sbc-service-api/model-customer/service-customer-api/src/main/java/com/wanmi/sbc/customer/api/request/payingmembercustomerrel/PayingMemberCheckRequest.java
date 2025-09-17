package com.wanmi.sbc.customer.api.request.payingmembercustomerrel;


import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author xuyunpeng
 * @className PayingMemberCheckRequest
 * @description
 * @date 2022/6/8 3:24 PM
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class PayingMemberCheckRequest implements Serializable {
    private static final long serialVersionUID = 1600560750508088538L;

    /**
     * 当前登陆用户
     */
    @Schema(description = "当前登陆用户")
    @NotBlank
    private String customerId;
}
