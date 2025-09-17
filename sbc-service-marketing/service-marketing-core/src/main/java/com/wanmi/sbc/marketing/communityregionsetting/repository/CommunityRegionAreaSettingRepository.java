package com.wanmi.sbc.marketing.communityregionsetting.repository;

import com.wanmi.sbc.marketing.communityregionsetting.model.root.CommunityRegionAreaSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * <p>社区拼团区域设置表DAO</p>
 * @author dyt
 * @date 2023-07-20 14:56:35
 */
@Repository
public interface CommunityRegionAreaSettingRepository extends JpaRepository<CommunityRegionAreaSetting, Long>,
        JpaSpecificationExecutor<CommunityRegionAreaSetting> {


    /**
     * 根据区域删除省市区
     * @param regionId 区域id
     */
    void deleteByRegionId(Long regionId);
}
