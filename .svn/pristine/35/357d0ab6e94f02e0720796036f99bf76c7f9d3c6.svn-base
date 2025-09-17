package com.wanmi.sbc.message.provider.impl.minimsgactivitysetting;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.empower.api.provider.minimsgcustomerrecord.MiniMsgCustomerRecordProvider;
import com.wanmi.sbc.empower.api.request.minimsgcustomerrecord.MiniMsgCusRecordModifyByActivityIdRequest;
import com.wanmi.sbc.message.api.provider.minimsgactivitysetting.MiniMsgActivitySettingProvider;
import com.wanmi.sbc.message.api.request.minimsgactivitysetting.MiniMsgActivitySettingAddRequest;
import com.wanmi.sbc.message.api.request.minimsgactivitysetting.MiniMsgActivitySettingDelByIdListRequest;
import com.wanmi.sbc.message.api.request.minimsgactivitysetting.MiniMsgActivitySettingDelByIdRequest;
import com.wanmi.sbc.message.api.request.minimsgactivitysetting.MiniMsgActivitySettingModifyByIdRequest;
import com.wanmi.sbc.message.api.request.minimsgactivitysetting.MiniMsgActivitySettingModifyByIdsRequest;
import com.wanmi.sbc.message.api.request.minimsgactivitysetting.MiniMsgActivitySettingModifyRequest;
import com.wanmi.sbc.message.api.response.minimsgactivitysetting.MiniMsgActivitySettingAddResponse;
import com.wanmi.sbc.message.api.response.minimsgactivitysetting.MiniMsgActivitySettingModifyResponse;
import com.wanmi.sbc.message.bean.enums.ProgramSendStatus;
import com.wanmi.sbc.message.bean.vo.MiniMsgActivitySettingVO;
import com.wanmi.sbc.message.minimsgactivitysetting.model.root.MiniMsgActivitySetting;
import com.wanmi.sbc.message.minimsgactivitysetting.service.MiniMsgActivitySettingService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * <p>小程序订阅消息配置表保存服务接口实现</p>
 * @author xufeng
 * @date 2022-08-11 16:16:32
 */
@RestController
@Validated
public class MiniMsgActivitySettingController implements MiniMsgActivitySettingProvider {
	@Autowired
	private MiniMsgActivitySettingService miniMsgActivitySettingService;

	@Autowired
	private MiniMsgCustomerRecordProvider miniMsgCustomerRecordProvider;

	@Override
	@Transactional
	public BaseResponse<MiniMsgActivitySettingAddResponse> add(@RequestBody @Valid MiniMsgActivitySettingAddRequest miniMsgActivitySettingAddRequest) {
		MiniMsgActivitySetting miniMsgActivitySetting = KsBeanUtil.convert(miniMsgActivitySettingAddRequest, MiniMsgActivitySetting.class);
		miniMsgActivitySetting.setSendStatus(ProgramSendStatus.NOT_SEND);
		miniMsgActivitySetting.setDelFlag(DeleteFlag.NO);
		if (miniMsgActivitySetting.getType()==0){
			miniMsgActivitySetting.setSendTime(LocalDateTime.now());
		}
		miniMsgActivitySetting.setScanFlag(Boolean.FALSE);
		// 新增活动配置
		MiniMsgActivitySetting response =
				miniMsgActivitySettingService.add(miniMsgActivitySetting);
		// 异步处理数据
		miniMsgActivitySettingService.dealCustomerRecord(response);
		return BaseResponse.success(new MiniMsgActivitySettingAddResponse(miniMsgActivitySettingService.wrapperVo(response)));
	}

	@Override
	public BaseResponse dealCustomerRecord(@RequestBody MiniMsgActivitySettingVO miniMsgActivitySettingVO) {
		MiniMsgActivitySetting miniMsgActivitySetting = KsBeanUtil.convert(miniMsgActivitySettingVO, MiniMsgActivitySetting.class);
		// 异步处理数据
		miniMsgActivitySettingService.dealCustomerRecord(miniMsgActivitySetting);
		return BaseResponse.SUCCESSFUL();
	}


