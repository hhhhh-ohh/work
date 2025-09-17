package com.wanmi.sbc.setting.pagemanage.repository;

import com.wanmi.sbc.setting.pagemanage.model.root.GoodsInfoExtend;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/**
 * @author houshuai
 * @date 2021/5/26 15:32
 * @description
 *     商品推广Do
 */
public interface GoodsInfoExtendRepository extends MongoRepository<GoodsInfoExtend, String> {

    /**
     * 根据goodsId查询商品推广
     *
     * @param goodsId
     * @return
     */
    Optional<GoodsInfoExtend> findByGoodsId(String goodsId);
}
