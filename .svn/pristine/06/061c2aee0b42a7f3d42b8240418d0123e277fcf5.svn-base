package com.wanmi.sbc.mq.report.service;

import com.alibaba.fastjson2.JSON;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.LogOutStatus;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.excel.Column;
import com.wanmi.sbc.common.util.excel.ExcelHelper;
import com.wanmi.sbc.common.util.excel.impl.SpelColumnRender;
import com.wanmi.sbc.customer.bean.vo.DistributionCustomerVO;
import com.wanmi.sbc.elastic.api.provider.customer.EsDistributionCustomerQueryProvider;
import com.wanmi.sbc.elastic.api.request.customer.EsDistributionCustomerPageRequest;
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
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author xuyunpeng
 * @className DistributionCustomerExportService
 * @description 分销员导出
 * @date 2021/6/7 3:45 下午
 **/
@Service
@Slf4j
public class DistributionCustomerExportService implements ExportBaseService {

    @Autowired
    private OsdService osdService;

    @Autowired
    private EsDistributionCustomerQueryProvider esDistributionCustomerQueryProvider;

    @Autowired
    private ExportUtilService exportUtilService;

    public static final int SIZE = 5000;

    @Override
    public BaseResponse exportReport(ExportData data) throws Exception {
        log.info("distrubutionCustomer export begin, param:{}", data);

        EsDistributionCustomerPageRequest pageRequest = JSON.parseObject(data.getParam(), EsDistributionCustomerPageRequest.class);

        LocalDateTime dateTime = LocalDateTime.now();
        String fileName = String.format("批量导出分销员_%s.xls"
                , dateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"))+exportUtilService.getRandomNum());
        String resourceKey = String.format("distributionCustomer/excel/%s", fileName);

        ExcelHelper excelHelper = new ExcelHelper();
        Column[] columns = getColumns();
        SXSSFSheet sheet = excelHelper.addSXSSFSheetHead("分销员导出", columns);

        Long total = esDistributionCustomerQueryProvider.countForCount(pageRequest).getContext();

        //总页数
        long fileSize = ReportUtil.calPage(total, SIZE);
        //分页处理
        int rowIndex = 0;
        pageRequest.setPageSize(SIZE);
        for (int i = 0; i < fileSize; i++) {
            pageRequest.setPageNum(i);
            List<DistributionCustomerVO> dataRecords = esDistributionCustomerQueryProvider.export(pageRequest).getContext().getDistributionCustomerVOList();
            //判断客户是否已注销
            Map<String, LogOutStatus> map = exportUtilService.getLogOutStatus(dataRecords.stream()
                    .map(DistributionCustomerVO::getCustomerId).collect(Collectors.toList()));
            dataRecords.forEach(dataRecord -> {
                if (Objects.equals(LogOutStatus.LOGGED_OUT,map.get(dataRecord.getCustomerId()))){
                    dataRecord.setCustomerAccount(dataRecord.getCustomerAccount()+Constants.LOGGED_OUT);
                }
            });
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
     * @date 2021/6/7 3:53 下午
     */
    public Column[] getColumns() {
        Column[] columns = {
                new Column("分销员名称", new SpelColumnRender<DistributionCustomerVO>("customerName")),
                new Column("分销员账号", new SpelColumnRender<DistributionCustomerVO>("customerAccount")),
                new Column("分销员等级", new SpelColumnRender<DistributionCustomerVO>("distributorLevelName")),
                new Column("加入时间", new SpelColumnRender<DistributionCustomerVO>("createTime")),
                new Column("邀新人数", new SpelColumnRender<DistributionCustomerVO>("inviteCount")),
                new Column("有效邀新数", new SpelColumnRender<DistributionCustomerVO>("inviteAvailableCount")),
                new Column("已入账邀新奖金", new SpelColumnRender<DistributionCustomerVO>("rewardCash")),
                new Column("未入账邀新奖金", new SpelColumnRender<DistributionCustomerVO>("rewardCashNotRecorded")),
                new Column("分销订单", new SpelColumnRender<DistributionCustomerVO>("distributionTradeCount")),
                new Column("销售额", new SpelColumnRender<DistributionCustomerVO>("sales")),
                new Column("已入账分销佣金", new SpelColumnRender<DistributionCustomerVO>("commission")),
                new Column("未入账分销佣金", new SpelColumnRender<DistributionCustomerVO>("commissionNotRecorded")),
                new Column("账号状态", (cell, object) -> {
                    DistributionCustomerVO distributionCustomer = (DistributionCustomerVO) object;
                    if (distributionCustomer.getForbiddenFlag().equals(DefaultFlag.YES)) {
                        cell.setCellValue("禁止分销");
                    } else if (distributionCustomer.getForbiddenFlag().equals(DefaultFlag.NO)) {
                        cell.setCellValue("启用");
                    }
                }),
        };
        return columns;
    }
}
