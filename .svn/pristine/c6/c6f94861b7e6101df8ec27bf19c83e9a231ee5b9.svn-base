package com.wanmi.sbc.marketing.communityrel.repository;

import com.wanmi.sbc.marketing.communityrel.model.root.CommunityLeaderRel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

/**
 * <p>社区团购活动团长关联表DAO</p>
 * @author dyt
 * @date 2023-07-24 14:32:15
 */
@Repository
public interface CommunityLeaderRelRepository extends JpaRepository<CommunityLeaderRel, Long>,
        JpaSpecificationExecutor<CommunityLeaderRel> {

    @Modifying
    void deleteByActivityId(String activityId);
}
