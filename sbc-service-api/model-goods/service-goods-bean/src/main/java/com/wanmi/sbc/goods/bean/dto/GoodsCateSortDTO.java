package com.wanmi.sbc.goods.bean.dto;


import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 签约分类拖拽排序请求
 * Created by chenli on 2018/9/13.
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsCateSortDTO implements Serializable {

    private static final long serialVersionUID = 8812387781331933810L;

    /**
     * 商品分类标识
     */
    @Schema(description = "商品分类标识", required = true)
    @NotNull
    private Long cateId;


    /**
     * 商品分类排序顺序
     */
    @Schema(description = "商品分类排序顺序", required = true)
    @NotNull
    private Integer cateSort;
}
