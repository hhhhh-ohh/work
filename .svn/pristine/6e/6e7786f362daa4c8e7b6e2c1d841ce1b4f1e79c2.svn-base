package com.wanmi.sbc.customer.api.provider.wechatvideo;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.customer.api.request.wechatvideo.VideoUserPageRequest;
import com.wanmi.sbc.customer.api.response.wechatvideo.VideoUserPageResponse;
import com.wanmi.sbc.customer.api.request.wechatvideo.VideoUserCountRequest;
import com.wanmi.sbc.customer.api.response.wechatvideo.VideoUserCountResponse;
import com.wanmi.sbc.customer.api.request.wechatvideo.VideoUserListRequest;
import com.wanmi.sbc.customer.api.response.wechatvideo.VideoUserListResponse;
import com.wanmi.sbc.customer.api.request.wechatvideo.VideoUserByIdRequest;
import com.wanmi.sbc.customer.api.response.wechatvideo.VideoUserByIdResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

/**
 * <p>视频号查询服务Provider</p>
 * @author zhaiqiankun
 * @date 2022-04-02 11:43:20
 */
@FeignClient(value = "${application.customer.name}", contextId = "VideoUserQueryProvider")
public interface VideoUserQueryProvider {

	/**
	 * 分页查询视频号API
	 *
	 * @author zhaiqiankun
	 * @param videoUserPageReq 分页请求参数和筛选对象 {@link VideoUserPageRequest}
	 * @return 视频号分页列表信息 {@link VideoUserPageResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/videouser/page")
	BaseResponse<VideoUserPageResponse> page(@RequestBody @Valid VideoUserPageRequest videoUserPageReq);

	/**
	 * 列表查询视频号API
	 *
	 * @author zhaiqiankun
	 * @param videoUserListReq 列表请求参数和筛选对象 {@link VideoUserListRequest}
	 * @return 视频号的列表信息 {@link VideoUserListResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/videouser/list")
	BaseResponse<VideoUserListResponse> list(@RequestBody @Valid VideoUserListRequest videoUserListReq);

	/**
	 * 单个查询视频号API
	 *
	 * @author zhaiqiankun
	 * @param videoUserByIdRequest 单个查询视频号请求参数 {@link VideoUserByIdRequest}
	 * @return 视频号详情 {@link VideoUserByIdResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/videouser/get-by-id")
	BaseResponse<VideoUserByIdResponse> getById(@RequestBody @Valid VideoUserByIdRequest videoUserByIdRequest);

	/**
	 * 查询视频号数量API
	 *
	 * @author zhaiqiankun
	 * @param videoUserCountRequest 查询视频号数量请求参数 {@link VideoUserCountRequest}
	 * @return 视频号结果 {@link VideoUserCountResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/videouser/count")
	BaseResponse<VideoUserCountResponse> getCount(@RequestBody @Valid VideoUserCountRequest videoUserCountRequest);

}

