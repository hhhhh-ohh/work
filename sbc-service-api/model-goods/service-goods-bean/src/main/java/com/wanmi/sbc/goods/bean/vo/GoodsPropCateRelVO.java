package com.wanmi.sbc.goods.bean.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * <p>商品类目与属性关联VO</p>
 * @author chenli
 * @date 2021-04-21 14:58:28
 */
@Schema
@Data
public class GoodsPropCateRelVO extends BasicResponse {
	private static final long serialVersionUID = 1L;

	/**
	 * 关联表主键
	 */
	@Schema(description = "关联表主键")
	private Long relId;

	/**
	 * 属性id
	 */
	@Schema(description = "属性id")
	private Long propId;

	/**
	 * 商品分类id
	 */
	@Schema(description = "商品分类id")
	private Long cateId;

	/**
	 * 父类编号
	 */
	@Schema(description = "父类编号")
	private Long parentId;

	/**
	 * 分类路径
	 */
	@Schema(description = "分类路径")
	private String catePath;

	/**
	 * 分类层次
	 */
	@Schema(description = "分类层次")
	private Integer cateGrade;

	/**
	 * 商品分类名称
	 */
	@Schema(description = "商品分类名称")
	private String cateName;

	/**
	 * 排序
	 */
	@Schema(description = "排序")
	private Integer relSort;

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