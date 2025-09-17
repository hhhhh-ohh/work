package com.wanmi.sbc.marketing.provider.impl.communitystockorder;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.marketing.api.provider.communitystockorder.CommunityStockOrderQueryProvider;
import com.wanmi.sbc.marketing.api.request.communitystockorder.CommunityStockOrderPageRequest;
import com.wanmi.sbc.marketing.api.request.communitystockorder.CommunityStockOrderQueryRequest;
import com.wanmi.sbc.marketing.api.response.communitystockorder.CommunityStockOrderPageResponse;
import com.wanmi.sbc.marketing.api.request.communitystockorder.CommunityStockOrderListRequest;
import com.wanmi.sbc.marketing.api.response.communitystockorder.CommunityStockOrderListResponse;
import com.wanmi.sbc.marketing.api.request.communitystockorder.CommunityStockOrderByIdRequest;
import com.wanmi.sbc.marketing.api.response.communitystockorder.CommunityStockOrderByIdResponse;
import com.wanmi.sbc.marketing.bean.vo.CommunityStockOrderVO;
import com.wanmi.sbc.marketing.communitystockorder.service.CommunityStockOrderService;
import com.wanmi.sbc.marketing.communitystockorder.model.root.CommunityStockOrder;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>社区团购备货单查询服务接口实现</p>
 * @author dyt
 * @date 2023-08-03 14:05:20
 */
@RestController
@Validated
public class CommunityStockOrderQueryController implements CommunityStockOrderQueryProvider {
	@Autowired
	private CommunityStockOrderService communityStockOrderService;

	@Override
	public BaseResponse<CommunityStockOrderPageResponse> page(@RequestBody @Valid CommunityStockOrderPageRequest communityStockOrderPageReq) {
		CommunityStockOrderQueryRequest queryReq = KsBeanUtil.convert(communityStockOrderPageReq, CommunityStockOrderQueryRequest.class);
		Page<CommunityStockOrder> communityStockOrderPage = communityStockOrderService.page(queryReq);
		Page<CommunityStockOrderVO> newPage = communityStockOrderPage.map(entity -> communityStockOrderService.wrapperVo(entity));
		MicroServicePage<CommunityStockOrderVO> microPage = new MicroServicePage<>(newPage, communityStockOrderPageReq.getPageable());
		CommunityStockOrderPageResponse finalRes = new CommunityStockOrderPageResponse(microPage);
		return BaseResponse.success(finalRes);
	}

	@Override
	public BaseResponse<CommunityStockOrderListResponse> list(@RequestBody @Valid CommunityStockOrderListRequest communityStockOrderListReq) {
		CommunityStockOrderQueryRequest queryReq = KsBeanUtil.convert(communityStockOrderListReq, CommunityStockOrderQueryRequest.class);
		List<CommunityStockOrder> communityStockOrderList = communityStockOrderService.list(queryReq);
		List<CommunityStockOrderVO> newList = communityStockOrderList.stream().map(entity -> communityStockOrderService.wrapperVo(entity)).collect(Collectors.toList());
		return BaseResponse.success(new CommunityStockOrderListResponse(newList));
	}

	@Override
	public BaseResponse<CommunityStockOrderByIdResponse> getById(@RequestBody @Valid CommunityStockOrderByIdRequest communityStockOrderByIdRequest) {
		CommunityStockOrder communityStockOrder =
		communityStockOrderService.getOne(communityStockOrderByIdRequest.getId());
		return BaseResponse.success(new CommunityStockOrderByIdResponse(communityStockOrderService.wrapperVo(communityStockOrder)));
	}

}

