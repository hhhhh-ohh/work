package com.wanmi.sbc.customer.distribution.model.root;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>用户分销排行榜统计数据实体类</p>
 * @author lq
 * @date 2019-04-19 10:05:05
 */
@Data
@NoArgsConstructor
public class DistributionCustomerRankingBase implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 会员ID
	 */
	private String customerId;

	/**
	 * 邀新人数\有效邀新人数\销售额(元)\预估收益
	 */
	private Long num;
	/**
	 * 销售额(元)\预估收益
	 */
	private BigDecimal totalAmount;

	public DistributionCustomerRankingBase(String customerId, Long num) {
		this.customerId = customerId;
		this.num = num;
	}

	public DistributionCustomerRankingBase(String customerId, BigDecimal totalAmount) {
		this.customerId = customerId;
		this.totalAmount = totalAmount;
	}
}