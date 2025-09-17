package com.wanmi.ares.report.trade.dao;

import com.wanmi.ares.report.base.dao.MyBatisBaseDao;
import com.wanmi.ares.report.trade.model.root.TradeSeven;
import com.wanmi.ares.request.flow.FlowDataListRequest;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * TradeSevenMapper继承基类
 */
@Repository
public interface TradeSevenMapper extends MyBatisBaseDao<TradeSeven, Long> {

    /***
     * 根据原有数据统计店铺类型分类数据
     * @param date              统计日期
     * @param shopId            模拟的公司ID
     * @param storeTypeList     统计包含的店铺类型
     */
    void insertTradeSumByStoreType(@Param("date") LocalDate date, @Param("shopId")String shopId,
                                   @Param("storeType")List<Integer> storeTypeList);
    /**
     * @param flowDataListRequest
     * @return
     */
    TradeSeven querySevenOnly(FlowDataListRequest flowDataListRequest);

    /**
     * @param flowDataListRequest
     * @return
     */
    List<TradeSeven> pageTradeSeven(FlowDataListRequest flowDataListRequest);

    /**
     *
     * @param flowDataListRequest
     * @return
     */
    List<TradeSeven> pageTradeStore(FlowDataListRequest flowDataListRequest);

    /**
     *
     * @param flowDataListRequest
     * @return
     */
    int pageTradeStoreCount(FlowDataListRequest flowDataListRequest);

    void deleteSevenTrade(LocalDate date);
}