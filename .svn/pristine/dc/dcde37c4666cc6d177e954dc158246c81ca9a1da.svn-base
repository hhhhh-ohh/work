package com.wanmi.sbc.message.api.request.storemessagedetail;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.message.bean.enums.ReadFlag;
import com.wanmi.sbc.message.bean.enums.StoreMessageType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>商家消息/公告分页查询请求参数</p>
 * @author 马连峰
 * @date 2022-07-05 10:52:24
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreMessageDetailPageRequest extends BaseQueryRequest {

	private static final long serialVersionUID = 1L;

	/**
	 * 批量查询-主键idList
	 */
	@Schema(description = "批量查询-主键idList")
	private List<String> idList;

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
	@NotNull
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
	 * 搜索条件:发送时间开始
	 */
	@Schema(description = "搜索条件:发送时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime sendTimeBegin;
	/**
	 * 搜索条件:发送时间截止
	 */
	@Schema(description = "搜索条件:发送时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime sendTimeEnd;

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

	/**
	 * 关联的消息节点id或公告id列表
	 */
	@Schema(description = "关联的消息节点id或公告id列表")
	private List<Long> joinIdList;

	/**
	 * 搜索条件:创建时间开始
	 */
	@Schema(description = "搜索条件:创建时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTimeBegin;
	/**
	 * 搜索条件:创建时间截止
	 */
	@Schema(description = "搜索条件:创建时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTimeEnd;

	/**
	 * 创建人
	 */
	@Schema(description = "创建人")
	private String createPerson;

	/**
	 * 搜索条件:修改时间开始
	 */
	@Schema(description = "搜索条件:修改时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTimeBegin;
	/**
	 * 搜索条件:修改时间截止
	 */
	@Schema(description = "搜索条件:修改时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTimeEnd;

	/**
	 * 修改人
	 */
	@Schema(description = "修改人")
	private String updatePerson;

	/**
	 * 删除标识 0：未删除 1：删除
	 */
	@Schema(description = "删除标识 0：未删除 1：删除")
	private DeleteFlag delFlag;

}