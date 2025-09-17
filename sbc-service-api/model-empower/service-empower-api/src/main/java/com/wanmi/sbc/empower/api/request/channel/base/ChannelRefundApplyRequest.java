package com.wanmi.sbc.empower.api.request.channel.base;

import com.wanmi.sbc.common.annotation.ApiEnumProperty;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.ThirdPlatformType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * linkedMall申请请求结构
 * Created by dyt on 6/5/2017.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class ChannelRefundApplyRequest extends BaseRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Schema(description = "渠道服务类型")
    private ThirdPlatformType thirdPlatformType;

    /**
     * 退单id
     */
    @ApiEnumProperty("发起退款的渠道子订单ID")
    @NotBlank
    private String subChannelOrderId;

    @ApiEnumProperty("0: 退货")
    private Boolean returnGood;

    @ApiEnumProperty("1: 退款")
    private Boolean refund;

    private BigDecimal applyRefundFee;

    /**
     * 原因id
     */
    @Schema(description = "原因id")
    @NotNull
    private Long reasonTextId;

    /**
     * 原因内容
     */
    @Schema(description = "原因内容")
    @NotBlank
    private String reasonTips;

    /**
     * 买家留言
     */
    @Schema(description = "留言")
    private String leaveMessage;

    /**
     * 凭证
     */
    @Schema(description = "凭证")
    private List<String> images;

    @Schema(description = "LinkedMall货物状态  4: 未发货, 6: 已发货, 1: 未收到货, 2:已收到货, 3:已寄回, 5: 卖家确认收货")
    private Integer goodsStatus;
}
