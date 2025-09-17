package com.wanmi.sbc.customer.api.request.payingmemberdiscountrel;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.DeleteFlag;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.*;
import jakarta.validation.constraints.NotBlank;

import lombok.*;

import org.hibernate.validator.constraints.*;

import java.math.BigDecimal;

/**
 * <p>折扣商品与付费会员等级关联表新增参数</p>
 * @author zhanghao
 * @date 2022-05-13 13:41:36
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayingMemberDiscountRelAddRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 折扣商品与付费会员等级关联id
	 */
	@Schema(description = "折扣商品与付费会员等级关联id")
	@Max(9999999999L)
	private Integer levelId;

	/**
	 * skuId
	 */
	@Schema(description = "skuId")
	@Length(max=32)
	@NotBlank
	private String goodsInfoId;

	/**
	 * 折扣
	 */
	@Schema(description = "折扣")
	@NotNull
	private BigDecimal discount;

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