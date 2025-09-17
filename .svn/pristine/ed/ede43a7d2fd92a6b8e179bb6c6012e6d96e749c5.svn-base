package com.wanmi.sbc.account.api.provider.credit;

import com.wanmi.sbc.account.api.request.credit.CheckCreditRepayRequest;
import com.wanmi.sbc.account.api.request.credit.CustomerCreditRepayAddRequest;
import com.wanmi.sbc.account.api.request.credit.CustomerCreditRepayCancelRequest;
import com.wanmi.sbc.account.api.request.credit.CustomerCreditRepayModifyRequest;
import com.wanmi.sbc.account.api.response.credit.CustomerCreditRepayAddResponse;
import com.wanmi.sbc.account.api.response.credit.repay.CustomerCreditRepayModifyResponse;
import com.wanmi.sbc.common.base.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>授信还款服务Provider</p>
 *
 * @author chenli
 * @date 2021-03-04 16:21:28
 */
@FeignClient(value = "${application.account.name}", contextId = "CustomerCreditRepayProvider")
public interface CustomerCreditRepayProvider {

    /**
     * 新增授信还款信息API
     *
     * @param customerCreditRepayAddRequest 授信订单信息新增参数结构 {@link CustomerCreditRepayAddRequest}
     * @return {@link CustomerCreditRepayAddResponse}
     */
    @PostMapping("/account/${application.account.version}/customercreditrepay/add")
    BaseResponse<CustomerCreditRepayAddResponse> add(@RequestBody @Valid CustomerCreditRepayAddRequest customerCreditRepayAddRequest);

    /**
     * 还款中还款记录作废
     *
     * @param request
     * @return
     */
    @PostMapping("/account/${application.account.version}/customercreditrepay/cancel")
    BaseResponse cancel(@RequestBody @Valid CustomerCreditRepayCancelRequest request);

    /*
     *//**
     * 修改授信还款信息API
     *
     * @param customerCreditRepayModifyRequest 授信订单信息修改参数结构 {@link CustomerCreditRepayModifyRequest}
     * @return 修改的授信订单信息信息 {@link CustomerCreditRepayModifyResponse}
     */
	@PostMapping("/account/${application.account.version}/customercreditrepay/modify")
    BaseResponse<CustomerCreditRepayModifyResponse> modify(@RequestBody @Valid CustomerCreditRepayModifyRequest customerCreditRepayModifyRequest);

	/**
     * 支付成功-更新授信还款
     * @param customerCreditRepayModifyRequest
     * @return
     */
	@PostMapping("/account/${application.account.version}/customercreditrepay/addByPaySuccess")
    BaseResponse<CustomerCreditRepayModifyResponse> modifyByPaySuccess(@RequestBody @Valid CustomerCreditRepayModifyRequest customerCreditRepayModifyRequest);

    /**
     * 线下授信还款审核
     *
     * @param request
     */
    @PostMapping("/account/${application.account.version}/customercreditrepay/check")
    BaseResponse checkCreditRepay(@RequestBody @Valid CheckCreditRepayRequest request);
}

