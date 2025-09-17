package com.wanmi.sbc.goods.api.request.prop;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.goods.bean.dto.GoodsPropDTO;

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
public class GoodsPropModifySortRequest extends BaseRequest {

    private static final long serialVersionUID = -329670954838007583L;

    @Schema(description = "商品属性")
    @NotNull
    private List<GoodsPropDTO> goodsProps;
}
