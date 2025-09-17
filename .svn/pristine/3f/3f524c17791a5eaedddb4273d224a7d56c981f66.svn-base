package com.wanmi.sbc.marketing.api.provider.countPrice;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.marketing.api.request.countprice.TradeCountCouponPriceRequest;
import com.wanmi.sbc.marketing.api.request.countprice.TradeCountMarketingPriceRequest;
import com.wanmi.sbc.marketing.api.response.countprice.TradeCountCouponPriceResponse;
import com.wanmi.sbc.marketing.api.response.countprice.TradeCountPricePluginResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 *
 * @description
 * @author  zhanggaolei
 * @date 2021/7/26 10:46 上午
 **/
@FeignClient(value = "${application.marketing.name}", contextId = "TradeCountMarketingPriceProvider")
public interface TradeCountMarketingPriceProvider {

    /**
    *  下单计算营销价格
     * @description
     * @author  wur
     * @date: 2022/2/24 14:34
     * @param request
     * @return @link TradeCountPricePluginResponse
     **/
    @PostMapping("/marketing/${application.marketing.version}/trade/count-marketing-price")
    BaseResponse<TradeCountPricePluginResponse> tradeCountMarketingPrice(@RequestBody @Valid TradeCountMarketingPriceRequest request);

    /**
     *  下单计算优惠券价格
     * @description
     * @author  wur
     * @date: 2022/2/24 14:34
     * @param request
     * @return @link TradeCountCouponPriceResponse
     **/
    @PostMapping("/marketing/${application.marketing.version}/trade/count-coupon-price")
    BaseResponse<TradeCountCouponPriceResponse> tradeCountCouponPrice(@RequestBody @Valid TradeCountCouponPriceRequest request);

}
