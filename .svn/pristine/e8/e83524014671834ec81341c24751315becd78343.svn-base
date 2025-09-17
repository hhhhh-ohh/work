package com.wanmi.sbc.goods.bean.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 商品标签DTO
 */
@Schema
@Data
public class GoodsLabelDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 标签id
	 */
	@Schema(description = "标签id")
	private Long goodsLabelId;

	/**
	 * 标签名称
	 */
	@Schema(description = "标签名称")
	private String labelName;

	/**
	 * 前端是否展示 0: 关闭 1:开启
	 */
	@Schema(description = "前端是否展示 0: 关闭 1:开启")
	private Boolean labelVisible;

	/**
	 * 排序
	 */
	@Schema(description = "排序")
	private Integer labelSort;
}