package com.wanmi.sbc.customer.repository;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.LogOutStatus;
import com.wanmi.sbc.customer.bean.enums.CheckState;
import com.wanmi.sbc.customer.bean.enums.EnterpriseCheckState;
import com.wanmi.sbc.customer.detail.model.root.CustomerDetailBase;
import com.wanmi.sbc.customer.model.root.Customer;
import com.wanmi.sbc.customer.model.root.CustomerBase;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * 会员数据层
 * Created by CHENLI on 2017/4/18.
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, String>, JpaSpecificationExecutor<Customer> {

    /**
     * 根据会员ID查询会员信息
     *
     * @param customerId
     * @return
     */
    Customer findByCustomerIdAndDelFlag(String customerId, DeleteFlag deleteFlag);

    /**
     * 批量查询会员信息
     *
     * @param idList
     * @return
     */
    List<Customer> findByCustomerIdIn(Collection<String> idList);

    /**
     * 检验账户是否存在
     *
     * @param customerAccount
     * @param deleteFlag
     * @return
     */
    Customer findByCustomerAccountAndDelFlagAndLogOutStatusIn(String customerAccount, DeleteFlag deleteFlag,
                                                            List<LogOutStatus> logOutStatuses);

    /**
     * 根据账户批量查询会员
     * @param customerAccountList 账户列表
     * @param deleteFlag 删除标识
     * @return 会员
     */
    List<Customer> findByCustomerAccountInAndDelFlagAndLogOutStatusIn(List<String> customerAccountList,
                                                                     DeleteFlag deleteFlag,
                                                                      List<LogOutStatus> logOutStatuses);

    /**
     * 查询成长值大于0的客户
     *
     * @return
     */
    @Query("select count(1) from Customer where growthValue > 0")
    Long findHasGrowthValueCustomer();

    /**
     * 审核客户状态
     *
     * @param checkState
     * @param customerId
     * @return
     */
    @Modifying
    @Query("update Customer c set c.checkState = :checkState,c.checkTime = :checkTime where c.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and c.customerId = " +
            ":customerId")
    int checkCustomerState(@Param("checkState") CheckState checkState, @Param("customerId") String customerId,@Param(
            "checkTime") LocalDateTime checkTime);

    /**
     * 审核企业会员
     *
     * @param enterpriseCheckState
     * @param customerId
     * @return
     */
    @Modifying
    @Query("update Customer c set c.enterpriseCheckState = :enterpriseCheckState, c.checkState = :checkState, " +
            "c.enterpriseCheckReason = :enterpriseCheckReason, " +
            "c.checkTime = :checkTime where c.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and c.customerId = :customerId")
    int checkEnterpriseCustomer(@Param("enterpriseCheckState") EnterpriseCheckState enterpriseCheckState,
                                @Param("checkState") CheckState checkState,
                                @Param("enterpriseCheckReason") String enterpriseCheckReason,
                                @Param("customerId") String customerId,
                                @Param("checkTime") LocalDateTime checkTime);

    /**
     * 批量删除会员
     *
     * @param customerIds
     * @return
     */
    @Modifying
    @Query("update Customer c set c.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where c.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and c.customerId in :customerIds")
    int deleteByCustomerId(@Param("customerIds") List<String> customerIds);

    /**
     * 解锁
     *
     * @param customerId
     * @return
     */
    @Modifying
    @Query("update Customer e set e.loginLockTime = null, e.loginErrorCount = 0 where e.customerId = :customerId")
    int unlockCustomer(@Param("customerId") String customerId);

    /**
     * 修改登录次数
     *
     * @param customerId
     */
    @Modifying
    @Query("update Customer e set e.loginErrorCount = IFNULL(e.loginErrorCount,0) + 1 where e.customerId = :customerId")
    int updateloginErrorCount(@Param("customerId") String customerId);


    /**
     * 新增可用积分
     * @param customerId   会员Id
     * @param pointsIncrease   增加积分的数量
     * @return
     */
    @Modifying
    @Query("update Customer e set e.pointsAvailable = IFNULL(e.pointsAvailable,0) + :pointsIncrease where e.customerId = :customerId")
    int addPointsAvailable(@Param("customerId") String customerId, @Param("pointsIncrease") Long pointsIncrease);

    /**
     * 减少可用积分
     * @param customerId   会员Id
     * @param adjustNum   减少积分的数量
     * @return
     */
    @Modifying
    @Query(value = "update customer e set e.points_available = IF(IFNULL(e.points_available,0) > :adjustNum,(IFNULL(e.points_available,0) - :adjustNum), 0)" +
            "where e.customer_id = :customerId", nativeQuery = true)
    int reducePointsAvailable(@Param("customerId") String customerId, @Param("adjustNum") Long adjustNum);

    /**
     * 新增成长值和会员级别
     * @param customerId   会员Id
     * @param growthValue   增加成长值
     * @param growthValue   增加成长值
     * @return
     */
    @Modifying
    @Query("update Customer e set e.growthValue = e.growthValue + :growthValue, e.customerLevelId = :customerLevelId, e.upgradeTime = now() where e.customerId = :customerId")
    int updateGrowthValueAndCustomerLevelId(@Param("customerId") String customerId, @Param("growthValue") Long growthValue, @Param("customerLevelId") Long customerLevelId);

    /**
     * 修改锁时间
     *
     * @param customerId customerId
     * @return
     */
    @Modifying
    @Query("update Customer e set e.loginLockTime = ?2 where e.customerId =?1")
    int updateLoginLockTime(String customerId, LocalDateTime localDateTime);

    /**
     * 修改客户登录时间
     *
     * @param customerId customerId
     * @param loginTime  loginTime
     * @param loginIp    loginIp
     * @return rows
     */
    @Modifying
    @Query("update Customer e set e.loginTime = ?2, e.loginErrorCount = 0, e.loginLockTime = null, e.loginIp = ?3 " +
            "where e.customerId =?1")
    int updateLoginTime(String customerId, LocalDateTime loginTime, String loginIp);


    /**
     * 修改绑定手机号
     *
     * @param customerId
     */
    @Modifying
    @Query("update Customer e set e.customerAccount = :customerAccount where e.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and e.customerId = " +
            ":customerId")
    int updateCustomerAccount(@Param("customerId") String customerId, @Param("customerAccount") String customerAccount);

    /**
     * 扣除会员积分
     *
     * @param customerId
     * @param points
     * @return
     */
    @Modifying
    @Query("update Customer c set c.pointsAvailable = c.pointsAvailable - :points, c.pointsUsed = c.pointsUsed + " +
            ":points where c.customerId = :customerId and c.pointsAvailable >= :points")
    int updateCustomerPoints(@Param("customerId") String customerId, @Param("points") Long points);

    /**
     * 修改会有的业务员
     *
     * @param employeeIdPre employeeIdPre
     * @param employeeId    employeeId
     * @return rows
     */
    @Modifying
    @Query("update CustomerDetail c set c.employeeId = :employeeId where c.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and c.employeeId = " +
            ":employeeIdPre")
    int updateCustomerByEmployeeId(@Param("employeeIdPre") String employeeIdPre, @Param("employeeId") String
            employeeId);

    /**
     * 查询成长值达到x的会员id列表
     *
     * @param growthValue 成长值
     * @return
     */
    @Query("select c.customerId from Customer c  where c.growthValue >= :growthValue and c.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO")
    List<String> findByGrowthValue(@Param("growthValue") Long growthValue);

    @Query(value = "select new com.wanmi.sbc.customer.model.root.CustomerBase(c.customerId,c.customerAccount) FROM " +
            "Customer c " +
            " where c.customerId = :customerId and c.delFlag = :delFlag")
    CustomerBase getBaseCustomerByCustomerId(@Param("customerId") String customerId, @Param("delFlag") DeleteFlag
            delFlag);

    /**
     * 根据会员ID查询会员等级ID
     *
     * @param customerIds
     * @return
     */
    @Query("select new com.wanmi.sbc.customer.model.root.CustomerBase(c.customerId,c.customerLevelId) FROM Customer c" +
            " where  c.customerId in :customerIds")
    List<CustomerBase> findCustomerLevelIdByCustomerIds(@Param("customerIds") List<String> customerIds);

    /**
     * 根据会员ID查询会员等级ID
     *
     * @param customerIds
     * @return
     */
    @Query("select new com.wanmi.sbc.customer.model.root.CustomerBase(c.customerId,c.customerAccount,c.logOutStatus)  FROM Customer " +
            "c where  c.customerId in :customerIds")
    List<CustomerBase> getBaseCustomerByCustomerIds(@Param("customerIds") List<String> customerIds);

    @Query("select c.customerId from Customer c  where c.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and c.checkState = 1 ")
    List<String> findCustomerIdByPageable(Pageable pageable);

    @Query("select c.customerId from Customer c  where c.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and c.checkState = 1 and c.customerLevelId in " +
            ":customerLevelIds")
    List<String> findCustomerIdByCustomerLevelIds(@Param("customerLevelIds") List<Long> customerLevelIds, Pageable
            pageable);


    /**
     * 根据会员ID查询可用积分
     * @param customerId
     * @return
     */
    @Query("select c.pointsAvailable from Customer c  where c.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and c.customerId = ?1 ")
    Long findPointsAvailableByCustomerId(String customerId);

    @Query(value = "select customer_account from customer where customer_account in (:phones) and del_flag=0 and log_out_status != 2",nativeQuery = true)
    List<String> getCustomerByPhones(@Param("phones") List<String> phones);

    /**
     * 根据会员ID查询会员成长值
     *
     * @param customerIds
     * @return
     */
    @Query("select new com.wanmi.sbc.customer.model.root.CustomerBase(c.customerId,c.customerAccount,c.growthValue,c.customerType) FROM Customer c" +
            " where  c.customerId in :customerIds")
    List<CustomerBase> findGrowthValueByCustomerIds(@Param("customerIds") List<String> customerIds);

    /**
     * 根据会员ID查询会员账号、审核状态、企业会员状态、驳回原因
     *
     * @param customerIds
     * @return
     */
    @Query("select new com.wanmi.sbc.customer.model.root.CustomerBase(c.customerId,c.customerAccount,c.customerLevelId,c.checkState,c.customerType,c.enterpriseCheckState,c.enterpriseCheckReason,c.pointsAvailable,c.logOutStatus" +
            ",c.cancellationReasonId,c.cancellationReason) " +
            " FROM Customer c" +
            " where  c.customerId in :customerIds ")
    List<CustomerBase> findCustomerBaseByCustomerIds(@Param("customerIds") List<String> customerIds);

    /***
     * 根据客户ID查询账户信息
     * @param customerId
     * @param delFlag
     * @return
     */
    @Query(value = "select new com.wanmi.sbc.customer.model.root.CustomerBase(" +
            "c.customerId,c.customerAccount,d.customerName) " +
            "FROM Customer c,CustomerDetail d " +
            "WHERE c.customerId = d.customerId AND c.delFlag = :delFlag AND d.delFlag = :delFlag " +
            "AND c.customerId = :customerId")
    CustomerBase getCustomerByCustomerId(@Param("customerId") String customerId, @Param("delFlag") DeleteFlag
            delFlag);

    /**
     * 0点执行登录次数清0操作
     *
     * @param updateTime  updateTime
     */
    @Modifying
    @Query("update Customer e set e.updateTime = ?1, e.loginErrorCount = 0 where e.loginErrorCount > 0 and e.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and e.loginLockTime is null")
    void modifyLoginErrorTime(LocalDateTime updateTime);

    /***
     * 根据员工ID查询会员信息
     * @param employeeIds
     * @param delFlag
     * @return
     */
    @Query(value = "select new com.wanmi.sbc.customer.detail.model.root.CustomerDetailBase(d.customerId,d.customerDetailId) FROM CustomerDetail d WHERE d.delFlag = :delFlag AND d.employeeId in :employeeIds")
    List<CustomerDetailBase> listCustomerByEmployeeIds(@Param("employeeIds") List<String> employeeIds, @Param("delFlag") DeleteFlag
            delFlag);

    /**
     * 客户注销状态
     *
     * @param logOutStatus
     * @param customerId
     * @return
     */
    @Modifying
    @Query("update Customer c set c.logOutStatus = :logOutStatus,c.cancellationReasonId = :cancellationReasonId,c" +
            ".cancellationReason = :cancellationReason, c.deleteTime = :deleteTime where c.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and c.customerId = " +
            ":customerId")
    int modifyLogOutStatusAndReason(@Param("logOutStatus") LogOutStatus logOutStatus,
                                    @Param("customerId") String customerId,
                                    @Param("cancellationReasonId") String cancellationReasonId,
                                    @Param("cancellationReason") String cancellationReason,
                                    @Param("deleteTime") LocalDateTime deleteTime);

    /**
     * 客户注销
     *
     * @param customerId
     * @return
     */
    @Modifying
    @Query(value = "update customer c set c.log_out_status = 2, c.delete_time = :deleteTime where c.del_flag = 0 and c.customer_id = :customerId " +
            "and c.log_out_status = 1 and c.delete_time <= :deleteTime-interval 10075 minute", nativeQuery = true)
    int modifyLogOutStatus(@Param("customerId") String customerId,
                           @Param("deleteTime") LocalDateTime deleteTime);

    /**
     * 获取注销中及注销用户
     *
     * @return
     */
    @Query("select new com.wanmi.sbc.customer.model.root.CustomerBase(c.customerId,c.customerAccount,c.logOutStatus) FROM Customer c" +
            " where  c.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and c.logOutStatus in :logoutStatus")
    List<CustomerBase> findByLogoutStatus(List<LogOutStatus> logoutStatus);

    /**
     * 按指定会员等级和指定升级时间的周X查询
     * @param customerLevelIds
     * @param upgradeTime
     * @param pageable
     * @return
     */
    @Query("select c.customerId from Customer c  where c.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and c.checkState = 1 and c.customerLevelId in " +
            ":customerLevelIds and WEEKDAY(c.upgradeTime) = :upgradeTime order by c.upgradeTime")
    List<String> findCustomerIdsByWeek(@Param("customerLevelIds") List<Long> customerLevelIds,
                                                  @Param("upgradeTime") int upgradeTime,
                                                  Pageable pageable);

    /**
     * 根据指定会员等级查询
     * @param customerLevelIds
     * @param pageable
     * @return
     */
    @Query("select c.customerId from Customer c  where c.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and c.checkState = 1 and c.customerLevelId in " +
            ":customerLevelIds order by c.upgradeTime")
    List<String> findCustomerIdsByLevelIds(@Param("customerLevelIds") List<Long> customerLevelIds,
                                       Pageable pageable);

    /**
     * 查找会员id
     * @param customerId
     * @return
     */
    @Query("select c.enterpriseCheckState from Customer c where c.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and c.checkState=1 and c.logOutStatus=0 and c.customerId=:customerId")
    EnterpriseCheckState findEnterpriseCheckStateById(@Param("customerId") String customerId);



    /**
     * 修改新人状态
     *
     * @param customerId
     * @return
     */
    @Modifying
    @Query("update Customer e set e.isNew = :isNew where e.customerId = :customerId")
    void modifyNewCustomerState(@Param("customerId") String customerId, @Param("isNew")Integer isNew);

    /**
     * 批量覆盖积分
     */
    @Modifying
    @Query("update Customer e set e.pointsAvailable = :adjustNum where e.customerId = :customerId")
    int replacePointsAvailable (@Param("adjustNum") Long adjustNum , @Param("customerId") String customerId);

    /**
     * 校验用户是否注销过
     */
    /**
     * 根据账户查询所有用户记录（包括已删除的）
     *
     * @param customerAccount 账户
     * @return 用户记录列表
     */
    @Query("select c from Customer c where c.customerAccount = :customerAccount order by c.createTime desc")
    List<Customer> findAllByCustomerAccount(@Param("customerAccount") String customerAccount);

    /**
     * 修改 降级到对应的会员等级和成长值
     * @param customerId   会员Id
     * @param growthValue   增加成长值
     * @param customerLevelId   会员等级id
     * @return
     */
    @Modifying
    @Query("update Customer e set e.growthValue = :growthValue, e.customerLevelId = :customerLevelId, e.upgradeTime = now() where e.customerId = :customerId")
    int downCustomerLevel(@Param("customerId") String customerId, @Param("growthValue") Long growthValue, @Param("customerLevelId") Long customerLevelId);

    /**
     * 更新会员等级有效期
     *
     * @param customerId
     * @param expireTime
     */
    @Modifying
    @Query("update Customer e set e.membershipExpiredTime = :expireTime where e.customerId = :customerId")
    void updateMemberShipExpiredTime(String customerId, LocalDateTime expireTime);

    /**
     * 查询会员过期时间在指定时间段内的会员
     *
     * @param startTime
     * @param endTime
     */
    @Query("select c.customerId from Customer c where c.membershipExpiredTime between :startTime and :endTime")
    List<String> CustomerIdsByMembershipExpiredTime(LocalDateTime startTime, LocalDateTime endTime);
}
