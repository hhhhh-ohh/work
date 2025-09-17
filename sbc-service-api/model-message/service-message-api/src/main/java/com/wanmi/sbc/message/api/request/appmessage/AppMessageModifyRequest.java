package com.wanmi.sbc.message.api.request.appmessage;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.*;

import lombok.*;

import org.hibernate.validator.constraints.*;

import java.time.LocalDateTime;

/**
 * <p>App站内信消息发送表修改参数</p>
 * @author xuyunpeng
 * @date 2020-01-06 10:53:00
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppMessageModifyRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@Schema(description = "主键id")
	@Length(max=32)
	private String appMessageId;

	/**
	 * 消息一级类型：0优惠促销、1服务通知
	 */
	@Schema(description = "消息一级类型：0优惠促销、1服务通知")
	@Max(9999999999L)
	private Integer messageType;

	/**
	 * 消息二级类型
	 */
	@Schema(description = "消息二级类型")
	@Max(9999999999L)
	private Integer messageTypeDetail;

	/**
	 * 封面图
	 */
	@Schema(description = "封面图")
	@Length(max=255)
	private String imgUrl;

	/**
	 * 消息标题
	 */
	@Schema(description = "消息标题")
	@Length(max=40)
	private String title;

	/**
	 * 消息内容
	 */
	@Schema(description = "消息内容")
	@Length(max=255)
	private String content;

	/**
	 * 跳转路由
	 */
	@Schema(description = "跳转路由")
	@Length(max=50)
	private String routeName;

	/**
	 * 移动端路由参数
	 */
	@Schema(description = "移动端路由参数")
	@Length(max=255)
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
	@Max(9999999999L)
	private Integer isRead;

	/**
	 * 创建时间
	 */
	@Schema(description = "创建时间", hidden = true)
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTime;

	/**
	 * 修改人
	 */
	@Schema(description = "修改人", hidden = true)
	private String updatePerson;

	/**
	 * 会员id
	 */
	@Schema(description = "会员id")
	@Length(max=32)
	private String customerId;

	/**
	 * 消息任务id
	 */
	@Schema(description = "消息任务id")
	@Max(9223372036854775807L)
	private Long messageSendId;

	/**
	 * 节点id
	 */
	@Schema(description = "节点id")
	@Max(9223372036854775807L)
	private Long nodeId;

}