package com.wanmi.sbc.marketing.drawrecord.repository;

import com.wanmi.sbc.marketing.drawrecord.model.root.DrawRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * <p>抽奖记录表DAO</p>
 * @author wwc
 * @date 2021-04-12 16:15:21
 */
@Repository
public interface DrawRecordRepository extends JpaRepository<DrawRecord, Long>,
        JpaSpecificationExecutor<DrawRecord> {

    /**
     * 导入抽奖中奖发货信息
     * @param drawRecord
     */
    @Modifying
    @Query(value = "update DrawRecord dr set dr.logisticsCompany = :#{#drawRecord.logisticsCompany}, dr.logisticsCode = :#{#drawRecord.logisticsCode}," +
            "dr.logisticsNo = :#{#drawRecord.logisticsNo}, dr.deliveryTime = :#{#drawRecord.deliveryTime}, dr.updatePerson = :#{#drawRecord.updatePerson}, dr.updateTime = :#{#drawRecord.updateTime}," +
            "dr.deliverStatus = :#{#drawRecord.deliverStatus} where dr.drawRecordCode = :#{#drawRecord.drawRecordCode}")
    void updateDeliverStatusByDrawRecordCodes(@Param("drawRecord") DrawRecord drawRecord);

}
