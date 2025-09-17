package com.wanmi.ares.report.trade.service;

import com.alibaba.fastjson2.JSON;
import com.wanmi.ares.enums.StoreSelectType;
import com.wanmi.ares.report.base.service.AresSystemConfigQueryService;
import com.wanmi.ares.report.flow.model.root.FlowReport;
import com.wanmi.ares.report.trade.dao.TradeDayMapper;
import com.wanmi.ares.report.trade.dao.TradeReportMapper;
import com.wanmi.ares.report.trade.model.request.TradeCollect;
import com.wanmi.ares.report.trade.model.root.TradeBase;
import com.wanmi.ares.report.trade.model.root.TradeDay;
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
public class TradeDayService extends ComputerUvHandler{

    @Resource
    private TradeDayMapper tradeDayMapper;

    @Resource
    private TradeReportMapper tradeReportMapper;
    @Resource
    private AresSystemConfigQueryService aresSystemConfigQueryService;

    @Transactional(isolation= Isolation.READ_UNCOMMITTED,rollbackFor = Exception.class)
    public void add(TradeDay tradeDay) {
        tradeDayMapper.insertSelective(tradeDay);
    }

    @Transactional(isolation= Isolation.READ_UNCOMMITTED,rollbackFor = Exception.class)
    public void addByBase(List<TradeBase> tradeBases, LocalDate date) {
        if (log.isInfoEnabled()) {
            log.info("TradeDayService -> addByBase call, params is {},{} ",
                    DateUtil.format(date, "yyyy-MM-dd"), JSON.toJSONString(tradeBases));
        }
        // 过滤不是Mock的公司Id，会由查询的flow_day带过来
        tradeBases.stream()
                .filter(i -> !StoreSelectType.isMockCompanyInfoId(i.getCompanyId()))
                .forEach(i->{
            this.computeUv(i,date);
            add(KsBeanUtil.convert(i, TradeDay.class));
        });
        /** 2021-09-24 add by zhengyang
         统计完成后，重新计算店铺、门店的统计数据
         判断当存在O2O配置开关并且打开时才生成记录 **/
        if (aresSystemConfigQueryService.queryO2oOpening()){
            generateTradeSumByStoreType(date);
        }
    }

    public List<TradeDay> queryTradeDay(FlowDataListRequest flowDataListRequest){
        return tradeDayMapper.queryDay(flowDataListRequest);
    }

    @Transactional(isolation= Isolation.READ_UNCOMMITTED,rollbackFor = Exception.class)
    public void reduceTradeBase(LocalDate date){
        //清空今日报表
        tradeDayMapper.deleteRecentDay(date);
        //生成今天报表
        TradeCollect tradeCollect = DateUtil.computeDateIntervalDay(date);
        List<TradeBase> tradeBaseDay = tradeReportMapper.collectTrade(tradeCollect);
        TradeBase tradeBase = tradeReportMapper.collectAllTrade(tradeCollect);
        tradeBaseDay.add(tradeBase);
        this.addByBase(tradeBaseDay, date);
    }

    @Transactional(isolation= Isolation.READ_UNCOMMITTED,rollbackFor = Exception.class)
    public void reduceYesterdayTradeBase(LocalDate date){
        LocalDate yesterday = date.minusDays(1);
        //清空昨日报表
        tradeDayMapper.deleteRecentDay(yesterday);
        //生成昨日报表
        TradeCollect tradeCollect = DateUtil.computeDateIntervalDay(yesterday);
        List<TradeBase> tradeBaseDay = tradeReportMapper.collectTrade(tradeCollect);
        TradeBase tradeBase = tradeReportMapper.collectAllTrade(tradeCollect);
        tradeBaseDay.add(tradeBase);
        this.addByBase(tradeBaseDay, yesterday);
    }

    public List<TradeDay> pageTradeDay(FlowDataListRequest flowDataListRequest){
        return tradeDayMapper.pageTradeDay(flowDataListRequest);
    }

    public int pageTradeDayCount(FlowDataListRequest flowDataListRequest){
        return tradeDayMapper.pageTradeDayCount(flowDataListRequest);
    }

    public List<TradeDay> pageTradeStore(FlowDataListRequest flowDataListRequest){
        return tradeDayMapper.pageTradeStore(flowDataListRequest);
    }

    public int pageTradeStoreCount(FlowDataListRequest flowDataListRequest){
        return tradeDayMapper.pageTradeStoreCount(flowDataListRequest);
    }

    public TradeDay queryTradeDayOne(FlowDataListRequest flowDataListRequest){
        return tradeDayMapper.queryDayOne(flowDataListRequest);
    }

    /***
     * 根据商家类型生成统计数据
     * @param date  统计日期
     */
    private void generateTradeSumByStoreType(LocalDate date) {
        if (log.isInfoEnabled()) {
            log.info("TradeDayService -> generateTradeSumByStoreType call, params is {} ",
                    DateUtil.format(date, "yyyy-MM-dd"));
        }
        // 统计商家数据
        tradeDayMapper.insertTradeSumByStoreType(date,
                StoreSelectType.SUPPLIER.getMockCompanyInfoId(),
                StoreType.getStoreTypeWithOutO2o());
        // 统计门店数据
        tradeDayMapper.insertTradeSumByStoreType(
                date,
                StoreSelectType.O2O.getMockCompanyInfoId(),
                Lists.newArrayList(StoreType.O2O.toValue()));
    }
}
