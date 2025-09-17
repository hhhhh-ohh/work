package com.wanmi.sbc.goods.service.detail;


import com.wanmi.sbc.common.base.PlatformAddress;

/**
 * @author zhanggaolei
 * @className GoodsDetailInterfac
 * @description TODO
 * @date 2021/8/11 3:47 下午
 */
public interface GoodsDetailInterface<T> {

    default T getDetail(String skuId, PlatformAddress address) {
        return getDetail(skuId, null, address);
    }

    default T getDetail(String skuId, Long storeId, PlatformAddress address) {
        T t = getData(skuId);
        t = setStock(t, storeId);
        t = filter(t, address);
        t = setMarketing(t, storeId);
        t = afterProcess(t, storeId);
        return t;
    }

    /**
     * 获取数据
     * @param skuId
     * @return
     */
    T getData(String skuId);

    /**
     * 设置库存
     * @param t
     * @return
     */
    T setStock(T t, Long storeId);

    /**
     * 过滤数据
     * @param t
     * @return
     */
    T filter(T t, PlatformAddress address);

    /**
     * 设置营销
     * @param t
     * @return
     */
    T setMarketing(T t, Long storeId);

    /**
     * 最后的数据处理
     * @param t
     * @param storeId
     * @return
     */
    T afterProcess(T t, Long storeId);
}
