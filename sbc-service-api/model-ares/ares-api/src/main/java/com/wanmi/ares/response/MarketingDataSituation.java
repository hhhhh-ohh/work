package com.wanmi.ares.response;

import com.wanmi.ares.enums.MarketingType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


/**
 * 营销概览-营销活动数据概况返回对象
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class MarketingDataSituation extends MarketingAnalysisBase {

    /**
     * 营销类型 0 优惠券 1拼团 2秒杀 3满系 4打包一口价 5第二件半价 6组合购 7预约 8全款预售 9订金预售 10预售
     */
    @Schema(description = "营销类型 0 优惠券 1拼团 2秒杀 3满系 4打包一口价 5第二件半价 6组合购 7预约 8全款预售 9订金预售 10预售", required = true)
    private MarketingType marketingType;

    @Schema(description = "活动数量")
    private long marketingActivityCount;

}
