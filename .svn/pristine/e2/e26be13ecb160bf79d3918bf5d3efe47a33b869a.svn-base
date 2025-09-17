package com.wanmi.sbc.message.minimsgtempsetting.repository;

import com.wanmi.sbc.common.enums.TriggerNodeType;
import com.wanmi.sbc.message.minimsgtempsetting.model.root.MiniMsgTempSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * <p>小程序订阅消息模版配置表DAO</p>
 * @author xufeng
 * @date 2022-08-12 11:19:52
 */
@Repository
public interface MiniMsgTempSettingRepository extends JpaRepository<MiniMsgTempSetting, Long>,
        JpaSpecificationExecutor<MiniMsgTempSetting> {

    /**
     * 单个删除小程序订阅消息模版配置表
     * @author xufeng
     */
    @Modifying
    @Query("delete MiniMsgTempSetting where id = ?1")
    void deleteById(Long id);

    /**
     * 查询单个小程序订阅消息模版配置表
     * @author xufeng
     */
    Optional<MiniMsgTempSetting> findById(Long id);

    /**
     * 更新模板Id，同时清空修改的温馨提示
     * @author xufeng
     */
    @Modifying
    @Query("update MiniMsgTempSetting set templateId = ?1,newTips=null where tid = ?2")
    void modifyByTid(String templateId, String tid);

    /**
     * 查询单个小程序订阅消息模版配置表
     * @author xufeng
     */
    MiniMsgTempSetting findByTriggerNodeId(TriggerNodeType nodeId);

}
