package com.wanmi.sbc.account.api.provider.credit;

import com.wanmi.sbc.account.api.request.credit.RepayOrderPageRequest;
import com.wanmi.sbc.account.api.response.credit.order.CustomerCreditOrderListResponse;
import com.wanmi.sbc.account.api.response.credit.order.RepayOrderPageResponse;
import com.wanmi.sbc.common.base.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * @author houshuai
 * @date 2021/3/1 14:47
 * @description <p> 授信订单关联信息查询接口 </p>
 */
@FeignClient(value = "${application.account.name}", contextId = "CreditOrderQueryProvider")
public interface CreditOrderQueryProvider {

    /**
     * 查询已还款授信订单
     *
     * @param request {@link RepayOrderPageRequest}
     * @return {@link RepayOrderPageResponse}
     */
    @PostMapping("/account/${application.account.version}/find-current-order-by-user-id")
    BaseResponse<Page<RepayOrderPageResponse>> findRepayOrderPage(@RequestBody RepayOrderPageRequest request);

    /**
     * 列表查询授信订单信息API
     *
     * @author zhongjichuan
     * @param customerCreditOrderListReq 列表请求参数和筛选对象 {@link RepayOrderPageRequest}
     * @return 授信订单信息的列表信息 {@link CustomerCreditOrderListResponse}
     */
    @PostMapping("/account/${application.account.version}/customercreditorder/list")
    BaseResponse<CustomerCreditOrderListResponse> list(@RequestBody @Valid RepayOrderPageRequest customerCreditOrderListReq);
}
