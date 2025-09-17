package com.wanmi.sbc.customer.api.request.ledgerreceiverrel;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import java.time.LocalDateTime;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.base.BaseRequest;
import lombok.*;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>分账绑定关系列表查询请求参数</p>
 * @author 许云鹏
 * @date 2022-07-01 16:24:24
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LedgerReceiverRelListRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 批量查询-idList
	 */
	@Schema(description = "批量查询-idList")
	private List<String> idList;


	/**
	 * 商户分账数据idList
	 */
	@Schema(description = "商户idList")
	private List<Long> supplierIdList;

	/**
	 * id
	 */
	@Schema(description = "id")
	private String id;

	/**
	 * 商户分账数据id
	 */
	@Schema(description = "商户分账数据id")
	private String ledgerSupplierId;

	/**
	 * 商户id
	 */
	@Schema(description = "商户id")
	private Long supplierId;

	/**
	 * 接收方id
	 */
	@Schema(description = "接收方id")
	private String receiverId;

	/**
	 * 接收方名称
	 */
	@Schema(description = "接收方名称")
	private String receiverName;

	/**
	 * 接收方编码(供应商编码或分销员账号)
	 */
	@Schema(description = "接收方编码(供应商编码或分销员账号)")
	private String receiverCode;

	/**
	 * 接收方类型 0、平台 1、供应商 2、分销员
	 */
	@Schema(description = "接收方类型 0、平台 1、供应商 2、分销员")
	private Integer receiverType;

	/**
	 * 开户审核状态 0、未进件 1、审核中 2、审核成功 3、审核失败
	 */
	@Schema(description = "开户审核状态 0、未进件 1、审核中 2、审核成功 3、审核失败")
	private Integer accountState;

	/**
	 * 绑定状态 0、未绑定 1、绑定中 2、已绑定 3、绑定失败
	 */
	@Schema(description = "绑定状态 0、未绑定 1、绑定中 2、已绑定 3、绑定失败")
	private Integer bindState;

	/**
	 * 搜索条件:绑定时间开始
	 */
	@Schema(description = "搜索条件:绑定时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime bindTimeBegin;
	/**
	 * 搜索条件:绑定时间截止
	 */
	@Schema(description = "搜索条件:绑定时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime bindTimeEnd;

	/**
	 * 外部绑定受理编号
	 */
	@Schema(description = "外部绑定受理编号")
	private String applyId;

	/**
	 * 删除标识 0、未删除 1、已删除
	 */
	@Schema(description = "删除标识 0、未删除 1、已删除")
	private DeleteFlag delFlag;

}