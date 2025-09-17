package com.wanmi.sbc.goods.goodscommissionconfig.respository;

import com.wanmi.sbc.goods.goodscommissionconfig.model.root.GoodsCommissionConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @description 商品代销配置
 * @author  wur
 * @date: 2021/9/10 10:01
 **/
@Repository
public interface GoodsCommissionConfigRepository extends JpaRepository<GoodsCommissionConfig, Long>,
        JpaSpecificationExecutor<GoodsCommissionConfig> {

    /**
     * 根据商家Id查询
     * @param storeId 商家Id
     * @return
     */
    GoodsCommissionConfig findByStoreId(Long storeId);

}
