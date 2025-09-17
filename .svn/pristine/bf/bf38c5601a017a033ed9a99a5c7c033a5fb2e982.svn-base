package com.wanmi.sbc.message.bean.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.message.bean.enums.MessageType;
import com.wanmi.sbc.message.bean.enums.NoticeType;
import com.wanmi.sbc.message.bean.enums.ReadFlag;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * <p>App站内信消息发送表VO</p>
 * @author xuyunpeng
 * @date 2020-01-06 10:53:00
 */
@Schema
@Data
public class AppMessageVO extends BasicResponse {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@Schema(description = "主键id")
	private String appMessageId;

	/**
	 * 消息一级类型：0优惠促销、1服务通知
	 */
	@Schema(description = "消息一级类型：0优惠促销、1服务通知")
	private MessageType messageType;

	/**
	 * 消息二级类型（目前只有服务通知有分类）
	 */
	@Schema(description = "消息二级类型")
	private NoticeType messageTypeDetail;

	/**
	 * 封面图
	 */
	@Schema(description = "封面图")
	private String imgUrl;

	/**
	 * 消息标题
	 */
	@Schema(description = "消息标题")
	private String title;

	/**
	 * 消息内容
	 */
	@Schema(description = "消息内容")
	private String content;

	/**
	 * 跳转路由
	 */
	@Schema(description = "跳转路由")
	private String routeName;

	/**
	 * 移动端路由参数
	 */
	@Schema(description = "移动端路由参数")
	private String routeParam;

	/**
	 * PC端路由参数
	 */
	@Schema(description = "PC端路由参数")
	private String pcRouteParam;

	/**
	 * 发送时间
	 */
	@Schema(description = "发送时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime sendTime;

	/**
	 * 是否已读 0：未读、1：已读
	 */
	@Schema(description = "是否已读 0：未读、1：已读")
	private ReadFlag isRead;

	/**
	 * 会员id
	 */
	@Schema(description = "会员id")
	private String customerId;

	/**
	 * 关联的任务或节点id
	 */
	@Schema(description = "关联的任务或节点id")
	private Long joinId;

	/**
	 * 周期购订单消息标识
	 */
	@Schema(description = "周期购订单消息标识")
	private Boolean buyCycleOrderFlag;

}