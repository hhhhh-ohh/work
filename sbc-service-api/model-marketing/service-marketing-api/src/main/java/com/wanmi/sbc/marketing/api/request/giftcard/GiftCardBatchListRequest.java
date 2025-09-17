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
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>礼品卡批次列表查询请求参数</p>
 * @author 马连峰
 * @date 2022-12-10 10:59:47
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GiftCardBatchListRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 批量查询-主键IdList
	 */
	@Schema(description = "批量查询-主键IdList")
	private List<Long> giftCardBatchIdList;

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
	 * 搜索条件:制/发卡时间开始
	 */
	@Schema(description = "搜索条件:制/发卡时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime generateTimeBegin;
	/**
	 * 搜索条件:制/发卡时间截止
	 */
	@Schema(description = "搜索条件:制/发卡时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime generateTimeEnd;

	/**
	 * 制/发卡人
	 */
	@Schema(description = "制/发卡人")
	private String generatePerson;

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
	 * 删除标记  0：正常，1：删除
	 */
	@Schema(description = "删除标记  0：正常，1：删除")
	private DeleteFlag delFlag;

	/**
	 * 搜索条件:创建时间开始
	 */
	@Schema(description = "搜索条件:创建时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTimeBegin;
	/**
	 * 搜索条件:创建时间截止
	 */
	@Schema(description = "搜索条件:创建时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTimeEnd;

	/**
	 * 创建人
	 */
	@Schema(description = "创建人")
	private String createPerson;

	/**
	 * 搜索条件:更新时间开始
	 */
	@Schema(description = "搜索条件:更新时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTimeBegin;
	/**
	 * 搜索条件:更新时间截止
	 */
	@Schema(description = "搜索条件:更新时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTimeEnd;

	/**
	 * 更新人
	 */
	@Schema(description = "更新人")
	private String updatePerson;

}