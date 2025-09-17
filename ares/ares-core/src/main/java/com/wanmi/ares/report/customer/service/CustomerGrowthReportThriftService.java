package com.wanmi.ares.report.customer.service;

import com.wanmi.ares.base.PageRequest;
import com.wanmi.ares.base.SortType;
import com.wanmi.ares.common.DateRange;
import com.wanmi.ares.common.WeekRange;
import com.wanmi.ares.interfaces.customer.CustomerGrowthReportService;
import com.wanmi.ares.report.customer.dao.CustomerGrowthReportMapper;
import com.wanmi.ares.report.customer.dao.ReplayStoreMapper;
import com.wanmi.ares.report.customer.model.root.CustomerGrowthReport;
import com.wanmi.ares.request.customer.CustomerGrowthReportRequest;
import com.wanmi.ares.request.customer.CustomerTrendQueryRequest;
import com.wanmi.ares.utils.DateUtil;
import com.wanmi.ares.view.customer.CustomerGrowthPageView;
import com.wanmi.ares.view.customer.CustomerGrowthReportView;
import com.wanmi.ares.view.customer.CustomerGrowthTrendView;
import org.assertj.core.util.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.Collections.EMPTY_LIST;
import static java.util.Collections.emptyList;

/**
 * 会员报表服务
 */
@Service
public class CustomerGrowthReportThriftService implements CustomerGrowthReportService.Iface {

    @Autowired
    private CustomerGrowthReportMapper customerGrowthReportMapper;
    @Autowired
    private ReplayStoreMapper storeMapper;

    /**
     * 这种方式待优化，但是可以防止注入风险
     */
    private static Dictionary<String, String> sortField =
            new Hashtable();

    static {
        sortField.put("customerAllCount", "customer_all_count");
        sortField.put("customerDayGrowthCount", "customer_day_growth_count");
        sortField.put("customerDayRegisterCount", "customer_day_register_count");
        sortField.put("baseDate", "base_date");
    }

    /**
     * 查询客户增长报表
     *
     * @param customerGrowthReportRequest customerGrowthReportRequest
     * @return CustomerGrowthPageView
     */
    @Override
    public CustomerGrowthPageView queryCustomerGrouthList(CustomerGrowthReportRequest customerGrowthReportRequest) {
        CustomerGrowthPageView customerGrowthPageView = new CustomerGrowthPageView();
        customerGrowthReportRequest.setStartDate(null);
        customerGrowthReportRequest.setEnDate(null);
        if (customerGrowthReportRequest.getCompanyId() == null || customerGrowthReportRequest.getCompanyId().equals("0")) {
            customerGrowthReportRequest.setCompanyId("0");
        }
//        else {
//            List<Store> stores = storeMapper.queryByCompanyIds(Lists.newArrayList(customerGrowthReportRequest.getCompanyId()));
//            if (null != stores && !stores.isEmpty()) {
//                Store store = stores.get(0);
//                if (null != store.getCompanyType() && store.getCompanyType() == 0 && store.getStoreType() != 2) {
//                    customerGrowthReportRequest.setCompanyId("0");
//                }
//            }
//        }

        int storeType = customerGrowthReportRequest.getStoreSelectType() == null ? 0 : customerGrowthReportRequest.getStoreSelectType().toValue();

        DateRange dateRange = DateUtil.parseDateRange(customerGrowthReportRequest.getDateCycle(), customerGrowthReportRequest.getMonth());
        //排序字段
        if (Objects.nonNull(customerGrowthReportRequest.getSortType())) {
            if (SortType.ASC == customerGrowthReportRequest.getSortType()) {
                customerGrowthReportRequest.setSortTypeText("ASC");
            } else {
                customerGrowthReportRequest.setSortTypeText("DESC");
            }
        }
        if (Objects.nonNull(customerGrowthReportRequest.getSortField()) && Objects.nonNull(sortField.get(customerGrowthReportRequest.getSortField()))) {
            customerGrowthReportRequest.setSortField(sortField.get(customerGrowthReportRequest.getSortField()));
        } else {
            //默认安装日期倒叙排
            customerGrowthReportRequest.setSortField(sortField.get("baseDate"));
            customerGrowthReportRequest.setSortTypeText("DESC");
        }
        customerGrowthReportRequest.setStartDate(dateRange.getStartDate());
        customerGrowthReportRequest.setEnDate(dateRange.getEndDate());


        Integer allCount = customerGrowthReportMapper.countCustomerReport(customerGrowthReportRequest);

        if ("0".equals(customerGrowthReportRequest.getCompanyId()) && storeType != 0){
            allCount = customerGrowthReportMapper.countCustomerReportByStoreType(customerGrowthReportRequest,storeType);
        }

        if (allCount <= 0) {
            customerGrowthPageView.setGrouthList(EMPTY_LIST);
            customerGrowthPageView.setTotal(0L);
            return customerGrowthPageView;
        }
        PageRequest pageRequest = new PageRequest();
        pageRequest.setStart((customerGrowthReportRequest.getPageNum() - 1) * customerGrowthReportRequest.getPageSize());
        pageRequest.setPageSize(customerGrowthReportRequest.getPageSize());

        List<CustomerGrowthReport> reports;

        reports = customerGrowthReportMapper.findAllCustomerGrowReport(customerGrowthReportRequest, pageRequest);

        if ("0".equals(customerGrowthReportRequest.getCompanyId()) && storeType != 0){
            reports = customerGrowthReportMapper.findAllCustomerGrowReportByStoreType(customerGrowthReportRequest, pageRequest, storeType);
        }

        List<CustomerGrowthReportView> customerGrowthReportViews = reports.stream().map(customerGrowthReport -> {
            CustomerGrowthReportView reportView = new CustomerGrowthReportView();
            BeanUtils.copyProperties(customerGrowthReport, reportView);
            return reportView;
        }).collect(Collectors.toList());

        customerGrowthPageView.setGrouthList(customerGrowthReportViews);

        customerGrowthPageView.setTotal(allCount);
        return customerGrowthPageView;
    }

