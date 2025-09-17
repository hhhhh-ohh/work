package com.wanmi.sbc.marketing.api.request.appointmentsale;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xuyunpeng
 * @className AppointmentSaleCloseRequest
 * @description 预约活动关闭请求体
 * @date 2021/6/24 2:15 下午
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class AppointmentSaleCloseRequest extends BaseRequest {

    private static final long serialVersionUID = 5345138410200071760L;

    @Schema(description = "预约活动id")
    @NotNull
    private Long id;

    @Schema(description = "店铺id")
    @NotNull
    private Long storeId;
}
