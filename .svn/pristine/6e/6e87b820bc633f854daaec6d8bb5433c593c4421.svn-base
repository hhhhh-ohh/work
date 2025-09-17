package com.wanmi.sbc.message.api.request.pushsend;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.message.bean.enums.PushFlag;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Max;

import lombok.*;

import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>推送信息修改参数</p>
 * @author Bob
 * @date 2020-01-08 17:15:32
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PushSendModifyRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@Schema(description = "id")
	@Max(9223372036854775807L)
	private Long id;

	/**
	 * 友盟安卓任务ID
	 */
	@Schema(description = "友盟安卓任务ID")
	@Length(max=32)
	private String androidTaskId;

	/**
	 * 友盟iOS任务ID
	 */
	@Schema(description = "友盟iOS任务ID")
	@Length(max=32)
	private String iosTaskId;

	/**
	 * 消息名称
	 */
	@Schema(description = "消息名称")
	@Length(max=32)
	private String msgName;

	/**
	 * 消息标题
	 */
	@Schema(description = "消息标题")
	@Length(max=64)
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
	@Max(127)
	private Integer msgRecipient;

	/**
	 * 等级、标签、人群ID。逗号分割
	 */
	@Schema(description = "等级、标签、人群ID。逗号分割")
	private String msgRecipientDetail;

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
	@Max(9999999999L)
	private Integer expectedSendCount;

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

	@Schema(description = "消息接收人", hidden = true)
	private List<String> customers;

	@Schema(description = "消息标签")
	private PushFlag pushFlag;

}