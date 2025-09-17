package com.wanmi.sbc.empower.api.response.ledger.lakala;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 拉卡拉电子合同下载返回参数
 */
@Schema
@Data
public class EcQStatusResponse extends LakalaBaseResponse{

    /**
     * 电子签约申请受理编号
     */
    @Schema(description = "电子签约申请受理编号")
    private Long ecApplyId;



    /**
     * 电子合同状态
     */
    @Schema(description = "电子合同状态")
    private String ecStatus;

    /**
     * 电子合同号
     * 电子合同状态为COMPLETED时返回
     */
    @Schema(description = "电子合同号")
    private String ecNo;


    /**
     * 	电子合同名称
     */
    @Schema(description = "电子合同名称")
    private String ecName;
}
