package com.wanmi.sbc.order.payingmemberrecord.repository;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.order.payingmemberrecord.model.root.PayingMemberRecord;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * <p>付费记录表DAO</p>
 * @author zhanghao
 * @date 2022-05-13 15:27:53
 */
@Repository
public interface PayingMemberRecordRepository extends JpaRepository<PayingMemberRecord, String>,
        JpaSpecificationExecutor<PayingMemberRecord> {

    /**
     * 单个删除付费记录表
     * @author zhanghao
     */
    @Override
    @Modifying
    @Query("update PayingMemberRecord set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where recordId = ?1")
    void deleteById(String recordId);

    /**
     * 批量删除付费记录表
     * @author zhanghao
     */
    @Modifying
    @Query("update PayingMemberRecord set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where recordId in ?1")
    void deleteByIdList(List<String> recordIdList);

    /**
     * 批量更新付费记录表状态
     * @author zhanghao
     */
    @Modifying
    @Query("update PayingMemberRecord set levelState = ?1 where recordId in ?2")
    void updateState(Integer levelState,List<String> recordIdList);


    /**
     * 查询单个付费记录表
     * @author zhanghao
     */
    Optional<PayingMemberRecord> findByRecordIdAndDelFlag(String id, DeleteFlag delFlag);

    /**
     * 查询拥有指定权益、开通时间为指定周X的记录
     * @param rightsId
     * @param date
     * @param pageable
     * @return
     */
    @Query("from PayingMemberRecord where delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and levelState = 0  and find_in_set(?1, rightsIds) > 0 " +
            "and expirationDate >= ?2 order by createTime")
    List<PayingMemberRecord> findByRightsWeek(Integer rightsId, LocalDate date, Pageable pageable);

    /**
     * 查询拥有指定权益、过期时间不与指定时间同月或本月月末过期的记录
     * @param rightsId
     * @param date
     * @param pageable
     * @return
     */
    @Query("from PayingMemberRecord where delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and levelState = 0 and find_in_set(?1, rightsIds) > 0 " +
            "and expirationDate > ?2 and (DATE_FORMAT(expirationDate,'%y-%m') != DATE_FORMAT(?2,'%y-%m') or expirationDate = ?3) order by createTime")
    List<PayingMemberRecord> findByRightsRepeatMonth(Integer rightsId, LocalDate date, LocalDate endDate, Pageable pageable);

    /**
     * 查询生效中、拥有指定权益的记录
     * @param rightsId
     * @param date
     * @param pageable
     * @return
     */
    @Query("from PayingMemberRecord where delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and levelState = 0 and find_in_set(?1, rightsIds) > 0" +
            " and expirationDate >= ?2 order by createTime")
    List<PayingMemberRecord> findByRightsIssueMonth(Integer rightsId, LocalDate date, Pageable pageable);


    List<PayingMemberRecord> findAllByCustomerIdAndDelFlag(String customerId,DeleteFlag deleteFlag);

    List<PayingMemberRecord> findAllByCustomerIdAndLevelStateAndDelFlag(String customerId, Integer levelState,DeleteFlag deleteFlag);

    @Query("from PayingMemberRecord where delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and levelState = 0 and customerId = ?1 and levelId = ?2 and expirationDate >= CURRENT_DATE")
    PayingMemberRecord findActiveRecord(String customerId, Integer levelId);


    @Modifying
    @Query("update PayingMemberRecord set levelState = 3,returnAmount = ?1, returnCause = ?2, returnCoupon = ?3, returnPoint = ?4, expirationDate = ?5  where recordId = ?6")
    void refundPayingMember(BigDecimal returnAmount,String returnCause,Integer returnCoupon,Integer returnPoint,LocalDate expirationDate,String recordId);

    @Modifying
    @Query("update PayingMemberRecord set levelState = ?1,expirationDate = ?2  where recordId = ?3")
    void updateExpirationDateAndLevelState(Integer levelState,LocalDate expirationDate,String recordId);
}
