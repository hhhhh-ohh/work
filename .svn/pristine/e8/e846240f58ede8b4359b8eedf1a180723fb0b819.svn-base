package com.wanmi.sbc.marketing.api.request.drawrecord;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>抽奖记录表分页查询请求参数</p>
 * @author wwc
 * @date 2021-04-12 16:15:21
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DrawRecordPageRequest extends BaseQueryRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 批量查询-抽奖记录主键List
	 */
	@Schema(description = "批量查询-抽奖记录主键List")
	private List<Long> idList;

	/**
	 * 抽奖记录主键
	 */
	@Schema(description = "抽奖记录主键")
	private Long id;

	/**
	 * 抽奖活动id
	 */
	@Schema(description = "抽奖活动id")
	private Long activityId;

	/**
	 * 抽奖记录编号
	 */
	@Schema(description = "抽奖记录编号")
	private String drawRecordCode;

	/**
	 * 搜索条件:抽奖时间开始
	 */
	@Schema(description = "搜索条件:抽奖时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime drawTimeBegin;
	/**
	 * 搜索条件:抽奖时间截止
	 */
	@Schema(description = "搜索条件:抽奖时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime drawTimeEnd;

	/**
	 * 抽奖状态 0 未中奖 1 中奖
	 */
	@Schema(description = "抽奖状态 0 未中奖 1 中奖")
	private Integer drawStatus;

	/**
	 * 奖项id
	 */
	@Schema(description = "奖项id")
	private Long prizeId;

	/**
	 * 奖品类型（0：积分 1：优惠券 2：实物奖品 3：自定义）
	 */
	@Schema(description = "奖品类型（0：积分 1：优惠券 2：实物奖品 3：自定义）")
	private Integer prizeType;

	/**
	 * 奖品名称
	 */
	@Schema(description = "奖品名称")
	private String prizeName;

	/**
	 * 奖品图片
	 */
	@Schema(description = "奖品图片")
	private String prizeUrl;

	/**
	 * 兑奖状态 0未兑奖  1已兑奖
	 */
	@Schema(description = "兑奖状态 0未兑奖  1已兑奖")
	private Integer redeemPrizeStatus;

	/**
	 * 0未发货  1已发货
	 */
	@Schema(description = "0未发货  1已发货")
	private Integer deliverStatus;

	/**
	 * 会员Id
	 */
	@Schema(description = "会员Id")
	private String customerId;

	/**
	 * 会员登录账号|手机号
	 */
	@Schema(description = "会员登录账号|手机号")
	private String customerAccount;

	/**
	 * 会员名称
	 */
	@Schema(description = "会员名称")
	private String customerName;

	/**
	 * 详细收货地址(包含省市区）
	 */
	@Schema(description = "详细收货地址(包含省市区）")
	private String detailAddress;

	/**
	 * 收货人
	 */
	@Schema(description = "收货人")
	private String consigneeName;

	/**
	 * 收货人手机号码
	 */
	@Schema(description = "收货人手机号码")
	private String consigneeNumber;

	/**
	 * 搜索条件:兑奖时间开始
	 */
	@Schema(description = "搜索条件:兑奖时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime redeemPrizeTimeBegin;
	/**
	 * 搜索条件:兑奖时间截止
	 */
	@Schema(description = "搜索条件:兑奖时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime redeemPrizeTimeEnd;

	/**
	 * 搜索条件:发货时间开始
	 */
	@Schema(description = "搜索条件:发货时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime deliveryTimeBegin;
	/**
	 * 搜索条件:发货时间截止
	 */
	@Schema(description = "搜索条件:发货时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime deliveryTimeEnd;

	/**
	 * 物流公司名称
	 */
	@Schema(description = "物流公司名称")
	private String logisticsCompany;

	/**
	 * 物流单号
	 */
	@Schema(description = "物流单号")
	private String logisticsNo;

	/**
	 * 物流公司标准编码
	 */
	@Schema(description = "物流公司标准编码")
	private String logisticsCode;

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
	 * 搜索条件:修改时间开始
	 */
	@Schema(description = "搜索条件:修改时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTimeBegin;
	/**
	 * 搜索条件:修改时间截止
	 */
	@Schema(description = "搜索条件:修改时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTimeEnd;

	/**
	 * 创建人
	 */
	@Schema(description = "创建人")
	private String createPerson;

	/**
	 * 编辑人
	 */
	@Schema(description = "编辑人")
	private String updatePerson;

}