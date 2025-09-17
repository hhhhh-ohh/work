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
public class SmsSignAliyunQueryResponse {

    private String Code;

    private String Message;

    private String RequestId;

    private String CreateDate;

    private String SignName;

    /**
     * 签名审核状态。其中：
     *
     * 0：审核中。
     * 1：审核通过。
     * 2：审核失败，请在返回参数Reason中查看审核失败原因。
     */
    private String SignStatus;

    /**
     * 审核备注。
     *
     * 如果审核状态为审核通过或审核中，参数Reason显示为“无审核备注”。
     * 如果审核状态为审核未通过，参数Reason显示审核的具体原因。
     */
    private String Reason;

    private Long smsSettingId;
}
