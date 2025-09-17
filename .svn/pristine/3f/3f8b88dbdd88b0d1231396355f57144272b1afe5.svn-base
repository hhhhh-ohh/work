package com.wanmi.sbc.marketing.provider.impl.communitydeliveryorder;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.marketing.api.provider.communitydeliveryorder.CommunityDeliveryOrderProvider;
import com.wanmi.sbc.marketing.api.request.communitydeliveryorder.CommunityDeliveryOrderAddRequest;
import com.wanmi.sbc.marketing.api.request.communitydeliveryorder.CommunityDeliveryOrderModifyRequest;
import com.wanmi.sbc.marketing.api.request.communitydeliveryorder.CommunityDeliveryOrderDelByActivityIdRequest;
import com.wanmi.sbc.marketing.api.request.communitydeliveryorder.CommunityDeliveryOrderDeliveryByIdListRequest;
import com.wanmi.sbc.marketing.communitydeliveryorder.service.CommunityDeliveryOrderService;
import com.wanmi.sbc.marketing.communitydeliveryorder.model.root.CommunityDeliveryOrder;

import jakarta.validation.Valid;

/**
 * <p>社区团购发货单保存服务接口实现</p>
 * @author dyt
 * @date 2023-08-03 16:23:20
 */
@RestController
@Validated
public class CommunityDeliveryOrderController implements CommunityDeliveryOrderProvider {
	@Autowired
	private CommunityDeliveryOrderService communityDeliveryOrderService;

	@Override
	public BaseResponse add(@RequestBody @Valid CommunityDeliveryOrderAddRequest communityDeliveryOrderAddRequest) {
		communityDeliveryOrderService.add(communityDeliveryOrderAddRequest);
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse modify(@RequestBody @Valid CommunityDeliveryOrderModifyRequest communityDeliveryOrderModifyRequest) {
		CommunityDeliveryOrder communityDeliveryOrder = KsBeanUtil.convert(communityDeliveryOrderModifyRequest, CommunityDeliveryOrder.class);
		communityDeliveryOrderService.modify(communityDeliveryOrder);
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse deleteByActivityId(@RequestBody @Valid CommunityDeliveryOrderDelByActivityIdRequest communityDeliveryOrderDelByActivityIdRequest) {
		communityDeliveryOrderService.deleteByActivityId(communityDeliveryOrderDelByActivityIdRequest.getActivityId());
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse modifyDeliveryStatusByActivityId(@RequestBody @Valid CommunityDeliveryOrderDeliveryByIdListRequest communityDeliveryOrderDeliveryByIdListRequest) {
		communityDeliveryOrderService.updateDeliveryStatusByActivityId(communityDeliveryOrderDeliveryByIdListRequest.getIdList());
		return BaseResponse.SUCCESSFUL();
	}

}

