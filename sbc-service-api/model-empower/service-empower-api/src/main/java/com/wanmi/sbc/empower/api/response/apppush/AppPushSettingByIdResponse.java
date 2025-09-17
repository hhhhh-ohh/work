package com.wanmi.sbc.empower.api.response.apppush;

import com.wanmi.sbc.empower.bean.vo.AppPushSettingVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>根据id查询任意（包含已删除）消息推送配置信息response</p>
 * @author 韩伟
 * @date 2021-04-01 16:36:29
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppPushSettingByIdResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 消息推送配置信息
     */
    @Schema(description = "消息推送配置信息")
    private AppPushSettingVO appPushSettingVO;
}
