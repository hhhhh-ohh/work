package com.wanmi.sbc.marketing.api.provider.communitystockorder;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.marketing.api.request.communitystockorder.CommunityStockOrderPageRequest;
import com.wanmi.sbc.marketing.api.response.communitystockorder.CommunityStockOrderPageResponse;
import com.wanmi.sbc.marketing.api.request.communitystockorder.CommunityStockOrderListRequest;
import com.wanmi.sbc.marketing.api.response.communitystockorder.CommunityStockOrderListResponse;
import com.wanmi.sbc.marketing.api.request.communitystockorder.CommunityStockOrderByIdRequest;
import com.wanmi.sbc.marketing.api.response.communitystockorder.CommunityStockOrderByIdResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

/**
 * <p>社区团购备货单查询服务Provider</p>
 * @author dyt
 * @date 2023-08-03 14:05:20
 */
@FeignClient(value = "${application.marketing.name}", contextId = "CommunityStockOrderQueryProvider")
public interface CommunityStockOrderQueryProvider {

	/**
	 * 分页查询社区团购备货单API
	 *
	 * @author dyt
	 * @param communityStockOrderPageReq 分页请求参数和筛选对象 {@link CommunityStockOrderPageRequest}
	 * @return 社区团购备货单分页列表信息 {@link CommunityStockOrderPageResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/communitystockorder/page")
	BaseResponse<CommunityStockOrderPageResponse> page(@RequestBody @Valid CommunityStockOrderPageRequest communityStockOrderPageReq);

	/**
	 * 列表查询社区团购备货单API
	 *
	 * @author dyt
	 * @param communityStockOrderListReq 列表请求参数和筛选对象 {@link CommunityStockOrderListRequest}
	 * @return 社区团购备货单的列表信息 {@link CommunityStockOrderListResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/communitystockorder/list")
	BaseResponse<CommunityStockOrderListResponse> list(@RequestBody @Valid CommunityStockOrderListRequest communityStockOrderListReq);

	/**
	 * 单个查询社区团购备货单API
	 *
	 * @author dyt
	 * @param communityStockOrderByIdRequest 单个查询社区团购备货单请求参数 {@link CommunityStockOrderByIdRequest}
	 * @return 社区团购备货单详情 {@link CommunityStockOrderByIdResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/communitystockorder/get-by-id")
	BaseResponse<CommunityStockOrderByIdResponse> getById(@RequestBody @Valid CommunityStockOrderByIdRequest communityStockOrderByIdRequest);

}

