package com.wanmi.ares.report.paymember.service;

import com.wanmi.ares.base.PageRequest;
import com.wanmi.ares.base.SortType;
import com.wanmi.ares.common.DateRange;
import com.wanmi.ares.common.WeekRange;
import com.wanmi.ares.enums.QueryDateCycle;
import com.wanmi.ares.report.customer.dao.CustomerMapper;
import com.wanmi.ares.report.paymember.dao.PayMemberAreaDistributeReportMapper;
import com.wanmi.ares.report.paymember.dao.PayMemberGrowReportMapper;
import com.wanmi.ares.report.paymember.dao.PayMemberMapper;
import com.wanmi.ares.report.paymember.model.root.PayMemberAreaDistributeReport;
import com.wanmi.ares.report.paymember.model.root.PayMemberGrowReport;
import com.wanmi.ares.request.paymember.PayMemberGrowthRequest;
import com.wanmi.ares.request.paymember.PayMemberQueryRequest;
import com.wanmi.ares.request.paymember.PayMemberTrendRequest;
import com.wanmi.ares.response.*;
import com.wanmi.ares.utils.DateUtil;
import com.wanmi.ares.utils.KsBeanUtil;
import com.wanmi.ares.view.paymember.*;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author xuyunpeng
 * @className PayMemberService
 * @description
 * @date 2022/5/25 2:33 PM
 **/
@Service
public class PayMemberQueryService {

    @Autowired
    private PayMemberAreaDistributeReportMapper payMemberAreaDistributeReportMapper;

    @Autowired
    private PayMemberMapper payMemberMapper;

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private PayMemberGrowReportMapper payMemberGrowReportMapper;


    private static Map<String, String> sortField = new HashMap<>();

    static {
        sortField.put("allCount", "all_count");
        sortField.put("dayGrowthCount", "day_growth_count");
        sortField.put("dayRenewalCount", "day_renewal_count");
        sortField.put("dayOvertimeCount", "day_overtime_count");
        sortField.put("baseDate", "base_date");
    }

