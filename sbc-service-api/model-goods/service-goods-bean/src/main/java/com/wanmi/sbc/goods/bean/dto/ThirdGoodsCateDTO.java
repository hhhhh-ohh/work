package com.wanmi.sbc.goods.bean.dto;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.ThirdPlatformType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>VO</p>
 * @author 
 * @date 2020-08-17 14:46:43
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ThirdGoodsCateDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 商品分类主键
	 */
	@Schema(description = "商品分类主键")
	@NotNull
	private Long cateId;

	/**
	 * 分类名称
	 */
	@Schema(description = "分类名称")
	@NotBlank
	private String cateName;

	/**
	 * 父分类ID
	 */
	@Schema(description = "父分类ID")
	@NotNull
	private Long cateParentId;

	/**
	 * 分类层次路径,例0|01|001
	 */
	@Schema(description = "分类层次路径,例0|01|001")
	@NotBlank
	private String catePath;

	/**
	 * 分类层级
	 */
	@Schema(description = "分类层级")
	@NotNull
	private Integer cateGrade;
	/**
	 * 创建时间
	 */
	private LocalDateTime createTime;

	/**
	 * 更新时间
	 */
	private LocalDateTime updateTime;
	/**
	 * 第三方平台来源(0,linkedmall)
	 */
	@Schema(description = "第三方平台来源(0,linkedmall)")
	@NotNull
	private ThirdPlatformType thirdPlatformType;


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
	 *
	 */
	private DeleteFlag delFlag;
}