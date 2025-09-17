package com.wanmi.sbc.goods.api.request.info;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 根据商品SKU编号查询请求
 * Created by daiyitian on 2017/3/24.
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GoodsInfoByIdRequest extends BaseRequest {

    private static final long serialVersionUID = -1635175303035960354L;

    /**
     * SKU编号
     */
    @Schema(description = "SKU编号")
    @NotBlank
    private String goodsInfoId;

    @Schema(description = "店铺Id")
    private Long storeId;
}
