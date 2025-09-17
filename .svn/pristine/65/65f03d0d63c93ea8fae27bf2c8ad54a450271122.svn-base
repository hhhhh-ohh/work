package com.wanmi.sbc.vas.api.request.recommend.filterrulessetting;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.DeleteFlag;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>新增参数</p>
 * @author zhongjichuan
 * @date 2020-11-26 16:32:49
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilterRulesSettingAddRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 多少天内不重复
	 */
	@Schema(description = "多少天内不重复")
	@Max(32767)
	private Integer dayNum;

	/**
	 * 多少条内不重复
	 */
	@Schema(description = "多少条内不重复")
	@Max(9999999999L)
	private Integer num;

	/**
	 *  过滤规则类型：0：展示后去重，1：点击后去重，2：购买后去重
	 */
	@Schema(description = " 过滤规则类型：0：展示后去重，1：点击后去重，2：购买后去重")
	@NotNull
	@Max(127)
	private Integer type;

	/**
	 * 是否删除标志 0：否，1：是
	 */
	@Schema(description = "是否删除标志 0：否，1：是", hidden = true)
	private DeleteFlag delFlag;

	/**
	 * 创建人
	 */
	@Schema(description = "创建人", hidden = true)
	private String createPerson;

	/**
	 * 修改人
	 */
	@Schema(description = "修改人", hidden = true)
	private String updatePerson;

}