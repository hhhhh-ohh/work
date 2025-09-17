package com.wanmi.sbc.vas.bean.vo.recommend;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>智能推荐配置VO</p>
 * @author lvzhenwei
 * @date 2020-11-27 16:28:20
 */
@Schema
@Data
public class RecommendSystemConfigVO extends BasicResponse {
	private static final long serialVersionUID = 1L;

	/**
	 *  编号
	 */
	@Schema(description = " 编号")
	private Long id;

	/**
	 * 键
	 */
	@Schema(description = "键")
	private String configKey;

	/**
	 * 类型
	 */
	@Schema(description = "类型")
	private String configType;

	/**
	 * 名称
	 */
	@Schema(description = "名称")
	private String configName;

	/**
	 * 备注
	 */
	@Schema(description = "备注")
	private String remark;

	/**
	 * 状态,0:未启用1:已启用
	 */
	@Schema(description = "状态,0:未启用1:已启用")
	private Integer status;

	/**
	 * 配置内容，如JSON内容
	 */
	@Schema(description = "配置内容，如JSON内容")
	private String context;

}