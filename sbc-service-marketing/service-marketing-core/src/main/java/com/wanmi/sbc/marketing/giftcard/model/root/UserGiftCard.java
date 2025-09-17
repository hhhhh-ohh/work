package com.wanmi.sbc.marketing.giftcard.model.root;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.marketing.bean.enums.ExpirationType;
import com.wanmi.sbc.marketing.bean.enums.GiftCardSourceType;
import com.wanmi.sbc.marketing.bean.enums.GiftCardStatus;
import com.wanmi.sbc.marketing.bean.enums.GiftCardType;
import lombok.Data;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>用户礼品卡实体类</p>
 * @author 吴瑞
 * @date 2022-12-09 14:02:19
 */
@Data
@Entity
@Table(name = "user_gift_card")
public class UserGiftCard implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 礼品卡Id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_gift_card_id")
	private Long userGiftCardId;

	/**
	 * 用户Id
	 */
	@Column(name = "customer_id")
	private String customerId;

	/**
	 * 礼品卡Id
	 */
	@Column(name = "gift_card_id")
	private Long giftCardId;

	/**
	 * 礼品卡名称
	 */
	@Column(name = "gift_card_name")
	private String giftCardName;

	/**
	 * 礼品卡批次号
	 */
	@Column(name = "batch_no")
	private String batchNo;

	/**
	 * 礼品卡卡号
	 */
	@Column(name = "gift_card_no")
	private String giftCardNo;

	/**
	 * 礼品卡面值
	 */
	@Column(name = "par_value")
	private Long parValue;

	/**
	 * 礼品卡余额
	 */
	@Column(name = "balance")
	private BigDecimal balance;

	/**
	 * 礼品卡状态 0：待激活  1：已激活 2：已销卡
	 */
	@Column(name = "card_status")
	private GiftCardStatus cardStatus;

	/**
	 * 过期时间类型 0：长期有效 1：领取多少月内有效 2：具体时间
	 */
	@Column(name = "expiration_type")
	@Enumerated
	private ExpirationType expirationType;

	/**
	 * 过期时间
	 */
	@Column(name = "expiration_time")
	@Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime expirationTime;

	/**
	 * 卡的来源 0：制卡 1：发卡
	 */
	@Column(name = "source_type")
	private GiftCardSourceType sourceType;

	/**
	 * 会员获卡时间，制卡兑换时间/发卡接收时间
	 */
	@Column(name = "acquire_time")
	@Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime acquireTime;

	/**
	 * 归属会员，制卡兑换人/发卡接收人
	 */
	@Column(name = "belong_person")
	private String belongPerson;

	/**
	 * 激活时间
	 */
	@Column(name = "activation_time")
	@Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime activationTime;

	/**
	 * 销卡时所剩余额
	 */
	@Column(name = "cancel_balance")
	private BigDecimal cancelBalance;
	
	/**
	 * 销卡时间
	 */
	@Column(name = "cancel_time")
	@Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime cancelTime;

	/**
	 * 销卡人
	 */
	@Column(name = "cancel_person")
	private String cancelPerson;

	/**
	 * 销卡描述
	 */
	@Column(name = "cancel_desc")
	private String cancelDesc;

	/**
	 * 礼品卡类型
	 */
	@Column(name = "gift_card_type")
	private GiftCardType giftCardType;


	/**
	 * 对应校服小助手订单号
	 */
	@Column(name = "order_sn")
	private String orderSn;

	/**
	 * 对应校服小助手订单详细id
	 */
	@Column(name = "order_detail_retail_id")
	private Integer orderDetailRetailId;

	/**
	 * 是否需要预约发货: 默认 0-不需要; 1-需要
	 */
	@Column(name = "appointment_shipment_flag")
	private Integer appointmentShipmentFlag;

}
