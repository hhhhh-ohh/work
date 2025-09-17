package com.wanmi.sbc.setting.payadvertisement.repository;

import com.wanmi.sbc.setting.payadvertisement.model.root.PayAdvertisement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.wanmi.sbc.common.enums.DeleteFlag;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;

/**
 * <p>支付广告页配置DAO</p>
 * @author 黄昭
 * @date 2022-04-06 10:03:54
 */
@Repository
public interface PayAdvertisementRepository extends JpaRepository<PayAdvertisement, Long>,
        JpaSpecificationExecutor<PayAdvertisement> {

    /**
     * 查询单个支付广告页配置
     * @author 黄昭
     */
    Optional<PayAdvertisement> findByIdAndDelFlag(Long id, DeleteFlag delFlag);

    /**
     * 校验同时间段同店铺是否存在支付广告页
     * @param storeType
     * @param storeIds
     * @param startTime
     * @param endTime
     */
    @Query(value = "SELECT count(1) FROM pay_advertisement p " +
            "LEFT JOIN pay_advertisement_store s " +
            "ON p.id = s.pay_advertisement_id " +
            "WHERE p.del_flag = 0 " +
            "AND IF(:storeType = 1,1=1,s.store_id IN (:storeIds)) " +
            "AND NOT(p.start_time > :endTime OR p.end_time < :startTime)"
            ,nativeQuery = true)
    int findSameTimeAdvertisement(@Param("storeType") Integer storeType,@Param("storeIds") List<Long> storeIds
            ,@Param("startTime") LocalDateTime startTime,@Param("endTime") LocalDateTime endTime);

    /**
     * 校验同时间段同店铺是否存在支付广告页
     * @param storeType
     * @param storeIds
     * @param startTime
     * @param endTime
     */
    @Query(value = "SELECT count(1) FROM pay_advertisement p " +
            "LEFT JOIN pay_advertisement_store s " +
            "ON p.id = s.pay_advertisement_id " +
            "WHERE p.del_flag = 0 " +
            "AND IF(:storeType = 1,1=1,s.store_id IN (:storeIds)) " +
            "AND NOT(p.start_time > :endTime OR p.end_time < :startTime) " +
            "AND p.id != :id AND p.end_time > NOW()"
            ,nativeQuery = true)
    int findSameTimeAdvertisement(@Param("id") Long id,@Param("storeType") Integer storeType,@Param("storeIds") List<Long> storeIds
            ,@Param("startTime") LocalDateTime startTime,@Param("endTime") LocalDateTime endTime);
}
