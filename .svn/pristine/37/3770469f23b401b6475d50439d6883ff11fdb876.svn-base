package com.wanmi.sbc.marketing.bean.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.marketing.bean.enums.GiftCardSourceType;
import com.wanmi.sbc.marketing.bean.enums.GiftCardStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * <p>用户礼品卡实体类</p>
 * @author 吴瑞
 * @date 2022-12-09 14:02:19
 */
@Data
@Schema
public class UserGiftCardVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 用户礼品卡Id
	 */
	@Schema(description = "用户礼品卡Id")
	private Long userGiftCardId;

	/**
	 * 用户Id
	 */
	@Schema(description = "用户Id")
	private String customerId;

	/**
	 * 礼品卡Id
	 */
	@Schema(description = "礼品卡Id")
	private Long giftCardId;

	/**
	 * 礼品卡名称
	 */
	@Schema(description = "礼品卡名称")
	private String giftCardName;

	/**
	 * 礼品卡批次号
	 */
	@Schema(description = "礼品卡批次号")
	private String batchNo;

	/**
	 * 礼品卡明细Id
	 */
	@Schema(description = "礼品卡明细Id")
	private Long giftCardDetailId;

	/**
	 * 礼品卡卡号
	 */
	@Schema(description = "礼品卡卡号")
	private String giftCardNo;

	/**
	 * 礼品卡面值
	 */
	@Schema(description = "礼品卡面值")
	private Long parValue;

	/**
	 * 礼品卡余额
	 */
	@Schema(description = "礼品卡余额")
	private BigDecimal balance;

	/**
	 * 礼品卡状态 0：待激活  1：已激活 2：已销卡
	 */
	@Schema(description = "礼品卡状态 0：待激活  1：已激活 2：已销卡")
	private GiftCardStatus cardStatus;

	/**
	 * 过期时间
	 */
	@Schema(description = "过期时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	public LocalDateTime expirationTime;

	/**
	 * 礼品卡来源
	 */
	@Schema(description = "礼品卡来源")
	private GiftCardSourceType sourceType;

	/**
	 * 会员获卡时间，制卡兑换时间/发卡接收时间
	 */
	@Schema(description = "会员获卡时间，制卡兑换时间/发卡接收时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	public LocalDateTime acquireTime;

	/**
	 * 归属会员，制卡兑换人/发卡接收人
	 */
	@Schema(description = "归属会员，制卡兑换人/发卡接收人")
	private String belongPerson;

	/**
	 * 激活时间
	 */
	@Schema(description = "激活时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime activationTime;

	/**
	 * 销卡时所剩余额
	 */
	@Schema(description = "销卡时所剩余额")
	private BigDecimal cancelBalance;

	/**
	 * 销卡时间
	 */
	@Schema(description = "销卡时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime cancelTime;

	/**
	 * 销卡人
	 */
	@Schema(description = "销卡人")
	private String cancelPerson;

	/**
	 * 销卡描述
	 */
	@Schema(description = "销卡描述")
	private String cancelDesc;

}
