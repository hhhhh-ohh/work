package com.wanmi.sbc.marketing.communitydeliveryorder.model.root;


import com.wanmi.sbc.marketing.bean.enums.DeliveryOrderSummaryType;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

/**
 * <p>社区团购发货单实体类</p>
 * @author dyt
 * @date 2023-08-03 16:23:20
 */
@Data
@Entity
@Table(name = "community_delivery_order")
public class CommunityDeliveryOrder implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
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
	 * 团长Id
	 */
	@Column(name = "leader_id")
	private String leaderId;

	/**
	 * 区域名称
	 */
	@Column(name = "area_name")
	private String areaName;

	/**
	 * 商品json内容
	 */
	@Column(name = "goods_context")
	private String goodsContext;

	/**
	 * 发货状态 0:未发货 1:已发货
	 */
	@Column(name = "delivery_status")
	private Integer deliveryStatus;

	/**
	 * 汇总类型 0:按团长 1:按区域
	 */
	@Column(name = "type")
	@Enumerated
	private DeliveryOrderSummaryType type;

	/**
	 * 团长名称
	 */
	@Column(name = "leader_name")
	private String leaderName;

	/**
	 * 自提点名称
	 */
	@Column(name = "pickup_point_name")
	private String pickupPointName;

	/**
	 * 联系电话
	 */
	@Column(name = "contact_number")
	private String contactNumber;

	/**
	 * 全详细地址
	 */
	@Column(name = "full_address")
	private String fullAddress;

	/**
	 * 创建时间
	 */
	@Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
	@Column(name = "create_time")
	private LocalDateTime createTime;
}
