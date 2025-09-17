package com.wanmi.sbc.setting.thirdaddress.repository;

import com.wanmi.sbc.setting.bean.enums.AddrLevel;
import com.wanmi.sbc.setting.thirdaddress.model.root.ThirdAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.wanmi.sbc.common.enums.DeleteFlag;
import java.util.Optional;
import java.util.List;

/**
 * <p>第三方地址映射表DAO</p>
 * @author dyt
 * @date 2020-08-14 13:41:44
 */
@Repository
public interface ThirdAddressRepository extends JpaRepository<ThirdAddress, String>,
        JpaSpecificationExecutor<ThirdAddress> {

    Optional<ThirdAddress> findByPlatformAddrIdAndDelFlag(String platformAddrId, DeleteFlag delFlag);

    Optional<ThirdAddress> findByIdAndDelFlag(String id, DeleteFlag delFlag);

    /**
     * 更新指定父id下子地址级别
     * @param thirdParentId 父id
     * @param addrLevel 新级别
     * @return
     */
    @Modifying
    @Query("update ThirdAddress set level = ?2 where thirdParentId = ?1")
    int updateSubAddLevel(String thirdParentId, AddrLevel addrLevel);

    /**
     * 查询指定父id和级别下的子id列表
     * @param thirdParentId 父id
     * @param addrLevel 级别
     * @return
     */
    @Query("SELECT thirdAddrId FROM ThirdAddress where thirdParentId IN ?1 AND level = ?2 AND delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO")
    List<String> findThirdAddrIdByParentIds(List<String> thirdParentId, AddrLevel addrLevel);
}
