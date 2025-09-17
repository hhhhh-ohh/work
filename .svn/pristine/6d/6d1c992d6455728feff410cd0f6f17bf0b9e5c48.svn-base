package com.wanmi.sbc.marketing.provider.impl.newcomerpurchaseconfig;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.marketing.api.provider.newcomerpurchaseconfig.NewcomerPurchaseConfigQueryProvider;
import com.wanmi.sbc.marketing.api.request.newcomerpurchaseconfig.NewcomerPurchaseDetailRequest;
import com.wanmi.sbc.marketing.api.response.newcomerpurchaseconfig.NewcomerPurchaseConfigByIdResponse;
import com.wanmi.sbc.marketing.api.response.newcomerpurchaseconfig.NewcomerPurchaseDetailResponse;
import com.wanmi.sbc.marketing.bean.vo.NewcomerPurchaseConfigVO;
import com.wanmi.sbc.marketing.newcomerpurchaseconfig.model.root.NewcomerPurchaseConfig;
import com.wanmi.sbc.marketing.newcomerpurchaseconfig.service.NewcomerPurchaseConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * <p>新人专享设置查询服务接口实现</p>
 * @author zhanghao
 * @date 2022-08-19 14:28:12
 */
@RestController
@Validated
public class NewcomerPurchaseConfigQueryController implements NewcomerPurchaseConfigQueryProvider {
	@Autowired
	private NewcomerPurchaseConfigService newcomerPurchaseConfigService;

	@Override
	public BaseResponse<NewcomerPurchaseConfigByIdResponse> getOne() {
		NewcomerPurchaseConfig newcomerPurchaseConfig = newcomerPurchaseConfigService.getOne();
		return BaseResponse.success(new NewcomerPurchaseConfigByIdResponse(newcomerPurchaseConfigService.wrapperVo(newcomerPurchaseConfig)));
	}

	@Override
	public BaseResponse<NewcomerPurchaseDetailResponse> detailForMobile(@RequestBody @Valid NewcomerPurchaseDetailRequest request) {
		NewcomerPurchaseDetailResponse response = newcomerPurchaseConfigService.detailForMobile(request.getCustomerId());
		return BaseResponse.success(response);
	}


	@Override
	public BaseResponse<NewcomerPurchaseConfigByIdResponse> detail() {
		NewcomerPurchaseConfigVO newcomerPurchaseConfig =
				newcomerPurchaseConfigService.detail();
		return BaseResponse.success(new NewcomerPurchaseConfigByIdResponse(newcomerPurchaseConfig));
	}

	@Override
	public BaseResponse<Boolean> checkActive() {
		Boolean active = newcomerPurchaseConfigService.checkActive();
		return BaseResponse.success(active);
	}
}

