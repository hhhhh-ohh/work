package com.wanmi.sbc.message.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.message.bean.enums.PushPlatform;
import com.wanmi.sbc.message.bean.enums.PushStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>推送详情VO</p>
 * @author Bob
 * @date 2020-01-08 17:16:17
 */
@Schema
@Data
public class PushDetailVO extends BasicResponse {
	private static final long serialVersionUID = 1L;

	/**
	 * 友盟推送任务ID
	 */
	@Schema(description = "友盟推送任务ID")
	private String taskId;

	@Schema(description = "taskId对应的平台 0：iOS 1：android")
	private PushPlatform platform;

	/**
	 * 节点ID
	 */
	@Schema(description = "节点ID")
	private Long nodeId;

	/**
	 * 实际发送
	 */
	@Schema(description = "实际发送")
	private Integer sendSum;

	/**
	 * 打开数
	 */
	@Schema(description = "打开数")
	private Integer openSum;

	/**
	 * 发送状态 0-排队中, 1-发送中，2-发送完成，3-发送失败，4-消息被撤销
	 */
	@Schema(description = "发送状态 0-排队中, 1-发送中，2-发送完成，3-发送失败，4-消息被撤销")
	private PushStatus sendStatus;

	/**
	 * 失败信息
	 */
	@Schema(description = "失败信息")
	private String failRemark;

	@Schema(description = "运营计划ID")
	private Long planId;

}