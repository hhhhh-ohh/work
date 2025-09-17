package com.wanmi.sbc.marketing.communityrel.model.root;

import com.wanmi.sbc.marketing.bean.enums.CommunitySalesType;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

/**
 * <p>社区团购活动区域关联表实体类</p>
 * @author dyt
 * @date 2023-07-24 14:38:10
 */
@Data
@Entity
@Table(name = "community_area_rel")
public class CommunityAreaRel  implements Serializable {
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
	 * 区域Id
	 */
	@Column(name = "area_id")
	private String areaId;

	/**
	 * 区域名称
	 */
	@Column(name = "area_name")
	private String areaName;

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
	@Column(name = "sales_type")
	private CommunitySalesType salesType;

}
