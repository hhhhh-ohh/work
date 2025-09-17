package com.wanmi.sbc.message;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.empower.api.provider.sms.SmsProvider;
import com.wanmi.sbc.empower.api.provider.sms.aliyun.SmsSignAliyunProvider;
import com.wanmi.sbc.empower.api.provider.sms.aliyun.SmsTemplateAliyunProvider;
import com.wanmi.sbc.empower.api.request.sms.SmsSendRequest;
import com.wanmi.sbc.empower.api.request.sms.aliyun.*;
import com.wanmi.sbc.empower.api.response.sms.aliyun.SmsSignAliyunAddResponse;
import com.wanmi.sbc.empower.api.response.sms.aliyun.SmsSignAliyunModifyResponse;
import com.wanmi.sbc.empower.bean.vo.SmsResponseAliyunVO;
import com.wanmi.sbc.message.bean.constant.SmsResponseCode;
import com.wanmi.sbc.message.bean.enums.SmsType;
import com.wanmi.sbc.message.smssenddetail.model.root.SmsSendDetail;
import com.wanmi.sbc.message.smssign.model.root.SmsSign;
import com.wanmi.sbc.message.smssign.service.SmsSignService;
import com.wanmi.sbc.message.smstemplate.model.root.SmsTemplate;
import com.wanmi.sbc.message.smstemplate.repository.SmsTemplateRepository;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: zgl
 * \* Date: 2019-12-4
 * \* Time: 14:29
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
@Component
public class SmsProxy  {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SmsTemplateRepository smsTemplateRepository;

    @Autowired
    private SmsSignService smsSignService;

    @Autowired
    private SmsSignAliyunProvider smsSignAliyunProvider;

    @Autowired
    private SmsTemplateAliyunProvider smsTemplateAliyunProvider;

    @Autowired
    private SmsProvider smsProvider;

    public SmsBaseResponse addSmsSign(SmsSign smsSign){
        SmsSignAliyunAddRequest request = KsBeanUtil.copyPropertiesThird(smsSign, SmsSignAliyunAddRequest.class);
        request.setSignName(smsSign.getSmsSignName());
        request.setSignSource(smsSign.getSignSource().toValue());
        List<SmsSignAliyunFileRequest> signFilList =
                smsSign.getSmsSignFileInfoList().stream()
                        .map(smsSignFileInfo -> KsBeanUtil.copyPropertiesThird(smsSignFileInfo, SmsSignAliyunFileRequest.class))
                        .collect(Collectors.toList());
        request.setSignFilList(signFilList);
        BaseResponse<SmsSignAliyunAddResponse> response = smsSignAliyunProvider.add(request);
        return KsBeanUtil.copyPropertiesThird(response.getContext(), SmsBaseResponse.class);
    }
    public SmsBaseResponse modifySmsSign(SmsSign smsSign){
        SmsSignAliyunModifyRequest request = KsBeanUtil.copyPropertiesThird(smsSign, SmsSignAliyunModifyRequest.class);
        request.setSignName(smsSign.getSmsSignName());
        request.setSignSource(smsSign.getSignSource().toValue());
        List<SmsSignAliyunFileRequest> signFilList =
                smsSign.getSmsSignFileInfoList().stream()
                        .map(smsSignFileInfo -> KsBeanUtil.copyPropertiesThird(smsSignFileInfo, SmsSignAliyunFileRequest.class))
                        .collect(Collectors.toList());
        request.setSignFilList(signFilList);
        BaseResponse<SmsSignAliyunModifyResponse> response = smsSignAliyunProvider.modify(request);
        return KsBeanUtil.copyPropertiesThird(response.getContext(), SmsBaseResponse.class);
    }

    public SmsBaseResponse deleteSmsSign(SmsSign smsSign){
        SmsSignAliyunDeleteRequest request = KsBeanUtil.copyPropertiesThird(smsSign, SmsSignAliyunDeleteRequest.class);
        request.setSignName(smsSign.getSmsSignName());
        BaseResponse response = smsSignAliyunProvider.delete(request);
        return KsBeanUtil.copyPropertiesThird(response.getContext(), SmsBaseResponse.class);
    }

    public SmsBaseResponse querySmsSign(SmsSign smsSign){
        SmsSignAliyunQueryRequest request = KsBeanUtil.copyPropertiesThird(smsSign, SmsSignAliyunQueryRequest.class);
        request.setSignName(smsSign.getSmsSignName());
        BaseResponse response = smsSignAliyunProvider.query(request);
        return KsBeanUtil.copyPropertiesThird(response.getContext(), SmsBaseResponse.class);
    }

    public SmsBaseResponse addSmsTemplate(SmsTemplate smsTemplate){
        SmsTemplateAliyunAddRequest request = KsBeanUtil.copyPropertiesThird(smsTemplate, SmsTemplateAliyunAddRequest.class);
        request.setTemplateType(smsTemplate.getTemplateType().toValue());
        BaseResponse response = smsTemplateAliyunProvider.add(request);
        return KsBeanUtil.copyPropertiesThird(response.getContext(), SmsBaseResponse.class);
    }

