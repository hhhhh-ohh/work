package com.wanmi.sbc.empower.provider.impl.minimsgcustomerrecord;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.TriggerNodeType;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.empower.api.provider.minimsgcustomerrecord.MiniMsgCustomerRecordQueryProvider;
import com.wanmi.sbc.empower.api.request.minimsgcustomerrecord.MiniMsgCusRecordByActivityIdRequest;
import com.wanmi.sbc.empower.api.request.minimsgcustomerrecord.MiniMsgCustomerRecordByIdRequest;
import com.wanmi.sbc.empower.api.request.minimsgcustomerrecord.MiniMsgCustomerRecordListRequest;
import com.wanmi.sbc.empower.api.request.minimsgcustomerrecord.MiniMsgCusRecordPageRequest;
import com.wanmi.sbc.empower.api.request.minimsgcustomerrecord.MiniMsgCusRecordQueryRequest;
import com.wanmi.sbc.empower.api.response.minimsgcustomerrecord.MiniMsgCustomerRecordByActivityIdResponse;
import com.wanmi.sbc.empower.api.response.minimsgcustomerrecord.MiniMsgCustomerRecordByIdResponse;
import com.wanmi.sbc.empower.api.response.minimsgcustomerrecord.MiniMsgCustomerRecordListResponse;
import com.wanmi.sbc.empower.api.response.minimsgcustomerrecord.MiniMsgCustomerRecordPageResponse;
import com.wanmi.sbc.empower.bean.vo.MiniMsgCustomerRecordVO;
import com.wanmi.sbc.empower.minimsgcustomerrecord.model.root.MiniMsgCustomerRecord;
import com.wanmi.sbc.empower.minimsgcustomerrecord.service.MiniMsgCustomerRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>客户订阅消息信息表查询服务接口实现</p>
 * @author xufeng
 * @date 2022-08-12 10:26:16
 */
@RestController
@Validated
public class MiniMsgCusRecordQueryController implements MiniMsgCustomerRecordQueryProvider {
	@Autowired
	private MiniMsgCustomerRecordService miniMsgCustomerRecordService;

	@Override
	public BaseResponse<MiniMsgCustomerRecordPageResponse> page(@RequestBody @Valid MiniMsgCusRecordPageRequest miniProgramSubscribeMessageCustomerRecordPageReq) {
		MiniMsgCusRecordQueryRequest queryReq = KsBeanUtil.convert(miniProgramSubscribeMessageCustomerRecordPageReq, MiniMsgCusRecordQueryRequest.class);
		Page<MiniMsgCustomerRecord> miniProgramSubscribeMessageCustomerRecordPage = miniMsgCustomerRecordService.page(queryReq);
		Page<MiniMsgCustomerRecordVO> newPage = miniProgramSubscribeMessageCustomerRecordPage.map(entity -> miniMsgCustomerRecordService.wrapperVo(entity));
		MicroServicePage<MiniMsgCustomerRecordVO> microPage = new MicroServicePage<>(newPage, miniProgramSubscribeMessageCustomerRecordPageReq.getPageable());
		MiniMsgCustomerRecordPageResponse finalRes = new MiniMsgCustomerRecordPageResponse(microPage);
		return BaseResponse.success(finalRes);
	}

	@Override
	public BaseResponse<MiniMsgCustomerRecordListResponse> list(@RequestBody @Valid MiniMsgCustomerRecordListRequest miniProgramSubscribeMessageCustomerRecordListReq) {
		MiniMsgCusRecordQueryRequest queryReq = KsBeanUtil.convert(miniProgramSubscribeMessageCustomerRecordListReq, MiniMsgCusRecordQueryRequest.class);
		List<MiniMsgCustomerRecord> miniMsgCustomerRecordList = miniMsgCustomerRecordService.list(queryReq);
		List<MiniMsgCustomerRecordVO> newList = miniMsgCustomerRecordList.stream().map(entity -> miniMsgCustomerRecordService.wrapperVo(entity)).collect(Collectors.toList());
		return BaseResponse.success(new MiniMsgCustomerRecordListResponse(newList));
	}

	@Override
	public BaseResponse<MiniMsgCustomerRecordByIdResponse> getById(@RequestBody @Valid MiniMsgCustomerRecordByIdRequest miniMsgCustomerRecordByIdRequest) {
		MiniMsgCustomerRecord miniMsgCustomerRecord =
		miniMsgCustomerRecordService.getOne(miniMsgCustomerRecordByIdRequest.getId());
		return BaseResponse.success(new MiniMsgCustomerRecordByIdResponse(miniMsgCustomerRecordService.wrapperVo(miniMsgCustomerRecord)));
	}

	@Override
	public BaseResponse<Long> countRecordsByTriggerNodeId() {
		Long returnNum =
				miniMsgCustomerRecordService.countRecordsByTriggerNodeId(TriggerNodeType.NEW_ACTIVITY);
		return BaseResponse.success(returnNum);
	}

	@Override
	public BaseResponse<MiniMsgCustomerRecordByActivityIdResponse> countRecordsByActivityId(MiniMsgCusRecordByActivityIdRequest messageCustomerRecordByActivityIdRequest) {
		Long returnNum =
				miniMsgCustomerRecordService.countRecordsByActivityId(messageCustomerRecordByActivityIdRequest.getActivityId());
		Long returnSuccessNum =
				miniMsgCustomerRecordService.countRecordsByActivityIdAndErrCode(messageCustomerRecordByActivityIdRequest.getActivityId());
		return BaseResponse.success(MiniMsgCustomerRecordByActivityIdResponse.builder().returnNum(returnNum).returnSuccessNum(returnSuccessNum).build());
	}

}

