package com.wanmi.sbc.marketing.communityregionsetting.repository;

import com.wanmi.sbc.marketing.communityregionsetting.model.root.CommunityRegionSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * <p>社区拼团区域设置表DAO</p>
 * @author dyt
 * @date 2023-07-20 14:19:23
 */
@Repository
public interface CommunityRegionSettingRepository extends JpaRepository<CommunityRegionSetting, Long>,
        JpaSpecificationExecutor<CommunityRegionSetting> {


    /**
     * 单个查询社区拼团区域设置表
     * @author dyt
     */
    Optional<CommunityRegionSetting> findByRegionIdAndStoreId(Long regionId, Long storeId);

}
