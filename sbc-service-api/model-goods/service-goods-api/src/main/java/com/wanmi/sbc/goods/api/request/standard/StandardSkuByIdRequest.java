package com.wanmi.sbc.goods.api.request.standard;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商品库Sku编辑请求
 * Created by daiyitian on 2017/3/24.
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StandardSkuByIdRequest extends BaseRequest {

    private static final long serialVersionUID = -2340490212737876320L;

    /**
     * 商品SKU信息
     */
    @Schema(description = "商品SKU信息")
    @NotBlank
    private String standardInfoId;

}
