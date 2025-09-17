package com.wanmi.sbc.empower.bean.dto.channel.base.goods;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 渠道商品标签DTO
 * @author daiyitian
 */
@Schema
@Data
public class ChannelGoodsLabelDto implements Serializable {
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