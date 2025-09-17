package com.wanmi.sbc.goods.storecate.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.wanmi.sbc.common.util.WmCollectionUtils;
import com.wanmi.sbc.goods.storecate.model.root.StoreCateGoodsRela;
import com.wanmi.sbc.goods.storecate.repository.StoreCateGoodsRelaRepository;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author: wanggang
 * @createDate: 2018/11/15 10:01
 * @version: 1.0
 */
@Service
public class StoreCateGoodsRelaService {

    @Autowired private StoreCateGoodsRelaRepository storeCateGoodsRelaRepository;

    /**
     * 根据商品ID查询
     *
     * @param goodsIds 商品ID
     * @return
     */
    public List<StoreCateGoodsRela> selectByGoodsId(List<String> goodsIds) {
        return storeCateGoodsRelaRepository.selectByGoodsId(goodsIds);
    }


    /**
     * @description 更新商品关联
     * @author daiyitian
     * @date 2021/4/16 9:53
     * @param goodsId 商品id
     * @param storeCateIds 店铺分类id
     * @return void
     */
    @Transactional
    public void updateByGoods(String goodsId, List<Long> storeCateIds) {
        this.storeCateGoodsRelaRepository.deleteByGoodsId(goodsId);
        if (CollectionUtils.isNotEmpty(storeCateIds)) {
            storeCateGoodsRelaRepository.saveAll(
                    storeCateIds.stream()
                            .map(
                                    id -> {
                                        StoreCateGoodsRela rel = new StoreCateGoodsRela();
                                        rel.setGoodsId(goodsId);
                                        rel.setStoreCateId(id);
                                        return rel;
                                    })
                            .collect(Collectors.toList()));
        }
    }

    /**
     * 根据店铺分类id获取所有商品id
     *
     * @param storeCateIds 店铺分类id
     * @return 商品id
     */
    public List<String> findGoodsIdByStoreCateIds(List<Long> storeCateIds) {
        return storeCateGoodsRelaRepository.selectGoodsIdByStoreCateIds(storeCateIds);
    }

    /**
     * 统计数量
     * @param storeCateIds 店铺分类id
     * @param goodsIds 商品id
     * @return 数量
     */
    public Long countByStoreCateIdsAndGoodsIds(List<Long> storeCateIds, List<String> goodsIds){
        return storeCateGoodsRelaRepository.countByStoreCateIdsAndGoodsIds(storeCateIds, goodsIds);
    }
}
