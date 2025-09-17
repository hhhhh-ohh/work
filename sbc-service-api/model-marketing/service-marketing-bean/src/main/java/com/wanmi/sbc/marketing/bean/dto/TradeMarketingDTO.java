package com.wanmi.sbc.marketing.bean.dto;

import com.alibaba.fastjson2.annotation.JSONField;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <p>订单相关营销信息请求Bean</p>
 * Created by of628-wenzhi on 2018-02-27-下午4:30.
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TradeMarketingDTO implements Serializable {

    private static final long serialVersionUID = -1734718976293492276L;

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
     * 该营销活动关联的订单商品id集合
     */
    @Schema(description = "该营销活动关联的订单商品id集合")
    private List<String> skuIds;

    /**
     * 如果是满赠，则填入用户选择的赠品id集合
     */
    @Schema(description = "如果是满赠，则填入用户选择的赠品id集合")
    @JSONField(alternateNames = {"giftIds", "giftSkuIds"})
    private List<String> giftSkuIds;

    /**
     * 如果是加价购，则填入用户选择的商品id集合
     */
    @Schema(description = "如果是加价购，则填入用户选择的商品id集合")
    private List<String> preferentialSkuIds;
}
