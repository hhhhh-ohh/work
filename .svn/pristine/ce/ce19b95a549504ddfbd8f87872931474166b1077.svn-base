package com.wanmi.sbc.account.api.provider.credit;

import com.wanmi.sbc.account.api.request.credit.*;
import com.wanmi.sbc.account.api.response.credit.CreditRepayDetailResponse;
import com.wanmi.sbc.account.api.response.credit.CreditRepayPageResponse;
import com.wanmi.sbc.account.api.response.credit.account.CreditAccountCheckResponse;
import com.wanmi.sbc.account.api.response.credit.account.CreditAccountForRepayResponse;
import com.wanmi.sbc.account.api.response.credit.repay.CreditRepayOverviewPageResponse;
import com.wanmi.sbc.account.api.response.credit.repay.CustomerCreditRepayAndOrdersByRepayCodeResponse;
import com.wanmi.sbc.account.api.response.credit.repay.CustomerCreditRepayByRepayCodeResponse;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * @author houshuai
 * @date 2021/3/2 17:03
 * @description <p> 授信还款查询api </p>
 */
@FeignClient(value = "${application.account.name}", contextId = "CreditRepayQueryProvider")
public interface CreditRepayQueryProvider {

    /***
     * 在线还款校验当前账号授信期额度是否全部还款完成
     * @param request {@link CreditAccountForRepayRequest}
     * @return
     */
    @PostMapping("/account/${application.account.version}/check-credit-account-for-repay")
    BaseResponse<CreditAccountCheckResponse> checkCreditAccountHasRepaid(@RequestBody CreditAccountForRepayRequest request);

    /***
     * 在线还款查询授信账户信息和还款信息
     * @param request {@link CreditAccountForRepayRequest}
     * @return {@link CreditAccountForRepayResponse}
     */
    @PostMapping("/account/${application.account.version}/get-credit-account-for-repay")
    BaseResponse<CreditAccountForRepayResponse> getCreditAccountByCustomerIdForRepay(@RequestBody CreditAccountForRepayRequest request);

    /**
     * 分页查询还款订单列表
     *
     * @param request
     * @return
     *
     * @param request {@link CreditRepayPageRequest}
     * @return {@link CreditRepayPageResponse}
     */
    @PostMapping("/account/${application.account.version}/find-credit-repay-page")
    BaseResponse<MicroServicePage<CreditRepayPageResponse>> findCreditHasRepaidPage(@RequestBody @Valid CreditRepayPageRequest request);

    /***
     * 根据订单查询还款单号和还款状态
     * @param request
     * @return
     */
    @PostMapping("/account/${application.account.version}/find-credit-repay-byorderid")
    BaseResponse<CreditRepayPageResponse> findRepayOrderByOrderId(@RequestBody @Validated CreditOrderQueryRequest request);

    /**
     * 查询订单详情列表
     *
     * @param request {@link CreditRepayDetailRequest}
     * @return {@link CreditRepayDetailResponse}
     */
    @PostMapping("/account/${application.account.version}/get-credit-repay")
    BaseResponse<MicroServicePage<CreditRepayDetailResponse>> getCreditRepay(@RequestBody @Valid CreditRepayDetailRequest request);

    /***
     * 分页查询授信还款概览列表
     * @param request
     * @return
     */
    @PostMapping("/account/${application.account.version}/find-repay-order-page")
    BaseResponse<MicroServicePage<CreditRepayOverviewPageResponse>> findRepayOrderPage(@RequestBody @Validated CreditRepayOverviewPageRequest request);

    /**
     * 根据授信还款单号查询
     *
     */
    @PostMapping("/account/${application.account.version}/credit/repay/get-by-repay-code")
    BaseResponse<CustomerCreditRepayByRepayCodeResponse> getCreditRepayByRepayCode(@RequestBody @Valid CustomerCreditRepayByRepayCodeRequest repayByRepayCodeRequest);


    /**
     * 还款详情
     * @param request
     * @return
     */
    @PostMapping("/account/${application.account.version}/get-credit-has-repaid-detail")
    BaseResponse<CreditRepayPageResponse> getCreditHasRepaidDetail(@RequestBody @Validated CreditHasRepaidDetailRequest request);

    /**
     * 根据还款单号 查询还款详情-包含关联订单信息
     *
     * @param customerCreditRepayByIdRequest
     * @return
     */
    @PostMapping("/account/${application.account.version}/customercreditrepay/getCreditRepayAndOrdersByRepayCode")
    BaseResponse<CustomerCreditRepayAndOrdersByRepayCodeResponse> getCreditRepayAndOrdersByRepayCode(@RequestBody @Valid CustomerCreditRepayByRepayCodeRequest customerCreditRepayByIdRequest);

    /***
     * 根据订单查询还款完成的记录
     * @param request
     * @return
     */
    @PostMapping("/account/${application.account.version}/find-finish-credit-repay-byorderid")
    BaseResponse<CreditRepayPageResponse> findFinishRepayOrderByOrderId(@RequestBody @Validated CreditOrderQueryRequest request);

}
