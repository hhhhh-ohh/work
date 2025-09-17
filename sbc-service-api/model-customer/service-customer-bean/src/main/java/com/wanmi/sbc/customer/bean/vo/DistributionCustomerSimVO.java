package com.wanmi.sbc.customer.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.enums.DefaultFlag;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>分销员VO</p>
 * @author lq
 * @date 2019-02-19 10:13:15
 */
@Schema
@Data
public class DistributionCustomerSimVO extends BasicResponse {
	private static final long serialVersionUID = 1L;

	/**
	 * 会员的详情的Id
	 */
    @Schema(description = "分销员标识UUID")
	private String customerDetailId;

	/**
	 * 会员ID
	 */
    @Schema(description = "会员ID")
	private String customerId;

	/**
	 * 分销员ID
	 */
	@Schema(description = "分销员ID")
	private String distributionId;

	/**
	 * 分销员等级ID
	 */
	@Schema(description = "分销员等级ID")
	private String distributorLevelId;

	/**
	 * 会员名称
	 */
    @Schema(description = "会员名称")
	private String customerName;

	/**
	 * 是否为分销员
	 */
	@Schema(description = "是否为分销员")
	private DefaultFlag distributorFlag;

	/**
	 * 是否被禁用
	 */
	@Schema(description = "是否被禁用")
	private DefaultFlag forbiddenFlag;

	/**
	 * 分销员等级规则说明
	 */
	@Schema(description = "分销员等级规则说明")
	private String distributorLevelDesc;


}