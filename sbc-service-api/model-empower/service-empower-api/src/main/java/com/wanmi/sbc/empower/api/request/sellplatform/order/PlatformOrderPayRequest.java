package com.wanmi.sbc.empower.api.request.sellplatform.order;

import com.wanmi.sbc.empower.api.request.sellplatform.ThirdBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
*
 * @description  获取小程序AccessToken请求
 * @author  wur
 * @date: 2022/4/1 14:26
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class PlatformOrderPayRequest extends ThirdBaseRequest {

    private static final long serialVersionUID = -8015726253741444133L;

    /**
     * 订单ID
     */
    @Schema(description = "订单ID， 与out_order_id二选一")
    private String order_id;

    /**
     * 商家自定义商品ID
     */
    @Schema(description = "商家自定义订单ID，与 order_id 二选一")
    private String out_order_id;

    /**
     *
     */
    @NotEmpty
    @Schema(description = "用户的openid")
    private String openid;

    /**
    * 类型，默认1:支付成功,2:支付失败,3:用户取消,4:超时未支付;5:商家取消;10:其他原因取消
     **/
    @NotNull
    @Schema(description = "类型，默认1:支付成功,2:支付失败,3:用户取消,4:超时未支付;5:商家取消;10:其他原因取消")
    private Integer action_type;

    /**
     *  其他具体原因
     */
    @Schema(description = "其他具体原因")
    private String action_remark;

    /**
     * 支付订单号
     */
    @Schema(description = "支付订单号，action_type=1且order/add时传的pay_method_type=0时必填")
    private String transaction_id;

    /**
     * 支付完成时间
     */
    @Schema(description = "支付完成时间，action_type=1时必填，yyyy-MM-dd HH:mm:ss")
    private String pay_time;

}