    /**
     * 查询付费会员-区域数据
     * @param request
     * @return
     */
    public PayMemberAreaViewResponse queryAreaView(PayMemberQueryRequest request) {
        LocalDate date = DateUtil.parseByDateCycle(request.getDateCycle());
        List<PayMemberAreaDistributeReport> list = payMemberAreaDistributeReportMapper.query(date);
        List<PayMemberAreaView> viewList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(list)) {
            list.forEach(i -> viewList.add(new PayMemberAreaView(i.getCityId().toString(), i.getNum())));
        }
        long payMemberTotal = this.totalCount(request);
        //会员占比
        BigDecimal customerTotal = new BigDecimal(customerMapper.queryTotal(date));
        BigDecimal proportion = BigDecimal.valueOf(payMemberTotal)
                .divide(customerTotal, 3, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));
        return PayMemberAreaViewResponse.builder().areaViews(viewList).total(payMemberTotal).proportion(proportion).build();
    }

    /**
     * 查询付费会员总数
     * @param request
     * @return
     */
    public long totalCount(PayMemberQueryRequest request) {
        LocalDate date;
        if (StringUtils.isNotBlank(request.getMonth())) {
            date = DateUtil.parseByMonth(request.getMonth());
        } else {
            date = DateUtil.parseByDateCycle(request.getDateCycle());
        }
        long total = payMemberMapper.queryTotal(date);
        return total;

    }

    /**
     * 数据概况
     * @return
     */
    public PayMemberOverViewResponse overView() {
        LocalDate date = DateUtil.parseByDateCycle(QueryDateCycle.TODAY);
        long payMemberTotal = payMemberMapper.queryTotal(date);
        BigDecimal customerTotal = new BigDecimal(customerMapper.queryTotal(date));
        BigDecimal proportion = null;
        if (customerTotal.compareTo(BigDecimal.ZERO) != 0) {
            proportion = new BigDecimal(payMemberTotal).divide(customerTotal, 4, RoundingMode.HALF_UP).multiply(new BigDecimal(100)).setScale(1, RoundingMode.HALF_UP);
        }

        return PayMemberOverViewResponse.builder().total(payMemberTotal).proportion(proportion).build();
    }

    /**
     * 查询会员新增报表数据
     * @param request
     * @return
     */
    public PayMemberGrowthNewResponse queryGrowthNewList(PayMemberGrowthRequest request) {
        //填充参数
        dealRequest(request);
        //查询总数量
        int total = payMemberGrowReportMapper.queryGrowthTotal(request);
        //查询分页数据
        List<PayMemberGrowReport> growReport = new ArrayList<>();
        if (total > NumberUtils.INTEGER_ZERO) {
            growReport = queryGrowthList(request);
        }
        List<PayMemberGrowthNewView> reports = growReport.stream()
                .map(r -> KsBeanUtil.convert(r, PayMemberGrowthNewView.class))
                .collect(Collectors.toList());

        return PayMemberGrowthNewResponse.builder()
                .pageNum(request.getPageNum())
                .pageSize(request.getPageSize())
                .growthViewList(reports)
                .total(total)
                .build();
    }

    /**
     * 查询会员续费报表数据
     * @param request
     * @return
     */
    public PayMemberGrowthRenewalResponse queryGrowthRenewalList(PayMemberGrowthRequest request) {
        //填充参数
        dealRequest(request);
        //查询总数量
        int total = payMemberGrowReportMapper.queryGrowthTotal(request);
        //查询分页数据
        List<PayMemberGrowReport> growReport = new ArrayList<>();
        if (total > NumberUtils.INTEGER_ZERO) {
            growReport = queryGrowthList(request);
        }
        List<PayMemberGrowthRenewalView> reports = growReport.stream()
                .map(r -> KsBeanUtil.convert(r, PayMemberGrowthRenewalView.class))
                .collect(Collectors.toList());

        return PayMemberGrowthRenewalResponse.builder()
                .pageNum(request.getPageNum())
                .pageSize(request.getPageSize())
                .growthViewList(reports)
                .total(total)
                .build();
    }

    /**
     * 查询会员趋势数据
     * @param request
     * @return
     */
    public List<PayMemberGrowReport> queryGrowthTrend(PayMemberTrendRequest request) {
        DateRange dateRange = DateUtil.parseDateRange(request.getQueryDateCycle(), request.getMonth());
        List<PayMemberGrowReport> reports = new ArrayList<>();
        //是否是按周
        if (!request.getWeekly()) {
            //查询趋势数据-按天
            reports = payMemberGrowReportMapper
                    .findAllGrowReportByDate(dateRange.getStartDate(), dateRange.getEndDate());
            reports.forEach(r -> {
                LocalDate baseDate = LocalDate.parse(r.getBaseDate());
                r.setBaseDate(DateUtil.formatLocalDate(baseDate, DateUtil.FMT_DATE_3)
                        + "/" + DateUtil.getWeekStr(baseDate));
            });
            return reports;
        } else {
            //查询趋势数据-按周
            List<WeekRange> weekRanges = DateUtil.getWeekRanges(LocalDate.parse(dateRange.getStartDate()), LocalDate.parse(dateRange.getEndDate()));
            for (WeekRange weekRange : weekRanges) {
                PayMemberGrowReport report = payMemberGrowReportMapper
                        .findAllGrowReportByWeek(weekRange.getFirstDay().toString(), weekRange.getEndDay().toString());
                if (report != null) {
                    report.setBaseDate(DateUtil.formatLocalDate(weekRange.getFirstDay(), DateUtil.FMT_DATE_3)
                            + "-" + DateUtil.formatLocalDate(weekRange.getEndDay(), DateUtil.FMT_DATE_3));
                    reports.add(report);
                }
            }
            return reports;
        }
    }

    /**
     * 查询增长报表数据
     * @param request
     * @return
     */
    public List<PayMemberGrowReport> queryGrowthList(PayMemberGrowthRequest request) {
        PageRequest pageRequest = new PageRequest();
        pageRequest.setStart((request.getPageNum() - 1) * request.getPageSize());
        pageRequest.setPageSize(request.getPageSize());
        return payMemberGrowReportMapper.findAllGrowReport(request, pageRequest);
    }

    /**
     * 填充参数
     * @param request
     */
    private void dealRequest(PayMemberGrowthRequest request){
        DateRange dateRange = DateUtil.parseDateRange(request.getDateCycle(), request.getMonth());
        //排序字段
        if (Objects.nonNull(request.getSortType())) {
            if (SortType.ASC == request.getSortType()) {
                request.setSortTypeText("ASC");
            } else {
                request.setSortTypeText("DESC");
            }
        }
        if (Objects.nonNull(request.getSortField()) && Objects.nonNull(sortField.get(request.getSortField()))) {
            request.setSortField(sortField.get(request.getSortField()));
        } else {
            //默认安装日期倒叙排
            request.setSortField(sortField.get("baseDate"));
            request.setSortTypeText("DESC");
        }
        request.setStartDate(dateRange.getStartDate());
        request.setEndDate(dateRange.getEndDate());
    }
}
