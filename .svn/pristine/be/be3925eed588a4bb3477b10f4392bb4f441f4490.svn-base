package com.wanmi.sbc.marketing.bean.dto;

import com.wanmi.sbc.marketing.bean.enums.MarketingPluginType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
*
 * @description  营销算价请求 - 订单营销活动信息
 * @author  wur
 * @date: 2022/2/24 10:38
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CountPriceMarketingDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 营销活动Id
     */
    @Schema(description = "营销活动Id")
    @NotNull
    private Long marketingId;

    @Schema(description = "营销活动类型")
    @NotNull
    private MarketingPluginType marketingPluginType;

    @Schema(description = "营销活动明细")
    @NotNull
    private Object detail;

    /**
     * 用户选中的营销等级id
     */
    @Schema(description = "用户选中的营销等级id")
    private Long marketingLevelId;

    /**
     * 该营销活动关联的订单商品id集合
     */
    @Schema(description = "该营销活动关联的订单商品id集合")
    @NotBlank
    private List<String> skuIds;

    /**
     * 如果是满赠，则填入用户选择的赠品id集合
     */
    @Schema(description = "如果是满赠，则填入用户选择的赠品id集合")
    private List<String> giftSkuIds;

    /**
     * 如果是满返，则填入用户选择的优惠券id集合
     */
    @Schema(description = "如果是满返，则填入用户选择的优惠券id集合")
    private List<String> couponIds;

    @Schema(description = "加价购商品id集合")
    private List<String> preferentialSkuIds;

    public <T> T getDetail(){
        return (T) detail;
    }

}