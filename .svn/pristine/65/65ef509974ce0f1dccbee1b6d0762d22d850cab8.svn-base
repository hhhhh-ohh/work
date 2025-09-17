package com.wanmi.sbc.empower.deliveryrecord.repository;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.empower.deliveryrecord.model.root.DeliveryRecordDada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * <p>达达配送记录DAO</p>
 * @author dyt
 * @date 2019-07-30 14:08:26
 */
@Repository
public interface DeliveryRecordDadaRepository extends JpaRepository<DeliveryRecordDada, String>,
        JpaSpecificationExecutor<DeliveryRecordDada> {


    DeliveryRecordDada findByOrderNoAndDelFlag(String orderNo, DeleteFlag deleteFlag);

}
