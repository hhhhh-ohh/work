package com.wanmi.ares.report.customer.service;

import com.wanmi.ares.report.base.model.ExportQuery;
import com.wanmi.ares.report.base.service.CustomerTradeBaseService;
import com.wanmi.ares.report.customer.dao.CustomerOrderReportMapper;
import com.wanmi.ares.report.customer.dao.ReplayStoreMapper;
import com.wanmi.ares.report.customer.model.request.CustomerOrderDataRequest;
import com.wanmi.ares.report.customer.model.root.CustomerReport;
import com.wanmi.ares.report.service.ExportBaseService;
import com.wanmi.ares.source.service.StoreService;
import com.wanmi.ares.utils.Constants;
import com.wanmi.ares.utils.DateUtil;
import com.wanmi.ares.utils.excel.Column;
import com.wanmi.ares.utils.excel.ExcelHelper;
import com.wanmi.ares.utils.excel.impl.SpelColumnRender;
import com.wanmi.ares.utils.osd.OsdService;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.LogOutStatus;

import jodd.util.RandomString;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * 会员模块导出报表
 */
@Service
@Slf4j
@Transactional(readOnly = true)
public class CustomerTradeExportService implements ExportBaseService {

    @Autowired
    private CustomerOrderReportMapper customerOrderReportMapper;

    @Autowired
    private OsdService osdService;

    @Autowired
    private StoreService storeService;

    @Autowired
    private ReplayStoreMapper replayStoreMapper;

    @Autowired
    private CustomerTradeBaseService customerTradeBaseService;

    private static final String EXCEL_TYPE = "%s.xls";

    /**
     * 导出列字段
     * @return
     */
    public Column[] getCustomerColumn(){
        return new Column[]{
            new Column("客户名称", new SpelColumnRender<CustomerReport>("name")),
                    new Column("客户账号", new SpelColumnRender<CustomerReport>("account")),
                    new Column("下单笔数", new SpelColumnRender<CustomerReport>("orderCount")),
                    new Column("下单件数", new SpelColumnRender<CustomerReport>("skuCount")),
                    new Column("下单金额", (cell, object) -> {
                        CustomerReport customerReport = (CustomerReport) object;
                        cell.setCellValue(customerReport.getAmount().toString());
                    }),
                    new Column("付款订单数", new SpelColumnRender<CustomerReport>("payOrderCount")),
                    new Column("付款金额", (cell, object) -> {
                        CustomerReport customerReport = (CustomerReport) object;
                        cell.setCellValue(customerReport.getPayAmount().toString());
                    }),
                    new Column("笔单价", (cell, object) -> {
                        CustomerReport customerReport = (CustomerReport) object;
                        if (Objects.nonNull(customerReport.getOrderPerPrice())) {
                            cell.setCellValue(customerReport.getOrderPerPrice().setScale(2, RoundingMode.HALF_UP).toString());
                        } else {
                            cell.setCellValue(BigDecimal.ZERO.setScale(2).toString());
                        }
                    }),
                    new Column("退单笔数", new SpelColumnRender<CustomerReport>("returnCount")),
                    new Column("退货件数", new SpelColumnRender<CustomerReport>("returnSkuCount")),
                    new Column("退单金额", (cell, object) -> {
                        CustomerReport customerReport = (CustomerReport) object;
                        cell.setCellValue(customerReport.getReturnAmount().toString());
                    })
        };
    }

