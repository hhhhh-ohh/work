package com.wanmi.sbc.customer.api.request.ledgerreceiverrel;

import com.wanmi.sbc.common.base.BaseQueryRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 * <p>分账绑定关系补偿记录通用查询请求参数</p>
 * @author xuyunpeng
 * @date 2022-07-14 15:15:45
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LedgerReceiverRelRecordQueryRequest extends BaseQueryRequest {
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
	 * 清分账户id
	 */
	@Schema(description = "清分账户id")
	private String accountId;

	/**
	 * 账户类型 0、商户 1、接收方
	 */
	@Schema(description = "账户类型 0、商户 1、接收方")
	private Integer businessType;

}