package com.wanmi.sbc.elastic.goods.service;

import com.wanmi.sbc.elastic.api.request.goods.EsGoodsInfoRequest;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsModifyStoreNameByStoreIdRequest;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsStoreInfoModifyRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;

/***
 * ES商品信息数据源接口
 * @className EsGoodsElasticServiceInterface
 * @author zhengyang
 * @date 2021/7/8 18:09
 **/
public interface EsGoodsElasticServiceInterface {

    /**
     * 初始化SPU持化于ES
     */
    void initEsGoods(EsGoodsInfoRequest request);

    /**
     * 更新店铺信息
     *
     * @param request 更新入参
     */
    void updateStoreStateByStoreId(EsGoodsStoreInfoModifyRequest request);

    /**
     * 商品-更新店铺信息时，更新商品goods中对应店铺名称，同步es
     *
     * @param request 更新入参
     */
    void updateStoreNameByStoreId(EsGoodsModifyStoreNameByStoreIdRequest request);
}
