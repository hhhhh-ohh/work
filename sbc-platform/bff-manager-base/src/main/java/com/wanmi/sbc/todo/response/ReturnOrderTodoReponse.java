package com.wanmi.sbc.todo.response;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Created by sunkun on 2017/9/18.
 */
@Schema
@Data
public class ReturnOrderTodoReponse extends BasicResponse {

    /**
     * 待审核
     */
    @Schema(description = "待审核")
    private long waitAudit;

    /**
     * 待填写物流
     */
    @Schema(description = "待填写物流")
    private long waitFillLogistics;

    /**
     * 待收货
     */
    @Schema(description = "待收货")
    private long waitReceiving;

    /**
     * 待退款
     */
    @Schema(description = "待退款")
    private long waitRefund = 0;
}
