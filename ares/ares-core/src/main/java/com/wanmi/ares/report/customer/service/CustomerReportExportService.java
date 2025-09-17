package com.wanmi.ares.report.customer.service;

import com.wanmi.ares.base.PageRequest;
import com.wanmi.ares.report.base.model.ExportQuery;
import com.wanmi.ares.report.customer.dao.CustomerGrowthReportMapper;
import com.wanmi.ares.report.customer.model.root.CustomerGrowthReport;
import com.wanmi.ares.report.service.ExportBaseService;
import com.wanmi.ares.request.customer.CustomerGrowthReportRequest;
import com.wanmi.ares.source.service.StoreService;
import com.wanmi.ares.utils.DateUtil;
import com.wanmi.ares.utils.excel.Column;
import com.wanmi.ares.utils.excel.ExcelHelper;
import com.wanmi.ares.utils.excel.impl.SpelColumnRender;
import com.wanmi.ares.utils.osd.OsdService;
import com.wanmi.sbc.common.base.BaseResponse;
import jodd.util.RandomString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

/**
 * 会员模块导出报表
 */
@Service
@Slf4j
@Transactional(readOnly = true)
public class CustomerReportExportService implements ExportBaseService {

    @Autowired
    private CustomerGrowthReportMapper customerGrowthReportMapper;

    @Autowired
    private OsdService osdService;

    @Autowired
    private StoreService storeService;

    private static final String EXCEL_TYPE = "%s.xls";

    /**
     * 导出列字段
     * @return
     */
    public Column[] getPlatformGrowthColumn(){
        return new Column[]{
                new Column("日期", new SpelColumnRender<CustomerGrowthReport>("baseDate")),
                new Column("客户总数", new SpelColumnRender<CustomerGrowthReport>("customerAllCount")),
                new Column("新增客户数", new SpelColumnRender<CustomerGrowthReport>("customerDayGrowthCount")),
                new Column("注册客户数", new SpelColumnRender<CustomerGrowthReport>("customerDayRegisterCount")),
        };
    }

    /**
     * 导出列字段
     * @return
     */
    public Column[] getCustomerGrowthColumn(){
        return new Column[]{
                new Column("日期", new SpelColumnRender<CustomerGrowthReport>("baseDate")),
                new Column("客户总数", new SpelColumnRender<CustomerGrowthReport>("customerAllCount")),
                new Column("新增客户数", new SpelColumnRender<CustomerGrowthReport>("customerDayGrowthCount")),
        };
    }

    /**
     * 会员增长报表格式
     *
     * @param customerGrowthReports customerGrowthReports
     * @param excelHelper           excelHelper
     */
    private void customerGrowthSheet(List<CustomerGrowthReport> customerGrowthReports, ExcelHelper excelHelper, boolean isPlatformRequest) {
        if (isPlatformRequest) {
            excelHelper.addSheet("客户增长报表", getPlatformGrowthColumn(), customerGrowthReports);
        } else {
            excelHelper.addSheet("客户增长报表", getCustomerGrowthColumn(), customerGrowthReports);
        }
    }

    /**
     * 计算页码
     *
     * @param count
     * @param size
     * @return
     */
    private int calPage(int count, int size) {
        int page = count / size;
        if (count % size == 0) {
            return page;
        } else {
            return page + 1;
        }
    }

    @Override
    public BaseResponse exportReport(ExportQuery exportQuery) throws IOException {
        List<String> fileUrl = Lists.newArrayList();
        CustomerGrowthReportRequest reportRequest = new CustomerGrowthReportRequest();
        reportRequest.setCompanyId(exportQuery.getCompanyId() == null ? "0" : exportQuery.getCompanyId());
        reportRequest.setStartDate(exportQuery.getDateFrom()).setEnDate(exportQuery.getDateTo());
        reportRequest.setSortField("base_date");
        reportRequest.setSortTypeText("DESC");
        PageRequest pageRequest = new PageRequest();
        pageRequest.setPageSize(exportQuery.getSize());

        boolean isPlatformRequest = "0".equals(exportQuery.getCompanyId());

        int storeType = exportQuery.getStoreSelectType() == null ? 0 : exportQuery.getStoreSelectType().toValue();

        int count = customerGrowthReportMapper.countCustomerReport(reportRequest);

        if ("0".equals(reportRequest.getCompanyId()) && storeType != 0){
            count = customerGrowthReportMapper.countCustomerReportByStoreType(reportRequest,storeType);
        }

        LocalDate endDate = DateUtil.parse2Date(exportQuery.getDateTo(), DateUtil.FMT_DATE_1);
        String randomData = RandomString.get().randomAlpha(4);
        String commonFileName = String.format("customer/growth/%s/%s客户增长报表_%s_%s-%s", DateUtil.format(endDate,
                DateUtil.FMT_MONTH_2), storeService.getStoreName(exportQuery), exportQuery.getDateFrom(),
                exportQuery.getDateTo(), randomData);
        commonFileName = osdService.getFileRootPath().concat(commonFileName);
        if (count < 1) {
            String fileName = String.format(EXCEL_TYPE, commonFileName);
            //不存在，则上传
            if (!osdService.existsFiles(fileName)) {
                ExcelHelper excelHelper = new ExcelHelper();
                customerGrowthSheet(Lists.newArrayList(), excelHelper, isPlatformRequest);
                ByteArrayOutputStream emptyStream = new ByteArrayOutputStream();
                excelHelper.write(emptyStream);
                osdService.uploadExcel(emptyStream, fileName);
            }
            fileUrl.add(fileName);
            return BaseResponse.success(fileUrl);
        }

        int page = calPage(count, exportQuery.getSize());

        for (int i = 0; i < page; i++) {
            pageRequest.setStart(i * exportQuery.getSize());
            List<CustomerGrowthReport> customerGrowthReports = customerGrowthReportMapper.findAllCustomerGrowReport(reportRequest, pageRequest);

            if ("0".equals(reportRequest.getCompanyId()) && storeType != 0){
                customerGrowthReports = customerGrowthReportMapper.findAllCustomerGrowReportByStoreType(reportRequest, pageRequest, storeType);
            }

            if (CollectionUtils.isEmpty(customerGrowthReports)) {
                return BaseResponse.success(fileUrl);
            }

            ExcelHelper excelHelper = new ExcelHelper();
            customerGrowthSheet(customerGrowthReports, excelHelper, isPlatformRequest);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            excelHelper.write(byteArrayOutputStream);
            String fileName = i == 0 ? String.format(EXCEL_TYPE, commonFileName) : String.format("%s(%s).xls", commonFileName,
                    String.valueOf(i));
            osdService.uploadExcel(byteArrayOutputStream, fileName);
            fileUrl.add(fileName);
        }

        return BaseResponse.success(fileUrl);
    }
}
