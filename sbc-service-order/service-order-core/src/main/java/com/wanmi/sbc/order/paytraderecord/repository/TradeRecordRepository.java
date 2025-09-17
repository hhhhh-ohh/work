package com.wanmi.sbc.order.paytraderecord.repository;

import com.wanmi.sbc.empower.bean.enums.TradeStatus;
import com.wanmi.sbc.order.paytraderecord.model.root.PayTradeRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by of628-wenzhi on 2017-08-11-下午3:41.
 */
@Repository
public interface TradeRecordRepository extends JpaRepository<PayTradeRecord, String>, JpaSpecificationExecutor<PayTradeRecord> {

    PayTradeRecord findByBusinessId(String businessId);

    /***
     * 根据业务ID集合查询
     * @return
     */
    @Query("from PayTradeRecord where businessId in(:businessIdList) and tradeNo is not null")
    List<PayTradeRecord> findByBusinessIdList(@Param("businessIdList") List<String> businessIdList);

    PayTradeRecord findTopByBusinessIdAndStatus(String businessId, TradeStatus status);

    long countByBusinessId(String businessId);

    PayTradeRecord findByChargeId(String chargeId);

    int deleteByBusinessId(String businessId);

    int deleteByPayNo(String businessId);

    /**
     * 查询所有账户的授信已使用额度（当前周期）
     *
     * @return
     */
    @Query(
            "select sum(practicalPrice) from PayTradeRecord where channelItemId in(24,25,26) and status = '1' " +
                    "and tradeType = 'PAY' ")
    BigDecimal sumCreditUsedAmount();

    /**
     * 查询所有账户的授信已还款额度
     *
     * @return
     */
    @Query(
            "select sum(practicalPrice) from PayTradeRecord where status = '1' and businessId like 'CR%' ")
    BigDecimal sumCreditHasRepaidAmount();

    /**
     * 根据业务ID批量查询
     * @param businessIds  业务ID集合
     * @return
     */
    List<PayTradeRecord> findByBusinessIdIn(List<String> businessIds);

    /**
     * 根据交易流水号批量查询
     * @param transNoList  交易流水号集合
     * @return
     */

    @Query("from PayTradeRecord where tradeNo in(:transNoList)")

    List<PayTradeRecord> findBusinessIdByTransNoList(List<String> transNoList);
}
