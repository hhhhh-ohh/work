package com.wanmi.sbc.marketing.electroniccoupon.model.root;

import com.wanmi.sbc.common.enums.DeleteFlag;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

/**
 * <p>电子卡密表实体类</p>
 * @author 许云鹏
 * @date 2022-01-26 17:24:59
 */
@Data
@Entity
@Table(name = "electronic_card")
public class ElectronicCard {
	private static final long serialVersionUID = 1L;

	/**
	 * 卡密Id
	 */
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "id")
	private String id;

	/**
	 * 卡券id
	 */
	@Column(name = "coupon_id")
	private Long couponId;

	/**
	 * 卡号
	 */
	@Column(name = "card_number")
	private String cardNumber;

	/**
	 * 卡密
	 */
	@Column(name = "card_password")
	private String cardPassword;

	/**
	 * 优惠码
	 */
	@Column(name = "card_promo_code")
	private String cardPromoCode;

	/**
	 * 卡密状态  0、未发送 1、已发送 2、已失效
	 */
	@Column(name = "card_state")
	private Integer cardState;

	/**
	 * 卡密销售开始时间
	 */
	@Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
	@Column(name = "sale_start_time")
	private LocalDateTime saleStartTime;

	/**
	 * 卡密销售结束时间
	 */
	@Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
	@Column(name = "sale_end_time")
	private LocalDateTime saleEndTime;

	/**
	 * 批次id
	 */
	@Column(name = "record_id")
	private String recordId;

	/**
	 * 是否删除 0 否  1 是
	 */
	@Column(name = "del_flag")
	@Enumerated
	private DeleteFlag delFlag;

	/**
	 * 绑定的订单号
	 */
	@Column(name = "order_no")
	private String orderNo;
}
