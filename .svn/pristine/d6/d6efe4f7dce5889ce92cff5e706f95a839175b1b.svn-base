package com.wanmi.sbc.message.api.request.smstemplate;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.message.api.request.SmsBaseRequest;
import com.wanmi.sbc.message.bean.enums.ReviewStatus;
import com.wanmi.sbc.message.bean.enums.SmsType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

import org.hibernate.validator.constraints.NotBlank;

import java.time.LocalDateTime;

/**
 * <p>短信模板修改参数</p>
 *
 * @author lvzhenwei
 * @date 2019-12-03 15:43:29
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SmsTemplateModifyRequest extends SmsBaseRequest {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Schema(description = "主键")
    @NotNull
    private Long id;

    /**
     * 模板名称
     */
    @Schema(description = "模板名称")
    @NotBlank
    private String templateName;

    /**
     * 模板内容
     */
    @Schema(description = "模板内容")
    @NotBlank
    private String templateContent;

    /**
     * 短信模板申请说明
     */
    @Schema(description = "短信模板申请说明")
    @NotBlank
    private String remark;

    /**
     * 短信类型。其中： 0：验证码。 1：短信通知。 2：推广短信。 短信类型,0：验证码,1：短信通知,2：推广短信,3：国际/港澳台消息。
     */
    @Schema(description = "短信类型。其中： 0：验证码。 1：短信通知。 2：推广短信。 短信类型,0：验证码,1：短信通知,2：推广短信,3：国际/港澳台消息。")
    @NotNull
    private SmsType templateType;

    /**
     * 模板状态，0：待审核，1：审核通过，2：审核未通过
     */
    @Schema(description = "模板状态，0：待审核，1：审核通过，2：审核未通过")
    private ReviewStatus reviewStatus;

    /**
     * 模板code,模板审核通过返回的模板code，发送短信时使用
     */
    @Schema(description = "模板code,模板审核通过返回的模板code，发送短信时使用")
    private String templateCode;

    /**
     * 审核原因
     */
    @Schema(description = "审核原因")
    private String reviewReason;

    /**
     * 短信配置id
     */
    @Schema(description = "短信配置id")
    private Long smsSettingId;

    /**
     * 删除标识位，0：未删除，1：已删除
     */
    @Schema(description = "删除标识位，0：未删除，1：已删除")
    private DeleteFlag delFlag;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    /**
     * 业务类型，发送短信时使用
     */
    @Schema(description = "业务类型")
    private String businessType;

    /**
     * 用途
     */
    @Schema(description = "用途")
    private String purpose;

    /**
     * 签名id
     */
    @Schema(description = "签名id")
    private Long signId;

    /**
     * 开关标识, 0:关,1:开
     */
    @Schema(description = "开关标识, 0:关,1:开")
    private Boolean openFlag;
}