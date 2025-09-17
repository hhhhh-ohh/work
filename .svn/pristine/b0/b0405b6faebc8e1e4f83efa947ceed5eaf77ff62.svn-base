package com.wanmi.sbc.message.minimsgactivitysetting.repository;

import com.wanmi.sbc.message.bean.enums.ProgramSendStatus;
import com.wanmi.sbc.message.minimsgactivitysetting.model.root.MiniMsgActivitySetting;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * <p>小程序订阅消息配置表DAO</p>
 * @author xufeng
 * @date 2022-08-11 16:16:32
 */
@Repository
public interface MiniMsgActivitySettingRepository extends JpaRepository<MiniMsgActivitySetting, Long>,
        JpaSpecificationExecutor<MiniMsgActivitySetting> {

    /**
     * 单个删除小程序订阅消息配置表
     * @author xufeng
     */
    @Modifying
    @Query("update MiniMsgActivitySetting set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where id = ?1")
    void deleteById(Long id);

    /**
     * 批量删除小程序订阅消息配置表
     * @author xufeng
     */
    @Modifying
    @Query("delete MiniMsgActivitySetting where id in ?1")
    void deleteByIdList(List<Long> idList);

    /**
     * 查询单个小程序订阅消息配置表
     * @author xufeng
     */
    Optional<MiniMsgActivitySetting> findById(Long id);

    /**
     * 更新状态
     * @author xufeng
     */
    @Modifying
    @Query("update MiniMsgActivitySetting set sendStatus = ?1, preCount = ?2, realCount =?3 where id = ?4")
    void modifyById(ProgramSendStatus sendStatus, Integer preCount, Integer realCount, Long id);

    /**
     * 更新状态
     * @author xufeng
     */
    @Modifying
    @Query("update MiniMsgActivitySetting set scanFlag = true where id in ?1")
    void modifyScanFlagByIds(List<Long> ids);

}