	@Override
	public BaseResponse<MiniMsgActivitySettingModifyResponse> modify(@RequestBody @Valid MiniMsgActivitySettingModifyRequest miniMsgActivitySettingModifyRequest) {
		MiniMsgActivitySetting returnSetting =
				miniMsgActivitySettingService.getOne(miniMsgActivitySettingModifyRequest.getId());
		// 校验活动是未推送状态
		if (DeleteFlag.YES == returnSetting.getDelFlag() || ProgramSendStatus.NOT_SEND != returnSetting.getSendStatus()){
			throw new SbcRuntimeException(CommonErrorCodeEnum.K000003);
		}

		MiniMsgActivitySetting miniMsgActivitySetting = KsBeanUtil.convert(miniMsgActivitySettingModifyRequest, MiniMsgActivitySetting.class);

		miniMsgActivitySetting.setSendStatus(ProgramSendStatus.NOT_SEND);
		miniMsgActivitySetting.setType(returnSetting.getType());
		miniMsgActivitySetting.setSendTime(returnSetting.getSendTime());
		miniMsgActivitySetting.setDelFlag(DeleteFlag.NO);
		miniMsgActivitySetting.setPreCount(returnSetting.getPreCount());
		miniMsgActivitySetting.setCreateTime(returnSetting.getCreateTime());
		miniMsgActivitySetting.setCreatePerson(returnSetting.getCreatePerson());
		miniMsgActivitySetting.setScanFlag(returnSetting.getScanFlag());
		MiniMsgActivitySetting response =
				miniMsgActivitySettingService.modify(miniMsgActivitySetting);
		return BaseResponse.success(new MiniMsgActivitySettingModifyResponse(
				miniMsgActivitySettingService.wrapperVo(response)));
	}

	@Override
	@Transactional
	public BaseResponse deleteById(@RequestBody @Valid MiniMsgActivitySettingDelByIdRequest miniMsgActivitySettingDelByIdRequest) {
		MiniMsgActivitySetting returnSetting =
				miniMsgActivitySettingService.getOne(miniMsgActivitySettingDelByIdRequest.getId());
		// 校验活动不是未推送及失败状态，抛出异常
		if (DeleteFlag.YES == returnSetting.getDelFlag()){
			throw new SbcRuntimeException(CommonErrorCodeEnum.K000003);
		}

		if (ProgramSendStatus.SENDING == returnSetting.getSendStatus() || ProgramSendStatus.SEND_DOWN == returnSetting.getSendStatus()){
			throw new SbcRuntimeException(CommonErrorCodeEnum.K000003);
		}
		miniMsgActivitySettingService.deleteById(miniMsgActivitySettingDelByIdRequest.getId());
		// 删除时还原绑定的活动
		if (ProgramSendStatus.NOT_SEND == returnSetting.getSendStatus()) {
			miniMsgCustomerRecordProvider.updateByActivityId(MiniMsgCusRecordModifyByActivityIdRequest.builder()
					.activityId(returnSetting.getId()).build());
		}
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse deleteByIdList(@RequestBody @Valid MiniMsgActivitySettingDelByIdListRequest miniMsgActivitySettingDelByIdListRequest) {
		miniMsgActivitySettingService.deleteByIdList(miniMsgActivitySettingDelByIdListRequest.getIdList());
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse modifyById(@RequestBody @Valid MiniMsgActivitySettingModifyByIdRequest miniProgramSubscribeMessageActivitySettingModifyRequest) {
		miniMsgActivitySettingService.modifyById(miniProgramSubscribeMessageActivitySettingModifyRequest.getSendStatus(), miniProgramSubscribeMessageActivitySettingModifyRequest.getPreCount(), miniProgramSubscribeMessageActivitySettingModifyRequest.getRealCount(), miniProgramSubscribeMessageActivitySettingModifyRequest.getId());
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse modifyScanFlagByIds(@RequestBody @Valid MiniMsgActivitySettingModifyByIdsRequest miniProgramSubscribeMessageActivitySettingModifyRequest) {
		miniMsgActivitySettingService.modifyScanFlagByIds(miniProgramSubscribeMessageActivitySettingModifyRequest.getIds());
		return BaseResponse.SUCCESSFUL();
	}

}

