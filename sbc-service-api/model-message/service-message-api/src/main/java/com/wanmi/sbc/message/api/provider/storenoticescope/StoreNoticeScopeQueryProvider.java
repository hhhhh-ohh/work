package com.wanmi.sbc.message.api.provider.storenoticescope;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.message.api.request.storenoticescope.StoreNoticeScopePageRequest;
import com.wanmi.sbc.message.api.response.storenoticescope.StoreNoticeScopePageResponse;
import com.wanmi.sbc.message.api.request.storenoticescope.StoreNoticeScopeListRequest;
import com.wanmi.sbc.message.api.response.storenoticescope.StoreNoticeScopeListResponse;
import com.wanmi.sbc.message.api.request.storenoticescope.StoreNoticeScopeByIdRequest;
import com.wanmi.sbc.message.api.response.storenoticescope.StoreNoticeScopeByIdResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

/**
 * <p>商家公告发送范围查询服务Provider</p>
 * @author 马连峰
 * @date 2022-07-05 10:11:33
 */
@FeignClient(value = "${application.message.name}", contextId = "StoreNoticeScopeQueryProvider")
public interface StoreNoticeScopeQueryProvider {

	/**
	 * 分页查询商家公告发送范围API
	 *
	 * @author 马连峰
	 * @param storeNoticeScopePageReq 分页请求参数和筛选对象 {@link StoreNoticeScopePageRequest}
	 * @return 商家公告发送范围分页列表信息 {@link StoreNoticeScopePageResponse}
	 */
	@PostMapping("/message/${application.sms.version}/storenoticescope/page")
	BaseResponse<StoreNoticeScopePageResponse> page(@RequestBody @Valid StoreNoticeScopePageRequest storeNoticeScopePageReq);

	/**
	 * 列表查询商家公告发送范围API
	 *
	 * @author 马连峰
	 * @param storeNoticeScopeListReq 列表请求参数和筛选对象 {@link StoreNoticeScopeListRequest}
	 * @return 商家公告发送范围的列表信息 {@link StoreNoticeScopeListResponse}
	 */
	@PostMapping("/message/${application.sms.version}/storenoticescope/list")
	BaseResponse<StoreNoticeScopeListResponse> list(@RequestBody @Valid StoreNoticeScopeListRequest storeNoticeScopeListReq);

	/**
	 * 单个查询商家公告发送范围API
	 *
	 * @author 马连峰
	 * @param storeNoticeScopeByIdRequest 单个查询商家公告发送范围请求参数 {@link StoreNoticeScopeByIdRequest}
	 * @return 商家公告发送范围详情 {@link StoreNoticeScopeByIdResponse}
	 */
	@PostMapping("/message/${application.sms.version}/storenoticescope/get-by-id")
	BaseResponse<StoreNoticeScopeByIdResponse> getById(@RequestBody @Valid StoreNoticeScopeByIdRequest storeNoticeScopeByIdRequest);

}

