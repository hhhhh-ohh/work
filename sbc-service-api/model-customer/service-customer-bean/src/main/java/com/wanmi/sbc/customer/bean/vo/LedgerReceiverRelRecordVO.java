package com.wanmi.sbc.customer.bean.vo;

import lombok.Data;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>分账绑定关系补偿记录VO</p>
 * @author xuyunpeng
 * @date 2022-07-14 15:15:45
 */
@Schema
@Data
public class LedgerReceiverRelRecordVO implements Serializable {
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
	private String accountId;

	/**
	 * 账户类型 0、商户 1、接收方
	 */
	@Schema(description = "账户类型 0、商户 1、接收方")
	private Integer businessType;

}