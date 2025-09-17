package com.wanmi.sbc.account.provider.impl.credit;


import com.wanmi.sbc.account.api.provider.credit.CreditAuditProvider;
import com.wanmi.sbc.account.api.request.credit.CreditApplyRequest;
import com.wanmi.sbc.account.api.request.credit.CreditAuditRequest;
import com.wanmi.sbc.account.api.response.credit.account.CreditAccountDetailResponse;
import com.wanmi.sbc.account.credit.service.CreditAuditService;
import com.wanmi.sbc.common.base.BaseResponse;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

//import jakarta.annotation.Resource;

/***
 * 授信用户审核
 * @author zhengyang
 * @since 2021-03-02
 */
@RestController
@Validated
public class CreditAuditController implements CreditAuditProvider {

    @Resource
    private CreditAuditService creditAuditService;

    /***
     * 同意审批
     * @param request
     */
    @Override
    public BaseResponse<CreditAccountDetailResponse> applyAgree(CreditAuditRequest request) {
        return BaseResponse.success(creditAuditService.applyAgree(request));
    }

    /***
     * 驳回审批
     * @param request
     */
    @Override
    public BaseResponse<CreditAccountDetailResponse> applyReject(CreditAuditRequest request) {
        return BaseResponse.success(creditAuditService.applyReject(request));
    }

    /***
     * 申请审批
     * @param request
     */
    @Override
    public BaseResponse applyAudit(CreditApplyRequest request) {
        creditAuditService.applyAudit(request);
        return BaseResponse.SUCCESSFUL();
    }
}
