package com.wanmi.sbc.empower.api.response.sellplatform.order;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author wur
 * @className ThirdPlatformAddOrderResponse
 * @description 生成订单返回
 * @date 2022/4/1 19:30
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class PlatformAddOrderResponse implements Serializable {

    private static final long serialVersionUID = -8015726253741444133L;

    @Schema(description = "ticket")
    private String ticket;

    @Schema(description = "订单金额，单位：分")
    private Integer final_price;

    /**
     * 小商店内部订单号  微信侧订单 需要保存
     */
    @Schema(description = "小商店内部订单号 微信侧订单 需要保存")
    private String order_id;

    /**
     * 商家的订单号
     */
    @Schema(description = "商家的订单号")
    private String out_order_id;

}