package com.wanmi.sbc.empower.api.response.ledger.lakala;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Set;


/**
 * 拉卡拉商户进件返回参数
 */
@Schema
@Data
public class QueryContractResponse extends LakalaBaseResponse{

    /**
     * 进件ID
     */
    @Schema(description = "进件ID")
    private String contractId;


    /**
     * 进件状态
     * 未提交：NO_COMMIT 已提交 COMMIT 提交失败：COMMIT_FAIL 转人工审核：MANUAL_AUDIT 审核中：REVIEW_ING 审核通过：WAIT_FOR_CONTACT 审核驳回：INNER_CHECK_REJECTED
     */
    @Schema(description = "进件状态")
    private String contractStatus;


    /**
     * 进件描述
     * 进件审核通过，返回“审核通过”,进件审核驳回，返回具体的驳回理由。
     */
    @Schema(description = "进件描述")
    private String contractMemo;

    /**
     * 拉卡拉内部商户号
     * 该属性审核通过才有
     */
    @Schema(description = "拉卡拉内部商户号")
    private String merInnerNo;


    /**
     * 银联商户号
     * 该属性审核通过才有
     */
    @Schema(description = "银联商户号")
    private String merCupNo;


    /**
     * 终端列表信息
     * 该属性审核通过并且是增商、增终进件才有
     */
    @Schema(description = "终端列表信息")
    private Set<TermData> termDatas;
}
