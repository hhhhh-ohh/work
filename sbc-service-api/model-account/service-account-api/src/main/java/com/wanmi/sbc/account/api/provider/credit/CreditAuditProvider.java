package com.wanmi.sbc.account.api.provider.credit;


import com.wanmi.sbc.account.api.request.credit.CreditApplyRequest;
import com.wanmi.sbc.account.api.request.credit.CreditAuditRequest;
import com.wanmi.sbc.account.api.request.validgroup.AddGroup;
import com.wanmi.sbc.account.api.request.validgroup.AgreeGroup;
import com.wanmi.sbc.account.api.request.validgroup.RejectGroup;
import com.wanmi.sbc.account.api.response.credit.account.CreditAccountDetailResponse;
import com.wanmi.sbc.common.base.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/***
 * 用户授信申请记录
 * @author zhengyang
 * @since 2021-03-01
 */
@FeignClient(value = "${application.account.name}", contextId = "CreditAuditProvider")
public interface CreditAuditProvider {

    /***
     * 同意审批
     * @param request
     */
    @PostMapping("/account/${application.account.version}/creditaudit/applyAgree")
    BaseResponse<CreditAccountDetailResponse> applyAgree(@RequestBody @Validated({AgreeGroup.class}) CreditAuditRequest request);

    /***
     * 拒绝审批
     * @param request
     */
    @PostMapping("/account/${application.account.version}/creditaudit/applyReject")
    BaseResponse<CreditAccountDetailResponse> applyReject(@RequestBody @Validated({RejectGroup.class}) CreditAuditRequest request);

    /***
     * 申请审批
     * @param request
     */
    @PostMapping("/account/${application.account.version}/creditaudit/applyAudit")
    BaseResponse applyAudit(@RequestBody @Validated({AddGroup.class}) CreditApplyRequest request);
}
