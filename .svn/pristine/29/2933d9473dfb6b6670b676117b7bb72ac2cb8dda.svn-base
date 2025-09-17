package com.wanmi.sbc.message.messagesendnode.repository;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.message.bean.enums.NodeType;
import com.wanmi.sbc.message.bean.enums.SwitchFlag;
import com.wanmi.sbc.message.messagesendnode.model.root.MessageSendNode;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * <p>站内信通知节点表DAO</p>
 * @author xuyunpeng
 * @date 2020-01-09 11:45:53
 */
@Repository
public interface MessageSendNodeRepository extends JpaRepository<MessageSendNode, Long>,
        JpaSpecificationExecutor<MessageSendNode> {

    /**
     * 单个删除站内信通知节点表
     * @author xuyunpeng
     */
    @Modifying
    @Query("update MessageSendNode set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where id = ?1")
    void deleteById(Long id);

    Optional<MessageSendNode> findByIdAndDelFlag(Long id, DeleteFlag delFlag);

    @Modifying
    @Query("update MessageSendNode set status = ?1 where id = ?2")
    void updateStatus(SwitchFlag status, Long id);

    @Modifying
    @Query("update MessageSendNode set sendSum = sendSum + 1 where id = ?1")
    void addSendSum(Long id);

    @Modifying
    @Query("update MessageSendNode set openSum = openSum + 1 where id = ?1")
    void addOpenSum(Long id);

    MessageSendNode findByNodeTypeAndNodeCode(NodeType nodeType, String nodeCode);

}
