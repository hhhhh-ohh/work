package com.wanmi.sbc.setting.bean.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>商家消息节点下拉列表VO</p>
 * @author 马连峰
 * @date 2022-07-11 09:41:21
 */
@Schema
@Data
public class StoreMessageNodeSelectVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Schema(description = "主键")
	private Long id;

	/**
	 * 节点类型名称
	 */
	@Schema(description = "节点类型名称")
	private String typeName;

	/**
	 * 节点标识
	 */
	@Schema(description = "节点标识")
	private String nodeCode;
}