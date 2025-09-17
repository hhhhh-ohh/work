package com.wanmi.sbc.marketing.api.request.giftcard;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import java.time.LocalDateTime;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.marketing.bean.enums.GiftCardDetailStatus;
import com.wanmi.sbc.marketing.bean.enums.GiftCardSendStatus;
import com.wanmi.sbc.marketing.bean.enums.GiftCardSourceType;
import lombok.*;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>礼品卡详情导出查询请求参数</p>
 * @author 马连峰
 * @date 2022-12-10 10:58:28
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GiftCardDetailExportRequest extends BaseQueryRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 批量查询-礼品卡卡号，主键List
	 */
	@Schema(description = "批量查询-礼品卡卡号，主键List")
	private List<String> giftCardNoList;

	/**
	 * 礼品卡卡号，主键
	 */
	@Schema(description = "礼品卡卡号，主键")
	private String giftCardNo;

	/**
	 * 礼品卡id
	 */
	@Schema(description = "礼品卡id")
	private Long giftCardId;

	/**
	 * 批次编号
	 */
	@Schema(description = "批次编号")
	private String batchNo;

	/**
	 * 来源类型 0：制卡 1：发卡 2：购卡
	 */
	@Schema(description = "来源类型 0：制卡 1：发卡 2：购卡")
	private GiftCardSourceType sourceType;

	/**
	 * 兑换码
	 */
	@Schema(description = "兑换码")
	private String exchangeCode;

	/**
	 * 搜索条件:有效期开始
	 */
	@Schema(description = "搜索条件:有效期开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime expirationTimeBegin;
	/**
	 * 搜索条件:有效期截止
	 */
	@Schema(description = "搜索条件:有效期截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime expirationTimeEnd;

	/**
	 * 搜索条件:会员获卡时间，制卡兑换时间/发卡接收时间开始
	 */
	@Schema(description = "搜索条件:会员获卡时间，制卡兑换时间/发卡接收时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime acquireTimeBegin;
	/**
	 * 搜索条件:会员获卡时间，制卡兑换时间/发卡接收时间截止
	 */
	@Schema(description = "搜索条件:会员获卡时间，制卡兑换时间/发卡接收时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime acquireTimeEnd;

	/**
	 * 搜索条件:激活时间开始
	 */
	@Schema(description = "搜索条件:激活时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime activationTimeBegin;
	/**
	 * 搜索条件:激活时间截止
	 */
	@Schema(description = "搜索条件:激活时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime activationTimeEnd;

	/**
	 * 归属会员，制卡兑换人/发卡接收人
	 */
	@Schema(description = "归属会员，制卡兑换人/发卡接收人")
	private String belongPerson;

	/**
	 * 激活会员
	 */
	@Schema(description = "激活会员")
	private String activationPerson;

	/**
	 * 礼品卡详情状态 0:未兑换 1:未激活 2:已激活 3:已销卡 4:已过期
	 */
	@Schema(description = "礼品卡详情状态 0:未兑换 1:未激活 2:已激活 3:已销卡 4:已过期")
	private GiftCardDetailStatus cardDetailStatus;

	/**
	 * 销卡人
	 */
	@Schema(description = "销卡人")
	private String cancelPerson;

	/**
	 * 发卡状态 0：待发 1：成功 2：失败
	 */
	@Schema(description = "发卡状态 0：待发 1：成功 2：失败")
	private GiftCardSendStatus sendStatus;

	/**
	 * 状态变更原因，目前仅针对销卡原因
	 */
	@Schema(description = "状态变更原因，目前仅针对销卡原因")
	private String statusReason;

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
	 * 搜索条件:修改时间开始
	 */
	@Schema(description = "搜索条件:修改时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTimeBegin;
	/**
	 * 搜索条件:修改时间截止
	 */
	@Schema(description = "搜索条件:修改时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTimeEnd;

	/**
	 * 更新人
	 */
	@Schema(description = "更新人")
	private String updatePerson;

}
