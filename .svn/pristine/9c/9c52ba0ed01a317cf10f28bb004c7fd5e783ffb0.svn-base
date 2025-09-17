package com.wanmi.sbc.empower.logisticslog.repository;

import com.wanmi.sbc.empower.logisticslog.model.root.LogisticsLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.wanmi.sbc.common.enums.DeleteFlag;
import java.util.Optional;
import java.util.List;

/**
 * <p>物流记录DAO</p>
 * @author 宋汉林
 * @date 2021-04-13 17:21:25
 */
@Repository
public interface LogisticsLogRepository extends JpaRepository<LogisticsLog, String>,
        JpaSpecificationExecutor<LogisticsLog> {

    /**
     * 单个删除物流记录
     * @author 宋汉林
     */
    @Modifying
    @Query("update LogisticsLog set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where id = ?1")
    void deleteById(String id);

    Optional<LogisticsLog> findByIdAndStoreIdAndDelFlag(String id, Long storeId, DeleteFlag delFlag);

    Optional<LogisticsLog> findByIdAndDelFlag(String id, DeleteFlag delFlag);

    @Query(value = "select * from logistics_log where order_no=?1 limit 1", nativeQuery = true)
    LogisticsLog findByOrderNo(String orderNo);
}
