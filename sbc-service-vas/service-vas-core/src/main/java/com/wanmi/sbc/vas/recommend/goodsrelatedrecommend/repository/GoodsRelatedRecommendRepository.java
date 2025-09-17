package com.wanmi.sbc.vas.recommend.goodsrelatedrecommend.repository;

import com.wanmi.sbc.vas.recommend.goodsrelatedrecommend.model.root.GoodsRelatedRecommend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

/**
 * <p>商品相关性推荐DAO</p>
 * @author lvzhenwei
 * @date 2020-11-24 16:13:10
 */
@Repository
public interface GoodsRelatedRecommendRepository extends JpaRepository<GoodsRelatedRecommend, Long>,
        JpaSpecificationExecutor<GoodsRelatedRecommend> {

    @Modifying
    @Query("update GoodsRelatedRecommend set weight = ?1,updateTime = now() where id = ?2")
    int updateWeight(BigDecimal weight, Long id);

    @Modifying
    @Query("update GoodsRelatedRecommend set weight = null,updateTime = now() where id = ?1")
    int updateWeightNull(Long id);

}
