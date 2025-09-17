package com.wanmi.sbc.marketing.api.provider.giftcard;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.marketing.api.request.giftcard.*;
import com.wanmi.sbc.marketing.api.response.giftcard.GiftCardCancelResultResponse;
import com.wanmi.sbc.marketing.api.response.giftcard.GiftCardDetailAddResponse;
import com.wanmi.sbc.marketing.api.response.giftcard.GiftCardDetailModifyResponse;
import com.wanmi.sbc.marketing.api.response.giftcard.GiftCardDetailQueryBalanceResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;
import java.util.List;

/**
 * <p>礼品卡详情保存服务Provider</p>
 * @author 马连峰
 * @date 2022-12-09 14:08:26
 */
@FeignClient(value = "${application.marketing.name}", contextId = "GiftCardDetailProvider")
public interface GiftCardDetailProvider {

	/**
	 * 新增礼品卡详情API
	 *
	 * @author 马连峰
	 * @param giftCardDetailAddRequest 礼品卡详情新增参数结构 {@link GiftCardDetailAddRequest}
	 * @return 新增的礼品卡详情信息 {@link GiftCardDetailAddResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/giftcarddetail/add")
	BaseResponse<GiftCardDetailAddResponse> add(@RequestBody @Valid GiftCardDetailAddRequest giftCardDetailAddRequest);

	/**
	 * 修改礼品卡详情API
	 *
	 * @author 马连峰
	 * @param giftCardDetailModifyRequest 礼品卡详情修改参数结构 {@link GiftCardDetailModifyRequest}
	 * @return 修改的礼品卡详情信息 {@link GiftCardDetailModifyResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/giftcarddetail/modify")
	BaseResponse<GiftCardDetailModifyResponse> modify(@RequestBody @Valid GiftCardDetailModifyRequest giftCardDetailModifyRequest);

	/**
	 * @description 礼品卡销卡
	 * @author  lvzhenwei
	 * @date 2022/12/14 7:28 下午
	 * @param request
	 * @return com.wanmi.sbc.common.base.BaseResponse
	 **/
	@PostMapping("/marketing/${application.marketing.version}/giftcarddetail/cancel")
	BaseResponse<GiftCardCancelResultResponse> cancelCard(@RequestBody @Valid GiftCardCancelRequest request);

	/**
	 * @description 注销指定用户名下所有礼品卡
	 * @author  lvzhenwei
	 * @date 2022/12/14 7:28 下午
	 * @param request
	 * @return com.wanmi.sbc.common.base.BaseResponse
	 **/
	@PostMapping("/marketing/${application.marketing.version}/giftcarddetail/cancel-customer")
	BaseResponse<GiftCardCancelResultResponse> cancelCustomerCard(@RequestBody @Valid GiftCardCustomerCancelRequest request);

	/**
	 * 修改礼品卡详情API
	 *
	 * @author 马连峰
	 * @param request 礼品卡详情修改参数结构 {@link GiftCardDetailQueryBalanceRequest}
	 * @return 修改的礼品卡详情信息 {@link GiftCardDetailModifyResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/giftcarddetail/query-balance")
	BaseResponse<GiftCardDetailQueryBalanceResponse> queryBalance(@RequestBody @Valid GiftCardDetailQueryBalanceRequest request);


	/**
	 * @description 小助手退单对应的礼品卡销卡
	 * @author  刘方鑫
	 * @date 2025/08/08 7:28 下午
	 * @param request
	 * @return com.wanmi.sbc.common.base.BaseResponse
	 **/
	@PostMapping("/marketing/${application.marketing.version}/giftcarddetail/oldSendNewCancelCard")
	BaseResponse<GiftCardCancelResultResponse> oldSendNewCancelCard(@RequestBody  OldSendNewGiftCardCancelRequest request);
}

