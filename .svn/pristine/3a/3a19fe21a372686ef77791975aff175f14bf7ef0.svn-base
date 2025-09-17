package com.wanmi.sbc.customer.api.provider.wechatvideo;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.customer.api.request.wechatvideo.*;
import com.wanmi.sbc.customer.api.response.wechatvideo.VideoUserAddResponse;
import com.wanmi.sbc.customer.api.response.wechatvideo.VideoUserModifyResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

/**
 * <p>视频号保存服务Provider</p>
 * @author zhaiqiankun
 * @date 2022-04-02 11:43:20
 */
@FeignClient(value = "${application.customer.name}", contextId = "VideoUserProvider")
public interface VideoUserProvider {

	/**
	 * 新增视频号API
	 *
	 * @author zhaiqiankun
	 * @param videoUserAddRequest 视频号新增参数结构 {@link VideoUserAddRequest}
	 * @return 新增的视频号信息 {@link VideoUserAddResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/videouser/add")
	BaseResponse<VideoUserAddResponse> add(@RequestBody @Valid VideoUserAddRequest videoUserAddRequest);


	/**
	 * 批量新增视频号API
	 * @param batchSaveRequest
	 * @return
	 */
	@PostMapping("/customer/${application.customer.version}/videouser/batch-save")
	BaseResponse batchSave(@RequestBody @Valid VideoUserBatchSaveRequest batchSaveRequest);

	/**
	 * 修改视频号API
	 *
	 * @author zhaiqiankun
	 * @param videoUserModifyRequest 视频号修改参数结构 {@link VideoUserModifyRequest}
	 * @return 修改的视频号信息 {@link VideoUserModifyResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/videouser/modify")
	BaseResponse<VideoUserModifyResponse> modify(@RequestBody @Valid VideoUserModifyRequest videoUserModifyRequest);

	/**
	 * 单个删除视频号API
	 *
	 * @author zhaiqiankun
	 * @param videoUserDelByIdRequest 单个删除参数结构 {@link VideoUserDelByIdRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/videouser/delete-by-id")
	BaseResponse deleteById(@RequestBody @Valid VideoUserDelByIdRequest videoUserDelByIdRequest);

	/**
	 * 批量删除视频号API
	 *
	 * @author zhaiqiankun
	 * @param videoUserDelByIdListRequest 批量删除参数结构 {@link VideoUserDelByIdListRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/videouser/delete-by-id-list")
	BaseResponse deleteByIdList(@RequestBody @Valid VideoUserDelByIdListRequest videoUserDelByIdListRequest);

	/**
	 * 修改指定字段视频号API
	 *
	 * @author zhaiqiankun
	 * @param videoUserModifyFieldRequest 修改 {@link VideoUserModifyFieldRequest}
	 * @return 修改结果 {@link BaseResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/videouser/update-field")
	BaseResponse updateField(@RequestBody @Valid VideoUserModifyFieldRequest videoUserModifyFieldRequest);

}

