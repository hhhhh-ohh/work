package com.wanmi.sbc.order.api.request.payingmemberrecord;

import java.math.BigDecimal;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import java.time.LocalDateTime;
import com.wanmi.sbc.common.util.CustomLocalDateSerializer;
import com.wanmi.sbc.common.util.CustomLocalDateDeserializer;
import java.time.LocalDate;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import lombok.*;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>付费记录表通用查询请求参数</p>
 * @author zhanghao
 * @date 2022-05-13 15:27:53
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayingMemberRecordQueryRequest extends BaseQueryRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 批量查询-记录idList
	 */
	@Schema(description = "批量查询-记录idList")
	private List<String> recordIdList;

	/**
	 * 记录id
	 */
	@Schema(description = "记录id")
	private String recordId;

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
	 * 会员id
	 */
	@Schema(description = "会员id")
	private String customerId;

	/**
	 * 会员名称
	 */
	@Schema(description = "会员名称")
	private String customerName;

	/**
	 * 支付数量
	 */
	@Schema(description = "支付数量")
	private Integer payNum;

	/**
	 * 支付金额
	 */
	@Schema(description = "支付金额")
	private BigDecimal payAmount;

	/**
	 * 搜索条件:支付时间开始
	 */
	@Schema(description = "搜索条件:支付时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime payTimeBegin;
	/**
	 * 搜索条件:支付时间截止
	 */
	@Schema(description = "搜索条件:支付时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime payTimeEnd;

	/**
	 * 搜索条件:会员到期时间开始
	 */
	@Schema(description = "搜索条件:会员到期时间开始")
	@JsonSerialize(using = CustomLocalDateSerializer.class)
	@JsonDeserialize(using = CustomLocalDateDeserializer.class)
	private LocalDate expirationDateBegin;
	/**
	 * 搜索条件:会员到期时间截止
	 */
	@Schema(description = "搜索条件:会员到期时间截止")
	@JsonSerialize(using = CustomLocalDateSerializer.class)
	@JsonDeserialize(using = CustomLocalDateDeserializer.class)
	private LocalDate expirationDateEnd;

	/**
	 * 等级状态：0.生效中，1.未生效，2.已过期，3.已退款
	 */
	@Schema(description = "等级状态：0.生效中，1.未生效，2.已过期，3.已退款")
	private Integer levelState;

	/**
	 * 已优惠金额
	 */
	@Schema(description = "已优惠金额")
	private BigDecimal alreadyDiscountAmount;

	/**
	 * 已领积分
	 */
	@Schema(description = "已领积分")
	private Long alreadyReceivePoint;

	/**
	 * 退款金额
	 */
	@Schema(description = "退款金额")
	private BigDecimal returnAmount;

	/**
	 * 退款回收券 0.是，1.否
	 */
	@Schema(description = "退款回收券 0.是，1.否")
	private Integer returnCoupon;

	/**
	 * 退款回收积分 0.是，1.否
	 */
	@Schema(description = "退款回收积分 0.是，1.否")
	private Integer returnPoint;

	/**
	 * 权益id集合
	 */
	@Schema(description = "权益id集合")
	private String rightsIds;

	/**
	 * 首次开通 0.是，1.否
	 */
	@Schema(description = "首次开通 0.是，1.否")
	private Integer firstOpen;

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