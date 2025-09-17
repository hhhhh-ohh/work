package com.wanmi.sbc.account.provider.impl.credit;

import com.wanmi.sbc.account.api.provider.credit.CreditAccountProvider;
import com.wanmi.sbc.account.api.request.credit.CreditAccountPageRequest;
import com.wanmi.sbc.account.api.request.credit.CreditAmountRestoreRequest;
import com.wanmi.sbc.account.credit.service.CreditAccountService;
import com.wanmi.sbc.common.base.BaseResponse;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;

/**
 * @author houshuai
 * @date 2021/2/27 14:34
 * @description <p> 授信账户controller</p>
 */
@RestController
public class CreditAccountController implements CreditAccountProvider {

    @Resource
    private CreditAccountService creditAccountService;

    /**
     * 用户授信账户额度还原
     *
     * @param request {@link CreditAccountPageRequest}
     * @return
     */
    @Override
    public BaseResponse restoreCreditAmount(@RequestBody CreditAmountRestoreRequest request) {
        creditAccountService.restoreCreditAmount(request);
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 授信账户额度变更
     *
     * @param request
     * @return
     */
    @Override
    public BaseResponse<Integer> updateCreditAmount(CreditAmountRestoreRequest request) {
        creditAccountService.updateCreditAmout(request);
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse recoverCreditAmount(@RequestBody CreditAccountPageRequest request) {
        creditAccountService.recoverCreditAmount(request);
        return BaseResponse.SUCCESSFUL();
    }
}
