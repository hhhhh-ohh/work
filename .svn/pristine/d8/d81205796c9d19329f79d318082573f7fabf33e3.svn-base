package com.wanmi.sbc.setting.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.setting.bean.dto.BottomNavMobileItemDTO;
import com.wanmi.sbc.setting.bean.enums.UsePageType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.Valid;
import java.util.List;

/**
 * <p>移动端悬浮导航栏VO</p>
 *
 * @author dyt
 * @date 2020-04-29 14:28:21
 */
@Schema
@Data
public class BottomNavMobileVO extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @Schema(description = "编号", hidden = true)
    private Long storeId;

    /**
     * 背景色
     */
    @Schema(description = "背景色")
    private String bottomNavBackgroundColor;

    /**
     * 导航项
     */
    @Schema(description = "导航项")
    @Valid
    private List<BottomNavMobileItemVO> bottomNavItems;
}