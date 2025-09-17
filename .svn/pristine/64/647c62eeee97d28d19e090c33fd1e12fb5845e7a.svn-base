package com.wanmi.sbc.empower.api.provider.logisticslogdetail;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.empower.api.request.logisticslogdetail.LogisticsLogDetailPageRequest;
import com.wanmi.sbc.empower.api.response.logisticslogdetail.LogisticsLogDetailPageResponse;
import com.wanmi.sbc.empower.api.request.logisticslogdetail.LogisticsLogDetailListRequest;
import com.wanmi.sbc.empower.api.response.logisticslogdetail.LogisticsLogDetailListResponse;
import com.wanmi.sbc.empower.api.request.logisticslogdetail.LogisticsLogDetailByIdRequest;
import com.wanmi.sbc.empower.api.response.logisticslogdetail.LogisticsLogDetailByIdResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

/**
 * <p>物流记录明细查询服务Provider</p>
 * @author 宋汉林
 * @date 2021-04-15 14:57:38
 */
@FeignClient(value = "${application.empower.name}", contextId = "LogisticsLogDetailQueryProvider")
public interface LogisticsLogDetailQueryProvider {

	/**
	 * 分页查询物流记录明细API
	 *
	 * @author 宋汉林
	 * @param logisticsLogDetailPageReq 分页请求参数和筛选对象 {@link LogisticsLogDetailPageRequest}
	 * @return 物流记录明细分页列表信息 {@link LogisticsLogDetailPageResponse}
	 */
	@PostMapping("/empower/${application.empower.version}/logisticslogdetail/page")
	BaseResponse<LogisticsLogDetailPageResponse> page(@RequestBody @Valid LogisticsLogDetailPageRequest logisticsLogDetailPageReq);

	/**
	 * 列表查询物流记录明细API
	 *
	 * @author 宋汉林
	 * @param logisticsLogDetailListReq 列表请求参数和筛选对象 {@link LogisticsLogDetailListRequest}
	 * @return 物流记录明细的列表信息 {@link LogisticsLogDetailListResponse}
	 */
	@PostMapping("/empower/${application.empower.version}/logisticslogdetail/list")
	BaseResponse<LogisticsLogDetailListResponse> list(@RequestBody @Valid LogisticsLogDetailListRequest logisticsLogDetailListReq);

	/**
	 * 单个查询物流记录明细API
	 *
	 * @author 宋汉林
	 * @param logisticsLogDetailByIdRequest 单个查询物流记录明细请求参数 {@link LogisticsLogDetailByIdRequest}
	 * @return 物流记录明细详情 {@link LogisticsLogDetailByIdResponse}
	 */
	@PostMapping("/empower/${application.empower.version}/logisticslogdetail/get-by-id")
	BaseResponse<LogisticsLogDetailByIdResponse> getById(@RequestBody @Valid LogisticsLogDetailByIdRequest logisticsLogDetailByIdRequest);

}

