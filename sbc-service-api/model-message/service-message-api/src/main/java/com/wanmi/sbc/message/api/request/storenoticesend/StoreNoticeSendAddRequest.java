package com.wanmi.sbc.message.api.request.storenoticesend;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.message.bean.enums.SendType;
import com.wanmi.sbc.message.bean.enums.StoreNoticeReceiveScope;
import com.wanmi.sbc.message.bean.enums.StoreNoticeTargetScope;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>商家公告新增参数</p>
 * @author 马连峰
 * @date 2022-07-04 10:56:58
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoreNoticeSendAddRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 公告标题
	 */
	@Schema(description = "公告标题")
	@Length(max=40)
	@NotBlank
	private String title;

	/**
	 * 公告内容
	 */
	@Schema(description = "公告内容")
	@Length(max=100000)
	@NotBlank
	private String content;

	/**
	 * 接收范围 0：全部 1：商家 2：供应商
	 */
	@Schema(description = "接收范围 0：全部 1：商家 2：供应商")
	@NotNull
	private StoreNoticeReceiveScope receiveScope;

	/**
	 * 商家范围 0：全部 1：自定义商家
	 */
	@Schema(description = "商家范围 0：全部 1：自定义商家")
	private StoreNoticeTargetScope supplierScope;

	/**
	 * 商家范围目标ids
	 */
	@Schema(description = "商家范围目标ids")
	private List<Long> supplierScopeIds;

	/**
	 * 供应商范围 0：全部 1：自定义供应商
	 */
	@Schema(description = "供应商范围 0：全部 1：自定义供应商")
	private StoreNoticeTargetScope providerScope;

	/**
	 * 供应商范围目标ids
	 */
	@Schema(description = "供应商范围目标ids")
	private List<Long> providerScopeIds;

	/**
	 * 发送时间类型 0：立即 1：定时
	 */
	@NotNull
	@Schema(description = "推送时间类型 0：立即、1：定时")
	private SendType sendTimeType;

	/**
	 * 发送时间
	 */
	@Schema(description = "发送时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime sendTime;

	/**
	 * createPerson
	 */
	@Schema(description = "createPerson", hidden = true)
	private String createPerson;

	/**
	 * 任务扫描间隔,单位分钟
	 */
	@Schema(description = "任务扫描间隔,单位分钟")
	private int withinTime;

}