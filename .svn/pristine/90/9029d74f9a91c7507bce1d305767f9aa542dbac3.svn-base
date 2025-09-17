package com.wanmi.sbc.setting.payadvertisement.model.root;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.List;

import com.wanmi.sbc.common.enums.DeleteFlag;

import lombok.Data;
import jakarta.persistence.*;
import com.wanmi.sbc.common.base.BaseEntity;

/**
 * <p>支付广告页配置实体类</p>
 * @author 黄昭
 * @date 2022-04-06 10:03:54
 */
@Data
@Entity
@Table(name = "pay_advertisement")
public class PayAdvertisement extends BaseEntity {

	/**
	 * 支付广告id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	/**
	 * 广告名称
	 */
	@Column(name = "advertisement_name")
	private String advertisementName;

	/**
	 * 投放开始时间
	 */
	@Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
	@Column(name = "start_time")
	private LocalDateTime startTime;

	/**
	 * 投放结束时间
	 */
	@Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
	@Column(name = "end_time")
	private LocalDateTime endTime;

	/**
	 * 1:全部店铺  2:部分店铺
	 */
	@Column(name = "store_type")
	private Integer storeType;

	/**
	 * 订单金额
	 */
	@Column(name = "order_price")
	private BigDecimal orderPrice;

	/**
	 * 广告图片
	 */
	@Column(name = "advertisement_img")
	private String advertisementImg;

	/**
	 * 目标客户 1:全平台客户 -1:指定客户 other:部分客户
	 */
	@Column(name = "join_level")
	private String joinLevel;

	/**
	 * 删除标记  0：正常，1：删除
	 */
	@Column(name = "del_flag")
	@Enumerated
	private DeleteFlag delFlag;

	/**
	 * 是否暂停（1：暂停，0：正常）
	 */
	@Column(name = "is_pause")
	private Integer isPause;

	/**
	 * 支付广告页关联店铺
	 */
	@OneToMany
	@JoinColumn(name = "pay_advertisement_id", insertable = false, updatable = false)
	@JsonIgnore
	private List<PayAdvertisementStore> payAdvertisementStore;

}
