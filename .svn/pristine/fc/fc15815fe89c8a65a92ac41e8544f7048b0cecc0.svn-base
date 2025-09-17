package com.wanmi.sbc.customer.payingmembercustomerrel.repository;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.customer.payingmembercustomerrel.model.root.PayingMemberCustomerRel;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * <p>客户与付费会员等级关联表DAO</p>
 * @author zhanghao
 * @date 2022-05-13 13:40:48
 */
@Repository
public interface PayingMemberCustomerRelRepository extends JpaRepository<PayingMemberCustomerRel, Long>,
        JpaSpecificationExecutor<PayingMemberCustomerRel> {

    /**
     * 单个删除客户与付费会员等级关联表
     * @author zhanghao
     */
    @Modifying
    @Query("update PayingMemberCustomerRel set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where id = ?1")
    void deleteById(Long id);

    /**
     * 批量删除客户与付费会员等级关联表
     * @author zhanghao
     */
    @Modifying
    @Query("update PayingMemberCustomerRel set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where id in ?1")
    void deleteByIdList(List<Long> idList);

    /**
     * 查询单个客户与付费会员等级关联表
     * @author zhanghao
     */
    Optional<PayingMemberCustomerRel> findByIdAndDelFlag(Long id, DeleteFlag delFlag);

    /**
     * 查询单个客户与付费会员等级关联表
     * @author zhanghao
     */
    Optional<PayingMemberCustomerRel> findByCustomerIdAndDelFlag(String customerId, DeleteFlag delFlag);

    /**
     * 查询有效期内的会员数据
     * @param customerId
     * @param now
     * @return
     */
    @Query("from PayingMemberCustomerRel where delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and customerId = ?1 and expirationDate >= ?2")
    List<PayingMemberCustomerRel> findByCustomer(String customerId, LocalDate now);


    /**
     * 更新过期时间
     * @author zhanghao
     */
    @Modifying
    @Query("update PayingMemberCustomerRel set expirationDate = ?1 where id = ?2")
    void updateExpirationDate(LocalDate expirationDate, Long id);


    /**
     * 更新过期时间跟开通时间
     * @author zhanghao
     */
    @Modifying
    @Query("update PayingMemberCustomerRel set expirationDate = ?1, openTime = ?2 where id = ?3")
    void updateExpirationDateAndOpenTime(LocalDate expirationDate, LocalDateTime openTime, Long id);

    @Modifying
    @Query("update PayingMemberCustomerRel set discountAmount = discountAmount + ?3 where customerId = ?1 and levelId = ?2")
    void updateCustomerDiscount(String customerId, Integer levelId, BigDecimal discount);

    @Query("from PayingMemberCustomerRel where delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and customerId = ?1 and levelId = ?2")
    PayingMemberCustomerRel findDiscountByLevelId(String customerId, Integer levelId);

    @Query(value = "select  expiration_date  from paying_member_customer_rel " +
            "where level_id = ?1 " +
            "and customer_id = ?2 and del_flag= 0",nativeQuery = true)
    LocalDate findMostExpirationDate(Integer levelId,String customerId);

    @Query(value = "select level_name  from paying_member_level " +
            "where level_id = ?1 and del_flag= 0 ",nativeQuery = true)
    String findMostLevelName(Integer levelId);

    @Query(value = "select max(level_id) as level_id from paying_member_customer_rel where customer_id = ?1 and expiration_date >= ?2 and del_flag= 0" ,nativeQuery = true)
    Integer findMostLevelId(String customerId,LocalDate now);

    @Query(value = "select customerId from PayingMemberCustomerRel where delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and expirationDate >= ?2 and WEEKDAY(openTime) = WEEKDAY(?2) and customerId in ?1 ")
    List<String> findActiveCustomerIdsByWeek(List<String> customerIds, LocalDate date);

    @Query(value = "select customerId from PayingMemberCustomerRel where expirationDate < now() and delFlag= 0")
    List<String> findExpirationList();

    @Query(value =" update PayingMemberCustomerRel set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where customerId = ?1")
    Integer deleteByCustomerId(String customerId);
}
