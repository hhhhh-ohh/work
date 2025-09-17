package com.wanmi.sbc.marketing.api.request.communityactivity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>批量更新生成标识的社区团购活动表请求参数</p>
 * @author dyt
 * @date 2023-07-24 14:26:35
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunityActivityModifyGenerateFlagByIdListRequest extends BaseRequest {
private static final long serialVersionUID = 1L;

	/**
	 * 批量删除-主键List
	 */
	@Schema(description = "批量删除-主键List")
	@NotEmpty
	private List<String> activityIdList;

	/**
	 * 生成时间
	 */
	@Schema(description = "生成时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime generateTime;

	/**
	 * 生成标识
	 */
	@Schema(description = "生成标识")
	@NotNull
	private Integer generateFlag;
}
