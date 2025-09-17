package com.wanmi.sbc.order.optimization.trade1.snapshot.base.service;

import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.goods.bean.vo.GoodsIntervalPriceVO;
import com.wanmi.sbc.goods.bean.vo.GoodsVO;
import com.wanmi.sbc.order.api.optimization.trade1.request.TradeItemRequest;
import com.wanmi.sbc.order.bean.dto.TradeItemDTO;

import java.util.List;

public interface TradeBuyAssembleInterface {

    /**
     * @description 构建订单基本信息
     * @author edz
     * @date: 2022/3/18 16:42
     * @param tradeItem 订单对象
     * @param goodsInfo 数据库查询出的商品对象
     * @param goods 数据库查询出的商品对象
     * @param storeId 店铺ID
     * @return void
     */
    void tradeItemBaseBuilder(
            TradeItemDTO tradeItem, GoodsInfoVO goodsInfo, GoodsVO goods, Long storeId);

    /**
     * @description 构建订单价格信息（没有处理营销）
     * @author edz
     * @date: 2022/3/18 16:44
     * @param tradeItem 前端请求参数
     * @param tradeItemDTO 订单对象
     * @param goodsInfo 数据库查询出来的商品对象
     * @param goods 数据库查询出来的商品对象
     * @param goodsIntervalPrices 商品区间价格列表
     * @return void
     */
    void tradeItemPriceBuilder(
            TradeItemRequest tradeItem,
            TradeItemDTO tradeItemDTO,
            GoodsInfoVO goodsInfo,
            GoodsVO goods,
            List<GoodsIntervalPriceVO> goodsIntervalPrices);
}
