package com.wanmi.sbc.marketing.api.request.appointmentsale;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>单个删除预约抢购请求参数</p>
 * @author zxd
 * @date 2020-05-21 10:32:23
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentSaleDelByIdRequest extends BaseRequest {
private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @Schema(description = "id")
    @NotNull
    private Long id;

    /**
     * storeId
     */
    @Schema(description = "店铺id")
    private Long storeId;
}
