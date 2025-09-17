package com.wanmi.sbc.message.api.response.smssign;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.message.bean.vo.SmsSignVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>短信签名分页结果</p>
 * @author lvzhenwei
 * @date 2019-12-03 15:49:24
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SmsSignPageResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 短信签名分页结果
     */
    @Schema(description = "短信签名分页结果")
    private MicroServicePage<SmsSignVO> smsSignVOPage;
}
