package com.wanmi.sbc.account.api.provider.credit;

import com.wanmi.sbc.account.api.request.credit.CreditOrderRequest;
import com.wanmi.sbc.common.base.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author wangchao
 * @date 2021/3/9 10:47
 * @description <p> 授信订单关联信息查询接口 </p>
 */
@FeignClient(value = "${application.account.name}", contextId = "CreditOrderProvider")
public interface CreditOrderProvider {

    /**
     * 批量增加授信支付关联订单记录
     * @param requests
     * @return
     */
    @PostMapping("/account/${application.account.version}/credit/order/add")
    BaseResponse addCreditOrder(@RequestBody List<CreditOrderRequest> requests);
}
