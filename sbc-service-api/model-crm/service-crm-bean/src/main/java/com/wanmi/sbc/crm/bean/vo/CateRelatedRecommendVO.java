package com.wanmi.sbc.crm.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;


import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>分类相关性推荐VO</p>
 * @author lvzhenwei
 * @date 2020-11-26 10:55:53
 */
@Schema
@Data
public class CateRelatedRecommendVO extends BasicResponse {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@Schema(description = "主键id")
	private Long id;

	/**
	 * 分类id
	 */
	@Schema(description = "分类id")
	private String cateId;

	/**
	 * 关联分类id
	 */
	@Schema(description = "关联分类id")
	private String relatedCateId;

	/**
	 * 提升度
	 */
	@Schema(description = "提升度")
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
	private Integer type;

}