package com.wanmi.sbc.customer.payingmembercustomerrel.model.root;

import com.wanmi.sbc.common.base.BaseEntity;
import com.wanmi.sbc.common.enums.DeleteFlag;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Data;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

/**
 * <p>客户与付费会员等级关联表实体类</p>
 * @author zhanghao
 * @date 2022-05-13 13:40:48
 */
@Data
@Entity
@Table(name = "paying_member_customer_rel")
public class PayingMemberCustomerRel extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	/**
	 * 客户与付费会员等级关联id
	 */
	@Column(name = "level_id")
	private Integer levelId;

	/**
	 * 会员id
	 */
	@Column(name = "customer_id")
	private String customerId;

	/**
	 * 开通时间
	 */
	@Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
	@Column(name = "open_time")
	private LocalDateTime openTime;

	/**
	 * 会员到期时间
	 */
	@Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
	@Column(name = "expiration_date")
	private LocalDate expirationDate;

	/**
	 * 总共优惠金额
	 */
	@Column(name = "discount_amount")
	private BigDecimal discountAmount;

	/**
	 * 删除标识：0：未删除；1：已删除
	 */
	@Column(name = "del_flag")
	@Enumerated
	private DeleteFlag delFlag;

}
