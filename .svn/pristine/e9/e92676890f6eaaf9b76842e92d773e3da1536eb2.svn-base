package com.wanmi.sbc.message.api.response.smssenddetail;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.message.bean.vo.SmsSendDetailVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

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
public class SmsSendDetailAddResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 已新增的短信发送信息
     */
    @Schema(description = "已新增的短信发送信息")
    private SmsSendDetailVO smsSendDetailVO;
}
