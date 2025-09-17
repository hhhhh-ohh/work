package com.wanmi.sbc.message.api.provider.storenoticesend;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.message.api.request.storenoticesend.*;
import com.wanmi.sbc.message.api.response.storenoticesend.StoreNoticeSendAddResponse;
import com.wanmi.sbc.message.api.response.storenoticesend.StoreNoticeSendModifyResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

/**
 * <p>商家公告保存服务Provider</p>
 * @author 马连峰
 * @date 2022-07-04 10:56:58
 */
@FeignClient(value = "${application.message.name}", contextId = "StoreNoticeSendProvider")
public interface StoreNoticeSendProvider {

	/**
	 * 新增商家公告API
	 *
	 * @author 马连峰
	 * @param storeNoticeSendAddRequest 商家公告新增参数结构 {@link StoreNoticeSendAddRequest}
	 * @return 新增的商家公告信息 {@link StoreNoticeSendAddResponse}
	 */
	@PostMapping("/sms/${application.sms.version}/storenoticesend/add")
	BaseResponse add(@RequestBody @Valid StoreNoticeSendAddRequest storeNoticeSendAddRequest);

	/**
	 * 修改商家公告API
	 *
	 * @author 马连峰
	 * @param storeNoticeSendModifyRequest 商家公告修改参数结构 {@link StoreNoticeSendModifyRequest}
	 * @return 修改的商家公告信息 {@link StoreNoticeSendModifyResponse}
	 */
	@PostMapping("/sms/${application.sms.version}/storenoticesend/modify")
	BaseResponse modify(@RequestBody @Valid StoreNoticeSendModifyRequest storeNoticeSendModifyRequest);

	/**
	 * 根据公告ID修改公告状态
	 * @param modifyStatusRequest
	 * @return
	 */
	@PostMapping("/sms/${application.sms.version}/storenoticesend/modify-status-by-id")
	BaseResponse modifyStatusById(@RequestBody @Valid StoreNoticeSendModifyStatusRequest modifyStatusRequest);

	/**
	 * 根据公告ID修改公告扫描标识
	 * @param modifyScanFlagRequest
	 * @return
	 */
	@PostMapping("/sms/${application.sms.version}/storenoticesend/modify-scan-flag")
	BaseResponse modifyScanFlag(@RequestBody @Valid StoreNoticeSendModifyScanFlagRequest modifyScanFlagRequest);

	/**
	 * 单个删除商家公告API
	 *
	 * @author 马连峰
	 * @param storeNoticeSendDelByIdRequest 单个删除参数结构 {@link StoreNoticeSendDelByIdRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/sms/${application.sms.version}/storenoticesend/delete-by-id")
	BaseResponse deleteById(@RequestBody @Valid StoreNoticeSendDelByIdRequest storeNoticeSendDelByIdRequest);

	/**
	 * 单个撤回商家公告API
	 *
	 * @author 马连峰
	 * @param storeNoticeSendDelByIdRequest 单个删除参数结构 {@link StoreNoticeSendDelByIdRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/sms/${application.sms.version}/storenoticesend/withdraw-by-id")
	BaseResponse withdrawById(@RequestBody @Valid StoreNoticeSendDelByIdRequest storeNoticeSendDelByIdRequest);

}

