package com.wanmi.sbc.crm.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import lombok.Data;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>偏好标签明细VO</p>
 * @author dyt
 * @date 2020-03-11 14:58:07
 */
@Schema
@Data
public class PreferenceTagDetailVO extends BasicResponse {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@Schema(description = "id")
	private Long id;

	/**
	 * 标签id
	 */
	@Schema(description = "标签id")
	private Long tagId;

	/**
	 * 偏好类标签名称
	 */
	@Schema(description = "偏好类标签名称")
	private String detailName;

	/**
	 * 会员人数
	 */
	@Schema(description = "会员人数")
	private Long customerCount;

}