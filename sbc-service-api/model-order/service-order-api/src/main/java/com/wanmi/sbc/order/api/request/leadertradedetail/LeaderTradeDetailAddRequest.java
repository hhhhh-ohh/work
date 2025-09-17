package com.wanmi.sbc.order.api.request.leadertradedetail;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.DeleteFlag;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Max;

import lombok.*;

import org.hibernate.validator.constraints.Length;

/**
 * <p>团长订单新增参数</p>
 * @author Bob
 * @date 2023-08-03 14:16:52
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LeaderTradeDetailAddRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 团长ID
	 */
	@Schema(description = "团长ID")
	@Length(max=32)
	private String leaderId;

	/**
	 * 团长的会员ID
	 */
	@Schema(description = "团长的会员ID")
	@Length(max=32)
	private String leaderCustomerId;

	/**
	 * 社区团购活动ID
	 */
	@Schema(description = "社区团购活动ID")
	@Length(max=32)
	private String communityActivityId;

	/**
	 * 订单会员ID
	 */
	@Schema(description = "订单会员ID")
	@Length(max=32)
	private String customerId;

	/**
	 * 会员名称
	 */
	@Schema(description = "会员名称")
	@Length(max=128)
	private String customerName;

	/**
	 * 会员头像
	 */
	@Schema(description = "会员头像")
	@Length(max=128)
	private String customerPic;

	/**
	 * 订单ID
	 */
	@Schema(description = "订单ID")
	@Length(max=32)
	private String tradeId;

	/**
	 * 商品ID
	 */
	@Schema(description = "商品ID")
	@Length(max=64)
	private String goodsInfoId;

	/**
	 * 商品规格
	 */
	@Schema(description = "商品规格")
	@Length(max=64)
	private String goodsInfoSpec;

	/**
	 * 商品数量
	 */
	@Schema(description = "商品数量")
	@Max(9999999999L)
	private Long goodsInfoNum;

	/**
	 * 跟团号
	 */
	@Schema(description = "跟团号")
	@Max(9999999999L)
	private Long activityTradeNo;

	/**
	 * 创建人
	 */
	@Schema(description = "创建人", hidden = true)
	private String createPerson;

	/**
	 * 更新人
	 */
	@Schema(description = "更新人", hidden = true)
	private String updatePerson;

	/**
	 * 是否删除 
	 */
	@Schema(description = "是否删除 ", hidden = true)
	private DeleteFlag delFlag;

}