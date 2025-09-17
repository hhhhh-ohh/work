package com.wanmi.sbc.order.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.marketing.bean.vo.MarketingHalfPriceSecondPieceLevelVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * <p>订单相关营销信息请求Bean</p>
 * Created by of628-wenzhi on 2018-02-27-下午4:30.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class TradeConfirmMarketingVO extends BasicResponse {

    private static final long serialVersionUID = 1L;

    /**
     * 营销活动Id
     */
    @Schema(description = "营销活动Id")
    private Long marketingId;

    /**
     * 营销等级id
     */
    @Schema(description = "营销等级id")
    private Long marketingLevelId;


    /**
     * 与Marketing.marketingType保持一致
     */
    @Schema(description = "促销类型", contentSchema = com.wanmi.sbc.marketing.bean.enums.MarketingType.class)
    private Integer marketingType;

    /**
     * 促销描述
     */
    @Schema(description = "促销描述")
    private String marketingDesc;

    /**
     * 该营销活动关联的订单商品id集合
     */
    @Schema(description = "该营销活动关联的订单商品id集合")
    private List<String> skuIds;

    /**
     * 如果是满赠，则填入用户选择的赠品id集合
     */
    @Schema(description = "如果是满赠，则填入用户选择的赠品id集合")
    private List<String> giftSkuIds;

    /**
     * 如果是加价购，则填入用户选择的商品id集合
     */
    @Schema(description = "如果是加价购，则填入用户选择的商品id集合")
    private List<String> preferentialSkuIds;

    /**
     * 第二件半价
     */
    private MarketingHalfPriceSecondPieceLevelVO halfPriceSecondPieceLevel;

    /**
     * 如果是满返，则填入用户选择的赠券id集合
     */
    @Schema(description = "如果是满返，则填入用户选择的赠券id集合")
    private List<String> returnCouponIds;
}
