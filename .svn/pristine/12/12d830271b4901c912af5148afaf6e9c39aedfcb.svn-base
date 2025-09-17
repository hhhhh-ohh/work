package com.wanmi.sbc.goods.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.enums.ThirdPlatformType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * <p>第三方平台类目VO</p>
 * @author 
 * @date 2020-08-29 13:35:42
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ThirdGoodsCateVO extends BasicResponse {
	private static final long serialVersionUID = 1L;

	public ThirdGoodsCateVO(Long cateId,String cateName, Integer cateGrade, ThirdPlatformType thirdPlatformType,Long cateParentId,Integer qualificationType,String qualification,Integer productQualificationType,String productQualification,String catePath){
		this.cateId = cateId;
		this.cateName = cateName;
		this.cateGrade = cateGrade;
		this.thirdPlatformType = thirdPlatformType;
		this.cateParentId = cateParentId;
		this.qualificationType = qualificationType;
		this.qualification = qualification;
		this.catePath = catePath;
		this.productQualificationType = productQualificationType;
		this.productQualification = productQualification;
	}
	/**
	 * id
	 */
	@Schema(description = "id")
	private Long id;

	/**
	 * 三方商品分类主键
	 */
	@Schema(description = "三方商品分类主键")
	private Long cateId;

	/**
	 * 分类名称
	 */
	@Schema(description = "分类名称")
	private String cateName;

	/**
	 * 父分类ID
	 */
	@Schema(description = "父分类ID")
	private Long cateParentId;

	/**
	 * 分类层次路径,例0|01|001
	 */
	@Schema(description = "分类层次路径,例0|01|001")
	private String catePath;

	/**
	 * 分类层级
	 */
	@Schema(description = "分类层级")
	private Integer cateGrade;

	/**
	 * 第三方平台来源(0,linkedmall)
	 */
	@Schema(description = "第三方平台来源(0,linkedmall 1,京东VOP)")
	private ThirdPlatformType thirdPlatformType;


	/**
	 * 微信类目资质类型,0:不需要,1:必填,2:选填
	 */
	@Schema(description = "微信类目资质类型,0:不需要,1:必填,2:选填")
	private Integer qualificationType;

	/**
	 * 微信类目资质
	 */
	@Schema(description = "微信类目资质")
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

	@Schema(description = "微信类目资质图片")
	private List<String> certificateUrls;

	@Schema(description = "映射的平台类目")
	private List<GoodsCateVO> goodsCateVOS;


}