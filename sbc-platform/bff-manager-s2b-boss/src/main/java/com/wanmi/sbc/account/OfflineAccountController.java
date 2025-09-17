package com.wanmi.sbc.account;

import com.wanmi.sbc.account.api.provider.company.CompanyAccountQueryProvider;
import com.wanmi.sbc.account.api.request.company.CompanyAccountByCompanyInfoIdAndDefaultFlagRequest;
import com.wanmi.sbc.account.bean.vo.CompanyAccountVO;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.sensitiveword.annotation.ReturnSensitiveWords;
import com.wanmi.sbc.job.service.DistributionTempJobService;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商家结算银行账户
 * Created by sunkun on 2017/11/2.
 */
@RestController("bossOfflineAccountController")
@Validated
@RequestMapping("/account")
@Tag(name = "OfflineAccountController", description = "S2B 平台端-商家结算银行账户API")
public class OfflineAccountController {

    @Autowired
    private CompanyAccountQueryProvider companyAccountQueryProvider;


    /**
     * 获取商家结算银行账户
     *
     * @return
     */
    @Operation(summary = "S2B 平台端-获取商家结算银行账户")
    @Parameter(name = "companyInfoId", description = "商家的公司id", required = true)
    @GetMapping(value = "/list/{companyInfoId}")
    @ReturnSensitiveWords(functionName = "f_boss_company_account_list_sign_word")
    public BaseResponse<List<CompanyAccountVO>> list(@PathVariable Long companyInfoId) {
        return BaseResponse.success(companyAccountQueryProvider.listByCompanyInfoIdAndDefaultFlag(
                CompanyAccountByCompanyInfoIdAndDefaultFlagRequest.builder()
                        .companyInfoId(companyInfoId).defaultFlag(DefaultFlag.NO).build()
        ).getContext().getCompanyAccountVOList());
    }
}
