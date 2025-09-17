package com.wanmi.sbc.account.provider.impl.credit;

import com.wanmi.sbc.account.api.provider.credit.CreditAccountQueryProvider;
import com.wanmi.sbc.account.api.request.credit.CreditAccountDetailRequest;
import com.wanmi.sbc.account.api.request.credit.CreditAccountPageRequest;
import com.wanmi.sbc.account.api.request.credit.CreditRecoverDetailRequest;
import com.wanmi.sbc.account.api.request.credit.CreditRecoverPageRequest;
import com.wanmi.sbc.account.api.response.credit.CreditRecoverPageResponse;
import com.wanmi.sbc.account.api.response.credit.account.CreditAccountDetailResponse;
import com.wanmi.sbc.account.api.response.credit.account.CreditAccountPageResponse;
import com.wanmi.sbc.account.api.response.credit.account.CreditAccountStatisticsResponse;
import com.wanmi.sbc.account.credit.service.CreditAccountQueryService;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * @author houshuai
 * @date 2021/2/27 14:34
 * @description
 *     <p>授信账户controller
 */
@RestController
public class CreditAccountQueryController implements CreditAccountQueryProvider {

    @Autowired private CreditAccountQueryService creditAccountQueryService;

    /**
     * @param request {@link CreditAccountPageRequest}
     * @return
     */
    @Override
    public BaseResponse<MicroServicePage<CreditAccountPageResponse>> findCreditAccountPage(
            @RequestBody CreditAccountPageRequest request) {
        MicroServicePage<CreditAccountPageResponse> response =
                creditAccountQueryService.findCreditAccountPage(request);
        return BaseResponse.success(response);
    }

    /**
     * 分页查询授信账户
     * @param request {@link CreditAccountPageRequest}
     * @return
     */
    @Override
    public BaseResponse<MicroServicePage<CreditAccountPageResponse>>
                                findCreditAccountForPage(@RequestBody CreditAccountPageRequest request) {
        return BaseResponse.success(creditAccountQueryService.findCreditAccountForPage(request));
    }

    /**
     * @param request {@link CreditAccountDetailRequest}
     * @return
     */
    @Override
    public BaseResponse<CreditAccountDetailResponse> getCreditAccountDetail(
            @RequestBody @Valid CreditAccountDetailRequest request) {
        String id = request.getCustomerId();
        CreditAccountDetailResponse response = creditAccountQueryService.getCreditAccountDetail(id);
        return BaseResponse.success(response);
    }

    /**
     * * 根据登录用户查询授信账户
     *
     * @param request
     * @return
     */
    @Override
    public BaseResponse<CreditAccountDetailResponse> getCreditAccountByCustomerId(
            @RequestBody CreditAccountDetailRequest request) {
        return BaseResponse.success(
                creditAccountQueryService.getCreditAccountByCustomerId(request.getCustomerId()));
    }

    /**
     * 分页查询额度恢复记录
     *
     * @param request {@link CreditAccountDetailRequest}
     * @return
     */
    @Override
    public BaseResponse<MicroServicePage<CreditRecoverPageResponse>> findCreditRecoverPage(
            @RequestBody @Valid CreditRecoverPageRequest request) {
        MicroServicePage<CreditRecoverPageResponse> response =
                creditAccountQueryService.findCreditRecoverPage(request);
        return BaseResponse.success(response);
    }

    /**
     * 查询额度恢复详情
     *
     * @param request {@link CreditRecoverDetailRequest}
     * @return
     */
    @Override
    public BaseResponse<CreditRecoverPageResponse> getCreditRecoverDetail(
            @RequestBody @Valid CreditRecoverDetailRequest request) {
        CreditRecoverPageResponse response =
                creditAccountQueryService.getCreditRecoverDetail(request.getId());
        return BaseResponse.success(response);
    }

    @Override
    public BaseResponse<CreditAccountStatisticsResponse> findCustomerCreditAccountStatistics() {
        return BaseResponse.success(
                creditAccountQueryService.findCustomerCreditAccountStatistics());
    }
}
