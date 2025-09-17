package com.wanmi.sbc.vas.api.request.recommend.goodscorrelationmodelsetting;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.DeleteFlag;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;

import lombok.*;

import java.math.BigDecimal;

/**
 * <p>新增参数</p>
 * @author zhongjichuan
 * @date 2020-11-27 11:27:06
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsCorrelationModelSettingAddRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 统计范围0：近一个月，1：近3个月，2：近6个月，3：近1年
	 */
	@Schema(description = "统计范围0：近一个月，1：近3个月，2：近6个月，3：近1年")
	@NotNull
	@Max(127)
	private Integer statisticalRange;

	/**
	 * 预估订单数据量
	 */
	@Schema(description = "预估订单数据量")
	@Max(9223372036854775807L)
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
	@NotNull
	@Max(127)
	private Integer checkStatus;

	/**
	 * 是否删除标志 0：否，1：是
	 */
	@Schema(description = "是否删除标志 0：否，1：是", hidden = true)
	private DeleteFlag delFlag;

	/**
	 * 创建人
	 */
	@Schema(description = "创建人", hidden = true)
	private String createPerson;

	/**
	 * 修改人
	 */
	@Schema(description = "修改人", hidden = true)
	private String updatePerson;

}