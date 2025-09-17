package com.wanmi.sbc.marketing.provider.impl.communitydeliveryorder;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.marketing.api.provider.communitydeliveryorder.CommunityDeliveryOrderQueryProvider;
import com.wanmi.sbc.marketing.api.request.communitydeliveryorder.CommunityDeliveryOrderPageRequest;
import com.wanmi.sbc.marketing.api.request.communitydeliveryorder.CommunityDeliveryOrderQueryRequest;
import com.wanmi.sbc.marketing.api.response.communitydeliveryorder.CommunityDeliveryOrderPageResponse;
import com.wanmi.sbc.marketing.api.request.communitydeliveryorder.CommunityDeliveryOrderListRequest;
import com.wanmi.sbc.marketing.api.response.communitydeliveryorder.CommunityDeliveryOrderListResponse;
import com.wanmi.sbc.marketing.api.request.communitydeliveryorder.CommunityDeliveryOrderByIdRequest;
import com.wanmi.sbc.marketing.api.response.communitydeliveryorder.CommunityDeliveryOrderByIdResponse;
import com.wanmi.sbc.marketing.bean.vo.CommunityDeliveryOrderVO;
import com.wanmi.sbc.marketing.communitydeliveryorder.service.CommunityDeliveryOrderService;
import com.wanmi.sbc.marketing.communitydeliveryorder.model.root.CommunityDeliveryOrder;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>社区团购发货单查询服务接口实现</p>
 * @author dyt
 * @date 2023-08-03 16:23:20
 */
@RestController
@Validated
public class CommunityDeliveryOrderQueryController implements CommunityDeliveryOrderQueryProvider {
	@Autowired
	private CommunityDeliveryOrderService communityDeliveryOrderService;

	@Override
	public BaseResponse<CommunityDeliveryOrderPageResponse> page(@RequestBody @Valid CommunityDeliveryOrderPageRequest communityDeliveryOrderPageReq) {
		CommunityDeliveryOrderQueryRequest queryReq = KsBeanUtil.convert(communityDeliveryOrderPageReq, CommunityDeliveryOrderQueryRequest.class);
		Page<CommunityDeliveryOrder> communityDeliveryOrderPage = communityDeliveryOrderService.page(queryReq);
		Page<CommunityDeliveryOrderVO> newPage = communityDeliveryOrderPage.map(entity -> communityDeliveryOrderService.wrapperVo(entity));
		MicroServicePage<CommunityDeliveryOrderVO> microPage = new MicroServicePage<>(newPage, communityDeliveryOrderPageReq.getPageable());
		CommunityDeliveryOrderPageResponse finalRes = new CommunityDeliveryOrderPageResponse(microPage);
		return BaseResponse.success(finalRes);
	}

	@Override
	public BaseResponse<CommunityDeliveryOrderListResponse> list(@RequestBody @Valid CommunityDeliveryOrderListRequest communityDeliveryOrderListReq) {
		CommunityDeliveryOrderQueryRequest queryReq = KsBeanUtil.convert(communityDeliveryOrderListReq, CommunityDeliveryOrderQueryRequest.class);
		List<CommunityDeliveryOrder> communityDeliveryOrderList = communityDeliveryOrderService.list(queryReq);
		List<CommunityDeliveryOrderVO> newList = communityDeliveryOrderList.stream().map(entity -> communityDeliveryOrderService.wrapperVo(entity)).collect(Collectors.toList());
		return BaseResponse.success(new CommunityDeliveryOrderListResponse(newList));
	}

	@Override
	public BaseResponse<CommunityDeliveryOrderByIdResponse> getById(@RequestBody @Valid CommunityDeliveryOrderByIdRequest communityDeliveryOrderByIdRequest) {
		CommunityDeliveryOrder communityDeliveryOrder =
		communityDeliveryOrderService.getOne(communityDeliveryOrderByIdRequest.getId());
		return BaseResponse.success(new CommunityDeliveryOrderByIdResponse(communityDeliveryOrderService.wrapperVo(communityDeliveryOrder)));
	}

}

