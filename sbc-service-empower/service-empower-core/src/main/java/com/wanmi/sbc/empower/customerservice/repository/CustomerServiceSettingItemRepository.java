package com.wanmi.sbc.empower.customerservice.repository;

import com.wanmi.sbc.empower.customerservice.model.root.CustomerServiceSettingItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.wanmi.sbc.common.enums.DeleteFlag;
import java.util.Optional;
import java.util.List;

/**
 * <p>在线客服配置DAO</p>
 * @author 韩伟
 * @date 2021-04-08 16:43:56
 */
@Repository
public interface CustomerServiceSettingItemRepository extends JpaRepository<CustomerServiceSettingItem, Long>,
        JpaSpecificationExecutor<CustomerServiceSettingItem> {

    Optional<CustomerServiceSettingItem> findByServiceItemIdAndStoreIdAndDelFlag(Integer id, Long storeId, DeleteFlag delFlag);

    List<CustomerServiceSettingItem> findByCustomerServiceIdAndDelFlag(Long customerServiceId, DeleteFlag delFlag);

    /**
     * 批量删除在线客服下面的座席
     * @param customerServiceId
     */
    void deleteByCustomerServiceId(Long customerServiceId);

    /**
     * 查询QQ号是否重复
     * @param serverAccount
     * @return
     */
    @Query("from CustomerServiceSettingItem l where l.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and l.customerServiceAccount in ?1 ")
    List<CustomerServiceSettingItem> checkDuplicateAccount(List<String> serverAccount);

}
