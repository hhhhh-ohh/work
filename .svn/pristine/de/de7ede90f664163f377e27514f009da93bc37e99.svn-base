package com.wanmi.sbc.setting.api.request.recommend;

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
 * <p>种草信息表修改参数</p>
 * @author 黄昭
 * @date 2022-05-17 16:24:21
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecommendModifyRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@Schema(description = "id")
	@NotNull
	private Long id;

	/**
	 * 标题
	 */
	@Schema(description = "标题")
	@NotBlank
	@Length(max=40)
	private String title;

	/**
	 * 分类id
	 */
	@Schema(description = "分类id")
	private Long cateId;

	/**
	 * 封面
	 */
	@Schema(description = "封面")
	@NotBlank
	private String coverImg;

	/**
	 * 视频
	 */
	@Schema(description = "视频")
	private String video;

	/**
	 * 修改人
	 */
	@Schema(description = "修改人", hidden = true)
	private String updatePerson;

	/**
	 * 修改时间
	 */
	@Schema(description = "修改时间", hidden = true)
	private LocalDateTime updateTime;

}
