package com.wanmi.sbc.setting.api.request.helpcenterarticlerecord;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.*;

import lombok.*;

import org.hibernate.validator.constraints.*;

import java.time.LocalDateTime;

/**
 * <p>帮助中心文章记录修改参数</p>
 * @author 吕振伟
 * @date 2023-03-17 16:56:08
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HelpCenterArticleRecordModifyRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键Id
	 */
	@Schema(description = "主键Id")
	@Max(9223372036854775807L)
	private Long id;

	/**
	 * 文章id
	 */
	@Schema(description = "文章id")
	@NotNull
	@Max(9223372036854775807L)
	private Long articleId;

	/**
	 * customerId
	 */
	@Schema(description = "customerId")
	@Length(max=32)
	private String customerId;

	/**
	 * 解决状态  0：未解决，1：已解决
	 */
	@Schema(description = "解决状态  0：未解决，1：已解决")
	@NotNull
	private DefaultFlag solveType;

	/**
	 * 解决时间
	 */
	@Schema(description = "解决时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime solveTime;

	/**
	 * 更新人
	 */
	@Schema(description = "更新人", hidden = true)
	private String updatePerson;

}
