package com.wanmi.sbc.goods.api.request.prop;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.goods.bean.dto.GoodsPropDetailDTO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsPropDeleteRefByPropDetailRequest extends BaseRequest {

    private static final long serialVersionUID = -4804198848043076680L;

    @Schema(description = "商品属性详情")
    @NotNull
    private GoodsPropDetailDTO goodsPropDetail;
}
