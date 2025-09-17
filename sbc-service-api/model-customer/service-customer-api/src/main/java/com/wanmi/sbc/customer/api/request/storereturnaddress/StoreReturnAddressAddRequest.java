package com.wanmi.sbc.customer.api.request.storereturnaddress;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.ValidateUtil;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.*;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;

/**
 * <p>店铺退货地址表新增参数</p>
 * @author dyt
 * @date 2020-11-02 11:38:39
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreReturnAddressAddRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 公司信息ID
	 */
	@Schema(description = "公司信息ID", hidden = true)
	private Long companyInfoId;

	/**
	 * 店铺信息ID
	 */
	@Schema(description = "店铺信息ID", hidden = true)
	private Long storeId;

	/**
	 * 收货人
	 */
	@Schema(description = "收货人")
	@NotBlank
	@Length(min = 2, max=15)
	private String consigneeName;

	/**
	 * 收货人手机号码
	 */
	@Schema(description = "收货人手机号码")
	@NotBlank
	@Length(min=11, max=11)
	private String consigneeNumber;

	/**
	 * 省份
	 */
	@Schema(description = "省份")
	@NotNull
	private Long provinceId;

	/**
	 * 市
	 */
	@Schema(description = "市")
	@NotNull
	private Long cityId;

	/**
	 * 区
	 */
	@Schema(description = "区")
	@NotNull
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
	@NotBlank
	@Length(max=60)
	private String returnAddress;

	/**
	 * 是否是默认地址
	 */
	@Schema(description = "是否是默认地址")
	private Boolean isDefaultAddress;

	/**
	 * 创建人
	 */
	@Schema(description = "创建人", hidden = true)
	private String createPerson;

	@Override
	public void checkParam() {
		if(StringUtils.isNotEmpty(consigneeNumber)){
			if(!ValidateUtil.isPhone(consigneeNumber)){
				throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
			}
		}
	}
}