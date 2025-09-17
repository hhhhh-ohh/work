package com.wanmi.sbc.customer.bean.vo;

import lombok.Data;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>商户分账绑定数据VO</p>
 * @author 许云鹏
 * @date 2022-07-01 15:56:20
 */
@Schema
@Data
public class LedgerSupplierVO implements Serializable {
	private static final long serialVersionUID = 1L;

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