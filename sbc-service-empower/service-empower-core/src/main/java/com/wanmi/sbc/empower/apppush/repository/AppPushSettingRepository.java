package com.wanmi.sbc.empower.apppush.repository;

import com.wanmi.sbc.empower.apppush.model.root.AppPushSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.wanmi.sbc.common.enums.DeleteFlag;
import java.util.Optional;
import java.util.List;

/**
 * <p>消息推送配置DAO</p>
 * @author 韩伟
 * @date 2021-04-01 16:36:29
 */
@Repository
public interface AppPushSettingRepository extends JpaRepository<AppPushSetting, Integer>,
        JpaSpecificationExecutor<AppPushSetting> {

    Optional<AppPushSetting> findByIdAndDelFlag(Integer id, DeleteFlag delFlag);

}
