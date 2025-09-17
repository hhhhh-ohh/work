package com.wanmi.sbc.setting.api.provider.storemessagenode;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.setting.api.request.storemessagenode.StoreMessageNodeByIdRequest;
import com.wanmi.sbc.setting.api.request.storemessagenode.StoreMessageNodeListRequest;
import com.wanmi.sbc.setting.api.request.storemessagenode.StoreMessageNodeSettingModifyRequest;
import com.wanmi.sbc.setting.api.response.storemessagenode.StoreMessageNodeByIdResponse;
import com.wanmi.sbc.setting.api.response.storemessagenode.StoreMessageNodeListResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>商家消息节点查询服务Provider</p>
 * @author 马连峰
 * @date 2022-07-11 09:41:21
 */
@FeignClient(value = "${application.setting.name}", contextId = "StoreMessageNodeQueryProvider")
public interface StoreMessageNodeQueryProvider {

	/**
	 * 列表查询商家消息节点API
	 *
	 * @author 马连峰
	 * @param storeMessageNodeListReq 列表请求参数和筛选对象 {@link StoreMessageNodeListRequest}
	 * @return 商家消息节点的列表信息 {@link StoreMessageNodeListResponse}
	 */
	@PostMapping("/setting/${application.setting.version}/storemessagenode/list")
	BaseResponse<StoreMessageNodeListResponse> list(@RequestBody @Valid StoreMessageNodeListRequest storeMessageNodeListReq);

	/**
	 * 单个查询商家消息节点API
	 *
	 * @author 马连峰
	 * @param storeMessageNodeByIdRequest 单个查询商家消息节点请求参数 {@link StoreMessageNodeByIdRequest}
	 * @return 商家消息节点详情 {@link StoreMessageNodeByIdResponse}
	 */
	@PostMapping("/setting/${application.setting.version}/storemessagenode/get-by-id")
	BaseResponse<StoreMessageNodeByIdResponse> getById(@RequestBody @Valid StoreMessageNodeByIdRequest storeMessageNodeByIdRequest);

}

