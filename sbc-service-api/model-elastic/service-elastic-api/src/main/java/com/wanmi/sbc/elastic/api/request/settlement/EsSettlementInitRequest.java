package com.wanmi.sbc.elastic.api.request.settlement;

import com.wanmi.sbc.common.base.EsInitRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 结算分页查询请求
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
public class EsSettlementInitRequest extends EsInitRequest {

    private static final long serialVersionUID = -5115285012717445946L;
    /**
     * 结算单号批量ID
     */
    @Schema(description = "结算单号批量ID")
    private List<String> idList;

    @Schema(description = "结算单号批量UUID")
    private List<String> uuidList;

}
