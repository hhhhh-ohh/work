package com.wanmi.sbc.message.pushsend.repository;

import com.wanmi.sbc.message.pushsend.model.root.PushSend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * <p>会员推送信息DAO</p>
 * @author Bob
 * @date 2020-01-08 17:15:32
 */
@Repository
public interface PushSendRepository extends JpaRepository<PushSend, Long>,
        JpaSpecificationExecutor<PushSend> {

    /**
     * 单个删除会员推送信息
     * @author Bob
     */
    @Modifying
    @Query("update PushSend set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where id = ?1")
    void deleteById(Long id);

}
