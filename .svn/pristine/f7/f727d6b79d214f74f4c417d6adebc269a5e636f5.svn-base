package com.wanmi.sbc.order.payingmemberrecordtemp.model.root;

import java.math.BigDecimal;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import java.time.LocalDateTime;
import java.time.LocalDate;
import com.wanmi.sbc.common.enums.DeleteFlag;

import lombok.Data;
import jakarta.persistence.*;
import com.wanmi.sbc.common.base.BaseEntity;
import org.hibernate.annotations.GenericGenerator;

/**
 * <p>付费记录临时表实体类</p>
 * @author zhanghao
 * @date 2022-05-13 15:28:45
 */
@Data
@Entity
@Table(name = "paying_member_record_temp")
public class PayingMemberRecordTemp extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * 记录id
	 */
	@Id
	@Column(name = "record_id")
	private String recordId;

	/**
	 * 付费会员等级id
	 */
	@Column(name = "level_id")
	private Integer levelId;

	/**
	 * 付费会员等级名称
	 */
	@Column(name = "level_name")
	private String levelName;

	/**
	 * 付费会员等级昵称
	 */
	@Column(name = "level_nick_name")
	private String levelNickName;

	/**
	 * 会员id
	 */
	@Column(name = "customer_id")
	private String customerId;

	/**
	 * 会员名称
	 */
	@Column(name = "customer_name")
	private String customerName;

	/**
	 * 支付数量
	 */
	@Column(name = "pay_num")
	private Integer payNum;

	/**
	 * 支付金额
	 */
	@Column(name = "pay_amount")
	private BigDecimal payAmount;

	/**
	 * 支付时间
	 */
	@Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
	@Column(name = "pay_time")
	private LocalDateTime payTime;

	/**
	 * 会员到期时间
	 */
	@Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
	@Column(name = "expiration_date")
	private LocalDate expirationDate;

	/**
	 * 支付状态：0.未支付，1.已支付
	 */
	@Column(name = "pay_state")
	private Integer payState;

	/**
	 * 权益id集合
	 */
	@Column(name = "rights_ids")
	private String rightsIds;

	/**
	 * 删除标识：0：未删除；1：已删除
	 */
	@Column(name = "del_flag")
	@Enumerated
	private DeleteFlag delFlag;

}
