package com.wanmi.sbc.goods.api.request.newcomerpurchasegoods;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.DeleteFlag;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.*;

import lombok.*;

import org.hibernate.validator.constraints.*;

/**
 * <p>新人购商品表新增参数</p>
 * @author zhanghao
 * @date 2022-08-19 14:28:56
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewcomerPurchaseGoodsAddRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * sku ID
	 */
	@Schema(description = "sku ID")
	@Length(max=32)
	private String goodsInfoId;

	/**
	 * createPerson
	 */
	@Schema(description = "createPerson", hidden = true)
	private String createPerson;

	/**
	 * updatePerson
	 */
	@Schema(description = "updatePerson", hidden = true)
	private String updatePerson;

	/**
	 * 删除标识：0：未删除；1：已删除
	 */
	@Schema(description = "删除标识：0：未删除；1：已删除", hidden = true)
	private DeleteFlag delFlag;

}