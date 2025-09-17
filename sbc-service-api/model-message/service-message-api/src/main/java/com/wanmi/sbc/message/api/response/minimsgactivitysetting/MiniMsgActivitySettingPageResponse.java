package com.wanmi.sbc.message.api.response.minimsgactivitysetting;

import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.message.bean.vo.MiniMsgActivitySettingVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>小程序订阅消息配置表分页结果</p>
 * @author xufeng
 * @date 2022-08-11 16:16:32
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MiniMsgActivitySettingPageResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 小程序订阅消息配置表分页结果
     */
    @Schema(description = "小程序订阅消息配置表分页结果")
    private MicroServicePage<MiniMsgActivitySettingVO> miniMsgActivitySettingVOPage;
}
