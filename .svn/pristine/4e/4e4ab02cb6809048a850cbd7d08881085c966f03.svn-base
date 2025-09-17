package com.wanmi.sbc.marketing.bean.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.wanmi.sbc.common.enums.AuditStatus;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.SignWordType;
import com.wanmi.sbc.common.sensitiveword.annotation.SensitiveWordsField;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import java.time.LocalDateTime;

import com.wanmi.sbc.marketing.bean.enums.GiftCardBatchType;
import com.wanmi.sbc.marketing.bean.enums.GiftCardExchangeMode;
import lombok.Data;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>礼品卡批次VO</p>
 * @author 马连峰
 * @date 2022-12-10 10:59:47
 */
@Schema
@Data
public class GiftCardBatchVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键Id
	 */
	@Schema(description = "主键Id")
	private Long giftCardBatchId;

	/**
	 * 礼品卡Id
	 */
	@Schema(description = "礼品卡Id")
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
	private GiftCardBatchType batchType;

	/**
	 * 批次数量(制/发卡数量)
	 */
	@Schema(description = "批次数量(制/发卡数量)")
	private Long batchNum;

	/**
	 * 批次编号(制/发卡批次)，年月日时分秒毫秒+3位随机数
	 */
	@Schema(description = "批次编号(制/发卡批次)，年月日时分秒毫秒+3位随机数")
	private String batchNo;

	/**
	 * 制/发卡时间
	 */
	@Schema(description = "制/发卡时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime generateTime;

	/**
	 * 制/发卡人
	 */
	@Schema(description = "制/发卡人")
	private String generatePerson;

	/**
	 * 制/发卡人名称
	 */
	@SensitiveWordsField(signType = SignWordType.NAME)
	@Schema(description = "制/发卡人名称")
	private String generatePersonName;

	/**
	 * 制/发卡人账号
	 */
	@SensitiveWordsField(signType = SignWordType.PHONE)
	@Schema(description = "制/发卡人账号")
	private String generatePersonAccount;

	/**
	 * 起始卡号
	 */
	@Schema(description = "起始卡号")
	private String startCardNo;

	/**
	 * 结束卡号
	 */
	@Schema(description = "结束卡号")
	private String endCardNo;

	/**
	 * 审核状态 0:待审核 1:已审核通过 2:审核不通过
	 */
	@Schema(description = "审核状态 0:待审核 1:已审核通过 2:审核不通过")
	private AuditStatus auditStatus;

	/**
	 * 审核驳回原因
	 */
	@Schema(description = "审核驳回原因")
	private String auditReason;

	/**
	 * excel导入的文件oss地址（仅批量发卡时存在）
	 */
	@Schema(description = "excel导入的文件oss地址（仅批量发卡时存在）")
	private String excelFilePath;

	/**
	 * 是否导出小程序一卡一码URL，0:不导出，1：导出
	 */
	@Schema(description = "是否导出小程序一卡一码URL，0:不导出，1：导")
	private DefaultFlag exportMiniCodeType;

	/**
	 * 是否导出H5一卡一码URL，0:不导出，1：导出
	 */
	@Schema(description = "是否导出H5一卡一码URL，0:不导出，1：导出")
	private DefaultFlag exportWebCodeType;

	/**
	 * 礼品卡信息
	 */
	@Schema(description = "礼品卡信息")
	private GiftCardVO giftCard;

}