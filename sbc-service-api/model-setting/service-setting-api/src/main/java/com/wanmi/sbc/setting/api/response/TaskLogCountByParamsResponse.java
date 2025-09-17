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
public class TaskLogCountByParamsResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 统计数
     */
    @Schema(description = "统计数")
    private Long count;
}
