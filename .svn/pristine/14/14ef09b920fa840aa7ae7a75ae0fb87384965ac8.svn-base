package com.wanmi.sbc.marketing.api.provider.communitystockorder;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.marketing.api.request.communitystockorder.CommunityStockOrderAddRequest;
import com.wanmi.sbc.marketing.api.request.communitystockorder.CommunityStockOrderDelByActivityIdRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>社区团购备货单保存服务Provider</p>
 * @author dyt
 * @date 2023-08-03 14:05:20
 */
@FeignClient(value = "${application.marketing.name}", contextId = "CommunityStockOrderProvider")
public interface CommunityStockOrderProvider {

	/**
	 * 新增社区团购备货单API
	 *
	 * @author dyt
	 * @param request 社区团购备货单新增参数结构 {@link CommunityStockOrderAddRequest}
	 * @return 操作结果 {@link BaseResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/communitystockorder/add")
	BaseResponse add(@RequestBody @Valid CommunityStockOrderAddRequest request);

	/**
	 * 单个删除社区团购备货单API
	 *
	 * @author dyt
	 * @param request 单个删除参数结构 {@link CommunityStockOrderDelByActivityIdRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/communitystockorder/delete-by-activity-id")
	BaseResponse deleteByActivityId(@RequestBody @Valid CommunityStockOrderDelByActivityIdRequest request);

}

