package com.wanmi.ares.report.trade.service;

import com.alibaba.fastjson2.JSON;
import com.wanmi.ares.enums.StoreSelectType;
import com.wanmi.ares.report.base.service.AresSystemConfigQueryService;
import com.wanmi.ares.report.trade.dao.TradeMonthMapper;
import com.wanmi.ares.report.trade.dao.TradeReportMapper;
import com.wanmi.ares.report.trade.model.request.TradeCollect;
import com.wanmi.ares.report.trade.model.root.TradeBase;
import com.wanmi.ares.report.trade.model.root.TradeMonth;
import com.wanmi.ares.request.flow.FlowDataListRequest;
import com.wanmi.ares.utils.DateUtil;
import com.wanmi.ares.utils.KsBeanUtil;
import com.wanmi.sbc.common.enums.StoreType;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.Resource;
import java.time.LocalDate;
import java.util.List;

/**
 * @Author: ZhangLingKe
 * @Description:
 * @Date: 2019-08-27 10:51
 */
@Slf4j
@Service
public class TradeMonthService extends ComputerUvHandler {

    @Resource
    private TradeMonthMapper tradeMonthMapper;

    @Resource
    private TradeReportMapper tradeReportMapper;

    @Resource
    private TradeWeekService tradeWeekService;
    @Resource
    private AresSystemConfigQueryService aresSystemConfigQueryService;

    @Transactional(isolation= Isolation.READ_UNCOMMITTED,rollbackFor = Exception.class)
    public void add(TradeMonth tradeMonth) {
        tradeMonthMapper.insertSelective(tradeMonth);
    }

    @Transactional(isolation= Isolation.READ_UNCOMMITTED,rollbackFor = Exception.class)
    public void addByBase(List<TradeBase> tradeBases, LocalDate date,String yearMonth) {
        if (log.isInfoEnabled()) {
            log.info("TradeMonthService -> addByBase call, params is {},{} ",
                    DateUtil.format(date, "yyyy-MM-dd"), JSON.toJSONString(tradeBases));
        }
        // 过滤不是Mock的公司Id，会由查询的flow_day带过来
        tradeBases.stream()
                .filter(i -> !StoreSelectType.isMockCompanyInfoId(i.getCompanyId()))
                .forEach(i->{
            this.computeUv(i, date);
            TradeMonth tradeMonth = KsBeanUtil.convert(i, TradeMonth.class);
            tradeMonth.setMonth(yearMonth);
            add(tradeMonth);
        });
        /** 2021-09-24 add by zhengyang
         统计完成后，重新计算店铺、门店的统计数据
         判断当存在O2O配置开关并且打开时才生成记录 **/
        if (aresSystemConfigQueryService.queryO2oOpening()){
            generateTradeSumByStoreType(date, yearMonth);
        }
    }

    @Transactional(isolation= Isolation.READ_UNCOMMITTED,rollbackFor = Exception.class)
    public void reduceTradeBase(LocalDate date){
        //生成统计数据
        for (int i = 1; i < 7; i++) {
            //删除过期数据
            String oldYearMonth = DateUtil.yearMonth(date.minusMonths(i));
            tradeMonthMapper.deleteRecentMonth(oldYearMonth);
            TradeCollect tradeCollect = DateUtil.computeDateIntervalMonthDay(i, date);
            List<TradeBase> tradeBaseMon = tradeReportMapper.collectTrade(tradeCollect);
            TradeBase tradeBase = tradeReportMapper.collectAllTrade(tradeCollect);
            tradeBaseMon.add(tradeBase);
            String yearMonth = DateUtil.yearMonth(tradeCollect.getBeginDate());
            this.addByBase(tradeBaseMon, date, yearMonth);
            tradeWeekService.reduceTradeBase(DateUtil.getWeekLastDayTrade(tradeCollect.getBeginDate(), tradeCollect.getEndDate()), date, 1,yearMonth);
        }
    }

    public TradeMonth queryTradeMonthOne(FlowDataListRequest flowDataListRequest){
        return tradeMonthMapper.queryTradeMonthOne(flowDataListRequest);
    }

    public List<TradeMonth> pageTradeStore(FlowDataListRequest flowDataListRequest) {
//        flowDataListRequest.setPageNum(flowDataListRequest.getPageNum()-1);
        return tradeMonthMapper.pageTradeStore(flowDataListRequest);
    }

    public int pageTradeStoreCount(FlowDataListRequest flowDataListRequest) {
        return tradeMonthMapper.pageTradeStoreCount(flowDataListRequest);
    }

    /***
     * 根据商家类型生成统计数据
     * @param date          日期
     * @param yearMonth     统计月
     */
    private void generateTradeSumByStoreType(LocalDate date,String yearMonth) {
        // 统计商家数据
        tradeMonthMapper.insertTradeSumByStoreType(date, yearMonth, StoreSelectType.SUPPLIER.getMockCompanyInfoId(),
                StoreType.getStoreTypeWithOutO2o());
        // 统计门店数据
        tradeMonthMapper.insertTradeSumByStoreType(date, yearMonth, StoreSelectType.O2O.getMockCompanyInfoId(),
                Lists.newArrayList(StoreType.O2O.toValue()));
    }
}
