package com.wanmi.sbc.order.service;

/**
 * @author zhanggaolei
 * @className TradeConfirmInterface
 * @description TODO
 * @date 2022/2/16 2:13 下午
 **/
public interface TradeConfirmInterface {
    /**
     * 请求参数处理
     */
    void setRequest();

    /**
     * 获取商品信息
     */
    void getGoodsInfoData();

    /**
     * 获取店铺信息
     */
    void getStoreInfoData();

    /**
     * 获取库存
     */
    void getStock();

    /**
     * 商品校验
     */
    void goodsCheck();

    /**
     * 分销处理
     */
    void distribution();

    /**
     * 获取相关设置
     */
    void getSetting();

    /**
     * 获取营销
     */
    void getMarketing();

    /**
     * 营销校验
     */
    void marketingCheck();

    /**
     * 订单分组
     */
    void tradeGroup();

    /**
     * 设置价格
     */
    void setPrice();

    /**
     * 拼装订单数据
     */
    void assembleTrade();

    /**
     * 存储订单快照
     */
    void saveSnapshot();
}
