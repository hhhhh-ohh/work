package com.wanmi.sbc.setting.api.provider.systemconfig;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.setting.api.request.*;
import com.wanmi.sbc.setting.api.request.systemconfig.PayingMemberModifyRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;
import java.util.List;

/**
 * Created by feitingting on 2019/11/6.
 */
@FeignClient(value = "${application.setting.name}", contextId = "SystemConfigSaveProvider")
public interface SystemConfigSaveProvider {

    @PostMapping("/setting/${application.setting.version}/sysconfig/modify")
    BaseResponse modify(@RequestBody @Valid ConfigContextModifyByTypeAndKeyRequest request);

    @PostMapping("/setting/${application.setting.version}/sysconfig/update")
    BaseResponse update(@RequestBody @Valid ConfigUpdateRequest request);

    @PostMapping("/setting/${application.setting.version}/sysconfigs/update")
    BaseResponse update(@RequestBody @Valid List<ConfigModifyRequest> configModifyRequests);

    /**
     * 修改线下支付设置
     *
     * @param request
     * @return
     */
    @PostMapping("/setting/${application.setting.version}/sysconfig/modify-offline-pay-setting")
    BaseResponse modifyOfflinePaySetting(@RequestBody @Valid ConfigStatusModifyByTypeAndKeyRequest request);

    @PostMapping("/setting/${application.setting.version}/sysconfig/modifyEsGoodsBoost")
    BaseResponse modifyEsGoodsBoost(@RequestBody @Valid ModifyEsGoodsBoostRequest request);

    @PostMapping("/setting/${application.setting.version}/sysconfig/modify-paying-member-setting")
    BaseResponse modifyPayingMemberSetting(@RequestBody @Valid PayingMemberModifyRequest request);

    @PostMapping("/setting/${application.setting.version}/sysconfig/modify-seo-setting")
    BaseResponse modifySeoSetting(@RequestBody @Valid SeoSettingModifyRequest request);
}
