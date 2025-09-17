package com.wanmi.sbc.message.bean.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.message.bean.enums.ReadFlag;
import com.wanmi.sbc.message.bean.enums.StoreMessageType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>商家消息/公告VO</p>
 * @author 马连峰
 * @date 2022-07-05 10:52:24
 */
@Schema
@Data
public class StoreMessageDetailVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@Schema(description = "主键id")
	private String id;

	/**
	 * 消息一级类型 0：消息 1：公告
	 */
	@Schema(description = "消息一级类型 0：消息 1：公告")
	private StoreMessageType messageType;

	/**
	 * 商家id
	 */
	@Schema(description = "商家id")
	private Long storeId;

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
	 * 路由参数，json格式
	 */
	@Schema(description = "路由参数，json格式")
	private String routeParam;

	/**
	 * 发送时间
	 */
	@Schema(description = "发送时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime sendTime;

	/**
	 * 是否已读 0：未读 1：已读
	 */
	@Schema(description = "是否已读 0：未读 1：已读")
	private ReadFlag isRead;

	/**
	 * 关联的消息节点id或公告id
	 */
	@Schema(description = "关联的消息节点id或公告id")
	private Long joinId;

}