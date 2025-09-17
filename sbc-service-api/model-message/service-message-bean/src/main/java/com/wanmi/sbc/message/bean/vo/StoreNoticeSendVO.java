package com.wanmi.sbc.message.bean.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import java.time.LocalDateTime;

import com.wanmi.sbc.message.bean.enums.*;
import lombok.Data;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>商家公告VO</p>
 * @author 马连峰
 * @date 2022-07-04 10:56:58
 */
@Schema
@Data
public class StoreNoticeSendVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@Schema(description = "主键id")
	private Long id;

	/**
	 * 公告标题
	 */
	@Schema(description = "公告标题")
	private String title;

	/**
	 * 公告内容
	 */
	@Schema(description = "公告内容")
	private String content;

	/**
	 * 接收范围 0：全部 1：商家 2：供应商
	 */
	@Schema(description = "接收范围 0：全部 1：商家 2：供应商")
	private StoreNoticeReceiveScope receiveScope;

	/**
	 * 商家范围 0：全部 1：自定义商家
	 */
	@Schema(description = "商家范围 0：全部 1：自定义商家")
	private StoreNoticeTargetScope supplierScope;

	/**
	 * 商家范围目标ids
	 */
	@Schema(description = "商家范围ids")
	private List<Long> supplierScopeIds = new ArrayList<>();

	/**
	 * 供应商范围 0：全部 1：自定义供应商
	 */
	@Schema(description = "供应商范围 0：全部 1：自定义供应商")
	private StoreNoticeTargetScope providerScope;

	/**
	 * 供应商范围目标ids
	 */
	@Schema(description = "供应商范围ids")
	private List<Long> providerScopeIds = new ArrayList<>();

	/**
	 * 发送时间类型 0：立即 1：定时
	 */
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
	 * 公告发送状态 0：未发送 1：发送中 2：已发送 3：发送失败 4：已撤回
	 */
	@Schema(description = "公告发送状态 0：未发送 1：发送中 2：已发送 3：发送失败 4：已撤回")
	private StoreNoticeSendStatus sendStatus;

	/**
	 * 定时任务扫描标识 0：未扫面 1：已扫描
	 */
	@Schema(description = "定时任务扫描标识 0：未扫面 1：已扫描")
	private BoolFlag scanFlag;

	/**
	 * 创建人
	 */
	@Schema(description = "创建人")
	private String createPerson;

}