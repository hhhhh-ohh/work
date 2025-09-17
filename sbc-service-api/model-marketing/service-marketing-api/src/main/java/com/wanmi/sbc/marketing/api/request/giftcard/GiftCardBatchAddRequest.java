package com.wanmi.sbc.marketing.api.request.giftcard;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.AuditStatus;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.marketing.bean.enums.GiftCardBatchType;
import com.wanmi.sbc.marketing.bean.enums.GiftCardExchangeMode;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.*;
import jakarta.validation.constraints.NotBlank;

import lombok.*;

import org.hibernate.validator.constraints.*;

import java.time.LocalDateTime;

/**
 * <p>礼品卡批次新增参数</p>
 * @author 马连峰
 * @date 2022-12-10 10:59:47
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GiftCardBatchAddRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 礼品卡Id
	 */
	@Schema(description = "礼品卡Id")
	@NotNull
	@Max(9223372036854775807L)
	private Long giftCardId;

	/**
	 * 兑换方式 0:卡密模式
	 */
	@Schema(description = "兑换方式 0:卡密模式")
	private GiftCardExchangeMode exchangeMode;

	/**
	 * 批次类型 0:制卡 1:发卡
	 */
	@Schema(description = "批次类型 0:制卡 1:发卡")
	@NotNull
	private GiftCardBatchType batchType;

	/**
	 * 批次数量(制/发卡数量)
	 */
	@Schema(description = "批次数量(制/发卡数量)")
	@Max(9223372036854775807L)
	private Long batchNum;

	/**
	 * 批次编号(制/发卡批次)，年月日时分秒毫秒+3位随机数
	 */
	@Schema(description = "批次编号(制/发卡批次)，年月日时分秒毫秒+3位随机数")
	@NotBlank
	@Length(max=18)
	private String batchNo;

	/**
	 * 制/发卡时间
	 */
	@Schema(description = "制/发卡时间")
	@NotNull
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime generateTime;

	/**
	 * 制/发卡人
	 */
	@Schema(description = "制/发卡人")
	@Length(max=32)
	private String generatePerson;

	/**
	 * 起始卡号
	 */
	@Schema(description = "起始卡号")
	@Length(max=20)
	private String startCardNo;

	/**
	 * 结束卡号
	 */
	@Schema(description = "结束卡号")
	@Length(max=20)
	private String endCardNo;

	/**
	 * 审核状态 0:待审核 1:已审核通过 2:审核不通过
	 */
	@Schema(description = "审核状态 0:待审核 1:已审核通过 2:审核不通过")
	@NotNull
	private AuditStatus auditStatus;

	/**
	 * 审核驳回原因
	 */
	@Schema(description = "审核驳回原因")
	@Length(max=255)
	private String auditReason;

	/**
	 * excel导入的文件oss地址（仅批量发卡时存在）
	 */
	@Schema(description = "excel导入的文件oss地址（仅批量发卡时存在）")
	@Length(max=255)
	private String excelFilePath;

	/**
	 * 删除标记  0：正常，1：删除
	 */
	@Schema(description = "删除标记  0：正常，1：删除", hidden = true)
	private DeleteFlag delFlag;

	/**
	 * 创建人
	 */
	@Schema(description = "创建人", hidden = true)
	private String createPerson;

	/**
	 * 更新人
	 */
	@Schema(description = "更新人", hidden = true)
	private String updatePerson;

}