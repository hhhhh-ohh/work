package com.wanmi.sbc.setting.api.request.helpcenterarticle;

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
 * @date 2023-03-15 10:15:47
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HelpCenterArticleModifyRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键Id
	 */
	@Schema(description = "主键Id")
	@NotNull
	private Long id;

	/**
	 * 文章标题
	 */
	@Schema(description = "文章标题")
	@NotBlank
	@Length(max=40)
	private String articleTitle;

	/**
	 * 文章分类id
	 */
	@Schema(description = "文章分类id")
	@NotNull
	private Long articleCateId;

	/**
	 * 文章内容
	 */
	@Schema(description = "文章内容")
	@NotBlank
	private String articleContent;

	/**
	 * 文章状态，0:展示，1:隐藏
	 */
	@Schema(description = "文章状态，0:展示，1:隐藏")
	@NotNull
	private DefaultFlag articleType;

	/**
	 * 查看次数
	 */
	@Schema(description = "查看次数")
	private Long viewNum;

	/**
	 * 解决次数
	 */
	@Schema(description = "解决次数")
	private Long solveNum;

	/**
	 * 未解决次数
	 */
	@Schema(description = "未解决次数")
	private Long unresolvedNum;

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
