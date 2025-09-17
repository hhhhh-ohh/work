package com.wanmi.sbc.setting.bean.vo;

import com.wanmi.sbc.common.enums.storemessage.StoreMessagePlatform;
import lombok.Data;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>商家消息节点VO</p>
 * @author 马连峰
 * @date 2022-07-11 09:41:21
 */
@Schema
@Data
public class StoreMessageNodeVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Schema(description = "主键")
	private Long id;

	/**
	 * 所属菜单名称
	 */
	@Schema(description = "所属菜单名称")
	private String menuName;

	/**
	 * 节点类型名称
	 */
	@Schema(description = "节点类型名称")
	private String typeName;

	/**
	 * 推送节点名称
	 */
	@Schema(description = "推送节点名称")
	private String pushName;

	/**
	 * 功能标识，用于鉴权
	 */
	@Schema(description = "功能标识，用于鉴权")
	private String functionName;

	/**
	 * 节点标识
	 */
	@Schema(description = "节点标识")
	private String nodeCode;

	/**
	 * 节点通知内容模板
	 */
	@Schema(description = "节点通知内容模板")
	private String nodeContext;

	/**
	 * 平台类型 0:平台 1:商家 2:供应商
	 */
	@Schema(description = "平台类型 0:平台 1:商家 2:供应商")
	private StoreMessagePlatform platformType;

	/**
	 * 排序字段
	 */
	@Schema(description = "排序字段")
	private Integer sort;

	/**
	 * 跨行数，用于前端渲染
	 */
	@Schema(description = "排序字段")
	private Integer rowSpan;

}