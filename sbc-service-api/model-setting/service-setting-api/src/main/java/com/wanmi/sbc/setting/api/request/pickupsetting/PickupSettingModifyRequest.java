package com.wanmi.sbc.setting.api.request.pickupsetting;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.common.util.ValidateUtil;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>pickup_setting修改参数</p>
 * @author 黄昭
 * @date 2021-09-03 11:01:10
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class PickupSettingModifyRequest extends BaseRequest {
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
	 * 自提点名称
	 */
	@Schema(description = "自提点名称")
	@NotBlank
	@Length(min = 1, max = 14)
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
	@NotBlank
	@Length(max = 15)
	private String phone;

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
	 * 街道
	 */
	@Schema(description = "街道")
	private Long streetId;

	/**
	 * 详细街道地址
	 */
	@Schema(description = "详细街道地址")
	@NotBlank
	@Length(max = 100)
	private String pickupAddress;

	/**
	 * 自提时间说明
	 */
	@Schema(description = "自提时间说明")
	@NotBlank
	@Length(max = 100)
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
	 * 修改人
	 */
	@Schema(description = "修改时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTime;

	/**
	 * 修改人
	 */
	@Schema(description = "修改人", hidden = true)
	private String updatePerson;

	/**
	 * 自提点员工id
	 */
	@Schema(description = "自提点员工id")
	private List<String> employeeIds;

	public void checkParam(){
		log.info("统一参数校验入口");
		if (StringUtils.isNotBlank(areaCode)) {
			if ((areaCode.length() > 4 || areaCode.length() < 3)) {
				throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
			}
			if (!ValidateUtil.isStringNum(areaCode)) {
				throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
			}
		}
		if (ValidateUtil.isSpeChs(name) || !ValidateUtil.isNum(phone)){
			throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
		}
	}

}
