package com.wanmi.sbc.marketing.communitydeliveryorder.repository;

import com.wanmi.sbc.marketing.communitydeliveryorder.model.root.CommunityDeliveryOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * <p>社区团购发货单DAO</p>
 * @author dyt
 * @date 2023-08-03 16:23:20
 */
@Repository
public interface CommunityDeliveryOrderRepository extends JpaRepository<CommunityDeliveryOrder, String>,
        JpaSpecificationExecutor<CommunityDeliveryOrder> {

    @Modifying
    @Query("delete from CommunityDeliveryOrder where activityId = :activityId")
    void deleteByActivityId(@Param("activityId") String activityId);

    @Modifying
    @Query("update CommunityDeliveryOrder set deliveryStatus = :deliveryStatus where id in (:ids)")
    void updateDeliveryStatusByActivityId(@Param("deliveryStatus") Integer id, @Param("ids") List<String> ids);
}
