package com.wanmi.sbc.setting.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 菜单权限返回类
 * Author: bail
 * Time: 2017/12/28.16:34
 */
@Schema
@Data
public class MenuInfoVO extends BasicResponse {
    private static final long serialVersionUID = 1L;
    /**
     * 节点标识
     */
    @Schema(description = "节点标识")
    private String id;

    /**
     * 父节点标识
     */
    @Schema(description = "父节点标识")
    private String pid;

    /**
     * 菜单/权限id
     */
    @Schema(description = "菜单/权限id")
    private String realId;

    /**
     * 显示名
     */
    @Schema(description = "显示名")
    private String title;

    /**
     * 层级(权限默认层级999)
     */
    @Schema(description = "层级-权限默认层级999")
    private Integer grade;

    /**
     * 菜单图标
     */
    @Schema(description = "权限默认层级999")
    private String icon;

    /**
     * 权限名
     */
    @Schema(description = "权限名")
    private String authNm;

    /**
     * 权限url路径
     */
    @Schema(description = "权限url路径")
    private String url;

    /**
     * url请求类型
     */
    @Schema(description = "url请求类型")
    private String reqType;

    /**
     * 权限备注
     */
    @Schema(description = "权限备注")
    private String authRemark;

    /**
     * 是否为菜单路径
     */
    @Schema(description = "是否为菜单路径")
    private Integer isMenuUrl;

    /**
     * 排序号
     */
    @Schema(description = "排序号")
    private Integer sort;
}
