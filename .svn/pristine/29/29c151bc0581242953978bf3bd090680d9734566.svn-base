package com.wanmi.sbc.setting.api.response;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 角色-菜单功能返回类
 * Author: bail
 * Time: 2017/12/29
 */
@Schema
@Data
public class RoleMenuFuncIdsQueryResponse extends BasicResponse {

    private static final long serialVersionUID = 1L;
    /**
     * 拥有的菜单标识List
     */
    @Schema(description = "拥有的菜单标识List")
    private List<String> menuIdList = new ArrayList<>();

    /**
     * 拥有的功能标识List
     */
    @Schema(description = "拥有的功能标识List")
    private List<String> functionIdList = new ArrayList<>();

}
