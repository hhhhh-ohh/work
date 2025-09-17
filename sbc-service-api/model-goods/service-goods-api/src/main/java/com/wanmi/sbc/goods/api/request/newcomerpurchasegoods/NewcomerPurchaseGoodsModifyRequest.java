package com.wanmi.sbc.goods.api.request.newcomerpurchasegoods;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.*;

import lombok.*;

import org.hibernate.validator.constraints.*;

/**
 * <p>新人购商品表修改参数</p>
 * @author zhanghao
 * @date 2022-08-19 14:28:56
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewcomerPurchaseGoodsModifyRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Schema(description = "主键")
	@Max(9999999999L)
	private Integer id;

	/**
	 * sku ID
	 */
	@Schema(description = "sku ID")
	@Length(max=32)
	private String goodsInfoId;

	/**
	 * updatePerson
	 */
	@Schema(description = "updatePerson", hidden = true)
	private String updatePerson;

}
