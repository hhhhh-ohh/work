package com.wanmi.sbc.mq.report.service;

import com.alibaba.fastjson2.JSON;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.excel.Column;
import com.wanmi.sbc.common.util.excel.ExcelHelper;
import com.wanmi.sbc.common.util.excel.impl.SpelColumnRender;
import com.wanmi.sbc.customer.api.provider.ledgerreceiverrel.LedgerReceiverRelQueryProvider;
import com.wanmi.sbc.customer.api.request.ledgerreceiverrel.ProviderBindStateQueryRequest;
import com.wanmi.sbc.customer.api.response.ledgerreceiverrel.BindStateQueryResponse;
import com.wanmi.sbc.customer.bean.vo.LakalaProviderBindVO;
import com.wanmi.sbc.mq.report.entity.ExportData;
import com.wanmi.sbc.mq.report.osd.OsdService;
import com.wanmi.sbc.mq.report.service.base.ExportBaseService;
import com.wanmi.sbc.mq.report.service.base.ExportUtilService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author edz
 * @className LakalaProviderBindStateExportService
 * @description TODO
 * @date 2022/9/16 10:09
 **/
@Service
@Slf4j
public class LakalaProviderBindStateExportService implements ExportBaseService {

    @Autowired
    private ExportUtilService exportUtilService;

    @Autowired
    private LedgerReceiverRelQueryProvider ledgerReceiverRelQueryProvider;

    @Resource
    private OsdService osdService;

    @Override
    public BaseResponse exportReport(ExportData data) throws Exception {
        log.info("inviteRecord export begin, param:{}", data);

        LocalDateTime dateTime = LocalDateTime.now();
        String fileName = String.format("批量导出商家供应商关系绑定_%s.xls"
                , dateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"))+exportUtilService.getRandomNum());
        String resourceKey = String.format("ledgerReceiverRel/excel/%s", fileName);
        ProviderBindStateQueryRequest queryReq = JSON.parseObject(data.getParam(), ProviderBindStateQueryRequest.class);

        ExcelHelper excelHelper = new ExcelHelper();
        Column[] columns = getColumns();
        SXSSFSheet sheet = excelHelper.addSXSSFSheetHead("分账绑定关系导出列表", columns);

        boolean nextPage = true;
        int rowIndex = 0;
        queryReq.setPageSize(500);
        while (nextPage){
            BindStateQueryResponse bindStateQueryResponse =
                    ledgerReceiverRelQueryProvider.queryProviderBindStatePage(queryReq).getContext();
            List<LakalaProviderBindVO> dataRecords =
                    bindStateQueryResponse.getLakalaProviderBindVOPage().getContent();
            excelHelper.addSXSSFSheetRow(sheet, columns, dataRecords, rowIndex + 1);
            rowIndex = rowIndex + dataRecords.size();
            nextPage = bindStateQueryResponse.getLakalaProviderBindVOPage().hasNext();
        }

        //上传
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        excelHelper.writeForSXSSF(outputStream);
        osdService.uploadExcel(outputStream, resourceKey);

        return BaseResponse.success(resourceKey);
    }

    public Column[] getColumns() {
        return new Column[]{
                new Column("供应商名称", new SpelColumnRender<LakalaProviderBindVO>("providerName")),
                new Column("供应商编码", new SpelColumnRender<LakalaProviderBindVO>("providerCode")),
                new Column("进件状态", (cell, object) -> cell.setCellValue("已进件")),
                new Column("分账关系绑定状态", (cell, object) -> {
                    LakalaProviderBindVO lakalaProviderBindVO = (LakalaProviderBindVO) object;
                    Integer bindState =  lakalaProviderBindVO.getLedgerBindState();
                    String desc = "";
                    switch (bindState) {
                        case 0:
                            desc = "未绑定";
                            break;
                        case 1:
                            desc = "审核中";
                            break;
                        case 2:
                            desc = "已绑定";
                            break;
                        case 3:
                            desc = "绑定失败";
                            break;
                        default:
                            break;
                    }
                    cell.setCellValue(desc);
                })
        };
    }
}
