package com.wanmi.sbc.customer.api.request.payingmemberrecommendrel;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.*;
import jakarta.validation.constraints.NotBlank;

import lombok.*;

import org.hibernate.validator.constraints.*;

/**
 * <p>推荐商品与付费会员等级关联表修改参数</p>
 * @author zhanghao
 * @date 2022-05-13 13:41:51
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayingMemberRecommendRelModifyRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Schema(description = "主键")
	@Max(9223372036854775807L)
	private Long id;

	/**
	 * 推荐商品与付费会员等级关联id
	 */
	@Schema(description = "推荐商品与付费会员等级关联id")
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
	 * updatePerson
	 */
	@Schema(description = "updatePerson", hidden = true)
	private String updatePerson;

}
