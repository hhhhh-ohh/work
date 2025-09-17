package com.wanmi.sbc.setting.api.response.hovernavmobile;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.setting.bean.vo.HoverNavMobileVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>根据id查询任意（包含已删除）移动端悬浮导航栏信息response</p>
 * @author dyt
 * @date 2020-04-29 14:28:21
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HoverNavMobileByIdResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 移动端悬浮导航栏信息
     */
    @Schema(description = "移动端悬浮导航栏信息")
    private HoverNavMobileVO hoverNavMobileVO;
}
