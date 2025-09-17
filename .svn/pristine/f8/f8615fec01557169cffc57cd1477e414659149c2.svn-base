package com.wanmi.sbc.goods.api.request.priceadjustmentrecord;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

import java.time.LocalDateTime;

/***
 * @description 查询调价记录表指定时间内生效的调价单
 * @author malianfeng
 * @date 2021/9/10 10:02
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PriceAdjustmentRecordWillEffectiveListRequest extends BaseRequest {

	private static final long serialVersionUID = 1L;

	/**
	 * 搜索条件:生效时间开始
	 */
	@Schema(description = "搜索条件:生效时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	@NotNull
	private LocalDateTime effectiveTimeBegin;

	/**
	 * 搜索条件:生效时间截止
	 */
	@Schema(description = "搜索条件:生效时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	@NotNull
	private LocalDateTime effectiveTimeEnd;

}