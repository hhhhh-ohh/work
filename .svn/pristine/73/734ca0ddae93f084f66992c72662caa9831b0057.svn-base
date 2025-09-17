package com.wanmi.sbc.order.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author zhanggaolei
 * @className MarketingPluginLabelDetailVO
 * @description TODO
 * @date 2022/1/17 4:11 下午
 */
@Data
public class MarketingPluginLabelSimpleDetailVO extends BasicResponse {
    /** 营销编号 */
    @Schema(description = "营销编号")
    private Object marketingId;

    /** 促销类型 */
    @Schema(description = "营销类型",
            contentSchema = com.wanmi.sbc.marketing.bean.enums.MarketingPluginType.class)
    private Integer marketingType;

    /** 促销描述 */
    @Schema(description = "营销描述")
    private String marketingDesc;

    /** 活动价格 */
    @Schema(description = "营销价格")
    private BigDecimal pluginPrice;

    /**活动类型*/
    @Schema(description = "活动类型 0: 满金额 1：满数量")
    private Integer subType;

    public <T> T getMarketingId() {
        return (T) marketingId;
    }
}
