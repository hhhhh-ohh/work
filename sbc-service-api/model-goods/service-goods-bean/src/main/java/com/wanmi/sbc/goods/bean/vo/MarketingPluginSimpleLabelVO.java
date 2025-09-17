package com.wanmi.sbc.goods.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author zhanggaolei
 * @className MarketingPluginSimpleLabelVO
 * @description TODO
 * @date 2021/6/26 10:34
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MarketingPluginSimpleLabelVO extends BasicResponse {

    /**
     * 营销活动Id
     */
    @Schema(description = "营销活动Id")
    private Object marketingId;

    /**
     * 促销类型
     */
    @Schema(description = "营销类型", contentMediaType = "com.wanmi.sbc.marketing.bean.enums.MarketingPluginType.class")
    private Integer marketingType;

    /**
     * 促销描述
     */
    @Schema(description = "营销描述")
    private String marketingDesc;

    /**
     * 活动价格
     */
    @Schema(description = "营销价格")
    private BigDecimal pluginPrice;

    public <T> T getMarketingId(){
        return (T) marketingId;
    }

}
