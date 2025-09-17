package com.wanmi.sbc.empower.api.provider.logisticslog;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.empower.api.request.logisticslog.LogisticsLogPageRequest;
import com.wanmi.sbc.empower.api.request.logisticslog.LogisticsLogSimpleListByCustomerIdRequest;
import com.wanmi.sbc.empower.api.response.logisticslog.LogisticsLogPageResponse;
import com.wanmi.sbc.empower.api.request.logisticslog.LogisticsLogListRequest;
import com.wanmi.sbc.empower.api.response.logisticslog.LogisticsLogListResponse;
import com.wanmi.sbc.empower.api.request.logisticslog.LogisticsLogByIdRequest;
import com.wanmi.sbc.empower.api.response.logisticslog.LogisticsLogByIdResponse;
import com.wanmi.sbc.empower.api.response.logisticslog.LogisticsLogSimpleListByCustomerIdResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

/**
 * <p>物流记录查询服务Provider</p>
 * @author 宋汉林
 * @date 2021-04-13 17:21:25
 */
@FeignClient(value = "${application.empower.name}", contextId = "LogisticsLogQueryProvider")
public interface LogisticsLogQueryProvider {

	/**
	 * 分页查询物流记录API
	 *
	 * @author 宋汉林
	 * @param logisticsLogPageReq 分页请求参数和筛选对象 {@link LogisticsLogPageRequest}
	 * @return 物流记录分页列表信息 {@link LogisticsLogPageResponse}
	 */
	@PostMapping("/empower/${application.empower.version}/logisticslog/page")
	BaseResponse<LogisticsLogPageResponse> page(@RequestBody @Valid LogisticsLogPageRequest logisticsLogPageReq);

	/**
	 * 列表查询物流记录API
	 *
	 * @author 宋汉林
	 * @param logisticsLogListReq 列表请求参数和筛选对象 {@link LogisticsLogListRequest}
	 * @return 物流记录的列表信息 {@link LogisticsLogListResponse}
	 */
	@PostMapping("/empower/${application.empower.version}/logisticslog/list")
	BaseResponse<LogisticsLogListResponse> list(@RequestBody @Valid LogisticsLogListRequest logisticsLogListReq);

	/**
	 * 单个查询物流记录API
	 *
	 * @author 宋汉林
	 * @param logisticsLogByIdRequest 单个查询物流记录请求参数 {@link LogisticsLogByIdRequest}
	 * @return 物流记录详情 {@link LogisticsLogByIdResponse}
	 */
	@PostMapping("/empower/${application.empower.version}/logisticslog/get-by-id")
	BaseResponse<LogisticsLogByIdResponse> getById(@RequestBody @Valid LogisticsLogByIdRequest logisticsLogByIdRequest);

	/**
	 * 根据会员查询服务列表-简化
	 *
	 * @param request {@link  LogisticsLogSimpleListByCustomerIdRequest}
	 * @return 服务列表 {@link LogisticsLogSimpleListByCustomerIdResponse}
	 */
	@PostMapping("/empower/${application.empower.version}/logistics-log/list-by-customer-id")
	BaseResponse<LogisticsLogSimpleListByCustomerIdResponse> listByCustomerId(@RequestBody
																					  LogisticsLogSimpleListByCustomerIdRequest
																					  request);

	/**
	 * 单个查询物流记录API
	 *
	 * @author 宋汉林
	 * @param logisticsLogByIdRequest 单个查询物流记录请求参数 {@link LogisticsLogByIdRequest}
	 * @return 物流记录详情 {@link LogisticsLogByIdResponse}
	 */
	@PostMapping("/empower/${application.empower.version}/logisticslog/get-by-order-no")
	BaseResponse<LogisticsLogByIdResponse> getByOrderNo(@RequestBody @Valid LogisticsLogByIdRequest logisticsLogByIdRequest);

}

