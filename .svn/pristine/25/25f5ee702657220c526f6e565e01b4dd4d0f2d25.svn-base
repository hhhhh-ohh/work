package com.wanmi.sbc.marketing.bean.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.wanmi.sbc.common.enums.SignWordType;
import com.wanmi.sbc.common.sensitiveword.annotation.SensitiveWordsField;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import java.time.LocalDateTime;

import com.wanmi.sbc.marketing.bean.enums.GiftCardDetailStatus;
import com.wanmi.sbc.marketing.bean.enums.GiftCardSendStatus;
import com.wanmi.sbc.marketing.bean.enums.GiftCardSourceType;
import com.wanmi.sbc.marketing.bean.enums.GiftCardType;
import lombok.Data;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>礼品卡详情VO</p>
 * @author 马连峰
 * @date 2022-12-10 10:58:28
 */
@Schema
@Data
public class GiftCardDetailVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 礼品卡卡号，主键
	 */
	@Schema(description = "礼品卡卡号，主键")
	private String giftCardNo;

	/**
	 * 礼品卡id
	 */
	@Schema(description = "礼品卡id")
	private Long giftCardId;

	/**
	 * 批次编号
	 */
	@Schema(description = "批次编号")
	private String batchNo;

	/**
	 * 来源类型 0：制卡 1：发卡 2：购卡
	 */
	@Schema(description = "来源类型 0：制卡 1：发卡 2：购卡")
	private GiftCardSourceType sourceType;

	/**
	 * 兑换码
	 */
	@Schema(description = "兑换码")
	private String exchangeCode;

	/**
	 * 有效期
	 */
	@Schema(description = "有效期")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime expirationTime;

	/**
	 * 会员获卡时间，制卡兑换时间/发卡接收时间
	 */
	@Schema(description = "会员获卡时间，制卡兑换时间/发卡接收时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime acquireTime;

	/**
	 * 激活时间
	 */
	@Schema(description = "激活时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime activationTime;

	/**
	 * 归属会员，制卡兑换人/发卡接收人
	 */
	@Schema(description = "归属会员，制卡兑换人/发卡接收人")
	private String belongPerson;

	/**
	 * 激活会员
	 */
	@Schema(description = "激活会员")
	private String activationPerson;

	/**
	 * 礼品卡详情状态 0:未兑换 1:未激活 2:已激活 3:已销卡 4:已过期
	 */
	@Schema(description = "礼品卡详情状态 0:未兑换 1:未激活 2:已激活 3:已销卡 4:已过期")
	private GiftCardDetailStatus cardDetailStatus;

	/**
	 * 销卡人
	 */
	@Schema(description = "销卡人")
	private String cancelPerson;

	/**
	 * 销卡时间
	 */
	@Schema(description = "销卡时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime cancelTime;

	/**
	 * 发卡状态 0：待发 1：成功 2：失败
	 */
	@Schema(description = "发卡状态 0：待发 1：成功 2：失败")
	private GiftCardSendStatus sendStatus;

	/**
	 * 状态变更原因，目前仅针对销卡原因
	 */
	@Schema(description = "状态变更原因，目前仅针对销卡原因")
	private String statusReason;

	/**
	 * 激活会员名称
	 */
	@SensitiveWordsField(signType = SignWordType.NAME)
	@Schema(description = "激活会员名称")
	private String activationPersonName;

	/**
	 * 激活会员账号
	 */
	@SensitiveWordsField(signType = SignWordType.PHONE)
	@Schema(description = "激活会员账号")
	private String activationPersonAccount;

	/**
	 * 归属会员名称
	 */
	@SensitiveWordsField(signType = SignWordType.NAME)
	@Schema(description = "归属会员名称")
	private String belongPersonName;

	/**
	 * 归属会员账号
	 */
	@SensitiveWordsField(signType = SignWordType.PHONE)
	@Schema(description = "归属会员账号")
	private String belongPersonAccount;

	/**
	 * 批次信息，用于导出聚合
	 */
	@Schema(description = "批次信息，用于导出聚合")
	private GiftCardBatchVO giftCardBatchVO;

	/**
	 * 礼品卡类型
	 */
	@Schema(description = "礼品卡类型")
	private GiftCardType giftCardType;

	/**
	 * 移动端商城网址
	 */
	@Schema(description = "移动端商城网址")
	private String mobileWebsite;

}