package com.wanmi.sbc.empower.api.response.apppush;

import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.empower.bean.vo.AppPushSettingVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>消息推送配置分页结果</p>
 * @author 韩伟
 * @date 2021-04-01 16:36:29
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppPushSettingPageResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 消息推送配置分页结果
     */
    @Schema(description = "消息推送配置分页结果")
    private MicroServicePage<AppPushSettingVO> appPushSettingVOPage;
}
