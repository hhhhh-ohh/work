package com.wanmi.sbc.empower.api.request.deliveryrecord;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.base.Operator;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

import org.hibernate.validator.constraints.NotBlank;

/**
 * <p>单个删除达达配送记录请求参数</p>
 * @author zhangwenchang
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryRecordDadaCancelByIdRequest extends BaseRequest {
private static final long serialVersionUID = 1L;

    /**
     * 店铺标识
     */
    @Schema(description = "店铺标识", hidden = true)
    private Long storeId;

    /**
     * 订单号
     */
    @Schema(description = "订单号")
    @NotBlank
    private String orderCode;

    /**
     * 取消理由id
     */
    @Schema(description = "取消理由id")
    @NotNull
    private Integer cancelReasonId;

    /**
     * 取消
     */
    @Schema(description = "取消原因")
    private String reason;

    /**
     * 用户信息
     */
    @Schema(description = "用户信息")
    private Operator operator;
}
