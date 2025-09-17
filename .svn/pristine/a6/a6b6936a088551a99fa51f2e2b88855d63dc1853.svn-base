package com.wanmi.sbc.crm.api.request.caterelatedrecommend;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.*;

import lombok.*;

import org.hibernate.validator.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>分类相关性推荐修改参数</p>
 * @author lvzhenwei
 * @date 2020-11-26 10:55:53
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CateRelatedRecommendModifyRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@Schema(description = "主键id")
	@Max(9223372036854775807L)
	private Long id;

	/**
	 * 分类id
	 */
	@Schema(description = "分类id")
	@Length(max=32)
	private String cateId;

	/**
	 * 关联分类id
	 */
	@Schema(description = "关联分类id")
	@Length(max=32)
	private String relatedCateId;

	/**
	 * 提升度
	 */
	@Schema(description = "提升度")
	@Max(9999999999L)
	private Integer lift;

	/**
	 * 权重
	 */
	@Schema(description = "权重")
	private BigDecimal weight;

	/**
	 * 类型，0：关联分析，1：手动关联
	 */
	@Schema(description = "类型，0：关联分析，1：手动关联")
	@Max(127)
	private Integer type;

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