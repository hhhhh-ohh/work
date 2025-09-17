package com.wanmi.sbc.customer.communitypickup.model.root;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.customer.bean.enums.LeaderCheckStatus;
import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

/**
 * <p>团长自提点表实体类</p>
 * @author dyt
 * @date 2023-07-21 14:10:45
 */
@Data
@Entity
@Table(name = "community_leader_pickup_point")
public class CommunityLeaderPickupPoint implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 自提点id
	 */
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "pickup_point_id")
	private String pickupPointId;

	/**
	 * 团长id
	 */
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
	 * 审核状态, 0:未审核 1审核通过 2审核失败 3禁用中
	 */
	@Enumerated
	@Column(name = "check_status")
	private LeaderCheckStatus checkStatus;

	/**
	 * 自提点名称
	 */
	@Column(name = "pickup_point_name")
	private String pickupPointName;

	/**
	 * 自提点省份
	 */
	@Column(name = "pickup_province_id")
	private Long pickupProvinceId;

	/**
	 * 自提点城市
	 */
	@Column(name = "pickup_city_id")
	private Long pickupCityId;

	/**
	 * 自提点区县
	 */
	@Column(name = "pickup_area_id")
	private Long pickupAreaId;

	/**
	 * 自提点街道
	 */
	@Column(name = "pickup_street_id")
	private Long pickupStreetId;

	/**
	 * 详细地址
	 */
	@Column(name = "address")
	private String address;

	/**
	 * 经度
	 */
	@Column(name = "lng")
	private BigDecimal lng;

	/**
	 * 纬度
	 */
	@Column(name = "lat")
	private BigDecimal lat;

	/**
	 * 联系电话
	 */
	@Column(name = "contact_number")
	private String contactNumber;

	/**
	 * 自提时间
	 */
	@Column(name = "pickup_time")
	private String pickupTime;

	/**
	 * 全详细地址
	 */
	@Column(name = "full_address")
	private String fullAddress;

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
	 * 删除标识 0:未删除 1:已删除
	 */
	@Column(name = "del_flag")
	@Enumerated
	private DeleteFlag delFlag;

	/**
	 * 会员id
	 */
	@Column(name = "customer_id")
	private String customerId;
	
	/**
	 * 自提点照片
	 */
	@Column(name = "photos")
	private String pickupPhotos;
}
