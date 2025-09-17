package com.wanmi.sbc.order.request;


import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.order.bean.enums.AuditState;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Created by Administrator on 2017/4/28.
 */
@Schema
@Data
public class TradeAuditRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 审核状态
     */
    @Schema(description = "审核状态")
    private AuditState auditState = AuditState.CHECKED;

    /**
     * 原因备注，用于审核驳回
     */
    @Schema(description = "原因备注，用于审核驳回")
    private String reason;
}
