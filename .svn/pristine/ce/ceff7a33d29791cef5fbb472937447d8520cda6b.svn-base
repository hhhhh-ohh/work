package com.wanmi.sbc.marketing.request;

import com.wanmi.sbc.goods.bean.dto.GoodsMutexValidateDTO;
import com.wanmi.sbc.marketing.bean.enums.MarketingScopeType;
import com.wanmi.sbc.marketing.bean.enums.MarketingType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.Arrays;
import java.util.List;

/**
 * @Author: dyt
 * @Description: 全局营销互斥请求入参
 * @Date: 2018-11-30 14:03
 */
@EqualsAndHashCode(callSuper = true)
@Schema
@Data
public class MarketingMutexValidateRequest extends GoodsMutexValidateDTO {

    @Schema(description = "非验证id-编辑时排除id")
    private String notSelfId;

    @Schema(description = "验证营销类型")
    private List<MarketingType> marketingTypes;

    @Schema(description = "验证满系营销商品范围")
    private List<MarketingScopeType> scopeTypes;

    @Schema(description = "是否满系编辑")
    private Boolean marketingIdFlag;

    @Schema(description = "是否拼团编辑")
    private Boolean grouponIdFlag;

    @Schema(description = "是否秒杀编辑")
    private Boolean flashIdFlag;

    @Schema(description = "是否预约编辑")
    private Boolean appointmentIdFlag;

    @Schema(description = "是否预售编辑")
    private Boolean bookingIdFlag;

    @Schema(description = "是否社区团购编辑")
    private Boolean communityIdFlag;

    @Schema(description = "是否砍价编辑")
    private Boolean bargainIdFlag;

    /**
     * 默认参与互斥营销的类型
     */
    public void setAllMarketing(){
        marketingTypes = Arrays.asList(MarketingType.REDUCTION, MarketingType.DISCOUNT, MarketingType.GIFT,
                MarketingType.BUYOUT_PRICE, MarketingType.HALF_PRICE_SECOND_PIECE, MarketingType.PREFERENTIAL
        );
    }
}
