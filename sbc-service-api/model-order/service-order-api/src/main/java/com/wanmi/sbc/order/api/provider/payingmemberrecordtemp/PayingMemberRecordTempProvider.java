package com.wanmi.sbc.order.api.provider.payingmemberrecordtemp;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.order.api.request.payingmemberrecordtemp.PayingMemberRecordTempAddRequest;
import com.wanmi.sbc.order.api.response.payingmemberrecordtemp.PayingMemberRecordTempAddResponse;
import com.wanmi.sbc.order.api.request.payingmemberrecordtemp.PayingMemberRecordTempModifyRequest;
import com.wanmi.sbc.order.api.response.payingmemberrecordtemp.PayingMemberRecordTempModifyResponse;
import com.wanmi.sbc.order.api.request.payingmemberrecordtemp.PayingMemberRecordTempDelByIdRequest;
import com.wanmi.sbc.order.api.request.payingmemberrecordtemp.PayingMemberRecordTempDelByIdListRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

/**
 * <p>付费记录临时表保存服务Provider</p>
 * @author zhanghao
 * @date 2022-05-13 15:28:45
 */
@FeignClient(value = "${application.order.name}", contextId = "PayingMemberRecordTempProvider")
public interface PayingMemberRecordTempProvider {

	/**
	 * 新增付费记录临时表API
	 *
	 * @author zhanghao
	 * @param payingMemberRecordTempAddRequest 付费记录临时表新增参数结构 {@link PayingMemberRecordTempAddRequest}
	 * @return 新增的付费记录临时表信息 {@link PayingMemberRecordTempAddResponse}
	 */
	@PostMapping("/order/${application.order.version}/payingmemberrecordtemp/add")
	BaseResponse<PayingMemberRecordTempAddResponse> add(@RequestBody @Valid PayingMemberRecordTempAddRequest payingMemberRecordTempAddRequest);

	/**
	 * 修改付费记录临时表API
	 *
	 * @author zhanghao
	 * @param payingMemberRecordTempModifyRequest 付费记录临时表修改参数结构 {@link PayingMemberRecordTempModifyRequest}
	 * @return 修改的付费记录临时表信息 {@link PayingMemberRecordTempModifyResponse}
	 */
	@PostMapping("/order/${application.order.version}/payingmemberrecordtemp/modify")
	BaseResponse<PayingMemberRecordTempModifyResponse> modify(@RequestBody @Valid PayingMemberRecordTempModifyRequest payingMemberRecordTempModifyRequest);

	/**
	 * 单个删除付费记录临时表API
	 *
	 * @author zhanghao
	 * @param payingMemberRecordTempDelByIdRequest 单个删除参数结构 {@link PayingMemberRecordTempDelByIdRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/order/${application.order.version}/payingmemberrecordtemp/delete-by-id")
	BaseResponse deleteById(@RequestBody @Valid PayingMemberRecordTempDelByIdRequest payingMemberRecordTempDelByIdRequest);

	/**
	 * 批量删除付费记录临时表API
	 *
	 * @author zhanghao
	 * @param payingMemberRecordTempDelByIdListRequest 批量删除参数结构 {@link PayingMemberRecordTempDelByIdListRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/order/${application.order.version}/payingmemberrecordtemp/delete-by-id-list")
	BaseResponse deleteByIdList(@RequestBody @Valid PayingMemberRecordTempDelByIdListRequest payingMemberRecordTempDelByIdListRequest);

}

