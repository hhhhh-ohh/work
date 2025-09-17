package com.wanmi.sbc.customer.response;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 会员绑定手机号返回
 * Created by CHENLI on 2017/7/22.
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerSafeResponse extends BasicResponse {

    /**
     * 会员账号，绑定手机号
     */
    @Schema(description = "会员账号，绑定手机号")
    private String customerAccount;

    /**
     * 密码安全级别
     */
    @Schema(description = "密码安全级别")
    private Integer safeLevel;

    /**
     * 支付密码的安全级别
     */
    @Schema(description = "支付密码的安全级别")
    private Integer paySafeLevel;
}
