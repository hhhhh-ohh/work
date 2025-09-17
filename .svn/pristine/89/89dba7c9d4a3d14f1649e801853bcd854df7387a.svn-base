package com.wanmi.sbc.marketing.provider.impl.communitystockorder;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.marketing.api.provider.communitystockorder.CommunityStockOrderProvider;
import com.wanmi.sbc.marketing.api.request.communitystockorder.CommunityStockOrderAddRequest;
import com.wanmi.sbc.marketing.api.request.communitystockorder.CommunityStockOrderDelByActivityIdRequest;
import com.wanmi.sbc.marketing.communitystockorder.service.CommunityStockOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * <p>社区团购备货单保存服务接口实现</p>
 * @author dyt
 * @date 2023-08-03 14:05:20
 */
@RestController
@Validated
public class CommunityStockOrderController implements CommunityStockOrderProvider {
	@Autowired
	private CommunityStockOrderService communityStockOrderService;

	@Override
	public BaseResponse add(@RequestBody @Valid CommunityStockOrderAddRequest request) {
		communityStockOrderService.add(request);
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse deleteByActivityId(@RequestBody @Valid CommunityStockOrderDelByActivityIdRequest request) {
		communityStockOrderService.deleteByActivityId(request.getActivityId());
		return BaseResponse.SUCCESSFUL();
	}
}

