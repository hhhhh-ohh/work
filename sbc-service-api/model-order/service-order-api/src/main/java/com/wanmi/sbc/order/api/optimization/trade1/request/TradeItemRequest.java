package com.wanmi.sbc.order.api.optimization.trade1.request;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author edz
 * @className TradeItemRequest
 * @description TODO
 * @date 2022/2/23 15:28
 **/
@Data
@Schema
public class TradeItemRequest extends BaseRequest {

    @Schema(description = "skuId")
    private String skuId;

    @Schema(description = "购买数量")
    private long num;

    @Schema(description = "是否是预约抢购商品")
    private Boolean isAppointmentSaleGoods = Boolean.FALSE;

    @Schema(description = "抢购活动Id")
    private Long appointmentSaleId;

    @Schema(description = "是否是预售商品")
    private Boolean isBookingSaleGoods = Boolean.FALSE;

    @Schema(description = "预售活动Id")
    private Long bookingSaleId;

    @Schema(description = "积分价")
    private Long buyPoint;

    @Schema(description = "促销活动id")
    private Long marketingId;

    @Schema(description = "加价购促销活动id")
    private Long preferentialMarketingId;
}
