package com.wanmi.sbc.goods.api.request.thirdgoodscate;

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
 * <p>第三方平台类目修改参数</p>
 * @author 
 * @date 2020-08-29 13:35:42
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ThirdGoodsCateModifyRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@Schema(description = "id")
	@Max(9223372036854775807L)
	private Long id;

	/**
	 * 三方商品分类主键
	 */
	@Schema(description = "三方商品分类主键")
	@NotNull
	@Max(9223372036854775807L)
	private Long cateId;

	/**
	 * 分类名称
	 */
	@Schema(description = "分类名称")
	@NotBlank
	@Length(max=45)
	private String cateName;

	/**
	 * 父分类ID
	 */
	@Schema(description = "父分类ID")
	@Max(9223372036854775807L)
	private Long cateParentId;

	/**
	 * 分类层次路径,例0|01|001
	 */
	@Schema(description = "分类层次路径,例0|01|001")
	@NotBlank
	@Length(max=1000)
	private String catePath;

	/**
	 * 分类层级
	 */
	@Schema(description = "分类层级")
	@NotNull
	@Max(127)
	private Integer cateGrade;

	/**
	 * 创建时间
	 */
	@Schema(description = "创建时间", hidden = true)
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTime;

	/**
	 * 第三方平台来源(0,linkedmall)
	 */
	@Schema(description = "第三方平台来源(0,linkedmall)")
	@NotNull
	@Max(127)
	private Integer thirdPlatformType;

}