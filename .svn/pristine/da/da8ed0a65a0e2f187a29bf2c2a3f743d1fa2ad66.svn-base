package com.wanmi.sbc.customer.api.request.payingmemberlevel;

import java.math.BigDecimal;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import java.time.LocalDateTime;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import lombok.*;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>付费会员等级表通用查询请求参数</p>
 * @author zhanghao
 * @date 2022-05-13 11:42:42
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayingMemberLevelQueryRequest extends BaseQueryRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 批量查询-付费会员等级idList
	 */
	@Schema(description = "批量查询-付费会员等级idList")
	private List<Integer> levelIdList;

	/**
	 * 付费会员等级id
	 */
	@Schema(description = "付费会员等级id")
	private Integer levelId;

	/**
	 * 付费会员等级名称
	 */
	@Schema(description = "付费会员等级名称")
	private String levelName;

	/**
	 * 付费会员等级昵称
	 */
	@Schema(description = "付费会员等级昵称")
	private String levelNickName;

	/**
	 * 付费会员等级状态 0.开启 1.暂停
	 */
	@Schema(description = "付费会员等级状态 0.开启 1.暂停")
	private Integer levelState;

	/**
	 * 付费会员等级背景类型：0.背景色 1.背景图
	 */
	@Schema(description = "付费会员等级背景类型：0.背景色 1.背景图")
	private Integer levelBackGroundType;

	/**
	 * 付费会员等级背景详情
	 */
	@Schema(description = "付费会员等级背景详情")
	private String levelBackGroundDetail;

	/**
	 * 付费会员等级字体颜色
	 */
	@Schema(description = "付费会员等级字体颜色")
	private String levelFontColor;

	/**
	 * 付费会员等级商家范围：0.自营商家 1.自定义选择
	 */
	@Schema(description = "付费会员等级商家范围：0.自营商家 1.自定义选择")
	private Integer levelStoreRange;

	/**
	 * 付费会员等级折扣类型：0.所有商品统一设置 1.自定义商品设置
	 */
	@Schema(description = "付费会员等级折扣类型：0.所有商品统一设置 1.自定义商品设置")
	private Integer levelDiscountType;

	/**
	 * 付费会员等级所有商品统一设置 折扣
	 */
	@Schema(description = "付费会员等级所有商品统一设置 折扣")
	private BigDecimal levelAllDiscount;

	/**
	 * 搜索条件:创建时间开始
	 */
	@Schema(description = "搜索条件:创建时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTimeBegin;
	/**
	 * 搜索条件:创建时间截止
	 */
	@Schema(description = "搜索条件:创建时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTimeEnd;

	/**
	 * createPerson
	 */
	@Schema(description = "createPerson")
	private String createPerson;

	/**
	 * 搜索条件:更新时间开始
	 */
	@Schema(description = "搜索条件:更新时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTimeBegin;
	/**
	 * 搜索条件:更新时间截止
	 */
	@Schema(description = "搜索条件:更新时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTimeEnd;

	/**
	 * updatePerson
	 */
	@Schema(description = "updatePerson")
	private String updatePerson;

	/**
	 * 删除标识：0：未删除；1：已删除
	 */
	@Schema(description = "删除标识：0：未删除；1：已删除")
	private DeleteFlag delFlag;

}