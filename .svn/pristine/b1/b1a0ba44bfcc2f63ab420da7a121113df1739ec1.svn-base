package com.wanmi.sbc.marketing.communityrel.repository;

import com.wanmi.sbc.marketing.communityrel.model.root.CommunityCommissionAreaRel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

/**
 * <p>社区团购活动佣金区域关联表DAO</p>
 * @author dyt
 * @date 2023-07-24 14:40:22
 */
@Repository
public interface CommunityCommissionAreaRelRepository extends JpaRepository<CommunityCommissionAreaRel, Long>,
        JpaSpecificationExecutor<CommunityCommissionAreaRel> {

    @Modifying
    void deleteByActivityId(String activityId);
}
