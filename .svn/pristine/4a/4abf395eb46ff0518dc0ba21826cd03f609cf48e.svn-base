package com.wanmi.sbc.message.api.request.pushdetail;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.message.bean.enums.PushPlatform;
import com.wanmi.sbc.message.bean.enums.PushStatus;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Max;

import lombok.*;

import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

/**
 * <p>推送详情修改参数</p>
 * @author Bob
 * @date 2020-01-08 17:16:17
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PushDetailModifyRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 友盟推送任务ID
	 */
	@Schema(description = "友盟推送任务ID")
	@Length(max=32)
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
	@Max(9999999999L)
	private Integer sendSum;

	/**
	 * 打开数
	 */
	@Schema(description = "打开数")
	@Max(9999999999L)
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
	@Length(max=128)
	private String failRemark;

	/**
	 * 创建时间
	 */
	@Schema(description = "创建时间", hidden = true)
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTime;

	/**
	 * 更新人ID
	 */
	@Schema(description = "更新人ID", hidden = true)
	private String updatePerson;

}