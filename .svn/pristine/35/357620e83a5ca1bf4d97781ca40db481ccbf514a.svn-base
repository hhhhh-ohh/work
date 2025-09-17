package com.wanmi.sbc.empower.api.provider.miniprogramset;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.empower.api.request.miniprogramset.MiniProgramSetByIdRequest;
import com.wanmi.sbc.empower.api.request.miniprogramset.MiniProgramSetByTypeRequest;
import com.wanmi.sbc.empower.api.request.miniprogramset.MiniProgramSetListRequest;
import com.wanmi.sbc.empower.api.request.miniprogramset.MiniProgramSetPageRequest;
import com.wanmi.sbc.empower.api.response.miniprogramset.MiniProgramSetByIdResponse;
import com.wanmi.sbc.empower.api.response.miniprogramset.MiniProgramSetByTypeResponse;
import com.wanmi.sbc.empower.api.response.miniprogramset.MiniProgramSetListResponse;
import com.wanmi.sbc.empower.api.response.miniprogramset.MiniProgramSetPageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>小程序配置查询服务Provider</p>
 * @author zhanghao
 * @date 2021-04-22 17:20:23
 */
@FeignClient(value = "${application.empower.name}", contextId = "MiniProgramSetQueryProvider")
public interface MiniProgramSetQueryProvider {

	/**
	 * 分页查询小程序配置API
	 *
	 * @author zhanghao
	 * @param miniProgramSetPageReq 分页请求参数和筛选对象 {@link MiniProgramSetPageRequest}
	 * @return 小程序配置分页列表信息 {@link MiniProgramSetPageResponse}
	 */
	@PostMapping("/empower/${application.empower.version}/miniprogramset/page")
	BaseResponse<MiniProgramSetPageResponse> page(@RequestBody @Valid MiniProgramSetPageRequest miniProgramSetPageReq);

	/**
	 * 列表查询小程序配置API
	 *
	 * @author zhanghao
	 * @param miniProgramSetListReq 列表请求参数和筛选对象 {@link MiniProgramSetListRequest}
	 * @return 小程序配置的列表信息 {@link MiniProgramSetListResponse}
	 */
	@PostMapping("/empower/${application.empower.version}/miniprogramset/list")
	BaseResponse<MiniProgramSetListResponse> list(@RequestBody @Valid MiniProgramSetListRequest miniProgramSetListReq);

	/**
	 * 单个查询小程序配置API
	 *
	 * @author zhanghao
	 * @param miniProgramSetByIdRequest 单个查询小程序配置请求参数 {@link MiniProgramSetByIdRequest}
	 * @return 小程序配置详情 {@link MiniProgramSetByIdResponse}
	 */
	@PostMapping("/empower/${application.empower.version}/miniprogramset/get-by-id")
	BaseResponse<MiniProgramSetByIdResponse> getById(@RequestBody @Valid MiniProgramSetByIdRequest miniProgramSetByIdRequest);


	/**
	 * 单个查询小程序配置API
	 *
	 * @author zhanghao
	 * @param miniProgramSetByTypeRequest 单个查询小程序配置请求参数 {@link MiniProgramSetByTypeRequest}
	 * @return 小程序配置详情 {@link MiniProgramSetByTypeResponse}
	 */
	@PostMapping("/empower/${application.empower.version}/miniprogramset/get-by-type")
	BaseResponse<MiniProgramSetByTypeResponse> getByType(@RequestBody @Valid MiniProgramSetByTypeRequest miniProgramSetByTypeRequest);

}

