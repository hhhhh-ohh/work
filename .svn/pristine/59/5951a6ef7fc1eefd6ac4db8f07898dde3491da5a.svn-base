package com.wanmi.sbc.empower.api.request.sms.aliyun;

import com.wanmi.sbc.empower.api.request.EmpowerBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
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
public class SmsTemplateAliyunDeleteRequest extends EmpowerBaseRequest {
    private static final long serialVersionUID = 1L;

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