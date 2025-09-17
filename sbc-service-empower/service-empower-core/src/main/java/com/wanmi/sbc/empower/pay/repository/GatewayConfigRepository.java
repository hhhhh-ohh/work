package com.wanmi.sbc.empower.pay.repository;

import com.wanmi.sbc.empower.bean.enums.PayGatewayEnum;
import com.wanmi.sbc.empower.pay.model.root.PayGatewayConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by sunkun on 2017/8/3.
 */
@Repository
public interface GatewayConfigRepository extends JpaRepository<PayGatewayConfig, Long> {

    @Query("select p from PayGatewayConfig p where p.payGateway.id = ?1 and p.storeId = ?2 ")
    PayGatewayConfig queryConfigByGatwayIdAndStoreId(Long gatwayId, Long storeId);

    @Query("select p from PayGatewayConfig p where p.payGateway.isOpen = com.wanmi.sbc.empower.bean.enums.IsOpen.YES and p.storeId = ?1")
    List<PayGatewayConfig> queryConfigByOpenAndStoreId(Long storeId);

    @Query("select p from PayGatewayConfig p where p.payGateway.name=?1  and p.storeId = ?2 ")
    PayGatewayConfig queryConfigByNameAndStoreId(PayGatewayEnum payGatewayEnum, Long storeId);

}
