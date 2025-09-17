package com.wanmi.sbc.marketing.communityassist.model.root;


import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

/**
 * <p>社区团购活动帮卖转发记录实体类</p>
 * @author dyt
 * @date 2023-08-01 15:45:58
 */
@Data
@Entity
@Table(name = "community_assist_record")
public class CommunityAssistRecord implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "id")
	private String id;

	/**
	 * 活动id
	 */
	@Column(name = "activity_id")
	private String activityId;

	/**
	 * 店铺id
	 */
	@Column(name = "store_id")
	private Long storeId;

	/**
	 * 团长id
	 */
	@Column(name = "leader_id")
	private String leaderId;

	/**
	 * 创建时间
	 */
	@Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
	@Column(name = "create_time")
	private LocalDateTime createTime;
}
