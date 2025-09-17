package com.wanmi.sbc.setting.pickupsetting.model.root;

import com.wanmi.sbc.common.base.BaseEntity;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.PluginType;
import com.wanmi.sbc.common.enums.StoreType;
import lombok.Data;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>pickup_setting实体类</p>
 * @author 黄昭
 * @date 2021-09-03 11:01:10
 */
@Data
@Entity
@Table(name = "pickup_setting")
public class PickupSetting extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	/**
	 * 店铺ID
	 */
	@Column(name = "store_id")
	private Long storeId;

	/**
	 * 自提点名称
	 */
	@Column(name = "name")
	private String name;

	/**
	 * 自提点区号
	 */
	@Column(name = "area_code")
	private String areaCode;

	/**
	 * 自提点联系电话
	 */
	@Column(name = "phone")
	private String phone;

	/**
	 * 省份
	 */
	@Column(name = "province_id")
	private Long provinceId;

	/**
	 * 市
	 */
	@Column(name = "city_id")
	private Long cityId;

	/**
	 * 区
	 */
	@Column(name = "area_id")
	private Long areaId;

	/**
	 * 街道
	 */
	@Column(name = "street_id")
	private Long streetId;

	/**
	 * 详细街道地址
	 */
	@Column(name = "pickup_address")
	private String pickupAddress;

	/**
	 * 是否是默认地址
	 */
	@Column(name = "is_default_address")
	private Integer isDefaultAddress;

	/**
	 * 自提时间说明
	 */
	@Column(name = "remark")
	private String remark;

	/**
	 * 自提点照片
	 */
	@Column(name = "image_url")
	private String imageUrl;

	/**
	 * 经度
	 */
	@Column(name = "longitude")
	private BigDecimal longitude;

	/**
	 * 纬度
	 */
	@Column(name = "latitude")
	private BigDecimal latitude;

	/**
	 * 审核状态,0:未审核1 审核通过2审核失败
	 */
	@Column(name = "audit_status")
	private Integer auditStatus;

	/**
	 * 驳回理由
	 */
	@Column(name = "audit_reason")
	private String auditReason;

	/**
	 * 是否启用 1:启用 0:停用
	 */
	@Column(name = "enable_status")
	private Integer enableStatus;

	/**
	 * 是否删除标志 0：否，1：是
	 */
	@Column(name = "del_flag")
	@Enumerated
	private DeleteFlag delFlag;

	/**
	 * 删除时间
	 */
	@Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
	@Column(name = "delete_time")
	private LocalDateTime deleteTime;

	/**
	 * 删除人
	 */
	@Column(name = "delete_person")
	private String deletePerson;

	/**
	 * 店铺类型
	 */
	@Enumerated
	@Column(name = "store_type")
	private StoreType storeType;
}
