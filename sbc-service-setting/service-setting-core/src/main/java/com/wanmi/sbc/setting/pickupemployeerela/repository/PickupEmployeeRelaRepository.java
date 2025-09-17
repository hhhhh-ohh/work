package com.wanmi.sbc.setting.pickupemployeerela.repository;

import com.wanmi.sbc.setting.pickupemployeerela.model.root.PickupEmployeeRela;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * <p>自提员工关系DAO</p>
 * @author xufeng
 * @date 2021-09-06 14:23:11
 */
@Repository
public interface PickupEmployeeRelaRepository extends JpaRepository<PickupEmployeeRela, Long>,
        JpaSpecificationExecutor<PickupEmployeeRela> {

    /**
     * 列表查询自提员工关系
     *
     * @author xufeng
     */
    List<PickupEmployeeRela> findByPickupId(Long pickupId);

    /**
     * 批量删除自提员工关系
     * @param pickupId
     */
    void deleteByPickupId(Long pickupId);

    /**
     * 根据多个员工编号进行删除
     *
     * @param employeeIds   员工ID
     */
    @Modifying
    @Query("delete from PickupEmployeeRela where employeeId in ?1")
    void deleteByEmployeeIds(List<String> employeeIds);

}
