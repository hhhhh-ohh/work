package com.wanmi.sbc.goods.api.request.restrictedrecord;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.wanmi.sbc.common.util.CustomLocalDateSerializer;
import com.wanmi.sbc.common.util.CustomLocalDateDeserializer;
import java.time.LocalDate;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import lombok.*;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>限售分页查询请求参数</p>
 * @author 限售记录
 * @date 2020-04-11 15:59:01
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestrictedRecordPageRequest extends BaseQueryRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 批量查询-记录主键List
	 */
	@Schema(description = "批量查询-记录主键List")
	private List<Long> recordIdList;

	/**
	 * 记录主键
	 */
	@Schema(description = "记录主键")
	private Long recordId;

	/**
	 * 会员的主键
	 */
	@Schema(description = "会员的主键")
	private String customerId;

	/**
	 * 货品主键
	 */
	@Schema(description = "货品主键")
	private String goodsInfoId;

	/**
	 * 购买的数量
	 */
	@Schema(description = "购买的数量")
	private Long purchaseNum;

	/**
	 * 周期类型（0: 终生，1:周  2:月  3:年）
	 */
	@Schema(description = "周期类型（0: 终生，1:周  2:月  3:年）")
	private Integer restrictedCycleType;

	/**
	 * 搜索条件:开始时间开始
	 */
	@Schema(description = "搜索条件:开始时间开始")
	@JsonSerialize(using = CustomLocalDateSerializer.class)
	@JsonDeserialize(using = CustomLocalDateDeserializer.class)
	private LocalDate startDateBegin;
	/**
	 * 搜索条件:开始时间截止
	 */
	@Schema(description = "搜索条件:开始时间截止")
	@JsonSerialize(using = CustomLocalDateSerializer.class)
	@JsonDeserialize(using = CustomLocalDateDeserializer.class)
	private LocalDate startDateEnd;

	/**
	 * 搜索条件:结束时间开始
	 */
	@Schema(description = "搜索条件:结束时间开始")
	@JsonSerialize(using = CustomLocalDateSerializer.class)
	@JsonDeserialize(using = CustomLocalDateDeserializer.class)
	private LocalDate endDateBegin;
	/**
	 * 搜索条件:结束时间截止
	 */
	@Schema(description = "搜索条件:结束时间截止")
	@JsonSerialize(using = CustomLocalDateSerializer.class)
	@JsonDeserialize(using = CustomLocalDateDeserializer.class)
	private LocalDate endDateEnd;

}