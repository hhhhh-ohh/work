package com.wanmi.ares.report.trade.dao;

import com.wanmi.ares.report.base.dao.MyBatisBaseDao;
import com.wanmi.ares.report.trade.model.request.TradeReportRequest;
import com.wanmi.ares.report.trade.model.root.TradeDay;
import com.wanmi.ares.request.flow.FlowDataListRequest;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * TradeDayDAO继承基类
 */
@Repository
public interface TradeDayMapper extends MyBatisBaseDao<TradeDay, Long> {

    /***
     * 根据原有数据统计店铺类型分类数据
     * @param date              统计日期
     * @param shopId            模拟的公司ID
     * @param storeTypeList     统计包含的店铺类型
     */
    void insertTradeSumByStoreType(@Param("date") LocalDate date, @Param("shopId")String shopId,
                                @Param("storeType")List<Integer> storeTypeList);

    /**
     * 按天删除数据
     * @param day
     */
    void deleteRecentDay(LocalDate day);

    /**
     *
     * @param flowDataListRequest
     * @return
     */
    List<TradeDay> queryDay(FlowDataListRequest flowDataListRequest);

    /**
     *
     * @param flowDataListRequest
     * @return
     */
    List<TradeDay> pageTradeDay(FlowDataListRequest flowDataListRequest);

    /**
     *
     * @param request
     * @return
     */
    List<TradeDay> listTradeDay(TradeReportRequest request);

    /**
     *
     * @param flowDataListRequest
     * @return
     */
    int pageTradeDayCount(FlowDataListRequest flowDataListRequest);

    /**
     *
     * @param flowDataListRequest
     * @return
     */
    List<TradeDay> pageTradeStore(FlowDataListRequest flowDataListRequest);

    /**
     *
     * @param flowDataListRequest
     * @return
     */
    int pageTradeStoreCount(FlowDataListRequest flowDataListRequest);

    /**
     *
     * @param flowDataListRequest
     * @return
     */
    TradeDay queryDayOne(FlowDataListRequest flowDataListRequest);

}