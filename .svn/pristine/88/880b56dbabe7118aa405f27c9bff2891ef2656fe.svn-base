package com.wanmi.sbc.message.provider.impl.smssend;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.message.api.provider.smssend.SmsSendSaveProvider;
import com.wanmi.sbc.message.api.request.smssend.SmsSendAddRequest;
import com.wanmi.sbc.message.api.request.smssend.SmsSendDelByIdListRequest;
import com.wanmi.sbc.message.api.request.smssend.SmsSendDelByIdRequest;
import com.wanmi.sbc.message.api.request.smssend.SmsSendDetailSendRequest;
import com.wanmi.sbc.message.api.request.smssend.SmsSendModifyRequest;
import com.wanmi.sbc.message.api.request.smssend.SmsSendSaveRequest;
import com.wanmi.sbc.message.api.response.smssend.SmsSendAddResponse;
import com.wanmi.sbc.message.api.response.smssend.SmsSendDetailSendResponse;
import com.wanmi.sbc.message.api.response.smssend.SmsSendModifyResponse;
import com.wanmi.sbc.message.bean.enums.*;
import com.wanmi.sbc.message.smssend.model.root.SmsSend;
import com.wanmi.sbc.message.smssend.service.SmsSendService;
import com.wanmi.sbc.message.smssend.service.SmsSendTaskService;
import com.wanmi.sbc.message.smssign.model.root.SmsSign;
import com.wanmi.sbc.message.smssign.service.SmsSignService;
import com.wanmi.sbc.message.smstemplate.model.root.SmsTemplate;
import com.wanmi.sbc.message.smstemplate.service.SmsTemplateService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.time.LocalDateTime;

/**
 * <p>短信发送保存服务接口实现</p>
 * @author zgl
 * @date 2019-12-03 15:36:05
 */
@RestController
@Validated
public class SmsSendSaveController implements SmsSendSaveProvider {
	@Autowired
	private SmsSendService smsSendService;
	@Autowired
	private SmsSignService smsSignService;
	@Autowired
	private SmsTemplateService smsTemplateService;

	@Autowired
	private SmsSendTaskService smsSendTaskService;

	@Override
	public BaseResponse<SmsSendAddResponse> add(@RequestBody @Valid SmsSendAddRequest smsSendAddRequest) {
		SmsSend smsSend = new SmsSend();
		KsBeanUtil.copyPropertiesThird(smsSendAddRequest, smsSend);
		paramProcess(smsSend);
		return BaseResponse.success(new SmsSendAddResponse(
				smsSendService.wrapperVo(smsSendService.add(smsSend))));
	}

	@Override
	public BaseResponse<SmsSendModifyResponse> modify(@RequestBody @Valid SmsSendModifyRequest smsSendModifyRequest) {
		SmsSend smsSend = new SmsSend();
		KsBeanUtil.copyPropertiesThird(smsSendModifyRequest, smsSend);
		SmsSend checkBean = smsSendService.getById(smsSend.getId());
		if(ResendType.YES == checkBean.getResendType()
				|| (SendType.DELAY == checkBean.getSendType()
						&& checkBean.getSendTime().minusSeconds(30).isAfter( LocalDateTime.now())
						&& SendStatus.NO_BEGIN == checkBean.getStatus()
				)
		){
			paramProcess(smsSend);
			return BaseResponse.success(new SmsSendModifyResponse(
					smsSendService.wrapperVo(smsSendService.modify(smsSend))));
		}else{
			throw new SbcRuntimeException(MessageErrorCodeEnum.K090014);
 		}

	}

	@Override
	public BaseResponse save(@RequestBody @Valid SmsSendSaveRequest smsSendSaveRequest){
        SmsSend smsSend = new SmsSend();
        KsBeanUtil.copyPropertiesThird(smsSendSaveRequest, smsSend);
        smsSendService.save(smsSend);
        return BaseResponse.SUCCESSFUL();
    }

	@Override
	public BaseResponse deleteById(@RequestBody @Valid SmsSendDelByIdRequest smsSendDelByIdRequest) {
		try {
			smsSendService.deleteById(smsSendDelByIdRequest.getId());
			return BaseResponse.SUCCESSFUL();
		}catch (Exception e){
			throw new SbcRuntimeException(MessageErrorCodeEnum.K090015);
		}
	}

	@Override
	public BaseResponse deleteByIdList(@RequestBody @Valid SmsSendDelByIdListRequest smsSendDelByIdListRequest) {
		smsSendService.deleteByIdList(smsSendDelByIdListRequest.getIdList());
		return BaseResponse.SUCCESSFUL();
	}

	private SmsSend paramProcess(SmsSend smsSend){

		StringBuilder receiveContext = new StringBuilder();
		receiveContext.append(smsSend.getReceiveType().getContent());
		if(StringUtils.isNotEmpty(smsSend.getManualAdd())){
			receiveContext.append("；手工添加");
		}
		smsSend.setReceiveContext(receiveContext.toString());

		StringBuilder context = new StringBuilder();
		SmsSign smsSign = this.smsSignService.getById(smsSend.getSignId());
		if(smsSign == null || smsSign.getReviewStatus()!=ReviewStatus.REVIEWPASS||smsSign.getDelFlag().equals(DeleteFlag.YES)){
			throw new SbcRuntimeException(MessageErrorCodeEnum.K090003);
		}else{
			context.append('【')
					.append(smsSign.getSmsSignName())
					.append('】');
		}
		SmsTemplate smsTemplate = this.smsTemplateService.findByTemplateCode(smsSend.getTemplateCode());
		if(smsTemplate==null || smsTemplate.getReviewStatus()!=ReviewStatus.REVIEWPASS||smsTemplate.getDelFlag().equals(DeleteFlag.YES)){
			throw new SbcRuntimeException(MessageErrorCodeEnum.K090008);
		}else {
			context.append(smsTemplate.getTemplateContent());
			smsSend.setSmsSettingId(smsTemplate.getSmsSettingId());
		}
		smsSend.setContext(context.toString());

		if(smsSend.getSendType()==SendType.DELAY){
			if(smsSend.getSendTime().getHour()> Constants.NUM_22||smsSend.getSendTime().getHour()<Constants.EIGHT){
				throw new SbcRuntimeException(MessageErrorCodeEnum.K090013);
			}
		}else if(smsSend.getSendType()==SendType.NOW){
			if(LocalDateTime.now().getHour()>Constants.NUM_22||LocalDateTime.now().getHour()<Constants.EIGHT){
				throw new SbcRuntimeException(MessageErrorCodeEnum.K090013);
			}
		}

		return smsSend;
	}

    @Override
    public BaseResponse<SmsSendDetailSendResponse> sendDetail(@RequestBody @Valid SmsSendDetailSendRequest request){
        boolean res = smsSendTaskService.sendDetail(KsBeanUtil.convert(request.getSendDTO(), SmsSend.class));
        return BaseResponse.success(SmsSendDetailSendResponse.builder().sendFlag(res).build());
    }
}

