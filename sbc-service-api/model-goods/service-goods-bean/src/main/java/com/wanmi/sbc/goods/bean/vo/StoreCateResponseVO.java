package com.wanmi.sbc.goods.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Author: bail
 * Time: 2017/11/13.10:25
 */
@Schema
@Data
public class StoreCateResponseVO extends BasicResponse {

    /**
     * 店铺分类标识
     */
    @Schema(description = "店铺分类标识")
    private Long storeCateId;

    /**
     * 店铺标识
     */
    @Schema(description = "店铺标识")
    private Long storeId;

    /**
     * 店铺分类名称
     */
    @Schema(description = "店铺分类名称")
    private String cateName;

    /**
     * 父分类标识
     */
    @Schema(description = "父分类标识")
    private Long cateParentId;

    /**
     * 分类层次
     */
    @Schema(description = "分类层次")
    private Integer cateGrade;

    /**
     * 分类路径
     */
    @Schema(description = "分类路径")
    private String catePath;

    /**
     * 删除标记
     */
    @Schema(description = "删除标记 0: 否, 1: 是")
    private DeleteFlag delFlag;

    /**
     * 排序
     */
    @Schema(description = "排序")
    private Integer sort;

    /**
     * 默认标记
     */
    @Schema(description = "默认标记，0: 否, 1: 是")
    private DefaultFlag isDefault;

    /**
     * 一对多关系，子分类
     */
    @Schema(description = "一对多关系，子分类")
    private List<StoreCateVO> storeCateList;
}
