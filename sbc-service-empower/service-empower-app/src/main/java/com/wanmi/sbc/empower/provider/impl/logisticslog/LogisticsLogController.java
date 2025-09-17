package com.wanmi.sbc.empower.provider.impl.logisticslog;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.empower.api.provider.logisticslog.LogisticsLogProvider;
import com.wanmi.sbc.empower.api.request.logisticslog.*;
import com.wanmi.sbc.empower.api.response.logisticslog.LogisticsLogModifyResponse;
import com.wanmi.sbc.empower.logisticslog.model.root.LogisticsLog;
import com.wanmi.sbc.empower.logisticslog.service.LogisticsLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>物流记录保存服务接口实现</p>
 * @author 宋汉林
 * @date 2021-04-13 17:21:25
 */
@RestController
@Validated
public class LogisticsLogController implements LogisticsLogProvider {

	@Autowired
	private LogisticsLogService logisticsLogService;

	@Override
	public BaseResponse add(@RequestBody @Valid LogisticsLogAddRequest logisticsLogAddRequest) {
		LogisticsLog logisticsLog = KsBeanUtil.convert(logisticsLogAddRequest, LogisticsLog.class);
		logisticsLogService.add(logisticsLog);
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse<LogisticsLogModifyResponse> modify(@RequestBody @Valid LogisticsLogModifyRequest logisticsLogModifyRequest) {
		LogisticsLog logisticsLog = KsBeanUtil.convert(logisticsLogModifyRequest, LogisticsLog.class);
		return BaseResponse.success(new LogisticsLogModifyResponse(
				logisticsLogService.wrapperVo(logisticsLogService.modify(logisticsLog))));
	}

	@Override
	public BaseResponse deleteById(@RequestBody @Valid LogisticsLogDelByIdRequest logisticsLogDelByIdRequest) {
		LogisticsLog logisticsLog = KsBeanUtil.convert(logisticsLogDelByIdRequest, LogisticsLog.class);
		logisticsLog.setDelFlag(DeleteFlag.YES);
		logisticsLogService.deleteById(logisticsLog);
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse deleteByIdList(@RequestBody @Valid LogisticsLogDelByIdListRequest logisticsLogDelByIdListRequest) {
		List<LogisticsLog> logisticsLogList = logisticsLogDelByIdListRequest.getIdList().stream()
			.map(Id -> {
				LogisticsLog logisticsLog = KsBeanUtil.convert(Id, LogisticsLog.class);
				logisticsLog.setDelFlag(DeleteFlag.YES);
				return logisticsLog;
			}).collect(Collectors.toList());
		logisticsLogService.deleteByIdList(logisticsLogList);
		return BaseResponse.SUCCESSFUL();
	}

	/**
	 * 根据快递100的回调通知请求参数
	 *
	 * @param request {@link  LogisticsLogNoticeForKuaidiHundredRequest}
	 * @return {@link BaseResponse}
	 */
	@Override
	public BaseResponse modifyForKuaidiHundred(@RequestBody LogisticsLogNoticeForKuaidiHundredRequest request) {
		logisticsLogService.modifyForKuaiDi100(request.getKuaidiHundredNoticeDTO());
		return BaseResponse.SUCCESSFUL();
	}

	/**
	 * 根据订单号修改物流信息
	 *
	 * @param request {@link  LogisticsLogModifyEndFlagRequest}
	 * @return {@link BaseResponse}
	 */
	@Override
	public BaseResponse modifyEndFlagByOrderNo(@RequestBody LogisticsLogModifyEndFlagRequest request) {
		logisticsLogService.modifyEndFlagByOrderNo(request.getOrderNo());
		return BaseResponse.SUCCESSFUL();
	}

}

