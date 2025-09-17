package com.wanmi.sbc.order.api.request.trade;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.order.bean.enums.AuditState;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: ZhangLingKe
 * @Description:
 * @Date: 2018-12-05 15:05
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class TradeAuditBatchRequest extends BaseRequest {

    /**
     * 交易id
     */
    @Schema(description = "交易id")
    private String[] ids;

    /**
     * 审核状态
     */
    @Schema(description = "审核状态")
    private AuditState auditState;

    /**
     * 原因
     */
    @Schema(description = "原因")
    private String reason;

    /**
     * 操作人
     */
    @Schema(description = "操作人")
    private Operator operator;

}
