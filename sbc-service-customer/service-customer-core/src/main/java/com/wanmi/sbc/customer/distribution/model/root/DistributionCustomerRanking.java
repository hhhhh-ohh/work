package com.wanmi.sbc.customer.distribution.model.root;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateSerializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

/**
 * <p>用户分销排行榜实体类</p>
 * @author lq
 * @date 2019-04-19 10:05:05
 */
@Data
@Entity
@Table(name = "distribution_customer_ranking")
public class DistributionCustomerRanking implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *ID
	 */
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "id")
	private String id;

	/**
	 * 会员ID
	 */
	@Column(name = "customer_id")
	private String customerId;

	/**
	 * 邀新人数
	 */
	@Column(name = "invite_count")
	private Integer inviteCount;

	/**
	 * 有效邀新人数
	 */
	@Column(name = "invite_available_count")
	private Integer inviteAvailableCount;

	/**
	 * 销售额(元) 
	 */
	@Column(name = "sale_amount")
	private BigDecimal saleAmount;

	/**
	 * 预估收益
	 */
	@Column(name = "commission")
	private BigDecimal commission;


	@Column(name = "target_date")
	@Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
	@JsonSerialize(using = CustomLocalDateSerializer.class)
	@JsonDeserialize(using = CustomLocalDateDeserializer.class)
	private LocalDate targetDate;

	@Column(name = "create_time")
	@Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTime;

}