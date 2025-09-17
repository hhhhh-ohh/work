package com.wanmi.sbc.goods.api.request.standard;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 根据商品库id获取商品库Sku查询请求
 * Created by daiyitian on 2017/3/24.
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StandardSkuByStandardIdRequest extends BaseRequest {

    private static final long serialVersionUID = 5539477667461297569L;

    @Schema(description = "商品库SPUid")
    @NotBlank
    private String standardId;

}
