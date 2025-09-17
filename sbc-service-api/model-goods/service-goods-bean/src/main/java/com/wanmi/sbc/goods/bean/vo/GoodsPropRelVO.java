package com.wanmi.sbc.goods.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.util.CustomLocalDateDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateSerializer;
import com.wanmi.sbc.goods.bean.enums.GoodsPropertyEnterType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

/**
 * ES存储的商品属性值关系
 *
 * @auther bail
 * @create 2018/03/23 10:04
 */
@Data
@Schema
public class GoodsPropRelVO extends BasicResponse {

    /**
     * 商品id
     */
    @Schema(description = "商品id")
    private String goodsId;

    /**
     * 属性id
     */
    @Schema(description = "属性id")
    private Long propId;

    /**
     * 属性名称
     */
    @Schema(description = "属性名称")
    private String propName;

    /**
     * 输入方式 0选项 1文本 2日期 3省市 4国家或地区
     */
    @Schema(description = "输入方式")
    private GoodsPropertyEnterType propType;

    /**
     * 属性值
     */
    @Schema(description = "属性值对象")
    private List<PropDetailVO> goodsPropDetailNest;

    /**
     * 是否开启索引 0未开启 1开启
     */
    @Schema(description = "是否开启索引")
    private DefaultFlag indexFlag;

    /**
     * 是否行业特性 0否 1是
     */
    @Schema(description = "是否行业特性")
    private DefaultFlag propCharacter;

    /**
     * 属性排序 越大越靠前
     */
    @Schema(description = "属性排序")
    private Long propSort;

    /**
     * 类目属性排序 越大越靠前
     */
    @Schema(description = "类目属性排序")
    private Integer catePropSort;

    /**
     *
     */
    @Schema(description = "类目id")
    private List<Long> cateIdList;
}
