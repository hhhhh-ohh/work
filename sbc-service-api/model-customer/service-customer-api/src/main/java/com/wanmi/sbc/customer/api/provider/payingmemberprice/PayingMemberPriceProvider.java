package com.wanmi.sbc.customer.api.provider.payingmemberprice;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.customer.api.request.payingmemberprice.PayingMemberPriceAddRequest;
import com.wanmi.sbc.customer.api.response.payingmemberprice.PayingMemberPriceAddResponse;
import com.wanmi.sbc.customer.api.request.payingmemberprice.PayingMemberPriceModifyRequest;
import com.wanmi.sbc.customer.api.response.payingmemberprice.PayingMemberPriceModifyResponse;
import com.wanmi.sbc.customer.api.request.payingmemberprice.PayingMemberPriceDelByIdRequest;
import com.wanmi.sbc.customer.api.request.payingmemberprice.PayingMemberPriceDelByIdListRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

/**
 * <p>付费设置表保存服务Provider</p>
 * @author zhanghao
 * @date 2022-05-13 13:40:30
 */
@FeignClient(value = "${application.customer.name}", contextId = "PayingMemberPriceProvider")
public interface PayingMemberPriceProvider {

	/**
	 * 新增付费设置表API
	 *
	 * @author zhanghao
	 * @param payingMemberPriceAddRequest 付费设置表新增参数结构 {@link PayingMemberPriceAddRequest}
	 * @return 新增的付费设置表信息 {@link PayingMemberPriceAddResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/payingmemberprice/add")
	BaseResponse add(@RequestBody @Valid PayingMemberPriceAddRequest payingMemberPriceAddRequest);

	/**
	 * 修改付费设置表API
	 *
	 * @author zhanghao
	 * @param payingMemberPriceModifyRequest 付费设置表修改参数结构 {@link PayingMemberPriceModifyRequest}
	 * @return 修改的付费设置表信息 {@link PayingMemberPriceModifyResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/payingmemberprice/modify")
	BaseResponse<PayingMemberPriceModifyResponse> modify(@RequestBody @Valid PayingMemberPriceModifyRequest payingMemberPriceModifyRequest);

	/**
	 * 单个删除付费设置表API
	 *
	 * @author zhanghao
	 * @param payingMemberPriceDelByIdRequest 单个删除参数结构 {@link PayingMemberPriceDelByIdRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/payingmemberprice/delete-by-id")
	BaseResponse deleteById(@RequestBody @Valid PayingMemberPriceDelByIdRequest payingMemberPriceDelByIdRequest);

	/**
	 * 批量删除付费设置表API
	 *
	 * @author zhanghao
	 * @param payingMemberPriceDelByIdListRequest 批量删除参数结构 {@link PayingMemberPriceDelByIdListRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/payingmemberprice/delete-by-id-list")
	BaseResponse deleteByIdList(@RequestBody @Valid PayingMemberPriceDelByIdListRequest payingMemberPriceDelByIdListRequest);

}

