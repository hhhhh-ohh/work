package com.wanmi.sbc.goods.api.request.info;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 根据商品SKU编号查询请求
 * Created by daiyitian on 2017/3/24.
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GoodsInfoViewByIdRequest extends BaseRequest implements Serializable {

    private static final long serialVersionUID = -6656920089623825961L;

    /**
     * SKU编号
     */
    @Schema(description = "SKU编号")
    @NotBlank
    private String goodsInfoId;
}
