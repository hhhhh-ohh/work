package com.wanmi.sbc.goods.bean.dto;


import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 店铺分类拖拽排序请求
 * @Author: chenli
 * @Date:  2018/9/13
 * @Description: 店铺分类排序request
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreCateSortDTO implements Serializable {

    private static final long serialVersionUID = 4804437985809802947L;

    /**
     * 店铺分类标识
     */
    @Schema(description = "店铺分类标识", required = true)
    @NotNull
    private Long storeCateId;


    /**
     * 店铺分类排序顺序
     */
    @Schema(description = "店铺分类排序顺序", required = true)
    @NotNull
    private Integer cateSort;
}
