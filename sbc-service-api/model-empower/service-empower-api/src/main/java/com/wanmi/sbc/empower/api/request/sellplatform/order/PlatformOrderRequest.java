package com.wanmi.sbc.empower.api.request.sellplatform.order;

import com.wanmi.sbc.empower.api.request.sellplatform.ThirdBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
*
 * @description   WxChannelsOrderRequest   微信视频订单请求
 * @author  wur
 * @date: 2022/4/7 17:42
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class PlatformOrderRequest extends ThirdBaseRequest {

    private static final long serialVersionUID = -8015726253741444133L;

    /**
     * 微信侧订单ID 从下单接口的返回中获取  必填
     */
    @NotEmpty
    @Schema(description = "微信侧订单ID 从下单接口的返回中获取  必填")
    private String order_id;

    /**
     * 商家自定义订单ID，必填
     */
    @NotEmpty
    @Schema(description = "商家自定义订单ID，必填")
    private String out_order_id;

    /**
     *用户的openid
     * 必填
     */
    @NotEmpty
    @Schema(description = "用户的openid，必填")
    private String openid;
}
