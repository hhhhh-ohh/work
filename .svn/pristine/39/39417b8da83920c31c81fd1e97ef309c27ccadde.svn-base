package com.wanmi.sbc.mq.report.service;

import com.alibaba.fastjson2.JSON;
import com.wanmi.sbc.account.bean.enums.PayType;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.*;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.DateUtil;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.util.excel.Column;
import com.wanmi.sbc.common.util.excel.ExcelHelper;
import com.wanmi.sbc.common.util.excel.impl.SpelColumnRender;
import com.wanmi.sbc.empower.bean.enums.WxSceneGroup;
import com.wanmi.sbc.marketing.bean.enums.BookingType;
import com.wanmi.sbc.marketing.bean.enums.CommunitySalesType;
import com.wanmi.sbc.marketing.bean.enums.GiftCardType;
import com.wanmi.sbc.mq.report.entity.DisabledExportRequest;
import com.wanmi.sbc.mq.report.entity.ExportData;
import com.wanmi.sbc.mq.report.entity.ReportUtil;
import com.wanmi.sbc.mq.report.entity.TradeExportRequest;
import com.wanmi.sbc.mq.report.osd.OsdService;
import com.wanmi.sbc.mq.report.service.base.ExportBaseService;
import com.wanmi.sbc.mq.report.service.base.ExportUtilService;
import com.wanmi.sbc.mq.report.service.base.TradeBaseService;
import com.wanmi.sbc.order.api.provider.trade.ProviderTradeQueryProvider;
import com.wanmi.sbc.order.api.provider.trade.TradeQueryProvider;
import com.wanmi.sbc.order.api.request.trade.TradeListExportRequest;
import com.wanmi.sbc.order.bean.dto.DisabledDTO;
import com.wanmi.sbc.order.bean.dto.TradeQueryDTO;
import com.wanmi.sbc.order.bean.enums.DeliverStatus;
import com.wanmi.sbc.order.bean.enums.FlowState;
import com.wanmi.sbc.order.bean.enums.PayState;
import com.wanmi.sbc.order.bean.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author 王超
 * @className TradeExportService
 * @description 社区团长佣金明细导出
 * @date 2023/8/1 10:43 上午
 **/
@Service
@Slf4j
public class CommunityCommissionDetailExportService implements ExportBaseService {

    @Autowired
    private TradeQueryProvider tradeQueryProvider;

    @Autowired
    private ProviderTradeQueryProvider providerTradeQueryProvider;

    @Autowired
    private OsdService osdService;

    @Autowired
    private ExportUtilService exportUtilService;

    @Autowired
    private TradeBaseService tradeBaseService;


    private static final int SIZE = 5000;

