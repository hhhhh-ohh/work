package com.wanmi.sbc.order.api.provider.payingmemberrecord;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.order.api.request.payingmemberrecord.*;
import com.wanmi.sbc.order.api.response.payingmemberrecord.PayingMemberRecordAddResponse;
import com.wanmi.sbc.order.api.response.payingmemberrecord.PayingMemberRecordModifyResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

/**
 * <p>付费记录表保存服务Provider</p>
 * @author zhanghao
 * @date 2022-05-13 15:27:53
 */
@FeignClient(value = "${application.order.name}", contextId = "PayingMemberRecordProvider")
public interface PayingMemberRecordProvider {

	/**
	 * 新增付费记录表API
	 *
	 * @author zhanghao
	 * @param payingMemberRecordAddRequest 付费记录表新增参数结构 {@link PayingMemberRecordAddRequest}
	 * @return 新增的付费记录表信息 {@link PayingMemberRecordAddResponse}
	 */
	@PostMapping("/order/${application.order.version}/payingmemberrecord/add")
	BaseResponse<PayingMemberRecordAddResponse> add(@RequestBody @Valid PayingMemberRecordAddRequest payingMemberRecordAddRequest);

	/**
	 * 修改付费记录表API
	 *
	 * @author zhanghao
	 * @param payingMemberRecordModifyRequest 付费记录表修改参数结构 {@link PayingMemberRecordModifyRequest}
	 * @return 修改的付费记录表信息 {@link PayingMemberRecordModifyResponse}
	 */
	@PostMapping("/order/${application.order.version}/payingmemberrecord/modify")
	BaseResponse<PayingMemberRecordModifyResponse> modify(@RequestBody @Valid PayingMemberRecordModifyRequest payingMemberRecordModifyRequest);

	/**
	 * 单个删除付费记录表API
	 *
	 * @author zhanghao
	 * @param payingMemberRecordDelByIdRequest 单个删除参数结构 {@link PayingMemberRecordDelByIdRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/order/${application.order.version}/payingmemberrecord/delete-by-id")
	BaseResponse deleteById(@RequestBody @Valid PayingMemberRecordDelByIdRequest payingMemberRecordDelByIdRequest);

	/**
	 * 批量删除付费记录表API
	 *
	 * @author zhanghao
	 * @param payingMemberRecordDelByIdListRequest 批量删除参数结构 {@link PayingMemberRecordDelByIdListRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/order/${application.order.version}/payingmemberrecord/delete-by-id-list")
	BaseResponse deleteByIdList(@RequestBody @Valid PayingMemberRecordDelByIdListRequest payingMemberRecordDelByIdListRequest);


	/**
	 * 每月X号发券API
	 *
	 * @author xuyunpeng
	 * @param
	 * @return 结果 {@link BaseResponse}
	 */
	@PostMapping("/order/${application.order.version}/payingmemberrecord/rights-coupon")
	BaseResponse rightsCoupon();


	/**
	 *	付费会员退款
	 *
	 * @author zhanghao
	 * @param payingMemberRecordModifyRequest 付费记录表修改参数结构 {@link PayingMemberRecordModifyRequest}
	 * @return 付费会员退款 {@link PayingMemberRecordModifyResponse}
	 */
	@PostMapping("/order/${application.order.version}/payingmemberrecord/refund-paying-member")
	BaseResponse refundPayingMember(@RequestBody PayingMemberRecordModifyRequest payingMemberRecordModifyRequest);



	/**
	 *	付费会员退款
	 *
	 * @author zhanghao
	 * @param payingMemberRecordModifyRequest 付费记录表修改参数结构 {@link PayingMemberRecordModifyRequest}
	 * @return 付费会员退款 {@link PayingMemberRecordModifyResponse}
	 */
	@PostMapping("/order/${application.order.version}/payingmemberrecord/auto-update-state")
	BaseResponse autoUpdateState(@RequestBody PayingMemberRecordListRequest payingMemberRecordModifyRequest);




}

