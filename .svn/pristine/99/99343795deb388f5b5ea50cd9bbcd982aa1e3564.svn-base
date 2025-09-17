package com.wanmi.ares.provider.impl;

import com.wanmi.ares.report.customer.service.CustomerGrowthGenerateService;
import com.wanmi.ares.report.employee.service.ReplayStoreCustomerRelaService;
import com.wanmi.ares.report.trade.service.TradeReportService;
import com.wanmi.ares.utils.DateUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;
import java.time.LocalDate;

/**
 * <p></p>
 * Created by of628-wenzhi on 2019-12-16-下午10:52.
 */
@RestController
@RequestMapping("/test")
public class TradeTestController {
    @Resource
    private TradeReportService tradeReportService;

    @Resource
    private CustomerGrowthGenerateService growthGenerateService;

    @Resource
    private ReplayStoreCustomerRelaService replayStoreCustomerRelaService;

    private  static  final String SUCCESS = "success";
    @GetMapping("/trade")
    public String generateTrade(String date){
        LocalDate localDate = DateUtil.parseLocalDate(date,DateUtil.FMT_DATE_1);
        try {
            tradeReportService.generateData1(localDate);
        } catch (Exception e) {
            return "fail";
        }

        return SUCCESS;
    }

    @GetMapping("/grow")
    public String generateGrow(String date){
        LocalDate localDate = DateUtil.parseLocalDate(date,DateUtil.FMT_DATE_1);
        try {
            growthGenerateService.generateAllCustomerGrowth(localDate);
        } catch (Exception e) {
            return "fail";
        }
        return SUCCESS;
    }

    @GetMapping("/employee/client")
    public String generateEmployeeClient(String date){
        LocalDate localDate = DateUtil.parseLocalDate(date,DateUtil.FMT_DATE_1);
        try {
            replayStoreCustomerRelaService.generateDay(localDate);
        } catch (Exception e) {
            return "fail";
        }
        return SUCCESS;
    }

}
