package com.wanmi.sbc.marketing.provider.impl.giftcard;

import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.marketing.api.request.giftcard.*;
import com.wanmi.sbc.marketing.api.response.giftcard.GiftCardCancelResultResponse;
import com.wanmi.sbc.marketing.api.response.giftcard.GiftCardDetailQueryBalanceResponse;
import com.wanmi.sbc.marketing.giftcard.service.UniformsGiftCardBindingService;
import com.wanmi.sbc.marketing.giftcard.service.UserGiftCardService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.marketing.api.provider.giftcard.GiftCardDetailProvider;
import com.wanmi.sbc.marketing.api.response.giftcard.GiftCardDetailAddResponse;
import com.wanmi.sbc.marketing.api.response.giftcard.GiftCardDetailModifyResponse;
import com.wanmi.sbc.marketing.giftcard.service.GiftCardDetailService;
import com.wanmi.sbc.marketing.giftcard.model.root.GiftCardDetail;

import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>礼品卡详情保存服务接口实现</p>
 * @author 马连峰
 * @date 2022-12-09 14:08:26
 */
@RestController
@Validated
public class GiftCardDetailController implements GiftCardDetailProvider {
	@Autowired
	private GiftCardDetailService giftCardDetailService;

	@Autowired
	private UserGiftCardService userGiftCardService;

	@Autowired
	private UniformsGiftCardBindingService uniformsGiftCardBindingService;

	@Override
	public BaseResponse<GiftCardDetailAddResponse> add(@RequestBody @Valid GiftCardDetailAddRequest giftCardDetailAddRequest) {
		GiftCardDetail giftCardDetail = KsBeanUtil.convert(giftCardDetailAddRequest, GiftCardDetail.class);
		return BaseResponse.success(new GiftCardDetailAddResponse(
				giftCardDetailService.wrapperVo(giftCardDetailService.add(giftCardDetail))));
	}

	@Override
	public BaseResponse<GiftCardDetailModifyResponse> modify(@RequestBody @Valid GiftCardDetailModifyRequest giftCardDetailModifyRequest) {
		GiftCardDetail giftCardDetail = KsBeanUtil.convert(giftCardDetailModifyRequest, GiftCardDetail.class);
		return BaseResponse.success(new GiftCardDetailModifyResponse(
				giftCardDetailService.wrapperVo(giftCardDetailService.modify(giftCardDetail))));
	}

	@Override
	public BaseResponse<GiftCardCancelResultResponse> cancelCard(@RequestBody @Valid GiftCardCancelRequest request) {
		List<String> giftCardNoList = request.getGiftCardNoList();
		List<String> cancelErrorList = new ArrayList<>();
		giftCardNoList.forEach(giftCardNo -> {
			try {
				giftCardDetailService.cancelCard(request, giftCardNo);
			} catch (Exception e) {
				if (giftCardNoList.size() > 1) {
					cancelErrorList.add(giftCardNo);
				} else {
					throw e;
				}
			}
		});
		return BaseResponse.success(GiftCardCancelResultResponse.builder()
				.cancelErrorGiftCardNoList(cancelErrorList).build());
	}

	@Override
	public BaseResponse<GiftCardCancelResultResponse> cancelCustomerCard(@RequestBody @Valid GiftCardCustomerCancelRequest request) {
		List<String> notCancelGiftCardNoList = userGiftCardService.findNotCancelGiftCardNoByCustomerId(request.getCustomerId());
		if (CollectionUtils.isEmpty(notCancelGiftCardNoList)) {
			return BaseResponse.success(new GiftCardCancelResultResponse());
		}
		return this.cancelCard(GiftCardCancelRequest.builder()
				.cancelDesc("用户注销")
				.giftCardNoList(notCancelGiftCardNoList)
				.cancelPerson(request.getCustomerId())
				.tradePersonType(DefaultFlag.NO)
				.build());
	}

	@Override
	public BaseResponse<GiftCardDetailQueryBalanceResponse> queryBalance(@RequestBody @Valid GiftCardDetailQueryBalanceRequest request) {
		return BaseResponse.success(GiftCardDetailQueryBalanceResponse.builder()
				.balance(userGiftCardService.queryBalance(request.getGiftCardNoList())).build());
	}

	@Override
	@PostMapping("/marketing/${application.marketing.version}/giftcarddetail/oldSendNewCancelCard")
	public BaseResponse<GiftCardCancelResultResponse> oldSendNewCancelCard(@RequestBody  OldSendNewGiftCardCancelRequest request){
		List<String> giftCardNoList = userGiftCardService.getGiftCardNoList(request);
		request.setGiftCardNoList(giftCardNoList);
		List<String> cancelErrorList = new ArrayList<>();
		giftCardNoList.forEach(giftCardNo -> {
			try {
				giftCardDetailService.cancelCard(request, giftCardNo);
			} catch (Exception e) {
				if (giftCardNoList.size() > 1) {
					cancelErrorList.add(giftCardNo);
				} else {
					throw e;
				}
			}
		});
		return BaseResponse.success(GiftCardCancelResultResponse.builder()
				.cancelErrorGiftCardNoList(cancelErrorList).build());
	}
}

