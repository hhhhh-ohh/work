package com.wanmi.sbc.goods.api.request.prop;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;
import java.io.Serializable;

@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsPropQueryIsChildNodeRequest extends BaseRequest {

    private static final long serialVersionUID = 3306750757024538019L;

    @Schema(description = "分类Id")
    @NotNull
    private Long cateId;
}
