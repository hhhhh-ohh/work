package com.wanmi.sbc.goods.api.request.goodsrestrictedcustomerrela;

import com.wanmi.sbc.goods.api.request.GoodsBaseRequest;
import com.wanmi.sbc.goods.bean.enums.AssignPersonRestrictedType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.*;

import lombok.*;

import org.hibernate.validator.constraints.*;

/**
 * <p>限售配置会员关系表修改参数</p>
 * @author baijz
 * @date 2020-04-08 11:32:28
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsRestrictedCustomerRelaModifyRequest extends GoodsBaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 限售会员的关系主键
	 */
	@Schema(description = "限售会员的关系主键")
	private Long relaId;

	/**
	 * 限售主键
	 */
	@Schema(description = "限售主键")
	private Long restrictedSaleId;

	/**
	 * 特定会员的限售类型 0: 会员等级  1：指定会员
	 */
	@Schema(description = "特定会员的限售类型 0: 会员等级  1：指定会员")
	private AssignPersonRestrictedType assignPersonRestrictedType;

	/**
	 * 会员ID
	 */
	@Schema(description = "会员ID")
	@Length(max=32)
	private String customerId;

	/**
	 * 会员等级ID
	 */
	@Schema(description = "会员等级ID")
	private Long customerLevelId;

}