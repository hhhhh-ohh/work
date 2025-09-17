package com.wanmi.sbc.goods.api.request.brand;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 品牌删除请求
 * Created by daiyitian on 2017/3/24.
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GoodsBrandDeleteByIdRequest extends BaseRequest {

    private static final long serialVersionUID = 2326693272021082396L;

    /**
     * 品牌编号
     */
    @Schema(description = "品牌编号")
    @NotNull
    private Long brandId;
}
