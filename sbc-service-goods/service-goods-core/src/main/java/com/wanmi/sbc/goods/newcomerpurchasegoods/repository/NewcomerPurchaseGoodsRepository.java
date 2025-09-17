package com.wanmi.sbc.goods.newcomerpurchasegoods.repository;

import com.wanmi.sbc.goods.newcomerpurchasegoods.model.root.NewcomerPurchaseGoods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.wanmi.sbc.common.enums.DeleteFlag;
import java.util.Optional;
import java.util.List;

/**
 * <p>新人购商品表DAO</p>
 * @author zhanghao
 * @date 2022-08-19 14:28:56
 */
@Repository
public interface NewcomerPurchaseGoodsRepository extends JpaRepository<NewcomerPurchaseGoods, Integer>,
        JpaSpecificationExecutor<NewcomerPurchaseGoods> {

    /**
     * 单个删除新人购商品表
     * @author zhanghao
     */
    @Modifying
    @Query("update NewcomerPurchaseGoods set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where id = ?1")
    void deleteById(Integer id);

    /**
     * 批量删除新人购商品表
     * @author zhanghao
     */
    @Modifying
    @Query("update NewcomerPurchaseGoods set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where id in ?1")
    void deleteByIdList(List<Integer> idList);

    /**
     * 查询单个新人购商品表
     * @author zhanghao
     */
    Optional<NewcomerPurchaseGoods> findByIdAndDelFlag(Integer id, DeleteFlag delFlag);

    /**
     * 查询未删除的goodsInfoIds
     * @return
     */
    @Query(value = "select distinct goodsInfoId from NewcomerPurchaseGoods where delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO")
    List<String> findGoodsInfoIds();

}
