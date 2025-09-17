package com.wanmi.sbc.marketing.api.provider.communitydeliveryorder;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.marketing.api.request.communitydeliveryorder.CommunityDeliveryOrderAddRequest;
import com.wanmi.sbc.marketing.api.request.communitydeliveryorder.CommunityDeliveryOrderModifyRequest;
import com.wanmi.sbc.marketing.api.request.communitydeliveryorder.CommunityDeliveryOrderDelByActivityIdRequest;
import com.wanmi.sbc.marketing.api.request.communitydeliveryorder.CommunityDeliveryOrderDeliveryByIdListRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

/**
 * <p>社区团购发货单保存服务Provider</p>
 * @author dyt
 * @date 2023-08-03 16:23:20
 */
@FeignClient(value = "${application.marketing.name}", contextId = "CommunityDeliveryOrderProvider")
public interface CommunityDeliveryOrderProvider {

	/**
	 * 新增社区团购发货单API
	 *
	 * @author dyt
	 * @param communityDeliveryOrderAddRequest 社区团购发货单新增参数结构 {@link CommunityDeliveryOrderAddRequest}
	 * @return 操作结果 {@link BaseResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/communitydeliveryorder/add")
	BaseResponse add(@RequestBody @Valid CommunityDeliveryOrderAddRequest communityDeliveryOrderAddRequest);

	/**
	 * 修改社区团购发货单API
	 *
	 * @author dyt
	 * @param communityDeliveryOrderModifyRequest 社区团购发货单修改参数结构 {@link CommunityDeliveryOrderModifyRequest}
	 * @return 操作结果 {@link BaseResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/communitydeliveryorder/modify")
	BaseResponse modify(@RequestBody @Valid CommunityDeliveryOrderModifyRequest communityDeliveryOrderModifyRequest);

	/**
	 * 单个删除社区团购发货单API
	 *
	 * @author dyt
	 * @param communityDeliveryOrderDelByActivityIdRequest 单个删除参数结构 {@link CommunityDeliveryOrderDelByActivityIdRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/communitydeliveryorder/delete-by-id")
	BaseResponse deleteByActivityId(@RequestBody @Valid CommunityDeliveryOrderDelByActivityIdRequest communityDeliveryOrderDelByActivityIdRequest);

	/**
	 * 批量删除社区团购发货单API
	 *
	 * @author dyt
	 * @param communityDeliveryOrderDelByIdListRequest 批量删除参数结构 {@link CommunityDeliveryOrderDeliveryByIdListRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/communitydeliveryorder/delivery-by-id-list")
	BaseResponse modifyDeliveryStatusByActivityId(@RequestBody @Valid CommunityDeliveryOrderDeliveryByIdListRequest communityDeliveryOrderDelByIdListRequest);

}

