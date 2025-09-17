package com.wanmi.sbc.empower.api.provider.logisticslogdetail;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.empower.api.request.logisticslogdetail.LogisticsLogDetailAddRequest;
import com.wanmi.sbc.empower.api.response.logisticslogdetail.LogisticsLogDetailAddResponse;
import com.wanmi.sbc.empower.api.request.logisticslogdetail.LogisticsLogDetailModifyRequest;
import com.wanmi.sbc.empower.api.response.logisticslogdetail.LogisticsLogDetailModifyResponse;
import com.wanmi.sbc.empower.api.request.logisticslogdetail.LogisticsLogDetailDelByIdRequest;
import com.wanmi.sbc.empower.api.request.logisticslogdetail.LogisticsLogDetailDelByIdListRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

/**
 * <p>物流记录明细保存服务Provider</p>
 * @author 宋汉林
 * @date 2021-04-15 14:57:38
 */
@FeignClient(value = "${application.empower.name}", contextId = "LogisticsLogDetailProvider")
public interface LogisticsLogDetailProvider {

	/**
	 * 新增物流记录明细API
	 *
	 * @author 宋汉林
	 * @param logisticsLogDetailAddRequest 物流记录明细新增参数结构 {@link LogisticsLogDetailAddRequest}
	 * @return 新增的物流记录明细信息 {@link LogisticsLogDetailAddResponse}
	 */
	@PostMapping("/empower/${application.empower.version}/logisticslogdetail/add")
	BaseResponse<LogisticsLogDetailAddResponse> add(@RequestBody @Valid LogisticsLogDetailAddRequest logisticsLogDetailAddRequest);

	/**
	 * 修改物流记录明细API
	 *
	 * @author 宋汉林
	 * @param logisticsLogDetailModifyRequest 物流记录明细修改参数结构 {@link LogisticsLogDetailModifyRequest}
	 * @return 修改的物流记录明细信息 {@link LogisticsLogDetailModifyResponse}
	 */
	@PostMapping("/empower/${application.empower.version}/logisticslogdetail/modify")
	BaseResponse<LogisticsLogDetailModifyResponse> modify(@RequestBody @Valid LogisticsLogDetailModifyRequest logisticsLogDetailModifyRequest);

	/**
	 * 单个删除物流记录明细API
	 *
	 * @author 宋汉林
	 * @param logisticsLogDetailDelByIdRequest 单个删除参数结构 {@link LogisticsLogDetailDelByIdRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/empower/${application.empower.version}/logisticslogdetail/delete-by-id")
	BaseResponse deleteById(@RequestBody @Valid LogisticsLogDetailDelByIdRequest logisticsLogDetailDelByIdRequest);

	/**
	 * 批量删除物流记录明细API
	 *
	 * @author 宋汉林
	 * @param logisticsLogDetailDelByIdListRequest 批量删除参数结构 {@link LogisticsLogDetailDelByIdListRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/empower/${application.empower.version}/logisticslogdetail/delete-by-id-list")
	BaseResponse deleteByIdList(@RequestBody @Valid LogisticsLogDetailDelByIdListRequest logisticsLogDetailDelByIdListRequest);

}

