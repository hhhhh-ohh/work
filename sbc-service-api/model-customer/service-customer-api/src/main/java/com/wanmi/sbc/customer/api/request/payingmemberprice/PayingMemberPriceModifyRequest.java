package com.wanmi.sbc.customer.api.request.payingmemberprice;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.customer.api.request.payingmemberrightsrel.PayingMemberRightsRelModifyRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.*;
import jakarta.validation.constraints.NotEmpty;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>付费设置表修改参数</p>
 * @author zhanghao
 * @date 2022-05-13 13:40:30
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayingMemberPriceModifyRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 付费设置id
	 */
	@Schema(description = "付费设置id")
	@Max(9999999999L)
	private Integer priceId;

	/**
	 * 付费设置id
	 */
	@Schema(description = "付费设置id")
	@Max(9999999999L)
	private Integer levelId;

	/**
	 * 付费设置数量 ，例如3个月
	 */
	@Schema(description = "付费设置数量 ，例如3个月")
	@Max(99)
	@Min(1)
	@NotNull
	private Integer priceNum;

	/**
	 * 付费设置总金额，例如上述3个月90元
	 */
	@Schema(description = "付费设置总金额，例如上述3个月90元")
	@NotNull
	private BigDecimal priceTotal;

	/**
	 * updatePerson
	 */
	@Schema(description = "updatePerson", hidden = true)
	private String updatePerson;


	/**
	 * 关联权益
	 */
	@Schema(description = "关联权益")
	@NotEmpty
	private List<PayingMemberRightsRelModifyRequest> payingMemberRightsRelModifyRequests;


	@Override
	public void checkParam() {
		double price = this.priceTotal.doubleValue();
		if (price < 0.01 || price> 9999999) {
			throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
		}
	}

}
