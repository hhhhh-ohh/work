package com.wanmi.sbc.customer.api.provider.payingmemberlevel;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.customer.api.request.payingmemberlevel.*;
import com.wanmi.sbc.customer.api.response.payingmemberlevel.PayingMemberLevelAddResponse;
import com.wanmi.sbc.customer.api.response.payingmemberlevel.PayingMemberLevelModifyResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>付费会员等级表保存服务Provider</p>
 * @author zhanghao
 * @date 2022-05-13 11:42:42
 */
@FeignClient(value = "${application.customer.name}", contextId = "PayingMemberLevelProvider")
public interface PayingMemberLevelProvider {

	/**
	 * 新增付费会员等级表API
	 *
	 * @author zhanghao
	 * @param payingMemberLevelAddRequest 付费会员等级表新增参数结构 {@link PayingMemberLevelAddRequest}
	 * @return 新增的付费会员等级表信息 {@link PayingMemberLevelAddResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/payingmemberlevel/add")
	BaseResponse add(@RequestBody @Valid PayingMemberLevelAddRequest payingMemberLevelAddRequest);

	/**
	 * 修改付费会员等级表API
	 *
	 * @author zhanghao
	 * @param payingMemberLevelModifyRequest 付费会员等级表修改参数结构 {@link PayingMemberLevelModifyRequest}
	 * @return 修改的付费会员等级表信息 {@link PayingMemberLevelModifyResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/payingmemberlevel/modify")
	BaseResponse<PayingMemberLevelModifyResponse> modify(@RequestBody @Valid PayingMemberLevelModifyRequest payingMemberLevelModifyRequest);

	/**
	 * 单个删除付费会员等级表API
	 *
	 * @author zhanghao
	 * @param payingMemberLevelDelByIdRequest 单个删除参数结构 {@link PayingMemberLevelDelByIdRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/payingmemberlevel/delete-by-id")
	BaseResponse deleteById(@RequestBody @Valid PayingMemberLevelDelByIdRequest payingMemberLevelDelByIdRequest);

	/**
	 * 批量删除付费会员等级表API
	 *
	 * @author zhanghao
	 * @param payingMemberLevelDelByIdListRequest 批量删除参数结构 {@link PayingMemberLevelDelByIdListRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/payingmemberlevel/delete-by-id-list")
	BaseResponse deleteByIdList(@RequestBody @Valid PayingMemberLevelDelByIdListRequest payingMemberLevelDelByIdListRequest);

	/**
	 * 付费会员等级状态更改API
	 *
	 * @author xuyunpeng
	 * @param payingMemberLevelStatusRequest 等级状态更改参数结构 {@link PayingMemberLevelStatusRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/payingmemberlevel/modify-status")
	BaseResponse modifyStatus(@RequestBody @Valid PayingMemberLevelStatusRequest payingMemberLevelStatusRequest);

}

