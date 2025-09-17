package com.wanmi.sbc.customer.api.response.loginregister;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 会员登录注册-发送手机验证码Request
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerSendMobileCodeResponse extends BasicResponse {

    private static final long serialVersionUID = 1L;

    @Schema(description = "验证码")
    private Integer result;
}
