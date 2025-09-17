package com.wanmi.sbc.customer.api.request.ledgerreceiverrel;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.*;

import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

/**
 * <p>分账绑定关系新增参数</p>
 * @author 许云鹏
 * @date 2022-07-01 16:24:24
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LedgerReceiverRelAddRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 商户分账数据id
	 */
	@Schema(description = "商户分账数据id")
	@NotBlank
	@Length(max=32)
	private String ledgerSupplierId;

	/**
	 * 商户id
	 */
	@Schema(description = "商户id")
	@NotNull
	private Long supplierId;

	/**
	 * 接收方id
	 */
	@Schema(description = "接收方id")
	@NotBlank
	@Length(max=50)
	private String receiverId;

	/**
	 * 接收方名称
	 */
	@Schema(description = "接收方名称")
	@Length(max=50)
	private String receiverName;

	/**
	 * 接收方编码(供应商编码或分销员账号)
	 */
	@Schema(description = "接收方编码(供应商编码或分销员账号)")
	@Length(max=32)
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
	 * 绑定时间
	 */
	@Schema(description = "绑定时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime bindTime;

	/**
	 * 外部绑定受理编号
	 */
	@Schema(description = "外部绑定受理编号")
	@Length(max=128)
	private String applyId;

	/**
	 * 删除标识 0、未删除 1、已删除
	 */
	@Schema(description = "删除标识 0、未删除 1、已删除", hidden = true)
	private DeleteFlag delFlag;

}