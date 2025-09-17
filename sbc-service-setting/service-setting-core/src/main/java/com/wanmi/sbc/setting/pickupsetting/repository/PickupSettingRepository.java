package com.wanmi.sbc.setting.pickupsetting.repository;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.setting.api.request.pickupsetting.PickupSettingAuditRequest;
import com.wanmi.sbc.setting.pickupsetting.model.root.PickupSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * <p>pickup_settingDAO</p>
 * @author 黄昭
 * @date 2021-09-03 11:01:10
 */
@Repository
public interface PickupSettingRepository extends JpaRepository<PickupSetting, Long>,
        JpaSpecificationExecutor<PickupSetting> {

    /**
     * 单个删除pickup_setting
     * @author 黄昭
     */
    @Modifying
    @Query("update PickupSetting set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where id = ?1")
    void deleteById(Long id);

    Optional<PickupSetting> findByIdAndDelFlag(Long id, DeleteFlag delFlag);

    /**
     * 根据Id更新自提点审批状态
     * @author 黄昭
     * @param
     * @return
     */
    @Modifying
    @Query(nativeQuery = true,value = "UPDATE pickup_setting SET audit_status = :auditStatus,audit_reason = :auditReason, audit_person = :updatePerson,audit_time = NOW(),update_person = :updatePerson,update_time = NOW() WHERE id = :id")
    void updateAuditStatusById(Long id,Integer auditStatus,String auditReason,String updatePerson);

    /**
     * 根据Id更新自提点启停用状态
     * @author 黄昭
     * @param
     * @return
     */
    @Modifying
    @Query("update PickupSetting p set p.enableStatus = :enableStatus,p.isDefaultAddress = :isDefaultAddress, p.updatePerson = :updatePerson, p.updateTime = now() where p.id = :id")
    void updateEnableStatusById(Long id,Integer enableStatus,String updatePerson,int isDefaultAddress);

    /**
     * 设置默认自提点
     * @author 徐锋
     * @param
     * @return
     */
    @Modifying
    @Query("update PickupSetting p set p.isDefaultAddress = 1, p.updatePerson = :updatePerson, p.updateTime = now() " +
            "where p.id = :id")
    void updateDefaultAddressById(Long id,String updatePerson);

    /**
     * 设置默认自提点
     * @author 徐锋
     * @param
     * @return
     */
    @Modifying
    @Query("update PickupSetting p set p.isDefaultAddress = 0, p.updatePerson = :updatePerson, p.updateTime = now() " +
            "where p.storeId = :storeId and p.isDefaultAddress = 1 and p.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO")
    void updateDefaultAddressByState(String updatePerson, Long storeId);

    /**
     * 根据Id更新自提点审批状态
     * @author 黄昭
     * @param
     * @return
     */
    @Query(nativeQuery = true,value = "SELECT id FROM pickup_setting ps WHERE store_id = :storeId AND ps.id NOT IN (SELECT pickup_id FROM pickup_employee_rela GROUP BY pickup_id)")
    List<Long> findNoEmployeePickupIds(Long storeId);

    @Query(value = "SELECT\n" +
            "\tps.id \n" +
            "FROM\n" +
            "\tpickup_setting ps\n" +
            "\tLEFT JOIN pickup_employee_rela per ON ps.id = per.pickup_id \n" +
            "WHERE\n" +
            "\tper.employee_id = :employeeId \n" +
            "\tAND ps.del_flag = 0",nativeQuery = true)
    List<Long> findByEmployeeId(String employeeId);

    List<PickupSetting> findByNameAndDelFlag(String name, DeleteFlag delFlag);
}
