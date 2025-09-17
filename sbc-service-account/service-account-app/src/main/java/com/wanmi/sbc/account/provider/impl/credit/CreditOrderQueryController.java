package com.wanmi.sbc.account.provider.impl.credit;

import com.wanmi.sbc.account.api.provider.credit.CreditOrderQueryProvider;
import com.wanmi.sbc.account.api.request.credit.RepayOrderPageRequest;
import com.wanmi.sbc.account.api.response.credit.order.CustomerCreditOrderListResponse;
import com.wanmi.sbc.account.api.response.credit.order.RepayOrderPageResponse;
import com.wanmi.sbc.account.bean.vo.CustomerCreditOrderVO;
import com.wanmi.sbc.account.credit.model.root.CustomerCreditOrder;
import com.wanmi.sbc.account.credit.service.CreditOrderQueryService;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.KsBeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author houshuai
 * @date 2021/3/1 15:43
 * @description <p> 授信订单api </p>
 */
@RestController
public class CreditOrderQueryController implements CreditOrderQueryProvider {

    @Autowired
    private CreditOrderQueryService creditOrderQueryService;

    /**
     *  已还款授信订单
     * @param request {@link RepayOrderPageRequest}
     * @return
     */
    @Override
    public BaseResponse<Page<RepayOrderPageResponse>> findRepayOrderPage(RepayOrderPageRequest request) {
        Page<RepayOrderPageResponse> response = creditOrderQueryService.findRepayOrderPage(request);
        return BaseResponse.success(response);
    }

    @Override
    public BaseResponse<CustomerCreditOrderListResponse> list(@RequestBody @Valid RepayOrderPageRequest request) {
        List<CustomerCreditOrder> customerCreditOrderList = creditOrderQueryService.getCreditOrderList(request);
        List<CustomerCreditOrderVO> newList = customerCreditOrderList.stream().map(entity -> creditOrderQueryService.wrapperVo(entity)).collect(Collectors.toList());
        return BaseResponse.success(new CustomerCreditOrderListResponse(newList));
    }
}
