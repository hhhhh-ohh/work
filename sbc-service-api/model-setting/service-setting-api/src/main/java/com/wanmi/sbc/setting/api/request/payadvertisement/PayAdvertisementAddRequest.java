package com.wanmi.sbc.setting.api.request.payadvertisement;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.*;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.*;
import jakarta.validation.constraints.NotBlank;

import lombok.*;

import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.validator.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * <p>支付广告页配置新增参数</p>
 * @author 黄昭
 * @date 2022-04-06 10:03:54
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayAdvertisementAddRequest extends BaseRequest {

	/**
	 * 广告名称
	 */
	@Schema(description = "广告名称")
	@NotBlank
	private String advertisementName;

	/**
	 * 投放开始时间
	 */
	@Schema(description = "投放开始时间")
	@NotNull
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime startTime;

	/**
	 * 投放结束时间
	 */
	@Schema(description = "投放结束时间")
	@NotNull
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime endTime;

	/**
	 * 1:全部店铺  2:部分店铺
	 */
	@Schema(description = "1:全部店铺  2:部分店铺")
	@NotNull
	private Integer storeType;

	/**
	 * 店铺ids
	 */
	@Schema(description = "店铺ids")
	private List<Long> storeIds;

	/**
	 * 订单金额
	 */
	@Schema(description = "订单金额")
	@Max(99999)
	@Min(0)
	private BigDecimal orderPrice;

	/**
	 * 广告图片
	 */
	@Schema(description = "广告图片")
	@NotBlank
	private String advertisementImg;

	/**
	 * 目标客户 1:全平台客户 other:指定等级
	 */
	@Schema(description = "目标客户 1:全平台客户 other:指定等级")
	@NotBlank
	private String joinLevel;

	/**
	 * 删除标记  0：正常，1：删除
	 */
	@Schema(description = "删除标记  0：正常，1：删除", hidden = true)
	private DeleteFlag delFlag;

	/**
	 * 创建人
	 */
	@Schema(description = "创建人", hidden = true)
	private String createPerson;

	@Override
	public void checkParam() {
		if (Objects.equals(Constants.TWO,storeType) && CollectionUtils.isEmpty(storeIds)){
			throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
		}
		if (startTime.withSecond(Constants.NUM_59).isBefore(LocalDateTime.now())){
			throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
		}
		if (endTime.isBefore(startTime)){
			throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
		}
	}
}