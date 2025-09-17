package com.wanmi.sbc.setting.api.response;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.setting.bean.vo.AuthorityVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Schema
@Data
public class AuthorityListResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;
    /**
     * 权限列表
     */
    @Schema(description = "权限列表")
    private List<AuthorityVO> authorityVOList = new ArrayList<>();
}
