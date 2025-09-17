package com.wanmi.sbc.setting.bean.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.enums.StoreType;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>pickup_settingVO</p>
 * @author 黄昭
 * @date 2021-09-03 11:01:10
 */
@Schema
@Data
public class PickupSettingVO extends BasicResponse {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Schema(description = "主键")
	private Long id;

	/**
	 * 店铺ID
	 */
	@Schema(description = "店铺ID")
	private Long storeId;

	/**
	 * 店铺名称
	 */
	@Schema(description = "店铺名称")
	private String storeName;

	/**
	 * 自提点名称
	 */
	@Schema(description = "自提点名称")
	private String name;

	/**
	 * 自提点区号
	 */
	@Schema(description = "自提点区号")
	private String areaCode;

	/**
	 * 自提点联系电话
	 */
	@Schema(description = "自提点联系电话")
	private String phone;

	/**
	 * 省份
	 */
	@Schema(description = "省份")
	private Long provinceId;

	/**
	 * 省份
	 */
	@Schema(description = "省份名称")
	private String provinceName;

	/**
	 * 市
	 */
	@Schema(description = "市")
	private Long cityId;

	/**
	 * 市
	 */
	@Schema(description = "市名称")
	private String cityName;

	/**
	 * 区
	 */
	@Schema(description = "区")
	private Long areaId;

	/**
	 * 区
	 */
	@Schema(description = "区名称")
	private String areaName;

	/**
	 * 街道
	 */
	@Schema(description = "街道")
	private Long streetId;

	/**
	 * 街道
	 */
	@Schema(description = "街道名称")
	private String streetName;

	/**
	 * 所在地区
	 */
	@Schema(description = "所在地区")
	private String address;

	/**
	 * 详细街道地址
	 */
	@Schema(description = "详细街道地址")
	private String pickupAddress;

	/**
	 * 是否是默认地址
	 */
	@Schema(description = "是否是默认地址")
	private Integer isDefaultAddress;

	/**
	 * 是否是默认地址名称
	 */
	@Schema(description = "是否是默认地址名称")
	private String isDefaultAddressName;

	/**
	 * 自提时间说明
	 */
	@Schema(description = "自提时间说明")
	private String remark;

	/**
	 * 自提点照片
	 */
	@Schema(description = "自提点照片")
	private String imageUrl;

	/**
	 * 经度
	 */
	@Schema(description = "经度")
	private BigDecimal longitude;

	/**
	 * 纬度
	 */
	@Schema(description = "纬度")
	private BigDecimal latitude;

	/**
	 * 审核状态,0:未审核1 审核通过2审核失败
	 */
	@Schema(description = "审核状态,0:未审核1 审核通过2审核失败")
	private Integer auditStatus;

	/**
	 * 审核状态名称
	 */
	@Schema(description = "审核状态名称")
	private String auditStatusName;

	/**
	 * 驳回理由
	 */
	@Schema(description = "驳回理由")
	private String auditReason;

	/**
	 * 是否启用 1:启用 0:停用
	 */
	@Schema(description = "是否启用 1:启用 0:停用")
	private Integer enableStatus;

	/**
	 * 是否启用名称
	 */
	@Schema(description = "是否启用名称")
	private String enableStatusName;

	/**
	 * 删除时间
	 */
	@Schema(description = "删除时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime deleteTime;

	/**
	 * 删除人
	 */
	@Schema(description = "删除人")
	private String deletePerson;

	/**
	 * 与收货地址的距离
	 */
	@Schema(description = "与收货地址的距离")
	private Long distance;

	/**
	 * 店铺类型
	 */
	@Schema(description = "店铺类型")
	private StoreType storeType;

	@Schema(description = "社区团购自提点id")
	private String pickupPointId;

}