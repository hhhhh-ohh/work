package com.wanmi.sbc.customer.api.request.payingmemberdiscountrel;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.*;
import jakarta.validation.constraints.NotBlank;

import lombok.*;

import org.hibernate.validator.constraints.*;

import java.math.BigDecimal;

/**
 * <p>折扣商品与付费会员等级关联表修改参数</p>
 * @author zhanghao
 * @date 2022-05-13 13:41:36
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayingMemberDiscountRelModifyRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Schema(description = "主键")
	@Max(9223372036854775807L)
	private Long id;

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
	 * updatePerson
	 */
	@Schema(description = "updatePerson", hidden = true)
	private String updatePerson;


	@Override
	public void checkParam() {
		if(!(discount.toString().matches("^[+]?([0-9]+(.[0-9]{1,2})?)$"))){
			throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
		} else if (discount.compareTo(BigDecimal.ZERO) < 0
				|| discount.compareTo(new BigDecimal("10.00")) > 0){
			throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
		}
	}

}
