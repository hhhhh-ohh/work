package com.wanmi.sbc.customer.api.request.print;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.customer.bean.enums.PrintDeliveryItem;
import com.wanmi.sbc.customer.bean.enums.PrintSize;
import com.wanmi.sbc.customer.bean.enums.PrintTradeItem;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrintSettingSaveRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Schema(description = "主键")
    private String id;

    /**
     * 商户id
     */
    @Schema(description = "商户id")
    @NotNull
    private Long storeId;

    /**
     * 打印尺寸
     */
    @Schema(description = "打印尺寸")
    private PrintSize printSize;

    /**
     * 订单设置
     */
    @Schema(description = "订单设置")
    private List<PrintTradeItem> tradeSettings;

    /**
     * 发货单设置
     */
    @Schema(description = "发货单设置")
    private List<PrintDeliveryItem> deliverySettings;

}