package com.wanmi.sbc.message.api.provider.storenoticesend;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.message.api.request.storenoticesend.StoreNoticeSendByIdRequest;
import com.wanmi.sbc.message.api.request.storenoticesend.StoreNoticeSendListRequest;
import com.wanmi.sbc.message.api.request.storenoticesend.StoreNoticeSendPageRequest;
import com.wanmi.sbc.message.api.response.storenoticesend.StoreNoticeSendByIdResponse;
import com.wanmi.sbc.message.api.response.storenoticesend.StoreNoticeSendListResponse;
import com.wanmi.sbc.message.api.response.storenoticesend.StoreNoticeSendPageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

/**
 * <p>商家公告查询服务Provider</p>
 * @author 马连峰
 * @date 2022-07-04 10:56:58
 */
@FeignClient(value = "${application.message.name}", contextId = "StoreNoticeSendQueryProvider")
public interface StoreNoticeSendQueryProvider {

	/**
	 * 分页查询商家公告API
	 *
	 * @author 马连峰
	 * @param storeNoticeSendPageReq 分页请求参数和筛选对象 {@link StoreNoticeSendPageRequest}
	 * @return 商家公告分页列表信息 {@link StoreNoticeSendPageResponse}
	 */
	@PostMapping("/sms/${application.sms.version}/storenoticesend/page")
	BaseResponse<StoreNoticeSendPageResponse> page(@RequestBody @Valid StoreNoticeSendPageRequest storeNoticeSendPageReq);

	/**
	 * 列表查询商家公告API
	 *
	 * @author 马连峰
	 * @param storeNoticeSendListReq 列表请求参数和筛选对象 {@link StoreNoticeSendListRequest}
	 * @return 商家公告的列表信息 {@link StoreNoticeSendListResponse}
	 */
	@PostMapping("/sms/${application.sms.version}/storenoticesend/list")
	BaseResponse<StoreNoticeSendListResponse> list(@RequestBody @Valid StoreNoticeSendListRequest storeNoticeSendListReq);

	/**
	 * 单个查询商家公告API
	 *
	 * @author 马连峰
	 * @param storeNoticeSendByIdRequest 单个查询商家公告请求参数 {@link StoreNoticeSendByIdRequest}
	 * @return 商家公告详情 {@link StoreNoticeSendByIdResponse}
	 */
	@PostMapping("/sms/${application.sms.version}/storenoticesend/get-by-id")
	BaseResponse<StoreNoticeSendByIdResponse> getById(@RequestBody @Valid StoreNoticeSendByIdRequest storeNoticeSendByIdRequest);
}

