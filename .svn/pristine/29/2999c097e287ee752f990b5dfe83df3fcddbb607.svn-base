package com.wanmi.sbc.goods.bean.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

/**
 * 商品分类实体类
 * Created by bail on 2017/11/13.
 */
@Schema
@Data
public class StoreCateVO extends BasicResponse {

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

    /**
     * 用户可编辑内容的对象转换,非null不复制
     * @param newStoreCate
     */
    public void convertBeforeEdit(StoreCateVO newStoreCate){
        if(newStoreCate.getCateName()!=null){
            this.cateName = newStoreCate.getCateName();
        }
        if(newStoreCate.getCateParentId()!=null){
            this.cateParentId = newStoreCate.getCateParentId();
        }
        if(newStoreCate.getCatePath()!=null){
            this.catePath = newStoreCate.getCatePath();
        }
        if(newStoreCate.getCateGrade()!=null){
            this.cateGrade = newStoreCate.getCateGrade();
        }
        if(newStoreCate.getDelFlag()!=null){
            this.delFlag = newStoreCate.getDelFlag();
        }
        if(newStoreCate.getSort()!=null){
            this.sort = newStoreCate.getSort();
        }
        if(newStoreCate.getIsDefault()!=null){
            this.isDefault = newStoreCate.getIsDefault();
        }
    }
}

