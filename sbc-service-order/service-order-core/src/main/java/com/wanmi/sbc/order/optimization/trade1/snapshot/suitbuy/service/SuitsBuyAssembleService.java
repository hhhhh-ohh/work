package com.wanmi.sbc.order.optimization.trade1.snapshot.suitbuy.service;

import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.goods.bean.vo.GoodsIntervalPriceVO;
import com.wanmi.sbc.goods.bean.vo.GoodsVO;
import com.wanmi.sbc.order.api.optimization.trade1.request.TradeItemRequest;
import com.wanmi.sbc.order.bean.dto.TradeItemDTO;
import com.wanmi.sbc.order.optimization.trade1.snapshot.base.service.impl.TradeBuyAssembleService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * @author edz
 * @className SuitBuyAssembleService
 * @description TODO
 * @date 2022/3/31 11:34
 */
@Service
public class SuitsBuyAssembleService extends TradeBuyAssembleService {

    @Override
    public void tradeItemPriceBuilder(
            TradeItemRequest tradeItem,
            TradeItemDTO tradeItemDTO,
            GoodsInfoVO goodsInfo,
            GoodsVO goods,
            List<GoodsIntervalPriceVO> goodsIntervalPrices) {
        // 订货区间设价
        BigDecimal price =
                goodsInfo.getMarketPrice() == null ? BigDecimal.ZERO : goodsInfo.getMarketPrice();
        tradeItemDTO.setLevelPrice(price);
        tradeItemDTO.setOriginalPrice(price);
        tradeItemDTO.setPrice(price);
        tradeItemDTO.setSplitPrice(
                tradeItemDTO
                        .getLevelPrice()
                        .multiply(new BigDecimal(tradeItemDTO.getNum()))
                        .setScale(2, RoundingMode.HALF_UP));
    }
}
