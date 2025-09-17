package com.wanmi.sbc.customer.communityleader.repository;

import com.wanmi.sbc.customer.communityleader.model.root.CommunityLeader;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * <p>社区团购团长表DAO</p>
 * @author dyt
 * @date 2023-07-21 11:10:45
 */
@Repository
public interface CommunityLeaderRepository extends JpaRepository<CommunityLeader, String>,
        JpaSpecificationExecutor<CommunityLeader> {

    /**
     * 单个查询社区团购团长表
     * @author dyt
     */
    Optional<CommunityLeader> findByLeaderId(String id);

    /**
     * 单个查询社区团购团长表
     * @author dyt
     */
    Optional<CommunityLeader> findByCustomerId(String customerId);

    /**
     * 更新帮卖标识
     * @author dyt
     */
    @Modifying
    @Query("update CommunityLeader set assistFlag = ?1 where leaderId in ?2")
    void updateAssistFlagByLeaderIdIn(Integer assistFlag, List<String> leaderId);

    /**
     * 更新删除标识
     * @author dyt
     */
    @Modifying
    @Query("update CommunityLeader set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where leaderId in ?1")
    void updateDeleteFlagByLeaderIdIn(List<String> leaderId);
}
