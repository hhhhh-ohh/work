package com.wanmi.sbc.setting.api.provider.storemessagenodesetting;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.setting.api.request.storemessagenodesetting.StoreMessageNodeSettingByIdRequest;
import com.wanmi.sbc.setting.api.request.storemessagenodesetting.StoreMessageNodeSettingByStoreIdRequest;
import com.wanmi.sbc.setting.api.request.storemessagenodesetting.StoreMessageNodeSettingDelByIdRequest;
import com.wanmi.sbc.setting.api.request.storemessagenodesetting.StoreMessageNodeSettingListRequest;
import com.wanmi.sbc.setting.api.response.storemessagenodesetting.StoreMessageNodeSettingByIdResponse;
import com.wanmi.sbc.setting.api.response.storemessagenodesetting.StoreMessageNodeSettingListResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>商家消息节点设置查询服务Provider</p>
 * @author 马连峰
 * @date 2022-07-11 09:42:56
 */
@FeignClient(value = "${application.setting.name}", contextId = "StoreMessageNodeSettingQueryProvider")
public interface StoreMessageNodeSettingQueryProvider {

	/**
	 * 列表查询商家消息节点设置API
	 *
	 * @author 马连峰
	 * @param storeMessageNodeSettingListReq 列表请求参数和筛选对象 {@link StoreMessageNodeSettingListRequest}
	 * @return 商家消息节点设置的列表信息 {@link StoreMessageNodeSettingListResponse}
	 */
	@PostMapping("/setting/${application.setting.version}/storemessagenodesetting/list")
	BaseResponse<StoreMessageNodeSettingListResponse> list(@RequestBody @Valid StoreMessageNodeSettingListRequest storeMessageNodeSettingListReq);

	/**
	 * 单个查询商家消息节点设置API
	 *
	 * @author 马连峰
	 * @param storeMessageNodeSettingByIdRequest 单个查询商家消息节点设置请求参数 {@link StoreMessageNodeSettingByIdRequest}
	 * @return 商家消息节点设置详情 {@link StoreMessageNodeSettingByIdResponse}
	 */
	@PostMapping("/setting/${application.setting.version}/storemessagenodesetting/get-by-id")
	BaseResponse<StoreMessageNodeSettingByIdResponse> getById(@RequestBody @Valid StoreMessageNodeSettingByIdRequest storeMessageNodeSettingByIdRequest);

	@PostMapping("/setting/${application.setting.version}/storemessagenodesetting/get-warning-stock")
	BaseResponse<StoreMessageNodeSettingByIdResponse> getWarningStock (@RequestBody @Valid StoreMessageNodeSettingByStoreIdRequest request);
}

