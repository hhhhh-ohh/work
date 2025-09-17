package com.wanmi.sbc.message.api.response.smstemplate;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.message.bean.vo.SmsTemplateVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>短信模板分页结果</p>
 * @author lvzhenwei
 * @date 2019-12-03 15:43:29
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SmsTemplatePageResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 短信模板分页结果
     */
    @Schema(description = "短信模板分页结果")
    private MicroServicePage<SmsTemplateVO> smsTemplateVOPage;
}
