package com.wanmi.sbc.marketing.drawactivity.model.root;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.marketing.bean.enums.DrawFromType;
import com.wanmi.sbc.marketing.bean.enums.DrawTimesType;
import com.wanmi.sbc.marketing.bean.enums.DrawType;
import com.wanmi.sbc.marketing.bean.enums.DrawWinTimesType;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

/**
 * <p>抽奖活动表实体类</p>
 * @author wwc
 * @date 2021-04-12 10:31:05
 */
@Data
@Entity
@DynamicUpdate
@Table(name = "draw_activity")
public class DrawActivity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	/**
	 * 活动名称
	 */
	@Column(name = "activity_name")
	private String activityName;

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
	 * 抽奖形式（0：九宫格，1：大转盘）
	 */
	@Column(name = "form_type")
	@Enumerated
	private DrawFromType formType;

	/**
	 * 抽奖类型（0：无限制，1：积分）
	 */
	@Column(name = "draw_type")
	@Enumerated
	private DrawType drawType;

	/**
	 * 消耗积分
	 */
	@Column(name = "consume_points")
	private Long consumePoints;

	/**
	 * 抽奖次数限制类型（0：每日，1：每人）
	 */
	@Column(name = "draw_times_type")
	@Enumerated
	private DrawTimesType drawTimesType;

	/**
	 * 抽奖次数，默认为0
	 */
	@Column(name = "draw_times")
	private Integer drawTimes;

	/**
	 * 中奖次数限制类型 （0：无限制，1：每人每天）
	 */
	@Column(name = "win_times_type")
	@Enumerated
	private DrawWinTimesType winTimesType;

	/**
	 * 每人每天最多中奖次数，默认为0
	 */
	@Column(name = "win_times")
	private Integer winTimes;

	/**
	 * 客户等级
	 */
	@Column(name = "join_level")
	private String joinLevel;

	/**
	 * 未中奖提示
	 */
	@Column(name = "not_award_tip")
	private String notAwardTip;

	/**
	 * 抽奖次数上限提示
	 */
	@Column(name = "max_award_tip")
	private String maxAwardTip;

	/**
	 * 活动规则说明
	 */
	@Column(name = "activity_content")
	private String activityContent;

	/**
	 * 实际抽奖人/次
	 */
	@Column(name = "draw_count")
	private Long drawCount;

	/**
	 * 实际中奖人/次
	 */
	@Column(name = "award_count")
	private Long awardCount;

	/**
	 * 是否暂停 0进行 1暂停
	 */
	@Column(name = "pause_flag")
	private Integer pauseFlag;

	/**
	 * 创建时间
	 */
	@Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
	@Column(name = "create_time")
	private LocalDateTime createTime;

	/**
	 * 创建人
	 */
	@Column(name = "create_person")
	private String createPerson;

	/**
	 * 修改时间
	 */
	@Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
	@Column(name = "update_time")
	private LocalDateTime updateTime;

	/**
	 * 修改人
	 */
	@Column(name = "update_person")
	private String updatePerson;

	/**
	 * 是否删除标志 0：否，1：是
	 */
	@Column(name = "del_flag")
	@Enumerated
	private DeleteFlag delFlag;
}