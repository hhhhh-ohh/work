package com.wanmi.sbc.message.provider.impl.minimsgactivitysetting;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.message.api.provider.minimsgactivitysetting.MiniMsgActivitySettingQueryProvider;
import com.wanmi.sbc.message.api.request.minimsgactivitysetting.MiniMsgActivitySettingByIdRequest;
import com.wanmi.sbc.message.api.request.minimsgactivitysetting.MiniMsgActivitySettingPageRequest;
import com.wanmi.sbc.message.api.request.minimsgactivitysetting.MiniMsgActivitySettingQueryRequest;
import com.wanmi.sbc.message.api.response.minimsgactivitysetting.MiniMsgActivitySettingByIdResponse;
import com.wanmi.sbc.message.api.response.minimsgactivitysetting.MiniMsgActivitySettingListResponse;
import com.wanmi.sbc.message.api.response.minimsgactivitysetting.MiniMsgActivitySettingPageResponse;
import com.wanmi.sbc.message.bean.vo.MiniMsgActivitySettingVO;
import com.wanmi.sbc.message.minimsgactivitysetting.model.root.MiniMsgActivitySetting;
import com.wanmi.sbc.message.minimsgactivitysetting.service.MiniMsgActivitySettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>小程序订阅消息配置表查询服务接口实现</p>
 * @author xufeng
 * @date 2022-08-11 16:16:32
 */
@RestController
@Validated
public class MiniMsgActivitySettingQueryController implements MiniMsgActivitySettingQueryProvider {
	@Autowired
	private MiniMsgActivitySettingService miniMsgActivitySettingService;

	@Override
	public BaseResponse<MiniMsgActivitySettingPageResponse> page(@RequestBody @Valid MiniMsgActivitySettingPageRequest miniProgramSubscribeMessageActivitySettingPageReq) {
		MiniMsgActivitySettingQueryRequest queryReq = KsBeanUtil.convert(miniProgramSubscribeMessageActivitySettingPageReq, MiniMsgActivitySettingQueryRequest.class);
		Page<MiniMsgActivitySetting> miniProgramSubscribeMessageActivitySettingPage = miniMsgActivitySettingService.page(queryReq);
		Page<MiniMsgActivitySettingVO> newPage = miniProgramSubscribeMessageActivitySettingPage.map(entity -> miniMsgActivitySettingService.wrapperVo(entity));
		MicroServicePage<MiniMsgActivitySettingVO> microPage = new MicroServicePage<>(newPage, miniProgramSubscribeMessageActivitySettingPageReq.getPageable());
		MiniMsgActivitySettingPageResponse finalRes = new MiniMsgActivitySettingPageResponse(microPage);
		return BaseResponse.success(finalRes);
	}

	@Override
	public BaseResponse<MiniMsgActivitySettingByIdResponse> getById(@RequestBody @Valid MiniMsgActivitySettingByIdRequest miniMsgActivitySettingByIdRequest) {
		MiniMsgActivitySetting miniMsgActivitySetting =
		miniMsgActivitySettingService.getOne(miniMsgActivitySettingByIdRequest.getId());
		return BaseResponse.success(new MiniMsgActivitySettingByIdResponse(miniMsgActivitySettingService.wrapperVo(miniMsgActivitySetting)));
	}

	@Override
	public BaseResponse<MiniMsgActivitySettingListResponse> list(@RequestBody @Valid MiniMsgActivitySettingQueryRequest queryRequest) {
		List<MiniMsgActivitySetting> list = miniMsgActivitySettingService.list(queryRequest);
		List<MiniMsgActivitySettingVO> voList = list.stream().map(entity -> miniMsgActivitySettingService.wrapperVo(entity)).collect(Collectors.toList());
		MiniMsgActivitySettingListResponse finalRes = new MiniMsgActivitySettingListResponse(voList);
		return BaseResponse.success(finalRes);
	}

}

