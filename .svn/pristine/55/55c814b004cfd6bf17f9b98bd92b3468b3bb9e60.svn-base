package com.wanmi.ares.report.trade.service;

import com.wanmi.ares.enums.StoreSelectType;
import com.wanmi.ares.report.base.service.AresSystemConfigQueryService;
import com.wanmi.ares.report.flow.model.root.FlowThirty;
import com.wanmi.ares.report.flow.service.FlowThirtyService;
import com.wanmi.ares.report.trade.dao.TradeReportMapper;
import com.wanmi.ares.report.trade.dao.TradeThirtyMapper;
import com.wanmi.ares.report.trade.model.request.TradeCollect;
import com.wanmi.ares.report.trade.model.root.TradeBase;
import com.wanmi.ares.report.trade.model.root.TradeThirty;
import com.wanmi.ares.request.flow.FlowDataListRequest;
import com.wanmi.ares.request.flow.FlowThirtyRequest;
import com.wanmi.ares.utils.DateUtil;
import com.wanmi.ares.utils.KsBeanUtil;
import com.wanmi.sbc.common.enums.StoreType;
import org.assertj.core.util.Lists;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author: ZhangLingKe
 * @Description:
 * @Date: 2019-08-27 10:51
 */
@Service
public class TradeThirtyService {

    @Resource
    private TradeThirtyMapper tradeThirtyMapper;

    @Resource
    private TradeReportMapper tradeReportMapper;

    @Resource
    private TradeWeekService tradeWeekService;

    @Resource
    private FlowThirtyService flowThirtyService;
    @Resource
    private AresSystemConfigQueryService aresSystemConfigQueryService;

    @Transactional(isolation= Isolation.READ_UNCOMMITTED,rollbackFor = Exception.class)
    public void add(TradeThirty tradeThirty) {
        tradeThirtyMapper.insertSelective(tradeThirty);
    }

    @Transactional(isolation= Isolation.READ_UNCOMMITTED,rollbackFor = Exception.class)
    public void addByBase(List<TradeBase> tradeBases, LocalDate date) {
        // 过滤不是Mock的公司Id，会由查询的flow_day带过来
        tradeBases.stream()
                .filter(i -> !StoreSelectType.isMockCompanyInfoId(i.getCompanyId()))
                .forEach(i->{
            this.computeUv(i, date);
            TradeThirty tradeThirty = KsBeanUtil.convert(i, TradeThirty.class);
            add(tradeThirty);
        });

        /** 2021-09-24 add by zhengyang 统计完成后，重新计算店铺、门店的统计数据 判断当存在O2O配置开关并且打开时才生成记录 * */
        if (aresSystemConfigQueryService.queryO2oOpening()) {
            generateTradeSumByStoreType(date);
        }
    }

    private void computeUv(TradeBase tradeBase,LocalDate date){
        FlowThirtyRequest flowThirtyRequest= new FlowThirtyRequest();
        flowThirtyRequest.setFlowDate(date);
        flowThirtyRequest.setCompanyId(tradeBase.getCompanyId().toString());
        FlowThirty flowThirty = flowThirtyService.queryFlowThirtInfo(flowThirtyRequest);
        tradeBase.setDate(date);
        tradeBase.setCreateTime(LocalDateTime.now());
        //填充下单转化率 统计时间内，下单人数/访客数UV
        if (flowThirty == null || flowThirty.getUv() == null || flowThirty.getUv().equals(0L)){
            tradeBase.setUv(0L);
            tradeBase.setOrderConversion(new BigDecimal("100.00"));
            //填充全店转换率 统计时间内，付款人数/访客数UV
            tradeBase.setAllConversion(new BigDecimal("100.00"));
        }else {
            tradeBase.setUv(flowThirty.getUv());
            tradeBase.setOrderConversion(new BigDecimal(tradeBase.getOrderUserNum())
                    .multiply(new BigDecimal("100"))
                    .divide(new BigDecimal(flowThirty.getUv()), 2, RoundingMode.HALF_UP));
            //填充全店转换率 统计时间内，付款人数/访客数UV
            tradeBase.setAllConversion(new BigDecimal(tradeBase.getPayUserNum())
                    .multiply(new BigDecimal("100"))
                    .divide(new BigDecimal(flowThirty.getUv()), 2, RoundingMode.HALF_UP));
        }
    }

    @Transactional(isolation= Isolation.READ_UNCOMMITTED,rollbackFor = Exception.class)
    public void reduceTradeBase(LocalDate date){
        //删除最近三十天报表
        tradeThirtyMapper.deleteThirtyTrade(date);
        //生成最近三十天报表
        TradeCollect tradeCollect = DateUtil.computeDateIntervalThirtyDay(date);
        List<TradeBase> tradeBaseThirtyDay = tradeReportMapper.collectTrade(tradeCollect);
        TradeBase tradeBase = tradeReportMapper.collectAllTrade(tradeCollect);
        tradeBaseThirtyDay.add(tradeBase);
        this.addByBase(tradeBaseThirtyDay,date);
        tradeWeekService.reduceTradeBase(DateUtil.getWeekLastDayTrade(tradeCollect.getBeginDate(), tradeCollect.getEndDate()), date, 0,null);
    }

    public TradeThirty queryTradeThirtyOne(FlowDataListRequest flowDataListRequest){
        return tradeThirtyMapper.queryTradeThirtyOne(flowDataListRequest);
    }

    public List<TradeThirty> pageTradeStore(FlowDataListRequest flowDataListRequest){
        return tradeThirtyMapper.pageTradeStore(flowDataListRequest);
    }

    public int pageTradeStoreCount(FlowDataListRequest flowDataListRequest){
        return tradeThirtyMapper.pageTradeStoreCount(flowDataListRequest);
    }
    /***
     * 根据商家类型生成统计数据
     * @param date          日期
     */
    private void generateTradeSumByStoreType(LocalDate date) {
        // 统计商家数据
        tradeThirtyMapper.insertTradeSumByStoreType(date, StoreSelectType.SUPPLIER.getMockCompanyInfoId(),
                StoreType.getStoreTypeWithOutO2o());
        // 统计门店数据
        tradeThirtyMapper.insertTradeSumByStoreType(date, StoreSelectType.O2O.getMockCompanyInfoId(),
                Lists.newArrayList(StoreType.O2O.toValue()));
    }
}
