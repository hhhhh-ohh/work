package com.wanmi.sbc.message.api.response.smssend;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.message.bean.vo.SmsSendVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>短信发送新增结果</p>
 * @author zgl
 * @date 2019-12-03 15:36:05
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SmsSendAddResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 已新增的短信发送信息
     */
    @Schema(description = "已新增的短信发送信息")
    private SmsSendVO smsSendVO;
}
