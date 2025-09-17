package com.wanmi.sbc.message.provider.impl.minimsgsetting;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.message.api.provider.minimsgsetting.MiniMsgSettingQueryProvider;
import com.wanmi.sbc.message.api.request.minimsgsetting.MiniMsgSettingByIdRequest;
import com.wanmi.sbc.message.api.request.minimsgsetting.MiniMsgSettingByNodeIdRequest;
import com.wanmi.sbc.message.api.request.minimsgsetting.MiniMsgSettingListRequest;
import com.wanmi.sbc.message.api.request.minimsgsetting.MiniMsgSettingPageRequest;
import com.wanmi.sbc.message.api.request.minimsgsetting.MiniMsgSettingQueryRequest;
import com.wanmi.sbc.message.api.response.minimsgsetting.MiniMsgSettingByIdResponse;
import com.wanmi.sbc.message.api.response.minimsgsetting.MiniMsgSettingListResponse;
import com.wanmi.sbc.message.api.response.minimsgsetting.MiniMsgSettingPageResponse;
import com.wanmi.sbc.message.bean.vo.MiniMsgSettingVO;
import com.wanmi.sbc.message.minimsgsetting.model.root.MiniMsgSetting;
import com.wanmi.sbc.message.minimsgsetting.service.MiniMsgSettingService;
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
 * @date 2022-08-08 11:37:13
 */
@RestController
@Validated
public class MiniMsgSettingQueryController implements MiniMsgSettingQueryProvider {
	@Autowired
	private MiniMsgSettingService miniMsgSettingService;

	@Override
	public BaseResponse<MiniMsgSettingPageResponse> page(@RequestBody @Valid MiniMsgSettingPageRequest miniProgramSubscribeMessageSettingPageReq) {
		MiniMsgSettingQueryRequest queryReq = KsBeanUtil.convert(miniProgramSubscribeMessageSettingPageReq, MiniMsgSettingQueryRequest.class);
		Page<MiniMsgSetting> miniProgramSubscribeMessageSettingPage = miniMsgSettingService.page(queryReq);
		Page<MiniMsgSettingVO> newPage = miniProgramSubscribeMessageSettingPage.map(entity -> miniMsgSettingService.wrapperVo(entity));
		MicroServicePage<MiniMsgSettingVO> microPage = new MicroServicePage<>(newPage, miniProgramSubscribeMessageSettingPageReq.getPageable());
		MiniMsgSettingPageResponse finalRes = new MiniMsgSettingPageResponse(microPage);
		return BaseResponse.success(finalRes);
	}

	@Override
	public BaseResponse<MiniMsgSettingListResponse> list(@RequestBody @Valid MiniMsgSettingListRequest miniProgramSubscribeMessageSettingListReq) {
		MiniMsgSettingQueryRequest queryReq = KsBeanUtil.convert(miniProgramSubscribeMessageSettingListReq, MiniMsgSettingQueryRequest.class);
		List<MiniMsgSetting> miniMsgSettingList = miniMsgSettingService.list(queryReq);
		List<MiniMsgSettingVO> newList = miniMsgSettingList.stream().map(entity -> miniMsgSettingService.wrapperVo(entity)).collect(Collectors.toList());
		return BaseResponse.success(new MiniMsgSettingListResponse(newList));
	}

	@Override
	public BaseResponse<MiniMsgSettingByIdResponse> getById(@RequestBody @Valid MiniMsgSettingByIdRequest miniMsgSettingByIdRequest) {
		MiniMsgSetting miniMsgSetting =
		miniMsgSettingService.getOne(miniMsgSettingByIdRequest.getId());
		return BaseResponse.success(new MiniMsgSettingByIdResponse(miniMsgSettingService.wrapperVo(miniMsgSetting)));
	}

	@Override
	public BaseResponse<MiniMsgSettingByIdResponse> findByNodeId(@RequestBody @Valid MiniMsgSettingByNodeIdRequest miniMsgSettingByNodeIdRequest) {
		MiniMsgSetting miniMsgSetting =
				miniMsgSettingService.findByNodeId(miniMsgSettingByNodeIdRequest.getNodeId());
		return BaseResponse.success(new MiniMsgSettingByIdResponse(miniMsgSettingService.wrapperVo(miniMsgSetting)));
	}

}

