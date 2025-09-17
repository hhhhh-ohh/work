package com.wanmi.sbc.message.minimsgsetting.repository;

import com.wanmi.sbc.common.enums.ProgramNodeType;
import com.wanmi.sbc.message.minimsgsetting.model.root.MiniMsgSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * <p>小程序订阅消息配置表DAO</p>
 * @author xufeng
 * @date 2022-08-08 11:37:13
 */
@Repository
public interface MiniMsgSettingRepository extends JpaRepository<MiniMsgSetting, Long>,
        JpaSpecificationExecutor<MiniMsgSetting> {

    /**
     * 单个删除小程序订阅消息配置表
     * @author xufeng
     */
    @Modifying
    @Query("delete MiniMsgSetting where id = ?1")
    void deleteById(Long id);

    /**
     * 查询单个小程序订阅消息配置表
     * @author xufeng
     */
    Optional<MiniMsgSetting> findById(Long id);

    /**
     * 查询单个小程序订阅消息配置表
     * @author xufeng
     */
    MiniMsgSetting findByNodeId(ProgramNodeType nodeId);

}
