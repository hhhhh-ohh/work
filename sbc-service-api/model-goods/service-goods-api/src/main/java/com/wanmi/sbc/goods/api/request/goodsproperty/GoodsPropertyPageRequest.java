package com.wanmi.sbc.goods.api.request.goodsproperty;

import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import java.time.LocalDateTime;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.goods.bean.enums.GoodsPropertyEnterType;
import lombok.*;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>商品属性分页查询请求参数</p>
 * @author chenli
 * @date 2021-04-21 14:56:01
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsPropertyPageRequest extends BaseQueryRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 批量查询-属性idList
	 */
	@Schema(description = "批量查询-属性idList")
	private List<Long> propIdList;

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
	 * 类目id
	 */
	@Schema(description = "类目id")
	private Long cateId;


}