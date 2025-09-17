package com.wanmi.sbc.mq.report.service;

import com.alibaba.fastjson2.JSON;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.DateUtil;
import com.wanmi.sbc.common.util.excel.Column;
import com.wanmi.sbc.common.util.excel.ExcelHelper;
import com.wanmi.sbc.common.util.excel.impl.SpelColumnRender;
import com.wanmi.sbc.elastic.api.provider.operationlog.EsOperationLogQueryProvider;
import com.wanmi.sbc.elastic.api.request.operationlog.EsOperationLogListRequest;
import com.wanmi.sbc.elastic.bean.vo.operationlog.EsOperationLogVO;
import com.wanmi.sbc.mq.report.entity.ExportData;
import com.wanmi.sbc.mq.report.entity.ReportUtil;
import com.wanmi.sbc.mq.report.osd.OsdService;
import com.wanmi.sbc.mq.report.service.base.ExportBaseService;
import com.wanmi.sbc.mq.report.service.base.ExportUtilService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author xuyunpeng
 * @className OperationLogExportService
 * @description 操作日志导出
 * @date 2021/6/7 5:06 下午
 **/
@Service
@Slf4j
public class OperationLogExportService implements ExportBaseService {

    @Autowired
    private EsOperationLogQueryProvider esOperationLogQueryProvider;

    @Autowired
    private OsdService osdService;

    @Autowired
    private ExportUtilService exportUtilService;

    public static final int SIZE = 5000;

    @Override
    public BaseResponse exportReport(ExportData data) throws Exception {
        log.info("operationLog export begin, param:{}", data);

        LocalDateTime dateTime = LocalDateTime.now();
        String fileName = String.format("操作日志_%s.xls"
                , dateTime.format(DateTimeFormatter.ofPattern(DateUtil.FMT_TIME_3))+exportUtilService.getRandomNum());
        String resourceKey = String.format("operationLog/excel/%s", fileName);

        ExcelHelper excelHelper = new ExcelHelper();
        Column[] columns = getColumns();
        SXSSFSheet sheet = excelHelper.addSXSSFSheetHead("操作日志", columns);

        EsOperationLogListRequest esOperationLogListRequest = JSON.parseObject(data.getParam(), EsOperationLogListRequest.class);
        Long total = esOperationLogQueryProvider.count(esOperationLogListRequest).getContext();
        //总页数
        long fileSize = ReportUtil.calPage(total, SIZE);
        //分页处理
        int rowIndex = 0;
        esOperationLogListRequest.setPageSize(SIZE);
        for (int i = 0; i < fileSize; i++) {
            esOperationLogListRequest.setPageNum(i);
            // 导出数据查询
            List<EsOperationLogVO> dataRecords = esOperationLogQueryProvider.queryOpLogByCriteria(esOperationLogListRequest).getContext().getOpLogPage().getContent();
            excelHelper.addSXSSFSheetRow(sheet, columns, dataRecords, rowIndex + 1);
            rowIndex = rowIndex + dataRecords.size();
        }

        //上传
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        excelHelper.writeForSXSSF(outputStream);
        osdService.uploadExcel(outputStream, resourceKey);

        return BaseResponse.success(resourceKey);
    }

    /**
     * @return
     * @description 获取表头
     * @author xuyunpeng
     * @date 2021/6/7 2:37 下午
     */
    public Column[] getColumns() {
        Column[] columns = {
                new Column("操作人账号", new SpelColumnRender<EsOperationLogVO>("opAccount")),
                new Column("操作人姓名", new SpelColumnRender<EsOperationLogVO>("opName")),
                new Column("操作人IP", new SpelColumnRender<EsOperationLogVO>("opIp")),
                new Column("操作时间", new SpelColumnRender<EsOperationLogVO>("opTime")),
                new Column("模块", new SpelColumnRender<EsOperationLogVO>("opModule")),
                new Column("操作类型", new SpelColumnRender<EsOperationLogVO>("opCode")),
                new Column("操作内容", new SpelColumnRender<EsOperationLogVO>("opContext"))
        };
        return columns;
    }
}
