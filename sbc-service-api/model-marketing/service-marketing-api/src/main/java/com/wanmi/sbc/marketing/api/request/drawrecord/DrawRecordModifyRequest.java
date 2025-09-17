package com.wanmi.sbc.marketing.api.request.drawrecord;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>抽奖记录表修改参数</p>
 * @author wwc
 * @date 2021-04-12 16:15:21
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DrawRecordModifyRequest implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 抽奖记录主键
	 */
	@Schema(description = "抽奖记录主键")
	@Max(9223372036854775807L)
	private Long id;

	/**
	 * 抽奖活动id
	 */
	@Schema(description = "抽奖活动id")
	@NotNull
	@Max(9223372036854775807L)
	private Long activityId;

	/**
	 * 抽奖记录编号
	 */
	@Schema(description = "抽奖记录编号")
	@NotBlank
	@Length(max=10)
	private String drawRecordCode;

	/**
	 * 抽奖时间
	 */
	@Schema(description = "抽奖时间")
	@NotNull
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime drawTime;

	/**
	 * 抽奖状态 0 未中奖 1 中奖
	 */
	@Schema(description = "抽奖状态 0 未中奖 1 中奖")
	@NotNull
	@Max(127)
	private Integer drawStatus;

	/**
	 * 奖项id
	 */
	@Schema(description = "奖项id")
	@Max(9223372036854775807L)
	private Long prizeId;

	/**
	 * 奖品名称
	 */
	@Schema(description = "奖品名称")
	@Length(max=20)
	private String prizeName;

	/**
	 * 奖品图片
	 */
	@Schema(description = "奖品图片")
	@Length(max=1024)
	private String prizeUrl;

	/**
	 * 兑奖状态 0未兑奖  1已兑奖
	 */
	@Schema(description = "兑奖状态 0未兑奖  1已兑奖")
	@NotNull
	@Max(127)
	private Integer redeemPrizeStatus;

	/**
	 * 0未发货  1已发货
	 */
	@Schema(description = "0未发货  1已发货")
	@NotNull
	@Max(127)
	private Integer deliverStatus;

	/**
	 * 会员Id
	 */
	@Schema(description = "会员Id")
	@NotBlank
	@Length(max=32)
	private String customerId;

	/**
	 * 会员登录账号|手机号
	 */
	@Schema(description = "会员登录账号|手机号")
	@Length(max=20)
	private String customerAccount;

	/**
	 * 会员名称
	 */
	@Schema(description = "会员名称")
	@Length(max=128)
	private String customerName;

	/**
	 * 详细收货地址(包含省市区）
	 */
	@Schema(description = "详细收货地址(包含省市区）")
	@Length(max=255)
	private String detailAddress;

	/**
	 * 收货人
	 */
	@Schema(description = "收货人")
	@Length(max=128)
	private String consigneeName;

	/**
	 * 收货人手机号码
	 */
	@Schema(description = "收货人手机号码")
	@Length(max=20)
	private String consigneeNumber;

	/**
	 * 兑奖时间
	 */
	@Schema(description = "兑奖时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime redeemPrizeTime;

	/**
	 * 发货时间
	 */
	@Schema(description = "发货时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime deliveryTime;

	/**
	 * 物流公司名称
	 */
	@Schema(description = "物流公司名称")
	@Length(max=128)
	private String logisticsCompany;

	/**
	 * 物流单号
	 */
	@Schema(description = "物流单号")
	@Length(max=32)
	private String logisticsNo;

	/**
	 * 物流公司标准编码
	 */
	@Schema(description = "物流公司标准编码")
	@Length(max=32)
	private String logisticsCode;

	/**
	 * 创建时间
	 */
	@Schema(description = "创建时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTime;

	/**
	 * 修改时间
	 */
	@Schema(description = "修改时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTime;

	/**
	 * 创建人
	 */
	@Schema(description = "创建人")
	@Length(max=32)
	private String createPerson;

	/**
	 * 编辑人
	 */
	@Schema(description = "编辑人")
	@Length(max=32)
	private String updatePerson;

}