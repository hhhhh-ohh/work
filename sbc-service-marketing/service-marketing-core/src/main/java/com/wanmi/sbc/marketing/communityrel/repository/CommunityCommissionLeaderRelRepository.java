package com.wanmi.sbc.marketing.communityrel.repository;

import com.wanmi.sbc.marketing.communityrel.model.root.CommunityCommissionLeaderRel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

/**
 * <p>社区团购活动佣金团长关联表DAO</p>
 * @author dyt
 * @date 2023-07-24 14:43:24
 */
@Repository
public interface CommunityCommissionLeaderRelRepository extends JpaRepository<CommunityCommissionLeaderRel, Long>,
        JpaSpecificationExecutor<CommunityCommissionLeaderRel> {

    @Modifying
    void deleteByActivityId(String activityId);
}
