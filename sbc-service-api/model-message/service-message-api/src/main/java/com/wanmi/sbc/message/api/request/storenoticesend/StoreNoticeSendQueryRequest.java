package com.wanmi.sbc.message.api.request.storenoticesend;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import java.time.LocalDateTime;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.message.bean.enums.SendType;
import com.wanmi.sbc.message.bean.enums.StoreNoticeReceiveScope;
import com.wanmi.sbc.message.bean.enums.StoreNoticeSendStatus;
import com.wanmi.sbc.message.bean.enums.StoreNoticeTargetScope;
import lombok.*;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>商家公告通用查询请求参数</p>
 * @author 马连峰
 * @date 2022-07-04 10:56:58
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreNoticeSendQueryRequest extends BaseQueryRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 批量查询-主键idList
	 */
	@Schema(description = "批量查询-主键idList")
	private List<Long> idList;

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
	 * 发送状态
	 */
	@Schema(description = "发送状态")
	private StoreNoticeSendStatus sendStatus;

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
	 * 供应商范围 0：全部 1：自定义供应商
	 */
	@Schema(description = "供应商范围 0：全部 1：自定义供应商")
	private StoreNoticeTargetScope providerScope;

	/**
	 * 推送时间类型 0：立即、1：定时
	 */
	@Schema(description = "推送时间类型 0：立即、1：定时")
	private SendType sendTimeType;

	/**
	 * 定时任务扫描标识 0：未扫面 1：已扫描
	 */
	@Schema(description = "定时任务扫描标识 0：未扫面 1：已扫描")
	private BoolFlag scanFlag;

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
	 * createPerson
	 */
	@Schema(description = "createPerson")
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
	 * updatePerson
	 */
	@Schema(description = "updatePerson")
	private String updatePerson;

	/**
	 * 删除标识 0：未删除 1：删除
	 */
	@Schema(description = "删除标识 0：未删除 1：删除")
	private DeleteFlag delFlag;

}