package com.wanmi.sbc.setting.api.request.recommendcate;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.*;
import jakarta.validation.constraints.NotBlank;

import lombok.*;

import org.hibernate.validator.constraints.*;

import java.time.LocalDateTime;

/**
 * <p>笔记分类表修改参数</p>
 * @author 王超
 * @date 2022-05-17 16:00:27
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecommendCateModifyRequest extends BaseRequest {

	private static final long serialVersionUID = 5598020188050984824L;

	/**
	 * 分类id
	 */
	@Schema(description = "分类id")
	@NotNull
	private Long cateId;

	/**
	 * 分类名称
	 */
	@Schema(description = "分类名称")
	@Length(max=10)
	@NotBlank
	private String cateName;

	/**
	 * 排序
	 */
	@Schema(description = "排序")
	@Max(127)
	private Integer cateSort;

	/**
	 * 修改人
	 */
	@Schema(description = "修改人", hidden = true)
	private String updatePerson;

	/**
	 * 删除时间
	 */
	@Schema(description = "删除时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime delTime;

	/**
	 * 删除人
	 */
	@Schema(description = "删除人")
	@Length(max=32)
	private String delPerson;

}
