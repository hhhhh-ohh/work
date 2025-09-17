package com.wanmi.sbc.customer.api.request.goodsfootmark;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.*;

import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

/**
 * <p>我的足迹新增参数</p>
 * @author 
 * @date 2022-05-30 07:30:41
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsFootmarkAddRequest extends CustomerBaseRequest {
	private static final long serialVersionUID = 1L;

	@Schema(description = "customerId")
	@NotNull
	private Long footmarkId;

	/**
	 * customerId
	 */
	@Schema(description = "customerId")
	@NotBlank
	@Length(max=32)
	private String customerId;

	/**
	 * goodsInfoId
	 */
	@Schema(description = "goodsInfoId")
	@NotBlank
	@Length(max=32)
	private String goodsInfoId;

	/**
	 * 创建时间
	 */
	@Schema(description = "创建时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTime;

	/**
	 * 更新时间
	 */
	@Schema(description = "更新时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTime;

	/**
	 * 删除标识,0:未删除1:已删除
	 */
	@Schema(description = "删除标识,0:未删除1:已删除")
	private DeleteFlag delFlag;

	/**
	 * 浏览次数
	 */
	@Schema(description = "浏览次数")
	@Max(9223372036854775807L)
	private Long viewTimes;

}