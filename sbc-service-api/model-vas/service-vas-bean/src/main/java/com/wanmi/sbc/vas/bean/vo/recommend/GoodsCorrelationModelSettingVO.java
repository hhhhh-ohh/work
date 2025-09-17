package com.wanmi.sbc.vas.bean.vo.recommend;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.vas.bean.enums.recommen.StatisticalRangeType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>VO</p>
 * @author zhongjichuan
 * @date 2020-11-27 11:27:06
 */
@Schema
@Data
public class GoodsCorrelationModelSettingVO extends BasicResponse {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Schema(description = "主键")
	private Integer id;

	/**
	 * 统计范围0：近一个月，1：近3个月，2：近6个月，3：近1年
	 */
	@Schema(description = "统计范围0：近一个月，1：近3个月，2：近6个月，3：近1年")
	private StatisticalRangeType statisticalRange;

	/**
	 * 预估订单数据量
	 */
	@Schema(description = "预估订单数据量")
	private Long tradeNum;

	/**
	 * 支持度
	 */
	@Schema(description = "支持度")
	private BigDecimal support;

	/**
	 * 置信度
	 */
	@Schema(description = "置信度")
	private BigDecimal confidence;

	/**
	 * 提升度
	 */
	@Schema(description = "提升度")
	private BigDecimal lift;

	/**
	 * 选中状态 0：未选中，1：选中
	 */
	@Schema(description = "选中状态 0：未选中，1：选中")
	private BoolFlag checkStatus;

}