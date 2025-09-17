package com.wanmi.sbc.setting.api.response.wechatvideo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @description 视频直播带货是否已启用
 * @author malianfeng
 * @date 2022/4/18 15:32
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WechatVideoSettingEnableResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 视频直播带货是否已启用
     */
    @Schema(description = "视频直播带货是否已启用")
    private Boolean isEnable;
}
