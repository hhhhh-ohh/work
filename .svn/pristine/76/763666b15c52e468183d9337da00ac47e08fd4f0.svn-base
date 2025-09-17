package com.wanmi.sbc.order.purchase.service;

import com.wanmi.sbc.common.base.PlatformAddress;
import com.wanmi.sbc.customer.bean.vo.CustomerVO;
import com.wanmi.sbc.customer.bean.vo.MiniStoreVO;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.goods.bean.vo.FreightCartVO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoCartVO;
import com.wanmi.sbc.goods.bean.vo.GoodsMarketingVO;
import com.wanmi.sbc.order.api.request.purchase.CartGoodsInfoRequest;
import com.wanmi.sbc.order.api.request.purchase.PurchaseInfoRequest;
import com.wanmi.sbc.order.purchase.Purchase;

import java.util.List;

/**
 * @author zhanggaolei
 * @className CartInterface
 * @description TODO
 * @date 2021/12/27 2:30 下午
 **/
public interface CartInterface {

    PurchaseInfoRequest setRequest(PurchaseInfoRequest request);

    /**
     * 获取购物车信息
     * @param request
     * @return
     */
    List<Purchase> getPurchase(PurchaseInfoRequest request);

    /***
     * 设置店铺信息，必须在getGoodsInfo之前调用
     * O2O用，SBC中为空方法
     *
     * @param request               购物车请求信息
     * @return
     */
    default List<StoreVO> getStoreInfoBeforeGoodsInfo(PurchaseInfoRequest request) {
        return null;
    }

    /**
     * 查询商品信息
     * @param goodsInfoIds
     * @param customer
     * @param storeId           门店ID，O2O模式用，不可删除
     * @return
     */
    List<GoodsInfoCartVO> getGoodsInfo(List<String> goodsInfoIds,CustomerVO customer, Long storeId);

    /**
     * 设置库存
     * @param goodsInfoCartList
     * @param address
     * @param storeId           门店ID，O2O模式用，不可删除
     */
    List<GoodsInfoCartVO> setStock(List<GoodsInfoCartVO> goodsInfoCartList,PlatformAddress address, Long storeId);

    /***
     * 设置店铺信息
     * @param goodsInfoCartList
     * @param purchaseList
     * @return
     */
    List<StoreVO> getStoreInfo(List<GoodsInfoCartVO> goodsInfoCartList, List<Purchase> purchaseList);

    /**
     * 校验后台的配置信息
     */
    List<GoodsInfoCartVO> checkSetting(List<GoodsInfoCartVO> goodsInfoCartList,CustomerVO customer,List<StoreVO> storeVOS, String inviteeId);

    /**
     * 获取营销
     * @param goodsInfoCartList
     * @param customer
     * @param storeId           门店ID，O2O模式用，不可删除
     */
    List<GoodsInfoCartVO> getMarketing(List<GoodsInfoCartVO> goodsInfoCartList,CustomerVO customer, Long storeId,List<CartGoodsInfoRequest> cartGoodsInfoRequests);

    /**
     * 设置购物车商品营销选择信息
     * @param sceneType 场景：0购物车，1快速下单
     */
    List<GoodsMarketingVO>  setGoodsMarketing(List<GoodsInfoCartVO> goodsInfoCartList,String customerId, Integer sceneType);

    /**
     * 设置购买数量
     */
    List<GoodsInfoCartVO> setBuyCount(List<GoodsInfoCartVO> goodsInfoCartVOList,List<Purchase> purchaseList);

    /**
     * 后置处理逻辑
     */
    List<GoodsInfoCartVO> afterProcess(List<GoodsInfoCartVO> goodsInfoCartVOList, CustomerVO customer);

    /**
     * 处理门店信息
     */
    List<MiniStoreVO> perfectStore(List<GoodsInfoCartVO> goodsInfoCartVOList, List<MiniStoreVO> storeVOList);

    /**
     * 处理运费模板
     */
    List<FreightCartVO> setFreight(List<GoodsInfoCartVO> goodsInfoCartVOList, List<StoreVO> storeVOS, PlatformAddress address);

    /**
     * 填充降价金额
     */
    void populateReductionPrice(List<GoodsInfoCartVO> goodsInfoCartVOList, List<Purchase> purchaseList, CustomerVO customer);
}
