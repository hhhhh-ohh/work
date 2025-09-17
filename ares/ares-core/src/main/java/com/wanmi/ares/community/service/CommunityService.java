package com.wanmi.ares.community.service;

import com.github.pagehelper.PageHelper;
import com.wanmi.ares.community.dao.CommunityReportMapper;
import com.wanmi.ares.community.model.CommunityOverviewReport;
import com.wanmi.sbc.common.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author edz
 * @className CommunityService
 * @description 社区团购
 * @date 2023/8/11 14:41
 **/
@Service
@Slf4j
public class CommunityService {

    @Autowired
    private CommunityReportMapper communityReportMapper;

    @Async
    @Transactional
    public void insertCustomer(String startDate){
        LocalDate localDate;
        if (StringUtils.isBlank(startDate)){
            localDate = LocalDate.now().minusDays(1);
        } else {
            localDate = LocalDate.parse(startDate, DateTimeFormatter.ofPattern(DateUtil.FMT_DATE_1));
        }
        communityReportMapper.delCommunityCustomerDay(localDate);
        communityReportMapper.delCommunityCustomerSeven(localDate);
        communityReportMapper.delCommunityCustomerThirty(localDate);
        communityReportMapper.delCommunityCustomerMonth(localDate);
        communityReportMapper.insertCommunityCustomerDay(localDate, localDate);
        communityReportMapper.insertCommunityCustomerSeven(localDate.minusDays(7), localDate);
        communityReportMapper.insertCommunityCustomerThirty(localDate.minusDays(30), localDate);
        if (LocalDate.now().getDayOfMonth() == 1){
            communityReportMapper.insertCommunityCustomerMonth(LocalDate.now().minusMonths(1), localDate);
        }
    }

    @Async
    @Transactional
    public void insertGoods(String startDate){
        LocalDate localDate;
        if (StringUtils.isBlank(startDate)){
            localDate = LocalDate.now().minusDays(1);
        } else {
            localDate = LocalDate.parse(startDate, DateTimeFormatter.ofPattern(DateUtil.FMT_DATE_1));
        }
        communityReportMapper.delCommunityGoodsDay(localDate);
        communityReportMapper.delCommunityGoodsSeven(localDate);
        communityReportMapper.delCommunityGoodsThirty(localDate);
        communityReportMapper.delCommunityGoodsMonth(localDate);
        communityReportMapper.insertCommunityGoodsDay(localDate, localDate);
        communityReportMapper.insertCommunityGoodsSeven(localDate.minusDays(7), localDate);
        communityReportMapper.insertCommunityGoodsThirty(localDate.minusDays(30), localDate);
        if (LocalDate.now().getDayOfMonth() == 1) {
            communityReportMapper.insertCommunityGoodsMonth(LocalDate.now().minusMonths(1), localDate);
        }
    }

    @Async
    @Transactional
    public void insertLeader(String startDate){
        LocalDate localDate;
        if (StringUtils.isBlank(startDate)){
            localDate = LocalDate.now().minusDays(1);
        } else {
            localDate = LocalDate.parse(startDate, DateTimeFormatter.ofPattern(DateUtil.FMT_DATE_1));
        }
        communityReportMapper.delCommunityLeaderDay(localDate);
        communityReportMapper.delCommunityLeaderSeven(localDate);
        communityReportMapper.delCommunityLeaderThirty(localDate);
        communityReportMapper.delCommunityLeaderMonth(localDate);
        communityReportMapper.insertCommunityLeaderDay(localDate, localDate, 0);
        communityReportMapper.insertCommunityLeaderSeven(localDate.minusDays(7), localDate, 0);
        communityReportMapper.insertCommunityLeaderThirty(localDate.minusDays(30), localDate, 0);
        if (LocalDate.now().getDayOfMonth() == 1) {
            communityReportMapper.insertCommunityLeaderMonth(LocalDate.now().minusMonths(1), localDate, 0);
        }

        communityReportMapper.insertCommunityLeaderDay(localDate, localDate, 1);
        communityReportMapper.insertCommunityLeaderSeven(localDate.minusDays(7), localDate, 1);
        communityReportMapper.insertCommunityLeaderThirty(localDate.minusDays(30), localDate, 1);
        if (LocalDate.now().getDayOfMonth() == 1) {
            communityReportMapper.insertCommunityLeaderMonth(LocalDate.now().minusMonths(1), localDate, 1);
        }
    }

    @Transactional
    public void insertOverview(String startDate){
        LocalDate localDate = LocalDate.now();
        if (StringUtils.isNotEmpty(startDate)){
            localDate = LocalDate.parse(startDate, DateTimeFormatter.ofPattern(DateUtil.FMT_DATE_1));
        }
        communityReportMapper.delCommunityOverviewDay(localDate);
        communityReportMapper.insertCommunityOverviewDay(localDate, localDate);
        communityReportMapper.insertCommunityOverviewDayBoss(localDate, localDate);
    }

