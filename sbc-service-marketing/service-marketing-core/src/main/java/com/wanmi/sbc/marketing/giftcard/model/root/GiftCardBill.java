package com.wanmi.sbc.marketing.giftcard.model.root;

import java.math.BigDecimal;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.marketing.bean.enums.GiftCardBusinessType;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import java.time.LocalDateTime;

import lombok.Data;
import jakarta.persistence.*;
import com.wanmi.sbc.common.base.BaseEntity;

/**
 * <p>礼品卡交易流水实体类</p>
 * @author 吴瑞
 * @date 2022-12-09 14:03:09
 */
@Data
@Entity
@Table(name = "gift_card_bill")
public class GiftCardBill {
	private static final long serialVersionUID = 1L;

	/**
	 * giftCardBillId
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "gift_card_bill_id")
	private Long giftCardBillId;

	/**
	 * 礼品卡Id
	 */
	@Column(name = "gift_card_id")
	private Long giftCardId;

	/**
	 * 用户Id
	 */
	@Column(name = "customer_id")
	private String customerId;

	/**
	 * 用户礼品卡Id
	 */
	@Column(name = "user_gift_card_id")
	private Long userGiftCardId;

	/**
	 * 礼品卡卡号
	 */
	@Column(name = "gift_card_no")
	private String giftCardNo;

	/**
	 * 交易类型 0：使用 1：退换 2: 发卡 3：兑换 4：销卡
	 */
	@Column(name = "business_type")
	@Enumerated
	private GiftCardBusinessType businessType;

	/**
	 * 业务Id
	 */
	@Column(name = "business_id")
	private String businessId;

	/**
	 * 交易前余额
	 */
	@Column(name = "before_balance")
	private BigDecimal beforeBalance;

	/**
	 * 交易金额
	 */
	@Column(name = "trade_balance")
	private BigDecimal tradeBalance;

	/**
	 * 交易后余额
	 */
	@Column(name = "after_balance")
	private BigDecimal afterBalance;

	/**
	 * 交易时间
	 */
	@Column(name = "trade_time")
	@Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime tradeTime;

	/**
	 * 交易人类型 0：C端用户 1：B端用户
	 */
	@Column(name = "trade_person_type")
	@Enumerated
	private DefaultFlag tradePersonType;

	/**
	 * tradePerson
	 */
	@Column(name = "trade_person")
	private String tradePerson;

}
