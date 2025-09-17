package com.wanmi.sbc.goods.api.response.goodsproperty;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.goods.bean.enums.GoodsPropertyEnterType;
import com.wanmi.sbc.goods.bean.vo.GoodsPropertyVO;
import com.wanmi.sbc.goods.bean.vo.PropertyDetailVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>根据id查询任意（包含已删除）商品属性信息response</p>
 * @author chenli
 * @date 2021-04-21 14:56:01
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsPropertyByIdResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

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
     * 拼音
     */
    @Schema(description = "拼音")
    private String propPinYin;

    /**
     * 简拼
     */
    @Schema(description = "简拼")
    private String propShortPinYin;

    /**
     * 商品发布时属性是否必填
     */
    @Schema(description = "商品发布时属性是否必填")
    private DefaultFlag propRequired;

    /**
     * 是否行业特性，设置为行业特性的属性，会在前端商品详情规格上方作为行业特性参数露出，如药品参数、工业品参数等
     */
    @Schema(description = "是否行业特性，设置为行业特性的属性，会在前端商品详情规格上方作为行业特性参数露出，如药品参数、工业品参数等")
    private DefaultFlag propCharacter;

    /**
     * 属性值输入方式，0选项 1文本 2日期 3省市 4国家或地区
     */
    @Schema(description = "属性值输入方式，0选项 1文本 2日期 3省市 4国家或地区")
    private GoodsPropertyEnterType propType;

    /**
     * 是否开启索引 0否 1是
     */
    @Schema(description = "是否开启索引 0否 1是")
    private DefaultFlag indexFlag;

    /**
     * 类目id集合
     */
    @Schema(description = "类目id集合")
    private List<String> cateIdList;

    /**
     * 属性值集合
     */
    @Schema(description = "属性值集合")
    private List<PropertyDetailVO> detailList;

}
