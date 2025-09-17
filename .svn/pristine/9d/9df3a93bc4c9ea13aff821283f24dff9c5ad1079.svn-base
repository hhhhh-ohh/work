package com.wanmi.sbc.message.api.request.storemessagedetail;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.message.bean.enums.ReadFlag;
import com.wanmi.sbc.message.bean.enums.StoreMessageType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.*;

import lombok.*;

import org.hibernate.validator.constraints.*;

import java.time.LocalDateTime;

/**
 * <p>商家消息/公告新增参数</p>
 * @author 马连峰
 * @date 2022-07-05 10:52:24
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreMessageDetailAddRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 消息一级类型 0：消息 1：公告
	 */
	@Schema(description = "消息一级类型 0：消息 1：公告")
	private StoreMessageType messageType;

	/**
	 * 商家id
	 */
	@Schema(description = "商家id")
	@Max(9999999999L)
	private Long storeId;

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
	@Length(max=5000)
	private String content;

	/**
	 * 路由参数，json格式
	 */
	@Schema(description = "路由参数，json格式")
	@Length(max=255)
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
	@Max(127)
	private ReadFlag isRead;

	/**
	 * 关联的消息节点id或公告id
	 */
	@Schema(description = "关联的消息节点id或公告id")
	@Max(9223372036854775807L)
	private Long joinId;

	/**
	 * 创建人
	 */
	@Schema(description = "创建人", hidden = true)
	private String createPerson;

	/**
	 * 修改人
	 */
	@Schema(description = "修改人", hidden = true)
	private String updatePerson;

	/**
	 * 删除标识 0：未删除 1：删除
	 */
	@Schema(description = "删除标识 0：未删除 1：删除", hidden = true)
	private DeleteFlag delFlag;

}