package com.wanmi.sbc.setting.api.response;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.setting.bean.vo.ConfigVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Schema
@Data
public class AuditConfigListResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;
    /**
     * 审核开关列表
     */
    @Schema(description = "审核开关列表")
    private List<ConfigVO> configVOList = new ArrayList<>();
}
