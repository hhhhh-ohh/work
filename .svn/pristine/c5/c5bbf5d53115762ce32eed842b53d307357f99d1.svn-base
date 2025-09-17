package com.wanmi.sbc.message.messagesend.repository;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.message.bean.enums.SendStatus;
import com.wanmi.sbc.message.messagesend.model.root.MessageSend;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * <p>站内信任务表DAO</p>
 * @author xuyunpeng
 * @date 2020-01-06 11:12:11
 */
@Repository
public interface MessageSendRepository extends JpaRepository<MessageSend, Long>,
        JpaSpecificationExecutor<MessageSend> {

    /**
     * 单个删除站内信任务表
     * @author xuyunpeng
     */
    @Modifying
    @Query("update MessageSend set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where messageId = ?1")
    void deleteById(Long messageId);

    Optional<MessageSend> findByMessageIdAndDelFlag(Long id, DeleteFlag delFlag);

    @Modifying
    @Query("update MessageSend set sendSum = sendSum + ?1 where messageId = ?2")
    void addSendSum(Integer sendSum, Long messageId);

    @Modifying
    @Query("update MessageSend set openSum = openSum + 1 where messageId = ?1")
    void addOpenSum(Long messageId);

    MessageSend findByPushId(String pushId);

    @Modifying
    @Query("update MessageSend set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where pushId = ?1")
    void deleteByPushId(String pushId);

    @Modifying
    @Query("update MessageSend set status = ?1 where messageId = ?2")
    void updateSendStatus(SendStatus staus, Long messageId);

}
