package com.wanmi.sbc.empower.api.response.ledger.lakala;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 拉卡拉创建分账商户返回参数
 */
@Schema
@Data
public class ApplySplitMerResponse extends LakalaBaseResponse{

    /**
     * 受理编号
     */
    @Schema(description = "受理编号")
    private Long applyId;
}
