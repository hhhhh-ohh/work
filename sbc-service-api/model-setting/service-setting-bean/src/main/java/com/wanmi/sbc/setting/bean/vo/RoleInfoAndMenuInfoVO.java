package com.wanmi.sbc.setting.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 菜单权限返回类
 * Author: bail
 * Time: 2017/12/28.16:34
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleInfoAndMenuInfoVO extends BasicResponse {
    private static final long serialVersionUID = 1L;
    /**
     * 角色ID
     */
    @Schema(description = "角色ID")
    private Long roleInfoId;

    /**
     * 角色名称
     */
    @Schema(description = "角色名称")
    private String roleName;

    /**
     * 一级菜单权限集合
     */
    @Schema(description = "一级菜单权限集合")
    private String menuNames;
}
