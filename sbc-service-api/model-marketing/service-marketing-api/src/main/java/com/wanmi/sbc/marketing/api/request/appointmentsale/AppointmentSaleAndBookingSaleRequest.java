package com.wanmi.sbc.marketing.api.request.appointmentsale;

import com.wanmi.sbc.common.base.BaseQueryRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotEmpty;

import lombok.*;

import java.util.List;

@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentSaleAndBookingSaleRequest extends BaseQueryRequest {


    private static final long serialVersionUID = -7552208113924726472L;

    @Schema(description = "skuId")
    @NotEmpty
    private List<String> goodsInfoIdList;

}
