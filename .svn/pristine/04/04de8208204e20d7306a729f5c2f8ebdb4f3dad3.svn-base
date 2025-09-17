package com.wanmi.sbc.empower.api.request.sms.aliyun;

import com.wanmi.sbc.empower.api.request.EmpowerBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.*;

import org.hibernate.validator.constraints.NotBlank;

/**
 * <p>短信签名删除参数</p>
 *
 * @author lvzhenwei
 * @date 2019-12-03 15:49:24
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SmsSignAliyunDeleteRequest extends EmpowerBaseRequest {
    private static final long serialVersionUID = 1L;

    /**
     * 短信配置id
     */
    @Schema(description = "短信配置id")
    private Long smsSettingId;

    /**
     * 签名名称
     */
    @Schema(description = "签名名称")
    @NotBlank
    private String signName;

}