package com.wanmi.sbc.goods.service.list;

/**
 * @author zhanggaolei
 * @className GoodsListInterface
 * @description TODO
 * @date 2021/8/11 3:47 下午
 */
public interface GoodsListInterface<K,T> {

    default T getList(K request) {
        return getList(request, null);
    }

    default T getList(K request, Long storeId) {
        request = setRequest(request);
        T t = getEsDataPage(request);
        if(isEmpty(t)){
            return t;
        }
        t = setStock(t, storeId);
        t = filter(t);
        t = setPrice(t);
        t = setMarketing(t, storeId);
        t = syncPurchase(t);
        t = afterProcess(t, storeId);
        return t;
    }

    /**
     * 设置请求参数
     * @param request
     * @return
     */
    K setRequest(K request);

    /**
     * 分页获取ES数据
     * @param request
     * @return
     */
    T getEsDataPage(K request);

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
    T filter(T t);

    /**
     * 设置价格
     * @param t
     * @return
     */
    T setPrice(T t);

    /**
     * 设置营销
     * @param t
     * @return
     */
    T setMarketing(T t, Long storeId);

    /**
     * 同步购物车信息
     * @param t
     * @return
     */
    T syncPurchase(T t);

    /**
     * 最后的数据处理
     * @param t
     * @return
     */
    T afterProcess(T t, Long storeId);

    boolean isEmpty(T t);
}
