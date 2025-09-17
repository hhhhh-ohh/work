package com.wanmi.sbc.marketing.bean.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.SignWordType;
import com.wanmi.sbc.common.sensitiveword.annotation.SensitiveWordsField;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.marketing.bean.enums.GiftCardBusinessType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>礼品卡交易流水实体类</p>
 * @author 吴瑞
 * @date 2022-12-09 14:03:09
 */
@Data
@Schema
public class GiftCardBillVO implements Serializable {

	private static final long serialVersionUID = 4043264917929737209L;

	/**
	 * jiao
	 */
	@Schema(description = "礼品卡Id")
	private Long giftCardBillId;

	/**
	 * 礼品卡Id
	 */
	@Schema(description = "礼品卡Id")
	private Long giftCardId;

	/**
	 * 用户Id
	 */
	@Schema(description = "用户Id")
	private String customerId;

	/**
	 * 用户礼品卡Id
	 */
	@Schema(description = "用户礼品卡Id")
	private Long userGiftCardId;

	/**
	 * 礼品卡卡号
	 */
	@Schema(description = "礼品卡卡号")
	private String giftCardNo;

	/**
	 * 交易类型 0：使用 1：退换 2: 发卡 3：兑换 4：销卡
	 */
	@Schema(description = "交易类型 0：使用 1：退换 2: 发卡 3：兑换 4：销卡")
	private GiftCardBusinessType businessType;

	/**
	 * 业务Id
	 */
	@Schema(description = "业务Id")
	private String businessId;

	/**
	 * 交易前余额
	 */
	@Schema(description = "交易前余额")
	private BigDecimal beforeBalance;

	/**
	 * 交易金额
	 */
	@Schema(description = "交易金额")
	private BigDecimal tradeBalance;

	/**
	 * 交易后余额
	 */
	@Schema(description = "交易后余额")
	private BigDecimal afterBalance;

	/**
	 * 交易时间
	 */
	@Schema(description = "交易时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime tradeTime;

	/**
	 * 交易人类型 0：C端用户 1：B端用户
	 */
	@Schema(description = "交易人类型 0：C端用户 1：B端用户")
	private DefaultFlag tradePersonType;

	/**
	 * 交易人
	 */
	@Schema(description = "交易人")
	private String tradePerson;

	/**
	 * 交易人名称
	 */
	@SensitiveWordsField(signType = SignWordType.NAME)
	@Schema(description = "交易人名称")
	private String tradePersonName;

	/**
	 * 交易人账号
	 */
	@SensitiveWordsField(signType = SignWordType.PHONE)
	@Schema(description = "交易人账号")
	private String tradePersonAccount;

	/**
	 * 礼品卡名称
	 */
	@Schema(description = "礼品卡名称")
	private String giftCardName;

	/**
	 * 礼品卡面值
	 */
	@Schema(description = "礼品卡面值")
	private Long giftCardParValue;

}
