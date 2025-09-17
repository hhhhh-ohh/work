package com.wanmi.sbc.setting.appexternalconfig.repository;

import com.wanmi.sbc.setting.appexternalconfig.model.root.AppExternalConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.wanmi.sbc.common.enums.DeleteFlag;
import java.util.Optional;
import java.util.List;

/**
 * <p>AppExternalConfigDAO</p>
 * @author 黄昭
 * @date 2022-09-27 15:26:05
 */
@Repository
public interface AppExternalConfigRepository extends JpaRepository<AppExternalConfig, Integer>,
        JpaSpecificationExecutor<AppExternalConfig> {

    /**
     * 单个删除AppExternalConfig
     * @author 黄昭
     */
    @Modifying
    @Query("update AppExternalConfig set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where id = ?1")
    void deleteById(Long id);

    /**
     * 批量删除AppExternalConfig
     * @author 黄昭
     */
    @Modifying
    @Query("update AppExternalConfig set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where id in ?1")
    void deleteByIdList(List<Long> idList);

    /**
     * 查询单个AppExternalConfig
     * @author 黄昭
     */
    Optional<AppExternalConfig> findByIdAndDelFlag(Long id, DeleteFlag delFlag);

}
