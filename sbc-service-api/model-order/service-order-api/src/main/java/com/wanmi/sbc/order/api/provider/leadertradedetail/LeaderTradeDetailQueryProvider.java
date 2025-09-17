package com.wanmi.sbc.order.api.provider.leadertradedetail;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.order.api.request.leadertradedetail.*;
import com.wanmi.sbc.order.api.response.leadertradedetail.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>团长订单查询服务Provider</p>
 * @author Bob
 * @date 2023-08-03 14:16:52
 */
@FeignClient(value = "${application.order.name}", contextId = "LeaderTradeDetailQueryProvider")
public interface LeaderTradeDetailQueryProvider {

	/**
	 * 分页查询团长订单API
	 *
	 * @author Bob
	 * @param leaderTradeDetailPageReq 分页请求参数和筛选对象 {@link LeaderTradeDetailPageRequest}
	 * @return 团长订单分页列表信息 {@link LeaderTradeDetailPageResponse}
	 */
	@PostMapping("/order/${application.order.version}/leadertradedetail/page")
	BaseResponse<LeaderTradeDetailPageResponse> page(@RequestBody @Valid LeaderTradeDetailPageRequest leaderTradeDetailPageReq);

	/**
	 * 列表查询团长订单API
	 *
	 * @author Bob
	 * @param leaderTradeDetailListReq 列表请求参数和筛选对象 {@link LeaderTradeDetailListRequest}
	 * @return 团长订单的列表信息 {@link LeaderTradeDetailListResponse}
	 */
	@PostMapping("/order/${application.order.version}/leadertradedetail/list")
	BaseResponse<LeaderTradeDetailListResponse> list(@RequestBody @Valid LeaderTradeDetailListRequest leaderTradeDetailListReq);

	/**
	 * 单个查询团长订单API
	 *
	 * @author Bob
	 * @param leaderTradeDetailByIdRequest 单个查询团长订单请求参数 {@link LeaderTradeDetailByIdRequest}
	 * @return 团长订单详情 {@link LeaderTradeDetailByIdResponse}
	 */
	@PostMapping("/order/${application.order.version}/leadertradedetail/get-by-id")
	BaseResponse<LeaderTradeDetailByIdResponse> getById(@RequestBody @Valid LeaderTradeDetailByIdRequest leaderTradeDetailByIdRequest);


	/**
	 * 查询团长的跟团人数（支付成功+帮卖+去重）
	 * @param request
	 * @return
	 */
	@PostMapping("/order/${application.order.version}/leadertradedetail/get-follow-num")
	BaseResponse<Long> findFollowNum(@RequestBody @Valid LeaderTradeDetailListRequest request);

	/**
	 * 跟团记录按活动分组做分页查询API
	 *
	 * @author dyt
	 * @param request 跟团记录按活动分组做分页查询请求参数 {@link LeaderTradeDetailTopGroupByActivityRequest}
	 * @return 团长订单列表信息 {@link LeaderTradeDetailTopGroupByActivityResponse}
	 */
	@PostMapping("/order/${application.order.version}/leadertradedetail/top-group-activity")
	BaseResponse<LeaderTradeDetailTopGroupByActivityResponse> topGroupActivity(@RequestBody @Valid LeaderTradeDetailTopGroupByActivityRequest request);

	/**
	 * 跟团记录按活动分组做分页查询API
	 *
	 * @author dyt
	 * @param request 跟团记录按活动分组做统计跟团次数查询请求参数 {@link LeaderTradeDetailTradeCountGroupByActivityRequest}
	 * @return 跟团记录跟团次数信息 {@link LeaderTradeDetailTradeCountGroupByActivityResponse}
	 */
	@PostMapping("/order/${application.order.version}/leadertradedetail/count-trade-group-activity")
	BaseResponse<LeaderTradeDetailTradeCountGroupByActivityResponse> countTradeGroupActivity(@RequestBody @Valid LeaderTradeDetailTradeCountGroupByActivityRequest request);

	/**
	 * 按活动分组做成团人数查询API
	 *
	 * @author dyt
	 * @param request 按活动分组做成团人数请求参数 {@link LeaderTradeDetailTradeCountLeaderRequest}
	 * @return 按活动分组做成团人数信息 {@link LeaderTradeDetailTradeCountLeaderResponse}
	 */
	@PostMapping("/order/${application.order.version}/leadertradedetail/count-leader-group-activity")
	BaseResponse<LeaderTradeDetailTradeCountLeaderResponse> countLeaderGroupActivity(@RequestBody @Valid LeaderTradeDetailTradeCountLeaderRequest request);

	/**
	 * 按活动分组做参团人数（去重）查询API
	 *
	 * @author dyt
	 * @param request 按活动分组做参团人数（去重）请求参数 {@link LeaderTradeDetailTradeCountLeaderRequest}
	 * @return 按活动分组做参团人数（去重）信息 {@link LeaderTradeDetailTradeCountLeaderResponse}
	 */
	@PostMapping("/order/${application.order.version}/leadertradedetail/count-customer-group-activity")
	BaseResponse<LeaderTradeDetailTradeCountCustomerResponse> countCustomerGroupActivity(@RequestBody @Valid LeaderTradeDetailTradeCountCustomerRequest request);
}

