package com.wanmi.sbc.marketing.api.response.market;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.marketing.bean.vo.GoodsInfoMarketingVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketInfoForPurchaseResponse extends BasicResponse {

    /**
     * 分销员佣金比例
     */
    private BigDecimal commissionRate = BigDecimal.ZERO;

    /**
     * 商品营销相关信息
     */
    private List<GoodsInfoMarketingVO> goodsInfos = new ArrayList<>();
}
