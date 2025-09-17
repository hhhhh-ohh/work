package com.wanmi.sbc.elastic.api.request.ledger;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.elastic.bean.dto.ledger.EsLedgerBindInfoDTO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author xuyunpeng
 * @className EsLedgerBindInfoBatchSaveRequest
 * @description
 * @date 2022/7/14 4:49 PM
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class EsLedgerBindInfoBatchSaveRequest extends BaseRequest {
    private static final long serialVersionUID = -4485978963434060305L;

    @Schema(description = "绑定关系数据")
    @NotEmpty
    private List<EsLedgerBindInfoDTO> infos;
}
