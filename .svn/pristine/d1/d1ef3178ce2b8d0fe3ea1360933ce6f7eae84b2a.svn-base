package com.wanmi.sbc.customer.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import java.time.LocalDateTime;
import lombok.Data;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>店铺退货地址表VO</p>
 * @author dyt
 * @date 2020-11-02 11:38:39
 */
@Schema
@Data
public class StoreReturnAddressVO extends BasicResponse {
	private static final long serialVersionUID = 1L;

	/**
	 * 收货地址ID
	 */
	@Schema(description = "收货地址ID")
	private String addressId;

	/**
	 * 公司信息ID
	 */
	@Schema(description = "公司信息ID")
	private Long companyInfoId;

	/**
	 * 店铺信息ID
	 */
	@Schema(description = "店铺信息ID")
	private Long storeId;

	/**
	 * 收货人
	 */
	@Schema(description = "收货人")
	private String consigneeName;

	/**
	 * 收货人手机号码
	 */
	@Schema(description = "收货人手机号码")
	private String consigneeNumber;

	/**
	 * 省份
	 */
	@Schema(description = "省份")
	private Long provinceId;

	/**
	 * 市
	 */
	@Schema(description = "市")
	private Long cityId;

	/**
	 * 区
	 */
	@Schema(description = "区")
	private Long areaId;

	/**
	 * 街道id
	 */
	@Schema(description = "街道id")
	private Long streetId;

	/**
	 * 详细街道地址
	 */
	@Schema(description = "详细街道地址")
	private String returnAddress;

	/**
	 * 是否是默认地址
	 */
	@Schema(description = "是否是默认地址")
	private Boolean isDefaultAddress;

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
	 * 省
	 */
	@Schema(description = "省")
	private String provinceName;

	/**
	 * 市
	 */
	@Schema(description = "市")
	private String cityName;

	/**
	 * 区
	 */
	@Schema(description = "区")
	private String areaName;

	/**
	 * 街道
	 */
	@Schema(description = "街道")
	private String streetName;

}