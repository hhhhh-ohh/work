package com.wanmi.sbc.empower.api.request.sellplatform.returnorder;

import com.wanmi.sbc.empower.api.request.sellplatform.ThirdBaseRequest;
import com.wanmi.sbc.empower.bean.vo.sellplatform.returnorder.PlatformReturnOrderProductInfoVO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
*
 * @description  新增售后单
 * @author  wur
 * @date: 2022/4/1 14:26
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class PlatformAddReturnOrderRequest extends ThirdBaseRequest {

    private static final long serialVersionUID = -8015726253741444133L;

    /**
     * 商家自定义商品ID
     */
    @NotEmpty
    @Schema(description = "创建时间，yyyy-MM-dd HH:mm:ss，与微信服务器不得超过5秒偏差")
    private String create_time;

    @Schema(description = "商家自定义订单ID")
    private String out_order_id;

    @Schema(description = "和out_order_id二选一")
    private String order_id;

    @Schema(description = "商家自定义售后ID")
    private String out_aftersale_id;

    @NotEmpty
    @Schema(description = "用户的openid")
    private String openid;

    @NotNull
    @Schema(description = "售后类型，1:退款,2:退款退货")
    private Integer type;

    @Schema(description = "售后商品")
    private PlatformReturnOrderProductInfoVO product_info;

    @NotEmpty
    @Schema(description = "退款原因")
    private String refund_reason;

    @Schema(description = "退单类型")
    private Integer refund_reason_type;

    @Schema(description = "退款金额，单位：分")
    private Integer orderamt;

}
