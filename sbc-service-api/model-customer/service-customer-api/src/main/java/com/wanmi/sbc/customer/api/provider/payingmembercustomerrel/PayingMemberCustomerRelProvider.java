package com.wanmi.sbc.customer.api.provider.payingmembercustomerrel;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.customer.api.request.payingmembercustomerrel.*;
import com.wanmi.sbc.customer.api.response.payingmembercustomerrel.PayingMemberCustomerRelAddResponse;
import com.wanmi.sbc.customer.api.response.payingmembercustomerrel.PayingMemberCustomerRelModifyResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>客户与付费会员等级关联表保存服务Provider</p>
 * @author zhanghao
 * @date 2022-05-13 13:40:48
 */
@FeignClient(value = "${application.customer.name}", contextId = "PayingMemberCustomerRelProvider")
public interface PayingMemberCustomerRelProvider {

	/**
	 * 新增客户与付费会员等级关联表API
	 *
	 * @author zhanghao
	 * @param payingMemberCustomerRelAddRequest 客户与付费会员等级关联表新增参数结构 {@link PayingMemberCustomerRelAddRequest}
	 * @return 新增的客户与付费会员等级关联表信息 {@link PayingMemberCustomerRelAddResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/payingmembercustomerrel/add")
	BaseResponse<PayingMemberCustomerRelAddResponse> add(@RequestBody @Valid PayingMemberCustomerRelAddRequest payingMemberCustomerRelAddRequest);

	/**
	 * 修改客户与付费会员等级关联表API
	 *
	 * @author zhanghao
	 * @param payingMemberCustomerRelModifyRequest 客户与付费会员等级关联表修改参数结构 {@link PayingMemberCustomerRelModifyRequest}
	 * @return 修改的客户与付费会员等级关联表信息 {@link PayingMemberCustomerRelModifyResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/payingmembercustomerrel/modify")
	BaseResponse<PayingMemberCustomerRelModifyResponse> modify(@RequestBody @Valid PayingMemberCustomerRelModifyRequest payingMemberCustomerRelModifyRequest);


	/**
	 * 修改客户与付费会员等级关联 过期时间
	 *
	 * @author zhanghao
	 * @param payingMemberCustomerRelModifyRequest 客户与付费会员等级关联表修改参数结构 {@link PayingMemberCustomerRelModifyRequest}
	 * @return 修改的客户与付费会员等级关联表信息 {@link}
	 */
	@PostMapping("/customer/${application.customer.version}/payingmembercustomerrel/update_expiration_date")
	BaseResponse updateExpirationDate(@RequestBody @Valid PayingMemberCustomerRelModifyRequest payingMemberCustomerRelModifyRequest);
	/**
	 * 单个删除客户与付费会员等级关联表API
	 *
	 * @author zhanghao
	 * @param payingMemberCustomerRelDelByIdRequest 单个删除参数结构 {@link PayingMemberCustomerRelDelByIdRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/payingmembercustomerrel/delete-by-id")
	BaseResponse deleteById(@RequestBody @Valid PayingMemberCustomerRelDelByIdRequest payingMemberCustomerRelDelByIdRequest);

	/**
	 * 批量删除客户与付费会员等级关联表API
	 *
	 * @author zhanghao
	 * @param payingMemberCustomerRelDelByIdListRequest 批量删除参数结构 {@link PayingMemberCustomerRelDelByIdListRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/payingmembercustomerrel/delete-by-id-list")
	BaseResponse deleteByIdList(@RequestBody @Valid PayingMemberCustomerRelDelByIdListRequest payingMemberCustomerRelDelByIdListRequest);

	/**
	 * 修改付费会员总优惠金额API
	 *
	 * @author zhanghao
	 * @param payingMemberUpdateDiscountRequest 参数结构 {@link PayingMemberUpdateDiscountRequest}
	 * @return 结果 {@link BaseResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/payingmembercustomerrel/update-customer-discount")
	BaseResponse updateCustomerDiscount(@RequestBody @Valid PayingMemberUpdateDiscountRequest payingMemberUpdateDiscountRequest);

	@PostMapping("/customer/${application.customer.version}/payingmembercustomerrel/degradation")
	BaseResponse degradation(PayingMemberDegradationRequest  request);

	@PostMapping("/customer/${application.customer.version}/payingmembercustomerrel/upgrade")
	BaseResponse upgrade(PayingMemberUpgradeRequest request);

	@PostMapping("/customer/${application.customer.version}/payingmembercustomerrel/assign")
	BaseResponse assign(PayingMemberAssignRequest request);
}

