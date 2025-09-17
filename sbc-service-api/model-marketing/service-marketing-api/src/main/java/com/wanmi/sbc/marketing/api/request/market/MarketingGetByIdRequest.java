package com.wanmi.sbc.marketing.api.request.market;


import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @Author: ZhangLingKe
 * @Description:
 * @Date: 2018-11-16 16:39
 */
@EqualsAndHashCode(callSuper = true)
@Schema
@Data
@NoArgsConstructor
public class MarketingGetByIdRequest extends MarketingIdRequest{
    private static final long serialVersionUID = 2878826543954581829L;

    /***
     * 门店ID，SBC时默认为空
     */
    @Schema(description = "门店Id")
    private Long storeId;

    /***
     * 构建一个请求对象
     * @param marketingId 营销ID
     * @return            请求对象
     */
    public static MarketingGetByIdRequest buildRequest(Long marketingId){
        MarketingGetByIdRequest request = new MarketingGetByIdRequest();
        request.setMarketingId(marketingId);
        return request;
    }
}
