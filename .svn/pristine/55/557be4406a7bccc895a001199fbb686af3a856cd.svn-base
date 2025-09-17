package com.wanmi.sbc.elastic.api.request.goods;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.goods.bean.enums.GoodsPropertyEnterType;
import com.wanmi.sbc.goods.bean.vo.PropertyDetailVO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

import org.hibernate.validator.constraints.Length;

import java.util.List;
import java.util.Map;

/**
 * <p>商品属性修改参数</p>
 * @author chenli
 * @date 2021-04-21 14:56:01
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EsGoodsPropertyModifyRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 属性id
	 */
	@Schema(description = "属性id")
	@NotNull
	private Long propId;

	/**
	 * 属性名称
	 */
	@Schema(description = "属性名称")
	@Length(max=22)
	private String propName;

	/**
	 * 拼音全称
	 */
	@Schema(description = "拼音全称")
	private String propPinYin;


	/**
	 * 商品发布时属性是否必填
	 */
	@Schema(description = "商品发布时属性是否必填")
	@NotNull
	private DefaultFlag propRequired;

	/**
	 * 是否行业特性，设置为行业特性的属性，会在前端商品详情规格上方作为行业特性参数露出，如药品参数、工业品参数等
	 */
	@Schema(description = "是否行业特性，设置为行业特性的属性，会在前端商品详情规格上方作为行业特性参数露出，如药品参数、工业品参数等")
	@NotNull
	private DefaultFlag propCharacter;

	/**
	 * 属性值输入方式，0选项 1文本 2日期 3省市 4国家或地区
	 */
	@Schema(description = "属性值输入方式，0选项 1文本 2日期 3省市 4国家或地区")
	@NotNull
	private GoodsPropertyEnterType propType;


	/**
	 * 类目id集合
	 */
	@Schema(description = "类目id集合")
	private List<Long> cateIdList;

	/**
	 * 属性值集合
	 */
	@Schema(description = "属性值集合")
	private List<PropertyDetailVO> detailList;

	/**
	 * 编辑属性信息
	 */
	@Schema(description = "属性值返回信息同步es")
	private Map<Boolean, List<Long>> detailIdMap;

	/**
	 * 编辑类目信息
	 */
	@Schema(description = "类目id返回信息同步es")
	private Map<Boolean, List<Long>> cateIdMap;

}
