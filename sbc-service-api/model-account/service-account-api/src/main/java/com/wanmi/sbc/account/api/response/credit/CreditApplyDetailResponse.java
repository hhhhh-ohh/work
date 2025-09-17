package com.wanmi.sbc.account.api.response.credit;

import com.wanmi.sbc.account.bean.enums.CreditAuditStatus;
import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/***
 * 授信申请详情返回值
 * @author zhengyang
 * @since 2021/3/18 13:53
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema
public class CreditApplyDetailResponse extends BasicResponse {

    private static final long serialVersionUID = 3085003315356612515L;

    /**
     * 会员id
     */
    @Schema(description = "会员ID")
    private String customerId;
    /**
     * 会员名称
     */
    @Schema(description = "会员名称")
    private String customerName;

    /**
     * 会员账号
     */
    @Schema(description = "会员账号")
    private String customerAccount;

    /**
     * 授信额度
     */
    @Schema(description = "授信额度")
    private BigDecimal creditAmount;

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
     * 生效天数
     */
    @Schema(description = "生效天数")
    private Integer effectiveDays;
}
