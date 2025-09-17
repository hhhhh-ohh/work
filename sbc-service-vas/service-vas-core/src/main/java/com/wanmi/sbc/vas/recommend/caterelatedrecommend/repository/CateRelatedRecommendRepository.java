package com.wanmi.sbc.vas.recommend.caterelatedrecommend.repository;

import com.wanmi.sbc.vas.recommend.caterelatedrecommend.model.root.CateRelatedRecommend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

/**
 * <p>分类相关性推荐DAO</p>
 * @author lvzhenwei
 * @date 2020-11-26 10:55:53
 */
@Repository
public interface CateRelatedRecommendRepository extends JpaRepository<CateRelatedRecommend, Long>,
        JpaSpecificationExecutor<CateRelatedRecommend> {

    @Modifying
    @Query("update CateRelatedRecommend set weight = ?1,updateTime = now() where id = ?2")
    int updateWeight(BigDecimal weight, Long id);

    @Modifying
    @Query("update CateRelatedRecommend set weight = null,updateTime = now() where id = ?1")
    int updateWeightNull(Long id);

}
