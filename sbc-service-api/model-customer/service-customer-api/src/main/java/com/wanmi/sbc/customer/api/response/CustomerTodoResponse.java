package com.wanmi.sbc.customer.api.response;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Created by sunkun on 2017/9/18.
 */
@Schema
@Data
public class CustomerTodoResponse extends BasicResponse {

    /**
     * 待审核客户
     */
    @Schema(description = "待审核客户")
    private long waitAuditCustomer;

    /**
     * 待审核增票资质
     */
    @Schema(description = "待审核增票资质")
    private long waitAuditCustomerInvoice;

    /**
     * 待审核开票订单
     */
    @Schema(description = "待审核开票订单")
    private long waitAuditOrderInvoice;

}
