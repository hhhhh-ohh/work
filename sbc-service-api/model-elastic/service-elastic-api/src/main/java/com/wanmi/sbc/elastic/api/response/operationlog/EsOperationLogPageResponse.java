package com.wanmi.sbc.elastic.api.response.operationlog;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.elastic.bean.vo.operationlog.EsOperationLogVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
@Schema
@Data
public class EsOperationLogPageResponse extends BasicResponse {

    @Schema(description = "操作日志列表")
    private MicroServicePage<EsOperationLogVO> opLogPage;
}
