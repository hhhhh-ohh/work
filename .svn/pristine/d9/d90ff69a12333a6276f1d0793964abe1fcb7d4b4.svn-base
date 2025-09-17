package com.wanmi.sbc.message.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.message.bean.enums.PushFlag;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>会员推送信息VO</p>
 * @author Bob
 * @date 2020-01-08 17:15:32
 */
@Schema
@Data
public class PushSendVO extends BasicResponse {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@Schema(description = "id")
	private Long id;

	/**
	 * 友盟安卓任务ID
	 */
	@Schema(description = "友盟安卓任务ID")
	private String androidTaskId;

	/**
	 * 友盟iOS任务ID
	 */
	@Schema(description = "友盟iOS任务ID")
	private String iosTaskId;

	/**
	 * 消息名称
	 */
	@Schema(description = "消息名称")
	private String msgName;

	/**
	 * 消息标题
	 */
	@Schema(description = "消息标题")
	private String msgTitle;

	/**
	 * 消息内容
	 */
	@Schema(description = "消息内容")
	private String msgContext;

	/**
	 * 消息封面图片
	 */
	@Schema(description = "消息封面图片")
	private String msgImg;

	/**
	 * 点击消息跳转的页面路由
	 */
	@Schema(description = "点击消息跳转的页面路由")
	private String msgRouter;

	/**
	 * 消息接受人 0:全部会员 1:按会员等级 2:按标签 3:按人群 4:指定会员
	 */
	@Schema(description = "消息接受人 0:全部会员 1:按会员等级 2:按标签 3:按人群 4:指定会员")
	private Integer msgRecipient;

	/**
	 * 等级、标签、人群ID。逗号分割
	 */
	@Schema(description = "等级、标签、人群ID。逗号分割")
	private String msgRecipientDetail;

	@Schema(description = "等级、标签、人群对应名称。逗号分割")
	private List<String> msgRecipientNames;

	private List<String> accountList;

	/**
	 * 推送时间
	 */
	@Schema(description = "推送时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime pushTime;

	/**
	 * 预计发送人数
	 */
	@Schema(description = "预计发送人数")
	private Integer expectedSendCount;

	/**
	 * 创建时间
	 */
	@Schema(description = "创建时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTime;

	@Schema(description = "推送详情")
	private PushDetailVO androidPushDetail;

	@Schema(description = "推送详情")
	private PushDetailVO iosPushDetail;

	@Schema(description = "消息标签")
	private PushFlag pushFlag;

	@Schema(description = "运营计划ID")
	private Long planId;

}