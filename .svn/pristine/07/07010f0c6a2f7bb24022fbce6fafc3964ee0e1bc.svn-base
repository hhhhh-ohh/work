package com.wanmi.ares.report.employee.service;

import com.wanmi.ares.report.base.model.ExportQuery;
import com.wanmi.ares.report.employee.dao.EmployeeClientReportMapper;
import com.wanmi.ares.report.service.ExportBaseService;
import com.wanmi.ares.source.service.StoreService;
import com.wanmi.ares.utils.excel.Column;
import com.wanmi.ares.utils.excel.ExcelHelper;
import com.wanmi.ares.utils.excel.impl.SpelColumnRender;
import com.wanmi.ares.utils.osd.OsdService;
import com.wanmi.ares.view.employee.EmployeeClientView;
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

/**
 * <p>业务员报表下载实现</p>
 * Created by of628-wenzhi on 2017-11-03-上午11:44.
 */
@Service
@Slf4j
public class EmployeeCustomerExportService implements ExportBaseService {

    @Resource
    private EmployeeQueryServiceImpl queryService;

    @Resource
    private OsdService osdService;

    @Resource
    private EmployeeClientReportMapper clientMapper;

    @Resource
    private StoreService storeService;

    private static final int SIZE = 5000;

    /**
     * 导出列字段
     * @return
     */
    public Column[] getClientColumn(){
        return new Column[]{
                new Column("业务员", new SpelColumnRender<EmployeeClientView>("employeeName")),
                new Column("新增客户数", new SpelColumnRender<EmployeeClientView>("newlyNum"))
        };
    }

    private void uploadClientFile(List<EmployeeClientView> records, List<String> locations, ExportQuery query)
            throws IOException {
        //如果没有报表数据，则生成只有表头的excel文件
        ExcelHelper<EmployeeClientView> excelHelper = new ExcelHelper<>();
        excelHelper.addSheet(
                "业务员获客统计",
                getClientColumn(),
                records
        );
        String randomData = RandomString.get().randomAlpha(4);
        String fileName = String.format("employee/client/%s/%s业务员获客统计报表_%s-%s%s-%s.xls",
                StringUtils.left(query.getDateTo(), query.getDateTo().lastIndexOf('-')),
                storeService.getStoreName(query),
                query.getDateFrom(),
                query.getDateTo(),
                locations.isEmpty() ? "" : "(" + locations.size() + ")",
                randomData
        );
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
        long count = clientMapper.countOfExport(query, query.getStoreSelectType() == null ? 0 : query.getStoreSelectType().toValue());
        List<EmployeeClientView> records;
        if (count != 0) {
            query.setSize(SIZE);
            while (i < count) {
                query.setBeginIndex(i);
                records = queryService.exportViewByClient(query);
                //生成文件并上传
                uploadClientFile(records, locations, query);
                i += records.size();
            }
        } else {
            records = Collections.emptyList();
            uploadClientFile(records, locations, query);
        }

        return BaseResponse.success(locations);
    }
}
