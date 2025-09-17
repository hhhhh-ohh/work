package com.wanmi.sbc.message.api.response.minimsgsetting;

import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.message.bean.vo.MiniMsgSettingVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>小程序订阅消息配置表分页结果</p>
 * @author xufeng
 * @date 2022-08-08 11:37:13
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MiniMsgSettingPageResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 小程序订阅消息配置表分页结果
     */
    @Schema(description = "小程序订阅消息配置表分页结果")
    private MicroServicePage<MiniMsgSettingVO> miniMsgSettingVOPage;
}
