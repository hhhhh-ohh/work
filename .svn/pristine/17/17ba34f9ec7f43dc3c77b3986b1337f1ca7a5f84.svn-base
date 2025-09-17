package com.wanmi.sbc.message.api.request.smssend;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.message.api.request.SmsBaseRequest;
import com.wanmi.sbc.message.bean.enums.ReceiveType;
import com.wanmi.sbc.message.bean.enums.ResendType;
import com.wanmi.sbc.message.bean.enums.SendType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.*;

import lombok.*;

import org.hibernate.validator.constraints.*;

import java.time.LocalDateTime;

/**
 * <p>短信发送新增参数</p>
 * @author zgl
 * @date 2019-12-03 15:36:05
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SmsSendAddRequest extends SmsBaseRequest {
	private static final long serialVersionUID = 1L;


	private Long smsSettingId;
	/**
	 * 短信内容
	 */
	@Schema(description = "短信内容")
	@Length(max=1024)
	private String context;

	/**
	 * 模板code
	 */
	@Schema(description = "模板code")
	@NotNull
	private String templateCode;

	/**
	 * 签名id
	 */
	@Schema(description = "签名id")
	@Max(9223372036854775807L)
	@NotNull
	private Long signId;

	/**
	 * 接收人描述
	 */
	@Schema(description = "接收人描述")
	@Length(max=2000)
	private String receiveContext;

	/**
	 * 接收类型（0-全部，1-会员等级，2-会员人群，3-自定义）
	 */
	@Schema(description = "接收类型（0-全部，1-会员等级，2-会员人群，3-自定义）")
	@NotNull
    private ReceiveType receiveType;

	/**
	 * 接收人明细
	 */
	@Schema(description = "接收人明细")
	private String receiveValue;

	/**
	 * 手工添加的号码
	 */
	@Schema(description = "手工添加的号码")
	private String manualAdd;

	/**
	 * 状态（0-未开始，1-进行中，2-已结束，3-任务失败）
	 */
	@Schema(description = "状态（0-未开始，1-进行中，2-已结束，3-任务失败）")
	private Integer status;

	/**
	 * 任务执行信息
	 */
	@Schema(description = "任务执行信息")
	@Length(max=2000)
	private String message;

	/**
	 * 发送类型（0-立即发送，1-定时发送）
	 */
	@Schema(description = "发送类型（0-立即发送，1-定时发送）")
	@NotNull
	private SendType sendType;

	/**
	 * 发送时间
	 */
	@Schema(description = "发送时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime sendTime;

	/**
	 * 预计发送条数
	 */
	@Schema(description = "预计发送条数")
	@Max(9999999999L)
	private Integer rowCount;

	/**
	 * 创建人
	 */
	@Schema(description = "创建人")
	@Length(max=32)
	private String createPerson;

	/**
	 * 创建时间
	 */
	@Schema(description = "创建时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTime;

	/**
	 * 更新人
	 */
	@Schema(description = "更新人")
	@Length(max=32)
	private String updatePerson;

	/**
	 * 更新时间
	 */
	@Schema(description = "更新时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTime;

	@Schema(description = "重发类型（0-不可重发，1-可重发）")
	private ResendType resendType=ResendType.NO;

	/**
	 * 发送明细条数
	 */
	@Schema(description = "发送明细条数")
	private Integer sendDetailCount;

}