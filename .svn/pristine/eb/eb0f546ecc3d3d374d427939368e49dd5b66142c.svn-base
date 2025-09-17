package com.wanmi.sbc.marketing.api.request.appointmentsale;

import com.wanmi.sbc.common.base.BaseQueryRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.*;

@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentSaleByGoodsIdRequest extends BaseQueryRequest {


    private static final long serialVersionUID = -5483219515387655159L;
    @Schema(description = "goodsId")
    @NotBlank
    private String goodsId;

}