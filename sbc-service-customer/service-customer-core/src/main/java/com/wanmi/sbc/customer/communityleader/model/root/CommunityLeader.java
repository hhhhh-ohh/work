package com.wanmi.sbc.customer.communityleader.model.root;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.customer.bean.enums.LeaderCheckStatus;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

/**
 * <p>社区团购团长表实体类</p>
 * @author dyt
 * @date 2023-07-21 11:10:45
 */
@Data
@Entity
@Table(name = "community_leader")
public class CommunityLeader implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 团长id
	 */
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "leader_id")
	private String leaderId;

	/**
	 * 团长账号
	 */
	@Column(name = "leader_account")
	private String leaderAccount;

	/**
	 * 团长名称
	 */
	@Column(name = "leader_name")
	private String leaderName;

	/**
	 * 团长简介
	 */
	@Column(name = "leader_description")
	private String leaderDescription;

	/**
	 * 审核状态, 0:未审核 1:审核通过 2:审核失败 3:禁用中
	 */
	@Enumerated
	@Column(name = "check_status")
	private LeaderCheckStatus checkStatus;

	/**
	 * 驳回原因
	 */
	@Column(name = "check_reason")
	private String checkReason;

	/**
	 * 禁用原因
	 */
	@Column(name = "disable_reason")
	private String disableReason;

	/**
	 * 创建时间
	 */
	@Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	@Column(name = "create_time")
	private LocalDateTime createTime;

	/**
	 * 更新时间
	 */
	@Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	@Column(name = "update_time")
	private LocalDateTime updateTime;

	/**
	 * 审核时间
	 */
	@Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
	@Column(name = "check_time")
	private LocalDateTime checkTime;

	/**
	 * 禁用时间
	 */
	@Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
	@Column(name = "disable_time")
	private LocalDateTime disableTime;

	/**
	 * 是否帮卖 0:否 1:是
	 */
	@Column(name = "assist_flag")
	private Integer assistFlag;

	/**
	 * 会员id
	 */
	@Column(name = "customer_id")
	private String customerId;

	/**
	 * 删除标识 0:未删除 1:已删除
	 */
	@Column(name = "del_flag")
	@Enumerated
	private DeleteFlag delFlag;
}
