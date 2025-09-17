package com.wanmi.sbc.message.api.provider.storenoticescope;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.message.api.request.storenoticescope.StoreNoticeScopeAddRequest;
import com.wanmi.sbc.message.api.response.storenoticescope.StoreNoticeScopeAddResponse;
import com.wanmi.sbc.message.api.request.storenoticescope.StoreNoticeScopeModifyRequest;
import com.wanmi.sbc.message.api.response.storenoticescope.StoreNoticeScopeModifyResponse;
import com.wanmi.sbc.message.api.request.storenoticescope.StoreNoticeScopeDelByIdRequest;
import com.wanmi.sbc.message.api.request.storenoticescope.StoreNoticeScopeDelByIdListRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

/**
 * <p>商家公告发送范围保存服务Provider</p>
 * @author 马连峰
 * @date 2022-07-05 10:11:33
 */
@FeignClient(value = "${application.message.name}", contextId = "StoreNoticeScopeProvider")
public interface StoreNoticeScopeProvider {

	/**
	 * 新增商家公告发送范围API
	 *
	 * @author 马连峰
	 * @param storeNoticeScopeAddRequest 商家公告发送范围新增参数结构 {@link StoreNoticeScopeAddRequest}
	 * @return 新增的商家公告发送范围信息 {@link StoreNoticeScopeAddResponse}
	 */
	@PostMapping("/message/${application.sms.version}/storenoticescope/add")
	BaseResponse<StoreNoticeScopeAddResponse> add(@RequestBody @Valid StoreNoticeScopeAddRequest storeNoticeScopeAddRequest);

	/**
	 * 修改商家公告发送范围API
	 *
	 * @author 马连峰
	 * @param storeNoticeScopeModifyRequest 商家公告发送范围修改参数结构 {@link StoreNoticeScopeModifyRequest}
	 * @return 修改的商家公告发送范围信息 {@link StoreNoticeScopeModifyResponse}
	 */
	@PostMapping("/message/${application.sms.version}/storenoticescope/modify")
	BaseResponse<StoreNoticeScopeModifyResponse> modify(@RequestBody @Valid StoreNoticeScopeModifyRequest storeNoticeScopeModifyRequest);

	/**
	 * 单个删除商家公告发送范围API
	 *
	 * @author 马连峰
	 * @param storeNoticeScopeDelByIdRequest 单个删除参数结构 {@link StoreNoticeScopeDelByIdRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/message/${application.sms.version}/storenoticescope/delete-by-id")
	BaseResponse deleteById(@RequestBody @Valid StoreNoticeScopeDelByIdRequest storeNoticeScopeDelByIdRequest);

	/**
	 * 批量删除商家公告发送范围API
	 *
	 * @author 马连峰
	 * @param storeNoticeScopeDelByIdListRequest 批量删除参数结构 {@link StoreNoticeScopeDelByIdListRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/message/${application.sms.version}/storenoticescope/delete-by-id-list")
	BaseResponse deleteByIdList(@RequestBody @Valid StoreNoticeScopeDelByIdListRequest storeNoticeScopeDelByIdListRequest);

}

