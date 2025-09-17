package com.wanmi.sbc.marketing.provider.impl.payingmember;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.marketing.api.provider.payingmember.PayingMemberDiscountProvider;
import com.wanmi.sbc.marketing.api.request.payingmember.PayingMemberSkuRequest;
import com.wanmi.sbc.marketing.api.response.payingmember.PayingMemberSkuResponse;
import com.wanmi.sbc.marketing.payingmember.PayingMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * @author xuyunpeng
 * @className PayingMemberDiscountController
 * @description
 * @date 2022/5/20 4:43 PM
 **/
@Validated
@RestController
public class PayingMemberDiscountController implements PayingMemberDiscountProvider {
    @Autowired
    private PayingMemberService payingMemberService;

    @Override
    public BaseResponse<PayingMemberSkuResponse> discountForSku(@RequestBody @Valid PayingMemberSkuRequest payingMemberSkuRequest) {
        PayingMemberSkuResponse response = payingMemberService.skuCustomer(payingMemberSkuRequest);
        return BaseResponse.success(response);
    }
}
