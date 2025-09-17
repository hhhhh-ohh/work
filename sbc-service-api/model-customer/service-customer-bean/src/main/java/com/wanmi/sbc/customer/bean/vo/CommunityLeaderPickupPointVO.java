package com.wanmi.sbc.customer.bean.vo;

import com.wanmi.sbc.common.enums.LogOutStatus;
import com.wanmi.sbc.common.enums.SignWordType;
import com.wanmi.sbc.common.sensitiveword.annotation.SensitiveWordsField;
import com.wanmi.sbc.customer.bean.enums.LeaderCheckStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>团长自提点表VO</p>
 * @author dyt
 * @date 2023-07-21 14:10:45
 */
@Schema
@Data
public class CommunityLeaderPickupPointVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 自提点id
	 */
	@Schema(description = "自提点id")
	private String pickupPointId;

	/**
	 * 团长id
	 */
	@Schema(description = "团长id")
	private String leaderId;

	/**
	 * 团长会员id
	 */
	@Schema(description = "团长会员id")
	private String customerId;

	/**
	 * 团长账号
	 */
	@SensitiveWordsField(signType = SignWordType.PHONE)
	@Schema(description = "团长账号")
	private String leaderAccount;

	/**
	 * 团长名称
	 */
	@SensitiveWordsField(signType = SignWordType.NAME)
	@Schema(description = "团长名称")
	private String leaderName;

	/**
	 * 审核状态, 0:未审核 1审核通过 2审核失败 3禁用中
	 */
	@Schema(description = "审核状态, 0:未审核 1审核通过 2审核失败 3禁用中")
	private LeaderCheckStatus checkStatus;

	/**
	 * 自提点名称
	 */
	@Schema(description = "自提点名称")
	private String pickupPointName;

	/**
	 * 自提点省份
	 */
	@Schema(description = "自提点省份")
	private Long pickupProvinceId;

	/**
	 * 自提点城市
	 */
	@Schema(description = "自提点城市")
	private Long pickupCityId;

	/**
	 * 自提点区县
	 */
	@Schema(description = "自提点区县")
	private Long pickupAreaId;

	/**
	 * 自提点街道
	 */
	@Schema(description = "自提点街道")
	private Long pickupStreetId;

	/**
	 * 详细地址
	 */
	@SensitiveWordsField(signType = SignWordType.ADDRESS)
	@Schema(description = "详细地址")
	private String address;

	/**
	 * 经度
	 */
	@Schema(description = "经度")
	private BigDecimal lng;

	/**
	 * 纬度
	 */
	@Schema(description = "纬度")
	private BigDecimal lat;

	/**
	 * 联系电话
	 */
	@Schema(description = "联系电话")
	@SensitiveWordsField(signType = SignWordType.PHONE)
	private String contactNumber;

	/**
	 * 自提时间
	 */
	@Schema(description = "自提时间")
	private String pickupTime;

	/**
	 * 全详细地址
	 */
	@Schema(description = "全详细地址")
	@SensitiveWordsField(signType = SignWordType.ADDRESS)
	private String fullAddress;

	/**
	 * 是否帮卖 0:否 1:是
	 */
	@Schema(description = "是否帮卖 0:否 1:是")
	private Integer assistFlag;

	/**
	 * 自提点照片
	 */
	@Schema(description = "自提点照片")
	private String pickupPhotos;

	/**
	 * 注销状态 0:正常 1:注销中 2:已注销
	 */
	@Schema(description = "注销状态 0:正常 1:注销中 2:已注销")
	private LogOutStatus logOutStatus;

	@Schema(description = "距离")
	private long distance;

	/**
	 * 自提服务订单数
	 */
	@Schema(description = "自提服务订单数")
	private Long pickupServiceOrderNum= 0L;

	/**
	 * 帮卖订单数
	 */
	@Schema(description = "帮卖订单数")
	private Long assistOrderNum = 0L;
}