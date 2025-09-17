package com.wanmi.sbc.marketing.bean.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.SignWordType;
import com.wanmi.sbc.common.sensitiveword.annotation.SensitiveWordsField;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.common.util.DateUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * <p>礼品卡单卡明细VO</p>
 * @author edy
 * @date 2023-10-24 10:59:47
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GiftCardDetailJoinVO implements Serializable {
	private static final long serialVersionUID = -8261536208704785040L;

	/**
	 * 礼品卡Id
	 */
	@Schema(description = "礼品卡Id")
	private Long giftCardId;

	/**
	 * 礼品卡卡号，主键
	 */
	@Schema(description = "礼品卡卡号，主键")
	private String giftCardNo;

	@Schema(description = "兑换吗")
	private String exchangeCode;

	/**
	 * 批次类型 0:制卡 1:发卡
	 */
	@Schema(description = "批次类型 0:制卡 1:发卡")
	private Integer batchType;

	/**
	 * 批次编号(制/发卡批次)，年月日时分秒毫秒+3位随机数
	 */
	@Schema(description = "批次编号(制/发卡批次)，年月日时分秒毫秒+3位随机数")
	private String batchNo;

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
	 *  礼品卡名称
	 */
	@Schema(description = "礼品卡名称")
	private String mainName;

	/**
	 *  面值
	 */
	@Schema(description = "面值")
	private BigDecimal parValue;

	/**
	 *  卡类型: 0 现金卡、1 全选提货卡、2 任选提货卡、3 平台储值卡、4 企业储值卡
	 */
	@Schema(description = "卡类型: 0 现金卡、1 全选提货卡、2 任选提货卡、3 平台储值卡、4 企业储值卡")
	private Integer giftCardType;

	/**
	 * 礼品卡余额
	 */
	@Schema(description = "礼品卡余额")
	private BigDecimal balance;

	/**
	 * 礼品卡状态 0：待激活  1：已激活 2：已作废
	 */
	@Schema(description = "礼品卡状态 0：待激活  1：已激活 2：已作废")
	private Integer cardStatus;

	/**
	 * 过期时间类型 0：长期有效 1：领取多少月内有效 2：具体时间
	 */
	@Schema(description = "过期时间类型 0：长期有效 1：领取多少月内有效 2：具体时间")
	private Integer expirationType;

	/**
	 * 指定月数
	 */
	@Schema(description = "指定月数")
	private Long rangeMonth;

	/**
	 * 过期时间
	 */
	@Schema(description = "过期时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime expirationTime;

	/**
	 * 过期开始时间
	 */
	@Schema(description = "过期开始时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime expirationStartTime;

	/**
	 * 礼品卡详情状态 0：未兑换 1：未激活 2：已激活 3：已作废 4：已过期 5：未开通 6：已作废
	 */
	@Schema(description = "礼品卡详情状态 0：未兑换 1：未激活 2：已激活 3：已作废 4：已过期 5：已作废")
	private Integer cardDetailStatus;

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
	 *  卡销售单号
	 */
	@Schema(description = "卡销售单号")
	private String businessNo;

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
	 * 发卡/兑换会员名称
	 */
	@SensitiveWordsField(signType = SignWordType.NAME)
	@Schema(description = "归属会员名称")
	private String belongPersonName;

	/**
	 * 发卡/兑换会员账号
	 */
	@SensitiveWordsField(signType = SignWordType.PHONE)
	@Schema(description = "归属会员账号")
	private String belongPersonAccount;

	@Schema(description = "制卡人id")
	private String  generatePerson;

	@Schema(description = "制卡人名称")
	private String  generatePersonName;

	/**
	 * 批次数量(制/发卡数量)
	 */
	@Schema(description = "批次数量(制/发卡数量)")
	private Long batchNum;

	@Schema(description = "0：待审核，1：审核通过，2：审核不通过")
	private Integer auditStatus;

	@Schema(description = "制卡时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime generateTime;

	@Schema(description = "发卡人类型 0：会员账号 1：辅助验证信息")
	private Integer sendPersonType;

	@Schema(description = "发卡状态 0：待发 1：成功 2：失败")
	private Integer sendStatus;

	private String expirateStr;

	/**
	 * 移动端商城网址
	 */
	@Schema(description = "移动端商城网址")
	private String mobileWebsite;

	@Schema(description = "是否导出小程序一卡一码URL，0:不导出，1：导出")
	private Integer exportMiniCodeType;

	@Schema(description = "是否导出H5一卡一码URL，0:不导出，1：导出")
	private Integer exportWebCodeType;


	public String getExpirateStr() {
		if (Objects.nonNull(expirationTime)) {
			return DateUtil.format(expirationTime, DateUtil.FMT_TIME_1);
		} else if(Objects.nonNull(expirationType)){
			switch (expirationType) {
				case 0:
					return "长期有效";
				case 1:
					return "自激活后" + rangeMonth + "月内有效";
				case 2:
					return expirationTime == null ? "-" : DateUtil.format(expirationTime, DateUtil.FMT_TIME_1);
			}
		}
		return expirateStr;
	}
}