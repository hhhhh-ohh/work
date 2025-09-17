package com.wanmi.sbc.goods.api.request.goodsrestrictedcustomerrela;

import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.goods.bean.enums.AssignPersonRestrictedType;
import com.wanmi.sbc.goods.bean.enums.PersonRestrictedType;
import lombok.*;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>限售配置通用查询请求参数</p>
 * @author baijz
 * @date 2020-04-08 11:32:28
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsRestrictedCustomerRelaQueryRequest extends BaseQueryRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 批量查询-限售会员的关系主键List
	 */
	@Schema(description = "批量查询-限售会员的关系主键List")
	private List<Long> relaIdList;

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
	private String customerId;

	/**
	 * 会员等级ID
	 */
	@Schema(description = "会员等级ID")
	private Long customerLevelId;

}