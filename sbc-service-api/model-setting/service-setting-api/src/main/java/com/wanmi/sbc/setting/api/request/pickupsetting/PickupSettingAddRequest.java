package com.wanmi.sbc.setting.api.request.pickupsetting;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.StoreType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
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
 * <p>pickup_setting新增参数</p>
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
public class PickupSettingAddRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

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
	 * 是否是默认地址
	 */
	@Schema(description = "是否是默认地址")
	private Integer isDefaultAddress;

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
	 * 审核状态,0:未审核1 审核通过2审核失败
	 */
	@Schema(description = "审核状态,0:未审核1 审核通过2审核失败")
	private Integer auditStatus;

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
	 * 创建时间
	 */
	@Schema(description = "创建时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTime;

	/**
	 * 创建人
	 */
	@Schema(description = "创建人", hidden = true)
	private String createPerson;

	/**
	 * 修改时间
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
	 * 是否删除标志 0：否，1：是
	 */
	@Schema(description = "是否删除标志 0：否，1：是", hidden = true)
	private DeleteFlag delFlag;

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
	 * 自提点员工id
	 */
	@Schema(description = "自提点员工id")
	private List<String> employeeIds;

	/**
	 * 自提类型
	 */
	@Schema(description = "自提类型")
	private StoreType storeType;

	public void checkParam(){
		log.info("统一参数校验入口");
		if (StringUtils.isNotBlank(areaCode)) {
			if ((areaCode.length() > 4 || areaCode.length() < 3)) {
				throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
			}
			if (!ValidateUtil.isStringNum(areaCode)) {
				throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
			}
		} else {
			if(phone.length() != Constants.NUM_11){
				throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
			}
		}

		if (StringUtils.isNotBlank(name)) {
			if (name.length() < 2) {
				throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
			}
			if (ValidateUtil.isSpeChs(name)) {
				throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
			}
		} else {
			throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
		}
		if ( !ValidateUtil.isNum(phone)){
			throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
		}
	}
}