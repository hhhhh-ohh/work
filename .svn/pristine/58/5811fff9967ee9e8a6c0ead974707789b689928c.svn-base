package com.wanmi.ares.report.employee.service;

import com.wanmi.ares.report.customer.dao.CustomerMapper;
import com.wanmi.ares.report.employee.dao.EmployeeClientReportMapper;
import com.wanmi.ares.report.employee.dao.EmployeePerformanceReportMapper;
import com.wanmi.ares.report.employee.model.root.EmployeeClientReport;
import com.wanmi.ares.report.employee.model.root.EmployeePerformanceReport;
import com.wanmi.ares.report.trade.model.request.TradeCollect;
import com.wanmi.ares.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.Resource;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.IntSupplier;
import java.util.stream.Collectors;

/**
 * <p>基于业务员维度的报表操作Service</p>
 * Created by of628-wenzhi on 2017-09-26-下午1:59.
 */
@Service
@Slf4j
public class EmployeeReportGenerateService {

    @Resource
    private EmployeePerformanceReportMapper performanceMapper;

    @Resource
    private CustomerMapper customerMapper;
    @Resource
    private EmployeeReportGenerateService reportGenerateService;


    public void generateData(String param,LocalDate now) {
        if(StringUtils.isEmpty(param)){
            return ;
        }
        if(param.contains("0")){
            reportGenerateService.generateDay(now);
        }
        if(param.contains("1")){
            reportGenerateService.generateYesterday(now);
        }
        if(param.contains("2")){
            reportGenerateService.generateSeven(now);
        }
        if(param.contains("3")){
            reportGenerateService.generateThirty(now);
        }
        if(param.contains("4")){
            reportGenerateService.generateMonth(now);
        }
    }

    @Transactional(isolation= Isolation.READ_UNCOMMITTED,rollbackFor = Exception.class)
    public void generateYesterday(LocalDate now){
        LocalDate yesterday = now.minusDays(1);
        //清空昨日报表
        performanceMapper.deleteTodayDay(yesterday);
        //生成昨日报表
        List<EmployeePerformanceReport> employeePerformanceReports = performanceMapper.collectThirdEmployeeTrade(DateUtil.computeDateIntervalDay(yesterday));
        employeePerformanceReports.addAll(performanceMapper.collectBossEmployeeTrade(DateUtil.computeDateIntervalDay(yesterday)));
        if (CollectionUtils.isEmpty(employeePerformanceReports)) {
            return;
        }

        employeePerformanceReports = employeePerformanceReports.stream()
                .filter(f -> f.getCompanyId() != null)
                .map(i-> {i.setTargetDate(yesterday);return i;})
                .collect(Collectors.toList());

        performanceMapper.insertDay(employeePerformanceReports);
    }

    @Transactional(isolation= Isolation.READ_UNCOMMITTED,rollbackFor = Exception.class)
    public void generateDay(LocalDate now){
        //清空今日报表
        performanceMapper.deleteTodayDay(now);
        //生成今天报表
        List<EmployeePerformanceReport> employeePerformanceReports = performanceMapper.collectThirdEmployeeTrade(DateUtil.computeDateIntervalDay(now));
        employeePerformanceReports.addAll(performanceMapper.collectBossEmployeeTrade(DateUtil.computeDateIntervalDay(now)));
        if (CollectionUtils.isEmpty(employeePerformanceReports)) {
            return;
        }

        employeePerformanceReports = employeePerformanceReports.stream()
                .filter(f -> f.getCompanyId() != null)
                .collect(Collectors.toList());

        performanceMapper.insertToday(employeePerformanceReports);
    }

    @Transactional(isolation= Isolation.READ_UNCOMMITTED,rollbackFor = Exception.class)
    public void generateSeven(LocalDate now){
        //清空最近日天报表
        performanceMapper.clear7Days();
        //生成最近七天报表
        List<EmployeePerformanceReport> employeePerformanceReports = performanceMapper.collectThirdEmployeeTrade(DateUtil.computeDateIntervalSeven(now));
        employeePerformanceReports.addAll(performanceMapper.collectBossEmployeeTrade(DateUtil.computeDateIntervalSeven(now)));
        if (CollectionUtils.isEmpty(employeePerformanceReports)) {
            return;
        }

        employeePerformanceReports = employeePerformanceReports.stream()
                .filter(f -> f.getCompanyId() != null)
                .collect(Collectors.toList());

        performanceMapper.insert7days(employeePerformanceReports);
    }

    @Transactional(isolation= Isolation.READ_UNCOMMITTED,rollbackFor = Exception.class)
    public void generateThirty(LocalDate now){

        //清空最近30日天报表
        performanceMapper.clear30Days();
        TradeCollect tradeCollect = DateUtil.computeDateIntervalThirtyDay(now);
        //生成最近30天报表
        List<EmployeePerformanceReport> employeePerformanceReports = performanceMapper.collectThirdEmployeeTrade(tradeCollect);
        employeePerformanceReports.addAll(performanceMapper.collectBossEmployeeTrade(tradeCollect));
        if (CollectionUtils.isEmpty(employeePerformanceReports)) {
            return;
        }

        employeePerformanceReports = employeePerformanceReports.stream()
                .filter(f -> f.getCompanyId() != null)
                .collect(Collectors.toList());

        performanceMapper.insert30Days(employeePerformanceReports);
    }

    @Transactional(isolation= Isolation.READ_UNCOMMITTED,rollbackFor = Exception.class)
    public void generateMonth(LocalDate now){
        //生成统计数据
        for (int i = 1; i < 7; i++) {
            //删除过期数据
            String oldYearMonth = DateUtil.yearMonth(now.minusMonths(i));
            performanceMapper.deleteRecentMonth(oldYearMonth);
            TradeCollect tradeCollect = DateUtil.computeDateIntervalMonthDay(i, now);
            List<EmployeePerformanceReport> employeePerformanceReports = performanceMapper.collectThirdEmployeeTrade(tradeCollect);
            employeePerformanceReports.addAll(performanceMapper.collectBossEmployeeTrade(tradeCollect));
            if (CollectionUtils.isEmpty(employeePerformanceReports)) {
                return;
            }
            employeePerformanceReports = employeePerformanceReports.stream().filter(f -> f.getCompanyId() != null).map(e -> {
                e.setTargetDate(tradeCollect.getEndDate());
                return e;}).collect(Collectors.toList());
            performanceMapper.insertMonth(employeePerformanceReports);
        }
    }
}

