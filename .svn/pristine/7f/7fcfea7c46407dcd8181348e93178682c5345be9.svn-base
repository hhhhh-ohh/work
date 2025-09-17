package com.wanmi.sbc.goods.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>商品属性值VO</p>
 * @author chenli
 * @date 2021-04-21 14:57:33
 */
@Schema
@Data
public class GoodsPropertyDetailVO extends BasicResponse {
	private static final long serialVersionUID = 1L;

	/**
	 * 属性值id
	 */
	@Schema(description = "属性值id")
	private Long detailId;

	/**
	 * 属性id外键
	 */
	@Schema(description = "属性id外键")
	private Long propId;

	/**
	 * 属性值
	 */
	@Schema(description = "属性值")
	private String detailName;

	/**
	 * 属性名拼音
	 */
	@Schema(description = "属性名拼音")
	private String detailPinYin;

	/**
	 * 排序
	 */
	@Schema(description = "排序")
	private Integer detailSort;

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
	private String deletePerson;

}