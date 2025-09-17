package com.wanmi.sbc.marketing.api.request.giftcard;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotEmpty;

import lombok.*;

import java.util.List;

/**
 * <p>用户礼品卡订单查询请求参数</p>
 * @author 吴瑞
 * @date 2022-12-12 09:45:09
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserGiftCardTradeRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 商品详情
	 */
	@Schema(description = "批量查询-礼品卡IdList")
	@NotEmpty
	private List<GoodsInfoVO> goodsInfoVOList;

	/**
	 * 用户Id
	 */
	@Schema(description = "用户Id")
	@NotEmpty
	private String customerId;

	/**
	 * 用户礼品卡Id
	 * 为空查询用户所有可用  不为空根据目标Id查询可用
	 */
	@Schema(description = "用户礼品卡Id")
	private List<Long> userGiftCardIdList;


}