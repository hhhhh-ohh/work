package com.wanmi.sbc.message.api.request.smstemplate;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import java.time.LocalDateTime;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.message.bean.enums.ReviewStatus;
import com.wanmi.sbc.message.bean.enums.SmsType;
import lombok.*;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>短信模板分页查询请求参数</p>
 * @author lvzhenwei
 * @date 2019-12-03 15:43:29
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SmsTemplatePageRequest extends BaseQueryRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 批量查询-主键List
	 */
	@Schema(description = "批量查询-主键List")
	private List<Long> idList;

	/**
	 * 主键
	 */
	@Schema(description = "主键")
	private Long id;

	/**
	 * 模板名称
	 */
	@Schema(description = "模板名称")
	private String templateName;

	/**
	 * 模板内容
	 */
	@Schema(description = "模板内容")
	private String templateContent;

	/**
	 * 短信模板申请说明
	 */
	@Schema(description = "短信模板申请说明")
	private String remark;

	/**
	 * 短信类型。其中： 0：验证码。 1：短信通知。 2：推广短信。 短信类型,0：验证码,1：短信通知,2：推广短信,3：国际/港澳台消息。
	 */
	@Schema(description = "短信类型。其中： 0：验证码。 1：短信通知。 2：推广短信。 短信类型,0：验证码,1：短信通知,2：推广短信,3：国际/港澳台消息。")
	private SmsType templateType;

	/**
	 * 模板状态，0：待审核，1：审核通过，2：审核未通过
	 */
	@Schema(description = "模板状态，0：待审核，1：审核通过，2：审核未通过")
	private ReviewStatus reviewStatus;

	/**
	 * 模板code,模板审核通过返回的模板code，发送短信时使用
	 */
	@Schema(description = "模板code,模板审核通过返回的模板code，发送短信时使用")
	private String templateCode;

	/**
	 * 审核原因
	 */
	@Schema(description = "审核原因")
	private String reviewReason;

	/**
	 * 短信配置id
	 */
	@Schema(description = "短信配置id")
	private Long smsSettingId;

	/**
	 * 删除标识位，0：未删除，1：已删除
	 */
	@Schema(description = "删除标识位，0：未删除，1：已删除")
	private DeleteFlag delFlag;

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

}