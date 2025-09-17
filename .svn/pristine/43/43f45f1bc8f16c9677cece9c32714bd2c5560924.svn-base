package com.wanmi.sbc.empower.api.provider.logisticslog;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.empower.api.request.logisticslog.*;
import com.wanmi.sbc.empower.api.response.logisticslog.LogisticsLogModifyResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>物流记录保存服务Provider</p>
 * @author 宋汉林
 * @date 2021-04-13 17:21:25
 */
@FeignClient(value = "${application.empower.name}", contextId = "LogisticsLogProvider")
public interface LogisticsLogProvider {

	/**
	 * 新增物流记录API
	 *
	 * @author 宋汉林
	 * @param logisticsLogAddRequest 物流记录新增参数结构 {@link LogisticsLogAddRequest}
	 * @return 新增的物流记录信息 {@link BaseResponse}
	 */
	@PostMapping("/empower/${application.empower.version}/logisticslog/add")
	BaseResponse add(@RequestBody @Valid LogisticsLogAddRequest logisticsLogAddRequest);

	/**
	 * 修改物流记录API
	 *
	 * @author 宋汉林
	 * @param logisticsLogModifyRequest 物流记录修改参数结构 {@link LogisticsLogModifyRequest}
	 * @return 修改的物流记录信息 {@link LogisticsLogModifyResponse}
	 */
	@PostMapping("/empower/${application.empower.version}/logisticslog/modify")
	BaseResponse<LogisticsLogModifyResponse> modify(@RequestBody @Valid LogisticsLogModifyRequest logisticsLogModifyRequest);

	/**
	 * 单个删除物流记录API
	 *
	 * @author 宋汉林
	 * @param logisticsLogDelByIdRequest 单个删除参数结构 {@link LogisticsLogDelByIdRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/empower/${application.empower.version}/logisticslog/delete-by-id")
	BaseResponse deleteById(@RequestBody @Valid LogisticsLogDelByIdRequest logisticsLogDelByIdRequest);

	/**
	 * 批量删除物流记录API
	 *
	 * @author 宋汉林
	 * @param logisticsLogDelByIdListRequest 批量删除参数结构 {@link LogisticsLogDelByIdListRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/empower/${application.empower.version}/logisticslog/delete-by-id-list")
	BaseResponse deleteByIdList(@RequestBody @Valid LogisticsLogDelByIdListRequest logisticsLogDelByIdListRequest);

	/**
	 * 根据快递100的回调通知请求参数
	 * @param request  {@link  LogisticsLogNoticeForKuaidiHundredRequest}
	 * @return   {@link BaseResponse}
	 */
	@PostMapping("/empower/${application.empower.version}/logistics-log/modify-for-Kuaidi-hundred")
	BaseResponse modifyForKuaidiHundred(@RequestBody LogisticsLogNoticeForKuaidiHundredRequest request);


	/**
	 * 根据
	 * @param request  {@link  LogisticsLogModifyEndFlagRequest}
	 * @return   {@link BaseResponse}
	 */
	@PostMapping("/empower/${application.empower.version}/logistics-log/modify-endFlag-by-orderNo")
	BaseResponse modifyEndFlagByOrderNo(@RequestBody LogisticsLogModifyEndFlagRequest request);

}

