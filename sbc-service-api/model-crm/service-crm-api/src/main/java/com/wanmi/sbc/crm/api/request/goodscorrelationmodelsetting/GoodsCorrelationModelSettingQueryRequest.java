package com.wanmi.sbc.crm.api.request.goodscorrelationmodelsetting;

import java.math.BigDecimal;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import java.time.LocalDateTime;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import lombok.*;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>通用查询请求参数</p>
 * @author zhongjichuan
 * @date 2020-11-27 11:27:06
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsCorrelationModelSettingQueryRequest extends BaseQueryRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 批量查询-主键List
	 */
	@Schema(description = "批量查询-主键List")
	private List<Integer> idList;

	/**
	 * 主键
	 */
	@Schema(description = "主键")
	private Integer id;

	/**
	 * 统计范围0：近一个月，1：近3个月，2：近6个月，3：近1年
	 */
	@Schema(description = "统计范围0：近一个月，1：近3个月，2：近6个月，3：近1年")
	private Integer statisticalRange;

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
	private Integer checkStatus;

	/**
	 * 是否删除标志 0：否，1：是
	 */
	@Schema(description = "是否删除标志 0：否，1：是")
	private DeleteFlag delFlag;

	/**
	 * 搜索条件:创建时间开始
	 */
	@Schema(description = "搜索条件:创建时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTimeBegin;
	/**
	 * 搜索条件:创建时间截止
	 */
	@Schema(description = "搜索条件:创建时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTimeEnd;

	/**
	 * 创建人
	 */
	@Schema(description = "创建人")
	private String createPerson;

	/**
	 * 搜索条件:修改时间开始
	 */
	@Schema(description = "搜索条件:修改时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTimeBegin;
	/**
	 * 搜索条件:修改时间截止
	 */
	@Schema(description = "搜索条件:修改时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTimeEnd;

	/**
	 * 修改人
	 */
	@Schema(description = "修改人")
	private String updatePerson;

}