package com.wanmi.sbc.goods.api.request.goods;

import com.wanmi.sbc.goods.api.request.GoodsBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author
 * @className GoodsElectronicIdRequest
 * @description
 * @date 2022/4/15 10:33 AM
 **/
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GoodsElectronicIdRequest extends GoodsBaseRequest {

    private static final long serialVersionUID = 5621833007962215905L;
    /**
     * 需排除的商品
     */
    @Schema(description = "需排除的商品")
    @NotEmpty
    private List<String> goodsIds;
}
