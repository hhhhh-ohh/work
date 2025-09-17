package com.wanmi.sbc.marketing.bean.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.LogOutStatus;
import com.wanmi.sbc.common.enums.SignWordType;
import com.wanmi.sbc.common.sensitiveword.annotation.SensitiveWordsField;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.marketing.bean.enums.DrawPrizeType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>抽奖记录表VO</p>
 * @author wwc
 * @date 2021-04-12 16:15:21
 */
@Schema
@Data
public class DrawRecordVO implements Serializable {
	private static final long serialVersionUID = 1L;

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
	 * 抽奖时间
	 */
	@Schema(description = "抽奖时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime drawTime;

	/**
	 * 抽奖状态 0 未中奖 1 中奖
	 */
	@Schema(description = "抽奖状态 0 未中奖 1 中奖")
	private Integer drawStatus;

	/**
	 * 抽奖状态名称
	 */
	@Schema(description = "抽奖状态名称")
	private String drawStatusName;

	/**
	 * 奖项id
	 */
	@Schema(description = "奖项id")
	private Long prizeId;

	/**
	 * 奖品类型（0：积分 1：优惠券 2：实物奖品 3：自定义）
	 */
	@Schema(description = "奖品类型（0：积分 1：优惠券 2：实物奖品 3：自定义）")
	private DrawPrizeType prizeType;

	/**
	 * 奖品类型名称
	 */
	@Schema(description = "奖品类型名称")
	private String prizeTypeName;

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
	 * 兑奖状态名称
	 */
	@Schema(description = "兑奖状态名称")
	private String redeemPrizeStatusName;

	/**
	 * 0未发货  1已发货
	 */
	@Schema(description = "0未发货  1已发货")
	private Integer deliverStatus;

	/**
	 * 发货状态名称
	 */
	@Schema(description = "发货状态名称")
	private String deliverStatusName;

	/**
	 * 会员Id
	 */
	@Schema(description = "会员Id")
	private String customerId;

	/**
	 * 会员登录账号|手机号
	 */
	@Schema(description = "会员登录账号|手机号")
	@SensitiveWordsField(signType = SignWordType.PHONE)
	private String customerAccount;

	/**
	 * 会员名称
	 */
	@Schema(description = "会员名称")
	@SensitiveWordsField(signType = SignWordType.NAME)
	private String customerName;

	/**
	 * 详细收货地址(包含省市区）
	 */
	@Schema(description = "详细收货地址(包含省市区）")
	@SensitiveWordsField(signType = SignWordType.ADDRESS)
	private String detailAddress;

	/**
	 * 收货人
	 */
	@Schema(description = "收货人")
	@SensitiveWordsField(signType = SignWordType.NAME)
	private String consigneeName;

	/**
	 * 收货人手机号码
	 */
	@Schema(description = "收货人手机号码")
	@SensitiveWordsField(signType = SignWordType.PHONE)
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
	private String createPerson;

	/**
	 * 编辑人
	 */
	@Schema(description = "编辑人")
	private String updatePerson;

	/**
	 * 活动名称
	 */
	@Schema(description = "活动名称")
	private String activityName;

	/**
	 * 发货时间
	 */
	@Schema(description = "发货时间字符串格式")
	private String deliveryTimeOfString;

	/**
	 * 自定义奖品内容
	 */
	@Schema(description = "自定义奖品内容")
	private String customize;

	/**
	 * 注销状态 0:正常 1:注销中 2:已注销
	 */
	@Schema(description = "注销状态 0:正常 1:注销中 2:已注销")
	private LogOutStatus logOutStatus;
}