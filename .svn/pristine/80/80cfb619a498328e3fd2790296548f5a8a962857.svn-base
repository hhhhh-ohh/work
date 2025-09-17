package com.wanmi.sbc.empower.api.request.deliveryrecord;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.hibernate.validator.constraints.NotBlank;

/**
 * <p>单个删除达达配送记录确认妥投异常请求参数</p>
 *
 * @author zhangwenchang
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryRecordDadaFaultConfirmRequest extends BaseRequest {
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
}
