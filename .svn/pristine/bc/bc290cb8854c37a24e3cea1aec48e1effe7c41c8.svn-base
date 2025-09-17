package com.wanmi.sbc.marketing.provider.impl.electroniccoupon;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.marketing.api.provider.electroniccoupon.ElectronicCouponProvider;
import com.wanmi.sbc.marketing.api.request.electroniccoupon.*;
import com.wanmi.sbc.marketing.api.response.electroniccoupon.*;
import com.wanmi.sbc.marketing.electroniccoupon.model.root.ElectronicCoupon;
import com.wanmi.sbc.marketing.electroniccoupon.service.ElectronicCouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;

/**
 * <p>电子卡券表保存服务接口实现</p>
 * @author 许云鹏
 * @date 2022-01-26 17:18:05
 */
@RestController
@Validated
public class ElectronicCouponController implements ElectronicCouponProvider {
	@Autowired
	private ElectronicCouponService electronicCouponService;

	@Override
	public BaseResponse<ElectronicCouponAddResponse> add(@RequestBody @Valid ElectronicCouponAddRequest electronicCouponAddRequest) {
		ElectronicCoupon electronicCoupon = KsBeanUtil.convert(electronicCouponAddRequest, ElectronicCoupon.class);
		return BaseResponse.success(new ElectronicCouponAddResponse(
				electronicCouponService.wrapperVo(electronicCouponService.add(electronicCoupon))));
	}

	@Override
	public BaseResponse<ElectronicCouponAddBatchResponse> addBatch(@RequestBody @Valid ElectronicCouponAddBatchRequest addBatchRequest) {
		List<ElectronicCoupon> electronicCouponList = KsBeanUtil.convertList(addBatchRequest.getCouponAddRequestList(), ElectronicCoupon.class);
		return BaseResponse.success(new ElectronicCouponAddBatchResponse(
				electronicCouponService.wrapperVoList(electronicCouponService.addBatch(electronicCouponList))));
	}

	@Override
	public BaseResponse<ElectronicCouponModifyResponse> modify(@RequestBody @Valid ElectronicCouponModifyRequest request) {
		return BaseResponse.success(new ElectronicCouponModifyResponse(
				electronicCouponService.wrapperVo(electronicCouponService.modify(request.getId(), request.getCouponName()))));
	}

	@Override
	public BaseResponse cardStateStatistical() {
		electronicCouponService.cardStateStatistical();
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse updateFreezeStock(@RequestBody @Valid ElectronicCouponUpdateFreezeStockRequest electronicCouponUpdateFreezeStockRequest) {
		electronicCouponService.updateFreezeStock(
				electronicCouponUpdateFreezeStockRequest.getFreezeStock(),
				electronicCouponUpdateFreezeStockRequest.getId(),
				electronicCouponUpdateFreezeStockRequest.getOrderNo(),
				electronicCouponUpdateFreezeStockRequest.getUnBindOrderFlag());
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse updateBindingFlag(@RequestBody @Valid ElectronicCouponUpdateBindRequest request) {
		electronicCouponService.updateBindingFlag(request.getUnBindingIds(), request.getBindingIds());
		return BaseResponse.SUCCESSFUL();
	}

}

