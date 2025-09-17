package com.wanmi.sbc.marketing.electroniccoupon.model.root;

import com.wanmi.sbc.common.enums.DeleteFlag;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

/**
 * <p>卡密发放记录表实体类</p>
 * @author 许云鹏
 * @date 2022-01-26 17:37:31
 */
@Data
@Entity
@Table(name = "electronic_send_record")
public class ElectronicSendRecord {
	private static final long serialVersionUID = 1L;

	/**
	 * 记录id
	 */
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "id")
	private String id;

	/**
	 * 订单号
	 */
	@Column(name = "order_no")
	private String orderNo;

	/**
	 * sku编码
	 */
	@Column(name = "sku_no")
	private String skuNo;

	/**
	 * 商品名称
	 */
	@Column(name = "sku_name")
	private String skuName;

	/**
	 * 收货人
	 */
	@Column(name = "account")
	private String account;

	/**
	 * 发放时间
	 */
	@Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
	@Column(name = "send_time")
	private LocalDateTime sendTime;

	/**
	 * 卡券id
	 */
	@Column(name = "coupon_id")
	private Long couponId;

	/**
	 * 卡券名称
	 */
	@Column(name = "coupon_name")
	private String couponName;

	/**
	 * 卡密id
	 */
	@Column(name = "card_id")
	private String cardId;

	/**
	 * 卡券内容
	 */
	@Column(name = "card_content")
	private String cardContent;

	/**
	 * 发放状态  0、发放成功 1、发送中 2、发送失败
	 */
	@Column(name = "send_state")
	private Integer sendState;

	/**
	 * 发放失败原因  0、库存不足1、已过销售期 2、其他原因
	 */
	@Column(name = "fail_reason")
	private Integer failReason;

	/**
	 * 是否删除 0 否  1 是
	 */
	@Column(name = "del_flag")
	@Enumerated
	private DeleteFlag delFlag;

	/**
	 * 店铺id
	 */
	@Column(name = "store_id")
	private Long storeId;

}
