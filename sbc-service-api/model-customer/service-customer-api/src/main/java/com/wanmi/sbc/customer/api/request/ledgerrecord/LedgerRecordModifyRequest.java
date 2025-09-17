package com.wanmi.sbc.customer.api.request.ledgerrecord;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.*;

import lombok.*;

import org.hibernate.validator.constraints.*;

import java.time.LocalDateTime;

/**
 * <p>分账开通记录修改参数</p>
 * @author 许云鹏
 * @date 2022-07-01 16:36:53
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LedgerRecordModifyRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@Schema(description = "id")
	@Length(max=32)
	private String id;

	/**
	 * 商户id
	 */
	@Schema(description = "商户id")
	@NotNull
	@Max(9223372036854775807L)
	private Long supplierId;

	/**
	 * 商家名称
	 */
	@Schema(description = "商家名称")
	@Length(max=50)
	private String supplierName;

	/**
	 * 商家编号
	 */
	@Schema(description = "商家编号")
	@Length(max=32)
	private String supplierCode;

	/**
	 * 开户审核状态 0、未进件 1、审核中 2、审核成功 3、审核失败
	 */
	@Schema(description = "开户审核状态 0、未进件 1、审核中 2、审核成功 3、审核失败")
	@Max(127)
	private Integer accountState;

	/**
	 * 开户时间
	 */
	@Schema(description = "开户时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime accountOpenTime;

	/**
	 * 分账审核状态 0、未开通 1、审核中 2、审核成功 3、审核失败
	 */
	@Schema(description = "分账审核状态 0、未开通 1、审核中 2、审核成功 3、审核失败")
	@Max(127)
	private Integer ledgerState;

	/**
	 * 分账开通时间
	 */
	@Schema(description = "分账开通时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime ledgerOpenTime;

	/**
	 * B2b网银新增状态：0、未新增 1、审核中 2、审核成功 3、审核失败
	 */
	@Schema(description = "B2b网银新增状态：0、未新增 1、审核中 2、审核成功 3、审核失败")
	private Integer b2bAddState;
}
