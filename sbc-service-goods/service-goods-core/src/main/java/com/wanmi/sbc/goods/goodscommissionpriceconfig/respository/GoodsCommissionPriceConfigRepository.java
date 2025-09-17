package com.wanmi.sbc.goods.goodscommissionpriceconfig.respository;

import com.wanmi.sbc.common.enums.EnableStatus;
import com.wanmi.sbc.goods.goodscommissionpriceconfig.model.root.GoodsCommissionPriceConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @description 商品代销配置
 * @author  wur
 * @date: 2021/9/10 10:01
 **/
@Repository
public interface GoodsCommissionPriceConfigRepository extends JpaRepository<GoodsCommissionPriceConfig, Long>,
        JpaSpecificationExecutor<GoodsCommissionPriceConfig> {

    GoodsCommissionPriceConfig findById(String id);
 }
