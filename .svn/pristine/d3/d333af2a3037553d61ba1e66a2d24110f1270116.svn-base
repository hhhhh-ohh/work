package com.wanmi.sbc.empower.sms.service.aliyun;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.empower.api.request.sms.SmsSettingQueryRequest;
import com.wanmi.sbc.empower.api.request.sms.aliyun.*;
import com.wanmi.sbc.empower.bean.constant.AliyunSmsResponseCodeMapping;
import com.wanmi.sbc.empower.bean.constant.SmsResponseCode;
import com.wanmi.sbc.empower.bean.enums.SmsPlatformType;
import com.wanmi.sbc.empower.bean.vo.SmsResponseAliyunVO;
import com.wanmi.sbc.empower.sms.model.root.SmsSetting;
import com.wanmi.sbc.empower.sms.service.SmsSettingService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @ClassName AliyunSmsService
 * @Description aliyun短信平台接口调用
 * @Author lvzhenwei
 * @Date 2019/12/4 10:01
 **/
@Service("aliyunSmsService")
@Slf4j
public class AliyunSmsService {

    @Autowired
    private SmsSettingService smsSettingService;

    /**
     * @Author lvzhenwei
     * @Description 调用阿里云短信平台接口统一入口
     * @Date 10:50 2019/12/4
     * @Param [request]
     * @return com.aliyuncs.CommonResponse
     **/
    public SmsResponseAliyunVO doAction(CommonRequest request){
        return doAction(request, null);
    }
    public SmsResponseAliyunVO doAction(CommonRequest request, Long smsSettingId){
        SmsResponseAliyunVO aliyunSmsResponse = new SmsResponseAliyunVO();
        SmsSetting smsSetting = smsSettingId != null
                ? smsSettingService.getById(smsSettingId)
                : smsSettingService.getSmsSettingInfoByParam(SmsSettingQueryRequest.builder().type(SmsPlatformType.ALIYUN).build());
        if(smsSetting == null || !SmsPlatformType.ALIYUN.equals(smsSetting.getType())){
            log.error("阿里云短信配置不存在！request={}", JSON.toJSONString(request));
            aliyunSmsResponse.setCode(CommonErrorCodeEnum.K000001.getCode());
            aliyunSmsResponse.setMessage("阿里云短信配置不存在！");
            return aliyunSmsResponse;
        }
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", smsSetting.getAccessKeyId(), smsSetting.getAccessKeySecret());
        IAcsClient client = new DefaultAcsClient(profile);
        try {
            request.setSysMethod(MethodType.POST);
            request.setSysDomain("dysmsapi.aliyuncs.com");
            request.setSysVersion("2017-05-25");
            request.putQueryParameter("RegionId", "cn-hangzhou");
            CommonResponse response = client.getCommonResponse(request);
            aliyunSmsResponse = JSONObject.parseObject(response.getData(), SmsResponseAliyunVO.class);
            String code = StringUtils.isBlank(AliyunSmsResponseCodeMapping.CODE_MAPPING.get(aliyunSmsResponse.getCode()))
                    ? SmsResponseCode.DEFAULT_ERROR
                    : AliyunSmsResponseCodeMapping.CODE_MAPPING.get(aliyunSmsResponse.getCode());
            aliyunSmsResponse.setCode(code);
            if(!SmsResponseCode.SUCCESS.equals(aliyunSmsResponse.getCode())) {
                log.error("请求阿里云失败，request={}，response:{}"
                        , JSON.toJSONString(request), JSON.toJSONString(response));
            }
        } catch (ServerException e) {
            log.error("请求阿里云异常", e);
            aliyunSmsResponse.setCode(CommonErrorCodeEnum.K000001.getCode());
            aliyunSmsResponse.setMessage(e.getErrMsg());
        } catch (ClientException e) {
            log.error("发送短信异常", e);
            aliyunSmsResponse.setCode(CommonErrorCodeEnum.K000001.getCode());
            aliyunSmsResponse.setMessage(e.getErrMsg());
        }
        aliyunSmsResponse.setSmsSettingId(smsSetting.getId());
        return aliyunSmsResponse;
    }

    /**
     * @return com.aliyuncs.CommonResponse
     * @Author lvzhenwei
     * @Description aliyun短信平台接口--新增短信签名接口
     * @Date 10:51 2019/12/4
     * @Param [smsSignQueryRequest]
     **/
    public SmsResponseAliyunVO addSmsSign(SmsSignAliyunAddRequest smsSign) {
        CommonRequest request = new CommonRequest();
        request.setSysAction("addSmsSign");
        request.putQueryParameter("SignName", smsSign.getSignName());
        request.putQueryParameter("SignSource", String.valueOf(smsSign.getSignSource()));
        request.putQueryParameter("Remark", smsSign.getRemark());
        int i = 0;
        for (SmsSignAliyunFileRequest smsSignFileInfo : smsSign.getSignFilList()) {
            i++;
            request.putQueryParameter("SignFileList." + i + ".FileSuffix", smsSignFileInfo.getFileName());
            request.putQueryParameter("SignFileList." + i + ".FileContents", smsSignFileInfo.getFileUrl());
        }
        return doAction(request, smsSign.getSmsSettingId());
    }

