package com.wanmi.sbc.setting.api.provider.storemessagenode;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.setting.api.request.storemessagenode.StoreMessageNodeAddRequest;
import com.wanmi.sbc.setting.api.request.storemessagenode.StoreMessageNodeDelByIdListRequest;
import com.wanmi.sbc.setting.api.request.storemessagenode.StoreMessageNodeDelByIdRequest;
import com.wanmi.sbc.setting.api.response.storemessagenode.StoreMessageNodeAddResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>商家消息节点保存服务Provider</p>
 * @author 马连峰
 * @date 2022-07-11 09:41:21
 */
@FeignClient(value = "${application.setting.name}", contextId = "StoreMessageNodeProvider")
public interface StoreMessageNodeProvider {

	/**
	 * 新增商家消息节点API
	 *
	 * @author 马连峰
	 * @param storeMessageNodeAddRequest 商家消息节点新增参数结构 {@link StoreMessageNodeAddRequest}
	 * @return 新增的商家消息节点信息 {@link StoreMessageNodeAddResponse}
	 */
	@PostMapping("/setting/${application.setting.version}/storemessagenode/add")
	BaseResponse<StoreMessageNodeAddResponse> add(@RequestBody @Valid StoreMessageNodeAddRequest storeMessageNodeAddRequest);

	/**
	 * 单个删除商家消息节点API
	 *
	 * @author 马连峰
	 * @param storeMessageNodeDelByIdRequest 单个删除参数结构 {@link StoreMessageNodeDelByIdRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/setting/${application.setting.version}/storemessagenode/delete-by-id")
	BaseResponse deleteById(@RequestBody @Valid StoreMessageNodeDelByIdRequest storeMessageNodeDelByIdRequest);

	/**
	 * 批量删除商家消息节点API
	 *
	 * @author 马连峰
	 * @param storeMessageNodeDelByIdListRequest 批量删除参数结构 {@link StoreMessageNodeDelByIdListRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/setting/${application.setting.version}/storemessagenode/delete-by-id-list")
	BaseResponse deleteByIdList(@RequestBody @Valid StoreMessageNodeDelByIdListRequest storeMessageNodeDelByIdListRequest);

}

