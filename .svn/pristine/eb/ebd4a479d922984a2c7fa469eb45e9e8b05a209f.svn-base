package com.wanmi.sbc.customer.api.provider.payingmemberrecommendrel;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.customer.api.request.payingmemberrecommendrel.PayingMemberRecommendRelAddRequest;
import com.wanmi.sbc.customer.api.response.payingmemberrecommendrel.PayingMemberRecommendRelAddResponse;
import com.wanmi.sbc.customer.api.request.payingmemberrecommendrel.PayingMemberRecommendRelModifyRequest;
import com.wanmi.sbc.customer.api.response.payingmemberrecommendrel.PayingMemberRecommendRelModifyResponse;
import com.wanmi.sbc.customer.api.request.payingmemberrecommendrel.PayingMemberRecommendRelDelByIdRequest;
import com.wanmi.sbc.customer.api.request.payingmemberrecommendrel.PayingMemberRecommendRelDelByIdListRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

/**
 * <p>推荐商品与付费会员等级关联表保存服务Provider</p>
 * @author zhanghao
 * @date 2022-05-13 13:41:51
 */
@FeignClient(value = "${application.customer.name}", contextId = "PayingMemberRecommendRelProvider")
public interface PayingMemberRecommendRelProvider {

	/**
	 * 新增推荐商品与付费会员等级关联表API
	 *
	 * @author zhanghao
	 * @param payingMemberRecommendRelAddRequest 推荐商品与付费会员等级关联表新增参数结构 {@link PayingMemberRecommendRelAddRequest}
	 * @return 新增的推荐商品与付费会员等级关联表信息 {@link PayingMemberRecommendRelAddResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/payingmemberrecommendrel/add")
	BaseResponse<PayingMemberRecommendRelAddResponse> add(@RequestBody @Valid PayingMemberRecommendRelAddRequest payingMemberRecommendRelAddRequest);

	/**
	 * 修改推荐商品与付费会员等级关联表API
	 *
	 * @author zhanghao
	 * @param payingMemberRecommendRelModifyRequest 推荐商品与付费会员等级关联表修改参数结构 {@link PayingMemberRecommendRelModifyRequest}
	 * @return 修改的推荐商品与付费会员等级关联表信息 {@link PayingMemberRecommendRelModifyResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/payingmemberrecommendrel/modify")
	BaseResponse<PayingMemberRecommendRelModifyResponse> modify(@RequestBody @Valid PayingMemberRecommendRelModifyRequest payingMemberRecommendRelModifyRequest);

	/**
	 * 单个删除推荐商品与付费会员等级关联表API
	 *
	 * @author zhanghao
	 * @param payingMemberRecommendRelDelByIdRequest 单个删除参数结构 {@link PayingMemberRecommendRelDelByIdRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/payingmemberrecommendrel/delete-by-id")
	BaseResponse deleteById(@RequestBody @Valid PayingMemberRecommendRelDelByIdRequest payingMemberRecommendRelDelByIdRequest);

	/**
	 * 批量删除推荐商品与付费会员等级关联表API
	 *
	 * @author zhanghao
	 * @param payingMemberRecommendRelDelByIdListRequest 批量删除参数结构 {@link PayingMemberRecommendRelDelByIdListRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/payingmemberrecommendrel/delete-by-id-list")
	BaseResponse deleteByIdList(@RequestBody @Valid PayingMemberRecommendRelDelByIdListRequest payingMemberRecommendRelDelByIdListRequest);

}

