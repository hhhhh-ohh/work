package com.wanmi.sbc.empower.api.request.channel.vop.order;

import com.wanmi.sbc.empower.api.request.vop.base.VopBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * 查询订单配送信息
 *
 * @author wur
 * @date: 2021/5/17 11:52
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class VopQueryOrderTrackRequest extends VopBaseRequest {

    private static final long serialVersionUID = -1L;

    /** 京东的订单单号 */
    @NotNull
    @Schema(description = "京东的订单单号")
    private String jdOrderId;

    /** 扩展参数 */
    @Schema(description = "是否返回订单的配送信息。0不返回配送信息。1，返回配送信息。只支持最近2个月的配送信息查询。")
    private Integer waybillCode;
}
