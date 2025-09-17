package com.wanmi.sbc.setting.api.response;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.setting.bean.vo.OperationLogVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
@Schema
@Data
public class OperationLogListResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;
    /**
     * 操作日志列表
     */
    @Schema(description = "操作日志列表")
    private List<OperationLogVO> logVOList = new ArrayList<>();
}
