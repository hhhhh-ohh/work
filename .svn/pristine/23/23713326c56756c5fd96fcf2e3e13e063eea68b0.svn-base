package com.wanmi.sbc.customer.request;

import com.wanmi.sbc.common.base.BaseRequest;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Data;

/**
 * 修改绑定手机号请求参数
 * Created by CHENLI on 2017/7/24.
 */
@Schema
@Data
public class CustomerMobileRequest extends BaseRequest {

    /**
     * 用户编号
     */
    @Schema(description = "客户详情id")
    private String customerId;

    /**
     * 账号
     */
    @Schema(description = "账号")
    private String customerAccount;

    /**
     * 验证码
     */
    @Schema(description = "验证码")
    private String verifyCode;

    /**
     * 旧验证码
     */
    @Schema(description = "旧验证码")
    private String oldVerifyCode;
}
