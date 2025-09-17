package com.wanmi.sbc.goods.api.request.prop;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsPropAddDefaultRefRequest extends BaseRequest {

    private static final long serialVersionUID = 1586163249560974514L;

    @Schema(description = "商品Id")
    @NotNull
    private List<String> goodsIds;

    @Schema(description = "属性Id")
    @NotNull
    private Long propId;
}
