package com.wanmi.sbc.third.login.request;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.third.login.TerminalStringType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Data;

/**
 * Created by gaomuwei on 2018/8/16.
 */
@Schema
@Data
public class WechatQuickLoginRequest extends BaseRequest {

    /**
     * 授权临时票据
     */
    @Schema(description = "授权临时票据")
    @NotBlank
    private String code;

    /**
     * 类型终端
     */
    @Schema(description = "类型终端")
    @NotNull
    private TerminalStringType channel;

    /**
     * 微信登录appId
     */
    @Schema(description = "微信登录appId")
    private String appId;

    /**
     * 微信登录appSecret
     */
    @Schema(description = "微信登录appSecret")
    private String appSecret;

    /**
     * 小程序登录，unionId可从前台传过来
     */
    @Schema(description = "微信用户唯一标识")
    private String unionId;

    /**
     * 小程序端的手机号，由前台直接传过来
     */
    @Schema(description = "小程序端获取手机号")
    private String phonNumber;

    /**
     * 昵称
     */
    @Schema(description = "昵称")
    private String nickName;

    /**
     * 头像
     */
    @Schema(description = "头像")
    private String headimgurl;

    /**
     * 小程序openId
     */
    @Schema(description = "小程序openId")
    private String openId;


    @Override
    public void checkParam() {
//        if(TerminalStringType.APP == channel) {
//            Validate.notBlank(appId, ValidateUtil.BLANK_EX_MESSAGE, "appId");
//            Validate.notBlank(appSecret, ValidateUtil.BLANK_EX_MESSAGE, "appSecret");
//        }
    }

    /**
     * 邀请码
     */
    @Schema(description = "邀请码")
    private String inviteCode;

    /**
     * 邀请人ID
     */
    @Schema(description = "邀请人ID")
    private String inviteeId;

    @Schema(description = "解密密钥")
    private String iv;

    @Schema(description = "微信加密数据")
    private String encryptedData;
}
