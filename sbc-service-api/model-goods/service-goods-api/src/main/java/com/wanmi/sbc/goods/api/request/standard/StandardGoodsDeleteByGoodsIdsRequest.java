package com.wanmi.sbc.goods.api.request.standard;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * <p>根据商品id列表删除商品请求类</p>
 * author: sunkun
 * Date: 2018-11-07
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StandardGoodsDeleteByGoodsIdsRequest extends BaseRequest {

    private static final long serialVersionUID = 5385728985322899918L;

    /**
     * 商品id集合
     */
    @Schema(description = "商品id集合")
    @NotNull
    @Size(min = 1)
    private List<String> goodsIds;
}
