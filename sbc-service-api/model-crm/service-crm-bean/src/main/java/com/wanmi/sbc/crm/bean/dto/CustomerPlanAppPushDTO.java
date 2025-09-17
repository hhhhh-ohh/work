package com.wanmi.sbc.crm.bean.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>运营计划App通知参数</p>
 * @author dyt
 * @date 2020-01-10 11:14:29
 */
@Schema
@Data
public class CustomerPlanAppPushDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 任务名称
	 */
	@Schema(description = "任务名称")
	private String name;

	/**
	 * 消息标题
	 */
	@Schema(description = "消息标题")
	private String noticeTitle;

	/**
	 * 消息内容
	 */
	@Schema(description = "消息内容")
	private String noticeContext;

	/**
	 * 封面地址
	 */
	@Schema(description = "封面地址")
	private String coverUrl;

	/**
	 * 落页地地址
	 */
	@Schema(description = "落页地地址")
	private String pageUrl;
}