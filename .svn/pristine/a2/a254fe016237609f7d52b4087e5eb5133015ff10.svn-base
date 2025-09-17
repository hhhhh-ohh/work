package com.wanmi.sbc.system;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.setting.api.provider.companyinfo.CompanyInfoQueryProvider;
import com.wanmi.sbc.setting.api.provider.companyinfo.CompanyInfoSaveProvider;
import com.wanmi.sbc.setting.api.request.companyinfo.CompanyInfoModifyRequest;
import com.wanmi.sbc.setting.api.response.companyinfo.CompanyInfoRopResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.Objects;

/**
 * 公司信息服务
 * Created by CHENLI on 2017/5/12.
 */
@RestController
@Validated
@Tag(name = "CompanyInfoController", description = "S2B 管理端公用-公司信息管理API")
public class CompanyInfoController {

    @Autowired
    private CompanyInfoQueryProvider companyInfoQueryProvider;

    @Autowired
    private CompanyInfoSaveProvider companyInfoSaveProvider;

    /**
     * 查询公司信息
     *
     * @return
     */
    @Operation(summary = "查询公司信息")
    @RequestMapping(value = "/companyInfo", method = RequestMethod.GET)
    public BaseResponse<CompanyInfoRopResponse> findCompanyInfo() {
        return companyInfoQueryProvider.findCompanyInfos();
    }

    /**
     * 查询公司信息，此接口与上面接口的区别在于，不需要鉴权
     *
     * @return
     */
    @Operation(summary = "查询公司信息，此接口与上面接口的区别在于，不需要鉴权")
    @RequestMapping(value = "/getCompanyInfo", method = RequestMethod.GET)
    public BaseResponse<CompanyInfoRopResponse> getCompanyInfo() {
        return companyInfoQueryProvider.findCompanyInfos();
    }

    /**
     * 修改公司信息
     *
     * @param companyInfoModifyRequest
     * @return
     */
    @Operation(summary = "修改公司信息")
    @RequestMapping(value = "/companyInfo", method = RequestMethod.PUT)
    public BaseResponse updateCompanyInfo(@RequestBody @Valid CompanyInfoModifyRequest companyInfoModifyRequest) {

        if (Objects.isNull(companyInfoModifyRequest.getCompanyInfoId())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        companyInfoSaveProvider.modify(companyInfoModifyRequest);
        return BaseResponse.SUCCESSFUL();

    }
}
