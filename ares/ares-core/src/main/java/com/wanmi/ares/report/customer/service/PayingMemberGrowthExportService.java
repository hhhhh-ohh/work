package com.wanmi.ares.report.customer.service;

import com.wanmi.ares.base.PageRequest;
import com.wanmi.ares.report.base.model.ExportQuery;
import com.wanmi.ares.report.customer.model.root.CustomerGrowthReport;
import com.wanmi.ares.report.paymember.dao.PayMemberGrowReportMapper;
import com.wanmi.ares.report.paymember.model.root.PayMemberGrowReport;
import com.wanmi.ares.report.service.ExportBaseService;
import com.wanmi.ares.request.paymember.PayMemberGrowthRequest;
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

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.List;

/**
 * @author xuyunpeng
 * @className PayingMemberGrowthExportService
 * @description 付费会员统计报表下载
 * @date 2022/5/27 11:20 AM
 **/
@Service
@Slf4j
public class PayingMemberGrowthExportService  implements ExportBaseService {
    @Autowired
    private OsdService osdService;

    @Autowired
    private PayMemberGrowReportMapper payMemberGrowReportMapper;

    private static final String EXCEL_TYPE = "%s.xls";

    @Override
    public BaseResponse exportReport(ExportQuery exportQuery) throws Exception {
        List<String> fileUrl = Lists.newArrayList();
        PayMemberGrowthRequest request = new PayMemberGrowthRequest();
        request.setStartDate(exportQuery.getDateFrom());
        request.setEndDate(exportQuery.getDateTo());
        request.setSortField("base_date");
        request.setSortTypeText("DESC");

        PageRequest pageRequest = new PageRequest();
        pageRequest.setPageSize(exportQuery.getSize());

        int total = payMemberGrowReportMapper.queryGrowthTotal(request);
        LocalDate endDate = DateUtil.parse2Date(exportQuery.getDateTo(), DateUtil.FMT_DATE_1);
        String randomData = RandomString.get().randomAlpha(4);
        String commonFileName = String.format("payMember/growth/%s/%s_%s_%s-%s", DateUtil.format(endDate,
                        DateUtil.FMT_MONTH_2), getSheetName(exportQuery.getPayMemberNewType()), exportQuery.getDateFrom(),
                exportQuery.getDateTo(), randomData);
        commonFileName = osdService.getFileRootPath().concat(commonFileName);
        if (total < 1) {
            String fileName = String.format(EXCEL_TYPE, commonFileName);
            //不存在，则上传
            if (!osdService.existsFiles(fileName)) {
                ExcelHelper<PayMemberGrowReport> excelHelper = new ExcelHelper<>();
                customerGrowthSheet(Lists.newArrayList(), excelHelper, exportQuery.getPayMemberNewType());
                ByteArrayOutputStream emptyStream = new ByteArrayOutputStream();
                excelHelper.write(emptyStream);
                osdService.uploadExcel(emptyStream, fileName);
            }
            fileUrl.add(fileName);
            return BaseResponse.success(fileUrl);
        }

        int page = calPage(total, exportQuery.getSize());

        for (int i = 0; i < page; i++) {
            pageRequest.setStart(i * exportQuery.getSize());
            List<PayMemberGrowReport> payMemberGrowReports = payMemberGrowReportMapper.findAllGrowReport(request, pageRequest);

            if (CollectionUtils.isEmpty(payMemberGrowReports)) {
                return BaseResponse.success(fileUrl);
            }

            ExcelHelper<PayMemberGrowReport> excelHelper = new ExcelHelper<>();
            customerGrowthSheet(payMemberGrowReports, excelHelper, exportQuery.getPayMemberNewType());
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            excelHelper.write(byteArrayOutputStream);
            String fileName = i == 0 ? String.format(EXCEL_TYPE, commonFileName) : String.format("%s(%s).xls", commonFileName,
                    String.valueOf(i));
            osdService.uploadExcel(byteArrayOutputStream, fileName);
            fileUrl.add(fileName);
        }
        return BaseResponse.success(fileUrl);
    }

    /**
     * 导出列字段-增长报表
     * @return
     */
    public Column[] getGrowthNewColumn(){
        return new Column[]{
                new Column("日期", new SpelColumnRender<CustomerGrowthReport>("baseDate")),
                new Column("会员总数", new SpelColumnRender<CustomerGrowthReport>("allCount")),
                new Column("新增会员数", new SpelColumnRender<CustomerGrowthReport>("dayGrowthCount")),
        };
    }

    /**
     * 导出列字段-续费报表
     * @return
     */
    public Column[] getGrowthRenewalColumn(){
        return new Column[]{
                new Column("日期", new SpelColumnRender<CustomerGrowthReport>("baseDate")),
                new Column("会员续费数", new SpelColumnRender<CustomerGrowthReport>("dayRenewalCount")),
                new Column("会员到期未续费数", new SpelColumnRender<CustomerGrowthReport>("dayOvertimeCount")),
        };
    }

    /**
     * 会员增长报表格式
     *
     * @param reports
     * @param excelHelper
     */
    private void customerGrowthSheet(List<PayMemberGrowReport> reports, ExcelHelper<PayMemberGrowReport> excelHelper, Boolean payMemberNewType) {
        if (payMemberNewType) {
            excelHelper.addSheet(getSheetName(payMemberNewType), getGrowthNewColumn(), reports);
        } else {
            excelHelper.addSheet(getSheetName(payMemberNewType), getGrowthRenewalColumn(), reports);
        }
    }

    private String getSheetName(Boolean payMemberNewType) {
        if (payMemberNewType) {
            return "会员增长报表";
        } else {
            return "会员续费报表";
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


}
