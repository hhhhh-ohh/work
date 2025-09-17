package com.wanmi.sbc.marketing.drawrecord.model.root;

import com.wanmi.sbc.marketing.bean.enums.DrawPrizeType;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

/**
 * <p>抽奖记录表实体类</p>
 * @author wwc
 * @date 2021-04-12 16:15:21
 */
@Data
@Entity
@Table(name = "draw_record")
public class DrawRecord implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 抽奖记录主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	/**
	 * 抽奖活动id
	 */
	@Column(name = "activity_id")
	private Long activityId;

	/**
	 * 抽奖记录编号
	 */
	@Column(name = "draw_record_code")
	private String drawRecordCode;

	/**
	 * 抽奖时间
	 */
	@Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
	@Column(name = "draw_time")
	private LocalDateTime drawTime;

	/**
	 * 抽奖状态 0 未中奖 1 中奖
	 */
	@Column(name = "draw_status")
	private Integer drawStatus;

	/**
	 * 奖项id
	 */
	@Column(name = "prize_id")
	private Long prizeId;

	/**
	 * 奖品类型（0：积分 1：优惠券 2：实物奖品 3：自定义）
	 */
	@Column(name = "prize_type")
	@Enumerated
	private DrawPrizeType prizeType;


	/**
	 * 奖品名称
	 */
	@Column(name = "prize_name")
	private String prizeName;

	/**
	 * 奖品图片
	 */
	@Column(name = "prize_url")
	private String prizeUrl;

	/**
	 * 兑奖状态 0未兑奖  1已兑奖
	 */
	@Column(name = "redeem_prize_status")
	private Integer redeemPrizeStatus;

	/**
	 * 0未发货  1已发货
	 */
	@Column(name = "deliver_status")
	private Integer deliverStatus;

	/**
	 * 会员Id
	 */
	@Column(name = "customer_id")
	private String customerId;

	/**
	 * 会员登录账号|手机号
	 */
	@Column(name = "customer_account")
	private String customerAccount;

	/**
	 * 会员名称
	 */
	@Column(name = "customer_name")
	private String customerName;

	/**
	 * 详细收货地址(包含省市区）
	 */
	@Column(name = "detail_address")
	private String detailAddress;

	/**
	 * 收货人
	 */
	@Column(name = "consignee_name")
	private String consigneeName;

	/**
	 * 收货人手机号码
	 */
	@Column(name = "consignee_number")
	private String consigneeNumber;

	/**
	 * 兑奖时间
	 */
	@Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
	@Column(name = "redeem_prize_time")
	private LocalDateTime redeemPrizeTime;

	/**
	 * 发货时间
	 */
	@Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
	@Column(name = "delivery_time")
	private LocalDateTime deliveryTime;

	/**
	 * 物流公司名称
	 */
	@Column(name = "logistics_company")
	private String logisticsCompany;

	/**
	 * 物流单号
	 */
	@Column(name = "logistics_no")
	private String logisticsNo;

	/**
	 * 物流公司标准编码
	 */
	@Column(name = "logistics_code")
	private String logisticsCode;

	/**
	 * 创建时间
	 */
	@Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
	@Column(name = "create_time")
	private LocalDateTime createTime;

	/**
	 * 修改时间
	 */
	@Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
	@Column(name = "update_time")
	private LocalDateTime updateTime;

	/**
	 * 创建人
	 */
	@Column(name = "create_person")
	private String createPerson;

	/**
	 * 编辑人
	 */
	@Column(name = "update_person")
	private String updatePerson;

}