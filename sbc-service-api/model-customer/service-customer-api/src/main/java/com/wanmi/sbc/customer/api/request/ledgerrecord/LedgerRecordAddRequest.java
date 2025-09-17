package com.wanmi.sbc.customer.api.request.ledgerrecord;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.*;

import lombok.*;

import org.hibernate.validator.constraints.*;

/**
 * <p>分账开通记录新增参数</p>
 * @author 许云鹏
 * @date 2022-07-01 16:36:53
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LedgerRecordAddRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 商户id
	 */
	@Schema(description = "商户id")
	@NotNull
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
	 * 操作人
	 */
	@Schema(description = "操作人")
	private String operator;

}