package com.wanmi.sbc.marketing.api.request.giftcard;

import java.math.BigDecimal;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import java.time.LocalDateTime;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.marketing.bean.enums.GiftCardBusinessType;
import com.wanmi.sbc.marketing.bean.enums.GiftCardType;
import lombok.*;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>礼品卡交易流水导出查询请求参数</p>
 * @author 吴瑞
 * @date 2022-12-15 21:18:27
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GiftCardBillExportRequest extends BaseQueryRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 礼品卡Id
	 */
	@Schema(description = "礼品卡Id")
	private Long giftCardId;

	/**
	 * 礼品卡Id
	 */
	@Schema(description = "礼品卡类型")
	private GiftCardType giftCardType;

	/**
	 * 批量查询-giftCardBillIdList
	 */
	@Schema(description = "批量查询-giftCardBillIdList")
	private List<Long> giftCardBillIdList;

	/**
	 * giftCardBillId
	 */
	@Schema(description = "giftCardBillId")
	private Long giftCardBillId;

	/**
	 * 用户Id
	 */
	@Schema(description = "用户Id")
	private String customerId;

	/**
	 * 用户礼品卡Id
	 */
	@Schema(description = "用户礼品卡Id")
	private Long userGiftCardId;

	/**
	 * 礼品卡卡号
	 */
	@Schema(description = "礼品卡卡号")
	private String giftCardNo;

	/**
	 * 交易类型 0：使用 1：退换 2: 发卡 3：兑换 4：销卡
	 */
	@Schema(description = "交易类型 0：使用 1：退换 2: 发卡 3：兑换 4：销卡")
	private GiftCardBusinessType businessType;

	/**
	 * 业务Id
	 */
	@Schema(description = "业务Id")
	private String businessId;

	/**
	 * 交易金额
	 */
	@Schema(description = "交易金额")
	private BigDecimal tradeBalance;

	/**
	 * 交易前余额
	 */
	@Schema(description = "交易前余额")
	private BigDecimal beforeBalance;

	/**
	 * 交易后余额
	 */
	@Schema(description = "交易后余额")
	private BigDecimal afterBalance;

	/**
	 * 搜索条件:交易时间开始
	 */
	@Schema(description = "搜索条件:交易时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime tradeTimeBegin;
	/**
	 * 搜索条件:交易时间截止
	 */
	@Schema(description = "搜索条件:交易时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime tradeTimeEnd;

	/**
	 * 交易人类型 0：C端用户 1：B端用户
	 */
	@Schema(description = "交易人类型 0：C端用户 1：B端用户")
	private DefaultFlag tradePersonType;

	/**
	 * 交易人
	 */
	@Schema(description = "交易人")
	private String tradePerson;

}
