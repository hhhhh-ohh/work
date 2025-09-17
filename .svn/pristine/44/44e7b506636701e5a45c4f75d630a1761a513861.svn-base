package com.wanmi.sbc.crm.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.crm.bean.enums.TagDimensionFirstLastType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>标签维度VO</p>
 * @author dyt
 * @date 2020-03-12 16:00:30
 */
@Schema
@Data
public class TagDimensionVO extends BasicResponse {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@Schema(description = "id")
	private Long id;

	/**
	 * 维度名
	 */
	@Schema(description = "维度名")
	private String name;

	/**
	 * 维度对应表明
	 */
	@Schema(description = "维度对应表明")
	private String tableName;

	/**
	 * 首次末次类型
	 */
	@Schema(description = "首次末次类型")
	private TagDimensionFirstLastType firstLastType;

	/**
	 * 首次末次类型
	 */
	@Schema(description = "参数列表")
	private List<TagParamVO> tagParamVOList;

}