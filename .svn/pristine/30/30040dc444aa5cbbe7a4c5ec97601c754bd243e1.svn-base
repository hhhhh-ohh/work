package com.wanmi.sbc.customer.api.request.ledgersupplier;

import com.wanmi.sbc.common.base.BaseQueryRequest;
import lombok.*;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>商户分账绑定数据通用查询请求参数</p>
 * @author 许云鹏
 * @date 2022-07-01 15:56:20
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LedgerSupplierQueryRequest extends BaseQueryRequest {
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
	private String ledgerAccountId;

	/**
	 * 商家id
	 */
	@Schema(description = "商家id")
	private Long companyInfoId;

	/**
	 * 商家名称
	 */
	@Schema(description = "商家名称")
	private String companyName;

	/**
	 * 商家编号
	 */
	@Schema(description = "商家编号")
	private String companyCode;

	/**
	 * 平台绑定状态 0、未绑定 1、已绑定
	 */
	@Schema(description = "平台绑定状态 0、未绑定 1、已绑定")
	private Integer platBindState;

	/**
	 * 供应商绑定数
	 */
	@Schema(description = "供应商绑定数")
	private Long providerNum;

	/**
	 * 分销员绑定数
	 */
	@Schema(description = "分销员绑定数")
	private Long distributionNum;

}