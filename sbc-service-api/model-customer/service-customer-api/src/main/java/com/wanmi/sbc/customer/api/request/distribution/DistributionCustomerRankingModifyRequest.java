package com.wanmi.sbc.customer.api.request.distribution;

import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.*;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.hibernate.validator.constraints.*;

import java.math.BigDecimal;

/**
 * <p>用户分销排行榜修改参数</p>
 * @author lq
 * @date 2019-04-19 10:05:05
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DistributionCustomerRankingModifyRequest extends CustomerBaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 会员ID
	 */
	@Length(max=32)
	@Schema(description = "会员ID")
	private String customerId;

	/**
	 * 邀新人数
	 */
	@Max(9999999999L)
	@Schema(description = "邀新人数")
	private Integer inviteCount;

	/**
	 * 有效邀新人数
	 */
	@Max(9999999999L)
	@Schema(description = "有效邀新人数")
	private Integer inviteAvailableCount;

	/**
	 * 销售额(元) 
	 */
	@Schema(description = "销售额(元)")
	private BigDecimal saleAmount;

	/**
	 * 预估收益
	 */
	@Schema(description = "预估收益")
	private BigDecimal commission;

}