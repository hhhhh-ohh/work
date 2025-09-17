package com.wanmi.sbc.marketing.api.request.fullreturn;

import com.wanmi.sbc.marketing.api.request.market.MarketingIdRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * @Author: xufeng
 * @Description:
 * @Date: 2022-04-11 16:44
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FullReturnDetailListByMarketingIdAndLevelIdRequest extends MarketingIdRequest {

    private static final long serialVersionUID = 1143651789462855860L;
    /**
     * 满赠等级id
     */
    @Schema(description = "满赠等级id")
    private Long returnLevelId;


}
