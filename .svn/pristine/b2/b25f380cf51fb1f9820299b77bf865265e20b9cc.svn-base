package com.wanmi.sbc.order.api.request.orderinvoice;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.OutputStream;
import java.io.Serializable;
import java.util.List;

/**
 * @Author: ZhangLingKe
 * @Description:
 * @Date: 2018-12-03 14:21
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class OrderInvoiceExportRequest extends BaseRequest {

    /**
     * 导出传输列表
     */
    @Schema(description = "导出传输列表")
    private List<OrderInvoiceExportRequest> orderInvoiceExportRequestList;

    /**
     * 输出流
     */
    @Schema(description = "输出流")
    private OutputStream outputStream;

}
