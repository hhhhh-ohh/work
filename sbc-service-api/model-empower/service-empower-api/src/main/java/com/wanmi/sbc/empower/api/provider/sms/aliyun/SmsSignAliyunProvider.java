package com.wanmi.sbc.empower.api.provider.sms.aliyun;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.empower.api.request.sms.aliyun.SmsSignAliyunAddRequest;
import com.wanmi.sbc.empower.api.request.sms.aliyun.SmsSignAliyunDeleteRequest;
import com.wanmi.sbc.empower.api.request.sms.aliyun.SmsSignAliyunModifyRequest;
import com.wanmi.sbc.empower.api.request.sms.aliyun.SmsSignAliyunQueryRequest;
import com.wanmi.sbc.empower.api.response.sms.aliyun.SmsSignAliyunAddResponse;
import com.wanmi.sbc.empower.api.response.sms.aliyun.SmsSignAliyunDeleteResponse;
import com.wanmi.sbc.empower.api.response.sms.aliyun.SmsSignAliyunModifyResponse;
import com.wanmi.sbc.empower.api.response.sms.aliyun.SmsSignAliyunQueryResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>阿里云短信签名保存服务Provider</p>
 *
 * @author hanwei
 * @date 2021-03-31
 */
@FeignClient(value = "${application.empower.name}",contextId = "SmsSignAliyunProvider")
public interface SmsSignAliyunProvider {

    /**
     * 申请短信签名
     * @param request
     * @return
     */
    @PostMapping("/sms/${application.empower.version}/smssignaliyun/add")
    BaseResponse<SmsSignAliyunAddResponse> add(@RequestBody @Valid SmsSignAliyunAddRequest request);

    /**
     * 修改未审核通过的短信签名证明文件，并重新提交审核
     * @param request
     * @return
     */
    @PostMapping("/sms/${application.empower.version}/smssignaliyun/modify")
    BaseResponse<SmsSignAliyunModifyResponse> modify(@RequestBody @Valid SmsSignAliyunModifyRequest request);

    /**
     * 删除短信签名
     * 不支持删除正在审核中的签名。
     * 短信签名删除后不可恢复，请谨慎操作。
     * @param request
     * @return
     */
    @PostMapping("/sms/${application.empower.version}/smssignaliyun/delete")
    BaseResponse<SmsSignAliyunDeleteResponse> delete(@RequestBody @Valid SmsSignAliyunDeleteRequest request);

    /**
     * 查询短信签名申请状态
     * @param request
     * @return
     */
    @PostMapping("/sms/${application.empower.version}/smssignaliyun/query")
    BaseResponse<SmsSignAliyunQueryResponse> query(@RequestBody @Valid SmsSignAliyunQueryRequest request);

}

