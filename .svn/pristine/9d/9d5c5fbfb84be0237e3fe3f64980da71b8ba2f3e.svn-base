package com.wanmi.sbc.credit;

import com.wanmi.sbc.account.api.provider.credit.CreditOverviewProvider;
import com.wanmi.sbc.account.api.response.credit.CreditOverviewResponse;
import com.wanmi.sbc.common.base.BaseResponse;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;

import jakarta.annotation.Resource;

/***
 * 授信总览
 * @author zhengyang
 * @since 2021/3/15 10:48
 */
@RestController
@Validated
@Tag(name = "CreditOverviewController", description = "S2B 授信总览API")
@RequestMapping("/credit/overview")
public class CreditOverviewController {

    @Resource
    private CreditOverviewProvider creditOverviewProvider;

    /**
     * 查询授信总览
     * @return {@link CreditOverviewResponse}
     */
    @PostMapping("/findCreditOverview")
    public BaseResponse<CreditOverviewResponse> findCreditOverview() {
        return creditOverviewProvider.findCreditOverview();
    }
}