    /**
     * @return com.aliyuncs.CommonResponse
     * @Author lvzhenwei
     * @Description aliyun短信平台接口--编辑短信签名接口
     * @Date 10:51 2019/12/4
     * @Param [smsSignQueryRequest]
     **/
    public SmsResponseAliyunVO modifySmsSign(SmsSignAliyunModifyRequest smsSign) {
        CommonRequest request = new CommonRequest();
        request.setSysAction("modifySmsSign");
        request.putQueryParameter("SignName", smsSign.getSignName());
        request.putQueryParameter("SignSource", String.valueOf(smsSign.getSignSource()));
        request.putQueryParameter("Remark", smsSign.getRemark());
        int i = 0;
        for (SmsSignAliyunFileRequest smsSignFileInfo : smsSign.getSignFilList()) {
            i++;
            request.putQueryParameter("SignFileList." + i + ".FileSuffix", smsSignFileInfo.getFileName());
            request.putQueryParameter("SignFileList." + i + ".FileContents", smsSignFileInfo.getFileUrl());
        }
        return doAction(request, smsSign.getSmsSettingId());
    }

    /**
     * @return com.aliyuncs.CommonResponse
     * @Author lvzhenwei
     * @Description aliyun短信平台接口--删除短信签名接口
     * @Date 10:51 2019/12/4
     * @Param [smsSignQueryRequest]
     **/
    public SmsResponseAliyunVO deleteSmsSign(SmsSignAliyunDeleteRequest smsSign) {
        CommonRequest request = new CommonRequest();
        request.setSysAction("deleteSmsSign");
        request.putQueryParameter("SignName", smsSign.getSignName());
        return doAction(request, smsSign.getSmsSettingId());
    }

    /**
     * @Author lvzhenwei
     * @Description aliyun短信平台接口--查询短信签名接口
     * @Date 19:31 2019/12/9
     * @Param [smsSign]
     * @return com.wanmi.sbc.message.SmsBaseResponse
     **/
    public SmsResponseAliyunVO querySmsSign(SmsSignAliyunQueryRequest smsSign){
        CommonRequest request = new CommonRequest();
        request.setSysAction("QuerySmsSign");
        request.putQueryParameter("SignName", smsSign.getSignName());
        return doAction(request, smsSign.getSmsSettingId());
    }

    /**
     * @return com.aliyuncs.CommonResponse
     * @Author lvzhenwei
     * @Description aliyun短信平台接口--新增短信模板接口
     * @Date 10:51 2019/12/4
     * @Param [smsTemplateByIdRequest]
     **/
    public SmsResponseAliyunVO addSmsTemplate(SmsTemplateAliyunAddRequest smsTemplate) {
        CommonRequest request = new CommonRequest();
        request.setSysAction("addSmsTemplate");
        request.putQueryParameter("TemplateType", String.valueOf(smsTemplate.getTemplateType()));
        request.putQueryParameter("TemplateName", smsTemplate.getTemplateName());
        request.putQueryParameter("TemplateContent", smsTemplate.getTemplateContent());
        request.putQueryParameter("Remark", smsTemplate.getRemark());
        return doAction(request, smsTemplate.getSmsSettingId());
    }

    /**
     * @return com.aliyuncs.CommonResponse
     * @Author lvzhenwei
     * @Description aliyun短信平台接口--新增短信模板接口
     * @Date 10:51 2019/12/4
     * @Param [smsTemplateByIdRequest]
     **/
    public SmsResponseAliyunVO modifySmsTemplate(SmsTemplateAliyunModifyRequest smsTemplate) {
        CommonRequest request = new CommonRequest();
        request.setSysAction("modifySmsTemplate");
        request.putQueryParameter("TemplateType", String.valueOf(smsTemplate.getTemplateType()));
        request.putQueryParameter("TemplateName", smsTemplate.getTemplateName());
        request.putQueryParameter("TemplateContent", smsTemplate.getTemplateContent());
        request.putQueryParameter("Remark", smsTemplate.getRemark());
        request.putQueryParameter("TemplateCode", smsTemplate.getTemplateCode());
        return doAction(request, smsTemplate.getSmsSettingId());
    }

    /**
     * @return com.aliyuncs.CommonResponse
     * @Author lvzhenwei
     * @Description aliyun短信平台接口--删除短信模板接口
     * @Date 10:51 2019/12/4
     * @Param [smsTemplateByIdRequest]
     **/
    public SmsResponseAliyunVO deleteSmsTemplate(SmsTemplateAliyunDeleteRequest smsTemplate) {
        CommonRequest request = new CommonRequest();
        request.setSysAction("deleteSmsTemplate");
        request.putQueryParameter("TemplateCode", smsTemplate.getTemplateCode());
        return doAction(request, smsTemplate.getSmsSettingId());
    }

    /**
     * @Author lvzhenwei
     * @Description aliyun短信平台接口--查询短信签名接口
     * @Date 19:31 2019/12/9
     * @Param [smsSign]
     * @return
     **/
    public SmsResponseAliyunVO querySmsTemplate(SmsTemplateAliyunQueryRequest smsTemplate){
        CommonRequest request = new CommonRequest();
        request.setSysAction("QuerySmsTemplate");
        request.putQueryParameter("TemplateCode", smsTemplate.getTemplateCode());
        return doAction(request, smsTemplate.getSmsSettingId());
    }

    /**
     * 返回值转换为 BasResponse
     * @author  hanwei
     * @date 2021/4/13 16:43
     * @return com.wanmi.sbc.common.base.BaseResponse<T>
     **/
    public static <T> BaseResponse<T> toBaseResponse(SmsResponseAliyunVO smsResponseAliyunVO , Class<T> clazz) {
        //阿里云请求成功但是返回错误码时，接口也返回成功响应，仅设置错误信息和原始errorData
        if(!SmsResponseCode.SUCCESS.equals(smsResponseAliyunVO.getCode())) {
            return new BaseResponse<>(CommonErrorCodeEnum.K000000.getCode(), smsResponseAliyunVO.getMessage(), smsResponseAliyunVO
                    , KsBeanUtil.copyPropertiesThird(smsResponseAliyunVO, clazz));
        }
        return BaseResponse.success(KsBeanUtil.copyPropertiesThird(smsResponseAliyunVO, clazz));
    }
}
