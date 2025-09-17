package com.wanmi.sbc.crm.bean.dto;

import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.crm.bean.enums.StatisticalRangeType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import lombok.Data;

import org.hibernate.validator.constraints.Range;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>VO</p>
 * @author zhongjichuan
 * @date 2020-11-27 11:27:06
 */
@Schema
@Data
public class GoodsCorrelationModelSettingDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Schema(description = "主键")
	@NotNull
	private Integer id;

	/**
	 * 统计范围0：近一个月，1：近3个月，2：近6个月，3：近1年
	 */
	@Schema(description = "统计范围0：近一个月，1：近3个月，2：近6个月，3：近1年")
	@NotNull
	private StatisticalRangeType statisticalRange;

	/**
	 * 支持度
	 */
	@Schema(description = "支持度")
	@Range(min = 0, max = 10)
	private BigDecimal support;

	/**
	 * 置信度
	 */
	@Schema(description = "置信度")
	@Range(min = 0, max = 10)
	private BigDecimal confidence;

	/**
	 * 提升度
	 */
	@Schema(description = "提升度")
	@Min(1)
	private BigDecimal lift;

	/**
	 * 选中状态 0：未选中，1：选中
	 */
	@Schema(description = "选中状态 0：未选中，1：选中")
	private BoolFlag checkStatus;

}