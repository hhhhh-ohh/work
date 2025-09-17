package com.wanmi.sbc.marketing.api.request.electroniccoupon;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>卡密导入记录表分页查询请求参数</p>
 * @author 许云鹏
 * @date 2022-01-26 17:36:55
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ElectronicImportRecordPageRequest extends BaseQueryRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 批量查询-批次idList
	 */
	@Schema(description = "批量查询-批次idList")
	private List<String> idList;

	/**
	 * 批次id
	 */
	@Schema(description = "批次id")
	private String id;

	/**
	 * 卡券id
	 */
	@Schema(description = "卡券id")
	@NotNull
	private Long couponId;

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
	 * 搜索条件:销售开始时间开始
	 */
	@Schema(description = "搜索条件:销售开始时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime saleStartTimeBegin;
	/**
	 * 搜索条件:销售开始时间截止
	 */
	@Schema(description = "搜索条件:销售开始时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime saleStartTimeEnd;

	/**
	 * 搜索条件:销售结束时间开始
	 */
	@Schema(description = "搜索条件:销售结束时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime saleEndTimeBegin;
	/**
	 * 搜索条件:销售结束时间截止
	 */
	@Schema(description = "搜索条件:销售结束时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime saleEndTimeEnd;

}