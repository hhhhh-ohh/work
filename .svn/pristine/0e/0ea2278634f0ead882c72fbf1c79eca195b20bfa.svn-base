package com.wanmi.sbc.customer.api.provider.payingmemberdiscountrel;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.customer.api.request.payingmemberdiscountrel.PayingMemberDiscountRelAddRequest;
import com.wanmi.sbc.customer.api.response.payingmemberdiscountrel.PayingMemberDiscountRelAddResponse;
import com.wanmi.sbc.customer.api.request.payingmemberdiscountrel.PayingMemberDiscountRelModifyRequest;
import com.wanmi.sbc.customer.api.response.payingmemberdiscountrel.PayingMemberDiscountRelModifyResponse;
import com.wanmi.sbc.customer.api.request.payingmemberdiscountrel.PayingMemberDiscountRelDelByIdRequest;
import com.wanmi.sbc.customer.api.request.payingmemberdiscountrel.PayingMemberDiscountRelDelByIdListRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

/**
 * <p>折扣商品与付费会员等级关联表保存服务Provider</p>
 * @author zhanghao
 * @date 2022-05-13 13:41:36
 */
@FeignClient(value = "${application.customer.name}", contextId = "PayingMemberDiscountRelProvider")
public interface PayingMemberDiscountRelProvider {

	/**
	 * 新增折扣商品与付费会员等级关联表API
	 *
	 * @author zhanghao
	 * @param payingMemberDiscountRelAddRequest 折扣商品与付费会员等级关联表新增参数结构 {@link PayingMemberDiscountRelAddRequest}
	 * @return 新增的折扣商品与付费会员等级关联表信息 {@link PayingMemberDiscountRelAddResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/payingmemberdiscountrel/add")
	BaseResponse<PayingMemberDiscountRelAddResponse> add(@RequestBody @Valid PayingMemberDiscountRelAddRequest payingMemberDiscountRelAddRequest);

	/**
	 * 修改折扣商品与付费会员等级关联表API
	 *
	 * @author zhanghao
	 * @param payingMemberDiscountRelModifyRequest 折扣商品与付费会员等级关联表修改参数结构 {@link PayingMemberDiscountRelModifyRequest}
	 * @return 修改的折扣商品与付费会员等级关联表信息 {@link PayingMemberDiscountRelModifyResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/payingmemberdiscountrel/modify")
	BaseResponse<PayingMemberDiscountRelModifyResponse> modify(@RequestBody @Valid PayingMemberDiscountRelModifyRequest payingMemberDiscountRelModifyRequest);

	/**
	 * 单个删除折扣商品与付费会员等级关联表API
	 *
	 * @author zhanghao
	 * @param payingMemberDiscountRelDelByIdRequest 单个删除参数结构 {@link PayingMemberDiscountRelDelByIdRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/payingmemberdiscountrel/delete-by-id")
	BaseResponse deleteById(@RequestBody @Valid PayingMemberDiscountRelDelByIdRequest payingMemberDiscountRelDelByIdRequest);

	/**
	 * 批量删除折扣商品与付费会员等级关联表API
	 *
	 * @author zhanghao
	 * @param payingMemberDiscountRelDelByIdListRequest 批量删除参数结构 {@link PayingMemberDiscountRelDelByIdListRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/payingmemberdiscountrel/delete-by-id-list")
	BaseResponse deleteByIdList(@RequestBody @Valid PayingMemberDiscountRelDelByIdListRequest payingMemberDiscountRelDelByIdListRequest);

}

