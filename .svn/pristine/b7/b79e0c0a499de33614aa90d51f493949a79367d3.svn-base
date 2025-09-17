package com.wanmi.sbc.goods.api.request.standard;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>修改商品库请求类</p>
 * author: sunkun
 * Date: 2018-11-07
 * @author Administrator
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StandardGoodsExportRequest extends BaseRequest {


    /**
     * 商品类型 0、实物商品 1、虚拟商品 2、电子卡券
     */
    @Schema(description = "商品类型 0、实物商品 1、虚拟商品 2、电子卡券")
    @NotNull
    private Integer goodsType;
}

