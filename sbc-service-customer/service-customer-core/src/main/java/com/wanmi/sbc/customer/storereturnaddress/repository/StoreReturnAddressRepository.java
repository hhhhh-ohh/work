package com.wanmi.sbc.customer.storereturnaddress.repository;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.customer.storereturnaddress.model.root.StoreReturnAddress;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * <p>店铺退货地址表DAO</p>
 * @author dyt
 * @date 2020-11-02 11:38:39
 */
@Repository
public interface StoreReturnAddressRepository extends JpaRepository<StoreReturnAddress, String>,
        JpaSpecificationExecutor<StoreReturnAddress> {

    /**
     * 单个删除店铺退货地址表
     * @author dyt
     */
    @Modifying
    @Query("update StoreReturnAddress set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where addressId = ?1")
    void deleteById(String addressId);

    Optional<StoreReturnAddress> findByAddressIdAndStoreIdAndDelFlag(String id, Long storeId, DeleteFlag delFlag);

}
