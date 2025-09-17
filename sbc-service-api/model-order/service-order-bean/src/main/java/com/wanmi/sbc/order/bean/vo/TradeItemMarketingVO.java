package com.wanmi.sbc.order.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
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
public class TradeItemMarketingVO extends BasicResponse {

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
     * 该营销活动关联的订单商品id集合
     */
    @Schema(description = "该营销活动关联的订单商品id集合")
    private List<String> skuIds;

    /**
     * 如果是满赠，则填入用户选择的赠品id集合
     */
    @Schema(description = "如果是满赠，则填入用户选择的赠品id集合")
    private List<String> giftSkuIds = new ArrayList<>();

    /**
     * 如果是加价购，则填入用户选择的商品id集合
     */
    @Schema(description = "如果是加价购，则填入用户选择的商品id集合")
    private List<String> preferentialSkuIds;
}
