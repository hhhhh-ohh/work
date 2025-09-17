package com.wanmi.sbc.marketing.communitysetting.repository;

import com.wanmi.sbc.marketing.communitysetting.model.root.CommunitySetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * <p>社区拼团商家设置表DAO</p>
 * @author dyt
 * @date 2023-07-20 11:30:25
 */
@Repository
public interface CommunitySettingRepository extends JpaRepository<CommunitySetting, Long>,
        JpaSpecificationExecutor<CommunitySetting> {

}
