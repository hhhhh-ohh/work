package com.wanmi.sbc.setting.openapisetting.repository;

import com.wanmi.sbc.common.enums.AuditStatus;
import com.wanmi.sbc.common.enums.EnableStatus;
import com.wanmi.sbc.setting.openapisetting.model.root.OpenApiSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.wanmi.sbc.common.enums.DeleteFlag;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;

/**
 * 开放平台api设置DAO
 *
 * @author lvzhenwei
 * @date 2021-04-12 17:00:26
 */
@Repository
public interface OpenApiSettingRepository
        extends JpaRepository<OpenApiSetting, Long>, JpaSpecificationExecutor<OpenApiSetting> {

    /**
     * 单个删除开放平台api设置
     * @author  lvzhenwei
     * @date 2021/4/14 7:22 下午
     * @param id
     * @return void
     **/
    @Override
    @Modifying
    @Query("update OpenApiSetting set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where id = ?1")
    void deleteById(Long id);

    /**
     * 开放平台权限审核
     * @author  lvzhenwei
     * @date 2021/4/14 7:22 下午
     * @param auditState
     * @param id
     * @return void
     **/
    @Modifying
    @Query("update OpenApiSetting set auditState = ?1, appKey = ?6, appSecret = ?2,limitingNum = ?3, contractEndDate = ?4 where id in ?5")
    void checkAuditState(AuditStatus auditState, String appSecret, Long limitingNum, LocalDateTime contractEndDate , Long id, String appKey);

    /**
     * 开放平台权限审核--审核驳回
     * @author  lvzhenwei
     * @date 2021/4/15 7:49 下午
     * @param auditState
     * @param auditReason
     * @param id
     * @return void
     **/
    @Modifying
    @Query("update OpenApiSetting set auditState = ?1, auditReason = ?2 where id in ?3")
    void checkAuditStateReason(AuditStatus auditState, String auditReason, Long id);

    /**
     * 开放平台权限禁用/启用
     * @author  lvzhenwei
     * @date 2021/4/14 7:22 下午
     * @param disableState
     * @param id
     * @return void
     **/
    @Modifying
    @Query("update OpenApiSetting set disableState = ?1, disableReason = ?2 where id in ?3")
    void changeDisableState(EnableStatus disableState, String disableReason, Long id);

    /**
     * 重置app secret
     * @author  lvzhenwei
     * @date 2021/4/14 4:34 下午
     * @param appSecret
     * @param id
     * @return void
     **/
    @Modifying
    @Query("update OpenApiSetting set appSecret = ?1 where id in ?2")
    void resetAppSecret(String appSecret,Long id);

    /**
     * 根据id查询开放平台api配置
     * @author  lvzhenwei
     * @date 2021/4/14 7:23 下午
     * @param id
     * @param delFlag
     * @return java.util.Optional<com.wanmi.sbc.setting.openapisetting.model.root.OpenApiSetting>
     **/
    Optional<OpenApiSetting> findByIdAndDelFlag(
            Long id, DeleteFlag delFlag);

    /**
    *  根据店铺ID查询
     * @author  wur
     * @date: 2021/4/25 11:34
     * @param storeId  店铺ID
     **/
    @Query(value = " SELECT * FROM open_api_setting WHERE del_flag = 0 and store_id = :storeId order by id desc LIMIT 1 ",
            nativeQuery = true)
    OpenApiSetting findByStoreId(@Param("storeId") Long storeId);

    @Query("from OpenApiSetting where platformType=com.wanmi.sbc.common.enums.PlatformType.BOSS and delFlag=com.wanmi.sbc.common.enums.DeleteFlag.NO")
    OpenApiSetting findBossSetting();
}