    public SmsBaseResponse modifySmsTemplate(SmsTemplate smsTemplate){
        SmsTemplateAliyunModifyRequest request = KsBeanUtil.copyPropertiesThird(smsTemplate, SmsTemplateAliyunModifyRequest.class);
        request.setTemplateType(smsTemplate.getTemplateType().toValue());
        BaseResponse response = smsTemplateAliyunProvider.modify(request);
        return KsBeanUtil.copyPropertiesThird(response.getContext(), SmsBaseResponse.class);
    }

    public SmsBaseResponse deleteSmsTemplate(SmsTemplate smsTemplate){
        SmsTemplateAliyunDeleteRequest request = KsBeanUtil.copyPropertiesThird(smsTemplate, SmsTemplateAliyunDeleteRequest.class);
        BaseResponse response = smsTemplateAliyunProvider.delete(request);
        return KsBeanUtil.copyPropertiesThird(response.getContext(), SmsBaseResponse.class);
    }

    public SmsBaseResponse querySmsTemplate(SmsTemplate smsTemplate){
        SmsTemplateAliyunQueryRequest request = KsBeanUtil.copyPropertiesThird(smsTemplate, SmsTemplateAliyunQueryRequest.class);
        BaseResponse response = smsTemplateAliyunProvider.query(request);
        return KsBeanUtil.copyPropertiesThird(response.getContext(), SmsBaseResponse.class);
    }

    public SmsBaseResponse sendSms(SmsSendDetail smsSendDetail){
        SmsSendRequest smsSendRequest = KsBeanUtil.copyPropertiesThird(smsSendDetail, SmsSendRequest.class);
        if(smsSendRequest.getPhoneNumbers().contains("；")){
            smsSendRequest.setPhoneNumbers(smsSendRequest.getPhoneNumbers().replace('；', ','));
        }
        if(smsSendRequest.getPhoneNumbers().contains(";")){
            smsSendRequest.setPhoneNumbers(smsSendRequest.getPhoneNumbers().replace(';', ','));
        }
        if(smsSendRequest.getPhoneNumbers().contains("，")){
            smsSendRequest.setPhoneNumbers(smsSendRequest.getPhoneNumbers().replace('，', ','));
        }
        //根据业务类型获取模板编码
        if (StringUtils.isBlank(smsSendDetail.getTemplateCode())
                && StringUtils.isNotBlank(smsSendDetail.getBusinessType())) {
            SmsTemplate smsTemplate = smsTemplateRepository.findByBusinessTypeAndDelFlag(smsSendDetail.getBusinessType(),
                    DeleteFlag.NO);
            if (Objects.nonNull(smsTemplate)) {
                smsSendRequest.setTemplateCode(smsTemplate.getTemplateCode());
                if (Objects.nonNull(smsTemplate.getSignId())) {
                    smsSendRequest.setSignName(smsSignService.getById(smsTemplate.getSignId()).getSmsSignName());
                }
                //通知类短信验证开关状态是否关闭
                if (SmsType.NOTICE == smsTemplate.getTemplateType()
                        && (Objects.isNull(smsTemplate.getOpenFlag()) || Boolean.FALSE.equals(smsTemplate.getOpenFlag()))) {
                    log.error("短信模板被关闭".concat(smsSendDetail.getBusinessType()));
                    SmsBaseResponse smsBaseResponse = new SmsBaseResponse();
                    smsBaseResponse.setCode(SmsResponseCode.DEFAULT_CONFIG_ERROR);
                    return smsBaseResponse;
                }
                //兼容华信
                smsSendRequest.setTemplateContent(smsTemplate.getTemplateContent());
            } else {
                log.error("发送短信模板不存在，businessType={}", smsSendDetail.getBusinessType());
                SmsBaseResponse smsBaseResponse = new SmsBaseResponse();
                smsBaseResponse.setCode(SmsResponseCode.DEFAULT_CONFIG_ERROR);
                return smsBaseResponse;
            }
        }
        if (smsSendDetail.getSignId() != null) {
            smsSendRequest.setSignName(smsSignService.getById(smsSendDetail.getSignId()).getSmsSignName());
        }
        if(StringUtils.isNotBlank(smsSendDetail.getTemplateParam())){
            JSONObject jsonObject = JSONObject.parseObject(smsSendDetail.getTemplateParam());
            Map<String, String> templateParamMap = jsonObject.entrySet().stream()
                    .collect(Collectors.toMap(Map.Entry::getKey,
                            e -> (String) e.getValue()));
            smsSendRequest.setTemplateParamMap(templateParamMap);
        }
        BaseResponse response = smsProvider.send(smsSendRequest);
        if(response.getContext() == null){
            return SmsBaseResponse.builder().Code(SmsResponseCode.SUCCESS).build();
        }else{
            SmsBaseResponse smsBaseResponse = KsBeanUtil.convert(response.getContext(),SmsBaseResponse.class);
            if(StringUtils.isBlank(smsBaseResponse.getCode())
                    && response.getContext() instanceof SmsResponseAliyunVO){
                smsBaseResponse.setCode(((SmsResponseAliyunVO) response.getContext()).getCode());
                smsBaseResponse.setMessage(((SmsResponseAliyunVO) response.getContext()).getMessage());
                smsBaseResponse.setBizId(((SmsResponseAliyunVO) response.getContext()).getBizId());
            }
            return smsBaseResponse;
        }
    }
}
