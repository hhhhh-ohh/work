package com.wanmi.sbc.customer.communitypickup.repository;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.customer.bean.enums.LeaderCheckStatus;
import com.wanmi.sbc.customer.communitypickup.model.root.CommunityLeaderPickupPoint;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * <p>团长自提点表DAO</p>
 *
 * @author dyt
 * @date 2023-07-21 14:10:45
 */
@Repository
public interface CommunityLeaderPickupPointRepository extends JpaRepository<CommunityLeaderPickupPoint, String>,
        JpaSpecificationExecutor<CommunityLeaderPickupPoint> {

    /**
     * 单个删除团长自提点表
     *
     * @author dyt
     */
    @Modifying
    @Query("update CommunityLeaderPickupPoint set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where pickupPointId = ?1")
    void deleteById(String pickupPointId);

    /**
     * 批量删除团长自提点表
     *
     * @author dyt
     */
    @Modifying
    @Query("update CommunityLeaderPickupPoint set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where pickupPointId in ?1")
    void deleteByIdList(List<String> pickupPointIdList);

    /**
     * 更新审核状态
     *
     * @author dyt
     */
    @Modifying
    @Query("update CommunityLeaderPickupPoint set checkStatus = ?1 where leaderId = ?2")
    void updateCheckStatusByLeaderId(LeaderCheckStatus status, String leaderId);

    /**
     * 单个查询团长自提点表
     *
     * @author dyt
     */
    Optional<CommunityLeaderPickupPoint> findByPickupPointIdAndDelFlag(String id, DeleteFlag delFlag);
}
