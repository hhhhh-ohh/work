package com.wanmi.sbc.setting.api.request.recommendcate;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import java.time.LocalDateTime;
import com.wanmi.sbc.common.base.BaseRequest;
import lombok.*;
import jakarta.validation.constraints.*;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.*;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>笔记分类表新增参数</p>
 * @author 王超
 * @date 2022-05-17 16:00:27
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecommendCateAddRequest extends BaseRequest {

	private static final long serialVersionUID = 2996750934540832253L;

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
	 * 是否删除标志 0：否，1：是
	 */
	@Schema(description = "是否删除标志 0：否，1：是", hidden = true)
	private DeleteFlag delFlag;

	/**
	 * 创建人
	 */
	@Schema(description = "创建人", hidden = true)
	private String createPerson;

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