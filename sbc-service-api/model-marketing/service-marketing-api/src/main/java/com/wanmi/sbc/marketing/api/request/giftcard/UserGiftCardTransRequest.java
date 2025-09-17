package com.wanmi.sbc.marketing.api.request.giftcard;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.marketing.bean.enums.GiftCardBusinessType;
import com.wanmi.sbc.marketing.bean.vo.GiftCardTransBusinessVO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>用户礼品卡交易请求参数</p>
 * @author 吴瑞
 * @date 2022-12-12 09:45:09
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserGiftCardTransRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 批量查询-礼品卡IdList
	 */
	@Schema(description = "用户礼品卡Id")
	@NotNull
	private Long userGiftCardId;

	/**
	 * 礼品卡Id
	 */
	@Schema(description = "礼品卡卡号")
	@NotEmpty
	private String giftCardNo;

	/**
	 * 用户Id
	 */
	@Schema(description = "用户Id")
	@NotEmpty
	private String customerId;

	/**
	 * 交易人类型 0：C端用户 1：B端用户
	 */
	@Schema(description = "交易人类型 0：C端用户 1：B端用户")
	@NotNull
	private DefaultFlag tradePersonType;

	/**
	 * 交易总金额
	 */
	@Schema(description = "交易总金额")
	@NotNull
	private BigDecimal sumTradePrice;

	/**
	 * 交易类型 0：订单抵扣 1：退换 2: 发卡 3：兑换 4：销卡
	 */
	@Schema(description = "交易类型 0：订单抵扣 1：退换 2: 发卡 3：兑换 4：销卡")
	@NotNull
	private GiftCardBusinessType businessType;

	/**
	 * 交易业务明细
	 */
	@Schema(description = "交易业务明细")
	@Valid
	@NotEmpty
	private List<GiftCardTransBusinessVO> transBusinessVOList;

	/**
	 * 是否强制处理  true: 当礼品卡余额小于请求要抵扣金额时会扣除礼品卡全部余额
	 */
	@Schema(description = "是否强制处理")
	public Boolean forceCommit;

	/**
	 * 是否回退如果回退则不创建Bill记录并且删除 businessType  businessId bill记录
	 */
	@Schema(description = "是否回退")
	public Boolean rollback;

	@Schema(description = "交易类型 0：订单抵扣 1：退换 2: 发卡 3：兑换 4：销卡")
	private GiftCardBusinessType rollbackBusinessType;

}