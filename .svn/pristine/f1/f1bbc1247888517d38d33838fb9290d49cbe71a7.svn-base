package com.wanmi.sbc.mq.report.service;

import com.alibaba.fastjson2.JSON;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.LogOutStatus;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.SensitiveUtils;
import com.wanmi.sbc.common.util.excel.Column;
import com.wanmi.sbc.common.util.excel.ExcelHelper;
import com.wanmi.sbc.common.util.excel.impl.SpelColumnRender;
import com.wanmi.sbc.customer.api.provider.distribution.DistributionCommissionQueryProvider;
import com.wanmi.sbc.customer.api.request.distribution.DistributionCommissionExportRequest;
import com.wanmi.sbc.customer.bean.vo.DistributionCommissionForPageVO;
import com.wanmi.sbc.mq.report.entity.ExportData;
import com.wanmi.sbc.mq.report.entity.ReportUtil;
import com.wanmi.sbc.mq.report.osd.OsdService;
import com.wanmi.sbc.mq.report.service.base.DistributionCommissionBaseService;
import com.wanmi.sbc.mq.report.service.base.ExportBaseService;
import com.wanmi.sbc.mq.report.service.base.ExportUtilService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author xuyunpeng
 * @className DistributionCommissionExportService
 * @description 分销员佣金导出
 * @date 2021/6/7 2:34 下午
 **/
@Service
@Slf4j
public class DistributionCommissionExportService implements ExportBaseService {

    @Autowired
    private OsdService osdService;

    @Autowired
    private DistributionCommissionQueryProvider distributionCommissionQueryProvider;

    public static final int SIZE = 5000;

    @Autowired
    private ExportUtilService exportUtilService;

    @Autowired
    private DistributionCommissionBaseService distributionCommissionBaseService;

    @Override
    public BaseResponse exportReport(ExportData data) throws Exception {
        log.info("distributionCommission export begin, param:{}", data);

        LocalDateTime dateTime = LocalDateTime.now();
        String fileName = String.format("批量导出分销员佣金记录_%s.xls"
                , dateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"))+exportUtilService.getRandomNum());
        String resourceKey = String.format("distributionCommission/excel/%s", fileName);

        ExcelHelper excelHelper = new ExcelHelper();
        Column[] columns = getColumns();
        SXSSFSheet sheet = excelHelper.addSXSSFSheetHead("分销员佣金导出", columns);

        DistributionCommissionExportRequest queryReq = JSON.parseObject(data.getParam(), DistributionCommissionExportRequest.class);
        Long total = distributionCommissionQueryProvider.countForExport(queryReq).getContext();
        //总页数
        long fileSize = ReportUtil.calPage(total, SIZE);
        //分页处理
        int rowIndex = 0;
        queryReq.setPageSize(SIZE);
        for (int i = 0; i < fileSize; i++) {
            queryReq.setPageNum(i);

            List<DistributionCommissionForPageVO> pageVOS = distributionCommissionBaseService.queryExport(data.getOperator(),queryReq);

            if(CollectionUtils.isNotEmpty(pageVOS)){
                //判断客户是否已注销
                Map<String, LogOutStatus> map = exportUtilService.getLogOutStatus(pageVOS.stream()
                        .map(DistributionCommissionForPageVO::getCustomerId).collect(Collectors.toList()));
                pageVOS.forEach(dataRecord -> {
                    if (Objects.equals(LogOutStatus.LOGGED_OUT,map.get(dataRecord.getCustomerId()))){
                        dataRecord.setCustomerAccount(dataRecord.getCustomerAccount()+Constants.LOGGED_OUT);
                    }
                });
            }

            excelHelper.addSXSSFSheetRow(sheet, columns, pageVOS, rowIndex + 1);
            rowIndex = rowIndex + pageVOS.size();
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
                new Column("分销员名称", new SpelColumnRender<DistributionCommissionForPageVO>("customerName")),
                new Column("分销员账号", (cell, object) -> {
                    DistributionCommissionForPageVO distributionCommissionForPageVO = (DistributionCommissionForPageVO) object;
                    // 账号脱敏
                    cell.setCellValue(SensitiveUtils.handlerMobilePhone(distributionCommissionForPageVO.getCustomerAccount()));
                }),
                new Column("分销员等级", new SpelColumnRender<DistributionCommissionForPageVO>("distributorLevelName")),
                new Column("加入时间", new SpelColumnRender<DistributionCommissionForPageVO>("createTime")),
                new Column("账号状态", (cell, object) -> {
                    DistributionCommissionForPageVO distributionCommissionForPageVO = (DistributionCommissionForPageVO) object;
                    if (distributionCommissionForPageVO.getForbiddenFlag().equals(DefaultFlag.YES)) {
                        cell.setCellValue("禁止分销");
                    } else if (distributionCommissionForPageVO.getForbiddenFlag().equals(DefaultFlag.NO)) {
                        cell.setCellValue("启用");
                    }
                }),
                new Column("已入账佣金", (cell, object) -> {
                    DistributionCommissionForPageVO distributionCommissionForPageVO = (DistributionCommissionForPageVO) object;
                    if (Objects.nonNull(distributionCommissionForPageVO.getCommissionTotal())) {
                        cell.setCellValue(distributionCommissionForPageVO.getCommissionTotal().toString());
                    } else {
                        cell.setCellValue("0");
                    }
                }),
                new Column("已入账分销佣金", (cell, object) -> {
                    DistributionCommissionForPageVO distributionCommissionForPageVO = (DistributionCommissionForPageVO) object;
                    if (Objects.nonNull(distributionCommissionForPageVO.getCommission())) {
                        cell.setCellValue(distributionCommissionForPageVO.getCommission().toString());
                    } else {
                        cell.setCellValue("0");
                    }
                }),
                new Column("已入账邀新奖金", (cell, object) -> {
                    DistributionCommissionForPageVO distributionCommissionForPageVO = (DistributionCommissionForPageVO) object;
                    if (Objects.nonNull(distributionCommissionForPageVO.getRewardCash())) {
                        cell.setCellValue(distributionCommissionForPageVO.getRewardCash().toString());
                    } else {
                        cell.setCellValue("0");
                    }
                }),
                new Column("未入账分销佣金", (cell, object) -> {
                    DistributionCommissionForPageVO distributionCommissionForPageVO = (DistributionCommissionForPageVO) object;
                    if (Objects.nonNull(distributionCommissionForPageVO.getCommissionNotRecorded())) {
                        cell.setCellValue(distributionCommissionForPageVO.getCommissionNotRecorded().toString());
                    } else {
                        cell.setCellValue("0");
                    }
                }),
                new Column("未入账邀新奖金", (cell, object) -> {
                    DistributionCommissionForPageVO distributionCommissionForPageVO = (DistributionCommissionForPageVO) object;
                    if (Objects.nonNull(distributionCommissionForPageVO.getRewardCashNotRecorded())) {
                        cell.setCellValue(distributionCommissionForPageVO.getRewardCashNotRecorded().toString());
                    } else {
                        cell.setCellValue("0");
                    }
                }),
        };
        return columns;
    }
}
