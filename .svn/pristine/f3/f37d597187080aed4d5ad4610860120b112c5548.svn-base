package com.wanmi.sbc.empower.api.provider.sms.aliyun;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.empower.api.request.sms.aliyun.SmsTemplateAliyunAddRequest;
import com.wanmi.sbc.empower.api.request.sms.aliyun.SmsTemplateAliyunDeleteRequest;
import com.wanmi.sbc.empower.api.request.sms.aliyun.SmsTemplateAliyunModifyRequest;
import com.wanmi.sbc.empower.api.request.sms.aliyun.SmsTemplateAliyunQueryRequest;
import com.wanmi.sbc.empower.api.response.sms.aliyun.SmsTemplateAliyunAddResponse;
import com.wanmi.sbc.empower.api.response.sms.aliyun.SmsTemplateAliyunDeleteResponse;
import com.wanmi.sbc.empower.api.response.sms.aliyun.SmsTemplateAliyunModifyResponse;
import com.wanmi.sbc.empower.api.response.sms.aliyun.SmsTemplateAliyunQueryResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>阿里云短信模板保存服务Provider</p>
 *
 * @author hanwei
 * @date 2021-03-31
 */
@FeignClient(value = "${application.empower.name}",contextId = "SmsTemplateAliyunProvider")
public interface SmsTemplateAliyunProvider {

    /**
     * 申请短信模板
     * @param request
     * @return
     */
    @PostMapping("/sms/${application.empower.version}/smstemplatealiyun/add")
    BaseResponse<SmsTemplateAliyunAddResponse> add(@RequestBody @Valid SmsTemplateAliyunAddRequest request);

    /**
     * 修改未通过审核的短信模板
     * 申请短信模板后，如果模板未通过审核，可以通过接口ModifySmsTemplate修改短信模板，并重新申请，提交审核。
     * @param request
     * @return
     */
    @PostMapping("/sms/${application.empower.version}/smstemplatealiyun/modify")
    BaseResponse<SmsTemplateAliyunModifyResponse> modify(@RequestBody @Valid SmsTemplateAliyunModifyRequest request);

    /**
     * 删除短信模板
     *      * 不支持删除正在审核中的模板。
     *      * 删除短信模板后不可恢复，请谨慎操作。
     * @param request
     * @return
     */
    @PostMapping("/sms/${application.empower.version}/smstemplatealiyun/delete")
    BaseResponse<SmsTemplateAliyunDeleteResponse> delete(@RequestBody @Valid SmsTemplateAliyunDeleteRequest request);

    /**
     * 查询短信模板的审核状态
     * @param request
     * @return
     */
    @PostMapping("/sms/${application.empower.version}/smstemplatealiyun/query")
    BaseResponse<SmsTemplateAliyunQueryResponse> query(@RequestBody @Valid SmsTemplateAliyunQueryRequest request);
}

