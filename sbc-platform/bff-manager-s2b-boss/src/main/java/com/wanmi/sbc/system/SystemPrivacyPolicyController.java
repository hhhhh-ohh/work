package com.wanmi.sbc.system;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.setting.api.provider.systemprivacypolicy.SystemPrivacyPolicyQueryProvider;
import com.wanmi.sbc.setting.api.provider.systemprivacypolicy.SystemPrivacyPolicySaveProvider;
import com.wanmi.sbc.setting.api.request.systemprivacypolicy.*;
import com.wanmi.sbc.setting.api.response.systemprivacypolicy.*;
import com.wanmi.sbc.setting.bean.enums.PrivacyType;
import com.wanmi.sbc.util.CommonUtil;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;


@Tag(name =  "隐私政策管理API", description =  "SystemPrivacyPolicyController")
@RestController
@Validated
@RequestMapping(value = "/boss/systemprivacypolicy")
public class SystemPrivacyPolicyController {

    @Autowired
    private SystemPrivacyPolicyQueryProvider systemPrivacyPolicyQueryProvider;

    @Autowired
    private SystemPrivacyPolicySaveProvider systemPrivacyPolicySaveProvider;

    @Autowired
    private CommonUtil commonUtil;


    /**
     * 查询隐私政策
     * @param type 0:APP 1:小程序
     * @return
     */
    @Operation(summary = "查询隐私政策")
    @GetMapping("/{type}")
    public BaseResponse<SystemPrivacyPolicyResponse> query(@PathVariable Integer type) {
        SystemPrivacyPolicyQueryRequest systemPrivacyPolicyQueryRequest = new SystemPrivacyPolicyQueryRequest();
        systemPrivacyPolicyQueryRequest.setPrivacyType(PrivacyType.fromValue(type));
        return systemPrivacyPolicyQueryProvider.querySystemPrivacyPolicy(systemPrivacyPolicyQueryRequest);
    }


    @Operation(summary = "编辑/新增隐私政策")
    @PostMapping
    public BaseResponse modify(@RequestBody @Valid SystemPrivacyPolicyRequest modifyReq) {
        modifyReq.setOperator(commonUtil.getOperator());
        return systemPrivacyPolicySaveProvider.modifyOrAdd(modifyReq);
    }


}
