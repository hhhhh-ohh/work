package com.wanmi.sbc.setting.hovernavmobile.model.root;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>移动端悬浮导航栏实体类</p>
 *
 * @author dyt
 * @date 2020-04-29 14:28:21
 */
@Data
public class BottomNavMobileItem implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 导航标识，例：tabIndex0
     */
    @Schema(description = "导航标识")
    private String key;

    /**
     * 导航标识，例：tabIndex0
     */
    @Schema(description = "导航名称")
    private String title;

    /**
     * 导航图标地址
     */
    @Schema(description = "导航图标地址")
    private String iconPath;

    /**
     * 选中后的导航图标地址
     */
    @Schema(description = "选中后的导航图标地址")
    private String selectedIconPath;

    /**
     * 落地页地址
     */
    @Schema(description = "落地页地址")
    private String link;
}