package com.wanmi.sbc.setting.api.response;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.setting.bean.vo.MenuInfoVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 菜单权限返回类
 * Author: bail
 * Time: 2017/12/28.16:34
 */
@Schema
@Data
public class MenuAndFunctionListResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;
    /**
     * 菜单及功能列表
     */
    @Schema(description = "菜单及功能列表")
    private List<MenuInfoVO> menuInfoVOList = new ArrayList<>();
}
