package com.wanmi.sbc.message.provider.impl.minimsgtempsetting;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.message.api.provider.minimsgtempsetting.MiniMsgTempSettingProvider;
import com.wanmi.sbc.message.api.request.minimsgtempsetting.MiniMsgTempSettingBatchModifyRequest;
import com.wanmi.sbc.message.api.request.minimsgtempsetting.MiniMsgTempSettingModifyRequest;
import com.wanmi.sbc.message.api.response.minimsgtempsetting.MiniMsgTempSettingModifyResponse;
import com.wanmi.sbc.message.minimsgtempsetting.model.root.MiniMsgTempSetting;
import com.wanmi.sbc.message.minimsgtempsetting.service.MiniMsgTempSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * <p>小程序订阅消息模版配置表保存服务接口实现</p>
 * @author xufeng
 * @date 2022-08-12 11:19:52
 */
@RestController
@Validated
public class MiniMsgTempSettingController implements MiniMsgTempSettingProvider {
	@Autowired
	private MiniMsgTempSettingService miniMsgTempSettingService;

	@Override
	public BaseResponse<MiniMsgTempSettingModifyResponse> modify(@RequestBody @Valid MiniMsgTempSettingModifyRequest miniMsgTempSettingModifyRequest) {
		MiniMsgTempSetting miniMsgTempSetting =
				miniMsgTempSettingService.getOne(miniMsgTempSettingModifyRequest.getId());
		miniMsgTempSetting.setNewTips(miniMsgTempSettingModifyRequest.getNewTips());
		return BaseResponse.success(new MiniMsgTempSettingModifyResponse(
				miniMsgTempSettingService.wrapperVo(miniMsgTempSettingService.modify(miniMsgTempSetting))));
	}

	@Override
	public BaseResponse batchModify(@RequestBody @Valid MiniMsgTempSettingBatchModifyRequest miniMsgTempSettingBatchModifyRequest) {
		miniMsgTempSettingBatchModifyRequest.getDataList().forEach(miniProgramSubscribeTemplateSettingBatchModifyData -> {
			miniMsgTempSettingService.modifyByTid(miniProgramSubscribeTemplateSettingBatchModifyData.getPriTmplId(), miniProgramSubscribeTemplateSettingBatchModifyData.getTid());
		});
		return BaseResponse.SUCCESSFUL();
	}

}

