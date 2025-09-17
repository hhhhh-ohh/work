package com.wanmi.sbc.marketing.api.request.electroniccoupon;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>电子卡密表分页查询请求参数</p>
 * @author 许云鹏
 * @date 2022-01-26 17:24:59
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ElectronicCardPageRequest extends BaseQueryRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 批量查询-卡密IdList
	 */
	@Schema(description = "批量查询-卡密IdList")
	private List<String> idList;

	/**
	 * 卡密Id
	 */
	@Schema(description = "卡密Id")
	private String id;

	/**
	 * 卡券id
	 */
	@Schema(description = "卡券id")
	@NotNull
	private Long couponId;

	/**
	 * 卡号
	 */
	@Schema(description = "卡号")
	private String cardNumber;

	/**
	 * 卡密
	 */
	@Schema(description = "卡密")
	private String cardPassword;

	/**
	 * 优惠码
	 */
	@Schema(description = "优惠码")
	private String cardPromoCode;

	/**
	 * 卡密状态  0、未发送 1、已发送 2、已失效
	 */
	@Schema(description = "卡密状态  0、未发送 1、已发送 2、已失效")
	private Integer cardState;

	/**
	 * 搜索条件:卡密销售开始时间开始
	 */
	@Schema(description = "搜索条件:卡密销售开始时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime saleStartTimeBegin;
	/**
	 * 搜索条件:卡密销售开始时间截止
	 */
	@Schema(description = "搜索条件:卡密销售开始时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime saleStartTimeEnd;

	/**
	 * 搜索条件:卡密销售结束时间开始
	 */
	@Schema(description = "搜索条件:卡密销售结束时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime saleEndTimeBegin;
	/**
	 * 搜索条件:卡密销售结束时间截止
	 */
	@Schema(description = "搜索条件:卡密销售结束时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime saleEndTimeEnd;

	/**
	 * 批次id
	 */
	@Schema(description = "批次id")
	private String recordId;

	/**
	 * 是否删除 0 否  1 是
	 */
	@Schema(description = "是否删除 0 否  1 是")
	private DeleteFlag delFlag;

	/**
	 * 是否加密处理
	 */
	private Boolean encrypt = Boolean.FALSE;

}