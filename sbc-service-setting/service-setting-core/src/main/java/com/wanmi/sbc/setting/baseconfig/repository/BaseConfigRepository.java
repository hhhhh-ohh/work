package com.wanmi.sbc.setting.baseconfig.repository;

import com.wanmi.sbc.setting.api.request.baseconfig.BaseConfigUpdateRequest;
import com.wanmi.sbc.setting.baseconfig.model.root.BaseConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * <p>基本设置DAO</p>
 * @author lq
 * @date 2019-11-05 16:08:31
 */
@Repository
public interface BaseConfigRepository extends JpaRepository<BaseConfig, Integer>,
        JpaSpecificationExecutor<BaseConfig> {

    /**
     * 修改注册注销协议
     * @param request
     */
    @Modifying
    @Query("update BaseConfig config " +
            "set config.registerContent = :#{#request.registerContent}," +
            "config.cancellationContent = :#{#request.cancellationContent}," +
            "config.payingMemberContent = :#{#request.payingMemberContent} " +
            "where config.baseConfigId = :#{#request.baseConfigId}")
    void modifyAgreement(@Param("request") BaseConfigUpdateRequest request);
}
