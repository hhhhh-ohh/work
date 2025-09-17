package com.wanmi.sbc.marketing.communityregionsetting.repository;

import com.wanmi.sbc.marketing.communityregionsetting.model.root.CommunityRegionLeaderSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * <p>社区拼团区域设置表DAO</p>
 * @author dyt
 * @date 2023-07-20 14:55:16
 */
@Repository
public interface CommunityRegionLeaderSettingRepository extends JpaRepository<CommunityRegionLeaderSetting, Long>,
        JpaSpecificationExecutor<CommunityRegionLeaderSetting> {

    /**
     * 根据区域删除自提点
     * @param regionId 区域
     */
    void deleteByRegionId(Long regionId);
}
