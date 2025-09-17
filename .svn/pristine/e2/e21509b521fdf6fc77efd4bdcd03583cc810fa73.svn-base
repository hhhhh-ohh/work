package com.wanmi.sbc.goods.api.request.thirdgoodscate;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.DeleteFlag;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.*;

import org.hibernate.validator.constraints.Length;

/**
 * <p>第三方平台类目新增参数</p>
 * @author 
 * @date 2020-08-29 13:35:42
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ThirdGoodsCateAddRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

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
	 * 第三方平台来源(0,linkedmall)
	 */
	@Schema(description = "第三方平台来源(0,linkedmall)")
	@NotNull
	@Max(127)
	private Integer thirdPlatformType;


	/**
	 * 微信类目资质类型,0:不需要,1:必填,2:选填
	 */
	private Integer qualificationType;

	/**
	 * 微信类目资质
	 */
	private String qualification;

	/**
	 * 微信商品资质类型,0:不需要,1:必填,2:选填
	 */
	@Schema(description = "微信商品资质类型,0:不需要,1:必填,2:选填")
	private Integer productQualificationType;

	/**
	 * 微信商品资质
	 */
	@Schema(description = "微信商品资质")
	private String productQualification;

	/**
	 * 删除标识,0:未删除1:已删除
	 */
	@Schema(description = "删除标识,0:未删除1:已删除", hidden = true)
	private DeleteFlag delFlag;

}