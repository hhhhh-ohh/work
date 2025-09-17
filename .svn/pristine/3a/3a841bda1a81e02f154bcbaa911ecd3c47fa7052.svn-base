package com.wanmi.sbc.account.api.provider.credit;

import com.wanmi.sbc.account.api.request.credit.CreditAccountPageRequest;
import com.wanmi.sbc.account.api.request.credit.CreditAmountRestoreRequest;
import com.wanmi.sbc.common.base.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


/***
 * 用户授信账号管理
 * @author zhengyang
 * @since 2021-03-03
 */
@FeignClient(value = "${application.account.name}", contextId = "CreditAccountProvider")
public interface CreditAccountProvider {

    /**
     * 用户授信账户额度还原
     *
     * @param request {@link CreditAmountRestoreRequest}
     * @return {@link BaseResponse}
     */
    @PostMapping("/account/${application.account.version}/restore_credit_amount")
    BaseResponse restoreCreditAmount(@RequestBody @Validated CreditAmountRestoreRequest request);

    /**
     * 用户授信额度变更（授信支付）
     * @param request
     * @return
     */
    @PostMapping("/account/${application.account.version}/update/credit/amount")
    BaseResponse updateCreditAmount(@RequestBody @Validated CreditAmountRestoreRequest request);

    /**
     * 额度恢复
     * @param request
     * @return
     */
    @PostMapping("/account/${application.account.version}/recover/credit/amount")
    BaseResponse recoverCreditAmount(@RequestBody CreditAccountPageRequest request);
}
