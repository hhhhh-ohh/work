package com.wanmi.sbc.empower.logisticssetting.repository;

import com.wanmi.sbc.empower.bean.enums.LogisticsType;
import com.wanmi.sbc.empower.logisticssetting.model.root.LogisticsSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.wanmi.sbc.common.enums.DeleteFlag;

import java.util.Optional;
import java.util.List;

/**
 * <p>物流配置DAO</p>
 *
 * @author 宋汉林
 * @date 2021-04-01 11:23:29
 */
@Repository
public interface LogisticsSettingRepository extends JpaRepository<LogisticsSetting, Long>,
        JpaSpecificationExecutor<LogisticsSetting> {

    /**
     * 单个删除物流配置
     *
     * @author 宋汉林
     */
    @Modifying
    @Query("update LogisticsSetting set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where id = ?1")
    @Override
    void deleteById(Long id);

    Optional<LogisticsSetting> findByIdAndDelFlag(Long id, DeleteFlag delFlag);

    Optional<LogisticsSetting> findByLogisticsTypeAndDelFlag(LogisticsType logisticsType, DeleteFlag delFlag);

}
