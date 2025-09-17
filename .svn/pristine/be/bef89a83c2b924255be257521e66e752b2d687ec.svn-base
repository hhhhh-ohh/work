package com.wanmi.sbc.message.api.request.smstemplate;

import com.wanmi.sbc.message.api.request.SmsBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SyncPlatformHistorySmsTemplateRequest extends SmsBaseRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 批量同步短信平台短信模板--短信模板code list
     */
    @Schema(description = "批量同步短信平台短信模板--短信模板code list")
    private List<String> templateCodeList;
}