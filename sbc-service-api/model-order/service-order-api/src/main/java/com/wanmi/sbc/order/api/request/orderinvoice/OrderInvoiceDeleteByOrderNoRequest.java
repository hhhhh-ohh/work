package com.wanmi.sbc.order.api.request.orderinvoice;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: ZhangLingKe
 * @Description:
 * @Date: 2018-12-03 15:02
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class OrderInvoiceDeleteByOrderNoRequest extends BaseRequest {

    /**
     * 订单号
     */
    @Schema(description = "订单号")
    private String orderNo;

}
