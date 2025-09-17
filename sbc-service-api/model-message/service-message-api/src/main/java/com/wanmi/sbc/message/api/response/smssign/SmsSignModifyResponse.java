package com.wanmi.sbc.message.api.response.smssign;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.message.bean.vo.SmsSignVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>短信签名修改结果</p>
 * @author lvzhenwei
 * @date 2019-12-03 15:49:24
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SmsSignModifyResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 已修改的短信签名信息
     */
    @Schema(description = "已修改的短信签名信息")
    private SmsSignVO smsSignVO;
}
