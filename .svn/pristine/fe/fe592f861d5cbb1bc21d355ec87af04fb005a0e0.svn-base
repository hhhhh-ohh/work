package com.wanmi.sbc.marketing.api.provider.market;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.marketing.api.request.market.MarketingScopeByMarketingIdRequest;
import com.wanmi.sbc.marketing.api.request.market.MarketingScopeListInvalidMarketingRequest;
import com.wanmi.sbc.marketing.api.request.market.MarketingScopeValidateRequest;
import com.wanmi.sbc.marketing.api.response.market.MarketingScopeByMarketingIdResponse;
import com.wanmi.sbc.marketing.api.response.market.MarketingScopeListInvalidMarketingResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * @Author: ZhangLingKe
 * @Description: 营销查询接口Feign客户端
 * @Date: 2018-11-16 16:56
 */
@FeignClient(value = "${application.marketing.name}", contextId = "MarketingScopeQueryProvider")
public interface MarketingScopeQueryProvider {

    /**
     * 根据营销编号查询营销等级集合
     * @param marketingScopeByMarketingIdRequest  {@link MarketingScopeByMarketingIdRequest}
     * @return {@link MarketingScopeByMarketingIdResponse}
     */
    @PostMapping("/marketing/${application.marketing.version}/scope/list-by-marketing-id")
    BaseResponse<MarketingScopeByMarketingIdResponse> listByMarketingId(@RequestBody @Valid MarketingScopeByMarketingIdRequest marketingScopeByMarketingIdRequest);

    /**
     * 查询失效营销订单商品列表
     * @param request  {@link MarketingScopeListInvalidMarketingRequest}
     * @return {@link MarketingScopeListInvalidMarketingResponse}
     */
    @PostMapping("/marketing/${application.marketing.version}/scope/list-invalid-marketing")
    BaseResponse<MarketingScopeListInvalidMarketingResponse> listInvalidMarketing(@RequestBody @Valid MarketingScopeListInvalidMarketingRequest request);

    /**
     * 营销范围验证
     * @param request 验证碜数 {@link MarketingScopeValidateRequest}
     * @return 操作结果 {@link BaseResponse}
     */
    @PostMapping("/marketing/${application.marketing.version}/validate")
    BaseResponse validate(@RequestBody @Valid MarketingScopeValidateRequest request);
}
