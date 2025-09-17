package com.wanmi.sbc.goods.bean.dto;



import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Schema
@Data
public class GoodsPropRequestDTO implements Serializable {

    private static final long serialVersionUID = -7478175539274476884L;

    @Schema(description = "最后一个属性Id")
    private Long lastPropId;

    @Schema(description = "商品属性")
    private GoodsPropDTO goodsProp;

    @Schema(description = "商品属性集合")
    private List<GoodsPropDTO> goodsProps;
}
