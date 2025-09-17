package com.wanmi.sbc.vas.bean.vo;

import com.wanmi.sbc.common.enums.DefaultFlag;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>商品评分算法VO</p>
 * @author Bob
 * @date 2021-03-03 14:27:46
 */
@Schema
@Data
public class CommodityScoringAlgorithmVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Schema(description = "主键")
	private Long id;

	/**
	 * 列名
	 */
	@Schema(description = "列名")
	private String keyType;

	/**
	 * 权值
	 */
	@Schema(description = "权值")
	private BigDecimal weight;

	/**
	 * 标签ID
	 */
	@Schema(description = "标签ID")
	private String tagId;

	/**
	 * 0: 否 1：是
	 */
	@Schema(description = "0: 否 1：是")
	private DefaultFlag isSelected;

}