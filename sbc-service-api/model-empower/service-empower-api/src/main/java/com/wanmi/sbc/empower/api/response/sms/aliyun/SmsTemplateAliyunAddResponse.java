package com.wanmi.sbc.empower.api.response.sms.aliyun;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SmsTemplateAliyunAddResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private String Code;

    private String Message;

    private String RequestId;

    private String TemplateCode;

    private Long smsSettingId;

}
