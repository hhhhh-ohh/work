package com.wanmi.sbc.setting.api.request.helpcenterarticlecate;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.*;
import jakarta.validation.constraints.NotBlank;

import lombok.*;

import org.hibernate.validator.constraints.*;

import java.time.LocalDateTime;

/**
 * <p>帮助中心文章信息修改参数</p>
 * @author 吕振伟
 * @date 2023-03-16 09:44:52
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HelpCenterArticleCateModifyRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键Id
	 */
	@Schema(description = "主键Id")
	@NotNull
	private Long id;

	/**
	 * 分类名称
	 */
	@Schema(description = "分类名称")
	@NotBlank
	@Length(max=6)
	private String cateName;

	/**
	 * 分类排序
	 */
	@Schema(description = "分类排序")
	private Integer cateSort;

	/**
	 * 是否默认分类 0:否，1：是
	 */
	@Schema(description = "是否默认分类 0:否，1：是")
	private DefaultFlag defaultCate;

	/**
	 * 创建时间
	 */
	@Schema(description = "创建时间", hidden = true)
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTime;

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
