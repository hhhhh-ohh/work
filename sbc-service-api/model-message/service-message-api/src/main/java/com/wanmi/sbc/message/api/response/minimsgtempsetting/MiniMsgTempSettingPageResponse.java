package com.wanmi.sbc.message.api.response.minimsgtempsetting;

import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.message.bean.vo.MiniMsgTemplateSettingVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>小程序订阅消息模版配置表分页结果</p>
 * @author xufeng
 * @date 2022-08-12 11:19:52
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MiniMsgTempSettingPageResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 小程序订阅消息模版配置表分页结果
     */
    @Schema(description = "小程序订阅消息模版配置表分页结果")
    private MicroServicePage<MiniMsgTemplateSettingVO> miniMsgTempSettingVOPage;
}
