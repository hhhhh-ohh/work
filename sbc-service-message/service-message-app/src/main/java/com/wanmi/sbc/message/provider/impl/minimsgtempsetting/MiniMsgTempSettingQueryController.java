package com.wanmi.sbc.message.provider.impl.minimsgtempsetting;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.message.api.provider.minimsgtempsetting.MiniMsgTempSettingQueryProvider;
import com.wanmi.sbc.message.api.request.minimsgtempsetting.MiniMsgTempSettingByIdRequest;
import com.wanmi.sbc.message.api.request.minimsgtempsetting.MiniMsgTempSettingByNodeIdRequest;
import com.wanmi.sbc.message.api.request.minimsgtempsetting.MiniMsgTempSettingListRequest;
import com.wanmi.sbc.message.api.request.minimsgtempsetting.MiniMsgTempSettingPageRequest;
import com.wanmi.sbc.message.api.request.minimsgtempsetting.MiniMsgTempSettingQueryRequest;
import com.wanmi.sbc.message.api.response.minimsgtempsetting.MiniMsgTempSettingByIdResponse;
import com.wanmi.sbc.message.api.response.minimsgtempsetting.MiniMsgTempSettingListResponse;
import com.wanmi.sbc.message.api.response.minimsgtempsetting.MiniMsgTempSettingPageResponse;
import com.wanmi.sbc.message.bean.vo.MiniMsgTemplateSettingVO;
import com.wanmi.sbc.message.minimsgtempsetting.model.root.MiniMsgTempSetting;
import com.wanmi.sbc.message.minimsgtempsetting.service.MiniMsgTempSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>小程序订阅消息模版配置表查询服务接口实现</p>
 * @author xufeng
 * @date 2022-08-12 11:19:52
 */
@RestController
@Validated
public class MiniMsgTempSettingQueryController implements MiniMsgTempSettingQueryProvider {
	@Autowired
	private MiniMsgTempSettingService miniMsgTempSettingService;

	@Override
	public BaseResponse<MiniMsgTempSettingPageResponse> page(@RequestBody @Valid MiniMsgTempSettingPageRequest miniProgramSubscribeTemplateSettingPageReq) {
		MiniMsgTempSettingQueryRequest queryReq = KsBeanUtil.convert(miniProgramSubscribeTemplateSettingPageReq, MiniMsgTempSettingQueryRequest.class);
		Page<MiniMsgTempSetting> miniProgramSubscribeTemplateSettingPage = miniMsgTempSettingService.page(queryReq);
		Page<MiniMsgTemplateSettingVO> newPage = miniProgramSubscribeTemplateSettingPage.map(entity -> miniMsgTempSettingService.wrapperVo(entity));
		MicroServicePage<MiniMsgTemplateSettingVO> microPage = new MicroServicePage<>(newPage, miniProgramSubscribeTemplateSettingPageReq.getPageable());
		MiniMsgTempSettingPageResponse finalRes = new MiniMsgTempSettingPageResponse(microPage);
		return BaseResponse.success(finalRes);
	}

	@Override
	public BaseResponse<MiniMsgTempSettingListResponse> list(@RequestBody @Valid MiniMsgTempSettingListRequest miniProgramSubscribeTemplateSettingListReq) {
		MiniMsgTempSettingQueryRequest queryReq = KsBeanUtil.convert(miniProgramSubscribeTemplateSettingListReq, MiniMsgTempSettingQueryRequest.class);
		List<MiniMsgTempSetting> miniMsgTempSettingList = miniMsgTempSettingService.list(queryReq);
		List<MiniMsgTemplateSettingVO> newList = miniMsgTempSettingList.stream().map(entity -> miniMsgTempSettingService.wrapperVo(entity)).collect(Collectors.toList());
		return BaseResponse.success(new MiniMsgTempSettingListResponse(newList));
	}

	@Override
	public BaseResponse<MiniMsgTempSettingByIdResponse> getById(@RequestBody @Valid MiniMsgTempSettingByIdRequest miniMsgTempSettingByIdRequest) {
		MiniMsgTempSetting miniMsgTempSetting =
		miniMsgTempSettingService.getOne(miniMsgTempSettingByIdRequest.getId());
		return BaseResponse.success(new MiniMsgTempSettingByIdResponse(miniMsgTempSettingService.wrapperVo(miniMsgTempSetting)));
	}


	@Override
	public BaseResponse<MiniMsgTempSettingByIdResponse> findByTriggerNodeId(@RequestBody @Valid MiniMsgTempSettingByNodeIdRequest templateSettingByTriggerNodeIdRequest) {
		MiniMsgTempSetting miniMsgTempSetting =
				miniMsgTempSettingService.findByTriggerNodeId(templateSettingByTriggerNodeIdRequest.getTriggerNodeId());
		return BaseResponse.success(new MiniMsgTempSettingByIdResponse(miniMsgTempSettingService.wrapperVo(miniMsgTempSetting)));
	}

}

