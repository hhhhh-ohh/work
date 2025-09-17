package com.wanmi.ares.report.trade.service;

import com.wanmi.ares.enums.StoreSelectType;
import com.wanmi.ares.report.base.service.AresSystemConfigQueryService;
import com.wanmi.ares.report.trade.dao.TradeReportMapper;
import com.wanmi.ares.report.trade.dao.TradeWeekMapper;
import com.wanmi.ares.report.trade.model.request.TradeCollect;
import com.wanmi.ares.report.trade.model.root.TradeBase;
import com.wanmi.ares.report.trade.model.root.TradeWeek;
import com.wanmi.ares.request.flow.FlowDataListRequest;
import com.wanmi.ares.utils.KsBeanUtil;
import com.wanmi.sbc.common.enums.StoreType;
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
@Service
public class TradeWeekService extends ComputerUvHandler{

    @Resource
    private TradeWeekMapper tradeWeekMapper;

    @Resource
    private TradeReportMapper tradeReportMapper;
    @Resource
    private AresSystemConfigQueryService aresSystemConfigQueryService;

    @Transactional(isolation= Isolation.READ_UNCOMMITTED,rollbackFor = Exception.class)
    public void add(TradeWeek tradeWeek) {
        tradeWeekMapper.insertSelective(tradeWeek);
    }

    @Transactional(isolation= Isolation.READ_UNCOMMITTED,rollbackFor = Exception.class)
    public void addByBase(List<TradeBase> tradeBases, LocalDate date,Integer type,String yearMonth,TradeCollect tradeCollect) {
        // 过滤不是Mock的公司Id，会由查询的flow_day带过来
        tradeBases.stream()
                .filter(i -> !StoreSelectType.isMockCompanyInfoId(i.getCompanyId()))
                .forEach(i->{
            this.computeUv(i, date);
            TradeWeek tradeWeek = KsBeanUtil.convert(i, TradeWeek.class);
            tradeWeek.setMonth(yearMonth);
            tradeWeek.setType(type);
            tradeWeek.setWeekStartDate(tradeCollect.getBeginDate());
            tradeWeek.setWeekEndDate(tradeCollect.getEndDate());
            add(tradeWeek);
        });
        /** 2021-09-24 add by zhengyang
         统计完成后，重新计算店铺、门店的统计数据
         判断当存在O2O配置开关并且打开时才生成记录 **/
        if (aresSystemConfigQueryService.queryO2oOpening()){
            generateTradeSumByStoreType(tradeCollect, date, type, yearMonth);
        }
    }

    @Transactional(isolation= Isolation.READ_UNCOMMITTED,rollbackFor = Exception.class)
    public void reduceTradeBase(LocalDate date,TradeCollect tradeCollect,Integer type,String yearMonth){
        //生成最近三十天报表
        List<TradeBase> tradeBaseThirtyDay = tradeReportMapper.collectTrade(tradeCollect);
        TradeBase tradeBase = tradeReportMapper.collectAllTrade(tradeCollect);
        tradeBaseThirtyDay.add(tradeBase);
        this.addByBase(tradeBaseThirtyDay,date,type,yearMonth,tradeCollect);
    }

    @Transactional(isolation= Isolation.READ_UNCOMMITTED,rollbackFor = Exception.class)
    public void reduceTradeBase(List<TradeCollect> tradeCollects, LocalDate date,Integer type,String yearMonth){
        //删除最近三十天周数据
        if(type.equals(0)){
            tradeWeekMapper.deleteThirtyWeek(type);
        }

        //删除最近三十天周数据
        if(type.equals(1)){
            tradeWeekMapper.deleteMonthWeek(yearMonth);
        }

        for (TradeCollect i : tradeCollects) {
            this.reduceTradeBase(date, i, type, yearMonth);
        }
    }

    public List<TradeWeek> queryWeek(FlowDataListRequest flowDataListRequest){
        return tradeWeekMapper.queryWeek(flowDataListRequest);
    }


    /***
     * 根据商家类型生成统计数据
     * @param tradeCollect  查询对象
     * @param date          日期
     * @param type          类型
     * @param yearMonth     统计月
     */
    private void generateTradeSumByStoreType(TradeCollect tradeCollect, LocalDate date,Integer type,String yearMonth) {
        // 统计商家数据
        tradeWeekMapper.insertTradeSumByStoreType(date, yearMonth, type, tradeCollect.getBeginDate(),
                tradeCollect.getEndDate(), StoreSelectType.SUPPLIER.getMockCompanyInfoId(),
                StoreType.getStoreTypeWithOutO2o());
        // 统计门店数据
        tradeWeekMapper.insertTradeSumByStoreType(date, yearMonth, type, tradeCollect.getBeginDate(),
                tradeCollect.getEndDate(), StoreSelectType.O2O.getMockCompanyInfoId(),
                Lists.newArrayList(StoreType.O2O.toValue()));
    }
}
