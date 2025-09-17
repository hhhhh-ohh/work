package com.wanmi.sbc.order.api.provider.payingmemberpayrecord;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.order.api.request.payingmemberpayrecord.PayingMemberPayRecordAddRequest;
import com.wanmi.sbc.order.api.response.payingmemberpayrecord.PayingMemberPayRecordAddResponse;
import com.wanmi.sbc.order.api.request.payingmemberpayrecord.PayingMemberPayRecordModifyRequest;
import com.wanmi.sbc.order.api.response.payingmemberpayrecord.PayingMemberPayRecordModifyResponse;
import com.wanmi.sbc.order.api.request.payingmemberpayrecord.PayingMemberPayRecordDelByIdRequest;
import com.wanmi.sbc.order.api.request.payingmemberpayrecord.PayingMemberPayRecordDelByIdListRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

/**
 * <p>付费会员支付记录表保存服务Provider</p>
 * @author zhanghao
 * @date 2022-05-13 15:29:08
 */
@FeignClient(value = "${application.order.name}", contextId = "PayingMemberPayRecordProvider")
public interface PayingMemberPayRecordProvider {

	/**
	 * 新增付费会员支付记录表API
	 *
	 * @author zhanghao
	 * @param payingMemberPayRecordAddRequest 付费会员支付记录表新增参数结构 {@link PayingMemberPayRecordAddRequest}
	 * @return 新增的付费会员支付记录表信息 {@link PayingMemberPayRecordAddResponse}
	 */
	@PostMapping("/order/${application.order.version}/payingmemberpayrecord/add")
	BaseResponse<PayingMemberPayRecordAddResponse> add(@RequestBody @Valid PayingMemberPayRecordAddRequest payingMemberPayRecordAddRequest);

	/**
	 * 修改付费会员支付记录表API
	 *
	 * @author zhanghao
	 * @param payingMemberPayRecordModifyRequest 付费会员支付记录表修改参数结构 {@link PayingMemberPayRecordModifyRequest}
	 * @return 修改的付费会员支付记录表信息 {@link PayingMemberPayRecordModifyResponse}
	 */
	@PostMapping("/order/${application.order.version}/payingmemberpayrecord/modify")
	BaseResponse<PayingMemberPayRecordModifyResponse> modify(@RequestBody @Valid PayingMemberPayRecordModifyRequest payingMemberPayRecordModifyRequest);

	/**
	 * 单个删除付费会员支付记录表API
	 *
	 * @author zhanghao
	 * @param payingMemberPayRecordDelByIdRequest 单个删除参数结构 {@link PayingMemberPayRecordDelByIdRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/order/${application.order.version}/payingmemberpayrecord/delete-by-id")
	BaseResponse deleteById(@RequestBody @Valid PayingMemberPayRecordDelByIdRequest payingMemberPayRecordDelByIdRequest);

	/**
	 * 批量删除付费会员支付记录表API
	 *
	 * @author zhanghao
	 * @param payingMemberPayRecordDelByIdListRequest 批量删除参数结构 {@link PayingMemberPayRecordDelByIdListRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/order/${application.order.version}/payingmemberpayrecord/delete-by-id-list")
	BaseResponse deleteByIdList(@RequestBody @Valid PayingMemberPayRecordDelByIdListRequest payingMemberPayRecordDelByIdListRequest);

}

