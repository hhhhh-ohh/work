package com.wanmi.sbc.setting.api.response;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.setting.bean.vo.MenuInfoVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
@Schema
@Data
public class MenuAndAuthorityListResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;
    /**
     * 系统所有的菜单,功能,权限
     */
    @Schema(description = "系统所有的菜单,功能,权限")
    private List<MenuInfoVO> menuInfoVOList = new ArrayList<>();
}
