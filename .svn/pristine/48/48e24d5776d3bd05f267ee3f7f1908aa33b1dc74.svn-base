package com.wanmi.sbc.message.pushsendnode.repository;

import com.wanmi.sbc.message.pushsendnode.model.root.PushSendNode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>会员推送通知节点DAO</p>
 * @author Bob
 * @date 2020-01-13 10:47:41
 */
@Repository
public interface PushSendNodeRepository extends JpaRepository<PushSendNode, Long>,
        JpaSpecificationExecutor<PushSendNode> {

    /**
     * 单个删除会员推送通知节点
     * @author Bob
     */
    @Modifying
    @Query("update PushSendNode set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where id = ?1")
    void deleteById(Long id);

    /**
     * 修改启用开关
     * @author Bob
     */
    @Modifying
    @Query("update PushSendNode set status = :status where id = :id")
    void updateStatus(@Param("status") int status, @Param("id") Long id);

    @Modifying
    @Query("update PushSendNode set expectedSendCount = coalesce(expectedSendCount, 0)  + 1 where id = :id")
    int updateForCount(@Param("id") long id);

    @Query("from PushSendNode where nodeType =?1 and nodeCode = ?2")
    PushSendNode findByTypeAndCode(int nodeType, String nodeCode);

    @Modifying
    @Query("update PushSendNode set nodeTitle = :nodeTitle, nodeContext = :nodeContext  where id = :id")
    int modify(@Param("nodeTitle") String nodeTitle, @Param("nodeContext") String nodeContext, @Param("id") long id);

    @Query(value = "select count(id) from push_send_node where node_type = ?1 and del_flag = 0 and status = 1",
            nativeQuery = true)
    int queryOpenNum(int nodeType);


}
