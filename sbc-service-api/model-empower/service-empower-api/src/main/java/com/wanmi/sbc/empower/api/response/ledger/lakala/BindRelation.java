package com.wanmi.sbc.empower.api.response.ledger.lakala;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema
@Data
public class BindRelation {


    /**
     * 接收方编号
     */
    @Schema(description = "接收方编号")
    private String receiverNo;


    /**
     * 接收方名称
     */
    @Schema(description = "接收方名称")
    private String receiverName;
}
