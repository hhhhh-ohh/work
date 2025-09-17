package com.wanmi.sbc.empower.api.response.ledger.lakala;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


/**
 * 拉卡拉电子合同申请返回参数
 */
@Schema
@Data
public class EcApplyResponse extends LakalaBaseResponse {

    /**
     * 电子签约申请受理编号
     */
    @Schema(description = "电子签约申请受理编号")
    private Long ecApplyId;

    /**
     * 电子签约申请结果H5
     * 申请成功时：待签约合同H5链接
     * 申请失败时：错误信息结果H5链接
     */
    @Schema(description = "电子签约申请结果H5")
    private String resultUrl;
}
