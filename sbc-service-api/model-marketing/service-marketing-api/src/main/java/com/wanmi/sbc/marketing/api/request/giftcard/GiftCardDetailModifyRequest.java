package com.wanmi.sbc.marketing.api.request.giftcard;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.marketing.bean.enums.GiftCardDetailStatus;
import com.wanmi.sbc.marketing.bean.enums.GiftCardSendStatus;
import com.wanmi.sbc.marketing.bean.enums.GiftCardSourceType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.*;
import jakarta.validation.constraints.NotBlank;

import lombok.*;

import org.hibernate.validator.constraints.*;

import java.time.LocalDateTime;

/**
 * <p>礼品卡详情修改参数</p>
 * @author 马连峰
 * @date 2022-12-10 10:58:28
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GiftCardDetailModifyRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 礼品卡卡号，主键
	 */
	@Schema(description = "礼品卡卡号，主键")
	@Length(max=20)
	private String giftCardNo;

	/**
	 * 礼品卡Id
	 */
	@Schema(description = "礼品卡Id")
	@NotNull
	@Max(9223372036854775807L)
	private Long giftCardId;

	/**
	 * 批次编号
	 */
	@Schema(description = "批次编号")
	@NotBlank
	@Length(max=18)
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
	@Length(max=20)
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
	@Length(max=32)
	private String belongPerson;

	/**
	 * 激活会员
	 */
	@Schema(description = "激活会员")
	@Length(max=32)
	private String activationPerson;

	/**
	 * 礼品卡详情状态 0:未兑换 1:未激活 2:已激活 3:已销卡 4:已过期
	 */
	@Schema(description = "礼品卡详情状态 0:未兑换 1:未激活 2:已激活 3:已销卡 4:已过期")
	@NotNull
	private GiftCardDetailStatus cardDetailStatus;

	/**
	 * 销卡人
	 */
	@Schema(description = "销卡人")
	@Length(max=32)
	private String cancelPerson;

	/**
	 * 发卡状态 0：待发 1：成功 2：失败
	 */
	@Schema(description = "发卡状态 0：待发 1：成功 2：失败")
	private GiftCardSendStatus sendStatus;

	/**
	 * 状态变更原因，目前仅针对销卡原因
	 */
	@Schema(description = "状态变更原因，目前仅针对销卡原因")
	@Length(max=255)
	private String statusReason;

	/**
	 * 更新人
	 */
	@Schema(description = "更新人", hidden = true)
	private String updatePerson;

}
