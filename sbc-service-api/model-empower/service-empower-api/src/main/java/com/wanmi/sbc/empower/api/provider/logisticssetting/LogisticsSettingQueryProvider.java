package com.wanmi.sbc.empower.api.provider.logisticssetting;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.empower.api.request.logisticssetting.LogisticsSettingByLogisticsTypeRequest;
import com.wanmi.sbc.empower.api.request.logisticssetting.LogisticsSettingPageRequest;
import com.wanmi.sbc.empower.api.response.logisticssetting.LogisticsSettingByLogisticsTypeResponse;
import com.wanmi.sbc.empower.api.response.logisticssetting.LogisticsSettingPageResponse;
import com.wanmi.sbc.empower.api.request.logisticssetting.LogisticsSettingListRequest;
import com.wanmi.sbc.empower.api.response.logisticssetting.LogisticsSettingListResponse;
import com.wanmi.sbc.empower.api.request.logisticssetting.LogisticsSettingByIdRequest;
import com.wanmi.sbc.empower.api.response.logisticssetting.LogisticsSettingByIdResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

/**
 * <p>物流配置查询服务Provider</p>
 * @author 宋汉林
 * @date 2021-04-01 11:23:29
 */
@FeignClient(value = "${application.empower.name}", contextId = "LogisticsSettingQueryProvider")
public interface LogisticsSettingQueryProvider {

	/**
	 * 分页查询物流配置API
	 *
	 * @author 宋汉林
	 * @param logisticsSettingPageReq 分页请求参数和筛选对象 {@link LogisticsSettingPageRequest}
	 * @return 物流配置分页列表信息 {@link LogisticsSettingPageResponse}
	 */
	@PostMapping("/empower/${application.empower.version}/logisticssetting/page")
	BaseResponse<LogisticsSettingPageResponse> page(@RequestBody @Valid LogisticsSettingPageRequest logisticsSettingPageReq);

	/**
	 * 列表查询物流配置API
	 *
	 * @author 宋汉林
	 * @param logisticsSettingListReq 列表请求参数和筛选对象 {@link LogisticsSettingListRequest}
	 * @return 物流配置的列表信息 {@link LogisticsSettingListResponse}
	 */
	@PostMapping("/empower/${application.empower.version}/logisticssetting/list")
	BaseResponse<LogisticsSettingListResponse> list(@RequestBody @Valid LogisticsSettingListRequest logisticsSettingListReq);

	/**
	 * 单个查询物流配置API
	 *
	 * @author 宋汉林
	 * @param logisticsSettingByIdRequest 单个查询物流配置请求参数 {@link LogisticsSettingByIdRequest}
	 * @return 物流配置详情 {@link LogisticsSettingByIdResponse}
	 */
	@PostMapping("/empower/${application.empower.version}/logisticssetting/get-by-id")
	BaseResponse<LogisticsSettingByIdResponse> getById(@RequestBody @Valid LogisticsSettingByIdRequest logisticsSettingByIdRequest);


	/**
	 * 单个查询物流配置API
	 *
	 * @author 宋汉林
	 * @param logisticsSettingByLogisticsTypeRequest 单个查询物流配置请求参数 {@link LogisticsSettingByLogisticsTypeRequest}
	 * @return 物流配置详情 {@link LogisticsSettingByLogisticsTypeResponse}
	 */
	@PostMapping("/empower/${application.empower.version}/logisticssetting/get-by-logistics-type")
	BaseResponse<LogisticsSettingByLogisticsTypeResponse> getByLogisticsType(@RequestBody @Valid LogisticsSettingByLogisticsTypeRequest logisticsSettingByLogisticsTypeRequest);

}

