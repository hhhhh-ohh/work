package com.wanmi.sbc.account.ledgerfunds.model.root;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import lombok.Data;
import jakarta.persistence.*;
import com.wanmi.sbc.common.base.BaseEntity;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

/**
 * <p>会员分账资金实体类</p>
 * @author xuyunpeng
 * @date 2022-07-25 16:54:38
 */
@Data
@Entity
@Table(name = "ledger_funds")
public class LedgerFunds{

	/**
	 * id
	 */
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "id")
	private String id;

	/**
	 * 会员id
	 */
	@Column(name = "customer_id")
	private String customerId;

	/**
	 * 待提现金额
	 */
	@Column(name = "withdrawn_amount")
	private BigDecimal withdrawnAmount;

	/**
	 * 已提现金额
	 */
	@Column(name = "already_draw_amount")
	private BigDecimal alreadyDrawAmount;

	/**
	 * 创建时间
	 */
	@CreatedDate
	@Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
	@Column(name = "create_time")
	private LocalDateTime createTime;

	/**
	 * 更新时间
	 */
	@LastModifiedDate
	@Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	@Column(name = "update_time")
	private LocalDateTime updateTime;

}
