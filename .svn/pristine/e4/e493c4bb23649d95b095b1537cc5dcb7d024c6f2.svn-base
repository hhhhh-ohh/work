package com.wanmi.sbc.system;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.setting.api.provider.companyinfo.CompanyInfoQueryProvider;
import com.wanmi.sbc.setting.api.response.companyinfo.CompanyInfoRopResponse;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;

/**
 * 公司信息服务
 * Created by CHENLI on 2017/5/12.
 */
@Tag(name = "CompanyInfoController", description = "公司信息服务Api")
@RestController
@Validated
public class CompanyInfoController {
    @Autowired
    private CompanyInfoQueryProvider companyInfoQueryProvider;

    /**
     * 查询公司信息
     * @return
     */
    @Operation(summary = "查询公司信息")
    @RequestMapping(value = "/companyInfo", method = RequestMethod.GET)
    public BaseResponse<CompanyInfoRopResponse> findCompanyInfo() {
        return companyInfoQueryProvider.findCompanyInfos();
//        CompositeResponse<CompanyInfoRopResponse> ropResponse = sdkClient.buildClientRequest()
//                .get(CompanyInfoRopResponse.class, "companyInfo.find", "1.0.0");
//        return BaseResponse.success( ropResponse.getSuccessResponse());
    }

}
