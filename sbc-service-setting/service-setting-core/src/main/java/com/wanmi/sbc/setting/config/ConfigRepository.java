package com.wanmi.sbc.setting.config;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.setting.bean.enums.ConfigKey;
import com.wanmi.sbc.setting.bean.enums.ConfigType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 系统配置数据源
 * Created by daiyitian on 2017/04/11.
 */
@Repository
public interface ConfigRepository extends JpaRepository<Config, Long>, JpaSpecificationExecutor<Config> {

    /**
     * 根据type查询config
     *
     * @param configType configType
     * @param delFlag    delFlag
     * @return config对象
     */
    Config findByConfigTypeAndDelFlag(String configType, DeleteFlag delFlag);

    /**
     * 根据type查询config
     *
     * @param configType configType
     * @param configKey  configKey
     * @param delFlag    delFlag
     * @return config对象
     */
    Config findByConfigTypeAndConfigKeyAndDelFlag(String configType, String configKey, DeleteFlag delFlag);

    /**
     * 根据type查询configList
     *
     * @param configKey configKey
     * @param delFlag   delFlag
     * @return Stream<Config>
     */
    List<Config> findByConfigKeyAndDelFlag(String configKey, DeleteFlag delFlag);

    /**
     * 根据初始化配置关键字查询初始化配置List
     *
     * @param configKeyList 初始化信息Key列表
     * @param delFlag       删除标记
     * @return Stream<Config>
     */
    @Query("from Config c where c.configKey in (:configKeyList) AND c.delFlag = :delFlag")
    List<Config> findByConfigKeyListAndDelFlag(@Param("configKeyList") List<String> configKeyList, @Param("delFlag") DeleteFlag delFlag);


    @Modifying
    @Query("update Config c set c.status = :status ,c.updateTime = :currentTime where c.configType = :configType and c.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO")
    int updateStatusByType(@Param("configType") String configType, @Param("status") Integer status, @Param("currentTime") LocalDateTime currentTime);

    /**
     * 修改设置
     *
     * @param configType configType
     * @param configKey  configKey
     * @param status     configKey
     * @return rows
     */
    @Modifying
    @Query("update Config c set c.status = :status ,c.updateTime = now(), c.context= :context where c.configKey = :configKey and c.configType = :configType and c.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO")
    int updateStatusByTypeAndConfigKey(@Param("configType") String configType, @Param("configKey") String configKey
            , @Param("status") Integer status, @Param("context") String context);

    /**
     * 修改设置
     * @return rows
     */
    @Modifying
    @Query("update Config c set c.status = :status ,c.updateTime = now(), c.context= :context where c.id = :id and c.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO")
    int updateConfigById(@Param("id") Long id, @Param("status") Integer status, @Param("context") String context);

    /**
     * 直播开关
     *
     * @param configKey
     * @param status
     * @return
     */
    @Transactional
    @Modifying
    @Query("update Config c set c.status = :status  where c.configKey = :configKey and c.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO")
    int updateStatusConfigKey(@Param("configKey") String configKey, @Param("status") Integer status);

    @Modifying
    @Query("update Config c set c.status = :status  where c.configKey = :configKey and c.configType = :configType and c.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO")
    void secondaryAuditUpdate(ConfigKey configKey, ConfigType configType, Integer status);

    /**
     * 根据type查询configList
     *
     * @param configKey configKey
     * @param configType   configType
     * @return Stream<Config>
     */
    @Query("from Config c where c.configKey = :configKey AND c.configType = :configType AND c.delFlag =com.wanmi.sbc.common.enums.DeleteFlag.NO" )
    List<Config> getWhetherOpenMap(String configKey,String configType);
}
