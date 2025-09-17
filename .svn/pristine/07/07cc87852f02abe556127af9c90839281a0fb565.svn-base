package com.wanmi.sbc.account.api.response.customerdrawcash;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 会员提现管理 状态统计对象
 * @author chenyufei
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDrawCashStatusResponse extends BasicResponse {

    @Schema(description = "待审核总数")
    private Integer waitAuditTotal;

    @Schema(description = "已完成总数")
    private Integer finishTotal;

    @Schema(description = "提现失败总数")
    private Integer drawCashFailedTotal;

    @Schema(description = "审核未通过总数")
    private Integer auditNotPassTotal;

    @Schema(description = "已取消总数")
    private Integer canceledTotal;
}
