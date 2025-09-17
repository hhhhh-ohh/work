package com.wanmi.sbc.order.api.request.orderinvoice;

import com.wanmi.sbc.common.base.BaseRequest;
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
public class OrderInvoiceCountByStateRequest extends BaseRequest {

    /**
     * 商家信息id
     */
    @Schema(description = "商家信息id")
    private Long companyInfoId;

    /**
     * 店铺id
     */
    @Schema(description = "店铺id")
    private Long storeId;

    /**
     * 开票状态
     */
    @Schema(description = "开票状态")
    private InvoiceState invoiceState;

}
