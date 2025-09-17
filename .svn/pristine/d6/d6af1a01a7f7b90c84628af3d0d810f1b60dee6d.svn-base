package com.wanmi.sbc.order.trade.repository;


import com.wanmi.sbc.order.trade.model.root.Trade;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 订单repository
 * Created by jinwei on 15/3/2017.
 */
@Repository
public interface TradeRepository extends MongoRepository<Trade, String> {

    List<Trade> findListByParentId(String parentId);

    List<Trade> findListByParentIdAndBuyer_Id(String parentId,String customerId);

    Optional<Trade> findTopByTradeDelivers_Logistics_LogisticNoAndTradeDelivers_Logistics_logisticStandardCode
            (String id, String logisticNo);

    Trade findByIdAndBuyer_Id(String id,String customerId);

    Trade findByWriteOffInfo_WriteOffCode(String writeOffCode);

    List<Trade> findByTailOrderNoIn(List<String> ids);

    /**
     * 查询需要预约发货且已过期的订单
     * @param startTime,endTime 当前时间字符串（格式："yyyy-MM-dd HH:mm:ss"）
     * @return 符合条件的订单列表
     */
    @Query("{ 'appointmentShipmentFlag': 1,  'appointmentShipmentTime': { $gte: ?0, $lte: ?1 } }")
    List<Trade> findExpiredAppointmentTrades(String startTime, String endTime);
}

