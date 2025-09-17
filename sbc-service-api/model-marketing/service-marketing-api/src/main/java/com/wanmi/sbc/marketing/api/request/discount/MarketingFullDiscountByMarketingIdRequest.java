package com.wanmi.sbc.marketing.api.request.discount;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p></p>
 * Date: 2018-11-20
 * @author Administrator
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MarketingFullDiscountByMarketingIdRequest extends BaseRequest {

    private static final long serialVersionUID = -2748557922082570171L;

    @Schema(description = "营销id")
    private Long marketingId;
}
