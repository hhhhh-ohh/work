package com.wanmi.sbc.marketing.bean.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>社区团购发货单VO</p>
 * @author dyt
 * @date 2023-08-03 16:23:20
 */
@Schema
@Data
public class CommunityDeliveryItemDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 商品名称
	 */
	@Schema(description = "商品名称")
	private String goodsName;

	/**
	 * 规格
	 */
	@Schema(description = "规格")
	private String specDesc;

	/**
	 * 数量
	 */
	@Schema(description = "数量")
	private Long num;
}