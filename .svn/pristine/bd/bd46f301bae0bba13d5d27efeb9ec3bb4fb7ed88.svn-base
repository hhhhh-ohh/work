package com.wanmi.sbc.marketing.api.request.giftcard;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.marketing.bean.enums.GiftCardBatchType;
import com.wanmi.sbc.marketing.bean.enums.GiftCardSourceType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>礼品卡单卡明细分页查询请求参数</p>
 * @author edy
 * @date 2023-10-24 10:58:28
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GiftCardDetailJoinPageRequest extends BaseQueryRequest {
	private static final long serialVersionUID = 7067804573283714076L;

	/**
	 *  礼品卡卡号1
	 */
	@Schema(description = "礼品卡起始卡号")
	private String giftCardNoStart;

	/**
	 *  礼品卡卡号2
	 */
	@Schema(description = "礼品卡结束卡号")
	private String giftCardNoEnd;

	@Schema(description = "礼品卡卡号")
	private String giftCardNo;

	/**
	 *  礼品卡名称
	 */
	@Schema(description = "礼品卡名称")
	private String mainName;

	/**
	 *  卡类型: 0 现金卡、1 全选提货卡、2 任选提货卡、3 平台储值卡、4 企业储值卡
	 */
	@Schema(description = "卡类型")
	private Integer giftCardType;

	/**
	 *  发卡/兑卡会员
	 */
	@Schema(description = "发卡/兑卡会员")
	private String belongPerson;

	/**
	 *  激活会员
	 */
	@Schema(description = "激活会员")
	private String activationPerson;

	/**
	 *  卡销售单号
	 */
	@Schema(description = "卡销售单号")
	private String businessNo;

	/**
	 * 批次类型 0:制卡 1:发卡
	 */
	@Schema(description = " 批次类型 0:制卡 1:发卡")
	private GiftCardBatchType batchType;

	/**
	 * 批次编号(制/发卡批次)，年月日时分秒毫秒+3位随机数
	 */
	@Schema(description = " 批次编号(制/发卡批次)，年月日时分秒毫秒+3位随机数")
	private String batchNo;

	/**
	 * 批次编号(制/发卡批次)，年月日时分秒毫秒+3位随机数
	 */
	@Schema(description = " 制卡批次编号")
	private String makeCardBatchNo;

	@Schema(description = " 发卡批次编号")
	private String sendCardBatchNo;

	/**
	 * 搜索条件:卡过期时间开始
	 */
	@Schema(description = "搜索条件:卡过期时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime expirationTimeBegin;
	/**
	 * 搜索条件:卡过期时间截止
	 */
	@Schema(description = "搜索条件:卡过期时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime expirationTimeEnd;

	/**
	 * 礼品卡详情状态 0：未兑换 1：未激活 2：已激活 3：已作废 4：已过期 5：未开通 6：已用完
	 */
	@Schema(description = "礼品卡详情状态 0：未兑换 1：未激活 2：已激活 3：已作废 4：已过期 5：未开通 6：已用完")
	private Integer cardDetailStatus;

	@Schema(description = "卡销售订单id")
	private String giftCardTradeId;

	@Schema(description = "制卡批次id")
	private Long giftCardBatchId;

	@Schema(description = "是否空卡")
	private DefaultFlag blankType;

	@Schema(description = "礼品卡卡号List")
	private List<String> giftCardNoList;

	/**
	 * 搜索条件:兑换时间开始
	 */
	@Schema(description = "搜索条件:兑换时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime acquireTimeBegin;
	/**
	 * 搜索条件:兑换时间截止
	 */
	@Schema(description = "搜索条件:兑换时间截止")
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

	@Schema(description = "发卡状态，0：待发 1：成功 2：失败")
	private Integer sendStatus;

	@Schema(description = "礼品卡来源类型 0：制卡 1：发卡")
	private GiftCardSourceType sourceType;

	/**
	 * 礼品卡id
	 */
	@Schema(description = "礼品卡id")
	private Long giftCardId;

	@Schema(description = "礼品卡详情销售单id")
	private String giftCardDetailTradeId;

}