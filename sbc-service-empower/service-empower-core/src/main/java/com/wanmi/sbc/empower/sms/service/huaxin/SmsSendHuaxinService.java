package com.wanmi.sbc.empower.sms.service.huaxin;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.empower.api.request.sms.SmsSendRequest;
import com.wanmi.sbc.empower.api.request.sms.SmsSettingQueryRequest;
import com.wanmi.sbc.empower.bean.enums.SmsPlatformType;
import com.wanmi.sbc.empower.sms.model.root.SmsSetting;
import com.wanmi.sbc.empower.sms.service.SmsSendBaseService;
import com.wanmi.sbc.empower.sms.service.SmsSettingService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Component(SmsPlatformType.SMS_SEND_SERVICE_HUAXIN)
public class SmsSendHuaxinService implements SmsSendBaseService {

    @Autowired
    private SmsSettingService smsSettingService;

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @Override
    public BaseResponse send(SmsSendRequest smsSendRequest) {

        SmsSetting smsSetting =
                smsSettingService.getSmsSettingInfoByParam(SmsSettingQueryRequest.builder().type(SmsPlatformType.HUAXIN).build());

        long begin = System.currentTimeMillis();
        // 封装参数
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("action", "send");
        paramMap.put("account", smsSetting.getAccessKeyId());
        paramMap.put("password", smsSetting.getAccessKeySecret());
        paramMap.put("mobile", smsSendRequest.getPhoneNumbers());
        String templateContent = smsSendRequest.getTemplateContent();
        if(Objects.nonNull(smsSendRequest.getTemplateParamMap())) {
            Map<String, String> param = smsSendRequest.getTemplateParamMap();
            for(Map.Entry<String, String> entry : param.entrySet()){
                templateContent = templateContent.replaceAll("\\$\\{"+entry.getKey()+"\\}",
                        Objects.toString(entry.getValue(), StringUtils.EMPTY));
            }
        }
        String[] template = smsSetting.getHuaxinTemplate().split("\\|");
        String smsSuffix = "";
        if (template.length == Constants.TWO) {
            smsSuffix = template[1];
        }

        String smsContent = templateContent.concat(smsSuffix);
        paramMap.put("content", smsContent);
        paramMap.put("sendTime", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        String result = null;
        try {
            result = restTemplateBuilder.build().getForObject(smsSetting.getHuaxinApiUrl()
                            + "?action={action}&userid=&account={account}&password={password}&mobile={mobile}&content={content}&sendTime={sendTime}",
                    String.class, paramMap);
            return pasreResult(result);
        } catch (Exception ex) {
            log.warn("发送短信失败, paramMap:{}, exMsg:{}; costTime:{}", paramMap, ex.getMessage(), (System.currentTimeMillis() - begin));
        } finally {
            log.info("发送短信结束; paramMap:{}; result:{}; costTime:{}", paramMap, result, (System.currentTimeMillis() - begin));
        }
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 解析xml字符串
     */
    private BaseResponse pasreResult(String xml) {
        Document doc;
        try {
            // 将字符转化为XML
            doc = DocumentHelper.parseText(xml);
            // 获取根节点
            Element rootElt = doc.getRootElement();
            // 获取根节点下的子节点的值
            String returnstatus = rootElt.elementText("returnstatus").trim();
            String message = rootElt.elementText("message").trim();
            if(Constants.SUCCESS.equals(returnstatus)){
                return BaseResponse.SUCCESSFUL();
            }
            log.error("短信发送失败，失败原因：{}", message);
            return BaseResponse.error(message);
        } catch (DocumentException e) {
            log.error("解析xml字符串异常", e);
            return BaseResponse.error(e.getMessage());
        }
    }
}
