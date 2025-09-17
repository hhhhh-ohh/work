package com.wanmi.sbc.goods.priceadjustmentrecord.repository;

import com.wanmi.sbc.goods.priceadjustmentrecord.model.root.PriceAdjustmentRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>调价记录表DAO</p>
 *
 * @author chenli
 * @date 2020-12-09 19:57:21
 */
@Repository
public interface PriceAdjustmentRecordRepository extends JpaRepository<PriceAdjustmentRecord, String>,
        JpaSpecificationExecutor<PriceAdjustmentRecord> {

    /**
     * 单个查询调价记录表
     *
     * @param id
     * @param storeId
     * @return
     */
    PriceAdjustmentRecord findByIdAndStoreId(String id, Long storeId);


    @Modifying
    @Query(value = "delete from PriceAdjustmentRecord r where r.id in ?1 and r.confirmFlag = com.wanmi.sbc.common.enums.DefaultFlag.NO")
    void deleteByIds(List<String> ids);

    @Query("select r.id from PriceAdjustmentRecord r where r.createTime < ?1 and r.confirmFlag = com.wanmi.sbc.common.enums.DefaultFlag.NO")
    List<String> findByTime(LocalDateTime time);

    /**
     * 查询调价记录表指定时间内生效的调价单
     *
     * @param effectiveTimeBegin 生效开始时间
     * @param effectiveTimeEnd 生效结束时间
     * @return List<PriceAdjustmentRecord>
     */
    @Query(value = "SELECT record.* FROM price_adjustment_record record\n"
            + "LEFT JOIN price_adjustment_record_detail detail\n"
            + "ON record.id = detail.price_adjustment_no\n"
            + "WHERE record.confirm_flag = 1\n"
            + "AND (record.scan_type IS NULL OR record.scan_type = 0)\n"
            + "AND record.effective_time >= :effectiveTimeBegin AND record.effective_time <= :effectiveTimeEnd\n"
            + "AND detail.adjust_result = 0"
            , nativeQuery = true)
    List<PriceAdjustmentRecord> findWillEffectiveList(@Param("effectiveTimeBegin") LocalDateTime effectiveTimeBegin,
                                                      @Param("effectiveTimeEnd") LocalDateTime effectiveTimeEnd);

    /**
     * 根据调价单ids更新其扫描标识为已扫描
     * @param ids 调价单ids
     */
    @Modifying
    @Query(value = "UPDATE price_adjustment_record r SET r.scan_type = 1 WHERE r.id IN :ids", nativeQuery = true)
    int modifyScanType(@Param("ids") List<String> ids);

    /**
     * 确认改价记录
     *
     * @param id
     * @param storeId
     */
    @Modifying
    @Query("update PriceAdjustmentRecord a set a.confirmFlag = com.wanmi.sbc.common.enums.DefaultFlag.YES,a.effectiveTime=?3 where a.id = ?1 and a.storeId=?2")
    void confirmAdjustRecord(String id, Long storeId, LocalDateTime effectiveTime);

    @Modifying
    @Query("update PriceAdjustmentRecord set goodsNum = goodsNum - 1 where id = ?1")
    void reduceGoodsNum(String id);

}
