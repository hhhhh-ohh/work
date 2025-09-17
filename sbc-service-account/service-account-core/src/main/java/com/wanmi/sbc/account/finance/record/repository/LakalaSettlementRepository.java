package com.wanmi.sbc.account.finance.record.repository;

import com.wanmi.sbc.account.finance.record.model.entity.LakalaSettlement;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * @description
 * @author edz
 * @date 2022/7/18 17:39
 */
@Repository
public interface LakalaSettlementRepository
        extends JpaRepository<LakalaSettlement, Long>, JpaSpecificationExecutor<LakalaSettlement> {

    LakalaSettlement findByStoreIdAndStartTimeAndEndTime(Long storeId, String startTime, String endTime);

    LakalaSettlement findByStoreIdAndStartTimeAndEndTimeAndSupplierStoreId(Long storeId, String startTime, String endTime,Long supplierStoreId);

    @Query(
            value =
                    " SELECT * FROM lakala_settlement WHERE  store_id = :storeId and date_format(create_time, '%Y-%m-%d') != CURRENT_DATE order by end_time desc LIMIT 1 ",
            nativeQuery = true)
    Optional<LakalaSettlement> getLastSettlementByStoreId(@Param("storeId") Long storeId);

    @Query(" select settleId  from LakalaSettlement  ")
    List<Long> listByPage(Pageable pageable);

    @Query(" select storeId  from LakalaSettlement  where settleId in (:settleIdList) ")
    List<Long> getStoreIdsByIds(@Param("settleIdList") List<Long> settleIdList);

    List<LakalaSettlement> findBySettleUuidIn(Collection<String> settleUuid);
}
