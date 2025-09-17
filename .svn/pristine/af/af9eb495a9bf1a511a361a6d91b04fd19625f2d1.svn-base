package com.wanmi.sbc.vas.api.request.recommend.recommendcatemanage;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.vas.bean.enums.recommen.NoPushType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Max;

import lombok.*;

import java.time.LocalDateTime;

/**
 * <p>分类推荐管理修改参数</p>
 * @author lvzhenwei
 * @date 2020-11-19 14:05:07
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecommendCateManageModifyRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Schema(description = "主键")
	@Max(9223372036854775807L)
	private Long id;

	/**
	 * 类目id
	 */
	@Schema(description = "类目id")
	private Long cateId;

	/**
	 * 权重
	 */
	@Schema(description = "权重")
	@Max(9999999999L)
	private Integer weight;

	/**
	 * 禁推标识 0：可推送；1:禁推
	 */
	@Schema(description = "禁推标识 0：可推送；1:禁推")
	private NoPushType noPushType;

	/**
	 * 更新时间
	 */
	@Schema(description = "更新时间", hidden = true)
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTime;

	/**
	 * 更新人
	 */
	@Schema(description = "更新人", hidden = true)
	private String updatePerson;

}