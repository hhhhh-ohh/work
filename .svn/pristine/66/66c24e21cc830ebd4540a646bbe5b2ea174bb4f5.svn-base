package com.wanmi.sbc.customer.bean.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import java.time.LocalDateTime;
import lombok.Data;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>分账开通记录VO</p>
 * @author 许云鹏
 * @date 2022-07-01 16:36:53
 */
@Schema
@Data
public class LedgerRecordVO implements Serializable {
	private static final long serialVersionUID = 1L;

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