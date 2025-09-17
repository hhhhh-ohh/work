package com.wanmi.sbc.marketing.grouponcate.model.entity;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

/**
 * @Author: chenli
 * @Date: 2019/5/16
 * @Description: 拼团分类排序request
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GrouponCateSort implements Serializable {

    private static final long serialVersionUID = 7376440832048250231L;

    /**
     * 拼团分类标识
     */
    @Schema(description = "拼团分类Id")
    @NotBlank
    @Length(max = 32)
    private String grouponCateId;


    /**
     * 拼团分类排序顺序
     */
    @Schema(description = "拼团分类排序顺序")
    @NotNull
    private Integer cateSort;
}
