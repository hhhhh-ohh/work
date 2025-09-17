package com.wanmi.sbc.order.trade.service;

import com.wanmi.sbc.goods.api.request.info.GoodsInfoBatchMinusStockRequest;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoBatchPlusStockRequest;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.order.trade.model.entity.TradeItem;
import org.springframework.stereotype.Service;

@Service
public class StockService {

    /**
     * o2o重新设置库存切面
     * @param vo
     * @para
     * @return
     */
    public GoodsInfoVO setStock(GoodsInfoVO vo){
        return vo;
    }

    /**
     * o2o 校验库存切面
     * @param request
     */
    public void checkStock(GoodsInfoBatchMinusStockRequest request){}

    /**
     * o2o 减库存切面
     * @param request
     */
    public void batchMinusStock(GoodsInfoBatchMinusStockRequest request){}

    /**
     * o2o 加库存切面
     * @param request
     */
    public void batchPlusStock(GoodsInfoBatchPlusStockRequest request){}

    /**
     * 获取运费模版Id
     * @return
     */
    public TradeItem getFreightTempId(TradeItem tradeItem){ return tradeItem;}
}
