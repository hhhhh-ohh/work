package com.wanmi.sbc.goods.bean.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.goods.bean.enums.GoodsPropertyEnterType;
import com.wanmi.sbc.goods.bean.enums.GoodsPropertyType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>商品与属性值关联实体类</p>
 * @author chenli
 * @date 2021-04-21 15:00:14
 */
@Data
@Schema
public class GoodsPropertyDetailRelSaveDTO implements Serializable {
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
	 * 属性值id，以逗号隔开
	 */
	@Schema(description = "属性值id，以逗号隔开")
	private String detailId;

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
	 * 输入方式为文本的属性值
	 */
	@Schema(description = "输入方式为文本的属性值")
	private String propValueText;

	/**
	 * 输入方式为日期的属性值
	 */
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	@Schema(description = "输入方式为日期的属性值")
	private LocalDateTime propValueDate;

	/**
	 * 输入方式为省市的属性值，省市id用逗号隔开
	 */
	@Schema(description = "输入方式为省市的属性值")
	private String propValueProvince;

	/**
	 * 输入方式为国家或地区的属性值，国家和地区用逗号隔开
	 */
	@Schema(description = "输入方式为国家或地区的属性值")
	private String propValueCountry;

	/**
	 * 是否删除标志 0：否，1：是
	 */
	@Schema(description = "是否删除标志")
	private DeleteFlag delFlag;

	/**
	 * 删除时间
	 */
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	@Schema(description = "删除时间")
	private LocalDateTime deleteTime;

	/**
	 * 删除人
	 */
	@Schema(description = "删除人")
	private String deletePerson;

	/**
	 * 创建时间
	 */
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	@Schema(description = "创建时间")
	private LocalDateTime createTime;

	/**
	 * 更新时间
	 */
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	@Schema(description = "更新时间")
	private LocalDateTime updateTime;
}
