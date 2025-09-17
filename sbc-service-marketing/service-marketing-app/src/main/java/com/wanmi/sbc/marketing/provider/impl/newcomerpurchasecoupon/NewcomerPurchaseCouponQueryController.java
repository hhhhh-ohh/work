package com.wanmi.sbc.marketing.provider.impl.newcomerpurchasecoupon;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.marketing.api.provider.newcomerpurchasecoupon.NewcomerPurchaseCouponQueryProvider;
import com.wanmi.sbc.marketing.api.request.newcomerpurchasecoupon.*;
import com.wanmi.sbc.marketing.api.response.newcomerpurchasecoupon.NewcomerPurchaseCouponByIdResponse;
import com.wanmi.sbc.marketing.api.response.newcomerpurchasecoupon.NewcomerPurchaseCouponFetchResponse;
import com.wanmi.sbc.marketing.api.response.newcomerpurchasecoupon.NewcomerPurchaseCouponListResponse;
import com.wanmi.sbc.marketing.api.response.newcomerpurchasecoupon.NewcomerPurchaseCouponPageResponse;
import com.wanmi.sbc.marketing.bean.vo.CouponInfoVO;
import com.wanmi.sbc.marketing.bean.vo.NewcomerPurchaseCouponVO;
import com.wanmi.sbc.marketing.coupon.model.root.CouponInfo;
import com.wanmi.sbc.marketing.coupon.service.CouponInfoService;
import com.wanmi.sbc.marketing.newcomerpurchasecoupon.model.root.NewcomerPurchaseCoupon;
import com.wanmi.sbc.marketing.newcomerpurchasecoupon.service.NewcomerPurchaseCouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>新人购优惠券查询服务接口实现</p>
 * @author zhanghao
 * @date 2022-08-19 14:27:36
 */
@RestController
@Validated
public class NewcomerPurchaseCouponQueryController implements NewcomerPurchaseCouponQueryProvider {

	@Autowired
	private NewcomerPurchaseCouponService newcomerPurchaseCouponService;

	@Autowired
	private CouponInfoService couponInfoService;

	@Override
	public BaseResponse<NewcomerPurchaseCouponPageResponse> page(@RequestBody @Valid NewcomerPurchaseCouponPageRequest newcomerPurchaseCouponPageReq) {
		NewcomerPurchaseCouponQueryRequest queryReq = KsBeanUtil.convert(newcomerPurchaseCouponPageReq, NewcomerPurchaseCouponQueryRequest.class);
		Page<NewcomerPurchaseCoupon> newcomerPurchaseCouponPage = newcomerPurchaseCouponService.page(queryReq);
		Page<NewcomerPurchaseCouponVO> newPage = newcomerPurchaseCouponPage.map(entity -> newcomerPurchaseCouponService.wrapperVo(entity));
		MicroServicePage<NewcomerPurchaseCouponVO> microPage = new MicroServicePage<>(newPage, newcomerPurchaseCouponPageReq.getPageable());
		NewcomerPurchaseCouponPageResponse finalRes = new NewcomerPurchaseCouponPageResponse(microPage);
		return BaseResponse.success(finalRes);
	}

	@Override
	public BaseResponse<NewcomerPurchaseCouponListResponse> list(@RequestBody @Valid NewcomerPurchaseCouponListRequest newcomerPurchaseCouponListReq) {
		NewcomerPurchaseCouponQueryRequest queryReq = KsBeanUtil.convert(newcomerPurchaseCouponListReq, NewcomerPurchaseCouponQueryRequest.class);
		List<NewcomerPurchaseCoupon> newcomerPurchaseCouponList = newcomerPurchaseCouponService.list(queryReq);
		List<NewcomerPurchaseCouponVO> newList = newcomerPurchaseCouponList.stream()
				.map(entity -> {
					NewcomerPurchaseCouponVO newcomerPurchaseCouponVO = newcomerPurchaseCouponService.wrapperVo(entity);
					String couponId = entity.getCouponId();
					CouponInfo couponInfo = couponInfoService.getCouponInfoById(couponId);
					CouponInfoVO couponInfoVO = couponInfoService.wrapperCouponDetailInfo(couponInfo);
					newcomerPurchaseCouponVO.setCouponInfoVO(couponInfoVO);
					return newcomerPurchaseCouponVO;
				}).collect(Collectors.toList());
		return BaseResponse.success(new NewcomerPurchaseCouponListResponse(newList));
	}

	@Override
	public BaseResponse<NewcomerPurchaseCouponByIdResponse> getById(@RequestBody @Valid NewcomerPurchaseCouponByIdRequest newcomerPurchaseCouponByIdRequest) {
		NewcomerPurchaseCoupon newcomerPurchaseCoupon =
		newcomerPurchaseCouponService.getOne(newcomerPurchaseCouponByIdRequest.getId());
		return BaseResponse.success(new NewcomerPurchaseCouponByIdResponse(newcomerPurchaseCouponService.wrapperVo(newcomerPurchaseCoupon)));
	}

	@Override
	public BaseResponse<Long> countForExport(@Valid NewcomerPurchaseCouponExportRequest request) {
		NewcomerPurchaseCouponQueryRequest queryReq = KsBeanUtil.convert(request, NewcomerPurchaseCouponQueryRequest.class);
		Long total = newcomerPurchaseCouponService.count(queryReq);
		return BaseResponse.success(total);
	}

	@Override
	public BaseResponse<NewcomerPurchaseCouponFetchResponse> getFetchCoupons(@RequestBody @Valid NewcomerPurchaseCouponGetFetchRequest request) {
		List<CouponInfoVO> coupons = newcomerPurchaseCouponService.getFetchCoupons(request.getCouponIds());
        return BaseResponse.success(NewcomerPurchaseCouponFetchResponse.builder().coupons(coupons).build());
	}

}

