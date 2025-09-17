package com.wanmi.sbc.goods.bean.dto;


import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>根据拼团活动ID、SPU编号查询拼团价格最小的拼团SKU信息</p>
 * @author groupon
 * @date 2019-05-15 14:49:12
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GrouponGoodsInfoByActivityIdAndGoodsIdDTO implements Serializable {


	/**
	 * 拼团活动ID
	 */
	@Schema(description = "拼团活动ID")
	@NotBlank
	private String grouponActivityId;

	/**
	 * SPU编号
	 */
	@Schema(description = "SPU编号")
	@NotBlank
	private String goodsId;
}