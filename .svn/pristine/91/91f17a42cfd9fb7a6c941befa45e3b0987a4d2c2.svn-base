package com.wanmi.sbc.message.api.provider.storemessagedetail;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.message.api.request.storemessagedetail.StoreMessageDetailPageRequest;
import com.wanmi.sbc.message.api.request.storemessagedetail.StoreMessageDetailTopListRequest;
import com.wanmi.sbc.message.api.response.storemessagedetail.StoreMessageDetailPageResponse;
import com.wanmi.sbc.message.api.request.storemessagedetail.StoreMessageDetailListRequest;
import com.wanmi.sbc.message.api.response.storemessagedetail.StoreMessageDetailListResponse;
import com.wanmi.sbc.message.api.request.storemessagedetail.StoreMessageDetailByIdRequest;
import com.wanmi.sbc.message.api.response.storemessagedetail.StoreMessageDetailByIdResponse;
import com.wanmi.sbc.message.api.response.storemessagedetail.StoreMessageDetailTopListResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

/**
 * <p>商家消息/公告查询服务Provider</p>
 * @author 马连峰
 * @date 2022-07-05 10:52:24
 */
@FeignClient(value = "${application.message.name}", contextId = "StoreMessageDetailQueryProvider")
public interface StoreMessageDetailQueryProvider {

	/**
	 * 分页查询商家消息/公告API
	 *
	 * @author 马连峰
	 * @param storeMessageDetailPageReq 分页请求参数和筛选对象 {@link StoreMessageDetailPageRequest}
	 * @return 商家消息/公告分页列表信息 {@link StoreMessageDetailPageResponse}
	 */
	@PostMapping("/message/${application.sms.version}/storemessagedetail/page")
	BaseResponse<StoreMessageDetailPageResponse> page(@RequestBody @Valid StoreMessageDetailPageRequest storeMessageDetailPageReq);

	/**
	 * 列表查询商家消息/公告API
	 *
	 * @author 马连峰
	 * @param topListRequest 列表请求参数和筛选对象 {@link StoreMessageDetailTopListRequest}
	 * @return 商家消息/公告的列表信息 {@link StoreMessageDetailTopListResponse}
	 */
	@PostMapping("/message/${application.sms.version}/storemessagedetail/top-list")
	BaseResponse<StoreMessageDetailTopListResponse> topList(@RequestBody @Valid StoreMessageDetailTopListRequest topListRequest);

	/**
	 * 列表查询商家消息/公告API
	 *
	 * @author 马连峰
	 * @param storeMessageDetailListReq 列表请求参数和筛选对象 {@link StoreMessageDetailListRequest}
	 * @return 商家消息/公告的列表信息 {@link StoreMessageDetailListResponse}
	 */
	@PostMapping("/message/${application.sms.version}/storemessagedetail/list")
	BaseResponse<StoreMessageDetailListResponse> list(@RequestBody @Valid StoreMessageDetailListRequest storeMessageDetailListReq);

	/**
	 * 单个查询商家消息/公告API
	 *
	 * @author 马连峰
	 * @param storeMessageDetailByIdRequest 单个查询商家消息/公告请求参数 {@link StoreMessageDetailByIdRequest}
	 * @return 商家消息/公告详情 {@link StoreMessageDetailByIdResponse}
	 */
	@PostMapping("/message/${application.sms.version}/storemessagedetail/get-by-id")
	BaseResponse<StoreMessageDetailByIdResponse> getById(@RequestBody @Valid StoreMessageDetailByIdRequest storeMessageDetailByIdRequest);

}

