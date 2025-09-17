package com.wanmi.sbc.marketing.communitystockorder.repository;

import com.wanmi.sbc.marketing.communitystockorder.model.root.CommunityStockOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * <p>社区团购备货单DAO</p>
 * @author dyt
 * @date 2023-08-03 14:05:20
 */
@Repository
public interface CommunityStockOrderRepository extends JpaRepository<CommunityStockOrder, String>,
        JpaSpecificationExecutor<CommunityStockOrder> {
    @Modifying
    void deleteByActivityId(String activityId);
}
