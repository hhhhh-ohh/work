package com.wanmi.sbc.message.api.response.smssend;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>短信发送新增结果</p>
 * @author zgl
 * @date 2019-12-03 15:43:37
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SmsSendDetailSendResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 发送标记
     */
    @Schema(description = "发送标记")
    private Boolean sendFlag;
}
