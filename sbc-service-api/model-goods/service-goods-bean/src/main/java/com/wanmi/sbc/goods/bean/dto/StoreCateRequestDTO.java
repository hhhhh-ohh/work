package com.wanmi.sbc.goods.bean.dto;

import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 商品分类实体类
 * Created by bail on 2017/11/13.
 */
@Schema
@Data
public class StoreCateRequestDTO implements Serializable {

    private static final long serialVersionUID = -8591145224304036601L;

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
     * 分类图片
     */
    @Schema(description = "分类图片")
    private String cateImg;

    /**
     * 分类路径
     */
    @Schema(description = "分类路径")
    private String catePath;

    /**
     * 分类层次
     */
    @Schema(description = "分类层次")
    private Integer cateGrade;

    /**
     * 删除标记
     */
    @Schema(description = "删除标记 0: 否, 1: 是")
    private DeleteFlag delFlag;

    /**
     * 默认标记
     */
    @Schema(description = "默认标记，0: 否, 1: 是")
    private DefaultFlag isDefault;

    /**
     * 排序
     */
    @Schema(description = "排序")
    private Integer sort;

    /**
     * 排序的集合
     */
    @Schema(description = "排序的集合")
    private List<StoreCateDTO> storeCateList = new ArrayList<>();
}