    @Override
    public BaseResponse exportReport(ExportData data) throws Exception {
        LocalDateTime dateTime = LocalDateTime.now();
        String fileName = String.format("批量导出社区团长佣金明细记录_%s.xls"
                , dateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"))+exportUtilService.getRandomNum());
        String resourceKey = String.format("communityCommissionDetail/excel/%s/%s/%s", data.getCompanyInfoId(), data.getUserId(), fileName);
        TradeExportRequest tradeExportRequest = JSON.parseObject(data.getParam(), TradeExportRequest.class);
        // 标识Map，用于生成不同的columns
        Map<String, Object> typeMap = new HashMap<>();
        // 标识代销平台
        typeMap.put("sellPlatformType", tradeExportRequest.getSellPlatformType());

        TradeListExportRequest exportRequest = getTradeRequest(data,tradeExportRequest);
        exportRequest.getTradeQueryDTO().setSortColumn("communityTradeCommission.settlementTime");
        exportRequest.getTradeQueryDTO().setSortRole("desc");
        exportRequest.getTradeQueryDTO().setCustomSortFlag(Boolean.TRUE);
        Long total = tradeQueryProvider.countTradeExport(exportRequest).getContext();
        //总页数
        long fileSize = ReportUtil.calPage(total, SIZE);

        //写入表头
        ExcelHelper excelHelper = new ExcelHelper();
        Column[] columns = this.getColumns();
        SXSSFSheet sheet = excelHelper.addSXSSFSheetHead("团长佣金明细导出", columns);

        //如果没有数据，直接生成空Excel
        if (total.equals(NumberUtils.LONG_ZERO)) {
            ByteArrayOutputStream emptyStream = new ByteArrayOutputStream();
            excelHelper.writeForSXSSF(emptyStream);
            osdService.uploadExcel(emptyStream, resourceKey);
            return BaseResponse.success(resourceKey);
        }

        //分页查询、导出
        exportRequest.getTradeQueryDTO().setPageSize(SIZE);
        exportRequest.getTradeQueryDTO().setLeaderCommissionDetailExport(Boolean.TRUE);
        try {
            exportTrade(excelHelper, sheet, columns, fileSize, exportRequest, data);
        } catch (Exception e) {
            excelHelper.deleteTempFile();
            throw e;
        }

        //上传
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        excelHelper.writeForSXSSF(outputStream);
        osdService.uploadExcel(outputStream, resourceKey);
        return BaseResponse.success(resourceKey);
    }

    /**
     * @description 获取订单数量（商家/平台）
     * @author  xuyunpeng
     * @date 2021/6/1 6:46 下午
     * @param data
     * @return
     */
    public TradeListExportRequest getTradeRequest(ExportData data,TradeExportRequest tradeExportRequest) {

        if (StringUtils.isNotEmpty(data.getAdminId())) {
            tradeExportRequest.setSupplierId(Long.valueOf(data.getAdminId()));
        }

        //设定状态条件逻辑,已审核状态需筛选出已审核与部分发货
        tradeExportRequest.makeAllAuditFlow();
        TradeQueryDTO tradeQueryDTO = KsBeanUtil.convert(tradeExportRequest, TradeQueryDTO.class);
        TradeListExportRequest exportRequest = TradeListExportRequest.builder().tradeQueryDTO(tradeQueryDTO).build();
        return exportRequest;
    }

    /**
     * @description 导出trade
     * @author  xuyupeng
     * @date 2021/6/1 2:45 下午
     * @return
     */
    public void exportTrade(ExcelHelper excelHelper, SXSSFSheet sheet, Column[] columns, long fileSize, TradeListExportRequest exportRequest, ExportData data) throws Exception {
        //分页写获取数据并写入excel
        int rowIndex = 0;
        for (int i = 0; i < fileSize; i++) {
            exportRequest.getTradeQueryDTO().setPageNum(i);
            List<TradeVO> tradeVOS = tradeBaseService.getTrade(data.getOperator(), exportRequest);
            addSXSSFSheetRowForTrade(sheet, columns, tradeVOS, rowIndex + 1);
            rowIndex = rowIndex + tradeVOS.size();
        }
    }

    /**
     * 分页导出Excel
     * @author  xufeng
     * @date 2021/12/15 10:37 下午
     * @param sheet
     * @param columns
     * @param dataList
     * @param rowIndex
     * @return void
     **/
    public void addSXSSFSheetRowForTrade(SXSSFSheet sheet, Column[] columns, List<TradeVO> dataList,int rowIndex) {
        for (TradeVO data : dataList) {
            int cellIndex = 0;
            SXSSFRow row = sheet.createRow(rowIndex);
            for (Column column : columns) {
                SXSSFCell cell = row.createCell(cellIndex++);
                column.getRender().render(cell, data);
            }
            rowIndex++;
        }
    }

    /**
     * @description
     * @author  佣金明细报表表头
     * @date 2021/6/1 2:06 下午
     * @return
     */
    public Column[] getColumns() {
        Column[] columns = {
                new Column("入账时间", (cell, object) -> {
                    TradeVO trade = (TradeVO) object;
                    CommunityTradeCommissionVO vo = trade.getCommunityTradeCommission();
                    if (Objects.nonNull(vo) && Objects.nonNull(vo.getSettlementTime())){
                        cell.setCellValue(vo.getSettlementTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                    }else{
                        cell.setCellValue("-");
                    }
                }),
                new Column("业务编号", new SpelColumnRender<TradeVO>("id")),
                new Column("账务类型", (cell, object) -> {
                    TradeVO trade = (TradeVO) object;
                    CommunityTradeCommissionVO vo = trade.getCommunityTradeCommission();
                    if (Objects.nonNull(vo) && Objects.nonNull(vo.getSalesType()) ){
                        if(vo.getSalesType().equals(CommunitySalesType.SELF)){
                            cell.setCellValue("自提服务佣金");
                        }else if(vo.getSalesType().equals(CommunitySalesType.LEADER)){
                            cell.setCellValue("帮卖佣金");
                        }else{
                            cell.setCellValue("-");
                        }
                    }else{
                        cell.setCellValue("-");
                    }
                }),
                new Column("收支金额", (cell, object) -> {
                    TradeVO trade = (TradeVO) object;
                    CommunityTradeCommissionVO vo = trade.getCommunityTradeCommission();
                    if (Objects.nonNull(vo) && Objects.nonNull(vo.getTotalCommission())){
                        cell.setCellValue("+" + vo.getTotalCommission());
                    }else{
                        cell.setCellValue("0");
                    }
                })
        };
        return columns;
    }

}
