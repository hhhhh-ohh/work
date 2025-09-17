package com.wanmi.sbc.marketing.api.provider.payingmember;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.marketing.api.request.payingmember.PayingMemberSkuRequest;
import com.wanmi.sbc.marketing.api.response.payingmember.PayingMemberSkuResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * @Author: xuyunpeng
 * @Description: 付费会员优惠查询接口Feign客户端
 * @Date: 2018-11-16 16:56
 */
@FeignClient(value = "${application.marketing.name}", contextId = "PayingMemberDiscountProvider")
public interface PayingMemberDiscountProvider {

    /**
     * 查询指定商品最大优惠金额
     * @param payingMemberSkuRequest  {@link PayingMemberSkuRequest}
     * @return {@link PayingMemberSkuResponse}
     */
    @PostMapping("/marketing/${application.marketing.version}/scope/paying-member-discount")
    BaseResponse<PayingMemberSkuResponse> discountForSku(@RequestBody @Valid PayingMemberSkuRequest payingMemberSkuRequest);
}
