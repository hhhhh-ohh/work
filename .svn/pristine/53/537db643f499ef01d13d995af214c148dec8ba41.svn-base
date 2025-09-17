package com.wanmi.sbc.message.api.response.smssend;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.message.bean.vo.SmsSendVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>短信发送分页结果</p>
 * @author zgl
 * @date 2019-12-03 15:36:05
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SmsSendPageResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 短信发送分页结果
     */
    @Schema(description = "短信发送分页结果")
    private MicroServicePage<SmsSendVO> smsSendVOPage;
}
