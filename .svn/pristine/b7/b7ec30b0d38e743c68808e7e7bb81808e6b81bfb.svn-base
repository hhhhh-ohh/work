package com.wanmi.sbc.setting.recommendcate.model.entity;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: 王超
 * @Date: 2022/5/16
 * @Description: 内容分类排序request
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecommendCateSort implements Serializable {

    private static final long serialVersionUID = -5306031653796376696L;

    /**
     * 分类标识
     */
    @Schema(description = "分类Id")
    @NotBlank
    private Long catId;


    /**
     * 分类排序顺序
     */
    @Schema(description = "分类排序顺序")
    @NotNull
    private Integer cateSort;
}
