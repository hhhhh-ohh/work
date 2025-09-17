package com.wanmi.sbc.order.pickupcoderecord.repository;

import com.wanmi.sbc.order.pickupcoderecord.model.root.PickupCodeRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.wanmi.sbc.common.enums.DeleteFlag;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;

/**
 * <p>提货码记录DAO</p>
 * @author 吕振伟
 * @date 2023-04-19 14:03:52
 */
@Repository
public interface PickupCodeRecordRepository extends JpaRepository<PickupCodeRecord, Long>,
        JpaSpecificationExecutor<PickupCodeRecord> {

    /**
     * 单个删除提货码记录
     * @author 吕振伟
     */
    @Modifying
    @Query("delete from PickupCodeRecord where createTime < ?1")
    void deleteExpirePickupCodeRecord(LocalDateTime delDate);

}
