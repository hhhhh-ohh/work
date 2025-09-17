package com.wanmi.sbc.goods.api.request.freight;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FreightTemplateGoodsExpressByIdRequest extends BaseRequest {

    private static final long serialVersionUID = 5871480221139425235L;

    /**
     * 运费模板id
     */
    @Schema(description = "运费模板id")
    @NotNull
    private Long freightTempId;

}
