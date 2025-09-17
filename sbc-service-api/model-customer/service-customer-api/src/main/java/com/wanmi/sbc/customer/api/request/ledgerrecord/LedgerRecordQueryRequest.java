package com.wanmi.sbc.customer.api.request.ledgerrecord;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import java.time.LocalDateTime;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import lombok.*;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>分账开通记录通用查询请求参数</p>
 * @author 许云鹏
 * @date 2022-07-01 16:36:53
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LedgerRecordQueryRequest extends BaseQueryRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 批量查询-idList
	 */
	@Schema(description = "批量查询-idList")
	private List<String> idList;

	/**
	 * id
	 */
	@Schema(description = "id")
	private String id;

	/**
	 * 商户id
	 */
	@Schema(description = "商户id")
	private Long supplierId;

	/**
	 * 商家名称
	 */
	@Schema(description = "商家名称")
	private String supplierName;

	/**
	 * 商家编号
	 */
	@Schema(description = "商家编号")
	private String supplierCode;

	/**
	 * 开户审核状态 0、未进件 1、审核中 2、审核成功 3、审核失败
	 */
	@Schema(description = "开户审核状态 0、未进件 1、审核中 2、审核成功 3、审核失败")
	private Integer accountState;

	/**
	 * 搜索条件:开户时间开始
	 */
	@Schema(description = "搜索条件:开户时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime accountOpenTimeBegin;
	/**
	 * 搜索条件:开户时间截止
	 */
	@Schema(description = "搜索条件:开户时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime accountOpenTimeEnd;

	/**
	 * 分账审核状态 0、未开通 1、审核中 2、审核成功 3、审核失败
	 */
	@Schema(description = "分账审核状态 0、未开通 1、审核中 2、审核成功 3、审核失败")
	private Integer ledgerState;

	/**
	 * 搜索条件:分账开通时间开始
	 */
	@Schema(description = "搜索条件:分账开通时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime ledgerOpenTimeBegin;
	/**
	 * 搜索条件:分账开通时间截止
	 */
	@Schema(description = "搜索条件:分账开通时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime ledgerOpenTimeEnd;

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

}