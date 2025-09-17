package com.wanmi.sbc.goods.ares;

import com.wanmi.sbc.goods.info.model.root.GoodsInfo;
import com.wanmi.sbc.goods.info.repository.GoodsInfoRepository;
import com.wanmi.sbc.goods.info.request.GoodsInfoQueryRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商品相关数据埋点 - 统计系统
 * Created by bail on 2017/10/16
 */
@Service
@Slf4j
public class GoodsAresService {

    @Autowired
    private GoodsInfoRepository goodsInfoRepository;

    /**
     * 根据spuId获取Sku
     *
     * @param goodsId spuId
     * @return
     */
    public List<GoodsInfo> findSkuByGoodsId(String goodsId) {
        return goodsInfoRepository.findAll(GoodsInfoQueryRequest.builder().goodsId(goodsId).build().getWhereCriteria());
    }

    /**
     * 根据spuIdList获取Sku
     *
     * @param goodsIds spuIdList
     * @return
     */
    public List<GoodsInfo> findSkuByGoodsIds(List<String> goodsIds) {
        return goodsInfoRepository.findAll(GoodsInfoQueryRequest.builder().goodsIds(goodsIds).build().getWhereCriteria());
    }

    /**
     * 根据skuIdList获取Sku
     *
     * @param goodsInfoIds
     * @return
     */
    public List<GoodsInfo> findSkuByGoodsInfoIds(List<String> goodsInfoIds) {
        return goodsInfoRepository.findAll(GoodsInfoQueryRequest.builder().goodsInfoIds(goodsInfoIds).build().getWhereCriteria());
    }
}
