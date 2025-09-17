package com.wanmi.sbc.marketing.provider.impl.countprice;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.marketing.api.provider.countPrice.TradeCountMarketingPriceProvider;
import com.wanmi.sbc.marketing.api.request.countprice.TradeCountCouponPriceRequest;
import com.wanmi.sbc.marketing.api.request.countprice.TradeCountMarketingPriceRequest;
import com.wanmi.sbc.marketing.api.response.countprice.TradeCountCouponPriceResponse;
import com.wanmi.sbc.marketing.api.response.countprice.TradeCountPricePluginResponse;
import com.wanmi.sbc.marketing.countPrice.TradeMarketingCountPriceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
*
 * @description
 * @author  wur
 * @date: 2022/2/26 17:14
 **/
@RestController
@Validated
@Slf4j
public class TradeMarketingCountPriceController implements TradeCountMarketingPriceProvider {

    @Autowired private TradeMarketingCountPriceService tradeMarketingCountPriceService;

    @Override
    public BaseResponse<TradeCountPricePluginResponse> tradeCountMarketingPrice(@Valid TradeCountMarketingPriceRequest request) {
        return BaseResponse.success(tradeMarketingCountPriceService.tradeCountMarketingPrice(request));
    }

    @Override
    public BaseResponse<TradeCountCouponPriceResponse> tradeCountCouponPrice(@Valid TradeCountCouponPriceRequest request) {
        //调用优惠券算价
        return BaseResponse.success(tradeMarketingCountPriceService.tradeCountCouponPrice(request));
    }

}
