package com.wanmi.sbc.customer.address.repository;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.customer.address.model.root.CustomerDeliveryAddress;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * 客户收货地址
 * Created by CHENLI on 2017/4/20.
 */
@Repository
public interface CustomerDeliveryAddressRepository extends JpaRepository<CustomerDeliveryAddress, String>,
        JpaSpecificationExecutor<CustomerDeliveryAddress> {

    /**
     * 修改为默认地址
     *
     * @param addressId
     * @param customerId
     */
    @Modifying
    @Query("update CustomerDeliveryAddress c set c.isDefaltAddress = com.wanmi.sbc.common.enums.DefaultFlag.YES " +
            "where c.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and c.deliveryAddressId = :addressId and c.customerId = :customerId")
    void updateDefault(@Param("addressId") String addressId, @Param("customerId") String customerId);

    /**
     * 删除客户收货地址
     *
     * @param addressId
     */
    @Modifying
    @Query("update CustomerDeliveryAddress c set c.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where c.deliveryAddressId = :addressId")
    void deleteAddress(@Param("addressId") String addressId);

    /**
     * 查询客户的收货地址
     *
     * @param customerId
     * @return
     */
    List<CustomerDeliveryAddress> findByCustomerIdAndDelFlagOrderByCreateTimeDesc(String customerId, DeleteFlag
            deleteFlag);

    /**
     * 查询该客户有多少条收货地址
     *
     * @param customerId
     * @return
     */
    @Query("select count(1) from CustomerDeliveryAddress c where c.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and c.customerId = :customerId")
    int countCustomerAddress(@Param("customerId") String customerId);

    /**
     * 根据收货地址ID和会员ID查询收货信息
     * @return
     */
    CustomerDeliveryAddress findByDeliveryAddressIdAndCustomerIdAndDelFlag(String deliveryAddressId, String customerId, DeleteFlag deleteFlag);
}
