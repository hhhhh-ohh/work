package com.wanmi.sbc.empower.provider.impl.logisticslog;

import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.empower.api.request.logisticslog.*;
import com.wanmi.sbc.empower.api.response.logisticslog.LogisticsLogSimpleListByCustomerIdResponse;
import com.wanmi.sbc.empower.bean.vo.LogisticsLogSimpleVO;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.empower.api.provider.logisticslog.LogisticsLogQueryProvider;
import com.wanmi.sbc.empower.api.response.logisticslog.LogisticsLogPageResponse;
import com.wanmi.sbc.empower.api.response.logisticslog.LogisticsLogListResponse;
import com.wanmi.sbc.empower.api.response.logisticslog.LogisticsLogByIdResponse;
import com.wanmi.sbc.empower.bean.vo.LogisticsLogVO;
import com.wanmi.sbc.empower.logisticslog.service.LogisticsLogService;
import com.wanmi.sbc.empower.logisticslog.model.root.LogisticsLog;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>物流记录查询服务接口实现</p>
 * @author 宋汉林
 * @date 2021-04-13 17:21:25
 */
@RestController
@Validated
public class LogisticsLogQueryController implements LogisticsLogQueryProvider {
	@Autowired
	private LogisticsLogService logisticsLogService;

	@Override
	public BaseResponse<LogisticsLogPageResponse> page(@RequestBody @Valid LogisticsLogPageRequest logisticsLogPageReq) {
		LogisticsLogQueryRequest queryReq = KsBeanUtil.convert(logisticsLogPageReq, LogisticsLogQueryRequest.class);
		Page<LogisticsLog> logisticsLogPage = logisticsLogService.page(queryReq);
		Page<LogisticsLogVO> newPage = logisticsLogPage.map(entity -> logisticsLogService.wrapperVo(entity));
		MicroServicePage<LogisticsLogVO> microPage = new MicroServicePage<>(newPage, logisticsLogPageReq.getPageable());
		LogisticsLogPageResponse finalRes = new LogisticsLogPageResponse(microPage);
		return BaseResponse.success(finalRes);
	}

	@Override
	public BaseResponse<LogisticsLogListResponse> list(@RequestBody @Valid LogisticsLogListRequest logisticsLogListReq) {
		LogisticsLogQueryRequest queryReq = KsBeanUtil.convert(logisticsLogListReq, LogisticsLogQueryRequest.class);
		List<LogisticsLog> logisticsLogList = logisticsLogService.list(queryReq);
		List<LogisticsLogVO> newList = logisticsLogList.stream().map(entity -> logisticsLogService.wrapperVo(entity)).collect(Collectors.toList());
		return BaseResponse.success(new LogisticsLogListResponse(newList));
	}

	@Override
	public BaseResponse<LogisticsLogByIdResponse> getById(@RequestBody @Valid LogisticsLogByIdRequest logisticsLogByIdRequest) {
		LogisticsLog logisticsLog =
		logisticsLogService.getOne(logisticsLogByIdRequest.getId(), logisticsLogByIdRequest.getStoreId());
		return BaseResponse.success(new LogisticsLogByIdResponse(logisticsLogService.wrapperVo(logisticsLog)));
	}

	/**
	 * 根据会员查询服务列表-简化
	 *
	 * @param request {@link  LogisticsLogSimpleListByCustomerIdRequest}
	 * @return 服务列表 {@link LogisticsLogSimpleListByCustomerIdResponse}
	 */
	@Override
	public BaseResponse<LogisticsLogSimpleListByCustomerIdResponse> listByCustomerId(@RequestBody LogisticsLogSimpleListByCustomerIdRequest request) {
		List<LogisticsLog> logs = logisticsLogService.list(LogisticsLogQueryRequest.builder()
				.customerId(request.getCustomerId()).hasDetailsFlag(BoolFlag.YES).customerShowLimit(BoolFlag.YES).build());
		List<LogisticsLogSimpleVO> vos = logs.stream().map(log -> {
			LogisticsLogSimpleVO vo = new LogisticsLogSimpleVO();
			vo.setId(log.getId());
			vo.setGoodsImg(log.getGoodsImg());
			vo.setGoodsName(log.getGoodsName());
			vo.setState(log.getState());
			vo.setDeliverId(log.getDeliverId());
			vo.setOrderNo(log.getOrderNo());
			if(CollectionUtils.isNotEmpty(log.getLogisticsLogDetails())) {
				vo.setContext(log.getLogisticsLogDetails().get(0).getContext());
				vo.setTime(log.getLogisticsLogDetails().get(0).getTime());
			}
			return vo;
		}).collect(Collectors.toList());
		return BaseResponse.success(LogisticsLogSimpleListByCustomerIdResponse.builder().logisticsList(vos).build());
	}

	@Override
	public BaseResponse<LogisticsLogByIdResponse> getByOrderNo(LogisticsLogByIdRequest logisticsLogByIdRequest) {
		LogisticsLog logisticsLog =
				logisticsLogService.getByOrderNo(logisticsLogByIdRequest.getOrderNo());
		return BaseResponse.success(new LogisticsLogByIdResponse(logisticsLogService.wrapperVo(logisticsLog)));
	}
}

