package com.wanmi.sbc.empower.api.request.sellplatform.order;

import com.wanmi.sbc.empower.api.request.sellplatform.ThirdBaseRequest;
import com.wanmi.sbc.empower.bean.vo.sellplatform.order.PlatformOrderDeliveryVO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
*
 * @description   订单发货
 * @author  wur
 * @date: 2022/4/1 14:26
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class PlatformDeliverySendRequest extends ThirdBaseRequest {

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
    * 发货完成标志位, 0: 未发完, 1:已发完
     **/
    @NotNull
    @Schema(description = "发货完成标志位, 0: 未发完, 1:已发完")
    private Integer finish_all_delivery;

    /**
     * 快递信息，delivery_type=1时必填
     */
    @Schema(description = "快递信息，下单选择正常快递时(delivery_type=1)必填")
    private List<PlatformOrderDeliveryVO> delivery_list;

    /**
     * 完成发货时间，finish_all_delivery = 1 必传
     */
    @Schema(description = "完成发货时间，finish_all_delivery = 1 必传")
    private String ship_done_time;

}
