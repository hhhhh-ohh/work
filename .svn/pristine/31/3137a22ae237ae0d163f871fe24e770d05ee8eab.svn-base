package com.wanmi.sbc.marketing.communitysku.repository;

import com.wanmi.sbc.marketing.communitysku.model.root.CommunitySkuRel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * <p>社区团购活动商品表DAO</p>
 * @author dyt
 * @date 2023-07-24 14:47:53
 */
@Repository
public interface CommunitySkuRelRepository extends JpaRepository<CommunitySkuRel, Long>,
        JpaSpecificationExecutor<CommunitySkuRel> {

    @Modifying
    void deleteByActivityId(String activityId);

    @Modifying
    @Query("update CommunitySkuRel set activityStock = ?1 where id = ?2")
    void updateActivityStockById(Long activityStock, Long id);

    @Modifying
    @Query("update CommunitySkuRel set sales = sales + ?1 where activityId = ?2 and skuId = ?3")
    int updateSales(Long sales, String activityId, String skuId);

    @Modifying
    @Query("update CommunitySkuRel set sales = sales + ?1 where activityId = ?2 and skuId = ?3 and activityStock >= sales + ?1")
    int updateSalesCheck(Long sales, String activityId, String skuId);

    Optional<CommunitySkuRel> getBySkuIdAndActivityId(String skuId, String activityId);
}
