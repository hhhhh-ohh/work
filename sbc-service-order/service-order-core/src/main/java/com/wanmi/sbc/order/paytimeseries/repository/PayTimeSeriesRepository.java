package com.wanmi.sbc.order.paytimeseries.repository;

import com.wanmi.sbc.order.paytimeseries.model.root.PayTimeSeries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 支付流水记录DAO
 *
 * @author zhanggaolei
 * @date 2022-12-08 15:30:16
 */
@Repository
public interface PayTimeSeriesRepository
        extends JpaRepository<com.wanmi.sbc.order.paytimeseries.model.root.PayTimeSeries, String>,
                JpaSpecificationExecutor<
                        com.wanmi.sbc.order.paytimeseries.model.root.PayTimeSeries> {

    /**
     * 查询单个支付流水记录
     *
     * @author zhanggaolei
     */
    Optional<PayTimeSeries> findByPayNo(String id);

    @Query("from PayTimeSeries p where p.status=1 and p.refundStatus!=1 and p.businessId=?1 and p.payNo!=?2")
    List<PayTimeSeries> findDuplicatePay(String businessId,String notPayNo);
}
