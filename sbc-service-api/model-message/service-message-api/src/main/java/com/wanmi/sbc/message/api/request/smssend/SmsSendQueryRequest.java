package com.wanmi.sbc.message.api.request.smssend;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.message.bean.enums.ReceiveType;
import com.wanmi.sbc.message.bean.enums.ResendType;
import com.wanmi.sbc.message.bean.enums.SendStatus;
import com.wanmi.sbc.message.bean.enums.SendType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>短信发送通用查询请求参数</p>
 * @author zgl
 * @date 2019-12-03 15:36:05
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SmsSendQueryRequest extends BaseQueryRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 批量查询-idList
	 */
	@Schema(description = "批量查询-idList")
	private List<Long> idList;

	/**
	 * id
	 */
	@Schema(description = "id")
	private Long id;

	/**
	 * 短信内容
	 */
	@Schema(description = "短信内容")
	private String context;

	@Schema(description = "平台商家id")
	private Long smsSettingId;
	/**
	 * 模板code
	 */
	@Schema(description = "模板Code")
	private String templateCode;

	/**
	 * 签名id
	 */
	@Schema(description = "签名id")
	private Long signId;

	/**
	 * 接收人描述
	 */
	@Schema(description = "接收人描述")
	private String receiveContext;

	/**
	 * 接收类型（0-全部，1-会员等级，2-会员人群，3-自定义）
	 */
	@Schema(description = "接收类型（0-全部，1-会员等级，2-会员人群，3-自定义）")
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
	private SendStatus status;

    /**
     * 非当前状态（0-未开始，1-进行中，2-已结束，3-任务失败）
     */
    @Schema(description = "非当前状态（0-未开始，1-进行中，2-已结束，3-任务失败）")
    private SendStatus noStatus;

	/**
	 * 任务执行信息
	 */
	@Schema(description = "任务执行信息")
	private String message;

	/**
	 * 发送类型（0-立即发送，1-定时发送）
	 */
	@Schema(description = "发送类型（0-立即发送，1-定时发送）")
	private SendType sendType;

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
	 * 预计发送条数
	 */
	@Schema(description = "预计发送条数")
	private Integer rowCount;

	/**
	 * 创建人
	 */
	@Schema(description = "创建人")
	private String createPerson;

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
	 * 更新人
	 */
	@Schema(description = "更新人")
	private String updatePerson;

	/**
	 * 搜索条件:更新时间开始
	 */
	@Schema(description = "搜索条件:更新时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTimeBegin;
	/**
	 * 搜索条件:更新时间截止
	 */
	@Schema(description = "搜索条件:更新时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTimeEnd;

	@Schema(description = "重发类型（0-不可重发，1-可重发）")
	private ResendType resendType;

}