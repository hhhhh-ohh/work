package com.wanmi.sbc.empower.provider.impl.logisticslogdetail;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.empower.api.provider.logisticslogdetail.LogisticsLogDetailProvider;
import com.wanmi.sbc.empower.api.request.logisticslogdetail.LogisticsLogDetailAddRequest;
import com.wanmi.sbc.empower.api.response.logisticslogdetail.LogisticsLogDetailAddResponse;
import com.wanmi.sbc.empower.api.request.logisticslogdetail.LogisticsLogDetailModifyRequest;
import com.wanmi.sbc.empower.api.response.logisticslogdetail.LogisticsLogDetailModifyResponse;
import com.wanmi.sbc.empower.api.request.logisticslogdetail.LogisticsLogDetailDelByIdRequest;
import com.wanmi.sbc.empower.api.request.logisticslogdetail.LogisticsLogDetailDelByIdListRequest;
import com.wanmi.sbc.empower.logisticslogdetail.service.LogisticsLogDetailService;
import com.wanmi.sbc.empower.logisticslogdetail.model.root.LogisticsLogDetail;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.validation.Valid;

/**
 * <p>物流记录明细保存服务接口实现</p>
 * @author 宋汉林
 * @date 2021-04-15 14:57:38
 */
@RestController
@Validated
public class LogisticsLogDetailController implements LogisticsLogDetailProvider {
	@Autowired
	private LogisticsLogDetailService logisticsLogDetailService;

	@Override
	public BaseResponse<LogisticsLogDetailAddResponse> add(@RequestBody @Valid LogisticsLogDetailAddRequest logisticsLogDetailAddRequest) {
		LogisticsLogDetail logisticsLogDetail = KsBeanUtil.convert(logisticsLogDetailAddRequest, LogisticsLogDetail.class);
		return BaseResponse.success(new LogisticsLogDetailAddResponse(
				logisticsLogDetailService.wrapperVo(logisticsLogDetailService.add(logisticsLogDetail))));
	}

	@Override
	public BaseResponse<LogisticsLogDetailModifyResponse> modify(@RequestBody @Valid LogisticsLogDetailModifyRequest logisticsLogDetailModifyRequest) {
		LogisticsLogDetail logisticsLogDetail = KsBeanUtil.convert(logisticsLogDetailModifyRequest, LogisticsLogDetail.class);
		return BaseResponse.success(new LogisticsLogDetailModifyResponse(
				logisticsLogDetailService.wrapperVo(logisticsLogDetailService.modify(logisticsLogDetail))));
	}

	@Override
	public BaseResponse deleteById(@RequestBody @Valid LogisticsLogDetailDelByIdRequest logisticsLogDetailDelByIdRequest) {
		LogisticsLogDetail logisticsLogDetail = KsBeanUtil.convert(logisticsLogDetailDelByIdRequest, LogisticsLogDetail.class);
		logisticsLogDetail.setDelFlag(DeleteFlag.YES);
		logisticsLogDetailService.deleteById(logisticsLogDetail);
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse deleteByIdList(@RequestBody @Valid LogisticsLogDetailDelByIdListRequest logisticsLogDetailDelByIdListRequest) {
		List<LogisticsLogDetail> logisticsLogDetailList = logisticsLogDetailDelByIdListRequest.getIdList().stream()
			.map(Id -> {
				LogisticsLogDetail logisticsLogDetail = KsBeanUtil.convert(Id, LogisticsLogDetail.class);
				logisticsLogDetail.setDelFlag(DeleteFlag.YES);
				return logisticsLogDetail;
			}).collect(Collectors.toList());
		logisticsLogDetailService.deleteByIdList(logisticsLogDetailList);
		return BaseResponse.SUCCESSFUL();
	}

}

