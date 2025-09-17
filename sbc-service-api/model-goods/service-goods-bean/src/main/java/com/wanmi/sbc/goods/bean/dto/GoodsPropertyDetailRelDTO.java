package com.wanmi.sbc.goods.bean.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.goods.bean.enums.GoodsPropertyEnterType;
import com.wanmi.sbc.goods.bean.enums.GoodsPropertyType;
import com.wanmi.sbc.goods.bean.vo.GoodsPropDetailVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>商品与属性值关联VO</p>
 * @author chenli
 * @date 2021-04-21 15:00:14
 */
@Schema
@Data
public class GoodsPropertyDetailRelDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * detailRelId
	 */
	@Schema(description = "detailRelId")
	private Long detailRelId;

	/**
	 * 商品id
	 */
	@Schema(description = "商品id")
	private String goodsId;

	/**
	 * 商品分类id
	 */
	@Schema(description = "商品分类id")
	private Long cateId;

	/**
	 * 属性id
	 */
	@Schema(description = "属性id")
	private Long propId;

	/**
	 * 属性值id
	 */
	@Schema(description = "商品属性详情")
	private List<GoodsPropDetailVO> goodsPropDetails;

	/**
	 * 属性值id
	 */
	@Schema(description = "属性值id")
	private List<Long> detailIdList;

	/**
	 * 商品类型 0商品 1商品库
	 */
	@Schema(description = "商品类型 0商品 1商品库")
	private GoodsPropertyType goodsType;

	/**
	 * 属性值输入方式，0选项 1文本 2日期 3省市 4国家或地区
	 */
	@Schema(description = "属性值输入方式，0选项 1文本 2日期 3省市 4国家或地区")
	private GoodsPropertyEnterType propType;

	/**
	 * 商品发布时属性是否必填
	 */
	@Schema(description = "商品发布时属性是否必填")
	private DefaultFlag propRequired;

	/**
	 * 输入方式为文本的属性值
	 */
	@Schema(description = "输入方式为文本的属性值")
	private String propValueText;

	/**
	 * 输入方式为日期的属性值
	 */
	@Schema(description = "输入方式为日期的属性值")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime propValueDate;

	/**
	 * 输入方式为省市的属性值，省市id用逗号隔开
	 */
	@Schema(description = "输入方式为省市的属性值")
	private List<String> propValueProvince;

	/**
	 * 输入方式为国家或地区的属性值，国家和地区用逗号隔开
	 */
	@Schema(description = "输入方式为国家或地区的属性值")
	private List<Long> propValueCountry;

	@Schema(description = "类目属性排序")
	private Integer sort;

	@Schema(description = "属性名称")
	private String propName;

}