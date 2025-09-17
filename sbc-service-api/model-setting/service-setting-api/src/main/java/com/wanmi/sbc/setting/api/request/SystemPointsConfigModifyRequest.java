package com.wanmi.sbc.setting.api.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.EnableStatus;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.setting.bean.enums.PointsUsageFlag;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Max;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * <p>积分设置修改参数</p>
 * @author yxz
 * @date 2019-03-28 16:24:21
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
public class SystemPointsConfigModifyRequest extends SettingBaseRequest {

	private static final long serialVersionUID = 1L;
	/**
	 * 主键
	 */
	@Schema(description = "主键")
	@Length(max=32)
	private String pointsConfigId;

	/**
	 * 满x积分可用
	 */
	@Schema(description = "满x积分可用")
	@Max(9999999999L)
	private Long overPointsAvailable;

	/**
	 * 积分抵扣限额
	 */
	@Schema(description = "积分抵扣限额")
	private BigDecimal maxDeductionRate;

	/**
	 * 积分过期月份
	 */
	@Schema(description = "积分过期月份")
	private Integer pointsExpireMonth;

	/**
	 * 积分过期日期
	 */
	@Schema(description = "积分过期日期")
	private Integer pointsExpireDay;

	/**
	 * 积分说明
	 */
	@Schema(description = "积分说明")
	private String remark;

	/**
	 * 使用方式 0:订单抵扣,1:商品抵扣
	 */
	@Schema(description = "使用方式 0:订单抵扣,1:商品抵扣")
	private PointsUsageFlag pointsUsageFlag;

	/**
	 * 是否启用标志 0：停用，1：启用
	 */
	@Schema(description = "是否启用标志 0：停用，1：启用")
	private EnableStatus status;

	/**
	 * 是否删除标志 0：否，1：是
	 */
	@Schema(description = "是否删除标志 0：否，1：是")
	private DeleteFlag delFlag;

	/**
	 * 创建时间
	 */
	@Schema(description = "创建时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTime;

	/**
	 * 修改时间
	 */
	@Schema(description = "修改时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTime;

	@Override
	public void checkParam() {
		if (Objects.isNull(pointsExpireMonth)){
			pointsExpireMonth = Constants.ZERO;
		}
		if (Objects.isNull(pointsExpireDay)){
			pointsExpireDay = Constants.ZERO;
		}
	}
}