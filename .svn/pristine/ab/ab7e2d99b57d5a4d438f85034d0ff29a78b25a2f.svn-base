package com.wanmi.sbc.order.api.provider.leadertradedetail;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.order.api.request.leadertradedetail.LeaderTradeDetailAddRequest;
import com.wanmi.sbc.order.api.request.leadertradedetail.LeaderTradeDetailDelByIdListRequest;
import com.wanmi.sbc.order.api.request.leadertradedetail.LeaderTradeDetailDelByIdRequest;
import com.wanmi.sbc.order.api.request.leadertradedetail.LeaderTradeDetailModifyRequest;
import com.wanmi.sbc.order.api.response.leadertradedetail.LeaderTradeDetailAddResponse;
import com.wanmi.sbc.order.api.response.leadertradedetail.LeaderTradeDetailModifyResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>团长订单保存服务Provider</p>
 * @author Bob
 * @date 2023-08-03 14:16:52
 */
@FeignClient(value = "${application.order.name}", contextId = "LeaderTradeDetailProvider")
public interface LeaderTradeDetailProvider {

	/**
	 * 新增团长订单API
	 *
	 * @author Bob
	 * @param leaderTradeDetailAddRequest 团长订单新增参数结构 {@link LeaderTradeDetailAddRequest}
	 * @return 新增的团长订单信息 {@link LeaderTradeDetailAddResponse}
	 */
	@PostMapping("/order/${application.order.version}/leadertradedetail/add")
	BaseResponse<LeaderTradeDetailAddResponse> add(@RequestBody @Valid LeaderTradeDetailAddRequest leaderTradeDetailAddRequest);

	/**
	 * 修改团长订单API
	 *
	 * @author Bob
	 * @param leaderTradeDetailModifyRequest 团长订单修改参数结构 {@link LeaderTradeDetailModifyRequest}
	 * @return 修改的团长订单信息 {@link LeaderTradeDetailModifyResponse}
	 */
	@PostMapping("/order/${application.order.version}/leadertradedetail/modify")
	BaseResponse<LeaderTradeDetailModifyResponse> modify(@RequestBody @Valid LeaderTradeDetailModifyRequest leaderTradeDetailModifyRequest);

	/**
	 * 单个删除团长订单API
	 *
	 * @author Bob
	 * @param leaderTradeDetailDelByIdRequest 单个删除参数结构 {@link LeaderTradeDetailDelByIdRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/order/${application.order.version}/leadertradedetail/delete-by-id")
	BaseResponse deleteById(@RequestBody @Valid LeaderTradeDetailDelByIdRequest leaderTradeDetailDelByIdRequest);

	/**
	 * 批量删除团长订单API
	 *
	 * @author Bob
	 * @param leaderTradeDetailDelByIdListRequest 批量删除参数结构 {@link LeaderTradeDetailDelByIdListRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/order/${application.order.version}/leadertradedetail/delete-by-id-list")
	BaseResponse deleteByIdList(@RequestBody @Valid LeaderTradeDetailDelByIdListRequest leaderTradeDetailDelByIdListRequest);

}

