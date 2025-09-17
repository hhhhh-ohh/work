package com.wanmi.sbc.vas.recommend.manualrecommendgoods.repository;

import com.wanmi.sbc.vas.recommend.manualrecommendgoods.model.root.ManualRecommendGoods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>手动推荐商品管理DAO</p>
 * @author lvzhenwei
 * @date 2020-11-23 10:51:47
 */
@Repository
public interface ManualRecommendGoodsRepository extends JpaRepository<ManualRecommendGoods, Long>,
        JpaSpecificationExecutor<ManualRecommendGoods> {

    @Modifying
    @Query("delete from ManualRecommendGoods where id in ?1")
    int deleteByIdList(List<Long> ids);

    @Modifying
    @Query("update ManualRecommendGoods set weight = ?1,updateTime = now() where id = ?2")
    int updateWeight(BigDecimal weight, Long id);

    @Modifying
    @Query("update ManualRecommendGoods set weight = null,updateTime = now() where id = ?1")
    int updateWeightNull(Long id);

}
