package com.wanmi.sbc.marketing.api.request.electroniccoupon;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

import java.time.LocalDateTime;

/**
 * <p>卡密导入记录表新增参数</p>
 * @author 许云鹏
 * @date 2022-01-26 17:36:55
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ElectronicImportRecordAddRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 卡券id
	 */
	@Schema(description = "卡券id")
	@NotNull
	private Long couponId;

	/**
	 * 销售开始时间
	 */
	@Schema(description = "销售开始时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime saleStartTime;

	/**
	 * 销售结束时间
	 */
	@Schema(description = "销售结束时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime saleEndTime;

	/**
	 * 创建时间
	 */
	@Schema(description = "创建时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTime;

}