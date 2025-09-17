package com.wanmi.sbc.goods.api.request.goodspropertydetailrel;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.goods.bean.enums.GoodsPropertyEnterType;
import com.wanmi.sbc.goods.bean.enums.GoodsPropertyType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.*;

import lombok.*;

import org.hibernate.validator.constraints.*;

import java.time.LocalDateTime;

/**
 * <p>商品与属性值关联修改参数</p>
 * @author chenli
 * @date 2021-04-21 15:00:14
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsPropertyDetailRelModifyRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * detailRelId
	 */
	@Schema(description = "detailRelId")
	@Max(9223372036854775807L)
	private Long detailRelId;

	/**
	 * 商品id
	 */
	@Schema(description = "商品id")
	@Length(max=32)
	private String goodsId;

	/**
	 * 商品分类id
	 */
	@Schema(description = "商品分类id")
	@NotNull
	@Max(9223372036854775807L)
	private Long cateId;

	/**
	 * 属性id
	 */
	@Schema(description = "属性id")
	@NotNull
	@Max(9223372036854775807L)
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
	@Max(127)
	private GoodsPropertyType goodsType;

	/**
	 * 属性值输入方式，0选项 1文本 2日期 3省市 4国家或地区
	 */
	@Schema(description = "属性值输入方式，0选项 1文本 2日期 3省市 4国家或地区")
	@NotNull
	@Max(127)
	private GoodsPropertyEnterType propType;

	/**
	 * 输入方式为文本的属性值
	 */
	@Schema(description = "输入方式为文本的属性值")
	@Length(max=50)
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
	@Schema(description = "输入方式为省市的属性值，省市id用逗号隔开")
	@Length(max=50)
	private String propValueProvince;

	/**
	 * 输入方式为国家或地区的属性值，国家和地区用逗号隔开
	 */
	@Schema(description = "输入方式为国家或地区的属性值，国家和地区用逗号隔开")
	@Length(max=50)
	private String propValueCountry;

	/**
	 * 修改人
	 */
	@Schema(description = "修改人", hidden = true)
	private String updatePerson;

	/**
	 * 删除时间
	 */
	@Schema(description = "删除时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime deleteTime;

	/**
	 * 删除人
	 */
	@Schema(description = "删除人")
	@Length(max=36)
	private String deletePerson;

}