    /**
     * 增长趋势查询
     *
     * @param request request
     * @return
     */
    @Override
    public List<CustomerGrowthTrendView> queryCustomerTrendList(CustomerTrendQueryRequest request) {
        List<CustomerGrowthTrendView> trendViews = Lists.newArrayList();
        DateRange dateRange = DateUtil.parseDateRange(request.getQueryDateCycle(), request.getMonth());

        //是否是按周
        if (!request.isWeekly()) {
            List<CustomerGrowthReport> customerGrowthReports;
            customerGrowthReports = findCustomerGrowthReports(request, dateRange);

            if (CollectionUtils.isEmpty(customerGrowthReports)) {
                return emptyList();
            }
            return customerGrowthReports.stream().map(customerGrowthReport -> {
                CustomerGrowthTrendView customerGrowthTrendView = new CustomerGrowthTrendView();
                LocalDate baseDate = LocalDate.parse(customerGrowthReport.getBaseDate());
                customerGrowthTrendView.setXValue(DateUtil.formatLocalDate(baseDate, DateUtil.FMT_DATE_3)
                        + "/" + DateUtil.getWeekStr(baseDate));
                BeanUtils.copyProperties(customerGrowthReport, customerGrowthTrendView);
                return customerGrowthTrendView;
            }).collect(Collectors.toList());
        } else {
            //按周查询
            List<WeekRange> weekRanges = DateUtil.getWeekRanges(LocalDate.parse(dateRange.getStartDate()), LocalDate.parse(dateRange.getEndDate()));
            weekRanges.forEach(weekRange -> {
                List<CustomerGrowthReport> customerGrowthReports =
                        findCustomerGrowthReports(request, new DateRange(DateUtil.formatLocalDate(weekRange.getFirstDay(), DateUtil.FMT_DATE_1),
                                DateUtil.formatLocalDate(weekRange.getEndDay(), DateUtil.FMT_DATE_1)));

                CustomerGrowthTrendView customerGrowthTrendView = new CustomerGrowthTrendView();
                //每天数据肯定会有，没有是有问题的
                if (!CollectionUtils.isEmpty(customerGrowthReports)) {
                    customerGrowthTrendView.setXValue(DateUtil.formatLocalDate(weekRange.getFirstDay(), DateUtil.FMT_DATE_3)
                            + "-" + DateUtil.formatLocalDate(weekRange.getEndDay(), DateUtil.FMT_DATE_3));
                    List<CustomerGrowthReport> reports = customerGrowthReports.stream().filter(customerGrowthReport ->
                            customerGrowthReport.getBaseDate().equals(DateUtil.formatLocalDate(weekRange.getEndDay(), DateUtil.FMT_DATE_1)))
                            .collect(Collectors.toList());
                    customerGrowthTrendView.setCustomerAllCount(reports.isEmpty() ? 0L : customerGrowthReports.get(0).getCustomerAllCount());
                    customerGrowthTrendView.setCustomerDayRegisterCount(customerGrowthReports.stream().mapToLong(CustomerGrowthReport::getCustomerDayRegisterCount).sum());
                    customerGrowthTrendView.setCustomerDayGrowthCount(customerGrowthReports.stream().mapToLong(CustomerGrowthReport::getCustomerDayGrowthCount).sum());
                    trendViews.add(customerGrowthTrendView);
                }
            });
            return trendViews;
        }
    }

    /**
     * 区分平台/店铺的查询
     *
     * @param request   request
     * @param dateRange dateRange
     * @return customerGrowthReports
     */
    private List<CustomerGrowthReport> findCustomerGrowthReports(CustomerTrendQueryRequest request, DateRange dateRange) {
        List<CustomerGrowthReport> customerGrowthReports;
        if (request.getCompanyInfoId() == null) {
            request.setCompanyInfoId("0");
        }
//        else {
//            List<Store> stores = storeMapper.queryByCompanyIds(Lists.newArrayList(request.getCompanyInfoId()));
//            if (null != stores && !stores.isEmpty()) {
//                Store store = stores.get(0);
//                if (store.getCompanyType() == 0 && store.getStoreType() != 2) {
//                    request.setCompanyInfoId("0");
//                }
//            }
//        }
        int storeType = request.getStoreSelectType() == null ? 0 : request.getStoreSelectType().toValue();

        String companyInfoId = request.getCompanyInfoId();

        customerGrowthReports = customerGrowthReportMapper
                .findCustomerGrowthReportByDate(dateRange.getStartDate(), dateRange.getEndDate(), request.getCompanyInfoId());

        if ("0".equals(companyInfoId) && storeType != 0) {
            customerGrowthReports = customerGrowthReportMapper
                    .findCustomerGrowthReportByDateAndStoreType(dateRange.getStartDate(), dateRange.getEndDate(), storeType);

        }

        return customerGrowthReports;
    }
}
