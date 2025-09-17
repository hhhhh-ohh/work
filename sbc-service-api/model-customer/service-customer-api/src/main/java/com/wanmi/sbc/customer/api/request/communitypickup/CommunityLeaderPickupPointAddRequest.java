package com.wanmi.sbc.customer.api.request.communitypickup;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.*;

import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

/**
 * <p>团长自提点表新增参数</p>
 * @author dyt
 * @date 2023-07-21 14:10:45
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunityLeaderPickupPointAddRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 自提点名称
	 */
	@Schema(description = "自提点名称")
	@NotBlank
	@Length(min = 2, max = 28)
	private String pickupPointName;

	/**
	 * 自提点省份
	 */
	@Schema(description = "自提点省份")
	@NotNull
	private Long pickupProvinceId;

	/**
	 * 自提点城市
	 */
	@Schema(description = "自提点城市")
	@NotNull
	private Long pickupCityId;

	/**
	 * 自提点区县
	 */
	@Schema(description = "自提点区县")
	@NotNull
	private Long pickupAreaId;

	/**
	 * 自提点街道
	 */
	@Schema(description = "自提点街道")
	private Long pickupStreetId;

	/**
	 * 详细地址
	 */
	@Schema(description = "详细地址")
	@NotBlank
	@Length(min = 1, max = 200)
	private String address;

	/**
	 * 经度
	 */
	@Schema(description = "经度")
	private BigDecimal lng;

	/**
	 * 纬度
	 */
	@Schema(description = "纬度")
	private BigDecimal lat;

	/**
	 * 联系电话-区号
	 */
	@Schema(description = "联系电话-区号")
	@Length(min = 3, max = 4)
	private String contactCode;

	/**
	 * 联系电话
	 */
	@Schema(description = "联系电话")
	@NotBlank
	@Length(min = 1, max = 15)
	private String contactNumber;

	/**
	 * 自提时间
	 */
	@Schema(description = "自提时间")
	@NotBlank
	@Length(min = 1, max = 200)
	private String pickupTime;

	/**
	 * 自提点照片
	 */
	@Schema(description = "自提点照片")
	private String pickupPhotos;
}