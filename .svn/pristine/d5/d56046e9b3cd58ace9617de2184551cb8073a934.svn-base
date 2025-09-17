package com.wanmi.sbc.goods.api.request.standard;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.goods.bean.dto.StandardSkuDTO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

/**
 * 商品库Sku编辑请求
 * Created by daiyitian on 2017/3/24.
 */
@Schema
@Data
public class StandardSkuModifyRequest extends BaseRequest {

    private static final long serialVersionUID = -5140383675975519211L;

    /**
     * 商品SKU信息
     */
    @Schema(description = "商品SKU信息")
    @NotNull
    private StandardSkuDTO goodsInfo;

}
