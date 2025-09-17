package com.wanmi.sbc.setting.systemconfig.repository;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.setting.systemconfig.model.root.SystemConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>系统配置表DAO</p>
 * @author yang
 * @date 2019-11-05 18:33:04
 */
@Repository
public interface SystemConfigRepository extends JpaRepository<SystemConfig, Long>,
        JpaSpecificationExecutor<SystemConfig> {

    /**
     * 单个删除系统配置表
     * @author yang
     */
    @Modifying
    @Query("update SystemConfig set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where id = ?1")
    void deleteById(Long id);

    SystemConfig findByConfigKeyAndConfigTypeAndDelFlag(String configKey, String configType, DeleteFlag deleteFlag);

    @Query(value = "SELECT context FROM system_config WHERE config_type=?1 AND del_flag=0 limit 1",nativeQuery = true)
    String findContextByConfigType(String configType);

    @Query(value = "SELECT context FROM system_config WHERE config_key = ?1 AND config_type = ?2 AND del_flag = 0" , nativeQuery = true)
    String findContextByConfigTypeAndConfigKey(String configKey,String configType);

}