    @Async
    @Transactional
    public void insertOverviewForStore(){
        int pageNum = 1;
        boolean dayFlag = true;
        while (dayFlag){
            int pageSize = 50;
            PageHelper.startPage(pageNum, pageSize);
            List<CommunityOverviewReport> communityOverviewReports = communityReportMapper.queryCommunityOverviewDayByYesterday();
            if (CollectionUtils.isNotEmpty(communityOverviewReports)){
                List<Long> storeId = communityOverviewReports.stream().map(CommunityOverviewReport::getStoreId).collect(Collectors.toList());
                List<CommunityOverviewReport> allCommunityOverviewReports =
                        communityReportMapper.queryCommunityOverview(storeId);
                Map<Long, CommunityOverviewReport> storeIdToEntityMap = new HashMap<>();
                if (CollectionUtils.isNotEmpty(allCommunityOverviewReports)){
                    storeIdToEntityMap.putAll(allCommunityOverviewReports.stream()
                            .collect(Collectors.toMap(CommunityOverviewReport::getStoreId, Function.identity())));
                }
                List<CommunityOverviewReport> insert = new ArrayList<>();
                List<Long> storeIds = new ArrayList<>();
                communityOverviewReports.forEach(item -> {
                    CommunityOverviewReport communityOverviewReport = storeIdToEntityMap.get(item.getStoreId());
                    if (Objects.isNull(communityOverviewReport)){
                        item.setCommissionPending(item.getCommissionPending().subtract(item.getCommissionReceived()));
                        item.setCommissionPendingPickup(item.getCommissionPendingPickup().subtract(item.getCommissionReceivedPickup()));
                        item.setCommissionPendingAssist(item.getCommissionPendingAssist().subtract(item.getCommissionReceivedAssist()));
                        insert.add(item);
                    } else {
                        communityOverviewReport.setPayOrderNum(communityOverviewReport.getPayOrderNum() + item.getPayOrderNum());
                        communityOverviewReport.setPayTotalPrice(communityOverviewReport.getPayTotalPrice().add(item.getPayTotalPrice()));
                        communityOverviewReport.setReturnNum(communityOverviewReport.getReturnNum() + item.getReturnNum());
                        communityOverviewReport.setReturnTotalPrice(communityOverviewReport.getReturnTotalPrice().add(item.getReturnTotalPrice()));
                        communityOverviewReport.setCustomerNum(0L);
                        communityOverviewReport.setLeaderNum(0L);
                        communityOverviewReport.setAssistOrderNum(communityOverviewReport.getAssistOrderNum() + item.getAssistOrderNum());
                        communityOverviewReport.setAssistOrderTotalPrice(communityOverviewReport.getAssistOrderTotalPrice().add(item.getAssistOrderTotalPrice()));
                        communityOverviewReport.setAssistReturnNum(communityOverviewReport.getAssistReturnNum() + item.getAssistReturnNum());
                        communityOverviewReport.setAssistReturnTotalPrice(communityOverviewReport.getAssistReturnTotalPrice().add(item.getAssistReturnTotalPrice()));
                        if (communityOverviewReport.getPayOrderNum() == 0){
                            communityOverviewReport.setAssistOrderRatio(BigDecimal.ZERO);
                        } else {
                            communityOverviewReport.setAssistOrderRatio(new BigDecimal(communityOverviewReport.getAssistOrderNum()).divide(new BigDecimal(communityOverviewReport.getPayOrderNum()),2, RoundingMode.HALF_UP));
                        }
                        communityOverviewReport.setLeaderCustomerNum(communityOverviewReport.getLeaderCustomerNum() + (item.getCustomerNum()));
                        communityOverviewReport.setCommissionReceived(communityOverviewReport.getCommissionReceived().add(item.getCommissionReceived()));
                        communityOverviewReport.setCommissionReceivedPickup(communityOverviewReport.getCommissionReceivedPickup().add(item.getCommissionReceivedPickup()));
                        communityOverviewReport.setCommissionReceivedAssist(communityOverviewReport.getCommissionReceivedAssist().add(item.getCommissionReceivedAssist()));
                        communityOverviewReport.setCommissionPending(communityOverviewReport.getCommissionPending().add(item.getCommissionPending()).subtract(item.getCommissionReceived()));
                        communityOverviewReport.setCommissionPendingPickup(communityOverviewReport.getCommissionPendingPickup().add(item.getCommissionPendingPickup()).subtract(item.getCommissionReceivedPickup()));
                        communityOverviewReport.setCommissionPendingAssist(communityOverviewReport.getCommissionPendingAssist().add(item.getCommissionPendingAssist()).subtract(item.getCommissionReceivedAssist()));
                        insert.add(communityOverviewReport);
                        storeIds.add(communityOverviewReport.getStoreId());
                    }
                });
                if (CollectionUtils.isNotEmpty(storeIds)){
                    log.info("CommunityService.insertOverviewForStore:删除店铺:{}", storeIds);
                    communityReportMapper.delCommunityOverview(storeIds);
                }
                log.info("CommunityService.insertOverviewForStore:插入总数:{}", insert.size());
                communityReportMapper.insertCommunityOverview(insert);
                if (communityOverviewReports.size() < pageSize) {
                    dayFlag = false;
                } else {
                    pageNum++;
                }
            } else {
                dayFlag = false;
            }
        }
    }

    @Async
    @Transactional
    public void insertOverviewForBoss(){
        // boss
        CommunityOverviewReport communityOverviewReport = communityReportMapper.queryForSum();
        if (communityOverviewReport.getPayOrderNum() == 0){
            communityOverviewReport.setAssistOrderRatio(BigDecimal.ZERO);
        } else {
            communityOverviewReport.setAssistOrderRatio(new BigDecimal(communityOverviewReport.getAssistOrderNum()).divide(new BigDecimal(communityOverviewReport.getPayOrderNum()),2, RoundingMode.HALF_UP));
        }
        communityOverviewReport.setCustomerNum(communityReportMapper.queryCustomerNum());
        communityOverviewReport.setLeaderNum(communityReportMapper.queryLeaderNum());
        communityReportMapper.delCommunityOverview(Collections.singletonList(-1L));
        communityReportMapper.insertCommunityOverview(Collections.singletonList(communityOverviewReport));
    }

    public void insertOverviewForAll(){
        communityReportMapper.delOverviewForAll();
        communityReportMapper.insertOverviewForAll();
    }

    public CommunityOverviewReport queryByBoss(){
        return communityReportMapper.queryByBoss();
    }
}
