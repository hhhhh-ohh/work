package com.wanmi.sbc.empower.api.response.sellplatform.returnorder;

import com.wanmi.sbc.empower.bean.vo.sellplatform.returnorder.PlatformReturnOrderProductInfoVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author wur
 * @className ThirdPlatformGetReturnOrderResponse
 * @description 查询退单详情
 * @date 2022/4/1 19:30
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class PlatformGetReturnOrderResponse implements Serializable {

    private static final long serialVersionUID = -8015726253741444133L;

    /**
     * 退单时间 时间戳（毫秒）
     */
    @Schema(description = "退单时间 时间戳（毫秒）")
    private String create_time;

    /**
     * 用户的OpenId
     */
    @Schema(description = "用户的OpenId")
    private String openid;

    /**
     * 退单原因类型
     */
    @Schema(description = "退单原因类型")
    private Integer refund_reason_type;

    /**
     * 退单原因
     */
    @Schema(description = "退单原因")
    private String refund_reason;

    /**
     * 退款金额，单位：分
     */
    @Schema(description = "退款金额，单位：分")
    private Integer orderamt;

    /**
     * 售后类型，1:退款,2:退款退货
     */
    @Schema(description = "售后类型，1:退款,2:退款退货")
    private Integer type;

    /**
     *  修改时间  时间戳（毫秒）
     */
    @Schema(description = "修改时间  时间戳（毫秒）")
    private String update_time;

    /**
     * 对应的商家订单号
     */
    @Schema(description = "对应的商家订单号")
    private String out_order_id;

    /**
     * 商家的退单号
     */
    @Schema(description = "商家的退单号")
    private String out_aftersale_id;

    /**
     * 对应的视频号侧的订单
     */
    @Schema(description = "对应的视频号侧的订单")
    private String order_id;

    /**
     * 视频号侧的退单号
     */
    @Schema(description = "视频号侧的退单号")
    private String aftersale_id;

    /**
     * 退单状态
     */
    @Schema(description = "退单状态")
    private Integer status;

    /**
     * 商品信息
     */
    @Schema(description = "商品信息")
    private List<PlatformReturnOrderProductInfoVO> product_info;

}
