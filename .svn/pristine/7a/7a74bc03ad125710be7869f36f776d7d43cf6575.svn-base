package com.wanmi.sbc.marketing.communityactivity.model.root;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.marketing.bean.enums.CommunityCommissionFlag;
import com.wanmi.sbc.marketing.bean.enums.CommunityLeaderRangeType;
import com.wanmi.sbc.marketing.bean.enums.CommunitySalesRangeType;
import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

/**
 * <p>社区团购活动表实体类</p>
 * @author dyt
 * @date 2023-07-24 14:26:35
 */
@Data
@Entity
@Table(name = "community_activity")
public class CommunityActivity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "activity_id")
	private String activityId;

	/**
	 * 店铺id
	 */
	@Column(name = "store_id")
	private Long storeId;

	/**
	 * 活动名称
	 */
	@Column(name = "activity_name")
	private String activityName;

	/**
	 * 活动描述
	 */
	@Column(name = "description")
	private String description;

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
	 * 延时结束时间
	 */
	@Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
	@Column(name = "real_end_time")
	private LocalDateTime realEndTime;

	/**
	 * 物流方式 0:自提 1:快递 以逗号拼凑
	 */
	@Column(name = "logistics_type")
	private String logisticsType;

	/**
	 * 销售渠道 0:自主销售 1:团长帮卖 以逗号拼凑
	 */
	@Column(name = "sales_type")
	private String salesType;

	/**
	 * 自主销售范围 0：全部 1：地区 2：自定义
	 */
	@Enumerated
	@Column(name = "sales_range")
	private CommunitySalesRangeType salesRange;

	/**
	 * 帮卖团长范围 0：全部 1：地区 2：自定义
	 */
	@Enumerated
	@Column(name = "leader_range")
	private CommunityLeaderRangeType leaderRange;

	/**
	 * 佣金设置 0：商品 1：按团长/自提点
	 */
	@Column(name = "commission_flag")
	private CommunityCommissionFlag commissionFlag;

	/**
	 * 批量-自提服务佣金
	 */
	@Column(name = "pickup_commission")
	private BigDecimal pickupCommission;

	/**
	 * 批量-帮卖团长佣金
	 */
	@Column(name = "assist_commission")
	private BigDecimal assistCommission;

	/**
	 * 团详情
	 */
	@Column(name = "details")
	private String details;

	/**
	 * 是否生成 0:未生成 1:已生成
	 */
	@Column(name = "generate_flag")
	private Integer generateFlag;

	/**
	 * 创建时间
	 */
	@Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
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
	 * 团图片
	 */
	@Column(name = "images")
	private String images;

	/**
	 * 团视频
	 */
	@Column(name = "video_url")
	private String videoUrl;

	/**
	 * 生成时间
	 */
	@Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
	@Column(name = "generate_time")
	private LocalDateTime generateTime;
}
