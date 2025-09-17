package com.wanmi.sbc.message.api.response.minimsgtempsetting;

import com.wanmi.sbc.message.bean.vo.MiniMsgTemplateSettingVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <p>小程序订阅消息模版配置表列表结果</p>
 * @author xufeng
 * @date 2022-08-12 11:19:52
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MiniMsgTempSettingListResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 小程序订阅消息模版配置表列表结果
     */
    @Schema(description = "小程序订阅消息模版配置表列表结果")
    private List<MiniMsgTemplateSettingVO> miniMsgTemplateSettingVOList;
}
