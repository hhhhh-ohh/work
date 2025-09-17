package com.wanmi.sbc.trade.request;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @description 可用支付项请求对象
 * @author malianfeng
 * @date 2021/7/21 11:46
 */
@Data
@Schema
public class PayItemsRequest extends BaseRequest {

    /**
     * 是否跨境订单
     */
    @Schema(description = "是否跨境订单")
    private Boolean crossBorderFlag;

    /**
     * 是否支付定金页面
     */
    @Schema(description = "是否支付定金页面")
    private Boolean isBookingSaleGoods;

    /**
     * 是否视频好标识
     */
    @Schema(description = "是否视频号标识")
    private Boolean isChannelsFlag = Boolean.FALSE;

    @Schema(description = "直连支付")
    private Boolean directPaymentFlag = Boolean.FALSE;

    @Schema(description = "订单ID")
    private String tid;

    @Schema(description = "父订单ID")
    private String parentTid;
}
