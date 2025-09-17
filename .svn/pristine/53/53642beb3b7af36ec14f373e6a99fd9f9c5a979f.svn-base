package com.wanmi.sbc.mq.report.service;

import com.alibaba.fastjson2.JSON;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.util.excel.Column;
import com.wanmi.sbc.common.util.excel.ExcelHelper;
import com.wanmi.sbc.common.util.excel.impl.SpelColumnRender;
import com.wanmi.sbc.customer.api.provider.ledgerreceiverrel.LedgerReceiverRelQueryProvider;
import com.wanmi.sbc.customer.api.request.ledgerreceiverrel.*;
import com.wanmi.sbc.common.enums.DeleteFlag;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import com.wanmi.sbc.elastic.api.provider.ledger.EsLedgerBindInfoQueryProvider;
import com.wanmi.sbc.elastic.api.request.ledger.EsLedgerBindInfoPageRequest;
import com.wanmi.sbc.elastic.bean.vo.ledger.EsLedgerBindInfoVO;
import com.wanmi.sbc.mq.report.entity.ExportData;
import com.wanmi.sbc.mq.report.entity.ReportUtil;
import com.wanmi.sbc.mq.report.osd.OsdService;
import com.wanmi.sbc.mq.report.service.base.ExportBaseService;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import java.io.ByteArrayOutputStream;

import org.springframework.beans.factory.annotation.Autowired;


@Service
@Slf4j
public class LedgerReceiverRelExportService implements ExportBaseService {

    @Autowired
    private EsLedgerBindInfoQueryProvider esLedgerBindInfoQueryProvider;

    @Resource
    private OsdService osdService;

    @Autowired
    private LedgerReceiverRelQueryProvider ledgerReceiverRelQueryProvider;

    private static final int SIZE = 5000;

    @Override
    public BaseResponse exportReport(ExportData data) throws Exception {

        EsLedgerBindInfoPageRequest queryReq = JSON.parseObject(data.getParam(), EsLedgerBindInfoPageRequest.class);
        queryReq.putSort("bindTime", "desc");

        LocalDateTime dateTime = LocalDateTime.now();
        String fileName = String.format("分账绑定关系列表_%s.xls", dateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmm")));
        String resourceKey = String.format("ledgerReceiverRel/excel/%s", fileName);

        LedgerReceiverRelExportRequest ledgerReceiverRelExportRequest = KsBeanUtil.convert(queryReq, LedgerReceiverRelExportRequest.class);
        ledgerReceiverRelExportRequest.setDelFlag(DeleteFlag.NO);

        Long total = ledgerReceiverRelQueryProvider.countForExport(ledgerReceiverRelExportRequest).getContext();

        ExcelHelper excelHelper = new ExcelHelper();
        Column[] columns = getColumns(queryReq.getReceiverType());
        SXSSFSheet sheet = excelHelper.addSXSSFSheetHead("分账绑定关系导出列表", columns);

        //总页数
        long fileSize = ReportUtil.calPage(total, SIZE);

        //分页处理
        int rowIndex = 0;
        queryReq.setPageSize(SIZE);
        for (int i = 0; i < fileSize; i++) {
            queryReq.setPageNum(i);
            // 导出数据查询
            List<EsLedgerBindInfoVO> dataRecords = esLedgerBindInfoQueryProvider.page(queryReq).getContext().getLedgerBindInfoVOPage().getContent();
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
     * 导出列表数据具体实现
     */
    private Column[] getColumns(Integer receiverType) {
        return receiverType == Constants.ONE ? new Column[]{
                new Column("供应商名称", new SpelColumnRender<EsLedgerBindInfoVO>("receiverName")),
                new Column("供应商编码", new SpelColumnRender<EsLedgerBindInfoVO>("receiverCode")),
                new Column("进件状态", (cell, object) -> {
                    EsLedgerBindInfoVO esLedgerBindInfoVO = (EsLedgerBindInfoVO) object;
                    Integer accountState =  ObjectUtils.defaultIfNull(esLedgerBindInfoVO.getAccountState(),Constants.MINUS_ONE);
                    String desc = "";
                    switch (accountState) {
                        case 0:
                            desc = "未进件";
                            break;
                        case 1:
                            desc = "审核中";
                            break;
                        case 2:
                            desc = "已进件";
                            break;
                        case 3:
                            desc = "审核失败";
                            break;
                        default:
                            break;
                    }
                    cell.setCellValue(desc);
                }),
                new Column("分账关系绑定状态", (cell, object) -> {
                    EsLedgerBindInfoVO esLedgerBindInfoVO = (EsLedgerBindInfoVO) object;
                    Integer bindState =  ObjectUtils.defaultIfNull(esLedgerBindInfoVO.getBindState(),Constants.MINUS_ONE);
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
        } : new Column[]{
                new Column("分销员名称", new SpelColumnRender<EsLedgerBindInfoVO>("receiverName")),
                new Column("分销员帐号", new SpelColumnRender<EsLedgerBindInfoVO>("receiverCode")),
                new Column("进件状态", (cell, object) -> {
                    EsLedgerBindInfoVO esLedgerBindInfoVO = (EsLedgerBindInfoVO) object;
                    Integer accountState =  ObjectUtils.defaultIfNull(esLedgerBindInfoVO.getAccountState(),Constants.MINUS_ONE);
                    String desc = "";
                    switch (accountState) {
                        case 0:
                            desc = "未进件";
                            break;
                        case 1:
                            desc = "审核中";
                            break;
                        case 2:
                            desc = "已进件";
                            break;
                        case 3:
                            desc = "审核失败";
                            break;
                        default:
                            break;
                    }
                    cell.setCellValue(desc);
                }),
                new Column("分账关系绑定状态", (cell, object) -> {
                    EsLedgerBindInfoVO esLedgerBindInfoVO = (EsLedgerBindInfoVO) object;
                    Integer bindState =  ObjectUtils.defaultIfNull(esLedgerBindInfoVO.getBindState(),Constants.MINUS_ONE);
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
