package com.wanmi.sbc.crm.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.crm.bean.enums.RelationType;
import com.wanmi.sbc.crm.bean.enums.TagType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>自动标签VO</p>
 * @author dyt
 * @date 2020-03-11 14:47:32
 */
@Schema
@Data
public class AutoTagInitVO extends BasicResponse {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@Schema(description = "id")
	private Long id;

	/**
	 * 自动标签名称
	 */
	@Schema(description = "自动标签名称")
	private String tagName;

	/**
	 * 标签类型，0：偏好标签组，1：指标值标签，2：指标值范围标签，3、综合类标签
	 */
	@Schema(description = "标签类型，0：偏好标签组，1：指标值标签，2：指标值范围标签，3、综合类标签")
	private TagType type;

    /**
     * 规则天数
     */
    @Schema(description = "规则天数")
    private Integer day;

	/**
	 * 一级维度且或关系，0：且，1：或
	 */
	@Schema(description = "一级维度且或关系，0：且，1：或")
	private RelationType relationType;

    /**
     * 一级维度-规则信息
     */
    @Schema(description = "一级维度-规则信息")
    private List<TagRuleVO> ruleList;

    /**
     * 信息是否存在
     */
    @Schema(description = "信息是否存在")
    private Boolean existsFlag;

	/**
	 * 系统标签id
	 */
	@Schema(description = "系统标签id")
	private Long systemTagId;

	/**
	 * 标签ID
	 */
	@Schema(description = "标签id")
	private Long tagId;

	/**
	 * 规则信息JSON字符串
	 * @see TagRuleVO
	 */
	@Schema(description = "规则信息JSON字符串")
	private String ruleJson;

	/**
	 * 规则信息JSON字符串
	 * @see TagRuleVO
	 */
	@Schema(description = "规则信息JSON字符串")
	private String ruleJsonSql;
}