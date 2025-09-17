package com.wanmi.sbc.account.api.request.customerdrawcash;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.account.bean.enums.AuditStatus;
import com.wanmi.sbc.account.bean.enums.DrawCashStatus;
import com.wanmi.sbc.account.bean.enums.FinishStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * 批量修改处理单状态
 */
@Schema
@EqualsAndHashCode()
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDrawCashBatchModifyAuditStatusRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    @Schema(description = "多个提现单编号")
    private List<String> drawCashIdList;

    @Schema(description = "审核状态")
    private AuditStatus auditStatus;

    @Schema(description = "审核失败的原因")
    private String rejectReason;

    /**
     * 提现状态(0:未提现,1:提现失败,2:提现成功)
     */
    @Schema(description = "提现状态(0:未提现,1:提现失败,2:提现成功)")
    private DrawCashStatus drawCashStatus;

    /**
     * 提现单完成状态(0:未完成,1:已完成)
     */
    @Schema(description = "提现单完成状态(0:未完成,1:已完成)")
    private FinishStatus finishStatus;

    /**
     * 提现失败原因
     */
    @Schema(description = "提现失败原因")
    private String drawCashFailedReason;

    /**
     * 账户余额
     */
    @Schema(description = "账户余额")
    private BigDecimal accountBalance;

    /**
     * 转账微信批次单号
     **/
    @Schema(description = "转账微信批次单号")
    private String batchId;

    /**
     * 转账微信明细单号
     **/
    @Schema(description = "转账微信明细单号")
    private String detailId;
}
