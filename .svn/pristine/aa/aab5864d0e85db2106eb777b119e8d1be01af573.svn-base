package com.wanmi.sbc.marketing.api.provider.communitydeliveryorder;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.marketing.api.request.communitydeliveryorder.CommunityDeliveryOrderPageRequest;
import com.wanmi.sbc.marketing.api.response.communitydeliveryorder.CommunityDeliveryOrderPageResponse;
import com.wanmi.sbc.marketing.api.request.communitydeliveryorder.CommunityDeliveryOrderListRequest;
import com.wanmi.sbc.marketing.api.response.communitydeliveryorder.CommunityDeliveryOrderListResponse;
import com.wanmi.sbc.marketing.api.request.communitydeliveryorder.CommunityDeliveryOrderByIdRequest;
import com.wanmi.sbc.marketing.api.response.communitydeliveryorder.CommunityDeliveryOrderByIdResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

/**
 * <p>社区团购发货单查询服务Provider</p>
 * @author dyt
 * @date 2023-08-03 16:23:20
 */
@FeignClient(value = "${application.marketing.name}", contextId = "CommunityDeliveryOrderQueryProvider")
public interface CommunityDeliveryOrderQueryProvider {

	/**
	 * 分页查询社区团购发货单API
	 *
	 * @author dyt
	 * @param communityDeliveryOrderPageReq 分页请求参数和筛选对象 {@link CommunityDeliveryOrderPageRequest}
	 * @return 社区团购发货单分页列表信息 {@link CommunityDeliveryOrderPageResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/communitydeliveryorder/page")
	BaseResponse<CommunityDeliveryOrderPageResponse> page(@RequestBody @Valid CommunityDeliveryOrderPageRequest communityDeliveryOrderPageReq);

	/**
	 * 列表查询社区团购发货单API
	 *
	 * @author dyt
	 * @param communityDeliveryOrderListReq 列表请求参数和筛选对象 {@link CommunityDeliveryOrderListRequest}
	 * @return 社区团购发货单的列表信息 {@link CommunityDeliveryOrderListResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/communitydeliveryorder/list")
	BaseResponse<CommunityDeliveryOrderListResponse> list(@RequestBody @Valid CommunityDeliveryOrderListRequest communityDeliveryOrderListReq);

	/**
	 * 单个查询社区团购发货单API
	 *
	 * @author dyt
	 * @param communityDeliveryOrderByIdRequest 单个查询社区团购发货单请求参数 {@link CommunityDeliveryOrderByIdRequest}
	 * @return 社区团购发货单详情 {@link CommunityDeliveryOrderByIdResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/communitydeliveryorder/get-by-id")
	BaseResponse<CommunityDeliveryOrderByIdResponse> getById(@RequestBody @Valid CommunityDeliveryOrderByIdRequest communityDeliveryOrderByIdRequest);

}

