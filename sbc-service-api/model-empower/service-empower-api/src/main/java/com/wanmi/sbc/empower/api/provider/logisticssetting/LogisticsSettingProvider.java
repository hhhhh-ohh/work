package com.wanmi.sbc.empower.api.provider.logisticssetting;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.empower.api.request.logisticssetting.LogisticsSettingAddRequest;
import com.wanmi.sbc.empower.api.response.logisticssetting.LogisticsSettingAddResponse;
import com.wanmi.sbc.empower.api.request.logisticssetting.LogisticsSettingModifyRequest;
import com.wanmi.sbc.empower.api.response.logisticssetting.LogisticsSettingModifyResponse;
import com.wanmi.sbc.empower.api.request.logisticssetting.LogisticsSettingDelByIdRequest;
import com.wanmi.sbc.empower.api.request.logisticssetting.LogisticsSettingDelByIdListRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

/**
 * <p>物流配置保存服务Provider</p>
 * @author 宋汉林
 * @date 2021-04-01 11:23:29
 */
@FeignClient(value = "${application.empower.name}", contextId = "LogisticsSettingProvider")
public interface LogisticsSettingProvider {

	/**
	 * 新增物流配置API
	 *
	 * @author 宋汉林
	 * @param logisticsSettingAddRequest 物流配置新增参数结构 {@link LogisticsSettingAddRequest}
	 * @return 新增的物流配置信息 {@link LogisticsSettingAddResponse}
	 */
	@PostMapping("/empower/${application.empower.version}/logisticssetting/add")
	BaseResponse<LogisticsSettingAddResponse> add(@RequestBody @Valid LogisticsSettingAddRequest logisticsSettingAddRequest);

	/**
	 * 修改物流配置API
	 *
	 * @author 宋汉林
	 * @param logisticsSettingModifyRequest 物流配置修改参数结构 {@link LogisticsSettingModifyRequest}
	 * @return 修改的物流配置信息 {@link LogisticsSettingModifyResponse}
	 */
	@PostMapping("/empower/${application.empower.version}/logisticssetting/modify")
	BaseResponse<LogisticsSettingModifyResponse> modify(@RequestBody @Valid LogisticsSettingModifyRequest logisticsSettingModifyRequest);

	/**
	 * 单个删除物流配置API
	 *
	 * @author 宋汉林
	 * @param logisticsSettingDelByIdRequest 单个删除参数结构 {@link LogisticsSettingDelByIdRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/empower/${application.empower.version}/logisticssetting/delete-by-id")
	BaseResponse deleteById(@RequestBody @Valid LogisticsSettingDelByIdRequest logisticsSettingDelByIdRequest);

	/**
	 * 批量删除物流配置API
	 *
	 * @author 宋汉林
	 * @param logisticsSettingDelByIdListRequest 批量删除参数结构 {@link LogisticsSettingDelByIdListRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/empower/${application.empower.version}/logisticssetting/delete-by-id-list")
	BaseResponse deleteByIdList(@RequestBody @Valid LogisticsSettingDelByIdListRequest logisticsSettingDelByIdListRequest);

}

