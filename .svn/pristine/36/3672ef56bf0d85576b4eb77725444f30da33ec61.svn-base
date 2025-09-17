package com.wanmi.sbc.customer.employee.repository;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.customer.employee.model.root.RoleInfo;
import java.util.Optional;
import java.util.stream.Stream;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * 角色数据
 * Created by zhangjin on 2017/4/19.
 */
public interface RoleInfoRepository extends JpaRepository<RoleInfo, Long> {

    /**
     * 根据角色名排它
     *
     * @param roleName roleName
     * @param delFlag  deleteFlag
     * @return int
     */
    Optional<RoleInfo> findByRoleNameAndCompanyInfoIdAndDelFlag(String roleName, Long companyInfoId, DeleteFlag
            delFlag);

    /**
     * 根据id查角色
     *
     * @param roleInfoId roleInfoId
     * @param delFlag    delFlag
     * @return Optional<RoleInfo>
     */
    Optional<RoleInfo> findByRoleInfoIdAndDelFlag(Long roleInfoId, DeleteFlag delFlag);

    /**
     * 根据companyInfoId查角色
     *
     * @param companyInfoId companyInfoId
     * @param delFlag       delFlag
     * @return Optional<RoleInfo>
     */
    Stream<RoleInfo> findByCompanyInfoIdAndDelFlag(Long companyInfoId, DeleteFlag delFlag);

    /**
     * 删除
     *
     */
    @Query("update RoleInfo e set e.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where e.roleInfoId in ?1 ")
    @Modifying
    int deleteRoleById(Long roleInfoId);
}
