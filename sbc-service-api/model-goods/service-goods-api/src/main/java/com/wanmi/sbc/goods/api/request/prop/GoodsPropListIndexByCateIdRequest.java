package com.wanmi.sbc.goods.api.request.prop;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wanggang
 * @createDate 2018/10/31 14:43
 * @version 1.0
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsPropListIndexByCateIdRequest extends BaseRequest {

    private static final long serialVersionUID = -2946477729503634991L;

    /**
     * 类目ID
     */
    @Schema(description = "类目ID")
    @NotNull
    private Long cateId;
}
