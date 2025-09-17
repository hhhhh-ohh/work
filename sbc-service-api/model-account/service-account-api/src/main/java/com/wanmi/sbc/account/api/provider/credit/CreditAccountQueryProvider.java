package com.wanmi.sbc.account.api.provider.credit;

import com.wanmi.sbc.account.api.request.credit.CreditAccountDetailRequest;
import com.wanmi.sbc.account.api.request.credit.CreditAccountPageRequest;
import com.wanmi.sbc.account.api.request.credit.CreditRecoverDetailRequest;
import com.wanmi.sbc.account.api.request.credit.CreditRecoverPageRequest;
import com.wanmi.sbc.account.api.response.credit.CreditRecoverPageResponse;
import com.wanmi.sbc.account.api.response.credit.account.CreditAccountDetailResponse;
import com.wanmi.sbc.account.api.response.credit.account.CreditAccountPageResponse;
import com.wanmi.sbc.account.api.response.credit.account.CreditAccountStatisticsResponse;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * @author houshuai
 * @date 2021/2/27 11:21
 * @description <p> 授信账户查询api </p>
 */
@FeignClient(value = "${application.account.name}", contextId = "CreditAccountQueryProvider")
public interface CreditAccountQueryProvider {

    /**
     * 授信账户分页查询
     *
     * @param request {@link CreditAccountPageRequest}
     * @return {@link CreditAccountPageResponse}
     */
    @PostMapping("/account/${application.account.version}/find-credit-account-page")
    BaseResponse<MicroServicePage<CreditAccountPageResponse>> findCreditAccountPage(@RequestBody CreditAccountPageRequest request);


    /**
     * 分页查询授信账户
     * @param request {@link CreditAccountPageRequest}
     * @return
     */
    @PostMapping("/account/${application.account.version}/find-credit-account-for-page")
    BaseResponse<MicroServicePage<CreditAccountPageResponse>> findCreditAccountForPage(@RequestBody CreditAccountPageRequest request);

    /**
     * 授信账户查看详情
     *
     * @param request {@link CreditAccountDetailRequest}
     * @return {@link CreditAccountDetailResponse}
     */
    @PostMapping("/account/${application.account.version}/get-credit-account-detail")
    BaseResponse<CreditAccountDetailResponse> getCreditAccountDetail(@RequestBody @Valid CreditAccountDetailRequest request);

    /***
     * 根据登录用户查询授信账户
     * @param request {@link CreditAccountDetailRequest}
     * @return {@link CreditAccountDetailResponse}
     */
    @PostMapping("/account/${application.account.version}/get-credit-account-by-login")
    BaseResponse<CreditAccountDetailResponse> getCreditAccountByCustomerId(@RequestBody CreditAccountDetailRequest request);


    /***
     * 分页查询额度恢复记录
     * @param request {@link CreditRecoverPageRequest}
     * @return {@link CreditRecoverPageResponse}
     */
    @PostMapping("/account/${application.account.version}/find-credit-recover-page")
    BaseResponse<MicroServicePage<CreditRecoverPageResponse>> findCreditRecoverPage(@RequestBody @Valid CreditRecoverPageRequest request);


    /***
     * 查询额度恢复记录
     * @param request {@link CreditRecoverDetailRequest}
     * @return {@link CreditRecoverPageResponse}
     */
    @PostMapping("/account/${application.account.version}/get-credit-recover-detail")
    BaseResponse<CreditRecoverPageResponse> getCreditRecoverDetail(@RequestBody @Valid CreditRecoverDetailRequest request);

    /**
     * @description 查询所有账户的授信统计数据
     * @author  chenli
     * @date 2021/4/22 14:52
     * @return
     **/
    @PostMapping("/account/${application.account.version}/get-credit-account-statistics")
    BaseResponse<CreditAccountStatisticsResponse> findCustomerCreditAccountStatistics();
}
