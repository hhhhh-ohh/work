package com.wanmi.sbc.goods.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * GoodsInfoSpecDetailRelVO
 *
 * @author lipeng
 * @dateTime 2018/11/9 下午2:36
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsInfoSpecDetailRelVO extends BasicResponse {

    private static final long serialVersionUID = -6705853990928847703L;


    public GoodsInfoSpecDetailRelVO(String goodsInfoId,String detailName, String specName) {
        this.goodsInfoId = goodsInfoId;
        this.detailName = detailName;
        this.specName = specName;
    }

    /**
     * SKU与规格值关联ID
     */
    @Schema(description = "SKU与规格值关联ID")
    private Long specDetailRelId;

    /**
     * 商品编号
     */
    @Schema(description = "商品编号")
    private String goodsId;

    /**
     * SKU编号
     */
    @Schema(description = "SKU编号")
    private String goodsInfoId;

    /**
     * 规格值ID
     */
    @Schema(description = "规格值ID")
    private Long specDetailId;

    /**
     * 规格ID
     */
    @Schema(description = "规格ID")
    private Long specId;

    /**
     * 规格值自定义名称
     * 分词搜索
     */
    @Schema(description = "规格值自定义名称，分词搜索")
    private String detailName;

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
     * 是否删除
     */
    @Schema(description = "是否删除，0: 否, 1: 是")
    private DeleteFlag delFlag;

    /**
     * 新增商品时，模拟规格ID
     * 表明与SKU的关系
     */
    @Schema(description = "新增商品时，模拟规格ID，表明与SKU的关系")
    private Long mockSpecId;

    /**
     * 新增商品时，模拟规格值ID
     * 表明与SKU的关系
     */
    @Schema(description = "新增商品时，模拟规格值ID，表明与SKU的关系")
    private Long mockSpecDetailId;

    /**
     * 规格项名称
     * 用于存储ES结构
     */
    @Schema(description = "规格项名称，用于存储ES结构")
    private String specName;

    /**
     * 规格项值
     * 用于存储ES结构，主要解决ES的聚合结果不以分词显示
     */
    @Schema(description = "规格项值，用于存储ES结构，主要解决ES的聚合结果不以分词显示")
    private String allDetailName;
}
