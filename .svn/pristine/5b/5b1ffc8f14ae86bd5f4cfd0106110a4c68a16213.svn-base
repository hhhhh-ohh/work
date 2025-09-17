package com.wanmi.ares.report.trade.service;

import com.wanmi.ares.enums.StoreSelectType;
import com.wanmi.ares.report.base.service.AresSystemConfigQueryService;
import com.wanmi.ares.report.trade.dao.TradeReportMapper;
import com.wanmi.ares.report.trade.dao.TradeSevenMapper;
import com.wanmi.ares.report.trade.model.root.TradeBase;
import com.wanmi.ares.report.trade.model.root.TradeSeven;
import com.wanmi.ares.request.flow.FlowDataListRequest;
import com.wanmi.ares.utils.DateUtil;
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
public class TradeSevenService extends ComputerUvHandler{

    @Resource
    private TradeSevenMapper tradeSevenMapper;

    @Resource
    private TradeReportMapper tradeReportMapper;
    @Resource
    private AresSystemConfigQueryService aresSystemConfigQueryService;

    @Transactional(isolation= Isolation.READ_UNCOMMITTED,rollbackFor = Exception.class)
    public void add(TradeSeven tradeSeven) {
        tradeSevenMapper.insertSelective(tradeSeven);
    }

    @Transactional(isolation= Isolation.READ_UNCOMMITTED,rollbackFor = Exception.class)
    public void addByBase(List<TradeBase> tradeBases,LocalDate date) {
        // 过滤不是Mock的公司Id，会由查询的flow_day带过来
        tradeBases.stream()
                .filter(i -> !StoreSelectType.isMockCompanyInfoId(i.getCompanyId()))
                .forEach(i->{
            this.computeUv(i,date);
            TradeSeven tradeSeven = KsBeanUtil.convert(i, TradeSeven.class);
            add(tradeSeven);
        });
        /** 2021-09-24 add by zhengyang
         * 统计完成后，重新计算店铺、门店的统计数据
         * 判断当存在O2O配置开关并且打开时才生成记录 * */
        if (aresSystemConfigQueryService.queryO2oOpening()) {
            generateTradeSumByStoreType(date);
        }
    }

    @Transactional(isolation= Isolation.READ_UNCOMMITTED,rollbackFor = Exception.class)
    public void reduceTradeBase(LocalDate date){
        //删除七天报表
        tradeSevenMapper.deleteSevenTrade(date);
        //生成七天报表
        List<TradeBase> tradeBaseSeven = tradeReportMapper.collectTrade(DateUtil.computeDateIntervalSeven(date));
        TradeBase tradeBase = tradeReportMapper.collectAllTrade(DateUtil.computeDateIntervalSeven(date));
        tradeBaseSeven.add(tradeBase);
        this.addByBase(tradeBaseSeven,date);
    }

    public TradeSeven queryTradeSevenOne(FlowDataListRequest flowDataListRequest){
        return tradeSevenMapper.querySevenOnly(flowDataListRequest);
    }

    public List<TradeSeven> pageTradeStore(FlowDataListRequest flowDataListRequest) {
        return tradeSevenMapper.pageTradeStore(flowDataListRequest);
    }

    public int pageTradeStoreCount(FlowDataListRequest flowDataListRequest) {
        return tradeSevenMapper.pageTradeStoreCount(flowDataListRequest);
    }

    /***
     * 根据商家类型生成统计数据
     * @param date          日期
     */
    private void generateTradeSumByStoreType(LocalDate date) {
        // 统计商家数据
        tradeSevenMapper.insertTradeSumByStoreType(date, StoreSelectType.SUPPLIER.getMockCompanyInfoId(),
                StoreType.getStoreTypeWithOutO2o());
        // 统计门店数据
        tradeSevenMapper.insertTradeSumByStoreType(date, StoreSelectType.O2O.getMockCompanyInfoId(),
                Lists.newArrayList(StoreType.O2O.toValue()));
    }
}
