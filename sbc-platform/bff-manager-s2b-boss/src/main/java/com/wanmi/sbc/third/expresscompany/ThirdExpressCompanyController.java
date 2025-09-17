package com.wanmi.sbc.third.expresscompany;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.setting.api.provider.expresscompanythirdrel.ExpressCompanyThirdRelProvider;
import com.wanmi.sbc.setting.api.provider.thirdexpresscompany.ThirdExpressCompanyQueryProvider;
import com.wanmi.sbc.setting.api.request.expresscompanythirdrel.ExpressCompanyThirdRelBatchSaveRequest;
import com.wanmi.sbc.setting.api.request.thirdexpresscompany.ThirdExpressCompanyQueryRequest;
import com.wanmi.sbc.setting.api.response.thirdexpresscompany.ThirdExpressCompanyListResponse;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;

@Tag(name =  "第三方平台物流公司管理API", description =  "ThirdExpressCompanyController")
@RestController
@Validated
@RequestMapping(value = "/third/expresscompany")
public class ThirdExpressCompanyController {

    @Autowired private ThirdExpressCompanyQueryProvider thirdExpressCompanyQueryProvider;

    @Autowired private ExpressCompanyThirdRelProvider expressCompanyThirdRelProvider;

    @Operation(summary = "查询第三方平台物流公司列表")
    @PostMapping("/list")
    public BaseResponse<ThirdExpressCompanyListResponse> list(@RequestBody ThirdExpressCompanyQueryRequest request) {
        request.setDelFlag(DeleteFlag.NO);
        return thirdExpressCompanyQueryProvider.list(request);
    }

    @Operation(summary = "批量保存平台与第三方物流平台的映射关系")
    @PostMapping("/batch-save")
    public BaseResponse batchSave(@Valid @RequestBody ExpressCompanyThirdRelBatchSaveRequest request) {
        return expressCompanyThirdRelProvider.batchSave(request);
    }

}
