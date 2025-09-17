package com.wanmi.sbc.goods.api.request.prop;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.goods.bean.dto.GoodsPropDetailRelDTO;

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
public class GoodsPropDeleteRefByPropDetailRelRequest extends BaseRequest {

    private static final long serialVersionUID = -4248154248411864514L;
    @Schema(description = "商品属性详情")
    @NotNull
    private List<GoodsPropDetailRelDTO> goodsPropDetailRels;
}
