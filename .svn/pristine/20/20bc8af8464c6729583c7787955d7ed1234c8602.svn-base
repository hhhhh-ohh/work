package com.wanmi.sbc.customer.api.provider.payingmemberrightsrel;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.customer.api.request.payingmemberrightsrel.PayingMemberRightsRelAddRequest;
import com.wanmi.sbc.customer.api.response.payingmemberrightsrel.PayingMemberRightsRelAddResponse;
import com.wanmi.sbc.customer.api.request.payingmemberrightsrel.PayingMemberRightsRelModifyRequest;
import com.wanmi.sbc.customer.api.response.payingmemberrightsrel.PayingMemberRightsRelModifyResponse;
import com.wanmi.sbc.customer.api.request.payingmemberrightsrel.PayingMemberRightsRelDelByIdRequest;
import com.wanmi.sbc.customer.api.request.payingmemberrightsrel.PayingMemberRightsRelDelByIdListRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

/**
 * <p>权益与付费会员等级关联表保存服务Provider</p>
 * @author zhanghao
 * @date 2022-05-13 13:41:21
 */
@FeignClient(value = "${application.customer.name}", contextId = "PayingMemberRightsRelProvider")
public interface PayingMemberRightsRelProvider {

	/**
	 * 新增权益与付费会员等级关联表API
	 *
	 * @author zhanghao
	 * @param payingMemberRightsRelAddRequest 权益与付费会员等级关联表新增参数结构 {@link PayingMemberRightsRelAddRequest}
	 * @return 新增的权益与付费会员等级关联表信息 {@link PayingMemberRightsRelAddResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/payingmemberrightsrel/add")
	BaseResponse<PayingMemberRightsRelAddResponse> add(@RequestBody @Valid PayingMemberRightsRelAddRequest payingMemberRightsRelAddRequest);

	/**
	 * 修改权益与付费会员等级关联表API
	 *
	 * @author zhanghao
	 * @param payingMemberRightsRelModifyRequest 权益与付费会员等级关联表修改参数结构 {@link PayingMemberRightsRelModifyRequest}
	 * @return 修改的权益与付费会员等级关联表信息 {@link PayingMemberRightsRelModifyResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/payingmemberrightsrel/modify")
	BaseResponse<PayingMemberRightsRelModifyResponse> modify(@RequestBody @Valid PayingMemberRightsRelModifyRequest payingMemberRightsRelModifyRequest);

	/**
	 * 单个删除权益与付费会员等级关联表API
	 *
	 * @author zhanghao
	 * @param payingMemberRightsRelDelByIdRequest 单个删除参数结构 {@link PayingMemberRightsRelDelByIdRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/payingmemberrightsrel/delete-by-id")
	BaseResponse deleteById(@RequestBody @Valid PayingMemberRightsRelDelByIdRequest payingMemberRightsRelDelByIdRequest);

	/**
	 * 批量删除权益与付费会员等级关联表API
	 *
	 * @author zhanghao
	 * @param payingMemberRightsRelDelByIdListRequest 批量删除参数结构 {@link PayingMemberRightsRelDelByIdListRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/payingmemberrightsrel/delete-by-id-list")
	BaseResponse deleteByIdList(@RequestBody @Valid PayingMemberRightsRelDelByIdListRequest payingMemberRightsRelDelByIdListRequest);

}

