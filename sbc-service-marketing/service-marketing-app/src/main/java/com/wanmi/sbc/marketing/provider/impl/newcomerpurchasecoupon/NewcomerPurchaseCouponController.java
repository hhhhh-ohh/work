package com.wanmi.sbc.marketing.provider.impl.newcomerpurchasecoupon;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.marketing.api.provider.newcomerpurchasecoupon.NewcomerPurchaseCouponProvider;
import com.wanmi.sbc.marketing.api.request.newcomerpurchasecoupon.*;
import com.wanmi.sbc.marketing.api.response.newcomerpurchasecoupon.NewcomerPurchaseCouponAddResponse;
import com.wanmi.sbc.marketing.api.response.newcomerpurchasecoupon.NewcomerPurchaseCouponModifyResponse;
import com.wanmi.sbc.marketing.newcomerpurchasecoupon.model.root.NewcomerPurchaseCoupon;
import com.wanmi.sbc.marketing.newcomerpurchasecoupon.service.NewcomerPurchaseCouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;

/**
 * <p>新人购优惠券保存服务接口实现</p>
 * @author zhanghao
 * @date 2022-08-19 14:27:36
 */
@RestController
@Validated
public class NewcomerPurchaseCouponController implements NewcomerPurchaseCouponProvider {
	@Autowired
	private NewcomerPurchaseCouponService newcomerPurchaseCouponService;

	@Override
	public BaseResponse<NewcomerPurchaseCouponAddResponse> add(@RequestBody @Valid NewcomerPurchaseCouponAddRequest newcomerPurchaseCouponAddRequest) {
		NewcomerPurchaseCoupon newcomerPurchaseCoupon = KsBeanUtil.convert(newcomerPurchaseCouponAddRequest, NewcomerPurchaseCoupon.class);
		return BaseResponse.success(new NewcomerPurchaseCouponAddResponse(
				newcomerPurchaseCouponService.wrapperVo(newcomerPurchaseCouponService.add(newcomerPurchaseCoupon))));
	}

	@Override
	public BaseResponse<NewcomerPurchaseCouponModifyResponse> modify(@RequestBody @Valid NewcomerPurchaseCouponModifyRequest newcomerPurchaseCouponModifyRequest) {
		NewcomerPurchaseCoupon newcomerPurchaseCoupon = KsBeanUtil.convert(newcomerPurchaseCouponModifyRequest, NewcomerPurchaseCoupon.class);
		return BaseResponse.success(new NewcomerPurchaseCouponModifyResponse(
				newcomerPurchaseCouponService.wrapperVo(newcomerPurchaseCouponService.modify(newcomerPurchaseCoupon))));
	}

	@Override
	public BaseResponse deleteById(@RequestBody @Valid NewcomerPurchaseCouponDelByIdRequest newcomerPurchaseCouponDelByIdRequest) {
		newcomerPurchaseCouponService.deleteById(newcomerPurchaseCouponDelByIdRequest.getId());
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse deleteByIdList(@RequestBody @Valid NewcomerPurchaseCouponDelByIdListRequest newcomerPurchaseCouponDelByIdListRequest) {
		newcomerPurchaseCouponService.deleteByIdList(newcomerPurchaseCouponDelByIdListRequest.getIdList());
		return BaseResponse.SUCCESSFUL();
	}

	/**
	 * 新增新人购优惠券API
	 *
	 * @param newcomerPurchaseCouponBatchSaveRequest 新人购优惠券新增参数结构 {@link NewcomerPurchaseCouponBatchSaveRequest}
	 * @return 新增的新人购优惠券信息 {@link}
	 * @author zhanghao
	 */
	@Override
	public BaseResponse batchSave(NewcomerPurchaseCouponBatchSaveRequest newcomerPurchaseCouponBatchSaveRequest) {
		List<NewcomerPurchaseCouponModifyRequest> newcomerPurchaseCouponModifyRequestList =
				newcomerPurchaseCouponBatchSaveRequest.getNewcomerPurchaseCouponModifyRequestList();
		List<NewcomerPurchaseCoupon> newcomerPurchaseCoupons = KsBeanUtil.convertList(newcomerPurchaseCouponModifyRequestList, NewcomerPurchaseCoupon.class);
		newcomerPurchaseCouponService.batchSave(newcomerPurchaseCoupons);
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse fetchCoupon(@RequestBody @Valid NewcomerPurchaseCouponFetchRequest request) {
        newcomerPurchaseCouponService.fetchNewComerCoupons(request.getCustomerId());
		return BaseResponse.SUCCESSFUL();
	}

}

