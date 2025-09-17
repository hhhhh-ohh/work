package com.wanmi.sbc.order.api.response.returnorder;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 根据状态统计退单响应结构
 * Created by sunkun on 2017/9/18.
 */
@Data
@Schema
public class ReturnOrderCountByFlowStateResponse extends BasicResponse {

    private static final long serialVersionUID = 1L;

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
