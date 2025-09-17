package com.wanmi.sbc.empower.minimsgcustomerrecord.repository;

import com.wanmi.sbc.common.enums.TriggerNodeType;
import com.wanmi.sbc.empower.minimsgcustomerrecord.model.root.MiniMsgCustomerRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * <p>客户订阅消息信息表DAO</p>
 * @author xufeng
 * @date 2022-08-12 10:26:16
 */
@Repository
public interface MiniMsgCustomerRecordRepository extends JpaRepository<MiniMsgCustomerRecord, Long>,
        JpaSpecificationExecutor<MiniMsgCustomerRecord> {

    /**
     * 单个删除客户订阅消息信息表
     * @author xufeng
     */
    @Modifying
    @Query("delete MiniMsgCustomerRecord where id = ?1")
    void deleteById(Long id);

    /**
     * 批量删除客户订阅消息信息表
     * @author xufeng
     */
    @Modifying
    @Query("delete MiniMsgCustomerRecord where id in ?1")
    void deleteByIdList(List<Long> idList);

    /**
     * 查询单个客户订阅消息信息表
     * @author xufeng
     */
    Optional<MiniMsgCustomerRecord> findById(Long id);

    /**
     * 查询可推送人数
     *
     * @param triggerNodeId
     * @return
     */
    @Query("select count(distinct i.customerId) from MiniMsgCustomerRecord i where " +
            "i.triggerNodeId = :triggerNodeId and i.messageActivityId = -1 ")
    Long countRecordsByTriggerNodeId(@Param("triggerNodeId") TriggerNodeType triggerNodeId);

    /**
     * 批量修改
     * @author xufeng
     */
    @Modifying
    @Query("update MiniMsgCustomerRecord set messageActivityId =?1 where id in ?2 ")
    void updateActivityIdByIdList(Long activityId, List<Long> idList);


    /**
     * 批量修改
     * @author xufeng
     */
    @Modifying
    @Query("update MiniMsgCustomerRecord set errCode =?1,errMsg =?2,sendFlag=1 where " +
            "messageActivityId = ?3 and openId = ?4")
    void updateByActivityIdAndOpenId(String errCode, String errMsg, Long activityId, String openId);

    /**
     * 修改
     * @author xufeng
     */
    @Modifying
    @Query("update MiniMsgCustomerRecord set errCode =?1,errMsg =?2,sendFlag=1 where id = ?3")
    void updateById(String errCode, String errMsg, Long id);

    /**
     * 查询实际推送人数
     *
     * @param messageActivityId
     * @return
     */
    @Query("select count(1) from MiniMsgCustomerRecord i where " +
            "i.messageActivityId = :messageActivityId ")
    Long countRecordsByActivityId(@Param("messageActivityId") Long messageActivityId);

    /**
     * 查询实际推送成功人数
     *
     * @param messageActivityId
     * @return
     */
    @Query("select count(1) from MiniMsgCustomerRecord i where " +
            "i.messageActivityId = :messageActivityId and i.errCode = 0 ")
    Long countRecordsByActivityIdAndErrCode(@Param("messageActivityId") Long messageActivityId);

    /**
     * 批量修改
     * @author xufeng
     */
    @Modifying
    @Query("update MiniMsgCustomerRecord set messageActivityId =-1 where messageActivityId = ?1 ")
    void updateByActivityId(Long messageActivityId);

}
