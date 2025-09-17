package com.wanmi.ares.report.trade.service;

import com.wanmi.ares.enums.QueryDateCycle;
import com.wanmi.ares.interfaces.TradeService;
import com.wanmi.ares.report.trade.model.request.TradeReportRequest;
import com.wanmi.ares.report.trade.service.TradeDataStrategy;
import com.wanmi.ares.report.trade.service.TradeDataStrategyChooser;
import com.wanmi.ares.request.flow.FlowDataListRequest;
import com.wanmi.ares.request.flow.FlowRequest;
import com.wanmi.ares.utils.DateUtil;
import com.wanmi.ares.utils.ParseData;
import com.wanmi.ares.view.trade.TradePageView;
import com.wanmi.ares.view.trade.TradeView;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunkun on 2017/10/16.
 */
@Service
public class TradeServiceImpl implements TradeService.Iface {

    @Resource
    private TradeDataStrategyChooser tradeDataStrategyChooser;

    @Override
    public List<TradeView> getTradeList(FlowRequest request)  {
        FlowDataListRequest flowDataListRequest = ParseData.parse(request);
        TradeDataStrategy tradeDataStrategy = tradeDataStrategyChooser.choose(flowDataListRequest.getStatisticsDataType());
        List<TradeView> tradeViewList = tradeDataStrategy.getTradeData(flowDataListRequest);
        return CollectionUtils.isNotEmpty(tradeViewList)?tradeViewList: new ArrayList<>();
    }

    @Override
    public TradePageView getTradePage(FlowRequest request) {
        FlowDataListRequest flowDataListRequest = ParseData.parse(request);
        TradeDataStrategy tradeDataStrategy = tradeDataStrategyChooser.choose(flowDataListRequest.getStatisticsDataType());
        return tradeDataStrategy.getTradePage(flowDataListRequest);
    }

    @Override
    public TradeView getOverview(FlowRequest request){
        FlowDataListRequest flowDataListRequest = ParseData.parse(request);
        TradeDataStrategy tradeDataStrategy = tradeDataStrategyChooser.choose(flowDataListRequest.getStatisticsDataType());
        TradeView tradeView = tradeDataStrategy.getTradeView(flowDataListRequest);
        return tradeView != null?tradeView:new TradeView();
    }

    @Override
    public TradePageView getStoreTradePage(FlowRequest request) {
        FlowDataListRequest flowDataListRequest = ParseData.parse(request);
        TradeDataStrategy tradeDataStrategy = tradeDataStrategyChooser.choose(flowDataListRequest.getStatisticsDataType());
        return tradeDataStrategy.getTradeStorePage(flowDataListRequest);
    }

}
