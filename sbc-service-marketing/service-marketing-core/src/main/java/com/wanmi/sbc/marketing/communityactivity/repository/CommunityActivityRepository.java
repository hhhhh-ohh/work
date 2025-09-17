package com.wanmi.sbc.marketing.communityactivity.repository;

import com.wanmi.sbc.marketing.communityactivity.model.root.CommunityActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;

/**
 * <p>社区团购活动表DAO</p>
 * @author dyt
 * @date 2023-07-24 14:26:35
 */
@Repository
public interface CommunityActivityRepository extends JpaRepository<CommunityActivity, String>,
        JpaSpecificationExecutor<CommunityActivity> {

    /**
     * 单个查询社区团购活动表
     * @author dyt
     */
    Optional<CommunityActivity> findByActivityIdAndStoreId(String id, Long storeId);

    /**
     * 更新生成标识
     * @param ids 活动id
     * @param generateFlag 状态标识
     * @param generateTime 生成时间
     */
    @Modifying
    @Query("update CommunityActivity set generateFlag = ?2, generateTime = ?3 where activityId in ?1")
    void updateGenerateFlagByIds(List<String> ids, Integer generateFlag, LocalDateTime generateTime);
}
