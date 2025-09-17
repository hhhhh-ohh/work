package com.wanmi.sbc.goods.api.request.goodspropertydetailrel;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.goods.bean.enums.GoodsPropertyEnterType;
import com.wanmi.sbc.goods.bean.enums.GoodsPropertyType;
import lombok.*;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>商品与属性值关联列表查询请求参数</p>
 * @author chenli
 * @date 2021-04-21 15:00:14
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsPropertyDetailRelListRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 批量查询-detailRelIdList
	 */
	@Schema(description = "批量查询-detailRelIdList")
	private List<Long> detailRelIdList;

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
	 * 批量商品id
	 */
	@Schema(description = "批量商品id")
	private List<String> goodsIds;

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
	 * 搜索条件:输入方式为日期的属性值开始
	 */
	@Schema(description = "搜索条件:输入方式为日期的属性值开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime propValueDateBegin;
	/**
	 * 搜索条件:输入方式为日期的属性值截止
	 */
	@Schema(description = "搜索条件:输入方式为日期的属性值截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime propValueDateEnd;

	/**
	 * 输入方式为省市的属性值，省市id用逗号隔开
	 */
	@Schema(description = "输入方式为省市的属性值，省市id用逗号隔开")
	private String propValueProvince;

	/**
	 * 输入方式为国家或地区的属性值，国家和地区用逗号隔开
	 */
	@Schema(description = "输入方式为国家或地区的属性值，国家和地区用逗号隔开")
	private String propValueCountry;

	/**
	 * 是否删除标志 0：否，1：是
	 */
	@Schema(description = "是否删除标志 0：否，1：是")
	private DeleteFlag delFlag;

	/**
	 * 搜索条件:创建时间开始
	 */
	@Schema(description = "搜索条件:创建时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTimeBegin;
	/**
	 * 搜索条件:创建时间截止
	 */
	@Schema(description = "搜索条件:创建时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTimeEnd;

	/**
	 * 创建人
	 */
	@Schema(description = "创建人")
	private String createPerson;

	/**
	 * 搜索条件:修改时间开始
	 */
	@Schema(description = "搜索条件:修改时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTimeBegin;
	/**
	 * 搜索条件:修改时间截止
	 */
	@Schema(description = "搜索条件:修改时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTimeEnd;

	/**
	 * 修改人
	 */
	@Schema(description = "修改人")
	private String updatePerson;

	/**
	 * 搜索条件:删除时间开始
	 */
	@Schema(description = "搜索条件:删除时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime deleteTimeBegin;
	/**
	 * 搜索条件:删除时间截止
	 */
	@Schema(description = "搜索条件:删除时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime deleteTimeEnd;

	/**
	 * 删除人
	 */
	@Schema(description = "删除人")
	private String deletePerson;

}