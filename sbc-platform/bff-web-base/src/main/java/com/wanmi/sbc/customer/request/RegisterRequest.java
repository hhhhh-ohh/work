package com.wanmi.sbc.customer.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.GenderType;
import com.wanmi.sbc.common.enums.TerminalType;
import com.wanmi.sbc.common.util.CustomLocalDateDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateSerializer;
import com.wanmi.sbc.customer.validGroups.*;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Created by dyt on 2017/7/11.
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest extends BaseRequest {

    /**
     * 用户编号
     */
    @Schema(description = "用户编号")
    @NotBlank(groups = {NotCustomerId.class})
    private String customerId;

    /**
     * 账号
     */
    @Schema(description = "账号")
    @NotBlank(groups = {NotCustomerAccount.class})
    private String customerAccount;

    /**
     * 密码
     */
    @Schema(description = "密码")
    @NotBlank(groups = {NotPassword.class})
    private String customerPassword;

    /**
     * 验证码
     */
    @Schema(description = "验证码")
    @NotBlank(groups = {NotVerify.class})
    private String verifyCode;

    /**
     * 会员名称
     */
    @Schema(description = "会员名称")
    private String customerName;

    /**
     * 省
     */
    @Schema(description = "省")
    private Long provinceId;

    /**
     * 市
     */
    @Schema(description = "市")
    private Long cityId;

    /**
     * 区
     */
    @Schema(description = "区")
    private Long areaId;

    /**
     * 街道
     */
    @Schema(description = "区")
    private Long streetId;

    /**
     * 详细地址
     */
    @Schema(description = "详细地址")
    private String customerAddress;

    /**
     * 联系人名字
     */
    @Schema(description = "联系人名字")
    private String contactName;

    /**
     * 联系方式
     */
    @Schema(description = "联系方式")
    private String contactPhone;

    /**
     * 是否是忘记密码 true：忘记密码 | false：
     */
    @Schema(description = "是否是忘记密码")
    private Boolean isForgetPassword;

    /**
     * 图片验证码
     */
    @Schema(description = "图片验证码")
    @NotBlank(groups = NotPatchca.class)
    private String patchca;

    /**
     * 图片验证码的key
     */
    @Schema(description = "图片验证码的key")
    @NotBlank(groups = NotPatchca.class)
    private String uuid;

    /**
     * 业务员id
     */
    @Schema(description = "业务员id")
    private String employeeId;

    /**
     * 邀请人id
     */
    @Schema(description = "邀请人id")
    private String inviteeId;

    /**
     * 分享人id
     */
    @Schema(description = "分享人id")
    private String shareUserId;

    /**
     * 团长id
     */
    @Schema(description = "团长id")
    private String leaderId;

    /**
     * 社区团购活动id
     */
    @Schema(description = "社区团购活动id")
    private String activityId;

    /**
     * 邀请码
     */
    @Schema(description = "邀请码")
    private String inviteCode;

    /**
     * 公司性质
     */
    @Schema(description = "公司性质")
    private Integer businessNatureType;

    /**
     * 公司名称
     */
    @Schema(description = "企业名称")
    private String enterpriseName;

    /**
     * 统一社会信用代码
     */
    @Schema(description = "统一社会信用代码")
    private String socialCreditCode;

    /**
     * 营业执照地址
     */
    @Schema(description = "营业执照地址")
    private String businessLicenseUrl;

    /**
     * 企业会员是否第一次点击注册
     */
    @Schema(description = "企业会员是否第一次点击注册")
    private Boolean firstRegisterFlag;

    /**
     * 终端类型，用于区分三端不同逻辑处理  PC:0 H5:1 APP:2
     */
    @Schema(description = "渠道终端")
    private TerminalType terminalType;


    /**
     * 生日
     */
    @Schema(description = "生日")
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @JsonDeserialize(using = CustomLocalDateDeserializer.class)
    private LocalDate birthDay;

    /**
     * 性别，0女，1男
     */
    @Schema(description = "性别，0女，1男")
    private GenderType gender;
}
