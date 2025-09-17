package com.wanmi.sbc.setting.bean.vo;

import com.wanmi.sbc.common.enums.BoolFlag;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>商家消息节点设置VO</p>
 * @author 马连峰
 * @date 2022-07-11 09:42:56
 */
@Schema
@Data
public class StoreMessageNodeSettingVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Schema(description = "主键")
	private Long id;

	/**
	 * 商家id
	 */
	@Schema(description = "商家id")
	private Long storeId;

	/**
	 * 消息节点id
	 */
	@Schema(description = "消息节点id")
	private Long nodeId;

	/**
	 * 消息节点标识
	 */
	@Schema(description = "消息节点标识")
	private String nodeCode;

	/**
	 * 启用状态 0:未启用 1:启用
	 */
	@Schema(description = "启用状态 0:未启用 1:启用")
	private BoolFlag status;

	/**
	 * 库存预警值
	 */
	@Schema(description = "库存预警值")
	@Min(1)
	@Max(99999)
	private Long warningStock;

}