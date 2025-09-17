package com.wanmi.sbc.goods.bean.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 商品分类实体类
 * Created by dyt on 2017/4/11.
 */
@Schema
@Data
public class GoodsCateDTO implements Serializable {

    /**
     * 分类编号
     */
    @Schema(description = "分类编号")
    private Long cateId;

    /**
     * 分类名称
     */
    @Schema(description = "分类名称")
    @NotBlank
    @Length(min = 1, max = 10)
    private String cateName;

    /**
     * 父类编号
     */
    @Schema(description = "父类编号")
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
     * 分类扣率
     */
    @Schema(description = "分类扣率")
    private BigDecimal cateRate;

    /**
     * 是否使用上级类目扣率 0 否   1 是
     */
    @Schema(description = "是否使用上级类目扣率，0 否   1 是")
    private DefaultFlag isParentCateRate;

    /**
     * 拼音
     */
    @Schema(description = "拼音")
    private String pinYin;

    /**
     * 简拼
     */
    @Schema(description = "简拼")
    private String sPinYin;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime updateTime;

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
     * 一对多关系，子分类
     */
    @Schema(description = "一对多关系，子分类")
    private List<GoodsCateDTO> goodsCateList;

    /**
     * 一对多关系，属性
     */
    @Schema(description = "一对多关系，属性")
    private List<GoodsPropDTO> goodsProps;
}
