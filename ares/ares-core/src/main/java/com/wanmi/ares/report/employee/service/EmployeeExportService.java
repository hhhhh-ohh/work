package com.wanmi.ares.report.employee.service;

import com.wanmi.ares.report.base.model.ExportQuery;
import com.wanmi.ares.report.service.ExportBaseService;
import com.wanmi.ares.source.service.StoreService;
import com.wanmi.ares.utils.excel.Column;
import com.wanmi.ares.utils.excel.ExcelHelper;
import com.wanmi.ares.utils.excel.impl.SpelColumnRender;
import com.wanmi.ares.utils.osd.OsdService;
import com.wanmi.ares.view.employee.EmployeePerformanceView;
import com.wanmi.sbc.common.base.BaseResponse;
import jodd.util.RandomString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>业务员报表下载实现</p>
 * Created by of628-wenzhi on 2017-11-03-上午11:44.
 */
@Service
@Slf4j
public class EmployeeExportService implements ExportBaseService {

    @Resource
    private EmployeeQueryServiceImpl queryService;

    @Resource
    private OsdService osdService;

    @Resource
    private StoreService storeService;

    private static final int SIZE = 5000;

    /**
     * 导出列字段
     * @return
     */
    public Column[] getPerformanceColumn(){
        return new Column[]{
                new Column("业务员", new SpelColumnRender<EmployeePerformanceView>("employeeName")),
                new Column("下单笔数", new SpelColumnRender<EmployeePerformanceView>("orderCount")),
                new Column("下单人数", new SpelColumnRender<EmployeePerformanceView>("customerCount")),
                new Column("下单金额", new SpelColumnRender<EmployeePerformanceView>("amount")),
                new Column("付款订单数", new SpelColumnRender<EmployeePerformanceView>("payCount")),
                new Column("付款人数", new SpelColumnRender<EmployeePerformanceView>("payCustomerCount")),
                new Column("付款金额", new SpelColumnRender<EmployeePerformanceView>("payAmount")),
                new Column("客单价", new SpelColumnRender<EmployeePerformanceView>("customerUnitPrice")),
                new Column("笔单价", new SpelColumnRender<EmployeePerformanceView>("orderUnitPrice")),
                new Column("退单笔数", new SpelColumnRender<EmployeePerformanceView>("returnCount")),
                new Column("退单人数", new SpelColumnRender<EmployeePerformanceView>("returnCustomerCount")),
                new Column("退单金额", new SpelColumnRender<EmployeePerformanceView>("returnAmount")),
        };
    }

    private void uploadPerformanceFile(List<EmployeePerformanceView> records, List<String> locations, ExportQuery query) throws IOException {
        //如果没有报表数据，则生成只有表头的excel文件
        ExcelHelper<EmployeePerformanceView> excelHelper = new ExcelHelper<>();
        excelHelper.addSheet(
                "业务员业绩统计",
                getPerformanceColumn(),
                records
        );
        String randomData = RandomString.get().randomAlpha(4);
        String fileName = String.format("employee/performance/%s/%s业务员业绩统计报表_%s-%s%s-%s.xls",
                StringUtils.left(query.getDateTo(), query.getDateTo().lastIndexOf('-')),
                storeService.getStoreName(query), query.getDateFrom(), query.getDateTo(), locations.isEmpty() ? "" :
                        "(" + locations.size() + ")", randomData);
        fileName = osdService.getFileRootPath().concat(fileName);
        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            excelHelper.write(os);
            osdService.uploadExcel(os, fileName);
            locations.add(fileName);
        }
    }

    @Override
    public BaseResponse exportReport(ExportQuery query) throws IOException {
        int i = 0;
        List<String> locations = new ArrayList<>();
        List<EmployeePerformanceView> records = queryService.exportViewByPerformance(query);
        long count = records.size();
        if (count != 0) {
            while (i < count) {
                List<EmployeePerformanceView> list = records.stream().skip(i).limit(SIZE).collect(Collectors.toList());
                //生成文件并上传
                uploadPerformanceFile(list, locations, query);
                i += list.size();
            }
        } else {
            records = Collections.emptyList();
            uploadPerformanceFile(records, locations, query);
        }

        return BaseResponse.success(locations);
    }
}
