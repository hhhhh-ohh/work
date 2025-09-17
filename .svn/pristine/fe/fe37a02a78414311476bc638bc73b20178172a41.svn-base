package com.wanmi.sbc.marketing.communityrel.repository;

import com.wanmi.sbc.marketing.communityrel.model.root.CommunityAreaRel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>社区团购活动区域关联表DAO</p>
 * @author dyt
 * @date 2023-07-24 14:38:10
 */
@Repository
public interface CommunityAreaRelRepository extends JpaRepository<CommunityAreaRel, Long>,
        JpaSpecificationExecutor<CommunityAreaRel> {

    @Modifying
    void deleteByActivityId(String activityId);
}
