package com.wanmi.sbc.setting.api.provider.systemconfig;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.setting.api.request.ConfigQueryRequest;
import com.wanmi.sbc.setting.api.request.systemconfig.SystemConfigQueryRequest;
import com.wanmi.sbc.setting.api.response.systemconfig.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * Created by feitingting on 2019/11/6.
 */
@FeignClient(value = "${application.setting.name}", contextId = "SystemConfigQueryProvider")
public interface SystemConfigQueryProvider {

    @PostMapping("/setting/${application.setting.version}/sysconfig/list")
    BaseResponse<SystemConfigResponse> findByConfigKeyAndDelFlag(@RequestBody @Valid ConfigQueryRequest request);

    @PostMapping("/setting/${application.setting.version}/sysconfig/list-new")
    BaseResponse<SystemConfigResponse> findByConfigKeyAndDelFlagNew(@RequestBody @Valid ConfigQueryRequest request);

    @PostMapping("/setting/${application.setting.version}/sysconfig/find-by-type")
    BaseResponse<SystemConfigTypeResponse> findByConfigTypeAndDelFlag(@RequestBody @Valid ConfigQueryRequest request);

    @PostMapping("/setting/${application.setting.version}/sysconfig/find-list")
    BaseResponse<SystemConfigResponse> list(@RequestBody @Valid SystemConfigQueryRequest request);

    /**
     * 获取线下支付设置
     *
     * @return
     */
    @PostMapping("/setting/${application.setting.version}/sysconfig/get-offline-pay-setting")
    BaseResponse<OfflinePaySettingResponse> getOfflinePaySetting();

    /**
     * 获取线下支付设置
     *
     * @return
     */
    @PostMapping("/setting/${application.setting.version}/sysconfig/findContextByConfigType")
    BaseResponse<SystemConfigContextReponse> findContextByConfigType();

    /**
     * 通过configKey和configType查询context
     */
    @PostMapping("/setting/${application.setting.version}/sysconfig/findContextByConfigTypeAndConfigKey")
    BaseResponse<SystemConfigContextReponse> findContextByConfigTypeAndConfigKey() ;

}
