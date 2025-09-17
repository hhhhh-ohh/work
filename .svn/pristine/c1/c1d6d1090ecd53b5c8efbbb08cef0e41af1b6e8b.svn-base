package com.wanmi.sbc.customer.api.request.ledgerrecord;

import com.wanmi.sbc.common.base.BaseQueryRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 * <p>分账开通记录分页查询请求参数</p>
 * @author 许云鹏
 * @date 2022-07-01 16:36:53
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LedgerRecordPageRequest extends BaseQueryRequest {
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
	 * 分账审核状态 0、未开通 1、审核中 2、审核成功 3、审核失败
	 */
	@Schema(description = "分账审核状态 0、未开通 1、审核中 2、审核成功 3、审核失败")
	private Integer ledgerState;

	/**
	 * B2b网银新增状态：0、未新增 1、审核中 2、审核成功 3、审核失败
	 */
	@Schema(description = "B2b网银新增状态：0、未新增 1、审核中 2、审核成功 3、审核失败")
	private Integer b2bAddState;

}