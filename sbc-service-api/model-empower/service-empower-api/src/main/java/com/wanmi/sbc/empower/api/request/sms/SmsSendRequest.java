package com.wanmi.sbc.empower.api.request.sms;

import com.wanmi.sbc.empower.api.request.EmpowerBaseRequest;
import com.wanmi.sbc.empower.bean.enums.SmsPlatformType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.*;

import java.util.Map;

/**
 * <p>短信发送请求参数</p>
 * @author dyt
 * @date 2019-12-03 15:36:05
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SmsSendRequest extends EmpowerBaseRequest {
	private static final long serialVersionUID = 1L;

    /**
     * 短信平台类型：0：阿里云短信平台
     */
    @Schema(description = "短信平台类型：0：阿里云短信平台")
    private SmsPlatformType type;

    /**
     * 接收短信的号码
     * 格式：国内短信：+/+86/0086/86或无任何前缀的11位手机号码，例如1381111****。 国际/港澳台消息：国际区号+号码，例如852000012****。
     * 支持对多个手机号码发送短信，手机号码之间以英文逗号（,）分隔。上限为1000个手机号码。批量调用相对于单条调用及时性稍有延迟。
     */
    @Schema(description = "接收短信的号码")
    @NotBlank
    private String phoneNumbers;

    /**
     * 短信签名，阿里云：必须是已添加、并通过审核的短信签名
     */
    private String signName;

    /**
     * 短信模板ID，阿里云：必须是已添加、并通过审核的短信模板
     */
    private String templateCode;

    /**
     * 短信模板内容，使用华信时必填
     */
    @Schema(description = "短信模板内容")
    private String templateContent;

    /**
     * 模板可变参数
     */
    @Schema(description = "模板可变参数")
    private Map<String, String> templateParamMap;

}