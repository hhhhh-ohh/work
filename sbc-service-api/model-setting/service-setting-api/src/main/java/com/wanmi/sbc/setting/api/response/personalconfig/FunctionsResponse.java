package com.wanmi.sbc.setting.api.response.personalconfig;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author huangzhao
 */
@Schema
@Data
@Builder
public class FunctionsResponse implements Serializable {

    private static final long serialVersionUID = -8787637881132259119L;
    @Schema(description = "菜单名称")
    private String name;

    @Schema(description = "是否选中 0：未选中 1：选中")
    private String isChecked;

    @Schema(description = "菜单图标")
    private String img;

    @Schema(description = "菜单链接")
    private String link;

    @Schema(description = "排序号")
    private Integer sort;
}