    /**
     * 会员报表构造
     *
     * @param customerReports customerReports
     * @param excelHelper     excelHelper
     */
    void customerReportSheet(List<CustomerReport> customerReports, ExcelHelper excelHelper) {
        excelHelper.addSheet("客户订货报表-按客户查看", getCustomerColumn(), customerReports);
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
        if (StringUtils.isBlank(exportQuery.getCompanyId())) {
            exportQuery.setCompanyId(Constants.BOSS_ID);
        }
        int storeType = exportQuery.getStoreSelectType() == null ? 0 : exportQuery.getStoreSelectType().toValue();
        LocalDate endDate = DateUtil.parse2Date(exportQuery.getDateTo(), DateUtil.FMT_DATE_1);
        String randomData = RandomString.get().randomAlpha(4);
        //客户名称
        String commonFileName = String.format("customer/order/%s/%s客户订货报表_按客户查看_%s_%s-%s", DateUtil.format(endDate,
                DateUtil.FMT_MONTH_2), storeService.getStoreName(exportQuery), exportQuery.getDateFrom(),
                exportQuery.getDateTo(), randomData);
        commonFileName = osdService.getFileRootPath().concat(commonFileName);
//        Store store = replayStoreMapper.queryByCompanyInfoId(Long.valueOf(exportQuery.getCompanyId()));
//        if (exportQuery.getCompanyId().equals(Constants.BOSS_ID) || store.getCompanyType() == 0) {
//            exportQuery.setCompanyId(Constants.BOSS_ID);
//        }
        CustomerOrderDataRequest customerOrderDataRequest = new CustomerOrderDataRequest();
        customerOrderDataRequest.setBeginDate(DateUtil.parse2Date(exportQuery.getDateFrom(), DateUtil.FMT_DATE_1));
        customerOrderDataRequest.setEndDate(DateUtil.parse2Date(exportQuery.getDateTo(), DateUtil.FMT_DATE_1).plusDays(1));
        customerOrderDataRequest.setStoreSelectType(storeType);

        int totalNum = customerOrderReportMapper.exportCustomerOrderTotalForBoss(customerOrderDataRequest);

        //查询单个门店或者商家
        if (!exportQuery.getCompanyId().equals(Constants.BOSS_ID)) {
            customerOrderDataRequest.setCompanyInfoId(Integer.parseInt(exportQuery.getCompanyId()));
            totalNum = customerOrderReportMapper.exportCustomerOrderTotalForSupplier(customerOrderDataRequest);
        }
        //查询说有门店或者商家
        else if (storeType != 0) {
            totalNum = customerOrderReportMapper.exportCustomerOrderTotalForSupplierByStoreType(customerOrderDataRequest);
        }

        if (totalNum == 0) {
            String fileName = String.format(EXCEL_TYPE, commonFileName);
            if (!osdService.existsFiles(fileName)) {
                ExcelHelper excelHelper = new ExcelHelper();
                customerReportSheet(Lists.newArrayList(), excelHelper);
                ByteArrayOutputStream emptyStream = new ByteArrayOutputStream();
                excelHelper.write(emptyStream);
                osdService.uploadExcel(emptyStream, fileName);
            }
            fileUrl.add(fileName);
            return BaseResponse.success(fileUrl);
        }
        int fileSize = calPage(totalNum, exportQuery.getSize());
        for (int i = 0; i < fileSize; i++) {
            ExcelHelper excelHelper = new ExcelHelper();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            for (int num = 0; num < calPage(totalNum - i * exportQuery.getSize(), 500); num++) {
                customerOrderDataRequest.setNumber((long) num + i * 10);
                customerOrderDataRequest.setSize(500L);
                List<CustomerReport> exportCustomerReports = customerTradeBaseService.queryExport(exportQuery.getOperator(), exportQuery,customerOrderDataRequest);



                exportCustomerReports.forEach(v->{
                    if (Objects.equals(LogOutStatus.LOGGED_OUT.toValue(),v.getLogOutStatus())){
                        v.setAccount(v.getAccount()+ com.wanmi.sbc.common.util.Constants.LOGGED_OUT);
                    }
                });

                customerReportSheet(exportCustomerReports, excelHelper);
                excelHelper.write(byteArrayOutputStream);
                if (num > 9) {
                    break;
                }
            }
            String fileName = i == 0 ? String.format(EXCEL_TYPE, commonFileName) : String.format("%s(%s).xls", commonFileName,
                    String.valueOf(i));
            osdService.uploadExcel(byteArrayOutputStream, fileName);
            fileUrl.add(fileName);
        }
        return BaseResponse.success(fileUrl);
    }

}
