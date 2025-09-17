package com.wanmi.sbc.order.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.marketing.bean.enums.MarketingType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>营销优惠金额明细</p>
 * Created by of628-wenzhi on 2018-04-19-下午9:52.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class DiscountsPriceDetailVO extends BasicResponse {

    private static final long serialVersionUID = 1L;
    /**
     * 营销类型
     */
    @Schema(description = "营销类型")
    private MarketingType marketingType;

    /**
     * 优惠金额
     */
    @Schema(description = "优惠金额")
    private BigDecimal discounts;
}
