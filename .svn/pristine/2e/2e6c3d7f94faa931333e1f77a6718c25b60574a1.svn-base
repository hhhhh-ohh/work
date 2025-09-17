package com.wanmi.sbc.setting.api.request;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Author: bail
 * Time: 2017/11/13.10:22
 */
@Schema
@Data
public class RoleMenuAuthSaveRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;
    /**
     * 角色标识
     */
    @Schema(description = "角色标识")
    private Long roleInfoId;

    /**
     * 菜单标识List
     */
    @Schema(description = "菜单标识List")
    private List<String> menuIdList;

    /**
     * 功能标识List
     */
    @Schema(description = "功能标识List")
    private List<String> functionIdList;

}
