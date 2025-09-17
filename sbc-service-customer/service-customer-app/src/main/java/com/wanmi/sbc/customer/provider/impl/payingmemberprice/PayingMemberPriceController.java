package com.wanmi.sbc.customer.provider.impl.payingmemberprice;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.customer.api.provider.payingmemberprice.PayingMemberPriceProvider;
import com.wanmi.sbc.customer.api.request.payingmemberprice.PayingMemberPriceAddRequest;
import com.wanmi.sbc.customer.api.response.payingmemberprice.PayingMemberPriceAddResponse;
import com.wanmi.sbc.customer.api.request.payingmemberprice.PayingMemberPriceModifyRequest;
import com.wanmi.sbc.customer.api.response.payingmemberprice.PayingMemberPriceModifyResponse;
import com.wanmi.sbc.customer.api.request.payingmemberprice.PayingMemberPriceDelByIdRequest;
import com.wanmi.sbc.customer.api.request.payingmemberprice.PayingMemberPriceDelByIdListRequest;
import com.wanmi.sbc.customer.payingmemberprice.service.PayingMemberPriceService;
import com.wanmi.sbc.customer.payingmemberprice.model.root.PayingMemberPrice;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.validation.Valid;

/**
 * <p>付费设置表保存服务接口实现</p>
 * @author zhanghao
 * @date 2022-05-13 13:40:30
 */
@RestController
@Validated
public class PayingMemberPriceController implements PayingMemberPriceProvider {
	@Autowired
	private PayingMemberPriceService payingMemberPriceService;

	@Override
	public BaseResponse add(@RequestBody @Valid PayingMemberPriceAddRequest payingMemberPriceAddRequest) {
//		PayingMemberPrice payingMemberPrice = KsBeanUtil.convert(payingMemberPriceAddRequest, PayingMemberPrice.class);
		payingMemberPriceService.add(payingMemberPriceAddRequest);
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse<PayingMemberPriceModifyResponse> modify(@RequestBody @Valid PayingMemberPriceModifyRequest payingMemberPriceModifyRequest) {
		PayingMemberPrice payingMemberPrice = KsBeanUtil.convert(payingMemberPriceModifyRequest, PayingMemberPrice.class);
		return BaseResponse.success(new PayingMemberPriceModifyResponse(
				payingMemberPriceService.wrapperVo(payingMemberPriceService.modify(payingMemberPrice))));
	}

	@Override
	public BaseResponse deleteById(@RequestBody @Valid PayingMemberPriceDelByIdRequest payingMemberPriceDelByIdRequest) {
		PayingMemberPrice payingMemberPrice = KsBeanUtil.convert(payingMemberPriceDelByIdRequest, PayingMemberPrice.class);
		payingMemberPrice.setDelFlag(DeleteFlag.YES);
		payingMemberPriceService.deleteById(payingMemberPrice);
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse deleteByIdList(@RequestBody @Valid PayingMemberPriceDelByIdListRequest payingMemberPriceDelByIdListRequest) {
		payingMemberPriceService.deleteByIdList(payingMemberPriceDelByIdListRequest.getPriceIdList());
		return BaseResponse.SUCCESSFUL();
	}

}

