package com.wanmi.sbc.empower.bean.vo;

import lombok.Data;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>拉卡拉经营内容表VO</p>
 * @author zhanghao
 * @date 2022-07-08 11:02:05
 */
@Schema
@Data
public class LedgerContentVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 经营内容id
	 */
	@Schema(description = "经营内容id")
	private Long contentId;

	/**
	 * 经营内容名称
	 */
	@Schema(description = "经营内容名称")
	private String contentName;

}