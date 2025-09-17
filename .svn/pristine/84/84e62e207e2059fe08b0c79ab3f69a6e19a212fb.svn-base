package com.wanmi.sbc.customer.bean.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>分账绑定关系VO</p>
 * @author 许云鹏
 * @date 2022-07-01 16:24:24
 */
@Schema
@Data
public class LedgerReceiverRelVO implements Serializable {
	private static final long serialVersionUID = 1L;

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
	 * 接收方编码
	 */
	@Schema(description = "接收方编码")
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
	private String applyId;

	/**
	 * 接收方账户
	 */
	@Schema(description = "接收方账户")
	private String receiverAccount;

	/**
	 * 审核状态 0、待审核 1、已审核 2、已驳回
	 */
	@Schema(description = "审核状态 0、待审核 1、已审核 2、已驳回")
	private Integer checkState;

	/**
	 * 驳回原因
	 */
	@Schema(description = "驳回原因")
	private String rejectReason;

	/**
	 * 电子合同编号
	 */
	@Schema(description = "电子合同编号")
	private String ecNo;

	/**
	 * 电子合同文件
	 */
	@Schema(description = "电子合同文件")
	private String ecContentId;

	/**
	 * 待签约的电子合同链接
	 */
	@Schema(description = "待签约的电子合同链接")
	private String ecUrl;

	/**
	 * 电子合同受理号
	 */
	@Schema(description = "电子合同受理号")
	private String ecApplyId;

}