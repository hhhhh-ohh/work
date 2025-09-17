package com.wanmi.sbc.order.api.response.orderinvoice;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.account.bean.enums.InvoiceState;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: ZhangLingKe
 * @Description:
 * @Date: 2018-12-03 15:25
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class OrderInvoiceCountByStateResponse extends BasicResponse {

    /**
     * 数量
     */
    @Schema(description = "数量")
    private Long count;
}
