package com.wanmi.sbc.account.provider.impl.credit;

import com.wanmi.sbc.account.api.provider.credit.CreditOrderProvider;
import com.wanmi.sbc.account.api.request.credit.CreditOrderRequest;
import com.wanmi.sbc.account.credit.service.CreditOrderService;
import com.wanmi.sbc.common.base.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author wangchao
 * @date 2021/3/1 15:43
 * @description <p> 授信订单api </p>
 */
@RestController
public class CreditOrderController implements CreditOrderProvider {

    @Autowired
    private CreditOrderService creditOrderService;

    /**
     * 新增授信支付订单关联数据
     * @param requests
     * @return
     */
    @Override
    public BaseResponse addCreditOrder(List<CreditOrderRequest> requests){
        requests.forEach(request -> creditOrderService.addCreditOrder(request));
        return BaseResponse.SUCCESSFUL();
    }

}
