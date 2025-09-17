package com.wanmi.sbc.system;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.setting.api.provider.systemprivacypolicy.SystemPrivacyPolicyQueryProvider;
import com.wanmi.sbc.setting.api.provider.systemprivacypolicy.SystemPrivacyPolicySaveProvider;
import com.wanmi.sbc.setting.api.request.systemprivacypolicy.SystemPrivacyPolicyQueryRequest;
import com.wanmi.sbc.setting.api.request.systemprivacypolicy.SystemPrivacyPolicyRequest;
import com.wanmi.sbc.setting.api.response.systemprivacypolicy.SystemPrivacyPolicyResponse;
import com.wanmi.sbc.setting.bean.enums.PrivacyType;
import com.wanmi.sbc.util.CommonUtil;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;


@Tag(name =  "隐私政策管理API", description =  "SystemPrivacyPolicyController")
@RestController
@Validated
@RequestMapping(value = "/privacypolicy/queryPrivacyPolicy")
public class SystemPrivacyPolicyController {

    @Autowired
    private SystemPrivacyPolicyQueryProvider systemPrivacyPolicyQueryProvider;


    @Operation(summary = "查询隐私政策")
    @GetMapping("/{type}")
    public BaseResponse<SystemPrivacyPolicyResponse> query(@PathVariable Integer type) {
        SystemPrivacyPolicyQueryRequest systemPrivacyPolicyQueryRequest = new SystemPrivacyPolicyQueryRequest();
        systemPrivacyPolicyQueryRequest.setPrivacyType(PrivacyType.fromValue(type));
        return systemPrivacyPolicyQueryProvider.querySystemPrivacyPolicy(systemPrivacyPolicyQueryRequest);
    }



}
