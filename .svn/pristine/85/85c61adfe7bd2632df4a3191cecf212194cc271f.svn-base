package com.wanmi.sbc.empower.api.provider.miniprogramset;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.empower.api.request.miniprogramset.MiniProgramSetAddRequest;
import com.wanmi.sbc.empower.api.response.miniprogramset.MiniProgramSetAddResponse;
import com.wanmi.sbc.empower.api.request.miniprogramset.MiniProgramSetModifyRequest;
import com.wanmi.sbc.empower.api.response.miniprogramset.MiniProgramSetModifyResponse;
import com.wanmi.sbc.empower.api.request.miniprogramset.MiniProgramSetDelByIdRequest;
import com.wanmi.sbc.empower.api.request.miniprogramset.MiniProgramSetDelByIdListRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

/**
 * <p>小程序配置保存服务Provider</p>
 * @author zhanghao
 * @date 2021-04-22 17:20:23
 */
@FeignClient(value = "${application.empower.name}", contextId = "MiniProgramSetProvider")
public interface MiniProgramSetProvider {

	/**
	 * 新增小程序配置API
	 *
	 * @author zhanghao
	 * @param miniProgramSetAddRequest 小程序配置新增参数结构 {@link MiniProgramSetAddRequest}
	 * @return 新增的小程序配置信息 {@link MiniProgramSetAddResponse}
	 */
	@PostMapping("/empower/${application.empower.version}/miniprogramset/add")
	BaseResponse<MiniProgramSetAddResponse> add(@RequestBody @Valid MiniProgramSetAddRequest miniProgramSetAddRequest);

	/**
	 * 修改小程序配置API
	 *
	 * @author zhanghao
	 * @param miniProgramSetModifyRequest 小程序配置修改参数结构 {@link MiniProgramSetModifyRequest}
	 * @return 修改的小程序配置信息 {@link MiniProgramSetModifyResponse}
	 */
	@PostMapping("/empower/${application.empower.version}/miniprogramset/modify")
	BaseResponse<MiniProgramSetModifyResponse> modify(@RequestBody @Valid MiniProgramSetModifyRequest miniProgramSetModifyRequest);

	/**
	 * 单个删除小程序配置API
	 *
	 * @author zhanghao
	 * @param miniProgramSetDelByIdRequest 单个删除参数结构 {@link MiniProgramSetDelByIdRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/empower/${application.empower.version}/miniprogramset/delete-by-id")
	BaseResponse deleteById(@RequestBody @Valid MiniProgramSetDelByIdRequest miniProgramSetDelByIdRequest);

	/**
	 * 批量删除小程序配置API
	 *
	 * @author zhanghao
	 * @param miniProgramSetDelByIdListRequest 批量删除参数结构 {@link MiniProgramSetDelByIdListRequest}
	 * @return 删除结果 @link BaseResponse}
	 */
	@PostMapping("/empower/${application.empower.version}/miniprogramset/delete-by-id-list")
	BaseResponse deleteByIdList(@RequestBody @Valid MiniProgramSetDelByIdListRequest miniProgramSetDelByIdListRequest);

}

