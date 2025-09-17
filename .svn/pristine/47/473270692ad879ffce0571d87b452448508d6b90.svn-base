package com.wanmi.sbc.crm.api.request.autotag;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.crm.bean.dto.AutoTagSelectDTO;
import com.wanmi.sbc.crm.bean.dto.TagRuleDTO;
import com.wanmi.sbc.crm.bean.enums.RelationType;
import com.wanmi.sbc.crm.bean.enums.TagType;
import com.wanmi.sbc.crm.bean.vo.RangeParamVo;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.*;

import org.hibernate.validator.constraints.Length;

import java.util.List;
import java.util.Map;

/**
 * <p>自动标签修改参数</p>
 * @author dyt
 * @date 2020-03-11 14:47:32
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AutoTagModifyRequest extends BaseRequest {
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
	@NotBlank
	@Length(max=255)
	private String tagName;

	/**
	 * 标签类型，0：偏好标签组，1：指标值标签，2：指标值范围标签，3、综合类标签
	 */
	@Schema(description = "标签类型，0：偏好标签组，1：指标值标签，2：指标值范围标签，3、综合类标签")
	@NotNull
	private TagType type;

	/**
	 * 规则天数
	 */
	@Schema(description = "规则天数")
	@NotNull
	private Integer day;

	/**
	 * 一级维度且或关系，0：且，1：或
	 */
	@Schema(description = "一级维度且或关系，0：且，1：或")
//    @NotNull
	private RelationType relationType;

	/**
	 * 创建人
	 */
	@Schema(description = "创建人", hidden = true)
	private String createPerson;

	/**
	 * 更新人
	 */
	@Schema(description = "更新人", hidden = true)
	private String updatePerson;

	/**
	 * 一级维度-规则信息
	 */
	@Schema(description = "一级维度-规则信息")
//    @Valid
//    @NotEmpty
	private List<TagRuleDTO> ruleList;

	@Schema(description = "一级维度-规则信息")
	private Integer count;

	@Schema(description = "一级维度-规则信息")
	private Integer maxLen;

	@Schema(description = "一级维度-规则信息")
	private Map<Integer, AutoTagSelectDTO> autoTagSelectMap;

	@Schema(description = "偏好类标签范围属性")
	private List<RangeParamVo> dataRange;

}