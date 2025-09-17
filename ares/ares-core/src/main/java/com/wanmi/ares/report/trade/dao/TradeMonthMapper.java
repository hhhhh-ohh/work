package com.wanmi.ares.report.trade.dao;

import com.wanmi.ares.report.base.dao.MyBatisBaseDao;
import com.wanmi.ares.report.trade.model.root.TradeMonth;
import com.wanmi.ares.request.flow.FlowDataListRequest;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * TradeMonthMapper继承基类
 */
@Repository
public interface TradeMonthMapper extends MyBatisBaseDao<TradeMonth, Long> {

    /***
     * 根据原有数据统计店铺类型分类数据
     * @param date              统计日期
     * @param yearMonth         统计月
     * @param shopId            模拟的公司ID
     * @param storeTypeList     统计包含的店铺类型
     */
    void insertTradeSumByStoreType(@Param("date") LocalDate date, @Param("yearMonth") String yearMonth,
                                   @Param("shopId")String shopId, @Param("storeType")List<Integer> storeTypeList);
    /**
     * 删除表数据
     */
    void deleteTable();

    /**
     * 按单月删除数据
     * @param month
     */
    void deleteRecentMonth(String month);

    /**
     * 按多月删除数据
     * @param months
     */
    void deleteRecentMonths(List months);

    /**
     * @param flowDataListRequest
     * @return
     */
    TradeMonth queryTradeMonthOne(FlowDataListRequest flowDataListRequest);

    /**
     *
     * @param flowDataListRequest
     * @return
     */
    List<TradeMonth> pageTradeStore(FlowDataListRequest flowDataListRequest);

    /**
     *
     * @param flowDataListRequest
     * @return
     */
    int pageTradeStoreCount(FlowDataListRequest flowDataListRequest);

}