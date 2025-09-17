package com.wanmi.sbc.marketing.giftcard.model.root;

import com.wanmi.sbc.marketing.bean.enums.GiftCardDetailStatus;
import com.wanmi.sbc.marketing.bean.enums.GiftCardSendStatus;
import com.wanmi.sbc.marketing.bean.enums.GiftCardSourceType;
import com.wanmi.sbc.marketing.bean.enums.GiftCardType;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import java.time.LocalDateTime;
import com.wanmi.sbc.common.enums.DeleteFlag;

import lombok.Data;
import jakarta.persistence.*;
import com.wanmi.sbc.common.base.BaseEntity;

/**
 * <p>礼品卡详情实体类</p>
 * @author 马连峰
 * @date 2022-12-10 10:58:28
 */
@Data
@Entity
@Table(name = "gift_card_detail")
public class GiftCardDetail extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * 礼品卡卡号，主键
	 */
	@Id
	@Column(name = "gift_card_no")
	private String giftCardNo;

	/**
	 * 礼品卡id
	 */
	@Column(name = "gift_card_id")
	private Long giftCardId;

	/**
	 * 批次编号
	 */
	@Column(name = "batch_no")
	private String batchNo;

	/**
	 * 来源类型 0：制卡 1：发卡 2：购卡
	 */
	@Column(name = "source_type")
	private GiftCardSourceType sourceType;

	/**
	 * 兑换码
	 */
	@Column(name = "exchange_code")
	private String exchangeCode;

	/**
	 * 有效期
	 */
	@Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
	@Column(name = "expiration_time")
	private LocalDateTime expirationTime;

	/**
	 * 会员获卡时间，制卡兑换时间/发卡接收时间
	 */
	@Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
	@Column(name = "acquire_time")
	private LocalDateTime acquireTime;

	/**
	 * 激活时间
	 */
	@Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
	@Column(name = "activation_time")
	private LocalDateTime activationTime;

	/**
	 * 归属会员，制卡兑换人/发卡接收人
	 */
	@Column(name = "belong_person")
	private String belongPerson;

	/**
	 * 激活会员
	 */
	@Column(name = "activation_person")
	private String activationPerson;

	/**
	 * 礼品卡详情状态 0:未兑换 1:未激活 2:已激活 3:已销卡 4:已过期
	 */
	@Column(name = "card_detail_status")
	private GiftCardDetailStatus cardDetailStatus;

	/**
	 * 销卡人
	 */
	@Column(name = "cancel_person")
	private String cancelPerson;

	/**
	 * 销卡时间
	 */
	@Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
	@Column(name = "cancel_time")
	private LocalDateTime cancelTime;

	/**
	 * 发卡状态 0：待发 1：成功 2：失败
	 */
	@Column(name = "send_status")
	private GiftCardSendStatus sendStatus;

	/**
	 * 状态变更原因，目前仅针对销卡原因
	 */
	@Column(name = "status_reason")
	private String statusReason;

	/**
	 * 礼品卡类型
	 */
	@Column(name = "gift_card_type")
	@Enumerated
	private GiftCardType giftCardType;

	/**
	 * 删除标记  0：正常，1：删除
	 */
	@Column(name = "del_flag")
	@Enumerated
	private DeleteFlag delFlag;

}
