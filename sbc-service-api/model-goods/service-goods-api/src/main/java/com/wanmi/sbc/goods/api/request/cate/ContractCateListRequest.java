package com.wanmi.sbc.goods.api.request.cate;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * <p></p>
 * author: sunkun
 * Date: 2018-11-05
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContractCateListRequest extends BaseRequest {

    private static final long serialVersionUID = -7453113968613964434L;

    /**
     * 店铺id
     */
    @Schema(description = "店铺id")
    @NotNull
    private Long storeId;

    /**
     * 商品分类标识
     */
    @Schema(description = "商品分类标识")
    private Long cateId;

    /**
     * 商品分类标识列表
     */
    @Schema(description = "商品分类标识列表")
    private List<Long> cateIds;
}
