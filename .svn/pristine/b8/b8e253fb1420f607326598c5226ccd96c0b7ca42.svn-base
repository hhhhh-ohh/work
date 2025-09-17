package com.wanmi.sbc.account.api.provider.credit;


import com.wanmi.sbc.account.api.request.credit.CreditApplyDetailRequest;
import com.wanmi.sbc.account.api.request.credit.CreditApplyQueryRequest;
import com.wanmi.sbc.account.api.request.credit.CreditAuditQueryRequest;
import com.wanmi.sbc.account.api.response.credit.CreditApplyDetailResponse;
import com.wanmi.sbc.account.bean.vo.CustomerApplyRecordVo;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/***
 * 用户授信申请记录
 * @author zhengyang
 * @since 2021-03-01
 */
@FeignClient(value = "${application.account.name}", contextId = "CustomerApplyRecordProvider")
public interface CustomerApplyRecordProvider {

    /***
     * 用户授信记录申请列表
     * @param request
     * @return
     */
    @PostMapping("/account/${application.account.version}/customerapplyrecord/query-apply-record")
    BaseResponse<MicroServicePage<CustomerApplyRecordVo>> queryApplyRecord(@RequestBody CreditAuditQueryRequest request);

    /***
     * 根据用户申请记录ID查询用户授信申请详情
     * @param request
     * @return
     */
    @PostMapping("/account/${application.account.version}/customerapplyrecord/find-creditaccount-applydetail")
    BaseResponse<CreditApplyDetailResponse> findCreditAccountApplyDetailById(@RequestBody @Validated CreditApplyDetailRequest request);

    /***
     * 根据用户申请记录ID查询用户授信申请详情
     * @param request
     * @return
     */
    @PostMapping("/account/${application.account.version}/customerapplyrecord/modify-by-customerId")
    BaseResponse modifyByCustomerId(@RequestBody @Validated CreditApplyQueryRequest request);
}
