package com.wanmi.sbc.message.vopmessage.repository;

import com.wanmi.sbc.message.vopmessage.model.root.VopLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * <p>Vop日志DAO</p>
 * @author xufeng
 * @date 2022-05-20 15:53:00
 */
@Repository
public interface VopLogRepository extends JpaRepository<VopLog, String>,
        JpaSpecificationExecutor<VopLog> {

}
