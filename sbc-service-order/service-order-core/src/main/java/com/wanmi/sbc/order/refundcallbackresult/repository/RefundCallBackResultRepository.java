package com.wanmi.sbc.order.refundcallbackresult.repository;

import com.wanmi.sbc.order.bean.enums.PayCallBackResultStatus;
import com.wanmi.sbc.order.refundcallbackresult.model.root.RefundCallBackResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * <p>退款回调结果DAO</p>
 * @author lvzhenwei
 * @date 2020-07-01 17:34:23
 */
@Repository
public interface RefundCallBackResultRepository extends JpaRepository<RefundCallBackResult, Long>,
        JpaSpecificationExecutor<RefundCallBackResult> {

    @Modifying
    @Query("update RefundCallBackResult set resultStatus = ?2,errorNum = errorNum+1 where businessId = ?1 and errorNum < 6")
    int updateStatusFailedByBusinessId(String businessId, PayCallBackResultStatus resultStatus);

    @Modifying
    @Query("update RefundCallBackResult set resultStatus = ?2 where businessId = ?1")
    int updateStatusSuccessByBusinessId(String businessId, PayCallBackResultStatus resultStatus);
}
