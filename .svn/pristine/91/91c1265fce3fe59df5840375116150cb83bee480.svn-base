package com.wanmi.sbc.customer.employee.repository;

import com.wanmi.sbc.common.enums.AccountType;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.customer.bean.enums.AccountState;
import com.wanmi.sbc.customer.bean.vo.EmployeeSimpleVO;
import com.wanmi.sbc.customer.employee.model.root.Employee;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Created by zhangjin on 2017/4/18.
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String>, JpaSpecificationExecutor<Employee> {


    @Query("update Employee e set e.accountState = :accountState where e.employeeId in :ids")
    @Modifying
    void updateAccountStatusById(@Param("accountState") AccountState accountState, @Param("ids") List<String> ids);


    /**
     * 批量删除
     *
     * @param ids
     */
    @Query("update Employee e set e.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where e.employeeId in :ids and e.accountName != 'system'")
    @Modifying
    void deleteEmployeesByIds(@Param("ids") List<String> ids);

    /**
     * 根据id查询业务员
     *
     * @param employeeId employeeId
     * @param delFlag    delFlag
     * @return Optional<Employee>
     */
    Optional<Employee> findByEmployeeIdAndDelFlag(String employeeId, DeleteFlag delFlag);


    /**
     * 根据员工id查询员工信息(包括角色)
     *
     * @param employeeId
     * @param delFlag
     * @return
     */
    @Query(value = "  SELECT t.account_name,t.is_master_account,t.role_ids,t.account_state, t.company_info_id, " +
            "t.account_type,t.employee_mobile,t.is_employee,t.manage_department_ids,t.department_ids,t.heir_employee_id,t.version_no FROM employee t WHERE t.employee_id = ?1 AND t.del_flag = ?2", nativeQuery = true)
    Object findEmployeeInfoById(String employeeId, int delFlag);

    /**
     * 根据员工姓名查询员工是否存在
     *
     * @param employeeName employeeName
     * @param delFlag      deleteFlag
     * @return rows
     */
    int countByEmployeeNameAndDelFlagAndAccountType(String employeeName, DeleteFlag delFlag, AccountType accountType);

    /**
     * 根据账号名查询员工是否存在
     *
     * @param accountName
     * @param delFlag
     * @return
     */
    int countByAccountNameAndDelFlagAndAccountType(String accountName, DeleteFlag delFlag, AccountType accountType);

    /**
     * 查询员工手机是否存在
     *
     * @param employeeMobile
     * @param delFlag
     * @return
     */
    int countByEmployeeMobileAndDelFlagAndAccountType(String employeeMobile, DeleteFlag delFlag, AccountType
            accountType);

    /**
     * 根据手机号码及平台查询用户
     *
     * @param phone phone
     * @return Optional<Employee>
     */
    Optional<Employee> findByEmployeeMobileAndDelFlagAndAccountType(String phone, DeleteFlag deleteFlag, AccountType
            accountType);

    /**
     * 根据手机号码查询用户
     *
     * @param phone phone
     * @return Optional<Employee>
     */
    Optional<Employee> findByEmployeeMobileAndDelFlag(String phone, DeleteFlag deleteFlag);

    /**
     * 重置员工的锁定时间和登录错误次数
     *
     * @param employeeId
     * @return
     */
    @Modifying
    @Query("update Employee e set e.loginLockTime = null, e.loginErrorTime = 0 where e.employeeId = :employeeId")
    int unlockEmployee(@Param("employeeId") String employeeId);

    /**
     * 修改员工登录时间
     *
     * @param employeeId employeeId
     * @param loginTime  loginTime
     * @return rows
     */
    @Modifying
    @Query("update Employee e set e.loginTime = ?2, e.loginErrorTime = 0, e.loginLockTime = null where e.employeeId " +
            "=?1")
    int updateLoginTime(String employeeId, LocalDateTime loginTime);


    /**
     * 修改登录次数
     *
     * @param id
     * @return ros
     */
    @Modifying
    @Query("update Employee e set e.loginErrorTime = e.loginErrorTime + 1 where e.employeeId =?1")
    int updateLoginErrorTime(String id);


    /**
     * 修改锁时间
     *
     * @param employeeId employeeId
     * @return
     */
    @Modifying
    @Query("update Employee e set e.loginLockTime = ?2 where e.employeeId =?1")
    int updateLoginLockTime(String employeeId, LocalDateTime localDateTime);

    /**
     * 修改账号密码
     *
     * @param accountPassword accountPassword
     * @param employeeId      employeeId
     * @return rows
     */
    @Modifying
    @Query("update Employee e set e.accountPassword = :accountPassword, e.versionNo = :versionNo where e.employeeId = :employeeId")
    int updateAccountPassord(@Param("accountPassword") String accountPassword, @Param("versionNo") Long versionNo, @Param("employeeId") String employeeId);

    /**
     * 查询所有员工
     *
     * @param deleteFlag deleteFlag
     * @return Stream<Employee>
     */
    List<Employee> findByDelFlagAndIsEmployeeAndAccountType(DeleteFlag deleteFlag, Integer isEmployee, AccountType
            accountType);

    /**
     * 查询所有员工
     *
     * @param deleteFlag
     * @param isEmployee
     * @param companyInfoId
     * @return Stream<Employee>
     */
    List<Employee> findByDelFlagAndIsEmployeeAndCompanyInfo_CompanyInfoId(DeleteFlag deleteFlag,
                                                                                        Integer isEmployee,
                                                                                        Long companyInfoId);

    /**
     * 根据账号名查询员工
     *
     * @param accountName accountName
     * @param delFlag      delFlag
     * @return Optional<Employee>
     */
    Optional<Employee> findByAccountNameAndDelFlagAndAccountType(String accountName, DeleteFlag delFlag, AccountType
            accountType);

    /**
     * 根据账号名查询员工
     *
     * @param accountName accountName
     * @param delFlag      delFlag
     * @param accountTypes 账号类型
     * @return Optional<Employee>
     */
    @Query("from Employee c where c.delFlag = ?2 and c.accountName = ?1 and c.accountType in (?3)")
    List<Employee> findByAccountNameAndDelFlagAndAccountTypes(String accountName, DeleteFlag delFlag,
                                                                  List<AccountType> accountTypes);

    /**
     * 批量查询员工
     *
     * @param employeeIds
     * @return
     */
    @Query("from Employee c where c.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and c.employeeId in (:employeeIds)")
    List<Employee> findByEmployeeIds(@Param("employeeIds") List<String> employeeIds);

    /**
     * 查询公司下所有的账号
     *
     * @param companyInfoId
     * @param delFlag
     * @return
     */
    @Query("from Employee c where c.delFlag = :delFlag and c.companyInfo.companyInfoId = :companyInfoId")
    List<Employee> findByCompanyInfoIdAndDelFlag(@Param("companyInfoId") Long companyInfoId, @Param("delFlag")
            DeleteFlag delFlag);

    /**
     * 查询公司下的主账号
     *
     * @param companyInfoId
     * @param delFlag
     * @return
     */
    @Query("from Employee c where c.delFlag = :delFlag and c.companyInfo.companyInfoId = :companyInfoId and c" +
            ".isMasterAccount = 1")
    Employee findMainEmployee(@Param("companyInfoId") Long companyInfoId, @Param("delFlag") DeleteFlag delFlag);

    /**
     * 查询公司下的主账号
     *
     * @param companyInfoId
     * @param delFlag
     * @return
     */
    @Query("from Employee c where c.delFlag = :delFlag and c.companyInfo.companyInfoId in (:companyInfoId) and c" +
            ".isMasterAccount = 1")
    List<Employee> findMainEmployeeList(@Param("companyInfoId") List<Long> companyInfoId, @Param("delFlag") DeleteFlag delFlag);

    /**
     * 统计余额总额、可冻结金额总额、可提现金额总额
     *
     * @return
     */
    @Query(value = "select new com.wanmi.sbc.customer.bean.vo.EmployeeSimpleVO(c.employeeId,c.employeeName,c.accountName) from Employee c where c.delFlag = :delFlag and c.companyInfoId = :companyInfoId and c" +
            ".isMasterAccount = 1")
    EmployeeSimpleVO findSimpleMainEmployee(@Param("companyInfoId") Long companyInfoId, @Param("delFlag") DeleteFlag delFlag);

    /**
     * 禁用员工
     *
     * @param accountDisableReason accountDisableReason
     * @param employeeId           employeeId
     * @return rows
     */
    @Modifying
    @Query("update Employee e set e.accountDisableReason = :accountDisableReason" +
            ", e.accountState = :accountState where e.employeeId = :employeeId")
    int disableEmployee(@Param("accountDisableReason") String accountDisableReason,
                        @Param("employeeId") String employeeId,
                        @Param("accountState") AccountState accountState
    );

    /**
     * 批量禁用员工
     *
     * @param accountDisableReason accountDisableReason
     * @param employeeIds          employeeIds
     * @return rows
     */
    @Modifying
    @Query("update Employee e set e.accountDisableReason = :accountDisableReason" +
            ", e.accountState = :accountState where e.employeeId in (:employeeIds)")
    int batchDisableEmployee(@Param("accountDisableReason") String accountDisableReason,
                             @Param("employeeIds") List<String> employeeIds,
                             @Param("accountState") AccountState accountState
    );

    /**
     * 批量设为业务员
     * @param ids
     * @return
     */
    @Modifying
    @Query("update Employee set isEmployee = 0 where employeeId in (?1)")
    int batchSetEmployee(List<String> ids);

    /**
     * 批量调整部门
     * @param departmentIds
     * @param ids
     * @return
     */
    @Modifying
    @Query("update Employee set departmentIds = ?1 where employeeId in (?2)")
    int changeDepartment(String departmentIds, List<String> ids);

    @Modifying
    @Query("update Employee set becomeMember = 1 where employeeId in (?1)")
    int activateAccount(List<String> employeeIds);

    /**
     * 批量查询员工
     *
     * @param employeeIds
     * @return
     */
    List<Employee> findByEmployeeIdIn(List<String> employeeIds);

    @Modifying
    @Query("update Employee set manageDepartmentIds = ?1 where employeeId = ?2")
    int modifyManageDepartment(String manageDepartmentIds, String employeeId);

    /**
     * 计算公司的员工数量
     * @return
     */
    @Query("select count(employeeId) from Employee where delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and companyInfoId = ?1 and accountType = ?2")
    int countEmployeeByDepartmentIds(Long companyInfoId, AccountType accountType);

    /**
     * 计算平台员工数量
     * @return
     */
    @Query("select count(employeeId) from Employee where delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and accountType = ?1")
    int countBossEmployeeByDepartmentIds(AccountType accountType);

    /**
     * 根据工号查询员工是否存在
     *
     * @param jobNo
     * @param delFlag
     * @return
     */
    int countByJobNoAndDelFlagAndAccountTypeAndCompanyInfoId(String jobNo, DeleteFlag delFlag, AccountType accountType, Long companyInfoId);

    /**
     * 根据交接人查询员工
     *
     * @return Stream<Employee>
     */
    @Query("select employeeId from Employee where heirEmployeeId in ?1 ")
    List<String> findEmployeeIdByHeirEmployeeIdIn(List<String> heirEmployeeId);


    /**
     * 修改交接人id
     * @param employeeId
     * @param heirEmployeeId
     * @return
     */
    @Modifying
    @Query("update Employee set heirEmployeeId = ?2 where employeeId in (?1)")
    int modifyHeirEmployeeId(List<String> employeeId, String heirEmployeeId);

    @Query("from Employee e where e.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO")
    List<Employee> listByPage(PageRequest pageable);

    /**
     * 0点执行登录次数清0操作
     *
     * @param updateTime  updateTime
     */
    @Modifying
    @Query("update Employee e set e.updateTime = ?1, e.loginErrorTime = 0 where e.loginErrorTime > 0 and e.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and e.loginLockTime is null")
    void modifyLoginErrorTime(LocalDateTime updateTime);

    /**
     * 修改员工version
     * @param versionNo
     * @param companyInfoId
     * @return
     */
    @Modifying
    @Query("update Employee set versionNo = ?1 where companyInfoId = ?2")
    int modifyVersionNo(Long versionNo, Long companyInfoId);
}
