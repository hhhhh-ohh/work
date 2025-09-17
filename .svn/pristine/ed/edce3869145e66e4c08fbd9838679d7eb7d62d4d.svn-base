package com.wanmi.sbc.setting.provider.impl.expresscompanythirdrel;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.setting.api.provider.expresscompanythirdrel.ExpressCompanyThirdRelProvider;
import com.wanmi.sbc.setting.api.request.expresscompanythirdrel.ExpressCompanyThirdRelBatchSaveRequest;
import com.wanmi.sbc.setting.api.request.expresscompanythirdrel.ExpressCompanyThirdRelMappingRequest;
import com.wanmi.sbc.setting.expresscompanythirdrel.service.ExpressCompanyThirdRelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * @description 第三方平台物流公司保存服务
 * @author malianfeng
 * @date 2022/4/26 17:44
 */
@RestController
@Validated
public class ExpressCompanyThirdRelController implements ExpressCompanyThirdRelProvider {

    @Autowired private ExpressCompanyThirdRelService expressCompanyThirdRelService;

    @Override
    public BaseResponse batchSave(@Valid @RequestBody ExpressCompanyThirdRelBatchSaveRequest request) {
        expressCompanyThirdRelService.batchSave(request);
        return BaseResponse.SUCCESSFUL();
    }

    public BaseResponse mapping(@Valid @RequestBody ExpressCompanyThirdRelMappingRequest request) {
        expressCompanyThirdRelService.mapping(request.getSellPlatformType());
        return BaseResponse.SUCCESSFUL();
    }
}
