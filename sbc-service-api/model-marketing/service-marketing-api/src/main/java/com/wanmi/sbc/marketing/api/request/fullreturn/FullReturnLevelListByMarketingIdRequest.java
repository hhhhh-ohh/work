package com.wanmi.sbc.marketing.api.request.fullreturn;

import com.wanmi.sbc.marketing.api.request.market.MarketingIdRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author: xufeng
 * @Description:
 * @Date: 2022-04-11 16:16
 */
@EqualsAndHashCode(callSuper = true)
@Schema
@Data
public class FullReturnLevelListByMarketingIdRequest extends MarketingIdRequest {

    private static final long serialVersionUID = 6736902045831506758L;

}
