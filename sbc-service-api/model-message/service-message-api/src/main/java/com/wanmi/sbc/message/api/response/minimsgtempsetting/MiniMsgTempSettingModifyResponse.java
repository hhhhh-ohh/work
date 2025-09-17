package com.wanmi.sbc.message.api.response.minimsgtempsetting;

import com.wanmi.sbc.message.bean.vo.MiniMsgTemplateSettingVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>小程序订阅消息模版配置表修改结果</p>
 * @author xufeng
 * @date 2022-08-12 11:19:52
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MiniMsgTempSettingModifyResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 已修改的小程序订阅消息模版配置表信息
     */
    @Schema(description = "已修改的小程序订阅消息模版配置表信息")
    private MiniMsgTemplateSettingVO miniMsgTemplateSettingVO;
}
