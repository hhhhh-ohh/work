package com.wanmi.sbc.marketing.api.request.market;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author: ZhangLingKe
 * @Description:
 * @Date: 2018-11-19 14:43
 */
@EqualsAndHashCode(callSuper = true)
@Schema
@Data
@Builder
public class MarketingPauseByIdRequest extends MarketingIdRequest{

    private static final long serialVersionUID = 4122963738996344103L;
}
