package com.wanmi.sbc.marketing.communitystatistics.repository;

import com.wanmi.sbc.marketing.communitystatistics.model.root.CommunityStatisticsCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * <p>社区团购活动会员信息表DAO</p>
 * @author dyt
 * @date 2023-07-24 14:49:55
 */
@Repository
public interface CommunityStatisticsCustomerRepository extends JpaRepository<CommunityStatisticsCustomer, String>,
        JpaSpecificationExecutor<CommunityStatisticsCustomer> {

    /**
     * 团长分组的统计去重会员人数
     * @param leaderIds 团长id
     * @return 去重会员人数
     */
    @Query(value = "SELECT leaderId,count(DISTINCT(customerId))" +
            " FROM CommunityStatisticsCustomer WHERE leaderId in :leaderIds GROUP BY leaderId")
    List<Object> countCustomerGroupLeaderIds(@Param("leaderIds") List<String> leaderIds);
}
