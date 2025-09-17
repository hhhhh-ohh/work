package com.wanmi.sbc.message.minimsgrecord.repository;

import com.wanmi.sbc.message.minimsgrecord.model.root.MiniMsgRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * <p>小程序订阅消息配置表DAO</p>
 * @author xufeng
 * @date 2022-08-08 16:51:37
 */
@Repository
public interface MiniMsgRecordRepository extends JpaRepository<MiniMsgRecord, Long>,
        JpaSpecificationExecutor<MiniMsgRecord> {

    /**
     * 查询单个小程序订阅消息配置表
     * @author xufeng
     */
    Optional<MiniMsgRecord> findById(Long id);

    /**
     * 查询单个小程序订阅消息配置表
     * @author xufeng
     */
    MiniMsgRecord findByCustomerId(String customerId);

}
