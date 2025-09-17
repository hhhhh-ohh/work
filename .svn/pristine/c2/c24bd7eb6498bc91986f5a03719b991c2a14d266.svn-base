package com.wanmi.sbc.goods.api.request.prop;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.goods.bean.dto.GoodsPropDetailDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsPropQueryPropDetailsOverStepRequest extends BaseRequest {

    private static final long serialVersionUID = -2472365333489662209L;

    @Schema(description = "商品属性详情")
    @NotNull
    private List<GoodsPropDetailDTO> goodsPropDetails;
}
