package com.wanmi.sbc.empower.api.response.sms.aliyun;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SmsSignAliyunDeleteResponse {

    private String Code;

    private String Message;

    private String RequestId;

    private String SignName;

    private Long smsSettingId;
}
