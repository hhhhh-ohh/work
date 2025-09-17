package com.wanmi.sbc.customer.ledgererrorrecord.repository;

import com.wanmi.sbc.customer.ledgererrorrecord.model.root.LedgerErrorRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * <p>分账接口错误记录DAO</p>
 * @author 许云鹏
 * @date 2022-07-09 12:34:25
 */
@Repository
public interface LedgerErrorRecordRepository extends JpaRepository<LedgerErrorRecord, String>,
        JpaSpecificationExecutor<LedgerErrorRecord> {

    /**
     * 根据业务id和类型查询
     * @param businessId
     * @param type
     * @return
     */
    LedgerErrorRecord findByBusinessIdAndType(String businessId, Integer type);

    @Modifying
    @Query("update LedgerErrorRecord set state = ?2 where id = ?1")
    void modifyState(String id, Integer state);
}
