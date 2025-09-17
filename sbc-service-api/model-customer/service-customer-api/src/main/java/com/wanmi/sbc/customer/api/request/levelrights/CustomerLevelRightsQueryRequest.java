package com.wanmi.sbc.customer.api.request.levelrights;

import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.customer.bean.enums.LevelRightsType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 * <p>会员等级权益表通用查询请求参数</p>
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerLevelRightsQueryRequest extends BaseQueryRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 批量查询-主键idList
	 */
	@Schema(description = "批量查询-主键idList")
	private List<Integer> rightsIdList;

	/**
	 * 主键id
	 */
	@Schema(description = "主键id")
	private Integer rightsId;

	/**
	 * 权益名称
	 */
	@Schema(description = "权益名称")
	private String rightsName;

	/**
	 * 权益类型 0等级徽章 1专属客服 2会员折扣 3券礼包 4返积分
	 */
	@Schema(description = "权益类型 0等级徽章 1专属客服 2会员折扣 3券礼包 4返积分")
	private LevelRightsType rightsType;

	/**
	 * logo地址
	 */
	@Schema(description = "logo地址")
	private String rightsLogo;

	/**
	 * 权益介绍
	 */
	@Schema(description = "权益介绍")
	private String rightsDescription;

	/**
	 * 权益规则(JSON)
	 */
	@Schema(description = "权益规则(JSON)")
	private String rightsRule;

	/**
	 * 活动Id
	 */
	@Schema(description = "活动Id")
	private String activityId;

	/**
	 * 是否开启 0:关闭 1:开启
	 */
	@Schema(description = "是否开启 0:关闭 1:开启")
	private Integer status;

	/**
	 * 删除标识 0:未删除1:已删除
	 */
	@Schema(description = " 删除标识 0:未删除1:已删除")
	private DeleteFlag delFlag;

	/**
	 * 过滤类型
	 */
	@Schema(description = "过滤类型")
	private List<LevelRightsType> filterTypes;


	/**
	 * 会员ID
	 */
	@Schema(description = "会员ID")
	private String customerId;

	/**
	 * 等级状态：0.生效中，1.未生效，2.已过期，3.已退款
	 */
	@Schema(description = "等级状态：0.生效中，1.未生效，2.已过期，3.已退款")
	private Integer levelState;

	/**
	 * 记录id
	 */
	@Schema(description = "记录id")
	private String recordId;
}