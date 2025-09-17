package com.wanmi.sbc.setting.api.provider.storemessagenodesetting;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.setting.api.request.storemessagenodesetting.*;
import com.wanmi.sbc.setting.api.response.storemessagenodesetting.StoreMessageNodeSettingAddResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

/**
 * <p>商家消息节点设置保存服务Provider</p>
 * @author 马连峰
 * @date 2022-07-11 09:42:56
 */
@FeignClient(value = "${application.setting.name}", contextId = "StoreMessageNodeSettingProvider")
public interface StoreMessageNodeSettingProvider {

	/**
	 * 新增商家消息节点设置API
	 *
	 * @author 马连峰
	 * @param storeMessageNodeSettingAddRequest 商家消息节点设置新增参数结构 {@link StoreMessageNodeSettingAddRequest}
	 * @return 新增的商家消息节点设置信息 {@link StoreMessageNodeSettingAddResponse}
	 */
	@PostMapping("/setting/${application.setting.version}/storemessagenodesetting/add")
	BaseResponse<StoreMessageNodeSettingAddResponse> add(@RequestBody @Valid StoreMessageNodeSettingAddRequest storeMessageNodeSettingAddRequest);

	/**
	 * 修改节点开关状态
	 *
	 * @author 马连峰
	 * @param storeMessageNodeSettingModifyStatusRequest 修改节点开关状态 {@link StoreMessageNodeSettingModifyStatusRequest}
	 * @return 修改结果 {@link BaseResponse}
	 */
	@PostMapping("/setting/${application.setting.version}/storemessagenodesetting/modify-status")
	BaseResponse modifyStatus(@RequestBody @Valid StoreMessageNodeSettingModifyStatusRequest storeMessageNodeSettingModifyStatusRequest);

	/**
	 * 单个删除商家消息节点设置API
	 *
	 * @author 马连峰
	 * @param storeMessageNodeSettingDelByIdRequest 单个删除参数结构 {@link StoreMessageNodeSettingDelByIdRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/setting/${application.setting.version}/storemessagenodesetting/delete-by-id")
	BaseResponse deleteById(@RequestBody @Valid StoreMessageNodeSettingDelByIdRequest storeMessageNodeSettingDelByIdRequest);

	/**
	 * 批量删除商家消息节点设置API
	 *
	 * @author 马连峰
	 * @param storeMessageNodeSettingDelByIdListRequest 批量删除参数结构 {@link StoreMessageNodeSettingDelByIdListRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/setting/${application.setting.version}/storemessagenodesetting/delete-by-id-list")
	BaseResponse deleteByIdList(@RequestBody @Valid StoreMessageNodeSettingDelByIdListRequest storeMessageNodeSettingDelByIdListRequest);

}

