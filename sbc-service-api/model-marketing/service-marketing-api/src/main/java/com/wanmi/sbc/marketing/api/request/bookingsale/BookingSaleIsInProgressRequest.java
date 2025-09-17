package com.wanmi.sbc.marketing.api.request.bookingsale;

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
public class BookingSaleIsInProgressRequest extends BaseQueryRequest {


    private static final long serialVersionUID = -3671027532438062137L;
    @Schema(description = "skuId")
    @NotBlank
    private String goodsInfoId;

}