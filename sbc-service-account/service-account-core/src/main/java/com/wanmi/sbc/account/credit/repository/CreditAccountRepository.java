package com.wanmi.sbc.account.credit.repository;

import com.wanmi.sbc.account.credit.model.root.CustomerCreditAccount;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @author houshuai
 * @date 2021/2/27 11:42
 * @description
 *     <p>授信账户DO
 */
@Repository
public interface CreditAccountRepository
        extends JpaRepository<CustomerCreditAccount, String>,
                JpaSpecificationExecutor<CustomerCreditAccount> {

    /**
     * 根据客户ID和删除标识查询出
     *
     * @param customerId 客户ID
     * @param deleteFlag 删除标志
     * @return {@link CustomerCreditAccount}
     */
    @Query("from CustomerCreditAccount where customerId = :customerId and delFlag = :deleteFlag")
    Optional<CustomerCreditAccount> findByCustomerIdAndDelFlag(
            @Param("customerId") String customerId, @Param("deleteFlag") DeleteFlag deleteFlag);

    /**
     * 用户详情
     *
     * @param id 主键
     * @param deleteFlag 删除标志
     * @return {@link CustomerCreditAccount}
     */
    Optional<CustomerCreditAccount> findCustomerCreditAccountByIdAndDelFlag(
            String id, DeleteFlag deleteFlag);

    /**
     * * 根据申请记录ID查询授信账户
     *
     * @param applyRecordId
     * @return
     */
    @Query("from CustomerCreditAccount where applyRecordId = ?1 or changeRecordId = ?1")
    CustomerCreditAccount findByApplyRecordId(String applyRecordId);

    /**
     * 额度变更
     *
     * @param customerId
     * @param amount
     */
    @Modifying
    @Query(
            "UPDATE CustomerCreditAccount SET "
                    + "repayAmount = IFNULL(repayAmount, 0) + :amount,"
                    + "usableAmount = IFNULL(usableAmount, 0) - :amount,"
                    + "usedAmount = IFNULL(usedAmount, 0) + :amount,"
                    + "usedStatus = com.wanmi.sbc.common.enums.BoolFlag.YES "
                    + "WHERE "
                    + "customerId = :customerId AND "
                    + "delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO AND "
                    + "IFNULL(usableAmount, 0) - :amount >= 0")
    int updateCustomerCreditAccount(
            @Param("customerId") String customerId, @Param("amount") BigDecimal amount);

    /**
     * * 恢复账户已使用余额 usedAmount 已使用额度减去订单金额 usableAmount 可使用额度加上订单金额 repayAmount 需还款金额加上订单金额
     *
     * @param amount
     * @param id
     */
    @Modifying
    @Query(
            "update CustomerCreditAccount set usableAmount = usableAmount + :amount,"
                    + "repayAmount = repayAmount- :amount "
                    + "where id = :id and repayAmount- :amount >= 0")
    int restoreCreditAmount(@Param("amount") BigDecimal amount, @Param("id") String id);

    /**
     * 新周期额度恢复
     *
     * @param account
     * @param now
     * @return
     */
    @Modifying
    @Query(
            "UPDATE CustomerCreditAccount "
                    +
                    // 变更恢复次数
                    "SET creditNum = :#{#account.creditNum},"
                    +
                    // 变更已还款金额
                    "hasRepaidAmount = :#{#account.hasRepaidAmount},"
                    +
                    // 变更已用金额
                    "usedAmount = :#{#account.usedAmount},"
                    +
                    // 变更可用金额
                    "usableAmount = :#{#account.usableAmount},"
                    +
                    // 变更周期开始时间
                    "startTime = :#{#account.startTime},"
                    +
                    // 变更周期结束时间
                    "endTime = :#{#account.endTime},"
                    +
                    // 变更使用状态
                    "usedStatus = :#{#account.usedStatus} "
                    + "WHERE repayAmount = 0.00 "
                    + "AND delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO "
                    + "AND endTime < :#{#now} "
                    + "AND customerId = :#{#account.customerId} ")
    int updateCreditAccountInfo(
            @Param("account") CustomerCreditAccount account, @Param("now") LocalDateTime now);

    /**
     * 变更授信账户 授信记录id为授信记录表中新周期id
     *
     * @param id
     * @param customerId
     */
    @Modifying
    @Query(
            "UPDATE CustomerCreditAccount "
                    +
                    // 变更恢复次数
                    "SET creditRecordId = :id "
                    + "WHERE customerId = :customerId")
    void updateCreditAccountRecordId(
            @Param("id") String id, @Param("customerId") String customerId);

    /**
     * 根据会员id判断是否存在
     *
     * @param customerId
     * @param deleteFlag
     * @return
     */
    boolean existsByCustomerIdAndDelFlag(String customerId, DeleteFlag deleteFlag);

    /**
     * 在线还款修改授信账户数据
     *
     * @param customerId 会员id
     * @param amount 变更金额
     * @return 更新记录条数
     */
    @Modifying
    @Query(
            "update CustomerCreditAccount set repayAmount = repayAmount-?2, "
                    + "hasRepaidAmount = hasRepaidAmount+?2, "
                    + "usableAmount = usableAmount+?2 "
                    + "where customerId=?1")
    int repayUpdateCreditAccount(String customerId, BigDecimal amount);

    /**
     * 修改扣减
     *
     * @param customerId 会员id
     * @param expiredChangeFlag 变更状态
     * @return 更新记录条数
     */
    @Modifying
    @Query(
            "update CustomerCreditAccount set expiredChangeFlag = ?2 "
                    + "where customerId=?1")
    int updateCreditAccountFlag(String customerId, BoolFlag expiredChangeFlag);

    /**
     * 查询授信账户总数
     *
     * @return
     */
    @Query(
            "select count(1) from CustomerCreditAccount where delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and creditAmount is not null")
    Long sumCreditAccount();

    /**
     * 查询所有账户的授信待还款
     *
     * @return
     */
    @Query(
            "select sum(repayAmount) from CustomerCreditAccount where delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and creditAmount is not null")
    BigDecimal sumCreditRepayAmount();

    /**
     * 查询所有账户的授信可用额度
     *
     * @return
     */
    @Query(
            "select sum(usableAmount) from CustomerCreditAccount where delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and creditAmount is not null and startTime < now() and endTime > now()")
    BigDecimal sumCreditUsableAmount();
}
