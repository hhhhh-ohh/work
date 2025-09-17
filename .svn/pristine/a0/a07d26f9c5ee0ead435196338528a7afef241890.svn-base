package com.wanmi.sbc.empower.api.request.sms.aliyun;

import com.wanmi.sbc.empower.api.request.EmpowerBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

import org.hibernate.validator.constraints.NotBlank;

/**
 * <p>阿里云短信模板修改参数</p>
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
public class SmsTemplateAliyunModifyRequest extends EmpowerBaseRequest {
    private static final long serialVersionUID = 1L;

    /**
     * 短信类型。其中： 0：验证码。 1：短信通知。 2：推广短信。 短信类型,0：验证码,1：短信通知,2：推广短信,3：国际/港澳台消息。
     */
    @Schema(description = "短信类型。其中： 0：验证码。 1：短信通知。 2：推广短信。 短信类型,0：验证码,1：短信通知,2：推广短信,3：国际/港澳台消息。")
    @NotNull
    private Integer templateType;

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
     * 短信模板CODE
     */
    @Schema(description = "短信模板CODE")
    @NotBlank
    private String TemplateCode;

    /**
     * 短信配置id
     */
    @Schema(description = "短信配置id")
    private Long smsSettingId;

}