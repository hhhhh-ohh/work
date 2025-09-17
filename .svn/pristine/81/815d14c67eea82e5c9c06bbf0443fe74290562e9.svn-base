package com.wanmi.sbc.marketing.communityrel.model.root;

import com.wanmi.sbc.marketing.bean.enums.CommunitySalesType;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

/**
 * <p>社区团购活动团长关联表实体类</p>
 * @author dyt
 * @date 2023-07-24 14:32:15
 */
@Data
@Entity
@Table(name = "community_leader_rel")
public class CommunityLeaderRel  implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	/**
	 * 活动id
	 */
	@Column(name = "activity_id")
	private String activityId;

	/**
	 * 团长Id
	 */
	@Column(name = "leader_id")
	private String leaderId;

	/**
	 * 团长自提点Id
	 */
	@Column(name = "pickup_point_id")
	private String pickupPointId;

	/**
	 * 开始时间
	 */
	@Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
	@Column(name = "start_time")
	private LocalDateTime startTime;

	/**
	 * 结束时间
	 */
	@Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
	@Column(name = "end_time")
	private LocalDateTime endTime;

	/**
	 * 销售渠道 0:自主销售 1:团长帮卖
	 */
	@Enumerated
	@Column(name = "sales_type")
	private CommunitySalesType salesType;

}
