package com.wanmi.sbc.third.login.request;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.third.login.TerminalStringType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import lombok.Data;

@Schema
@Data
public class WechatBindForLoginRequest extends BaseRequest {

    /**
     *  验证码
     */
    @Schema(description = "验证码")
    private String verifyCode;

    /**
     *   手机号
     */
    @Schema(description = "手机号")
    @NotBlank
    @Pattern(regexp = "^1\\d{10}$")
    private String phone;

    /**
     *  id（微信id）
     */
    @Schema(description = "id（微信id）")
    @NotBlank
    private String id;

    /**
     * 邀请人id
     */
    @Schema(description = "邀请人id")
    private String inviteeId;

    @Schema(description = "分享人用户id")
    private String shareUserId;

    /**
     * 邀请码
     */
    @Schema(description = "邀请码")
    private String inviteCode;


    /**
     * 类型终端
     */
    @Schema(description = "类型终端")
    @NotNull
    private TerminalStringType channel;

    /**
     * 店铺Id
     */
    @Schema(description = "店铺Id")
    private Long storeId;

}
