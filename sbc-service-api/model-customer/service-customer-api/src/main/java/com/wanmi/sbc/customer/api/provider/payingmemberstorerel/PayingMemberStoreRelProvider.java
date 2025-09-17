package com.wanmi.sbc.customer.api.provider.payingmemberstorerel;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.customer.api.request.payingmemberstorerel.PayingMemberStoreRelAddRequest;
import com.wanmi.sbc.customer.api.response.payingmemberstorerel.PayingMemberStoreRelAddResponse;
import com.wanmi.sbc.customer.api.request.payingmemberstorerel.PayingMemberStoreRelModifyRequest;
import com.wanmi.sbc.customer.api.response.payingmemberstorerel.PayingMemberStoreRelModifyResponse;
import com.wanmi.sbc.customer.api.request.payingmemberstorerel.PayingMemberStoreRelDelByIdRequest;
import com.wanmi.sbc.customer.api.request.payingmemberstorerel.PayingMemberStoreRelDelByIdListRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

/**
 * <p>商家与付费会员等级关联表保存服务Provider</p>
 * @author zhanghao
 * @date 2022-05-13 13:41:04
 */
@FeignClient(value = "${application.customer.name}", contextId = "PayingMemberStoreRelProvider")
public interface PayingMemberStoreRelProvider {

	/**
	 * 新增商家与付费会员等级关联表API
	 *
	 * @author zhanghao
	 * @param payingMemberStoreRelAddRequest 商家与付费会员等级关联表新增参数结构 {@link PayingMemberStoreRelAddRequest}
	 * @return 新增的商家与付费会员等级关联表信息 {@link PayingMemberStoreRelAddResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/payingmemberstorerel/add")
	BaseResponse<PayingMemberStoreRelAddResponse> add(@RequestBody @Valid PayingMemberStoreRelAddRequest payingMemberStoreRelAddRequest);

	/**
	 * 修改商家与付费会员等级关联表API
	 *
	 * @author zhanghao
	 * @param payingMemberStoreRelModifyRequest 商家与付费会员等级关联表修改参数结构 {@link PayingMemberStoreRelModifyRequest}
	 * @return 修改的商家与付费会员等级关联表信息 {@link PayingMemberStoreRelModifyResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/payingmemberstorerel/modify")
	BaseResponse<PayingMemberStoreRelModifyResponse> modify(@RequestBody @Valid PayingMemberStoreRelModifyRequest payingMemberStoreRelModifyRequest);

	/**
	 * 单个删除商家与付费会员等级关联表API
	 *
	 * @author zhanghao
	 * @param payingMemberStoreRelDelByIdRequest 单个删除参数结构 {@link PayingMemberStoreRelDelByIdRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/payingmemberstorerel/delete-by-id")
	BaseResponse deleteById(@RequestBody @Valid PayingMemberStoreRelDelByIdRequest payingMemberStoreRelDelByIdRequest);

	/**
	 * 批量删除商家与付费会员等级关联表API
	 *
	 * @author zhanghao
	 * @param payingMemberStoreRelDelByIdListRequest 批量删除参数结构 {@link PayingMemberStoreRelDelByIdListRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/payingmemberstorerel/delete-by-id-list")
	BaseResponse deleteByIdList(@RequestBody @Valid PayingMemberStoreRelDelByIdListRequest payingMemberStoreRelDelByIdListRequest);

}

