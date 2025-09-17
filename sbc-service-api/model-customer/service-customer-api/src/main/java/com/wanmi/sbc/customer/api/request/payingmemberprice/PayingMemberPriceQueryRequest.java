package com.wanmi.sbc.customer.api.request.payingmemberprice;

import java.math.BigDecimal;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import java.time.LocalDateTime;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import lombok.*;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>付费设置表通用查询请求参数</p>
 * @author zhanghao
 * @date 2022-05-13 13:40:30
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayingMemberPriceQueryRequest extends BaseQueryRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 批量查询-付费设置idList
	 */
	@Schema(description = "批量查询-付费设置idList")
	private List<Integer> priceIdList;

	/**
	 * 付费设置id
	 */
	@Schema(description = "付费设置id")
	private Integer priceId;

	/**
	 * 付费设置id
	 */
	@Schema(description = "付费设置id")
	private Integer levelId;

	/**
	 * 付费设置数量 ，例如3个月
	 */
	@Schema(description = "付费设置数量 ，例如3个月")
	private Integer priceNum;

	/**
	 * 付费设置总金额，例如上述3个月90元
	 */
	@Schema(description = "付费设置总金额，例如上述3个月90元")
	private BigDecimal priceTotal;

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
	 * createPerson
	 */
	@Schema(description = "createPerson")
	private String createPerson;

	/**
	 * 搜索条件:更新时间开始
	 */
	@Schema(description = "搜索条件:更新时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTimeBegin;
	/**
	 * 搜索条件:更新时间截止
	 */
	@Schema(description = "搜索条件:更新时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTimeEnd;

	/**
	 * updatePerson
	 */
	@Schema(description = "updatePerson")
	private String updatePerson;

	/**
	 * 删除标识：0：未删除；1：已删除
	 */
	@Schema(description = "删除标识：0：未删除；1：已删除")
	private DeleteFlag delFlag;

}