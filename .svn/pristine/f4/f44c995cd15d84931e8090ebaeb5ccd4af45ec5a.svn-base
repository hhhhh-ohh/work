package com.wanmi.sbc.customer.api.request.distribution;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.*;

import lombok.Data;

import org.hibernate.validator.constraints.*;

import java.math.BigDecimal;

/**
 * <p>用户分销排行榜新增参数</p>
 * @author lq
 * @date 2019-04-19 10:05:05
 */
@Schema
@Data
public class DistributionCustomerRankingAddRequest extends BaseRequest {


	private static final long serialVersionUID = 1L;
	/**
	 * 邀新人数
	 */
    @Schema(description = "邀新人数")
	@Max(9999999999L)
	private Integer inviteCount;

	/**
	 * 有效邀新人数
	 */
    @Schema(description = "有效邀新人数")
	@Max(9999999999L)
	private Integer inviteAvailableCount;

	/**
	 * 销售额(元) 
	 */
    @Schema(description = "销售额(元) ")
	private BigDecimal saleAmount;

	/**
	 * 预估收益
	 */
    @Schema(description = "预估收益")
	private BigDecimal commission;

}