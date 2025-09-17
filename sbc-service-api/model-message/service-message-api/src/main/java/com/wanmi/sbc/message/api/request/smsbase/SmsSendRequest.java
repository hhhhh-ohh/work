package com.wanmi.sbc.message.api.request.smsbase;

import com.wanmi.sbc.message.api.request.SmsBaseRequest;
import com.wanmi.sbc.message.bean.dto.SmsTemplateParamDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

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
public class SmsSendRequest extends SmsBaseRequest {
	private static final long serialVersionUID = 1L;

    /**
     * 接收短信的号码
     */
    @Schema(description = "接收短信的号码")
    private String phoneNumbers;

    /**
     * 模板可变参数
     */
    @Schema(description = "模板可变参数")
    private SmsTemplateParamDTO templateParamDTO;

    /**
     * 业务类型  参照com.wanmi.sbc.customer.bean.enums.SmsTemplate
     */
    @Schema(description = "业务类型")
    private String businessType;
}