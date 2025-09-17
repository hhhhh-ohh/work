package com.wanmi.sbc.marketing.communityassist.repository;

import com.wanmi.sbc.marketing.communityassist.model.root.CommunityAssistRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>社区团购活动帮卖转发记录DAO</p>
 * @author dyt
 * @date 2023-08-01 15:45:58
 */
@Repository
public interface CommunityAssistRecordRepository extends JpaRepository<CommunityAssistRecord, String>,
        JpaSpecificationExecutor<CommunityAssistRecord> {

    /**
     * 活动分组的统计去重团长汇总
     * @param activityIds 活动id
     * @return 去重团长人数
     */
    @Query(value = "SELECT activityId,count(DISTINCT(leaderId))" +
            " FROM CommunityAssistRecord WHERE activityId in (:activityIds) GROUP BY activityId")
    List<Object> totalGroupActivityIds(@Param("activityIds") List<String> activityIds);
}
