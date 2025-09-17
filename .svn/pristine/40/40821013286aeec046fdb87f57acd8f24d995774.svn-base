package com.wanmi.sbc.account.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.account.bean.enums.CreditAuditStatus;
import com.wanmi.sbc.common.enums.BoolFlag;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/***
 * 授信申请记录Vo
 */
@Data
public class CreditApplyRecordVo extends BasicResponse {

    /**
     * 审核状态 0待审核 1拒绝 2通过 3变更额度审核
     */
    @Schema(description = "审核状态")
    private CreditAuditStatus auditStatus;

    /**
     * 申请原因
     */
    @Schema(description = "申请原因")
    private String applyNotes;

    /**
     * 驳回原因
     */
    @Schema(description = "驳回原因")
    private String rejectReason;

    /**
     * 是否生效 0 否 1是
     */
    @Schema(description = "是否生效")
    private BoolFlag effectStatus;
}
