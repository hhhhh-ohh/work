package com.wanmi.sbc.empower.provider.impl.logisticslogdetail;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.empower.api.provider.logisticslogdetail.LogisticsLogDetailQueryProvider;
import com.wanmi.sbc.empower.api.request.logisticslogdetail.LogisticsLogDetailPageRequest;
import com.wanmi.sbc.empower.api.request.logisticslogdetail.LogisticsLogDetailQueryRequest;
import com.wanmi.sbc.empower.api.response.logisticslogdetail.LogisticsLogDetailPageResponse;
import com.wanmi.sbc.empower.api.request.logisticslogdetail.LogisticsLogDetailListRequest;
import com.wanmi.sbc.empower.api.response.logisticslogdetail.LogisticsLogDetailListResponse;
import com.wanmi.sbc.empower.api.request.logisticslogdetail.LogisticsLogDetailByIdRequest;
import com.wanmi.sbc.empower.api.response.logisticslogdetail.LogisticsLogDetailByIdResponse;
import com.wanmi.sbc.empower.bean.vo.LogisticsLogDetailVO;
import com.wanmi.sbc.empower.logisticslogdetail.service.LogisticsLogDetailService;
import com.wanmi.sbc.empower.logisticslogdetail.model.root.LogisticsLogDetail;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>物流记录明细查询服务接口实现</p>
 * @author 宋汉林
 * @date 2021-04-15 14:57:38
 */
@RestController
@Validated
public class LogisticsLogDetailQueryController implements LogisticsLogDetailQueryProvider {
	@Autowired
	private LogisticsLogDetailService logisticsLogDetailService;

	@Override
	public BaseResponse<LogisticsLogDetailPageResponse> page(@RequestBody @Valid LogisticsLogDetailPageRequest logisticsLogDetailPageReq) {
		LogisticsLogDetailQueryRequest queryReq = KsBeanUtil.convert(logisticsLogDetailPageReq, LogisticsLogDetailQueryRequest.class);
		Page<LogisticsLogDetail> logisticsLogDetailPage = logisticsLogDetailService.page(queryReq);
		Page<LogisticsLogDetailVO> newPage = logisticsLogDetailPage.map(entity -> logisticsLogDetailService.wrapperVo(entity));
		MicroServicePage<LogisticsLogDetailVO> microPage = new MicroServicePage<>(newPage, logisticsLogDetailPageReq.getPageable());
		LogisticsLogDetailPageResponse finalRes = new LogisticsLogDetailPageResponse(microPage);
		return BaseResponse.success(finalRes);
	}

	@Override
	public BaseResponse<LogisticsLogDetailListResponse> list(@RequestBody @Valid LogisticsLogDetailListRequest logisticsLogDetailListReq) {
		LogisticsLogDetailQueryRequest queryReq = KsBeanUtil.convert(logisticsLogDetailListReq, LogisticsLogDetailQueryRequest.class);
		List<LogisticsLogDetail> logisticsLogDetailList = logisticsLogDetailService.list(queryReq);
		List<LogisticsLogDetailVO> newList = logisticsLogDetailList.stream().map(entity -> logisticsLogDetailService.wrapperVo(entity)).collect(Collectors.toList());
		return BaseResponse.success(new LogisticsLogDetailListResponse(newList));
	}

	@Override
	public BaseResponse<LogisticsLogDetailByIdResponse> getById(@RequestBody @Valid LogisticsLogDetailByIdRequest logisticsLogDetailByIdRequest) {
		LogisticsLogDetail logisticsLogDetail =
		logisticsLogDetailService.getOne(logisticsLogDetailByIdRequest.getId());
		return BaseResponse.success(new LogisticsLogDetailByIdResponse(logisticsLogDetailService.wrapperVo(logisticsLogDetail)));
	}

}

